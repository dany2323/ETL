update dmalm_siss_history_workitem w set st_chiuso = ( 
select c_date_value from dmalm_siss_history_cf_workitem c where c_name = 'st_chiuso' 
and fk_workitem = w.c_pk and data_caricamento = ?
)