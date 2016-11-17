SPOOL 203_ALTER_TABLE_CHANGED_WI.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
ALTER TABLE DMALM_ANOMALIA_ASSISTENZA  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_ANOMALIA_PRODOTTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_BUILD  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_DIFETTO_PRODOTTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_DOCUMENTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_FASE  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_MANUTENZIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PEI  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PROGETTO_DEMAND  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PROGETTO_ESE  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PROGETTO_SVILUPPO_DEM  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PROGETTO_SVILUPPO_SVIL  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_PROGRAMMA  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_RELEASE_DI_PROGETTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_RELEASE_IT  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_RELEASE_SERVIZI  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_RICHIESTA_GESTIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_RICHIESTA_MANUTENZIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_SOTTOPROGRAMMA  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_TASK  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_TASK_IT  ADD CHANGED VARCHAR2(100);
ALTER TABLE DMALM_TESTCASE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_ANOMALIA_ASSISTENZA  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_ANOMALIA_PRODOTTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_BUILD  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_DIFETTO_PRODOTTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_DOCUMENTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_FASE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_MANUTENZIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PEI  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PROGETTO_DEMAND  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PROGETTO_ESE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PROGETTO_SVILUPPO_DEM  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PROGETTO_SVILUPPO_SVIL  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_PROGRAMMA  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_RELEASE_DI_PROGETTO  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_RELEASE_IT  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_RELEASE_SERVIZI  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_RICHIESTA_GESTIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_RICHIESTA_MANUTENZIONE  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_SOTTOPROGRAMMA  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_TASK  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_TASK_IT  ADD CHANGED VARCHAR2(100);
ALTER TABLE T_DMALM_TESTCASE  ADD CHANGED VARCHAR2(100);
COMMENT ON COLUMN DMALM_ANOMALIA_ASSISTENZA.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_ANOMALIA_PRODOTTO.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_BUILD.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_DIFETTO_PRODOTTO.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_DOCUMENTO.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_FASE.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_MANUTENZIONE.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PEI.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PROGETTO_DEMAND.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PROGETTO_ESE.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PROGETTO_SVILUPPO_DEM.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PROGETTO_SVILUPPO_SVIL.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_PROGRAMMA.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_RELEASE_DI_PROGETTO.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_RELEASE_IT.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_RELEASE_SERVIZI.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_RICHIESTA_GESTIONE.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_RICHIESTA_MANUTENZIONE.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_SOTTOPROGRAMMA.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_TASK.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_TASK_IT.CHANGED IS 'CHANGED';
COMMENT ON COLUMN DMALM_TESTCASE.CHANGED IS 'CHANGED';
SPOOL OFF;