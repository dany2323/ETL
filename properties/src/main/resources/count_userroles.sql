select count(USER_ROLES_PK) as cardinalita from DMALM_USER_ROLES
	where DATA_CARICAMENTO = ? and REPOSITORY = ? 
