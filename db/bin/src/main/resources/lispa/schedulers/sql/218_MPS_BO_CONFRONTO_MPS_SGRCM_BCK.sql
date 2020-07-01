SPOOL 218_MPS_BO_CONFRONTO_MPS_SGRCM_BCK.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE MPS_BO_CONFRONTO_MPS_SGRCM_BCK
(
  FK_WI_PADRE                NUMBER(15),
  CODICE_WI_PADRE            VARCHAR2(100 BYTE),
  TIPO_WI_PADRE              VARCHAR2(50 BYTE),
  CODICE_INCARICO_WI_PADRE   VARCHAR2(200 BYTE),
  FK_WI_FIGLIO               NUMBER(15),
  CODICE_WI_FIGLIO           VARCHAR2(100 BYTE),
  TIPO_WI_FIGLIO             VARCHAR2(50 BYTE),
  CODICE_RILASCIO_WI_FIGLIO  VARCHAR2(200 BYTE),
  FK_WI_DOCUMENTO            NUMBER(15),
  CODICE_WI_DOCUMENTO        VARCHAR2(100 BYTE),
  TIPO_WI_DOCUMENTO          VARCHAR2(50 BYTE),
  IDCONTRATTO_MPS            NUMBER(10),
  CODCONTRATTO_MPS           VARCHAR2(40 BYTE),
  IDRILASCIO                 NUMBER(10),
  CODRILASCIO                VARCHAR2(40 BYTE),
  CODVERBALE                 VARCHAR2(40 BYTE),
  IDVERBALEVALIDAZIONE       NUMBER(10),
  IDATTIVITA                 NUMBER(10),
  CODATTIVITA                VARCHAR2(32 BYTE),
  REPOSITORY_SGRCM           VARCHAR2(40 BYTE),
  REPOSITORY_MPS             VARCHAR2(40 BYTE)
)
 PCTFREE 10 
 PCTUSED 40 
 MAXTRANS 255 
 TABLESPACE TS_ALM 
 LOGGING 
 STORAGE ( 
   INITIAL 655360 
   NEXT 1048576 
   PCTINCREASE 0 
   MINEXTENTS 1 
   MAXEXTENTS 2147483645 
   FREELISTS 1 
   FREELIST GROUPS 1 
   BUFFER_POOL DEFAULT 
 ); 
COMMENT ON TABLE MPS_BO_CONFRONTO_MPS_SGRCM_BCK IS 'MPS_BO_CONFRONTO_MPS_SGRCM_BCK';
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.FK_WI_PADRE               IS 'FK_WI_PADRE              ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODICE_WI_PADRE           IS 'CODICE_WI_PADRE          ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.TIPO_WI_PADRE             IS 'TIPO_WI_PADRE            ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODICE_INCARICO_WI_PADRE  IS 'CODICE_INCARICO_WI_PADRE ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.FK_WI_FIGLIO              IS 'FK_WI_FIGLIO             ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODICE_WI_FIGLIO          IS 'CODICE_WI_FIGLIO         ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.TIPO_WI_FIGLIO            IS 'TIPO_WI_FIGLIO           ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODICE_RILASCIO_WI_FIGLIO IS 'CODICE_RILASCIO_WI_FIGLIO' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.FK_WI_DOCUMENTO           IS 'FK_WI_DOCUMENTO          ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODICE_WI_DOCUMENTO       IS 'CODICE_WI_DOCUMENTO      ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.TIPO_WI_DOCUMENTO         IS 'TIPO_WI_DOCUMENTO        ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.IDCONTRATTO_MPS           IS 'IDCONTRATTO_MPS          ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODCONTRATTO_MPS          IS 'CODCONTRATTO_MPS         ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.IDRILASCIO                IS 'IDRILASCIO               ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODRILASCIO               IS 'CODRILASCIO              ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODVERBALE                IS 'CODVERBALE               ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.IDVERBALEVALIDAZIONE      IS 'IDVERBALEVALIDAZIONE     ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.IDATTIVITA                IS 'IDATTIVITA               ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.CODATTIVITA               IS 'CODATTIVITA              ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.REPOSITORY_SGRCM          IS 'REPOSITORY_SGRCM         ' ;
COMMENT ON COLUMN MPS_BO_CONFRONTO_MPS_SGRCM_BCK.REPOSITORY_MPS            IS 'REPOSITORY_MPS           ' ;
CREATE UNIQUE INDEX MPS_BO_CONF_MPS_SGRCM_BCK_PK ON MPS_BO_CONFRONTO_MPS_SGRCM_BCK 
    ( 
     FK_WI_DOCUMENTO,     
     FK_WI_FIGLIO,
     FK_WI_PADRE,     
     IDATTIVITA,        
     IDCONTRATTO_MPS,          
     IDRILASCIO,    
     IDVERBALEVALIDAZIONE  
    ) 
    TABLESPACE TS_ALM 
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
ALTER TABLE MPS_BO_CONFRONTO_MPS_SGRCM_BCK 
    ADD CONSTRAINT MPS_BO_CONF_MPS_SGRCM_BCK_PK PRIMARY KEY ( FK_WI_DOCUMENTO, FK_WI_FIGLIO, FK_WI_PADRE, IDATTIVITA, IDCONTRATTO_MPS, IDRILASCIO, IDVERBALEVALIDAZIONE ) 
    USING INDEX MPS_BO_CONF_MPS_SGRCM_BCK_PK;
SPOOL OFF;