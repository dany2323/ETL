package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmClassificatori;
import lispa.schedulers.queryimplementation.staging.oreste.QStgClassificatori;
import lispa.schedulers.utils.DateUtils;

public class ClassificatoriDAO
{
	private static Logger logger = Logger.getLogger(ClassificatoriDAO.class); 

	public static long fillClassificatori() throws Exception
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			OresteDmAlmClassificatori  viewClassificatori  = OresteDmAlmClassificatori.dmAlmClassificatori;
			QStgClassificatori  stgClassificatori  = QStgClassificatori.stgClassificatori;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			//			CODICE_TIPOLOGIA	VARCHAR2
			//			CODICE_CLASSIFICATORE	VARCHAR2
			//			ID	NUMBER
			//			DATA_CARICAMENTO	TIMESTAMP(6)

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> classificatori = query.from(viewClassificatori).list(viewClassificatori.all());

			for(Tuple row : classificatori) {
				new SQLInsertClause(connection, dialect, stgClassificatori)
				.columns(
						stgClassificatori.codiceTipologia, 
						stgClassificatori.codiceClassificatore,
						stgClassificatori.id,
						stgClassificatori.dataCaricamento,
						stgClassificatori.dmalmStgClassificatoriPk
						)
						.values( 
								row.get(viewClassificatori.codiceTipologia), 
								row.get(viewClassificatori.codiceClassificatore),
								row.get(viewClassificatori.id),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("STG_CLASSIFICATORI_SEQ.nextval")

								)
								.execute();

			}




			connection.commit();

		}
		catch (Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			
			n_righe_inserite=0;
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return n_righe_inserite;

	}

	public static List<Tuple> getAllClassificatori(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> classificatori = null;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			classificatori = query
					.from(stgClassificatori)
					.where(stgClassificatori.dataCaricamento.eq(dataEsecuzione))
					.list();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return classificatori;
	}

	public static List<Tuple> getDuplicateCodiciClassificatori(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> classificatori = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			classificatori = 


					query.from(stgClassificatori)
					.where(stgClassificatori.dataCaricamento.eq(dataEsecuzione))
					.where(stgClassificatori.codiceClassificatore.in(
							new SQLSubQuery()
							.from(stgClassificatori)
							.where(stgClassificatori.dataCaricamento.eq(dataEsecuzione))
							.where(stgClassificatori.codiceClassificatore.notLike("#ANNULLATO%"))
							.groupBy(stgClassificatori.codiceTipologia, stgClassificatori.codiceClassificatore)
							.having(stgClassificatori.codiceClassificatore.count().gt(1) )
							.list(stgClassificatori.codiceClassificatore)
							))
							.list(stgClassificatori.all());


		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return classificatori;
	}

	public static List<Tuple> getCodiciClassificatoriNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> classificatori = new ArrayList<Tuple>();

		/*
		select * from DMALM_STG_CLASSIFICATORI where CODICE_CLASSIFICATORE is null
		 */
		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			classificatori = 

					query.from(stgClassificatori)
					.where(stgClassificatori.codiceClassificatore.isNull())
					.where(stgClassificatori.dataCaricamento.eq(dataEsecuzione))
					.list(stgClassificatori.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return classificatori;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			new SQLDeleteClause(connection, dialect, stgClassificatori)
			.where(stgClassificatori.dataCaricamento.lt(dataEsecuzioneDeleted).or(stgClassificatori.dataCaricamento
					.gt(DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(),-1))))
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

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			new SQLDeleteClause(connection, dialect, stgClassificatori)
			.where(stgClassificatori.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e)
		
		{
			logger.debug(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}
	public static void  recoverClassificatori() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			new SQLDeleteClause(connection, dialect, stgClassificatori)
			.where(stgClassificatori.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}


}