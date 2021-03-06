SPOOL 044_DMALM_PROJECT_ROLES.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_PROJECT_ROLES 
    ( 
     PROJECT_ROLES_PK NUMBER  NOT NULL , 
     RUOLO VARCHAR2 (50 BYTE) , 
     DESCRIZIONE VARCHAR2 (50 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     REPOSITORY VARCHAR2 (20 BYTE) 
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
COMMENT ON TABLE DMALM_PROJECT_ROLES IS 'DMALM_PROJECT_ROLES               ';
COMMENT ON COLUMN DMALM_PROJECT_ROLES.PROJECT_ROLES_PK IS 'PROJECT_ROLES_PK                  '; 
COMMENT ON COLUMN DMALM_PROJECT_ROLES.RUOLO IS 'RUOLO                             '; 
COMMENT ON COLUMN DMALM_PROJECT_ROLES.DESCRIZIONE IS 'DESCRIZIONE                       '; 
COMMENT ON COLUMN DMALM_PROJECT_ROLES.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_PROJECT_ROLES.REPOSITORY IS 'REPOSITORY                        '; 
CREATE UNIQUE INDEX PROJECT_ROLES_PK ON DMALM_PROJECT_ROLES 
    ( 
     PROJECT_ROLES_PK ASC 
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
CREATE INDEX DMALM_PROJECT_ROLES_IDX_1 ON DMALM_PROJECT_ROLES 
    ( 
     DATA_CARICAMENTO ASC 
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
ALTER TABLE DMALM_PROJECT_ROLES 
    ADD CONSTRAINT PROJECT_ROLES_PK PRIMARY KEY ( PROJECT_ROLES_PK ) 
    USING INDEX PROJECT_ROLES_PK ;
;
;
SPOOL OFF;
