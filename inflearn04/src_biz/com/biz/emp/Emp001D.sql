--[[[ EMP001D_01

SELECT
EMP_N
, EMP_NM
, DEPT_N
, HP_N
FROM EMP 
WHERE EMP_NM = [EMP_NM] 
ORDER BY EMP_N

--]]]

--[[[ EMP001D_02

UPDATE EMP 
SET HP_N = [HP_N] 
WHERE EMP_N = [EMP_N]

--]]]

