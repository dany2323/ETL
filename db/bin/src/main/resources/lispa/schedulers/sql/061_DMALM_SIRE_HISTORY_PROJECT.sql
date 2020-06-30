SPOOL 061_DMALM_SIRE_HISTORY_PROJECT.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SIRE_HISTORY_PROJECT 
    ( 
     C_TRACKERPREFIX VARCHAR2 (4000 BYTE) , 
     C_IS_LOCAL NUMBER (1) , 
     C_PK VARCHAR2 (4000 BYTE) , 
     FK_URI_LEAD VARCHAR2 (4000 BYTE) , 
     C_DELETED NUMBER (1) , 
     C_FINISH TIMESTAMP , 
     C_URI VARCHAR2 (4000 BYTE) , 
     C_START TIMESTAMP , 
     FK_URI_PROJECTGROUP VARCHAR2 (4000 BYTE) , 
     C_ACTIVE NUMBER (1) , 
     C_LOCATION VARCHAR2 (4000 BYTE) , 
     FK_PROJECTGROUP VARCHAR2 (4000 BYTE) , 
     FK_LEAD VARCHAR2 (4000 BYTE) , 
     C_LOCKWORKRECORDSDATE VARCHAR2 (100 BYTE) , 
     C_NAME VARCHAR2 (4000 BYTE) , 
     C_ID VARCHAR2 (4000 BYTE) , 
     C_REV NUMBER (38) , 
     DATA_CARICAMENTO TIMESTAMP , 
     SIRE_HISTORY_PROJECT_PK NUMBER  NOT NULL , 
     C_CREATED TIMESTAMP , 
     TEMPLATE VARCHAR2 (20 BYTE) 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 2097152 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SIRE_HISTORY_PROJECT IS 'DMALM_SIRE_HISTORY_PROJECT        ';
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_TRACKERPREFIX IS 'C_TRACKERPREFIX                   '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_IS_LOCAL IS 'C_IS_LOCAL                        '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_PK IS 'C_PK                              '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.FK_URI_LEAD IS 'FK_URI_LEAD                       '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_DELETED IS 'C_DELETED                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_FINISH IS 'C_FINISH                          '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_URI IS 'C_URI                             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_START IS 'C_START                           '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.FK_URI_PROJECTGROUP IS 'FK_URI_PROJECTGROUP               '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_ACTIVE IS 'C_ACTIVE                          '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_LOCATION IS 'C_LOCATION                        '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.FK_PROJECTGROUP IS 'FK_PROJECTGROUP                   '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.FK_LEAD IS 'FK_LEAD                           '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_LOCKWORKRECORDSDATE IS 'C_LOCKWORKRECORDSDATE             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_NAME IS 'C_NAME                            '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_ID IS 'C_ID                              '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_REV IS 'C_REV                             '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.SIRE_HISTORY_PROJECT_PK IS 'SIRE_HISTORY_PROJECT_PK           '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.C_CREATED IS 'C_CREATED                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_PROJECT.TEMPLATE IS 'TEMPLATE                          '; 
CREATE UNIQUE INDEX DMALM_SIRE_HISTORY_PROJEC_PK ON DMALM_SIRE_HISTORY_PROJECT 
    ( 
     SIRE_HISTORY_PROJECT_PK ASC 
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
CREATE INDEX SIRE_HISTORY_PROJECT_IDX_3 ON DMALM_SIRE_HISTORY_PROJECT 
    ( 
     C_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
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
CREATE INDEX SIRE_HISTORY_PROJECT_IDX_2 ON DMALM_SIRE_HISTORY_PROJECT 
    ( 
     DATA_CARICAMENTO ASC 
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
CREATE INDEX SIRE_HISTORY_PROJECT_IDX_1 ON DMALM_SIRE_HISTORY_PROJECT 
    ( 
     C_NAME ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
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
ALTER TABLE DMALM_SIRE_HISTORY_PROJECT 
    ADD CONSTRAINT DMALM_SIRE_HISTORY_PROJECT_PK PRIMARY KEY ( SIRE_HISTORY_PROJECT_PK ) 
    USING INDEX DMALM_SIRE_HISTORY_PROJEC_PK ;
SPOOL OFF;
