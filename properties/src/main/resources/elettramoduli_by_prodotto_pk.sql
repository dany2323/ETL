SELECT * 
FROM DMALM_EL_MODULI m 
WHERE DMALM_PRODOTTO_FK_02 = ? 
AND DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_MODULI m2 WHERE m.ID_MODULO=m2.ID_MODULO) 
AND NOME NOT LIKE '#ANNULLATO FISICAMENTE##%' 