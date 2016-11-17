update dmalm_sire_history_workitem w set ticket_id = ( 
select to_char(c_string_value) from dmalm_sire_history_cf_workitem c where c_name = 'ticketid' 
and fk_workitem = w.c_pk and data_caricamento = ?
)