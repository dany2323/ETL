SPOOL 227_T_MPS_BO_ATTIVITA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE TABLE T_MPS_BO_ATTIVITA
(
  IDATTIVITAPADRE          NUMBER(10),
  IDATTIVITA               NUMBER(10),
  IDCONTRATTO              NUMBER(10),
  CODATTIVITA              VARCHAR2(32 BYTE),
  TITOLO                   VARCHAR2(255 BYTE),
  DESATTIVITA              VARCHAR2(4000 BYTE),
  DATA_INIZIO              DATE,
  DATA_FINE                DATE,
  AVANZAMENTO              NUMBER,
  DATA_ULTIMO_AVANZAMENTO  DATE,
  TIPO_ATTIVITA            CHAR(1 BYTE),
  STATO                    CHAR(1 BYTE),
  INSERITO_DA              NUMBER(10),
  INSERITO_IL              DATE,
  MODIFICATO_DA            NUMBER(10),
  MODIFICATO_IL            DATE,
  RECORDSTATUS             CHAR(1 BYTE)
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
COMMENT ON TABLE T_MPS_BO_ATTIVITA IS 'T_MPS_BO_ATTIVITA';
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.IDATTIVITAPADRE         IS 'IDATTIVITAPADRE        ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.IDATTIVITA              IS 'IDATTIVITA             ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.IDCONTRATTO             IS 'IDCONTRATTO            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.CODATTIVITA             IS 'CODATTIVITA            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.TITOLO                  IS 'TITOLO                 ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.DESATTIVITA             IS 'DESATTIVITA            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.DATA_INIZIO             IS 'DATA_INIZIO            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.DATA_FINE               IS 'DATA_FINE              ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.AVANZAMENTO             IS 'AVANZAMENTO            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.DATA_ULTIMO_AVANZAMENTO IS 'DATA_ULTIMO_AVANZAMENTO' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.TIPO_ATTIVITA           IS 'TIPO_ATTIVITA          ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.STATO                   IS 'STATO                  ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.INSERITO_DA             IS 'INSERITO_DA            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.INSERITO_IL             IS 'INSERITO_IL            ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.MODIFICATO_DA           IS 'MODIFICATO_DA          ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.MODIFICATO_IL           IS 'MODIFICATO_IL          ' ;
COMMENT ON COLUMN T_MPS_BO_ATTIVITA.RECORDSTATUS            IS 'RECORDSTATUS           ' ;
CREATE UNIQUE INDEX T_MPS_BO_ATTIVITA_PK ON T_MPS_BO_ATTIVITA 
    ( 
     IDATTIVITA ASC 
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
ALTER TABLE T_MPS_BO_ATTIVITA 
    ADD CONSTRAINT T_MPS_BO_ATTIVITA_PK PRIMARY KEY ( IDATTIVITA ) 
    USING INDEX T_MPS_BO_ATTIVITA_PK;
SPOOL OFF;