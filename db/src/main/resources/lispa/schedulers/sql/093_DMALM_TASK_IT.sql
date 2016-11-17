SPOOL 093_DMALM_TASK_IT.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_TASK_IT 
    ( 
     CD_TASK_IT VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_TASK_IT DATE , 
     DT_RISOLUZIONE_TASK_IT DATE , 
     DT_MODIFICA_TASK_IT DATE , 
     DT_CAMBIO_STATO_TASK_IT DATE , 
     MOTIVO_RISOLUZIONE_TASK_IT VARCHAR2 (255 BYTE) , 
     ID_AUTORE_TASK_IT VARCHAR2 (20 BYTE) , 
     DS_AUTORE_TASK_IT VARCHAR2 (50 BYTE) , 
     DT_SCADENZA_TASK_IT DATE , 
     TITOLO_TASK_IT VARCHAR2 (4000 BYTE) , 
     DMALM_TASK_IT_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_TASK_IT NUMBER , 
     DT_CARICAMENTO_TASK_IT DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_TASK_IT_MESE NUMBER (5) DEFAULT 0 , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     AVANZAMENTO NUMBER (8) , 
     CODICE VARCHAR2 (200 BYTE) , 
     DT_INIZIO_PIANIFICATO TIMESTAMP , 
     DT_FINE_PIANIFICATA TIMESTAMP , 
     TIPO_TASK VARCHAR2 (200 BYTE) , 
     DT_INIZIO_EFFETTIVO TIMESTAMP , 
     DT_FINE_EFFETTIVA TIMESTAMP , 
     SEVERITY_TASK_IT VARCHAR2 (20 BYTE) , 
     PRIORITY_TASK_IT VARCHAR2 (20 BYTE) , 
     DURATA_EFFETTIVA NUMBER (8) , 
     DESCRIZIONE_TASK_IT CLOB , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) DEFAULT 0 , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) DEFAULT 0 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_TASK_IT IS 'DMALM_TASK_IT                     ';
COMMENT ON COLUMN DMALM_TASK_IT.CD_TASK_IT IS 'CD_TASK_IT                        '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_CREAZIONE_TASK_IT IS 'DT_CREAZIONE_TASK_IT              '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_RISOLUZIONE_TASK_IT IS 'DT_RISOLUZIONE_TASK_IT            '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_MODIFICA_TASK_IT IS 'DT_MODIFICA_TASK_IT               '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_CAMBIO_STATO_TASK_IT IS 'DT_CAMBIO_STATO_TASK_IT           '; 
COMMENT ON COLUMN DMALM_TASK_IT.MOTIVO_RISOLUZIONE_TASK_IT IS 'MOTIVO_RISOLUZIONE_TASK_IT        '; 
COMMENT ON COLUMN DMALM_TASK_IT.ID_AUTORE_TASK_IT IS 'ID_AUTORE_TASK_IT                 '; 
COMMENT ON COLUMN DMALM_TASK_IT.DS_AUTORE_TASK_IT IS 'DS_AUTORE_TASK_IT                 '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_SCADENZA_TASK_IT IS 'DT_SCADENZA_TASK_IT               '; 
COMMENT ON COLUMN DMALM_TASK_IT.TITOLO_TASK_IT IS 'TITOLO_TASK_IT                    '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_TASK_IT_PK IS 'DMALM_TASK_IT_PK                  '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_TASK_IT.RANK_STATO_TASK_IT IS 'RANK_STATO_TASK_IT                '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_CARICAMENTO_TASK_IT IS 'DT_CARICAMENTO_TASK_IT            '; 
COMMENT ON COLUMN DMALM_TASK_IT.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_TASK_IT.RANK_STATO_TASK_IT_MESE IS 'RANK_STATO_TASK_IT_MESE           '; 
COMMENT ON COLUMN DMALM_TASK_IT.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_TASK_IT.AVANZAMENTO IS 'AVANZAMENTO                       '; 
COMMENT ON COLUMN DMALM_TASK_IT.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_INIZIO_PIANIFICATO IS 'DT_INIZIO_PIANIFICATO             '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_FINE_PIANIFICATA IS 'DT_FINE_PIANIFICATA               '; 
COMMENT ON COLUMN DMALM_TASK_IT.TIPO_TASK IS 'TIPO_TASK                         '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_INIZIO_EFFETTIVO IS 'DT_INIZIO_EFFETTIVO               '; 
COMMENT ON COLUMN DMALM_TASK_IT.DT_FINE_EFFETTIVA IS 'DT_FINE_EFFETTIVA                 '; 
COMMENT ON COLUMN DMALM_TASK_IT.SEVERITY_TASK_IT IS 'SEVERITY_TASK_IT                  '; 
COMMENT ON COLUMN DMALM_TASK_IT.PRIORITY_TASK_IT IS 'PRIORITY_TASK_IT                  '; 
COMMENT ON COLUMN DMALM_TASK_IT.DURATA_EFFETTIVA IS 'DURATA_EFFETTIVA                  '; 
COMMENT ON COLUMN DMALM_TASK_IT.DESCRIZIONE_TASK_IT IS 'DESCRIZIONE_TASK_IT               '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_TASK_IT.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_TASK_IT.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011409 ON DMALM_TASK_IT 
    ( 
     DMALM_TASK_IT_PK ASC 
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
CREATE INDEX IDX_TASK_IT ON DMALM_TASK_IT 
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
ALTER TABLE DMALM_TASK_IT 
    ADD CONSTRAINT DMALM_TASK_IT_PK PRIMARY KEY ( DMALM_TASK_IT_PK ) 
    USING INDEX SYS_C0011409 ;
;
;
;
;
ALTER TABLE DMALM_TASK_IT 
    ADD CONSTRAINT TASK_IT_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_TASK_IT 
    ADD CONSTRAINT TASK_IT_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_TASK_IT 
    ADD CONSTRAINT TASK_IT_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_TASK_IT 
    ADD CONSTRAINT TASK_IT_FK_06 FOREIGN KEY 
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
