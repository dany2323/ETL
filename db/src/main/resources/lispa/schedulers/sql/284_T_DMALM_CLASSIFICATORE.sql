SPOOL 284_T_DMALM_CLASSIFICATORE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_CLASSIFICATORE
(
  DMALM_CLASSIFICATORE_PK         NUMBER(15)    NOT NULL,
  CD_CLASSIFICATORE               VARCHAR2(100 BYTE),
  DT_CREAZIONE_CLASSIF            DATE,
  DT_RISOLUZIONE_CLASSIF          DATE,
  DT_MODIFICA_CLASSIF             DATE,
  DT_CAMBIO_STATO_CLASSIF         DATE,
  ID_AUTORE_CLASSIFICATORE        VARCHAR2(100 BYTE),
  DS_AUTORE_CLASSIFICATORE        VARCHAR2(100 BYTE),
  DT_SCADENZA_PROG_SVIL_D         TIMESTAMP(6),
  TITOLO_CLASSIFICATORE           VARCHAR2(4000 BYTE),
  ID_REPOSITORY                   VARCHAR2(20 BYTE),
  DMALM_STRUTTURA_ORG_FK_01       NUMBER(15),
  DMALM_PROJECT_FK_02             NUMBER(15),
  DMALM_STATO_WORKITEM_FK_03      NUMBER(16),
  DMALM_TEMPO_FK_04               NUMBER(15)    DEFAULT 0,
  DMALM_AREA_TEMATICA_FK_05       NUMBER(15)    DEFAULT 0,
  DMALM_USER_FK_06                NUMBER(15)    DEFAULT 0,
  STG_PK                          VARCHAR2(4000 BYTE),
  URI_CLASSIFICATORE              VARCHAR2(4000 BYTE),
  CF_AREA                         VARCHAR2(100 BYTE),
  CF_AMBITO                       VARCHAR2(100 BYTE),
  CF_SCHEDA_SERVIZIO              VARCHAR2(100 BYTE),
  CF_RIFERIMENTI                  VARCHAR2(100 BYTE),
  RANK_STATO_CLASSIFICATORE       NUMBER,
  RANK_STATO_CLASSIF_MESE         NUMBER(5)     DEFAULT 0,
  DT_CARICAMENTO_CLASSIF          DATE,
  DT_STORICIZZAZIONE              DATE,
  ANNULLATO                       VARCHAR2(50 BYTE),
  DT_ANNULLAMENTO                 DATE,
  CHANGED                         VARCHAR2(100 BYTE)
)
 TABLESPACE "TS_ALM"; 
 
COMMENT ON TABLE T_DMALM_CLASSIFICATORE IS 'T_DMALM_CLASSIFICATORE';

COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_CLASSIFICATORE_PK    IS 'DMALM_CLASSIFICATORE_PK';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CD_CLASSIFICATORE          IS 'CD_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_CREAZIONE_CLASSIF       IS 'DT_CREAZIONE_CLASSIF';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_RISOLUZIONE_CLASSIF     IS 'DT_RISOLUZIONE_CLASSIF';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_MODIFICA_CLASSIF        IS 'DT_MODIFICA_CLASSIF';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_CAMBIO_STATO_CLASSIF    IS 'DT_CAMBIO_STATO_CLASSIF';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.ID_AUTORE_CLASSIFICATORE   IS 'ID_AUTORE_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DS_AUTORE_CLASSIFICATORE   IS 'DS_AUTORE_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_SCADENZA_PROG_SVIL_D    IS 'DT_SCADENZA_PROG_SVIL_D';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.TITOLO_CLASSIFICATORE      IS 'TITOLO_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.ID_REPOSITORY              IS 'ID_REPOSITORY';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_STRUTTURA_ORG_FK_01  IS 'DMALM_STRUTTURA_ORG_FK_01';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_PROJECT_FK_02        IS 'DMALM_PROJECT_FK_02';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_TEMPO_FK_04          IS 'DMALM_TEMPO_FK_04';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_AREA_TEMATICA_FK_05  IS 'DMALM_AREA_TEMATICA_FK_05';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DMALM_USER_FK_06           IS 'DMALM_USER_FK_06';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.STG_PK                     IS 'STG_PK';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.URI_CLASSIFICATORE         IS 'URI_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CF_AREA                    IS 'CF_AREA';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CF_AMBITO                  IS 'CF_AMBITO';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CF_SCHEDA_SERVIZIO         IS 'CF_SCHEDA_SERVIZIO';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CF_RIFERIMENTI             IS 'CF_RIFERIMENTI';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.RANK_STATO_CLASSIFICATORE  IS 'RANK_STATO_CLASSIFICATORE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.RANK_STATO_CLASSIF_MESE    IS 'RANK_STATO_CLASSIF_MESE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_CARICAMENTO_CLASSIF     IS 'DT_CARICAMENTO_CLASSIF';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_STORICIZZAZIONE         IS 'DT_STORICIZZAZIONE';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.ANNULLATO                  IS 'ANNULLATO';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.DT_ANNULLAMENTO            IS 'DT_ANNULLAMENTO';
COMMENT ON COLUMN T_DMALM_CLASSIFICATORE.CHANGED                    IS 'CHANGED';

CREATE UNIQUE INDEX T_DMALM_CLASSIF_DEMAND_PK ON T_DMALM_CLASSIFICATORE
(DMALM_CLASSIFICATORE_PK)
    TABLESPACE "TS_ALM" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 65536 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
	
ALTER TABLE T_DMALM_CLASSIFICATORE ADD (
  CONSTRAINT T_DMALM_CLASSIF_DEMAND_PK
  PRIMARY KEY
  (DMALM_CLASSIFICATORE_PK)
  USING INDEX T_DMALM_CLASSIF_DEMAND_PK);
SPOOL OFF;