select
	c.DMALM_STG_CLASSIFICATORI_PK as DMALM_CLASSIFICATORI_PK,
	CODICE_TIPOLOGIA as TIPO_CLASSIFICATORE,
	CODICE_CLASSIFICATORE as CODICE_CLASSIFICATORE,
	id as ID_ORESTE
 from DMALM_STG_CLASSIFICATORI c
	where
 c.DATA_CARICAMENTO = ? 
 and c.CODICE_CLASSIFICATORE is not null
 and c.CODICE_TIPOLOGIA is not null
