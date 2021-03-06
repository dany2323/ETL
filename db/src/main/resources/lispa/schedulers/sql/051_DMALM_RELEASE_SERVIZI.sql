SPOOL 051_DMALM_RELEASE_SERVIZI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_RELEASE_SERVIZI 
    ( 
     CD_REL_SERVIZI VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_REL_SERVIZI DATE , 
     DT_RISOLUZIONE_REL_SERVIZI DATE , 
     DT_MODIFICA_REL_SERVIZI DATE , 
     DT_CAMBIO_STATO_REL_SERVIZI DATE , 
     DESCRIZIONE_REL_SERVIZI VARCHAR2 (4000 BYTE) , 
     MOTIVO_RISOLUZIONE_REL_SERVIZI VARCHAR2 (255 BYTE) , 
     ID_AUTORE_REL_SERVIZI VARCHAR2 (100 BYTE) , 
     DS_AUTORE_REL_SERVIZI VARCHAR2 (50 BYTE) , 
     DT_SCADENZA_REL_SERVIZI DATE , 
     TITOLO_REL_SERVIZI VARCHAR2 (4000 BYTE) , 
     DMALM_REL_SERVIZI_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_REL_SERVIZI NUMBER , 
     DT_CARICAMENTO_REL_SERVIZI DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_REL_SERVIZI_MESE NUMBER (5) DEFAULT 0 , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     RICHIESTA_ANALISI_IMPATTI_REL VARCHAR2 (20 BYTE) , 
     PREVISTO_FERMO_SERVIZIO_REL VARCHAR2 (20 BYTE) , 
     MOTIVO_SOSPENSIONE_RELEASE_SER VARCHAR2 (20 BYTE) , 
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
        INITIAL 3145728 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_RELEASE_SERVIZI IS 'DMALM_RELEASE_SERVIZI             ';
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.CD_REL_SERVIZI IS 'CD_REL_SERVIZI                    '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_CREAZIONE_REL_SERVIZI IS 'DT_CREAZIONE_REL_SERVIZI          '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_RISOLUZIONE_REL_SERVIZI IS 'DT_RISOLUZIONE_REL_SERVIZI        '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_MODIFICA_REL_SERVIZI IS 'DT_MODIFICA_REL_SERVIZI           '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_CAMBIO_STATO_REL_SERVIZI IS 'DT_CAMBIO_STATO_REL_SERVIZI       '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DESCRIZIONE_REL_SERVIZI IS 'DESCRIZIONE_REL_SERVIZI           '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.MOTIVO_RISOLUZIONE_REL_SERVIZI IS 'MOTIVO_RISOLUZIONE_REL_SERVIZI    '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.ID_AUTORE_REL_SERVIZI IS 'ID_AUTORE_REL_SERVIZI             '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DS_AUTORE_REL_SERVIZI IS 'DS_AUTORE_REL_SERVIZI             '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_SCADENZA_REL_SERVIZI IS 'DT_SCADENZA_REL_SERVIZI           '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.TITOLO_REL_SERVIZI IS 'TITOLO_REL_SERVIZI                '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_REL_SERVIZI_PK IS 'DMALM_REL_SERVIZI_PK              '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.RANK_STATO_REL_SERVIZI IS 'RANK_STATO_REL_SERVIZI            '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_CARICAMENTO_REL_SERVIZI IS 'DT_CARICAMENTO_REL_SERVIZI        '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.RANK_STATO_REL_SERVIZI_MESE IS 'RANK_STATO_REL_SERVIZI_MESE       '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.RICHIESTA_ANALISI_IMPATTI_REL IS 'RICHIESTA_ANALISI_IMPATTI_REL     '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.PREVISTO_FERMO_SERVIZIO_REL IS 'PREVISTO_FERMO_SERVIZIO_REL       '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.MOTIVO_SOSPENSIONE_RELEASE_SER IS 'MOTIVO_SOSPENSIONE_RELEASE_SER    '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011297 ON DMALM_RELEASE_SERVIZI 
    ( 
     DMALM_REL_SERVIZI_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 262144 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_RELEASE_SERVIZI ON DMALM_RELEASE_SERVIZI 
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
ALTER TABLE DMALM_RELEASE_SERVIZI 
    ADD CONSTRAINT DMALM_RELEASE_SERVIZI_PK PRIMARY KEY ( DMALM_REL_SERVIZI_PK ) 
    USING INDEX SYS_C0011297 ;
ALTER TABLE DMALM_RELEASE_SERVIZI 
    ADD CONSTRAINT RELEASE_SERVIZI_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_SERVIZI 
    ADD CONSTRAINT RELEASE_SERVIZI_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_SERVIZI 
    ADD CONSTRAINT RELEASE_SERVIZI_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_RELEASE_SERVIZI 
    ADD CONSTRAINT RELEASE_SERVIZI_FK_06 FOREIGN KEY 
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
