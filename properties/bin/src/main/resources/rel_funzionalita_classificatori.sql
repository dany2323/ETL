merge into DMALM_FUNZIONALITA_CLASF tg using 
	(  
	select distinct  
		funz.DMALM_FUNZIONALITA_SEQ as fk_funzionalita,  
		clasf.DMALM_CLASSIFICATORI_PK as fk_classificatori  
	from V_DM_ALM_FUNZ_CLASF rel   
		join DMALM_CLASSIFICATORI clasf on rel.ID = clasf.ID_ORESTE  
		join DMALM_CLASSIFICATORI c on c.ID_ORESTE = clasf.ID_ORESTE  
		join DMALM_FUNZIONALITA funz on rel.ID_FUNZIONALITA = funz.DMALM_FUNZIONALITA_PK  
		join DMALM_FUNZIONALITA f on f.DMALM_FUNZIONALITA_PK = funz.DMALM_FUNZIONALITA_PK  
			where f.DT_FINE_VALIDITA =  
				(  
				select max(ff.DT_FINE_VALIDITA) from DMALM_FUNZIONALITA ff  
					where ff.DMALM_FUNZIONALITA_PK = f.DMALM_FUNZIONALITA_PK   
				)  
								and c.DT_FINE_VALIDITA = 
				(  
				select max(cc.DT_FINE_VALIDITA) from DMALM_CLASSIFICATORI cc  
					where cc.ID_ORESTE = c.ID_ORESTE  
				)  
	) rs   
	on (tg.FK_FUNZIONALITA = rs.fk_funzionalita and tg.FK_CLASSIFICATORI = rs.fk_classificatori)  
	when not matched then   
		insert (tg.FK_FUNZIONALITA, tg.FK_CLASSIFICATORI, tg.DT_CARICAMENTO)  
		values(rs.fk_funzionalita, rs.fk_classificatori, ?)  