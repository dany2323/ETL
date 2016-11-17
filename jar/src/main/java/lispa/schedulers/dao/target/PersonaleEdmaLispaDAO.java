package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PERSONALE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class PersonaleEdmaLispaDAO {

	private static Logger logger = Logger
			.getLogger(PersonaleEdmaLispaDAO.class);

	public static List<DmalmPersonale> getAllPersonale(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmPersonale bean = null;
		List<DmalmPersonale> personale = new ArrayList<DmalmPersonale>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_PERSONALE);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmPersonale();

				bean.setDmalmPersonalePk(rs.getInt("DMALM_PERSONALE_PK"));
				bean.setCdPersonale(rs.getString("CD_PERSONALE"));
				bean.setCdEnte(rs.getString("CD_ENTE"));
				bean.setCdResponsabile(rs.getString("CD_RESPONSABILE"));
				bean.setCdSuperiore(rs.getString("CD_SUPERIORE"));
				bean.setCdVisibilita(rs.getString("CD_VISIBILITA"));
				bean.setNome(rs.getString("NOME"));
				bean.setCognome(rs.getString("COGNOME"));
				bean.setMatricola(rs.getString("MATRICOLA"));
				bean.setCodiceFiscale(rs.getString("CODICE_FISCALE"));
				bean.setDtAttivazione(rs.getTimestamp("DT_ATTIVAZIONE"));
				bean.setDtDisattivazione(rs.getTimestamp("DT_DISATTIVAZIONE"));
				bean.setDtInizioValiditaEdma(rs
						.getTimestamp("DT_INIZIO_VALIDITA"));
				bean.setDtFineValiditaEdma(rs.getTimestamp("DT_FINE_VALIDITA"));
				bean.setIdEdma(rs.getInt("ID_EDMA"));
				bean.setIdSede(rs.getInt("ID_SEDE"));
				bean.setNote(rs.getString("NOTE"));
				bean.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));
				bean.setInterno(rs.getInt("INTERNO"));
				bean.setIdentificatore(rs.getString("IDENTIFICATORE"));

				personale.add(bean);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return personale;
	}

	public static void updateDataFineValidita(Timestamp dataelaborazione,
			DmalmPersonale personale) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			new SQLUpdateClause(connection, dialect, qPersonale)
					.where(qPersonale.cdPersonale.eq(personale.getCdPersonale()))
					.where(qPersonale.dtFineValidita.in(new SQLSubQuery()
							.from(qPersonale)
							.where(qPersonale.cdPersonale.eq(personale
									.getCdPersonale()))
							.list(qPersonale.dtFineValidita.max())))
					.set(qPersonale.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataelaborazione, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updatePersonaleEdmaLispa(DmalmPersonale personale)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			new SQLUpdateClause(connection, dialect, qPersonale)
					.where(qPersonale.cdPersonale.eq(personale.getCdPersonale()))
					.where(qPersonale.dtFineValidita.in(new SQLSubQuery()
							.from(qPersonale)
							.where(qPersonale.cdPersonale.eq(personale
									.getCdPersonale()))
							.list(qPersonale.dtFineValidita.max())))
					.set(qPersonale.cdPersonale, personale.getCdPersonale())
					.set(qPersonale.nome, personale.getNome())
					.set(qPersonale.cognome, personale.getCognome())
					.set(qPersonale.indirizzoEmail,
							personale.getIndirizzoEmail())
					.set(qPersonale.codiceFiscale, personale.getCodiceFiscale())
					.set(qPersonale.identificatore,
							personale.getIdentificatore())
					.set(qPersonale.idGrado, personale.getIdGrado())
					.set(qPersonale.idSede, personale.getIdSede())
					.set(qPersonale.cdEnte, personale.getCdEnte())
					.set(qPersonale.cdVisibilita, personale.getCdVisibilita())
					.set(qPersonale.note, personale.getNote())

					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertPersonaleEdmaLispa(DmalmPersonale personale)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			new SQLInsertClause(connection, dialect, qPersonale)
					.columns(qPersonale.cdEnte, qPersonale.cdPersonale,
							qPersonale.cdResponsabile, qPersonale.cdSuperiore,
							qPersonale.cdVisibilita, qPersonale.codiceFiscale,
							qPersonale.cognome, qPersonale.dmalmPersonalePk,
							qPersonale.dtAttivazione, qPersonale.dtCaricamento,
							qPersonale.dtDisattivazione,
							qPersonale.dtFineValiditaEdma,
							qPersonale.dtInizioValiditaEdma, qPersonale.idEdma,
							qPersonale.identificatore, qPersonale.idGrado,
							qPersonale.idSede, qPersonale.indirizzoEmail,
							qPersonale.interno, qPersonale.matricola,
							qPersonale.nome, qPersonale.note,
							qPersonale.dtFineValidita,
							qPersonale.dtInizioValidita)
					.values(personale.getCdEnte(),
							personale.getCdPersonale(),
							personale.getCdResponsabile(),
							personale.getCdSuperiore(),
							personale.getCdVisibilita(),
							personale.getCodiceFiscale(),
							personale.getCognome(),
							personale.getDmalmPersonalePk(),
							personale.getDtAttivazione(),
							DataEsecuzione.getInstance().getDataEsecuzione(),
							personale.getDtDisattivazione(),
							// DateUtils.setDtFineValidita9999() ,
							// DateUtils.formatDataEsecuzione(DataEsecuzione.getInstance().getDataEsecuzione()),
							personale.getDtFineValiditaEdma(),
							personale.getDtInizioValiditaEdma(),
							personale.getIdEdma(),
							personale.getIdentificatore(),
							personale.getIdGrado(), personale.getIdSede(),
							personale.getIndirizzoEmail(),
							personale.getInterno(), personale.getMatricola(),
							personale.getNome(), personale.getNote(),
							personale.getDtFineValiditaEdma(),
							DateUtils.setDtInizioValidita1900()).execute();

			// connection.commit();

		} catch (Throwable e) {

			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertPersonaleEdmaLispaUpdate(Timestamp dataEsecuzione,
			DmalmPersonale personale) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			new SQLInsertClause(connection, dialect, qPersonale)
					.columns(qPersonale.cdEnte, qPersonale.cdPersonale,
							qPersonale.cdResponsabile, qPersonale.cdSuperiore,
							qPersonale.cdVisibilita, qPersonale.codiceFiscale,
							qPersonale.cognome, qPersonale.dmalmPersonalePk,
							qPersonale.dtAttivazione, qPersonale.dtCaricamento,
							qPersonale.dtDisattivazione,
							qPersonale.dtFineValiditaEdma,
							qPersonale.dtInizioValiditaEdma, qPersonale.idEdma,
							qPersonale.identificatore, qPersonale.idGrado,
							qPersonale.idSede, qPersonale.indirizzoEmail,
							qPersonale.interno, qPersonale.matricola,
							qPersonale.nome, qPersonale.note,
							qPersonale.dtFineValidita,
							qPersonale.dtInizioValidita)
					.values(personale.getCdEnte(), personale.getCdPersonale(),
							personale.getCdResponsabile(),
							personale.getCdSuperiore(),
							personale.getCdVisibilita(),
							personale.getCodiceFiscale(),
							personale.getCognome(),
							personale.getDmalmPersonalePk(),
							personale.getDtAttivazione(),
							DataEsecuzione.getInstance().getDataEsecuzione(),
							personale.getDtDisattivazione(),
							personale.getDtFineValiditaEdma(),
							personale.getDtInizioValiditaEdma(),
							personale.getIdEdma(),
							personale.getIdentificatore(),
							personale.getIdGrado(), personale.getIdSede(),
							personale.getIndirizzoEmail(),
							personale.getInterno(), personale.getMatricola(),
							personale.getNome(), personale.getNote(),
							DateUtils.setDtFineValidita9999(), dataEsecuzione)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getPersonaleEdmaLispa(DmalmPersonale personale)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> strutture = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			strutture = query
					.from(qPersonale)
					.where(qPersonale.cdPersonale.eq(personale.getCdPersonale()))
					.where(qPersonale.dtFineValidita.in(new SQLSubQuery()
							.from(qPersonale)
							.where(qPersonale.cdPersonale.eq(personale
									.getCdPersonale()))
							.list(qPersonale.dtFineValidita.max())))
					.list(qPersonale.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture;
	}

	public static int getPersonaleEdmaLispaByCodice(String cdPersonale)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> strutture = new ArrayList<Integer>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;

			strutture = query
					.from(qPersonale)
					.where(qPersonale.cdPersonale.equalsIgnoreCase(cdPersonale))
					.where(qPersonale.dtFineValidita.in(new SQLSubQuery()
							.from(qPersonale)
							.where(qPersonale.cdPersonale.eq(cdPersonale))
							.list(qPersonale.dtFineValidita.max())))
					.list(qPersonale.dmalmPersonalePk);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture.size() > 0 ? strutture.get(0) : 0;
	}
}