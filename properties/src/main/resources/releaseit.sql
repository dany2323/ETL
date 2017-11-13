SELECT 'SIRE' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.sire_history_workitem_pk as DMALM_RELEASEIT_PK,
		hw.c_pk as STG_RELEASEIT_PK, 
		hw.c_id as CD_RELEASEIT, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RELEASEIT,
		hu.c_id as ID_AUTORE_RELEASEIT, 
		hu.c_name as NOME_AUTORE_RELEASEIT,
		hw.c_title as TITOLO_RELEASEIT,
		nvl(
			hw.c_resolvedon
			, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_RELIT,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RELIT,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		hw.c_duedate as DATA_SCADENZA_RELEASEIT,
		CASE
        	WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON )
            ELSE 0
        END as GIORNI_FESTIVI,
        CASE
           WHEN hw.C_RESOLVEDON is null THEN 0
           WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1
           ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
        END as DURATA_EFF_RELEASE_IT,
		(select distinct hcf.c_dateonly_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_dts') as DATA_RILASCIO_AD_ESERCIZIO,
		(select distinct hcf.c_dateonly_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_dispok') as DATA_DISPONIBILITA_EFF,
		(select distinct hcf.c_dateonly_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_it_start') as DATA_INIZIO_IT,
		(select distinct hcf.c_dateonly_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_it_end') as DATA_FINE_IT
		(select distinct hcf.c_string_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'severity') as SEVERITY
		(select distinct hcf.c_string_value from dmalm_sire_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'priority') as PRIORITY
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
		where hw.c_type = 'release_it'
		and hw.data_caricamento = ? 
UNION   ALL  
SELECT 'SISS' as ID_REPOSITORY,
		hw.c_uri as URI_WI, 
		hw.siss_history_workitem_pk as DMALM_RELEASEIT_PK,
		hw.c_pk as STG_RELEASEIT_PK, 
		hw.c_id as CD_RELEASEIT, 
		hw.c_created as DATA_INSERIMENTO_RECORD, 
		nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
		nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06,
		hp.c_name as NOME_PROJECT, 
		nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03,
		hw.c_updated as DATA_MODIFICA_RELEASEIT,
		hu.c_id as ID_AUTORE_RELEASEIT, 
		hu.c_name as NOME_AUTORE_RELEASEIT,
		hw.c_title as TITOLO_RELEASEIT,
		nvl(
		hw.c_resolvedon
		, {ts '9999-12-31 00:00:00'}
		) as DATA_RISOLUZIONE_RELIT,
		hw.c_resolution as MOTIVO_RISOLUZIONE_RELIT,
		TO_CHAR(hw.c_description) as DESCRIPTION,
		hw.c_duedate as DATA_SCADENZA_RELEASEIT,
		CASE
        WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON )
           ELSE 0
        END as GIORNI_FESTIVI,
        CASE
         WHEN hw.C_RESOLVEDON is null THEN 0
         WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1
         ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),'yyyy-mm-dd'))
        END as DURATA_EFF_RELEASE_IT,
		(select distinct hcf.c_dateonly_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_dts') as DATA_RILASCIO_AD_ESERCIZIO,
		(select distinct hcf.c_dateonly_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_dispok') as DATA_DISPONIBILITA_EFF,
		(select distinct hcf.c_dateonly_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_it_start') as DATA_INIZIO_IT,
		(select distinct hcf.c_dateonly_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'data_it_end') as DATA_FINE_IT
		(select distinct hcf.c_string_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'severity') as SEVERITY
		(select distinct hcf.c_string_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'priority') as PRIORITY
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
		where hw.c_type = 'release_it'
		and hw.data_caricamento = ?   