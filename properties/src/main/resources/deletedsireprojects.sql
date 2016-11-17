select distinct ID_PROJECT 
from DMALM_PROJECT where ANNULLATO is null 
and id_repository = 'SIRE' 
and DT_FINE_VALIDITA != ? 
group by ID_PROJECT 
minus  
select c_id   
from DMALM_SIRE_CURRENT_PROJECT where DATA_CARICAMENTO = ?  

