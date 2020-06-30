update dmalm_sire_history_workitem w set fr = ( 
select c_boolean_value from dmalm_sire_history_cf_workitem c where c_name = 'fr' 
and fk_workitem = w.c_pk and data_caricamento = ?
)