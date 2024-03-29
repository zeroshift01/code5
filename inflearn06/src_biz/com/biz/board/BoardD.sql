--[[[ SELECT

SELECT 
TITLE
, TXT
, EM
, FILE_ID_1
, FILE_ID_1 
, FILE_NM_1
, FILE_ID_2
, FILE_ID_2 
, FILE_NM_2
, RG_ID
FROM
BZ_BOARD
WHERE N = [TOKEN_N]
ORDER BY N


--]]]


--[[[ LIST, TIMEOUT=10, LIMIT=3


SELECT 
N TOKEN_N
, N
, TITLE
, EM
FROM
BZ_BOARD
WHERE 1 = 1
[~ FIND_OPT ^ 01 ^ AND TITLE LIKE '%'||[FIND_STR]||'%' ~]
[~ NEXT_N ^ IS_NOT_NULL ^ AND N <= [NEXT_N] ~]
AND DEL_Y IS NULL
ORDER BY N DESC


--]]]

--[[[ WRITE

INSERT INTO BZ_BOARD
(
TITLE
, TXT
, EM
, FILE_ID_1
, FILE_NM_1
, FILE_ID_2
, FILE_NM_2
, RG_ID
, RG_IP
, RG_DTM
) VALUES (
[TITLE]
, [TXT]
, [EM]
, [FILE_ID_1]
, [FILE_NM_1]
, [FILE_ID_2]
, [FILE_NM_2]
, [SESSIONB.ID]
, [SESSIONB.IP]
, [SYSDTM.DTM]
)

--]]]

--[[[ UPDATE

UPDATE BZ_BOARD
SET TITLE = [TITLE]
, TXT = [TXT]
, EM = [EM]
[~ FILE_ID_1 ^ IS_NOT_NULL ^ , FILE_ID_1 = [FILE_ID_1] ~]
[~ FILE_ID_1 ^ IS_NOT_NULL ^ , FILE_NM_1 = [FILE_NM_1] ~]
[~ FILE_ID_2 ^ IS_NOT_NULL ^ , FILE_ID_2 = [FILE_ID_2] ~]
[~ FILE_ID_2 ^ IS_NOT_NULL ^ , FILE_NM_2 = [FILE_NM_2] ~]
, MDF_ID = [SESSIONB.ID]
, MDF_IP = [SESSIONB.IP]
, MDF_DTM = [SYSDTM.DTM]
WHERE N = [TOKEN_N]
AND RG_ID = [SESSIONB.ID]

--]]]

--[[[ UPDATE_ALL

UPDATE BZ_BOARD
SET TITLE = [TITLE]
, EM = [EM]
, MDF_ID = [SESSIONB.ID]
, MDF_IP = [SESSIONB.IP]
, MDF_DTM = [SYSDTM.DTM]
WHERE N = [TOKEN_N]

--]]]


--[[[ DELETE

UPDATE BZ_BOARD
SET DEL_Y = 'Y'
, MDF_ID = [SESSIONB.ID]
, MDF_IP = [SESSIONB.IP]
, MDF_DTM = [SYSDTM.DTM]
WHERE N = [TOKEN_N]
AND DEL_Y IS NULL


--]]]


--[[[ DELETEALL

DELETE BZ_BOARD;

--]]]
