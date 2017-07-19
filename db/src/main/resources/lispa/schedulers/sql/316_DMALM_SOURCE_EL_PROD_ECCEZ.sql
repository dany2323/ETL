SPOOL 316_DMALM_SOURCE_EL_PROD_ECCEZ.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
CREATE TABLE DMALM_SOURCE_EL_PROD_ECCEZ
(
	SIGLA_OGGETTO_ELETTRA VARCHAR2(500 BYTE) NOT NULL,
	TIPO_EL_PROD_ECCEZIONE NUMBER

)
TABLESPACE "TS_ALM"; 

COMMENT ON TABLE DMALM_SOURCE_EL_PROD_ECCEZ IS 'DMALM_SOURCE_EL_PROD_ECCEZ';
COMMENT ON COLUMN DMALM_SOURCE_EL_PROD_ECCEZ.SIGLA_OGGETTO_ELETTRA IS 'SIGLA_OGGETTO_ELETTRA';
COMMENT ON COLUMN DMALM_SOURCE_EL_PROD_ECCEZ.TIPO_EL_PROD_ECCEZIONE IS 'TIPO_EL_PROD_ECCEZIONE';


CREATE UNIQUE INDEX DMALM_SOURCE_EL_PROD_ECCEZ_PK ON DMALM_SOURCE_EL_PROD_ECCEZ
(SIGLA_OGGETTO_ELETTRA)
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
	
ALTER TABLE DMALM_SOURCE_EL_PROD_ECCEZ ADD (
  CONSTRAINT DMALM_SOURCE_EL_PROD_ECCEZ_PK
  PRIMARY KEY
  (SIGLA_OGGETTO_ELETTRA)
  USING INDEX DMALM_SOURCE_EL_PROD_ECCEZ_PK);
SPOOL OFF;