package com.code5.fw.data;

import java.io.File;

import com.code5.fw.db.Sql;
import com.code5.fw.security.CryptFile;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class UploadFileB {

	private Sql sql = new Sql(this);

	private static String FORM_NO_01 = "UPLOADFILEB_01";
	private static String FORM_NO_02 = "UPLOADFILEB_02";
	private static String FORM_NO_03 = "UPLOADFILEB_03";

	private long size = 0;
	private String fileId = null;
	private String contentType = null;
	private String fileName = null;
	private String fileUrl = null;

	private boolean isSave = false;
	private boolean isDelete = false;

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
	public UploadFileB() {
		this.size = 0;
		this.fileId = "";
		this.contentType = "";
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

	/**
	 * @param fileId
	 * @throws Exception
	 */
	public UploadFileB(String fileId) throws Exception {

		if (fileId == null) {
			return;
		}

		if ("".equals(fileId)) {
			return;
		}

		Box box = BoxContext.getThread();
		box.put("UPLOADFILEB.FILE_ID", fileId);

		Table table = sql.getTable(FORM_NO_02);
		if (table.size() != 1) {
			throw new Exception();
		}

		Box thisBox = table.getBox();

		this.size = thisBox.getLong("SIZE");
		this.fileId = thisBox.s("fileId");
		this.contentType = thisBox.s("CONTENT_TYPE");
		this.fileName = thisBox.s("FILE_NAME");
		this.fileUrl = thisBox.s("FILE_URL");
	}

	/**
	 * 
	 */
	public void save() throws Exception {

		this.isSave = false;

		if (this.size == 0) {
			return;
		}

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

		sql.executeSql(FORM_NO_01);

		this.isSave = true;
	}

	/**
	 * 
	 */
	public void delete() throws Exception {

		this.isDelete = false;

		if (this.size == 0) {
			return;
		}

		File file = new File(this.fileUrl);
		if (!file.isFile()) {
			return;
		}

		if (!file.delete()) {
			return;
		}

		Box box = BoxContext.getThread();

		box.put("UPLOADFILEB.FILE_ID", this.fileId);

		sql.executeSql(FORM_NO_03);

		this.isDelete = true;
	}

	/**
	 * @return
	 */
	public boolean isSave() {
		return this.isSave;
	}

	/**
	 * @return
	 */
	public boolean isDelete() {
		return this.isDelete;
	}

}
