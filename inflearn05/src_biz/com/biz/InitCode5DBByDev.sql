--[[[ BZ_BOARD_01
DROP TABLE BZ_BOARD;
--]]]

--[[[ BZ_BOARD_02
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

--[[[ BZ_BOARD_I_01
INSERT INTO BZ_BOARD (TITLE, TXT) VALUES('TITLE 1','TXT 1');
--]]]

--[[[ BZ_BOARD_I_02
INSERT INTO BZ_BOARD (TITLE, TXT) VALUES('TITLE 2','TXT 2');
--]]]

--[[[ BZ_BOARD_I_03
INSERT INTO BZ_BOARD (TITLE, TXT) VALUES('TITLE 3','TXT 3');
--]]]


--[[[ BZ_ID_01

DROP TABLE BZ_ID;

--]]]
--[[[ BZ_ID_02

CREATE TABLE BZ_ID (
ID PRIMARY KEY
, PIN
, AUTH
, FAIL_CNT
, LAST_LOGIN_DTM
);

--]]]

--[[[ BZ_ID_I_01

INSERT INTO BZ_ID VALUES(
'admin'
,'1'
,'A0'
,'0'
,''
);

--]]]

--[[[ BZ_ID_I_02

INSERT INTO BZ_ID VALUES(
'user1'
,'1'
,'U0'
,'0'
,''
);

--]]]

--[[[ BZ_ID_I_03
INSERT INTO BZ_ID VALUES(
'user2'
,'1'
,'U0'
,'0'
,''
);
--]]]



--[[[ FW_CONTROLLER_01
drop table FW_CONTROLLER;
--]]]

--[[[ FW_CONTROLLER_02
CREATE TABLE FW_CONTROLLER(
KEY
, CLASS_NAME
, METHOD_NAME
);
--]]]


--[[[ FW_CONTROLLER_I_01
INSERT INTO FW_CONTROLLER VALUES('callList','com.biz.board.Board','callList');
--]]]

--[[[ FW_CONTROLLER_I_02
INSERT INTO FW_CONTROLLER VALUES('callWrite','com.biz.board.Board','callWrite');
--]]]

--[[[ FW_CONTROLLER_I_02_2
INSERT INTO FW_CONTROLLER VALUES('exeWrite','com.biz.board.Board','exeWrite');
--]]]

--[[[ FW_CONTROLLER_I_03
INSERT INTO FW_CONTROLLER VALUES('callUpdate','com.biz.board.Board','callUpdate');
--]]]

--[[[ FW_CONTROLLER_I_04
INSERT INTO FW_CONTROLLER VALUES('exeUpdate','com.biz.board.Board','exeUpdate');
--]]]

--[[[ FW_CONTROLLER_I_05
INSERT INTO FW_CONTROLLER VALUES('exeDelete','com.biz.board.Board','exeDelete');
--]]]

--[[[ FW_CONTROLLER_I_06
INSERT INTO FW_CONTROLLER VALUES('forceDelete','com.biz.board.BoardAdmin','forceDelete');
--]]]

--[[[ FW_CONTROLLER_I_07
INSERT INTO FW_CONTROLLER VALUES('allDelete','com.biz.board.BoardAdmin','allDelete');
--]]]

--[[[ FW_CONTROLLER_I_08
INSERT INTO FW_CONTROLLER VALUES('allUpdate','com.biz.board.BoardAdmin','allUpdate');
--]]]

--[[[ FW_CONTROLLER_I_09
INSERT INTO FW_CONTROLLER VALUES('delete','com.biz.board.Board','delete');
--]]]

--[[[ FW_CONTROLLER_I_10
INSERT INTO FW_CONTROLLER VALUES('exeLogin','com.biz.login.Login','exeLogin');
--]]]

--[[[ FW_CONTROLLER_I_11
INSERT INTO FW_CONTROLLER VALUES('exeLogout','com.biz.login.Login','exeLogout');
--]]]

--[[[ FW_CONTROLLER_I_12
INSERT INTO FW_CONTROLLER VALUES('callLogin','com.biz.login.Login','callLogin');
--]]]


--[[[ FW_VIEW_01
drop TABLE FW_VIEW ;
--]]]

--[[[ FW_VIEW_02
CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, VIEW
, TMPL
, TITLE
);
--]]]


--[[[ FW_VIEW_I_02
INSERT INTO FW_VIEW (KEY,VIEW,TMPL,TITLE) VALUES ('list','list.jsp','main.jsp','게시물 리스트');
--]]]

--[[[ FW_VIEW_I_03
INSERT INTO FW_VIEW (KEY,VIEW,TMPL,TITLE) VALUES ('write','write.jsp','main.jsp','게시물 쓰기');
--]]]

--[[[ FW_VIEW_I_04
INSERT INTO FW_VIEW (KEY,VIEW,TMPL,TITLE) VALUES ('update','update.jsp','main.jsp','게시물 수정');
--]]]

--[[[ FW_VIEW_I_05
INSERT INTO FW_VIEW (KEY,VIEW,TMPL,TITLE) VALUES ('login','login.jsp','','');
--]]]

--[[[ FW_VIEW_I_06
INSERT INTO FW_VIEW VALUES('null','/WEB-INF/classes/com/code5/fw/web/jsp/null.jsp','','');
--]]]

--[[[ FW_VIEW_I_07
INSERT INTO FW_VIEW VALUES('err','/WEB-INF/classes/com/code5/fw/web/jsp/err.jsp','','');

--]]]
