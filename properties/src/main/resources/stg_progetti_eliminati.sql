SELECT DISTINCT PRJ.ID_ASM, PRJ.ID_PROGETTO                                          
FROM DMALM_PROGETTO_SFERA PRJ                                                               
WHERE PRJ.DT_FINE_VALIDITA = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss') 
AND PRJ.ID_ASM > 0 
AND PRJ.ID_PROGETTO > 0 
AND NOT NVL(PRJ.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE'                                   
AND NOT PRJ.nome_progetto IN ( 
					SELECT DISTINCT PROG.nome_progetto                                    
                         FROM DMALM_PROGETTO_SFERA PROG, DMALM_ASM ASMPROG, DMALM_ASM ASMPRJ                                                 
                        WHERE PROG.DT_FINE_VALIDITA = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss') 
                          AND NOT NVL(PROG.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE'         
                          AND PROG.NOME_PROGETTO = PRJ.nome_progetto 
                          AND NOT PROG.ID_ASM = PRJ.ID_ASM 
                          AND NOT PROG.ID_PROGETTO = PRJ.ID_PROGETTO 
                          AND ASMPROG.DMALM_ASM_PK = PROG.DMALM_ASM_FK 
                          AND ASMPRJ.DMALM_ASM_PK = PRJ.DMALM_ASM_FK  
                          AND ASMPROG.APPLICAZIONE = ASMPRJ.APPLICAZIONE  
                          AND NOT ASMPROG.ID_ASM = ASMPRJ.ID_ASM )  
MINUS 
SELECT DISTINCT ID_ASM, ID_PROGETTO  
FROM DMALM_STG_MISURA 
WHERE DT_CARICAMENTO = ?  
AND ID_ASM > 0 
AND ID_PROGETTO > 0 
 