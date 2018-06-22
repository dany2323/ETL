SELECT 'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_ANOM_ASS_PK,
		hw.c_pk as STG_ANOM_ASS_PK, 
		hw.c_id as CD_ANOM_ASS, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hw.c_duedate as DATA_SCADENZA,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_ANOM_ASS,
		hu.c_id as ID_AUTORE_ANOM_ASS, 
		hu.c_name as NOME_AUTORE_ANOM_ASS,
		hw.c_title as TITOLO_ANOM_ASS,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		CASE 
			WHEN NOT hw.C_RESOLVEDON is null THEN (
				SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN hw.c_created and hw.C_RESOLVEDON 
			) 
			ELSE 0 
		END as GIORNI_FESTIVI, 
		CASE 
			WHEN hw.C_RESOLVEDON is null THEN 0 
			WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') = to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1 
			ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd')) 
			END as TEMPO_TOTALE_RISOLUZIONE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_ANOM_ASS,
		hw.c_resolution as MOTIVO_RISOLUZIONE_ANOM_ASS,
		hw.c_severity as SEVERITY_ANOM_ASS,
		hw.c_priority as PRIORITA_ANOM_ASS,
		hw.ticket_id as TICKET_ID,
		hw.ca as CA,
		nvl (
			(select distinct hcf.c_float_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'cs')
			,(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'cs')
		) as CS,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'prod_cod') as CODICE_PRODOTTO,	
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'frequenza') as FREQUENZA,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'segnalazioni') as SEGNALAZIONI,	
		hw.st_chiuso as STATO_CHIUSO,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'so') as SO,
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'platform') as PLATFORM,
		hw.aoid as AOID
		from dmalm_sire_history_workitem hw left join dmalm_sire_history_project hp 
		on hw.FK_PROJECT = hp.c_pk
				left join DMALM_SIRE_HISTORY_USER hu on hw.fk_author = hu.c_pk
					left join dmalm_sire_history_project hp on hw.FK_PROJECT = hp.c_pk 
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY='SIRE' 
		where hw.c_type = 'anomalia_assistenza'
		and hw.data_caricamento = ?  
UNION ALL  
	SELECT 'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_ANOM_ASS_PK,
		hw.c_pk as STG_ANOM_ASS_PK, 
		hw.c_id as CD_ANOM_ASS, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hw.c_duedate as DATA_SCADENZA,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_ANOM_ASS,
		hu.c_id as ID_AUTORE_ANOM_ASS, 
		hu.c_name as NOME_AUTORE_ANOM_ASS,
		hw.c_title as TITOLO_ANOM_ASS,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		CASE 
			WHEN NOT hw.C_RESOLVEDON is null THEN (
				SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN hw.c_created and hw.C_RESOLVEDON 
			) 
			ELSE 0 
		END as GIORNI_FESTIVI, 
		CASE 
			WHEN hw.C_RESOLVEDON is null THEN 0 
			WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') = to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1 
			ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd')) 
			END as TEMPO_TOTALE_RISOLUZIONE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_ANOM_ASS,
		hw.c_resolution as MOTIVO_RISOLUZIONE_ANOM_ASS,
		hw.c_severity as SEVERITY_ANOM_ASS,
		hw.c_priority as PRIORITA_ANOM_ASS,
		hw.ticket_id as TICKET_ID,
		hw.ca as CA,
		nvl (
			(select distinct hcf.c_float_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'cs')
			,(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'cs')
		) as CS,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'prod_cod') as CODICE_PRODOTTO,	
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'frequenza') as FREQUENZA,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'segnalazioni') as SEGNALAZIONI,	
		hw.st_chiuso as STATO_CHIUSO,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'so') as SO,
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'platform') as PLATFORM,
		hw.aoid as AOID
		from dmalm_siss_history_workitem hw left join dmalm_siss_history_project hp 
		on hw.FK_PROJECT = hp.c_pk
				left join DMALM_SISS_HISTORY_USER hu on hw.fk_author = hu.c_pk
				    left join dmalm_siss_history_project hp on hw.FK_PROJECT = hp.c_pk
					left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = hu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY='SISS'  
		where hw.c_type = 'anomalia_assistenza'
		and hw.data_caricamento = ?  