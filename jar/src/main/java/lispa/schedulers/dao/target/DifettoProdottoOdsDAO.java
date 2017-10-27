package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmDifettoProdottoOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DifettoProdottoOdsDAO {
	private static Logger logger = Logger
			.getLogger(DifettoProdottoOdsDAO.class);

	private static QDmalmDifettoProdottoOds difettoODS = QDmalmDifettoProdottoOds.dmalmDifettoProdottoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void insert(List<DmalmDifettoProdotto> difetti,
			Timestamp dataesecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmDifettoProdotto difetto : difetti) {

				connection.setAutoCommit(false);

				new SQLInsertClause(connection, dialect, difettoODS)
						.columns(difettoODS.cdDifetto,
								difettoODS.dmalmDifettoProdottoPk,
								difettoODS.dmalmProjectFk02,
								difettoODS.dmalmStrutturaOrgFk01,
								difettoODS.dmalmTempoFk04,
								difettoODS.dsAutoreDifetto,
								difettoODS.dsDifetto,
								difettoODS.dtCambioStatoDifetto,
								difettoODS.dtCaricamentoRecordDifetto,
								difettoODS.dtChiusuraDifetto,
								difettoODS.dtCreazioneDifetto,
								difettoODS.dtModificaRecordDifetto,
								difettoODS.dtRisoluzioneDifetto,
								difettoODS.idAutoreDifetto,
								difettoODS.idkAreaTematica,
								difettoODS.idRepository,
								difettoODS.motivoRisoluzioneDifetto,
								difettoODS.nrGiorniFestivi,
								difettoODS.numeroLineaDifetto,
								difettoODS.numeroTestataDifetto,
								difettoODS.provenienzaDifetto,
								difettoODS.rankStatoDifetto,
								difettoODS.severity,
								difettoODS.dmalmStatoWorkitemFk03,
								difettoODS.tempoTotRisoluzioneDifetto,
								difettoODS.idProject,
								difettoODS.descrizioneStrutturaOrg,
								difettoODS.stgPk, difettoODS.causaDifetto,
								difettoODS.naturaDifetto,
								difettoODS.dmalmUserFk06, difettoODS.uri,
								difettoODS.effortCostoSviluppo,
								difettoODS.dtDisponibilita,
								difettoODS.priority)
						.values(difetto.getCdDifetto(),
								difetto.getDmalmDifettoProdottoPk(),
								difetto.getDmalmProjectFk02(),
								difetto.getDmalmStrutturaOrgFk01(),
								difetto.getDmalmTempoFk04(),
								difetto.getDsAutoreDifetto(),
								difetto.getDsDifetto(),
								difetto.getDtCambioStatoDifetto(),
								difetto.getDtCaricamentoRecordDifetto(),
								difetto.getDtChiusuraDifetto(),
								difetto.getDtCreazioneDifetto(),
								difetto.getDtModificaRecordDifetto(),
								difetto.getDtRisoluzioneDifetto(),
								difetto.getIdAutoreDifetto(),
								difetto.getIdkAreaTematica(),
								difetto.getIdRepository(),
								difetto.getMotivoRisoluzioneDifetto(),
								difetto.getNrGiorniFestivi(),
								difetto.getNumeroLineaDifetto(),
								difetto.getNumeroTestataDifetto(),
								difetto.getProvenienzaDifetto(),
								difetto.getRankStatoDifetto(),
								difetto.getSeverity(),
								difetto.getDmalmStatoWorkitemFk03(),
								difetto.getTempoTotRisoluzioneDifetto(), null,
								null, difetto.getStgPk(),
								difetto.getCausaDifetto(),
								difetto.getNaturaDifetto(),
								difetto.getDmalmUserFk06(), difetto.getUri(),
								difetto.getEffortCostoSviluppo(),
								difetto.getDtDisponibilita(),
								//DM_ALM-320
								difetto.getPriority()).execute();
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

	public static List<DmalmDifettoProdotto> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmDifettoProdotto> res = new ArrayList<DmalmDifettoProdotto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(difettoODS).orderBy(difettoODS.cdDifetto.asc())
					.orderBy(difettoODS.dtModificaRecordDifetto.asc())
					.list(difettoODS.all());

			connection.commit();

			for (Tuple row : list) {
				DmalmDifettoProdotto bean = new DmalmDifettoProdotto();
				bean.setCdDifetto(row.get(difettoODS.cdDifetto));
				bean.setDescrizioneProject(row.get(difettoODS.idProject));
				bean.setDescrizioneUnitaOrganizzativa(row
						.get(difettoODS.descrizioneStrutturaOrg));
				bean.setDmalmDifettoProdottoPk(row
						.get(difettoODS.dmalmDifettoProdottoPk));
				bean.setDmalmProjectFk02(row.get(difettoODS.dmalmProjectFk02));
				bean.setDmalmStatoWorkitemFk03(row
						.get(difettoODS.dmalmStatoWorkitemFk03));
				bean.setDmalmStrutturaOrgFk01(row
						.get(difettoODS.dmalmStrutturaOrgFk01));
				bean.setDmalmTempoFk04(row.get(difettoODS.dmalmTempoFk04));
				bean.setDsAutoreDifetto(row.get(difettoODS.dsAutoreDifetto));
				bean.setDsDifetto(row.get(difettoODS.dsDifetto));
				bean.setDtCambioStatoDifetto(row
						.get(difettoODS.dtCambioStatoDifetto));
				bean.setDtCaricamentoRecordDifetto(row
						.get(difettoODS.dtCaricamentoRecordDifetto));
				bean.setDtChiusuraDifetto(row.get(difettoODS.dtChiusuraDifetto));
				bean.setDtCreazioneDifetto(row
						.get(difettoODS.dtCreazioneDifetto));
				bean.setDtModificaRecordDifetto(row
						.get(difettoODS.dtModificaRecordDifetto));
				bean.setDtRisoluzioneDifetto(row
						.get(difettoODS.dtRisoluzioneDifetto));
				bean.setDtStoricizzazione(row.get(difettoODS.dtStoricizzazione));
				bean.setIdAutoreDifetto(row.get(difettoODS.idAutoreDifetto));
				bean.setIdkAreaTematica(row.get(difettoODS.idkAreaTematica));
				bean.setIdRepository(row.get(difettoODS.idRepository));
				bean.setMotivoRisoluzioneDifetto(row
						.get(difettoODS.motivoRisoluzioneDifetto));
				bean.setNrGiorniFestivi(row.get(difettoODS.nrGiorniFestivi));
				bean.setNumeroLineaDifetto(row
						.get(difettoODS.numeroLineaDifetto));
				bean.setNumeroTestataDifetto(row
						.get(difettoODS.numeroTestataDifetto));
				bean.setProvenienzaDifetto(row
						.get(difettoODS.provenienzaDifetto));
				bean.setRankStatoDifetto(row.get(difettoODS.rankStatoDifetto));
				bean.setSeverity(row.get(difettoODS.severity));
				bean.setTempoTotRisoluzioneDifetto(row
						.get(difettoODS.tempoTotRisoluzioneDifetto));
				bean.setStgPk(row.get(difettoODS.stgPk));
				bean.setCausaDifetto(row.get(difettoODS.causaDifetto));
				bean.setNaturaDifetto(row.get(difettoODS.naturaDifetto));
				bean.setDmalmUserFk06(row.get(difettoODS.dmalmUserFk06));
				bean.setUri(row.get(difettoODS.uri));
				bean.setEffortCostoSviluppo(row
						.get(difettoODS.effortCostoSviluppo));
				bean.setDtDisponibilita(row.get(difettoODS.dtDisponibilita));

				res.add(bean);
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return res;

	}

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, difettoODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

}