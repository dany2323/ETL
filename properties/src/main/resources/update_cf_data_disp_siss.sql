update dmalm_siss_history_workitem w set data_disp = ( 
select c_dateonly_value from dmalm_siss_history_cf_workitem c where c_name = 'data_disp' 
and fk_workitem = w.c_pk and data_caricamento = ?
)