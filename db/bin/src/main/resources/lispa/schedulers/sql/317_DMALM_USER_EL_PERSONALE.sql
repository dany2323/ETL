SPOOL 317_DMALM_USER_EL_PERSONALE.sql

CREATE TABLE DMALM_EL_PERSONALE 
   (	DMALM_PERSONALE_PK NUMBER(15,0) NOT NULL ENABLE, 
	ID_EDMA VARCHAR2(100 BYTE), 
	CD_PERSONALE VARCHAR2(40 BYTE), 
	DT_INIZIO_VALIDITA_EDMA DATE, 
	DT_FINE_VALIDITA_EDMA DATE, 
	DT_ATTIVAZIONE DATE, 
	DT_DISATTIVAZIONE DATE, 
	NOTE VARCHAR2(500 BYTE), 
	INTERNO NUMBER(1,0), 
	CD_RESPONSABILE VARCHAR2(40 BYTE), 
	INDIRIZZO_EMAIL VARCHAR2(80 BYTE), 
	NOME VARCHAR2(100 BYTE), 
	COGNOME VARCHAR2(50 BYTE), 
	MATRICOLA VARCHAR2(32 BYTE), 
	CODICE_FISCALE VARCHAR2(32 BYTE), 
	IDENTIFICATORE VARCHAR2(40 BYTE), 
	ID_GRADO NUMBER(8,0), 
	ID_SEDE NUMBER(8,0), 
	CD_SUPERIORE VARCHAR2(40 BYTE), 
	CD_ENTE VARCHAR2(40 BYTE), 
	CD_VISIBILITA VARCHAR2(40 BYTE), 
	DT_CARICAMENTO DATE, 
	DT_INIZIO_VALIDITA DATE, 
	DT_FINE_VALIDITA DATE, 
	ANNULLATO VARCHAR2(100 BYTE), 
	DT_ANNULLAMENTO DATE, 
	DMALM_UNITAORGANIZZATIVA_FK_01 NUMBER(15,0), 
	DMALM_UNITAORGANIZZ_FLAT_FK_02 NUMBER(15,0), 
	 CONSTRAINT DMALM_EL_PERSONALE_PK PRIMARY KEY (DMALM_PERSONALE_PK)
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_ALM" ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TS_ALM" ;

   COMMENT ON COLUMN DMALM_EL_PERSONALE.DMALM_PERSONALE_PK IS 'DMALM_PERSONALE_PK     ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.ID_EDMA IS 'ID_EDMA                ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CD_PERSONALE IS 'CD_PERSONALE           ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_INIZIO_VALIDITA_EDMA IS 'DT_INIZIO_VALIDITA_EDMA';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_FINE_VALIDITA_EDMA IS 'DT_FINE_VALIDITA_EDMA  ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_ATTIVAZIONE IS 'DT_ATTIVAZIONE         ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_DISATTIVAZIONE IS 'DT_DISATTIVAZIONE      ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.NOTE IS 'NOTE                   ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.INTERNO IS 'INTERNO                ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CD_RESPONSABILE IS 'CD_RESPONSABILE        ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.INDIRIZZO_EMAIL IS 'INDIRIZZO_EMAIL        ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.NOME IS 'NOME                   ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.COGNOME IS 'COGNOME                ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.MATRICOLA IS 'MATRICOLA              ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CODICE_FISCALE IS 'CODICE_FISCALE         ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.IDENTIFICATORE IS 'IDENTIFICATORE         ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.ID_GRADO IS 'ID_GRADO               ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.ID_SEDE IS 'ID_SEDE                ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CD_SUPERIORE IS 'CD_SUPERIORE           ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CD_ENTE IS 'CD_ENTE                ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.CD_VISIBILITA IS 'CD_VISIBILITA          ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_CARICAMENTO IS 'DT_CARICAMENTO         ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_INIZIO_VALIDITA IS 'DT_INIZIO_VALIDITA     ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_FINE_VALIDITA IS 'DT_FINE_VALIDITA       ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.ANNULLATO IS 'ANNULLATO              ';
   COMMENT ON COLUMN DMALM_EL_PERSONALE.DT_ANNULLAMENTO IS 'DT_ANNULLAMENTO        ';
   COMMENT ON TABLE DMALM_EL_PERSONALE  IS 'DMALM_EL_PERSONALE';
SPOOL OFF;