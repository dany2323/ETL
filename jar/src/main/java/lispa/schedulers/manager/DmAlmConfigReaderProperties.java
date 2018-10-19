
package lispa.schedulers.manager;

public class DmAlmConfigReaderProperties
{
	public static final String PROPERTIES_READER_FILE_NAME = "datamart.properties";
	
	//wi-191
	public static final String VERSIONE_ETL = "4.9.00";
	
	//QUERY FATTI
	public static final String SQL_ANOMALIA					    = "anomalia.sql";
	public static final String SQL_ANOMALIA_PRODOTTO_DUMMY	    = "anomaliaprodottodummy.sql";
	public static final String SQL_DIFETTO_PRODOTTO_DUMMY	    = "difettoprodottodummy.sql";
	public static final String SQL_DEFECT 						= "defect.sql";
	public static final String SQL_PROGRAMMA					= "programma.sql";
	public static final String SQL_PROGETTO_DEMAND				= "rqd.sql";
	public static final String SQL_PROGETTO_SVILUPPO_SVILUPPO 	= "srqs.sql";
	public static final String SQL_PROGETTO_SVILUPPO_DEMAND 	= "drqs.sql";
	public static final String SQL_DOCUMENTO					= "documento.sql";
	public static final String SQL_RELEASE_DI_PROGETTO			= "release.sql";
	public static final String SQL_TASK							= "task.sql";
	public static final String SQL_TESTCASE						= "testcase.sql";
	public static final String SQL_MANUTENZIONE 				= "sman.sql";
	public static final String SQL_SOTTOPROGRAMMA				= "sottoprogramma.sql";
	public static final String SQL_RICHIESTA_MANUTENZIONE		= "dman.sql";
	public static final String SQL_FASE							= "fase.sql";
	public static final String SQL_PEI							= "pei.sql";
	public static final String SQL_PROGETTO_ESE					= "progettoese.sql";
	public static final String SQL_RELEASE_IT 					= "releaseit.sql";
	public static final String SQL_BUILD 						= "build.sql";
	public static final String SQL_RICHIESTA_GESTIONE			= "richiestagestione.sql";
	public static final String SQL_TASK_IT						= "taskit.sql";
	public static final String SQL_RELEASE_SERVIZI 				= "releaseser.sql";
	public static final String SQL_ANOMALIA_ASSISTENZA 			= "anomaliaassistenza.sql";
	public static final String SQL_WORKITEM_USERASSIGNEES 		= "workitemassignees.sql";
	public static final String SQL_CLASS_DEMAND					= "classificatoredem.sql";
	public static final String SQL_CLASS						= "classificatore.sql";

	//QUERY DIMENSIONI
	public static final String SQL_STRUTTURAORGANIZZATIVA 		= "strutturaorganizzativa.sql";
	public static final String SQL_STATOWORKITEM 				= "statoworkitem.sql";
	public static final String SQL_PROJECT 						= "project.sql";
	public static final String SQL_TEMPO 						= "tempo.sql";
	public static final String SQL_AREATEMATICA 				= "areatematica.sql";
	public static final String SQL_LINKEDWORKITEMS				= "linkedworkitems.sql";
	public static final String SQL_ATTACHMENT					= "attachment.sql";
	public static final String SQL_HYPERLINK					= "hyperlink.sql";
	public static final String SQL_USER							= "user.sql";
	
	public static final String SQL_CLASSIFICATORI 				= "classificatori.sql";
	public static final String SQL_PROJECTROLES 				= "projectroles.sql";
	public static final String SQL_PERSONALE 					= "personale.sql";
	public static final String SQL_PRODOTTI 					= "prodotti.sql";
	public static final String SQL_SOTTOSISTEMI 				= "sottosistemi.sql";
	public static final String SQL_MODULI 						= "moduli.sql";
	public static final String SQL_FUNZIONALITA 				= "funzionalita.sql";
	public static final String SQL_AMBIENTETECNOLOGICO 			= "ambientetecnologico.sql";
	public static final String SQL_VSIREWORKITEMLINK 			= "vsireworkitemlink.sql";
	public static final String SQL_VSISSWORKITEMLINK 			= "vsissworkitemlink.sql";
	
	public static final String SQL_ELETTRAUNITAORGANIZZATIVE  				= "elettraunitaorganizzative.sql";
	public static final String SQL_ELETTRACLASSIFICATORI 					= "elettraclassificatori.sql";
	public static final String SQL_ELETTRAMODULI 							= "elettramoduli.sql";
	public static final String SQL_MODULI_BY_PRODOTTO_PK = "elettramoduli_by_prodotto_pk.sql"; //Per gli annullati fisici
	public static final String SQL_ELETTRAPERSONALE							= "elettrapersonale.sql";
	public static final String SQL_ELETTRAPRODOTTIARCHITETTURE				= "elettraprodottiarchitetture.sql";
	public static final String SQL_ELETTRAFUNZIONALITA						= "elettrafunzionalita.sql";
	public static final String SQL_ELETTRAAMBIENTETECNOLOGICO				= "elettraambientetecnologico.sql";
	public static final String SQL_ELETTRAAMBIENTETECNOLOGICOCLASSIFICATORI	= "elettraambientetecnologicoclassificatori.sql";
	
	public static final String SQL_DELETE_SIRE_HISTORY_WORKITEM	= "deletesirehistoryworkitem.sql";
	public static final String SQL_DELETE_SISS_HISTORY_WORKITEM	= "deletesisshistoryworkitem.sql";
	
	//SQL Project eliminati tra un'esecuzione e l'altra
	public static final String SQL_DELETED_SIRE_PROJECTS							= "deletedsireprojects.sql";
	public static final String SQL_DELETED_SISS_PROJECTS							= "deletedsissprojects.sql";
	//SQL Workitem eliminati 
	public static final String SET_ANNULLATO_DELETED_WORKITEM 						= "annulladeletedworkitems.sql";				
	//
	public static final String SQL_UNMARKED_PROJECTS 						= "unmarkedprojects.sql";
	//SQL delete record di LOG datati da DMALM_LOG_DEBUG
	public static final String SQL_TARGET_LOG_DELETE						="delete_elapsed_log.sql";
	//riattiva i project che erano messi ad UNMARKED
	public static final String SQL_REACTIVATED_PROJECTS 					="reactivate_project.sql"; 
	//Prendo la current
	public static final String SQL_CURRENT_SIRE_PROJECTS = 						"current_projects";
	
	//TABELLE DI RELAZIONE TRA OGGETTI ORESTE E CLASSIFICATORI
	public static final String SQL_REL_MODULI_CLASSIFICATORI 				= "rel_moduli_classificatori.sql";
	public static final String SQL_REL_FUNZIONALITA_CLASSIFICATORI 			= "rel_funzionalita_classificatori.sql";
	public static final String SQL_REL_AMBIENTITECNOLOGICI_CLASSIFICATORI 	= "rel_ambientitecnologici_classificatori.sql";
	public static final String SQL_REL_PRODOTTI_CLASSIFICATORI 				= "rel_prodotti_classificatori.sql";
	
	// SEQUENCE
	public static final String SEQUENCE_DMALM_PROJECT_ROLES_SGR = "DM_ALM_PROJECT_ROLES_SGR_SEQ.nextval";
		
	//AMBIENTE
	public static final String DM_ALM_AMBIENTE = "dmalm.ambiente";
	
	//ESECUZIONE
	public static final String DM_ALM_ESECUZIONE_EDMA = "dmalm.esecuzione.edma";
	public static final String DM_ALM_ESECUZIONE_SFERA = "dmalm.esecuzione.sfera";
	public static final String DM_ALM_ESECUZIONE_MPS = "dmalm.esecuzione.mps";

	// DATABASE DM_ALM ORACLE
	public static final String DM_ALM_DRIVER_CLASS_NAME = "dmalm.driver_class_name";
	public static final String DM_ALM_USER = "dmalm.user";
	public static final String DM_ALM_PSW = "dmalm.password";
	public static final String DM_ALM_HOST = "dmalm.host";
	public static final String DM_ALM_PORT = "dmalm.port";
	public static final String DM_ALM_DB_NAME = "dmalm.dbname";
	public static final String DM_ALM_DB_URL= "dmalm.url";

	// DATABASE FONTE ELETTRA ORACLE
	public static final String ELETTRA_DRIVER_CLASS_NAME = "dmalm.fonte.elettra.driver_class_name";
	public static final String ELETTRA_USER = "dmalm.fonte.elettra.user";
	public static final String ELETTRA_PSW = "dmalm.fonte.elettra.password";
	public static final String ELETTRA_HOST = "dmalm.fonte.elettra.host";
	public static final String ELETTRA_PORT = "dmalm.fonte.elettra.port";
	public static final String ELETTRA_DB_NAME = "dmalm.fonte.elettra.dbname";
	public static final String ELETTRA_DB_URL= "dmalm.fonte.elettra.url";
	
	// DATABASE POSTGRES
	public static final String PG_DRIVER_CLASS_NAME = "pg.driver_class_name";
	
	// SISS
	public static final String SISS_CURRENT_URL= "siss.current.url";
	public static final String SISS_CURRENT_USERNAME = "siss.current.username";
	public static final String SISS_CURRENT_PSW ="siss.current.password";

	public static final String SISS_HISTORY_URL = "siss.history.url";
	public static final String SISS_HISTORY_USERNAME ="siss.history.username";
	public static final String SISS_HISTORY_PSW ="siss.history.password";
	
	// SIRE
	public static final String SIRE_CURRENT_URL= "sire.current.url";
	public static final String SIRE_CURRENT_USERNAME = "sire.current.username";
	public static final String SIRE_CURRENT_PSW ="sire.current.password";

	public static final String SIRE_HISTORY_URL = "sire.history.url";
	public static final String SIRE_HISTORY_USERNAME ="sire.history.username";
	public static final String SIRE_HISTORY_PSW ="sire.history.password";
	
	// SVN SIRE
	public static final String SIRE_SVN_URL= "sire.svn.url";
	public static final String SIRE_SVN_USERNAME= "sire.svn.username";
	public static final String SIRE_SVN_PSW= "sire.svn.password";
	
	public static final String SIRE_SVN_STATOWORKITEM_ANOMALIA_FILE = "sire.svn.stato_workitem_anomalia";
	public static final String SIRE_SVN_STATOWORKITEM_DIFETTO_FILE = "sire.svn.stato_workitem_difetto";
	public static final String SIRE_SVN_STATOWORKITEM_PROGRAMMA_FILE = "sire.svn.stato_workitem_programma";
	public static final String SIRE_SVN_STATOWORKITEM_SOTTOPROGRAMMA_FILE = "sire.svn.stato_workitem_sottoprogramma";
	public static final String SIRE_SVN_STATOWORKITEM_PROGETTODEMAND_FILE = "sire.svn.stato_workitem_progettodemand";
	public static final String SIRE_SVN_STATOWORKITEM_PROGETTOSVILUPPODEMAND_FILE = "sire.svn.stato_workitem_progettosviluppodemand";
	public static final String SIRE_SVN_STATOWORKITEM_RICHIESTAMANUTENZIONE_FILE = "sire.svn.stato_workitem_richiestamanutenzione";
	public static final String SIRE_SVN_STATOWORKITEM_DOCUMENTO_FILE = "sire.svn.stato_workitem_documento";
	public static final String SIRE_SVN_STATOWORKITEM_FASE_FILE = "sire.svn.stato_workitem_fase";
	public static final String SIRE_SVN_STATOWORKITEM_PROGETTOESE_FILE = "sire.svn.stato_workitem_progettoese";
	public static final String SIRE_SVN_STATOWORKITEM_PEI_FILE = "sire.svn.stato_workitem_pei";
	public static final String SIRE_SVN_STATOWORKITEM_PROGETTOSVILUPPOSVILUPPO_FILE = "sire.svn.stato_workitem_progettosvilupposviluppo";
	public static final String SIRE_SVN_STATOWORKITEM_MANUTENZIONE_FILE = "sire.svn.stato_workitem_manutenzione";
	public static final String SIRE_SVN_STATOWORKITEM_TESTCASE_FILE = "sire.svn.stato_workitem_testcase";
	public static final String SIRE_SVN_STATOWORKITEM_TASK_FILE = "sire.svn.stato_workitem_task";
	public static final String SIRE_SVN_STATOWORKITEM_RELEASEDIPROGETTO_FILE = "sire.svn.stato_workitem_releasediprogetto";
	public static final String SIRE_SVN_STATOWORKITEM_RELEASEIT_FILE = "sire.svn.stato_workitem_releaseit";
	public static final String SIRE_SVN_STATOWORKITEM_BUILD_FILE = "sire.svn.stato_workitem_build";
	public static final String SIRE_SVN_STATOWORKITEM_TASKIT_FILE = "sire.svn.stato_workitem_taskit";
	public static final String SIRE_SVN_STATOWORKITEM_ANOMALIAASSISTENZA_FILE = "sire.svn.stato_workitem_anomaliaassistenza";
	public static final String SIRE_SVN_STATOWORKITEM_RICHIESTAGESTIONE_FILE = "sire.svn.stato_workitem_richiestagestione";
	public static final String SIRE_SVN_STATOWORKITEM_RELEASESERVIZI_FILE = "sire.svn.stato_workitem_releaseservizi";
	public static final String SIRE_SVN_STATOWORKITEM_CLASSIFICATORE_FILE = "sire.svn.stato_workitem_classificatore";
	
	public static final String SIRE_SVN_PROJECTROLES_FILE_SVILUPPO = "sire.svn.project_roles.sviluppo";
	public static final String SIRE_SVN_PROJECTROLES_FILE_DEMAND = "sire.svn.project_roles.demand";
	public static final String SIRE_SVN_PROJECTROLES_FILE_DEMAND2016 = "sire.svn.project_roles.demand2016";
	public static final String SIRE_SVN_PROJECTROLES_FILE_ASSISTENZA = "sire.svn.project_roles.assistenza";
	public static final String SIRE_SVN_PROJECTROLES_FILE_INTEGRAZIONETECNICA = "sire.svn.project_roles.integrazionetecnica";
	public static final String SIRE_SVN_PROJECTROLES_FILE_SERVIZIDEPLOY = "sire.svn.project_roles.servizideploy";
	public static final String SIRE_SVN_PROJECTROLES_GLOBAL_1_FILE = "sire.svn.project_roles.global.1";
	public static final String SIRE_SVN_PROJECTROLES_GLOBAL_2_FILE = "sire.svn.project_roles.global.2";
	public static final String SIRE_SVN_USER_ROLES_FILE = "sire.svn.userroles";
	public static final String SIRE_SVN_SCHEDE_SERVIZO_FILE = "sire.svn.schede_servizio";
	
	//SVN SIRE LINKED_WORKITEM_ROLES
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_SVILUPPO = "sire.svn.linked_workitem_roles.sviluppo";
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_DEMAND = "sire.svn.linked_workitem_roles.demand";
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_ASSISTENZA = "sire.svn.linked_workitem_roles.assistenza";
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_INTEGRAZIONETECNICA = "sire.svn.linked_workitem_roles.integrazionetecnica";
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_SERVIZIDEPLOY = "sire.svn.linked_workitem_roles.servizideploy";
	public static final String SIRE_SVN_LINKEDWORKITEMROLES_DEMAND2016 = "sire.svn.linked_workitem_roles.demand2016";
	
	// SVN SISS
	public static final String SISS_SVN_URL= "siss.svn.url";               
	public static final String SISS_SVN_USERNAME= "siss.svn.username";
	public static final String SISS_SVN_PSW= "siss.svn.password";
	
	public static final String SISS_SVN_STATOWORKITEM_ANOMALIA_FILE = "siss.svn.stato_workitem_anomalia";
	public static final String SISS_SVN_STATOWORKITEM_DIFETTO_FILE = "siss.svn.stato_workitem_difetto";
	public static final String SISS_SVN_STATOWORKITEM_PROGRAMMA_FILE = "siss.svn.stato_workitem_programma";
	public static final String SISS_SVN_STATOWORKITEM_SOTTOPROGRAMMA_FILE = "siss.svn.stato_workitem_sottoprogramma";
	public static final String SISS_SVN_STATOWORKITEM_PROGETTODEMAND_FILE = "siss.svn.stato_workitem_progettodemand";
	public static final String SISS_SVN_STATOWORKITEM_PROGETTOSVILUPPODEMAND_FILE = "siss.svn.stato_workitem_progettosviluppodemand";
	public static final String SISS_SVN_STATOWORKITEM_RICHIESTAMANUTENZIONE_FILE = "siss.svn.stato_workitem_richiestamanutenzione";
	public static final String SISS_SVN_STATOWORKITEM_DOCUMENTO_FILE = "siss.svn.stato_workitem_documento";
	public static final String SISS_SVN_STATOWORKITEM_FASE_FILE = "siss.svn.stato_workitem_fase";
	public static final String SISS_SVN_STATOWORKITEM_PROGETTOESE_FILE = "siss.svn.stato_workitem_progettoese";
	public static final String SISS_SVN_STATOWORKITEM_PEI_FILE = "siss.svn.stato_workitem_pei";
	public static final String SISS_SVN_STATOWORKITEM_PROGETTOSVILUPPOSVILUPPO_FILE = "siss.svn.stato_workitem_progettosvilupposviluppo";
	public static final String SISS_SVN_STATOWORKITEM_MANUTENZIONE_FILE = "siss.svn.stato_workitem_manutenzione";
	public static final String SISS_SVN_STATOWORKITEM_TESTCASE_FILE = "siss.svn.stato_workitem_testcase";
	public static final String SISS_SVN_STATOWORKITEM_TASK_FILE = "siss.svn.stato_workitem_task";
	public static final String SISS_SVN_STATOWORKITEM_RELEASEDIPROGETTO_FILE = "siss.svn.stato_workitem_releasediprogetto";
	public static final String SISS_SVN_STATOWORKITEM_RELEASEIT_FILE = "siss.svn.stato_workitem_releaseit";
	public static final String SISS_SVN_STATOWORKITEM_BUILD_FILE = "siss.svn.stato_workitem_build";
	public static final String SISS_SVN_STATOWORKITEM_TASKIT_FILE = "siss.svn.stato_workitem_taskit";
	public static final String SISS_SVN_STATOWORKITEM_ANOMALIAASSISTENZA_FILE = "siss.svn.stato_workitem_anomaliaassistenza";
	public static final String SISS_SVN_STATOWORKITEM_RICHIESTAGESTIONE_FILE = "siss.svn.stato_workitem_richiestagestione";
	public static final String SISS_SVN_STATOWORKITEM_RELEASESERVIZI_FILE = "siss.svn.stato_workitem_releaseservizi";
	public static final String SISS_SVN_STATOWORKITEM_CLASSIFICATORE_FILE = "siss.svn.stato_workitem_classificatore";
	
	public static final String SISS_SVN_PROJECTROLES_FILE_SVILUPPO = "siss.svn.project_roles.sviluppo";
	public static final String SISS_SVN_PROJECTROLES_FILE_DEMAND = "siss.svn.project_roles.demand";
	public static final String SISS_SVN_PROJECTROLES_FILE_DEMAND2016 = "siss.svn.project_roles.demand2016";
	public static final String SISS_SVN_PROJECTROLES_FILE_ASSISTENZA = "siss.svn.project_roles.assistenza";
	public static final String SISS_SVN_PROJECTROLES_FILE_INTEGRAZIONETECNICA = "siss.svn.project_roles.integrazionetecnica";
	public static final String SISS_SVN_PROJECTROLES_FILE_SERVIZIDEPLOY = "siss.svn.project_roles.servizideploy";
	public static final String SISS_SVN_PROJECTROLES_GLOBAL_1_FILE = "siss.svn.project_roles.global.1";
	public static final String SISS_SVN_PROJECTROLES_GLOBAL_2_FILE = "siss.svn.project_roles.global.2";
	public static final String SISS_SVN_USER_ROLES_FILE = "siss.svn.userroles";
	public static final String SISS_SVN_SCHEDE_SERVIZIO_FILE = "siss.svn.schede_servizio";
	
	//SVN SISS LINKED_WORKITEM_ROLES
	public static final String SISS_SVN_LINKEDWORKITEMROLES_SVILUPPO = "siss.svn.linked_workitem_roles.sviluppo";
	public static final String SISS_SVN_LINKEDWORKITEMROLES_DEMAND = "siss.svn.linked_workitem_roles.demand";
	public static final String SISS_SVN_LINKEDWORKITEMROLES_ASSISTENZA = "siss.svn.linked_workitem_roles.assistenza";
	public static final String SISS_SVN_LINKEDWORKITEMROLES_INTEGRAZIONETECNICA = "siss.svn.linked_workitem_roles.integrazionetecnica";
	public static final String SISS_SVN_LINKEDWORKITEMROLES_SERVIZIDEPLOY = "siss.svn.linked_workitem_roles.servizideploy";
	public static final String SISS_SVN_LINKEDWORKITEMROLES_DEMAND2016 = "siss.svn.linked_workitem_roles.demand2016";
	
	public static final String DMALM_STAGING_DAY_DELETE = "dmalm.staging.day.delete";
	public static final String SCHEDULAZIONE_BO = "schedulazione.bo.enable";
	public static final String LOG4J_ROOT_FILE_PATH = "dmalm.log4j.root.path";
	public static final String UPDATE_DESCRIZIONE_ENABLER="update.descrizione.enable";
	public static final String DMALM_TARGET_LOG_DELETE="dmalm.log.elapseday.delete";
	public static final String DMALM_DEADLOCK_RETRY="dmalm.deadlock.retry";
	public static final String DMALM_DEADLOCK_WAIT="dmalm.deadlock.wait";
	
	public static final String DMALM_SCHEMA_POLARION = "dmalm.schema.polarion";
	public static final String DMALM_SCHEMA_DATAMART_STAGING = "dmalm.schema.datamart.staging";
	public static final String DMALM_SCHEMA_DATAMART_TARGET = "dmalm.schema.datamart.target";
	public static final String DMALM_SCHEMA_FONTE_ELETTRA = "dmalm.fonte.elettra.schema";
	
	//FILE PROJECT UNMARKED
	public static final String UNMARKED_PROJECTS_FILE_SIRE = "sire.file.unmarked";
	public static final String UNMARKED_PROJECTS_FILE_SISS = "siss.file.unmarked";
	
	//MAIL PARAMETERS
	public static final String MAIL_TO = "mail.to";
	public static final String MAIL_FROM = "mail.from";
	public static final String MAIL_SERVER = "mail.server";
	public static final String MAIL_LOG_PATH="mail.log.path";

	//PK
	public static final String WORKITEM_PK_NEXTVAL="dmalm_workitem_pk_nextval.sql";
	public static final String PERSONALE_PK_MAXVAL="dmalm_personale_pk_maxval.sql";
	public static final String PERSONALE_PK_MAXVAL_BY_NOME_COGNOME="dmalm_personale_pk_maxval_by_nome_cognome.sql";
	public static final String USER_ROLES_SGR_PK_MAXVAL="dmalm_user_roles_sgr_pk_maxval.sql";

	//SFERA
	public static final String NOTLINKED_PROJSF_WORKITEM="notlinked_projsf_wi.sql";;
	public static final String DMALM_SFERA_PATH="dmalm.sfera.path";
	public static final String DMALM_SFERA_CURRENT_PATH ="dmalm.sfera.current";
	public static final String DMALM_SFERA_CSV = "dmalm.sfera.csv";
	public static final String SFERA_MISURA_PK_NEXTVAL="dmalm_misura_pk_nextval.sql";;

	public static final String SVI_MEV_ORDER = "sql_svi_mev_msr_format.sql";
	public static final String SQL_STG_MISURES = "stg_misure.sql";
	public static final String SQL_PROGETTI_SFERA = "stg_progetti_sfera.sql";
	public static final String SQL_ASM = "stg_asm.sql";
	public static final String SQL_ASM_ELIMINATE = "stg_asm_eliminate.sql";
	public static final String SQL_PROGETTI_ELIMINATI = "stg_progetti_eliminati.sql";
	public static final String SQL_MISURE_ELIMINATE = "stg_misure_eliminate.sql";
	
	//MPS
	public static final String DMALM_MPS_PATH="dmalm.mps.path";
	public static final String DMALM_MPS_PREFISSO_AMBIENTE="dmalm.mps.prefisso.ambiente";
	public static final String SQL_STG_ATTIVITAES = "stg_mps_attivita.sql";
	public static final String SQL_STG_CONTRATTIES = "stg_mps_contratti.sql";
	public static final String SQL_STG_FIRMATARI_VERBALIES = "stg_mps_firmatari_verbale.sql";
	public static final String SQL_STG_RESPONSABILI_CONTRATTOES = "stg_mps_responsabili_contratto.sql";
	public static final String SQL_STG_RESPONSABILI_OFFERTAES = "stg_mps_responsabili_offerta.sql";
	public static final String SQL_STG_VERBALIES = "stg_mps_verbali.sql";
	public static final String SQL_STG_RILASCI_VERBALIES = "stg_mps_rilasci_verbali.sql";
	public static final String SQL_STG_RILASCIES = "stg_mps_rilasci.sql";

	public static final String SQL_ELETTRAFUNZIONALITA_BY_MODULO_PK = "funzionalita_by_modulo_pk.sql";
}