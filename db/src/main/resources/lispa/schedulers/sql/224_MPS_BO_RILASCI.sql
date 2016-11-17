SPOOL 224_MPS_BO_RILASCI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE MPS_BO_RILASCI
(
  IDRILASCIO               NUMBER(10),
  IDCONTRATTO              NUMBER(10),
  CODRILASCIO              VARCHAR2(32 BYTE),
  TIPORILASCIO             VARCHAR2(7 BYTE),
  SOTTOTIPORILASCIO        VARCHAR2(4000 BYTE),
  TITOLORILASCIO           VARCHAR2(4000 BYTE),
  DESATTIVITA              VARCHAR2(4000 BYTE),
  DATA_INIZIO              DATE,
  DATA_RILASCIO            DATE,
  DATA_VALIDAZIONE         DATE,
  STATO_FATTURAZIONE       VARCHAR2(22 BYTE),
  STATO_FINANZIAMENTO      VARCHAR2(23 BYTE),
  IMPORTO_RILASCIO         NUMBER,
  TOTALE_SPALMATO          NUMBER,
  TOTALE_VERBALIZZATO      NUMBER,
  TOTALE_RICHIESTA         NUMBER,
  TOTALE_FATTURATO         NUMBER,
  TOTALE_FATTURABILE       NUMBER,
  DATA_RILASCIO_EFFETTIVO  DATE,
  VARIANTE_MIGLIORATIVA    VARCHAR2(1 BYTE),
  STATO_VERBALIZZAZIONE    VARCHAR2(20 BYTE)
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
COMMENT ON TABLE MPS_BO_RILASCI IS 'MPS_BO_RILASCI';
COMMENT ON COLUMN MPS_BO_RILASCI.IDRILASCIO              IS 'IDRILASCIO             ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.IDCONTRATTO             IS 'IDCONTRATTO            ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.CODRILASCIO             IS 'CODRILASCIO            ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TIPORILASCIO            IS 'TIPORILASCIO           ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.SOTTOTIPORILASCIO       IS 'SOTTOTIPORILASCIO      ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TITOLORILASCIO          IS 'TITOLORILASCIO         ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.DESATTIVITA             IS 'DESATTIVITA            ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.DATA_INIZIO             IS 'DATA_INIZIO            ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.DATA_RILASCIO           IS 'DATA_RILASCIO          ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.DATA_VALIDAZIONE        IS 'DATA_VALIDAZIONE       ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.STATO_FATTURAZIONE      IS 'STATO_FATTURAZIONE     ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.STATO_FINANZIAMENTO     IS 'STATO_FINANZIAMENTO    ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.IMPORTO_RILASCIO        IS 'IMPORTO_RILASCIO       ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TOTALE_SPALMATO         IS 'TOTALE_SPALMATO        ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TOTALE_VERBALIZZATO     IS 'TOTALE_VERBALIZZATO    ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TOTALE_RICHIESTA        IS 'TOTALE_RICHIESTA       ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TOTALE_FATTURATO        IS 'TOTALE_FATTURATO       ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.TOTALE_FATTURABILE      IS 'TOTALE_FATTURABILE     ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.DATA_RILASCIO_EFFETTIVO IS 'DATA_RILASCIO_EFFETTIVO' ;
COMMENT ON COLUMN MPS_BO_RILASCI.VARIANTE_MIGLIORATIVA   IS 'VARIANTE_MIGLIORATIVA  ' ;
COMMENT ON COLUMN MPS_BO_RILASCI.STATO_VERBALIZZAZIONE   IS 'STATO_VERBALIZZAZIONE  ' ;
CREATE INDEX MPS_RILASCI_IDCONTR ON MPS_BO_RILASCI
(IDCONTRATTO)
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
CREATE UNIQUE INDEX MPS_BO_RILASCI_PK ON MPS_BO_RILASCI 
    ( 
     IDRILASCIO ASC 
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
ALTER TABLE MPS_BO_RILASCI 
    ADD CONSTRAINT MPS_BO_RILASCI_PK PRIMARY KEY ( IDRILASCIO ) 
    USING INDEX MPS_BO_RILASCI_PK;

SPOOL OFF;