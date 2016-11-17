SPOOL 140_T_DMALM_SOTTOPROGRAMMA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_SOTTOPROGRAMMA 
    ( 
     CD_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_SOTTOPROGRAMMA DATE , 
     DT_RISOLUZIONE_SOTTOPROGRAMMA DATE , 
     DT_MODIFICA_SOTTOPROGRAMMA DATE , 
     DT_CAMBIO_STATO_SOTTOPROGRAMMA DATE , 
     MOTIVO_RISOLUZIONE_SOTTOPROGR VARCHAR2 (255 BYTE) , 
     ID_AUTORE_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DS_AUTORE_SOTTOPROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_SOTTOPROGRAMMA DATE , 
     TITOLO_SOTTOPROGRAMMA VARCHAR2 (4000 BYTE) , 
     DMALM_SOTTOPROGRAMMA_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_SOTTOPROGRAMMA NUMBER , 
     DT_CARICAMENTO_SOTTOPROGRAMMA DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_SOTTOPROGRAMMA_MESE NUMBER (5) , 
     DESCRIZIONE_SOTTOPROGRAMMA CLOB , 
     CODICE VARCHAR2 (200 BYTE) , 
     NUMERO_TESTATA VARCHAR2 (50 BYTE) , 
     NUMERO_LINEA VARCHAR2 (50 BYTE) , 
     DT_COMPLETAMENTO TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE T_DMALM_SOTTOPROGRAMMA IS 'T_DMALM_SOTTOPROGRAMMA            ';
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.CD_SOTTOPROGRAMMA IS 'CD_SOTTOPROGRAMMA                 '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_CREAZIONE_SOTTOPROGRAMMA IS 'DT_CREAZIONE_SOTTOPROGRAMMA       '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_RISOLUZIONE_SOTTOPROGRAMMA IS 'DT_RISOLUZIONE_SOTTOPROGRAMMA     '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_MODIFICA_SOTTOPROGRAMMA IS 'DT_MODIFICA_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_CAMBIO_STATO_SOTTOPROGRAMMA IS 'DT_CAMBIO_STATO_SOTTOPROGRAMMA    '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.MOTIVO_RISOLUZIONE_SOTTOPROGR IS 'MOTIVO_RISOLUZIONE_SOTTOPROGR     '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.ID_AUTORE_SOTTOPROGRAMMA IS 'ID_AUTORE_SOTTOPROGRAMMA          '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DS_AUTORE_SOTTOPROGRAMMA IS 'DS_AUTORE_SOTTOPROGRAMMA          '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_SCADENZA_SOTTOPROGRAMMA IS 'DT_SCADENZA_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.TITOLO_SOTTOPROGRAMMA IS 'TITOLO_SOTTOPROGRAMMA             '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_SOTTOPROGRAMMA_PK IS 'DMALM_SOTTOPROGRAMMA_PK           '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.RANK_STATO_SOTTOPROGRAMMA IS 'RANK_STATO_SOTTOPROGRAMMA         '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_CARICAMENTO_SOTTOPROGRAMMA IS 'DT_CARICAMENTO_SOTTOPROGRAMMA     '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.RANK_STATO_SOTTOPROGRAMMA_MESE IS 'RANK_STATO_SOTTOPROGRAMMA_MESE    '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DESCRIZIONE_SOTTOPROGRAMMA IS 'DESCRIZIONE_SOTTOPROGRAMMA        '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DT_COMPLETAMENTO IS 'DT_COMPLETAMENTO                  '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN T_DMALM_SOTTOPROGRAMMA.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX PK_T_DMALM_SOTTOPROGRAMMA ON T_DMALM_SOTTOPROGRAMMA 
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
ALTER TABLE T_DMALM_SOTTOPROGRAMMA 
    ADD CONSTRAINT PK_T_DMALM_SOTTOPROGRAMMA PRIMARY KEY ( DMALM_SOTTOPROGRAMMA_PK ) 
    USING INDEX PK_T_DMALM_SOTTOPROGRAMMA ;
;
;
;
;
SPOOL OFF;
