update dmalm_anomalia_prodotto wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_difetto_prodotto wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_documento wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_release_di_progetto wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_manutenzione wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_progetto_sviluppo_svil wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
	update dmalm_task wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
); 
	update dmalm_task_it wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
);
update dmalm_testcase wi set DMALM_AREA_TEMATICA_FK_05 = (
	select DMALM_AREA_TEMATICA_FK_01 from dmalm_project p where p.DMALM_PROJECT_PK = wi.DMALM_PROJECT_FK_02
)