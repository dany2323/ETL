INSERT /*+ APPEND */ INTO DMALM_LINKED_WORKITEMS  (CODICE_WI_FIGLIO,
                                    CODICE_WI_PADRE,
                                    TIPO_WI_FIGLIO,
                                    TIPO_WI_PADRE,
                                    FK_WI_FIGLIO,
                                    FK_WI_PADRE,
                                    RUOLO,
                                    DT_CARICAMENTO,
                                    TITOLO_WI_FIGLIO,
                                    TITOLO_WI_PADRE,
                                    URI_WI_FIGLIO,
                                    URI_WI_PADRE,
                                    ID_REPOSITORY_FIGLIO,
                                    ID_REPOSITORY_PADRE,
                                    CODICE_PROJECT_FIGLIO,
                                    CODICE_PROJECT_PADRE,
                                    LINKED_WORKITEMS_PK)
   SELECT ID_FIGLIO,
          ID_PADRE,
          TIPO_FIGLIO,
          TIPO_PADRE,
          PK_FIGLIO,
          PK_PADRE,
          RUOLO,
          DT_CARICAMENTO,
          TITOLO_WI_FIGLIO,
          TITOLO_WI_PADRE,
          URI_WI_FIGLIO,
          URI_WI_PADRE,
          ID_REPOSITORY_FIGLIO,
          ID_REPOSITORY_PADRE,
          ID_PROJECT_FIGLIO,
          ID_PROJECT_PADRE,
          LINKED_WORKITEMS_SEQ.NEXTVAL
     FROM (
SELECT    /*+ leading (l wifiglio wipadre) use_nl(wifliglio) use_nl(wipadre) index (wipadre) index(wifiglio) */
             (select  prjpadre.ID_PROJECT from dmalm_project prjpadre where PRJPADRE.DMALM_PROJECT_PK = wipadre.PROJECT_FK) AS ID_PROJECT_PADRE,
             (select prjfiglio.ID_PROJECT from dmalm_project prjfiglio where PRJFIGLIO.DMALM_PROJECT_PK = wifiglio.PROJECT_FK) AS ID_PROJECT_FIGLIO,
                  wipadre.ID_REPOSITORY AS ID_REPOSITORY_PADRE,
                  wifiglio.ID_REPOSITORY AS ID_REPOSITORY_FIGLIO,
                  wipadre.codice AS ID_PADRE,
                  wifiglio.codice AS ID_FIGLIO,
                  wipadre.TYPE AS TIPO_PADRE,
                  wifiglio.TYPE AS TIPO_FIGLIO,
                  l.c_role AS RUOLO,
                  wipadre.DMALM_PK AS PK_PADRE,
                  wifiglio.DMALM_PK AS PK_FIGLIO,
                  l.data_caricamento AS DT_CARICAMENTO,
                  wipadre.titolo AS TITOLO_WI_PADRE,
                  wifiglio.titolo AS TITOLO_WI_FIGLIO,
                  l.FK_URI_P_WORKITEM AS URI_WI_PADRE,
                  l.FK_URI_WORKITEM AS URI_WI_FIGLIO
             FROM dmalm_sire_current_work_linked l
                  JOIN TOTAL wifiglio
                     ON l.FK_P_WORKITEM = wifiglio.uri
                  JOIN TOTAL wipadre
                     ON l.FK_WORKITEM = wipadre.uri
              WHERE     wifiglio.RANK_STATO = 1
                  AND wipadre.RANK_STATO = 1
                  AND wipadre.ID_REPOSITORY = 'SIRE'
                  AND wipadre.ID_REPOSITORY = wifiglio.ID_REPOSITORY
                  AND l.DATA_CARICAMENTO = ?
           UNION  SELECT  /*+ leading (l wifiglio wipadre) use_nl(wifliglio) use_nl(wipadre) index (wipadre) index(wifiglio) */
              (select  prjpadre.ID_PROJECT from dmalm_project prjpadre where PRJPADRE.DMALM_PROJECT_PK = wipadre.PROJECT_FK) AS ID_PROJECT_PADRE,
             (select prjfiglio.ID_PROJECT from dmalm_project prjfiglio where PRJFIGLIO.DMALM_PROJECT_PK = wifiglio.PROJECT_FK) AS ID_PROJECT_FIGLIO,
                  wipadre.ID_REPOSITORY AS ID_REPOSITORY_PADRE,
                  wifiglio.ID_REPOSITORY AS ID_REPOSITORY_FIGLIO,
                  wipadre.codice AS ID_PADRE,
                  wifiglio.codice AS ID_FIGLIO,
                  wipadre.TYPE AS TIPO_PADRE,
                  wifiglio.TYPE AS TIPO_FIGLIO,
                  l.c_role AS RUOLO,
                  wipadre.DMALM_PK AS PK_PADRE,
                  wifiglio.DMALM_PK AS PK_FIGLIO,
                  l.data_caricamento AS DT_CARICAMENTO,
                  wipadre.titolo AS TITOLO_WI_PADRE,
                  wifiglio.titolo AS TITOLO_WI_FIGLIO,
                  l.FK_URI_P_WORKITEM AS URI_WI_PADRE,
                  l.FK_URI_WORKITEM AS URI_WI_FIGLIO
             FROM dmalm_siss_current_work_linked l
                  JOIN TOTAL wifiglio
                     ON l.FK_P_WORKITEM = wifiglio.uri
                  JOIN TOTAL wipadre
                     ON l.FK_WORKITEM = wipadre.uri
            WHERE     wifiglio.RANK_STATO = 1
                  AND wipadre.RANK_STATO = 1
                  AND wipadre.ID_REPOSITORY = 'SISS'
                  AND wipadre.ID_REPOSITORY = wifiglio.ID_REPOSITORY
                  AND l.DATA_CARICAMENTO = ?
                  order by ID_PADRE, ID_FIGLIO, PK_PADRE, PK_FIGLIO ) 