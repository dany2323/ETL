SPOOL 009_DMALM_BUILD.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_BUILD 
    ( 
     CD_BUILD VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_BUILD DATE , 
     DT_RISOLUZIONE_BUILD DATE , 
     DT_MODIFICA_BUILD DATE , 
     DT_CAMBIO_STATO_BUILD DATE , 
     MOTIVO_RISOLUZIONE_BUILD VARCHAR2 (255 BYTE) , 
     ID_AUTORE_BUILD VARCHAR2 (100 BYTE) , 
     DS_AUTORE_BUILD VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_BUILD DATE , 
     TITOLO_BUILD VARCHAR2 (4000 BYTE) , 
     DMALM_BUILD_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_BUILD NUMBER , 
     DT_CARICAMENTO_BUILD DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_BUILD_MESE NUMBER (5) DEFAULT 0 , 
     CODICE VARCHAR2 (200 BYTE) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DESCRIZIONE_BUILD CLOB , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_BUILD IS 'DMALM_BUILD                       ';
COMMENT ON COLUMN DMALM_BUILD.CD_BUILD IS 'CD_BUILD                          '; 
COMMENT ON COLUMN DMALM_BUILD.DT_CREAZIONE_BUILD IS 'DT_CREAZIONE_BUILD                '; 
COMMENT ON COLUMN DMALM_BUILD.DT_RISOLUZIONE_BUILD IS 'DT_RISOLUZIONE_BUILD              '; 
COMMENT ON COLUMN DMALM_BUILD.DT_MODIFICA_BUILD IS 'DT_MODIFICA_BUILD                 '; 
COMMENT ON COLUMN DMALM_BUILD.DT_CAMBIO_STATO_BUILD IS 'DT_CAMBIO_STATO_BUILD             '; 
COMMENT ON COLUMN DMALM_BUILD.MOTIVO_RISOLUZIONE_BUILD IS 'MOTIVO_RISOLUZIONE_BUILD          '; 
COMMENT ON COLUMN DMALM_BUILD.ID_AUTORE_BUILD IS 'ID_AUTORE_BUILD                   '; 
COMMENT ON COLUMN DMALM_BUILD.DS_AUTORE_BUILD IS 'DS_AUTORE_BUILD                   '; 
COMMENT ON COLUMN DMALM_BUILD.DT_SCADENZA_BUILD IS 'DT_SCADENZA_BUILD                 '; 
COMMENT ON COLUMN DMALM_BUILD.TITOLO_BUILD IS 'TITOLO_BUILD                      '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_BUILD_PK IS 'DMALM_BUILD_PK                    '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_BUILD.RANK_STATO_BUILD IS 'RANK_STATO_BUILD                  '; 
COMMENT ON COLUMN DMALM_BUILD.DT_CARICAMENTO_BUILD IS 'DT_CARICAMENTO_BUILD              '; 
COMMENT ON COLUMN DMALM_BUILD.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_BUILD.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_BUILD.RANK_STATO_BUILD_MESE IS 'RANK_STATO_BUILD_MESE             '; 
COMMENT ON COLUMN DMALM_BUILD.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_BUILD.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_BUILD.DESCRIZIONE_BUILD IS 'DESCRIZIONE_BUILD                 '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_BUILD.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_BUILD.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE INDEX IDX_BUILD ON DMALM_BUILD 
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
CREATE UNIQUE INDEX SYS_C0011227 ON DMALM_BUILD 
    ( 
     DMALM_BUILD_PK ASC 
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
ALTER TABLE DMALM_BUILD 
    ADD CONSTRAINT DMALM_BUILD_PK PRIMARY KEY ( DMALM_BUILD_PK ) 
    USING INDEX SYS_C0011227 ;
ALTER TABLE DMALM_BUILD 
    ADD CONSTRAINT BUILD_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_BUILD 
    ADD CONSTRAINT BUILD_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_BUILD 
    ADD CONSTRAINT BUILD_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_BUILD 
    ADD CONSTRAINT BUILD_FK_06 FOREIGN KEY 
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
