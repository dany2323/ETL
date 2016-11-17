SPOOL 189_FUNCT_GET_LAST_PROJECT.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FUNCTION FUNCT_GET_LAST_PROJECT(id_prj in varchar2, data_s in varchar2) RETURN varchar2 IS
nome_prj varchar2(100);
into nome_prj
from (
 case when rank() OVER (PARTITION BY  id_project order by dt_fine_validita desc) = 1 then nome_completo_project end as nome_project
 from  dmalm_project
 WHERE  id_project = id_prj AND
 to_char(dt_fine_validita,'YYYY-MM') >= data_s and to_char(dt_inizio_validita,'YYYY-MM')<=data_s); 
   RETURN nome_prj;
     WHEN NO_DATA_FOUND THEN
        nome_prj:= 'nd';
        RETURN nome_prj;
   WHEN OTHERS THEN
           nome_prj:= 'nd';
           RETURN nome_prj;
END funct_get_last_project;
/
show errors;

SPOOL OFF;
