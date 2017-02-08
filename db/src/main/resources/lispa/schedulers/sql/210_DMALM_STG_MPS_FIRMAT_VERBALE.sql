SPOOL 210_DMALM_STG_MPS_FIRMAT_VERBALE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_MPS_FIRMAT_VERBALE
(
  IDVERBALEVALIDAZIONE    NUMBER(10),
  IDUTENTE                NUMBER(10),
  FIRMATARIO_VERBALE      VARCHAR2(257 BYTE),
  TIPOLOGIA_RESPONSABILE  VARCHAR2(128 BYTE),
  ORDINE_FIRMA            NUMBER(4),
  FIRMATO                 CHAR(1 BYTE),
  DATA_FIRMA              DATE,
  IDENTE                  NUMBER(10),
  ENTE                    VARCHAR2(32 BYTE)
)
 PCTFREE 10 
 PCTUSED 40 
 MAXTRANS 255 
 TABLESPACE TS_ALM 
 LOGGING 
 STORAGE ( 
   INITIAL 655360 
   NEXT 1048576 
   PCTINCREASE 0 
   MINEXTENTS 1 
   MAXEXTENTS 2147483645 
   FREELISTS 1 
   FREELIST GROUPS 1 
   BUFFER_POOL DEFAULT 
 ); 
COMMENT ON TABLE DMALM_STG_MPS_FIRMAT_VERBALE IS 'DMALM_STG_MPS_FIRMAT_VERBALE';
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.IDVERBALEVALIDAZIONE   IS 'IDVERBALEVALIDAZIONE  ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.IDUTENTE               IS 'IDUTENTE              ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.FIRMATARIO_VERBALE     IS 'FIRMATARIO_VERBALE    ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.TIPOLOGIA_RESPONSABILE IS 'TIPOLOGIA_RESPONSABILE' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.ORDINE_FIRMA           IS 'ORDINE_FIRMA          ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.FIRMATO                IS 'FIRMATO               ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.DATA_FIRMA             IS 'DATA_FIRMA            ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.IDENTE                 IS 'IDENTE                ' ;
COMMENT ON COLUMN DMALM_STG_MPS_FIRMAT_VERBALE.ENTE                   IS 'ENTE                  ' ;
CREATE UNIQUE INDEX DMALM_STG_MPS_FIR_VERBALE_PK ON DMALM_STG_MPS_FIRMAT_VERBALE 
    ( 
     IDVERBALEVALIDAZIONE ASC,IDUTENTE ASC 
    ) 
    TABLESPACE TS_ALM 
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
ALTER TABLE DMALM_STG_MPS_FIRMAT_VERBALE 
    ADD CONSTRAINT DMALM_STG_MPS_FIR_VERBALE_PK PRIMARY KEY ( IDVERBALEVALIDAZIONE, IDUTENTE ) 
    USING INDEX DMALM_STG_MPS_FIR_VERBALE_PK;
SPOOL OFF;