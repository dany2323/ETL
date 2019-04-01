SELECT  
		'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_RICH_GESTIONE_PK,
		hw.c_pk as STG_RICH_GESTIONE_PK, 
		hw.c_id as CD_RICH_GESTIONE, 
		hw.c_created as DATA_INSERIMENTO_RECORD_RG, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RICH_GESTIONE,
		hu.c_id as ID_AUTORE_RICH_GESTIONE, 
		hu.c_name as NOME_AUTORE_RICH_GESTIONE,
		hw.c_duedate as DATA_SCADENZA_RICH_GESTIONE, 		
		hw.c_title as TITOLO_RICH_GESTIONE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_RICH_GESTIONE,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RICH_GEST, 
		to_char(hw.c_description) as DESCRIZIONE_RICH_GESTIONE, 
		(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'categoria') as CF_categoria,
		hw.TICKET_ID as CF_ticketid,
		hw.DATA_DISP as CF_data_disp,
		CASE
           WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
           (	
           select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
           ),
           {ts '1900-01-01 00:00:00'}
           ))
           else {ts '9999-12-31 00:00:00'}
		END as DATA_CHIUSURA_RICH_GESTIONE,
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
		where hw.c_type = 'richiesta_gestione'
		and hw.data_caricamento = ?  
UNION    ALL  
SELECT  
		'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_RICH_GESTIONE_PK,
		hw.c_pk as STG_RICH_GESTIONE_PK, 
		hw.c_id as CD_RICH_GESTIONE, 
		hw.c_created as DATA_INSERIMENTO_RECORD_RG, 
		nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RICH_GESTIONE,
		hu.c_id as ID_AUTORE_RICH_GESTIONE, 
		hu.c_name as NOME_AUTORE_RICH_GESTIONE,
		hw.c_duedate as DATA_SCADENZA_RICH_GESTIONE, 		
		hw.c_title as TITOLO_RICH_GESTIONE,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_RICH_GESTIONE,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RICH_GEST, 
		to_char(hw.c_description) as DESCRIZIONE_RICH_GESTIONE, 
		(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'categoria') as CF_categoria,
		hw.TICKET_ID as CF_ticketid,
		hw.DATA_DISP as CF_data_disp,
		CASE
           WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
           (	
           select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
           ),
           {ts '1900-01-01 00:00:00'}
           ))
           else {ts '9999-12-31 00:00:00'}
		END as DATA_CHIUSURA_RICH_GESTIONE,
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
		where hw.c_type = 'richiesta_gestione'
		and hw.data_caricamento = ?
