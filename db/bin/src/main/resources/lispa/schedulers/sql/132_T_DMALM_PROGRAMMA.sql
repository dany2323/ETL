SPOOL 132_T_DMALM_PROGRAMMA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_PROGRAMMA 
    ( 
     CD_PROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_PROGRAMMA DATE , 
     DT_RISOLUZIONE_PROGRAMMA DATE , 
     DT_MODIFICA_PROGRAMMA DATE , 
     DT_CAMBIO_STATO_PROGRAMMA DATE , 
     MOTIVO_RISOLUZIONE_PROGRAMMA VARCHAR2 (255 BYTE) , 
     ID_AUTORE_PROGRAMMA VARCHAR2 (100 BYTE) , 
     DS_AUTORE_PROGRAMMA VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_PROGRAMMA DATE , 
     TITOLO_PROGRAMMA VARCHAR2 (4000 BYTE) , 
     DMALM_PROGRAMMA_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_PROGRAMMA NUMBER , 
     DT_CARICAMENTO_PROGRAMMA DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_PROGRAMMA_MESE NUMBER (5) , 
     DESCRIZIONE_PROGRAMMA CLOB , 
     NUMERO_LINEA VARCHAR2 (50 BYTE) , 
     NUMERO_TESTATA VARCHAR2 (50 BYTE) , 
     ASSIGNEE VARCHAR2 (4000 BYTE) , 
     CF_TIPOLOGIA VARCHAR2 (50 BYTE) , 
     CF_SERVICE_MANAGER VARCHAR2 (40 BYTE) , 
     CF_REFERENTE_REGIONALE VARCHAR2 (50 BYTE) , 
     CF_CONTRATTO VARCHAR2 (100 BYTE) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) , 
     CODICE VARCHAR2 (200 BYTE) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE T_DMALM_PROGRAMMA IS 'T_DMALM_PROGRAMMA                 ';
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CD_PROGRAMMA IS 'CD_PROGRAMMA                      '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_CREAZIONE_PROGRAMMA IS 'DT_CREAZIONE_PROGRAMMA            '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_RISOLUZIONE_PROGRAMMA IS 'DT_RISOLUZIONE_PROGRAMMA          '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_MODIFICA_PROGRAMMA IS 'DT_MODIFICA_PROGRAMMA             '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_CAMBIO_STATO_PROGRAMMA IS 'DT_CAMBIO_STATO_PROGRAMMA         '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.MOTIVO_RISOLUZIONE_PROGRAMMA IS 'MOTIVO_RISOLUZIONE_PROGRAMMA      '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.ID_AUTORE_PROGRAMMA IS 'ID_AUTORE_PROGRAMMA               '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DS_AUTORE_PROGRAMMA IS 'DS_AUTORE_PROGRAMMA               '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_SCADENZA_PROGRAMMA IS 'DT_SCADENZA_PROGRAMMA             '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.TITOLO_PROGRAMMA IS 'TITOLO_PROGRAMMA                  '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_PROGRAMMA_PK IS 'DMALM_PROGRAMMA_PK                '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.RANK_STATO_PROGRAMMA IS 'RANK_STATO_PROGRAMMA              '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_CARICAMENTO_PROGRAMMA IS 'DT_CARICAMENTO_PROGRAMMA          '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.RANK_STATO_PROGRAMMA_MESE IS 'RANK_STATO_PROGRAMMA_MESE         '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DESCRIZIONE_PROGRAMMA IS 'DESCRIZIONE_PROGRAMMA             '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.ASSIGNEE IS 'ASSIGNEE                          '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CF_TIPOLOGIA IS 'CF_TIPOLOGIA                      '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CF_SERVICE_MANAGER IS 'CF_SERVICE_MANAGER                '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CF_REFERENTE_REGIONALE IS 'CF_REFERENTE_REGIONALE            '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CF_CONTRATTO IS 'CF_CONTRATTO                      '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
COMMENT ON COLUMN T_DMALM_PROGRAMMA.CODICE IS 'CODICE                            '; 
CREATE UNIQUE INDEX PK_T_DMALM_PROGRAMMA ON T_DMALM_PROGRAMMA 
    ( 
     DMALM_PROGRAMMA_PK ASC 
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
ALTER TABLE T_DMALM_PROGRAMMA 
    ADD CONSTRAINT PK_T_DMALM_PROGRAMMA PRIMARY KEY ( DMALM_PROGRAMMA_PK ) 
    USING INDEX PK_T_DMALM_PROGRAMMA ;
;
;
;
;
SPOOL OFF;