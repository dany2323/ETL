SELECT substr(REGEXP_SUBSTR(PATH_PROJECT,'default:/.+/\.'),1,LENGTH(substr(REGEXP_SUBSTR(PATH_PROJECT,'default:/.+/\.'),1))-2) as PATH_PROJECT 
FROM DMALM_PROJECT  
WHERE DT_FINE_VALIDITA = ? 
AND ANNULLATO = 'UNMARKED'
AND ID_REPOSITORY = ? 
