SPOOL 320_TYPE_CALIPSO.sql

create or replace TYPE "DM_ALMSTGCALIPSOSCHEDASERVTYPE" as object(
    "Codice servizio" VARCHAR2(500),
	"Servizio di business" VARCHAR2(500),
	"Ambito" VARCHAR2(500),
	"Cliente" VARCHAR2(500),
	"Responsabile di Struttura" VARCHAR2(500),
	"Responsabile del Servizio" VARCHAR2(500),
	"Responsabile di Gestione" VARCHAR2(500),
	"Referente di Gestione" VARCHAR2(500),
	"Referente dell applicazione" VARCHAR2(500),
	"Software a supporto" VARCHAR2(500),
	"Ambito manutenzione ordinar sw" VARCHAR2(500),
	"Tipologia Incarico" VARCHAR2(500),
	"Scheda Incarico" VARCHAR2(500),
	"Stato del servizio" VARCHAR2(500),
	"Tipologia Infrastruttura" VARCHAR2(500),
	"Classe di servizio infrstrttrl" VARCHAR2(500),
	"Ambito ASSI e GEST per RL" VARCHAR2(500),
	"Data Ultimo Aggiornamento" VARCHAR2(500)
);