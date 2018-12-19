insert into dmalm_anomalia_prodotto_dummy
 select DMALM_ANM_PROD_DUMMY_SEQ.nextval, DMALM_STRUTTURA_ORG_FK_01, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03, TEMPO_PK, DMALM_AREA_TEMATICA_FK_05, CD_ANOMALIA, 
        DT_CREAZIONE_ANOMALIA, DT_RISOLUZIONE_ANOMALIA, DT_MODIFICA_RECORD_ANOMALIA, DT_CHIUSURA_ANOMALIA, DT_CAMBIO_STATO_ANOMALIA, DS_ANOMALIA, MOTIVO_RISOLUZIONE_ANOMALIA, 
        ID_AUTORE_ANOMALIA, DS_AUTORE_ANOMALIA, NUMERO_TESTATA_ANOMALIA, NUMERO_LINEA_ANOMALIA, SEVERITY, ID_ANOMALIA_ASSISTENZA, TICKET_SIEBEL_ANOMALIA_ASS, DT_APERTURA_TICKET, 
        DT_CHIUSURA_TICKET, EFFORT_ANALISI, EFFORT_COSTO_SVILUPPO, TEMPO_TOT_RISOLUZIONE_ANOMALIA, NR_GIORNI_FESTIVI, 0 AS RANK_STATO_ANOMALIA, ?, ID_REPOSITORY, ?, 
        RANK_STATO_ANOMALIA_MESE, STG_PK, ANNULLATO, DESCRIZIONE_ANOMALIA, DMALM_USER_FK_06, URI_ANOMALIA_PRODOTTO, CHANGED, 0 AS FL_ULTIMA_SITUAZIONE, DT_ANNULLAMENTO, CONTESTAZIONE, NOTE_CONTESTAZIONE, DATA_DISPONIBILITA, PRIORITY
 from (
  select a.*, nvl(tmp.DMALM_TEMPO_PK, 0) AS TEMPO_PK
  from dmalm_anomalia_prodotto a
  left join dmalm_tempo tmp on tmp.DT_OSSERVAZIONE = trunc(?)
  where to_char(a.dt_storicizzazione, 'yyyymm') = ?
  and a.rank_stato_anomalia_mese = 1
  and a.cd_anomalia in( (
   select distinct ap.cd_anomalia
   from dmalm_anomalia_prodotto ap
   join DMALM_STATO_WORKITEM sw on sw.DMALM_STATO_WORKITEM_PK = ap.DMALM_STATO_WORKITEM_FK_03 
   where ap.annullato is null
   and ap.rank_stato_anomalia_mese = 1
   and (sw.CD_STATO <> 'chiuso' or sw.CD_STATO <> 'chiuso_falso')
   and to_char(ap.dt_storicizzazione, 'yyyymm') = ?
   union all
   select distinct apd.cd_anomalia
   from dmalm_anomalia_prodotto_dummy apd
   where to_char(apd.dt_storicizzazione, 'yyyymm') = ?)
   minus (
    select distinct ap.cd_anomalia
    from dmalm_anomalia_prodotto ap
    where to_char(ap.dt_storicizzazione, 'yyyymm') = ?
    union all
    select distinct apd.cd_anomalia
    from dmalm_anomalia_prodotto_dummy apd
    where to_char(apd.dt_storicizzazione, 'yyyymm') = ?) ) 
  union all
  select a.*, nvl(tmp.DMALM_TEMPO_PK, 0) AS TEMPO_PK
  from dmalm_anomalia_prodotto_dummy a
  left join dmalm_tempo tmp on tmp.DT_OSSERVAZIONE = trunc(?)
  where to_char(a.dt_storicizzazione, 'yyyymm') = ?
  and a.rank_stato_anomalia_mese = 1
  and a.cd_anomalia in( (
   select distinct ap.cd_anomalia
   from dmalm_anomalia_prodotto ap
   join DMALM_STATO_WORKITEM sw on sw.DMALM_STATO_WORKITEM_PK = ap.DMALM_STATO_WORKITEM_FK_03 
   where ap.annullato is null
   and ap.rank_stato_anomalia_mese = 1
   and (sw.CD_STATO <> 'chiuso' or sw.CD_STATO <> 'chiuso_falso')
   and to_char(ap.dt_storicizzazione, 'yyyymm') = ?
   union all
   select distinct apd.cd_anomalia
   from dmalm_anomalia_prodotto_dummy apd
   where to_char(apd.dt_storicizzazione, 'yyyymm') = ?)
   minus (
    select distinct ap.cd_anomalia
    from dmalm_anomalia_prodotto ap
    where to_char(ap.dt_storicizzazione, 'yyyymm') = ?
    union all
    select distinct apd.cd_anomalia
    from dmalm_anomalia_prodotto_dummy apd
    where to_char(apd.dt_storicizzazione, 'yyyymm') = ?) ) ) 