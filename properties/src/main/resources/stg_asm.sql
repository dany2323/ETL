SELECT  
MIN(DMALM_STG_MISURA_PK) AS DMALM_STG_MISURA_PK, 
NOTE_ASM, 
P_APP_ACC_AUTH_LAST_UPDATE, 
P_APP_CD_ALTRE_ASM_COMMON_SERV, 
P_APP_CD_AMBITO_MAN_ATTUALE, 
P_APP_CD_AMBITO_MAN_FUTURO, 
P_APP_CD_ASM_CONFINANTI, 
P_APP_CD_DIREZIONE_DEMAND, 
P_APP_CD_FLUSSI_IO_ASM,  
TO_TIMESTAMP(P_APP_DT_FINE_VALIDITA_ASM,'DD-MM-YYYY HH24:MI:SS.FF') as P_APP_DT_FINE_VALIDITA_ASM, 
TO_TIMESTAMP(P_APP_DT_INIZIO_VALIDITA_ASM,'DD-MM-YYYY HH24:MI:SS.FF') as P_APP_DT_INIZIO_VALIDITA_ASM, 
TO_TIMESTAMP(P_APP_DT_ULTIMO_AGGIORN,'DD-MM-YYYY HH24:MI:SS.FF') as P_APP_DT_ULTIMO_AGGIORN, 
P_APP_DN_SIST_TERZI_CONFIN, 
P_APP_DN_UTENTI_FINALI_ASM, 
P_APP_DN_ASM, 
P_APP_FL_DAMISURARE_PATR_FP, 
P_APP_FL_IN_MANUTENZIONE, 
(CASE P_APP_FL_MISURARE_SVIMEV_FP   
WHEN 'SI' THEN 1 
WHEN 'NO' THEN 0 
END) as P_APP_FL_MISURARE_SVIMEV_FP, 
(CASE P_APP_FL_SERVIZIO_COMUNE 
WHEN 'SI' THEN 1 
WHEN 'NO' THEN 0 
END) as P_APP_FL_SERVIZIO_COMUNE, 
P_APP_INDIC_VALIDAZIONE_ASM, 
P_APP_NOME_AUTH_LAST_UPDATE, 
APP_CLS, 
APPLICAZIONE, 
TO_TIMESTAMP(DT_DISMISSIONE,'DD-MM-YYYY HH24:MI:SS.FF') as DT_DISMISSIONE,  
TO_TIMESTAMP(DT_INIZIO_ESERCIZIO, 'DD-MM-YYYY HH24:MI:SS.FF') as DT_INIZIO_ESERCIZIO, 
DT_CARICAMENTO, 
FREQUENZA_UTILIZZO, 
ID_ASM, 
INCLUDI_IN_DB_PATRIMONIO, 
NUMERO_UTENTI, 
PERMISSIONS, 
PROPRIETA_LEGALE, 
UTILIZZATA, 
VAF_PREDEFINITO,  
TO_TIMESTAMP(DT_PREVISTA_PROSSIMA_MPP_ASM, 'DD-MM-YYYY HH24:MI:SS.FF') as DT_PREVISTA_PROSSIMA_MPP_ASM,  
FIP01_INIZIO_ESERCIZIO_ASM, 
FIP02_INDICE_QUALITA_DOC_ASM, 
FIP03_COMP_PT_SVIL_ESERC_ASM, 
FIP04_NR_PT_TGT_ESERC_ASM, 
FIP07_LING_PROG_PRIN_REAL_ASM, 
FIP10_GRADO_ACCESSIBILITA_ASM, 
FIP11_GRADO_QUALITA_COD_ASM, 
FIP12_UT_FRAMEWORK_AZ_ASM, 
FIP13_COMP_ALG_SW_ASM, 
FIP15_LV_CURA_GRAF_INT_UT_ASM 
FROM DMALM_STG_MISURA 
WHERE DT_CARICAMENTO = ?  
GROUP BY 
NOTE_ASM, 
P_APP_ACC_AUTH_LAST_UPDATE, 
P_APP_CD_ALTRE_ASM_COMMON_SERV, 
P_APP_CD_AMBITO_MAN_ATTUALE, 
P_APP_CD_AMBITO_MAN_FUTURO, 
P_APP_CD_ASM_CONFINANTI, 
P_APP_CD_DIREZIONE_DEMAND, 
P_APP_CD_FLUSSI_IO_ASM,  
P_APP_DT_FINE_VALIDITA_ASM, 
P_APP_DT_INIZIO_VALIDITA_ASM , 
P_APP_DT_ULTIMO_AGGIORN,  
P_APP_DN_SIST_TERZI_CONFIN,  
P_APP_DN_UTENTI_FINALI_ASM, 
P_APP_DN_ASM,  
P_APP_FL_DAMISURARE_PATR_FP, 
P_APP_FL_IN_MANUTENZIONE, 
P_APP_FL_MISURARE_SVIMEV_FP, 
P_APP_FL_SERVIZIO_COMUNE, 
P_APP_INDIC_VALIDAZIONE_ASM, 
P_APP_NOME_AUTH_LAST_UPDATE, 
APP_CLS, 
APPLICAZIONE, 
DT_DISMISSIONE,  
DT_INIZIO_ESERCIZIO, 
DT_CARICAMENTO, 
FREQUENZA_UTILIZZO, 
ID_ASM, 
INCLUDI_IN_DB_PATRIMONIO, 
NUMERO_UTENTI, 
PERMISSIONS, 
PROPRIETA_LEGALE, 
UTILIZZATA, 
VAF_PREDEFINITO, 
DT_PREVISTA_PROSSIMA_MPP_ASM, 
FIP01_INIZIO_ESERCIZIO_ASM, 
FIP02_INDICE_QUALITA_DOC_ASM, 
FIP03_COMP_PT_SVIL_ESERC_ASM, 
FIP04_NR_PT_TGT_ESERC_ASM, 
FIP07_LING_PROG_PRIN_REAL_ASM, 
FIP10_GRADO_ACCESSIBILITA_ASM, 
FIP11_GRADO_QUALITA_COD_ASM, 
FIP12_UT_FRAMEWORK_AZ_ASM, 
FIP13_COMP_ALG_SW_ASM, 
FIP15_LV_CURA_GRAF_INT_UT_ASM 
ORDER BY ID_ASM 
