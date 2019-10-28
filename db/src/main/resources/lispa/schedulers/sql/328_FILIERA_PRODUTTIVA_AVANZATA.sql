SPOOL 327_FILIERA_PRODUTTIVA_AVANZATA.sql

SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
  
CREATE MATERIALIZED VIEW DMALM_FILIERA_PRODUTTIVA_AVANZ AS select
ID_FILIERA,
LIVELLO,
WI_FK FK_WI,
WI_CODICE CODICE_WI,
WI_TIPO TIPO_WI,
wi_repo ID_REPOSITORY,
wi_project CODICE_PROJECT,
wi_ruolo RUOLO,
posizione_wi,
-posizione_wi + LEAD(posizione_wi, 1, posizione_wi) OVER (PARTITION BY id_filiera ORDER BY livello) AS gap_wi
from (
WITH wi_linked AS
(
select a.*,
rank () over (partition by a.codice_wi_padre||tipo_wi_padre||id_repository_padre||codice_project_padre,a.codice_wi_figlio||tipo_wi_figlio||id_repository_figlio||codice_project_figlio order by a.linked_workitems_pk DESC) rank_wi,
codice_wi_padre||id_repository_padre||codice_project_padre||tipo_wi_padre chiave_padre,
codice_wi_figlio||id_repository_figlio||codice_project_figlio||tipo_wi_figlio chiave_figlio
from DMALM_LINKED_WORKITEMS a
where
a.tipo_wi_figlio in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman','release_ser','release_it', 'release')
and tipo_wi_padre in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman', 'release_ser','release_it', 'release')
),
wi_linked_set_root as
(
select DISTINCT a.*, nvl(b.codice_wi_figlio,'root') flag_root
from wi_linked a,
wi_linked b
where
a.codice_wi_padre||a.id_repository_padre||a.codice_project_padre||a.tipo_wi_padre = b.codice_wi_figlio(+)||b.id_repository_figlio(+)||b.codice_project_figlio(+)||b.tipo_wi_figlio(+)
and b.tipo_wi_figlio(+) in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman') and b.tipo_wi_padre(+) in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman') --modifica
and a.rank_wi = 1),
wi_linked_asc as
(
select * from wi_linked_set_root
where rank_wi = 1 and
(tipo_wi_figlio in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman') and tipo_wi_padre in ('rqd','sottoprogramma', 'programma', 'srqs', 'drqs','dman', 'sman'))
    and      ruolo in ('relates_to','parent')
),
wi_linked_desc as
(
select * from wi_linked
where rank_wi = 1 and
    (tipo_wi_figlio in ('release_ser','release_it', 'release', 'srqs', 'drqs','sman','dman')
    and tipo_wi_padre in ('release_ser','release_it', 'release')
    and      ruolo in ('rilasciato_in','incluso_in','rilasciato') )
),
wi_linked_ger_asc as
(select
      connect_by_root   wi_linked_asc.codice_wi_padre wi_padre,           
      connect_by_root   wi_linked_asc.fk_wi_padre fk_wi_padre,
      connect_by_root   wi_linked_asc.tipo_wi_padre tipo_wi_padre,
      connect_by_root   wi_linked_asc.id_repository_padre repository_padre,
      connect_by_root   wi_linked_asc.codice_project_padre codice_project_padre,
      fk_wi_figlio, codice_wi_figlio, tipo_wi_figlio,
      '#'||connect_by_root   wi_linked_asc.fk_wi_padre||SYS_CONNECT_BY_PATH (fk_wi_figlio, '#') PATH_cod_wi_sup_fk,
      '#'||connect_by_root   wi_linked_asc.codice_wi_padre||'*'||connect_by_root   wi_linked_asc.tipo_wi_padre||'*'||connect_by_root   wi_linked_asc.id_repository_padre||'*'||connect_by_root   wi_linked_asc.codice_project_padre||'*'||connect_by_root   wi_linked_asc.ruolo||SYS_CONNECT_BY_PATH (codice_wi_figlio||'*'||tipo_wi_figlio||'*'||id_repository_figlio||'*'||codice_project_figlio||'*'||ruolo, '#') PATH_cod_wi_sup,
      connect_by_isleaf   foglia,           
      to_char(level) livello
from wi_linked_asc
--where connect_by_isleaf = 1
START WITH wi_linked_asc.flag_root = 'root'
CONNECT BY NOCYCLE PRIOR chiave_figlio = chiave_padre
ORDER SIBLINGS BY wi_linked_asc.CHIAVE_padre
),

wi_linked_ger_desc as

(select

      connect_by_root   wi_linked_desc.codice_wi_padre wi_padre,           

      connect_by_root   wi_linked_desc.fk_wi_padre fk_wi_padre,

      connect_by_root   wi_linked_desc.tipo_wi_padre tipo_wi_padre,

      connect_by_root   wi_linked_desc.id_repository_padre repository_padre,

      connect_by_root   wi_linked_desc.codice_project_padre codice_project_padre,

     SYS_CONNECT_BY_PATH (fk_wi_padre, '#') PATH_cod_wi_sup_fk,

     SYS_CONNECT_BY_PATH (codice_wi_padre||'*'||tipo_wi_padre||'*'||id_repository_padre||'*'||codice_project_padre||'*'||ruolo, '#') PATH_cod_wi_sup,

      connect_by_isleaf   foglia,           

      to_char(level) livello

from wi_linked_desc

where connect_by_isleaf = 1

--START WITH wi_linked_desc.flag_root = 'root'

CONNECT BY NOCYCLE PRIOR chiave_padre = chiave_figlio

ORDER SIBLINGS BY wi_linked_desc.CHIAVE_figlio

),

wi_linked_ger_desc_dist as

(

select distinct * from wi_linked_ger_desc

),

wi_linked_ger_asc_dist as

(

--select distinct * from wi_linked_ger_asc

select

wi_padre, fk_wi_padre, tipo_wi_padre, repository_padre, codice_project_padre, fk_wi_figlio, codice_wi_figlio, tipo_wi_figlio,

PATH_cod_wi_sup_fk, PATH_cod_wi_sup, foglia, livello

from wi_linked_ger_asc

--where PATH_cod_wi_sup like '%AGORA-9301%'

union

select

case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then codice_wi_figlio else codice_wi_padre end codice_wi_padre,

case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then fk_wi_figlio else fk_wi_padre end fk_wi_padre,

case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then tipo_wi_figlio else tipo_wi_figlio end tipo_wi_padre,

case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then id_repository_figlio else id_repository_padre end id_repository_padre,

case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then codice_project_figlio else codice_project_padre end codice_project_padre,

fk_wi_figlio,

codice_wi_figlio,

tipo_wi_figlio,

'#'||case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then fk_wi_figlio else fk_wi_padre end PATH_cod_wi_sup_fk,

'#'||case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then codice_wi_figlio else codice_wi_padre end||'*'||case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then tipo_wi_figlio else tipo_wi_figlio end||'*'||case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then id_repository_figlio else id_repository_padre end||'*'||case when tipo_wi_padre in ('release', 'release_it', 'release_ser') then codice_project_figlio else codice_project_padre end||'*'||ruolo PATH_cod_wi_sup, 1 foglia, '1' livello

from wi_linked_set_root

where rank_wi = 1 and

ruolo in ('rilasciato_in')

),

wi_linked_ger as

(

select

--rownum id_filiera,

b.PATH_cod_wi_sup_fk||'#'||a.PATH_cod_wi_sup_fk path_ger_fk,

b.PATH_cod_wi_sup||'#'||a.PATH_cod_wi_sup path_ger

from

wi_linked_ger_desc_dist a,

wi_linked_ger_asc_dist b,

wi_linked_desc c

where

b.fk_wi_figlio = C.FK_WI_FIGLIO(+) and b.tipo_wi_figlio = C.TIPO_WI_FIGLIO(+)

and a.fk_wi_padre(+) = C.FK_WI_padre and a.tipo_wi_padre(+) = C.TIPO_WI_padre

--and c.ruolo in ('relates_to', 'rilasciato_in') --and c.tipo_wi_padre in ('release_ser','release_it', 'release')

--and c.rank_wi = 1

group by

b.PATH_cod_wi_sup_fk||'#'||a.PATH_cod_wi_sup_fk,

b.PATH_cod_wi_sup||'#'||a.PATH_cod_wi_sup

order by 2

),

wi_linked_ger_id as

(

select

rownum id_filiera,

path_ger_fk,

path_ger

from

wi_linked_ger

),

wi_linked_ger_upivot as

(

select

id_filiera,

d.id livello,

CASE WHEN d.id = 1 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,1))

     WHEN d.id = 2 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,1))

     WHEN d.id = 3 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,1))

     WHEN d.id = 4 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,1))

     WHEN d.id = 5 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,1)) 

     WHEN d.id = 6 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,1))

     WHEN d.id = 7 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,1))

     WHEN d.id = 8 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,1))

     WHEN d.id = 9 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,1))

     WHEN d.id = 10 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,1))

     WHEN d.id = 11 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,1)) 

     WHEN d.id = 12 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,1))

     WHEN d.id = 13 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,1))

     WHEN d.id = 14 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,1))

     WHEN d.id = 15 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,1))

END wi_codice,                                                                                                                    

CASE WHEN d.id = 1 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,1))

     WHEN d.id = 2 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,2))

     WHEN d.id = 3 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,3))

     WHEN d.id = 4 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,4))

     WHEN d.id = 5 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,5)) 

     WHEN d.id = 6 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,6))

     WHEN d.id = 7 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,7))

     WHEN d.id = 8 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,8))

     WHEN d.id = 9 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,9))

     WHEN d.id = 10 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,10))

     WHEN d.id = 11 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,11))

     WHEN d.id = 12 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,12))

     WHEN d.id = 13 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,13))

     WHEN d.id = 14 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,14))

     WHEN d.id = 15 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,15))    

END wi_fk,                                                                                                                                                                                

CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,2)

     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,2)

     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,2)

     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,2)

     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,2)

     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,2)

     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,2)

     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,2)

     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,2)

     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,2)

     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,2)

     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,2)

     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,2)

     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,2)

     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,2)      

END wi_tipo,

CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,3)

     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,3)

     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,3)

     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,3)

     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,3)

     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,3)

     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,3)

     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,3)

     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,3)

     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,3)

     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,3)

     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,3)

     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,3)

     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,3)

     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,3)      

END wi_repo,

CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,4)

     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,4)

     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,4)

     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,4)

     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,4)

     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,4)

     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,4)

     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,4)

     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,4)

     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,4)

     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,4)

     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,4)

     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,4)

     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,4)

     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,4)

END wi_project,

CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,5)

     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,5)

     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,5)

     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,5)

     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,5)

     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,5)

     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,5)

     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,5)

     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,5)

     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,5)

     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,5)

     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,5)

     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,5)

     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,5)

     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,5)      

END wi_ruolo

from

wi_linked_ger_id

CROSS JOIN (SELECT LEVEL ID

            FROM   dual

            CONNECT BY LEVEL <= 15) d

group by

id_filiera,

d.id,

CASE WHEN d.id = 1 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,1))

     WHEN d.id = 2 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,1))

     WHEN d.id = 3 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,1))

     WHEN d.id = 4 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,1))

     WHEN d.id = 5 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,1)) 
     WHEN d.id = 6 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,1))
     WHEN d.id = 7 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,1))
     WHEN d.id = 8 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,1))
     WHEN d.id = 9 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,1))
     WHEN d.id = 10 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,1))
     WHEN d.id = 11 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,1)) 
     WHEN d.id = 12 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,1))
     WHEN d.id = 13 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,1))
     WHEN d.id = 14 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,1))
     WHEN d.id = 15 THEN UPPER (REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,1))
END,                                                                                                                    
CASE WHEN d.id = 1 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,1))
     WHEN d.id = 2 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,2))
     WHEN d.id = 3 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,3))
     WHEN d.id = 4 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,4))
     WHEN d.id = 5 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,5)) 
     WHEN d.id = 6 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,6))
     WHEN d.id = 7 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,7))
     WHEN d.id = 8 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,8))
     WHEN d.id = 9 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,9))
     WHEN d.id = 10 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,10))
     WHEN d.id = 11 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,11))
     WHEN d.id = 12 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,12))
     WHEN d.id = 13 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,13))
     WHEN d.id = 14 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,14))
     WHEN d.id = 15 THEN UPPER (REGEXP_SUBSTR (path_ger_fk,'[^#]+',1,15))
END,                                                                                                                                                                                 
CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,2)
     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,2)
     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,2)
     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,2)
     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,2)
     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,2)
     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,2)
     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,2)
     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,2)
     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,2)
     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,2)
     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,2)
     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,2)
     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,2)
     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,2)
END ,
CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,3)
     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,3)
     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,3)
     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,3)
     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,3)
     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,3)
     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,3)
     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,3)
     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,3)
     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,3)
     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,3)
     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,3)
     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,3)
     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,3)
     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,3)
END ,
CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,4)
     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,4)
     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,4)
     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,4)
     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,4)
     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,4)
     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,4)
     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,4)
     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,4)
     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,4)
     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,4)
     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,4)
     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,4)
     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,4)
     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,4)
END ,
CASE WHEN d.id = 1 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,1),'[^*]+',1,5)
     WHEN d.id = 2 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,2),'[^*]+',1,5)
     WHEN d.id = 3 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,3),'[^*]+',1,5)
     WHEN d.id = 4 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,4),'[^*]+',1,5)
     WHEN d.id = 5 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,5),'[^*]+',1,5)
     WHEN d.id = 6 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,6),'[^*]+',1,5)
     WHEN d.id = 7 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,7),'[^*]+',1,5)
     WHEN d.id = 8 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,8),'[^*]+',1,5)
     WHEN d.id = 9 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,9),'[^*]+',1,5)
     WHEN d.id = 10 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,10),'[^*]+',1,5)
     WHEN d.id = 11 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,11),'[^*]+',1,5)
     WHEN d.id = 12 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,12),'[^*]+',1,5)
     WHEN d.id = 13 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,13),'[^*]+',1,5)
     WHEN d.id = 14 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,14),'[^*]+',1,5)
     WHEN d.id = 15 THEN REGEXP_SUBSTR (REGEXP_SUBSTR (path_ger,'[^#]+',1,15),'[^*]+',1,5) 
END)
select
wi_linked_ger_upivot.*,
case
when wi_tipo = 'programma' then 1
when wi_tipo = 'sottoprogramma' then 2
when wi_tipo = 'rqd' then 3
when wi_tipo = 'drqs' then 4
when wi_tipo = 'srqs' then 5
when wi_tipo = 'dman' then 6
when wi_tipo = 'sman' then 7
when wi_tipo = 'release' then 8
when wi_tipo = 'release_it' then 9
when wi_tipo = 'release_ser' then 10
else 0 end posizione_wi
from
wi_linked_ger_upivot
where wi_codice is not null)