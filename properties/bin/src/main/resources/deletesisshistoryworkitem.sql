DELETE FROM DMALM_SISS_HISTORY_WORKITEM 
WHERE (data_caricamento <= ? 
AND C_STATUS not in('chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito')  
AND NOT c_type = 'defect' 
AND siss_history_workitem_pk NOT in (!))  
or data_caricamento = ? 