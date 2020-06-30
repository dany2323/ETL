package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentWorkitem;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentWorkitem;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmStgCurrentWorkitems;
import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class CurrentWorkitemsDAO {

	private static Logger logger = Logger.getLogger(CurrentWorkitemsDAO.class);

	static SQLTemplates  dialect = new HSQLDBTemplates(){ {
		setPrintSchema(true);
	}};

	
	private static SireCurrentWorkitem qSireCurrentWorkitem = SireCurrentWorkitem.workitem;

	private static SissCurrentWorkitem qSissCurrentWorkitem = SissCurrentWorkitem.workitem;

	private static QDmalmStgCurrentWorkitems qStgCurrentWorkitems = QDmalmStgCurrentWorkitems.dmalmStgCurrentWorkitems;

	public static void fillSireCurrentWorkitems() throws SQLException,DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			List<Tuple> allSireWorkitems = query.distinct()
					.from(qSireCurrentWorkitem)
					.where(qSireCurrentWorkitem.cId.isNotNull())
					.list(qSireCurrentWorkitem.cPk,qSireCurrentWorkitem.cId, qSireCurrentWorkitem.cType);
			
			
			int batchcounter = 0;
			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					qStgCurrentWorkitems);
			for (Tuple row : allSireWorkitems) {
				insert.columns(qStgCurrentWorkitems.stgPk,qStgCurrentWorkitems.codice,
						qStgCurrentWorkitems.type,
						qStgCurrentWorkitems.idRepository)
						.values(row.get(qSireCurrentWorkitem.cPk),row.get(qSireCurrentWorkitem.cId),
								row.get(qSireCurrentWorkitem.cType), "SIRE")
						.addBatch();
				batchcounter++;

				if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
						&& !insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							qStgCurrentWorkitems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			
			connection.commit();
		} catch (DAOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e1) {
			logger.error(e1.getMessage());
			e1.printStackTrace();
		}
		finally {
		
			if(cm != null) cm.closeConnection(connection);
		}
	}
	public static void fillSissCurrentWorkitems() throws SQLException,DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			List<Tuple> allSissWorkitems = query
					.from(qSissCurrentWorkitem)
					.where(qSissCurrentWorkitem.cId.isNotNull())
					.list(qSissCurrentWorkitem.cPk,qSissCurrentWorkitem.cId, qSissCurrentWorkitem.cType);
			int batchcounter = 0;

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					qStgCurrentWorkitems);

			for (Tuple row : allSissWorkitems) {
				
				insert.columns(qStgCurrentWorkitems.stgPk,
						qStgCurrentWorkitems.codice,
						qStgCurrentWorkitems.type,
						qStgCurrentWorkitems.idRepository)
						.values(row.get(qSissCurrentWorkitem.cPk),row.get(qSissCurrentWorkitem.cId),
								row.get(qSissCurrentWorkitem.cType), "SISS").addBatch();
				batchcounter++;
				if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
						&& !insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							qStgCurrentWorkitems);
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
			}
			connection.commit();
		}
		 catch (DAOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			finally {
				
				if(cm != null) cm.closeConnection(connection);
			}
		
	}
	public static void deleteCurrentWorkitems() throws DAOException {
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QDmalmStgCurrentWorkitems currentWorkitems  = QDmalmStgCurrentWorkitems.dmalmStgCurrentWorkitems;

			new SQLDeleteClause(connection, dialect, currentWorkitems)
					.execute();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
		
	}
	
	
}
