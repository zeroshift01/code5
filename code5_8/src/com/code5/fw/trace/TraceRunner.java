package com.code5.fw.trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.code5.fw.data.Box;
import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
import com.code5.fw.web.BoxContext;

/**
 * @author seuk
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
		readTraceProperties();
	}

	/**
	 * @return
	 */
	public static TraceRunner getTraceRunner() {

		return TRACE;
	}

	private String logDir = null;

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
	private String cntr = "notCntr";

	/**
	 * 
	 */
	private String host = "notHost";

	/**
	 * 
	 */
	private String rnd = makeRnd(8);

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
		this.cntr = "p" + request.getServerPort();

		init();
	}

	/**
	 * @param cntr
	 */
	public void init(String cntr) {

		if (this.isInit) {
			return;
		}

		this.isMulti = false;
		this.cntr = cntr;

		init();
	}

	/**
	 * @param cntr
	 */
	public void init(String cntr, boolean isMulti) {

		if (this.isInit) {
			return;
		}

		this.isMulti = isMulti;
		this.cntr = cntr;

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

			String logDirx = InitProperty.LOG_DIR_PATTERN();

			try {
				String hostName = InetAddress.getLocalHost().getHostName();
				if (hostName.contains(".")) {
					throw new Exception("error hostName [" + hostName + "]");
				}

				this.host = hostName;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

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

		String dtm = getThisDTMByForm();

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
		String renameLogFileUrl = logFileUrl + "." + getThisDTM();
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

				if (logFileUrl == null) {
					return;
				}

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

		String serviceKey = box.s(Box.KEY_SERVICE);

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

	/**
	 * @return
	 */
	private static String getThisDTM() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * @return
	 */
	private static String getThisDTMByForm() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * @param len
	 * @return
	 */
	private static String makeRnd(int len) {

		StringBuffer temp = new StringBuffer();
		SecureRandom rnd = new SecureRandom();
		for (int i = 0; i < len; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((rnd.nextInt(10)));
				break;
			}
		}
		return temp.toString();
	}

}
