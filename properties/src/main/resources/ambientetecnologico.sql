		SELECT
			a.ID_AMB_TECNOLOGICO_PK as DMALM_AMBIENTE_TECNOLOGICO_SEQ,
			NVL(
			CASE
				WHEN p.ID_EDMA_PRODOTTO is null
					THEN 0
				ELSE p.ID_EDMA_PRODOTTO
			END, 0) as ID_EDMA_PADRE_PRODOTTO,
			NVL(
			CASE
				WHEN p.ID_EDMA_PRODOTTO is null
					THEN ( SELECT m.ID_EDMA_MODULO FROM DMALM_STG_MODULI m where m.ID_EDMA_MODULO = a.ID_EDMA_PADRE and a.DATA_CARICAMENTO = m.DATA_CARICAMENTO )  
				ELSE 0
			END, 0) as ID_EDMA_PADRE_MODULO,
			a.ID_EDMA as ID_EDMA,
			a.ID_AMBIENTE_TECNOLOGICO as ID_AMBIENTE_TECNOLOGICO,
			a.SIGLA_PRODOTTO as SIGLA_PRODOTTO,
			null as TIPO_OGGETTO,
			a.SIGLA_MODULO as SIGLA_MODULO,
			a.NOME_AMBIENTE_TECNOLOGICO as NOME,
			a.DESCR_AMBIENTE_TECNOLOGICO as DS_AMBIENTE_TECNOLOGICO,
			a.VERSIONE_SO as VERSIONE_SO,
			a.CLASF_ARCHI_RIFERIMENTO as ARCHITETTURA,
			a.CLASF_INFRASTRUTTURE as INFRASTRUTTURA,
			a.CLASF_SO as SISTEMA_OPERATIVO,
			a.CLASF_TIPI_LAYER as TIPO_LAYER, 
			CASE
				WHEN INSTR(a.NOME_AMBIENTE_TECNOLOGICO, 'ANNULLATO') > 0 THEN 'SI'
				ELSE 'NO'
			END AS ANNULLATO,
			CASE
				WHEN
					LENGTH(REGEXP_SUBSTR(a.NOME_AMBIENTE_TECNOLOGICO, '##[a-zA-Z0-9_.*!?-]+#'))=11 and INSTR(a.NOME_AMBIENTE_TECNOLOGICO, 'ANNULLATO') > 0
				THEN
					TO_TIMESTAMP(replace(replace(REGEXP_SUBSTR(a.NOME_AMBIENTE_TECNOLOGICO, '##[a-zA-Z0-9_.*!?-]+#'),'##',''),'#',''),'yyyymmdd')
				ELSE
					{ts '9999-12-31 00:00:00'}
			END AS DATA_ANNULLAMENTO
	from
		DMALM_STG_AMBIENTE_TECNOLOGICO a left join DMALM_STG_PROD_ARCHITETTURE p on a.ID_EDMA_PADRE = p.ID_EDMA_PRODOTTO and a.DATA_CARICAMENTO = p.DATA_CARICAMENTO
	where a.DATA_CARICAMENTO  = ?