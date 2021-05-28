del C:\public\code5\doc\code5.db
cd C:\public\lib\sqlite
sqlite3 C:\public\code5\doc\code5.db

CREATE TABLE BZ_BOARD(
N INTEGER PRIMARY KEY AUTOINCREMENT
, TITLE
, TXT
, EM
, FILE_ID_1
, FILE_ID_2
, FILE_ID_3
, RG_ID
, RG_IP
, RG_DTM
, MDF_ID
, MDF_IP
, MDF_DTM
, DEL_Y
);

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
  
INSERT INTO FW_CONTROLLER VALUES('login','com.code5.biz.login.Login','login');
INSERT INTO FW_CONTROLLER VALUES('loginView','com.code5.biz.login.Login','loginView');
INSERT INTO FW_CONTROLLER VALUES('filedownload','com.code5.fw.web.MasterControllerMultipart','fileDownload');
INSERT INTO FW_CONTROLLER VALUES('downloadfile','com.code5.fw.web.MasterControllerMultipart','downloadFile');

INSERT INTO FW_CONTROLLER VALUES('list','com.biz.board.Board','list');
INSERT INTO FW_CONTROLLER VALUES('listjson','com.biz.board.Board','listJson');

INSERT INTO FW_CONTROLLER VALUES('updateview','com.biz.board.Board','updateView');
INSERT INTO FW_CONTROLLER VALUES('writeview','com.biz.board.Board','writeView');

INSERT INTO FW_CONTROLLER VALUES('update','com.biz.board.Board','update');
INSERT INTO FW_CONTROLLER VALUES('write','com.biz.board.Board','write');

INSERT INTO FW_CONTROLLER VALUES('delete','com.biz.board.Board','delete');
INSERT INTO FW_CONTROLLER VALUES('forcedelete','com.biz.board.Board','forceDelete');


drop TABLE FW_VIEW ;

CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, JSP
, TMPL_JSP
, TITLE);


INSERT INTO FW_VIEW VALUES('fileDownload','/WEB-INF/classes/com/code5/fw/web/jsp/fileDownload.jsp','','');
INSERT INTO FW_VIEW VALUES('downloadFile','/WEB-INF/classes/com/code5/fw/web/jsp/downloadFile.jsp','','');
INSERT INTO FW_VIEW VALUES('nullView','/WEB-INF/classes/com/code5/fw/web/jsp/nullView.jsp','','');
INSERT INTO FW_VIEW VALUES('errView','/WEB-INF/classes/com/code5/fw/web/jsp/errView.jsp','','');

INSERT INTO FW_VIEW VALUES('login','/WEB-INF/classes/com/biz/login/jsp/login.jsp','','');
INSERT INTO FW_VIEW VALUES('list','/WEB-INF/classes/com/biz/board/jsp/list.jsp','/WEB-INF/classes/com/biz/board/jsp/tmpl.jsp','조회');
INSERT INTO FW_VIEW VALUES('write','/WEB-INF/classes/com/biz/board/jsp/write.jsp','/WEB-INF/classes/com/biz/board/jsp/tmpl.jsp','조회');
INSERT INTO FW_VIEW VALUES('update','/WEB-INF/classes/com/biz/board/jsp/update.jsp','/WEB-INF/classes/com/biz/board/jsp/tmpl.jsp','조회');


CREATE TABLE FW_FILE_DOWNLOAD (
REAL_FILE_NAME
, ID
, ST_DTM
, ED_DTM
);
CREATE TABLE FW_FILE_UPLOAD (
FILE_ID primary KEY
, SIZE
, NAME
, CONTENT_TYPE
, FILE_NAME
, FILE_URL
, RG_ID
, RG_DTM
);
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