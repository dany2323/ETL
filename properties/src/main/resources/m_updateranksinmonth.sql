update DMALM_ANOMALIA_PRODOTTO set RANK_STATO_ANOMALIA_MESE = 0; 
update DMALM_ANOMALIA_ASSISTENZA set RANK_STATO_ANOMALIA_ASS_MESE = 0; 
update DMALM_BUILD set RANK_STATO_BUILD_MESE = 0; 
update DMALM_CLASSIFICATORE set RANK_STATO_CLASSIF_MESE = 0;
update DMALM_DIFETTO_PRODOTTO set RANK_STATO_DIFETTO_MESE = 0; 
update DMALM_DOCUMENTO set RANK_STATO_DOCUMENTO_MESE = 0; 
update DMALM_FASE set RANK_STATO_FASE_MESE = 0; 
update DMALM_MANUTENZIONE set RANK_STATO_MANUTENZIONE_MESE = 0; 
update DMALM_PEI set RANK_STATO_PEI_MESE = 0; 
update DMALM_PROGETTO_DEMAND set RANK_STATO_PROGETTO_DEM_MESE = 0; 
update DMALM_PROGETTO_ESE set RANK_STATO_PROGETTO_ESE_MESE = 0; 
update DMALM_PROGETTO_SVILUPPO_DEM set RANK_STATO_PROG_SVIL_D_MESE = 0; 
update DMALM_PROGETTO_SVILUPPO_SVIL set RANK_STATO_PROG_SVIL_S_MESE = 0; 
update DMALM_PROGRAMMA set RANK_STATO_PROGRAMMA_MESE = 0; 
update DMALM_RELEASE_DI_PROGETTO set RANK_STATO_RELEASEDIPROG_MESE = 0; 
update DMALM_RELEASE_IT set RANK_STATO_RELEASE_IT_MESE = 0; 
update DMALM_RELEASE_SERVIZI set RANK_STATO_REL_SERVIZI_MESE = 0; 
update DMALM_RICHIESTA_GESTIONE set RANK_STATO_RICHIESTA_GEST_MESE = 0; 
update DMALM_RICHIESTA_MANUTENZIONE set RANK_STATO_RICH_MANUT_MESE = 0; 
update DMALM_SOTTOPROGRAMMA set RANK_STATO_SOTTOPROGRAMMA_MESE = 0; 
update DMALM_TASK set RANK_STATO_TASK_MESE = 0; 
update DMALM_TASK_IT set RANK_STATO_TASK_IT_MESE = 0; 
update DMALM_TESTCASE set RANK_STATO_TESTCASE_MESE = 0;
update DMALM_ANOMALIA_PRODOTTO set RANK_STATO_ANOMALIA_MESE = 1 where DMALM_ANOMALIA_PRODOTTO_PK in (
select DMALM_ANOMALIA_PRODOTTO_PK from 
(
select a.dmalm_anomalia_prodotto_pk, row_number() over (partition by a.cd_anomalia, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) order by DMALM_ANOMALIA_PRODOTTO_PK desc) as ranker from DMALM_ANOMALIA_PRODOTTO a
) Z 
where ranker = 1); 
update DMALM_ANOMALIA_ASSISTENZA set RANK_STATO_ANOMALIA_ASS_MESE = 1 where DMALM_ANOMALIA_ASS_PK in (
select DMALM_ANOMALIA_ASS_PK from 
(
select a.DMALM_ANOMALIA_ASS_PK, row_number() over 
	(partition by a.CD_ANOMALIA_ASS, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_ANOMALIA_ASS_PK desc) as ranker from DMALM_ANOMALIA_ASSISTENZA a
) Z 
where ranker = 1);
update DMALM_BUILD set RANK_STATO_BUILD_MESE = 1 where DMALM_BUILD_PK in (
select DMALM_BUILD_PK from 
(
select a.DMALM_BUILD_PK, row_number() over 
	(partition by a.CD_BUILD, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_BUILD_PK desc) as ranker from DMALM_BUILD a
) Z 
where ranker = 1); 
update DMALM_CLASSIFICATORE set RANK_STATO_CLASSIF_MESE = 1 where DMALM_CLASSIFICATORE_PK in (
select DMALM_CLASSIFICATORE_PK from 
(
select a.DMALM_CLASSIFICATORE_PK, row_number() over 
	(partition by a.CD_CLASSIFICATORE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_CLASSIFICATORE_PK desc) as ranker from DMALM_CLASSIFICATORE a
) Z 
where ranker = 1); 
update DMALM_DIFETTO_PRODOTTO set RANK_STATO_DIFETTO_MESE = 1 where DMALM_DIFETTO_PRODOTTO_PK in (
select DMALM_DIFETTO_PRODOTTO_PK from
(
select a.dmalm_DIFETTO_prodotto_pk, row_number() over (partition by a.cd_DIFETTO, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) order by DMALM_DIFETTO_PRODOTTO_PK desc) as ranker from DMALM_DIFETTO_PRODOTTO a
) Z 
where ranker = 1);
update DMALM_DOCUMENTO set RANK_STATO_DOCUMENTO_MESE = 1 where DMALM_DOCUMENTO_PK in (
select DMALM_DOCUMENTO_PK from 
(
select a.DMALM_DOCUMENTO_PK, row_number() over 
	(partition by a.CD_DOCUMENTO, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_DOCUMENTO_PK desc) as ranker from DMALM_DOCUMENTO a
) Z 
where ranker = 1); 
update DMALM_FASE set RANK_STATO_FASE_MESE = 1 where DMALM_FASE_PK in (
select DMALM_FASE_PK from 
(
select a.DMALM_FASE_PK, row_number() over 
	(partition by a.CD_FASE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_FASE_PK desc) as ranker from DMALM_FASE a
) Z 
where ranker = 1); 
update DMALM_MANUTENZIONE set RANK_STATO_MANUTENZIONE_MESE = 1 where DMALM_MANUTENZIONE_PK in (
select DMALM_MANUTENZIONE_PK from 
(
select a.DMALM_MANUTENZIONE_PK, row_number() over 
	(partition by a.CD_MANUTENZIONE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_MANUTENZIONE_PK desc) as ranker from DMALM_MANUTENZIONE a
) Z 
where ranker = 1); 
update DMALM_PEI set RANK_STATO_PEI_MESE = 1 where DMALM_PEI_PK in (
select DMALM_PEI_PK from 
(
select a.DMALM_PEI_PK, row_number() over 
	(partition by a.CD_PEI, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PEI_PK desc) as ranker from DMALM_PEI a
) Z 
where ranker = 1); 
update DMALM_PROGETTO_DEMAND set RANK_STATO_PROGETTO_DEM_MESE = 1 where DMALM_PROGETTO_DEMAND_PK in (
select DMALM_PROGETTO_DEMAND_PK from 
(
select a.DMALM_PROGETTO_DEMAND_PK, row_number() over 
	(partition by a.CD_PROGETTO_DEMAND, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PROGETTO_DEMAND_PK desc) as ranker from DMALM_PROGETTO_DEMAND a
) Z 
where ranker = 1); 
update DMALM_PROGETTO_ESE set RANK_STATO_PROGETTO_ESE_MESE = 1 where DMALM_PROGETTO_ESE_PK in (
select DMALM_PROGETTO_ESE_PK from 
(
select a.DMALM_PROGETTO_ESE_PK, row_number() over 
	(partition by a.CD_PROGETTO_ESE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PROGETTO_ESE_PK desc) as ranker from DMALM_PROGETTO_ESE a
) Z 
where ranker = 1); 
update DMALM_PROGETTO_SVILUPPO_DEM set RANK_STATO_PROG_SVIL_D_MESE = 1 where DMALM_PROG_SVIL_D_PK in (
select DMALM_PROG_SVIL_D_PK from 
(
select a.DMALM_PROG_SVIL_D_PK, row_number() over 
	(partition by a.CD_PROG_SVIL_D, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PROG_SVIL_D_PK desc) as ranker from DMALM_PROGETTO_SVILUPPO_DEM a
) Z 
where ranker = 1); 
update DMALM_PROGETTO_SVILUPPO_SVIL set RANK_STATO_PROG_SVIL_S_MESE = 1 where DMALM_PROG_SVIL_S_PK in (
select DMALM_PROG_SVIL_S_PK from 
(
select a.DMALM_PROG_SVIL_S_PK, row_number() over 
	(partition by a.CD_PROG_SVIL_S, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PROG_SVIL_S_PK desc) as ranker from DMALM_PROGETTO_SVILUPPO_SVIL a
) Z 
where ranker = 1); 
update DMALM_PROGRAMMA set RANK_STATO_PROGRAMMA_MESE = 1 where DMALM_PROGRAMMA_PK in (
select DMALM_PROGRAMMA_PK from 
(
select a.DMALM_PROGRAMMA_PK, row_number() over 
	(partition by a.CD_PROGRAMMA, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_PROGRAMMA_PK desc) as ranker from DMALM_PROGRAMMA a
) Z 
where ranker = 1); 
update DMALM_RELEASE_DI_PROGETTO set RANK_STATO_RELEASEDIPROG_MESE = 1 where DMALM_RELEASEDIPROG_PK in (
select DMALM_RELEASEDIPROG_PK from 
(
select a.DMALM_RELEASEDIPROG_PK, row_number() over 
	(partition by a.CD_RELEASEDIPROG, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_RELEASEDIPROG_PK desc) as ranker from DMALM_RELEASE_DI_PROGETTO a
) Z 
where ranker = 1); 
update DMALM_RELEASE_IT set RANK_STATO_RELEASE_IT_MESE = 1 where DMALM_RELEASE_IT_PK in (
select DMALM_RELEASE_IT_PK from 
(
select a.DMALM_RELEASE_IT_PK, row_number() over 
	(partition by a.CD_RELEASE_IT, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_RELEASE_IT_PK desc) as ranker from DMALM_RELEASE_IT a
) Z 
where ranker = 1); 
update DMALM_RELEASE_SERVIZI set RANK_STATO_REL_SERVIZI_MESE = 1 where DMALM_REL_SERVIZI_PK in (
select DMALM_REL_SERVIZI_PK from 
(
select a.DMALM_REL_SERVIZI_PK, row_number() over 
	(partition by a.CD_REL_SERVIZI, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_REL_SERVIZI_PK desc) as ranker from DMALM_RELEASE_SERVIZI a
) Z 
where ranker = 1); 
update DMALM_RICHIESTA_GESTIONE set RANK_STATO_RICHIESTA_GEST_MESE = 1 where DMALM_RICHIESTA_GEST_PK in (
select DMALM_RICHIESTA_GEST_PK from 
(
select a.DMALM_RICHIESTA_GEST_PK, row_number() over 
	(partition by a.CD_RICHIESTA_GEST, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_RICHIESTA_GEST_PK desc) as ranker from DMALM_RICHIESTA_GESTIONE a
) Z 
where ranker = 1); 
update DMALM_RICHIESTA_MANUTENZIONE set RANK_STATO_RICH_MANUT_MESE = 1 where DMALM_RICH_MANUTENZIONE_PK in (
select DMALM_RICH_MANUTENZIONE_PK from 
(
select a.DMALM_RICH_MANUTENZIONE_PK, row_number() over 
	(partition by a.CD_RICHIESTA_MANUTENZIONE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_RICH_MANUTENZIONE_PK desc) as ranker from DMALM_RICHIESTA_MANUTENZIONE a
) Z 
where ranker = 1); 
update DMALM_SOTTOPROGRAMMA set RANK_STATO_SOTTOPROGRAMMA_MESE = 1 where DMALM_SOTTOPROGRAMMA_PK in (
select DMALM_SOTTOPROGRAMMA_PK from 
(
select a.DMALM_SOTTOPROGRAMMA_PK, row_number() over 
	(partition by a.CD_SOTTOPROGRAMMA, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_SOTTOPROGRAMMA_PK desc) as ranker from DMALM_SOTTOPROGRAMMA a
) Z 
where ranker = 1); 
update DMALM_TASK set RANK_STATO_TASK_MESE = 1 where DMALM_TASK_PK in (
select DMALM_TASK_PK from 
(
select a.DMALM_TASK_PK, row_number() over 
	(partition by a.CD_TASK, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_TASK_PK desc) as ranker from DMALM_TASK a
) Z 
where ranker = 1); 
update DMALM_TASK_IT set RANK_STATO_TASK_IT_MESE = 1 where DMALM_TASK_IT_PK in (
select DMALM_TASK_IT_PK from 
(
select a.DMALM_TASK_IT_PK, row_number() over 
	(partition by a.CD_TASK_IT, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_TASK_IT_PK desc) as ranker from DMALM_TASK_IT a
) Z 
where ranker = 1); 
update DMALM_TESTCASE set RANK_STATO_TESTCASE_MESE = 1 where DMALM_TESTCASE_PK in (
select DMALM_TESTCASE_PK from 
(
select a.DMALM_TESTCASE_PK, row_number() over 
	(partition by a.CD_TESTCASE, extract(year from DT_STORICIZZAZIONE), extract(month from DT_STORICIZZAZIONE) 
	order by DMALM_TESTCASE_PK desc) as ranker from DMALM_TESTCASE a
) Z 
where ranker = 1);