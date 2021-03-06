SPOOL 020_DMALM_FUNZIONALITA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_FUNZIONALITA 
    ( 
     DMALM_FUNZIONALITA_PK NUMBER (15)  NOT NULL , 
     DMALM_MODULO_FK_01 NUMBER (15) , 
     ID_FUNZIONALITA VARCHAR2 (500 BYTE)  NOT NULL , 
     TIPO_OGGETTO VARCHAR2 (12 BYTE) , 
     SIGLA_PRODOTTO VARCHAR2 (500 BYTE) , 
     SIGLA_SOTTOSISTEMA VARCHAR2 (500 BYTE) , 
     SIGLA_MODULO VARCHAR2 (500 BYTE) , 
     SIGLA_FUNZIONALITA VARCHAR2 (500 BYTE) , 
     NOME VARCHAR2 (500 BYTE) , 
     DS_FUNZIONALITA VARCHAR2 (4000 BYTE) , 
     ANNULLATO VARCHAR2 (2 BYTE) , 
     DT_INIZIO_VALIDITA DATE , 
     DT_FINE_VALIDITA DATE , 
     CATEGORIA VARCHAR2 (4000 BYTE) , 
     LINGUAGGI VARCHAR2 (4000 BYTE) , 
     TIPI_ELABORAZIONE VARCHAR2 (4000 BYTE) , 
     DT_INSERIMENTO_RECORD DATE , 
     DT_ANNULLAMENTO DATE , 
     DMALM_FUNZIONALITA_SEQ NUMBER (15)  NOT NULL 
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
COMMENT ON TABLE DMALM_FUNZIONALITA IS 'DMALM_FUNZIONALITA                ';
COMMENT ON COLUMN DMALM_FUNZIONALITA.DMALM_FUNZIONALITA_PK IS 'DMALM_FUNZIONALITA_PK             '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DMALM_MODULO_FK_01 IS 'DMALM_MODULO_FK_01                '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.ID_FUNZIONALITA IS 'ID_FUNZIONALITA                   '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.TIPO_OGGETTO IS 'TIPO_OGGETTO                      '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.SIGLA_PRODOTTO IS 'SIGLA_PRODOTTO                    '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.SIGLA_SOTTOSISTEMA IS 'SIGLA_SOTTOSISTEMA                '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.SIGLA_MODULO IS 'SIGLA_MODULO                      '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.SIGLA_FUNZIONALITA IS 'SIGLA_FUNZIONALITA                '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.NOME IS 'NOME                              '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DS_FUNZIONALITA IS 'DS_FUNZIONALITA                   '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA                '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA                  '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.CATEGORIA IS 'CATEGORIA                         '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.LINGUAGGI IS 'LINGUAGGI                         '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.TIPI_ELABORAZIONE IS 'TIPI_ELABORAZIONE                 '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DT_INSERIMENTO_RECORD IS 'DT_INSERIMENTO_RECORD             '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DT_ANNULLAMENTO IS 'DT_ANNULLAMENTO                   '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA.DMALM_FUNZIONALITA_SEQ IS 'DMALM_FUNZIONALITA_SEQ            '; 
CREATE UNIQUE INDEX DMALM_FUNZIONALITA_PK ON DMALM_FUNZIONALITA 
    ( 
     DMALM_FUNZIONALITA_SEQ ASC 
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
CREATE INDEX DMALM_FUNZIONALITA_IDX_1 ON DMALM_FUNZIONALITA 
    ( 
     DT_FINE_VALIDITA ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 327680 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE UNIQUE INDEX DMALM_FUNZIONALITA_UK_01 ON DMALM_FUNZIONALITA 
    ( 
     DMALM_FUNZIONALITA_PK ASC , 
     DMALM_MODULO_FK_01 ASC , 
     ID_FUNZIONALITA ASC , 
     SIGLA_PRODOTTO ASC , 
     SIGLA_MODULO ASC , 
     DT_INIZIO_VALIDITA ASC , 
     SIGLA_SOTTOSISTEMA ASC , 
     DT_INSERIMENTO_RECORD ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 1048576 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_FUNZIONALITA 
    ADD CONSTRAINT DMALM_FUNZIONALITA_PK PRIMARY KEY ( DMALM_FUNZIONALITA_SEQ ) 
    USING INDEX DMALM_FUNZIONALITA_PK ;
ALTER TABLE DMALM_FUNZIONALITA 
    ADD CONSTRAINT DMALM_FUNZIONALITA_UK_01 UNIQUE ( DMALM_FUNZIONALITA_PK , DMALM_MODULO_FK_01 , ID_FUNZIONALITA , SIGLA_PRODOTTO , SIGLA_MODULO , DT_INIZIO_VALIDITA , SIGLA_SOTTOSISTEMA , DT_INSERIMENTO_RECORD ) 
    USING INDEX DMALM_FUNZIONALITA_UK_01 ;
SPOOL OFF;
