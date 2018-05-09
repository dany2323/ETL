insert into DMALM_ATTACHMENT (CD_ATTACHMENT, STATO_CANCELLATO, NOME_AUTORE, NOME_FILE, DIMENSIONE_FILE, ID_PROJECT, 
							TITOLO, DT_MODIFICA_ATTACHMENT, URL, DMALM_FK_WORKITEM_01, DMALM_WORKITEM_TYPE, DT_CARICAMENTO, ID_REPOSITORY, ATTACHMENT_PK)
		select ID_ATTACHMENT, STATO_CANCELLATO, NOME_AUTORE_ATTACHMENT, NOME_FILE, DIMENSIONE_FILE, ID_PROJECT,
				TITOLO, DATA_MODIFICA, URL_ATTACHMENT, PK, TIPO_WORKITEM, DT_CARICAMENTO, ID_REPOSITORY, ATTACHMENT_SEQ.nextval
		from 
		(
			select 
				'SIRE' as ID_REPOSITORY,
				ha.c_id as ID_ATTACHMENT,
				ha.c_deleted as STATO_CANCELLATO,
				hu.c_name as NOME_AUTORE_ATTACHMENT,
				ha.c_filename as NOME_FILE,
				ha.c_length as DIMENSIONE_FILE,
                nvl((SELECT MAX(hp.c_id) FROM dmalm_sire_history_project hp WHERE ha.fk_project = hp.c_pk) , 0) as ID_PROJECT, 
				ha.c_title as TITOLO,
				ha.c_updated as DATA_MODIFICA,
				ha.c_url as URL_ATTACHMENT,
				hw.c_type as TIPO_WORKITEM,
				ha.fk_workitem as FK_WORKITEM,
				t.DMALM_PK as PK,
				ha.DATA_CARICAMENTO as DT_CARICAMENTO 
				from dmalm_sire_history_attachment ha left join dmalm_sire_history_workitem hw 
					on ha.fk_workitem = hw.c_pk
						join dmalm_sire_history_user hu on ha.fk_author = hu.c_pk
								join total t on t.stg_pk = ha.fk_workitem
			where ha.data_caricamento = ?
			union all
			select 
				'SISS' as ID_REPOSITORY,
				ha.c_id as ID_ATTACHMENT,
				ha.c_deleted as STATO_CANCELLATO,
				hu.c_name as NOME_AUTORE_ATTACHMENT,
				ha.c_filename as NOME_FILE,
				ha.c_length as DIMENSIONE_FILE,
                nvl((SELECT MAX(hp.c_id) FROM dmalm_siss_history_project hp WHERE ha.fk_project = hp.c_pk) , 0) as ID_PROJECT, 
				ha.c_title as TITOLO,
				ha.c_updated as DATA_MODIFICA,
				ha.c_url as URL_ATTACHMENT,
				hw.c_type as TIPO_WORKITEM, 
				ha.fk_workitem as FK_WORKITEM,
				t.DMALM_PK as PK,
				ha.DATA_CARICAMENTO as DT_CARICAMENTO 
				from dmalm_siss_history_attachment ha left join dmalm_siss_history_workitem hw 
					on ha.fk_workitem = hw.c_pk
						join dmalm_siss_history_user hu on ha.fk_author = hu.c_pk
								join total t on t.stg_pk = ha.fk_workitem
			where ha.DATA_CARICAMENTO = ? 
		)