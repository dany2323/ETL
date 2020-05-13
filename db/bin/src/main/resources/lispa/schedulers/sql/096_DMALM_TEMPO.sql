SPOOL 096_DMALM_TEMPO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK NUMBER (15)  NOT NULL , 
     DT_OSSERVAZIONE DATE , 
     GIORNO NUMBER (2) , 
     DS_GIORNO VARCHAR2 (15 BYTE) , 
     MESE VARCHAR2 (2 BYTE) , 
     DS_MESE VARCHAR2 (20 BYTE) , 
     TRIMESTRE VARCHAR2 (20 BYTE) , 
     SEMESTRE VARCHAR2 (20 BYTE) , 
     ANNO VARCHAR2 (4 BYTE) , 
     FL_FESTIVO NUMBER (1) 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 393216 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_TEMPO IS 'DMALM_TEMPO                       ';
COMMENT ON COLUMN DMALM_TEMPO.DMALM_TEMPO_PK IS 'DMALM_TEMPO_PK                    '; 
COMMENT ON COLUMN DMALM_TEMPO.DT_OSSERVAZIONE IS 'DT_OSSERVAZIONE                   '; 
COMMENT ON COLUMN DMALM_TEMPO.GIORNO IS 'GIORNO                            '; 
COMMENT ON COLUMN DMALM_TEMPO.DS_GIORNO IS 'DS_GIORNO                         '; 
COMMENT ON COLUMN DMALM_TEMPO.MESE IS 'MESE                              '; 
COMMENT ON COLUMN DMALM_TEMPO.DS_MESE IS 'DS_MESE                           '; 
COMMENT ON COLUMN DMALM_TEMPO.TRIMESTRE IS 'TRIMESTRE                         '; 
COMMENT ON COLUMN DMALM_TEMPO.SEMESTRE IS 'SEMESTRE                          '; 
COMMENT ON COLUMN DMALM_TEMPO.ANNO IS 'ANNO                              '; 
COMMENT ON COLUMN DMALM_TEMPO.FL_FESTIVO IS 'FL_FESTIVO                        '; 
CREATE UNIQUE INDEX PK_DMALM_TEMPO ON DMALM_TEMPO 
    ( 
     DMALM_TEMPO_PK ASC 
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
CREATE INDEX DMALM_TEMPO_IDX_1 ON DMALM_TEMPO 
    ( 
     DT_OSSERVAZIONE ASC 
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
CREATE INDEX DMALM_TEMPO_IDX_2 ON DMALM_TEMPO 
    ( 
     FL_FESTIVO ASC 
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
ALTER TABLE DMALM_TEMPO 
    ADD CONSTRAINT PK_DMALM_TEMPO PRIMARY KEY ( DMALM_TEMPO_PK ) 
    USING INDEX PK_DMALM_TEMPO ;
SPOOL OFF;
