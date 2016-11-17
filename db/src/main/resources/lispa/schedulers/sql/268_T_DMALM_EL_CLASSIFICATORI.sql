SPOOL 268_T_DMALM_EL_CLASSIFICATORI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_EL_CLASSIFICATORI 
 ( 
  DMALM_CLASSIFICATORI_PK        NUMBER(15)          NOT NULL,
  ID_CLASSIFICATORE_ORESTE       VARCHAR2(200 BYTE)  NOT NULL,
  TIPO_CLASSIFICATORE            VARCHAR2(255 BYTE),
  CODICE_CLASSIFICATORE          VARCHAR2(4000 BYTE),
  DT_CARICAMENTO                 DATE,
  DT_INIZIO_VALIDITA             DATE                NOT NULL,
  DT_FINE_VALIDITA               DATE                NOT NULL
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE T_DMALM_EL_CLASSIFICATORI IS 'T_DMALM_EL_CLASSIFICATORI';
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.DMALM_CLASSIFICATORI_PK     IS 'DMALM_CLASSIFICATORI_PK    '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.ID_CLASSIFICATORE_ORESTE    IS 'ID_CLASSIFICATORE_ORESTE   '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.TIPO_CLASSIFICATORE         IS 'TIPO_CLASSIFICATORE        '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.CODICE_CLASSIFICATORE       IS 'CODICE_CLASSIFICATORE      '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.DT_CARICAMENTO              IS 'DT_CARICAMENTO             '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.DT_INIZIO_VALIDITA          IS 'DT_INIZIO_VALIDITA         '; 
COMMENT ON COLUMN T_DMALM_EL_CLASSIFICATORI.DT_FINE_VALIDITA            IS 'DT_FINE_VALIDITA           '; 

CREATE UNIQUE INDEX T_DMALM_EL_CLASSIFICATORI_PK ON T_DMALM_EL_CLASSIFICATORI
(DMALM_CLASSIFICATORI_PK)
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
	
ALTER TABLE T_DMALM_EL_CLASSIFICATORI ADD (
  CONSTRAINT T_DMALM_EL_CLASSIFICATORI_PK
  PRIMARY KEY
  (DMALM_CLASSIFICATORI_PK)
  USING INDEX T_DMALM_EL_CLASSIFICATORI_PK);
SPOOL OFF;
