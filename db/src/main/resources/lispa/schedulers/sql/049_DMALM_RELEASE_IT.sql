SPOOL 049_DMALM_RELEASE_IT.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RELEASE_IT 
    ( 
     CD_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RELEASE_IT DATE , 
     DT_RISOLUZIONE_RELEASE_IT DATE , 
     DT_MODIFICA_RELEASE_IT DATE , 
     DT_CAMBIO_STATO_RELEASE_IT DATE , 
     DESCRIZIONE_RELEASE_IT VARCHAR2 (4000 BYTE) , 
     MOTIVO_RISOLUZIONE_RELEASE_IT VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DS_AUTORE_RELEASE_IT VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_RELEASE_IT DATE , 
     TITOLO_RELEASE_IT VARCHAR2 (4000 BYTE) , 
     DMALM_RELEASE_IT_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_RELEASE_IT NUMBER , 
     DT_CARICAMENTO_RELEASE_IT DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_RELEASE_IT_MESE NUMBER (5) DEFAULT 0 , 
     DT_RILASCIO_RELEASE TIMESTAMP , 
     DT_DISPONIBILITA_EFF_RELEASE TIMESTAMP , 
     DT_INIZIO_RELEASE TIMESTAMP , 
     DT_FINE_RELEASE TIMESTAMP , 
     DURATA_EFF_RELEASE NUMBER (8) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 9437184 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_RELEASE_IT IS 'DMALM_RELEASE_IT                  ';
COMMENT ON COLUMN DMALM_RELEASE_IT.CD_RELEASE_IT IS 'CD_RELEASE_IT                     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_CREAZIONE_RELEASE_IT IS 'DT_CREAZIONE_RELEASE_IT           '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_RISOLUZIONE_RELEASE_IT IS 'DT_RISOLUZIONE_RELEASE_IT         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_MODIFICA_RELEASE_IT IS 'DT_MODIFICA_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_CAMBIO_STATO_RELEASE_IT IS 'DT_CAMBIO_STATO_RELEASE_IT        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DESCRIZIONE_RELEASE_IT IS 'DESCRIZIONE_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.MOTIVO_RISOLUZIONE_RELEASE_IT IS 'MOTIVO_RISOLUZIONE_RELEASE_IT     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.ID_AUTORE_RELEASE_IT IS 'ID_AUTORE_RELEASE_IT              '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DS_AUTORE_RELEASE_IT IS 'DS_AUTORE_RELEASE_IT              '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_SCADENZA_RELEASE_IT IS 'DT_SCADENZA_RELEASE_IT            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.TITOLO_RELEASE_IT IS 'TITOLO_RELEASE_IT                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_RELEASE_IT_PK IS 'DMALM_RELEASE_IT_PK               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.RANK_STATO_RELEASE_IT IS 'RANK_STATO_RELEASE_IT             '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_CARICAMENTO_RELEASE_IT IS 'DT_CARICAMENTO_RELEASE_IT         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.RANK_STATO_RELEASE_IT_MESE IS 'RANK_STATO_RELEASE_IT_MESE        '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_RILASCIO_RELEASE IS 'DT_RILASCIO_RELEASE               '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_DISPONIBILITA_EFF_RELEASE IS 'DT_DISPONIBILITA_EFF_RELEASE      '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_INIZIO_RELEASE IS 'DT_INIZIO_RELEASE                 '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DT_FINE_RELEASE IS 'DT_FINE_RELEASE                   '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DURATA_EFF_RELEASE IS 'DURATA_EFF_RELEASE                '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_RELEASE_IT.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011294 ON DMALM_RELEASE_IT 
    ( 
     DMALM_RELEASE_IT_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 851968 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_RELEASE_IT ON DMALM_RELEASE_IT 
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
ALTER TABLE DMALM_RELEASE_IT 
    ADD CONSTRAINT DMALM_RELEASE_IT_PK PRIMARY KEY ( DMALM_RELEASE_IT_PK ) 
    USING INDEX SYS_C0011294 ;
ALTER TABLE DMALM_RELEASE_IT 
    ADD CONSTRAINT RELEASE_IT_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_IT 
    ADD CONSTRAINT RELEASE_IT_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_IT 
    ADD CONSTRAINT RELEASE_IT_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_IT 
    ADD CONSTRAINT RELEASE_IT_FK_06 FOREIGN KEY 
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
