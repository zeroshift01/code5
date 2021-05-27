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