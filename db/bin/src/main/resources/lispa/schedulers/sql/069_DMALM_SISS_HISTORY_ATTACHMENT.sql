SPOOL 069_DMALM_SISS_HISTORY_ATTACHMENT.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SISS_HISTORY_ATTACHMENT 
    ( 
     C_PK VARCHAR2 (4000 BYTE) , 
     C_URI VARCHAR2 (4000 BYTE) , 
     C_REV NUMBER (38) , 
     C_DELETED NUMBER (1) , 
     C_IS_LOCAL NUMBER (1) , 
     FK_AUTHOR VARCHAR2 (4000 BYTE) , 
     FK_URI_AUTHOR VARCHAR2 (4000 BYTE) , 
     C_FILENAME VARCHAR2 (4000 BYTE) , 
     C_ID VARCHAR2 (4000 BYTE) , 
     FK_PROJECT VARCHAR2 (4000 BYTE) , 
     FK_URI_PROJECT VARCHAR2 (4000 BYTE) , 
     C_TITLE VARCHAR2 (4000 BYTE) , 
     C_UPDATED TIMESTAMP , 
     C_URL VARCHAR2 (600 BYTE) , 
     FK_WORKITEM VARCHAR2 (4000 BYTE) , 
     FK_URI_WORKITEM VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     SISS_HISTORY_ATTACHMENT_PK NUMBER (25)  NOT NULL , 
     C_LENGTH NUMBER (20) 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 41943040 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SISS_HISTORY_ATTACHMENT IS 'DMALM_SISS_HISTORY_ATTACHMENT     ';
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_PK IS 'C_PK                              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_URI IS 'C_URI                             '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_REV IS 'C_REV                             '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_DELETED IS 'C_DELETED                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_IS_LOCAL IS 'C_IS_LOCAL                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_AUTHOR IS 'FK_AUTHOR                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_URI_AUTHOR IS 'FK_URI_AUTHOR                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_FILENAME IS 'C_FILENAME                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_ID IS 'C_ID                              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_PROJECT IS 'FK_PROJECT                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_URI_PROJECT IS 'FK_URI_PROJECT                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_TITLE IS 'C_TITLE                           '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_UPDATED IS 'C_UPDATED                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_URL IS 'C_URL                             '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_WORKITEM IS 'FK_WORKITEM                       '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.FK_URI_WORKITEM IS 'FK_URI_WORKITEM                   '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.SISS_HISTORY_ATTACHMENT_PK IS 'SISS_HISTORY_ATTACHMENT_PK        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_ATTACHMENT.C_LENGTH IS 'C_LENGTH                          '; 
CREATE INDEX SISS_ATTACHMENT_IDX_01 ON DMALM_SISS_HISTORY_ATTACHMENT 
    ( 
     FK_WORKITEM ASC 
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
CREATE UNIQUE INDEX SYS_C0011359 ON DMALM_SISS_HISTORY_ATTACHMENT 
    ( 
     SISS_HISTORY_ATTACHMENT_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 720896 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
--  ERROR: PK name length exceeds maximum allowed length(30) 
ALTER TABLE DMALM_SISS_HISTORY_ATTACHMENT 
    ADD CONSTRAINT DMALM_SISS_HISTORY_ATTACH_PK PRIMARY KEY ( SISS_HISTORY_ATTACHMENT_PK ) 
    USING INDEX SYS_C0011359 ;
SPOOL OFF;