SELECT
 DMALM_STG_PROD_ARCH_PK,
 ID_PRODOTTO_EDMA,
 ID_PRODOTTO,
 TIPO_OGGETTO,
 SIGLA,
 NOME,
 DS_PRODOTTO,
 AREA_PRODOTTO,
 RESPONSABILE_PRODOTTO,
 ANNULLATO,
 DT_ANNULLAMENTO,
 AMBITO_MANUTENZIONE,
 AREA_TEMATICA,
 BASE_DATI_ETL,
 BASE_DATI_LETTURA,
 BASE_DATI_SCRITTURA,
 CATEGORIA,
 FORNITURA_RISORSE_ESTERNE,
 CD_AREA_PRODOTTO,
 DT_CARICAMENTO,
 AMBITO_TECNOLOGICO,
 AMBITO_MANUTENZIONE_DENOM,
 AMBITO_MANUTENZIONE_CODICE,
 STATO_PRODOTTO,
 
NVL(CASE WHEN CD_AREA_PRODOTTO is null
            THEN 0
            ELSE ( SELECT uo.DMALM_UNITA_ORG_PK FROM DMALM_EL_UNITA_ORGANIZZATIVE uo where uo.CD_AREA = CD_AREA_PRODOTTO and uo.DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_UNITA_ORGANIZZATIVE mx where mx.CD_AREA =CD_AREA_PRODOTTO) ) 
        END, 0) as UNITAORGANIZZATIVA_FK, 
NVL(CASE WHEN RESPONSABILE_PRODOTTO is null
            THEN 0
            ELSE ( SELECT p.DMALM_PERSONALE_PK FROM DMALM_EL_PERSONALE p where p.CD_PERSONALE = CD_RESPONSABILE_PRODOTTO and p.DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_PERSONALE mx where mx.CD_PERSONALE = CD_RESPONSABILE_PRODOTTO ) ) 
        END, 0) as PERSONALE_FK 
FROM DMALM_STG_EL_PRODOTTI_ARCH   
WHERE DT_CARICAMENTO = ? 
