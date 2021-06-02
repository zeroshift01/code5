DROP TABLE BZ_BOARD;

CREATE TABLE BZ_BOARD(
N INTEGER PRIMARY KEY AUTOINCREMENT
, TITLE
, TXT
, EM
, FILE_NM_1
, FILE_ID_1
, FILE_NM_2
, FILE_ID_2
, RG_ID
, RG_IP
, RG_DTM
, MDF_ID
, MDF_IP
, MDF_DTM
, DEL_Y
);

DROP TABLE BZ_ID;

CREATE TABLE BZ_ID (
ID PRIMARY KEY
, PIN
, AUTH
, FAIL_CNT
, LAST_LOGIN_DTM
);


INSERT INTO BZ_ID VALUES(
'admin'
,'413190dd77a132b98077112e493ce5bceef53ef60dfb4360764c1f9ceba85289'
,'A0'
,'0'
,''
);

INSERT INTO BZ_ID VALUES(
'user1'
,'eaab2eac6bfb55c5994efbac5ec6b116a45f53892649c700003fdeeab765a1a3'
,'U0'
,'0'
,''
);

INSERT INTO BZ_ID VALUES(
'user2'
,'5ced0bc626a116b6dafbf341db47bad167c444f53c4a769d8d7c09e1c6231c17'
,'U0'
,'0'
,''
);


drop table FW_CONTROLLER;

CREATE TABLE FW_CONTROLLER(
KEY
, CLASS_NAME
, METHOD_NAME
);


INSERT INTO FW_CONTROLLER VALUES('file001','com.code5.fw.web.MasterControllerMultipart','file001');

INSERT INTO FW_CONTROLLER VALUES('exeLogin','com.biz.login.Login','exeLogin');
INSERT INTO FW_CONTROLLER VALUES('exeLogout','com.biz.login.Login','exeLogout');
INSERT INTO FW_CONTROLLER VALUES('callLogin','com.biz.login.Login','callLogin');


INSERT INTO FW_CONTROLLER VALUES('callList','com.biz.board.Board','callList');
INSERT INTO FW_CONTROLLER VALUES('callWrite','com.biz.board.Board','callWrite');
INSERT INTO FW_CONTROLLER VALUES('callUpdate','com.biz.board.Board','callUpdate');
INSERT INTO FW_CONTROLLER VALUES('callListByJson','com.biz.board.Board','callListByJson');


INSERT INTO FW_CONTROLLER VALUES('exeUpdate','com.biz.board.Board','exeUpdate');
INSERT INTO FW_CONTROLLER VALUES('exeWrite','com.biz.board.Board','exeWrite');
INSERT INTO FW_CONTROLLER VALUES('exeDelete','com.biz.board.Board','exeDelete');

INSERT INTO FW_CONTROLLER VALUES('forceDelete','com.biz.board.BoardAdmin','forceDelete');
INSERT INTO FW_CONTROLLER VALUES('allDelete','com.biz.board.BoardAdmin','allDelete');
INSERT INTO FW_CONTROLLER VALUES('allUpdate','com.biz.board.BoardAdmin','allUpdate');


drop TABLE FW_VIEW ;

CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, JSP
, TMPL_JSP
, TITLE);


INSERT INTO FW_VIEW VALUES('file001','/WEB-INF/classes/com/code5/fw/web/jsp/file001.jsp','','');
INSERT INTO FW_VIEW VALUES('null','/WEB-INF/classes/com/code5/fw/web/jsp/null.jsp','','');
INSERT INTO FW_VIEW VALUES('err','/WEB-INF/classes/com/code5/fw/web/jsp/err.jsp','','');

INSERT INTO FW_VIEW VALUES('login','/WEB-INF/classes/com/biz/login/jsp/login.jsp','','');
INSERT INTO FW_VIEW VALUES('list','/WEB-INF/classes/com/biz/board/jsp/list.jsp','/WEB-INF/classes/com/biz/board/jsp/main.jsp','리스트');
INSERT INTO FW_VIEW VALUES('write','/WEB-INF/classes/com/biz/board/jsp/write.jsp','/WEB-INF/classes/com/biz/board/jsp/main.jsp','등록화면');
INSERT INTO FW_VIEW VALUES('update','/WEB-INF/classes/com/biz/board/jsp/update.jsp','/WEB-INF/classes/com/biz/board/jsp/main.jsp','수정화면');

drop table FW_UPLOAD_FILE;
CREATE TABLE FW_UPLOAD_FILE (
FILE_ID primary KEY
, SIZE
, CONTENT_TYPE
, FILE_NAME
, FILE_URL
, RG_ID
, RG_DTM
, DEL_Y
, DEL_ID
, DEL_DTM
);

drop table FW_DOWNLOAD_FILE;

CREATE TABLE FW_DOWNLOAD_FILE (
FILE_ID
, ID
, DTM
);

