SELECT * 
FROM DMALM_EL_PRODOTTI_ARCH p1 
WHERE ID_PRODOTTO NOT IN (SELECT ID_PRODOTTO FROM DMALM_STG_EL_PRODOTTI_ARCH WHERE DT_CARICAMENTO = ?) 
AND DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_PRODOTTI_ARCH p2 WHERE p1.ID_PRODOTTO=p2.ID_PRODOTTO) 
AND DMALM_PRODOTTO_PK>0 
AND p1.NOME NOT LIKE '#ANNULLATO FISICAMENTE##%' 