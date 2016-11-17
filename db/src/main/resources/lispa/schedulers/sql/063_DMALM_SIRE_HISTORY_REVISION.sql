SPOOL 063_DMALM_SIRE_HISTORY_REVISION.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_PK VARCHAR2 (4000 BYTE)  NOT NULL , 
     C_URI VARCHAR2 (4000 BYTE) , 
     C_AUTHOR VARCHAR2 (4000 BYTE) , 
     C_CREATED TIMESTAMP , 
     C_MESSAGE VARCHAR2 (4000 BYTE) , 
     C_REPOSITORYNAME VARCHAR2 (4000 BYTE) , 
     C_REV NUMBER (30) , 
     C_DELETED NUMBER (1) , 
     C_INTERNALCOMMIT NUMBER (1) , 
     C_IS_LOCAL NUMBER (1) , 
     C_NAME VARCHAR2 (4000 BYTE) , 
     SIRE_HISTORY_REVISION_PK NUMBER (30)  NOT NULL , 
     DATA_CARICAMENTO TIMESTAMP 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 109051904 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SIRE_HISTORY_REVISION IS 'DMALM_SIRE_HISTORY_REVISION       ';
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_PK IS 'C_PK                              '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_URI IS 'C_URI                             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_AUTHOR IS 'C_AUTHOR                          '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_CREATED IS 'C_CREATED                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_MESSAGE IS 'C_MESSAGE                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_REPOSITORYNAME IS 'C_REPOSITORYNAME                  '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_REV IS 'C_REV                             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_DELETED IS 'C_DELETED                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_INTERNALCOMMIT IS 'C_INTERNALCOMMIT                  '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_IS_LOCAL IS 'C_IS_LOCAL                        '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.C_NAME IS 'C_NAME                            '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.SIRE_HISTORY_REVISION_PK IS 'SIRE_HISTORY_REVISION_PK          '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_REVISION.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
CREATE INDEX IDX_SIRE_REVISION_2 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_REPOSITORYNAME ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 12582912 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_5 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_AUTHOR ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 15728640 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_6 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_MESSAGE ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 42991616 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_9 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_URI ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 53477376 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_10 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_CREATED ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 10485760 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_1 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_REV ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 8388608 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_4 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_INTERNALCOMMIT ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 9437184 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_7 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_IS_LOCAL ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 8388608 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_8 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_DELETED ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 8388608 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX IDX_SIRE_REVISION_3 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     C_NAME ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 14680064 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE UNIQUE INDEX SYS_C0011331 ON DMALM_SIRE_HISTORY_REVISION 
    ( 
     SIRE_HISTORY_REVISION_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 7340032 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
ALTER TABLE DMALM_SIRE_HISTORY_REVISION 
    ADD CONSTRAINT DMALM_SIRE_HISTORY_REVISION_PK PRIMARY KEY ( SIRE_HISTORY_REVISION_PK ) 
    USING INDEX SYS_C0011331 ;
SPOOL OFF;
