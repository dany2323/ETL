SPOOL 021_DMALM_FUNZIONALITA_CLASF.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_FUNZIONALITA_CLASF 
    ( 
     FK_FUNZIONALITA NUMBER (15)  NOT NULL , 
     FK_CLASSIFICATORI NUMBER (15)  NOT NULL , 
     DT_CARICAMENTO TIMESTAMP 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 65536 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_FUNZIONALITA_CLASF IS 'DMALM_FUNZIONALITA_CLASF          ';
COMMENT ON COLUMN DMALM_FUNZIONALITA_CLASF.FK_FUNZIONALITA IS 'FK_FUNZIONALITA                   '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA_CLASF.FK_CLASSIFICATORI IS 'FK_CLASSIFICATORI                 '; 
COMMENT ON COLUMN DMALM_FUNZIONALITA_CLASF.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
CREATE UNIQUE INDEX FUNZ_CLASF_PK ON DMALM_FUNZIONALITA_CLASF 
    ( 
     FK_FUNZIONALITA ASC , 
     FK_CLASSIFICATORI ASC 
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
ALTER TABLE DMALM_FUNZIONALITA_CLASF 
    ADD CONSTRAINT PK_DMALM_FUNZIONALITA_CLASF PRIMARY KEY ( FK_FUNZIONALITA, FK_CLASSIFICATORI ) 
    USING INDEX FUNZ_CLASF_PK ;
ALTER TABLE DMALM_FUNZIONALITA_CLASF 
    ADD CONSTRAINT DMALM_FUNZ_CLASF_FK_01 FOREIGN KEY 
    ( 
     FK_CLASSIFICATORI
    ) 
    REFERENCES DMALM_CLASSIFICATORI 
    ( 
     DMALM_CLASSIFICATORI_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_FUNZIONALITA_CLASF 
    ADD CONSTRAINT DMALM_FUNZ_CLASF_FK_02 FOREIGN KEY 
    ( 
     FK_FUNZIONALITA
    ) 
    REFERENCES DMALM_FUNZIONALITA 
    ( 
     DMALM_FUNZIONALITA_SEQ
    ) 
    NOT DEFERRABLE 
;
SPOOL OFF;
