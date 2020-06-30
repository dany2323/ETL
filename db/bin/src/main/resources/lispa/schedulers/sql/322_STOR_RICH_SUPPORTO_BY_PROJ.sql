SPOOL 322_STOR_RICH_SUPPORTO_BY_PROJ.sql

create or replace PROCEDURE STOR_RICH_SUPPORTO_BY_PROJ AS 
v_count NUMBER;
v_id_richiesta_supporto VARCHAR2(500);
v_id_repository VARCHAR2(400);
v_dmalm_project_pk NUMBER;
v_DMALM_PROJECT_FK_02 NUMBER;
v_id_project VARCHAR2(500);
v_DT_INIZIO_VALIDITA DATE;
v_DT_FINE_VALIDITA DATE;
cursor cur_rich_supporto is select ID_RICHIESTA_SUPPORTO, id_repository, DMALM_PROJECT_FK_02
from dmalm_richiesta_supporto
where RANK_STATO_RICH_SUPPORTO = 1
group by ID_RICHIESTA_SUPPORTO, id_repository, DMALM_PROJECT_FK_02;
begin
OPEN cur_rich_supporto;
LOOP
    FETCH cur_rich_supporto INTO v_id_richiesta_supporto, v_id_repository, v_DMALM_PROJECT_FK_02;
    EXIT WHEN cur_rich_supporto%NOTFOUND;
    dbms_output.put_line('v_id_richiesta_supporto '||v_id_richiesta_supporto||' - v_id_repository '||v_id_repository||' - v_DMALM_PROJECT_FK_02 '||v_DMALM_PROJECT_FK_02);
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
            if trunc(v_DT_fine_validita) != '31-DEC-9999' then
                SELECT count(id_project)
                INTO v_count
                FROM dmalm_project 
                where id_project = v_id_project and ID_REPOSITORY = v_id_repository and trunc(DT_fine_validita) = '31-DEC-9999';

                if v_count > 0 then
                    SELECT dmalm_project_pk, dt_inizio_validita
                    INTO v_dmalm_project_pk, v_dt_inizio_validita
                    FROM dmalm_project 
                    where id_project = v_id_project and ID_REPOSITORY = v_id_repository and trunc(DT_fine_validita) = '31-DEC-9999';

                    FOR v_query_rich_supp_da_agg IN (SELECT ID_REPOSITORY, URI_RICHIESTA_SUPPORTO, DMALM_RICH_SUPPORTO_PK,
                        STG_PK, DMALM_PROJECT_FK_02, DMALM_USER_FK_06, ID_RICHIESTA_SUPPORTO, DATA_RISOLUZIONE_RICH_SUPPORTO,
                        NR_GIORNI_FESTIVI, TEMPO_TOT_RICH_SUPPORTO, DMALM_STATO_WORKITEM_FK_03, DATA_CREAZ_RICH_SUPPORTO,
                        DATA_MODIFICA_RECORD, DATA_CHIUS_RICH_SUPPORTO, USERID_RICH_SUPPORTO, NOME_RICH_SUPPORTO, MOTIVO_RISOLUZIONE,
                        SEVERITY_RICH_SUPPORTO, DESCRIZIONE_RICH_SUPPORTO, NUMERO_TESTATA_RDI, RANK_STATO_RICH_SUPPORTO,
                        DATA_CAMBIO_STATO_RIC_SUPP, DATA_DISPONIBILITA, PRIORITY_RICH_SUPPORTO, ANNULLATO, DATA_ANNULLAMENTO,
                        DT_STORICIZZAZIONE, DATA_CARICAMENTO, CODICE_AREA, CODICE_PRODOTTO, DATA_SCADENZA, TIMESPENT, DMALM_TEMPO_FK_04
                    FROM dmalm_richiesta_supporto 
                    WHERE DMALM_PROJECT_FK_02 = v_DMALM_PROJECT_FK_02 AND id_richiesta_supporto = v_id_richiesta_supporto AND id_repository = v_id_repository AND RANK_STATO_RICH_SUPPORTO = 1) LOOP

                    INSERT INTO dmalm_richiesta_supporto VALUES(v_query_rich_supp_da_agg.ID_REPOSITORY, v_query_rich_supp_da_agg.URI_RICHIESTA_SUPPORTO, 
                        HISTORY_WORKITEM_SEQ.nextval, v_query_rich_supp_da_agg.STG_PK, v_query_rich_supp_da_agg.DMALM_PROJECT_FK_02, 
                        v_query_rich_supp_da_agg.DMALM_USER_FK_06, v_query_rich_supp_da_agg.ID_RICHIESTA_SUPPORTO, v_query_rich_supp_da_agg.DATA_RISOLUZIONE_RICH_SUPPORTO,
                        v_query_rich_supp_da_agg.NR_GIORNI_FESTIVI, 1, v_query_rich_supp_da_agg.DMALM_STATO_WORKITEM_FK_03, v_query_rich_supp_da_agg.DATA_CREAZ_RICH_SUPPORTO, 
                        v_query_rich_supp_da_agg.DATA_MODIFICA_RECORD, v_query_rich_supp_da_agg.DATA_CHIUS_RICH_SUPPORTO, v_query_rich_supp_da_agg.USERID_RICH_SUPPORTO, 
                        v_query_rich_supp_da_agg.NOME_RICH_SUPPORTO, v_query_rich_supp_da_agg.MOTIVO_RISOLUZIONE, v_query_rich_supp_da_agg.SEVERITY_RICH_SUPPORTO, 
                        v_query_rich_supp_da_agg.DESCRIZIONE_RICH_SUPPORTO, v_query_rich_supp_da_agg.NUMERO_TESTATA_RDI, 1, v_query_rich_supp_da_agg.DATA_CAMBIO_STATO_RIC_SUPP, 
                        v_query_rich_supp_da_agg.DATA_DISPONIBILITA, v_query_rich_supp_da_agg.PRIORITY_RICH_SUPPORTO, v_query_rich_supp_da_agg.ANNULLATO, 
                        v_query_rich_supp_da_agg.DATA_ANNULLAMENTO, v_query_rich_supp_da_agg.DT_STORICIZZAZIONE, v_query_rich_supp_da_agg.DATA_CARICAMENTO, 
                        v_query_rich_supp_da_agg.CODICE_AREA, v_query_rich_supp_da_agg.CODICE_PRODOTTO, v_query_rich_supp_da_agg.DATA_SCADENZA, 
                        v_query_rich_supp_da_agg.TIMESPENT, v_query_rich_supp_da_agg.DMALM_TEMPO_FK_04);
                    END LOOP;
                    UPDATE dmalm_richiesta_supporto SET RANK_STATO_RICH_SUPPORTO = 0 WHERE ID_RICHIESTA_SUPPORTO = v_id_richiesta_supporto AND id_repository = v_id_repository AND DMALM_PROJECT_FK_02 = v_DMALM_PROJECT_FK_02 AND RANK_STATO_RICH_SUPPORTO = 1;
                    dbms_output.put_line('MODIFICHE EFFETTUATE!!!');
                end if;
            end if;
        end if;
END LOOP;
CLOSE cur_rich_supporto;
END STOR_RICH_SUPPORTO_BY_PROJ;