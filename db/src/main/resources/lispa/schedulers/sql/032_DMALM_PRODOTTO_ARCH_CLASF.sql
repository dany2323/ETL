SPOOL 032_DMALM_PRODOTTO_ARCH_CLASF.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_PRODOTTO_ARCH_CLASF 
    ( 
     FK_PRODOTTO NUMBER (15)  NOT NULL , 
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
COMMENT ON TABLE DMALM_PRODOTTO_ARCH_CLASF IS 'DMALM_PRODOTTO_ARCH_CLASF         ';
COMMENT ON COLUMN DMALM_PRODOTTO_ARCH_CLASF.FK_PRODOTTO IS 'FK_PRODOTTO                       '; 
COMMENT ON COLUMN DMALM_PRODOTTO_ARCH_CLASF.FK_CLASSIFICATORI IS 'FK_CLASSIFICATORI                 '; 
COMMENT ON COLUMN DMALM_PRODOTTO_ARCH_CLASF.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
CREATE UNIQUE INDEX PK_PRODOTTO_CLASF ON DMALM_PRODOTTO_ARCH_CLASF 
    ( 
     FK_PRODOTTO ASC , 
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
ALTER TABLE DMALM_PRODOTTO_ARCH_CLASF 
    ADD CONSTRAINT PK_DMALM_PRODOTTO_ARCH_CLASF PRIMARY KEY ( FK_PRODOTTO, FK_CLASSIFICATORI ) 
    USING INDEX PK_PRODOTTO_CLASF ;
;
ALTER TABLE DMALM_PRODOTTO_ARCH_CLASF 
    ADD CONSTRAINT DMALM_PROD_CLASF_FK_01 FOREIGN KEY 
    ( 
     FK_CLASSIFICATORI
    ) 
    REFERENCES DMALM_CLASSIFICATORI 
    ( 
     DMALM_CLASSIFICATORI_PK
    ) 
    NOT DEFERRABLE 
;
ALTER TABLE DMALM_PRODOTTO_ARCH_CLASF 
    ADD CONSTRAINT DMALM_PROD_CLASF_FK_02 FOREIGN KEY 
    ( 
     FK_PRODOTTO
    ) 
    REFERENCES DMALM_PRODOTTO 
    ( 
     DMALM_PRODOTTO_SEQ
    ) 
    NOT DEFERRABLE 
;
;
SPOOL OFF;
