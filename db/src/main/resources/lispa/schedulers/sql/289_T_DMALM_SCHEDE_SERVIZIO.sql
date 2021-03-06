SPOOL 289_T_DMALM_SCHEDE_SERVIZIO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_SCHEDE_SERVIZIO
(
  DMALM_SCHEDE_SERVIZIO_PK  NUMBER              NOT NULL,
  ID                        VARCHAR2(20 BYTE),
  NAME                      VARCHAR2(200 BYTE),
  SORTER                    NUMBER,
  DT_CARICAMENTO            DATE                NOT NULL,
  DT_INIZIO_VALIDITA        DATE                NOT NULL,
  DT_FINE_VALIDITA          DATE                NOT NULL,
  REPOSITORY                VARCHAR2(4 BYTE)    NOT NULL
)
 TABLESPACE "TS_ALM"; 
 
COMMENT ON TABLE T_DMALM_SCHEDE_SERVIZIO IS 'T_DMALM_SCHEDE_SERVIZIO';

COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.DMALM_SCHEDE_SERVIZIO_PK IS 'DMALM_SCHEDE_SERVIZIO_PK';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.ID IS 'ID';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.NAME IS 'NAME';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.SORTER IS 'SORTER';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.DT_CARICAMENTO IS 'DT_CARICAMENTO';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA';
COMMENT ON COLUMN T_DMALM_SCHEDE_SERVIZIO.REPOSITORY IS 'REPOSITORY';

CREATE UNIQUE INDEX T_DMALM_SCHEDE_SERVIZIO_PK ON T_DMALM_SCHEDE_SERVIZIO
(DMALM_SCHEDE_SERVIZIO_PK)
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
	
ALTER TABLE T_DMALM_SCHEDE_SERVIZIO ADD (
  CONSTRAINT T_DMALM_SCHEDE_SERVIZIO_PK
  PRIMARY KEY
  (DMALM_SCHEDE_SERVIZIO_PK)
  USING INDEX T_DMALM_SCHEDE_SERVIZIO_PK);
SPOOL OFF;
