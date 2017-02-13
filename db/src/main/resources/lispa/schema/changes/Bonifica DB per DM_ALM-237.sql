# Modificare per ambiente di PRE-PROD / PROD rimuovendo il mascheramento della uo.CD_AREA

UPDATE DMALM_EL_PERSONALE p
SET p.DMALM_UNITAORGANIZZATIVA_FK_01 =
        (
          select uo.DMALM_UNITA_ORG_PK
          FROM DMALM_EL_UNITA_ORGANIZZATIVE uo  
          WHERE p.CD_SUPERIORE = rawtohex(DBMS_CRYPTO.Hash (UTL_I18N.STRING_TO_RAW (uo.CD_AREA, 'AL32UTF8'),2))
          AND p.DT_INIZIO_VALIDITA >= uo.DT_INIZIO_VALIDITA
		  AND p.DT_FINE_VALIDITA <= uo.DT_FINE_VALIDITA
          )
AND p.DMALM_PERSONALE_PK <> 0;