package lispa.schedulers.dao.edma;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.edma.EdmaDmAlmPersonale;
import lispa.schedulers.queryimplementation.staging.edma.QStgPersonale;
import lispa.schedulers.utils.DateUtils;

public class PersonaleDAO
{
	private static Logger logger = Logger.getLogger(PersonaleDAO.class); 

	public static long fillPersonale() throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			EdmaDmAlmPersonale qViewPersonale = EdmaDmAlmPersonale.dmAlmPersonale;
			QStgPersonale  qStgPersonale  = QStgPersonale.stgPersonale;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> personale = query.from(qViewPersonale).list(qViewPersonale.all());

			for(Tuple row : personale) {
				new SQLInsertClause(connection, dialect, qStgPersonale)
				.columns(
						qStgPersonale.id, 
						qStgPersonale.codice,
						qStgPersonale.dataInizioValidita,
						qStgPersonale.dataFineValidita,
						qStgPersonale.dataAttivazione,
						qStgPersonale.dataDisattivazione,
						qStgPersonale.note,
						qStgPersonale.interno,
						qStgPersonale.codResponsabile,
						qStgPersonale.indirizzoEmail,
						qStgPersonale.nome,
						qStgPersonale.cognome,
						qStgPersonale.matricola,
						qStgPersonale.codiceFiscale,
						qStgPersonale.identficatore,
						qStgPersonale.idGradoUfficio,
						qStgPersonale.idSede,
						qStgPersonale.codSuperiore,
						qStgPersonale.codEnte,
						qStgPersonale.codVisibilita,
						qStgPersonale.tipopersona,
						qStgPersonale.dataCaricamento,
						qStgPersonale.dmalmStgPersonalePk
						)
						.values(
								row.get(qViewPersonale.id), 
								row.get(qViewPersonale.codice),
								DateUtils.dateToString(row.get(qViewPersonale.dataInizioValidita), DmAlmConstants.DATE_FORMAT_DB),
								DateUtils.dateToString(row.get(qViewPersonale.dataFineValidita), DmAlmConstants.DATE_FORMAT_DB),
								DateUtils.dateToString(row.get(qViewPersonale.dataAttivazione), DmAlmConstants.DATE_FORMAT_DB),
								DateUtils.dateToString(row.get(qViewPersonale.dataDisattivazione), DmAlmConstants.DATE_FORMAT_DB),
								row.get(qViewPersonale.note),
								row.get(qViewPersonale.interno),
								row.get(qViewPersonale.codResponsabile),
								row.get(qViewPersonale.indirizzoEmail),
								row.get(qViewPersonale.nome),
								row.get(qViewPersonale.cognome),
								row.get(qViewPersonale.matricola),
								row.get(qViewPersonale.codiceFiscale),
								row.get(qViewPersonale.identficatore),
								row.get(qViewPersonale.idGradoUfficio),
								row.get(qViewPersonale.idSede),
								row.get(qViewPersonale.codSuperiore),
								row.get(qViewPersonale.codEnte),
								row.get(qViewPersonale.codVisibilita),
								null,
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("STG_PERSONALE_SEQ.nextval")

								)
								.execute();
			}




			connection.commit();


		}
		catch (Exception e)
		{
			lispa.schedulers.manager.ErrorManager.getInstance().exceptionOccurred(true, e);
		
			
			n_righe_inserite=0;
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return n_righe_inserite;

	}

	public static List<Tuple> getPersonaleByCodice(QStgPersonale  qstgPersonale, String codice, Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> personale = new ArrayList<Tuple>();


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 

			personale = 

					query.from(qstgPersonale)
					.where(qstgPersonale.dataCaricamento.eq(dataEsecuzione))
					.where(qstgPersonale.codice.equalsIgnoreCase(codice.toUpperCase()))
					.list(qstgPersonale.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return personale;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QStgPersonale  qstgPersonale = QStgPersonale.stgPersonale;

			new SQLDeleteClause(connection, dialect, qstgPersonale)
			.where(qstgPersonale.dataCaricamento.lt(dataEsecuzione).or(qstgPersonale.dataCaricamento
					.gt(DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance()
							.getDataEsecuzione(),-1)))).execute();

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

			QStgPersonale  qstgPersonale = QStgPersonale.stgPersonale;

			new SQLDeleteClause(connection, dialect, qstgPersonale)
			.where(qstgPersonale.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}

	public static void recoverPersonale() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgPersonale  qstgPersonale = QStgPersonale.stgPersonale;

			new SQLDeleteClause(connection, dialect, qstgPersonale)
			.where(qstgPersonale.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

}