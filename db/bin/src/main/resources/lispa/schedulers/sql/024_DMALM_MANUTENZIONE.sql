SPOOL 024_DMALM_MANUTENZIONE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_MANUTENZIONE 
    ( 
     CD_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_MANUTENZIONE DATE , 
     DT_RISOLUZIONE_MANUTENZIONE DATE , 
     DT_MODIFICA_MANUTENZIONE DATE , 
     DT_CAMBIO_STATO_MANUTENZIONE DATE , 
     MOTIVO_RISOLUZIONE_MANUTENZION VARCHAR2 (255 BYTE) , 
     ID_AUTORE_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DS_AUTORE_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_MANUTENZIONE DATE , 
     TITOLO_MANUTENZIONE VARCHAR2 (4000 BYTE) , 
     DMALM_MANUTENZIONE_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_MANUTENZIONE NUMBER , 
     DT_CARICAMENTO_MANUTENZIONE DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_MANUTENZIONE_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_MANUTENZIONE CLOB , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     TEMPO_TOTALE_RISOLUZIONE NUMBER (8) , 
     SEVERITY_MANUTENZIONE VARCHAR2 (20 BYTE) , 
     PRIORITY_MANUTENZIONE VARCHAR2 (20 BYTE) , 
     ASSIGNEES_MANUTENZIONE VARCHAR2 (4000 BYTE) , 
     NUMERO_TESTATA VARCHAR2 (200 BYTE) , 
     NUMERO_LINEA VARCHAR2 (200 BYTE) , 
     CODICE VARCHAR2 (200 BYTE) , 
     DATA_INIZIO TIMESTAMP , 
     DATA_INIZIO_EFF TIMESTAMP , 
     DATA_DISPONIBILITA TIMESTAMP , 
     DATA_DISP_EFF TIMESTAMP , 
     FORNITURA VARCHAR2 (80 BYTE) , 
     DATA_RILASCIO_IN_ES TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_MANUTENZIONE IS 'DMALM_MANUTENZIONE                ';
COMMENT ON COLUMN DMALM_MANUTENZIONE.CD_MANUTENZIONE IS 'CD_MANUTENZIONE                   '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_CREAZIONE_MANUTENZIONE IS 'DT_CREAZIONE_MANUTENZIONE         '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_RISOLUZIONE_MANUTENZIONE IS 'DT_RISOLUZIONE_MANUTENZIONE       '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_MODIFICA_MANUTENZIONE IS 'DT_MODIFICA_MANUTENZIONE          '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_CAMBIO_STATO_MANUTENZIONE IS 'DT_CAMBIO_STATO_MANUTENZIONE      '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.MOTIVO_RISOLUZIONE_MANUTENZION IS 'MOTIVO_RISOLUZIONE_MANUTENZION    '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.ID_AUTORE_MANUTENZIONE IS 'ID_AUTORE_MANUTENZIONE            '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DS_AUTORE_MANUTENZIONE IS 'DS_AUTORE_MANUTENZIONE            '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_SCADENZA_MANUTENZIONE IS 'DT_SCADENZA_MANUTENZIONE          '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.TITOLO_MANUTENZIONE IS 'TITOLO_MANUTENZIONE               '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_MANUTENZIONE_PK IS 'DMALM_MANUTENZIONE_PK             '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.RANK_STATO_MANUTENZIONE IS 'RANK_STATO_MANUTENZIONE           '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_CARICAMENTO_MANUTENZIONE IS 'DT_CARICAMENTO_MANUTENZIONE       '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.RANK_STATO_MANUTENZIONE_MESE IS 'RANK_STATO_MANUTENZIONE_MESE      '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DESCRIZIONE_MANUTENZIONE IS 'DESCRIZIONE_MANUTENZIONE          '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.TEMPO_TOTALE_RISOLUZIONE IS 'TEMPO_TOTALE_RISOLUZIONE          '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.SEVERITY_MANUTENZIONE IS 'SEVERITY_MANUTENZIONE             '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.PRIORITY_MANUTENZIONE IS 'PRIORITY_MANUTENZIONE             '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.ASSIGNEES_MANUTENZIONE IS 'ASSIGNEES_MANUTENZIONE            '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DATA_INIZIO IS 'DATA_INIZIO                       '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DATA_INIZIO_EFF IS 'DATA_INIZIO_EFF                   '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DATA_DISPONIBILITA IS 'DATA_DISPONIBILITA                '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DATA_DISP_EFF IS 'DATA_DISP_EFF                     '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.FORNITURA IS 'FORNITURA                         '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DATA_RILASCIO_IN_ES IS 'DATA_RILASCIO_IN_ES               '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_MANUTENZIONE.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011254 ON DMALM_MANUTENZIONE 
    ( 
     DMALM_MANUTENZIONE_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 917504 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_MANUTENZIONE ON DMALM_MANUTENZIONE 
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
ALTER TABLE DMALM_MANUTENZIONE 
    ADD CONSTRAINT DMALM_MANUTENZIONE_PK PRIMARY KEY ( DMALM_MANUTENZIONE_PK ) 
    USING INDEX SYS_C0011254 ;
ALTER TABLE DMALM_MANUTENZIONE 
    ADD CONSTRAINT MANUTENZIONE_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_MANUTENZIONE 
    ADD CONSTRAINT MANUTENZIONE_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_MANUTENZIONE 
    ADD CONSTRAINT MANUTENZIONE_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_MANUTENZIONE 
    ADD CONSTRAINT MANUTENZIONE_FK_06 FOREIGN KEY 
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