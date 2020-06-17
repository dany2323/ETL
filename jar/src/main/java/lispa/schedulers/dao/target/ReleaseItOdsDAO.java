package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseItOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ReleaseItOdsDAO {

	private static Logger logger = Logger.getLogger(ReleaseItOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseItOds releaseItOds = QDmalmReleaseItOds.dmalmReleaseItOds;

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, releaseItOds).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insert(List<DmalmReleaseIt> staging_release_IT,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Long> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmReleaseIt releaseIt : staging_release_IT) {
				if(listPk.contains(releaseIt.getDmalmReleaseItPk()))
					logger.info("Trovata DmalmReleaseItPk DUPLICATA!!!"+releaseIt.getDmalmReleaseItPk());
				else{
					
					listPk.add(releaseIt.getDmalmReleaseItPk());
					new SQLInsertClause(connection, dialect, releaseItOds)
							.columns(releaseItOds.cdReleaseIt,
									releaseItOds.descrizioneReleaseIt,
									releaseItOds.dmalmReleaseItPk,
									releaseItOds.dmalmProjectFk02,
									releaseItOds.dmalmStatoWorkitemFk03,
									releaseItOds.dmalmStrutturaOrgFk01,
									releaseItOds.dmalmTempoFk04,
									releaseItOds.dsAutoreReleaseIt,
									releaseItOds.dtCambioStatoReleaseIt,
									releaseItOds.dtCaricamentoReleaseIt,
									releaseItOds.dtCreazioneReleaseIt,
									releaseItOds.dtModificaReleaseIt,
									releaseItOds.dtRisoluzioneReleaseIt,
									releaseItOds.dtScadenzaReleaseIt,
									releaseItOds.dtStoricizzazione,
									releaseItOds.idAutoreReleaseIt,
									releaseItOds.idRepository,
									releaseItOds.motivoRisoluzioneReleaseIt,
									releaseItOds.rankStatoReleaseIt,
									releaseItOds.durataEffRelease,
									releaseItOds.titoloReleaseIt,
									releaseItOds.stgPk, releaseItOds.dtFineRelease,
									releaseItOds.dtDisponibilitaEffRelease,
									releaseItOds.dtRilascioRelease,
									releaseItOds.dtInizioRelease,
									releaseItOds.dmalmUserFk06, 
									releaseItOds.uri,
									releaseItOds.severity,
									releaseItOds.priority,
									releaseItOds.typeRelease,
									releaseItOds.motivoSospensione,
									releaseItOds.counterQf,
									releaseItOds.giorniQf,
									releaseItOds.tagAlm, releaseItOds.tsTagAlm)
							.values(releaseIt.getCdReleaseIt(),
									releaseIt.getDescrizioneReleaseIt(),
									releaseIt.getDmalmReleaseItPk(),
									releaseIt.getDmalmProjectFk02(),
									releaseIt.getDmalmStatoWorkitemFk03(),
									releaseIt.getDmalmStrutturaOrgFk01(),
									releaseIt.getDmalmTempoFk04(),
									releaseIt.getDsAutoreReleaseIt(),
									releaseIt.getDtCambioStatoReleaseIt(),
									releaseIt.getDtCaricamentoReleaseIt(),
									releaseIt.getDtCreazioneReleaseIt(),
									releaseIt.getDtModificaReleaseIt(),
									releaseIt.getDtRisoluzioneReleaseIt(),
									releaseIt.getDtScadenzaReleaseIt(),
									releaseIt.getDtModificaReleaseIt(),
									releaseIt.getIdAutoreReleaseIt(),
									releaseIt.getIdRepository(),
									releaseIt.getMotivoRisoluzioneReleaseIt(),
									new Double(1), releaseIt.getDurataEffRelease(),
									releaseIt.getTitoloReleaseIt(),
									releaseIt.getStgPk(),
									releaseIt.getDtFineRelease(),
									releaseIt.getDtDisponibilitaEffRelease(),
									releaseIt.getDtRilascioRelease(),
									releaseIt.getDtInizioRelease(),
									releaseIt.getDmalmUserFk06(),
									releaseIt.getUri(),
									releaseIt.getSeverity(),
									releaseIt.getPriority(),
									releaseIt.getTypeRelease(),
									releaseIt.getMotivoSospensione(),
									releaseIt.getCounterQf(),
									releaseIt.getGiorniQf(),
									releaseIt.getTagAlm(), releaseIt.getTsTagAlm()).execute();
									
				}
				connection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmReleaseIt> getAll() throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmReleaseIt> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseItOds)
					.orderBy(releaseItOds.cdReleaseIt.asc())
					.orderBy(releaseItOds.dtModificaReleaseIt.asc())
					.list(Projections.bean(DmalmReleaseIt.class,
							releaseItOds.all()));

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
