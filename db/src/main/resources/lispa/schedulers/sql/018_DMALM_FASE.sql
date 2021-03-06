SPOOL 018_DMALM_FASE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_FASE 
    ( 
     CD_FASE VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_FASE DATE , 
     DT_RISOLUZIONE_FASE DATE , 
     DT_MODIFICA_FASE DATE , 
     DT_CAMBIO_STATO_FASE DATE , 
     MOTIVO_RISOLUZIONE_FASE VARCHAR2 (255 BYTE) , 
     ID_AUTORE_FASE VARCHAR2 (100 BYTE) , 
     DS_AUTORE_FASE VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_FASE DATE , 
     TITOLO_FASE VARCHAR2 (4000 BYTE) , 
     DMALM_FASE_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_FASE NUMBER , 
     DT_CARICAMENTO_FASE DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_FASE_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_FASE CLOB , 
     APPLICABILE NUMBER (1) , 
     CODICE VARCHAR2 (200 BYTE) , 
     DATA_INIZIO_PIANIFICATO TIMESTAMP , 
     DATA_FINE_PIANIFICATA TIMESTAMP , 
     DATA_INIZIO_EFFETTIVO TIMESTAMP , 
     DATA_FINE_EFFETTIVA TIMESTAMP , 
     DATA_INIZIO_BASELINE TIMESTAMP , 
     DATA_FINE_BASELINE TIMESTAMP , 
     DATA_PASSAGGIO_IN_ESECUZIONE TIMESTAMP , 
     DURATA_EFFETTIVA_FASE NUMBER (5) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_FASE IS 'DMALM_FASE                        ';
COMMENT ON COLUMN DMALM_FASE.CD_FASE IS 'CD_FASE                           '; 
COMMENT ON COLUMN DMALM_FASE.DT_CREAZIONE_FASE IS 'DT_CREAZIONE_FASE                 '; 
COMMENT ON COLUMN DMALM_FASE.DT_RISOLUZIONE_FASE IS 'DT_RISOLUZIONE_FASE               '; 
COMMENT ON COLUMN DMALM_FASE.DT_MODIFICA_FASE IS 'DT_MODIFICA_FASE                  '; 
COMMENT ON COLUMN DMALM_FASE.DT_CAMBIO_STATO_FASE IS 'DT_CAMBIO_STATO_FASE              '; 
COMMENT ON COLUMN DMALM_FASE.MOTIVO_RISOLUZIONE_FASE IS 'MOTIVO_RISOLUZIONE_FASE           '; 
COMMENT ON COLUMN DMALM_FASE.ID_AUTORE_FASE IS 'ID_AUTORE_FASE                    '; 
COMMENT ON COLUMN DMALM_FASE.DS_AUTORE_FASE IS 'DS_AUTORE_FASE                    '; 
COMMENT ON COLUMN DMALM_FASE.DT_SCADENZA_FASE IS 'DT_SCADENZA_FASE                  '; 
COMMENT ON COLUMN DMALM_FASE.TITOLO_FASE IS 'TITOLO_FASE                       '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_FASE_PK IS 'DMALM_FASE_PK                     '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_FASE.RANK_STATO_FASE IS 'RANK_STATO_FASE                   '; 
COMMENT ON COLUMN DMALM_FASE.DT_CARICAMENTO_FASE IS 'DT_CARICAMENTO_FASE               '; 
COMMENT ON COLUMN DMALM_FASE.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_FASE.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_FASE.RANK_STATO_FASE_MESE IS 'RANK_STATO_FASE_MESE              '; 
COMMENT ON COLUMN DMALM_FASE.DESCRIZIONE_FASE IS 'DESCRIZIONE_FASE                  '; 
COMMENT ON COLUMN DMALM_FASE.APPLICABILE IS 'APPLICABILE                       '; 
COMMENT ON COLUMN DMALM_FASE.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_FASE.DATA_INIZIO_PIANIFICATO IS 'DATA_INIZIO_PIANIFICATO           '; 
COMMENT ON COLUMN DMALM_FASE.DATA_FINE_PIANIFICATA IS 'DATA_FINE_PIANIFICATA             '; 
COMMENT ON COLUMN DMALM_FASE.DATA_INIZIO_EFFETTIVO IS 'DATA_INIZIO_EFFETTIVO             '; 
COMMENT ON COLUMN DMALM_FASE.DATA_FINE_EFFETTIVA IS 'DATA_FINE_EFFETTIVA               '; 
COMMENT ON COLUMN DMALM_FASE.DATA_INIZIO_BASELINE IS 'DATA_INIZIO_BASELINE              '; 
COMMENT ON COLUMN DMALM_FASE.DATA_FINE_BASELINE IS 'DATA_FINE_BASELINE                '; 
COMMENT ON COLUMN DMALM_FASE.DATA_PASSAGGIO_IN_ESECUZIONE IS 'DATA_PASSAGGIO_IN_ESECUZIONE      '; 
COMMENT ON COLUMN DMALM_FASE.DURATA_EFFETTIVA_FASE IS 'DURATA_EFFETTIVA_FASE             '; 
COMMENT ON COLUMN DMALM_FASE.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_FASE.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_FASE.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011248 ON DMALM_FASE 
    ( 
     DMALM_FASE_PK ASC 
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
CREATE INDEX IDX_FASE ON DMALM_FASE 
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
ALTER TABLE DMALM_FASE 
    ADD CONSTRAINT DMALM_FASE_PK PRIMARY KEY ( DMALM_FASE_PK ) 
    USING INDEX SYS_C0011248 ;
ALTER TABLE DMALM_FASE 
    ADD CONSTRAINT FASE_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_FASE 
    ADD CONSTRAINT FASE_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_FASE 
    ADD CONSTRAINT FASE_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_FASE 
    ADD CONSTRAINT FASE_FK_06 FOREIGN KEY 
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
