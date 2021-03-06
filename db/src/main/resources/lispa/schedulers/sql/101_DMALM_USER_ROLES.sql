SPOOL 101_DMALM_USER_ROLES.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_USER_ROLES 
    ( 
     USER_ROLES_PK NUMBER  NOT NULL , 
     ID_PROJECT VARCHAR2 (100 BYTE)  NOT NULL , 
     UTENTE VARCHAR2 (50 BYTE)  NOT NULL , 
     RUOLO VARCHAR2 (50 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     REPOSITORY VARCHAR2 (20 BYTE) , 
     DATA_MODIFICA TIMESTAMP , 
     REVISION NUMBER (10) 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 35651584 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_USER_ROLES IS 'DMALM_USER_ROLES                  ';
COMMENT ON COLUMN DMALM_USER_ROLES.USER_ROLES_PK IS 'USER_ROLES_PK                     '; 
COMMENT ON COLUMN DMALM_USER_ROLES.ID_PROJECT IS 'ID_PROJECT                        '; 
COMMENT ON COLUMN DMALM_USER_ROLES.UTENTE IS 'UTENTE                            '; 
COMMENT ON COLUMN DMALM_USER_ROLES.RUOLO IS 'RUOLO                             '; 
COMMENT ON COLUMN DMALM_USER_ROLES.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_USER_ROLES.REPOSITORY IS 'REPOSITORY                        '; 
COMMENT ON COLUMN DMALM_USER_ROLES.DATA_MODIFICA IS 'DATA_MODIFICA                     '; 
COMMENT ON COLUMN DMALM_USER_ROLES.REVISION IS 'REVISION                          '; 
CREATE UNIQUE INDEX USER_ROLES_PK ON DMALM_USER_ROLES 
    ( 
     USER_ROLES_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 3145728 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX DMALM_USER_ROLES_IDX_1 ON DMALM_USER_ROLES 
    ( 
     DATA_CARICAMENTO ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 5242880 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX DMALM_USER_ROLES_IDX_3 ON DMALM_USER_ROLES 
    ( 
     RUOLO ASC 
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
ALTER TABLE DMALM_USER_ROLES 
    ADD CONSTRAINT USER_ROLES_PK PRIMARY KEY ( USER_ROLES_PK ) 
    USING INDEX USER_ROLES_PK ;
SPOOL OFF;
