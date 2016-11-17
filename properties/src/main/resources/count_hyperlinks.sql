select sum(n) as cardinalita from (
	select count(SIRE_HISTORY_HYPERLINK_PK) as n from DMALM_SIRE_HISTORY_HYPERLINK
	where DATA_CARICAMENTO = ?
		union all
	select count(SISS_HISTORY_HYPERLINK_PK) as n from DMALM_SISS_HISTORY_HYPERLINK
	where DATA_CARICAMENTO = ?
)