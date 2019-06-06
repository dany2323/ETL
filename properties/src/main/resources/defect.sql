	SELECT 
	'SISS' as IDENTIFICATIVO_REPOSITORY,
	cw.c_uri as URI_WI,
	cw.SISS_HISTORY_WORKITEM_PK as DMALM_DIFETTO_PRODOTTO_PK,
	cw.C_PK as STG_PK,
	nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= cp.c_id AND cw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS') , 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
	cw.C_ID as IDENTIFICATIVO_DIFETTO,  
	NVL(cw.C_RESOLVEDON, {ts '9999-12-31 00:00:00'}) as DATA_RISOLUZIONE_DIFETTO,
	CASE
				   WHEN NOT cw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  cw.c_created and cw.C_RESOLVEDON )
				   ELSE 0
	END as GIORNI_FESTIVI,
	CASE
				   WHEN cw.C_RESOLVEDON is null THEN 0
				   WHEN to_char(cw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(cw.C_CREATED, 'yyyy-mm-dd') THEN 1
				   ELSE TO_NUMBER(to_date(to_char(cw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(cw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
	END as TEMPO_TOTALE_RISOLUZIONE,
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
	cw.c_CREATED as DATA_CREAZIONE_DIFETTO,
	cw.C_UPDATED as DATA_MODIFICA_RECORD,
	CASE
				   WHEN cw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = cw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
				   (	
				   select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = cw.C_ID and hh.C_STATUS != cw.C_STATUS and hh.C_UPDATED < cw.C_UPDATED
				   ),
				   {ts '1900-01-01 00:00:00'}
				   ))
				   WHEN cw.C_STATUS = 'chiuso_falso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = cw.c_uri and shw.C_STATUS = 'chiuso_falso' and shw.C_UPDATED > nvl(
				   (	
				   select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = cw.C_ID and hh.C_STATUS != cw.C_STATUS and hh.C_UPDATED < cw.C_UPDATED
				   ),
				   {ts '1900-01-01 00:00:00'}
				   ))
				   else {ts '9999-12-31 00:00:00'}
	END as DATA_CHIUSURA_DIFETTO,
	cu.C_ID as USERID_AUTORE_DIFETTO,
	cu.C_NAME as NOME_AUTORE_DIFETTO,
	cw.C_RESOLUTION as MOTIVO_RISOLUZIONE,
	cw.C_SEVERITY as SEVERITY,
	cw.C_TITLE as DESCRIZIONE_DIFETTO,
	cw.cod_intervento as NUMERO_TESTATA_RDI,
	cw.cod_intervento as NUMERO_LINEA_RDI,
	cw.COSTO_SVILUPPO as EFFORT_COSTO_SVILUPPO,
	(select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'causa') as CAUSA,
	(select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'natura') as NATURA,
	cw.data_disp as DATA_DISPONIBILITA,
	(select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'role') as PROVENIENZA_DIFETTO,
	cw.C_PRIORITY as PRIORITY,
	(select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = cw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = cw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
	FROM 
					DMALM_SISS_HISTORY_WORKITEM cw 
									left join DMALM_SISS_HISTORY_USER cu on  cw.FK_AUTHOR = cu.C_PK                              
					left join DMALM_SISS_HISTORY_PROJECT cp on cw.FK_PROJECT = cp.C_PK
						left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = cw.c_type and cw.c_status = st.CD_STATO and cp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = cu.c_id and cw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS'
	WHERE   cw.c_type = 'defect'
					and cw.data_caricamento = ?  
	union ALL  
	SELECT 
	'SIRE' as IDENTIFICATIVO_REPOSITORY,
	cw.c_uri as URI_WI,
	cw.SIRE_HISTORY_WORKITEM_PK as DMALM_DIFETTO_PRODOTTO_PK,
	cw.C_PK as STG_PK,
	nvl((SELECT MAX(DMALM_PROJECT_PK) FROM DMALM_PROJECT p WHERE p.ID_PROJECT= cp.c_id AND cw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE') , 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
	cw.C_ID as IDENTIFICATIVO_DIFETTO,  
	NVL(cw.C_RESOLVEDON, {ts '9999-12-31 00:00:00'}) as DATA_RISOLUZIONE_DIFETTO,
	CASE
				   WHEN NOT cw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  cw.c_created and cw.C_RESOLVEDON )
				   ELSE 0
	END as GIORNI_FESTIVI,
	CASE
				   WHEN cw.C_RESOLVEDON is null THEN 0
				   WHEN to_char(cw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(cw.C_CREATED, 'yyyy-mm-dd') THEN 1
				   ELSE TO_NUMBER(to_date(to_char(cw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(cw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
	END as TEMPO_TOTALE_RISOLUZIONE,
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
	cw.c_CREATED as DATA_CREAZIONE_DIFETTO,
	cw.C_UPDATED as DATA_MODIFICA_RECORD,
	CASE
				   WHEN cw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM shw where shw.c_uri = cw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl(
				   (	
				   select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = cw.C_ID and hh.C_STATUS != cw.C_STATUS and hh.C_UPDATED < cw.C_UPDATED
				   ),
				   {ts '1900-01-01 00:00:00'}
				   ))
				   WHEN cw.C_STATUS = 'chiuso_falso' THEN (select min(shw.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM shw where shw.c_uri = cw.c_uri and shw.C_STATUS = 'chiuso_falso' and shw.C_UPDATED > nvl(
				   (	
				   select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = cw.C_ID and hh.C_STATUS != cw.C_STATUS and hh.C_UPDATED < cw.C_UPDATED
				   ),
				   {ts '1900-01-01 00:00:00'}
				   ))
				   else {ts '9999-12-31 00:00:00'}
	END as DATA_CHIUSURA_DIFETTO,
	cu.C_ID as USERID_AUTORE_DIFETTO,
	cu.C_NAME as NOME_AUTORE_DIFETTO,
	cw.C_RESOLUTION as MOTIVO_RISOLUZIONE,
	cw.C_SEVERITY as SEVERITY,
	cw.C_TITLE as DESCRIZIONE_DIFETTO,
	cw.cod_intervento as NUMERO_TESTATA_RDI,
	cw.cod_intervento as NUMERO_LINEA_RDI,
	cw.COSTO_SVILUPPO as EFFORT_COSTO_SVILUPPO,
	(select distinct to_char(cf.c_string_value) from dmalm_sire_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'causa') as CAUSA,
	(select distinct to_char(cf.c_string_value) from dmalm_sire_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'natura') as NATURA,
	cw.data_disp as DATA_DISPONIBILITA,
	(select distinct to_char(cf.c_string_value) from dmalm_sire_history_cf_workitem cf where cf.fk_workitem = cw.c_pk and cf.c_name = 'role') as PROVENIENZA_DIFETTO,
	cw.C_PRIORITY as PRIORITY,
	(select distinct to_char(hcf.c_string_value) from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = cw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
		(select distinct hcf.c_date_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = cw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
	FROM 
					DMALM_SIRE_HISTORY_WORKITEM cw 
									left join DMALM_SIRE_HISTORY_USER cu on  cw.FK_AUTHOR = cu.C_PK
	left join DMALM_SIRE_HISTORY_PROJECT cp on  cw.FK_PROJECT = cp.C_PK
		left join dmalm_stato_workitem st
					on st.ORIGINE_STATO = cw.c_type and cw.c_status = st.CD_STATO and cp.template = st.template
							left join DMALM_USER u
							on u.ID_USER = cu.c_id and cw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE'
	WHERE cw.c_type = 'defect'
					and cw.data_caricamento = ? 
				   