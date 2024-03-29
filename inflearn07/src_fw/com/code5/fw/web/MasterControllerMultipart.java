package com.code5.fw.web;

import java.io.File;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.UploadFileB;
import com.code5.fw.db.Sql;
import com.code5.fw.trace.Trace;

/**
 * @author zero
 *
 */
public class MasterControllerMultipart extends MasterController implements BizController {

	/**
	 * 
	 */
	private String appNmae = InitYaml.get().getAppName();

	/**
	 *
	 */
	public void reload() {
		super.reload();
		this.uploadFileDirUrl = InitYaml.get().s("UPLOAD_FILE_DIR.URL");
	}

	/**
	 * 
	 */
	private String uploadFileDirUrl = InitYaml.get().s("UPLOAD_FILE_DIR.URL");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	protected void setBox(HttpServletRequest request, Box box) throws Exception {

		String boxContentType = request.getContentType();
		if (boxContentType == null) {
			throw new ServletException();
		}

		boxContentType = boxContentType.toLowerCase();
		if (!boxContentType.startsWith("multipart/form-data")) {
			throw new ServletException();
		}
		box.put(Box.KEY_CONTENT_TYPE, "multipart/form-data");

		String KEY = request.getPathInfo().substring(1);
		box.put(Box.KEY_SERVICE, KEY);

		box.put(Box.KEY_REMOTE_ADDR, request.getRemoteAddr());

		SessionB sessionB = (SessionB) request.getSession().getAttribute(Box.KEY_SESSIONB);

		if (sessionB == null) {
			throw new LoginException();
		}

		if (!sessionB.isLogin()) {
			throw new LoginException();
		}

		box.put(Box.KEY_SESSIONB, sessionB);

		Collection<Part> parts = request.getParts();

		for (Part part : parts) {

			long size = part.getSize();
			String name = part.getName();

			if (size == 0) {
				part.delete();
				continue;
			}

			String fileName = part.getSubmittedFileName();
			if (fileName == null) {
				part.delete();
				continue;
			}

			String fileId = createFileId();

			String contentType = part.getContentType();

			String fileUrl = this.uploadFileDirUrl + File.separatorChar + fileId;

			part.write(fileUrl);
			part.delete();

			UploadFileB uploadFileB = new UploadFileB(size, fileId, contentType, fileName, fileUrl);

			box.put(name, uploadFileB);

		}

	}

	private String createFileId() {
		return DateTime.getThisDTM() + "_" + getFileCnt() + "_" + this.appNmae;
	}

	private static int fileCnt = 0;

	synchronized int getFileCnt() {
		return fileCnt++;
	}

	/**
	 * @return
	 */
	public String file001() throws Exception {

		Box box = BoxContext.get();
		String FILE_ID = box.s("FILE_ID");
		FILE_ID = box.getSessionB().getDataByToken("file001", FILE_ID);

		box.put("FILE_ID", FILE_ID);

		UploadFileB uploadFileB = new UploadFileB(FILE_ID);

		File file = new File(uploadFileB.getFileUrl());
		if (!file.exists()) {
			throw new Exception();
		}

		box.put("file", uploadFileB);

		Sql sql = new Sql(this);
		sql.executeSql("DOWNLOADFILE_01");

		return "file001";
	}

	/**
	 *
	 */
	@Override
	protected void closeAOP() {

		try {

			Box box = BoxContext.get();

			String[] keys = box.getKeys();
			for (int i = 0; i < keys.length; i++) {

				Object obj = box.get(keys[i]);

				if (!(obj instanceof UploadFileB)) {
					continue;
				}

				UploadFileB uploadFileB = (UploadFileB) obj;
				if (uploadFileB.isSave()) {
					continue;
				}

				(new File(uploadFileB.getFileUrl())).delete();
			}

		} catch (Exception ex) {
			Trace trace = new Trace(this);
			trace.write(ex);
		}

		super.closeAOP();

	}

}
