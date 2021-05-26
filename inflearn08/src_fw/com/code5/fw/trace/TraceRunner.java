package com.code5.fw.trace;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.MakeRnd;
import com.code5.fw.data.SessionB;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public final class TraceRunner {

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
		reload();
		this.hostName = InitYaml.get().getHostName();
		this.appName = InitYaml.get().getAppName();

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
	private String filePatten = null;

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
	private boolean isInit = false;

	/**
	 * 
	 */
	private boolean isMulti = false;

	/**
	 * 
	 */
	private String hostName = "";

	/**
	 * 
	 */
	private String appName = "";

	/**
	 * 
	 */
	public void init() {

		if (this.isInit) {
			return;
		}
		_init();
	}

	/**
	 * 
	 */
	private synchronized void _init() {

		try {

			if (this.isInit) {
				return;
			}
			this.isInit = true;

			if (this.isWriteLog) {
				if (this.isMulti) {
					System.setOut(new TraceNotPrintStream());
				}
			}

			String logDirx = InitProperty.LOG_DIR_PATTERN();

			logDirx = logDirx.replace("[host]", this.host);
			logDirx = logDirx.replace("[rnd]", this.rnd);
			logDirx = logDirx.replace("[cntr]", this.cntr);

			(new File(logDirx)).mkdir();

			if (!(new File(logDirx)).isDirectory()) {
				throw new Exception("not Directory [" + logDirx + "]");
			}

			this.logDir = logDirx;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @param logKey
	 * @return
	 */
	private String makeLogFileUrl(String logKey) {

		if (this.logDir == null) {
			return null;
		}

		if (logKey.contains(".")) {
			(new RuntimeException("no logKey [" + logKey + "]")).printStackTrace();
			return null;
		}

		String logFilePatten = InitProperty.LOG_FILE_PATTERN();

		logFilePatten = logFilePatten.replace("[rnd]", this.rnd);
		logFilePatten = logFilePatten.replace("[name]", logKey);

		return this.logDir + File.separatorChar + logFilePatten;
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
			sb.append("[" + user.getId() + "]");
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

		this.logDir = InitYaml.get().s("LOG.DIR");

		this.filePatten = InitYaml.get().s("LOG.FILE_PATTERN");

		this.isWriteLog = InitYaml.get().is("WRITE_LOG");

		noLogMap.clear();

		String[] ss = InitYaml.get().ss("NOLOG.CLASS_NAME");
		for (int i = 0; i < ss.length; i++) {
			noLogMap.put("NOLOG.CLASS_NAME." + ss[i], "");
		}

		ss = InitYaml.get().ss("NOLOG.SERVICE_KEY");
		for (int i = 0; i < ss.length; i++) {
			noLogMap.put("NOLOG.SERVICE_KEY." + ss[i], "");
		}

		if (this.isWriteLog) {
			if (this.isMulti) {

				if (!(System.out instanceof TraceNotPrintStream)) {
					System.setOut(new TraceNotPrintStream());
				}
			}
		}

		if (!(new File(this.logDir)).isDirectory()) {
			(new Exception("not Directory [" + this.logDir + "]")).printStackTrace();
			return;
		}
		
		if(this.hostName

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
