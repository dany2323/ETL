merge into DMALM_CHANGED_WORKITEM dcw using 
				( 
				select * from total where CODICE in 
								( select distinct CODICE from total 
								where ID_REPOSITORY = 'SIRE' group by CODICE having count(distinct (TYPE)) >1
								)
								and ID_REPOSITORY = 'SIRE'
				union all
				select * from total where CODICE in 
								(
								select distinct CODICE from total
								where ID_REPOSITORY = 'SISS' group by CODICE having count(distinct (TYPE)) >1
								) 
								and ID_REPOSITORY = 'SISS'									
				) t 
on ( t.CODICE = dcw.CODICE and t.ID_REPOSITORY = dcw.ID_REPOSITORY and t.TYPE = dcw.TYPE and t.DT_STORICIZZAZIONE=dcw.DT_STORICIZZAZIONE) 
WHEN NOT MATCHED THEN 
INSERT( dcw.STG_PK,dcw.DMALM_PK,dcw.TYPE,dcw.ID_REPOSITORY,dcw.CODICE,dcw.DT_STORICIZZAZIONE) values ( t.STG_PK,t.DMALM_PK,t.TYPE,t.ID_REPOSITORY,t.CODICE,t.DT_STORICIZZAZIONE) 