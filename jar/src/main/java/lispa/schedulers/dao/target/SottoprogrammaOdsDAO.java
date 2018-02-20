package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmSottoprogrammaOds;

public class SottoprogrammaOdsDAO {

	private static Logger logger = Logger.getLogger(SottoprogrammaDAO.class);

	private static QDmalmSottoprogrammaOds sottoprogrammaODS = QDmalmSottoprogrammaOds.dmalmSottoprogrammaOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, sottoprogrammaODS)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmSottoprogramma> staging_sottoprogramma,
			Timestamp dataEsecuzione) throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmSottoprogramma sottoprogramma : staging_sottoprogramma) {
				new SQLInsertClause(connection, dialect, sottoprogrammaODS)
						.columns(sottoprogrammaODS.idRepository,
								sottoprogrammaODS.dmalmSottoprogrammaPk,
								sottoprogrammaODS.cdSottoprogramma,
								sottoprogrammaODS.dtCreazioneSottoprogramma,
								sottoprogrammaODS.dmalmProjectFk02,
								sottoprogrammaODS.dmalmStatoWorkitemFk03,
								sottoprogrammaODS.dtCambioStatoSottoprogramma,
								sottoprogrammaODS.dtScadenzaSottoprogramma,
								sottoprogrammaODS.dtModificaSottoprogramma,
								sottoprogrammaODS.idAutoreSottoprogramma,
								sottoprogrammaODS.dsAutoreSottoprogramma,
								sottoprogrammaODS.titoloSottoprogramma,
								sottoprogrammaODS.motivoRisoluzioneSottoprogr,
								sottoprogrammaODS.dtRisoluzioneSottoprogramma,
								sottoprogrammaODS.descrizioneSottoprogramma,
								sottoprogrammaODS.codice,
								sottoprogrammaODS.numeroLinea,
								sottoprogrammaODS.numeroTestata,
								sottoprogrammaODS.dtCompletamento,
								sottoprogrammaODS.stgPk,
								sottoprogrammaODS.dtCaricamentoSottoprogramma,
								sottoprogrammaODS.dmalmTempoFk04,
								sottoprogrammaODS.dmalmUserFk06,
								sottoprogrammaODS.uri,
								sottoprogrammaODS.severity, sottoprogrammaODS.priority)
						.values(sottoprogramma.getIdRepository(),
								sottoprogramma.getDmalmSottoprogrammaPk(),
								sottoprogramma.getCdSottoprogramma(),
								sottoprogramma.getDtCreazioneSottoprogramma(),
								sottoprogramma.getDmalmProjectFk02(),
								sottoprogramma.getDmalmStatoWorkitemFk03(),
								sottoprogramma.getDtCambioStatoSottoprogramma(),
								sottoprogramma.getDtScadenzaSottoprogramma(),
								sottoprogramma.getDtModificaSottoprogramma(),
								sottoprogramma.getIdAutoreSottoprogramma(),
								sottoprogramma.getDsAutoreSottoprogramma(),
								sottoprogramma.getTitoloSottoprogramma(),
								sottoprogramma.getMotivoRisoluzioneSottoprogr(),
								sottoprogramma.getDtRisoluzioneSottoprogramma(),
								sottoprogramma.getDescrizioneSottoprogramma(),
								sottoprogramma.getCodice(),
								sottoprogramma.getNumeroLinea(),
								sottoprogramma.getNumeroTestata(),
								sottoprogramma.getDtCompletamento(),
								sottoprogramma.getStgPk(),
								sottoprogramma.getDtCaricamentoSottoprogramma(),
								sottoprogramma.getDmalmTempoFk04(),
								sottoprogramma.getDmalmUserFk06(),
								sottoprogramma.getUri(),
								//DM_ALM-320
								sottoprogramma.getSeverity(), sottoprogramma.getPriority()).execute();
			}
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmSottoprogramma> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmSottoprogramma> resultListEl = new LinkedList<DmalmSottoprogramma>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(sottoprogrammaODS)
					.orderBy(sottoprogrammaODS.cdSottoprogramma.asc())
					.orderBy(sottoprogrammaODS.dtModificaSottoprogramma.asc())
					.list(sottoprogrammaODS.all());
			
			for (Tuple result : list) {
				DmalmSottoprogramma resultEl = new DmalmSottoprogramma();
				resultEl.setCdSottoprogramma(result.get(sottoprogrammaODS.cdSottoprogramma));
				resultEl.setCodice(result.get(sottoprogrammaODS.codice));
				resultEl.setNumeroLinea(result.get(sottoprogrammaODS.numeroLinea));
				resultEl.setNumeroTestata(result.get(sottoprogrammaODS.numeroTestata));
				resultEl.setDmalmUserFk06(result.get(sottoprogrammaODS.dmalmUserFk06));
				resultEl.setDescrizioneSottoprogramma(result.get(sottoprogrammaODS.descrizioneSottoprogramma));
				resultEl.setDmalmProjectFk02(result.get(sottoprogrammaODS.dmalmProjectFk02));
				resultEl.setDmalmSottoprogrammaPk(result.get(sottoprogrammaODS.dmalmSottoprogrammaPk));
				resultEl.setDmalmStatoWorkitemFk03(result.get(sottoprogrammaODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(sottoprogrammaODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(sottoprogrammaODS.dmalmTempoFk04));
				resultEl.setDsAutoreSottoprogramma(result.get(sottoprogrammaODS.dsAutoreSottoprogramma));
				resultEl.setDtCambioStatoSottoprogramma(result.get(sottoprogrammaODS.dtCambioStatoSottoprogramma));
				resultEl.setDtCaricamentoSottoprogramma(result.get(sottoprogrammaODS.dtCaricamentoSottoprogramma));
				resultEl.setDtCompletamento(result.get(sottoprogrammaODS.dtCompletamento));
				resultEl.setDtCreazioneSottoprogramma(result.get(sottoprogrammaODS.dtCreazioneSottoprogramma));
				resultEl.setDtModificaSottoprogramma(result.get(sottoprogrammaODS.dtModificaSottoprogramma));
				resultEl.setDtRisoluzioneSottoprogramma(result.get(sottoprogrammaODS.dtRisoluzioneSottoprogramma));
				resultEl.setDtScadenzaSottoprogramma(result.get(sottoprogrammaODS.dtScadenzaSottoprogramma));
				resultEl.setDtStoricizzazione(result.get(sottoprogrammaODS.dtStoricizzazione));
				resultEl.setIdAutoreSottoprogramma(result.get(sottoprogrammaODS.idAutoreSottoprogramma));
				resultEl.setIdRepository(result.get(sottoprogrammaODS.idRepository));
				resultEl.setMotivoRisoluzioneSottoprogr(result.get(sottoprogrammaODS.motivoRisoluzioneSottoprogr));
				resultEl.setRankStatoSottoprogramma((result.get(sottoprogrammaODS.rankStatoSottoprogramma) != null && result.get(sottoprogrammaODS.rankStatoSottoprogramma).equals(true))?new Double(1):new Double(0));
				resultEl.setRankStatoSottoprogrammaMese(result.get(sottoprogrammaODS.rankStatoSottoprogrammaMese));
				resultEl.setStgPk(result.get(sottoprogrammaODS.stgPk));
				resultEl.setUri(result.get(sottoprogrammaODS.uri));
				resultEl.setTitoloSottoprogramma(result.get(sottoprogrammaODS.titoloSottoprogramma));
				resultEl.setSeverity(result.get(sottoprogrammaODS.severity));
				resultEl.setPriority(result.get(sottoprogrammaODS.priority));
				resultListEl.add(resultEl);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;

	}
}
