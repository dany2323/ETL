SPOOL 015_DMALM_DOCUMENTO_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_DOCUMENTO_ODS 
    ( 
     CD_DOCUMENTO VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_DOCUMENTO TIMESTAMP , 
     DT_RISOLUZIONE_DOCUMENTO TIMESTAMP , 
     DT_MODIFICA_DOCUMENTO TIMESTAMP , 
     DT_CAMBIO_STATO_DOCUMENTO TIMESTAMP , 
     MOTIVO_RISOLUZIONE_DOCUMENTO VARCHAR2 (255 BYTE) , 
     ID_AUTORE_DOCUMENTO VARCHAR2 (100 BYTE) , 
     DS_AUTORE_DOCUMENTO VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_DOCUMENTO TIMESTAMP , 
     TITOLO_DOCUMENTO VARCHAR2 (4000 BYTE) , 
     DMALM_DOCUMENTO_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_DOCUMENTO NUMBER (1) , 
     DT_CARICAMENTO_DOCUMENTO TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_DOCUMENTO_MESE NUMBER (5) DEFAULT 0 , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     NUMERO_TESTATA VARCHAR2 (200 BYTE) , 
     NUMERO_LINEA VARCHAR2 (200 BYTE) , 
     DESCRIZIONE_DOCUMENTO CLOB , 
     ASSIGNEES_DOCUMENTO VARCHAR2 (4000 BYTE) , 
     CLASSIFICAZIONE VARCHAR2 (400 BYTE) , 
     VERSIONE VARCHAR2 (400 BYTE) , 
     TIPO VARCHAR2 (50 BYTE) , 
     CODICE VARCHAR2 (200 BYTE) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_DOCUMENTO_ODS IS 'DMALM_DOCUMENTO_ODS               ';
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.CD_DOCUMENTO IS 'CD_DOCUMENTO                      '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_CREAZIONE_DOCUMENTO IS 'DT_CREAZIONE_DOCUMENTO            '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_RISOLUZIONE_DOCUMENTO IS 'DT_RISOLUZIONE_DOCUMENTO          '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_MODIFICA_DOCUMENTO IS 'DT_MODIFICA_DOCUMENTO             '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_CAMBIO_STATO_DOCUMENTO IS 'DT_CAMBIO_STATO_DOCUMENTO         '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.MOTIVO_RISOLUZIONE_DOCUMENTO IS 'MOTIVO_RISOLUZIONE_DOCUMENTO      '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.ID_AUTORE_DOCUMENTO IS 'ID_AUTORE_DOCUMENTO               '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DS_AUTORE_DOCUMENTO IS 'DS_AUTORE_DOCUMENTO               '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_SCADENZA_DOCUMENTO IS 'DT_SCADENZA_DOCUMENTO             '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.TITOLO_DOCUMENTO IS 'TITOLO_DOCUMENTO                  '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_DOCUMENTO_PK IS 'DMALM_DOCUMENTO_PK                '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.RANK_STATO_DOCUMENTO IS 'RANK_STATO_DOCUMENTO              '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_CARICAMENTO_DOCUMENTO IS 'DT_CARICAMENTO_DOCUMENTO          '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.RANK_STATO_DOCUMENTO_MESE IS 'RANK_STATO_DOCUMENTO_MESE         '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.NUMERO_TESTATA IS 'NUMERO_TESTATA                    '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.NUMERO_LINEA IS 'NUMERO_LINEA                      '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DESCRIZIONE_DOCUMENTO IS 'DESCRIZIONE_DOCUMENTO             '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.ASSIGNEES_DOCUMENTO IS 'ASSIGNEES_DOCUMENTO               '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.CLASSIFICAZIONE IS 'CLASSIFICAZIONE                   '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.VERSIONE IS 'VERSIONE                          '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.TIPO IS 'TIPO                              '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_DOCUMENTO_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011240 ON DMALM_DOCUMENTO_ODS 
    ( 
     DMALM_DOCUMENTO_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 3145728 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_DOCUMENTO_ODS 
    ADD CONSTRAINT DMALM_DOCUMENTO_ODS_PK PRIMARY KEY ( DMALM_DOCUMENTO_PK ) 
    USING INDEX SYS_C0011240 ;
;
;
;
;
SPOOL OFF;
