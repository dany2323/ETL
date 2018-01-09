package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseDiProgettoOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ReleaseDiProgettoOdsDAO {

	private static Logger logger = Logger
			.getLogger(ReleaseDiProgettoOdsDAO.class);

	private static QDmalmReleaseDiProgettoOds releaseODS = QDmalmReleaseDiProgettoOds.dmalmReleaseDiProgettoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, releaseODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmReleaseDiProgetto> staging_releases,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmReleaseDiProgetto release : staging_releases) {

				if(listPk.contains(release.getDmalmReleasediprogPk()))
					logger.info("Trovata DmalmReleasediprogPk DUPLICATA!!!"+release.getDmalmReleasediprogPk());
				else{
					
				listPk.add(release.getDmalmReleasediprogPk());
				new SQLInsertClause(connection, dialect, releaseODS)
						.columns(releaseODS.cdReleasediprog, releaseODS.codice,
								releaseODS.dataDisponibilitaEff,
								releaseODS.dataPassaggioInEsercizio,
								releaseODS.descrizioneReleasediprog,
								releaseODS.dmalmProjectFk02,
								releaseODS.dmalmReleasediprogPk,
								releaseODS.dmalmStatoWorkitemFk03,
								releaseODS.dmalmStrutturaOrgFk01,
								releaseODS.dmalmTempoFk04,
								releaseODS.dsAutoreReleasediprog,
								releaseODS.dtCambioStatoReleasediprog,
								releaseODS.dtCaricamentoReleasediprog,
								releaseODS.dtCreazioneReleasediprog,
								releaseODS.dtModificaReleasediprog,
								releaseODS.dtRisoluzioneReleasediprog,
								releaseODS.dtScadenzaReleasediprog,
								releaseODS.dtStoricizzazione,
								releaseODS.fornitura, releaseODS.fr,
								releaseODS.idAutoreReleasediprog,
								releaseODS.idRepository,
								releaseODS.motivoRisoluzioneRelProg,
								releaseODS.numeroLinea,
								releaseODS.numeroTestata,
								releaseODS.rankStatoReleasediprog,
								releaseODS.titoloReleasediprog,
								releaseODS.versione, releaseODS.stgPk,
								releaseODS.dmalmAreaTematicaFk05,
								releaseODS.dmalmUserFk06, releaseODS.uri,
								releaseODS.dtInizioQF, releaseODS.dtFineQF,
								releaseODS.numQuickFix,
								releaseODS.severity, releaseODS.priority)
						.values(release.getCdReleasediprog(),
								release.getCodice(),
								release.getDataDisponibilitaEff(),
								release.getDataPassaggioInEsercizio(),
								release.getDescrizioneReleasediprog(),
								release.getDmalmProjectFk02(),
								release.getDmalmReleasediprogPk(),
								release.getDmalmStatoWorkitemFk03(),
								release.getDmalmStrutturaOrgFk01(),
								release.getDmalmTempoFk04(),
								release.getDsAutoreReleasediprog(),
								release.getDtCambioStatoReleasediprog(),
								release.getDtCaricamentoReleasediprog(),
								release.getDtCreazioneReleasediprog(),
								release.getDtModificaReleasediprog(),
								release.getDtRisoluzioneReleasediprog(),
								release.getDtScadenzaReleasediprog(),
								release.getDtModificaReleasediprog(),
								release.getFornitura(), release.getFr(),
								release.getIdAutoreReleasediprog(),
								release.getIdRepository(),
								release.getMotivoRisoluzioneRelProg(),
								release.getNumeroLinea(),
								release.getNumeroTestata(), new Double(1),
								release.getTitoloReleasediprog(),
								release.getVersione(), release.getStgPk(),
								release.getDmalmAreaTematicaFk05(),
								release.getDmalmUserFk06(), release.getUri(),
								release.getDtInizioQF(), release.getDtFineQF(),
								release.getNumQuickFix(),
								//DM_ALM-320
								release.getSeverity(), release.getPriority()).execute();
				}
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

	public static List<DmalmReleaseDiProgetto> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmReleaseDiProgetto> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseODS)
					.orderBy(releaseODS.cdReleasediprog.asc())
					.orderBy(releaseODS.dtModificaReleasediprog.asc())
					.list(Projections.bean(DmalmReleaseDiProgetto.class,
							releaseODS.all()));

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
