package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.data.UploadFileB;
import com.code5.fw.web.BizController;
import com.code5.fw.web.BizControllerStartExecute;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.MasterController;
import com.code5.fw.web.ServiceAnnotation;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Board implements BizController, BizControllerStartExecute {

	/**
	 *
	 */
	public String start() throws Exception {

		Box box = BoxContext.getThread();

		String service = box.s(Box.KEY_SERVICE);

		if (!service.equals("allDelete")) {
			return null;
		}

		String remoteAddr = box.s(Box.KEY_REMOTE_ADDR);

		String adminIP = "0:0:0:0:0:0:0:1";

		if (!remoteAddr.equals(adminIP)) {
			box.setAlertMsg("알수없는 오류가 발생했습니다.");
			return "error";
		}

		return null;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String callList() throws Exception {

		Box box = BoxContext.getThread();

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

		Box box = BoxContext.getThread();

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

		return MasterController.execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String callUpdate() throws Exception {

		Box box = BoxContext.getThread();

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

		Box box = BoxContext.getThread();

		MasterController.execute("callUpdate");
		Box thisBoard = box.getBox("board");

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
		box.put("FILE_NM2", file2.getFileName());

		BoardD dao = new BoardD();
		if (dao.update() != 1) {

			box.setAlertMsg("자신의 글만 수정할 수 있습니다.");
			return MasterController.execute("callUpdate");
		}

		if (isChangeFile1) {
			file1.save();
			oldFile1.delete();
		}

		if (isChangeFile2) {
			file1.save();
			oldFile2.save();
		}

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");
		return MasterController.execute("callUpdate");
	}

	/**
	 * @throws Exception
	 */
	private void delete() throws Exception {

		Box box = BoxContext.getThread();

		Box thisBoard = box.getBox("board");

		String THIS_FILE_ID_1 = thisBoard.s("FILE_ID_1");
		String THIS_FILE_ID_2 = thisBoard.s("FILE_ID_2");

		UploadFileB oldFile1 = new UploadFileB(THIS_FILE_ID_1);
		UploadFileB oldFile2 = new UploadFileB(THIS_FILE_ID_2);

		box.put("CHECK_ID", "OK");

		BoardD dao = new BoardD();
		if (dao.delete() != 1) {
			box.setAlertMsg("자신의 글만 삭제할 있습니다.");
			return;
		}

		oldFile1.delete();
		oldFile2.delete();

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String exeDelete() throws Exception {

		Box box = BoxContext.getThread();
		box.put("CHECK_ID", "OK");

		delete();

		return MasterController.execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0")
	public String forceDelete() throws Exception {

		Box box = BoxContext.getThread();
		box.put("CHECK_ID", "");

		delete();

		return MasterController.execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(auth = "A0")
	public String allDelete() throws Exception {

		BoardD dao = new BoardD();
		dao.deleteAll();

		TransactionContext.commit();

		Box box = BoxContext.getThread();
		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return MasterController.execute("callList");
	}

	@ServiceAnnotation(auth = "A0")
	public String allUpdate() throws Exception {

		Box box = BoxContext.getThread();
		Table input = box.createTableByKey("TOKEN_N");

		BoardD dao = new BoardD();
		for (int i = 0; i < input.size(); i++) {
			box.putFromTable(input, i);
			dao.updateAll();
		}

		TransactionContext.commit();

		box.setAlertMsg("성공적으로 작업이 수행되었습니다.");

		return MasterController.execute("callList");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ServiceAnnotation(isLogin = false)
	public String listJson() throws Exception {

		MasterController.execute("callList");
		return "listjson";

	}

}