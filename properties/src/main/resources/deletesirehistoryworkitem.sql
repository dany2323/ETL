DELETE
FROM DMALM_SIRE_HISTORY_WORKITEM
WHERE (data_caricamento <= ?
       AND sire_history_workitem_pk NOT in
         ( SELECT hw.sire_history_workitem_pk
          FROM DMALM_SIRE_HISTORY_WORKITEM hw
          JOIN
            (SELECT c_type, max(c_rev) AS max_c_rev
             FROM DMALM_SIRE_HISTORY_WORKITEM
             GROUP BY c_type) mx ON mx.c_type = hw.c_type
          AND mx.max_c_rev = hw.c_rev)
       AND C_STATUS NOT in('chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito')
       AND NOT c_type = 'defect')
  OR data_caricamento = ?