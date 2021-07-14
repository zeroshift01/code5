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

--[[[ BZ_BOARD_03
INSERT INTO BZ_BOARD (TITLE, TXT, EM) VALUES('TITLE 1','TXT 1', 'EM 1');
--]]]

--[[[ BZ_BOARD_04
INSERT INTO BZ_BOARD (TITLE, TXT, EM) VALUES('TITLE 2','TXT 2', 'EM 2');
--]]]

--[[[ BZ_BOARD_05
INSERT INTO BZ_BOARD (TITLE, TXT, EM) VALUES('TITLE 3','TXT 3', 'EM 3');
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

--[[[ FW_CONTROLLER_I_03
INSERT INTO FW_CONTROLLER VALUES('exeWrite','com.biz.board.Board','exeWrite');
--]]]

--[[[ FW_CONTROLLER_I_04
INSERT INTO FW_CONTROLLER VALUES('exeLogin','com.biz.login.Login','exeLogin');
--]]]

--[[[ FW_CONTROLLER_I_05
INSERT INTO FW_CONTROLLER VALUES('callLogin','com.biz.login.Login','callLogin');
--]]]
