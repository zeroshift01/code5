--[[[ INITCODE5DBBYDEV_01

DROP TABLE BZ_BOARD;

--]]]
--[[[ INITCODE5DBBYDEV_02

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

--]]]
--[[[ INITCODE5DBBYDEV_03

DROP TABLE BZ_ID;

--]]]
--[[[ INITCODE5DBBYDEV_04

CREATE TABLE BZ_ID (
ID PRIMARY KEY
, PIN
, AUTH
, FAIL_CNT
, LAST_LOGIN_DTM
);

--]]]
--[[[ INITCODE5DBBYDEV_05


INSERT INTO BZ_ID VALUES(
'admin'
,'413190dd77a132b98077112e493ce5bceef53ef60dfb4360764c1f9ceba85289'
,'A0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_06

INSERT INTO BZ_ID VALUES(
'user1'
,'eaab2eac6bfb55c5994efbac5ec6b116a45f53892649c700003fdeeab765a1a3'
,'U0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_07

INSERT INTO BZ_ID VALUES(
'user2'
,'5ced0bc626a116b6dafbf341db47bad167c444f53c4a769d8d7c09e1c6231c17'
,'U0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_08


drop table FW_CONTROLLER;

--]]]
--[[[ INITCODE5DBBYDEV_09

CREATE TABLE FW_CONTROLLER(
KEY
, CLASS_NAME
, METHOD_NAME
);

--]]]
--[[[ INITCODE5DBBYDEV_10


INSERT INTO FW_CONTROLLER VALUES('file001','com.code5.fw.web.MasterControllerMultipart','file001');

--]]]
--[[[ INITCODE5DBBYDEV_11

INSERT INTO FW_CONTROLLER VALUES('exeLogin','com.biz.login.Login','exeLogin');

--]]]
--[[[ INITCODE5DBBYDEV_12
INSERT INTO FW_CONTROLLER VALUES('exeLogout','com.biz.login.Login','exeLogout');

--]]]
--[[[ INITCODE5DBBYDEV_13
INSERT INTO FW_CONTROLLER VALUES('callLogin','com.biz.login.Login','callLogin');

--]]]
--[[[ INITCODE5DBBYDEV_14


INSERT INTO FW_CONTROLLER VALUES('callList','com.biz.board.Board','callList');

--]]]
--[[[ INITCODE5DBBYDEV_15
INSERT INTO FW_CONTROLLER VALUES('callWrite','com.biz.board.Board','callWrite');

--]]]
--[[[ INITCODE5DBBYDEV_16
INSERT INTO FW_CONTROLLER VALUES('callUpdate','com.biz.board.Board','callUpdate');

--]]]
--[[[ INITCODE5DBBYDEV_17
INSERT INTO FW_CONTROLLER VALUES('callListByJson','com.biz.board.Board','callListByJson');

--]]]
--[[[ INITCODE5DBBYDEV_18


INSERT INTO FW_CONTROLLER VALUES('exeUpdate','com.biz.board.Board','exeUpdate');

--]]]
--[[[ INITCODE5DBBYDEV_19
INSERT INTO FW_CONTROLLER VALUES('exeWrite','com.biz.board.Board','exeWrite');

--]]]
--[[[ INITCODE5DBBYDEV_20
INSERT INTO FW_CONTROLLER VALUES('exeDelete','com.biz.board.Board','exeDelete');

--]]]
--[[[ INITCODE5DBBYDEV_21

INSERT INTO FW_CONTROLLER VALUES('delete','com.biz.board.Board','delete');

--]]]
--[[[ INITCODE5DBBYDEV_22

INSERT INTO FW_CONTROLLER VALUES('forceDelete','com.biz.board.BoardAdmin','forceDelete');

--]]]
--[[[ INITCODE5DBBYDEV_23
INSERT INTO FW_CONTROLLER VALUES('allDelete','com.biz.board.BoardAdmin','allDelete');

--]]]
--[[[ INITCODE5DBBYDEV_24
INSERT INTO FW_CONTROLLER VALUES('allUpdate','com.biz.board.BoardAdmin','allUpdate');

--]]]
--[[[ INITCODE5DBBYDEV_25


drop TABLE FW_VIEW ;

--]]]
--[[[ INITCODE5DBBYDEV_26

CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, VIEW
, TMPL
, TITLE);

--]]]
--[[[ INITCODE5DBBYDEV_27


INSERT INTO FW_VIEW VALUES('file001','/WEB-INF/classes/com/code5/fw/web/jsp/file001.jsp','','');

--]]]
--[[[ INITCODE5DBBYDEV_28
INSERT INTO FW_VIEW VALUES('null','/WEB-INF/classes/com/code5/fw/web/jsp/null.jsp','','');

--]]]
--[[[ INITCODE5DBBYDEV_29
INSERT INTO FW_VIEW VALUES('err','/WEB-INF/classes/com/code5/fw/web/jsp/err.jsp','','');

--]]]
--[[[ INITCODE5DBBYDEV_30

INSERT INTO FW_VIEW VALUES('login','/WEB-INF/classes/com/biz/login/jsp/login.jsp','','');

--]]]
--[[[ INITCODE5DBBYDEV_31
INSERT INTO FW_VIEW VALUES('list','list.jsp','main.jsp','리스트');

--]]]
--[[[ INITCODE5DBBYDEV_32
INSERT INTO FW_VIEW VALUES('write','write.jsp','main.jsp','등록화면');

--]]]
--[[[ INITCODE5DBBYDEV_33
INSERT INTO FW_VIEW VALUES('update','update.jsp','main.jsp','수정화면');

--]]]
--[[[ INITCODE5DBBYDEV_34

drop table FW_UPLOAD_FILE;

--]]]
--[[[ INITCODE5DBBYDEV_35
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

--]]]
--[[[ INITCODE5DBBYDEV_36

drop table FW_DOWNLOAD_FILE;

--]]]
--[[[ INITCODE5DBBYDEV_37

CREATE TABLE FW_DOWNLOAD_FILE (
FILE_ID
, ID
, DTM
);

--]]]
