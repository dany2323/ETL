SPOOL 186_FUNCT_GER_DESCR_UO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FUNCTION FUNCT_GER_DESCR_UO(uo_cod in varchar2, data_s date, livello number) RETURN varchar2 IS
descr_uo varchar2(100);
case when upper(reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello))) like 'LOMBARDIA INFORMATICA%' or upper(reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello))) like 'PRESIDEN%' OR reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello)) IS NULL then  
    case when upper(reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello-1))) like 'LOMBARDIA INFORMATICA%' or upper(reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello-1))) like 'PRESIDEN%' OR reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello-1)) IS NULL then reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello-2)) else reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello-1)) end
else reverse(regexp_substr(reverse(PATH_desc_uo_sup),'[^*]+',1,livello)) end
into descr_uo
from (
SELECT DISTINCT 
                          SYS_CONNECT_BY_PATH (c.DS_AREA_EDMA, '*') PATH_desc_uo_sup
						  -- Specifica TABLESPACE
                      FROM DMALM_UNITA_ORGANIZZATIVA c
                    c.cd_area = uo_cod
                CONNECT BY NOCYCLE PRIOR CD_AREA = CD_UO_SUPERIORE
                              AND data_s  BETWEEN c.DT_INIZIO_VALIDITA  AND  c.DT_FINE_VALIDITA
                start with cd_area = 'LIA950' ); 
   RETURN descr_uo;
     WHEN NO_DATA_FOUND THEN
        descr_uo:= 'Non presente';
        RETURN descr_uo;
   WHEN OTHERS THEN
       -- Consider LOGGING; the error and then re-raise
          descr_uo:= 'Non presente';
           RETURN descr_uo;
END funct_ger_descr_uo;
/
show errors;

SPOOL OFF;
