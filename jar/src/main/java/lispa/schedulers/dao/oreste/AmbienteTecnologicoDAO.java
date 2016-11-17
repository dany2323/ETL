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
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmAmbTecnologico;
import lispa.schedulers.queryimplementation.staging.oreste.QStgAmbienteTecnologico;
import lispa.schedulers.utils.DateUtils;

public class AmbienteTecnologicoDAO
{

	private static Logger logger = Logger.getLogger(AmbienteTecnologicoDAO.class); 

	public static long fillAmbienteTecnologico() throws Exception
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QStgAmbienteTecnologico   stgAmbienteTecnologico  = QStgAmbienteTecnologico.stgAmbienteTecnologico;
			OresteDmAlmAmbTecnologico  viewAmbienteTecnologico  = OresteDmAlmAmbTecnologico.dmAlmAmbTecnologico;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> ambientiTecnologici = query.from(viewAmbienteTecnologico).list(viewAmbienteTecnologico.all());

			for(Tuple row : ambientiTecnologici) {
				n_righe_inserite += 

						new SQLInsertClause(connection, dialect, stgAmbienteTecnologico)
				.columns(
						stgAmbienteTecnologico.idAmbienteTecnologico, 
						stgAmbienteTecnologico.idEdma,
						stgAmbienteTecnologico.idEdmaPadre,
						stgAmbienteTecnologico.nomeAmbienteTecnologico,
						stgAmbienteTecnologico.descrAmbienteTecnologico,
						stgAmbienteTecnologico.siglaModulo,
						stgAmbienteTecnologico.siglaProdotto,
						stgAmbienteTecnologico.clasfArchiRiferimento,
						stgAmbienteTecnologico.clasfInfrastrutture,
						stgAmbienteTecnologico.clasfSo,
						stgAmbienteTecnologico.clasfTipiLayer,
						stgAmbienteTecnologico.versioneSo,	
						stgAmbienteTecnologico.dataCaricamento,
						stgAmbienteTecnologico.idAmbTecnologicoPk

						)
						.values( 
								row.get(viewAmbienteTecnologico.idAmbienteTecnologico), 
								row.get(viewAmbienteTecnologico.idEdma),
								row.get(viewAmbienteTecnologico.idEdmaPadre),
								row.get(viewAmbienteTecnologico.nomeAmbienteTecnologico),
								row.get(viewAmbienteTecnologico.descrAmbienteTecnologico),
								row.get(viewAmbienteTecnologico.siglaModulo) != null ? row.get(viewAmbienteTecnologico.siglaModulo).trim() : null,
										row.get(viewAmbienteTecnologico.siglaProdotto) != null ? row.get(viewAmbienteTecnologico.siglaProdotto).trim() : null,
												row.get(viewAmbienteTecnologico.clasfArchiRiferimento),
												row.get(viewAmbienteTecnologico.clasfInfrastrutture),
												row.get(viewAmbienteTecnologico.clasfSo),
												row.get(viewAmbienteTecnologico.clasfTipiLayer),
												row.get(viewAmbienteTecnologico.versioneSo),					
												DataEsecuzione.getInstance().getDataEsecuzione(),
												StringTemplate.create("STG_AMBIENTE_TECNOLOGICO_SEQ.nextval")

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

	public static List<Tuple> getDuplicateNomeAmbienteTecnologico(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> ambiente = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			ambiente = 

					query.from(qstgAmbienteTecnologico)
					.where(qstgAmbienteTecnologico.nomeAmbienteTecnologico.in(
							new SQLSubQuery()
							.from(qstgAmbienteTecnologico)
							.where(qstgAmbienteTecnologico.dataCaricamento.eq(dataEsecuzione))
							.groupBy(qstgAmbienteTecnologico.nomeAmbienteTecnologico)
							.having(qstgAmbienteTecnologico.nomeAmbienteTecnologico.count().gt(1) )
							.list(qstgAmbienteTecnologico.nomeAmbienteTecnologico))
							)
							.where(qstgAmbienteTecnologico.dataCaricamento.eq(dataEsecuzione))
							.list(qstgAmbienteTecnologico.all()) ;



		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return ambiente;
	}

	public static List<Tuple> getNomeAmbienteTecnologicoNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> ambienti = new ArrayList<Tuple>();

		try
		{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			ambienti = 

					query.from(qstgAmbienteTecnologico)
					.where(qstgAmbienteTecnologico.dataCaricamento.eq(dataEsecuzione))
					.where(qstgAmbienteTecnologico.nomeAmbienteTecnologico.isNull())
					.list(qstgAmbienteTecnologico.all());

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return ambienti;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qstgAmbienteTecnologico)
			.where(qstgAmbienteTecnologico.dataCaricamento.lt(dataEsecuzioneDeleted).or(qstgAmbienteTecnologico.dataCaricamento
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

			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qstgAmbienteTecnologico)
			.where(qstgAmbienteTecnologico.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

	public static void recoverAmbienteTecnologico() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qstgAmbienteTecnologico)
			.where(qstgAmbienteTecnologico.dataCaricamento.eq(
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