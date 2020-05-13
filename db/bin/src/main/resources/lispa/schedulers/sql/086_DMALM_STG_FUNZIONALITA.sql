SPOOL 086_DMALM_STG_FUNZIONALITA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_FUNZIONALITA 
    ( 
     ID_EDMA_FUNZIONALITA NUMBER (38) , 
     ID_EDMA_PADRE NUMBER , 
     ID_FUNZIONALITA VARCHAR2 (100 BYTE) , 
     TIPO_FUNZIONALITA VARCHAR2 (12 BYTE) , 
     SIGLA_PRODOTTO VARCHAR2 (4000 BYTE) , 
     SIGLA_SOTTOSISTEMA VARCHAR2 (4000 BYTE) , 
     SIGLA_MODULO VARCHAR2 (4000 BYTE) , 
     SIGLA_FUNZIONALITA VARCHAR2 (4000 BYTE) , 
     NOME_FUNZIONALITA VARCHAR2 (4000 BYTE) , 
     DESCRIZIONE VARCHAR2 (4000 BYTE) , 
     FUNZIONALITA_ANNULLATA VARCHAR2 (2 BYTE) , 
     DFV_FUNZIONALITA_ANNULLATA VARCHAR2 (8 BYTE) , 
     CLASF_CATEGORIA VARCHAR2 (4000 BYTE) , 
     CLASF_LINGUAGGIO_DI_PROG VARCHAR2 (4000 BYTE) , 
     CLASF_TIPO_ELABOR VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     DMALM_STG_FUNZIONALITA_PK NUMBER  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 4194304 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_STG_FUNZIONALITA IS 'DMALM_STG_FUNZIONALITA            ';
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.ID_EDMA_FUNZIONALITA IS 'ID_EDMA_FUNZIONALITA              '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.ID_EDMA_PADRE IS 'ID_EDMA_PADRE                     '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.ID_FUNZIONALITA IS 'ID_FUNZIONALITA                   '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.TIPO_FUNZIONALITA IS 'TIPO_FUNZIONALITA                 '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.SIGLA_PRODOTTO IS 'SIGLA_PRODOTTO                    '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.SIGLA_SOTTOSISTEMA IS 'SIGLA_SOTTOSISTEMA                '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.SIGLA_MODULO IS 'SIGLA_MODULO                      '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.SIGLA_FUNZIONALITA IS 'SIGLA_FUNZIONALITA                '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.NOME_FUNZIONALITA IS 'NOME_FUNZIONALITA                 '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.DESCRIZIONE IS 'DESCRIZIONE                       '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.FUNZIONALITA_ANNULLATA IS 'FUNZIONALITA_ANNULLATA            '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.DFV_FUNZIONALITA_ANNULLATA IS 'DFV_FUNZIONALITA_ANNULLATA        '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.CLASF_CATEGORIA IS 'CLASF_CATEGORIA                   '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.CLASF_LINGUAGGIO_DI_PROG IS 'CLASF_LINGUAGGIO_DI_PROG          '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.CLASF_TIPO_ELABOR IS 'CLASF_TIPO_ELABOR                 '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_STG_FUNZIONALITA.DMALM_STG_FUNZIONALITA_PK IS 'DMALM_STG_FUNZIONALITA_PK         '; 
CREATE UNIQUE INDEX DMALM_STG_FUNZIONALITA_PK ON DMALM_STG_FUNZIONALITA 
    ( 
     DMALM_STG_FUNZIONALITA_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 196608 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX DMALM_STG_FUNZIONALITA_IDX_1 ON DMALM_STG_FUNZIONALITA 
    ( 
     SIGLA_FUNZIONALITA ASC 
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
CREATE INDEX DMALM_STG_FUNZIONALITA_IDX_2 ON DMALM_STG_FUNZIONALITA 
    ( 
     NOME_FUNZIONALITA ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 851968 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_STG_FUNZIONALITA 
    ADD CONSTRAINT DMALM_STG_FUNZIONALITA_PK PRIMARY KEY ( DMALM_STG_FUNZIONALITA_PK ) 
    USING INDEX DMALM_STG_FUNZIONALITA_PK ;
SPOOL OFF;
