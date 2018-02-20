package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmRichiestaGestioneOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class RichiestaGestioneOdsDAO {

	private static Logger logger = Logger
			.getLogger(RichiestaGestioneOdsDAO.class);

	private static QDmalmRichiestaGestioneOds richiestaODS = QDmalmRichiestaGestioneOds.dmalmRichiestaGestioneOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, richiestaODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmRichiestaGestione> staging_richieste,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmRichiestaGestione richiesta : staging_richieste) {

				new SQLInsertClause(connection, dialect, richiestaODS)
						.columns(richiestaODS.categoria,
								richiestaODS.cdRichiestaGest,
								richiestaODS.dataChiusura,
								richiestaODS.dataDisponibilita,
								richiestaODS.descrizioneRichiestaGest,
								richiestaODS.dmalmProjectFk02,
								richiestaODS.dmalmRichiestaGestPk,
								richiestaODS.dmalmStatoWorkitemFk03,
								richiestaODS.dmalmStrutturaOrgFk01,
								richiestaODS.dmalmTempoFk04,
								richiestaODS.dsAutoreRichiestaGest,
								richiestaODS.dtCambioStatoRichiestaGest,
								richiestaODS.dtCaricamentoRichiestaGest,
								richiestaODS.dtCreazioneRichiestaGest,
								richiestaODS.dtModificaRichiestaGest,
								richiestaODS.dtRisoluzioneRichiestaGest,
								richiestaODS.dtScadenzaRichiestaGest,
								richiestaODS.dtStoricizzazione,
								richiestaODS.idAutoreRichiestaGest,
								richiestaODS.idRepository,
								richiestaODS.motivoRisoluzioneRichGest,
								richiestaODS.stgPk, richiestaODS.ticketid,
								richiestaODS.titoloRichiestaGest,
								richiestaODS.dmalmUserFk06, richiestaODS.uri,
								richiestaODS.severity, richiestaODS.priority)
						.values(richiesta.getCategoria(),
								richiesta.getCdRichiestaGest(),
								richiesta.getDataChiusura(),
								richiesta.getDataDisponibilita(),
								richiesta.getDescrizioneRichiestaGest(),
								richiesta.getDmalmProjectFk02(),
								richiesta.getDmalmRichiestaGestPk(),
								richiesta.getDmalmStatoWorkitemFk03(),
								richiesta.getDmalmStrutturaOrgFk01(),
								richiesta.getDmalmTempoFk04(),
								richiesta.getDsAutoreRichiestaGest(),
								richiesta.getDtCambioStatoRichiestaGest(),
								richiesta.getDtCaricamentoRichiestaGest(),
								richiesta.getDtCreazioneRichiestaGest(),
								richiesta.getDtModificaRichiestaGest(),
								richiesta.getDtRisoluzioneRichiestaGest(),
								richiesta.getDtScadenzaRichiestaGest(),
								richiesta.getDtModificaRichiestaGest(),
								richiesta.getIdAutoreRichiestaGest(),
								richiesta.getIdRepository(),
								richiesta.getMotivoRisoluzioneRichGest(),
								richiesta.getStgPk(), richiesta.getTicketid(),
								richiesta.getTitoloRichiestaGest(),
								richiesta.getDmalmUserFk06(),
								richiesta.getUri(),
								richiesta.getSeverity(), richiesta.getPriority()).execute();

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

	public static List<DmalmRichiestaGestione> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmRichiestaGestione> resultList = new LinkedList<DmalmRichiestaGestione>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(richiestaODS)
					.orderBy(richiestaODS.cdRichiestaGest.asc())
					.orderBy(richiestaODS.dtModificaRichiestaGest.asc())
					.list(richiestaODS.all());

			for(Tuple t : list) {
				DmalmRichiestaGestione richGest = new DmalmRichiestaGestione();
				
				richGest.setUri(t.get(richiestaODS.uri));
				richGest.setCategoria(t.get(richiestaODS.categoria));
				richGest.setCdRichiestaGest(t.get(richiestaODS.cdRichiestaGest));
				richGest.setDataChiusura(t.get(richiestaODS.dataChiusura));
				richGest.setDataDisponibilita(t.get(richiestaODS.dataDisponibilita));
				richGest.setDescrizioneRichiestaGest(t.get(richiestaODS.descrizioneRichiestaGest));
				richGest.setDmalmProjectFk02(t.get(richiestaODS.dmalmProjectFk02));
				richGest.setDmalmRichiestaGestPk(t.get(richiestaODS.dmalmRichiestaGestPk));
				richGest.setDmalmStatoWorkitemFk03(t.get(richiestaODS.dmalmStatoWorkitemFk03));
				richGest.setDmalmStrutturaOrgFk01(t.get(richiestaODS.dmalmStrutturaOrgFk01));
				richGest.setDmalmTempoFk04(t.get(richiestaODS.dmalmTempoFk04));
				richGest.setDsAutoreRichiestaGest(t.get(richiestaODS.dsAutoreRichiestaGest));
				richGest.setDtCambioStatoRichiestaGest(t.get(richiestaODS.dtCambioStatoRichiestaGest));
				richGest.setDtCaricamentoRichiestaGest(t.get(richiestaODS.dtCaricamentoRichiestaGest));
				richGest.setDtCreazioneRichiestaGest(t.get(richiestaODS.dtCreazioneRichiestaGest));
				richGest.setDtModificaRichiestaGest(t.get(richiestaODS.dtModificaRichiestaGest));
				richGest.setDtRisoluzioneRichiestaGest(t.get(richiestaODS.dtRisoluzioneRichiestaGest));
				richGest.setDtScadenzaRichiestaGest(t.get(richiestaODS.dtScadenzaRichiestaGest));
				richGest.setDtStoricizzazione(t.get(richiestaODS.dtStoricizzazione));
				richGest.setIdAutoreRichiestaGest(t.get(richiestaODS.idAutoreRichiestaGest));
				richGest.setIdRepository(t.get(richiestaODS.idRepository));
				richGest.setMotivoRisoluzioneRichGest(t.get(richiestaODS.motivoRisoluzioneRichGest));
				richGest.setRankStatoRichiestaGest(t.get(richiestaODS.rankStatoRichiestaGest));
				richGest.setRankStatoRichiestaGestMese(t.get(richiestaODS.rankStatoRichiestaGestMese));
				richGest.setStgPk(t.get(richiestaODS.stgPk));
				richGest.setTicketid(t.get(richiestaODS.ticketid));
				richGest.setTitoloRichiestaGest(t.get(richiestaODS.titoloRichiestaGest));
				richGest.setDmalmUserFk06(t.get(richiestaODS.dmalmUserFk06));
				richGest.setSeverity(t.get(richiestaODS.severity));
				richGest.setPriority(t.get(richiestaODS.priority));
				resultList.add(richGest);
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
