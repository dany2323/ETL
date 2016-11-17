SPOOL 060_DMALM_SIRE_HISTORY_HYPERLINK.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SIRE_HISTORY_HYPERLINK 
    ( 
     FK_P_WORKITEM VARCHAR2 (4000 BYTE) , 
     FK_URI_P_WORKITEM VARCHAR2 (4000 BYTE) , 
     C_URI VARCHAR2 (4000 BYTE) , 
     C_ROLE VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     SIRE_HISTORY_HYPERLINK_PK NUMBER (25)  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 142606336 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SIRE_HISTORY_HYPERLINK IS 'DMALM_SIRE_HISTORY_HYPERLINK      ';
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.FK_P_WORKITEM IS 'FK_P_WORKITEM                     '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.FK_URI_P_WORKITEM IS 'FK_URI_P_WORKITEM                 '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.C_URI IS 'C_URI                             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.C_ROLE IS 'C_ROLE                            '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_HYPERLINK.SIRE_HISTORY_HYPERLINK_PK IS 'SIRE_HISTORY_HYPERLINK_PK         '; 
CREATE INDEX SIRE_HYPERLINK_IDX_02 ON DMALM_SIRE_HISTORY_HYPERLINK 
    ( 
     FK_P_WORKITEM ASC 
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
CREATE UNIQUE INDEX SYS_C0011325 ON DMALM_SIRE_HISTORY_HYPERLINK 
    ( 
     SIRE_HISTORY_HYPERLINK_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 6291456 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_SIRE_HISTORY_HYPERLINK 
    ADD CONSTRAINT DMALM_SIRE_HISTORY_HYPER_PK PRIMARY KEY ( SIRE_HISTORY_HYPERLINK_PK ) 
    USING INDEX SYS_C0011325 ;
SPOOL OFF;
