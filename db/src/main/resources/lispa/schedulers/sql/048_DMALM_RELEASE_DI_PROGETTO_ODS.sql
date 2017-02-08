SPOOL 048_DMALM_RELEASE_DI_PROGETTO_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RELEASE_DI_PROGETTO_ODS 
    ( 
     CD_RELEASEDIPROG VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RELEASEDIPROG TIMESTAMP , 
     DT_RISOLUZIONE_RELEASEDIPROG TIMESTAMP , 
     DT_MODIFICA_RELEASEDIPROG TIMESTAMP , 
     DT_CAMBIO_STATO_RELEASEDIPROG TIMESTAMP , 
     MOTIVO_RISOLUZIONE_REL_PROG VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RELEASEDIPROG VARCHAR2 (32 BYTE) , 
     DS_AUTORE_RELEASEDIPROG VARCHAR2 (50 BYTE) , 
     DT_SCADENZA_RELEASEDIPROG TIMESTAMP , 
     TITOLO_RELEASEDIPROG VARCHAR2 (4000 BYTE) , 
     DMALM_RELEASEDIPROG_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_RELEASEDIPROG NUMBER (1) , 
     DT_CARICAMENTO_RELEASEDIPROG TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_RELEASEDIPROG_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_RELEASEDIPROG CLOB , 
     DATA_PASSAGGIO_IN_ESERCIZIO TIMESTAMP , 
     CODICE VARCHAR2 (200 BYTE) , 
     FR NUMBER (1) , 
     DATA_DISPONIBILITA_EFF TIMESTAMP , 
     FORNITURA VARCHAR2 (80 BYTE) , 
     VERSIONE VARCHAR2 (400 BYTE) , 
     NUMERO_LINEA VARCHAR2 (200 BYTE) , 
     NUMERO_TESTATA VARCHAR2 (200 BYTE) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_RELEASE_DI_PROGETTO_ODS IS 'DMALM_RELEASE_DI_PROGETTO_ODS     ';
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.CD_RELEASEDIPROG IS 'CD_RELEASEDIPROG                  '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_CREAZIONE_RELEASEDIPROG IS 'DT_CREAZIONE_RELEASEDIPROG        '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_RISOLUZIONE_RELEASEDIPROG IS 'DT_RISOLUZIONE_RELEASEDIPROG      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_MODIFICA_RELEASEDIPROG IS 'DT_MODIFICA_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_CAMBIO_STATO_RELEASEDIPROG IS 'DT_CAMBIO_STATO_RELEASEDIPROG     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.MOTIVO_RISOLUZIONE_REL_PROG IS 'MOTIVO_RISOLUZIONE_REL_PROG       '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.ID_AUTORE_RELEASEDIPROG IS 'ID_AUTORE_RELEASEDIPROG           '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DS_AUTORE_RELEASEDIPROG IS 'DS_AUTORE_RELEASEDIPROG           '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_SCADENZA_RELEASEDIPROG IS 'DT_SCADENZA_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.TITOLO_RELEASEDIPROG IS 'TITOLO_RELEASEDIPROG              '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_RELEASEDIPROG_PK IS 'DMALM_RELEASEDIPROG_PK            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.RANK_STATO_RELEASEDIPROG IS 'RANK_STATO_RELEASEDIPROG          '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_CARICAMENTO_RELEASEDIPROG IS 'DT_CARICAMENTO_RELEASEDIPROG      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.RANK_STATO_RELEASEDIPROG_MESE IS 'RANK_STATO_RELEASEDIPROG_MESE     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DESCRIZIONE_RELEASEDIPROG IS 'DESCRIZIONE_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DATA_PASSAGGIO_IN_ESERCIZIO IS 'DATA_PASSAGGIO_IN_ESERCIZIO       '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.FR IS 'FR                                '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DATA_DISPONIBILITA_EFF IS 'DATA_DISPONIBILITA_EFF            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.FORNITURA IS 'FORNITURA                         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.VERSIONE IS 'VERSIONE                          '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011293 ON DMALM_RELEASE_DI_PROGETTO_ODS 
    ( 
     DMALM_RELEASEDIPROG_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 5242880 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_RELEASE_DI_PROGETTO_ODS 
    ADD CONSTRAINT DMALM_RELEASE_DI_PROG_ODS_PK PRIMARY KEY ( DMALM_RELEASEDIPROG_PK ) 
    USING INDEX SYS_C0011293 ;
;
;
;
;
SPOOL OFF;
