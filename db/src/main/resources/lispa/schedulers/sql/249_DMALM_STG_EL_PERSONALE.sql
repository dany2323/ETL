SPOOL 249_DMALM_STG_EL_PERSONALE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_EL_PERSONALE 
 ( 
  DMALM_STG_PERSONALE_PK   NUMBER(15)           NOT NULL,
  ID_EDMA                  VARCHAR2 (100 Byte),
  CD_PERSONALE             VARCHAR2(40 BYTE),
  DT_INIZIO_VALIDITA_EDMA  DATE,
  DT_FINE_VALIDITA_EDMA    DATE,
  DT_ATTIVAZIONE           DATE,
  DT_DISATTIVAZIONE        DATE,
  NOTE                     VARCHAR2(500 BYTE),
  INTERNO                  NUMBER(1),
  CD_RESPONSABILE          VARCHAR2(40 BYTE),
  INDIRIZZO_EMAIL          VARCHAR2(80 BYTE),
  NOME                     VARCHAR2(100 BYTE),
  COGNOME                  VARCHAR2(50 BYTE),
  MATRICOLA                VARCHAR2(32 BYTE),
  CODICE_FISCALE           VARCHAR2(32 BYTE),
  IDENTIFICATORE           VARCHAR2(40 BYTE),
  ID_GRADO                 NUMBER(8),
  ID_SEDE                  NUMBER(8),
  CD_SUPERIORE             VARCHAR2(40 BYTE),
  CD_ENTE                  VARCHAR2(40 BYTE),
  CD_VISIBILITA            VARCHAR2(40 BYTE),
  DT_CARICAMENTO           DATE
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE DMALM_STG_EL_PERSONALE IS 'DMALM_STG_EL_PERSONALE';
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DMALM_STG_PERSONALE_PK   IS 'DMALM_STG_PERSONALE_PK '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.ID_EDMA                  IS 'ID_EDMA                '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CD_PERSONALE             IS 'CD_PERSONALE           '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DT_INIZIO_VALIDITA_EDMA  IS 'DT_INIZIO_VALIDITA_EDMA'; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DT_FINE_VALIDITA_EDMA    IS 'DT_FINE_VALIDITA_EDMA  '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DT_ATTIVAZIONE           IS 'DT_ATTIVAZIONE         '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DT_DISATTIVAZIONE        IS 'DT_DISATTIVAZIONE      '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.NOTE                     IS 'NOTE                   '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.INTERNO                  IS 'INTERNO                '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CD_RESPONSABILE          IS 'CD_RESPONSABILE        '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.INDIRIZZO_EMAIL          IS 'INDIRIZZO_EMAIL        '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.NOME                     IS 'NOME                   '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.COGNOME                  IS 'COGNOME                '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.MATRICOLA                IS 'MATRICOLA              '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CODICE_FISCALE           IS 'CODICE_FISCALE         '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.IDENTIFICATORE           IS 'IDENTIFICATORE         '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.ID_GRADO                 IS 'ID_GRADO               '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.ID_SEDE                  IS 'ID_SEDE                '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CD_SUPERIORE             IS 'CD_SUPERIORE           '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CD_ENTE                  IS 'CD_ENTE                '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.CD_VISIBILITA            IS 'CD_VISIBILITA          '; 
COMMENT ON COLUMN DMALM_STG_EL_PERSONALE.DT_CARICAMENTO           IS 'DT_CARICAMENTO         '; 

CREATE UNIQUE INDEX DMALM_STG_EL_PERSONALE_PK ON DMALM_STG_EL_PERSONALE
(DMALM_STG_PERSONALE_PK)
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
	
ALTER TABLE DMALM_STG_EL_PERSONALE ADD (
  CONSTRAINT DMALM_STG_EL_PERSONALE_PK
  PRIMARY KEY
  (DMALM_STG_PERSONALE_PK)
  USING INDEX DMALM_STG_EL_PERSONALE_PK);
SPOOL OFF;
