SPOOL 248_DMALM_STG_EL_UNITA_ORGANIZZ.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_EL_UNITA_ORGANIZZ 
 ( 
  DMALM_STG_UNITA_ORG_PK   NUMBER(15)           NOT NULL,
  ID_EDMA                  VARCHAR2 (100 Byte),
  CD_AREA                  VARCHAR2(40 BYTE),
  DT_INIZIO_VALIDITA_EDMA  DATE,
  DT_FINE_VALIDITA_EDMA    DATE,
  DS_AREA_EDMA             VARCHAR2(255 BYTE),
  DT_ATTIVAZIONE           DATE,
  DT_DISATTIVAZIONE        DATE,
  NOTE                     VARCHAR2(500 BYTE),
  INTERNO                  NUMBER(1),
  CD_RESPONSABILE_AREA     VARCHAR2(40 BYTE),
  INDIRIZZO_EMAIL          VARCHAR2(80 BYTE),
  ID_TIPOLOGIA_UFFICIO     NUMBER(8),
  ID_GRADO_UFFICIO         NUMBER(8),
  ID_SEDE                  NUMBER(8),
  CD_UO_SUPERIORE          VARCHAR2(40 BYTE),
  CD_ENTE                  VARCHAR2(40 BYTE),
  CD_VISIBILITA            VARCHAR2(40 BYTE),
  DT_CARICAMENTO           DATE
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE DMALM_STG_EL_UNITA_ORGANIZZ IS 'DMALM_STG_EL_UNITA_ORGANIZZ';
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DMALM_STG_UNITA_ORG_PK   IS 'DMALM_STG_UNITA_ORG_PK '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.ID_EDMA                  IS 'ID_EDMA                '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.CD_AREA                  IS 'CD_AREA                '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DT_INIZIO_VALIDITA_EDMA  IS 'DT_INIZIO_VALIDITA_EDMA'; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DT_FINE_VALIDITA_EDMA    IS 'DT_FINE_VALIDITA_EDMA  '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DS_AREA_EDMA             IS 'DS_AREA_EDMA           '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DT_ATTIVAZIONE           IS 'DT_ATTIVAZIONE         '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DT_DISATTIVAZIONE        IS 'DT_DISATTIVAZIONE      '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.NOTE                     IS 'NOTE                   '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.INTERNO                  IS 'INTERNO                '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.CD_RESPONSABILE_AREA     IS 'CD_RESPONSABILE_AREA   '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.INDIRIZZO_EMAIL          IS 'INDIRIZZO_EMAIL        '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.ID_TIPOLOGIA_UFFICIO     IS 'ID_TIPOLOGIA_UFFICIO   '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.ID_GRADO_UFFICIO         IS 'ID_GRADO_UFFICIO       '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.ID_SEDE                  IS 'ID_SEDE                '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.CD_UO_SUPERIORE          IS 'CD_UO_SUPERIORE        '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.CD_ENTE                  IS 'CD_ENTE                '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.CD_VISIBILITA            IS 'CD_VISIBILITA          '; 
COMMENT ON COLUMN DMALM_STG_EL_UNITA_ORGANIZZ.DT_CARICAMENTO           IS 'DT_CARICAMENTO         '; 

CREATE UNIQUE INDEX DMALM_STG_EL_UNITA_ORGANIZZ_PK ON DMALM_STG_EL_UNITA_ORGANIZZ
(DMALM_STG_UNITA_ORG_PK)
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
	
ALTER TABLE DMALM_STG_EL_UNITA_ORGANIZZ ADD (
  CONSTRAINT DMALM_STG_EL_UNITA_ORGANIZZ_PK
  PRIMARY KEY
  (DMALM_STG_UNITA_ORG_PK)
  USING INDEX DMALM_STG_EL_UNITA_ORGANIZZ_PK);
SPOOL OFF;
