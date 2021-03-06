SPOOL 291_DMALM_TEMPLATE_DOCUMENTI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_TEMPLATE_DOCUMENTI
(
  TEMPLATE      VARCHAR2(30 BYTE),
  ID_FILIERA    NUMBER(6),
  LIVELLO       NUMBER(6),
  FK_WI         NUMBER(16),
  FK_DOCUMENTO  NUMBER(16)
)
TABLESPACE "TS_ALM"; 

COMMENT ON TABLE DMALM_TEMPLATE_DOCUMENTI IS 'DMALM_TEMPLATE_DOCUMENTI';
COMMENT ON COLUMN DMALM_TEMPLATE_DOCUMENTI.TEMPLATE IS 'TEMPLATE';
COMMENT ON COLUMN DMALM_TEMPLATE_DOCUMENTI.ID_FILIERA IS 'ID_FILIERA';
COMMENT ON COLUMN DMALM_TEMPLATE_DOCUMENTI.LIVELLO IS 'LIVELLO';
COMMENT ON COLUMN DMALM_TEMPLATE_DOCUMENTI.FK_WI IS 'FK_WI';
COMMENT ON COLUMN DMALM_TEMPLATE_DOCUMENTI.FK_DOCUMENTO IS 'FK_DOCUMENTO';

CREATE UNIQUE INDEX DMALM_TEMPLATE_DOCUMENTI_PK ON DMALM_TEMPLATE_DOCUMENTI
(TEMPLATE, ID_FILIERA, LIVELLO, FK_WI, FK_DOCUMENTO)
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

ALTER TABLE DMALM_TEMPLATE_DOCUMENTI ADD (
  CONSTRAINT DMALM_TEMPLATE_DOCUMENTI_PK
  PRIMARY KEY
  (TEMPLATE, ID_FILIERA, LIVELLO, FK_WI, FK_DOCUMENTO)
  USING INDEX DMALM_TEMPLATE_DOCUMENTI_PK);
SPOOL OFF;
