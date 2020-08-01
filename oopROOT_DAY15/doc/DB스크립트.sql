drop TABLE BZ_ALNC;

CREATE TABLE BZ_ALNC (
KEY -- KEY
, ALNC_DTM  -- 승인일시
, ALNC_N -- 승인번호
, CRD_N -- 카드번호
, AMT -- 승인금액
, RET -- 승인결과
, RG_ID -- 입력아이디
, RG_IP -- 입력아이피
, RG_DTM -- 입력시간
, MDF_ID -- 수정아이디
, MDF_IP -- 수정아이피
, MDF_DTM -- 수정시간
, PRIMARY KEY(KEY)
);

CREATE UNIQUE INDEX IX_BZ_ALNC_01 ON BZ_ALNC(ALNC_DTM, ALNC_N, CRD_N);



delete from fw_sql WHERE KEY LIKE 'EXE001%';

insert into fw_sql values('EXE001D_01','INSERT INTO BZ_ALNC (
KEY
, CRD_N
, AMT
, RG_ID
, RG_IP
, RG_DTM
) VALUES (
[KEY]
, [ENC_CRD_N]
, [AMT]
, [USER_ID]
, [USER_IP]
, [DTM]
)
');

insert into fw_sql values('EXE001D_02','UPDATE BZ_ALNC
SET ALNC_DTM = [ALNC_DTM]
, ALNC_N = [ALNC_N]
, RET = [RET]
, MDF_ID = [USER_ID]
, MDF_IP = [USER_IP]
, MDF_DTM = [MDF_DTM]
WHERE KEY = [KEY]
');

insert into fw_sql values ('EXE001D_03','SELECT 
KEY
, ALNC_DTM
, ALNC_N
, CRD_N
, AMT
, RET
, RG_ID
, RG_IP
, RG_DTM
, MDF_ID
, MDF_IP
, MDF_DTM
FROM BZ_ALNC
WHERE RG_ID = [USER_ID]
ORDER BY RG_DTM DESC 
');

insert into fw_sql values ('EXE001D_04','SELECT 
KEY
, ALNC_DTM
, ALNC_N
, CRD_N
, AMT
, RET
, RG_ID
, RG_IP
, RG_DTM
, MDF_ID
, MDF_IP
, MDF_DTM
FROM BZ_ALNC
WHERE RG_ID = [USER_ID]
AND CRD_N = [ENC_CRD_N] 
ORDER BY ALNC_DTM DESC 
');


insert into fw_controller values ('exe00110','com.code5.biz.exe001.Exe001','exe00110','Y','');
insert into fw_controller values ('exe00111','com.code5.biz.exe001.Exe001','exe00111','Y','');
insert into fw_controller values ('exe00120','com.code5.biz.exe001.Exe001','exe00120','Y','');

insert into fw_view values ('exe00110','/WEB-INF/classes/com/code5/biz/exe001/jsp/exe00110.jsp');
insert into fw_view values ('exe00120','/WEB-INF/classes/com/code5/biz/exe001/jsp/exe00120.jsp');


0