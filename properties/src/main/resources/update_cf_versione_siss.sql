update dmalm_siss_history_workitem w set versione = ( 
select to_char(c_string_value) from dmalm_siss_history_cf_workitem c where c_name = 'versione' 
and fk_workitem = w.c_pk and data_caricamento = ?
)