SPOOL 293_DMALM_TEMPLATE_INT_TECNICA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_TEMPLATE_INT_TECNICA
(
  ID_FILIERA      NUMBER(6)                     NOT NULL,
  LIVELLO         NUMBER(6)                     NOT NULL,
  SOTTOLIVELLO    NUMBER(6),
  FK_WI           NUMBER(16)                    NOT NULL,
  CODICE_WI       VARCHAR2(100 BYTE),
  TIPO_WI         VARCHAR2(100 BYTE),
  ID_REPOSITORY   VARCHAR2(20 BYTE),
  URI_WI          VARCHAR2(4000 BYTE),
  CODICE_PROJECT  VARCHAR2(100 BYTE),
  RUOLO           VARCHAR2(100 BYTE),
  DT_CARICAMENTO  TIMESTAMP(6)                  NOT NULL
)
TABLESPACE "TS_ALM"; 

COMMENT ON TABLE DMALM_TEMPLATE_INT_TECNICA IS 'DMALM_TEMPLATE_INT_TECNICA';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.ID_FILIERA IS 'ID_FILIERA';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.LIVELLO IS 'LIVELLO';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.SOTTOLIVELLO IS 'SOTTOLIVELLO';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.FK_WI IS 'FK_WI';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.CODICE_WI IS 'CODICE_WI';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.TIPO_WI IS 'TIPO_WI';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.ID_REPOSITORY IS 'ID_REPOSITORY';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.URI_WI IS 'URI_WI';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.CODICE_PROJECT IS 'CODICE_PROJECT';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.RUOLO IS 'RUOLO';
COMMENT ON COLUMN DMALM_TEMPLATE_INT_TECNICA.DT_CARICAMENTO IS 'DT_CARICAMENTO';

CREATE UNIQUE INDEX DMALM_TEMPLATE_INT_TECNICA_PK ON DMALM_TEMPLATE_INT_TECNICA
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

CREATE INDEX IDX1_TEMPLATE_INT_TECNICA ON DMALM_TEMPLATE_INT_TECNICA
(FK_WI)
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
	
ALTER TABLE DMALM_TEMPLATE_INT_TECNICA ADD (
  CONSTRAINT DMALM_TEMPLATE_INT_TECNICA_PK
  PRIMARY KEY
  (ID_FILIERA, LIVELLO)
  USING INDEX DMALM_TEMPLATE_INT_TECNICA_PK);
SPOOL OFF;
