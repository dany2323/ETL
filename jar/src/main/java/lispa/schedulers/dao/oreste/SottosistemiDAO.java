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
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmSottosistemi;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

public class SottosistemiDAO
{

	private static Logger logger = Logger.getLogger(SottosistemiDAO.class); 

	public static long fillSottosistemi() throws Exception
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QStgSottosistemi   stgSottositemi  = QStgSottosistemi.stgSottosistemi;
			OresteDmAlmSottosistemi  viewSottosistemi  = OresteDmAlmSottosistemi.dmAlmSottosistemi;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> sottosistemi = query.from(viewSottosistemi).list(viewSottosistemi.all());

			for(Tuple row : sottosistemi) {
				n_righe_inserite += new SQLInsertClause(connection, dialect, stgSottositemi)
				.columns(
						stgSottositemi.idEdmaSottosistema, 
						stgSottositemi.idEdmaPadreSottosistema,
						stgSottositemi.idSottosistema,
						stgSottositemi.tipoOggetto,
						stgSottositemi.siglaProdottoSottosistema,
						stgSottositemi.siglaSottosistema,
						stgSottositemi.nomeSottosistema,
						stgSottositemi.descrizioneSottosistema,
						stgSottositemi.sottosistemaAnnullato,
						stgSottositemi.dfvSottosistemaAnnullato,
						stgSottositemi.edmaRespoSottosistema,
						stgSottositemi.clasfBaseDatiEtl,
						stgSottositemi.clasfBaseDatiLettura,
						stgSottositemi.clasfBaseDatiScrittura,
						stgSottositemi.clasfTipoSottosistema,
						stgSottositemi.dataCaricamento,
						stgSottositemi.dmalmSottosistemimPk
						)
						.values( 
								row.get(viewSottosistemi.idEdmaSottosistema), 
								row.get(viewSottosistemi.idEdmaPadreSottosistema),
								row.get(viewSottosistemi.idSottosistema),
								row.get(viewSottosistemi.tipoOggetto),
								row.get(viewSottosistemi.siglaProdottoSottosistema) != null ? row.get(viewSottosistemi.siglaProdottoSottosistema).trim() : null,
										row.get(viewSottosistemi.siglaSottosistema) != null ? row.get(viewSottosistemi.siglaSottosistema).trim() : null,
												row.get(viewSottosistemi.nomeSottosistema),
												row.get(viewSottosistemi.descrizioneSottosistema),
												row.get(viewSottosistemi.sottosistemaAnnullato),
												row.get(viewSottosistemi.dfvSottosistemaAnnullato),
												StringUtils.getMaskedValue(row.get(viewSottosistemi.edmaRespoSottosistema)),
												row.get(viewSottosistemi.clasfBaseDatiEtl),
												row.get(viewSottosistemi.clasfBaseDatiLettura),
												row.get(viewSottosistemi.clasfBaseDatiScrittura),
												row.get(viewSottosistemi.clasfTipoSottosistema),
												DataEsecuzione.getInstance().getDataEsecuzione(),
												StringTemplate.create("STG_SOTTOSISTEMI_SEQ.nextval")

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

	public static List<Tuple> checkStatoSottosistemiProdottoAnnullato(QStgSottosistemi  qstgSottosistemi, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> el = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			el = 

					query.from(qstgSottosistemi)
					.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
					.where(qstgSottosistemi.nomeSottosistema.notLike("%ANNULLATO%"))
					.where(qstgSottosistemi.siglaProdottoSottosistema.equalsIgnoreCase(siglaProdotto))
					.list(qstgSottosistemi.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return el;
	}

	public static List<Tuple> getSottosistemaByProdotto(QStgSottosistemi qstgSottosistemi, String siglaProdotto,Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgSottosistemi)
					.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
					.where(qstgSottosistemi.siglaProdottoSottosistema.equalsIgnoreCase(siglaProdotto))
					.list(qstgSottosistemi.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return funzionalita;
	}

	public static List<Tuple> getSottosistemiAnnullatiByProdotto(QStgSottosistemi qstgSottosistemi, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgSottosistemi)
					.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
					.where(qstgSottosistemi.siglaProdottoSottosistema.eq(siglaProdotto))
					.where(qstgSottosistemi.nomeSottosistema.like("%ANNULLATO%"))
					.list(qstgSottosistemi.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return funzionalita;
	}

	public static List<Tuple> getDuplicateNomeSottosistema(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> sottosistemi = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			sottosistemi = 

					query.from(qstgSottosistemi)
					.where( qstgSottosistemi.nomeSottosistema.in
							(
									new SQLSubQuery().from(qstgSottosistemi)
									.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
									.groupBy(qstgSottosistemi.siglaProdottoSottosistema)
									.groupBy(qstgSottosistemi.nomeSottosistema)
									.having(qstgSottosistemi.nomeSottosistema.count().gt(1) )
									.list(qstgSottosistemi.nomeSottosistema)
									)
							)
							.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
							.where(qstgSottosistemi.siglaProdottoSottosistema.isNotNull()) // di uno specifico prodotto quindi sigla prodotto != null
							.list(qstgSottosistemi.all()) ;


		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return sottosistemi;
	}

	public static List<Tuple> getNomiSottosistemiNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> sottosistemi = new ArrayList<Tuple>();


		//select * from DMALM_STG_PROD_ARCHITETTURE where SIGLA_PRODOTTO is null

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			sottosistemi = 

					query.from(qstgSottosistemi)
					.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
					.where(qstgSottosistemi.nomeSottosistema.isNull()).list(qstgSottosistemi.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return sottosistemi;
	}

	public static List<Tuple> getSottosistemiAnnullati(QStgSottosistemi  qstgSottosistemi, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> sottosistemiAnnullati = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			sottosistemiAnnullati = 

					query.from(qstgSottosistemi)
					.where(qstgSottosistemi.dataCaricamento.eq(dataEsecuzione))
					.where(qstgSottosistemi.nomeSottosistema
							.like("%ANNULLATO%"))
							.list(qstgSottosistemi.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return sottosistemiAnnullati;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			new SQLDeleteClause(connection, dialect, qstgSottosistemi)
			.where(qstgSottosistemi.dataCaricamento.lt(dataEsecuzioneDeleted).or(qstgSottosistemi.dataCaricamento
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

			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			new SQLDeleteClause(connection, dialect, qstgSottosistemi)
			.where(qstgSottosistemi.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	public static void recoverSottosistemi() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			new SQLDeleteClause(connection, dialect, qstgSottosistemi)
			.where(qstgSottosistemi.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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