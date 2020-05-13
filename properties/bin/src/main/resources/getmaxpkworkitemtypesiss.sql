SELECT hw.siss_history_workitem_pk 
FROM DMALM_SISS_HISTORY_WORKITEM hw 
JOIN (SELECT c_type, max(c_rev) AS max_c_rev 
FROM DMALM_SISS_HISTORY_WORKITEM 
GROUP BY c_type) mx ON mx.c_type = hw.c_type 
AND mx.max_c_rev = hw.c_rev 