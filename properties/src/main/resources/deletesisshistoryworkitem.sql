delete from DMALM_SISS_HISTORY_WORKITEM 
where (data_caricamento <= ? 
and siss_history_workitem_pk not in(
  select hw.siss_history_workitem_pk 
  from DMALM_SISS_HISTORY_WORKITEM hw
  join (select c_type, max(c_rev) as max_c_rev
    from DMALM_SISS_HISTORY_WORKITEM
    group by c_type) mx 
  on mx.c_type = hw.c_type and mx.max_c_rev = hw.c_rev) 
and siss_history_workitem_pk not in(
    select st.siss_history_workitem_pk 
    from DMALM_SISS_HISTORY_WORKITEM st 
    where st.C_STATUS in('chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito')) 
and not c_type = 'defect' 
) 
or data_caricamento = ? 
