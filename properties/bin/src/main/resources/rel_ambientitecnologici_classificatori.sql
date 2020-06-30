merge into DMALM_AMBTECN_CLASF tg USING  
(SELECT distinct  
  (SELECT atech.DMALM_AMBIENTE_TECNOLOGICO_SEQ  
  FROM DMALM_AMBIENTE_TECNOLOGICO atech  
  WHERE atech.DMALM_AMBIENTE_TECNOLOGICO_PK = at.DMALM_AMBIENTE_TECNOLOGICO_PK  
  and at.dmalm_prodotto_fk_01 = atech.dmalm_prodotto_fk_01  
  and at.dmalm_modulo_fk_02 = atech.dmalm_modulo_fk_02 
  and at.sigla_modulo = atech.sigla_modulo 
  and at.sigla_prodotto = atech.sigla_prodotto 
  AND atech.DT_FINE_VALIDITA                =  
    (SELECT MAX(att.DT_FINE_VALIDITA)  
    FROM DMALM_AMBIENTE_TECNOLOGICO att  
    WHERE att.DMALM_AMBIENTE_TECNOLOGICO_PK = atech.DMALM_AMBIENTE_TECNOLOGICO_PK  
    and att.dmalm_prodotto_fk_01 = atech.dmalm_prodotto_fk_01  
    and att.dmalm_modulo_fk_02 = atech.dmalm_modulo_fk_02 
	and at.sigla_modulo = atech.sigla_modulo 
	and at.sigla_prodotto = atech.sigla_prodotto 	
    )  
  ) AS fk_amb_tech,  
  (SELECT c.DMALM_CLASSIFICATORI_PK  
  FROM DMALM_CLASSIFICATORI c  
  WHERE c.ID_ORESTE      = clasf.ID_ORESTE  
  AND c.DT_FINE_VALIDITA =  
    (SELECT MAX(cc.DT_FINE_VALIDITA)  
    FROM DMALM_CLASSIFICATORI cc  
    WHERE cc.ID_ORESTE = c.ID_ORESTE  
    )  
  ) AS fk_classificatori  
FROM V_DM_ALM_AMBTECN_CLASF rel  
JOIN DMALM_CLASSIFICATORI clasf  
ON rel.ID = clasf.ID_ORESTE  
JOIN DMALM_AMBIENTE_TECNOLOGICO at  
ON rel.ID_AMB_TECN                  = at.DMALM_AMBIENTE_TECNOLOGICO_PK  
) RS ON (tg.FK_AMBIENTE_TECNOLOGICO = rs.fk_amb_tech AND tg.FK_CLASSIFICATORI = rs.fk_classificatori)  
WHEN NOT MATCHED THEN  
  INSERT  
    (  
      TG.FK_AMBIENTE_TECNOLOGICO,  
      TG.FK_CLASSIFICATORI,  
      tg.dt_caricamento  
    )  
    VALUES  
    (  
      rs.fk_amb_tech,  
      rs.fk_classificatori,  
      ?  
    )
