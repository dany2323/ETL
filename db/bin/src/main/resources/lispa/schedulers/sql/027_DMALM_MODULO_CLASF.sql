SPOOL 027_DMALM_MODULO_CLASF.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_MODULO_CLASF 
    ( 
     FK_MODULO NUMBER (15)  NOT NULL , 
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
COMMENT ON TABLE DMALM_MODULO_CLASF IS 'DMALM_MODULO_CLASF                ';
COMMENT ON COLUMN DMALM_MODULO_CLASF.FK_MODULO IS 'FK_MODULO                         '; 
COMMENT ON COLUMN DMALM_MODULO_CLASF.FK_CLASSIFICATORI IS 'FK_CLASSIFICATORI                 '; 
COMMENT ON COLUMN DMALM_MODULO_CLASF.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
CREATE UNIQUE INDEX MODULO_CLASF_PK ON DMALM_MODULO_CLASF 
    ( 
     FK_MODULO ASC , 
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
ALTER TABLE DMALM_MODULO_CLASF 
    ADD CONSTRAINT PK_DMALM_MODULO_CLASF PRIMARY KEY ( FK_MODULO, FK_CLASSIFICATORI ) 
    USING INDEX MODULO_CLASF_PK ;
ALTER TABLE DMALM_MODULO_CLASF 
    ADD CONSTRAINT DMALM_MOD_CLASF_FK_01 FOREIGN KEY 
    ( 
     FK_CLASSIFICATORI
    ) 
    REFERENCES DMALM_CLASSIFICATORI 
    ( 
     DMALM_CLASSIFICATORI_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_MODULO_CLASF 
    ADD CONSTRAINT DMALM_MOD_CLASF_FK_02 FOREIGN KEY 
    ( 
     FK_MODULO
    ) 
    REFERENCES DMALM_MODULO 
    ( 
     DMALM_MODULO_SEQ
    ) 
    NOT DEFERRABLE 
;
;
SPOOL OFF;