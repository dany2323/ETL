SPOOL 261_DMALM_EL_PRODOTTI_ARCH.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE DMALM_EL_PRODOTTI_ARCH 
 ( 
  DMALM_PRODOTTO_PK               NUMBER(15)           NOT NULL,
  ID_PRODOTTO_EDMA                VARCHAR2(100 BYTE),
  ID_PRODOTTO                     VARCHAR2(100 BYTE),
  TIPO_OGGETTO                    VARCHAR2(12 BYTE),
  SIGLA                           VARCHAR2(4000 BYTE),
  NOME                            VARCHAR2(4000 BYTE),
  DS_PRODOTTO                     VARCHAR2(4000 BYTE),
  AREA_PRODOTTO                   VARCHAR2(4000 BYTE),
  RESPONSABILE_PRODOTTO           VARCHAR2(4000 BYTE),
  ANNULLATO                       VARCHAR2(2 BYTE),
  DT_ANNULLAMENTO                 DATE,
  AMBITO_MANUTENZIONE             VARCHAR2(4000 BYTE),
  AREA_TEMATICA                   VARCHAR2(4000 BYTE),
  BASE_DATI_ETL                   VARCHAR2(4000 BYTE),
  BASE_DATI_LETTURA               VARCHAR2(4000 BYTE),
  BASE_DATI_SCRITTURA             VARCHAR2(4000 BYTE),
  CATEGORIA                       VARCHAR2(4000 BYTE),
  FORNITURA_RISORSE_ESTERNE       VARCHAR2(4000 BYTE),
  CD_AREA_PRODOTTO                VARCHAR2(4000 BYTE),
  DT_CARICAMENTO                  DATE,
  DMALM_UNITAORGANIZZATIVA_FK_01  NUMBER(15),
  DMALM_PERSONALE_FK_02           NUMBER(15),
  DT_INIZIO_VALIDITA              DATE,
  DT_FINE_VALIDITA                DATE,
  AMBITO_TECNOLOGICO              VARCHAR2(4000 BYTE),
  AMBITO_MANUTENZIONE_DENOM       VARCHAR2(4000 BYTE),
  AMBITO_MANUTENZIONE_CODICE      VARCHAR2(4000 BYTE),
  STATO_PRODOTTO                  VARCHAR2(100 BYTE)
 ) 
 TABLESPACE "TS_ALM"; 
COMMENT ON TABLE DMALM_EL_PRODOTTI_ARCH IS 'DMALM_EL_PRODOTTI_ARCH';
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DMALM_PRODOTTO_PK              IS 'DMALM_PRODOTTO_PK        '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.ID_PRODOTTO_EDMA               IS 'ID_PRODOTTO_EDMA         '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.ID_PRODOTTO                    IS 'ID_PRODOTTO              '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.TIPO_OGGETTO                   IS 'TIPO_OGGETTO             '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.SIGLA                          IS 'SIGLA                    '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.NOME                           IS 'NOME                     '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DS_PRODOTTO                    IS 'DS_PRODOTTO              '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.AREA_PRODOTTO                  IS 'AREA_PRODOTTO            '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.RESPONSABILE_PRODOTTO          IS 'RESPONSABILE_PRODOTTO    '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.ANNULLATO                      IS 'ANNULLATO                '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DT_ANNULLAMENTO                IS 'DT_ANNULLAMENTO          '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.AMBITO_MANUTENZIONE            IS 'AMBITO_MANUTENZIONE      '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.AREA_TEMATICA                  IS 'AREA_TEMATICA            '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.BASE_DATI_ETL                  IS 'BASE_DATI_ETL            '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.BASE_DATI_LETTURA              IS 'BASE_DATI_LETTURA        '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.BASE_DATI_SCRITTURA            IS 'BASE_DATI_SCRITTURA      '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.CATEGORIA                      IS 'CATEGORIA                '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.FORNITURA_RISORSE_ESTERNE      IS 'FORNITURA_RISORSE_ESTERNE'; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.CD_AREA_PRODOTTO               IS 'CD_AREA_PRODOTTO         '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DT_CARICAMENTO                 IS 'DT_CARICAMENTO           '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DMALM_UNITAORGANIZZATIVA_FK_01 IS 'DMALM_UNITAORGANIZZATIVA_FK_01'; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DMALM_PERSONALE_FK_02          IS 'DMALM_PERSONALE_FK_02    '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DT_INIZIO_VALIDITA             IS 'DT_INIZIO_VALIDITA       '; 
COMMENT ON COLUMN DMALM_EL_PRODOTTI_ARCH.DT_FINE_VALIDITA               IS 'DT_FINE_VALIDITA         '; 

CREATE UNIQUE INDEX DMALM_EL_PRODOTTI_ARCH_PK ON DMALM_EL_PRODOTTI_ARCH
(DMALM_PRODOTTO_PK)
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
	
ALTER TABLE DMALM_EL_PRODOTTI_ARCH ADD (
  CONSTRAINT DMALM_EL_PRODOTTI_ARCH_PK
  PRIMARY KEY
  (DMALM_PRODOTTO_PK)
  USING INDEX DMALM_EL_PRODOTTI_ARCH_PK);
  
ALTER TABLE DMALM_EL_PRODOTTI_ARCH ADD (
  CONSTRAINT DMALM_EL_PERSONALE_FK_02 
  FOREIGN KEY (DMALM_PERSONALE_FK_02) 
  REFERENCES DMALM_EL_PERSONALE (DMALM_PERSONALE_PK),
  CONSTRAINT DMALM_EL_UNITAORGANIZZAT_FK_01 
  FOREIGN KEY (DMALM_UNITAORGANIZZATIVA_FK_01) 
  REFERENCES DMALM_EL_UNITA_ORGANIZZATIVE (DMALM_UNITA_ORG_PK));  
SPOOL OFF;
