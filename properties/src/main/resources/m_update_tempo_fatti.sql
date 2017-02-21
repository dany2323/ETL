update DMALM_ANOMALIA_PRODOTTO d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_ANOMALIA_ASSISTENZA d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_BUILD d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
);
 update DMALM_CLASSIFICATORE d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_DIFETTO_PRODOTTO d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_RICHIESTA_MANUTENZIONE d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update dmalm_documento d set DMALM_TEMPO_FK_04 = (
	select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_PROGETTO_SVILUPPO_DEM d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_FASE d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_PEI d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_PROGETTO_ESE d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
);
  update DMALM_PROGRAMMA d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_RELEASE_DI_PROGETTO d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_RELEASE_IT d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)			
);
  update DMALM_RELEASE_SERVIZI d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update dmalm_richiesta_gestione d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_PROGETTO_DEMAND d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_MANUTENZIONE d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
);
  update DMALM_SOTTOPROGRAMMA d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
);
  update DMALM_PROGETTO_SVILUPPO_SVIL d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_TASK d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update DMALM_TASK_IT d set DMALM_TEMPO_FK_04 = (
 select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
 update dmalm_testcase d set DMALM_TEMPO_FK_04 = (
	select nvl(DMALM_TEMPO_PK, 0) from dmalm_tempo where DT_OSSERVAZIONE = trunc(d.DT_STORICIZZAZIONE)
); 
