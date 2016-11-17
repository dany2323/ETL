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
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.utils.DateUtils;

public class FunzionalitaDAO
{

	private static Logger logger = Logger.getLogger(FunzionalitaDAO.class); 

	private static OresteDmAlmFunzionalita  qViewFunzionalita  = OresteDmAlmFunzionalita.dmAlmFunzionalita;

	private static QStgFunzionalita qstgFunzionalita = QStgFunzionalita.stgFunzionalita;

	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

	public static long fillFunzionalita() throws Exception
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> funzionalita = query.from(qViewFunzionalita).list(qViewFunzionalita.all());

			for (Tuple row : funzionalita) 
			{

				n_righe_inserite += 

						new SQLInsertClause(connection, dialect, qstgFunzionalita)
				.columns(
						qstgFunzionalita.idEdmaFunzionalita, 
						qstgFunzionalita.idEdmaPadre,
						qstgFunzionalita.idFunzionalita,
						qstgFunzionalita.tipoFunzionalita,
						qstgFunzionalita.siglaProdotto,
						qstgFunzionalita.siglaSottosistema,
						qstgFunzionalita.siglaModulo,
						qstgFunzionalita.siglaFunzionalita,
						qstgFunzionalita.nomeFunzionalita,
						qstgFunzionalita.descrizione,
						qstgFunzionalita.funzionalitaAnnullata,
						qstgFunzionalita.dfvFunzionalitaAnnullata,
						qstgFunzionalita.clasfCategoria,
						qstgFunzionalita.clasfLinguaggioDiProg,
						qstgFunzionalita.clasfTipoElabor,
						qstgFunzionalita.dataCaricamento,
						qstgFunzionalita.dmalmFunzionalitaPk
						)
						.values( 
								row.get(qViewFunzionalita.idEdmaFunzionalita), 
								row.get(qViewFunzionalita.idEdmaPadre),
								row.get(qViewFunzionalita.idFunzionalita),
								row.get(qViewFunzionalita.tipoFunzionalita),
								row.get(qViewFunzionalita.siglaProdotto) != null ? row.get(qViewFunzionalita.siglaProdotto).trim() : null,
										row.get(qViewFunzionalita.siglaSottosistema) != null ? row.get(qViewFunzionalita.siglaSottosistema).trim() : null,
												row.get(qViewFunzionalita.siglaModulo) != null ? row.get(qViewFunzionalita.siglaModulo).trim() : null,
														row.get(qViewFunzionalita.siglaFunzionalita) != null ? row.get(qViewFunzionalita.siglaFunzionalita).trim() : null,
																row.get(qViewFunzionalita.nomeFunzionalita),
																row.get(qViewFunzionalita.descrizione),
																row.get(qViewFunzionalita.funzionalitaAnnullata),
																row.get(qViewFunzionalita.dfvFunzionalitaAnnullata),
																row.get(qViewFunzionalita.clasfCategoria),
																row.get(qViewFunzionalita.clasfLinguaggioDiProg),
																row.get(qViewFunzionalita.clasfTipoElabor),
																DataEsecuzione.getInstance().getDataEsecuzione(),
																StringTemplate.create("STG_FUNZIONALITA_SEQ.nextval")

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

	public static List<Tuple> checkStatoFunzionalitaProdottoAnnullato(QStgFunzionalita  qstgFunzionalita, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> el = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			el = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.nomeFunzionalita.notLike("%ANNULLATO%"))
					.where(qstgFunzionalita.siglaProdotto.equalsIgnoreCase(siglaProdotto))
					.list(qstgFunzionalita.all()) ;

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

	/**
	 * 
	 * @param qstgFunzionalita
	 * @param siglaSottosistema
	 * @return l'elenco delle funzionalita non annullate per il sottosistema annullato
	 * @throws Exception
	 */

	public static List<Tuple> checkStatoFunzionalitaSottosistemaAnnullato(QStgFunzionalita  qstgFunzionalita, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> el 			= new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			el = 
					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.nomeFunzionalita.notLike("%ANNULLATO%"))
					.where(qstgFunzionalita.siglaSottosistema.equalsIgnoreCase(siglaSottosistema))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> checkStatoFunzionalitaModuloAnnullato(QStgFunzionalita  qstgFunzionalita, String siglaModulo, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> el 			= new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			el = 
					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.nomeFunzionalita.notLike("%ANNULLATO%"))
					.where(qstgFunzionalita.siglaModulo.equalsIgnoreCase(siglaModulo))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaByProdotto(QStgFunzionalita qstgFunzionalita, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaProdotto.equalsIgnoreCase(siglaProdotto))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaByProdottoModulo(String siglaProdotto, String siglaModulo, Timestamp dataEsecuzione) throws Exception
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

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaProdotto.toUpperCase().trim().equalsIgnoreCase(siglaProdotto.toUpperCase()))
					.where(qstgFunzionalita.siglaModulo.toUpperCase().trim().equalsIgnoreCase(siglaModulo.toUpperCase()))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaBySottosistema(QStgFunzionalita qstgFunzionalita, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaSottosistema.equalsIgnoreCase(siglaSottosistema))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaAnnullateByProdotto(QStgFunzionalita qstgFunzionalita, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaProdotto.eq(siglaProdotto))
					.where(qstgFunzionalita.nomeFunzionalita.like("%ANNULLATO%"))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaAnnullateBySottosistema(QStgFunzionalita qstgFunzionalita, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaSottosistema.isNotNull())
					.where(qstgFunzionalita.siglaSottosistema.eq(siglaSottosistema))
					.where(qstgFunzionalita.nomeFunzionalita.like("%ANNULLATO%"))
					.list(qstgFunzionalita.all()) ;

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

	public static List<Tuple> getFunzionalitaAnnullateByModulo(QStgFunzionalita qstgFunzionalita, String siglaModulo, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaModulo.eq(siglaModulo))
					.where(qstgFunzionalita.nomeFunzionalita.like("%ANNULLATO%"))
					.list(qstgFunzionalita.all()) ;

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



	public static List<Tuple> getDuplicateSiglaFunzionalitaInsiemeProdotto(QStgFunzionalita  qstgFunzionalita, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalita = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.siglaFunzionalita.in
							(
									new SQLSubQuery()
									.from(qstgFunzionalita)
									.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
									.where(qstgFunzionalita.siglaSottosistema.isNull())
									.where(qstgFunzionalita.siglaModulo.isNull())
									.where(qstgFunzionalita.siglaFunzionalita.isNotNull())
									.groupBy(qstgFunzionalita.siglaProdotto)
									.groupBy(qstgFunzionalita.siglaFunzionalita)
									.having(qstgFunzionalita.siglaFunzionalita.count().gt(1) )
									.list(qstgFunzionalita.siglaFunzionalita))
							)
							.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
							.where(qstgFunzionalita.siglaSottosistema.isNull())
							.where(qstgFunzionalita.siglaModulo.isNull())
							.where(qstgFunzionalita.siglaFunzionalita.isNotNull())
							.list(qstgFunzionalita.all()) ;


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

	public static List<Tuple> getDuplicateSiglaFunzionalita(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgFunzionalita  qstgFunzionalita   = QStgFunzionalita.stgFunzionalita;

			moduli = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.siglaFunzionalita.in(
							new SQLSubQuery()
							.from(qstgFunzionalita)
							.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
							.groupBy(qstgFunzionalita.siglaFunzionalita)
							.having(qstgFunzionalita.siglaFunzionalita.count().gt(1) )
							.list(qstgFunzionalita.siglaFunzionalita))
							)
							.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
							.where(qstgFunzionalita.siglaProdotto.isNotNull()) // appartenenti ad uno specifico prodotto
							.list(qstgFunzionalita.all()) ;


		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getDuplicateNomeFunzionalita(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.nomeFunzionalita.notLike("%ANNULLATO%"))
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.groupBy(qstgFunzionalita.siglaProdotto)
					.groupBy(qstgFunzionalita.siglaModulo)
					.groupBy(qstgFunzionalita.nomeFunzionalita)
					.having(qstgFunzionalita.nomeFunzionalita.count().gt(1) )
					.list(qstgFunzionalita.siglaProdotto, qstgFunzionalita.siglaModulo, qstgFunzionalita.nomeFunzionalita );


		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getSiglaFunzionalitaNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.siglaFunzionalita.isNull())
					.list(qstgFunzionalita.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getNomeFunzionalitaNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.nomeFunzionalita.isNull())
					.list(qstgFunzionalita.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getFunzionalitaAnnullate(QStgFunzionalita  qstgFunzionalita, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> funzionalitaAnnullate = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query 							= new SQLQuery(connection, dialect); 

			funzionalitaAnnullate = 

					query.from(qstgFunzionalita)
					.where(qstgFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(qstgFunzionalita.nomeFunzionalita
							.like("%ANNULLATO%"))
							.list(qstgFunzionalita.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return funzionalitaAnnullate;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			new SQLDeleteClause(connection, dialect, qstgFunzionalita)
			.where(qstgFunzionalita.dataCaricamento.lt(dataEsecuzioneDeleted).or(qstgFunzionalita.dataCaricamento
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

			QStgFunzionalita  qstgFunzionalita   = QStgFunzionalita.stgFunzionalita;

			new SQLDeleteClause(connection, dialect, qstgFunzionalita)
			.where(qstgFunzionalita.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	public static void recoverFunzionalita() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgFunzionalita  qstgFunzionalita   = QStgFunzionalita.stgFunzionalita;

			new SQLDeleteClause(connection, dialect, qstgFunzionalita)
			.where(qstgFunzionalita.dataCaricamento.eq(
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