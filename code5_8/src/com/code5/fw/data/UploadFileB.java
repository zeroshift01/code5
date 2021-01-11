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
	private String fileId = null;
	private String contentType = null;
	private String fileName = null;
	private String fileUrl = null;

	private boolean isSave = false;

	/**
	 * 
	 */
	public UploadFileB() {
		this.fileId = "";
		this.contentType = "";
		this.fileName = "";
		this.fileName = "";
		this.fileUrl = "";
	}

	/**
	 * @param size
	 * @param fileId
	 * @param contentType
	 * @param fileName
	 * @param fileUrl
	 */
	public UploadFileB(long size, String fileId, String contentType, String fileName, String fileUrl) {

		this.size = size;
		this.fileId = fileId;
		this.contentType = contentType;
		this.fileName = fileName;
		this.fileUrl = fileUrl;

	}

	public String getFileId() {
		return fileId;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public long getSize() {
		return size;
	}

	/**
	 * 
	 */
	public void save() throws Exception {

		Box box = BoxContext.getThread();

		String from = this.fileUrl;
		String to = this.fileUrl + ".enc";
		this.fileUrl = to;

		CryptFile.getCryptFile().encrypt(from, to);

		box.put("UPLOADFILEB.FILE_ID", this.fileId);
		box.put("UPLOADFILEB.SIZE", "" + this.size);
		box.put("UPLOADFILEB.FILE_NAME", this.fileName);
		box.put("UPLOADFILEB.CONTENT_TYPE", this.contentType);
		box.put("UPLOADFILEB.FILE_URL", this.fileUrl);

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
