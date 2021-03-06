SELECT ID_ASM, ID_PROGETTO, ID_MSR , DM_ALM_MISURA_SEQ.nextval AS MISURA_PK 
FROM ( 
 SELECT DISTINCT ID_ASM, ID_PROGETTO, ID_MSR 
 FROM DMALM_MISURA MISURA 
 WHERE MISURA.RANK_STATO_MISURA = 1 
 AND MISURA.ID_ASM > 0 
 AND MISURA.ID_PROGETTO > 0 
 AND MISURA.ID_MSR > 0 
 AND NOT NVL(MISURA.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE'  
 AND NOT MISURA.NOME_MISURA IN ( SELECT DISTINCT MIS.NOME_MISURA
                            FROM DMALM_MISURA MIS, 
                            DMALM_PROGETTO_SFERA PRJMIS, DMALM_PROGETTO_SFERA PRJMISURA,
                            DMALM_ASM ASMMIS, DMALM_ASM ASMMISURA
                           WHERE MIS.RANK_STATO_MISURA = 1
                             AND NOT NVL(MIS.ANNULLATO, ' ') = 'ANNULLATO FISICAMENTE'
                             AND MISURA.NOME_MISURA = MIS.NOME_MISURA 
                             AND NOT MIS.ID_MSR = MISURA.ID_MSR
                             AND NOT MIS.ID_PROGETTO = MISURA.ID_PROGETTO
                             AND NOT MIS.ID_ASM = MISURA.ID_ASM
                             AND PRJMIS.DMALM_PROGETTO_SFERA_PK = MIS.DMALM_PRJ_FK
                             AND PRJMIS.DMALM_PROGETTO_SFERA_PK = MIS.DMALM_PRJ_FK
                             AND PRJMISURA.NOME_PROGETTO = PRJMIS.NOME_PROGETTO
                             AND NOT PRJMISURA.ID_PROGETTO = PRJMIS.ID_PROGETTO
                             AND ASMMIS.DMALM_ASM_PK = PRJMIS.DMALM_ASM_FK 
                             AND ASMMISURA.DMALM_ASM_PK = PRJMISURA.DMALM_ASM_FK  
                             AND ASMMISURA.APPLICAZIONE = ASMMIS.APPLICAZIONE  
                             AND NOT ASMMISURA.ID_ASM = ASMMIS.ID_ASM)
  MINUS 
 SELECT DISTINCT ID_ASM, ID_PROGETTO, ID_MSR 
 FROM DMALM_STG_MISURA 
 WHERE DT_CARICAMENTO = ? 
 AND ID_ASM > 0 
 AND ID_PROGETTO > 0 
 AND ID_MSR > 0  )