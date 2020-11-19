cd C:\public\sqlite
sqlite3 C:\public\sqlite\code5.db



DROP TABLE FW_SQL;

DROP TABLE FW_CONTROLLER;

DROP TABLE FW_VIEW;

CREATE TABLE FW_SQL (
KEY PRIMARY KEY
, SQL
);

CREATE TABLE FW_CONTROLLER (
KEY PRIMARY KEY
, CLASS_NAME
, METHOD_NAME
);

CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, JSP
);

INSERT INTO FW_SQL VALUES (
'MASTERCONTROLLERD_01'
, 'SELECT
KEY
, CLASS_NAME
, METHOD_NAME
FROM FW_CONTROLLER
WHERE KEY = [KEY]'
);

INSERT INTO FW_SQL VALUES (
'MASTERCONTROLLERD_02'
, 'SELECT
KEY
, JSP
FROM FW_VIEW
WHERE KEY = [KEY]'
);



INSERT INTO FW_CONTROLLER VALUES ('mng00110','com.code5.biz.mng001.Mng001','mng00110');
INSERT INTO FW_CONTROLLER VALUES ('mng00111','com.code5.biz.mng001.Mng001','mng00111');
INSERT INTO FW_CONTROLLER VALUES ('mng00120','com.code5.biz.mng001.Mng001','mng00120');
INSERT INTO FW_CONTROLLER VALUES ('mng00121','com.code5.biz.mng001.Mng001','mng00121');
INSERT INTO FW_CONTROLLER VALUES ('mng00130','com.code5.biz.mng001.Mng001','mng00130');
INSERT INTO FW_CONTROLLER VALUES ('mng00131','com.code5.biz.mng001.Mng001','mng00131');


INSERT INTO FW_VIEW VALUES ('mng00110','/WEB-INF/classes/com/code5/biz/mng001/jsp/mng00110.jsp');
INSERT INTO FW_VIEW VALUES ('mng00120','/WEB-INF/classes/com/code5/biz/mng001/jsp/mng00120.jsp');
INSERT INTO FW_VIEW VALUES ('mng00130','/WEB-INF/classes/com/code5/biz/mng001/jsp/mng00130.jsp');


INSERT INTO FW_SQL VALUES ('MNG001D_01','SELECT KEY
, SQL
FROM FW_SQL
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_02','UPDATE FW_SQL
SET SQL = [SQL]
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_03','INSERT INTO FW_SQL (
KEY
, SQL
) VALUES (
[KEY]
, [SQL]
)
');

INSERT INTO FW_SQL VALUES ('MNG001D_04','SELECT
KEY
, CLASS_NAME
, METHOD_NAME
FROM FW_CONTROLLER
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_05','UPDATE FW_CONTROLLER
SET CLASS_NAME = [CLASS_NAME]
, METHOD_NAME = [METHOD_NAME]
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_06','INSERT INTO FW_CONTROLLER
(
KEY
, CLASS_NAME
, METHOD_NAME
) 
VALUES
(
[KEY]
, [CLASS_NAME]
, [METHOD_NAME]
)
');

INSERT INTO FW_SQL VALUES ('MNG001D_07','SELECT 
KEY
, JSP
FROM FW_VIEW 
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_08','UPDATE FW_VIEW
SET JSP = [JSP]
FROM FW_VIEW 
WHERE KEY = [KEY]
');

INSERT INTO FW_SQL VALUES ('MNG001D_09','INSERT INTO FW_VIEW 
(
KEY
, JSP
) VALUES (
[KEY]
, [JSP]
)
');