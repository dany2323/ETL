select
	p.DATA_CARICAMENTO as DT_CARICAMENTO,
	p.SIRE_HISTORY_PROJECT_PK as DMALM_PROJECT_PK,
	p.C_ID as ID_PROJECT,
	'SIRE' as ID_REPOSITORY,
	REPLACE(REPLACE(REGEXP_SUBSTR(p.C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as SIGLA_PROJECT,
    p.template as TEMPLATE,
	p.C_NAME as NOME_COMPLETO_PROJECT,
	p.C_LOCATION as PATH_PROJECT,
	p.C_PK,
	1 as FL_ATTIVO,
	p.C_REV as N_REVISION,
	p.C_CREATED, 
	p.C_TRACKERPREFIX, 
	p.C_IS_LOCAL, 
	p.FK_URI_LEAD, 
	p.C_DELETED, 
	p.C_FINISH, 
	p.C_URI, 
	p.C_START, 
	p.FK_URI_PROJECTGROUP, 
	p.C_ACTIVE, 
	p.FK_PROJECTGROUP, 
	p.FK_LEAD, 
	p.C_LOCKWORKRECORDSDATE, 
	p.C_DESCRIPTION 
 from DMALM_SIRE_HISTORY_PROJECT p 
 where p.DATA_CARICAMENTO = ?  
  and p.c_id is not null 
  and p.c_created < (SELECT MIN (DT_INIZIO_VALIDITA) 
                 FROM DMALM_PROJECT proj 
                 WHERE proj.id_repository='SIRE' 
                 AND proj.id_project=p.c_id) 
 UNION  ALL  
 select
	p.DATA_CARICAMENTO as DT_CARICAMENTO,
	p.SISS_HISTORY_PROJECT_PK as DMALM_PROJECT_PK,
	p.C_ID as ID_PROJECT,
	'SISS' as ID_REPOSITORY,
	REPLACE(REPLACE(REGEXP_SUBSTR(p.C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as SIGLA_PROJECT,
	p.template as TEMPLATE,
	p.C_NAME as NOME_COMPLETO_PROJECT,
	p.C_LOCATION as PATH_PROJECT,
	p.C_PK,
	1 as FL_ATTIVO,
	p.C_REV as N_REVISION,
	p.C_CREATED, 
	p.C_TRACKERPREFIX, 
	p.C_IS_LOCAL, 
	p.FK_URI_LEAD, 
	p.C_DELETED, 
	p.C_FINISH, 
	p.C_URI, 
	p.C_START, 
	p.FK_URI_PROJECTGROUP, 
	p.C_ACTIVE, 
	p.FK_PROJECTGROUP, 
	p.FK_LEAD, 
	p.C_LOCKWORKRECORDSDATE, 
	p.C_DESCRIPTION 
 from DMALM_SISS_HISTORY_PROJECT p 
 where p.DATA_CARICAMENTO = ?  
 and p.c_id is not null 
 and p.c_created < (SELECT MIN (DT_INIZIO_VALIDITA) 
                 FROM DMALM_PROJECT proj 
                 WHERE proj.id_repository='SISS' 
                 AND proj.id_project=p.c_id) 
 order by ID_PROJECT, N_REVISION DESC