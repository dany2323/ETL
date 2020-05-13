SPOOL 084_DMALM_STG_AMBIENTE_TECNOLOGICO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_AMBIENTE_TECNOLOGICO 
    ( 
     ID_AMBIENTE_TECNOLOGICO VARCHAR2 (100 BYTE) , 
     NOME_AMBIENTE_TECNOLOGICO VARCHAR2 (4000 BYTE) , 
     DESCR_AMBIENTE_TECNOLOGICO VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     ID_EDMA_PADRE NUMBER , 
     ID_EDMA NUMBER , 
     SIGLA_PRODOTTO VARCHAR2 (4000 BYTE) , 
     SIGLA_MODULO VARCHAR2 (4000 BYTE) , 
     VERSIONE_SO VARCHAR2 (4000 BYTE) , 
     CLASF_ARCHI_RIFERIMENTO VARCHAR2 (4000 BYTE) , 
     CLASF_INFRASTRUTTURE VARCHAR2 (4000 BYTE) , 
     CLASF_SO VARCHAR2 (4000 BYTE) , 
     CLASF_TIPI_LAYER VARCHAR2 (4000 BYTE) , 
     ID_AMB_TECNOLOGICO_PK NUMBER  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 655360 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_STG_AMBIENTE_TECNOLOGICO IS 'DMALM_STG_AMBIENTE_TECNOLOGICO    ';
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.ID_AMBIENTE_TECNOLOGICO IS 'ID_AMBIENTE_TECNOLOGICO           '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.NOME_AMBIENTE_TECNOLOGICO IS 'NOME_AMBIENTE_TECNOLOGICO         '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.DESCR_AMBIENTE_TECNOLOGICO IS 'DESCR_AMBIENTE_TECNOLOGICO        '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.ID_EDMA_PADRE IS 'ID_EDMA_PADRE                     '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.ID_EDMA IS 'ID_EDMA                           '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.SIGLA_PRODOTTO IS 'SIGLA_PRODOTTO                    '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.SIGLA_MODULO IS 'SIGLA_MODULO                      '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.VERSIONE_SO IS 'VERSIONE_SO                       '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.CLASF_ARCHI_RIFERIMENTO IS 'CLASF_ARCHI_RIFERIMENTO           '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.CLASF_INFRASTRUTTURE IS 'CLASF_INFRASTRUTTURE              '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.CLASF_SO IS 'CLASF_SO                          '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.CLASF_TIPI_LAYER IS 'CLASF_TIPI_LAYER                  '; 
COMMENT ON COLUMN DMALM_STG_AMBIENTE_TECNOLOGICO.ID_AMB_TECNOLOGICO_PK IS 'ID_AMB_TECNOLOGICO_PK             '; 
CREATE UNIQUE INDEX PK_DMALM_STG_AMBTECN ON DMALM_STG_AMBIENTE_TECNOLOGICO 
    ( 
     ID_AMB_TECNOLOGICO_PK ASC 
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
ALTER TABLE DMALM_STG_AMBIENTE_TECNOLOGICO 
    ADD CONSTRAINT PK_DMALM_STG_AMBTECN PRIMARY KEY ( ID_AMB_TECNOLOGICO_PK ) 
    USING INDEX PK_DMALM_STG_AMBTECN ;
SPOOL OFF;
