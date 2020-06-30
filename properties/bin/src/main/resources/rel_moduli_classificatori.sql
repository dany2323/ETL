merge into DMALM_MODULO_CLASF tg using 
	(  
	select distinct  
		md.DMALM_MODULO_SEQ as fk_modulo,  
		clasf.DMALM_CLASSIFICATORI_PK as fk_classificatori  
	from V_DM_ALM_MODULI_CLASF rel    
		join DMALM_CLASSIFICATORI clasf on rel.ID = clasf.ID_ORESTE 
			join DMALM_CLASSIFICATORI c on c.ID_ORESTE = clasf.ID_ORESTE    
		join DMALM_MODULO md on rel.ID_modulo = md.DMALM_MODULO_PK  
			join DMALM_MODULO M on M.DMALM_MODULO_PK = md.DMALM_MODULO_PK  
			where M.DT_FINE_VALIDITA =   
				(  
				select max(MM.DT_FINE_VALIDITA) from DMALM_MODULO MM  
					where MM.DMALM_MODULO_PK = M.DMALM_MODULO_PK   
				) 
        and c.DT_FINE_VALIDITA =   
				(  
				select max(cc.DT_FINE_VALIDITA) from DMALM_CLASSIFICATORI cc  
					where cc.ID_ORESTE = c.ID_ORESTE  
				)
	) rs 
	on (tg.FK_MODULO = rs.fk_modulo and tg.FK_CLASSIFICATORI = rs.fk_classificatori)  
	when not matched then  
		insert (tg.FK_MODULO, tg.FK_CLASSIFICATORI, tg.DT_CARICAMENTO)  
		values(rs.fk_modulo, rs.fk_classificatori, ?)  