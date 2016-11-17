package lispa.schedulers.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.QDmalmEsitiCaricamenti;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class EsitiCaricamentoDAO
{
	
	private static Logger logger = Logger.getLogger(EsitiCaricamentoDAO.class);
	
	public static void insert(Date dataEsecuzione, String target, String stato, Date dtInizioCaricamento, Date dtFineCaricamento, int righeCaricate, int righeModificate, int righeErrate, int righeScartate) throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(true);

			QDmalmEsitiCaricamenti qEsitiCaricamento = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;
			
			logger.debug("insert -> DataEsecuzione "+dataEsecuzione+ ", target "+target+ ", stato "+stato+ ", DataInizioCaricamento "+dtInizioCaricamento+", DataFineCaricamento "+dtFineCaricamento+", righeCaricate "+righeCaricate+", righeModificate "+righeModificate+", righeErrate "+righeErrate+ ", righeScartate "+righeScartate);

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			
			new SQLInsertClause(connection, dialect, qEsitiCaricamento)
			.columns(
					qEsitiCaricamento.dataCaricamento,
					qEsitiCaricamento.entitaTarget,
					qEsitiCaricamento.statoEsecuzione,
					qEsitiCaricamento.dtInizioCaricamento,
					qEsitiCaricamento.dtFineCaricamento,
					qEsitiCaricamento.righeCaricate,
					qEsitiCaricamento.righeModificate,
					qEsitiCaricamento.righeErrate,
					qEsitiCaricamento.righeScartate
					
					)
		    .values(dataEsecuzione,
		    		target,
		    		stato,
		    		dtInizioCaricamento,
		    		dtFineCaricamento,
		    		righeCaricate,
		    		righeModificate,
		    		righeErrate,
		    		righeScartate
					)
		    .execute();
			
			connection.commit();
			
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	
	public static void deleteByTarget( Timestamp dataCaricamento, String target) throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			connection.setAutoCommit(false);

			QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

			SQLTemplates dialect = new HSQLDBTemplates(); 
			
			new SQLDeleteClause(connection, dialect, esiti)
						.where(esiti.dataCaricamento.eq(dataCaricamento))
						.where(esiti.entitaTarget.eq(target))
					    .execute();
			
			connection.commit();

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			
				throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
		
	}
		
		public static void delete( Timestamp dataCaricamento) throws DAOException, SQLException
		{

			ConnectionManager cm = null;
			Connection connection = null;

			try{
				cm = ConnectionManager.getInstance();
				connection = cm.getConnectionOracle();
				
				connection.setAutoCommit(false);

				QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

				SQLTemplates dialect = new HSQLDBTemplates(); 
				
				new SQLDeleteClause(connection, dialect, esiti)
							.where(esiti.dataCaricamento.eq(dataCaricamento))
						    .execute();
				
				connection.commit();

			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				
				
					throw new DAOException(e);
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
				
				QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;
				
				new SQLDeleteClause(connection, dialect, esiti)
						.where(esiti.dataCaricamento.eq(
								date)).execute();

			} catch (Exception e) 
			{
				
			} 
			finally 
			{
				if(cm != null) cm.closeConnection(connection);
			}
		}
	
	public static void updateCleaningInfo( Timestamp dataCaricamento, String target, int righeerrate, int righescartate ) throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			connection.setAutoCommit(false);

			QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

			SQLTemplates dialect = new HSQLDBTemplates(); 
			
			new SQLUpdateClause(connection, dialect, esiti)
			    .where(esiti.dataCaricamento.eq(dataCaricamento))
			    .where(esiti.entitaTarget.eq(target))
			    .set(esiti.righeErrate 		, esiti.righeErrate.add(righeerrate))
			    .set(esiti.righeScartate 		, esiti.righeScartate.add(righescartate))
			    .execute();
			
			connection.commit();

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			
				throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	
	public static void updateETLTargetInfo( Timestamp dataCaricamento, String stato, Timestamp dtInizioCaricamento, Timestamp dtFineCaricamento,  String target, int righeNuove, int righeModificate ) throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;

		logger.debug("updateETLTargetInfo -> DataEsecuzione "+dataCaricamento+ ", target "+target+ ", stato "+stato+ ", DataInizioCaricamento "+dtInizioCaricamento+", DataFineCaricamento "+dtFineCaricamento+", righeCaricate "+righeNuove+", righeModificate "+righeModificate);

		
		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			connection.setAutoCommit(false);

			QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

			SQLTemplates dialect = new HSQLDBTemplates(); 
			
			new SQLUpdateClause(connection, dialect, esiti)
			    .where(esiti.dataCaricamento.eq(dataCaricamento))
			    .where(esiti.entitaTarget.eq(target))
			    .set(esiti.statoEsecuzione 		, stato)
			    .set(esiti.dtInizioCaricamento 		, dtInizioCaricamento)
			    .set(esiti.dtFineCaricamento 		, dtFineCaricamento)
			    .set(esiti.righeCaricate 		, righeNuove)
			    .set(esiti.righeModificate 		, righeModificate)
			    .execute();
			
			connection.commit();

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

	public static void inserisciRecordSentinella() {
		
		QDmalmEsitiCaricamenti qEsitiCaricamento = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
	
		try {
			
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			new SQLInsertClause(connection, dialect, qEsitiCaricamento)
				.columns(qEsitiCaricamento.dataCaricamento, 
						qEsitiCaricamento.entitaTarget,
						qEsitiCaricamento.statoEsecuzione,
						qEsitiCaricamento.dtInizioCaricamento,
						qEsitiCaricamento.dtFineCaricamento,
						qEsitiCaricamento.righeCaricate,
						qEsitiCaricamento.righeErrate,
						qEsitiCaricamento.righeModificate,
						qEsitiCaricamento.righeScartate
						)
				.values(DataEsecuzione.getInstance().getDataEsecuzione(),
						DmAlmConstants.TARGET_ALL,
						DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE,
						DateUtils.setDtInizioValidita1900(),
						DateUtils.setDtFineValidita9999(),
						0,
						0,
						0,
						0)
				.execute();
			
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			}
		finally {
			try {
				if(cm != null) cm.closeConnection(connection);
			}
			catch(Exception e){
				
			}
		}
		
	}

	public static void recoverEsitiCaricamento() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

			new SQLDeleteClause(connection, dialect, esiti)
			.where(esiti.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
		
	






}