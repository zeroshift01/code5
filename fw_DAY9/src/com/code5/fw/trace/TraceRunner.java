package com.code5.fw.trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.data.Box;
import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
import com.code5.fw.util.DateTime;
import com.code5.fw.util.StringUtil;
import com.code5.fw.web.BoxContext;

/**
 * @author seuk
 *
 */
public final class TraceRunner {

	/**
	 * 
	 */
	private static TraceRunner TRACE = new TraceRunner();

	/**
	 * 
	 */
	private TraceRunner() {
		readTraceProperties();
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
	private String logFileDirRoot = InitProperty.LOG_FILE_DIR_ROOT();

	/**
	 * 
	 */
	private String logFileDir = null;

	/**
	 * 
	 */
	private String logFilePrefix = StringUtil.makePwd(8);

	/**
	 * 
	 */
	private boolean isWriteLog = InitProperty.IS_WRITE_LOG();

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
	private String serviceName = "notInit";

	/**
	 * 
	 */
	private boolean isInit = false;

	/**
	 * 
	 */
	private boolean isMulti = false;

	/**
	 * @param request
	 */
	public void init(HttpServletRequest request) {

		if (this.isInit) {
			return;
		}

		this.isMulti = true;
		this.serviceName = "p" + request.getServerPort();

		init();
	}

	/**
	 * @param serviceName
	 */
	public void init(String serviceName) {

		if (this.isInit) {
			return;
		}

		this.isMulti = false;
		this.serviceName = serviceName;

		init();
	}

	/**
	 * 
	 */
	private synchronized void init() {

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

			this.logFileDir = this.logFileDirRoot + File.separatorChar + this.serviceName;

			File file = new File(this.logFileDir);
			if (!file.isDirectory()) {
				file.mkdir();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 
	 * 
	 * @param log
	 * @return
	 */
	private String makeLogStr(String className, String log, Box box) {

		String classNameShort = makeClassNameShort(className);

		String dtm = DateTime.getThisDTM();
		String serviceKey = box.s(Box.SERVICE_KEY);
		int hashCode = box.hashCode();
		String id = "notLogin";

		SessionB user = box.getSessionB();
		if (user != null) {
			id = user.getId();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("[" + dtm + "]");
		sb.append("[" + hashCode + "]");
		sb.append("[" + serviceKey + "]");
		sb.append("[" + id + "]");
		sb.append("[" + classNameShort + "]");
		sb.append(log);

		return sb.toString();

	}

	/**
	 * @param logKey
	 * @return
	 */
	private String makeLogFileUrl(String logKey) {

		return this.logFileDir + File.separatorChar + this.logFilePrefix + "." + logKey + ".log";
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

		if (file.length() < this.logFileSizeLimit) {
			return false;
		}

		return true;

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
				if (isRolling(logFileUrl)) {
					rollingLogFile(logFileUrl);
				}

				traceWriter = new TraceWriter(logKey, logFileUrl, isMulti);

				traceWriterMap.put(logKey, traceWriter);

			}

			traceWriter.initCnt++;

			if (traceWriter.initCnt < 5000) {
				traceWriter.println(log);
				return;
			}

			traceWriter.initCnt = 0;

			if (isRolling(traceWriter.logFileUrl)) {
				traceWriter.close();
				rollingLogFile(traceWriter.logFileUrl);
			}

			traceWriter = new TraceWriter(logKey, traceWriter.logFileUrl, this.isMulti);
			traceWriterMap.put(logKey, traceWriter);
			traceWriter.println(log);

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

		String serviceKey = box.s(Box.SERVICE_KEY);

		if (noLogMap.containsKey("noLog.serviceKey." + serviceKey)) {
			return true;
		}

		if (noLogMap.containsKey("noLog.className." + className)) {
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
	private void readTraceProperties() {

		BufferedReader br = null;
		try {

			String url = InitProperty.TRACE_CONFIG_URL();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(url)));

			for (int i = 0; i < 10000; i++) {
				String s = br.readLine();
				if (s == null) {
					break;
				}

				s = s.trim();

				if ("".equals(s)) {
					continue;
				}

				if (s.startsWith("#")) {
					continue;
				}

				if (s.startsWith("noLog")) {
					noLogMap.put(s, "true");
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void reload() {
		noLogMap.clear();
		readTraceProperties();
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
