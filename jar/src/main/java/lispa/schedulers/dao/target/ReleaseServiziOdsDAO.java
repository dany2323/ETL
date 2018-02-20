package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseServiziOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
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

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmReleaseServizi release : staging_releaseservizi) {
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
								releaseserviziODS.priority)
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
								release.getSeverity(), release.getPriority())
						.execute();
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

	public static List<DmalmReleaseServizi> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmReleaseServizi> resultList = new LinkedList<DmalmReleaseServizi>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseserviziODS)
					.orderBy(releaseserviziODS.cdRelServizi.asc())
					.orderBy(releaseserviziODS.dtModificaRelServizi.asc())
					.list(releaseserviziODS.all());

			for (Tuple t : list) {
				DmalmReleaseServizi relSer = new DmalmReleaseServizi();
			
				relSer.setUri(t.get(releaseserviziODS.uri));
				relSer.setCdRelServizi(t.get(releaseserviziODS.cdRelServizi));
				relSer.setDescrizioneRelServizi(t.get(releaseserviziODS.descrizioneRelServizi));
				relSer.setDmalmProjectFk02(t.get(releaseserviziODS.dmalmProjectFk02));
				relSer.setDmalmRelServiziPk(t.get(releaseserviziODS.dmalmRelServiziPk));
				relSer.setDmalmStatoWorkitemFk03(t.get(releaseserviziODS.dmalmStatoWorkitemFk03));
				relSer.setDmalmStrutturaOrgFk01(t.get(releaseserviziODS.dmalmStrutturaOrgFk01));
				relSer.setDmalmTempoFk04(t.get(releaseserviziODS.dmalmTempoFk04));
				relSer.setDsAutoreRelServizi(t.get(releaseserviziODS.dsAutoreRelServizi));
				relSer.setDtCambioStatoRelServizi(t.get(releaseserviziODS.dtCambioStatoRelServizi));
				relSer.setDtCaricamentoRelServizi(t.get(releaseserviziODS.dtCaricamentoRelServizi));
				relSer.setDtCreazioneRelServizi(t.get(releaseserviziODS.dtCreazioneRelServizi));
				relSer.setDtModificaRelServizi(t.get(releaseserviziODS.dtModificaRelServizi));
				relSer.setDtRisoluzioneRelServizi(t.get(releaseserviziODS.dtRisoluzioneRelServizi));
				relSer.setDtScadenzaRelServizi(t.get(releaseserviziODS.dtScadenzaRelServizi));
				relSer.setDtStoricizzazione(t.get(releaseserviziODS.dtStoricizzazione));
				relSer.setIdAutoreRelServizi(t.get(releaseserviziODS.idAutoreRelServizi));
				relSer.setIdRepository(t.get(releaseserviziODS.idRepository));
				relSer.setMotivoRisoluzioneRelServizi(t.get(releaseserviziODS.motivoRisoluzioneRelServizi));
				relSer.setMotivoSospensioneReleaseSer(t.get(releaseserviziODS.motivoSospensioneReleaseSer));
				relSer.setPrevistoFermoServizioRel(t.get(releaseserviziODS.previstoFermoServizioRel));
				relSer.setRankStatoRelServizi(t.get(releaseserviziODS.rankStatoRelServizi));
				relSer.setRankStatoRelServiziMese(t.get(releaseserviziODS.rankStatoRelServiziMese));
				relSer.setRichiestaAnalisiImpattiRel(t.get(releaseserviziODS.richiestaAnalisiImpattiRel));
				relSer.setStgPk(t.get(releaseserviziODS.stgPk));
				relSer.setTitoloRelServizi(t.get(releaseserviziODS.titoloRelServizi));
				relSer.setDmalmUserFk06(t.get(releaseserviziODS.dmalmUserFk06));
				relSer.setSeverity(t.get(releaseserviziODS.severity));
				relSer.setPriority(t.get(releaseserviziODS.priority));
				resultList.add(relSer);
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
