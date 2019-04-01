SELECT 'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_RELEASESER_PK,
		hw.c_pk as STG_RELEASESER_PK, 
		hw.c_id as CD_RELEASESER, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RELEASESER,
		hw.c_duedate as DATA_SCADENZA_RELEASESER,
		hu.c_id as ID_AUTORE_RELEASESER, 
		hu.c_name as NOME_AUTORE_RELEASESER,
		hw.c_title as TITOLO_RELEASESER,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RELEASESER,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_RELEASESER,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'rich_impatti') as RICHIESTA_IMPATTI,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'prev_fermo') as PREV_FERMO,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'motivoSospensione') as MOTIVO_SOSPENSIONE,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
		from dmalm_sire_history_workitem hw left join dmalm_sire_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
		left join DMALM_SIRE_HISTORY_USER hu 
		on hw.fk_author = hu.c_pk 
			left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
		where hw.c_type = 'release_ser'
		and hw.data_caricamento = ?  
UNION  ALL  
SELECT 'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI,
		hw.siss_history_workitem_pk as DMALM_RELEASESER_PK,
		hw.c_pk as STG_RELEASESER_PK, 
		hw.c_id as CD_RELEASESER, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RELEASESER,
		hw.c_duedate as DATA_SCADENZA_RELEASESER,
		hu.c_id as ID_AUTORE_RELEASESER, 
		hu.c_name as NOME_AUTORE_RELEASESER,
		hw.c_title as TITOLO_RELEASESER,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RELEASESER,
		nvl(
		hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
			) as DATA_RISOLUZIONE_RELEASESER,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'rich_impatti') as RICHIESTA_IMPATTI,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'prev_fermo') as PREV_FERMO,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'motivoSospensione') as MOTIVO_SOSPENSIONE,
		hw.c_severity as SEVERITY,
		hw.c_priority as PRIORITY,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
		from dmalm_siss_history_workitem hw left join dmalm_siss_history_project hp 
		on hw.FK_PROJECT = hp.c_pk 
		left join DMALM_SISS_HISTORY_USER hu 
		on hw.fk_author = hu.c_pk 
			left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
		where hw.c_type = 'release_ser'
		and hw.data_caricamento = ?  