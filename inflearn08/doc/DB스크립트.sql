del C:\public\code5\doc\code5.db
cd C:\public\lib\sqlite
sqlite3 C:\public\code5\doc\code5.db

drop table FW_CONTROLLER;
CREATE TABLE FW_CONTROLLER(
  KEY,
  CLASS_NAME,
  METHOD_NAME);
  
INSERT INTO FW_CONTROLLER VALUES('login','com.code5.biz.login.Login','login');
INSERT INTO FW_CONTROLLER VALUES('loginView','com.code5.biz.login.Login','loginView');
INSERT INTO FW_CONTROLLER VALUES('filedownload','com.code5.fw.web.MasterControllerMultipart','fileDownload');
INSERT INTO FW_CONTROLLER VALUES('downloadfile','com.code5.fw.web.MasterControllerMultipart','downloadFile');
INSERT INTO FW_CONTROLLER VALUES('emp00110','com.code5.biz.emp.emp001.Emp001','emp00110');
INSERT INTO FW_CONTROLLER VALUES('emp00120','com.code5.biz.emp.emp001.Emp001','emp00120');
INSERT INTO FW_CONTROLLER VALUES('emp00210','com.code5.biz.emp.emp002.Emp002','emp00210');
INSERT INTO FW_CONTROLLER VALUES('emp00220','com.code5.biz.emp.emp002.Emp002','emp00220');
INSERT INTO FW_CONTROLLER VALUES('admin001','com.code5.fw.web.Admin','admin001');

drop TABLE FW_VIEW ;
CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, JSP
, TMPL_JSP, TITLE);

INSERT INTO FW_VIEW VALUES('loginView','/WEB-INF/classes/com/code5/biz/login/jsp/loginView.jsp',NULL,NULL);
INSERT INTO FW_VIEW VALUES('fileDownload','/WEB-INF/classes/com/code5/fw/web/jsp/fileDownload.jsp',NULL,NULL);
INSERT INTO FW_VIEW VALUES('downloadFile','/WEB-INF/classes/com/code5/fw/web/jsp/downloadFile.jsp',NULL,NULL);
INSERT INTO FW_VIEW VALUES('emp00110','/WEB-INF/classes/com/code5/biz/emp/emp001/jsp/emp00110.jsp','/WEB-INF/classes/com/code5/biz/emp/jsp/empMain.jsp','EMP 조회');
INSERT INTO FW_VIEW VALUES('nullView','/WEB-INF/classes/com/code5/fw/web/jsp/nullView.jsp','','');
INSERT INTO FW_VIEW VALUES('errView','/WEB-INF/classes/com/code5/fw/web/jsp/errView.jsp','','');


DROP TABLE EMP ;
CREATE TABLE EMP (
EMP_N PRIMARY KEY
, EMP_NM
, HP_N
, DEPT_N
, FILE_ID);
INSERT INTO EMP VALUES('N0001','ABC','','D01','');
INSERT INTO EMP VALUES('N0002','ZZZ','6b0e62cc4ad4508b108540aba547afa8','D01','');
INSERT INTO EMP VALUES('N0003','ABC','55afea71894c57aa58405a112ab7532d','D02','');


CREATE TABLE BZ_ID (
ID PRIMARY KEY
, PIN
, AUTH
, FAIL_CNT
, LAST_LOGIN_DTM
);
INSERT INTO BZ_ID VALUES('id_A0','424ab5a6448f7b6aca9cd65c361b672c3d853622bd29001ee15bc5c50bcfa169','A0',0,'');
INSERT INTO BZ_ID VALUES('id_U0','337c1456c9b72fd82583e974ac3885295373b1968210cfc0cb5418c554935f4f','U0','0','');
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