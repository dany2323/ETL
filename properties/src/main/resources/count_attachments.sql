select sum(n) as cardinalita from (
	select count(SIRE_HISTORY_ATTACHMENT_PK) as n from dmalm_sire_history_attachment
	where DATA_CARICAMENTO = ?
		union all
	select count(SISS_HISTORY_ATTACHMENT_PK) as n from dmalm_siss_history_attachment
	where DATA_CARICAMENTO = ?
)