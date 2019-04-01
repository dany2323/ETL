package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmFaseOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class FaseOdsDAO {

	private static Logger logger = Logger.getLogger(TaskOdsDAO.class);

	private static QDmalmFaseOds faseODS = QDmalmFaseOds.dmalmFaseOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, faseODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmFase> staging_fasi,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmFase fase : staging_fasi) {

				new SQLInsertClause(connection, dialect, faseODS)
						.columns(faseODS.applicabile, faseODS.cdFase,
								faseODS.codice, faseODS.dataFineBaseline,
								faseODS.dataFineEffettiva,
								faseODS.dataFinePianificata,
								faseODS.dataInizioBaseline,
								faseODS.dataInizioEffettivo,
								faseODS.dataInizioPianificato,
								faseODS.dataPassaggioInEsecuzione,
								faseODS.descrizioneFase, faseODS.dmalmFasePk,
								faseODS.dmalmProjectFk02,
								faseODS.dmalmStatoWorkitemFk03,
								faseODS.dmalmStrutturaOrgFk01,
								faseODS.dmalmTempoFk04, faseODS.dsAutoreFase,
								faseODS.dtCambioStatoFase,
								faseODS.dtCaricamentoFase,
								faseODS.dtCreazioneFase,
								faseODS.dtModificaFase,
								faseODS.dtRisoluzioneFase,
								faseODS.dtScadenzaFase,
								faseODS.dtStoricizzazione,
								faseODS.durataEffettivaFase,
								faseODS.idAutoreFase, faseODS.idRepository,
								faseODS.motivoRisoluzioneFase,
								faseODS.rankStatoFase, faseODS.titoloFase,
								faseODS.stgPk, faseODS.dmalmUserFk06,
								faseODS.uri,
								faseODS.severity, faseODS.priority,
								faseODS.tagAlm, faseODS.tsTagAlm)
						.values(fase.getApplicabile(), fase.getCdFase(),
								fase.getCodice(), fase.getDataFineBaseline(),
								fase.getDataFineEffettiva(),
								fase.getDataFinePianificata(),
								fase.getDataInizioBaseline(),
								fase.getDataInizioEffettivo(),
								fase.getDataInizioPianificato(),
								fase.getDataPassaggioInEsecuzione(),
								fase.getDescrizioneFase(),
								fase.getDmalmFasePk(),
								fase.getDmalmProjectFk02(),
								fase.getDmalmStatoWorkitemFk03(),
								fase.getDmalmStrutturaOrgFk01(),
								fase.getDmalmTempoFk04(),
								fase.getDsAutoreFase(),
								fase.getDtCambioStatoFase(),
								fase.getDtCaricamentoFase(),
								fase.getDtCreazioneFase(),
								fase.getDtModificaFase(),
								fase.getDtRisoluzioneFase(),
								fase.getDtScadenzaFase(),
								fase.getDtModificaFase(),
								fase.getDurataEffettivaFase(),
								fase.getIdAutoreFase(), fase.getIdRepository(),
								fase.getMotivoRisoluzioneFase(), new Double(1),
								fase.getTitoloFase(), fase.getStgPk(),
								fase.getDmalmUserFk06(), fase.getUri(),
								fase.getSeverity(), fase.getPriority(),
								fase.getTagAlm(), fase.getTsTagAlm()

						).execute();
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

	public static List<DmalmFase> getAll() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmFase> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(faseODS).orderBy(faseODS.cdFase.asc())
					.orderBy(faseODS.dtModificaFase.asc())
					.list(Projections.bean(DmalmFase.class, faseODS.all()));

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}

}
