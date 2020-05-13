SPOOL 325_PACKAGE_RFC.sql

SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
create or replace PACKAGE BODY RFC AS

  FUNCTION GET_ALL_RFC(dataCaricamento TIMESTAMP) RETURN SYS_REFCURSOR AS
  
  cur_rfc SYS_REFCURSOR;
  
  BEGIN
    OPEN cur_rfc FOR SELECT 
    'SIRE' as ID_REPOSITORY, 
	hw.c_uri as URI_RFC, 
	hw.SIRE_HISTORY_WORKITEM_PK as DMALM_RFC_PK, 
	hw.C_PK as STG_PK, 
	nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06, 
	hw.C_ID as CD_RFC, 
	NVL(hw.C_RESOLVEDON, to_timestamp('9999-12-31 01:00:00', 'YYYY-MM-DD HH:MI:SS')) as DATA_RISOLUZIONE_RFC, 
	CASE 
	   WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON ) 
	   ELSE 0 
	END as NR_GIORNI_FESTIVI, 
	CASE 
	   WHEN hw.C_RESOLVEDON is null THEN 0 
	   WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1 
	   ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),	'yyyy-mm-dd')) 
	END as TEMPO_TOT_RFC, 
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03, 
	hw.c_CREATED as DATA_CREAZ_RFC, 
	hw.C_UPDATED as DATA_MODIFICA_RECORD, 
	CASE 
	   WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl( 
       (	 
       select max(hh.C_UPDATED) from DMALM_SIRE_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED 
   ), 
   to_timestamp('1900-01-01 01:00:00', 'YYYY-MM-DD HH:MI:SS') 
   )) 
   else to_timestamp('9999-12-31 01:00:00', 'YYYY-MM-DD HH:MI:SS') 
    END as DATA_CHIUS_RFC, 
    cu.C_ID as USERID_RFC, 
    cu.C_NAME as NOME_RFC, 
    hw.C_RESOLUTION as MOTIVO_RISOLUZIONE, 
    hw.C_SEVERITY as SEVERITY_RFC, 
    hw.C_TITLE as DESCRIZIONE_RFC, 
    hw.cod_intervento as NUMERO_TESTATA_RDI, 
    hw.data_disp as DATA_DISPONIBILITA, 
    hw.C_PRIORITY as PRIORITY_RFC, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'tipologiaintervento') as TIPOLOGIA_INTERVENTO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'tipologiadatitrattati') as TIPOLOGIA_DATI_TRATTATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'richiestait') as RICHIESTA_IT, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'infrastrutturaEsistente') as INFRASTRUTTURA_ESISTENTE, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'cambiamentoRichiesto') as CAMBIAMENTO_RICHIESTO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'descrizioneutenza') as DESCRIZIONE_UTENZA, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'requisitiUtilizzo') as REQUISITI_DI_UTILIZZO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'modalitaGestione') as MODALITA_GESTIONE, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'criptazioneDati') as CRIPTAZIONE_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'politicaArchiviazioneDati') as POLITICA_ARCHIVIZIONE_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'separazioneRuoliAccessoDati') as SEPARAZIONE_RUOLI_ACCESSO_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'classeServizio') as CLASSE_DI_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'accessoServizio') as ACCESSO_AL_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'assistenzaUtenti') as ASSISTENZA_UTENTI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'analisiRischi') as ANALISI_DEI_RISCHI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'descrizioneservizio') as DESCRIZIONE_DEL_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'codiceSchedaServizio') as CODICE_SCHEDA_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'titoloRichiesta') as TITOLO_DELLA_RICHIESTA, 
    hw.c_duedate as DATA_SCADENZA ,
    hw.c_timespent as TIMESPENT,
    (select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
    (select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
    FROM  
    DMALM_SIRE_HISTORY_WORKITEM hw  
        left join DMALM_SIRE_HISTORY_USER cu on  hw.FK_AUTHOR = cu.C_PK 
        left join DMALM_SIRE_HISTORY_PROJECT cp on hw.FK_PROJECT = cp.C_PK 
                left join dmalm_stato_workitem st 
                    on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and cp.template = st.template 
                        left join DMALM_PROJECT p  
                        on p.ID_PROJECT = cp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SIRE'  
                            left join DMALM_USER u 
                            on u.ID_USER = cu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SIRE' 
    WHERE hw.c_type = 'rfc' 
    and hw.data_caricamento = dataCaricamento 
    UNION ALL
    SELECT 
    'SISS' as ID_REPOSITORY, 
	hw.c_uri as URI_RFC, 
	hw.SISS_HISTORY_WORKITEM_PK as DMALM_RFC_PK, 
	hw.C_PK as STG_PK, 
	nvl(p.DMALM_PROJECT_PK, 0) as DMALM_PROJECT_FK_02, 
	nvl(u.DMALM_USER_PK, 0) as DMALM_USER_FK_06, 
	hw.C_ID as CD_RFC, 
	NVL(hw.C_RESOLVEDON, to_timestamp('9999-12-31 01:00:00', 'YYYY-MM-DD HH:MI:SS')) as DATA_RISOLUZIONE_RFC, 
	CASE 
	   WHEN NOT hw.C_RESOLVEDON is null THEN (SELECT count(*) FROM DMALM_TEMPO where FL_FESTIVO = 1 and DT_OSSERVAZIONE BETWEEN  hw.c_created and hw.C_RESOLVEDON ) 
	   ELSE 0 
	END as NR_GIORNI_FESTIVI, 
	CASE 
	   WHEN hw.C_RESOLVEDON is null THEN 0 
	   WHEN to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd') =  to_char(hw.C_CREATED, 'yyyy-mm-dd') THEN 1 
	   ELSE TO_NUMBER(to_date(to_char(hw.C_RESOLVEDON, 'yyyy-mm-dd'),'yyyy-mm-dd') - to_date(to_char(hw.C_CREATED, 'yyyy-mm-dd'),	'yyyy-mm-dd')) 
	END as TEMPO_TOT_RFC, 
	nvl(st.DMALM_STATO_WORKITEM_PK, 0) as DMALM_STATO_WORKITEM_FK_03, 
	hw.c_CREATED as DATA_CREAZ_RFC, 
	hw.C_UPDATED as DATA_MODIFICA_RECORD, 
	CASE 
	   WHEN hw.C_STATUS = 'chiuso' THEN (select min(shw.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM shw where shw.c_uri = hw.c_uri and shw.C_STATUS = 'chiuso' and shw.C_UPDATED > nvl( 
       (	 
       select max(hh.C_UPDATED) from DMALM_SISS_HISTORY_WORKITEM hh where hh.C_ID = hw.C_ID and hh.C_STATUS != hw.C_STATUS and hh.C_UPDATED < hw.C_UPDATED 
   ), 
   to_timestamp('1900-01-01 01:00:00', 'YYYY-MM-DD HH:MI:SS') 
   )) 
   else to_timestamp('9999-12-31 01:00:00', 'YYYY-MM-DD HH:MI:SS') 
    END as DATA_CHIUS_RFC, 
    cu.C_ID as USERID_RFC, 
    cu.C_NAME as NOME_RFC, 
    hw.C_RESOLUTION as MOTIVO_RISOLUZIONE, 
    hw.C_SEVERITY as SEVERITY_RFC, 
    hw.C_TITLE as DESCRIZIONE_RFC, 
    hw.cod_intervento as NUMERO_TESTATA_RDI, 
    hw.data_disp as DATA_DISPONIBILITA, 
    hw.C_PRIORITY as PRIORITY_RFC, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'tipologiaintervento') as TIPOLOGIA_INTERVENTO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'tipologiadatitrattati') as TIPOLOGIA_DATI_TRATTATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'richiestait') as RICHIESTA_IT, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'infrastrutturaEsistente') as INFRASTRUTTURA_ESISTENTE, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'cambiamentoRichiesto') as CAMBIAMENTO_RICHIESTO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'descrizioneutenza') as DESCRIZIONE_UTENZA, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'requisitiUtilizzo') as REQUISITI_DI_UTILIZZO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'modalitaGestione') as MODALITA_GESTIONE, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'criptazioneDati') as CRIPTAZIONE_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'politicaArchiviazioneDati') as POLITICA_ARCHIVIZIONE_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'separazioneRuoliAccessoDati') as SEPARAZIONE_RUOLI_ACCESSO_DATI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'classeServizio') as CLASSE_DI_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'accessoServizio') as ACCESSO_AL_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'assistenzaUtenti') as ASSISTENZA_UTENTI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'analisiRischi') as ANALISI_DEI_RISCHI, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'descrizioneservizio') as DESCRIZIONE_DEL_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'codiceSchedaServizio') as CODICE_SCHEDA_SERVIZIO, 
    (select distinct to_char(cf.c_string_value) from dmalm_siss_history_cf_workitem cf where cf.fk_workitem = hw.C_PK and cf.c_name = 'titoloRichiesta') as TITOLO_DELLA_RICHIESTA, 
    hw.c_duedate as DATA_SCADENZA ,
    hw.c_timespent as TIMESPENT,
    (select distinct to_char(hcf.c_string_value) from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'tag_alm') as TAG_ALM,
    (select distinct hcf.c_date_value from dmalm_siss_history_cf_workitem hcf where hcf.fk_workitem = hw.c_pk and hcf.c_name = 'ts_tag_alm') as TS_TAG_ALM
    FROM  
    DMALM_SISS_HISTORY_WORKITEM hw  
        left join DMALM_SISS_HISTORY_USER cu on  hw.FK_AUTHOR = cu.C_PK 
        left join DMALM_SISS_HISTORY_PROJECT cp on hw.FK_PROJECT = cp.C_PK 
                left join dmalm_stato_workitem st 
                    on st.ORIGINE_STATO = hw.c_type and hw.c_status = st.CD_STATO and cp.template = st.template 
                        left join DMALM_PROJECT p  
                        on p.ID_PROJECT = cp.c_id and hw.c_updated between p.DT_INIZIO_VALIDITA and p.DT_FINE_VALIDITA and p.ID_REPOSITORY = 'SISS'  
                            left join DMALM_USER u 
                            on u.ID_USER = cu.c_id and hw.c_updated between u.DT_INIZIO_VALIDITA and u.DT_FINE_VALIDITA and u.ID_REPOSITORY = 'SISS' 
    WHERE hw.c_type = 'rfc' 
    and hw.data_caricamento = dataCaricamento;
    RETURN cur_rfc;
  END GET_ALL_RFC;

  FUNCTION GET_RFC (richiesta RFCTYPE) RETURN SYS_REFCURSOR AS
    cur_rfc SYS_REFCURSOR;
BEGIN
    OPEN cur_rfc FOR SELECT * FROM DMALM_RFC WHERE CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY AND RANK_STATO_RFC = 1;
    RETURN cur_rfc;
  END GET_RFC;

  PROCEDURE INSERT_RFC (richiesta RFCTYPE, v_data_cambio_stato_rfc TIMESTAMP, dataCaricamento TIMESTAMP) AS
  BEGIN
  
       INSERT /*+ APPEND */ INTO DMALM_RFC (CD_RFC, DMALM_RFC_PK, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03,
       DMALM_TEMPO_FK_04, DMALM_USER_FK_06, RANK_STATO_RFC, RANK_STATO_RFC_MESE, DT_CARICAMENTO_RFC, TAG_ALM, TS_TAG_ALM, 
       ANNULLATO, STG_PK, URI_RFC, DT_STORICIZZAZIONE, DATA_MODIFICA_RECORD, TIMESPENT, 
       DESCRIPTION, TIPOLOGIA_INTERVENTO, TIPOLOGIA_DATI_TRATTATI, RICHIESTA_IT, INFRASTRUTTURA_ESISTENTE, CAMBIAMENTO_RICHIESTO, DESCRIZIONE_UTENZA,
       REQUISITI_DI_UTILIZZO, MODALITA_GESTIONE, CRIPTAZIONE_DATI, POLITICA_ARCHIVIZIONE_DATI, SEPARAZIONE_RUOLI_ACCESSO_DATI,
       CLASSE_DI_SERVIZIO, ACCESSO_AL_SERVIZIO, ASSISTENZA_UTENTI, ANALISI_DEI_RISCHI,DESCRIZIONE_DEL_SERVIZIO, COD_SCHEDA_SERVIZIO_POLARION,
       TITOLO_DELLA_RICHIESTA, ID_REPOSITORY, DATA_CAMBIO_STATO_RFC, DATA_ANNULLAMENTO) 
       VALUES (NVL(richiesta.CD_RFC, NULL), richiesta.DMALM_RFC_PK, NVL(richiesta.DMALM_PROJECT_FK_02, NULL), 
       NVL(richiesta.DMALM_STATO_WORKITEM_FK_03, NULL), NVL(richiesta.DMALM_TEMPO_FK_04, NULL), NVL(richiesta.DMALM_USER_FK_06, NULL), 
       NVL(richiesta.RANK_STATO_RFC, NULL), NVL(richiesta.RANK_STATO_RFC_MESE, NULL),
       dataCaricamento, NVL(richiesta.TAG_ALM, NULL), NVL(richiesta.TS_TAG_ALM, NULL), NVL(richiesta.ANNULLATO, NULL),
       NVL(richiesta.STG_PK, NULL), NVL(richiesta.URI_RFC, NULL), NVL(richiesta.DT_STORICIZZAZIONE, NULL),
       NVL(richiesta.DATA_MODIFICA_RECORD, NULL), NVL(richiesta.TIMESPENT, NULL), NVL(richiesta.DESCRIPTION, NULL), NVL(richiesta.TIPOLOGIA_INTERVENTO, NULL),
       NVL(richiesta.TIPOLOGIA_DATI_TRATTATI, NULL), NVL(richiesta.RICHIESTA_IT, NULL), NVL(richiesta.INFRASTRUTTURA_ESISTENTE, NULL), NVL(richiesta.CAMBIAMENTO_RICHIESTO, NULL), 
       NVL(richiesta.DESCRIZIONE_UTENZA, NULL), NVL(richiesta.REQUISITI_DI_UTILIZZO, NULL), NVL(richiesta.MODALITA_GESTIONE, NULL), NVL(richiesta.CRIPTAZIONE_DATI, NULL),
       NVL(richiesta.POLITICA_ARCHIVIZIONE_DATI, NULL), NVL(richiesta.SEPARAZIONE_RUOLI_ACCESSO_DATI, NULL),  NVL(richiesta.CLASSE_DI_SERVIZIO, NULL), NVL(richiesta.ACCESSO_AL_SERVIZIO, NULL), 
       NVL(richiesta.ASSISTENZA_UTENTI, NULL), NVL(richiesta.ANALISI_DEI_RISCHI, NULL), NVL(richiesta.DESCRIZIONE_DEL_SERVIZIO, NULL), NVL(richiesta.COD_SCHEDA_SERVIZIO_POLARION, NULL),
       NVL(richiesta.TITOLO_DELLA_RICHIESTA, NULL), NVL(richiesta.ID_REPOSITORY, NULL), NVL(v_data_cambio_stato_rfc, NULL), NVL(richiesta.DATA_ANNULLAMENTO, NULL));    

  END INSERT_RFC;

  PROCEDURE UPDATE_RANK (richiesta RFCTYPE, rank NUMBER) AS
    v_query VARCHAR2(1000);
  BEGIN
    UPDATE DMALM_RFC SET RANK_STATO_RFC = rank WHERE CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY;

  END UPDATE_RANK;

  PROCEDURE INSERT_UPDATE_RFC_DT_CAM (richiesta RFCTYPE, v_data_cambio_stato_rfc TIMESTAMP, dataCaricamento TIMESTAMP) AS
    v_query VARCHAR2(10000);
  BEGIN
    INSERT /*+ APPEND */ INTO DMALM_RFC (CD_RFC, DMALM_RFC_PK, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03,
       DMALM_TEMPO_FK_04, DMALM_USER_FK_06, RANK_STATO_RFC, RANK_STATO_RFC_MESE, DT_CARICAMENTO_RFC, TAG_ALM, TS_TAG_ALM, 
       ANNULLATO, STG_PK, URI_RFC, DT_STORICIZZAZIONE, DATA_MODIFICA_RECORD, TIMESPENT, 
       DESCRIPTION, TIPOLOGIA_INTERVENTO, TIPOLOGIA_DATI_TRATTATI, RICHIESTA_IT, INFRASTRUTTURA_ESISTENTE, CAMBIAMENTO_RICHIESTO, DESCRIZIONE_UTENZA,
       REQUISITI_DI_UTILIZZO, MODALITA_GESTIONE, CRIPTAZIONE_DATI, POLITICA_ARCHIVIZIONE_DATI, SEPARAZIONE_RUOLI_ACCESSO_DATI,
       CLASSE_DI_SERVIZIO, ACCESSO_AL_SERVIZIO, ASSISTENZA_UTENTI, ANALISI_DEI_RISCHI,DESCRIZIONE_DEL_SERVIZIO, COD_SCHEDA_SERVIZIO_POLARION,
       TITOLO_DELLA_RICHIESTA, ID_REPOSITORY, DATA_CAMBIO_STATO_RFC, DATA_ANNULLAMENTO)  
VALUES (NVL(richiesta.CD_RFC, NULL), HISTORY_WORKITEM_SEQ.nextval, NVL(richiesta.DMALM_PROJECT_FK_02, NULL), 
       NVL(richiesta.DMALM_STATO_WORKITEM_FK_03, NULL), NVL(richiesta.DMALM_TEMPO_FK_04, NULL), NVL(richiesta.DMALM_USER_FK_06, NULL), 
       NVL(richiesta.RANK_STATO_RFC, NULL), NVL(richiesta.RANK_STATO_RFC_MESE, NULL),
       dataCaricamento, NVL(richiesta.TAG_ALM, NULL), NVL(richiesta.TS_TAG_ALM, NULL), NVL(richiesta.ANNULLATO, NULL),
       NVL(richiesta.STG_PK, NULL), NVL(richiesta.URI_RFC, NULL), NVL(richiesta.DT_STORICIZZAZIONE, NULL),
       NVL(richiesta.DATA_MODIFICA_RECORD, NULL), NVL(richiesta.TIMESPENT, NULL), NVL(richiesta.DESCRIPTION, NULL), NVL(richiesta.TIPOLOGIA_INTERVENTO, NULL),
       NVL(richiesta.TIPOLOGIA_DATI_TRATTATI, NULL), NVL(richiesta.RICHIESTA_IT, NULL), NVL(richiesta.INFRASTRUTTURA_ESISTENTE, NULL), NVL(richiesta.CAMBIAMENTO_RICHIESTO, NULL), 
       NVL(richiesta.DESCRIZIONE_UTENZA, NULL), NVL(richiesta.REQUISITI_DI_UTILIZZO, NULL), NVL(richiesta.MODALITA_GESTIONE, NULL), NVL(richiesta.CRIPTAZIONE_DATI, NULL),
       NVL(richiesta.POLITICA_ARCHIVIZIONE_DATI, NULL), NVL(richiesta.SEPARAZIONE_RUOLI_ACCESSO_DATI, NULL),  NVL(richiesta.CLASSE_DI_SERVIZIO, NULL), NVL(richiesta.ACCESSO_AL_SERVIZIO, NULL), 
       NVL(richiesta.ASSISTENZA_UTENTI, NULL), NVL(richiesta.ANALISI_DEI_RISCHI, NULL), NVL(richiesta.DESCRIZIONE_DEL_SERVIZIO, NULL), NVL(richiesta.COD_SCHEDA_SERVIZIO_POLARION, NULL),
       NVL(richiesta.TITOLO_DELLA_RICHIESTA, NULL), NVL(richiesta.ID_REPOSITORY, NULL), NVL(v_data_cambio_stato_rfc, NULL), NVL(richiesta.DATA_ANNULLAMENTO, NULL));
  END INSERT_UPDATE_RFC_DT_CAM;

  PROCEDURE INSERT_UPDATE_RFC (richiesta RFCTYPE, dataCaricamento TIMESTAMP) AS
    v_query VARCHAR2(10000);
  BEGIN
    INSERT /*+ APPEND */ INTO DMALM_RFC (CD_RFC, DMALM_RFC_PK, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03,
       DMALM_TEMPO_FK_04, DMALM_USER_FK_06, RANK_STATO_RFC, RANK_STATO_RFC_MESE, DT_CARICAMENTO_RFC, TAG_ALM, TS_TAG_ALM, 
       ANNULLATO, STG_PK, URI_RFC, DT_STORICIZZAZIONE, DATA_MODIFICA_RECORD, TIMESPENT, 
       DESCRIPTION, TIPOLOGIA_INTERVENTO, TIPOLOGIA_DATI_TRATTATI, RICHIESTA_IT, INFRASTRUTTURA_ESISTENTE, CAMBIAMENTO_RICHIESTO, DESCRIZIONE_UTENZA,
       REQUISITI_DI_UTILIZZO, MODALITA_GESTIONE, CRIPTAZIONE_DATI, POLITICA_ARCHIVIZIONE_DATI, SEPARAZIONE_RUOLI_ACCESSO_DATI,
       CLASSE_DI_SERVIZIO, ACCESSO_AL_SERVIZIO, ASSISTENZA_UTENTI, ANALISI_DEI_RISCHI,DESCRIZIONE_DEL_SERVIZIO, COD_SCHEDA_SERVIZIO_POLARION,
       TITOLO_DELLA_RICHIESTA, ID_REPOSITORY, DATA_CAMBIO_STATO_RFC, DATA_ANNULLAMENTO)  
VALUES (NVL(richiesta.CD_RFC, NULL), HISTORY_WORKITEM_SEQ.nextval, NVL(richiesta.DMALM_PROJECT_FK_02, NULL), 
       NVL(richiesta.DMALM_STATO_WORKITEM_FK_03, NULL), NVL(richiesta.DMALM_TEMPO_FK_04, NULL), NVL(richiesta.DMALM_USER_FK_06, NULL), 
       NVL(richiesta.RANK_STATO_RFC, NULL), NVL(richiesta.RANK_STATO_RFC_MESE, NULL),
       dataCaricamento, NVL(richiesta.TAG_ALM, NULL), NVL(richiesta.TS_TAG_ALM, NULL), NVL(richiesta.ANNULLATO, NULL),
       NVL(richiesta.STG_PK, NULL), NVL(richiesta.URI_RFC, NULL), NVL(richiesta.DT_STORICIZZAZIONE, NULL),
       NVL(richiesta.DATA_MODIFICA_RECORD, NULL), NVL(richiesta.TIMESPENT, NULL), NVL(richiesta.DESCRIPTION, NULL), NVL(richiesta.TIPOLOGIA_INTERVENTO, NULL),
       NVL(richiesta.TIPOLOGIA_DATI_TRATTATI, NULL), NVL(richiesta.RICHIESTA_IT, NULL), NVL(richiesta.INFRASTRUTTURA_ESISTENTE, NULL), NVL(richiesta.CAMBIAMENTO_RICHIESTO, NULL), 
       NVL(richiesta.DESCRIZIONE_UTENZA, NULL), NVL(richiesta.REQUISITI_DI_UTILIZZO, NULL), NVL(richiesta.MODALITA_GESTIONE, NULL), NVL(richiesta.CRIPTAZIONE_DATI, NULL),
       NVL(richiesta.POLITICA_ARCHIVIZIONE_DATI, NULL), NVL(richiesta.SEPARAZIONE_RUOLI_ACCESSO_DATI, NULL),  NVL(richiesta.CLASSE_DI_SERVIZIO, NULL), NVL(richiesta.ACCESSO_AL_SERVIZIO, NULL), 
       NVL(richiesta.ASSISTENZA_UTENTI, NULL), NVL(richiesta.ANALISI_DEI_RISCHI, NULL), NVL(richiesta.DESCRIZIONE_DEL_SERVIZIO, NULL), NVL(richiesta.COD_SCHEDA_SERVIZIO_POLARION, NULL),
       NVL(richiesta.TITOLO_DELLA_RICHIESTA, NULL), NVL(richiesta.ID_REPOSITORY, NULL), NVL(richiesta.DATA_CAMBIO_STATO_RFC, NULL), NVL(richiesta.DATA_ANNULLAMENTO, NULL));

  END INSERT_UPDATE_RFC;

  PROCEDURE UPDATE_RFC (richiesta RFCTYPE) AS
  BEGIN
    UPDATE DMALM_RFC SET DMALM_RFC_PK = NVL(richiesta.DMALM_RFC_PK, NULL),
    DMALM_PROJECT_FK_02 = NVL(richiesta.DMALM_PROJECT_FK_02, NULL) , DMALM_STATO_WORKITEM_FK_03 = NVL(richiesta.DMALM_STATO_WORKITEM_FK_03, NULL) , DMALM_TEMPO_FK_04 = NVL(richiesta.DMALM_TEMPO_FK_04, NULL),
    DMALM_USER_FK_06= NVL(richiesta.DMALM_USER_FK_06, NULL) , RANK_STATO_RFC = NVL(richiesta.RANK_STATO_RFC, NULL) , RANK_STATO_RFC_MESE = NVL(richiesta.RANK_STATO_RFC_MESE, NULL) ,
    DT_CARICAMENTO_RFC = NVL(richiesta.DT_CARICAMENTO_RFC, NULL), TAG_ALM = NVL(richiesta.TAG_ALM, NULL) , TS_TAG_ALM = NVL(richiesta.TS_TAG_ALM, NULL),
    ANNULLATO = NVL(richiesta.ANNULLATO, NULL) , STG_PK = NVL(richiesta.STG_PK, NULL), URI_RFC = NVL(richiesta.URI_RFC, NULL),
    DT_STORICIZZAZIONE = NVL(richiesta.DT_STORICIZZAZIONE, NULL) , DATA_MODIFICA_RECORD = NVL(richiesta.DATA_MODIFICA_RECORD, NULL),
    TIMESPENT = NVL(richiesta.TIMESPENT, NULL) , DESCRIPTION = NVL(richiesta.DESCRIPTION, NULL) , TIPOLOGIA_INTERVENTO = NVL(richiesta.TIPOLOGIA_INTERVENTO, NULL), 
    TIPOLOGIA_DATI_TRATTATI = NVL(richiesta.TIPOLOGIA_DATI_TRATTATI, NULL) , RICHIESTA_IT = NVL(richiesta.RICHIESTA_IT, NULL) , INFRASTRUTTURA_ESISTENTE = NVL(richiesta.INFRASTRUTTURA_ESISTENTE, NULL) , 
    CAMBIAMENTO_RICHIESTO = NVL(richiesta.CAMBIAMENTO_RICHIESTO, NULL), DESCRIZIONE_UTENZA = NVL(richiesta.DESCRIZIONE_UTENZA, NULL), REQUISITI_DI_UTILIZZO = NVL(richiesta.REQUISITI_DI_UTILIZZO, NULL), 
    MODALITA_GESTIONE = NVL(richiesta.MODALITA_GESTIONE, NULL), CRIPTAZIONE_DATI = NVL(richiesta.CRIPTAZIONE_DATI, NULL), POLITICA_ARCHIVIZIONE_DATI = NVL(richiesta.POLITICA_ARCHIVIZIONE_DATI, NULL), 
    SEPARAZIONE_RUOLI_ACCESSO_DATI = NVL(richiesta.SEPARAZIONE_RUOLI_ACCESSO_DATI, NULL), CLASSE_DI_SERVIZIO = NVL(richiesta.CLASSE_DI_SERVIZIO, NULL), ACCESSO_AL_SERVIZIO = NVL(richiesta.ACCESSO_AL_SERVIZIO, NULL),
    ASSISTENZA_UTENTI = NVL(richiesta.ASSISTENZA_UTENTI, NULL), ANALISI_DEI_RISCHI = NVL(richiesta.ANALISI_DEI_RISCHI, NULL), DESCRIZIONE_DEL_SERVIZIO = NVL(richiesta.DESCRIZIONE_DEL_SERVIZIO, NULL),
    COD_SCHEDA_SERVIZIO_POLARION = NVL(richiesta.COD_SCHEDA_SERVIZIO_POLARION, NULL), TITOLO_DELLA_RICHIESTA = NVL(richiesta.TITOLO_DELLA_RICHIESTA, NULL), DATA_CAMBIO_STATO_RFC = NVL(richiesta.DATA_CAMBIO_STATO_RFC, NULL),
    DATA_ANNULLAMENTO = NVL(richiesta.DATA_ANNULLAMENTO, NULL)
    
WHERE CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY AND RANK_STATO_RFC = 1;
  END UPDATE_RFC;

  FUNCTION GET_RFC_BY_PK (pk NUMBER) RETURN SYS_REFCURSOR AS
    cur_rfc SYS_REFCURSOR;
  BEGIN
    OPEN cur_rfc FOR SELECT * FROM DMALM_RFC WHERE DMALM_RFC_PK = pk;
    RETURN cur_rfc;
  END GET_RFC_BY_PK;

  PROCEDURE DELETE_RFC_ODS AS
  BEGIN
    DELETE FROM DMALM_RFC_ODS;
    
  END DELETE_RFC_ODS;

  PROCEDURE INSERT_RFC_ODS (richiesta RFCTYPE, dataCaricamento TIMESTAMP) AS
  BEGIN
    INSERT /*+ APPEND */ INTO DMALM_RFC (CD_RFC, DMALM_RFC_PK, DMALM_PROJECT_FK_02, DMALM_STATO_WORKITEM_FK_03,
       DMALM_TEMPO_FK_04, DMALM_USER_FK_06, RANK_STATO_RFC, RANK_STATO_RFC_MESE, DT_CARICAMENTO_RFC, TAG_ALM, TS_TAG_ALM, 
       ANNULLATO, STG_PK, URI_RFC, DT_STORICIZZAZIONE, DATA_MODIFICA_RECORD, TIMESPENT, 
       DESCRIPTION, TIPOLOGIA_INTERVENTO, TIPOLOGIA_DATI_TRATTATI, RICHIESTA_IT, INFRASTRUTTURA_ESISTENTE, CAMBIAMENTO_RICHIESTO, DESCRIZIONE_UTENZA,
       REQUISITI_DI_UTILIZZO, MODALITA_GESTIONE, CRIPTAZIONE_DATI, POLITICA_ARCHIVIZIONE_DATI, SEPARAZIONE_RUOLI_ACCESSO_DATI,
       CLASSE_DI_SERVIZIO, ACCESSO_AL_SERVIZIO, ASSISTENZA_UTENTI, ANALISI_DEI_RISCHI,DESCRIZIONE_DEL_SERVIZIO, COD_SCHEDA_SERVIZIO_POLARION,
       TITOLO_DELLA_RICHIESTA, ID_REPOSITORY, DATA_CAMBIO_STATO_RFC, DATA_ANNULLAMENTO) 
       VALUES (NVL(richiesta.CD_RFC, NULL), richiesta.DMALM_RFC_PK, NVL(richiesta.DMALM_PROJECT_FK_02, NULL), 
       NVL(richiesta.DMALM_STATO_WORKITEM_FK_03, NULL), NVL(richiesta.DMALM_TEMPO_FK_04, NULL), NVL(richiesta.DMALM_USER_FK_06, NULL), 
       NVL(richiesta.RANK_STATO_RFC, NULL), NVL(richiesta.RANK_STATO_RFC_MESE, NULL),
       dataCaricamento, NVL(richiesta.TAG_ALM, NULL), NVL(richiesta.TS_TAG_ALM, NULL), NVL(richiesta.ANNULLATO, NULL),
       NVL(richiesta.STG_PK, NULL), NVL(richiesta.URI_RFC, NULL), NVL(richiesta.DT_STORICIZZAZIONE, NULL),
       NVL(richiesta.DATA_MODIFICA_RECORD, NULL), NVL(richiesta.TIMESPENT, NULL), NVL(richiesta.DESCRIPTION, NULL), NVL(richiesta.TIPOLOGIA_INTERVENTO, NULL),
       NVL(richiesta.TIPOLOGIA_DATI_TRATTATI, NULL), NVL(richiesta.RICHIESTA_IT, NULL), NVL(richiesta.INFRASTRUTTURA_ESISTENTE, NULL), NVL(richiesta.CAMBIAMENTO_RICHIESTO, NULL), 
       NVL(richiesta.DESCRIZIONE_UTENZA, NULL), NVL(richiesta.REQUISITI_DI_UTILIZZO, NULL), NVL(richiesta.MODALITA_GESTIONE, NULL), NVL(richiesta.CRIPTAZIONE_DATI, NULL),
       NVL(richiesta.POLITICA_ARCHIVIZIONE_DATI, NULL), NVL(richiesta.SEPARAZIONE_RUOLI_ACCESSO_DATI, NULL),  NVL(richiesta.CLASSE_DI_SERVIZIO, NULL), NVL(richiesta.ACCESSO_AL_SERVIZIO, NULL), 
       NVL(richiesta.ASSISTENZA_UTENTI, NULL), NVL(richiesta.ANALISI_DEI_RISCHI, NULL), NVL(richiesta.DESCRIZIONE_DEL_SERVIZIO, NULL), NVL(richiesta.COD_SCHEDA_SERVIZIO_POLARION, NULL),
       NVL(richiesta.TITOLO_DELLA_RICHIESTA, NULL), NVL(richiesta.ID_REPOSITORY, NULL), NVL(richiesta.DATA_CAMBIO_STATO_RFC, NULL), NVL(richiesta.DATA_ANNULLAMENTO, NULL)); 
  END INSERT_RFC_ODS;

  FUNCTION GET_ALL_RFC_ODS RETURN SYS_REFCURSOR AS
    cur_rfc SYS_REFCURSOR;

    v_query VARCHAR2(10000);
  BEGIN
    OPEN cur_rfc FOR SELECT * FROM DMALM_RFC_ODS ORDER BY CD_RFC, DATA_MODIFICA_RECORD ASC;
    return cur_rfc;
  END GET_ALL_RFC_ODS;

  FUNCTION GET_PK_RFC_BY_PK_HIS (pk_project INTEGER) RETURN SYS_REFCURSOR AS
    cur_rfc SYS_REFCURSOR;
  BEGIN
    OPEN cur_rfc FOR SELECT * FROM DMALM_RFC WHERE DMALM_PROJECT_FK_02 = pk_project;
    return cur_rfc;
  END GET_PK_RFC_BY_PK_HIS;

  FUNCTION GET_PK_RFC_BY_PK_HIS_SUB (pk_project INTEGER) RETURN SYS_REFCURSOR AS
    cur_rfc SYS_REFCURSOR;
  BEGIN
    OPEN cur_rfc FOR SELECT * FROM DMALM_RFC rs WHERE rs.DMALM_PROJECT_FK_02 = pk_project
    AND rs.DT_STORICIZZAZIONE IN (SELECT MAX(ris.DT_STORICIZZAZIONE) FROM DMALM_RFC ris WHERE rs.DMALM_PROJECT_FK_02 = ris.DMALM_PROJECT_FK_02 AND rs.CD_RFC = ris.CD_RFC);
    return cur_rfc;
  END GET_PK_RFC_BY_PK_HIS_SUB;

  PROCEDURE UPDATE_WI_RFC_DELETE (richiesta RFCTYPE, dataCaricamento TIMESTAMP) AS
  BEGIN
    UPDATE DMALM_RFC SET ANNULLATO = 'ANNULLATO FISICAMENTE', DATA_ANNULLAMENTO = dataCaricamento WHERE CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY;

  END UPDATE_WI_RFC_DELETE;

  FUNCTION CHECK_ESISTENZA_RFC (richiesta RFCTYPE, pk_project INTEGER) RETURN VARCHAR2 AS
    v_flag VARCHAR2(5);

    v_query VARCHAR2(1000);
    v_cursor SYS_REFCURSOR;
    v_pk INTEGER;
  BEGIN
    v_flag := 'FALSE';

    OPEN v_cursor FOR SELECT DMALM_RFC_PK FROM DMALM_RFC WHERE DMALM_PROJECT_FK_02 = pk_project
         AND CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY;
    LOOP
        FETCH v_cursor INTO v_pk;
        EXIT WHEN v_cursor%NOTFOUND;

        v_flag := 'TRUE';
    END LOOP;

    return v_flag;
  END CHECK_ESISTENZA_RFC;

  PROCEDURE CHECK_ALTERED_ROW_RFC (richiesta RFCTYPE, dataCaricamento TIMESTAMP) AS
    v_count INTEGER := 0;
    v_data_cambio_stato_rfc TIMESTAMP(6) := richiesta.DATA_MODIFICA_RECORD;
    v_count_modify_row INTEGER := 0;
    v_flag VARCHAR2(5) := 'TRUE';
    v_flag_modified_row VARCHAR2(5) := 'FALSE';
    v_cursor SYS_REFCURSOR;
    v_column_name VARCHAR2(35);
    v_sql VARCHAR2(1000);
    TYPE v_type_noscd_array IS VARRAY(15) OF VARCHAR2(30);
    v_noscd_array v_type_noscd_array := v_type_noscd_array('DMALM_RFC_PK', 'CD_RFC', 'CD_RFC',
        'ID_REPOSITORY', 'RANK_STATO_RFC', 'STG_PK', 'TIMESPENT', 'DT_STORICIZZAZIONE', 
        'DATA_SCADENZA', 'DATA_CREAZ_RFC', 'DATA_ANNULLAMENTO', 'DT_CARICAMENTO_RFC',
        'DMALM_USER_FK_06', 'DMALM_TEMPO_FK_04');
    richiesta_type VARCHAR2(100);
  BEGIN
    SELECT COUNT(DMALM_RFC_PK)
    INTO v_count
    FROM DMALM_RFC WHERE CD_RFC = richiesta.CD_RFC AND ID_REPOSITORY = richiesta.ID_REPOSITORY AND RANK_STATO_RFC = 1;

    if v_count = 0 then
        INSERT_RFC (richiesta, v_data_cambio_stato_rfc, dataCaricamento);
    else       
        OPEN v_cursor FOR select column_name from dba_tab_columns where TABLE_NAME = 'DMALM_RFC' and OWNER = 'DM_ALM_SV2';
        LOOP
            FETCH v_cursor INTO v_column_name;
            EXIT WHEN v_cursor%NOTFOUND;

            FOR i IN 1..v_noscd_array.count LOOP
                if trim(v_column_name) = trim(v_noscd_array(i)) then
                    v_flag := 'FALSE';
                end if;
            END LOOP;
            if v_flag = 'TRUE' then
                richiesta_type := 'richiesta.'||v_column_name;
                v_sql := 'SELECT COUNT(DMALM_RFC_PK) FROM DMALM_RFC WHERE CD_RFC = :CD_RFC AND ID_REPOSITORY = :ID_REPOSITORY AND RANK_STATO_RFC = 1 AND :v_column_name = :richiesta_type';
                EXECUTE IMMEDIATE v_sql INTO v_count_modify_row USING richiesta.CD_RFC, richiesta.ID_REPOSITORY, v_column_name, richiesta_type;

                if v_count_modify_row > 0 then
                    v_flag_modified_row := 'TRUE';
                end if;
            end if;
            v_flag := 'TRUE';
        END LOOP;

        if v_flag_modified_row = 'TRUE' then
            UPDATE_RANK(richiesta, 0);
            INSERT_UPDATE_RFC_DT_CAM(richiesta, v_data_cambio_stato_rfc, dataCaricamento);
        else
            UPDATE_RFC(richiesta);
        end if;
    end if;
    COMMIT;
  END CHECK_ALTERED_ROW_RFC;

END RFC;