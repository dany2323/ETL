SPOOL 035_DMALM_PROGETTO_ESE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_PROGETTO_ESE 
    ( 
     CD_PROGETTO_ESE VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_PROGETTO_ESE DATE , 
     DT_RISOLUZIONE_PROGETTO_ESE DATE , 
     DT_MODIFICA_PROGETTO_ESE DATE , 
     DT_CAMBIO_STATO_PROGETTO_ESE DATE , 
     DESCRIZIONE_PROGETTO_ESE VARCHAR2 (4000 BYTE) , 
     MOTIVO_RISOLUZIONE_PROG_ESE VARCHAR2 (255 BYTE) , 
     ID_AUTORE_PROGETTO_ESE VARCHAR2 (100 BYTE) , 
     DS_AUTORE_PROGETTO_ESE VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_PROGETTO_ESE DATE , 
     TITOLO_PROGETTO_ESE VARCHAR2 (4000 BYTE) , 
     DMALM_PROGETTO_ESE_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (16) , 
     DMALM_TEMPO_FK_04 NUMBER (15) DEFAULT 0 , 
     RANK_STATO_PROGETTO_ESE NUMBER , 
     DT_CARICAMENTO_PROGETTO_ESE DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_PROGETTO_ESE_MESE NUMBER (5) DEFAULT 0 , 
     CF_CODICE VARCHAR2 (200 BYTE) , 
     CF_DT_ULTIMA_SOTTOMISSIONE TIMESTAMP , 
     LISTA_HYPERLINKS VARCHAR2 (4000 BYTE) , 
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
        INITIAL 65536 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_PROGETTO_ESE IS 'DMALM_PROGETTO_ESE                ';
COMMENT ON COLUMN DMALM_PROGETTO_ESE.CD_PROGETTO_ESE IS 'CD_PROGETTO_ESE                   '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_CREAZIONE_PROGETTO_ESE IS 'DT_CREAZIONE_PROGETTO_ESE         '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_RISOLUZIONE_PROGETTO_ESE IS 'DT_RISOLUZIONE_PROGETTO_ESE       '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_MODIFICA_PROGETTO_ESE IS 'DT_MODIFICA_PROGETTO_ESE          '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_CAMBIO_STATO_PROGETTO_ESE IS 'DT_CAMBIO_STATO_PROGETTO_ESE      '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DESCRIZIONE_PROGETTO_ESE IS 'DESCRIZIONE_PROGETTO_ESE          '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.MOTIVO_RISOLUZIONE_PROG_ESE IS 'MOTIVO_RISOLUZIONE_PROG_ESE       '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.ID_AUTORE_PROGETTO_ESE IS 'ID_AUTORE_PROGETTO_ESE            '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DS_AUTORE_PROGETTO_ESE IS 'DS_AUTORE_PROGETTO_ESE            '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_SCADENZA_PROGETTO_ESE IS 'DT_SCADENZA_PROGETTO_ESE          '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.TITOLO_PROGETTO_ESE IS 'TITOLO_PROGETTO_ESE               '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_PROGETTO_ESE_PK IS 'DMALM_PROGETTO_ESE_PK             '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.RANK_STATO_PROGETTO_ESE IS 'RANK_STATO_PROGETTO_ESE           '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_CARICAMENTO_PROGETTO_ESE IS 'DT_CARICAMENTO_PROGETTO_ESE       '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.RANK_STATO_PROGETTO_ESE_MESE IS 'RANK_STATO_PROGETTO_ESE_MESE      '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.CF_CODICE IS 'CF_CODICE                         '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.CF_DT_ULTIMA_SOTTOMISSIONE IS 'CF_DT_ULTIMA_SOTTOMISSIONE        '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.LISTA_HYPERLINKS IS 'LISTA_HYPERLINKS                  '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_PROGETTO_ESE.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX SYS_C0011271 ON DMALM_PROGETTO_ESE 
    ( 
     DMALM_PROGETTO_ESE_PK ASC 
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
CREATE INDEX IDX_PROGETTO_ESE ON DMALM_PROGETTO_ESE 
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
ALTER TABLE DMALM_PROGETTO_ESE 
    ADD CONSTRAINT DMALM_PROGETTO_ESE_PK PRIMARY KEY ( DMALM_PROGETTO_ESE_PK ) 
    USING INDEX SYS_C0011271 ;
ALTER TABLE DMALM_PROGETTO_ESE 
    ADD CONSTRAINT PROGETTO_ESE_FK_02 FOREIGN KEY 
    ( 
     DMALM_PROJECT_FK_02
    ) 
    REFERENCES DMALM_PROJECT 
    ( 
     DMALM_PROJECT_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_PROGETTO_ESE 
    ADD CONSTRAINT PROGETTO_ESE_FK_03 FOREIGN KEY 
    ( 
     DMALM_STATO_WORKITEM_FK_03
    ) 
    REFERENCES DMALM_STATO_WORKITEM 
    ( 
     DMALM_STATO_WORKITEM_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_PROGETTO_ESE 
    ADD CONSTRAINT PROGETTO_ESE_FK_04 FOREIGN KEY 
    ( 
     DMALM_TEMPO_FK_04
    ) 
    REFERENCES DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_PROGETTO_ESE 
    ADD CONSTRAINT PROGETTO_ESE_FK_06 FOREIGN KEY 
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
