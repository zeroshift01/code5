package com.code5.fw.web;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.UploadFileB;
import com.code5.fw.db.SqlRunner;

/**
 * @author seuk
 *
 */
public class MasterControllerMultipart extends MasterController implements BizController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param request
	 * @return
	 */
	protected Box createBox(HttpServletRequest request) throws ServletException, IOException {

		Box box = new BoxHttp(request);
		BoxContext.setThread(box);

		String KEY = request.getPathInfo().substring(1);
		box.put(Box.KEY_SERVICE, KEY);

		box.put(Box.KEY_REMOTE_ADDR, request.getRemoteAddr());

		box.put(Box.KEY_CONTENT_TYPE, request.getContentType());

		Object sessionB = request.getSession().getAttribute(Box.KEY_SESSIONB);
		if (sessionB instanceof SessionB) {
			box.put(Box.KEY_SESSIONB, sessionB);
		}

		Collection<Part> parts = request.getParts();

		for (Part part : parts) {

			long size = part.getSize();
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

			String fileUrl = InitProperty.UPLOAD_FILE_DIR_TEMP_URL() + File.separatorChar + fileId;

			part.write(fileUrl);
			part.delete();

			UploadFileB uploadFileB = new UploadFileB(size, fileId, contentType, fileName, fileUrl);

			String name = part.getName();
			box.put(name, uploadFileB);

		}

		return box;

	}

	/**
	 * @return
	 */
	private String createFileId() {

		String RND = getRND();
		return DateTime.getThisDTM() + "_" + getFileCnt() + "_" + RND;

	}

	/**
	 * 
	 */
	private static int fileCnt = 0;

	/**
	 * @return
	 */
	synchronized int getFileCnt() {
		return fileCnt++;
	}

	/**
	 * @return
	 */
	public String fileDownload() throws Exception {

		Box box = BoxContext.getThread();
		String FILE_ID = box.s("FILE_ID");
		FILE_ID = box.getSessionB().getDataByToken("filedownload", FILE_ID);

		box.put("FILE_ID", FILE_ID);
		Box fileBox = SqlRunner.getSqlRunner().getTable("MASTERCONTROLLERMULTIPART_01").getBox();

		File file = new File(fileBox.s("FILE_REAL_URL"));
		if (!file.exists()) {
			throw new Exception();
		}

		box.put("fileBox", fileBox);

		return "fileDownload";
	}
}
