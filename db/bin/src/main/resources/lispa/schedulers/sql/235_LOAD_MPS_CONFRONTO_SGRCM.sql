SPOOL 235_LOAD_MPS_CONFRONTO_SGRCM.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE OR REPLACE PROCEDURE load_mps_confronto_sgrcm (
   v_esito_proc OUT INTEGER)
AS
BEGIN
   DECLARE
      --        v_riciclo integer;
      v_identificativo    INTEGER;
      c_esito_ok          INTEGER := 3;
      v_controllo_esito   INTEGER;
      v_data_inizio       TIMESTAMP;
      v_data_fine         TIMESTAMP;
      --        err_num integer;
      err_msg             VARCHAR2 (100);
   BEGIN
      -- Reperisco la max dell'identificativo dell'ultimo caricamento avvenuto
      SELECT NVL (MAX (id), 0)
        INTO v_identificativo
        FROM mps_bo_confronto_sgrcm_log;

      v_identificativo := v_identificativo + 1;


      BEGIN
         v_esito_proc := 1;

         --      Creazione del backup.
         EXECUTE IMMEDIATE 'truncate table MPS_BO_CONFRONTO_MPS_SGRCM_BCK';

         INSERT INTO MPS_BO_CONFRONTO_MPS_SGRCM_BCK
            SELECT * FROM MPS_BO_CONFRONTO_MPS_SGRCM;


         EXECUTE IMMEDIATE 'truncate table MPS_BO_CONFRONTO_MPS_SGRCM';

         /*
         Inserimento dei dati MPS / SGRCM nella tabella di confronto.
         */

         v_data_inizio := SYSDATE;

         INSERT INTO MPS_BO_CONFRONTO_MPS_SGRCM
              SELECT FK_WI_PADRE,
                     codice_wi_padre,
                     tipo_wi_padre,
                     codice_incarico_sgrcm,
                     fk_wi_figlio,
                     codice_wi_figlio,
                     tipo_wi_figlio,
                     codice_rilascio_wi_figlio,
                     fk_wi_doc,
                     codice_wi_documento,
                     tipo_wi_documento,
                     idcontratto,
                     codcontratto,
                     idrilascio,
                     codrilascio,
                     codverbale,
                     idverbale,
                     idattivita,
                     codattivita,
                     repository_sgrcm,
                     repository_mps
                FROM    (SELECT DMALM_PROGRAMMA.DMALM_PROGRAMMA_PK FK_WI_PADRE,
                                DMALM_PROGRAMMA.CD_PROGRAMMA CODICE_WI_PADRE,
                                'PROGRAMMA' TIPO_WI_PADRE,
                                TRIM (DMALM_PROGRAMMA.CODICE)
                                   CODICE_INCARICO_SGRCM,
                                V_GERARCHIA_MISTA_LINKED_WI.FK_WI_FIGLIO,
                                NVL (
                                   TRIM (
                                      DMALM_PROGETTO_DEMAND.CD_PROGETTO_DEMAND),
                                   NVL (
                                      TRIM (
                                         DMALM_RICHIESTA_MANUTENZIONE.CD_RICHIESTA_MANUTENZIONE),
                                      NVL (
                                         TRIM (
                                            DMALM_SOTTOPROGRAMMA.CD_SOTTOPROGRAMMA),
                                         'ND')))
                                   CODICE_WI_FIGLIO,
                                CASE
                                   WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                           'rqd'
                                   THEN
                                      'Progetto Demand'
                                   WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                           'dman'
                                   THEN
                                      'Richiesta Manutenzione'
                                   WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                           'sottoprogramma'
                                   THEN
                                      'Sottoprogramma'
                                   WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                           'documento'
                                   THEN
                                      'Documento'
                                END
                                   TIPO_WI_FIGLIO,
                                NVL (
                                   TRIM (DMALM_PROGETTO_DEMAND.CODICE),
                                   NVL (
                                      TRIM (DMALM_SOTTOPROGRAMMA.CODICE),
                                      NVL (
                                         TRIM (
                                            DMALM_RICHIESTA_MANUTENZIONE.CODICE),
                                         'ND')))
                                   CODICE_RILASCIO_WI_FIGLIO,
                                V_GERARCHIA_MISTA_LINKED_WI.FK_WI_DOC,
                                V_GERARCHIA_MISTA_LINKED_WI.CODICE_WI_DOC
                                   CODICE_WI_DOCUMENTO,
                                DMALM_DOCUMENTO.TIPO TIPO_WI_DOCUMENTO,
                                DMALM_PROGRAMMA.ID_REPOSITORY REPOSITORY_SGRCM
                           FROM DMALM_PROGETTO_DEMAND,
                                DMALM_RICHIESTA_MANUTENZIONE,
                                DMALM_SOTTOPROGRAMMA,
                                DMALM_DOCUMENTO,
                                (SELECT A.REPOSITORY_padre,
                                        A.TIPO_WI_PADRE,
                                        A.WI_PADRE,
                                        A.FK_WI_PADRE,
                                        A.TIPO_WI_FIGLIO,
                                        A.WI_FIGLIO,
                                        A.FK_WI_FIGLIO,
                                        B.CODICE_WI_FIGLIO CODICE_WI_DOC,
                                        B.TIPO_WI_FIGLIO TIPO_WI_DOC,
                                        B.FK_WI_FIGLIO FK_WI_DOC
                                   FROM (  SELECT repository_padre,
                                                  tipo_wi_padre,
                                                  wi_padre,
                                                  fk_wi_padre,
                                                  wi_figlio,
                                                  MAX (fk_wi_figlio) fk_wi_figlio,
                                                  MAX (tipo_wi_figlio)
                                                     tipo_wi_figlio
                                             FROM (SELECT wi_padre,
                                                          fk_wi_padre,
                                                          tipo_wi_padre,
                                                          repository_padre,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                REGEXP_SUBSTR (
                                                                   path_cod_wi_sup,
                                                                   '[^#]+',
                                                                   1,
                                                                   1),
                                                                '[^*]+',
                                                                1,
                                                                1))
                                                             lev1,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                REGEXP_SUBSTR (
                                                                   path_cod_wi_sup,
                                                                   '[^#]+',
                                                                   1,
                                                                   2),
                                                                '[^*]+',
                                                                1,
                                                                1))
                                                             lev2,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                REGEXP_SUBSTR (
                                                                   path_cod_wi_sup,
                                                                   '[^#]+',
                                                                   1,
                                                                   3),
                                                                '[^*]+',
                                                                1,
                                                                1))
                                                             lev3,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                REGEXP_SUBSTR (
                                                                   path_cod_wi_sup,
                                                                   '[^#]+',
                                                                   1,
                                                                   4),
                                                                '[^*]+',
                                                                1,
                                                                1))
                                                             lev4,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                REGEXP_SUBSTR (
                                                                   path_cod_wi_sup,
                                                                   '[^#]+',
                                                                   1,
                                                                   5),
                                                                '[^*]+',
                                                                1,
                                                                1))
                                                             lev5,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup_fk,
                                                                '[^#]+',
                                                                1,
                                                                1))
                                                             fk_lev1,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup_fk,
                                                                '[^#]+',
                                                                1,
                                                                2))
                                                             fk_lev2,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup_fk,
                                                                '[^#]+',
                                                                1,
                                                                3))
                                                             fk_lev3,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup_fk,
                                                                '[^#]+',
                                                                1,
                                                                4))
                                                             fk_lev4,
                                                          UPPER (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup_fk,
                                                                '[^#]+',
                                                                1,
                                                                5))
                                                             fk_lev5,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                1),
                                                             '[^*]+',
                                                             1,
                                                             2)
                                                             tipo1,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                2),
                                                             '[^*]+',
                                                             1,
                                                             2)
                                                             tipo2,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                3),
                                                             '[^*]+',
                                                             1,
                                                             2)
                                                             tipo3,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                4),
                                                             '[^*]+',
                                                             1,
                                                             2)
                                                             tipo4,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                5),
                                                             '[^*]+',
                                                             1,
                                                             2)
                                                             tipo5,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                1),
                                                             '[^*]+',
                                                             1,
                                                             3)
                                                             repo1,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                2),
                                                             '[^*]+',
                                                             1,
                                                             3)
                                                             repo2,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                3),
                                                             '[^*]+',
                                                             1,
                                                             3)
                                                             repo3,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                4),
                                                             '[^*]+',
                                                             1,
                                                             3)
                                                             repo4,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                5),
                                                             '[^*]+',
                                                             1,
                                                             3)
                                                             repo5,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                1),
                                                             '[^*]+',
                                                             1,
                                                             4)
                                                             proj1,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                2),
                                                             '[^*]+',
                                                             1,
                                                             4)
                                                             proj2,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                3),
                                                             '[^*]+',
                                                             1,
                                                             4)
                                                             proj3,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                4),
                                                             '[^*]+',
                                                             1,
                                                             4)
                                                             proj4,
                                                          REGEXP_SUBSTR (
                                                             REGEXP_SUBSTR (
                                                                path_cod_wi_sup,
                                                                '[^#]+',
                                                                1,
                                                                5),
                                                             '[^*]+',
                                                             1,
                                                             4)
                                                             proj5
                                                     FROM (    SELECT CONNECT_BY_ROOT linked.codice_wi_padre
                                                                         wi_padre,
                                                                      CONNECT_BY_ROOT linked.fk_wi_padre
                                                                         fk_wi_padre,
                                                                      CONNECT_BY_ROOT linked.tipo_wi_padre
                                                                         tipo_wi_padre,
                                                                      CONNECT_BY_ROOT linked.id_repository_padre
                                                                         repository_padre,
                                                                      CONNECT_BY_ROOT linked.codice_project_padre,
                                                                      SYS_CONNECT_BY_PATH (
                                                                         linked.codice_wi_figlio
                                                                         || '*'
                                                                         || linked.tipo_wi_figlio
                                                                         || '*'
                                                                         || linked.id_repository_figlio
                                                                         || '*'
                                                                         || linked.codice_project_figlio,
                                                                         '#')
                                                                         PATH_cod_wi_sup,
                                                                      SYS_CONNECT_BY_PATH (
                                                                         linked.fk_wi_figlio,
                                                                         '#')
                                                                         PATH_cod_wi_sup_fk
                                                                 FROM (SELECT c.*
                                                                         FROM --DMALM_LINKED_WORKITEMS
                                                                             (SELECT a.*,
                                                                                     RANK ()
                                                                                     OVER (
                                                                                        PARTITION BY a.codice_wi_padre
                                                                                                     || id_repository_padre
                                                                                                     || codice_project_padre,
                                                                                                     a.codice_wi_figlio
                                                                                                     || id_repository_figlio
                                                                                                     || codice_project_figlio
                                                                                        ORDER BY
                                                                                           a.linked_workitems_pk DESC)
                                                                                        rank_wi,
                                                                                     codice_wi_padre
                                                                                     || id_repository_padre
                                                                                     || codice_project_padre
                                                                                     || tipo_wi_padre
                                                                                        chiave_padre,
                                                                                     codice_wi_figlio
                                                                                     || id_repository_figlio
                                                                                     || codice_project_figlio
                                                                                     || tipo_wi_figlio
                                                                                        chiave_figlio
                                                                                FROM DMALM_LINKED_WORKITEMS a) c
                                                                        WHERE rank_wi =
                                                                                 1) linked
                                                           CONNECT BY NOCYCLE PRIOR linked.chiave_figlio =
                                                                                 linked.chiave_padre
                                                           START WITH linked.tipo_wi_padre =
                                                                         'programma')) UNPIVOT ((wi_figlio,
                                                                                                 fk_wi_figlio,
                                                                                                 tipo_wi_figlio)
                                                                                       FOR wi
                                                                                       IN  ((lev1,
                                                                                             fk_lev1,
                                                                                             tipo1),
                                                                                           (lev2,
                                                                                            fk_lev2,
                                                                                            tipo2),
                                                                                           (lev3,
                                                                                            fk_lev3,
                                                                                            tipo3),
                                                                                           (lev4,
                                                                                            fk_lev4,
                                                                                            tipo4),
                                                                                           (lev5,
                                                                                            fk_lev5,
                                                                                            tipo5)))
                                            WHERE TIPO_WI_FIGLIO IN
                                                     ('dman',
                                                      'rqd',
                                                      'sottoprogramma')
                                         GROUP BY repository_padre,
                                                  tipo_wi_padre,
                                                  wi_padre,
                                                  fk_wi_padre,
                                                  wi_figlio) A,
                                        (SELECT c.*
                                           FROM       --DMALM_LINKED_WORKITEMS
                                               (SELECT a.*,
                                                       RANK ()
                                                       OVER (
                                                          PARTITION BY a.codice_wi_padre,
                                                                       a.codice_wi_figlio
                                                          ORDER BY
                                                             a.FK_WI_FIGLIO DESC,
                                                             A.FK_WI_PADRE DESC)
                                                          rank_wi
                                                  FROM DMALM_LINKED_WORKITEMS a) c
                                          WHERE rank_wi = 1) B
                                  WHERE A.FK_WI_FIGLIO = B.FK_WI_PADRE(+)
                                        AND B.TIPO_WI_FIGLIO(+) = 'documento') V_GERARCHIA_MISTA_LINKED_WI,
                                DMALM_PROGRAMMA
                          WHERE (DMALM_PROGRAMMA.DMALM_PROGRAMMA_PK =
                                    V_GERARCHIA_MISTA_LINKED_WI.FK_WI_PADRE(+))
                                AND ( (CASE
                                          WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                                  'sottoprogramma'
                                          THEN
                                             V_GERARCHIA_MISTA_LINKED_WI.FK_WI_FIGLIO
                                       END) =
                                        DMALM_SOTTOPROGRAMMA.DMALM_SOTTOPROGRAMMA_PK(+))
                                AND ( (CASE
                                          WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                                  'rqd'
                                          THEN
                                             V_GERARCHIA_MISTA_LINKED_WI.FK_WI_FIGLIO
                                       END) =
                                        DMALM_PROGETTO_DEMAND.DMALM_PROGETTO_DEMAND_PK(+))
                                AND ( (CASE
                                          WHEN V_GERARCHIA_MISTA_LINKED_WI.TIPO_WI_FIGLIO =
                                                  'dman'
                                          THEN
                                             V_GERARCHIA_MISTA_LINKED_WI.FK_WI_FIGLIO
                                       END) =
                                        DMALM_RICHIESTA_MANUTENZIONE.DMALM_RICH_MANUTENZIONE_PK(+))
                                AND (V_GERARCHIA_MISTA_LINKED_WI.FK_WI_DOC =
                                        DMALM_DOCUMENTO.DMALM_DOCUMENTO_PK(+))
                                AND DMALM_PROGRAMMA.RANK_STATO_PROGRAMMA = 1
                                AND DMALM_PROGRAMMA.ANNULLATO IS NULL) sgrcm
                     FULL OUTER JOIN
                        (SELECT contratti.idcontratto,
                                contratti.codcontratto,
                                rilasci.idrilascio,
                                rilasci.codrilascio,
                                verbali.codverbale,
                                verbali.idverbale,
                                attivita.idattivita,
                                attivita.codattivita,
                                REPOSITORY repository_mps
                           FROM mps_bo_contratti contratti,
                                (SELECT *
                                   FROM (SELECT mps_bo_rilasci.*,
                                                RANK ()
                                                OVER (
                                                   PARTITION BY idcontratto,
                                                                codrilascio
                                                   ORDER BY data_rilascio DESC)
                                                   rank_rilascio
                                           FROM mps_bo_rilasci)
                                  WHERE rank_rilascio = 1) rilasci,
                                mps_bo_attivita attivita,
                                (SELECT idrilascio, idverbale, codverbale
                                   FROM (SELECT b.idrilascio,
                                                b.idverbalevalidazione
                                                   idverbale,
                                                c.codverbale,
                                                c.data_verbale,
                                                RANK ()
                                                OVER (
                                                   PARTITION BY b.idrilascio
                                                   ORDER BY c.data_verbale DESC)
                                                   rank_verbale
                                           FROM mps_bo_rilasci_verbali b,
                                                mps_bo_verbali c
                                          WHERE b.IDVERBALEVALIDAZIONE =
                                                   c.IDVERBALEVALIDAZIONE
                                                AND c.IDVERBALEVALIDAZIONE !=
                                                       -1)
                                  WHERE rank_verbale = 1) verbali
                          WHERE contratti.idcontratto = rilasci.idcontratto(+)
                                AND rilasci.idrilascio = attivita.idattivita(+)
                                AND rilasci.idrilascio = verbali.idrilascio(+)) mps
                     ON UPPER (REPLACE (mps.codcontratto, ' ', '')) =
                           UPPER (
                              REPLACE (sgrcm.CODICE_INCARICO_SGRCM, ' ', ''))
                        AND UPPER (REPLACE (mps.codrilascio, ' ', '')) =
                               UPPER (
                                  REPLACE (sgrcm.CODICE_RILASCIO_WI_FIGLIO,
                                           ' ',
                                           ''))
            ORDER BY codice_incarico_sgrcm, codice_rilascio_wi_figlio;

         -- Il campo esito_elaborazione è valorizzato a 1 per Ok a 0 per Ko.
         INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                 data_inizio_elaborazione,
                                                 esito_elaborazione,
                                                 descrizione_elaborazione,
                                                 data_fine_elaborazione)
              VALUES (v_identificativo,
                      v_data_inizio,
                      1,
                      'Insert eseguita con successo.',
                      SYSDATE);

         COMMIT;
      EXCEPTION
         WHEN OTHERS
         THEN
            err_msg := SUBSTR (SQLERRM, 1, 100);

            --                    err_num := SQLCODE;

            INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                    data_inizio_elaborazione,
                                                    esito_elaborazione,
                                                    descrizione_elaborazione,
                                                    data_fine_elaborazione)
                 VALUES (v_identificativo,
                         v_data_inizio,
                         0,
                         err_msg,
                         SYSDATE);

            COMMIT;
      END;                                                            --insert

      BEGIN
         v_data_inizio := SYSDATE;

         /*update valori mps (idcontratto, codcontratto) che non hanno corrispondenze nei rilasci con SGRCM)*/
         UPDATE MPS_BO_CONFRONTO_MPS_SGRCM c
            SET IDCONTRATTO_mps =
                   (SELECT z.idcontratto
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.CODICE, ' ', '')) =
                              UPPER (
                                 REPLACE (c.CODICE_INCARICO_WI_PADRE,
                                          ' ',
                                          ''))
                           AND z.rank_confronto = 1),
                codcontratto_mps =
                   (SELECT z.codcontratto
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.CODICE, ' ', '')) =
                              UPPER (
                                 REPLACE (c.CODICE_INCARICO_WI_PADRE,
                                          ' ',
                                          ''))
                           AND z.rank_confronto = 1),
                repository_mps =
                   (SELECT z.repository_mps
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.CODICE, ' ', '')) =
                              UPPER (
                                 REPLACE (c.CODICE_INCARICO_WI_PADRE,
                                          ' ',
                                          ''))
                           AND z.rank_confronto = 1)
          WHERE c.codcontratto_mps IS NULL
                AND UPPER (REPLACE (c.CODICE_INCARICO_WI_PADRE, ' ', '')) IN
                       (SELECT UPPER (REPLACE (z.codice, ' ', ''))
                          FROM (SELECT /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                      sgrcm.cd_programma,
                                       sgrcm.codice,
                                       sgrcm.id_repository,
                                       SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                       RANK ()
                                       OVER (
                                          PARTITION BY UPPER (
                                                          REPLACE (
                                                             sgrcm.codice,
                                                             ' ',
                                                             ''))
                                          ORDER BY
                                             sgrcm.DMALM_PROGRAMMA_PK DESC)
                                          rank_confronto,
                                       MPS.CODCONTRATTO,
                                       mps.idcontratto,
                                       REPOSITORY repository_mps
                                  FROM    dmalm_programma sgrcm
                                       INNER JOIN
                                          mps_bo_contratti mps
                                       ON UPPER (
                                             REPLACE (sgrcm.codice, ' ', '')) =
                                             UPPER (
                                                REPLACE (mps.codcontratto,
                                                         ' ',
                                                         ''))
                                 WHERE sgrcm.rank_stato_programma = 1
                                       AND sgrcm.annullato IS NULL) z);

         INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                 data_inizio_elaborazione,
                                                 esito_elaborazione,
                                                 descrizione_elaborazione,
                                                 data_fine_elaborazione)
              VALUES (v_identificativo,
                      v_data_inizio,
                      1,
                      'Update MPS not in SGRCM eseguita con successo.',
                      SYSDATE);

         COMMIT;
      EXCEPTION
         WHEN OTHERS
         THEN
            err_msg := SUBSTR (SQLERRM, 1, 100);

            INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                    data_inizio_elaborazione,
                                                    esito_elaborazione,
                                                    descrizione_elaborazione,
                                                    data_fine_elaborazione)
                 VALUES (v_identificativo,
                         v_data_inizio,
                         0,
                         err_msg,
                         SYSDATE);

            COMMIT;
      END;                                                          -- update1

      BEGIN
         v_data_inizio := SYSDATE;

         /*update valori sgrcm (fk_wi_padre, codice_incarico_wi_padre) che non hanno corrispondenze nei rilasci con MPS)*/
         UPDATE MPS_BO_CONFRONTO_MPS_SGRCM c
            SET fk_wi_padre =
                   (SELECT z.fk_wi_padre
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.codcontratto, ' ', '')) =
                              UPPER (REPLACE (c.codcontratto_mps, ' ', ''))
                           AND z.rank_confronto = 1),
                codice_wi_padre =
                   (SELECT z.cd_programma
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.codcontratto, ' ', '')) =
                              UPPER (REPLACE (c.codcontratto_mps, ' ', ''))
                           AND z.rank_confronto = 1),
                codice_incarico_wi_padre =
                   (SELECT z.codice
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.codcontratto, ' ', '')) =
                              UPPER (REPLACE (c.codcontratto_mps, ' ', ''))
                           AND z.rank_confronto = 1),
                repository_sgrcm =
                   (SELECT z.id_repository
                      FROM (SELECT    /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                  sgrcm.cd_programma,
                                   sgrcm.codice,
                                   sgrcm.id_repository,
                                   SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                   RANK ()
                                   OVER (
                                      PARTITION BY UPPER (
                                                      REPLACE (
                                                         sgrcm.codice,
                                                         ' ',
                                                         ''))
                                      ORDER BY
                                         sgrcm.DMALM_PROGRAMMA_PK DESC)
                                      rank_confronto,
                                   MPS.CODCONTRATTO,
                                   mps.idcontratto,
                                   REPOSITORY repository_mps
                              FROM    dmalm_programma sgrcm
                                   INNER JOIN
                                      mps_bo_contratti mps
                                   ON UPPER (
                                         REPLACE (sgrcm.codice, ' ', '')) =
                                         UPPER (
                                            REPLACE (mps.codcontratto,
                                                     ' ',
                                                     ''))
                             WHERE sgrcm.rank_stato_programma = 1
                                   AND sgrcm.annullato IS NULL) z
                     WHERE UPPER (REPLACE (z.codcontratto, ' ', '')) =
                              UPPER (REPLACE (c.codcontratto_mps, ' ', ''))
                           AND z.rank_confronto = 1--group by z.id_repository
                   )
          WHERE c.codice_incarico_wi_padre IS NULL
                AND UPPER (REPLACE (C.CODCONTRATTO_MPS, ' ', '')) IN
                       (SELECT UPPER (REPLACE (z.codcontratto, ' ', ''))
                          FROM (SELECT /*+ index(sgrcm IDX_PROGRAMMA_CODICE) */
                                      sgrcm.cd_programma,
                                       sgrcm.codice,
                                       sgrcm.id_repository,
                                       SGRCM.DMALM_PROGRAMMA_PK fk_wi_padre,
                                       RANK ()
                                       OVER (
                                          PARTITION BY UPPER (
                                                          REPLACE (
                                                             sgrcm.codice,
                                                             ' ',
                                                             ''))
                                          ORDER BY
                                             sgrcm.DMALM_PROGRAMMA_PK DESC)
                                          rank_confronto,
                                       MPS.CODCONTRATTO,
                                       mps.idcontratto,
                                       REPOSITORY repository_mps
                                  FROM    dmalm_programma sgrcm
                                       INNER JOIN
                                          mps_bo_contratti mps
                                       ON UPPER (
                                             REPLACE (sgrcm.codice, ' ', '')) =
                                             UPPER (
                                                REPLACE (mps.codcontratto,
                                                         ' ',
                                                         ''))
                                 WHERE sgrcm.rank_stato_programma = 1
                                       AND sgrcm.annullato IS NULL) z);

         INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                 data_inizio_elaborazione,
                                                 esito_elaborazione,
                                                 descrizione_elaborazione,
                                                 data_fine_elaborazione)
              VALUES (v_identificativo,
                      v_data_inizio,
                      1,
                      'Update SGRCM not in MPS eseguita con successo.',
                      SYSDATE);

         COMMIT;
      EXCEPTION
         WHEN OTHERS
         THEN
            err_msg := SUBSTR (SQLERRM, 1, 100);

            INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                    data_inizio_elaborazione,
                                                    esito_elaborazione,
                                                    descrizione_elaborazione,
                                                    data_fine_elaborazione)
                 VALUES (v_identificativo,
                         v_data_inizio,
                         0,
                         err_msg,
                         SYSDATE);

            COMMIT;
      END update2;

      BEGIN
         SELECT SUM (esito_elaborazione)
           INTO v_controllo_esito
           FROM mps_bo_confronto_sgrcm_log
          WHERE id = v_identificativo;

         IF v_controllo_esito != c_esito_ok
         THEN
            v_esito_proc := 0;
            v_data_inizio := SYSDATE;

            -- Ripristino del backup.
            EXECUTE IMMEDIATE 'truncate table MPS_BO_CONFRONTO_MPS_SGRCM';

            INSERT INTO MPS_BO_CONFRONTO_MPS_SGRCM
               SELECT * FROM MPS_BO_CONFRONTO_MPS_SGRCM_BCK;

            INSERT INTO mps_bo_confronto_sgrcm_log (id,
                                                    data_inizio_elaborazione,
                                                    esito_elaborazione,
                                                    descrizione_elaborazione,
                                                    data_fine_elaborazione)
                 VALUES (v_identificativo,
                         v_data_inizio,
                         -1,
                         'Effettuato il ripristino del backup.',
                         SYSDATE);

            COMMIT;
         END IF;
      END;                                    -- verifica esecuzione procedura
   END;
END load_mps_confronto_sgrcm;
/
SHOW ERRORS;
SPOOL OFF;