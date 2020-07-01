SPOOL 054_DMALM_RICHIESTA_GESTIONE_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RICHIESTA_GESTIONE_ODS 
    ( 
     CD_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RICHIESTA_GEST TIMESTAMP , 
     DT_RISOLUZIONE_RICHIESTA_GEST TIMESTAMP , 
     DT_MODIFICA_RICHIESTA_GEST TIMESTAMP , 
     DT_CAMBIO_STATO_RICHIESTA_GEST TIMESTAMP , 
     MOTIVO_RISOLUZIONE_RICH_GEST VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DS_AUTORE_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_RICHIESTA_GEST TIMESTAMP , 
     TITOLO_RICHIESTA_GEST VARCHAR2 (4000 BYTE) , 
     DMALM_RICHIESTA_GEST_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_RICHIESTA_GEST NUMBER (1) , 
     DT_CARICAMENTO_RICHIESTA_GEST TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_RICHIESTA_GEST_MESE NUMBER (1) DEFAULT 0 , 
     DESCRIZIONE_RICHIESTA_GEST CLOB , 
     CATEGORIA VARCHAR2 (200 BYTE) , 
     TICKETID VARCHAR2 (100 BYTE) , 
     DATA_DISPONIBILITA TIMESTAMP , 
     DATA_CHIUSURA TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_RICHIESTA_GESTIONE_ODS IS 'DMALM_RICHIESTA_GESTIONE_ODS      ';
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.CD_RICHIESTA_GEST IS 'CD_RICHIESTA_GEST                 '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_CREAZIONE_RICHIESTA_GEST IS 'DT_CREAZIONE_RICHIESTA_GEST       '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_RISOLUZIONE_RICHIESTA_GEST IS 'DT_RISOLUZIONE_RICHIESTA_GEST     '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_MODIFICA_RICHIESTA_GEST IS 'DT_MODIFICA_RICHIESTA_GEST        '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_CAMBIO_STATO_RICHIESTA_GEST IS 'DT_CAMBIO_STATO_RICHIESTA_GEST    '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.MOTIVO_RISOLUZIONE_RICH_GEST IS 'MOTIVO_RISOLUZIONE_RICH_GEST      '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.ID_AUTORE_RICHIESTA_GEST IS 'ID_AUTORE_RICHIESTA_GEST          '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DS_AUTORE_RICHIESTA_GEST IS 'DS_AUTORE_RICHIESTA_GEST          '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_SCADENZA_RICHIESTA_GEST IS 'DT_SCADENZA_RICHIESTA_GEST        '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.TITOLO_RICHIESTA_GEST IS 'TITOLO_RICHIESTA_GEST             '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_RICHIESTA_GEST_PK IS 'DMALM_RICHIESTA_GEST_PK           '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.RANK_STATO_RICHIESTA_GEST IS 'RANK_STATO_RICHIESTA_GEST         '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_CARICAMENTO_RICHIESTA_GEST IS 'DT_CARICAMENTO_RICHIESTA_GEST     '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.RANK_STATO_RICHIESTA_GEST_MESE IS 'RANK_STATO_RICHIESTA_GEST_MESE    '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DESCRIZIONE_RICHIESTA_GEST IS 'DESCRIZIONE_RICHIESTA_GEST        '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.CATEGORIA IS 'CATEGORIA                         '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.TICKETID IS 'TICKETID                          '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DATA_DISPONIBILITA IS 'DATA_DISPONIBILITA                '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DATA_CHIUSURA IS 'DATA_CHIUSURA                     '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011302 ON DMALM_RICHIESTA_GESTIONE_ODS 
    ( 
     DMALM_RICHIESTA_GEST_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 458752 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_RICHIESTA_GESTIONE_ODS 
    ADD CONSTRAINT DMALM_RICHIESTA_GEST_ODS_PK PRIMARY KEY ( DMALM_RICHIESTA_GEST_PK ) 
    USING INDEX SYS_C0011302 ;
;
;
;
;
SPOOL OFF;