SPOOL 080_DMALM_SOTTOPROGRAMMA_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SOTTOPROGRAMMA_ODS 
    ( 
     CD_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_SOTTOPROGRAMMA TIMESTAMP , 
     DT_RISOLUZIONE_SOTTOPROGRAMMA TIMESTAMP , 
     DT_MODIFICA_SOTTOPROGRAMMA TIMESTAMP , 
     DT_CAMBIO_STATO_SOTTOPROGRAMMA TIMESTAMP , 
     MOTIVO_RISOLUZIONE_SOTTOPROGR VARCHAR2 (255 BYTE) , 
     ID_AUTORE_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DS_AUTORE_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_SOTTOPROGRAMMA TIMESTAMP , 
     TITOLO_SOTTOPROGRAMMA VARCHAR2 (4000 BYTE) , 
     DMALM_SOTTOPROGRAMMA_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_SOTTOPROGRAMMA NUMBER (1) , 
     DT_CARICAMENTO_SOTTOPROGRAMMA TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_SOTTOPROGRAMMA_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_SOTTOPROGRAMMA CLOB , 
     CODICE VARCHAR2 (200 BYTE) , 
     NUMERO_TESTATA VARCHAR2 (50 BYTE) , 
     NUMERO_LINEA VARCHAR2 (50 BYTE) , 
     DT_COMPLETAMENTO TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_SOTTOPROGRAMMA_ODS IS 'DMALM_SOTTOPROGRAMMA_ODS          ';
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.CD_SOTTOPROGRAMMA IS 'CD_SOTTOPROGRAMMA                 '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_CREAZIONE_SOTTOPROGRAMMA IS 'DT_CREAZIONE_SOTTOPROGRAMMA       '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_RISOLUZIONE_SOTTOPROGRAMMA IS 'DT_RISOLUZIONE_SOTTOPROGRAMMA     '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_MODIFICA_SOTTOPROGRAMMA IS 'DT_MODIFICA_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_CAMBIO_STATO_SOTTOPROGRAMMA IS 'DT_CAMBIO_STATO_SOTTOPROGRAMMA    '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.MOTIVO_RISOLUZIONE_SOTTOPROGR IS 'MOTIVO_RISOLUZIONE_SOTTOPROGR     '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.ID_AUTORE_SOTTOPROGRAMMA IS 'ID_AUTORE_SOTTOPROGRAMMA          '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DS_AUTORE_SOTTOPROGRAMMA IS 'DS_AUTORE_SOTTOPROGRAMMA          '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_SCADENZA_SOTTOPROGRAMMA IS 'DT_SCADENZA_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.TITOLO_SOTTOPROGRAMMA IS 'TITOLO_SOTTOPROGRAMMA             '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_SOTTOPROGRAMMA_PK IS 'DMALM_SOTTOPROGRAMMA_PK           '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.RANK_STATO_SOTTOPROGRAMMA IS 'RANK_STATO_SOTTOPROGRAMMA         '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_CARICAMENTO_SOTTOPROGRAMMA IS 'DT_CARICAMENTO_SOTTOPROGRAMMA     '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.RANK_STATO_SOTTOPROGRAMMA_MESE IS 'RANK_STATO_SOTTOPROGRAMMA_MESE    '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DESCRIZIONE_SOTTOPROGRAMMA IS 'DESCRIZIONE_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DT_COMPLETAMENTO IS 'DT_COMPLETAMENTO                  '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011381 ON DMALM_SOTTOPROGRAMMA_ODS 
    ( 
     DMALM_SOTTOPROGRAMMA_PK ASC 
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
ALTER TABLE DMALM_SOTTOPROGRAMMA_ODS 
    ADD CONSTRAINT DMALM_SOTTOPROGRAMMA_ODS_PK PRIMARY KEY ( DMALM_SOTTOPROGRAMMA_PK ) 
    USING INDEX SYS_C0011381 ;
;
;
;
;
SPOOL OFF;
