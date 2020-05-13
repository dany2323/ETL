SPOOL 114_T_DMALM_DIFETTO_PRODOTTO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_DIFETTO_PRODOTTO 
    ( 
     DMALM_DIFETTO_PRODOTTO_PK NUMBER (15)  NOT NULL , 
     DMALM_STRUTTURA_ORG_FK_01 NUMBER (15) , 
     DMALM_PROJECT_FK_02 NUMBER (15) , 
     DMALM_STATO_WORKITEM_FK_03 NUMBER (15) , 
     DMALM_TEMPO_FK_04 NUMBER (15) , 
     DMALM_AREA_TEMATICA_FK_05 NUMBER (15) , 
     CD_DIFETTO VARCHAR2 (100 BYTE) , 
     DS_DIFETTO VARCHAR2 (4000 BYTE) , 
     DT_CREAZIONE_DIFETTO DATE , 
     DT_RISOLUZIONE_DIFETTO DATE , 
     DT_CHIUSURA_DIFETTO DATE , 
     DT_CAMBIO_STATO_DIFETTO DATE , 
     DT_MODIFICA_RECORD_DIFETTO DATE , 
     ID_AUTORE_DIFETTO VARCHAR2 (100 BYTE) , 
     DS_AUTORE_DIFETTO VARCHAR2 (100 BYTE) , 
     MOTIVO_RISOLUZIONE_DIFETTO VARCHAR2 (255 BYTE) , 
     SEVERITY VARCHAR2 (10 BYTE) , 
     PROVENIENZA_DIFETTO VARCHAR2 (2 BYTE) , 
     TEMPO_TOT_RISOLUZIONE_DIFETTO NUMBER (8,2) , 
     NR_GIORNI_FESTIVI NUMBER (5) , 
     RANK_STATO_DIFETTO NUMBER (2) , 
     DT_CARICAMENTO_RECORD_DIFETTO DATE , 
     ID_REPOSITORY VARCHAR2 (20 BYTE) , 
     DT_STORICIZZAZIONE DATE , 
     NUMERO_LINEA_DIFETTO VARCHAR2 (3 BYTE) , 
     NUMERO_TESTATA_DIFETTO VARCHAR2 (50 BYTE) , 
     RANK_STATO_DIFETTO_MESE NUMBER (5) , 
     STG_PK VARCHAR2 (4000 BYTE) , 
     ANNULLATO VARCHAR2 (50 BYTE) , 
     CAUSA_DIFETTO VARCHAR2 (100 BYTE) , 
     NATURA_DIFETTO VARCHAR2 (100 BYTE) , 
     DMALM_USER_FK_06 NUMBER (15) 
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
COMMENT ON TABLE T_DMALM_DIFETTO_PRODOTTO IS 'T_DMALM_DIFETTO_PRODOTTO          ';
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_DIFETTO_PRODOTTO_PK IS 'DMALM_DIFETTO_PRODOTTO_PK         '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_STRUTTURA_ORG_FK_01 IS 'DMALM_STRUTTURA_ORG_FK_01         '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_PROJECT_FK_02 IS 'DMALM_PROJECT_FK_02               '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_STATO_WORKITEM_FK_03 IS 'DMALM_STATO_WORKITEM_FK_03        '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_TEMPO_FK_04 IS 'DMALM_TEMPO_FK_04                 '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_AREA_TEMATICA_FK_05 IS 'DMALM_AREA_TEMATICA_FK_05         '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.CD_DIFETTO IS 'CD_DIFETTO                        '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DS_DIFETTO IS 'DS_DIFETTO                        '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_CREAZIONE_DIFETTO IS 'DT_CREAZIONE_DIFETTO              '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_RISOLUZIONE_DIFETTO IS 'DT_RISOLUZIONE_DIFETTO            '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_CHIUSURA_DIFETTO IS 'DT_CHIUSURA_DIFETTO               '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_CAMBIO_STATO_DIFETTO IS 'DT_CAMBIO_STATO_DIFETTO           '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_MODIFICA_RECORD_DIFETTO IS 'DT_MODIFICA_RECORD_DIFETTO        '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.ID_AUTORE_DIFETTO IS 'ID_AUTORE_DIFETTO                 '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DS_AUTORE_DIFETTO IS 'DS_AUTORE_DIFETTO                 '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.MOTIVO_RISOLUZIONE_DIFETTO IS 'MOTIVO_RISOLUZIONE_DIFETTO        '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.SEVERITY IS 'SEVERITY                          '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.PROVENIENZA_DIFETTO IS 'PROVENIENZA_DIFETTO               '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.TEMPO_TOT_RISOLUZIONE_DIFETTO IS 'TEMPO_TOT_RISOLUZIONE_DIFETTO     '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.NR_GIORNI_FESTIVI IS 'NR_GIORNI_FESTIVI                 '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.RANK_STATO_DIFETTO IS 'RANK_STATO_DIFETTO                '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_CARICAMENTO_RECORD_DIFETTO IS 'DT_CARICAMENTO_RECORD_DIFETTO     '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.ID_REPOSITORY IS 'ID_REPOSITORY                     '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DT_STORICIZZAZIONE IS 'DT_STORICIZZAZIONE                '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.NUMERO_LINEA_DIFETTO IS 'NUMERO_LINEA_DIFETTO              '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.NUMERO_TESTATA_DIFETTO IS 'NUMERO_TESTATA_DIFETTO            '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.RANK_STATO_DIFETTO_MESE IS 'RANK_STATO_DIFETTO_MESE           '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.STG_PK IS 'STG_PK                            '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.ANNULLATO IS 'ANNULLATO                         '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.CAUSA_DIFETTO IS 'CAUSA_DIFETTO                     '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.NATURA_DIFETTO IS 'NATURA_DIFETTO                    '; 
COMMENT ON COLUMN T_DMALM_DIFETTO_PRODOTTO.DMALM_USER_FK_06 IS 'DMALM_USER_FK_06                  '; 
CREATE UNIQUE INDEX PK_T_DMALM_DIFETTO_PRODOTTO ON T_DMALM_DIFETTO_PRODOTTO 
    ( 
     DMALM_DIFETTO_PRODOTTO_PK ASC 
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
ALTER TABLE T_DMALM_DIFETTO_PRODOTTO 
    ADD CONSTRAINT PK_T_DMALM_DIFETTO_PRODOTTO PRIMARY KEY ( DMALM_DIFETTO_PRODOTTO_PK ) 
    USING INDEX PK_T_DMALM_DIFETTO_PRODOTTO ;
;
;
;
;
SPOOL OFF;
