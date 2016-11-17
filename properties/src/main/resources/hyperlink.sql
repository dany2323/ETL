insert into DMALM_HYPERLINK (DMALM_FK_WORKITEM_01, DMALM_WORKITEM_TYPE, RUOLO, URI, DT_CARICAMENTO, ID_REPOSITORY, HYPERLINK_PK)
	select PK, TIPO_WORKITEM, RUOLO, URI, DATA_CARICAMENTO, ID_REPOSITORY, HYPERLINK_SEQ.nextval
	from
		(
		select 
			'SIRE' as ID_REPOSITORY,
			hw.c_type as TIPO_WORKITEM,
			hh.c_role as RUOLO,
			hh.c_uri as URI,
			t.DMALM_PK as PK,
			hh.DATA_CARICAMENTO as DATA_CARICAMENTO
		from dmalm_sire_history_hyperlink hh left join dmalm_sire_history_workitem hw 
		on hh.fk_p_workitem = hw.c_pk join total t on t.stg_pk = hh.fk_p_workitem
		where hh.DATA_CARICAMENTO = ? 
		union all
		select 
			'SISS' as ID_REPOSITORY,
			hw.c_type as TIPO_WORKITEM,
			hh.c_role as RUOLO,
			hh.c_uri as URI,
			t.DMALM_PK as PK,
			hh.DATA_CARICAMENTO as DATA_CARICAMENTO
		from dmalm_siss_history_hyperlink hh left join dmalm_siss_history_workitem hw 
		on hh.fk_p_workitem = hw.c_pk join total t on t.stg_pk = hh.fk_p_workitem
		where hh.DATA_CARICAMENTO = ? 
	)