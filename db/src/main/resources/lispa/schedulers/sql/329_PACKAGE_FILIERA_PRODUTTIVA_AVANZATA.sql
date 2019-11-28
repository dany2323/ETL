SPOOL 329_PACKAGE_FILIERA_PRODUTTIVA_AVANZATA.sql

SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
  
create or replace PACKAGE FILIERA_PRODUTTIVA_AVANZATA AS 

  PROCEDURE REFRESH_ALL_DATA_FILIERA;

END FILIERA_PRODUTTIVA_AVANZATA;

create or replace PACKAGE BODY FILIERA_PRODUTTIVA_AVANZATA AS

PROCEDURE REFRESH_ALL_DATA_FILIERA AS
BEGIN
    DBMS_MVIEW.REFRESH('DMALM_FILIERA_PRODUTTIVA_AVANZ', 'C');    
END REFRESH_ALL_DATA_FILIERA;

END FILIERA_PRODUTTIVA_AVANZATA;