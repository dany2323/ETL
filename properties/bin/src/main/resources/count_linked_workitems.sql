select sum(n) as cardinalita from (
	select count(SIRE_HISTORY_WORK_LINKED_PK) as n from DMALM_SIRE_HISTORY_WORK_LINKED
	where DATA_CARICAMENTO = ?
		union all
	select count(SISS_HISTORY_WORK_LINKED_PK) as n from DMALM_SISS_HISTORY_WORK_LINKED
	where DATA_CARICAMENTO = ?
)