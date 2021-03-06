SPOOL 124_T_DMALM_PEI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_PEI 
    ( 
     CD_PEI VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_PEI DATE , 
     DT_RISOLUZIONE_PEI DATE , 
     DT_MODIFICA_PEI DATE , 
     DT_CAMBIO_STATO_PEI DATE , 
     MOTIVO_RISOLUZIONE_PEI VARCHAR2 (255 BYTE) , 
     ID_AUTORE_PEI VARCHAR2 (100 BYTE) , 
     DS_AUTORE_PEI VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_PEI DATE , 
     TITOLO_PEI VARCHAR2 (4000 BYTE) , 
     DMALM_PEI_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_PEI NUMBER , 
     DT_CARICAMENTO_PEI DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_PEI_MESE NUMBER (5) , 
     DESCRIZIONE_PEI CLOB , 
     CODICE VARCHAR2 (200 BYTE) , 
     DT_PREVISTA_COMPL_REQ TIMESTAMP , 
     DT_PREVISTA_PASS_IN_ES TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV";;
COMMENT ON TABLE T_DMALM_PEI IS 'T_DMALM_PEI                       ';
COMMENT ON COLUMN T_DMALM_PEI.CD_PEI IS 'CD_PEI                            '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_CREAZIONE_PEI IS 'DT_CREAZIONE_PEI                  '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_RISOLUZIONE_PEI IS 'DT_RISOLUZIONE_PEI                '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_MODIFICA_PEI IS 'DT_MODIFICA_PEI                   '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_CAMBIO_STATO_PEI IS 'DT_CAMBIO_STATO_PEI               '; 
COMMENT ON COLUMN T_DMALM_PEI.MOTIVO_RISOLUZIONE_PEI IS 'MOTIVO_RISOLUZIONE_PEI            '; 
COMMENT ON COLUMN T_DMALM_PEI.ID_AUTORE_PEI IS 'ID_AUTORE_PEI                     '; 
COMMENT ON COLUMN T_DMALM_PEI.DS_AUTORE_PEI IS 'DS_AUTORE_PEI                     '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_SCADENZA_PEI IS 'DT_SCADENZA_PEI                   '; 
COMMENT ON COLUMN T_DMALM_PEI.TITOLO_PEI IS 'TITOLO_PEI                        '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_PEI_PK IS 'DMALM_PEI_PK                      '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN T_DMALM_PEI.RANK_STATO_PEI IS 'RANK_STATO_PEI                    '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_CARICAMENTO_PEI IS 'DT_CARICAMENTO_PEI                '; 
COMMENT ON COLUMN T_DMALM_PEI.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN T_DMALM_PEI.RANK_STATO_PEI_MESE IS 'RANK_STATO_PEI_MESE               '; 
COMMENT ON COLUMN T_DMALM_PEI.DESCRIZIONE_PEI IS 'DESCRIZIONE_PEI                   '; 
COMMENT ON COLUMN T_DMALM_PEI.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_PREVISTA_COMPL_REQ IS 'DT_PREVISTA_COMPL_REQ             '; 
COMMENT ON COLUMN T_DMALM_PEI.DT_PREVISTA_PASS_IN_ES IS 'DT_PREVISTA_PASS_IN_ES            '; 
COMMENT ON COLUMN T_DMALM_PEI.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN T_DMALM_PEI.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN T_DMALM_PEI.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX PK_T_DMALM_PEI ON T_DMALM_PEI 
    ( 
     DMALM_PEI_PK ASC 
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
ALTER TABLE T_DMALM_PEI 
    ADD CONSTRAINT PK_T_DMALM_PEI PRIMARY KEY ( DMALM_PEI_PK ) 
    USING INDEX PK_T_DMALM_PEI ;
;
;
;
;
SPOOL OFF;
