delete from dmalm_difetto_prodotto_dummy 
where DMALM_DIF_PROD_DUMMY_PK in(
  select dm.DMALM_DIF_PROD_DUMMY_PK
  from dmalm_difetto_prodotto_dummy dm
  join dmalm_difetto_prodotto dp
  on dp.cd_difetto = dm.cd_difetto
  and to_char(dp.dt_storicizzazione, 'yyyymm') = to_char(dm.dt_storicizzazione, 'yyyymm'));  
delete from dmalm_anomalia_prodotto_dummy 
where DMALM_ANM_PROD_DUMMY_PK in(
  select dm.DMALM_ANM_PROD_DUMMY_PK
  from dmalm_anomalia_prodotto_dummy dm
  join dmalm_anomalia_prodotto ap
  on ap.cd_anomalia = dm.cd_anomalia
  and to_char(ap.dt_storicizzazione, 'yyyymm') = to_char(dm.dt_storicizzazione, 'yyyymm'));
