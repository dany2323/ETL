SPOOL 050_DMALM_RELEASE_IT_ODS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RELEASE_IT_ODS 
    ( 
     CD_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RELEASE_IT TIMESTAMP , 
     DT_RISOLUZIONE_RELEASE_IT TIMESTAMP , 
     DT_MODIFICA_RELEASE_IT TIMESTAMP , 
     DT_CAMBIO_STATO_RELEASE_IT TIMESTAMP , 
     DESCRIZIONE_RELEASE_IT VARCHAR2 (4000 BYTE) , 
     MOTIVO_RISOLUZIONE_RELEASE_IT VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DS_AUTORE_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_RELEASE_IT TIMESTAMP , 
     TITOLO_RELEASE_IT VARCHAR2 (4000 BYTE) , 
     DMALM_RELEASE_IT_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_RELEASE_IT NUMBER (1) , 
     DT_CARICAMENTO_RELEASE_IT TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE TIMESTAMP , 
     RANK_STATO_RELEASE_IT_MESE NUMBER (5) DEFAULT 0 , 
     DT_RILASCIO_RELEASE TIMESTAMP , 
     DT_DISPONIBILITA_EFF_RELEASE TIMESTAMP , 
     DT_INIZIO_RELEASE TIMESTAMP , 
     DT_FINE_RELEASE TIMESTAMP , 
     DURATA_EFF_RELEASE NUMBER (8) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 14680064 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_RELEASE_IT_ODS IS 'DMALM_RELEASE_IT_ODS              ';
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.CD_RELEASE_IT IS 'CD_RELEASE_IT                     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_CREAZIONE_RELEASE_IT IS 'DT_CREAZIONE_RELEASE_IT           '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_RISOLUZIONE_RELEASE_IT IS 'DT_RISOLUZIONE_RELEASE_IT         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_MODIFICA_RELEASE_IT IS 'DT_MODIFICA_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_CAMBIO_STATO_RELEASE_IT IS 'DT_CAMBIO_STATO_RELEASE_IT        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DESCRIZIONE_RELEASE_IT IS 'DESCRIZIONE_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.MOTIVO_RISOLUZIONE_RELEASE_IT IS 'MOTIVO_RISOLUZIONE_RELEASE_IT     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.ID_AUTORE_RELEASE_IT IS 'ID_AUTORE_RELEASE_IT              '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DS_AUTORE_RELEASE_IT IS 'DS_AUTORE_RELEASE_IT              '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_SCADENZA_RELEASE_IT IS 'DT_SCADENZA_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.TITOLO_RELEASE_IT IS 'TITOLO_RELEASE_IT                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_RELEASE_IT_PK IS 'DMALM_RELEASE_IT_PK               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.RANK_STATO_RELEASE_IT IS 'RANK_STATO_RELEASE_IT             '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_CARICAMENTO_RELEASE_IT IS 'DT_CARICAMENTO_RELEASE_IT         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.RANK_STATO_RELEASE_IT_MESE IS 'RANK_STATO_RELEASE_IT_MESE        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_RILASCIO_RELEASE IS 'DT_RILASCIO_RELEASE               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_DISPONIBILITA_EFF_RELEASE IS 'DT_DISPONIBILITA_EFF_RELEASE      '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_INIZIO_RELEASE IS 'DT_INIZIO_RELEASE                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DT_FINE_RELEASE IS 'DT_FINE_RELEASE                   '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DURATA_EFF_RELEASE IS 'DURATA_EFF_RELEASE                '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT_ODS.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011296 ON DMALM_RELEASE_IT_ODS 
    ( 
     DMALM_RELEASE_IT_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 2097152 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_RELEASE_IT_ODS 
    ADD CONSTRAINT DMALM_RELEASE_IT_ODS_PK PRIMARY KEY ( DMALM_RELEASE_IT_PK ) 
    USING INDEX SYS_C0011296 ;
;
;
;
;
SPOOL OFF;
