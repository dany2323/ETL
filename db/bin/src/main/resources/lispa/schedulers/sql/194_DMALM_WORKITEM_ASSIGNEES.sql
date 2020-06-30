SPOOL 194_DMALM_WORKITEM_ASSIGNEES.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_WORKITEM_ASSIGNEES 
    ( 
     DMALM_WORKITEM_FK NUMBER (15) , 
     DMALM_USER_FK NUMBER (15) , 
     CD_WORKITEM VARCHAR2 (200 BYTE) , 
     ID_USER VARCHAR2 (200 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     DMALM_WORKITEM_ASSIGNEE_PK NUMBER (38) 
     CONSTRAINT DMALM_WIASS_PK_CONSTRAINT NOT NULL , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     WORKITEM_TYPE VARCHAR2 (40 BYTE) 
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
    ); 
COMMENT ON TABLE DMALM_WORKITEM_ASSIGNEES IS 'DMALM_WORKITEM_ASSIGNEES';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.DMALM_WORKITEM_FK IS 			'DMALM_WORKITEM_FK ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.DMALM_USER_FK IS                 'DMALM_USER_FK  ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.CD_WORKITEM IS                   'CD_WORKITEM ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.ID_USER IS                       'ID_USER  ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.DATA_CARICAMENTO  IS             'DATA_CARICAMENTO  ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.DMALM_WORKITEM_ASSIGNEE_PK IS    'DMALM_WORKITEM_ASSIGNEE_PK ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.ID_REPOSITORY  IS                'ID_REPOSITORY  ';
COMMENT ON COLUMN DMALM_WORKITEM_ASSIGNEES.WORKITEM_TYPE  IS                'WORKITEM_TYPE  ';	
CREATE INDEX DMALM_WORKITEM_ASSIGNEES_IDX_1 ON DMALM_WORKITEM_ASSIGNEES 
    ( 
     DMALM_WORKITEM_FK ASC , 
     WORKITEM_TYPE ASC 
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
    NOLOGGING; 
;
CREATE UNIQUE INDEX PK_DMALM_WORKITEM_ASSIGNEE ON DMALM_WORKITEM_ASSIGNEES 
    ( 
     DMALM_WORKITEM_ASSIGNEE_PK ASC 
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
;

ALTER TABLE DMALM_WORKITEM_ASSIGNEES 
    ADD CONSTRAINT PK_DMALM_WORKITEM_ASSIGNEE PRIMARY KEY ( DMALM_WORKITEM_ASSIGNEE_PK ) 
    USING INDEX PK_DMALM_WORKITEM_ASSIGNEE ;

SPOOL OFF;