package com.code5.fw.data;

import com.code5.fw.db.SqlRunner;
import com.code5.fw.security.CryptFile;
import com.code5.fw.web.BoxContext;

/**
 * @author seuk
 *
 */
public class UploadFileB {

	/**
	 * 
	 */
	private static String FORM_NO_01 = "UPLOADFILEB_01";

	private long size = 0;
	private String name = null;
	private String contentType = null;
	private String submittedFileName = null;
	private String realFileName = null;
	private String realFileUrl = null;

	private boolean isSave = false;

	/**
	 * @param size
	 * @param name
	 * @param contentType
	 * @param submittedFileName
	 * @param realFileName
	 * @param realFileUrl
	 */
	public UploadFileB(long size, String name, String contentType, String submittedFileName, String realFileName,
			String realFileUrl) {

		this.size = size;
		this.name = name;
		this.contentType = contentType;
		this.submittedFileName = submittedFileName;
		this.realFileName = realFileName;
		this.realFileUrl = realFileUrl;

	}

	public long getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public String getSubmittedFileName() {
		return submittedFileName;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public String getRealFileUrl() {
		return realFileUrl;
	}

	/**
	 * 
	 */
	public void save() throws Exception {

		Box box = BoxContext.getThread();

		String from = this.realFileUrl;
		String to = this.realFileUrl + ".enc";
		this.realFileUrl = to;

		CryptFile.getCryptFile().encrypt(from, to);

		box.put("UPLOADFILEB.REAL_FILE_NAME", this.realFileName);
		box.put("UPLOADFILEB.SIZE", "" + this.size);
		box.put("UPLOADFILEB.NAME", this.name);
		box.put("UPLOADFILEB.CONTENT_TYPE", this.contentType);
		box.put("UPLOADFILEB.SUBMITTED_FILE_NAME", this.submittedFileName);
		box.put("UPLOADFILEB.REAL_FILE_URL", this.realFileUrl);

		SqlRunner.getSqlRunner().executeSql(FORM_NO_01);

		this.isSave = true;
	}

	/**
	 * @return
	 */
	public boolean isSave() {
		return this.isSave;
	}

}
