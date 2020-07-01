SPOOL 134_T_DMALM_PROJECT_ROLES_SGR.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_PROJECT_ROLES_SGR 
    ( 
     DMALM_PROJECT_ROLES_PK NUMBER (15)  NOT NULL , 
     TIPOLOGIA_PROJECT VARCHAR2 (50 BYTE) , 
     RUOLO VARCHAR2 (50 BYTE) , 
     DT_CARICAMENTO DATE  NOT NULL , 
     DT_INIZIO_VALIDITA DATE  NOT NULL , 
     DT_FINE_VALIDITA DATE  NOT NULL 
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
COMMENT ON TABLE T_DMALM_PROJECT_ROLES_SGR IS 'T_DMALM_PROJECT_ROLES_SGR         ';
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.DMALM_PROJECT_ROLES_PK IS 'DMALM_PROJECT_ROLES_PK            '; 
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.TIPOLOGIA_PROJECT IS 'TIPOLOGIA_PROJECT                 '; 
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.RUOLO IS 'RUOLO                             '; 
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA                '; 
COMMENT ON COLUMN T_DMALM_PROJECT_ROLES_SGR.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA                  '; 
CREATE UNIQUE INDEX PK_T_DMALM_PROJECT_ROLES_SGR ON T_DMALM_PROJECT_ROLES_SGR 
    ( 
     DMALM_PROJECT_ROLES_PK ASC 
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
ALTER TABLE T_DMALM_PROJECT_ROLES_SGR 
    ADD CONSTRAINT PK_T_DMALM_PROJECT_ROLES_SGR PRIMARY KEY ( DMALM_PROJECT_ROLES_PK ) 
    USING INDEX PK_T_DMALM_PROJECT_ROLES_SGR ;
;
;
SPOOL OFF;