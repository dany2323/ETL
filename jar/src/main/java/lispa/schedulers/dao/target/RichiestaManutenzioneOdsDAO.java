package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmRichManutenzioneOds;

public class RichiestaManutenzioneOdsDAO {

	private static Logger logger = Logger.getLogger(DocumentoOdsDAO.class);

	private static QDmalmRichManutenzioneOds richiestaODS = QDmalmRichManutenzioneOds.dmalmRichManutenzioneOds;

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

	public static void insert(
			List<DmalmRichiestaManutenzione> staging_richieste,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmRichiestaManutenzione richiesta : staging_richieste) {

				new SQLInsertClause(connection, dialect, richiestaODS)
						.columns(richiestaODS.cdRichiestaManutenzione,
								richiestaODS.classeDiFornitura,
								richiestaODS.codice,
								richiestaODS.dataChiusuraRichManut,
								richiestaODS.dataDispEffettiva,
								richiestaODS.dataDispPianificata,
								richiestaODS.dataInizioEffettivo,
								richiestaODS.dataInizioPianificato,
								richiestaODS.descrizioneRichManutenzione,
								richiestaODS.dmalmProjectFk02,
								richiestaODS.dmalmRichManutenzionePk,
								richiestaODS.dmalmStatoWorkitemFk03,
								richiestaODS.dmalmStrutturaOrgFk01,
								richiestaODS.dmalmTempoFk04,
								richiestaODS.dsAutoreRichManutenzione,
								richiestaODS.dtCambioStatoRichManut,
								richiestaODS.dtCaricamentoRichManut,
								richiestaODS.dtCreazioneRichManutenzione,
								richiestaODS.dtModificaRichManutenzione,
								richiestaODS.dtRisoluzioneRichManut,
								richiestaODS.dtScadenzaRichManutenzione,
								richiestaODS.dtStoricizzazione,
								richiestaODS.durataEffettivaRichMan,
								richiestaODS.idAutoreRichManutenzione,
								richiestaODS.idRepository,
								richiestaODS.motivoRisoluzioneRichManut,
								richiestaODS.rankStatoRichManutenzione,
								richiestaODS.titoloRichiestaManutenzione,
								richiestaODS.stgPk, richiestaODS.dmalmUserFk06,
								richiestaODS.uri,
								richiestaODS.severity, richiestaODS.priority)
						.values(richiesta.getCdRichiestaManutenzione(),
								richiesta.getClasseDiFornitura(),
								richiesta.getCodice(),
								richiesta.getDataChiusuraRichManut(),
								richiesta.getDataDispEffettiva(),
								richiesta.getDataDispPianificata(),
								richiesta.getDataInizioEffettivo(),
								richiesta.getDataInizioPianificato(),
								richiesta.getDescrizioneRichManutenzione(),
								richiesta.getDmalmProjectFk02(),
								richiesta.getDmalmRichManutenzionePk(),
								richiesta.getDmalmStatoWorkitemFk03(),
								richiesta.getDmalmStrutturaOrgFk01(),
								richiesta.getDmalmTempoFk04(),
								richiesta.getDsAutoreRichManutenzione(),
								richiesta.getDtCambioStatoRichManut(),
								richiesta.getDtCaricamentoRichManut(),
								richiesta.getDtCreazioneRichManutenzione(),
								richiesta.getDtModificaRichManutenzione(),
								richiesta.getDtRisoluzioneRichManut(),
								richiesta.getDtScadenzaRichManutenzione(),
								richiesta.getDtModificaRichManutenzione(),
								richiesta.getDurataEffettivaRichMan(),
								richiesta.getIdAutoreRichManutenzione(),
								richiesta.getIdRepository(),
								richiesta.getMotivoRisoluzioneRichManut(),
								new Double(1),
								richiesta.getTitoloRichiestaManutenzione(),
								richiesta.getStgPk(),
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

	public static List<DmalmRichiestaManutenzione> getAll()
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmRichiestaManutenzione> resultListEl = new LinkedList<DmalmRichiestaManutenzione>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(richiestaODS)
					.orderBy(richiestaODS.cdRichiestaManutenzione.asc())
					.orderBy(richiestaODS.dtModificaRichManutenzione.asc())
					.list(richiestaODS.all());
			
			for (Tuple result : list) {
				DmalmRichiestaManutenzione resultEl = new DmalmRichiestaManutenzione();
				resultEl.setCdRichiestaManutenzione(result.get(richiestaODS.cdRichiestaManutenzione));
				resultEl.setClasseDiFornitura(result.get(richiestaODS.classeDiFornitura));
				resultEl.setCodice(result.get(richiestaODS.codice));
				resultEl.setDmalmUserFk06(result.get(richiestaODS.dmalmUserFk06));
				resultEl.setDataChiusuraRichManut(result.get(richiestaODS.dataChiusuraRichManut));
				resultEl.setDataDispEffettiva(result.get(richiestaODS.dataDispEffettiva));
				resultEl.setDataDispPianificata(result.get(richiestaODS.dataDispPianificata));
				resultEl.setDataInizioEffettivo(result.get(richiestaODS.dataInizioEffettivo));
				resultEl.setDataInizioPianificato(result.get(richiestaODS.dataInizioPianificato));
				resultEl.setDescrizioneRichManutenzione(result.get(richiestaODS.descrizioneRichManutenzione));
				resultEl.setDmalmProjectFk02(result.get(richiestaODS.dmalmProjectFk02));
				resultEl.setDmalmRichManutenzionePk(result.get(richiestaODS.dmalmRichManutenzionePk));
				resultEl.setDmalmStatoWorkitemFk03(result.get(richiestaODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(richiestaODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(richiestaODS.dmalmTempoFk04));
				resultEl.setDsAutoreRichManutenzione(result.get(richiestaODS.dsAutoreRichManutenzione));
				resultEl.setDtCambioStatoRichManut(result.get(richiestaODS.dtCambioStatoRichManut));
				resultEl.setDtCaricamentoRichManut(result.get(richiestaODS.dtCaricamentoRichManut));
				resultEl.setDtCreazioneRichManutenzione(result.get(richiestaODS.dtCreazioneRichManutenzione));
				resultEl.setDtModificaRichManutenzione(result.get(richiestaODS.dtModificaRichManutenzione));
				resultEl.setDtRisoluzioneRichManut(result.get(richiestaODS.dtRisoluzioneRichManut));
				resultEl.setDtScadenzaRichManutenzione(result.get(richiestaODS.dtScadenzaRichManutenzione));
				resultEl.setDtStoricizzazione(result.get(richiestaODS.dtStoricizzazione));
				resultEl.setDurataEffettivaRichMan(result.get(richiestaODS.durataEffettivaRichMan));
				resultEl.setIdAutoreRichManutenzione(result.get(richiestaODS.idAutoreRichManutenzione));
				resultEl.setIdRepository(result.get(richiestaODS.idRepository));
				resultEl.setMotivoRisoluzioneRichManut(result.get(richiestaODS.motivoRisoluzioneRichManut));
				resultEl.setRankStatoRichManutenzione(result.get(richiestaODS.rankStatoRichManutenzione));
				resultEl.setRankStatoRichManutMese(result.get(richiestaODS.rankStatoRichManutMese));
				resultEl.setStgPk(result.get(richiestaODS.stgPk));
				resultEl.setUri(result.get(richiestaODS.uri));
				resultEl.setTitoloRichiestaManutenzione(result.get(richiestaODS.titoloRichiestaManutenzione));
				resultEl.setSeverity(result.get(richiestaODS.severity));
				resultEl.setPriority(result.get(richiestaODS.priority));
				resultListEl.add(resultEl);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;

	}

}
