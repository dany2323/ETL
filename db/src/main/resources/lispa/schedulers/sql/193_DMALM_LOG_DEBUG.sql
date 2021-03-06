SPOOL 193_DMALM_LOG_DEBUG.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_LOG_DEBUG 
    ( 
     OBJECT_TYPE VARCHAR2 (40 BYTE) , 
     LOG_DEBUG VARCHAR2 (4000 BYTE)  NOT NULL , 
     ACTION VARCHAR2 (500 BYTE)  NOT NULL , 
     ID NUMBER (15) , 
     RELATED_OBJECT_NAME VARCHAR2 (200 BYTE) , 
     ID_RELATED_OBJECT NUMBER (15) , 
     DATA_CARICAMENTO TIMESTAMP  NOT NULL 
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
COMMENT ON TABLE DMALM_LOG_DEBUG IS 'DMALM_LOG_DEBUG';
COMMENT ON COLUMN DMALM_LOG_DEBUG.OBJECT_TYPE IS 'OBJECT_TYPE';
COMMENT ON COLUMN DMALM_LOG_DEBUG.LOG_DEBUG	IS 'LOG_DEBUG';
COMMENT ON COLUMN DMALM_LOG_DEBUG.ACTION IS 'ACTION';
COMMENT ON COLUMN DMALM_LOG_DEBUG.ID IS 'ID';
COMMENT ON COLUMN DMALM_LOG_DEBUG.RELATED_OBJECT_NAME	IS 'RELATED_OBJECT_NAME';
COMMENT ON COLUMN DMALM_LOG_DEBUG.ID_RELATED_OBJECT	IS 'ID_RELATED_OBJECT';
COMMENT ON COLUMN DMALM_LOG_DEBUG.DATA_CARICAMENTO	IS 'DATA_CARICAMENTO';
CREATE UNIQUE INDEX PK_DMALM_LOG_DEBUG ON DMALM_LOG_DEBUG 
    ( 
     DATA_CARICAMENTO ASC , 
     ACTION ASC , 
     LOG_DEBUG ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 146 
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

ALTER TABLE DMALM_LOG_DEBUG 
    ADD CONSTRAINT PK_DMALM_LOG_DEBUG PRIMARY KEY ( DATA_CARICAMENTO, ACTION, LOG_DEBUG ) 
    USING INDEX PK_DMALM_LOG_DEBUG ;

SPOOL OFF;












