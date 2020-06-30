SELECT * 
FROM DMALM_EL_PERSONALE p1 
WHERE CD_PERSONALE NOT IN (SELECT CD_PERSONALE FROM DMALM_STG_EL_PERSONALE WHERE DT_CARICAMENTO = ?) 
AND DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_PERSONALE p2 WHERE p1.CD_PERSONALE=p2.CD_PERSONALE) 
AND p1.DMALM_PERSONALE_PK >0 
AND NVL(p1.ANNULLATO,'NO') <>'SI' 

