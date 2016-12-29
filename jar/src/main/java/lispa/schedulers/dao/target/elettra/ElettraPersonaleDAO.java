package lispa.schedulers.dao.target.elettra;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_ELETTRAPERSONALE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ElettraPersonaleDAO {
	private static Logger logger = Logger.getLogger(ElettraPersonaleDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmElPersonale qDmalmElPersonale = QDmalmElPersonale.qDmalmElPersonale;

	public static List<DmalmElPersonale> getAllPersonale(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmElPersonale bean = null;
		List<DmalmElPersonale> personale = new ArrayList<DmalmElPersonale>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_ELETTRAPERSONALE);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmElPersonale();

				bean.setPersonalePk(rs.getInt("DMALM_PERSONALE_PK"));
				bean.setIdEdma(rs.getString("ID_EDMA"));
				bean.setCodicePersonale(rs.getString("CD_PERSONALE"));
				bean.setDataInizioValiditaEdma(rs
						.getTimestamp("DT_INIZIO_VALIDITA_EDMA"));
				bean.setDataFineValiditaEdma(rs
						.getTimestamp("DT_FINE_VALIDITA_EDMA"));
				bean.setDataAttivazione(rs.getTimestamp("DT_ATTIVAZIONE"));
				bean.setDataDisattivazione(rs.getTimestamp("DT_DISATTIVAZIONE"));
				bean.setNote(rs.getString("NOTE"));
				bean.setInterno(rs.getInt("INTERNO"));
				bean.setCodiceResponsabile(rs.getString("CD_RESPONSABILE"));
				bean.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));
				bean.setNome(rs.getString("NOME"));
				bean.setCognome(rs.getString("COGNOME"));
				bean.setMatricola(rs.getString("MATRICOLA"));
				bean.setCodiceFiscale(rs.getString("CODICE_FISCALE"));
				bean.setIdentificatore(rs.getString("IDENTIFICATORE"));
				bean.setIdGrado(rs.getInt("ID_GRADO"));
				bean.setIdSede(rs.getInt("ID_SEDE"));
				bean.setCdSuperiore(rs.getString("CD_SUPERIORE"));
				bean.setCdEnte(rs.getString("CD_ENTE"));
				bean.setCdVisibilita(rs.getString("CD_VISIBILITA"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setAnnullato("NO");
			

				personale.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		return personale;
	}

	public static void updateDataFineValidita(Timestamp dataelaborazione,
			Integer personalePk) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElPersonale)
					.where(qDmalmElPersonale.personalePk.eq(personalePk))
					.set(qDmalmElPersonale.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataelaborazione,
									-1)).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updatePersonale(Integer personalePk,
			DmalmElPersonale personale) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElPersonale)
					.where(qDmalmElPersonale.personalePk.eq(personalePk))
					.set(qDmalmElPersonale.codicePersonale,
							personale.getCodicePersonale())
					.set(qDmalmElPersonale.nome, personale.getNome())
					.set(qDmalmElPersonale.cognome, personale.getCognome())
					.set(qDmalmElPersonale.indirizzoEmail,
							personale.getIndirizzoEmail())
					.set(qDmalmElPersonale.codiceFiscale,
							personale.getCodiceFiscale())
					.set(qDmalmElPersonale.identificatore,
							personale.getIdentificatore())
					.set(qDmalmElPersonale.idGrado, personale.getIdGrado())
					.set(qDmalmElPersonale.idSede, personale.getIdSede())
					.set(qDmalmElPersonale.cdEnte, personale.getCdEnte())
					.set(qDmalmElPersonale.cdVisibilita,
							personale.getCdVisibilita())
					.set(qDmalmElPersonale.note, personale.getNote())

					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertPersonale(DmalmElPersonale personale)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, qDmalmElPersonale)
					.columns(qDmalmElPersonale.cdEnte,
							qDmalmElPersonale.codicePersonale,
							qDmalmElPersonale.codiceResponsabile,
							qDmalmElPersonale.cdSuperiore,
							qDmalmElPersonale.cdVisibilita,
							qDmalmElPersonale.codiceFiscale,
							qDmalmElPersonale.cognome,
							qDmalmElPersonale.personalePk,
							qDmalmElPersonale.dataAttivazione,
							qDmalmElPersonale.dataCaricamento,
							qDmalmElPersonale.dataDisattivazione,
							qDmalmElPersonale.dataFineValidita,
							qDmalmElPersonale.dataFineValiditaEdma,
							qDmalmElPersonale.dataInizioValidita,
							qDmalmElPersonale.dataInizioValiditaEdma,
							qDmalmElPersonale.idEdma,
							qDmalmElPersonale.idGrado,
							qDmalmElPersonale.idSede,
							qDmalmElPersonale.identificatore,
							qDmalmElPersonale.indirizzoEmail,
							qDmalmElPersonale.interno,
							qDmalmElPersonale.matricola,
							qDmalmElPersonale.nome, qDmalmElPersonale.note,
							qDmalmElPersonale.annullato,
							qDmalmElPersonale.dataAnnullamento,
							qDmalmElPersonale.unitaOrganizzativaFk,
							qDmalmElPersonale.unitaOrganizzativaFlatFk)
					.values(personale.getCdEnte(),
							personale.getCodicePersonale(),
							personale.getCodiceResponsabile(),
							personale.getCdSuperiore(),
							personale.getCdVisibilita(),
							personale.getCodiceFiscale(),
							personale.getCognome(),
							personale.getPersonalePk(),
							personale.getDataAttivazione(),
							personale.getDataCaricamento(),
							personale.getDataDisattivazione(),
							(personale.getDataFineValiditaEdma() != null ? personale
									.getDataFineValiditaEdma() : DateUtils
									.setDtFineValidita9999()),
							personale.getDataFineValiditaEdma(),
							DateUtils.setDtInizioValidita1900(),
							personale.getDataInizioValiditaEdma(),
							personale.getIdEdma(), personale.getIdGrado(),
							personale.getIdSede(),
							personale.getIdentificatore(),
							personale.getIndirizzoEmail(),
							personale.getInterno(), personale.getMatricola(),
							personale.getNome(), personale.getNote(), null,
							null,
							0, 0).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertPersonaleUpdate(Timestamp dataEsecuzione,
			DmalmElPersonale personale) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElPersonale)
					.columns(qDmalmElPersonale.cdEnte,
							qDmalmElPersonale.codicePersonale,
							qDmalmElPersonale.codiceResponsabile,
							qDmalmElPersonale.cdSuperiore,
							qDmalmElPersonale.cdVisibilita,
							qDmalmElPersonale.codiceFiscale,
							qDmalmElPersonale.cognome,
							qDmalmElPersonale.personalePk,
							qDmalmElPersonale.dataAttivazione,
							qDmalmElPersonale.dataCaricamento,
							qDmalmElPersonale.dataDisattivazione,
							qDmalmElPersonale.dataFineValidita,
							qDmalmElPersonale.dataFineValiditaEdma,
							qDmalmElPersonale.dataInizioValidita,
							qDmalmElPersonale.dataInizioValiditaEdma,
							qDmalmElPersonale.idEdma,
							qDmalmElPersonale.idGrado,
							qDmalmElPersonale.idSede,
							qDmalmElPersonale.identificatore,
							qDmalmElPersonale.indirizzoEmail,
							qDmalmElPersonale.interno,
							qDmalmElPersonale.matricola,
							qDmalmElPersonale.nome, qDmalmElPersonale.note,
							qDmalmElPersonale.annullato,
							qDmalmElPersonale.dataAnnullamento,
							qDmalmElPersonale.unitaOrganizzativaFk,
							qDmalmElPersonale.unitaOrganizzativaFlatFk)
					.values(personale.getCdEnte(),
							personale.getCodicePersonale(),
							personale.getCodiceResponsabile(),
							personale.getCdSuperiore(),
							personale.getCdVisibilita(),
							personale.getCodiceFiscale(),
							personale.getCognome(), personale.getPersonalePk(),
							personale.getDataAttivazione(),
							personale.getDataCaricamento(),
							personale.getDataDisattivazione(),
							DateUtils.setDtFineValidita9999(),
							personale.getDataFineValiditaEdma(),
							dataEsecuzione,
							personale.getDataInizioValiditaEdma(),
							personale.getIdEdma(), personale.getIdGrado(),
							personale.getIdSede(),
							personale.getIdentificatore(),
							personale.getIndirizzoEmail(),
							personale.getInterno(), 
							personale.getMatricola(),
							personale.getNome(), 
							personale.getNote(), 
							null,
							null,
							personale.getUnitaOrganizzativaFk(),
							personale.getUnitaOrganizzativaFlatFk()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getPersonale(DmalmElPersonale personale)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> strutture = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			strutture = query
					.from(qDmalmElPersonale)
					.where(qDmalmElPersonale.codicePersonale.eq(personale
							.getCodicePersonale()))
					.where(qDmalmElPersonale.dataFineValidita.in(new SQLSubQuery()
							.from(qDmalmElPersonale)
							.where(qDmalmElPersonale.codicePersonale
									.eq(personale.getCodicePersonale()))
							.list(qDmalmElPersonale.dataFineValidita.max())))
					.list(qDmalmElPersonale.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return strutture;
	}
	
	public static List<Tuple> getAllPersonaleUnitaOrganizzativa(Timestamp dataEsecuzione) throws DAOException {
		List<Tuple> newPersonaleList = new ArrayList<Tuple>();	
	
		ConnectionManager cm = null;
		Connection connection = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
	

			QDmalmProdotto qProdotto = QDmalmProdotto.dmalmProdotto;
			
			// tutte le persone nuove o quelle per le quali la FK Unita
			// Organizzativa è variata
			
			newPersonaleList = query
					.from(qDmalmElPersonale)
					.join(qProdotto)
					.on(qProdotto.dmalmPersonaleFk02.eq(qDmalmElPersonale.personalePk))
					.where(qDmalmElPersonale.personalePk.ne(0))
					.where(qDmalmElPersonale.dataFineValidita.eq(DateUtils.setDtFineValidita9999()))
					.where(qProdotto.dtFineValidita.eq(DateUtils.setDtFineValidita9999()))
					.where(qDmalmElPersonale.unitaOrganizzativaFk.ne(qProdotto.dmalmUnitaOrganizzativaFk01))
					.distinct()
					.list(qDmalmElPersonale.codicePersonale, qProdotto.dmalmUnitaOrganizzativaFk01);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		return newPersonaleList;
	}
	
	public static void updateFkUnitaOrganizzativa(DmalmElPersonale personale) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();


			connection.setAutoCommit(false);
			
			HSQLDBTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			new SQLUpdateClause(connection, dialect, qDmalmElPersonale)
					.where(qDmalmElPersonale.codicePersonale.eq(personale.getCodicePersonale()))
					.where(qDmalmElPersonale.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(qDmalmElPersonale.unitaOrganizzativaFk,
							personale.getUnitaOrganizzativaFk())
					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	public static void updateDataFineValidita(Timestamp dataelaborazione,
			DmalmElPersonale personale) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmElPersonale qPersonale = QDmalmElPersonale.qDmalmElPersonale;

			new SQLUpdateClause(connection, dialect, qPersonale)
					.where(qPersonale.codicePersonale.eq(personale.getCodicePersonale()))
					.where(qPersonale.dataFineValidita.in(new SQLSubQuery()
							.from(qPersonale)
							.where(qPersonale.codicePersonale.eq(personale
									.getCodicePersonale()))
							.list(qPersonale.dataFineValidita.max())))
					.set(qPersonale.dataFineValidita,
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
}
