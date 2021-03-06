SPOOL 067_DMALM_SIRE_HISTORY_WORK_LINKED.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_SIRE_HISTORY_WORK_LINKED 
    ( 
     C_SUSPECT NUMBER (1) , 
     C_ROLE VARCHAR2 (4000 BYTE) , 
     FK_P_WORKITEM VARCHAR2 (4000 BYTE) , 
     C_REVISION VARCHAR2 (4000 BYTE) , 
     FK_URI_P_WORKITEM VARCHAR2 (4000 BYTE) , 
     FK_URI_WORKITEM VARCHAR2 (4000 BYTE) , 
     FK_WORKITEM VARCHAR2 (4000 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP , 
     SIRE_HISTORY_WORK_LINKED_PK NUMBER  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 75497472 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_SIRE_HISTORY_WORK_LINKED IS 'DMALM_SIRE_HISTORY_WORK_LINKED    ';
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.C_SUSPECT IS 'C_SUSPECT                         '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.C_ROLE IS 'C_ROLE                            '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.FK_P_WORKITEM IS 'FK_P_WORKITEM                     '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.C_REVISION IS 'C_REVISION                        '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.FK_URI_P_WORKITEM IS 'FK_URI_P_WORKITEM                 '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.FK_URI_WORKITEM IS 'FK_URI_WORKITEM                   '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.FK_WORKITEM IS 'FK_WORKITEM                       '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_SIRE_HISTORY_WORK_LINKED.SIRE_HISTORY_WORK_LINKED_PK IS 'SIRE_HISTORY_WORK_LINKED_PK       '; 
CREATE INDEX SIRE_LINKED_IDX_02 ON DMALM_SIRE_HISTORY_WORK_LINKED 
    ( 
     FK_P_WORKITEM ASC 
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
CREATE UNIQUE INDEX DMALM_SIRE_HISTORY_WORK_L_PK ON DMALM_SIRE_HISTORY_WORK_LINKED 
    ( 
     SIRE_HISTORY_WORK_LINKED_PK ASC 
    ) 
    TABLESPACE "TS_ALM_SV" 
    PCTFREE 10 
    MAXTRANS 255 
    STORAGE ( 
        INITIAL 4194304 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT ) 
    LOGGING; 
CREATE INDEX SIRE_LINKED_IDX_01 ON DMALM_SIRE_HISTORY_WORK_LINKED 
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
--  ERROR: PK name length exceeds maximum allowed length(30) 
ALTER TABLE DMALM_SIRE_HISTORY_WORK_LINKED 
    ADD CONSTRAINT DMALM_SIRE_HIST_WORK_LINK_PK PRIMARY KEY ( SIRE_HISTORY_WORK_LINKED_PK ) 
    USING INDEX DMALM_SIRE_HISTORY_WORK_L_PK ;
SPOOL OFF;
