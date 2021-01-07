package com.code5.fw.data;

/**
 * @author seuk
 *
 */
public class UploadFileB {

	private long size = 0;
	private String name = null;
	private String contentType = null;
	private String submittedFileName = null;
	private String realFileName = null;
	private String realFileUrl = null;

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
	public void fileOk() {
		// this.realFileUrl;
	}

}
