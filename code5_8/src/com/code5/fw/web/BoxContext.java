package com.code5.fw.web;

import java.io.File;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;
import com.code5.fw.data.UploadFileB;

/**
 * @author seuk
 *
 */
public class BoxContext {

	/**
	 * 
	 */
	private static ThreadLocal<Box> TL = new ThreadLocal<Box>();

	/**
	 * 
	 * 
	 * 
	 * @param box
	 */
	static void setThread(Box box) {
		TL.set(box);
	}

	/**
	 * 
	 */
	static void removeThread() {

		Box box = TL.get();

		// 업로드 후 사용 안한 파일은 삭제
		if ("mul".equals(box.s(Box.KEY_CONTENT_TYPE))) {

			String[] keys = box.getKeys();
			for (int i = 0; i < keys.length; i++) {

				Object obj = box.get(keys[i]);

				if (!(obj instanceof UploadFileB)) {
					continue;
				}

				UploadFileB uploadFileB = (UploadFileB) obj;
				if (!uploadFileB.isSave()) {
					continue;
				}

				(new File(uploadFileB.getFileUrl())).delete();
			}

		}

		TL.remove();
	}

	/**
	 * 
	 * 
	 * 
	 * @param box
	 */
	public static Box getThread() {

		Box box = TL.get();

		if (box != null) {
			return box;
		}
		box = new BoxLocal();
		setThread(box);
		return box;
	}

}
