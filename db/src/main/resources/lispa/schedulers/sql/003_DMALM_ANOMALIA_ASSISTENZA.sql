SPOOL 003_DMALM_ANOMALIA_ASSISTENZA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_ANOMALIA_ASSISTENZA 
    ( 
     CD_ANOMALIA_ASS VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_ANOMALIA_ASS DATE , 
     DT_RISOLUZIONE_ANOMALIA_ASS DATE , 
     DT_MODIFICA_ANOMALIA_ASS DATE , 
     DT_CAMBIO_STATO_ANOMALIA_ASS DATE , 
     MOTIVO_RISOLUZIONE_ANOMALIA_AS VARCHAR2 (255 BYTE) , 
     ID_AUTORE_ANOMALIA_ASS VARCHAR2 (100 BYTE) , 
     DS_AUTORE_ANOMALIA_ASS VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_ANOMALIA_ASS DATE , 
     TITOLO_ANOMALIA_ASS VARCHAR2 (4000 BYTE) , 
     DMALM_ANOMALIA_ASS_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_ANOMALIA_ASS NUMBER , 
     DT_CARICAMENTO_ANOMALIA_ASS DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_ANOMALIA_ASS_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_ANOMALIA_ASS CLOB , 
     TEMPO_TOTALE_RISOLUZIONE NUMBER (8) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     TICKETID VARCHAR2 (4000 BYTE) , 
     CA VARCHAR2 (20 BYTE) , 
     CS VARCHAR2 (20 BYTE) , 
     PROD_COD VARCHAR2 (300 BYTE) , 
     FREQUENZA VARCHAR2 (200 BYTE) , 
     SEGNALAZIONI VARCHAR2 (50 BYTE) , 
     ST_CHIUSO TIMESTAMP , 
     SO VARCHAR2 (200 BYTE) , 
     PLATFORM VARCHAR2 (200 BYTE) , 
     AOID VARCHAR2 (200 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     SEVERITY VARCHAR2 (20 BYTE) , 
     PRIORITY VARCHAR2 (20 BYTE) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_ANOMALIA_ASSISTENZA IS 'DMALM_ANOMALIA_ASSISTENZA         ';
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.CD_ANOMALIA_ASS IS 'CD_ANOMALIA_ASS                   '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_CREAZIONE_ANOMALIA_ASS IS 'DT_CREAZIONE_ANOMALIA_ASS         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_RISOLUZIONE_ANOMALIA_ASS IS 'DT_RISOLUZIONE_ANOMALIA_ASS       '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_MODIFICA_ANOMALIA_ASS IS 'DT_MODIFICA_ANOMALIA_ASS          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_CAMBIO_STATO_ANOMALIA_ASS IS 'DT_CAMBIO_STATO_ANOMALIA_ASS      '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.MOTIVO_RISOLUZIONE_ANOMALIA_AS IS 'MOTIVO_RISOLUZIONE_ANOMALIA_AS    '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.ID_AUTORE_ANOMALIA_ASS IS 'ID_AUTORE_ANOMALIA_ASS            '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DS_AUTORE_ANOMALIA_ASS IS 'DS_AUTORE_ANOMALIA_ASS            '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_SCADENZA_ANOMALIA_ASS IS 'DT_SCADENZA_ANOMALIA_ASS          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.TITOLO_ANOMALIA_ASS IS 'TITOLO_ANOMALIA_ASS               '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_ANOMALIA_ASS_PK IS 'DMALM_ANOMALIA_ASS_PK             '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.RANK_STATO_ANOMALIA_ASS IS 'RANK_STATO_ANOMALIA_ASS           '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_CARICAMENTO_ANOMALIA_ASS IS 'DT_CARICAMENTO_ANOMALIA_ASS       '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.RANK_STATO_ANOMALIA_ASS_MESE IS 'RANK_STATO_ANOMALIA_ASS_MESE      '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DESCRIZIONE_ANOMALIA_ASS IS 'DESCRIZIONE_ANOMALIA_ASS          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.TEMPO_TOTALE_RISOLUZIONE IS 'TEMPO_TOTALE_RISOLUZIONE          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.TICKETID IS 'TICKETID                          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.CA IS 'CA                                '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.CS IS 'CS                                '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.PROD_COD IS 'PROD_COD                          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.FREQUENZA IS 'FREQUENZA                         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.SEGNALAZIONI IS 'SEGNALAZIONI                      '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.ST_CHIUSO IS 'ST_CHIUSO                         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.SO IS 'SO                                '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.PLATFORM IS 'PLATFORM                          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.AOID IS 'AOID                              '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.SEVERITY IS 'SEVERITY                          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.PRIORITY IS 'PRIORITY                          '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE INDEX IDX_ANOMALIA_ASSISTENZA ON DMALM_ANOMALIA_ASSISTENZA 
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
CREATE UNIQUE INDEX SYS_C0011222 ON DMALM_ANOMALIA_ASSISTENZA 
    ( 
     DMALM_ANOMALIA_ASS_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 589824 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA 
    ADD CONSTRAINT DMALM_ANOMALIA_ASSISTENZA_PK PRIMARY KEY ( DMALM_ANOMALIA_ASS_PK ) 
    USING INDEX SYS_C0011222 ;
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA 
    ADD CONSTRAINT ANOMALIA_ASSISTENZA_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA 
    ADD CONSTRAINT ANOMALIA_ASSISTENZA_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA 
    ADD CONSTRAINT ANOMALIA_ASSISTENZA_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA 
    ADD CONSTRAINT ANOMALIA_ASSISTENZA_FK_06 FOREIGN KEY 
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
