SPOOL 198_STORICIZZA_PROJ_PRODOTTO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE OR REPLACE PROCEDURE STORICIZZA_PROJ_PRODOTTO (V_PROJECT DMALM_PROJECT%ROWTYPE,V_UO_PRODOTTO DMALM_PRODOTTO.DMALM_UNITAORGANIZZATIVA_FK_01%TYPE) IS
    l_today_timestamp   TIMESTAMP := SYSTIMESTAMP;
   BEGIN
       UPDATE DMALM_PROJECT PROJ
            SET PROJ.DT_FINE_VALIDITA =
            ( SELECT SYSDATE - INTERVAL '0.001' SECOND FROM DUAL
            )
            WHERE PROJ.DMALM_PROJECT_PK=V_PROJECT.DMALM_PROJECT_PK;  
            
             insert into DMALM_LOG_DEBUG (OBJECT_TYPE,
               LOG_DEBUG,
               ACTION,
               ID,
               RELATED_OBJECT_NAME,
               ID_RELATED_OBJECT,
               DATA_CARICAMENTO)
               values('TRIGGER_PRODOTTO',
               'UPDATING RECORD '||V_PROJECT.TEMPLATE || ' WITH NAME: '|| V_PROJECT.NOME_COMPLETO_PROJECT|| ' PK: '|| V_PROJECT.DMALM_PROJECT_PK || ' AND CURRENT_UO_FK: '||V_PROJECT.DMALM_STRUTTURA_ORG_FK_02,
               'UPDATING',
                V_UO_PRODOTTO, 
               'DMALM_PROJECT',
               V_PROJECT.DMALM_PROJECT_PK,
               l_today_timestamp);
               
            	INSERT
              INTO DMALM_PROJECT
              (
              DMALM_PROJECT_PK,
              DMALM_AREA_TEMATICA_FK_01,
              ID_PROJECT,
              ID_REPOSITORY,
              SIGLA_PROJECT,
              NOME_COMPLETO_PROJECT,
              PATH_PROJECT,
              SERVICE_MANAGERS,
              FL_ATTIVO,
              DT_INIZIO_VALIDITA,
              DT_FINE_VALIDITA,
              DT_CARICAMENTO,
              TEMPLATE,
              DMALM_STRUTTURA_ORG_FK_02,
              ANNULLATO,
              DATA_MODIFICA_PROJECT
              )
              VALUES
              (
              HISTORY_PROJECT_SEQ.NEXTVAL,
              V_PROJECT.DMALM_AREA_TEMATICA_FK_01,
              V_PROJECT.ID_PROJECT,
              V_PROJECT.ID_REPOSITORY,
              V_PROJECT.SIGLA_PROJECT,
              V_PROJECT.NOME_COMPLETO_PROJECT,
              V_PROJECT.PATH_PROJECT,
              V_PROJECT.SERVICE_MANAGERS,
              V_PROJECT.FL_ATTIVO,
              (SELECT SYSDATE FROM DUAL
              ),
              TIMESTAMP '9999-12-31 00:00:00',
              V_PROJECT.DT_CARICAMENTO,
              V_PROJECT.TEMPLATE,
              V_UO_PRODOTTO,
              V_PROJECT.ANNULLATO,
                (SELECT SYSDATE FROM DUAL
                )
              );
              
              insert into DMALM_LOG_DEBUG (OBJECT_TYPE,
               LOG_DEBUG,
               ACTION,
               ID,
               RELATED_OBJECT_NAME,
               ID_RELATED_OBJECT,
               DATA_CARICAMENTO)
               values('TRIGGER_PRODOTTO',
               'INSERTING RECORD PROJECT '||V_PROJECT.TEMPLATE || ' WITH NAME: '|| V_PROJECT.NOME_COMPLETO_PROJECT|| ' AND PK: '|| HISTORY_PROJECT_SEQ.CURRVAL,
               'INSERTING',
                V_UO_PRODOTTO,
               'DMALM_PROJECT',
               HISTORY_PROJECT_SEQ.CURRVAL,
               l_today_timestamp);           
 END storicizza_proj_prodotto;
SHOW ERRORS;
SPOOL OFF;
