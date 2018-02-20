package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoDemandOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgettoDemandOdsDAO {

	private static Logger logger = Logger.getLogger(ProgettoDemandOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoDemandOds progettoDemandODS = QDmalmProgettoDemandOds.dmalmProgettoDemandOds;

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoDemandODS)
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

	public static void insert(List<DmalmProgettoDemand> staging_progettoDemand,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoDemand progettoDemand : staging_progettoDemand) {
				new SQLInsertClause(connection, dialect, progettoDemandODS)
						.columns(progettoDemandODS.cdProgettoDemand,
								progettoDemandODS.descrizioneProgettoDemand,
								progettoDemandODS.dmalmProgettoDemandPk,
								progettoDemandODS.dmalmProjectFk02,
								progettoDemandODS.dmalmStatoWorkitemFk03,
								progettoDemandODS.dmalmStrutturaOrgFk01,
								progettoDemandODS.dmalmTempoFk04,
								progettoDemandODS.dsAutoreProgettoDemand,
								progettoDemandODS.dtCambioStatoProgettoDem,
								progettoDemandODS.dtCaricamentoProgettoDemand,
								progettoDemandODS.dtCreazioneProgettoDemand,
								progettoDemandODS.dtModificaProgettoDemand,
								progettoDemandODS.dtRisoluzioneProgettoDemand,
								progettoDemandODS.dtScadenzaProgettoDemand,
								progettoDemandODS.dtStoricizzazione,
								progettoDemandODS.idAutoreProgettoDemand,
								progettoDemandODS.idRepository,
								progettoDemandODS.motivoRisoluzioneProgDem,
								progettoDemandODS.rankStatoProgettoDemand,
								progettoDemandODS.tempoTotaleRisoluzione,
								progettoDemandODS.titoloProgettoDemand,
								progettoDemandODS.dtChiusuraProgettoDemand,
								progettoDemandODS.codice,
								progettoDemandODS.cfOwnerDemand,
								progettoDemandODS.cfDtEnunciazione,
								progettoDemandODS.cfDtDisponibilitaEff,
								progettoDemandODS.cfDtDisponibilita,
								progettoDemandODS.cfDtValidazione,
								progettoDemandODS.cfReferenteSviluppo,
								progettoDemandODS.cfReferenteEsercizio,
								progettoDemandODS.aoid,
								progettoDemandODS.stgPk,
								progettoDemandODS.fornitura,
								progettoDemandODS.dmalmUserFk06,
								progettoDemandODS.uri,
								progettoDemandODS.codObiettivoAziendale,
								progettoDemandODS.codObiettivoUtente,
								progettoDemandODS.cfClassificazione,
								progettoDemandODS.severity, 
								progettoDemandODS.priority)
						.values(progettoDemand.getCdProgettoDemand(),
								progettoDemand.getDescrizioneProgettoDemand(),
								progettoDemand.getDmalmProgettoDemandPk(),
								progettoDemand.getDmalmProjectFk02(),
								progettoDemand.getDmalmStatoWorkitemFk03(),
								progettoDemand.getDmalmStrutturaOrgFk01(),
								progettoDemand.getDmalmTempoFk04(),
								progettoDemand.getDsAutoreProgettoDemand(),
								progettoDemand.getDtCambioStatoProgettoDem(),
								progettoDemand.getDtCaricamentoProgettoDemand(),
								progettoDemand.getDtCreazioneProgettoDemand(),
								progettoDemand.getDtModificaProgettoDemand(),
								progettoDemand.getDtRisoluzioneProgettoDemand(),
								progettoDemand.getDtScadenzaProgettoDemand(),
								progettoDemand.getDtModificaProgettoDemand(),
								progettoDemand.getIdAutoreProgettoDemand(),
								progettoDemand.getIdRepository(),
								progettoDemand.getMotivoRisoluzioneProgDem(),
								new Double(1),
								progettoDemand.getTempoTotaleRisoluzione(),
								progettoDemand.getTitoloProgettoDemand(),
								progettoDemand.getDtChiusuraProgettoDemand(),
								progettoDemand.getCodice(),
								progettoDemand.getCfOwnerDemand(),
								progettoDemand.getCfDtEnunciazione(),
								progettoDemand.getCfDtDisponibilitaEff(),
								progettoDemand.getCfDtDisponibilita(),
								progettoDemand.getCfDtValidazione(),
								progettoDemand.getCfReferenteSviluppo(),
								progettoDemand.getCfReferenteEsercizio(),
								progettoDemand.getAoid(),
								progettoDemand.getStgPk(),
								progettoDemand.getFornitura(),
								progettoDemand.getDmalmUserFk06(),
								progettoDemand.getUri(),
								progettoDemand.getCodObiettivoAziendale(),
								progettoDemand.getCodObiettivoUtente(),
								progettoDemand.getCfClassificazione(),
								//DM_ALM-320
								progettoDemand.getSeverity(),
								progettoDemand.getPriority())
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

	public static List<DmalmProgettoDemand> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmProgettoDemand> resultList = new LinkedList<DmalmProgettoDemand>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoDemandODS)
					.orderBy(progettoDemandODS.cdProgettoDemand.asc())
					.orderBy(progettoDemandODS.dtModificaProgettoDemand.asc())
					.list(progettoDemandODS.all());

			for ( Tuple t : list) {
				DmalmProgettoDemand pDemand = new DmalmProgettoDemand();
				pDemand.setCodObiettivoAziendale(t.get(progettoDemandODS.codObiettivoAziendale));
				pDemand.setCodObiettivoUtente(t.get(progettoDemandODS.codObiettivoUtente));
				pDemand.setUri(t.get(progettoDemandODS.uri));
				pDemand.setStgPk(t.get(progettoDemandODS.stgPk));
				pDemand.setAoid(t.get(progettoDemandODS.aoid));
				pDemand.setCdProgettoDemand(t.get(progettoDemandODS.cdProgettoDemand));
				pDemand.setCfDtDisponibilita(t.get(progettoDemandODS.cfDtDisponibilita));
				pDemand.setCfDtDisponibilitaEff(t.get(progettoDemandODS.cfDtDisponibilitaEff));
				pDemand.setCfDtEnunciazione(t.get(progettoDemandODS.cfDtEnunciazione));
				pDemand.setCfDtValidazione(t.get(progettoDemandODS.cfDtValidazione));
				pDemand.setCfOwnerDemand(t.get(progettoDemandODS.cfOwnerDemand));
				pDemand.setCfReferenteEsercizio(t.get(progettoDemandODS.cfReferenteEsercizio));
				pDemand.setCfReferenteSviluppo(t.get(progettoDemandODS.cfReferenteSviluppo));
				pDemand.setCodice(t.get(progettoDemandODS.codice));
				pDemand.setDescrizioneProgettoDemand(t.get(progettoDemandODS.descrizioneProgettoDemand));
				pDemand.setDmalmProgettoDemandPk(t.get(progettoDemandODS.dmalmProgettoDemandPk));
				pDemand.setDmalmProjectFk02(t.get(progettoDemandODS.dmalmProjectFk02));
				pDemand.setDmalmStatoWorkitemFk03(t.get(progettoDemandODS.dmalmStatoWorkitemFk03));
				pDemand.setDmalmStrutturaOrgFk01(t.get(progettoDemandODS.dmalmStrutturaOrgFk01));
				pDemand.setDmalmTempoFk04(t.get(progettoDemandODS.dmalmTempoFk04));
				pDemand.setDsAutoreProgettoDemand(t.get(progettoDemandODS.dsAutoreProgettoDemand));
				pDemand.setDtCambioStatoProgettoDem(t.get(progettoDemandODS.dtCambioStatoProgettoDem));
				pDemand.setDtCaricamentoProgettoDemand(t.get(progettoDemandODS.dtCaricamentoProgettoDemand));
				pDemand.setDtChiusuraProgettoDemand(t.get(progettoDemandODS.dtChiusuraProgettoDemand));
				pDemand.setDtCreazioneProgettoDemand(t.get(progettoDemandODS.dtCreazioneProgettoDemand));
				pDemand.setDtModificaProgettoDemand(t.get(progettoDemandODS.dtModificaProgettoDemand));
				pDemand.setDtRisoluzioneProgettoDemand(t.get(progettoDemandODS.dtRisoluzioneProgettoDemand));
				pDemand.setDtScadenzaProgettoDemand(t.get(progettoDemandODS.dtScadenzaProgettoDemand));
				pDemand.setDtStoricizzazione(t.get(progettoDemandODS.dtStoricizzazione));
				pDemand.setFornitura(t.get(progettoDemandODS.fornitura));
				pDemand.setIdAutoreProgettoDemand(t.get(progettoDemandODS.idAutoreProgettoDemand));
				pDemand.setIdRepository(t.get(progettoDemandODS.idRepository));
				pDemand.setMotivoRisoluzioneProgDem(t.get(progettoDemandODS.motivoRisoluzioneProgDem));
				pDemand.setRankStatoProgettoDemMese(t.get(progettoDemandODS.rankStatoProgettoDemMese));
				pDemand.setRankStatoProgettoDemand(t.get(progettoDemandODS.rankStatoProgettoDemand));
				pDemand.setTempoTotaleRisoluzione(t.get(progettoDemandODS.tempoTotaleRisoluzione));
				pDemand.setTitoloProgettoDemand(t.get(progettoDemandODS.titoloProgettoDemand));
				pDemand.setDmalmUserFk06(t.get(progettoDemandODS.dmalmUserFk06));
				pDemand.setCfClassificazione(t.get(progettoDemandODS.cfClassificazione));
				pDemand.setSeverity(t.get(progettoDemandODS.severity));
				pDemand.setPriority(t.get(progettoDemandODS.priority));
				
				resultList.add(pDemand);
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
