SPOOL 265_DMALM_EL_AMBTECN_CLASSIF.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_EL_AMBTECN_CLASSIF 
 ( 
  DMALM_AMBTECN_CLASSIF_PK     NUMBER(15)           NOT NULL,
  ID_AMB_TECN                  VARCHAR2(100 BYTE)   NOT NULL,
  NOME_AMB_TECN                VARCHAR2(4000 BYTE),
  DESCRIZIONE_AMB_TECN         VARCHAR2(4000 BYTE),
  ID_CLASSIFICATORE            VARCHAR2(100 BYTE)   NOT NULL,
  NOME_CLASSIFICATORE          VARCHAR2(4000 BYTE),
  DESCR_CLASSIFICATORE         VARCHAR2(255 BYTE),
  ANNULLATO                    VARCHAR2(100 BYTE),
  DT_ANNULLAMENTO              DATE,
  DT_CARICAMENTO               DATE,
  DT_INIZIO_VALIDITA           DATE,
  DT_FINE_VALIDITA             DATE
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE DMALM_EL_AMBTECN_CLASSIF IS 'DMALM_EL_AMBTECN_CLASSIF';
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DMALM_AMBTECN_CLASSIF_PK    IS 'DMALM_AMBTECN_CLASSIF_PK'; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.ID_AMB_TECN                 IS 'ID_AMB_TECN                '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.NOME_AMB_TECN               IS 'NOME_AMB_TECN              '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DESCRIZIONE_AMB_TECN        IS 'DESCRIZIONE_AMB_TECN       '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.ID_CLASSIFICATORE           IS 'ID_CLASSIFICATORE          '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.NOME_CLASSIFICATORE         IS 'NOME_CLASSIFICATORE        '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DESCR_CLASSIFICATORE        IS 'DESCR_CLASSIFICATORE       '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.ANNULLATO                   IS 'ANNULLATO                  '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DT_ANNULLAMENTO             IS 'DT_ANNULLAMENTO            '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DT_CARICAMENTO              IS 'DT_CARICAMENTO             '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DT_INIZIO_VALIDITA          IS 'DT_INIZIO_VALIDITA         '; 
COMMENT ON COLUMN DMALM_EL_AMBTECN_CLASSIF.DT_FINE_VALIDITA            IS 'DT_FINE_VALIDITA           '; 

CREATE UNIQUE INDEX DMALM_EL_AMBTECN_CLASS_PK ON DMALM_EL_AMBTECN_CLASSIF
(DMALM_AMBTECN_CLASSIF_PK)
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
	
ALTER TABLE DMALM_EL_AMBTECN_CLASSIF ADD (
  CONSTRAINT DMALM_EL_AMBTECN_CLASS_PK
  PRIMARY KEY
  (DMALM_AMBTECN_CLASSIF_PK)
  USING INDEX DMALM_EL_AMBTECN_CLASS_PK);
SPOOL OFF;
