package com.biz.brd;

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
public class Brd01 implements BizController {

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   callList
	 */
	@ServiceAnnotation(isLogin = false)
	public String brd01010() throws Exception {

		Box box = BoxContext.get();

		BrdD dao = new BrdD();
		Table list = dao.brd01010();
		box.put("list", list);

		return "brd01010";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   callWrite
	 */
	public String brd01020() throws Exception {
		return "brd01020";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   exeWrite
	 */
	public String brd01021() throws Exception {

		Box box = BoxContext.get();

		UploadFileB file1 = box.getUploadFileB("FILE_1");
		UploadFileB file2 = box.getUploadFileB("FILE_2");

		box.put("FILE_ID_1", file1.getFileId());
		box.put("FILE_NM_1", file1.getFileName());

		box.put("FILE_ID_2", file2.getFileId());
		box.put("FILE_NM_2", file2.getFileName());

		BrdD dao = new BrdD();
		dao.write();

		file1.save();
		file2.save();

		return execute("brd01020");
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   callUpdate
	 */
	public String brd01030() throws Exception {

		Box box = BoxContext.get();

		BrdD dao = new BrdD();
		Box board = dao.brd01030();

		String RG_ID = board.s("RG_ID");
		SessionB user = box.getSessionB();
		if (RG_ID.equals(user.getId())) {
			board.put("IS_RG_ID", true);
		}

		box.put("board", board);

		return "brd01030";
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   exeUpdate
	 */
	public String brd01031() throws Exception {

		Box box = BoxContext.get();

		execute("brd01020");
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
		box.put("FILE_NM_2", file2.getFileName());

		BrdD dao = new BrdD();
		if (dao.brd01031() != 1) {

			box.setAlertMsg("자신의 글만 수정할 수 있습니다.");
			return execute("brd01030");
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
		return execute("brd01030");
	}

	/**
	 * @throws Exception
	 * 
	 *                   delete
	 */
	@ServiceAnnotation(isInternal = true)
	public void delete() throws Exception {

		Box box = BoxContext.get();

		Box thisBoard = box.getBox("board");

		String THIS_FILE_ID_1 = thisBoard.s("FILE_ID_1");
		String THIS_FILE_ID_2 = thisBoard.s("FILE_ID_2");

		UploadFileB oldFile1 = new UploadFileB(THIS_FILE_ID_1);
		UploadFileB oldFile2 = new UploadFileB(THIS_FILE_ID_2);

		BrdD dao = new BrdD();
		if (dao.delete() != 1) {
			throw new Exception("삭제할 데이터가 없습니다.");
		}

		oldFile1.delete();
		oldFile2.delete();
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 *                   exeDelete
	 */
	public String brd01040() throws Exception {

		Box box = BoxContext.get();

		execute("brd01030");

		Box thisBoard = box.getBox("board");

		if (!thisBoard.getBoolean("IS_RG_ID")) {
			box.setAlertMsg("자신의 글만 삭제할 있습니다.");
			return execute("callList");
		}

		execute("Brd01.delete");

		return execute("brd01010");
	}

}
