package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmRichManutenzioneOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

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
		List <Long> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmRichiestaManutenzione richiesta : staging_richieste) {
				if(listPk.contains(richiesta.getDmalmRichManutenzionePk()))
					logger.info("Trovata DmalmRichManutenzionePk DUPLICATA!!!"+richiesta.getDmalmRichManutenzionePk());
				else{
					
					listPk.add(richiesta.getDmalmRichManutenzionePk());
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
									richiestaODS.severity, richiestaODS.priority,
									richiestaODS.tagAlm, richiestaODS.tsTagAlm)
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
									richiesta.getSeverity(), richiesta.getPriority(),
									richiesta.getTagAlm(), richiesta.getTsTagAlm()).execute();
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

	public static List<DmalmRichiestaManutenzione> getAll()
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmRichiestaManutenzione> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(richiestaODS)
					.orderBy(richiestaODS.cdRichiestaManutenzione.asc())
					.orderBy(richiestaODS.dtModificaRichManutenzione.asc())
					.list(Projections.bean(DmalmRichiestaManutenzione.class,
							richiestaODS.all()));

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
