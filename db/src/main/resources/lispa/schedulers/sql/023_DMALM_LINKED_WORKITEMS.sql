SPOOL 023_DMALM_LINKED_WORKITEMS.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_LINKED_WORKITEMS 
    ( 
  CODICE_WI_FIGLIO       VARCHAR2(100 BYTE),
  CODICE_WI_PADRE        VARCHAR2(100 BYTE),
  TIPO_WI_FIGLIO         VARCHAR2(100 BYTE),
  TIPO_WI_PADRE          VARCHAR2(100 BYTE),
  FK_WI_FIGLIO           NUMBER(16),
  FK_WI_PADRE            NUMBER(16),
  RUOLO                  VARCHAR2(100 BYTE),
  DT_CARICAMENTO         TIMESTAMP(6),
  LINKED_WORKITEMS_PK    NUMBER(15)             NOT NULL,
  TITOLO_WI_FIGLIO       VARCHAR2(4000 BYTE),
  TITOLO_WI_PADRE        VARCHAR2(4000 BYTE),
  URI_WI_PADRE           VARCHAR2(4000 BYTE),
  URI_WI_FIGLIO          VARCHAR2(4000 BYTE),
  ID_REPOSITORY_FIGLIO   VARCHAR2(20 BYTE),
  ID_REPOSITORY_PADRE    VARCHAR2(20 BYTE),
  CODICE_PROJECT_FIGLIO  VARCHAR2(100 BYTE),
  CODICE_PROJECT_PADRE   VARCHAR2(100 BYTE)
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 39845888 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_LINKED_WORKITEMS IS 'DMALM_LINKED_WORKITEMS';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.CODICE_WI_FIGLIO      IS 'CODICE_WI_FIGLIO     ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.CODICE_WI_PADRE       IS 'CODICE_WI_PADRE      ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.TIPO_WI_FIGLIO        IS 'TIPO_WI_FIGLIO       ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.TIPO_WI_PADRE         IS 'TIPO_WI_PADRE        ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.FK_WI_FIGLIO          IS 'FK_WI_FIGLIO         ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.FK_WI_PADRE           IS 'FK_WI_PADRE          ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.RUOLO                 IS 'RUOLO                ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.DT_CARICAMENTO        IS 'DT_CARICAMENTO       ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.LINKED_WORKITEMS_PK   IS 'LINKED_WORKITEMS_PK  ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.TITOLO_WI_FIGLIO      IS 'TITOLO_WI_FIGLIO     ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.TITOLO_WI_PADRE       IS 'TITOLO_WI_PADRE      ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.URI_WI_PADRE          IS 'URI_WI_PADRE         ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.URI_WI_FIGLIO         IS 'URI_WI_FIGLIO        ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.ID_REPOSITORY_FIGLIO  IS 'ID_REPOSITORY_FIGLIO ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.ID_REPOSITORY_PADRE   IS 'ID_REPOSITORY_PADRE  ';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.CODICE_PROJECT_FIGLIO IS 'CODICE_PROJECT_FIGLIO';
COMMENT ON COLUMN DMALM_LINKED_WORKITEMS.CODICE_PROJECT_PADRE  IS 'CODICE_PROJECT_PADRE ';
CREATE UNIQUE INDEX PK_DMALM_LINKED_WORKITEMS ON DMALM_LINKED_WORKITEMS 
    ( 
     LINKED_WORKITEMS_PK ASC 
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
ALTER TABLE DMALM_LINKED_WORKITEMS 
    ADD CONSTRAINT PK_DMALM_LINKED_WORKITEMS PRIMARY KEY ( LINKED_WORKITEMS_PK ) 
    USING INDEX PK_DMALM_LINKED_WORKITEMS ;
SPOOL OFF;
