insert into DMALM_LINKED_WORKITEMS (CODICE_WI_FIGLIO, CODICE_WI_PADRE, TIPO_WI_FIGLIO, TIPO_WI_PADRE, FK_WI_FIGLIO, FK_WI_PADRE, RUOLO, DT_CARICAMENTO, TITOLO_WI_FIGLIO, TITOLO_WI_PADRE, URI_WI_FIGLIO, URI_WI_PADRE, ID_REPOSITORY_FIGLIO, ID_REPOSITORY_PADRE, CODICE_PROJECT_FIGLIO, CODICE_PROJECT_PADRE, LINKED_WORKITEMS_PK)  
 select ID_FIGLIO, ID_PADRE, TIPO_FIGLIO, TIPO_PADRE, PK_FIGLIO, PK_PADRE, RUOLO, DT_CARICAMENTO, TITOLO_WI_FIGLIO, TITOLO_WI_PADRE, URI_WI_FIGLIO, URI_WI_PADRE, ID_REPOSITORY_FIGLIO, ID_REPOSITORY_PADRE, ID_PROJECT_FIGLIO, ID_PROJECT_PADRE, LINKED_WORKITEMS_SEQ.nextval
    from 
        (
            select distinct
                prjpadre.ID_PROJECT AS ID_PROJECT_PADRE, 
                prjfiglio.ID_PROJECT AS ID_PROJECT_FIGLIO, 
                wipadre.ID_REPOSITORY as ID_REPOSITORY_PADRE, 
                wifiglio.ID_REPOSITORY as ID_REPOSITORY_FIGLIO, 
                wipadre.codice as ID_PADRE,
                wifiglio.codice as ID_FIGLIO,
                wipadre.type as TIPO_PADRE,
                wifiglio.type as TIPO_FIGLIO, 
                l.c_role as RUOLO, 
                wipadre.DMALM_PK as PK_PADRE,
                wifiglio.DMALM_PK as PK_FIGLIO, 
                l.data_caricamento as DT_CARICAMENTO,
                wipadre.titolo as TITOLO_WI_PADRE,
                wifiglio.titolo as TITOLO_WI_FIGLIO, 
                l.FK_URI_P_WORKITEM as URI_WI_PADRE,
                l.FK_URI_WORKITEM as URI_WI_FIGLIO
             from dmalm_sire_current_work_linked l  
             join TOTAL wifiglio on l.FK_P_WORKITEM = wifiglio.uri
             join TOTAL wipadre on l.FK_WORKITEM = wipadre.uri
             join dmalm_project prjfiglio on PRJFIGLIO.DMALM_PROJECT_PK = wifiglio.PROJECT_FK
             join dmalm_project prjpadre on PRJPADRE.DMALM_PROJECT_PK = wipadre.PROJECT_FK
             where wifiglio.RANK_STATO = 1 
             and wipadre.RANK_STATO = 1 
             and wipadre.ID_REPOSITORY = 'SIRE' 
             and wipadre.ID_REPOSITORY = wifiglio.ID_REPOSITORY 
             and l.DATA_CARICAMENTO = ? 
           UNION all
            select distinct
                prjpadre.ID_PROJECT AS ID_PROJECT_PADRE, 
                prjfiglio.ID_PROJECT AS ID_PROJECT_FIGLIO, 
                wipadre.ID_REPOSITORY as ID_REPOSITORY_PADRE, 
                wifiglio.ID_REPOSITORY as ID_REPOSITORY_FIGLIO, 
                wipadre.codice as ID_PADRE,
                wifiglio.codice as ID_FIGLIO,
                wipadre.type as TIPO_PADRE,
                wifiglio.type as TIPO_FIGLIO, 
                l.c_role as RUOLO, 
                wipadre.DMALM_PK as PK_PADRE,
                wifiglio.DMALM_PK as PK_FIGLIO, 
                l.data_caricamento as DT_CARICAMENTO,
                wipadre.titolo as TITOLO_WI_PADRE,
                wifiglio.titolo as TITOLO_WI_FIGLIO, 
                l.FK_URI_P_WORKITEM as URI_WI_PADRE,
                l.FK_URI_WORKITEM as URI_WI_FIGLIO
             from dmalm_siss_current_work_linked l  
             join TOTAL wifiglio on l.FK_P_WORKITEM = wifiglio.uri
             join TOTAL wipadre on l.FK_WORKITEM = wipadre.uri
             join dmalm_project prjfiglio on PRJFIGLIO.DMALM_PROJECT_PK = wifiglio.PROJECT_FK
             join dmalm_project prjpadre on PRJPADRE.DMALM_PROJECT_PK = wipadre.PROJECT_FK
             where wifiglio.RANK_STATO = 1 
             and wipadre.RANK_STATO = 1 
             and wipadre.ID_REPOSITORY = 'SISS' 
             and wipadre.ID_REPOSITORY = wifiglio.ID_REPOSITORY     
             and l.DATA_CARICAMENTO = ? 
             order by ID_PADRE, ID_FIGLIO, PK_PADRE, PK_FIGLIO
        ) 
	