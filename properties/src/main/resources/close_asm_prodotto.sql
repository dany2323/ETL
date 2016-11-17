update DMALM_ASM_PRODOTTO pr 
set pr.dt_fine_validita = (select min(dt_inizio_validita - INTERVAL '1' SECOND) from DMALM_ASM_PRODOTTO mi
  where mi.DMALM_ASM_PK > 0
  and mi.dt_fine_validita = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss')
  and mi.DMALM_PRODOTTO_SEQ = pr.DMALM_PRODOTTO_SEQ) 
where pr.DMALM_PRODOTTO_SEQ > 0 
and pr.DMALM_ASM_PK = 0 
and pr.dt_fine_validita = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss') 
and exists (select ex.*
  from DMALM_ASM_PRODOTTO ex
  where ex.DMALM_ASM_PK > 0
  and ex.dt_fine_validita = TO_DATE('31/12/9999 00:00:00', 'dd/mm/yyyy hh24:mi:ss')
  and ex.DMALM_PRODOTTO_SEQ = pr.DMALM_PRODOTTO_SEQ)