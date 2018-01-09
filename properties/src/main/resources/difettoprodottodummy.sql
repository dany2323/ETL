
insert into dmalm_difetto_prodotto_dummy
 select DMALM_DIF_PROD_DUMMY_SEQ.nextval, DMALM_STRUTTURA_ORG_FK_01, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03, TEMPO_PK, DMALM_AREA_TEMATICA_FK_05, CD_DIFETTO, 
        DS_DIFETTO, DT_CREAZIONE_DIFETTO, DT_RISOLUZIONE_DIFETTO, DT_CHIUSURA_DIFETTO, DT_CAMBIO_STATO_DIFETTO, DT_MODIFICA_RECORD_DIFETTO, ID_AUTORE_DIFETTO, DS_AUTORE_DIFETTO, 
		MOTIVO_RISOLUZIONE_DIFETTO, SEVERITY, PROVENIENZA_DIFETTO, TEMPO_TOT_RISOLUZIONE_DIFETTO, NR_GIORNI_FESTIVI, 0 AS RANK_STATO_DIFETTO, ?, ID_REPOSITORY, ?, NUMERO_LINEA_DIFETTO, 
		NUMERO_TESTATA_DIFETTO, RANK_STATO_DIFETTO_MESE, STG_PK, ANNULLATO, CAUSA_DIFETTO, NATURA_DIFETTO, DMALM_USER_FK_06, URI_DIFETTO_PRODOTTO, CHANGED, EFFORT_COSTO_SVILUPPO,
		0 AS FL_ULTIMA_SITUAZIONE, DT_ANNULLAMENTO, DATA_DISPONIBILITA, PRIORITY
 from (
  select a.*, nvl(tmp.DMALM_TEMPO_PK, 0) AS TEMPO_PK
  from dmalm_difetto_prodotto a
  left join dmalm_tempo tmp on tmp.DT_OSSERVAZIONE = trunc(?)
  where to_char(a.dt_storicizzazione, 'yyyymm') = ?
  and a.rank_stato_difetto_mese = 1
  and a.cd_difetto in( (
   select distinct ap.cd_difetto
   from dmalm_difetto_prodotto ap
   join DMALM_STATO_WORKITEM sw on sw.DMALM_STATO_WORKITEM_PK = ap.DMALM_STATO_WORKITEM_FK_03 
   where ap.annullato is null
   and ap.rank_stato_difetto_mese = 1
   and sw.CD_STATO <> 'chiuso'
   and to_char(ap.dt_storicizzazione, 'yyyymm') = ?
   union all
   select distinct apd.cd_difetto
   from dmalm_difetto_prodotto_dummy apd
   where to_char(apd.dt_storicizzazione, 'yyyymm') = ?)
   minus (
    select distinct ap.cd_difetto
    from dmalm_difetto_prodotto ap
    where to_char(ap.dt_storicizzazione, 'yyyymm') = ?
    union all
    select distinct apd.cd_difetto
    from dmalm_difetto_prodotto_dummy apd
    where to_char(apd.dt_storicizzazione, 'yyyymm') = ?) ) 
  union all
  select a.*, nvl(tmp.DMALM_TEMPO_PK, 0) AS TEMPO_PK
  from dmalm_difetto_prodotto_dummy a
  left join dmalm_tempo tmp on tmp.DT_OSSERVAZIONE = trunc(?)
  where to_char(a.dt_storicizzazione, 'yyyymm') = ?
  and a.rank_stato_difetto_mese = 1
  and a.cd_difetto in( (
   select distinct ap.cd_difetto
   from dmalm_difetto_prodotto ap
   join DMALM_STATO_WORKITEM sw on sw.DMALM_STATO_WORKITEM_PK = ap.DMALM_STATO_WORKITEM_FK_03 
   where ap.annullato is null
   and ap.rank_stato_difetto_mese = 1
   and sw.CD_STATO <> 'chiuso'
   and to_char(ap.dt_storicizzazione, 'yyyymm') = ?
   union all
   select distinct apd.cd_difetto
   from dmalm_difetto_prodotto_dummy apd
   where to_char(apd.dt_storicizzazione, 'yyyymm') = ?)
   minus (
    select distinct ap.cd_difetto
    from dmalm_difetto_prodotto ap
    where to_char(ap.dt_storicizzazione, 'yyyymm') = ?
    union all
    select distinct apd.cd_difetto
    from dmalm_difetto_prodotto_dummy apd
    where to_char(apd.dt_storicizzazione, 'yyyymm') = ?) ) ) 