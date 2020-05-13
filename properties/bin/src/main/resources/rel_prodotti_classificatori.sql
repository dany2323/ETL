merge into DMALM_PRODOTTO_ARCH_CLASF tg using  
	(  
	select distinct  
		prod.DMALM_PRODOTTO_SEQ as fk_prodotto,  
		clasf.DMALM_CLASSIFICATORI_PK as fk_classificatori  
	from V_DM_ALM_PRODARCH_CLASF rel    
		join DMALM_CLASSIFICATORI clasf on rel.ID = clasf.ID_ORESTE  
    join DMALM_CLASSIFICATORI c on c.ID_ORESTE = clasf.ID_ORESTE  
		join DMALM_PRODOTTO prod on rel.ID_PRODOTTO = prod.DMALM_PRODOTTO_PK  
    join dmalm_prodotto p on p.DMALM_PRODOTTO_PK = prod.DMALM_PRODOTTO_PK  
				where p.DT_FINE_VALIDITA =   
				(  
				select max(pp.DT_FINE_VALIDITA) from dmalm_prodotto pp   
					where pp.DMALM_PRODOTTO_PK = p.DMALM_PRODOTTO_PK  
				)
        and c.DT_FINE_VALIDITA =   
				(  
				select max(cc.DT_FINE_VALIDITA) from DMALM_CLASSIFICATORI cc  
					where cc.ID_ORESTE = c.ID_ORESTE  
				) 
	) rs    
	on (tg.FK_PRODOTTO = rs.fk_prodotto and tg.FK_CLASSIFICATORI = rs.fk_classificatori)  
	when not matched then 
		insert (tg.FK_PRODOTTO, tg.FK_CLASSIFICATORI, tg.dt_caricamento)  
		values(rs.fk_prodotto, rs.fk_classificatori, ?)  