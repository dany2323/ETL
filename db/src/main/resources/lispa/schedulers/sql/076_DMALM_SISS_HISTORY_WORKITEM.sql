SPOOL 076_DMALM_SISS_HISTORY_WORKITEM.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SISS_HISTORY_WORKITEM 
    ( 
     FK_MODULE VARCHAR2 (4000 BYTE) , 
     C_IS_LOCAL NUMBER (38) , 
     C_PRIORITY VARCHAR2 (4000 BYTE) , 
     C_AUTOSUSPECT NUMBER (38) , 
     C_RESOLUTION VARCHAR2 (4000 BYTE) , 
     C_CREATED TIMESTAMP , 
     C_OUTLINENUMBER VARCHAR2 (4000 BYTE) , 
     FK_PROJECT VARCHAR2 (4000 BYTE) , 
     C_DELETED NUMBER (38) , 
     C_PLANNEDEND TIMESTAMP , 
     C_UPDATED TIMESTAMP , 
     FK_AUTHOR VARCHAR2 (4000 BYTE) , 
     FK_URI_MODULE VARCHAR2 (4000 BYTE) , 
     C_TIMESPENT FLOAT (63) , 
     C_SEVERITY VARCHAR2 (4000 BYTE) , 
     C_RESOLVEDON TIMESTAMP , 
     FK_URI_PROJECT VARCHAR2 (4000 BYTE) , 
     C_TITLE VARCHAR2 (4000 BYTE) , 
     C_ID VARCHAR2 (100 BYTE) , 
     C_REV NUMBER (19) , 
     C_PLANNEDSTART TIMESTAMP , 
     FK_URI_AUTHOR VARCHAR2 (4000 BYTE) , 
     C_DUEDATE TIMESTAMP , 
     C_REMAININGESTIMATE FLOAT (63) , 
     C_TYPE VARCHAR2 (100 BYTE) , 
     C_LOCATION VARCHAR2 (4000 BYTE) , 
     FK_TIMEPOINT VARCHAR2 (4000 BYTE) , 
     C_INITIALESTIMATE FLOAT (63) , 
     FK_URI_TIMEPOINT VARCHAR2 (4000 BYTE) , 
     C_PREVIOUSSTATUS VARCHAR2 (4000 BYTE) , 
     SISS_HISTORY_WORKITEM_PK NUMBER  NOT NULL , 
     DATA_CARICAMENTO TIMESTAMP  NOT NULL , 
     C_STATUS VARCHAR2 (100 BYTE) , 
     C_URI VARCHAR2 (4000 BYTE) , 
     C_PK VARCHAR2 (4000 BYTE) , 
     COSTO_SVILUPPO FLOAT (126) , 
     COD_INTERVENTO VARCHAR2 (4000 BYTE) , 
     TICKET_ID VARCHAR2 (300 BYTE) , 
     CA FLOAT (126) , 
     ST_CHIUSO TIMESTAMP , 
     C_DESCRIPTION CLOB , 
     VERSIONE VARCHAR2 (400 BYTE) , 
     DATA_INIZIO TIMESTAMP , 
     DATA_DISP TIMESTAMP , 
     DATA_INIZIO_EFF TIMESTAMP , 
     AOID VARCHAR2 (200 BYTE) , 
     CODICE VARCHAR2 (200 BYTE) , 
     CLASSE_DOC VARCHAR2 (50 BYTE) , 
     TIPO_DOC VARCHAR2 (70 BYTE) , 
     FR NUMBER (1) , 
     FORNITURA VARCHAR2 (80 BYTE) , 
     DATA_DISPONIBILITA_EFFETTIVA TIMESTAMP , 
     DATA_FINE TIMESTAMP , 
     DATA_FINE_EFFETTIVA TIMESTAMP , 
     VERSION VARCHAR2 (300 BYTE) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE DMALM_SISS_HISTORY_WORKITEM IS 'DMALM_SISS_HISTORY_WORKITEM       ';
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_MODULE IS 'FK_MODULE                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_IS_LOCAL IS 'C_IS_LOCAL                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_PRIORITY IS 'C_PRIORITY                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_AUTOSUSPECT IS 'C_AUTOSUSPECT                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_RESOLUTION IS 'C_RESOLUTION                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_CREATED IS 'C_CREATED                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_OUTLINENUMBER IS 'C_OUTLINENUMBER                   '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_PROJECT IS 'FK_PROJECT                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_DELETED IS 'C_DELETED                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_PLANNEDEND IS 'C_PLANNEDEND                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_UPDATED IS 'C_UPDATED                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_AUTHOR IS 'FK_AUTHOR                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_URI_MODULE IS 'FK_URI_MODULE                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_TIMESPENT IS 'C_TIMESPENT                       '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_SEVERITY IS 'C_SEVERITY                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_RESOLVEDON IS 'C_RESOLVEDON                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_URI_PROJECT IS 'FK_URI_PROJECT                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_TITLE IS 'C_TITLE                           '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_ID IS 'C_ID                              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_REV IS 'C_REV                             '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_PLANNEDSTART IS 'C_PLANNEDSTART                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_URI_AUTHOR IS 'FK_URI_AUTHOR                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_DUEDATE IS 'C_DUEDATE                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_REMAININGESTIMATE IS 'C_REMAININGESTIMATE               '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_TYPE IS 'C_TYPE                            '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_LOCATION IS 'C_LOCATION                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_TIMEPOINT IS 'FK_TIMEPOINT                      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_INITIALESTIMATE IS 'C_INITIALESTIMATE                 '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FK_URI_TIMEPOINT IS 'FK_URI_TIMEPOINT                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_PREVIOUSSTATUS IS 'C_PREVIOUSSTATUS                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.SISS_HISTORY_WORKITEM_PK IS 'SISS_HISTORY_WORKITEM_PK          '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_STATUS IS 'C_STATUS                          '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_URI IS 'C_URI                             '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_PK IS 'C_PK                              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.COSTO_SVILUPPO IS 'COSTO_SVILUPPO                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.COD_INTERVENTO IS 'COD_INTERVENTO                    '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.TICKET_ID IS 'TICKET_ID                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.CA IS 'CA                                '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.ST_CHIUSO IS 'ST_CHIUSO                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.C_DESCRIPTION IS 'C_DESCRIPTION                     '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.VERSIONE IS 'VERSIONE                          '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_INIZIO IS 'DATA_INIZIO                       '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_DISP IS 'DATA_DISP                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_INIZIO_EFF IS 'DATA_INIZIO_EFF                   '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.AOID IS 'AOID                              '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.CLASSE_DOC IS 'CLASSE_DOC                        '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.TIPO_DOC IS 'TIPO_DOC                          '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FR IS 'FR                                '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.FORNITURA IS 'FORNITURA                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_DISPONIBILITA_EFFETTIVA IS 'DATA_DISPONIBILITA_EFFETTIVA      '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_FINE IS 'DATA_FINE                         '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.DATA_FINE_EFFETTIVA IS 'DATA_FINE_EFFETTIVA               '; 
COMMENT ON COLUMN DMALM_SISS_HISTORY_WORKITEM.VERSION IS 'VERSION                           '; 
CREATE UNIQUE INDEX SYS_C0011373 ON DMALM_SISS_HISTORY_WORKITEM 
    ( 
     SISS_HISTORY_WORKITEM_PK ASC 
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
CREATE UNIQUE INDEX DMALM_SISS_HISTORY_PK ON DMALM_SISS_HISTORY_WORKITEM 
	(
	C_PK
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
CREATE INDEX SISS_HISTORY_WORKITEM_IDX_1 ON DMALM_SISS_HISTORY_WORKITEM 
	(
	DATA_CARICAMENTO
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
CREATE INDEX SISS_HISTORY_WORKITEM_IDX_3 ON DMALM_SISS_HISTORY_WORKITEM 
	(
	C_URI
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
CREATE INDEX SISS_HISTORY_WORKITEM_IDX_7 ON DMALM_SISS_HISTORY_WORKITEM 
	(
	C_TYPE
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
ALTER TABLE DMALM_SISS_HISTORY_WORKITEM 
    ADD CONSTRAINT DMALM_SISS_HISTORY_WORKITEM_PK PRIMARY KEY ( SISS_HISTORY_WORKITEM_PK ) 
    USING INDEX SYS_C0011373 ;
SPOOL OFF;
