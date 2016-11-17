SPOOL 256_DMALM_FILIERA_PRODUTTIVA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_FILIERA_PRODUTTIVA 
 ( 
  ID_FILIERA             NUMBER(6)            NOT NULL,
  LIVELLO                NUMBER(6)            NOT NULL,
  SOTTOLIVELLO           NUMBER(6),
  FK_WI                  NUMBER(16)           NOT NULL,
  CODICE_WI              VARCHAR2(100 BYTE),
  TIPO_WI                VARCHAR2(100 BYTE),
  ID_REPOSITORY          VARCHAR2(20 BYTE),
  URI_WI                 VARCHAR2(4000 BYTE),
  CODICE_PROJECT         VARCHAR2(100 BYTE),
  FK_WI_FIGLIO           NUMBER(16)           NOT NULL,
  CODICE_WI_FIGLIO       VARCHAR2(100 BYTE),
  TIPO_WI_FIGLIO         VARCHAR2(100 BYTE),
  ID_REPOSITORY_FIGLIO   VARCHAR2(20 BYTE),
  URI_WI_FIGLIO          VARCHAR2(4000 BYTE),
  CODICE_PROJECT_FIGLIO  VARCHAR2(100 BYTE),
  RUOLO                  VARCHAR2(100 BYTE),
  DT_CARICAMENTO         TIMESTAMP(6)         NOT NULL
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE DMALM_FILIERA_PRODUTTIVA IS 'DMALM_FILIERA_PRODUTTIVA';
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.ID_FILIERA            IS 'ID_FILIERA           '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.LIVELLO               IS 'LIVELLO              '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.SOTTOLIVELLO          IS 'SOTTOLIVELLO         '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.FK_WI                 IS 'FK_WI                '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.CODICE_WI             IS 'CODICE_WI            '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.TIPO_WI               IS 'TIPO_WI              '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.ID_REPOSITORY         IS 'ID_REPOSITORY        '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.URI_WI                IS 'URI_WI               '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.CODICE_PROJECT        IS 'CODICE_PROJECT       '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.FK_WI_FIGLIO          IS 'FK_WI_FIGLIO         '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.CODICE_WI_FIGLIO      IS 'CODICE_WI_FIGLIO     '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.TIPO_WI_FIGLIO        IS 'TIPO_WI_FIGLIO       '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.ID_REPOSITORY_FIGLIO  IS 'ID_REPOSITORY_FIGLIO '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.URI_WI_FIGLIO         IS 'URI_WI_FIGLIO        '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.CODICE_PROJECT_FIGLIO IS 'CODICE_PROJECT_FIGLIO'; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.RUOLO                 IS 'RUOLO                '; 
COMMENT ON COLUMN DMALM_FILIERA_PRODUTTIVA.DT_CARICAMENTO        IS 'DT_CARICAMENTO       '; 

CREATE UNIQUE INDEX DMALM_FILIERA_PRODUTTIVA_PK ON DMALM_FILIERA_PRODUTTIVA
(ID_FILIERA, LIVELLO)
    TABLESPACE "TS_ALM" 
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
	
ALTER TABLE DMALM_FILIERA_PRODUTTIVA ADD (
  CONSTRAINT DMALM_FILIERA_PRODUTTIVA_PK
  PRIMARY KEY
  (ID_FILIERA, LIVELLO)
  USING INDEX DMALM_FILIERA_PRODUTTIVA_PK);
SPOOL OFF;
