SPOOL 150_ATTACHMENT_SEQ.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE SEQUENCE ATTACHMENT_SEQ 
    INCREMENT BY 1 
    MAXVALUE 9999999999999999999999999999 
    MINVALUE 1 
    CACHE 20; 
SPOOL OFF;
