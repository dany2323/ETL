SPOOL 185_FUNCT_GER_CODICE_UO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FUNCTION FUNCT_GER_CODICE_UO(uo_pk in varchar2, data_s date, livello number) RETURN varchar2 IS
codice_uo varchar2(100);
case when reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) in ('LISPA','LIA900') or reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) is null then  
    case when reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) in ('LISPA','LIA900') or reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) is null then reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-2)) else reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello-1)) end
else reverse(regexp_substr(reverse(path_cod_uo_sup),'[^#]+',1,livello)) end
into codice_uo
from (
SELECT DISTINCT 
                          SYS_CONNECT_BY_PATH (c.CD_AREA, '#') PATH_cod_uo_sup
						  -- Specifica TABLESPACE
                      FROM DMALM_UNITA_ORGANIZZATIVA c
                    c.DMALM_UNITA_ORG_PK = uo_pk
                CONNECT BY NOCYCLE PRIOR CD_AREA = CD_UO_SUPERIORE
                              AND data_s  BETWEEN c.DT_INIZIO_VALIDITA  AND  c.DT_FINE_VALIDITA
                start with cd_area = 'LIA950' ); 
   RETURN codice_uo;
     WHEN NO_DATA_FOUND THEN
        codice_uo:= 'Non presente';
        RETURN codice_uo;
   WHEN OTHERS THEN
           codice_uo:= 'Non presente';
           RETURN codice_uo;
END funct_ger_codice_uo;
/
show errors;

SPOOL OFF;
