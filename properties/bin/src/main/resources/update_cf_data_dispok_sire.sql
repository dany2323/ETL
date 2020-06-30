update dmalm_sire_history_workitem w set data_disponibilita_effettiva = ( 
select c_dateonly_value from dmalm_sire_history_cf_workitem c where c_name = 'data_dispok' 
and fk_workitem = w.c_pk and data_caricamento = ?
)