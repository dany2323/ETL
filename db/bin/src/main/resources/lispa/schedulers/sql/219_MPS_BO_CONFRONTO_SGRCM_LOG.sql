SPOOL 219_MPS_BO_CONFRONTO_SGRCM_LOG.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE MPS_BO_CONFRONTO_SGRCM_LOG
(
  ID                        NUMBER,
  DATA_INIZIO_ELABORAZIONE  TIMESTAMP(6),
  ESITO_ELABORAZIONE        NUMBER,
  DESCRIZIONE_ELABORAZIONE  VARCHAR2(200 BYTE),
  DATA_FINE_ELABORAZIONE    TIMESTAMP(6),
  CONFRONTO_SGRCM_PK        RAW(16)                 DEFAULT sys_guid()
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
COMMENT ON TABLE MPS_BO_CONFRONTO_SGRCM_LOG IS 'MPS_BO_CONFRONTO_SGRCM_LOG';
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.ID                       IS 'ID                      ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.DATA_INIZIO_ELABORAZIONE IS 'DATA_INIZIO_ELABORAZIONE' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.ESITO_ELABORAZIONE       IS 'ESITO_ELABORAZIONE      ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.DESCRIZIONE_ELABORAZIONE IS 'DESCRIZIONE_ELABORAZIONE' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.DATA_FINE_ELABORAZIONE   IS 'DATA_FINE_ELABORAZIONE  ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_SGRCM_LOG.CONFRONTO_SGRCM_PK       IS 'CONFRONTO_SGRCM_PK  ' ;
CREATE UNIQUE INDEX MPS_BO_CONFRONTO_SGRCM_LOG_PK ON MPS_BO_CONFRONTO_SGRCM_LOG 
    ( 
     CONFRONTO_SGRCM_PK 
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
ALTER TABLE MPS_BO_CONFRONTO_SGRCM_LOG 
    ADD CONSTRAINT MPS_BO_CONFRONTO_SGRCM_LOG_PK PRIMARY KEY ( CONFRONTO_SGRCM_PK ) 
    USING INDEX MPS_BO_CONFRONTO_SGRCM_LOG_PK;
SPOOL OFF;