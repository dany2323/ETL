SPOOL 056_DMALM_RICH_MANUTENZIONE_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RICH_MANUTENZIONE_ODS 
    ( 
     CD_RICHIESTA_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RICH_MANUTENZIONE TIMESTAMP , 
     DT_RISOLUZIONE_RICH_MANUT TIMESTAMP , 
     DT_MODIFICA_RICH_MANUTENZIONE TIMESTAMP , 
     DT_CAMBIO_STATO_RICH_MANUT TIMESTAMP , 
     MOTIVO_RISOLUZIONE_RICH_MANUT VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RICH_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DS_AUTORE_RICH_MANUTENZIONE VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_RICH_MANUTENZIONE TIMESTAMP , 
     TITOLO_RICHIESTA_MANUTENZIONE VARCHAR2 (4000 BYTE) , 
     DMALM_RICH_MANUTENZIONE_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_RICH_MANUTENZIONE NUMBER (1) , 
     DT_CARICAMENTO_RICH_MANUT TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_RICH_MANUT_MESE NUMBER (5) DEFAULT 0 , 
     DESCRIZIONE_RICH_MANUTENZIONE CLOB , 
     DATA_CHIUSURA_RICH_MANUT TIMESTAMP , 
     DATA_INIZIO_PIANIFICATO TIMESTAMP , 
     DATA_INIZIO_EFFETTIVO TIMESTAMP , 
     DATA_DISP_PIANIFICATA TIMESTAMP , 
     DATA_DISP_EFFETTIVA TIMESTAMP , 
     CODICE VARCHAR2 (200 BYTE) , 
     CLASSE_DI_FORNITURA VARCHAR2 (80 BYTE) , 
     DURATA_EFFETTIVA_RICH_MAN NUMBER (5) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_RICH_MANUTENZIONE_ODS IS 'DMALM_RICH_MANUTENZIONE_ODS       ';
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.CD_RICHIESTA_MANUTENZIONE IS 'CD_RICHIESTA_MANUTENZIONE         '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_CREAZIONE_RICH_MANUTENZIONE IS 'DT_CREAZIONE_RICH_MANUTENZIONE    '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_RISOLUZIONE_RICH_MANUT IS 'DT_RISOLUZIONE_RICH_MANUT         '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_MODIFICA_RICH_MANUTENZIONE IS 'DT_MODIFICA_RICH_MANUTENZIONE     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_CAMBIO_STATO_RICH_MANUT IS 'DT_CAMBIO_STATO_RICH_MANUT        '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.MOTIVO_RISOLUZIONE_RICH_MANUT IS 'MOTIVO_RISOLUZIONE_RICH_MANUT     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.ID_AUTORE_RICH_MANUTENZIONE IS 'ID_AUTORE_RICH_MANUTENZIONE       '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DS_AUTORE_RICH_MANUTENZIONE IS 'DS_AUTORE_RICH_MANUTENZIONE       '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_SCADENZA_RICH_MANUTENZIONE IS 'DT_SCADENZA_RICH_MANUTENZIONE     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.TITOLO_RICHIESTA_MANUTENZIONE IS 'TITOLO_RICHIESTA_MANUTENZIONE     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_RICH_MANUTENZIONE_PK IS 'DMALM_RICH_MANUTENZIONE_PK        '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.RANK_STATO_RICH_MANUTENZIONE IS 'RANK_STATO_RICH_MANUTENZIONE      '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_CARICAMENTO_RICH_MANUT IS 'DT_CARICAMENTO_RICH_MANUT         '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.RANK_STATO_RICH_MANUT_MESE IS 'RANK_STATO_RICH_MANUT_MESE        '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DESCRIZIONE_RICH_MANUTENZIONE IS 'DESCRIZIONE_RICH_MANUTENZIONE     '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DATA_CHIUSURA_RICH_MANUT IS 'DATA_CHIUSURA_RICH_MANUT          '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DATA_INIZIO_PIANIFICATO IS 'DATA_INIZIO_PIANIFICATO           '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DATA_INIZIO_EFFETTIVO IS 'DATA_INIZIO_EFFETTIVO             '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DATA_DISP_PIANIFICATA IS 'DATA_DISP_PIANIFICATA             '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DATA_DISP_EFFETTIVA IS 'DATA_DISP_EFFETTIVA               '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.CLASSE_DI_FORNITURA IS 'CLASSE_DI_FORNITURA               '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DURATA_EFFETTIVA_RICH_MAN IS 'DURATA_EFFETTIVA_RICH_MAN         '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RICH_MANUTENZIONE_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011305 ON DMALM_RICH_MANUTENZIONE_ODS 
    ( 
     DMALM_RICH_MANUTENZIONE_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 393216 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_RICH_MANUTENZIONE_ODS 
    ADD CONSTRAINT DMALM_RICH_MANUTENZIONE_ODS_PK PRIMARY KEY ( DMALM_RICH_MANUTENZIONE_PK ) 
    USING INDEX SYS_C0011305 ;
SPOOL OFF;
