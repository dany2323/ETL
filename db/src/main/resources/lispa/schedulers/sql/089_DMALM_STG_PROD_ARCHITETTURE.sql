SPOOL 089_DMALM_STG_PROD_ARCHITETTURE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_PROD_ARCHITETTURE 
    ( 
     ID_EDMA_PRODOTTO NUMBER (38) , 
     ID_PRODOTTO VARCHAR2 (100 BYTE) , 
     TIPO_OGGETTO VARCHAR2 (12 BYTE) , 
     SIGLA_PRODOTTO VARCHAR2 (4000 BYTE) , 
     NOME_PRODOTTO VARCHAR2 (4000 BYTE) , 
     DESCRIZIONE_PRODOTTO VARCHAR2 (4000 BYTE) , 
     EDMA_AREA_PRODOTTO VARCHAR2 (4000 BYTE) , 
     EDMA_RESP_PRODOTTO VARCHAR2 (4000 BYTE) , 
     PRODOTTO_ANNULLATO VARCHAR2 (2 BYTE) , 
     DFV_PRODOTTO_ANNULLATO VARCHAR2 (8 BYTE) , 
     CLASF_AMBITO_MANUTENZIONE VARCHAR2 (4000 BYTE) , 
     CLASF_AREA_TEMATICA VARCHAR2 (4000 BYTE) , 
     CLASF_BASE_DATI_ETL VARCHAR2 (4000 BYTE) , 
     CLASF_BASE_DATI_LETTURA VARCHAR2 (4000 BYTE) , 
     CLASF_BASE_DATI_SCRITTURA VARCHAR2 (4000 BYTE) , 
     CLASF_FORN_RIS_EST_GARA VARCHAR2 (4000 BYTE) , 
     CLASF_CATEGORIA VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     DMALM_STG_PROD_ARCHITETTURE_PK NUMBER  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 2097152 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_STG_PROD_ARCHITETTURE IS 'DMALM_STG_PROD_ARCHITETTURE       ';
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.ID_EDMA_PRODOTTO IS 'ID_EDMA_PRODOTTO                  '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.ID_PRODOTTO IS 'ID_PRODOTTO                       '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.TIPO_OGGETTO IS 'TIPO_OGGETTO                      '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.SIGLA_PRODOTTO IS 'SIGLA_PRODOTTO                    '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.NOME_PRODOTTO IS 'NOME_PRODOTTO                     '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.DESCRIZIONE_PRODOTTO IS 'DESCRIZIONE_PRODOTTO              '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.EDMA_AREA_PRODOTTO IS 'EDMA_AREA_PRODOTTO                '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.EDMA_RESP_PRODOTTO IS 'EDMA_RESP_PRODOTTO                '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.PRODOTTO_ANNULLATO IS 'PRODOTTO_ANNULLATO                '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.DFV_PRODOTTO_ANNULLATO IS 'DFV_PRODOTTO_ANNULLATO            '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_AMBITO_MANUTENZIONE IS 'CLASF_AMBITO_MANUTENZIONE         '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_AREA_TEMATICA IS 'CLASF_AREA_TEMATICA               '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_BASE_DATI_ETL IS 'CLASF_BASE_DATI_ETL               '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_BASE_DATI_LETTURA IS 'CLASF_BASE_DATI_LETTURA           '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_BASE_DATI_SCRITTURA IS 'CLASF_BASE_DATI_SCRITTURA         '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_FORN_RIS_EST_GARA IS 'CLASF_FORN_RIS_EST_GARA           '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.CLASF_CATEGORIA IS 'CLASF_CATEGORIA                   '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_STG_PROD_ARCHITETTURE.DMALM_STG_PROD_ARCHITETTURE_PK IS 'DMALM_STG_PROD_ARCHITETTURE_PK    '; 
CREATE INDEX DMALM_STG_PROD_ARCH_IDX_1 ON DMALM_STG_PROD_ARCHITETTURE 
    ( 
     SIGLA_PRODOTTO ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
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
CREATE INDEX DMALM_STG_PROD_ARCH_IDX_2 ON DMALM_STG_PROD_ARCHITETTURE 
    ( 
     NOME_PRODOTTO ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 131072 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX DMALM_STG_PROD_ARCH_IDX_3 ON DMALM_STG_PROD_ARCHITETTURE 
    ( 
     DATA_CARICAMENTO ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
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
CREATE UNIQUE INDEX PK_DMALM_STG_PROD_ARCHITETTURE ON DMALM_STG_PROD_ARCHITETTURE 
    ( 
     DMALM_STG_PROD_ARCHITETTURE_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
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
ALTER TABLE DMALM_STG_PROD_ARCHITETTURE 
    ADD CONSTRAINT PK_DMALM_STG_PROD_ARCHITETTURE PRIMARY KEY ( DMALM_STG_PROD_ARCHITETTURE_PK ) 
    USING INDEX PK_DMALM_STG_PROD_ARCHITETTURE ;
SPOOL OFF;
