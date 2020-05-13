select sum(n) as cardinalita from (
	select count(SIRE_HISTORY_WORKITEM_PK) as n from dmalm_sire_history_workitem
	where c_type = ?
	and DATA_CARICAMENTO = ?
		union all
	select count(SISS_HISTORY_WORKITEM_PK) as n from dmalm_siss_history_workitem
	where c_type = ?
	and DATA_CARICAMENTO = ?
)