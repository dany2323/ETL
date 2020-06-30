update dmalm_sire_history_workitem w set data_inizio_eff = ( 
select c_dateonly_value from dmalm_sire_history_cf_workitem c where c_name = 'data_inizio_eff' 
and fk_workitem = w.c_pk and data_caricamento = ?
)