SPOOL 226_MPS_BO_VERBALI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE MPS_BO_VERBALI
(
  CODVERBALE            VARCHAR2(32 BYTE),
  DATA_VERBALE          DATE,
  DATA_FIRMA            DATE,
  NOTE                  VARCHAR2(4000 BYTE),
  CONFORME              CHAR(1 BYTE),
  TIPO_VERBALE          VARCHAR2(7 BYTE),
  STATO_VERBALE         VARCHAR2(4000 BYTE),
  TOTALE_VERBALE        NUMBER,
  FATTURATO_VERBALE     NUMBER,
  PROSSIMO_FIRMATARIO   VARCHAR2(4000 BYTE),
  FIRMA_DIGITALE        CHAR(1 BYTE),
  IDVERBALEVALIDAZIONE  NUMBER
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
COMMENT ON TABLE MPS_BO_VERBALI IS 'MPS_BO_VERBALI';
COMMENT ON COLUMN MPS_BO_VERBALI.CODVERBALE           IS 'CODVERBALE          ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.DATA_VERBALE         IS 'DATA_VERBALE        ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.DATA_FIRMA           IS 'DATA_FIRMA          ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.NOTE                 IS 'NOTE                ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.CONFORME             IS 'CONFORME            ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.TIPO_VERBALE         IS 'TIPO_VERBALE        ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.STATO_VERBALE        IS 'STATO_VERBALE       ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.TOTALE_VERBALE       IS 'TOTALE_VERBALE      ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.FATTURATO_VERBALE    IS 'FATTURATO_VERBALE   ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.PROSSIMO_FIRMATARIO  IS 'PROSSIMO_FIRMATARIO ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.FIRMA_DIGITALE       IS 'FIRMA_DIGITALE      ' ;
COMMENT ON COLUMN MPS_BO_VERBALI.IDVERBALEVALIDAZIONE IS 'IDVERBALEVALIDAZIONE' ;
CREATE INDEX MPS_VERBALI_IDX1 ON MPS_BO_VERBALI
(IDVERBALEVALIDAZIONE)
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
CREATE UNIQUE INDEX MPS_BO_VERBALI_PK ON MPS_BO_VERBALI 
    ( 
     CODVERBALE, DATA_VERBALE
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
ALTER TABLE MPS_BO_VERBALI 
    ADD CONSTRAINT MPS_BO_VERBALI_PK PRIMARY KEY ( CODVERBALE, DATA_VERBALE ) 
    USING INDEX MPS_BO_VERBALI_PK;

SPOOL OFF;