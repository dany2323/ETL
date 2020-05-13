SPOOL 305_V_REL_DEMAND_INT_TECNICA.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
CREATE OR REPLACE FORCE VIEW V_REL_DEMAND_INT_TECNICA
(
   FK_WI_DEMAND,
   TIPO_WI_DEMAND,
   FK_WI_INT_TECNICA,
   TIPO_WI_INT_TECNICA
)
AS
(select lw.FK_WI_PADRE, lw.TIPO_WI_PADRE, lw.FK_WI_FIGLIO, lw.TIPO_WI_FIGLIO
 from DMALM_LINKED_WORKITEMS lw
 join DMALM_TEMPLATE_DEMAND td on lw.FK_WI_PADRE = td.fk_wi
 join DMALM_TEMPLATE_INT_TECNICA tit on lw.FK_WI_FIGLIO = tit.fk_wi
 union
 select lw.FK_WI_FIGLIO, lw.TIPO_WI_FIGLIO, lw.FK_WI_PADRE, lw.TIPO_WI_PADRE
 from DMALM_LINKED_WORKITEMS lw
 join DMALM_TEMPLATE_DEMAND td on lw.FK_WI_FIGLIO = td.fk_wi
 join DMALM_TEMPLATE_INT_TECNICA tit on lw.FK_WI_PADRE = tit.fk_wi);
SPOOL OFF;
