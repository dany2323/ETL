update dmalm_sire_history_workitem w set costo_sviluppo = ( 
select c_float_value from dmalm_sire_history_cf_workitem c where c_name = 'costosviluppo' 
and fk_workitem = w.c_pk and data_caricamento = ?
)