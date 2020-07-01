SPOOL 107_T_DMALM_AMBTECN_CLASF.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_AMBTECN_CLASF 
    ( 
     FK_AMBIENTE_TECNOLOGICO NUMBER (15)  NOT NULL , 
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
COMMENT ON TABLE T_DMALM_AMBTECN_CLASF IS 'T_DMALM_AMBTECN_CLASF             ';
COMMENT ON COLUMN T_DMALM_AMBTECN_CLASF.FK_AMBIENTE_TECNOLOGICO IS 'FK_AMBIENTE_TECNOLOGICO           '; 
COMMENT ON COLUMN T_DMALM_AMBTECN_CLASF.FK_CLASSIFICATORI IS 'FK_CLASSIFICATORI                 '; 
COMMENT ON COLUMN T_DMALM_AMBTECN_CLASF.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
CREATE UNIQUE INDEX PK_T_DMALM_AMBTECN_CLASF ON T_DMALM_AMBTECN_CLASF 
    ( 
     FK_AMBIENTE_TECNOLOGICO ASC , 
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
ALTER TABLE T_DMALM_AMBTECN_CLASF 
    ADD CONSTRAINT PK_T_DMALM_AMBTECN_CLASF PRIMARY KEY ( FK_AMBIENTE_TECNOLOGICO, FK_CLASSIFICATORI ) 
    USING INDEX PK_T_DMALM_AMBTECN_CLASF ;
;
;
SPOOL OFF;