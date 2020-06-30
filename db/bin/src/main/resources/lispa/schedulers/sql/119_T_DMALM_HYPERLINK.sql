SPOOL 119_T_DMALM_HYPERLINK.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_HYPERLINK 
    ( 
     DMALM_FK_WORKITEM_01 NUMBER (15) , 
     DMALM_WORKITEM_TYPE VARCHAR2 (50 BYTE) , 
     RUOLO VARCHAR2 (50 BYTE) , 
     URI VARCHAR2 (4000 BYTE) , 
     DT_CARICAMENTO TIMESTAMP , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     HYPERLINK_PK NUMBER (15)  NOT NULL 
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
COMMENT ON TABLE T_DMALM_HYPERLINK IS 'T_DMALM_HYPERLINK                 ';
COMMENT ON COLUMN T_DMALM_HYPERLINK.DMALM_FK_WORKITEM_01 IS 'DMALM_FK_WORKITEM_01              '; 
COMMENT ON COLUMN T_DMALM_HYPERLINK.DMALM_WORKITEM_TYPE IS 'DMALM_WORKITEM_TYPE               '; 
COMMENT ON COLUMN T_DMALM_HYPERLINK.RUOLO IS 'RUOLO                             '; 
COMMENT ON COLUMN T_DMALM_HYPERLINK.URI IS 'URI                               '; 
COMMENT ON COLUMN T_DMALM_HYPERLINK.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
COMMENT ON COLUMN T_DMALM_HYPERLINK.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
CREATE UNIQUE INDEX PK_T_DMALM_HYPERLINK ON T_DMALM_HYPERLINK 
    ( 
     HYPERLINK_PK ASC 
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
ALTER TABLE T_DMALM_HYPERLINK 
    ADD CONSTRAINT PK_T_DMALM_HYPERLINK PRIMARY KEY ( HYPERLINK_PK ) 
    USING INDEX PK_T_DMALM_HYPERLINK ;
SPOOL OFF;
