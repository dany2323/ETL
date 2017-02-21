SELECT DISTINCT cd_ANOMALIA AS CODICE,
                ID_REPOSITORY,
                'anomalia' AS TYPE
FROM dmalm_anomalia_prodotto
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_DOCUMENTO AS codice,
                ID_REPOSITORY,
                'documento' AS TYPE
FROM dmalm_documento
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_manutenzione AS CODICE,
                ID_REPOSITORY,
                'sman' AS TYPE
FROM dmalm_manutenzione
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_difetto AS CODICE,
                ID_REPOSITORY,
                'defect' AS TYPE
FROM dmalm_difetto_prodotto
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_anomalia_ass AS CODICE,
                ID_REPOSITORY,
                'anomalia_assistenza' AS TYPE
FROM dmalm_anomalia_assistenza
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_build AS CODICE,
                ID_REPOSITORY,
                'build' AS TYPE
FROM dmalm_build
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_fase AS CODICE,
                ID_REPOSITORY,
                'fase' AS TYPE
FROM dmalm_fase
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_pei AS CODICE,
                ID_REPOSITORY,
                'pei' AS TYPE
FROM dmalm_pei
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_progetto_demand AS CODICE,
                ID_REPOSITORY,
                'rqd' AS TYPE
FROM dmalm_progetto_demand
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_progetto_ese AS CODICE,
                ID_REPOSITORY,
                'progettoese' AS TYPE
FROM dmalm_progetto_ese
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_PROG_SVIL_D AS CODICE,
                ID_REPOSITORY ,
                'drqs' AS TYPE
FROM dmalm_progetto_sviluppo_dem
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_PROG_SVIL_S AS CODICE,
                ID_REPOSITORY,
                'srqs' AS TYPE
FROM dmalm_progetto_sviluppo_svil
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_PROGRAMMA AS CODICE,
                ID_REPOSITORY,
                'programma' AS TYPE
FROM dmalm_programma
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_RELEASEDIPROG AS CODICE,
                ID_REPOSITORY ,
                'release' AS TYPE
FROM dmalm_release_di_progetto
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_release_it AS CODICE,
                ID_REPOSITORY,
                'release_it' AS TYPE
FROM dmalm_release_it
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_REL_SERVIZI AS CODICE,
                ID_REPOSITORY,
                'release_ser' AS TYPE
FROM dmalm_release_servizi
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_RICHIESTA_GEST AS CODICE,
                ID_REPOSITORY,
                'richiesta_gestione' AS TYPE
FROM dmalm_richiesta_gestione
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_RICHIESTA_MANUTENZIONE AS CODICE,
                ID_REPOSITORY ,
                'dman' AS TYPE
FROM dmalm_richiesta_manutenzione
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_SOTTOPROGRAMMA AS CODICE,
                ID_REPOSITORY,
                'sottoprogramma' AS TYPE
FROM dmalm_sottoprogramma
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_task AS CODICE,
                ID_REPOSITORY,
                'task' AS TYPE
FROM dmalm_task
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_task_it AS CODICE,
                ID_REPOSITORY,
                'taskit' AS TYPE
FROM dmalm_task_it
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_testcase AS CODICE,
                ID_REPOSITORY,
                'testcase' AS TYPE
FROM dmalm_testcase
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
UNION ALL
SELECT DISTINCT cd_classificatore AS CODICE,
                ID_REPOSITORY,
                'classificatore' AS TYPE
FROM dmalm_classificatore
WHERE annullato IS NULL
  AND changed IS NULL minus
  SELECT DISTINCT CODICE , ID_REPOSITORY, TYPE
  FROM DMALM_STG_CURRENT_WORKITEMS
ORDER BY 2,
         3,
         1