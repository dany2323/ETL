SELECT 
	'SIRE' as IDENTIFICATIVO_REPOSITORY,
	hw.c_uri as URI_WI,
	hw.SIRE_HISTORY_WORKITEM_PK as DMALM_ANOMALIA_PRODOTTO_PK,
	hw.C_PK as STG_PK,
	nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
	hw.C_ID as IDENTIFICATIVO_ANOMALIA,  
	NVL(hw.C_RESOLVEDON, {ts '9999-12-31 00:00:00'}) as DATA_RISOLUZIONE_ANOMALIA,
	CASE
	   WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON )
	   ELSE 0
	END as GIORNI_FESTIVI,
	CASE
	   WHEN hw.C_RESOLVEDON is null THEN 0
	   WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1
	   ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
	END as TEMPO_TOTALE_RISOLUZIONE,
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
	hw.c_CREATED as DATA_CREAZIONE_ANOMALIA,
	hw.C_UPDATED as DATA_MODIFICA_RECORD,
	CASE
	   WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
	   (	
	   select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
	   ),
	   {ts '1900-01-01 00:00:00'}
	   ))
	   else {ts '9999-12-31 00:00:00'}
	END as DATA_CHIUSURA_ANOMALIA,
	hw.COSTO_SVILUPPO as EFFORT_COSTO_SVILUPPO,
	(select distinct to_char(cf.c_string_value) from dmalm_sire_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'contestazione') as CONTESTAZIONE,
	(select distinct to_char(cf.c_string_value) from dmalm_sire_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'note') as NOTE_CONTESTAZIONE, 
	cu.C_ID as USERID_AUTORE_ANOMALIA,
	cu.C_NAME as NOME_AUTORE_ANOMALIA,
	hw.C_RESOLUTION as MOTIVO_RISOLUZIONE,
	hw.C_SEVERITY as SEVERITY_ANOMALIA,
	hw.C_TITLE as DESCRIZIONE_ANOMALIA,
	hw.cod_intervento as numero_testata_RDI,
	hw.ticket_id as TICKET_SIEBEL_ANOMALIA,
	hw.ca as EFFORT_ANALISI_ANOMALIA,
	hw.st_chiuso as DATA_CHIUSURA_TCK_ANOMALIA,
	hw.data_disp as DATA_DISPONIBILITA,
	hw.C_PRIORITY as PRIORITY,
	(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
	FROM 
	DMALM_SIRE_HISTORY_WORKITEM hw 
			  left join DMALM_SIRE_HISTORY_USER cu on  hw.FK_AUTHOR = cu.C_PK
			  left join dmalm_sire_history_project hp on hw.FK_PROJECT = hp.c_pk
				left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
						left join DMALM_PROJECT p 
						on p.ID_PROJECT = hp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE' 
							left join DMALM_USER u
							on u.ID_USER = cu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
	WHERE  hw.c_type = 'anomalia' 
	and hw.data_caricamento = ? 
	union ALL  	
	SELECT 
	'SISS' as IDENTIFICATIVO_REPOSITORY,
	hw.c_uri as URI_WI,
	hw.SISS_HISTORY_WORKITEM_PK as DMALM_ANOMALIA_PRODOTTO_PK,
	hw.C_PK as STG_PK,
	nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= hp.c_id AND hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
	hw.C_ID as IDENTIFICATIVO_ANOMALIA,
	NVL(hw.C_RESOLVEDON, {ts '9999-12-31 00:00:00'}) as DATA_RISOLUZIONE_ANOMALIA,
	CASE
	   WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON )
	   ELSE 0
	END as GIORNI_FESTIVI,
	CASE
	   WHEN hw.C_RESOLVEDON is null THEN 0
	   WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1
	   ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
	END as TEMPO_TOTALE_RISOLUZIONE,
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
	hw.c_CREATED as DATA_CREAZIONE_ANOMALIA,
	hw.C_UPDATED as DATA_MODIFICA_RECORD,
	CASE
	   WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
	   (	
	   select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED
	   ),
	   {ts '1900-01-01 00:00:00'}
	   ))
	   else {ts '9999-12-31 00:00:00'}
	END as DATA_CHIUSURA_ANOMALIA,
	hw.COSTO_SVILUPPO as EFFORT_COSTO_SVILUPPO,
	(select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'contestazione') as CONTESTAZIONE,
	(select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'note') as NOTE_CONTESTAZIONE, 
	cu.C_ID as USERID_AUTORE_ANOMALIA,
	cu.C_NAME as NOME_AUTORE_ANOMALIA,
	hw.C_RESOLUTION as MOTIVO_RISOLUZIONE,
	hw.C_SEVERITY as SEVERITY_ANOMALIA,
	hw.C_TITLE as DESCRIZIONE_ANOMALIA,
	hw.cod_intervento as numero_testata_RDI,
	hw.ticket_id as TICKET_SIEBEL_ANOMALIA,
	hw.ca as EFFORT_ANALISI_ANOMALIA,
	hw.st_chiuso as DATA_CHIUSURA_TCK_ANOMALIA,
	hw.data_disp as DATA_DISPONIBILITA,
	hw.C_PRIORITY as PRIORITY,
	(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
	FROM 
	DMALM_SISS_HISTORY_WORKITEM hw 
		left join DMALM_SISS_HISTORY_USER cu on  hw.FK_AUTHOR = cu.C_PK
		left join dmalm_siss_history_project hp on hw.FK_PROJECT = hp.c_pk
				left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and hp.template = st.template
						left join DMALM_PROJECT p 
						on p.ID_PROJECT = hp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS' 
							left join DMALM_USER u
							on u.ID_USER = cu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
	WHERE hw.c_type = 'anomalia'
	and hw.data_caricamento = ?
