package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoSviluppoDOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgettoSviluppoDemandOdsDAO {

	private static Logger logger = Logger
			.getLogger(ProgettoSviluppoSviluppoOdsDAO.class);

	private static QDmalmProgettoSviluppoDOds progettoSvilDODS = QDmalmProgettoSviluppoDOds.dmalmProgettoSviluppoDOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoSvilDODS)
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

	public static void insert(
			List<DmalmProgettoSviluppoDem> staging_progettoSviluppoSvil,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoSviluppoDem progettoSviluppo : staging_progettoSviluppoSvil) {
				new SQLInsertClause(connection, dialect, progettoSvilDODS)
						.columns(progettoSvilDODS.cdProgSvilD,
								progettoSvilDODS.cfCodice,
								progettoSvilDODS.cfDataDispEffettiva,
								progettoSvilDODS.cfDataDispPianificata,
								progettoSvilDODS.cfDataInizio,
								progettoSvilDODS.cfDataInizioEff,
								progettoSvilDODS.cfFornitura,
								progettoSvilDODS.descrizioneProgSvilD,
								progettoSvilDODS.dmalmProgSvilDPk,
								progettoSvilDODS.dmalmProjectFk02,
								progettoSvilDODS.dmalmStatoWorkitemFk03,
								progettoSvilDODS.dmalmStrutturaOrgFk01,
								progettoSvilDODS.dmalmTempoFk04,
								progettoSvilDODS.dsAutoreProgSvilD,
								progettoSvilDODS.dtCambioStatoProgSvilD,
								progettoSvilDODS.dtCaricamentoProgSvilD,
								progettoSvilDODS.dtCreazioneProgSvilD,
								progettoSvilDODS.dtModificaProgSvilD,
								progettoSvilDODS.dtPassaggioEsercizio,
								progettoSvilDODS.dtRisoluzioneProgSvilD,
								progettoSvilDODS.dtScadenzaProgSvilD,
								progettoSvilDODS.dtStoricizzazione,
								progettoSvilDODS.idAutoreProgSvilD,
								progettoSvilDODS.idRepository,
								progettoSvilDODS.motivoRisoluzioneProgSvilD,
								progettoSvilDODS.priorityProgettoSvilDemand,
								progettoSvilDODS.rankStatoProgSvilD,
								progettoSvilDODS.severityProgettoSvilDemand,
								progettoSvilDODS.tempoTotaleRisoluzione,
								progettoSvilDODS.titoloProgSvilD,
								progettoSvilDODS.stgPk,
								progettoSvilDODS.dmalmUserFk06,
								progettoSvilDODS.uri)
						.values(progettoSviluppo.getCdProgSvilD(),
								progettoSviluppo.getCfCodice(),
								progettoSviluppo.getCfDataDispEffettiva(),
								progettoSviluppo.getCfDataDispPianificata(),
								progettoSviluppo.getCfDataInizio(),
								progettoSviluppo.getCfDataInizioEff(),
								progettoSviluppo.getCfFornitura(),
								progettoSviluppo.getDescrizioneProgSvilD(),
								progettoSviluppo.getDmalmProgSvilDPk(),
								progettoSviluppo.getDmalmProjectFk02(),
								progettoSviluppo.getDmalmStatoWorkitemFk03(),
								progettoSviluppo.getDmalmStrutturaOrgFk01(),
								progettoSviluppo.getDmalmTempoFk04(),
								progettoSviluppo.getDsAutoreProgSvilD(),
								progettoSviluppo.getDtCambioStatoProgSvilD(),
								progettoSviluppo.getDtCaricamentoProgSvilD(),
								progettoSviluppo.getDtCreazioneProgSvilD(),
								progettoSviluppo.getDtModificaProgSvilD(),
								progettoSviluppo.getDtPassaggioEsercizio(),
								progettoSviluppo.getDtRisoluzioneProgSvilD(),
								progettoSviluppo.getDtScadenzaProgSvilD(),
								progettoSviluppo.getDtModificaProgSvilD(),
								progettoSviluppo.getIdAutoreProgSvilD(),
								progettoSviluppo.getIdRepository(),
								progettoSviluppo
										.getMotivoRisoluzioneProgSvilD(),
								progettoSviluppo
										.getPriorityProgettoSvilDemand(),
								new Double(1),
								progettoSviluppo
										.getSeverityProgettoSvilDemand(),
								progettoSviluppo.getTempoTotaleRisoluzione(),
								progettoSviluppo.getTitoloProgSvilD(),
								progettoSviluppo.getStgPk(),
								progettoSviluppo.getDmalmUserFk06(),
								progettoSviluppo.getUri()).execute();
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

	public static List<DmalmProgettoSviluppoDem> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmProgettoSviluppoDem> resultList = new LinkedList<DmalmProgettoSviluppoDem>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoSvilDODS)
					.orderBy(progettoSvilDODS.cdProgSvilD.asc())
					.orderBy(progettoSvilDODS.dtModificaProgSvilD.asc())
					.list(progettoSvilDODS.all());
			
			for (Tuple t : list) {
				DmalmProgettoSviluppoDem pSvilDem = new DmalmProgettoSviluppoDem();
				
				pSvilDem.setUri(t.get(progettoSvilDODS.uri));
				pSvilDem.setStgPk(t.get(progettoSvilDODS.stgPk));
				pSvilDem.setAssignee(t.get(progettoSvilDODS.assignee));
				pSvilDem.setCdProgSvilD(t.get(progettoSvilDODS.cdProgSvilD));
				pSvilDem.setCfCodice(t.get(progettoSvilDODS.cfCodice));
				pSvilDem.setCfDataDispEffettiva(t.get(progettoSvilDODS.cfDataDispEffettiva));
				pSvilDem.setCfDataDispPianificata(t.get(progettoSvilDODS.cfDataDispPianificata));
				pSvilDem.setCfDataInizio(t.get(progettoSvilDODS.cfDataInizio));
				pSvilDem.setCfDataInizioEff(t.get(progettoSvilDODS.cfDataInizioEff));
				pSvilDem.setCfFornitura(t.get(progettoSvilDODS.cfFornitura));
				pSvilDem.setDescrizioneProgSvilD(t.get(progettoSvilDODS.descrizioneProgSvilD));
				pSvilDem.setDmalmProgSvilDPk(t.get(progettoSvilDODS.dmalmProgSvilDPk));
				pSvilDem.setDmalmProjectFk02(t.get(progettoSvilDODS.dmalmProjectFk02));
				pSvilDem.setDmalmStatoWorkitemFk03(t.get(progettoSvilDODS.dmalmStatoWorkitemFk03));
				pSvilDem.setDmalmStrutturaOrgFk01(t.get(progettoSvilDODS.dmalmStrutturaOrgFk01));
				pSvilDem.setDmalmTempoFk04(t.get(progettoSvilDODS.dmalmTempoFk04));
				pSvilDem.setDsAutoreProgSvilD(t.get(progettoSvilDODS.dsAutoreProgSvilD));
				pSvilDem.setDtCambioStatoProgSvilD(t.get(progettoSvilDODS.dtCambioStatoProgSvilD));
				pSvilDem.setDtCaricamentoProgSvilD(t.get(progettoSvilDODS.dtCaricamentoProgSvilD));
				pSvilDem.setDtCreazioneProgSvilD(t.get(progettoSvilDODS.dtCreazioneProgSvilD));
				pSvilDem.setDtModificaProgSvilD(t.get(progettoSvilDODS.dtModificaProgSvilD));
				pSvilDem.setDtPassaggioEsercizio(t.get(progettoSvilDODS.dtPassaggioEsercizio));
				pSvilDem.setDtRisoluzioneProgSvilD(t.get(progettoSvilDODS.dtRisoluzioneProgSvilD));
				pSvilDem.setDtScadenzaProgSvilD(t.get(progettoSvilDODS.dtScadenzaProgSvilD));
				pSvilDem.setDtStoricizzazione(t.get(progettoSvilDODS.dtStoricizzazione));
				pSvilDem.setIdAutoreProgSvilD(t.get(progettoSvilDODS.idAutoreProgSvilD));
				pSvilDem.setIdRepository(t.get(progettoSvilDODS.idRepository));
				pSvilDem.setMotivoRisoluzioneProgSvilD(t.get(progettoSvilDODS.motivoRisoluzioneProgSvilD));
				pSvilDem.setPriorityProgettoSvilDemand(t.get(progettoSvilDODS.priorityProgettoSvilDemand));
				pSvilDem.setRankStatoProgSvilD(t.get(progettoSvilDODS.rankStatoProgSvilD));
				pSvilDem.setRankStatoProgSvilDMese(t.get(progettoSvilDODS.rankStatoProgSvilDMese));
				pSvilDem.setSeverityProgettoSvilDemand(t.get(progettoSvilDODS.severityProgettoSvilDemand));
				pSvilDem.setTempoTotaleRisoluzione(t.get(progettoSvilDODS.tempoTotaleRisoluzione));
				pSvilDem.setTitoloProgSvilD(t.get(progettoSvilDODS.titoloProgSvilD));
				pSvilDem.setDmalmUserFk06(t.get(progettoSvilDODS.dmalmUserFk06));
				pSvilDem.setDtScadenza(t.get(progettoSvilDODS.dtScadenzaProgSvilD));
				resultList.add(pSvilDem);
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
