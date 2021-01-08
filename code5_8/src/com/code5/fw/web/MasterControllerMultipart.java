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

/**
 * @author seuk
 *
 */
public class MasterControllerMultipart extends MasterController {

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

			String submittedFileName = part.getSubmittedFileName();
			if (submittedFileName == null) {
				part.delete();
				continue;
			}

			String name = part.getName();
			String contentType = part.getContentType();
			String realFileName = createRealFileName();

			String realFileUrl = InitProperty.UPLOAD_FILE_DIR_TEMP_URL() + File.separatorChar + realFileName;

			part.write(realFileUrl);
			part.delete();

			UploadFileB uploadFileB = new UploadFileB(size, name, contentType, submittedFileName, realFileName,
					realFileUrl);

			box.put(name, uploadFileB);

		}

		return box;

	}

	private String createRealFileName() {

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

}
