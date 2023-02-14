--[[[ BZ_BOARD_01
DROP TABLE BZ_BOARD;
--]]]

--[[[ BZ_BOARD_02
CREATE TABLE BZ_BOARD(
N INT(10) AUTO_INCREMENT
, TITLE VARCHAR(1000)
, TXT VARCHAR(1000)
, EM VARCHAR(1000)
, FILE_NM_1 VARCHAR(1000)
, FILE_ID_1 VARCHAR(1000)
, FILE_NM_2 VARCHAR(1000)
, FILE_ID_2 VARCHAR(1000)
, RG_ID VARCHAR(1000)
, RG_IP VARCHAR(1000)
, RG_DTM VARCHAR(1000)
, MDF_ID VARCHAR(1000)
, MDF_IP VARCHAR(1000)
, MDF_DTM VARCHAR(1000)
, DEL_Y VARCHAR(1000)
, CONSTRAINT PK_BZ_BOARD PRIMARY KEY(N)
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
ID  VARCHAR(1000)
, PIN VARCHAR(1000)
, AUTH VARCHAR(1000)
, FAIL_CNT VARCHAR(1000)
, LAST_LOGIN_DTM VARCHAR(1000)
);

--]]]

1) DB 유출
2) 내부사용자 공격

"패스워드는 나만 알고 있어야 하는 정보"

--[[[ BZ_ID_I_01

INSERT INTO BZ_ID VALUES(
'admin'
,'413190dd77a132b98077112e493ce5bceef53ef60dfb4360764c1f9ceba85289'
,'A0'
,'0'
,''
);

--]]]

--[[[ BZ_ID_I_02

INSERT INTO BZ_ID VALUES(
'user1'
,'eaab2eac6bfb55c5994efbac5ec6b116a45f53892649c700003fdeeab765a1a3'
,'U0'
,'0'
,''
);

--]]]

--[[[ BZ_ID_I_03
INSERT INTO BZ_ID VALUES(
'user2'
,'5ced0bc626a116b6dafbf341db47bad167c444f53c4a769d8d7c09e1c6231c17'
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
FW_KEY VARCHAR(1000)
, CLASS_NAME VARCHAR(1000)
, METHOD_NAME VARCHAR(1000)
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
FW_KEY VARCHAR(1000)
, VIEW VARCHAR(1000)
, TMPL VARCHAR(1000)
, TITLE VARCHAR(1000)
);
--]]]


--[[[ FW_VIEW_I_02
INSERT INTO FW_VIEW VALUES ('list','list.jsp','main.jsp','list');
--]]]

--[[[ FW_VIEW_I_03
INSERT INTO FW_VIEW VALUES ('write','write.jsp','main.jsp','write');
--]]]

--[[[ FW_VIEW_I_04
INSERT INTO FW_VIEW VALUES ('update','update.jsp','main.jsp','update');
--]]]

--[[[ FW_VIEW_I_05
INSERT INTO FW_VIEW VALUES ('login','login.jsp','','login');
--]]]

--[[[ FW_VIEW_I_06
INSERT INTO FW_VIEW VALUES('null','/WEB-INF/classes/com/code5/fw/web/jsp/null.jsp','','');
--]]]

--[[[ FW_VIEW_I_07
INSERT INTO FW_VIEW VALUES('err','/WEB-INF/classes/com/code5/fw/web/jsp/err.jsp','','');

--]]]
