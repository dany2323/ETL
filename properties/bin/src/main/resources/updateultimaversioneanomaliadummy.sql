update dmalm_anomalia_prodotto_dummy 
set FL_ULTIMA_SITUAZIONE = 1 
where DMALM_ANM_PROD_DUMMY_PK in(
  select dm.DMALM_ANM_PROD_DUMMY_PK  
  from dmalm_anomalia_prodotto_dummy dm
  left join dmalm_anomalia_prodotto ap
  on ap.cd_anomalia = dm.cd_anomalia
  and to_char(ap.dt_storicizzazione, 'yyyymm') = to_char(dm.dt_storicizzazione, 'yyyymm')
  where to_char(dm.dt_storicizzazione, 'yyyymm') = ? 
  and ap.DMALM_ANOMALIA_PRODOTTO_PK is null)
 