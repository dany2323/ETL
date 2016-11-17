 
 select msr.NOME_PROGETTO, msr.DMALM_STG_MISURA_PK,msr.APPLICAZIONE,msr.ID_PROGETTO,msr.NOME_MISURA,msr.ID_ASM,msr.ID_MSR, msr.TIPO_PROGETTO from DMALM_STG_MISURA msr
 	where msr.TIPO_PROGETTO != 'Baseline'  and msr.NOME_PROGETTO not like '%/%' and msr.P_APP_CD_DIREZIONE_DEMAND = 'SIRE' and
 (case when msr.NOME_PROGETTO not like '%#%'  and msr.NOME_PROGETTO not like '%**%'
 then msr.NOME_PROGETTO 
 when msr.NOME_PROGETTO like '%#%' and NOME_PROGETTO like '%**%'
 then TRIM(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\#'),1,LENGTH(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\#'),1))-1))
 when msr.NOME_PROGETTO like '%**%'
 then TRIM(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\*'),1,LENGTH(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\*'),1))-1))
 	end) not in(select distinct wipjs.CD_PROG_SVIL_S from DMALM_PROGETTO_SVILUPPO_SVIL wipjs join DMALM_PROJECT pj on wipjs.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wipjs.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO' 
 		union all 
 		select distinct wiman.CD_MANUTENZIONE from DMALM_MANUTENZIONE wiman join DMALM_PROJECT pj on wiman.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wiman.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO'
 		union all
 		select distinct witest.CD_TESTCASE from DMALM_TESTCASE witest join DMALM_PROJECT pj on witest.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where witest.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO' 
 		union all
 		select distinct witask.CD_TASK from DMALM_TASK witask join DMALM_PROJECT pj on witask.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where witask.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO'
 		union all
 		select distinct widefect.CD_DIFETTO from DMALM_DIFETTO_PRODOTTO widefect join DMALM_PROJECT pj on widefect.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where widefect.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO' 
 		union all
 		select distinct widoc.CD_DOCUMENTO from DMALM_DOCUMENTO widoc join DMALM_PROJECT pj on widoc.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where widoc.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO' 
 		union all
 		select distinct wirelease.CD_RELEASEDIPROG from DMALM_RELEASE_DI_PROGETTO wirelease join DMALM_PROJECT pj on wirelease.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wirelease.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO'
 		union all
 		select distinct wianomalia.CD_ANOMALIA from DMALM_ANOMALIA_PRODOTTO wianomalia join DMALM_PROJECT pj on wianomalia.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wianomalia.ID_REPOSITORY = 'SIRE' and pj.TEMPLATE= 'SVILUPPO'    ) 
 union all
 select msr.NOME_PROGETTO, msr.DMALM_STG_MISURA_PK,msr.APPLICAZIONE,msr.ID_PROGETTO,msr.NOME_MISURA,msr.ID_ASM,msr.ID_MSR, msr.TIPO_PROGETTO from DMALM_STG_MISURA msr
 	where msr.TIPO_PROGETTO != 'Baseline'  and msr.NOME_PROGETTO not like '%/%' and msr.P_APP_CD_DIREZIONE_DEMAND = 'SISS' and
 (case when msr.NOME_PROGETTO not like '%#%'  and msr.NOME_PROGETTO not like '%**%'
 then msr.NOME_PROGETTO 
 when msr.NOME_PROGETTO like '%#%' and NOME_PROGETTO like '%**%'
 then TRIM(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\#'),1,LENGTH(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\#'),1))-1))
 when msr.NOME_PROGETTO like '%**%'
 then TRIM(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\*'),1,LENGTH(substr(regexp_substr(msr.NOME_PROGETTO,'\w+-\w+\*'),1))-1))
 	end) not in(select distinct wipjs.CD_PROG_SVIL_S from DMALM_PROGETTO_SVILUPPO_SVIL wipjs join DMALM_PROJECT pj on wipjs.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wipjs.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO' 
		union all 
		select distinct wiman.CD_MANUTENZIONE from DMALM_MANUTENZIONE wiman join DMALM_PROJECT pj on wiman.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wiman.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO'
		union all
		select distinct witest.CD_TESTCASE from DMALM_TESTCASE witest join DMALM_PROJECT pj on witest.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where witest.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO' 
		union all
		select distinct witask.CD_TASK from DMALM_TASK witask join DMALM_PROJECT pj on witask.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where witask.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO'
		union all
		select distinct widefect.CD_DIFETTO from DMALM_DIFETTO_PRODOTTO widefect join DMALM_PROJECT pj on widefect.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where widefect.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO' 
		union all
		select distinct widoc.CD_DOCUMENTO from DMALM_DOCUMENTO widoc join DMALM_PROJECT pj on widoc.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where widoc.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO' 
		union all
		select distinct wirelease.CD_RELEASEDIPROG from DMALM_RELEASE_DI_PROGETTO wirelease join DMALM_PROJECT pj on wirelease.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wirelease.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO'
		union all
		select distinct wianomalia.CD_ANOMALIA from DMALM_ANOMALIA_PRODOTTO wianomalia join DMALM_PROJECT pj on wianomalia.DMALM_PROJECT_FK_02=pj.DMALM_PROJECT_PK where wianomalia.ID_REPOSITORY = 'SISS' and pj.TEMPLATE= 'SVILUPPO'    )  