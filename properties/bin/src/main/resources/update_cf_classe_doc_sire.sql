update dmalm_sire_history_workitem w set classe_doc = ( 
select to_char(c_string_value) from dmalm_sire_history_cf_workitem c where c_name = 'classe_doc' 
and fk_workitem = w.c_pk and data_caricamento = ?
)