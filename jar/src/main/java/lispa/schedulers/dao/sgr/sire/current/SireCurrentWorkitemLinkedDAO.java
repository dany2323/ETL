package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentWorkitemLinked;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentWorkitemLinkedDAO.class);

	public static long fillSireCurrentWorkitemLinked() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;
		long n_righe_inserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSIRECurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap; 

			PostgresTemplates dialect = new PostgresTemplates() 
			{
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> cfworkitems = query.from(fonteWorkitemLinked).list(
					
					new QTuple(
							StringTemplate.create("CASE WHEN " + fonteWorkitemLinked.cSuspect + "= 'true' THEN 1 ELSE 0 END as c_suspect"), 
							StringTemplate.create("SUBSTRING(" + fonteWorkitemLinked.cRole + ",0,4000) as c_role"),
							StringTemplate.create("(SELECT a.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " a WHERE a.c_id = " + fonteWorkitemLinked.fkUriPWorkitem + ") as fk_p_workitem"), 
							StringTemplate.create("SUBSTRING(" + fonteWorkitemLinked.cRevision + ",0,4000) as c_revision"),
							StringTemplate.create("(SELECT a.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " a WHERE a.c_id = " + fonteWorkitemLinked.fkUriPWorkitem + ") as fk_uri_p_workitem"), 
							StringTemplate.create("(SELECT b.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " b WHERE b.c_id = " + fonteWorkitemLinked.fkUriWorkitem + ") as fk_uri_workitem"), 
							StringTemplate.create("(SELECT c.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " c WHERE c.c_id = " +  fonteWorkitemLinked.fkUriWorkitem + ") as fk_workitem")
							)
					
					/*
					new QTuple(
							StringTemplate.create("CASEWHEN ("
									+ fonteWorkitemLinked.cSuspect
									+ "= 'true', 1,0 )"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.cRole
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkPWorkitem
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.cRevision
											+ ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkitemLinked.fkUriPWorkitem
									+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkUriWorkitem
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkWorkitem
											+ ",0,4000)"))*/
					
					);

			logger.debug("fillSireCurrentWorkitemLinked - cfworkitems.sizw: " + cfworkitems.size());
			
			SQLInsertClause insert = new SQLInsertClause(oracleConnection, dialect, stgProjectgroup);
			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();
				
				insert.columns(stgProjectgroup.cSuspect,
								stgProjectgroup.cRole,
								stgProjectgroup.fkPWorkitem,
								stgProjectgroup.cRevision,
								stgProjectgroup.fkUriPWorkitem,
								stgProjectgroup.fkUriWorkitem,
								stgProjectgroup.fkWorkitem,
								stgProjectgroup.dataCaricamento,
								stgProjectgroup.dmalmCurrentWorkitemLinkedPk)
						.values(el[0],
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6],
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("CURRENT_WORK_LINKED_SEQ.nextval"))
						.addBatch();
				
				n_righe_inserite++;
				
				if (!insert.isEmpty()) {
					if (n_righe_inserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect, stgProjectgroup);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				oracleConnection.commit();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			n_righe_inserite = 0;
			if (oracleConnection != null) {
				oracleConnection.rollback();
			}

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
		}

		return n_righe_inserite;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.lt(dataEsecuzione).or(
							stgProjectgroup.dataCaricamento.eq(DataEsecuzione
									.getInstance().getDataEsecuzione())))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSireCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
