delete from dmalm_siss_history_cf_workitem c where not exists (
	select null from dmalm_siss_history_workitem w where w.c_pk = c.fk_workitem
)