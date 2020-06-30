SPOOL 146_T_DMALM_UNITA_ORGANIZZATIVA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE TABLE T_DMALM_UNITA_ORGANIZZATIVA 
    ( 
     DMALM_UNITA_ORG_PK NUMBER (15)  NOT NULL , 
     CD_AREA VARCHAR2 (40 BYTE) , 
     DS_AREA_EDMA VARCHAR2 (255 BYTE) , 
     CD_UO_SUPERIORE VARCHAR2 (40 BYTE) , 
     DS_UO_SUPERIORE VARCHAR2 (255 BYTE) , 
     CD_RESPONSABILE_AREA VARCHAR2 (40 BYTE) , 
     DN_RESPONSABILE_AREA VARCHAR2 (255 BYTE) , 
     DT_INIZIO_VALIDITA_EDMA DATE , 
     DT_FINE_VALIDITA_EDMA DATE , 
     DT_CARICAMENTO DATE , 
     DT_ATTIVAZIONE DATE , 
     DT_DISATTIVAZIONE DATE , 
     NOTE VARCHAR2 (500 BYTE) , 
     ID_TIPOLOGIA_UFFICIO NUMBER (8) , 
     ID_GRADO_UFFICIO NUMBER (8) , 
     ID_SEDE NUMBER (8) , 
     CD_VISIBILITA VARCHAR2 (40 BYTE) , 
     CD_ENTE VARCHAR2 (40 BYTE) , 
     ID_EDMA NUMBER (8) , 
     INDIRIZZO_EMAIL VARCHAR2 (80 BYTE) , 
     INTERNO NUMBER (1) , 
     DT_INIZIO_VALIDITA DATE , 
     DT_FINE_VALIDITA DATE , 
     ANNULLATO VARCHAR2 (100 BYTE) 
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
COMMENT ON TABLE T_DMALM_UNITA_ORGANIZZATIVA IS 'T_DMALM_UNITA_ORGANIZZATIVA       ';
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DMALM_UNITA_ORG_PK IS 'DMALM_UNITA_ORG_PK                '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.CD_AREA IS 'CD_AREA                           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DS_AREA_EDMA IS 'DS_AREA_EDMA                      '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.CD_UO_SUPERIORE IS 'CD_UO_SUPERIORE                   '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DS_UO_SUPERIORE IS 'DS_UO_SUPERIORE                   '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.CD_RESPONSABILE_AREA IS 'CD_RESPONSABILE_AREA              '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DN_RESPONSABILE_AREA IS 'DN_RESPONSABILE_AREA              '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_INIZIO_VALIDITA_EDMA IS 'DT_INIZIO_VALIDITA_EDMA           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_FINE_VALIDITA_EDMA IS 'DT_FINE_VALIDITA_EDMA             '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_CARICAMENTO IS 'DT_CARICAMENTO                    '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_ATTIVAZIONE IS 'DT_ATTIVAZIONE                    '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_DISATTIVAZIONE IS 'DT_DISATTIVAZIONE                 '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.NOTE IS 'NOTE                              '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.ID_TIPOLOGIA_UFFICIO IS 'ID_TIPOLOGIA_UFFICIO              '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.ID_GRADO_UFFICIO IS 'ID_GRADO_UFFICIO                  '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.ID_SEDE IS 'ID_SEDE                           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.CD_VISIBILITA IS 'CD_VISIBILITA                     '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.CD_ENTE IS 'CD_ENTE                           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.ID_EDMA IS 'ID_EDMA                           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.INDIRIZZO_EMAIL IS 'INDIRIZZO_EMAIL                   '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.INTERNO IS 'INTERNO                           '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA                '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA                  '; 
COMMENT ON COLUMN T_DMALM_UNITA_ORGANIZZATIVA.ANNULLATO IS 'ANNULLATO                         '; 
CREATE UNIQUE INDEX PK_T_DMALM_UNITA_ORGANIZZATIVA ON T_DMALM_UNITA_ORGANIZZATIVA 
    ( 
     DMALM_UNITA_ORG_PK ASC 
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
ALTER TABLE T_DMALM_UNITA_ORGANIZZATIVA 
    ADD CONSTRAINT PK_T_DMALM_UNITA_ORGANIZZATIVA PRIMARY KEY ( DMALM_UNITA_ORG_PK ) 
    USING INDEX PK_T_DMALM_UNITA_ORGANIZZATIVA ;
SPOOL OFF;
