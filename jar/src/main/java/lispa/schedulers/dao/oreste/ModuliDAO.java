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
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.utils.DateUtils;

public class ModuliDAO
{

	private static Logger logger = Logger.getLogger(ModuliDAO.class); 

	public static long fillModuli() throws Exception
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QStgModuli   qStgModuli  = QStgModuli.stgModuli;
			OresteDmAlmModuli  qViewModuli  = OresteDmAlmModuli.dmAlmModuli;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> moduli = query.from(qViewModuli).list(qViewModuli.all());

			for(Tuple row : moduli) {
				n_righe_inserite += new SQLInsertClause(connection, dialect, qStgModuli)
				.columns(
						qStgModuli.idEdmaModulo, 
						qStgModuli.idEdmaPadreModulo,
						qStgModuli.idModulo,
						qStgModuli.tipoOggetto,
						qStgModuli.siglaProdottoModulo,
						qStgModuli.siglaSottosistemaModulo,
						qStgModuli.siglaModulo,
						qStgModuli.nomeModulo,
						qStgModuli.descrizioneModulo,
						qStgModuli.moduloAnnullato,
						qStgModuli.dfvModuloAnnullato,
						qStgModuli.edmaRespModulo,
						qStgModuli.clasfSottosistemaModulo,
						qStgModuli.clasfTecnologiaModulo,
						qStgModuli.clasfTipologiaModulo,
						qStgModuli.dataCaricamento,
						qStgModuli.dmalmModuliPk
						)
						.values( 
								row.get(qViewModuli.idEdmaModulo), 
								row.get(qViewModuli.idEdmaPadreModulo),
								row.get(qViewModuli.idModulo),
								row.get(qViewModuli.tipoOggetto),
								row.get(qViewModuli.siglaProdottoModulo) != null ? row.get(qViewModuli.siglaProdottoModulo).trim() : null,
										row.get(qViewModuli.siglaSottosistemaModulo) != null ? row.get(qViewModuli.siglaSottosistemaModulo).trim() : null,
												row.get(qViewModuli.siglaModulo) != null ? row.get(qViewModuli.siglaModulo).trim() : null,
														row.get(qViewModuli.nomeModulo),
														row.get(qViewModuli.descrizioneModulo),
														row.get(qViewModuli.moduloAnnullato),
														row.get(qViewModuli.dfvModuloAnnullato),
														row.get(qViewModuli.edmaRespModulo),
														row.get(qViewModuli.clasfSottosistemaModulo),
														row.get(qViewModuli.clasfTecnologiaModulo),
														row.get(qViewModuli.clasfTipologiaModulo),
														DataEsecuzione.getInstance().getDataEsecuzione(),
														StringTemplate.create("STG_MODULI_SEQ.nextval")

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

	public static List<Tuple> checkStatoModuliProdottoAnnullato(QStgModuli  qstgModuli, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
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

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.nomeModulo.notLike("%ANNULLATO%"))
					.where(qstgModuli.siglaProdottoModulo.equalsIgnoreCase(siglaProdotto))
					.list(qstgModuli.all()) ;

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
	 * @param qstgModuli
	 * @param siglaSottosistema
	 * @return per un sottosistema annullato logicamente ritorna la lista dei relativi moduli non annullati logicamente
	 * @throws Exception
	 */
	public static List<Tuple> checkStatoModuliSottosistemaAnnullato(QStgModuli  qstgModuli, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
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

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.nomeModulo.notLike("%ANNULLATO%"))
					.where(qstgModuli.siglaSottosistemaModulo.equalsIgnoreCase(siglaSottosistema))
					.list(qstgModuli.all()) ;

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

	public static List<Tuple> getModuliByProdotto(QStgModuli  qstgModuli, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.siglaProdottoModulo.toUpperCase().trim().equalsIgnoreCase(siglaProdotto.toUpperCase()))
					.list(qstgModuli.all()) ;

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

	public static List<Tuple> getModuliBySottosistema(QStgModuli  qstgModuli, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.siglaSottosistemaModulo.equalsIgnoreCase(siglaSottosistema))
					.list(qstgModuli.all()) ;

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

	public static List<Tuple> getModuliAnnullatiByProdotto(QStgModuli  qstgModuli, String siglaProdotto, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.siglaProdottoModulo.eq(siglaProdotto))
					.where(qstgModuli.nomeModulo.like("%ANNULLATO%"))
					.list(qstgModuli.all()) ;

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

	public static List<Tuple> getModuliAnnullatiBySottosistema(QStgModuli  qstgModuli, String siglaSottosistema, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.siglaSottosistemaModulo.eq(siglaSottosistema))
					.where(qstgModuli.nomeModulo.like("%ANNULLATO%"))
					.list(qstgModuli.all()) ;

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

	public static List<Tuple> getDuplicateSiglaModuloInsiemeProdotto(QStgModuli  stgModuli, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			//			select * from  DMALM_STG_MODULI where SIGLA_MODULO in 
			//			(
			//			SELECT SIGLA_MODULO FROM DMALM_STG_MODULI
			//			group by SIGLA_PRODOTTO_MODULO, SIGLA_MODULO
			//			having count(*) > 1
			//			)
			//			and not SIGLA_PRODOTTO_MODULO is null
			//			and (SIGLA_SOTTOSISTEMA_MODULO is null or SIGLA_SOTTOSISTEMA_MODULO = '*')

			moduli = 

					query
					.from(stgModuli)
					.where(stgModuli.siglaModulo.in(
							new SQLSubQuery()
							.from(stgModuli)
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.groupBy(stgModuli.siglaProdottoModulo)
							.groupBy(stgModuli.siglaModulo)
							.having(stgModuli.siglaModulo.count().gt(1) )
							.list(stgModuli.siglaModulo))
							)
							.where(stgModuli.siglaProdottoModulo.isNotNull()) // appartenenti ad uno specifico prodotto
							.where(stgModuli.siglaSottosistemaModulo.isNull().or(stgModuli.siglaSottosistemaModulo.equalsIgnoreCase("*")))
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.list(stgModuli.all()) ;


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

	public static List<Tuple> getDuplicateSiglaModuloInsiemeSottosistema(QStgModuli  stgModuli, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.siglaModulo.in
							(
									new SQLSubQuery()
									.from(stgModuli)
									.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
									.where(stgModuli.siglaSottosistemaModulo.isNotNull())
									.where(stgModuli.siglaSottosistemaModulo.notEqualsIgnoreCase("*"))
									.groupBy(stgModuli.siglaSottosistemaModulo)
									.groupBy(stgModuli.siglaModulo)
									.having(stgModuli.siglaModulo.count().gt(1) )
									.list(stgModuli.siglaModulo))
							)
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.list(stgModuli.all()) ;


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

	public static List<Tuple> getDuplicateNomeModuloInsiemeProdotto(QStgModuli  stgModuli, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates();
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			//			select * from  DMALM_STG_MODULI where NOME_MODULO in 
			//			(
			//				SELECT NOME_MODULO FROM DMALM_STG_MODULI
			//				group by SIGLA_PRODOTTO_MODULO, NOME_MODULO
			//				having count(*) > 1
			//			)
			//			and not SIGLA_PRODOTTO_MODULO is null
			//			and (SIGLA_SOTTOSISTEMA_MODULO is null or SIGLA_SOTTOSISTEMA_MODULO = '*')
			//			and NOME_MODULO not like ('%ANNULLATO%')

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.nomeModulo.in(
							new SQLSubQuery()
							.from(stgModuli)
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.groupBy(stgModuli.siglaProdottoModulo)
							.groupBy(stgModuli.nomeModulo)
							.having(stgModuli.nomeModulo.count().gt(1))
							.list(stgModuli.nomeModulo))
							)
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.where(stgModuli.siglaProdottoModulo.isNotNull()) 
							.where(stgModuli.siglaSottosistemaModulo.isNull().or(stgModuli.siglaSottosistemaModulo.equalsIgnoreCase("*")))
							.where(stgModuli.nomeModulo.notLike("%ANNULLATO%"))
							.list(stgModuli.all()) ;

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

	public static List<Tuple> getDuplicateNomeModuloInsiemeSottosistema(			QStgModuli  stgModuli, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.nomeModulo.in(
							new SQLSubQuery()
							.from(stgModuli)
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.where(stgModuli.siglaSottosistemaModulo.isNotNull())
							.where(stgModuli.siglaSottosistemaModulo.notEqualsIgnoreCase("*"))
							.groupBy(stgModuli.siglaSottosistemaModulo)
							.groupBy(stgModuli.nomeModulo)
							.having(stgModuli.nomeModulo.count().gt(1) )
							.list(stgModuli.nomeModulo))
							)
							//.where(stgModuli.siglaProdottoModulo.isNotNull()) // appartenenti ad uno specifico prodotto
							.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
							.list(stgModuli.all()) ;


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

	public static List<Tuple> getSiglaModuliNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgModuli  stgModuli   = QStgModuli.stgModuli;

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgModuli.siglaModulo.isNull()).list(stgModuli.all()) ;

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

	public static List<Tuple> getNomeModuliNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QStgModuli  stgModuli   = QStgModuli.stgModuli;

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgModuli.nomeModulo.isNull()).list(stgModuli.all()) ;

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

	public static List<Tuple> getModuliAnnullati(QStgModuli  qstgModuli, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduliAnnnullati = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduliAnnnullati = 

					query.from(qstgModuli)
					.where(qstgModuli.dataCaricamento.eq(dataEsecuzione))
					.where(qstgModuli.nomeModulo
							.like("%ANNULLATO%"))
							.list(qstgModuli.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return moduliAnnnullati;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception

	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QStgModuli  stgModuli   = QStgModuli.stgModuli;

			new SQLDeleteClause(connection, dialect, stgModuli)
			.where(stgModuli.dataCaricamento.lt(dataEsecuzioneDeleted).or(stgModuli.dataCaricamento
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

			QStgModuli  stgModuli   = QStgModuli.stgModuli;

			new SQLDeleteClause(connection, dialect, stgModuli)
			.where(stgModuli.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}


	public static List<Tuple> getAllModuli(QStgModuli  stgModuli, Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> moduli = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			moduli = 

					query.from(stgModuli)
					.where(stgModuli.dataCaricamento.eq(dataEsecuzione))
					.list(stgModuli.all()) ;

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
	public static void recoverModuli() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgModuli  stgModuli   = QStgModuli.stgModuli;

			new SQLDeleteClause(connection, dialect, stgModuli)
			.where(stgModuli.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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