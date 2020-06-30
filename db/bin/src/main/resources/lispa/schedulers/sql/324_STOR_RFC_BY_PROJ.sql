SPOOL 324_STOR_RFC_BY_PROJ.sql

SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;

--------------------------------------------------------
--  File creato - lunedì-ottobre-28-2019   
--------------------------------------------------------
--RFC
PROCEDURE STOR_RFC_BY_PROJ(dataCaricamento TIMESTAMP) AS 
v_count NUMBER;
v_CD_RFC VARCHAR2(500);
v_id_repository VARCHAR2(400);
v_dmalm_project_pk NUMBER;
v_DMALM_PROJECT_FK_02 NUMBER;
v_id_project VARCHAR2(500);
v_DT_INIZIO_VALIDITA DATE;
v_DT_FINE_VALIDITA DATE;
cursor cur_rfc is select CD_RFC, id_repository, DMALM_PROJECT_FK_02
from DMALM_RFC
where RANK_STATO_RFC = 1
group by CD_RFC, id_repository, DMALM_PROJECT_FK_02;
begin
OPEN cur_rfc;
LOOP
    FETCH cur_rfc INTO v_CD_RFC, v_id_repository, v_DMALM_PROJECT_FK_02;
    EXIT WHEN cur_rfc%NOTFOUND;
    DECLARE
        err_code VARCHAR2(100);
        err_msg VARCHAR2(4000);
    BEGIN
        SELECT count(DMALM_PROJECT_PK) 
        INTO v_count
        FROM dmalm_project 
        where DMALM_PROJECT_PK = v_DMALM_PROJECT_FK_02 and ID_REPOSITORY = v_id_repository;

        if v_count > 0 then
            SELECT id_project, DT_fine_validita 
            INTO v_id_project, v_DT_fine_validita
            FROM dmalm_project 
            where DMALM_PROJECT_PK = v_DMALM_PROJECT_FK_02 and ID_REPOSITORY = v_id_repository;

            dbms_output.put_line('v_id_project '||v_id_project||' - v_DT_fine_validita '||to_char(trunc(v_DT_fine_validita)));
            if trunc(v_DT_fine_validita) !=  to_date('31/12/9999','dd/mm/yyyy') then
                SELECT count(id_project)
                INTO v_count
                FROM dmalm_project 
                where id_project = v_id_project and ID_REPOSITORY = v_id_repository and trunc(DT_fine_validita) =  to_date('31/12/9999','dd/mm/yyyy');

                if v_count > 0 then
                    SELECT dmalm_project_pk, dt_inizio_validita
                    INTO v_dmalm_project_pk, v_dt_inizio_validita
                    FROM dmalm_project 
                    where id_project = v_id_project and ID_REPOSITORY = v_id_repository and trunc(DT_fine_validita) =  to_date('31/12/9999','dd/mm/yyyy');

                    FOR v_query_rfc_da_agg IN (SELECT CD_RFC, DMALM_RFC_PK, DMALM_PROJECT_FK_02, 
                        DMALM_STATO_WORKITEM_FK_03, DMALM_TEMPO_FK_04, DMALM_USER_FK_06, RANK_STATO_RFC, RANK_STATO_RFC_MESE, 
                        DT_CARICAMENTO_RFC, TAG_ALM, TS_TAG_ALM, ANNULLATO, STG_PK, URI_RFC, DT_STORICIZZAZIONE, 
                        DATA_MODIFICA_RECORD, TIMESPENT, DESCRIPTION, TIPOLOGIA_INTERVENTO, TIPOLOGIA_DATI_TRATTATI, RICHIESTA_IT, 
                        INFRASTRUTTURA_ESISTENTE, CAMBIAMENTO_RICHIESTO, DESCRIZIONE_UTENZA, REQUISITI_DI_UTILIZZO, MODALITA_GESTIONE, 
                        CRIPTAZIONE_DATI, POLITICA_ARCHIVIZIONE_DATI, SEPARAZIONE_RUOLI_ACCESSO_DATI, CLASSE_DI_SERVIZIO, 
                        ACCESSO_AL_SERVIZIO, ASSISTENZA_UTENTI, ANALISI_DEI_RISCHI, DESCRIZIONE_DEL_SERVIZIO, COD_SCHEDA_SERVIZIO_POLARION, 
                        TITOLO_DELLA_RICHIESTA, ID_REPOSITORY, DATA_CAMBIO_STATO_RFC, DATA_ANNULLAMENTO
                    FROM DMALM_RFC
                    WHERE DMALM_PROJECT_FK_02 = v_DMALM_PROJECT_FK_02 AND CD_RFC = v_CD_RFC AND id_repository = v_id_repository AND RANK_STATO_RFC = 1) LOOP

                    INSERT INTO DMALM_RFC VALUES(v_query_rfc_da_agg.CD_RFC, HISTORY_WORKITEM_SEQ.nextval, 
                        v_dmalm_project_pk, v_query_rfc_da_agg.DMALM_STATO_WORKITEM_FK_03, 
                        v_query_rfc_da_agg.DMALM_TEMPO_FK_04, v_query_rfc_da_agg.DMALM_USER_FK_06, 1, v_query_rfc_da_agg.RANK_STATO_RFC_MESE, 
                        dataCaricamento, v_query_rfc_da_agg.TAG_ALM, v_query_rfc_da_agg.TS_TAG_ALM, v_query_rfc_da_agg.ANNULLATO, 
                        v_query_rfc_da_agg.STG_PK, v_query_rfc_da_agg.URI_RFC, v_dt_inizio_validita, v_query_rfc_da_agg.DATA_MODIFICA_RECORD, 
                        v_query_rfc_da_agg.TIMESPENT, v_query_rfc_da_agg.DESCRIPTION, v_query_rfc_da_agg.TIPOLOGIA_INTERVENTO, 
                        v_query_rfc_da_agg.TIPOLOGIA_DATI_TRATTATI, v_query_rfc_da_agg.RICHIESTA_IT, v_query_rfc_da_agg.INFRASTRUTTURA_ESISTENTE, 
                        v_query_rfc_da_agg.CAMBIAMENTO_RICHIESTO, v_query_rfc_da_agg.DESCRIZIONE_UTENZA, v_query_rfc_da_agg.REQUISITI_DI_UTILIZZO, 
                        v_query_rfc_da_agg.MODALITA_GESTIONE, v_query_rfc_da_agg.CRIPTAZIONE_DATI, v_query_rfc_da_agg.POLITICA_ARCHIVIZIONE_DATI, 
                        v_query_rfc_da_agg.SEPARAZIONE_RUOLI_ACCESSO_DATI, v_query_rfc_da_agg.CLASSE_DI_SERVIZIO, v_query_rfc_da_agg.ACCESSO_AL_SERVIZIO, 
                        v_query_rfc_da_agg.ASSISTENZA_UTENTI, v_query_rfc_da_agg.ANALISI_DEI_RISCHI, v_query_rfc_da_agg.DESCRIZIONE_DEL_SERVIZIO, 
                        v_query_rfc_da_agg.COD_SCHEDA_SERVIZIO_POLARION, v_query_rfc_da_agg.TITOLO_DELLA_RICHIESTA, v_query_rfc_da_agg.ID_REPOSITORY, 
                        v_query_rfc_da_agg.DATA_CAMBIO_STATO_RFC, v_query_rfc_da_agg.DATA_ANNULLAMENTO);
                    END LOOP;
                    UPDATE DMALM_RFC SET RANK_STATO_RFC = 0 WHERE CD_RFC = v_CD_RFC AND id_repository = v_id_repository AND DMALM_PROJECT_FK_02 = v_DMALM_PROJECT_FK_02 AND RANK_STATO_RFC = 1;
                    dbms_output.put_line('MODIFICHE EFFETTUATE!!!');
                end if;
            end if;
        end if;
    EXCEPTION 
        WHEN INVALID_NUMBER THEN
            err_code := SQLCODE;
            err_msg := SUBSTR(SQLERRM, 1, 200);
            INSERT INTO DMALM_ERROR_LOG_CHECKPROJSTOR VALUES(v_dmalm_project_pk, v_dmalm_project_pk, v_id_repository, dataCaricamento, err_code, err_msg);
        WHEN OTHERS THEN
            err_code := SQLCODE;
            err_msg := SUBSTR(SQLERRM, 1, 200);
            INSERT INTO DMALM_ERROR_LOG_CHECKPROJSTOR VALUES(v_dmalm_project_pk, v_dmalm_project_pk, v_id_repository, dataCaricamento, err_code, err_msg);
    END;
END LOOP;
CLOSE cur_rfc;
END STOR_RFC_BY_PROJ;