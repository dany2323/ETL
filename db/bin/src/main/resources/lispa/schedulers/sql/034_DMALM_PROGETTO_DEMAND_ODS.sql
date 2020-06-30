SPOOL 034_DMALM_PROGETTO_DEMAND_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_PROGETTO_DEMAND_ODS 
    ( 
     CD_PROGETTO_DEMAND VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_PROGETTO_DEMAND TIMESTAMP , 
     DT_RISOLUZIONE_PROGETTO_DEMAND TIMESTAMP , 
     DT_MODIFICA_PROGETTO_DEMAND TIMESTAMP , 
     DT_CAMBIO_STATO_PROGETTO_DEM TIMESTAMP , 
     MOTIVO_RISOLUZIONE_PROG_DEM VARCHAR2 (255 BYTE) , 
     ID_AUTORE_PROGETTO_DEMAND VARCHAR2 (100 BYTE) , 
     DS_AUTORE_PROGETTO_DEMAND VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_PROGETTO_DEMAND TIMESTAMP , 
     TITOLO_PROGETTO_DEMAND VARCHAR2 (4000 BYTE) , 
     DMALM_PROGETTO_DEMAND_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_PROGETTO_DEMAND NUMBER (1) , 
     DT_CARICAMENTO_PROGETTO_DEMAND TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_PROGETTO_DEM_MESE NUMBER (5) DEFAULT 0 , 
     DT_CHIUSURA_PROGETTO_DEMAND TIMESTAMP , 
     TEMPO_TOTALE_RISOLUZIONE NUMBER (8) , 
     DESCRIZIONE_PROGETTO_DEMAND CLOB , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     CODICE VARCHAR2 (200 BYTE) , 
     CF_OWNER_DEMAND VARCHAR2 (50 BYTE) , 
     CF_DT_ENUNCIAZIONE TIMESTAMP , 
     CF_DT_DISPONIBILITA_EFF TIMESTAMP , 
     CF_DT_VALIDAZIONE TIMESTAMP , 
     CF_REFERENTE_SVILUPPO VARCHAR2 (50 BYTE) , 
     CF_REFERENTE_ESERCIZIO VARCHAR2 (50 BYTE) , 
     AOID VARCHAR2 (200 BYTE) , 
     FORNITURA VARCHAR2 (80 BYTE) , 
     CF_DT_DISPONIBILITA TIMESTAMP , 
     LISTA_HYPERLINKS VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_PROGETTO_DEMAND_ODS IS 'DMALM_PROGETTO_DEMAND_ODS         ';
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CD_PROGETTO_DEMAND IS 'CD_PROGETTO_DEMAND                '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_CREAZIONE_PROGETTO_DEMAND IS 'DT_CREAZIONE_PROGETTO_DEMAND      '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_RISOLUZIONE_PROGETTO_DEMAND IS 'DT_RISOLUZIONE_PROGETTO_DEMAND    '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_MODIFICA_PROGETTO_DEMAND IS 'DT_MODIFICA_PROGETTO_DEMAND       '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_CAMBIO_STATO_PROGETTO_DEM IS 'DT_CAMBIO_STATO_PROGETTO_DEM      '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.MOTIVO_RISOLUZIONE_PROG_DEM IS 'MOTIVO_RISOLUZIONE_PROG_DEM       '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.ID_AUTORE_PROGETTO_DEMAND IS 'ID_AUTORE_PROGETTO_DEMAND         '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DS_AUTORE_PROGETTO_DEMAND IS 'DS_AUTORE_PROGETTO_DEMAND         '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_SCADENZA_PROGETTO_DEMAND IS 'DT_SCADENZA_PROGETTO_DEMAND       '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.TITOLO_PROGETTO_DEMAND IS 'TITOLO_PROGETTO_DEMAND            '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_PROGETTO_DEMAND_PK IS 'DMALM_PROGETTO_DEMAND_PK          '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.RANK_STATO_PROGETTO_DEMAND IS 'RANK_STATO_PROGETTO_DEMAND        '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_CARICAMENTO_PROGETTO_DEMAND IS 'DT_CARICAMENTO_PROGETTO_DEMAND    '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.RANK_STATO_PROGETTO_DEM_MESE IS 'RANK_STATO_PROGETTO_DEM_MESE      '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DT_CHIUSURA_PROGETTO_DEMAND IS 'DT_CHIUSURA_PROGETTO_DEMAND       '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.TEMPO_TOTALE_RISOLUZIONE IS 'TEMPO_TOTALE_RISOLUZIONE          '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DESCRIZIONE_PROGETTO_DEMAND IS 'DESCRIZIONE_PROGETTO_DEMAND       '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_OWNER_DEMAND IS 'CF_OWNER_DEMAND                   '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_DT_ENUNCIAZIONE IS 'CF_DT_ENUNCIAZIONE                '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_DT_DISPONIBILITA_EFF IS 'CF_DT_DISPONIBILITA_EFF           '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_DT_VALIDAZIONE IS 'CF_DT_VALIDAZIONE                 '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_REFERENTE_SVILUPPO IS 'CF_REFERENTE_SVILUPPO             '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_REFERENTE_ESERCIZIO IS 'CF_REFERENTE_ESERCIZIO            '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.AOID IS 'AOID                              '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.FORNITURA IS 'FORNITURA                         '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.CF_DT_DISPONIBILITA IS 'CF_DT_DISPONIBILITA               '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.LISTA_HYPERLINKS IS 'LISTA_HYPERLINKS                  '; 
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011270 ON DMALM_PROGETTO_DEMAND_ODS 
    ( 
     DMALM_PROGETTO_DEMAND_PK ASC 
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
ALTER TABLE DMALM_PROGETTO_DEMAND_ODS 
    ADD CONSTRAINT DMALM_PROGETTO_DEMAND_ODS_PK PRIMARY KEY ( DMALM_PROGETTO_DEMAND_PK ) 
    USING INDEX SYS_C0011270 ;
;
;
;
;
SPOOL OFF;
