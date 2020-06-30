SPOOL 047_DMALM_RELEASE_DI_PROGETTO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RELEASE_DI_PROGETTO 
    ( 
     CD_RELEASEDIPROG VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RELEASEDIPROG DATE , 
     DT_RISOLUZIONE_RELEASEDIPROG DATE , 
     DT_MODIFICA_RELEASEDIPROG DATE , 
     DT_CAMBIO_STATO_RELEASEDIPROG DATE , 
     MOTIVO_RISOLUZIONE_REL_PROG VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RELEASEDIPROG VARCHAR2 (32 BYTE) , 
     DS_AUTORE_RELEASEDIPROG VARCHAR2 (50 BYTE) , 
     DT_SCADENZA_RELEASEDIPROG DATE , 
     TITOLO_RELEASEDIPROG VARCHAR2 (4000 BYTE) , 
     DMALM_RELEASEDIPROG_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_RELEASEDIPROG NUMBER , 
     DT_CARICAMENTO_RELEASEDIPROG DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
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
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_RELEASE_DI_PROGETTO IS 'DMALM_RELEASE_DI_PROGETTO         ';
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.CD_RELEASEDIPROG IS 'CD_RELEASEDIPROG                  '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_CREAZIONE_RELEASEDIPROG IS 'DT_CREAZIONE_RELEASEDIPROG        '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_RISOLUZIONE_RELEASEDIPROG IS 'DT_RISOLUZIONE_RELEASEDIPROG      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_MODIFICA_RELEASEDIPROG IS 'DT_MODIFICA_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_CAMBIO_STATO_RELEASEDIPROG IS 'DT_CAMBIO_STATO_RELEASEDIPROG     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.MOTIVO_RISOLUZIONE_REL_PROG IS 'MOTIVO_RISOLUZIONE_REL_PROG       '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.ID_AUTORE_RELEASEDIPROG IS 'ID_AUTORE_RELEASEDIPROG           '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DS_AUTORE_RELEASEDIPROG IS 'DS_AUTORE_RELEASEDIPROG           '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_SCADENZA_RELEASEDIPROG IS 'DT_SCADENZA_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.TITOLO_RELEASEDIPROG IS 'TITOLO_RELEASEDIPROG              '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_RELEASEDIPROG_PK IS 'DMALM_RELEASEDIPROG_PK            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.RANK_STATO_RELEASEDIPROG IS 'RANK_STATO_RELEASEDIPROG          '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_CARICAMENTO_RELEASEDIPROG IS 'DT_CARICAMENTO_RELEASEDIPROG      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.RANK_STATO_RELEASEDIPROG_MESE IS 'RANK_STATO_RELEASEDIPROG_MESE     '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DESCRIZIONE_RELEASEDIPROG IS 'DESCRIZIONE_RELEASEDIPROG         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DATA_PASSAGGIO_IN_ESERCIZIO IS 'DATA_PASSAGGIO_IN_ESERCIZIO       '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.FR IS 'FR                                '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DATA_DISPONIBILITA_EFF IS 'DATA_DISPONIBILITA_EFF            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.FORNITURA IS 'FORNITURA                         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.VERSIONE IS 'VERSIONE                          '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE INDEX IDX_RELEASE_DI_PROGETTO ON DMALM_RELEASE_DI_PROGETTO 
    ( 
     DT_STORICIZZAZIONE ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
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
CREATE UNIQUE INDEX SYS_C0011291 ON DMALM_RELEASE_DI_PROGETTO 
    ( 
     DMALM_RELEASEDIPROG_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 2097152 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_RELEASE_DI_PROGETTO 
    ADD CONSTRAINT DMALM_RELEASE_DI_PROGETTO_PK PRIMARY KEY ( DMALM_RELEASEDIPROG_PK ) 
    USING INDEX SYS_C0011291 ;
ALTER TABLE DMALM_RELEASE_DI_PROGETTO 
    ADD CONSTRAINT RELEASE_DI_PROGETTO_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_DI_PROGETTO 
    ADD CONSTRAINT RELEASE_DI_PROGETTO_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_DI_PROGETTO 
    ADD CONSTRAINT RELEASE_DI_PROGETTO_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_DI_PROGETTO 
    ADD CONSTRAINT RELEASE_DI_PROGETTO_FK_06 FOREIGN KEY 
    ( 
     DMALM_USER_FK_06
    ) 
    REFERENCES DMALM_USER 
    ( 
     DMALM_USER_PK
    ) 
    NOT DEFERRABLE 
;
SPOOL OFF;
