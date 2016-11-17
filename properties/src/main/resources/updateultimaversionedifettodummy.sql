update dmalm_difetto_prodotto_dummy 
set FL_ULTIMA_SITUAZIONE = 1 
where DMALM_DIF_PROD_DUMMY_PK in(
  select dm.DMALM_DIF_PROD_DUMMY_PK  
  from dmalm_difetto_prodotto_dummy dm
  left join dmalm_difetto_prodotto dp
  on dp.cd_difetto = dm.cd_difetto
  and to_char(dp.dt_storicizzazione, 'yyyymm') = to_char(dm.dt_storicizzazione, 'yyyymm')
  where to_char(dm.dt_storicizzazione, 'yyyymm') = ? 
  and dp.DMALM_DIFETTO_PRODOTTO_PK is null) 
 