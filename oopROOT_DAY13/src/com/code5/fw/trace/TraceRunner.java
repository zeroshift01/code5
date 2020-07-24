package com.code5.fw.trace;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;
import com.code5.fw.util.DateTime;
import com.code5.fw.util.StringUtil;
import com.code5.fw.web.Box;

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
	private boolean isLog = InitProperty.IS_LOG();

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

			// TODO
			if (this.isLog) {
				if (this.isMulti) {
					System.setOut(new TraceNotPrintStream());
				}
			}

			// TODO
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
	 * TODO
	 * 
	 * @param log
	 * @return
	 */
	private String makeLogStr(String className, String log, Box box) {

		String classNameShort = makeClassNameShort(className);

		String dtm = DateTime.getThisDTM();
		String url = box.getUrl();
		int hashCode = box.hashCode();
		String id = "notLogin";

		SessionB user = box.getSessionB();
		if (user != null) {
			id = user.getId();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("[" + dtm + "]");
		sb.append("[" + hashCode + "]");
		sb.append("[" + url + "]");
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

		Box box = Box.getThread();

		boolean isNotLogWrite = isNotLogWrite(className, box);
		if (isNotLogWrite) {
			return;
		}

		log = makeLogStr(className, log, box);

		if (!this.isLog) {
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

		String url = box.getUrl();

		// TODO
		if (isNotLogWriteMap.containsKey("isNotLogWrite")) {
			return true;
		}

		if (isNotLogWriteMap.containsKey("url_" + url)) {
			return true;
		}

		if (isNotLogWriteMap.containsKey("className_" + className)) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 */
	private ConcurrentHashMap<String, String> isNotLogWriteMap = new ConcurrentHashMap<String, String>();

	/**
	 * 
	 */
	private void readTraceProperties() {

		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle("trace");

			Enumeration<String> list = resourceBundle.getKeys();

			while (list.hasMoreElements()) {
				String KEY = list.nextElement();
				isNotLogWriteMap.put(KEY, "isNotLogWrite");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void reload() {

		isNotLogWriteMap.clear();

		try {
			Table table = Sql.getSql().getTable("TRACERUNNER_01");

			for (int i = 0; i < table.size(); i++) {

				String KEY = table.s("KEY", i);
				isNotLogWriteMap.put(KEY, "isNotLogWrite");

			}
		} catch (Exception ex) {
			// TODO 재귀 오류 제거
			ex.printStackTrace();
		}

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
