update dmalm_siss_history_workitem w set ca = ( 
select nvl(to_number(c_string_value, '9999999999.9999'), c_float_value) from dmalm_siss_history_cf_workitem c where c_name = 'ca' 
and fk_workitem = w.c_pk and data_caricamento = ?
)