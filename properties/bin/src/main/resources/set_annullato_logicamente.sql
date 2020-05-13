UPDATE DMALM_PROJECT p SET ANNULLATO = ? , DT_ANNULLAMENTO = ?			
	where p.ID_PROJECT IN 
(
	SELECT DISTINCT ID_PROJECT 
	FROM DMALM_PROJECT pp
	WHERE ID_REPOSITORY = ? 
	AND substr(REGEXP_SUBSTR(pp.PATH_PROJECT,'default:/.+/\.'),1,LENGTH(substr(REGEXP_SUBSTR(pp.PATH_PROJECT,'default:/.+/\.'),1))-2) = ?
) 