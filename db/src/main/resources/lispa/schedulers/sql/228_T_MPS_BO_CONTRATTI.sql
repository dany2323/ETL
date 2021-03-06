SPOOL 228_T_MPS_BO_CONTRATTI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE T_MPS_BO_CONTRATTI
(
  IDCONTRATTO             NUMBER(10),
  CODCONTRATTO            VARCHAR2(32 BYTE),
  TITOLOCONTRATTO         VARCHAR2(255 BYTE),
  ANNO_RIFERIMENTO        NUMBER(4),
  DATA_INIZIO             DATE,
  DATA_FINE               DATE,
  TIPO                    VARCHAR2(4000 BYTE),
  STATO                   VARCHAR2(4000 BYTE),
  FIRMA_DIGITALE          CHAR(1 BYTE),
  VARIATO                 VARCHAR2(4000 BYTE),
  NUM_VARIAZIONI          NUMBER,
  CODDIREZIONE            VARCHAR2(10 BYTE),
  DESDIREZIONE            VARCHAR2(255 BYTE),
  CODUO                   VARCHAR2(10 BYTE),
  DESUO                   VARCHAR2(255 BYTE),
  CODSTRUTTURA            VARCHAR2(32 BYTE),
  DESSTRUTTURA            VARCHAR2(255 BYTE),
  TOTALE_CONTRATTO        NUMBER,
  TOTALE_IMPEGNATO        NUMBER,
  TOTALE_SPALMATO         NUMBER,
  TOTALE_VERBALIZZATO     NUMBER,
  TOTALE_RICHIESTO        NUMBER,
  TOTALE_FATTURATO        NUMBER,
  TOTALE_FATTURABILE      NUMBER,
  PROSSIMO_FIRMATARIO     VARCHAR2(4000 BYTE),
  IN_CORSO_IL             DATE,
  NUMERO_RILASCI          NUMBER,
  NUMERO_RILASCI_FORFAIT  NUMBER,
  NUMERO_RILASCI_CANONE   NUMBER,
  NUMERO_RILASCI_CONSUMO  NUMBER,
  NUMERO_VERBALI          NUMBER,
  NUMERO_VERBALI_FORFAIT  NUMBER,
  NUMERO_VERBALI_CONSUMO  NUMBER,
  DESMOTIVOVARIAZIONE     VARCHAR2(255 BYTE),
  REPOSITORY              VARCHAR2(10 BYTE),
  PRIORITA                VARCHAR2(20 BYTE),
  ID_SM                   NUMBER(10),
  SERVICE_MANAGER         VARCHAR2(257 BYTE)
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
COMMENT ON TABLE T_MPS_BO_CONTRATTI IS 'T_MPS_BO_CONTRATTI';
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.IDCONTRATTO            IS 'IDCONTRATTO           ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.CODCONTRATTO           IS 'CODCONTRATTO          ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TITOLOCONTRATTO        IS 'TITOLOCONTRATTO       ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.ANNO_RIFERIMENTO       IS 'ANNO_RIFERIMENTO      ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DATA_INIZIO            IS 'DATA_INIZIO           ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DATA_FINE              IS 'DATA_FINE             ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TIPO                   IS 'TIPO                  ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.STATO                  IS 'STATO                 ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.FIRMA_DIGITALE         IS 'FIRMA_DIGITALE        ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.VARIATO                IS 'VARIATO               ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUM_VARIAZIONI         IS 'NUM_VARIAZIONI        ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.CODDIREZIONE           IS 'CODDIREZIONE          ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DESDIREZIONE           IS 'DESDIREZIONE          ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.CODUO                  IS 'CODUO                 ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DESUO                  IS 'DESUO                 ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.CODSTRUTTURA           IS 'CODSTRUTTURA          ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DESSTRUTTURA           IS 'DESSTRUTTURA          ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_CONTRATTO       IS 'TOTALE_CONTRATTO      ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_IMPEGNATO       IS 'TOTALE_IMPEGNATO      ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_SPALMATO        IS 'TOTALE_SPALMATO       ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_VERBALIZZATO    IS 'TOTALE_VERBALIZZATO   ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_RICHIESTO       IS 'TOTALE_RICHIESTO      ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_FATTURATO       IS 'TOTALE_FATTURATO      ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.TOTALE_FATTURABILE     IS 'TOTALE_FATTURABILE    ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.PROSSIMO_FIRMATARIO    IS 'PROSSIMO_FIRMATARIO   ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.IN_CORSO_IL            IS 'IN_CORSO_IL           ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_RILASCI         IS 'NUMERO_RILASCI        ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_RILASCI_FORFAIT IS 'NUMERO_RILASCI_FORFAIT' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_RILASCI_CANONE  IS 'NUMERO_RILASCI_CANONE ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_RILASCI_CONSUMO IS 'NUMERO_RILASCI_CONSUMO' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_VERBALI         IS 'NUMERO_VERBALI        ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_VERBALI_FORFAIT IS 'NUMERO_VERBALI_FORFAIT' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.NUMERO_VERBALI_CONSUMO IS 'NUMERO_VERBALI_CONSUMO' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.DESMOTIVOVARIAZIONE    IS 'DESMOTIVOVARIAZIONE   ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.REPOSITORY             IS 'REPOSITORY            ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.PRIORITA               IS 'PRIORITA              ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.ID_SM                  IS 'ID_SM                 ' ;
COMMENT ON COLUMN T_MPS_BO_CONTRATTI.SERVICE_MANAGER        IS 'SERVICE_MANAGER       ' ;
CREATE UNIQUE INDEX T_MPS_BO_CONTRATTI_PK ON T_MPS_BO_CONTRATTI 
    ( 
     IDCONTRATTO ASC 
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
ALTER TABLE T_MPS_BO_CONTRATTI 
    ADD CONSTRAINT T_MPS_BO_CONTRATTI_PK PRIMARY KEY ( IDCONTRATTO ) 
    USING INDEX T_MPS_BO_CONTRATTI_PK;
SPOOL OFF;