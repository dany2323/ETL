select
   distinct cd_ANOMALIA as CODICE,
   ID_REPOSITORY, 'anomalia' as type
   from dmalm_anomalia_prodotto
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_DOCUMENTO as codice,
   ID_REPOSITORY, 'documento' as type
   from dmalm_documento
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_manutenzione as CODICE,
   ID_REPOSITORY, 'sman' as type
   from dmalm_manutenzione
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_difetto as CODICE,
   ID_REPOSITORY, 'defect' as type
   from dmalm_difetto_prodotto
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_anomalia_ass as CODICE,
   ID_REPOSITORY, 'anomalia_assistenza' as type
   from dmalm_anomalia_assistenza
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_build as CODICE,
   ID_REPOSITORY, 'build' as type
   from dmalm_build
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_fase as CODICE,
   ID_REPOSITORY, 'fase' as type
   from dmalm_fase
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_pei as CODICE,
   ID_REPOSITORY, 'pei' as type
   from dmalm_pei
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_progetto_demand as CODICE,
   ID_REPOSITORY, 'rqd' as type
   from dmalm_progetto_demand
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_progetto_ese as CODICE,
   ID_REPOSITORY, 'progettoese' as type
   from dmalm_progetto_ese
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_PROG_SVIL_D as CODICE,
   ID_REPOSITORY , 'drqs' as type
   from dmalm_progetto_sviluppo_dem
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_PROG_SVIL_S as CODICE,
   ID_REPOSITORY, 'srqs' as type
   from dmalm_progetto_sviluppo_svil
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_PROGRAMMA as CODICE,
   ID_REPOSITORY, 'programma' as type
   from dmalm_programma
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_RELEASEDIPROG as CODICE,
   ID_REPOSITORY , 'release' as type
   from dmalm_release_di_progetto
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_release_it as CODICE,
   ID_REPOSITORY, 'release_it' as type
   from dmalm_release_it
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_REL_SERVIZI as CODICE,
   ID_REPOSITORY, 'release_ser' as type
   from dmalm_release_servizi
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_RICHIESTA_GEST as CODICE,
   ID_REPOSITORY, 'richiesta_gestione' as type
   from dmalm_richiesta_gestione
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_RICHIESTA_MANUTENZIONE as CODICE,
   ID_REPOSITORY , 'dman' as type
   from dmalm_richiesta_manutenzione
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_SOTTOPROGRAMMA as CODICE,
   ID_REPOSITORY, 'sottoprogramma' as type
   from dmalm_sottoprogramma
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_task as CODICE,
   ID_REPOSITORY, 'task' as type
   from dmalm_task
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_task_it as CODICE, 
   ID_REPOSITORY, 'taskit' as type
   from dmalm_task_it
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_testcase as CODICE,
   ID_REPOSITORY, 'testcase' as type
   from dmalm_testcase
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
   union
   all
   select
   distinct cd_classificatore as CODICE,
   ID_REPOSITORY, 'classificatore' as type
   from dmalm_classificatore
   where annullato is NULL
   and changed is null
   minus
   select distinct CODICE , ID_REPOSITORY, TYPE from DMALM_STG_CURRENT_WORKITEMS
 ORDER BY 2, 3, 1    
  