package lispa.schedulers.constant;


import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;

public final class DmAlmConstants {

	public static final String POLARION_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_POLARION);
	public static final String DMALM_STAGING_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_DATAMART_STAGING);
	public static final String DMALM_TARGET_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_DATAMART_TARGET);
	public static final String FONTE_ELETTRA_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_FONTE_ELETTRA);

	public static final String GLOBAL = "GLOBAL";
	public static final String SVILUPPO = "SVILUPPO";
	public static final String DEMAND = "DEMAND";
	public static final String DEMAND2016 = "DEMAND2016";
	public static final String IT = "IT";
	public static final String ASSISTENZA = "ASSISTENZA";
	public static final String SERDEP = "SERDEP";
	
	public static final String NON_PRESENTE = "Non presente";	
	

	public static final String CARICAMENTO_IN_ATTESA = "In attesa di caricamento";
	public static final String CARICAMENTO_TERMINATO_CORRETTAMENTE = "Caricamento terminato correttamente";
	public static final String CARICAMENTO_TERMINATO_CON_ERRORE = "Caricamento terminato con errore";

	public static final String DATE_FORMAT_EDMA_UO = "dd/MM/yyyy";
	public static final String DATE_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SFERA_FILENAME = "yyyy_MM_dd";
	public static final String TIMESTAMP_SFERA_FILENAME = "yyyy_MM_dd_HH_mm_ss";
	public static final int MAX_VARCHAR2_LENGHT = 4000;
	public static final int BATCH_SIZE = 1000;

	public static final String REPOSITORY_SIRE = "SIRE";
	public static final String REPOSITORY_SISS = "SISS";
	public static final String SCHEMA_CURRENT = "CURRENT";
	public static final String SCHEMA_HISTORY = "HISTORY";

	// giorno di "default delete" letto solo se il jar non riesce lettura da
	// properties
	public static final int DEFAULT_DAY_DELETE = 15;

	public static final String WORKITEM_TYPE_ANOMALIA = "anomalia";
	public static final String WORKITEM_TYPE_DIFETTO = "defect";
	public static final String WORKITEM_TYPE_ANOMALIAASSISTENZA = "anomalia_assistenza";
	public static final String WORKITEM_TYPE_PROGRAMMA = "programma";
	public static final String WORKITEM_TYPE_SOTTOPROGRAMMA = "sottoprogramma";
	public static final String WORKITEM_TYPE_PROGETTODEMAND = "rqd";
	public static final String WORKITEM_TYPE_PROGETTOSVILUPPO_DEMAND = "drqs";
	public static final String WORKITEM_TYPE_RICHIESTAMANUTENZIONE = "dman";
	public static final String WORKITEM_TYPE_DOCUMENTO = "documento";
	public static final String WORKITEM_TYPE_FASE = "fase";
	public static final String WORKITEM_TYPE_PROGETTOESE = "progettoese";
	public static final String WORKITEM_TYPE_PEI = "pei";
	public static final String WORKITEM_TYPE_PROGETTOSVILUPPO_SVILUPPO = "srqs";
	public static final String WORKITEM_TYPE_MANUTENZIONE = "sman";
	public static final String WORKITEM_TYPE_TESTCASE = "testcase";
	public static final String WORKITEM_TYPE_TASK = "task";
	public static final String WORKITEM_TYPE_RELEASEPROGETTO = "release";
	public static final String WORKITEM_TYPE_RELEASEIT = "release_it";
	public static final String WORKITEM_TYPE_BUILD = "build";
	public static final String WORKITEM_TYPE_TASKIT = "taskit";
	public static final String WORKITEM_TYPE_RICHIESTAGESTIONE = "richiesta_gestione";
	public static final String WORKITEM_TYPE_REALEASESERVIZI = "release_ser";
	public static final String WORKITEM_TYPE_CLASSIFICATORE_DEMAND = "classificatore_demand";
	public static final String WORKITEM_TYPE_CLASSIFICATORE = "classificatore";


	// CF appiattiti sulla tabella Workitem
	public static final String CF_WORKITEM_CNAME_COSTOSVILUPPO = "costosviluppo";
	public static final String CF_WORKITEM_CNAME_CODINTERVENTO = "cod_intervento";
	public static final String CF_WORKITEM_CNAME_TICKETID = "ticketid";
	public static final String CF_WORKITEM_CNAME_CA = "ca";
	public static final String CF_WORKITEM_CNAME_STCHIUSO = "st_chiuso";
	public static final String CF_WORKITEM_CNAME_VERSIONE = "versione";
	public static final String CF_WORKITEM_CNAME_DATA_INIZIO = "data_inizio";
	public static final String CF_WORKITEM_CNAME_DATA_DISP = "data_disp";
	public static final String CF_WORKITEM_CNAME_DATA_INIZIO_EFF = "data_inizio_eff";
	public static final String CF_WORKITEM_CNAME_AOID = "aoid";
	public static final String CF_WORKITEM_CNAME_CODICE = "codice";
	public static final String CF_WORKITEM_CNAME_CLASSE_DOC = "classe_doc";
	public static final String CF_WORKITEM_CNAME_TIPO_DOC = "tipo_doc";
	public static final String CF_WORKITEM_CNAME_FR = "fr";
	public static final String CF_WORKITEM_CNAME_FORNITURA = "fornitura";
	public static final String CF_WORKITEM_CNAME_DATA_DISPOK = "data_dispok";
	public static final String CF_WORKITEM_CNAME_DATA_FINE = "data_fine";
	public static final String CF_WORKITEM_CNAME_DATA_FINE_EFF = "data_fine_eff";
	public static final String CF_WORKITEM_CNAME_VERSION = "version";

	// UPDATE CF QUERIES SIRE
	public static final String UPDATE_CF_COSTOSVILUPPO_SIRE = "update_cf_costosviluppo_sire.sql";
	public static final String UPDATE_CF_CODINTERVENTO_SIRE = "update_cf_cod_intervento_sire.sql";
	public static final String UPDATE_CF_TICKETID_SIRE = "update_cf_ticketid_sire.sql";
	public static final String UPDATE_CF_CA_SIRE = "update_cf_ca_sire.sql";
	public static final String UPDATE_CF_STCHIUSO_SIRE = "update_cf_st_chiuso_sire.sql";
	public static final String UPDATE_CF_VERSIONE_SIRE = "update_cf_versione_sire.sql";
	public static final String UPDATE_CF_DATA_INIZIO_SIRE = "update_cf_data_inizio_sire.sql";
	public static final String UPDATE_CF_DATA_DISP_SIRE = "update_cf_data_disp_sire.sql";
	public static final String UPDATE_CF_DATA_INIZIO_EFF_SIRE = "update_cf_data_inizio_eff_sire.sql";
	public static final String UPDATE_CF_AOID_SIRE = "update_cf_aoid_sire.sql";
	public static final String UPDATE_CF_CODICE_SIRE = "update_cf_codice_sire.sql";
	public static final String UPDATE_CF_CLASSE_DOC_SIRE = "update_cf_classe_doc_sire.sql";
	public static final String UPDATE_CF_TIPO_DOC_SIRE = "update_cf_tipo_doc_sire.sql";
	public static final String UPDATE_CF_FR_SIRE = "update_cf_fr_sire.sql";
	public static final String UPDATE_CF_FORNITURA_SIRE = "update_cf_fornitura_sire.sql";
	public static final String UPDATE_CF_DATA_DISPONIBILITA_EFF_SIRE = "update_cf_data_dispok_sire.sql";
	public static final String UPDATE_CF_BY_QUERY_SIRE = "update_cf_on_workitem_sire.sql";
	public static final String UPDATE_CF_DATA_FINE_SIRE = "update_cf_data_fine_sire.sql";
	public static final String UPDATE_CF_DATA_FINE_EFF_SIRE = "update_cf_data_fine_eff_sire.sql";
	public static final String UPDATE_CF_VERSION_SIRE = "update_cf_version_sire.sql";

	// UPDATE CF QUERIES SISS	
	public static final String UPDATE_CF_COSTOSVILUPPO_SISS = "update_cf_costosviluppo_siss.sql";
	public static final String UPDATE_CF_CODINTERVENTO_SISS = "update_cf_cod_intervento_siss.sql";
	public static final String UPDATE_CF_TICKETID_SISS = "update_cf_ticketid_siss.sql";
	public static final String UPDATE_CF_CA_SISS = "update_cf_ca_siss.sql";
	public static final String UPDATE_CF_STCHIUSO_SISS = "update_cf_st_chiuso_siss.sql";
	public static final String UPDATE_CF_VERSIONE_SISS = "update_cf_versione_siss.sql";
	public static final String UPDATE_CF_DATA_INIZIO_SISS = "update_cf_data_inizio_siss.sql";
	public static final String UPDATE_CF_DATA_DISP_SISS = "update_cf_data_disp_siss.sql";
	public static final String UPDATE_CF_DATA_INIZIO_EFF_SISS = "update_cf_data_inizio_eff_siss.sql";
	public static final String UPDATE_CF_AOID_SISS = "update_cf_aoid_siss.sql";
	public static final String UPDATE_CF_CODICE_SISS = "update_cf_codice_siss.sql";
	public static final String UPDATE_CF_CLASSE_DOC_SISS = "update_cf_classe_doc_siss.sql";
	public static final String UPDATE_CF_TIPO_DOC_SISS = "update_cf_tipo_doc_siss.sql";
	public static final String UPDATE_CF_FR_SISS = "update_cf_fr_siss.sql";
	public static final String UPDATE_CF_FORNITURA_SISS = "update_cf_fornitura_siss.sql";
	public static final String UPDATE_CF_DATA_DISPONIBILITA_EFF_SISS = "update_cf_data_dispok_siss.sql";
	public static final String UPDATE_CF_DATA_FINE_SISS = "update_cf_data_fine_siss.sql";
	public static final String UPDATE_CF_BY_QUERY_SISS = "update_cf_on_workitem_siss.sql";
	public static final String UPDATE_CF_DATA_FINE_EFF_SISS = "update_cf_data_fine_eff_siss.sql";
	public static final String UPDATE_CF_VERSION_SISS = "update_cf_version_siss.sql";

	//update stato changed 
	public static final String GET_CHANGED_WI_SIRE = "get_changed_SIRE.sql";
	public static final String GET_CHANGED_WI_SISS = "get_changed_SISS.sql";
	public static final String DMALM_CHANGED_WORKITEM = "dmalm_changed_workitem.sql";
	public static final String CHANGED_FIELD = "C_TYPE";
	
	public static final String SET_ANNULLATO_LOGICAMENTE = "set_annullato_logicamente.sql";
	public static final String SET_ANNULLATO_FISICAMENTE = "set_annullato_fisicamente.sql";
	
	//count delle tabelle staging, per inizializzare capacity ArrayList
	public static final String COUNT_WORKITEM_BY_TYPE = "count_workitem_by_type.sql";
	public static final String COUNT_ATTACHMENTS = "count_attachments.sql";
	public static final String COUNT_HYPERLINKS = "count_hyperlinks.sql";
	public static final String COUNT_LINKED_WORKITEMS = "count_linked_workitems.sql";
	
	
	//cancellazione dei CF che non trovano corrispondenza in nessun workitem
	public static final String DELETE_NOT_MATCHING_CFS_SIRE = "delete_not_matching_cfs_sire.sql";
	public static final String DELETE_NOT_MATCHING_CFS_SISS = "delete_not_matching_cfs_siss.sql";
	
	// Query gestione tabelle Dummy 
	public static final String SQL_DELETE_ANOMALIA_DIFETTO_DUMMY = "deleteanomaliadifettodummy.sql";
	public static final String SQL_RESET_ULTIMA_VERSIONE_DUMMY = "resetultimaversionedummy.sql";
	public static final String SQL_UPDATE_ULTIMA_VERSIONE_ANOMALIA_DUMMY = "updateultimaversioneanomaliadummy.sql";
	public static final String SQL_UPDATE_ULTIMA_VERSIONE_DIFETTO_DUMMY = "updateultimaversionedifettodummy.sql";
	public static final String SQL_UPDATE_ULTIMA_VERSIONE_ANOMALIA = "updateultimaversioneanomalia.sql";
	public static final String SQL_UPDATE_ULTIMA_VERSIONE_DIFETTO = "updateultimaversionedifetto.sql";
	
	// Query relazione Project/Prodotto
	public static final String SQL_CLOSE_PROJECT_PRODOTTO = "closeprojectprodotto.sql";
	public static final String SQL_CLOSE_PROJECT_PRODOTTI_ARCHITETTURE = "closeprojectprodottiarchitetture.sql";
	
	//Query per procedura di ripristino: attenzione questi file contengono istruzioni sql multiple
	public static final String BACKUP_TARGET = "backup_target.sql";
	public static final String BACKUP_TARGET_WITH_PROCEDURE = "backup_target_with_procedure.sql";
	public static final String BACKUP_MPS_TARGET = "backup_mps_target.sql";
	public static final String TRUNCATE_BACKUP_TABLES = "truncate_backup_tables.sql";
	public static final String TRUNCATE_BACKUP_MPS_TABLES = "truncate_backup_mps_tables.sql";
	public static final String TRUNCATE_MPS_TABLES = "truncate_mps_tables.sql";
	public static final String DELETE_TARGET_TABLES = "delete_target_tables.sql";
	public static final String RECOVER_TARGET = "recover_target.sql";
	public static final String DELETE_TARGET_MPS_TABLES = "delete_target_mps_tables.sql";
	public static final String RECOVER_MPS_TARGET = "recover_mps_target.sql";
	
	//File .sql con query multiple. vanno eseguiti splittandoli con il separatore ';'
	public static final String M_UPDATE_RANK_FATTI = "m_updateranksinmonth.sql";
	public static final String M_UPDATE_UO_FATTI = "m_update_uo_fatti.sql";
	public static final String M_UPDATE_AT_FATTI = "m_update_at_fatti.sql";
	public static final String M_UPDATE_TEMPO_FATTI = "m_update_tempo_fatti.sql";
	public static final String M_UPDATE_ANNULLAMENTO_FATTI = "m_update_annullamento_fatti.sql";
	public static final String M_UPDATE_PROJECT_UOFLATFK = "m_update_project_uoflatfk.sql";
	public static final String M_UPDATE_EL_PROD_ARCH_UOFLATFK = "m_update_el_prod_arch_uoflatfk.sql";
	public static final String M_UPDATE_ASM_UOFLATFK = "m_update_asm_uoflatfk.sql";
	public static final String M_UPDATE_PERSONALE_UOFLATFK = "m_update_personale_uoflatfk.sql";
	public static final String M_SEPARATOR = ";";
	
	//File .sql cancellazione associazioni non più valide tabella dmalm_user_roles_sgr
	public static final String DELETE_OLD_USER_ROLES = "delete_old_user_roles.sql";
	
	public static final String ANNULLATO_LOGICAMENTE = "#ANNULLATO LOGICAMENTE##";
	public static final String ANNULLATO_FISICAMENTE = "#ANNULLATO FISICAMENTE##";
	public static final String UO_NON_TROVATA = "Non trovata";
	public static final String UO_SUFFIX = "LI";
	public static final String UO_IT = "LIA362";
	
	//LINK ASM-PRODOTTO E ASM-PRODOTTI_ARCH
	
	public static final String CLOSE_PRODOTTI_ARCH = "close_asm_prodotti_arch.sql";
	public static final String CLOSE_PRODOTTO = "close_asm_prodotto.sql";

	// FLAG ERRORE
	public static final String FLAG_ERRORE_SCARTATO = "NO";
	public static final String FLAG_ERRORE_NON_BLOCCANTE = "SI";

	// FONTI
	public static final String FONTE_ORESTE_CLASSIFICATORI = "DMALM_STG_CLASSIFICATORI";
	public static final String FONTE_ORESTE_PRODOTTO = "DMALM_STG_PROD_ARCHITETTURE";
	public static final String FONTE_ORESTE_SOTTOSISTEMA = "DMALM_STG_SOTTOSISTEMI";
	public static final String FONTE_ORESTE_MODULO = "DMALM_STG_MODULI";
	public static final String FONTE_ORESTE_FUNZIONALITA = "DMALM_STG_FUNZIONALITA";
	public static final String FONTE_ORESTE_AMBIENTETECNOLOGICO = "DMALM_STG_AMBIENTE_TECNOLOGICO";

	public static final String FONTE_ELETTRA_CLASSIFICATORI = "DMALM_STG_EL_CLASSIFICATORI";
	public static final String FONTE_ELETTRA_PRODOTTIARCHITETTURE = "DMALM_STG_EL_PRODOTTI_ARCH";
	public static final String FONTE_ELETTRA_AMBIENTETECNOLOGICOCLASSIFICATORI = "DMALM_STG_EL_AMBTECN_CLASSIF";
	public static final String FONTE_ELETTRA_MODULI = "DMALM_STG_EL_MODULI";
	public static final String FONTE_ELETTRA_FUNZIONALITA = "DMALM_STG_EL_FUNZIONALITA";
	public static final String FONTE_ELETTRA_AMBIENTETECNOLOGICO = "DMALM_STG_EL_AMBIENTE_TECNO";
	public static final String FONTE_ELETTRA_UNITAORGANIZZATIVE = "DMALM_STG_EL_UNITA_ORGANIZZ";
	public static final String FONTE_ELETTRA_PERSONALE = "DMALM_STG_EL_PERSONALE";
	
	public static final String FONTE_SGR_SIRE_CURRENT_PROJECT = "DMALM_SIRE_CURRENT_PROJECT";
	public static final String FONTE_SGR_SISS_CURRENT_PROJECT = "DMALM_SISS_CURRENT_PROJECT";
	public static final String FONTE_SGR_SIRE_HISTORY_PROJECT = "DMALM_SIRE_HISTORY_PROJECT";
	public static final String FONTE_SGR_SISS_HISTORY_PROJECT = "DMALM_SISS_HISTORY_PROJECT";
	public static final String FONTE_SGR_CURRENT_WORKITEMS = "DMALM_STG_CURRENT_WORKITEMS";
	public static final String FONTE_SGR_SIRE_HISTORY_WORKITEM = "DMALM_SIRE_HISTORY_WORKITEM";
	public static final String FONTE_SGR_SISS_HISTORY_WORKITEM = "DMALM_SISS_HISTORY_WORKITEM";
	
	// TARGET
	public static final String TARGET_ORESTE_CLASSIFICATORI = "DMALM_CLASSIFICATORI";
	public static final String TARGET_ORESTE_PRODOTTO = "DMALM_PRODOTTO";
	public static final String TARGET_ORESTE_SOTTOSISTEMA = "DMALM_SOTTOSISTEMA";
	public static final String TARGET_ORESTE_MODULO = "DMALM_MODULO";
	public static final String TARGET_ORESTE_FUNZIONALITA = "DMALM_FUNZIONALITA";
	public static final String TARGET_ORESTE_AMBIENTETECNOLOGICO = "DMALM_AMBIENTE_TECNOLOGICO";

	public static final String TARGET_EDMA_UNITAORGANIZZATIVA = "DMALM_UNITA_ORGANIZZATIVA";
	public static final String TARGET_EDMA_PERSONALE = "DMALM_PERSONALE";

	public static final String TARGET_ELETTRA_CLASSIFICATORI = "DMALM_EL_CLASSIFICATORI";
	public static final String TARGET_ELETTRA_PRODOTTIARCHITETTURE = "DMALM_EL_PRODOTTI_ARCH";
	public static final String TARGET_ELETTRA_AMBIENTETECNOLOGICOCLASSIFICATORI = "DMALM_EL_AMBTECN_CLASSIF";
	public static final String TARGET_ELETTRA_MODULI = "DMALM_EL_MODULI";
	public static final String TARGET_ELETTRA_FUNZIONALITA = "DMALM_EL_FUNZIONALITA";
	public static final String TARGET_ELETTRA_AMBIENTETECNOLOGICO = "DMALM_EL_AMBIENTE_TECNOLOGICO";
	public static final String TARGET_ELETTRA_UNITAORGANIZZATIVE = "DMALM_EL_UNITA_ORGANIZZATIVE";
	public static final String TARGET_ELETTRA_PERSONALE = "DMALM_EL_PERSONALE";
	
	public static final String TARGET_CALIPSO_SCHEDA_SERVIZIO = "DMALM_CALIPSO_SCHEDA_SERVIZIO";
	public static final String TARGET_CALIPSO_SCHEDA_SERVIZIO_STOR = "DMALM_CALIPSO_SCHED_SERVZ_STOR";
	
	public static final String TARGET_BUILD = "DMALM_BUILD";
	public static final String TARGET_ANOMALIA = "DMALM_ANOMALIA_PRODOTTO";
	public static final String TARGET_DIFETTO = "DMALM_DIFETTO_PRODOTTO";
	public static final String TARGET_DOCUMENTO = "DMALM_DOCUMENTO";
	public static final String TARGET_PROGETTO_SVILUPPO_SVILUPPO = "DMALM_PROGETTO_SVILUPPO_SVIL";
	public static final String TARGET_SOTTOPROGRAMMA = "DMALM_SOTTOPROGRAMMA";
	public static final String TARGET_MANUTENZIONE = "DMALM_MANUTENZIONE";
	public static final String TARGET_PROGRAMMA = "DMALM_PROGRAMMA";
	public static final String TARGET_RICHIESTA_MANUTENZIONE = "DMALM_RICHIESTA_MANUTENZIONE";
	public static final String TARGET_FASE = "DMALM_FASE";
	public static final String TARGET_PROGETTO_DEMAND = "DMALM_PROGETTO_DEMAND";
	public static final String TARGET_PROGETTO_SVILUPPO_DEMAND = "DMALM_PROGETTO_SVILUPPO_DEMAND";
	public static final String TARGET_PROGETTO_ESE = "DMALM_PROGETTO_ESE";
	public static final String TARGET_TASK = "DMALM_TASK";
	public static final String TARGET_RELEASE_IT = "DMALM_RELEASE_IT";
	public static final String TARGET_TESTCASE = "DMALM_TESTCASE";
	public static final String TARGET_RELEASE_DI_PROG = "DMALM_RELEASE_DI_PROGETTO";
	public static final String TARGET_RICHIESTA_GESTIONE = "DMALM_RICHIESTA_GESTIONE";
	public static final String TARGET_TASKIT = "DMALM_TASK_IT";
	public static final String TARGET_ANOMALIA_ASSISTENZA = "DMALM_ANOMALIA_ASSISTENZA";
	public static final String TARGET_RELEASE_SERVIZI = "DMALM_RELEASE_SERVIZI";
	public static final String TARGET_PEI = "DMALM_PEI";
	public static final String TARGET_CLASSIFICATORE = "DMALM_CLASSIFICATORE";
	public static final String TARGET_RICHIESTA_SUPPORTO = "DMALM_RICHIESTA_SUPPORTO";

	public static final String TARGET_AREATEMATICA = "DMALM_AREA_TEMATICA";
	public static final String TARGET_STATOWORKITEM = "DMALM_STATO_WORKITEM";
	public static final String TARGET_PROJECTROLES = "DMALM_PROJECT_ROLES_SGR";
	public static final String TARGET_USERROLES = "DMALM_USER_ROLES_SGR";
	public static final String TARGET_USER = "DMALM_USER";
	public static final String TARGET_USER_EL_PERSONALE = "DMALM_USER_EL_PERSONALE";
	public static final String TARGET_SCHEDE_SERVIZIO = "DMALM_SCHEDE_SERVIZIO";

	public static final String TARGET_SGR_SIRE_CURRENT_PROJECT = "DMALM_PROJECT";
	public static final String TARGET_SGR_SISS_CURRENT_PROJECT = "DMALM_PROJECT";
	public static final String TARGET_DMALM_PROJECT_PRODOTTO = "DMALM_PROJECT_PRODOTTO";
	public static final String TARGET_DMALM_PROJECT_PRODOTTIARCHITETTURE = "DMALM_PROJECT_PRODOTTI_ARCH";

	public static final String TARGET_LINKEDWORKITEMS = "DMALM_LINKED_WORKITEMS";
	public static final String TARGET_ATTACHMENT = "DMALM_ATTACHMENT";
	public static final String TARGET_HYPERLINK = "DMALM_HYPERLINK";
	
	public static final String TARGET_WORKITEMASSIGNEES = "DMALM_WORKITEM_ASSIGNEES";
	public static final String TARGET_REL_CLASSIFICATORI = "DMALM_REL_CLASSIFICATORI_ORESTE";
	
	public static final String TARGET_FILIERA_PRODUTTIVA = "DMALM_FILIERA_PRODUTTIVA";

	public static final String TARGET_ALL = "ALL";
	

	// MESSAGGI ERRORE
	public static final String ERROR_CARICAMENTO_BACKUP = "Errore caricamento tabelle di backup. Rollback non effettuato!";
	// CLASSIFICATORI
	public static final String CLASSIFICATORE_CODICE_DUPLICATO = "Codice Classificatore duplicato";
	public static final String CLASSIFICATORE_CODICE_NULL = "Codice Classificatore null";

	//QUERY ESTRAZIONE DATI PER ANNULLAMENTI ELETTRA
	public static final String ELETTRA_ANN_UNITA_ORGANIZZATIVE = "getannullamenti_el_unita_org.sql";
	public static final String ELETTRA_ANN_AMBTEC_CLASSIF = "getannullamenti_el_ambtec_classif.sql";
	public static final String ELETTRA_ANN_PERSONALE = "getannullamenti_el_personale.sql";
	public static final String ELETTRA_ANN_FUNZIONALITA = "getannullamenti_el_funzionalita.sql";
	public static final String ELETTRA_ANN_FUNZIONALITA_LOGICAMENTE = "getannullamenti_logicamente_el_funzionalita.sql";
	public static final String ELETTRA_ANN_MODULO = "getannullamenti_el_modulo.sql";
	public static final String ELETTRA_ANN_MODULO_LOGICAMENTE = "getannullamenti_logicamente_el_modulo.sql";
	public static final String ELETTRA_ANN_PRODOTTO = "getannullamenti_el_prodotto.sql";
	public static final String ELETTRA_ANN_PRODOTTO_FISICAMENTE= "getannullamenti_el_prodotto_fisicamente.sql";
	public static final String ELETTRA_ANN_PRODOTTO_LOGICAMENTE="getannullamenti_el_prodotto_logico.sql";
	// MESSAGGI ERRORE
	// PRODOTTO
	
	
	public static final String UO_NOT_VALID="ELE001 - Il codice dell'unità organizzativa non è valido";
	public static final String PRODOTTO_SIGLA_DUPLICATA = "Sigla Prodotto duplicata";
	public static final String PRODOTTO_SIGLA_NULL = "Sigla Prodotto is null";
	public static final String PRODOTTO_NOME_DUPLICATO = "Nome Prodotto duplicato";
	public static final String PRODOTTO_NOME_NULL = "Nome Prodotto is null";
	public static final String PRODOTTO_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita non è racchiusa fra ##";
	public static final String PRODOTTO_DATAFINEVALIDITA_ERRORE_FORMATO = "Annullamento non rispetta il formato nel campo NOME ";
	public static final String PRODOTTO_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita non rispetta il formato AAAAMMGG ";
	public static final String PRODOTTO_ANNULLATO_MODULO_NON_ANNULLATO = " ANNULLATO con uno o più MODULI non annullati";
	public static final String PRODOTTO_ANNULLATO_SOTTOSISTEMA_NON_ANNULLATO = " ANNULLATO con uno o più SOTTOSISTEMI non annullati";
	public static final String PRODOTTO_ANNULLATO_FUNZIONALITA_NON_ANNULLATA = " ANNULLATO con uno o più FUNZIONALITA non annullate";
	public static final String AREA_PRODOTTO_IS_PERSONAFISICA = " corrisponde ad una Persona FIsica (Tipo Persona) in EDMA_LISPA";
	public static final String AREA_PRODOTTO_NOT_AREA_IN_EDMA = " Il campo AREA PRODOTTO non corrisponde ad una Area(Codice Tipologia Ufficio) in UNITA_ORGANIZZATIVA_EDMA_LISPA";
	public static final String AREA_PRODOTTO_NON_PRESENTE = "Il campo AREA PRODOTTO non è valorizzato";
	public static final String RESPONSABILE_PRODOTTO_NON_PRESENTE = "Il campo RESPONSABILE PRODOTTO non è valorizzato: ";
	public static final String RESPONSABILE_PRODOTTO_NON_PERSONAFISICA = "Il RESPONSABILE PRODOTTO {} non è in PERSONALE_EDMA_LISPA ";
	public static final String AREA_PRODOTTO_INATTIVA = "Area Prodotto non in attività. Data fine validità precede la data attuale";

	public static final String PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita DISCENDENTE non racchiusa fra ##";
	public static final String PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita DISCENDENTE non rispetta il formato AAAAMMGG ";
	public static final String MODULO_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO = "Data fine Validita Modulo discendente successiva alla Data fine validita del Prodotto";
	public static final String SOTTOSISTEMA_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO = "Data fine Validita Sottosistema discendente successiva alla Data fine validita del Prodotto";
	public static final String FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO = "Data fine Validita Funzionalita discendente successiva alla Data fine validita del Prodotto";

	public static final String WRONG_WORKITEMS_STATUS = "Codice Stato non previsto per tipologia workitem";
	// MESSAGGI ERRORE
	// SOTTOSISTEMI

	public static final String SOTTOSISTEMA_NOME_DUPLICATO = "Nome Sottosistema duplicato nell'insieme dei Nomi di Sottosistema di uno specifico Prodotto";
	public static final String SOTTOSISTEMA_NOME_NULL = "Nome Sottosistema is null";
	public static final String SOTTOSISTEMA_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita non è racchiusa fra ##";
	public static final String SOTTOSISTEMA_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita non rispetta il formato AAAAMMGG ";
	public static final String SOTTOSISTEMA_ANNULLATO_MODULO_NON_ANNULLATO = " ANNULLATO con uno o più MODULI non annullati";
	public static final String SOTTOSISTEMA_ANNULLATO_FUNZIONALITA_NON_ANNULLATA = " ANNULLATO con uno o più FUNZIONALITA non annullate";

	public static final String SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita DISCENDENTE non racchiusa fra ##";
	public static final String SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita DISCENDENTE non rispetta il formato AAAAMMGG ";
	public static final String MODULO_DATAFINEVALIDITA_SUCCESSIVA_SOTTOSISTEMA = "Data fine Validita Modulo discendente successiva alla Data fine validita del Sottosistema";
	public static final String FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_SOTTOSISTEMA = "Data fine Validita Funzionalita discendente successiva alla Data fine validita del Sottosistema";

	// MESSAGGI ERRORE
	// MODULO

	public static final String MODULO_NOME_DUPLICATO_INSIEME_SOTTOSISTEMA = "Nome modulo duplicato nell'insieme dei nomi di modulo di uno specifico Sottosistema ";
	public static final String MODULO_NOME_DUPLICATO_INSIEME_PRODOTTO = "Nome modulo duplicato nell'insieme dei nomi di modulo di uno specifico Prodotto ";
	public static final String MODULO_NOME_NULL = "Nome is null";
	public static final String MODULO_SIGLA_DUPLICATO_INSIEME_PRODOTTO = "Sigla Modulo duplicata nell'insieme delle sigle di modulo di uno specifico Prodotto ";
	public static final String MODULO_SIGLA_DUPLICATO_INSIEME_SOTTOSISTEMA = "Sigla Modulo duplicata nell'insieme delle sigle di modulo di uno specifico Sottosistema ";
	public static final String MODULO_SIGLA_NULL = "Sigla is null";
	public static final String MODULO_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita non è racchiusa fra ##";
	public static final String MODULO_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita non rispetta il formato AAAAMMGG ";
	public static final String MODULO_ANNULLATO_MODULO_NON_ANNULLATO = " ANNULLATO con uno o più MODULI non annullati";
	public static final String MODULO_ANNULLATO_FUNZIONALITA_NON_ANNULLATA = " ANNULLATO con uno o più FUNZIONALITA non annullate";
	public static final String RESPONSABILE_MODULO_NON_PRESENTE = "Il campo RESPONSABILE MODULO {} non è valorizzato ";
	public static final String RESPONSABILE_MODULO_NON_PERSONAFISICA = "Il RESPONSABILE MODULO {} non è in PERSONALE_EDMA_LISPA ";

	public static final String MODULO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita DISCENDENTE non racchiusa fra ##";
	public static final String MODULO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita DISCENDENTE non rispetta il formato AAAAMMGG ";
	public static final String FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_MODULO = "Data fine Validita funzionalita discendente successiva alla Data fine validita del Modulo";

	// MESSAGGI ERRORE
	// FUNZIONALITA

	public static final String FUNZIONALITA_NOME_DUPLICATO = "Nome funzionalita duplicata: ";
	public static final String FUNZIONALITA_NOME_NULL = "Nome funzionalita is null";
	public static final String FUNZIONALITA_SIGLA_DUPLICATA_INSIEME_PRODOTTO = "Sigla funzionalita duplicata nell'insieme delle sigle di Funzionalità di uno specifico Prodotto ";
	public static final String FUNZIONALITA_SIGLA_DUPLICATA_INSIEME_SOTTOSISTEMA = "Sigla funzionalita duplicata";
	public static final String FUNZIONALITA_SIGLA_NULL = "Sigla funzionalita is null";
	public static final String FUNZIONALITA_DATAFINEVALIDITA_NO_CANCELLETTI = "Data Fine Validita non è racchiusa fra ##";
	public static final String FUNZIONALITA_DATAFINEVALIDITA_FORMATO_NON_VALIDO = "Data Fine Validita non rispetta il formato AAAAMMGG ";

	// MESSAGGI ERRORE
	// AMBIENTE TECNOLOGICO

	public static final String AMBIENTETECNOLOGICO_NOME_DUPLICATO = "Nome Ambiente Tecnologico duplicato";
	public static final String AMBIENTETECNOLOGICO_NOME_NULL = "Nome Ambiente Tecnologico is null";

	public static final String ROOT_UO = "LISPA";
	public static final String ROOT_UO_DESC = "LOMBARDIA INFORMATICA S.p.A.";
	public static final String ROOT_UO_CD_ENTE = "LI";
	public static final String LOGICAMENTE = "UNMARKED";
	public static final String FISICAMENTE = "ANNULLATO FISICAMENTE";
	public static final String DISMESSO = "DISMESSO";
	
	// MESSAGGI ERRORE
	// SGR PROJECT
	public static final String SGR_PROJECT_CNAME_NULL = " C_NAME is null";
	public static final String SGR_PROJECT_CNAME_DUPLICATO = " C_NAME duplicato";
	public static final String SGR_PROJECT_CNAME_MALFORMED = " C_NAME mancata congruenza nel formato del campo SW-{} ";

	public static final String SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED = " : nome identificativo, mancata congruenza nel formato del campo, deve iniziare con un trattino \"-\" ";
	public static final String SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED_GRAFFA = " : nome identificativo, mancata congruenza nel formato del campo, impossibile identificare il carattere \"}\" ";
	public static final String SGR_PROJECT_C_NAME_DESCRIZIONE_NULL = " Nome identificativo is null ";
	public static final String SGR_PROJECT_C_NAME_DESCRIZIONE_DUPLICATO = " Nome identificativo duplicato ";

	public static final String SGR_PROJECT_C_NAME_DESCRIZIONE_DIVERSO_IN_ORESTE = " : nome identificativo Project diverso dal nome dell'oggetto Oreste corrispondente (";

	public static final String SGR_PROJECT_PRODOTTO_NOT_FOUND_ORESTE = " Il prodotto associato al Project non risulta censito in Oreste";
	public static final String SGR_PROJECT_MODULO_PRODOTTO_NOTFOUND_ORESTE = " Il Modulo associato al Project non risulta figlio del Prodotto in Oreste";
	public static final String SGR_PROJECT_FUNZIONALITA_MODULO_PRODOTTO_NOTFOUND_ORESTE = " La funzionalita associata al Project non risulta figlia del Prodotto-Modulo in Oreste";

	public static final String SGR_PROJECT_PRODOTTO_ANNULLATO_ORESTE = "Prodotto annullato logicamente in Oreste";
	public static final String SGR_PROJECT_MODULO_ANNULLATO_ORESTE = "Modulo annullato logicamente in Oreste";
	public static final String SGR_PROJECT_FUNZIONALITA_ANNULLATO_ORESTE = "Funzionalita annullata logicamente in Oreste";
	public static final String SGR_PROJECT_ANNULLATO_FISICAMENTE = "Project annullato fisicamente";
	public static final String SGR_WI_ANNULLATO_FISICAMENTE = "WorkItem annullato fisicamente";
	
	public static final String SCHEDULAZIONE_BO_ENABLE = "enable";

	public static final String ENABLER = "enable";
	public static final String ANNULLATO_LOGICAMENTE_ELETTRA="ANNULLATO LOGICAMENTE";
	public static final String ANNULLATO_FISICAMENTE_ELETTRA="SI";

	public static final String UNMARKED = "UNMARKED";
	
	public static final String PROJECT_PATH_SUFFIX = "/.polarion/polarion-project.xml";
	
	//MAIL CONSTANTS
	public static final String SUBJECT = "ESITO ETL - DATE: ";
	public static final String ETLOK = "ETL COMPLETATO CON ESITO POSITIVO";
	public static final String ETLKO = "ETL COMPLETATO CON ESITO NEGATIVO";
	public static final String ETLMPSKO = " (IMPORT DATI MPS ROLLBACK PER ERRORE)";
	public static final String DM_ALM_ESECUZIONE = "true";
	
	//SFERA CONSTANTS
	public static final String FONTE_MISURA = "DMALM_STG_MISURA";
	public static final String TARGET_ASM = "DMALM_ASM";
	public static final String TARGET_MISURA = "DMALM_MISURA";
	public static final String TARGET_PROGETTO_SFERA = "DMALM_PROGETTO_SFERA";
	public static final String TARGET_ASM_PRODOTTO = "DMALM_ASM_PRODOTTO";
	public static final String TARGET_ASM_PRODOTTIARCHITETTURE = "DMALM_ASM_PRODOTTI_ARCH";
	public static final String FOL = "FOL; ";
	public static final String NECA = "NECA; ";
	public static final String SIG = "SIG; ";
	public static final String SINTEL = "SINTEL; ";
	public static final String FILENAME_EXPORT = "Sfera_Export_Massive_" ;
	public static final String EXP_SFERA_EXTENSION = ".xls" ;
	public static final String SVECCHIAMENTO = "Procedura_Svecchiamento";
	public static final String NKEY_SEPARATOR = "0";
	public static final String MEV = "Manutenzione Evolutiva";
	public static final String SVI = "Sviluppo";
	public static final String SFERA_PATH = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_PATH);
	public static final String SFERA_CURRENT = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_CURRENT_PATH);
	public static final String SFERA_CSV = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_CSV);
	public static final String AMBIENTE_PREPRODUZIONE = "AMBIENTE_PREPRODUZIONE";
	public static final String AMBIENTE_SVILUPPO = "AMBIENTE_SVILUPPO";
	public static final String AMBIENTE_LOCALE = "AMBIENTE_LOCALE";
	public static final String SFERA_ANNULLATO_LOGICAMENTE = "ANNULLATO LOGICAMENTE";
	public static final String SFERA_ANNULLATO_FISICAMENTE = "ANNULLATO FISICAMENTE";
	public static final String SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH = "#ANNULLATO LOGICAMENTE##";
	public static final String SFERA_ANNULLATO_FISICAMENTE_STARTSWITH = "#ANNULLATO FISICAMENTE##";
	public static final String PK_TARGET_ASM = "DMALM_ASM_PK";
	public static final String PK_TARGET_MISURA = "DMALM_MISURA_PK";
	public static final String PK_TARGET_PROGETTO_SFERA = "DMALM_PROGETTO_SFERA_PK";
	public static final String ID_TARGET_ASM = "ID_ASM";
	public static final String ID_TARGET_MISURA = "ID_MSR";
	public static final String ID_TARGET_PROGETTO_SFERA = "ID_PROGETTO";
	
	//MPS
	public static final String MPS_PATH = getProperty(DmAlmConfigReaderProperties.DMALM_MPS_PATH);
	public static final String MPS_PREFISSO_AMBIENTE = getProperty(DmAlmConfigReaderProperties.DMALM_MPS_PREFISSO_AMBIENTE);
	public static final String MPS_FILENAME_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
	public static final String MPS_CSV_EXTENSION = ".csv" ;
	public static final char MPS_CSV_SEPARATOR = ',';
	public static final String FILENAME_MPS_ATTIVITA = "MPS_Tabelle_Attivita_" ;
	public static final String FILENAME_MPS_CONTRATTI = "MPS_Tabelle_Contratti_" ;
	public static final String FILENAME_MPS_FIRM_VERBALE = "MPS_Tabelle_Firmatari_Verbale_" ;
	public static final String FILENAME_MPS_RESP_CONTRATTO = "MPS_Tabelle_Responsabili_Contratto_" ;
	public static final String FILENAME_MPS_RESP_OFFERTA = "MPS_Tabelle_Responsabili_Offerta_" ;
	public static final String FILENAME_MPS_RILASCI = "MPS_Tabelle_Rilasci_" ;
	public static final String FILENAME_MPS_RILASCI_EXC = "MPS_Tabelle_Rilasci_Verbali" ;
	public static final String FILENAME_MPS_RILASCI_VERBALI = "MPS_Tabelle_Rilasci_Verbali_" ;
	public static final String FILENAME_MPS_VERBALI = "MPS_Tabelle_Verbali_" ;
	public static final String TARGET_MPS_BO_ATTIVITA = "MPS_BO_ATTIVITA";
	public static final String TARGET_MPS_BO_CONTRATTI = "MPS_BO_CONTRATTI";
	public static final String TARGET_MPS_BO_FIRMATARI_VERBALE = "MPS_BO_FIRMATARI_VERBALE";
	public static final String TARGET_MPS_BO_RESPONSABILI_OFFERTA = "MPS_BO_RESPONSABILI_OFFERTA";
	public static final String TARGET_MPS_BO_RESPONS_CONTRATTO = "MPS_BO_RESPONSABILI_CONTRATTO";
	public static final String TARGET_MPS_BO_RILASCI = "MPS_BO_RILASCI";
	public static final String TARGET_MPS_BO_RILASCI_VERBALI = "MPS_BO_RILASCI_VERBALI";
	public static final String TARGET_MPS_BO_VERBALI = "MPS_BO_VERBALI";

	//CONTROLLI ERRATI
	public static final String PROJECT_NAME_MALFORMED = "ESF011 - NOME DEL PROGETTO SFERA NON RISPETTA IL PATTERN PREDEFINITO";
	public static final String WRONG_PROJECT_TYPE = "TIPO DELLA MPP DIVERSA DA BASELINE";
	public static final String WRONG_MISURE_TYPE = "TIPO DELLA MISURA NON È ASSOCIATO AL METODO CORRETTO";
	public static final String MISURE_NAME_MALFORMED = "ESF090 - NOME DELLA MISURA SFERA NON RISPETTA IL PATTERN PREDEFINITO";
	public static final String MISURE_APPROACH_MALFORMED = "APPROCCIO DELLA MISURA DIVERSO DA STANDARD, ERRATO!!";
	public static final String WRONG_MEASURER = "UTENTE MISURATORE ERRATO";
	public static final String WRONG_MEASURE_STATUS = "STATO ERRATO PER LA MISURA CORRENTE";
	public static final String WRONG_PAIR_TYPE = "LA COPPIA DI MISURE MISURATORE-FORNITORE NON E' DELLO STESSO TIPO";
	public static final String WRONG_DENOMINAZIONE = "FORMATTAZIONE DEL PATTERN DI DENOMINAZIONE ERRATO O DENOMINAZIONE NON PRESENTE IN ORESTE";
	public static final String WRONG_DENOMINAZIONE_UTENTI = "FORMATTAZIONE DEL PATTERN DI DENOMINAZIONE UTENTI ERRATO, ALLA DATA:";
	public static final String WRONG_CODASM_CONFINANTE = "UNO DEI COD_ASM CONFINANTI NON E' PRESENTE NELL'INSIEME GENERALE DEI COD_ASM:";
	public static final String WRONG_CODASM_CONFINANTE_1 = "L'INSIEME COD_FLUSSI_IO_ASM NON COINCIDE CON L'INSIEME UNIONE DENOM_SIST_TERZEPARTI_CONFINANTI + COD_ASM CONFINANTI:";
	public static final String WRONG_CODASM_FLUSSI_IO = "UNO DEI COD_ASM DEI FLUSSI_IO NON E' PRESENTE NELL'INSIEME GENERALE DEI COD_ASM:";
	public static final String WRONG_CODASM_TERZE_PARTI = "UNO DEI COD_ASM DEI DENOM_SIST_TERZEPARTI_CONFIN NON E' PRESENTE NELL'INSIEME GENERALE DEI COD_ASM:";;
	public static final String WRONG_FLAG_VALUES = "UNO O PIU FLAG DELLA MISURA PRESENTANO VALORI NON PREVISTI NEL DOMINIO DI VALORI";
	public static final String WRONG_COD_ASM_SERVCOM = "I COD_SERVIZI_COMUNI_ALTRE_ASM NON SONO UN SOTTOINSIEME DELLE COD_ASM CONFINANTI";
	public static final String WRONG_DATE_ASM = "DATA DI INIZIO VALIDITA' ASM MAGGIORE DELLA DATA CORRENTE DI ESECUZIONE E/O DATA FINE VALIDITA' MINORE DELLA DATA INIZIO VALIDITA' ASM:";
	public static final String WRONG_LINK_PROGSF_WI = "PROGETTO SFERA NON CORRISPONDENTE A NESSUN WORKITEM DEL TEMPLATE SVILUPPO";
	public static final String WRONG_LINK_ASM_PRODOTTO = "ASM SFERA NON CORRISPONDENTE A NESSUN PRODOTTO";
	public static final String WRONG_LINK_ASM_UNITAORGANIZZATIVA = "ASM SFERA CORRISPONDENTE A PIU UNITA ORGANIZZATIVE";
	public static final String WRONG_LINK_PROJECT_PRODOTTO = "PROJECT SGR_CM NON CORRISPONDENTE A NESSUN PRODOTTO";
	public static final String WRONG_LINK_PROJECT_UNITAORGANIZZATIVA = "PROJECT SGR_CM NON CORRISPONDENTE A NESSUNA UNITA ORGANIZZATIVA";
	//ECCEZIONI FILIERA
	public static final String ECCEZIONE_FILIERA_DRQS_SMAN = "ECCEZIONE: PROGETTO SVILUPPO DEMAND (drqs) LEGATO A MANUTENZIONE (sman)";
	public static final String ECCEZIONE_FILIERA_DMAN_SRQS = "ECCEZIONE: RICHIESTA MANUTENZIONE (dman) LEGATO A PROGETTO SVILUPPO SVILUPPO (srqs)";
	public static final String ECCEZIONE_FILIERA_RQD_CLASSIFICAZIONE = "ECCEZIONE: PROGETTO DEMAND (rqd) CON CF_CLASSIFICAZIONE NON VALORIZZATA";
	public static final String ECCEZIONE_FILIERA_RQD_SVILUPPO_NON_UTILIZZATO = "ECCEZIONE: PROGETTO DEMAND (rqd) CF_CLASSIFICAZIONE SVILUPPO NON UTILIZZATO IN FILIERA";
	//MOOD
	public static final String FELICITA = "E' UN ETL CHE RUNNA";
	public static final String TRISTEZZA = "E' UN ETL CON ESITO NEGATIVO";
	//MESSAGGI DI WARNING
	public static final String WRONG_CODASM = "ESF005 - FORMATTAZIONE DEL PATTERN DI COD_ASM ERRATO O COD_ASM NON PRESENTE IN ORESTE";
	public static final String WRONG_LIST = "LISTA DELLE MISURE ERRATE";
	public static final String RIGHT_LIST = "LISTA MISURE CORRETTE";
	public static final String WRONG_SVI_MSR_ORDER = "ESF014 - MISURA ST3 SPAIATA NON PRECEDUTA DA ST1 e/o ST2 o PRECEDUTA DA NOMI DI MISURA CON FORMATO ERRATO";
	public static final String BASELINE = "Baseline";
	public static final String PATR = "PATR";
	public static final String ERRORE_SPAZI_NOME_ASM = "ESF007 - NOME ASM SFERA CONTENENTE SPAZI";
	public static final String ERRORE_SPAZI_PROGETTO_ASM = "PROGETTO SFERA CONTENENTE SPAZI";
	public static final int FETCH_SIZE = 1000;
	public static String IDAPP_OBBLIGATORIO="ESF001 - IdApp e' obbligatorio";
	public static String IDPRJ_OBBLIGATORIO="ESF002 - IdPrj e' obbligatorio ";
	public static String IDMEA_OBBLIGATORIO="ESF003 - IdMea e' obbligatorio ";
	public static String APPLICAZIONE_OBBLIGATORIO="ESF004 - Applicazione e' obbligatorio ";
	public static String COD_ASM_NON_PRESENTE_ORESTE="ESF006 - COD_ASM non presente in  oreste :  ";
	public static String VAF_PREDEFINITO_OBBLIGATORIO="ESF008 - VAF predefinito e' obbligatorio ";
	public static String VAF_PREDEFINITO_NON_PERMESSO="ESF009 - VAF Predefinito valore non permesso : ";
	public static String PROGETTO_OBBLIGATORIO="ESF010 - Progetto e' obbligatorio ";
	public static String DATA_SFERA_FORMATO_NON_CORRETTO="ESF012 - Progetto - la data del progetto PATR-  sfera non rispetta il formato predefinito aaaammgg : ";
	public static String MISURA_OBBLIGATORIA="ESF013 - Misura e' obbligatorio ";
	public static String APPROCIO_OBBLIGATORIO="ESF015 - Approccio e' obbligatorio ";
	public static String APPROCIO_VALORE_NON_PERMESSO="ESF016 - Approccio - Valore non permesso : ";
	public static String METODO_OBBLIGATORIO="ESF017 - METODO E' OBBLIGATORIO";
	public static String METODO_VALORE_NON_PERMESSO="ESF018 - : Metodo - Valore non permesso : ";
	public static String STATO_MISURA_OBBLOGATORIO="ESF019 - Stato Misura e' obbligatorio ";
	public static String FP_PESATI_MIN_OBBLIGATORIO="ESF020 - FP pesati (MIN) e' obbligatorio ";
	public static String FP_PESATI_UFP_OBBLIGATORIO="ESF021 - FP pesati (UFP) e' obbligatorio ";
	public static String FP_PESATI_MAX_OBBLIGATORIO="ESF022 - FP pesati (MAX) e' obbligatorio ";
	public static String FP_NON_PESATI_MIN_OBBLIGATORIO="ESF023 - FP non pesati (MIN) e' obbligatorio ";
	public static String FP_NON_PESATI_UFP_OBBLIGATORIO="ESF024 - FP non pesati (UFP) e' obbligatorio ";
	public static String FP_NON_PESATI_MAX_E_OBBLIGATORIO="ESF025 - FP non pesati (MAX) e' obbligatorio";
	public static String DENOMINAZIONE_ASM_OBBLIGATORIO="ESF026 - APP-ATT:DENOMINAZIONE_ASM E' OBBLIGATORIO.";
	public static String DATA_ULTIMO_AGGIORN_OBBLIGATORIO="ESF027 - APP-ATT:DATA_ULTIMO_AGGIORN e' obbligatorio ";
	public static String DATA_ULTIMO_AGGIORN_FORMATO="ESF028 - APP-ATT:DATA_ULTIMO_AGGIORN la data deve avere il formato gg/mm/aaaa ";
	public static String DATA_ULTIMO_AGGIORN_SUP_CORRENTE="ESF029 - APP-ATT:DATA_ULTIMO_AGGIORN la data non deve essere superiore alla data corrente ";
	public static String ACCOUNT_AUTORE_ULTIMO_AGGIORN="ESF030 - APP-ATT:ACCOUNT_AUTORE_ULTIMO_AGGIORN e' obbligatorio ";
	public static String NOME_AUTORE_ULTIMO_AGGIORN_OBBLIGATORIO="ESF031 - APP-ATT:NOME_AUTORE_ULTIMO_AGGIORN e' obbligatorio";
	public static String DATA_INIIZIO_VALIDITA_ASM="ESF032 - APP-ATT:DATA_INIZIO_VALIDITA_ASM e' obbligatorio";
	public static String DATA_INIIZIO_VALIDITA_ASM_FORMATO="ESF033 - APP-ATT:DATA_INIZIO_VALIDITA_ASM la data deve avere il formato gg/mm/aaaa ";
	public static String DATA_FINE_VALIDITA_ASM_FORMATO="ESF034 - APP-ATT:DATA_FINE_VALIDITA_ASM la data deve avere il formato gg/mm/aaaa";
	public static String DATA_FINE_VALIDITA_ASM_MINORE_INIZIO="ESF035 - APP-ATT:DATA_FINE_VALIDITA_ASM minore della data inizio validita' asm ";
	public static String DENOM_UTENTI_FINALI_ASM_OBBLIGATORIO="ESF036 - APP-ATT:DENOM_UTENTI_FINALI_ASM e' obbligatorio";
	public static String DENOM_UTENTI_FINALI_ASM_PATTERN="ESF037 - ATT:DENOM_UTENTI_ FINALI_ASM non rispetta il pattern predefinito : ";
	public static String COD_ASM_CONFINANTI_PATTERN="ESF038 - APP-ATT: COD_ASM_CONFINANTI  non rispetta il pattern predefinito : ";
	public static String COD_ASM_CONFINANTI_NON_PRESENTI="ESF039 - Il COD_ASM confinanti non e' presente / attivo nell'insieme generale dei COD_ASM: ";
	public static String DENOM_SIST_TERZEPARTI_CONFINANTI="ESF040 - DENOM_SIST_TERZEPARTI_CONFINANTI non rispetta il pattern predefinito : ";
	public static String DENOM_SIST_TERZEPARTI_CONFINANTI_CODICE_ASM_VALIDE="ESF041 - APP-ATT: DENOM_SIST_TERZEPARTI_CONFINANTI non deve avere codici di ASM valide: ";
	public static String COD_FLUSS_IO_ASM="ESF042 - APP-ATT: COD_FLUSSI_IO_ASM non rispetta il pattern predefinito: ";
	public static String COD_FLUSSI_IO_ASM="ESF043 - COD_FLUSSI_IO_ASM non coincide con l'insieme unione DENOM_SIST_TERZEPARTI_CONFINANTI + COD_ASM CONFINANTI: ";
	public static String FLAG_ASM_SERVIZIO="ESF044 - APP-ATT:FLAG_ASM_SERVIZIO_COMUNE e' obbligatorio ";
	public static String FLAG_ASM_SERVIZIO_COMUNE="ESF045 - APP-ATT:FLAG_ASM_SERVIZIO_COMUNE - Valore non permesso :";
	public static String COD_ALTRE_ASM_NO_CONFINANTI="ESF046 - ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI non sono un sottoinsieme delle COD_ASM CONFINANTI : ";
	public static String COD_ALTRE_ASM_NO_PATTERN="ESF047 - ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI non rispetta il pattern predefinito : ";
	public static String COD_ALTRE_ASM_NON_PRESENTE="ESF048 - ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI  non e' presente / attivo / servizio comune nell'insieme generale dei COD_ASM : ";
	public static String FLAG_ASM_DA_MISURARE_OBBLIGATORIO_FP="ESF049 - APP-ATT:FLAG_ASM_DA_MISURARE_SVILUPPOMEV_IN_FP e' obbligatorio ";
	public static String FLAG_ASM_DA_MISURARE_VALORE_NON_PERMESSO="ESF050 - APP-ATT:FLAG_ASM_DA_MISURARE_SVILUPPOMEV_IN_FP - Valore non permesso : ";
	public static String FLAG_ASM_DA_MISURARE_IN_FP_OBBLIGATORIO="ESF051 - APP-ATT:FLAG_ASM_DA_MISURARE_PATRIMONIALE_IN_FP e' obbligatorio ";
	public static String FLAG_ASM_DA_MISURARE_IN_FP_NO_PERMESSO="ESF052 - APP-ATT:FLAG_ASM_DA_MISURARE_PATRIMONIALE_IN_FP - Valore non permesso : ";
	public static String FLAG_ASM_IN_MANUTENZIONE_OBBLIGATORIO="ESF053 - APP-ATT:FLAG_ASM_IN_MANUTENZIONE e' obbligatorio ";
	public static String FLAG_ASM_IN_MANUTENZIONE_NON_PERMESSO="ESF054 - APP-ATT:FLAG_ASM_IN_MANUTENZIONE - Valore non permesso : ";
	public static String COD_DIREZIONE_DEMAND_OBBLIGATORIO="ESF055 - APP-ATT:COD_DIREZIONE_DEMAND e' obbligatorio ";
	public static String FORNITORE_MPP_NON_VALORIZZATO_SVILUPPO="ESF056 - PRJ-ATT:FORNITORE_MPP non e' valorizzabile per progetti di tipo 'Sviluppo' oppure 'Manutenzione evolutiva'";
	public static String FORNITORE_SVILUPPO_MEV_NON_VALORIZZABILE="ESF058 - PRJ-ATT:FORNITORE_SVILUPPO_MEV non e' valorizzabile per progetti di tipo 'Baseline'";
	public static String COD_RDI_NO_PATTERN="ESF059 - PRJ-ATT: COD_ RDI non rispetta il pattern predefinito ";
	public static String MP_PERCENT_CICLO_DI_VITA="ESF061 - PRJ-ATT:MP_PERCENT_CICLO_DI_VITA non e' valorizzabile per progetti di tipo 'Baseline'";
	public static String MP_PERCENT_CICLO_DI_VITA_VAL_NON_PERMESSO="ESF062 - PRJ-ATT:MP_PERCENT_CICLO_DI_VITA - Valore non permesso : ";
	public static String FCC_FATT_CORREZZ_COMPLESSIVO_NO_BASELINE="ESF064 - PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO non e' valorizzabile per progetti di tipo 'Baseline' ";
	public static String FCC_FATT_CORREZZ_COMPLESSIVO_NO_VALIDO="ESF065 - PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO - Valore non permesso : ";
	public static String AUDIT_MONITORE_NO_VAL_IN_BASELINE="ESF066 - PRJ-ATT:AUDIT_MONITORE non e’ valorizzabile per progetti di tipo ‘Baseline’ ";
	public static String AUDIT_INDICE_VERIFICABILITA_NO_VAL_BASELINE="ESF067 - PRJ-ATT:AUDIT_INDICE_VERIFICABILITA non e' valorizzabile per progetti di tipo 'Baseline'";
	public static String UTENTE_MISURATORE_OBBLIGATORIO="ESF068 - Utente misuratore e' obbligatorio e deve assumere valore diverso da 'Admin': ";
	public static String UTILIZZATA_NON_PERMESSO="ESF069 - UTILIZZATA - Valore non permesso : ";
	public static String PROPRIETA_LEGALE_NON_PERMESSO="ESF070 - Proprietà legale - Valore non permesso : ";
	public static String INCLUDE_DB_NON_PERMESSO="ESF071 - Includi nel database di patrimonio - Valore non permesso : ";
	/*public static String FLAG_APPLICABILITA_LG_FP_DWH_NON_PERMESSO="ESF072 - PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_- DWH - Valore non permesso :";
	public static String FLAG_APPLICABILITA_LG_FP_GIS_NO_PERMESSO="ESF073 - PRJ-ATT:FLAG_APPLICABILITA_LG_FP_GIS - Valore non permesso : ";
	public static String FLAG_APPLICATIBILITA_LG_FP_EDMA_NON_PERMESSO="ESF074 - PRJ-ATT:FLAG_APPLICABILITA_LG_FP_EDMA - Valore non permesso : ";
	public static String FLAG_APPLICABILITA_LG_FP_MWARE_NO_PERMESSO="ESF075 - PRJ-ATT:FLAG_APPLICABILITA_LG_FP_MWARE - Valore non permesso : ";
	public static String FLAG_APPLICABILITA_LG_FP_SITIWEB="ESF076 - PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_SITIWEB - Valore non permesso : ";
	public static String FLAG_APPLICABILITA_LG_FP_NON_PERMESSO="ESF077 - PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_futuro-01 - Valore non permesso : ";
	public static String FLAG_AMBITO_TECNOLOGICO_TRANS_BATCH_REP_NON_PERMESSO="ESF079 - PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_TRANS_BATCH_REP - Valore non permesso : ";
	public static String FLAG_AMBITO_TECNOLOGICO_GIS_VALORE_NON_PERMESSO="ESF080 - PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ GIS - Valore non permesso :";
	public static String FLAG_AMBITO_TECNOLOGICO_PORTALI_NON_PERMESSO="ESF081 - PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_PORTALI - Valore non permesso : ";
	public static String FLAG_AMBITO_TECNOLOGICO_PIAT_SPEC_ENT_NON_PERMESSO="ESF082 - PRJ-ATT:FLAG_ AMBITO_TECNOLOGICO_ PIATTAF_ SPECIAL_ ENTERPRISE - Valore non permesso : ";
	public static String FLAG_AMBITO_TECNOLOGICO_FUTURO_NON_PERMESSO="ESF083 - PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-01 - Valore non permesso : ";
	public static String FLAG_AMBITO_TECNOLOGICO_FUTURO2_NON_PERMESSO="ESF084 - PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-02 - Valore non permesso : ";*/
	public static String MANCATA_CORRISPONDENZA_SFERA_WI="ESF087 - Mancata corrispondenza tra PROGETTO SFERA e relativo workitem SGR_CM ";
	public static String NO_CORR_STATO_WI_MISURA="ESF088 - Mancata corrispondenza tra lo STATO dei WI e lo stato della MISURA SFERA";
	public static String PERMESSION_NON_PERMESSO="ESF089 - PERMISSIONS  valore non permesso ";
    
	// nome Stored Procedure
	public static String STORED_PROCEDURE_VERIFICA_ESITO_ETL = "VERIFICA_ESITO_ETL";
	public static String STORED_PROCEDURE_STOR_ANOMALIA_PRODOTTO = "STOR_ANOMALIA_PRODOTTO_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_DIFETTO_PRODOTTO = "STOR_DIFETTO_PRODOTTO_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_RICHIESTA_SUPPORTO = "STOR_RICH_SUPPORTO_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_MANUTENZIONE = "STOR_MANUTENZIONE_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_PROG_DEMAND = "STOR_PROGETTO_DEMAND_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_PROGRAMMA = "STOR_PROGRAMMA_BY_PROJ";
	public static String STORED_PROCEDURE_REL_PROGETTO = "STOR_RELEASE_PROGETTO_BY_PROJ";
	public static String STORED_PROCEDURE_SOTTOPROGRAMMA = "STOR_SOTTOPROGRAMMA_BY_PROJ";
	public static String STORED_PROCEDURE_PROGETTO_SVILUPPO_SVILUPPO = "STOR_PROG_SVI_SVI_BY_PROJ";
	public static String STORED_PROCEDURE_STOR_PROG_ESE = "STOR_PROGETTO_ESE_BY_PROJ";
	public static String STORED_PROCEDURE_ANOMALIA_ASS = "STOR_ANOMALIA_ASS_BY_PROJ";
	public static String STORED_PROCEDURE_TASK = "STOR_TASK_BY_PROJ";
	public static String STORED_PROCEDURE_REL_SER = "STOR_RELEASE_SERVIZI_BY_PROJ";
	public static String FUNCTION_BACKUP_TARGET = "BACKUP_TARGET";
	public static String RECOVER_TARGET_SGR_ELETTRA = "RECOVER_TARGET_SGR_ELETTRA";

	// Calipso Sheet Name Scheda Servizio
	public static String CALIPSO_SHEET_NAME_SCHEDA_SERVIZIO = "Portfolio_esteso";

	
	public static String getProperty(String property) {

		String props = "";
		try {
			props = DmAlmConfigReader.getInstance().getProperty(property);
		} catch (PropertiesReaderException e) {
			
		}

		return props;
	}
}
