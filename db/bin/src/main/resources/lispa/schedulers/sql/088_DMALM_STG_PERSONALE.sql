SPOOL 088_DMALM_STG_PERSONALE.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE DMALM_STG_PERSONALE 
    ( 
     ID VARCHAR2 (50 BYTE)  NOT NULL , 
     CODICE VARCHAR2 (32 BYTE) , 
     DATA_INIZIO_VALIDITA VARCHAR2 (22 BYTE) , 
     DATA_FINE_VALIDITA VARCHAR2 (22 BYTE) , 
     DATA_ATTIVAZIONE VARCHAR2 (22 BYTE) , 
     DATA_DISATTIVAZIONE VARCHAR2 (22 BYTE) , 
     NOTE VARCHAR2 (200 BYTE) , 
     INTERNO VARCHAR2 (32 BYTE) , 
     COD_RESPONSABILE VARCHAR2 (32 BYTE) , 
     INDIRIZZO_EMAIL VARCHAR2 (50 BYTE) , 
     NOME VARCHAR2 (32 BYTE) , 
     COGNOME VARCHAR2 (32 BYTE) , 
     MATRICOLA VARCHAR2 (32 BYTE) , 
     CODICE_FISCALE VARCHAR2 (32 BYTE) , 
     IDENTFICATORE VARCHAR2 (32 BYTE) , 
     ID_GRADO_UFFICIO VARCHAR2 (32 BYTE) , 
     ID_SEDE VARCHAR2 (32 BYTE) , 
     COD_SUPERIORE VARCHAR2 (50 BYTE) , 
     COD_ENTE VARCHAR2 (50 BYTE) , 
     COD_VISIBILITA VARCHAR2 (50 BYTE) , 
     TIPOPERSONA VARCHAR2 (32 BYTE) , 
     DATA_CARICAMENTO TIMESTAMP  NOT NULL , 
     DMALM_STG_PERSONALE_PK NUMBER  NOT NULL 
    ) 
        PCTFREE 10 
        PCTUSED 40 
        MAXTRANS 255 
        TABLESPACE "TS_ALM_SV" 
        LOGGING; 
        STORAGE ( 
        INITIAL 131072 
        NEXT 1048576 
        PCTINCREASE 0 
        MINEXTENTS 1 
        MAXEXTENTS 2147483645 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
    ) 
COMMENT ON TABLE DMALM_STG_PERSONALE IS 'DMALM_STG_PERSONALE               ';
COMMENT ON COLUMN DMALM_STG_PERSONALE.ID IS 'ID                                '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.CODICE IS 'CODICE                            '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DATA_INIZIO_VALIDITA IS 'DATA_INIZIO_VALIDITA              '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DATA_FINE_VALIDITA IS 'DATA_FINE_VALIDITA                '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DATA_ATTIVAZIONE IS 'DATA_ATTIVAZIONE                  '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DATA_DISATTIVAZIONE IS 'DATA_DISATTIVAZIONE               '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.NOTE IS 'NOTE                              '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.INTERNO IS 'INTERNO                           '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.COD_RESPONSABILE IS 'COD_RESPONSABILE                  '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.INDIRIZZO_EMAIL IS 'INDIRIZZO_EMAIL                   '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.NOME IS 'NOME                              '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.COGNOME IS 'COGNOME                           '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.MATRICOLA IS 'MATRICOLA                         '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.CODICE_FISCALE IS 'CODICE_FISCALE                    '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.IDENTFICATORE IS 'IDENTFICATORE                     '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.ID_GRADO_UFFICIO IS 'ID_GRADO_UFFICIO                  '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.ID_SEDE IS 'ID_SEDE                           '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.COD_SUPERIORE IS 'COD_SUPERIORE                     '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.COD_ENTE IS 'COD_ENTE                          '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.COD_VISIBILITA IS 'COD_VISIBILITA                    '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.TIPOPERSONA IS 'TIPOPERSONA                       '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DATA_CARICAMENTO IS 'DATA_CARICAMENTO                  '; 
COMMENT ON COLUMN DMALM_STG_PERSONALE.DMALM_STG_PERSONALE_PK IS 'DMALM_STG_PERSONALE_PK            '; 
CREATE UNIQUE INDEX DMALM_STG_PERSONALE_PK ON DMALM_STG_PERSONALE 
    ( 
     DMALM_STG_PERSONALE_PK ASC 
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
ALTER TABLE DMALM_STG_PERSONALE 
    ADD CONSTRAINT DMALM_STG_PERSONALE_PK PRIMARY KEY ( DMALM_STG_PERSONALE_PK ) 
    USING INDEX DMALM_STG_PERSONALE_PK ;
SPOOL OFF;
