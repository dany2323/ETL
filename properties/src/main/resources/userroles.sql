 SELECT 
	ur.USER_ROLES_PK as DMALM_USER_ROLES_PK,
	ur.ID_PROJECT as ORIGINE,	
	ur.UTENTE as USERID,
	ur.RUOLO as RUOLO,
	ur.REPOSITORY as REPOSITORY, 
	ur.DATA_MODIFICA as DT_MODIFICA
 FROM DMALM_USER_ROLES ur
 	where ur.DATA_CARICAMENTO = ?
	and ur.ID_PROJECT = ?
	and ur.REPOSITORY = ?
	order by ur.REVISION asc