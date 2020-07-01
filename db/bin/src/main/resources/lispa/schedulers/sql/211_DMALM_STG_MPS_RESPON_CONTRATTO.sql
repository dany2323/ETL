SPOOL 211_DMALM_STG_MPS_RESPON_CONTRATTO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_MPS_RESPON_CONTRATTO
(
  IDCONTRATTO               NUMBER(10),
  IDUTENTE                  NUMBER(10),
  RESPONSABILE              VARCHAR2(257 BYTE),
  DESTIPOLOGIARESPONSABILE  VARCHAR2(128 BYTE),
  FIRMATARIO                CHAR(1 BYTE),
  ORDINE_FIRMA              NUMBER(4),
  FIRMATO                   CHAR(1 BYTE),
  DATA_FIRMA                DATE,
  IDENTE                    NUMBER(10),
  ENTE                      VARCHAR2(32 BYTE)
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
COMMENT ON TABLE DMALM_STG_MPS_RESPON_CONTRATTO IS 'DMALM_STG_MPS_RESPON_CONTRATTO';
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.IDCONTRATTO              IS 'IDCONTRATTO             ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.IDUTENTE                 IS 'IDUTENTE                ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.RESPONSABILE             IS 'RESPONSABILE            ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.DESTIPOLOGIARESPONSABILE IS 'DESTIPOLOGIARESPONSABILE' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.FIRMATARIO               IS 'FIRMATARIO              ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.ORDINE_FIRMA             IS 'ORDINE_FIRMA            ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.FIRMATO                  IS 'FIRMATO                 ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.DATA_FIRMA               IS 'DATA_FIRMA              ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.IDENTE                   IS 'IDENTE                  ' ;
COMMENT ON COLUMN DMALM_STG_MPS_RESPON_CONTRATTO.ENTE                     IS 'ENTE                    ' ;
CREATE UNIQUE INDEX DMALM_STG_MPS_RESP_CONTR_PK ON DMALM_STG_MPS_RESPON_CONTRATTO 
    ( 
     IDCONTRATTO, IDUTENTE, DESTIPOLOGIARESPONSABILE
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
ALTER TABLE DMALM_STG_MPS_RESPON_CONTRATTO 
    ADD CONSTRAINT DMALM_STG_MPS_RESP_CONTR_PK PRIMARY KEY ( IDCONTRATTO, IDUTENTE, DESTIPOLOGIARESPONSABILE ) 
    USING INDEX DMALM_STG_MPS_RESP_CONTR_PK;
SPOOL OFF;