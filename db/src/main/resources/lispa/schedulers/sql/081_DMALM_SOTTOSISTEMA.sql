SPOOL 081_DMALM_SOTTOSISTEMA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SOTTOSISTEMA 
    ( 
     DMALM_SOTTOSISTEMA_PK NUMBER (15)  NOT NULL , 
     DMALM_PRODOTTO_FK_01 NUMBER (15) , 
     ID_SOTTOSISTEMA VARCHAR2 (50 BYTE) , 
     TIPO_OGGETTO VARCHAR2 (1 BYTE) , 
     SIGLA_PRODOTTO VARCHAR2 (500 BYTE) , 
     SIGLA_SOTTOSISTEMA VARCHAR2 (500 BYTE) , 
     NOME VARCHAR2 (500 BYTE) , 
     DS_SOTTOSISTEMA VARCHAR2 (4000 BYTE) , 
     ANNULLATO VARCHAR2 (2 BYTE) , 
     DT_INIZIO_VALIDITA DATE  NOT NULL , 
     DT_FINE_VALIDITA DATE , 
     TIPO VARCHAR2 (4000 BYTE) , 
     BASE_DATI_ETL VARCHAR2 (4000 BYTE) , 
     BASE_DATI_LETTURA VARCHAR2 (4000 BYTE) , 
     BASE_DATI_SCRITTURA VARCHAR2 (4000 BYTE) , 
     DT_INSERIMENTO_RECORD DATE , 
     DT_ANNULLAMENTO DATE 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 196608 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SOTTOSISTEMA IS 'DMALM_SOTTOSISTEMA                ';
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DMALM_SOTTOSISTEMA_PK IS 'DMALM_SOTTOSISTEMA_PK             '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DMALM_PRODOTTO_FK_01 IS 'DMALM_PRODOTTO_FK_01              '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.ID_SOTTOSISTEMA IS 'ID_SOTTOSISTEMA                   '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.TIPO_OGGETTO IS 'TIPO_OGGETTO                      '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.SIGLA_PRODOTTO IS 'SIGLA_PRODOTTO                    '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.SIGLA_SOTTOSISTEMA IS 'SIGLA_SOTTOSISTEMA                '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.NOME IS 'NOME                              '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DS_SOTTOSISTEMA IS 'DS_SOTTOSISTEMA                   '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA                '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA                  '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.TIPO IS 'TIPO                              '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.BASE_DATI_ETL IS 'BASE_DATI_ETL                     '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.BASE_DATI_LETTURA IS 'BASE_DATI_LETTURA                 '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.BASE_DATI_SCRITTURA IS 'BASE_DATI_SCRITTURA               '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DT_INSERIMENTO_RECORD IS 'DT_INSERIMENTO_RECORD             '; 
COMMENT ON COLUMN DMALM_SOTTOSISTEMA.DT_ANNULLAMENTO IS 'DT_ANNULLAMENTO                   '; 
CREATE INDEX DMALM_SOTTOSISTEMA_IDX_1 ON DMALM_SOTTOSISTEMA 
    ( 
     DT_FINE_VALIDITA ASC 
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
CREATE UNIQUE INDEX PK_DMALM_SOTTOSISTEMA ON DMALM_SOTTOSISTEMA 
    ( 
     DMALM_SOTTOSISTEMA_PK ASC , 
     DT_INIZIO_VALIDITA ASC 
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
CREATE UNIQUE INDEX DMALM_SOTTOSISTEMA_UK_01 ON DMALM_SOTTOSISTEMA 
    ( 
     DMALM_SOTTOSISTEMA_PK ASC , 
     DMALM_PRODOTTO_FK_01 ASC , 
     ID_SOTTOSISTEMA ASC , 
     SIGLA_PRODOTTO ASC , 
     DT_INIZIO_VALIDITA ASC , 
     DT_INSERIMENTO_RECORD ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
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
ALTER TABLE DMALM_SOTTOSISTEMA 
    ADD CONSTRAINT PK_DMALM_SOTTOSISTEMA PRIMARY KEY ( DMALM_SOTTOSISTEMA_PK, DT_INIZIO_VALIDITA ) 
    USING INDEX PK_DMALM_SOTTOSISTEMA ;
ALTER TABLE DMALM_SOTTOSISTEMA 
    ADD CONSTRAINT DMALM_SOTTOSISTEMA_UK_01 UNIQUE ( DMALM_SOTTOSISTEMA_PK , DMALM_PRODOTTO_FK_01 , ID_SOTTOSISTEMA , SIGLA_PRODOTTO , DT_INIZIO_VALIDITA , DT_INSERIMENTO_RECORD ) 
    USING INDEX DMALM_SOTTOSISTEMA_UK_01 ;
SPOOL OFF;
