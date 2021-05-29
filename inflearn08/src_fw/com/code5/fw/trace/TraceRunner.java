package com.code5.fw.trace;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.MakeRnd;
import com.code5.fw.data.SessionB;
import com.code5.fw.web.Admin;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.Reload;

/**
 * @author zero
 *
 */
public final class TraceRunner implements Reload {

	/**
	 * 
	 * /**
	 * 
	 */
	private static TraceRunner TRACE = new TraceRunner();

	/**
	 * 
	 */
	private TraceRunner() {

		InitYaml init = InitYaml.get();
		this.hostName = init.getHostName();
		this.appName = init.getAppName();
		reload();
		Admin.addReload(this);
	}

	/**
	 * @return
	 */
	public static TraceRunner getTraceRunner() {

		return TRACE;
	}

	/**
	 * 
	 */
	private String logDir = null;

	/**
	 * 
	 */
	private String logFilePatten = null;

	/**
	 * 
	 */
	private boolean isWriteLog = false;

	/**
	 * 
	 */
	private final long logFileSizeLimit = 1024 * 1000 * 10;

	/**
	 * 
	 */
	private ConcurrentHashMap<String, TraceWriter> traceWriterMap = new ConcurrentHashMap<String, TraceWriter>();

	/**
	 * 
	 */
	private String rnd = MakeRnd.createRnd(8);

	/**
	 * 
	 */
	private boolean isLogDir = false;

	/**
	 * 
	 */
	private boolean isMulti = false;

	/**
	 * 
	 */
	private String hostName = null;

	/**
	 * 
	 */
	private String appName = null;

	/**
	 * @param logKey
	 * @return
	 */
	private String makeLogFileUrl(String logKey) {

		if (!this.isLogDir) {
			return null;
		}

		if (logKey.contains(".")) {
			(new RuntimeException("no logKey [" + logKey + "]")).printStackTrace();
			return null;
		}

		String logFileName = this.logFilePatten;

		logFileName = logFileName.replace("[rnd]", this.rnd);
		logFileName = logFileName.replace("[name]", logKey);

		return this.logDir + File.separatorChar + logFileName;
	}

	/**
	 * 
	 * 
	 * @param log
	 * @return
	 */
	private String makeLogStr(String className, String log, Box box) {

		String classNameShort = makeClassNameShort(className);

		String dtm = DateTime.getThisDTMByForm();

		int hashCode = box.hashCode();

		StringBuffer sb = new StringBuffer();
		sb.append("[" + dtm + "]");
		sb.append("[" + hashCode + "]");
		sb.append("[" + classNameShort + "]");

		String serviceKey = box.s(Box.KEY_SERVICE);
		if (!"".equals(serviceKey)) {
			sb.append("[" + serviceKey + "]");
		}

		SessionB user = box.getSessionB();
		if (user != null) {
			if (user.isLogin()) {
				sb.append("[" + user.getId() + "]");
			}
		}

		sb.append(log);

		return sb.toString();

	}

	/**
	 * @param logFileUrl
	 */
	private void rollingLogFile(String logFileUrl) {

		File file = new File(logFileUrl);
		String renameLogFileUrl = logFileUrl + "." + DateTime.getThisDTM();
		File renameFile = new File(renameLogFileUrl);
		file.renameTo(renameFile);

	}

	/**
	 * @param logFileUrl
	 * @return
	 */
	private boolean isRolling(String logFileUrl) {

		File file = new File(logFileUrl);

		if (!file.isFile()) {
			return false;
		}

		if (file.length() > this.logFileSizeLimit) {
			return true;
		}

		return false;

	}

	/**
	 * @param logKey
	 * @param className
	 * @param classNameShort
	 * @param log
	 */
	void write(String logKey, String className, String log) {

		Box box = BoxContext.getThread();

		boolean isNotLogWrite = isNotLogWrite(className, box);
		if (isNotLogWrite) {
			return;
		}

		log = makeLogStr(className, log, box);

		if (!this.isWriteLog) {
			System.out.println(log);
			return;
		}

		if (!this.isMulti) {
			System.out.println(log);
		}

		synchronized (logKey.intern()) {

			TraceWriter traceWriter = traceWriterMap.get(logKey);

			if (traceWriter == null) {

				String logFileUrl = makeLogFileUrl(logKey);

				if (logFileUrl == null) {
					return;
				}

				if (isRolling(logFileUrl)) {
					rollingLogFile(logFileUrl);
				}

				traceWriter = new TraceWriter(logKey, logFileUrl, this.isMulti);

				traceWriterMap.put(logKey, traceWriter);

			}

			traceWriter.println(log);
			traceWriter.initCnt++;

			if (traceWriter.initCnt < 5000) {
				return;
			}

			traceWriter.initCnt = 0;

			if (!isRolling(traceWriter.logFileUrl)) {
				return;
			}

			traceWriter.close();

			rollingLogFile(traceWriter.logFileUrl);

			traceWriter = new TraceWriter(logKey, traceWriter.logFileUrl, traceWriter.isMulti);
			traceWriterMap.put(logKey, traceWriter);

		}

	}

	/**
	 * 
	 */
	public void flush() {

		Iterator<String> iterator = traceWriterMap.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			TraceWriter traceWriter = traceWriterMap.get(key);
			traceWriter.flush();
		}

	}

	/**
	 * @param className
	 * @param box
	 * @return
	 */
	boolean isNotLogWrite(String className, Box box) {

		String serviceKey = box.s(Box.KEY_SERVICE);

		if (noLogMap.containsKey("NOLOG.SERVICE_KEY." + serviceKey)) {
			return true;
		}

		if (noLogMap.containsKey("NOLOG.CLASS_NAME." + className)) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 */
	private ConcurrentHashMap<String, String> noLogMap = new ConcurrentHashMap<String, String>();

	/**
	 * 
	 */
	public void reload() {

		this.isMulti = InitYaml.get().is("LOG.MULTI");

		this.isWriteLog = InitYaml.get().is("LOG.WRITE_LOG");

		noLogMap.clear();

		String[] ss = InitYaml.get().ss("NOLOG.CLASS_NAME");
		for (int i = 0; i < ss.length; i++) {
			noLogMap.put("NOLOG.CLASS_NAME." + ss[i], "NOLOG");
		}

		ss = InitYaml.get().ss("NOLOG.SERVICE_KEY");
		for (int i = 0; i < ss.length; i++) {
			noLogMap.put("NOLOG.SERVICE_KEY." + ss[i], "NOLOG");
		}

		if (this.isWriteLog) {
			if (this.isMulti) {

				if (!(System.out instanceof TraceNotPrintStream)) {
					System.setOut(new TraceNotPrintStream());
				}
			}
		}

		this.isLogDir = false;

		this.logDir = InitYaml.get().s("LOG.DIR");
		this.logFilePatten = InitYaml.get().s("LOG.FILE_PATTERN");

		String checkLogDir = this.logDir;
		File checkDir = new File(checkLogDir);

		if (!checkDir.isDirectory()) {
			(new Exception("not Directory [" + checkLogDir + "]")).printStackTrace();
			return;
		}

		if (this.hostName == null) {
			this.logDir = checkLogDir;
			this.isLogDir = true;
			return;
		}

		checkLogDir = checkLogDir + File.separator + this.hostName;
		checkDir = new File(checkLogDir);
		checkDir.mkdir();

		if (!checkDir.isDirectory()) {
			(new Exception("not Directory [" + checkLogDir + "]")).printStackTrace();
			return;
		}

		if (this.appName == null) {
			this.logDir = checkLogDir;
			this.isLogDir = true;
			return;
		}

		checkLogDir = checkLogDir + File.separator + this.appName;
		checkDir = new File(checkLogDir);
		checkDir.mkdir();

		if (!checkDir.isDirectory()) {
			(new Exception("not Directory [" + checkLogDir + "]")).printStackTrace();
			return;
		}

		this.logDir = checkLogDir;
		this.isLogDir = true;

	}

	/**
	 * 
	 */
	private String makeClassNameShort(String className) {

		int x = className.lastIndexOf(".");
		if (x >= 1) {
			return className.substring(x + 1);
		}

		return className;
	}

}
