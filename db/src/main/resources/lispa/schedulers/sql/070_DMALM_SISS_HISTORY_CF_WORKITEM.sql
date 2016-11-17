SPOOL 070_DMALM_SISS_HISTORY_CF_WORKITEM.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SISS_HISTORY_CF_WORKITEM 
    ( 
     C_DATEONLY_VALUE TIMESTAMP , 
     C_FLOAT_VALUE FLOAT (63) , 
     C_STRING_VALUE CLOB , 
     C_DATE_VALUE TIMESTAMP , 
     C_BOOLEAN_VALUE NUMBER (38) , 
     C_LONG_VALUE NUMBER (19) , 
     C_DURATIONTIME_VALUE FLOAT (63) , 
     C_CURRENCY_VALUE NUMBER (20) , 
     SISS_HISTORY_CF_WORKITEM_PK NUMBER  NOT NULL , 
     DATA_CARICAMENTO TIMESTAMP  NOT NULL , 
     FK_URI_WORKITEM VARCHAR2 (4000 BYTE) , 
     FK_WORKITEM VARCHAR2 (4000 BYTE) , 
     C_NAME VARCHAR2 (4000 BYTE) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_SISS_HISTORY_CF_WORKITEM IS 'DMALM_SISS_HISTORY_CF_WORKITEM    ';
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_DATEONLY_VALUE IS 'C_DATEONLY_VALUE                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_FLOAT_VALUE IS 'C_FLOAT_VALUE                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_STRING_VALUE IS 'C_STRING_VALUE                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_DATE_VALUE IS 'C_DATE_VALUE                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_BOOLEAN_VALUE IS 'C_BOOLEAN_VALUE                   '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_LONG_VALUE IS 'C_LONG_VALUE                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_DURATIONTIME_VALUE IS 'C_DURATIONTIME_VALUE              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_CURRENCY_VALUE IS 'C_CURRENCY_VALUE                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.SISS_HISTORY_CF_WORKITEM_PK IS 'SISS_HISTORY_CF_WORKITEM_PK       '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.FK_URI_WORKITEM IS 'FK_URI_WORKITEM                   '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.FK_WORKITEM IS 'FK_WORKITEM                       '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_CF_WORKITEM.C_NAME IS 'C_NAME                            '; 
CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_3 ON DMALM_SISS_HISTORY_CF_WORKITEM 
    ( 
     FK_WORKITEM ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 167 
    STORAGE ( 
        INITIAL 92274688 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE UNIQUE INDEX PK_DMALM_SISS_HISTORY_CF ON DMALM_SISS_HISTORY_CF_WORKITEM 
    ( 
     SISS_HISTORY_CF_WORKITEM_PK ASC 
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
	CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_2 ON DMALM_SISS_HISTORY_CF_WORKITEM 
	(
	FK_URI_WORKITEM
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
	CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_4 ON DMALM_SISS_HISTORY_CF_WORKITEM 
	(
	C_NAME
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
ALTER TABLE DMALM_SISS_HISTORY_CF_WORKITEM 
    ADD CONSTRAINT PK_DMALM_SISS_HISTORY_CF PRIMARY KEY ( SISS_HISTORY_CF_WORKITEM_PK ) 
    USING INDEX PK_DMALM_SISS_HISTORY_CF ;
SPOOL OFF;
