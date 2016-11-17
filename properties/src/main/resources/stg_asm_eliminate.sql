SELECT DISTINCT ASM.ID_ASM 
FROM DMALM_ASM ASM 
WHERE ASM.DT_FINE_VALIDITA = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss')  
AND ASM.ID_ASM > 0 
AND NOT NVL(ASM.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE' 
AND NOT ASM.APPLICAZIONE IN (SELECT APPLICAZIONE FROM DMALM_ASM EQ 
                                                WHERE EQ.APPLICAZIONE = ASM.APPLICAZIONE 
                                                AND NOT EQ.ID_ASM = ASM.ID_ASM 
                                                AND EQ.DT_FINE_VALIDITA = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss') 
                                                AND NOT NVL(EQ.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE') 
MINUS  
SELECT DISTINCT ID_ASM  
FROM DMALM_STG_MISURA 
WHERE DT_CARICAMENTO = ? 
AND ID_ASM > 0 