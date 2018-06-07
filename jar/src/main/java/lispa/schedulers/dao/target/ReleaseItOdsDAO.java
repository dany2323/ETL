package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseItOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
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

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmReleaseIt releaseIt : staging_release_IT) {
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
								releaseItOds.giorniQf)
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
								releaseIt.getGiorniQf()).execute();
								

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

		List<Tuple> list = null;
		List<DmalmReleaseIt> resultList = new LinkedList<DmalmReleaseIt>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseItOds)
					.orderBy(releaseItOds.cdReleaseIt.asc())
					.orderBy(releaseItOds.dtModificaReleaseIt.asc())
					.list(releaseItOds.all());

			for (Tuple t : list) {
				DmalmReleaseIt relIt = new DmalmReleaseIt();
				
				relIt.setPriority(t.get(releaseItOds.priority));
				relIt.setSeverity(t.get(releaseItOds.severity));
				relIt.setUri(t.get(releaseItOds.uri));
				relIt.setCdReleaseIt(t.get(releaseItOds.cdReleaseIt));
				relIt.setDescrizioneReleaseIt(t.get(releaseItOds.descrizioneReleaseIt));
				relIt.setDmalmProjectFk02(t.get(releaseItOds.dmalmProjectFk02));
				relIt.setDmalmReleaseItPk(t.get(releaseItOds.dmalmReleaseItPk));
				relIt.setDmalmStatoWorkitemFk03(t.get(releaseItOds.dmalmStatoWorkitemFk03));
				relIt.setDmalmStrutturaOrgFk01(t.get(releaseItOds.dmalmStrutturaOrgFk01));
				relIt.setDmalmTempoFk04(t.get(releaseItOds.dmalmTempoFk04));
				relIt.setDsAutoreReleaseIt(t.get(releaseItOds.dsAutoreReleaseIt));
				relIt.setDtCambioStatoReleaseIt(t.get(releaseItOds.dtCambioStatoReleaseIt));
				relIt.setDtCaricamentoReleaseIt(t.get(releaseItOds.dtCaricamentoReleaseIt));
				relIt.setDtCreazioneReleaseIt(t.get(releaseItOds.dtCreazioneReleaseIt));
				relIt.setDtDisponibilitaEffRelease(t.get(releaseItOds.dtDisponibilitaEffRelease));
				relIt.setDtFineRelease(t.get(releaseItOds.dtFineRelease));
				relIt.setDtInizioRelease(t.get(releaseItOds.dtInizioRelease));
				relIt.setDtModificaReleaseIt(t.get(releaseItOds.dtModificaReleaseIt));
				relIt.setDtRilascioRelease(t.get(releaseItOds.dtRilascioRelease));
				relIt.setDtRisoluzioneReleaseIt(t.get(releaseItOds.dtRisoluzioneReleaseIt));
				relIt.setDtScadenzaReleaseIt(t.get(releaseItOds.dtScadenzaReleaseIt));
				relIt.setDtStoricizzazione(t.get(releaseItOds.dtStoricizzazione));
				relIt.setDurataEffRelease(t.get(releaseItOds.durataEffRelease));
				relIt.setIdAutoreReleaseIt(t.get(releaseItOds.idAutoreReleaseIt));
				relIt.setIdRepository(t.get(releaseItOds.idRepository));
				relIt.setMotivoRisoluzioneReleaseIt(t.get(releaseItOds.motivoRisoluzioneReleaseIt));
				relIt.setRankStatoReleaseIt(t.get(releaseItOds.rankStatoReleaseIt));
				relIt.setRankStatoReleaseItMese(t.get(releaseItOds.rankStatoReleaseItMese));
				relIt.setStgPk(t.get(releaseItOds.stgPk));
				relIt.setTitoloReleaseIt(t.get(releaseItOds.titoloReleaseIt));
				relIt.setDmalmUserFk06(t.get(releaseItOds.dmalmUserFk06));
				relIt.setTypeRelease(t.get(releaseItOds.typeRelease));
				resultList.add(relIt);
			}
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

}
