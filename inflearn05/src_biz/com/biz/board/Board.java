package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.data.UploadFileB;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.ServiceAnnotation;

/**
 * @author zero
 *
 */
public class Board implements BizController {

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String callList() throws Exception {

		Box box = BoxContext.get();

		BoardD dao = new BoardD();
		Table list = dao.list();
		box.put("list", list);

		return "list";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callWrite() throws Exception {
		return "write";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeWrite() throws Exception {

		Box box = BoxContext.get();

		UploadFileB file1 = box.getUploadFileB("FILE_1");
		UploadFileB file2 = box.getUploadFileB("FILE_2");

		box.put("FILE_ID_1", file1.getFileId());
		box.put("FILE_NM_1", file1.getFileName());

		box.put("FILE_ID_2", file2.getFileId());
		box.put("FILE_NM_2", file2.getFileName());

		BoardD dao = new BoardD();
		dao.write();

		file1.save();
		file2.save();

		return execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callUpdate() throws Exception {

		Box box = BoxContext.get();

		BoardD dao = new BoardD();
		Box board = dao.select();

		box.put("board", board);

		return "update";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeUpdate() throws Exception {

		Box box = BoxContext.get();

		execute("callUpdate");
		Box thisBoard = box.getBox("board");

		SessionB user = box.getSessionB();

		if (!thisBoard.s("RG_ID").equals(user.getId())) {
			box.setAlertMsg("자신의 글만 수정할 수 있습니다.");
			return execute("callUpdate");
		}

		String THIS_FILE_ID_1 = thisBoard.s("FILE_ID_1");
		String THIS_FILE_ID_2 = thisBoard.s("FILE_ID_2");

		boolean isChangeFile1 = false;
		boolean isChangeFile2 = false;

		UploadFileB oldFile1 = new UploadFileB(THIS_FILE_ID_1);
		UploadFileB oldFile2 = new UploadFileB(THIS_FILE_ID_2);

		UploadFileB file1 = box.getUploadFileB("FILE_1");
		UploadFileB file2 = box.getUploadFileB("FILE_2");

		if (file1.getSize() > 0) {
			isChangeFile1 = true;
		}

		if (file2.getSize() > 0) {
			isChangeFile2 = true;
		}

		box.put("FILE_ID_1", file1.getFileId());
		box.put("FILE_NM_1", file1.getFileName());

		box.put("FILE_ID_2", file2.getFileId());
		box.put("FILE_NM_2", file2.getFileName());

		BoardD dao = new BoardD();
		if (dao.update() != 1) {
			throw new Exception();
		}

		if (isChangeFile1) {
			file1.save();
			oldFile1.delete();
		}

		if (isChangeFile2) {
			file2.save();
			oldFile2.delete();
		}

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");
		return execute("callUpdate");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {

		Box box = BoxContext.get();

		Box thisBoard = box.getBox("board");

		String THIS_FILE_ID_1 = thisBoard.s("FILE_ID_1");
		String THIS_FILE_ID_2 = thisBoard.s("FILE_ID_2");

		UploadFileB oldFile1 = new UploadFileB(THIS_FILE_ID_1);
		UploadFileB oldFile2 = new UploadFileB(THIS_FILE_ID_2);

		BoardD dao = new BoardD();
		if (dao.delete() != 1) {
			throw new Exception("삭제할 데이터가 없습니다.");
		}

		oldFile1.delete();
		oldFile2.delete();

		return "null";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeDelete() throws Exception {

		Box box = BoxContext.get();

		execute("callUpdate");

		Box thisBoard = box.getBox("board");

		if (!thisBoard.getBoolean("IS_RG_ID")) {
			box.setAlertMsg("자신의 글만 삭제할 있습니다.");
			return execute("callList");
		}

		execute("delete");

		return execute("callList");
	}

}
