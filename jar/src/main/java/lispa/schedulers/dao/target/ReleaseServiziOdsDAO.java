package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseServiziOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ReleaseServiziOdsDAO {

	private static Logger logger = Logger.getLogger(ReleaseServiziOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseServiziOds releaseserviziODS = QDmalmReleaseServiziOds.dmalmReleaseServiziOds;

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, releaseserviziODS)
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

	public static void insert(List<DmalmReleaseServizi> staging_releaseservizi,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Long> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmReleaseServizi release : staging_releaseservizi) {
				if(listPk.contains(release.getDmalmRelServiziPk()))
					logger.info("Trovata DmalmRelServiziPk DUPLICATA!!!"+release.getDmalmRelServiziPk());
				else{
					
					listPk.add(release.getDmalmRelServiziPk());
					new SQLInsertClause(connection, dialect, releaseserviziODS)
							.columns(releaseserviziODS.cdRelServizi,
									releaseserviziODS.descrizioneRelServizi,
									releaseserviziODS.dmalmRelServiziPk,
									releaseserviziODS.dmalmProjectFk02,
									releaseserviziODS.dmalmStatoWorkitemFk03,
									releaseserviziODS.dmalmStrutturaOrgFk01,
									releaseserviziODS.dmalmTempoFk04,
									releaseserviziODS.dsAutoreRelServizi,
									releaseserviziODS.dtCambioStatoRelServizi,
									releaseserviziODS.dtCaricamentoRelServizi,
									releaseserviziODS.dtCreazioneRelServizi,
									releaseserviziODS.dtModificaRelServizi,
									releaseserviziODS.dtRisoluzioneRelServizi,
									releaseserviziODS.dtScadenzaRelServizi,
									releaseserviziODS.dtStoricizzazione,
									releaseserviziODS.idAutoreRelServizi,
									releaseserviziODS.idRepository,
									releaseserviziODS.motivoRisoluzioneRelServizi,
									releaseserviziODS.rankStatoRelServizi,
									releaseserviziODS.titoloRelServizi,
									releaseserviziODS.stgPk,
									releaseserviziODS.motivoSospensioneReleaseSer,
									releaseserviziODS.previstoFermoServizioRel,
									releaseserviziODS.richiestaAnalisiImpattiRel,
									releaseserviziODS.dmalmUserFk06,
									releaseserviziODS.uri,
									releaseserviziODS.severity,
									releaseserviziODS.priority,
									releaseserviziODS.tagAlm, releaseserviziODS.tsTagAlm)
							.values(release.getCdRelServizi(),
									release.getDescrizioneRelServizi(),
									release.getDmalmRelServiziPk(),
									release.getDmalmProjectFk02(),
									release.getDmalmStatoWorkitemFk03(),
									release.getDmalmStrutturaOrgFk01(),
									release.getDmalmTempoFk04(),
									release.getDsAutoreRelServizi(),
									release.getDtCambioStatoRelServizi(),
									release.getDtCaricamentoRelServizi(),
									release.getDtCreazioneRelServizi(),
									release.getDtModificaRelServizi(),
									release.getDtRisoluzioneRelServizi(),
									release.getDtScadenzaRelServizi(),
									release.getDtModificaRelServizi(),
									release.getIdAutoreRelServizi(),
									release.getIdRepository(),
									release.getMotivoRisoluzioneRelServizi(),
									new Double(1), release.getTitoloRelServizi(),
									release.getStgPk(),
									release.getMotivoSospensioneReleaseSer(),
									release.getPrevistoFermoServizioRel(),
									release.getRichiestaAnalisiImpattiRel(),
									release.getDmalmUserFk06(), release.getUri(),
									release.getSeverity(), release.getPriority(),
									release.getTagAlm(), release.getTsTagAlm())
							.execute();
					connection.commit();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmReleaseServizi> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmReleaseServizi> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseserviziODS)
					.orderBy(releaseserviziODS.cdRelServizi.asc())
					.orderBy(releaseserviziODS.dtModificaRelServizi.asc())
					.list(Projections.bean(DmalmReleaseServizi.class,
							releaseserviziODS.all()));

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
