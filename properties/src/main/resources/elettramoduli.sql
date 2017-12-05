select 
 m.DMALM_STG_MODULI_PK,            
 m.ID_MODULO_EDMA,            
 m.ID_MODULO_EDMA_PADRE,            
 m.ID_MODULO,            
 m.TIPO_OGGETTO,            
 m.SIGLA_PRODOTTO,            
 m.SIGLA_SOTTOSISTEMA,            
 m.SIGLA_MODULO,            
 m.NOME,            
 m.DS_MODULO,            
 m.ANNULLATO,            
 m.DT_ANNULLAMENTO,            
 m.RESPONSABILE,            
 m.SOTTOSISTEMA,            
 m.TECNOLOGIE,            
 m.TIPO_MODULO,            
 m.DT_CARICAMENTO,
 m.STATO,
 NVL(CASE WHEN m.SIGLA_PRODOTTO is null
            THEN 0
            ELSE ( SELECT p.DMALM_PRODOTTO_PK FROM DMALM_EL_PRODOTTI_ARCH p where p.SIGLA = m.SIGLA_PRODOTTO and p.DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_PRODOTTI_ARCH mx where mx.SIGLA = m.SIGLA_PRODOTTO) ) 
        END, 0) as PRODOTTO_FK,
NVL(CASE WHEN m.RESPONSABILE is null
            THEN 0
            ELSE ( SELECT p.DMALM_PERSONALE_PK FROM DMALM_EL_PERSONALE p where p.CD_PERSONALE = replace(replace(REGEXP_SUBSTR(m.RESPONSABILE, '{[a-zA-Z0-9_.*!?-]+}'),'{',''),'}','') and p.DT_FINE_VALIDITA = (SELECT MAX(DT_FINE_VALIDITA) FROM DMALM_EL_PERSONALE mx where mx.CD_PERSONALE = replace(replace(REGEXP_SUBSTR(m.RESPONSABILE, '{[a-zA-Z0-9_.*!?-]+}'),'{',''),'}','') ) ) 
        END, 0) as PERSONALE_FK 
from DMALM_STG_EL_MODULI m 
where m.DT_CARICAMENTO = ? 
