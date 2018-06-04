 SELECT  
		'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_PROGETTO_ESE_PK,
		hw.c_pk as STG_PROGETTO_ESE_PK, 
		hw.c_id as CD_PROGETTO_ESE, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_PROGETTO_ESE,
		hu.c_id as ID_AUTORE_PROGETTO_ESE, 
		hu.c_name as NOME_AUTORE_PROGETTO_ESE, 
		hw.c_duedate as DATA_SCADENZA_PROGETTO_ESE,
		hw.c_title as TITOLO_PROGETTO_ESE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_PROGETTO_ESE, 
		hw.c_resolution as MOTIVO_RISOLUZIONE_PROGETTOESE,
		to_char(hw.c_description) as DESCRIZIONE_PROGETTO_ESE,
		hw.codice as CODICE,
		(select distinct hcf.c_date_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_sottomissione') as DATA_SOTTOMISSIONE,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY
		from dmalm_sire_history_workitem hw left join dmalm_sire_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SIRE_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
		where hw.c_type = 'progettoese'
		and hw.data_caricamento = ? 
UNION ALL  
  SELECT  
		'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_PROGETTO_ESE_PK,
		hw.c_pk as STG_PROGETTO_ESE_PK, 
		hw.c_id as CD_PROGETTO_ESE, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_PROGETTO_ESE,
		hu.c_id as ID_AUTORE_PROGETTO_ESE, 
		hu.c_name as NOME_AUTORE_PROGETTO_ESE, 
		hw.c_duedate as DATA_SCADENZA_PROGETTO_ESE,
		hw.c_title as TITOLO_PROGETTO_ESE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_PROGETTO_ESE, 
		hw.c_resolution as MOTIVO_RISOLUZIONE_PROGETTOESE,
		to_char(hw.c_description) as DESCRIZIONE_PROGETTO_ESE,
		hw.codice as CODICE,
		(select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_sottomissione') as DATA_SOTTOMISSIONE,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY
		from dmalm_siss_history_workitem hw left join dmalm_siss_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SISS_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
		where hw.c_type = 'progettoese'
		and hw.data_caricamento = ? 