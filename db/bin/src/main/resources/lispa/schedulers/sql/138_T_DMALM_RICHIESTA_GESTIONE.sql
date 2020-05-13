SPOOL 138_T_DMALM_RICHIESTA_GESTIONE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_RICHIESTA_GESTIONE 
    ( 
     CD_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DT_CREAZIONE_RICHIESTA_GEST DATE , 
     DT_RISOLUZIONE_RICHIESTA_GEST DATE , 
     DT_MODIFICA_RICHIESTA_GEST DATE , 
     DT_CAMBIO_STATO_RICHIESTA_GEST DATE , 
     MOTIVO_RISOLUZIONE_RICH_GEST VARCHAR2 (255 BYTE) , 
     ID_AUTORE_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DS_AUTORE_RICHIESTA_GEST VARCHAR2 (100 BYTE) , 
     DT_SCADENZA_RICHIESTA_GEST DATE , 
     TITOLO_RICHIESTA_GEST VARCHAR2 (4000 BYTE) , 
     DMALM_RICHIESTA_GEST_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     RANK_STATO_RICHIESTA_GEST NUMBER , 
     DT_CARICAMENTO_RICHIESTA_GEST DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     RANK_STATO_RICHIESTA_GEST_MESE NUMBER (5) , 
     DESCRIZIONE_RICHIESTA_GEST CLOB , 
     CATEGORIA VARCHAR2 (200 BYTE) , 
     TICKETID VARCHAR2 (100 BYTE) , 
     DATA_DISPONIBILITA TIMESTAMP , 
     DATA_CHIUSURA TIMESTAMP , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
    ) 
        TABLESPACE "TS_ALM_SV"; 
COMMENT ON TABLE T_DMALM_RICHIESTA_GESTIONE IS 'T_DMALM_RICHIESTA_GESTIONE        ';
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.CD_RICHIESTA_GEST IS 'CD_RICHIESTA_GEST                 '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_CREAZIONE_RICHIESTA_GEST IS 'DT_CREAZIONE_RICHIESTA_GEST       '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_RISOLUZIONE_RICHIESTA_GEST IS 'DT_RISOLUZIONE_RICHIESTA_GEST     '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_MODIFICA_RICHIESTA_GEST IS 'DT_MODIFICA_RICHIESTA_GEST        '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_CAMBIO_STATO_RICHIESTA_GEST IS 'DT_CAMBIO_STATO_RICHIESTA_GEST    '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.MOTIVO_RISOLUZIONE_RICH_GEST IS 'MOTIVO_RISOLUZIONE_RICH_GEST      '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.ID_AUTORE_RICHIESTA_GEST IS 'ID_AUTORE_RICHIESTA_GEST          '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DS_AUTORE_RICHIESTA_GEST IS 'DS_AUTORE_RICHIESTA_GEST          '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_SCADENZA_RICHIESTA_GEST IS 'DT_SCADENZA_RICHIESTA_GEST        '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.TITOLO_RICHIESTA_GEST IS 'TITOLO_RICHIESTA_GEST             '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_RICHIESTA_GEST_PK IS 'DMALM_RICHIESTA_GEST_PK           '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.RANK_STATO_RICHIESTA_GEST IS 'RANK_STATO_RICHIESTA_GEST         '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_CARICAMENTO_RICHIESTA_GEST IS 'DT_CARICAMENTO_RICHIESTA_GEST     '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.RANK_STATO_RICHIESTA_GEST_MESE IS 'RANK_STATO_RICHIESTA_GEST_MESE    '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DESCRIZIONE_RICHIESTA_GEST IS 'DESCRIZIONE_RICHIESTA_GEST        '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.CATEGORIA IS 'CATEGORIA                         '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.TICKETID IS 'TICKETID                          '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DATA_DISPONIBILITA IS 'DATA_DISPONIBILITA                '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DATA_CHIUSURA IS 'DATA_CHIUSURA                     '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN T_DMALM_RICHIESTA_GESTIONE.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX PK_T_DMALM_RICHIESTA_GESTIONE ON T_DMALM_RICHIESTA_GESTIONE 
    ( 
     DMALM_RICHIESTA_GEST_PK ASC 
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
ALTER TABLE T_DMALM_RICHIESTA_GESTIONE 
    ADD CONSTRAINT PK_T_DMALM_RICHIESTA_GESTIONE PRIMARY KEY ( DMALM_RICHIESTA_GEST_PK ) 
    USING INDEX PK_T_DMALM_RICHIESTA_GESTIONE ;
;
;
;
;
SPOOL OFF;
