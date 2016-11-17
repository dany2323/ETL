SPOOL 188_FUNCT_GER_DESCR_UO_NEW.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FUNCTION FUNCT_GER_DESCR_UO_NEW(uo_pk in varchar2, data_s date, data_u date, livello number) RETURN varchar2 IS
descr_uo varchar2(100);
select DS_AREA_EDMA 
into descr_uo
FROM DMALM_UNITA_ORGANIZZATIVA where cd_area = (
case when reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) in ('LISPA','LIA900') or reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) is null then  
    case when reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) in ('LISPA','LIA900') or reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) is null then reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-2)) else reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) end
else reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) end
from (
SELECT DISTINCT 
                          SYS_CONNECT_BY_PATH (c.CD_AREA, '#') PATH_cod_uo_sup
						  -- Specifica TABLESPACE
                      FROM DMALM_UNITA_ORGANIZZATIVA c
                    c.DMALM_UNITA_ORG_PK = uo_pk
                CONNECT BY NOCYCLE PRIOR CD_AREA = CD_UO_SUPERIORE
                              AND data_s  BETWEEN c.DT_INIZIO_VALIDITA  AND  c.DT_FINE_VALIDITA
                start with cd_area = 'LIA950' ) ) AND data_u between dt_inizio_validita and dt_fine_validita; 
   RETURN descr_uo;
     WHEN NO_DATA_FOUND THEN
        descr_uo:= 'Non presente';
        RETURN descr_uo;
   WHEN OTHERS THEN
       -- Consider LOGGING; the error and then re-raise
          descr_uo:= 'Non presente';
           RETURN descr_uo;
END funct_ger_descr_uo_new;
/
show errors;

SPOOL OFF;
