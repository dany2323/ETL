delete from DMALM_LOG_DEBUG where DATA_CARICAMENTO <
(select max(trunc(DATA_CARICAMENTO)) - ? from DMALM_LOG_DEBUG)