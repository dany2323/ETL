SELECT 'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_CLASSIFICATORE_PK,
		hw.c_pk as STG_PK, 
		hw.c_id as CD_CLASSIFICATORE, 
		hw.c_created as DT_CREAZIONE_CLASSIF, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DT_MODIFICA_CLASSIF,
		hw.c_duedate as DT_SCADENZA_PROG_SVIL_D,
		hu.c_id as ID_AUTORE_CLASSIFICATORE, 
		hu.c_name as DS_AUTORE_CLASSIFICATORE,
		hw.c_title as TITOLO_CLASSIFICATORE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as  DT_RISOLUZIONE_CLASSIF,
		hw.c_resolution as MOTIVO_RISOLUZIONE_CLASSIF,
		hw.codice as CODICE,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ambito') as AMBITO
		from dmalm_sire_history_workitem hw left join dmalm_sire_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SIRE_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
		where hw.c_type = 'classificatore_demand'
		and hw.data_caricamento = ? 
UNION ALL  
SELECT 'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_CLASSIFICATORE_PK,
		hw.c_pk as STG_PK, 
		hw.c_id as CD_CLASSIFICATORE, 
		hw.c_created as DT_CREAZIONE_CLASSIF, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DT_MODIFICA_CLASSIF,
		hw.c_duedate as DT_SCADENZA_PROG_SVIL_D,
		hu.c_id as ID_AUTORE_CLASSIFICATORE, 
		hu.c_name as DS_AUTORE_CLASSIFICATORE,
		hw.c_title as TITOLO_CLASSIFICATORE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as  DT_RISOLUZIONE_CLASSIF,
		hw.c_resolution as MOTIVO_RISOLUZIONE_CLASSIF,
		hw.codice as CODICE,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ambito') as AMBITO
		from dmalm_siss_history_workitem hw left join dmalm_siss_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
				left join DMALM_SISS_HISTORY_USER hu 
				on hw.fk_author = hu.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
		where hw.c_type = 'classificatore_demand'
		and hw.data_caricamento = ? 
		