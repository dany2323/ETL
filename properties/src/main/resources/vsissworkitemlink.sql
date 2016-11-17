INSERT INTO SISS_WORKITEM_LINK (DATACARICAMENTO, URI_WORKITEM, STATO_WORKITEM, TYPE_WORKITEM, URI_LINK, STATO_LINK, TYPE_LINK, ID_LINK, PK_LINK, CCREATED_LINK, WORKITEM_LINK_PK)   
select  
datacaricamento,  
uri_workitem,  
stato_workitem,  
type_workitem,  
uri_link,  
DMALM_SISS_HISTORY_WORKITEM.C_status as stato_link,  
DMALM_SISS_HISTORY_WORKITEM.C_TYPE as type_link,  
DMALM_SISS_HISTORY_WORKITEM.C_ID as id_link,  
DMALM_SISS_HISTORY_WORKITEM.C_PK as pk_link,  
DMALM_SISS_HISTORY_WORKITEM.C_CREATED as ccreated_link,  
WORKITEM_LINK_SEQ.nextval  
from   
(   
SELECT 'SISS' as repo, w.DATA_CARICAMENTO as datacaricamento, w.c_pk as uri_workitem, w.c_status as stato_workitem, w.c_type as type_workitem, a.FK_WORKITEM as uri_link   
FROM DMALM_SISS_HISTORY_WORKITEM w    
                left join DMALM_SISS_HISTORY_WORK_LINKED a on  w.C_PK = a.FK_P_WORKITEM   
where to_char(w.c_type)  ='anomalia'   
)  
a left join DMALM_SISS_HISTORY_WORKITEM on  a.uri_link =  DMALM_SISS_HISTORY_WORKITEM.C_PK 
