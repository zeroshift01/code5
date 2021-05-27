del C:\public\code5\doc\code5.db
cd C:\public\lib\sqlite
sqlite3 C:\public\code5\doc\code5.db

DROP TABLE EMP ;

CREATE TABLE EMP (
EMP_N PRIMARY KEY
, EMP_NM
, HP_N
, DEPT_N
);

INSERT INTO EMP VALUES ('N01','ABC','','D01');
INSERT INTO EMP VALUES ('N02','ZZZ','','D01');
INSERT INTO EMP VALUES ('N03','ABC','','D02');

DROP TABLE FW_CONTROLLER;
CREATE TABLE FW_CONTROLLER (
KEY PRIMARY KEY
, CLASS_NAME
, METHOD_NAME
);

DROP TABLE FW_VIEW;
CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, JSP
);

INSERT INTO FW_CONTROLLER VALUES ('emp00110','com.code5.biz.emp.Emp001','emp00110');
INSERT INTO FW_CONTROLLER VALUES ('emp00120','com.code5.biz.emp.Emp001','emp00120');
INSERT INTO FW_CONTROLLER VALUES ('login','com.code5.biz.login.Login','login');
INSERT INTO FW_CONTROLLER VALUES ('loginView','com.code5.biz.login.Login','loginView');

INSERT INTO FW_VIEW VALUES ('emp00110','/WEB-INF/classes/com/code5/biz/emp/jsp/emp00110.jsp');
INSERT INTO FW_VIEW VALUES ('loginView','/WEB-INF/classes/com/code5/biz/login/jsp/loginView.jsp');

