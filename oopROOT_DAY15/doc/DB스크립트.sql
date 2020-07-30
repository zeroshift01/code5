drop TABLE BZ_ALNC;

CREATE TABLE BZ_ALNC (
ALNC_DTM  -- 승인일시
, CRD_N -- 카드번호
, AMT -- 승인금액
, RET -- 승인결과
, RG_ID -- 입력아이디
, RG_IP -- 입력아이피
, RG_DTM -- 입력시간
, MDF_ID -- 수정아이디
, MDF_IP -- 수정아이피
, MDF_DTM -- 수정시간
, PRIMARY KEY(ALNC_DTM, CRD_N)
);

insert into fw_sql values('EXE001D_01','INSERT INTO BZ_ALNC (
ALNC_DTM
, CRD_N
, AMT
, RET
, RG_ID
, RG_IP
, RG_DTM
) VALUES (
[ALNC_DTM]
, [CRD_N]
, [AMT]
, [RET]
, [USER_ID]
, [USER_IP]
, [DTM]
)
');

insert into fw_sql values('EXE001D_02','UPDATE BZ_ALNC
SET RET = [RET]
, MDF_ID = [USER_ID]
, MDF_IP = [USER_IP]
, MDF_DTM = [MDF_DTM]
WHERE ALNC_DTM = [DTM]
AND CRD_N = [CRD_N]
');

insert into fw_sql values ('EXE001D_03','SELECT 
ALNC_DTM
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
ORDER BY ALNC_DTM DESC 
');

insert into fw_sql values ('EXE001D_04','SELECT 
ALNC_DTM
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
AND CRD_N = [CRD_N] 
ORDER BY ALNC_DTM DESC 
');

insert into fw_controller values ('exe00110','com.code5.biz.exe001.Exe001','exe00110','Y','');
insert into fw_controller values ('exe00111','com.code5.biz.exe001.Exe001','exe00111','Y','');
insert into fw_controller values ('exe00120','com.code5.biz.exe001.Exe001','exe00120','Y','');

insert into fw_view values ('exe00110','/WEB-INF/classes/com/code5/biz/exe001/jsp/exe00110.jsp');
insert into fw_view values ('exe00120','/WEB-INF/classes/com/code5/biz/exe001/jsp/exe00120.jsp');

