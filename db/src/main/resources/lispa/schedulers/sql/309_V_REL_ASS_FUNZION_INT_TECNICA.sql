SPOOL 309_V_REL_ASS_FUNZION_INT_TECNICA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FORCE VIEW V_REL_ASS_FUNZION_INT_TECNICA
(
   FK_WI_ASS_FUNZIONALE,
   TIPO_WI_ASS_FUNZIONALE,
   FK_WI_INT_TECNICA,
   TIPO_WI_INT_TECNICA
)
AS
(select lw.FK_WI_PADRE, lw.TIPO_WI_PADRE, lw.FK_WI_FIGLIO, lw.TIPO_WI_FIGLIO
 from DMALM_LINKED_WORKITEMS lw
 join DMALM_TEMPLATE_ASS_FUNZIONALE taf on lw.FK_WI_PADRE = taf.fk_wi
 join DMALM_TEMPLATE_INT_TECNICA tit on lw.FK_WI_FIGLIO = tit.fk_wi
 union
 select lw.FK_WI_FIGLIO, lw.TIPO_WI_FIGLIO, lw.FK_WI_PADRE, lw.TIPO_WI_PADRE
 from DMALM_LINKED_WORKITEMS lw
 join DMALM_TEMPLATE_ASS_FUNZIONALE taf on lw.FK_WI_FIGLIO = taf.fk_wi
 join DMALM_TEMPLATE_INT_TECNICA tit on lw.FK_WI_PADRE = tit.fk_wi);
SPOOL OFF;
