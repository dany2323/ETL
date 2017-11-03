 SELECT  
		'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_TESTCASE_PK,
		hw.c_pk as STG_TESTCASE_PK, 
		hw.c_id as CD_TESTCASE, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_TESTCASE,
		hu.c_id as ID_AUTORE_TESTCASE, 
		hu.c_name as NOME_AUTORE_TESTCASE, 
		hw.c_title as TITOLO_TESTCASE, 
		hw.c_duedate as DATA_SCADENZA_TESTCASE, 
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_TESTCASE, 
		hw.c_resolution as MOTIVO_RISOL_TESTCASE,
		to_char(hw.c_description) as DESCRIZIONE_TESTCASE,
		CASE
           WHEN hw.C_STATUS = 'eseguito' THEN (select min(shw.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'eseguito' and shw.C_UPDATED > nvl(
           (	
           select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
           ),
           {ts '1900-01-01 00:00:00'}
           ))
           else {ts '9999-12-31 00:00:00'}
        END as DATA_ESECUZIONE_TESTCASE,
		hw.codice as CODICE,
		hw.cod_intervento as CODICE_INTERVENTO,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY
		from dmalm_sire_history_workitem hw left join dmalm_sire_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SIRE_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
						left join DMALM_PROJECT p 
						on p.ID_PROJECT = hp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE'
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
		where hw.c_type = 'testcase'
		and hw.data_caricamento = ? 
UNION ALL  
 SELECT  
		'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_TESTCASE_PK,
		hw.c_pk as STG_TESTCASE_PK, 
		hw.c_id as CD_TESTCASE, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_TESTCASE,
		hu.c_id as ID_AUTORE_TESTCASE, 
		hu.c_name as NOME_AUTORE_TESTCASE, 
		hw.c_title as TITOLO_TESTCASE, 
		hw.c_duedate as DATA_SCADENZA_TESTCASE, 
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_TESTCASE, 
		hw.c_resolution as MOTIVO_RISOL_TESTCASE,
		to_char(hw.c_description) as DESCRIZIONE_TESTCASE,
		CASE
           WHEN hw.C_STATUS = 'eseguito' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'eseguito' and shw.C_UPDATED > nvl(
           (	
           select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
           ),
           {ts '1900-01-01 00:00:00'}
           ))
           else {ts '9999-12-31 00:00:00'}
        END as DATA_ESECUZIONE_TESTCASE,
		hw.codice as CODICE,
		hw.cod_intervento as CODICE_INTERVENTO,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY
		from dmalm_siss_history_workitem hw left join dmalm_siss_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SISS_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
						left join DMALM_PROJECT p 
						on p.ID_PROJECT = hp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS'
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
		where hw.c_type = 'testcase'
		and hw.data_caricamento = ? 