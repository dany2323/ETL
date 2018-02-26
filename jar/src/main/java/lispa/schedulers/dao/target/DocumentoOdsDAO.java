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

import lispa.schedulers.bean.target.fatti.DmalmDocumento;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmDocumentoOds;

public class DocumentoOdsDAO {

	private static Logger logger = Logger.getLogger(DocumentoOdsDAO.class);

	private static QDmalmDocumentoOds documentoODS = QDmalmDocumentoOds.dmalmDocumentoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, documentoODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmDocumento> staging_documenti,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmDocumento documento : staging_documenti) {

				new SQLInsertClause(connection, dialect, documentoODS)
					.columns(documentoODS.assigneesDocumento,
							documentoODS.cdDocumento,
							documentoODS.classificazione,
							documentoODS.dmalmUserFk06,
							documentoODS.codice,
							documentoODS.descrizioneDocumento,
							documentoODS.dmalmAreaTematicaFk05,
							documentoODS.dmalmDocumentoPk,
							documentoODS.dmalmProjectFk02,
							documentoODS.dmalmStatoWorkitemFk03,
							documentoODS.dmalmStrutturaOrgFk01,
							documentoODS.dmalmTempoFk04,
							documentoODS.dsAutoreDocumento,
							documentoODS.dtCambioStatoDocumento,
							documentoODS.dtCaricamentoDocumento,
							documentoODS.dtCreazioneDocumento,
							documentoODS.dtModificaDocumento,
							documentoODS.dtRisoluzioneDocumento,
							documentoODS.dtScadenzaDocumento,
							documentoODS.dtStoricizzazione,
							documentoODS.idAutoreDocumento,
							documentoODS.idRepository,
							documentoODS.motivoRisoluzioneDocumento,
							documentoODS.numeroLinea,
							documentoODS.numeroTestata,
							documentoODS.rankStatoDocumento,
							documentoODS.rankStatoDocumentoMese,
							documentoODS.stgPk,
							documentoODS.uri,
							documentoODS.tipo,
							documentoODS.titoloDocumento,
							documentoODS.versione,
							documentoODS.severity,
							documentoODS.priority)
					.values(documento.getAssigneesDocumento(),
							documento.getCdDocumento(),
							documento.getClassificazione(),
							documento.getDmalmUserFk06(),
							documento.getCodice(),
							documento.getDescrizioneDocumento(),
							documento.getDmalmAreaTematicaFk05(),
							documento.getDmalmDocumentoPk(),
							documento.getDmalmProjectFk02(),
							documento.getDmalmStatoWorkitemFk03(),
							documento.getDmalmStrutturaOrgFk01(),
							documento.getDmalmTempoFk04(),
							documento.getDsAutoreDocumento(),
							documento.getDtCambioStatoDocumento(),
							documento.getDtCaricamentoDocumento(),
							documento.getDtCreazioneDocumento(),
							documento.getDtModificaDocumento(),
							documento.getDtRisoluzioneDocumento(),
							documento.getDtScadenzaDocumento(),
							documento.getDtStoricizzazione(),
							documento.getIdAutoreDocumento(),
							documento.getIdRepository(),
							documento.getMotivoRisoluzioneDocumento(),
							documento.getNumeroLinea(),
							documento.getNumeroTestata(),
							documento.getRankStatoDocumento(),
							documento.getRankStatoDocumentoMese(),
							documento.getStgPk(),
							documento.getUri(),
							documento.getTipo(),
							documento.getTitoloDocumento(),
							documento.getVersione(),
							documento.getSeverity(),
							documento.getPriority())
					.execute();
				
				// con il cambio di libreria mysema 
				// è stato modificato anche il modo
				// in cui vengono inseriti i dati a db
//				insert.populate(documento).execute();

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

	public static List<DmalmDocumento> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmDocumento> resultListEl = new LinkedList<DmalmDocumento>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(documentoODS)
					.orderBy(documentoODS.cdDocumento.asc())
					.orderBy(documentoODS.dtModificaDocumento.asc())
					.list(documentoODS.all());
			
			for (Tuple result : list) {
				DmalmDocumento resultEl = new DmalmDocumento();
				resultEl.setAssigneesDocumento(result.get(documentoODS.assigneesDocumento));
				resultEl.setCdDocumento(result.get(documentoODS.cdDocumento));
				resultEl.setClassificazione(result.get(documentoODS.classificazione));
				resultEl.setDmalmUserFk06(result.get(documentoODS.dmalmUserFk06));
				resultEl.setCodice(result.get(documentoODS.codice));
				resultEl.setDescrizioneDocumento(result.get(documentoODS.descrizioneDocumento));
				resultEl.setDmalmAreaTematicaFk05(result.get(documentoODS.dmalmAreaTematicaFk05));
				resultEl.setDmalmDocumentoPk(result.get(documentoODS.dmalmDocumentoPk));
				resultEl.setDmalmProjectFk02(result.get(documentoODS.dmalmProjectFk02));
				resultEl.setDmalmStatoWorkitemFk03(result.get(documentoODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(documentoODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(documentoODS.dmalmTempoFk04));
				resultEl.setDsAutoreDocumento(result.get(documentoODS.dsAutoreDocumento));
				resultEl.setDtCambioStatoDocumento(result.get(documentoODS.dtCambioStatoDocumento));
				resultEl.setDtCaricamentoDocumento(result.get(documentoODS.dtCaricamentoDocumento));
				resultEl.setDtCreazioneDocumento(result.get(documentoODS.dtCreazioneDocumento));
				resultEl.setDtModificaDocumento(result.get(documentoODS.dtModificaDocumento));
				resultEl.setDtRisoluzioneDocumento(result.get(documentoODS.dtRisoluzioneDocumento));
				resultEl.setDtScadenzaDocumento(result.get(documentoODS.dtScadenzaDocumento));
				resultEl.setDtStoricizzazione(result.get(documentoODS.dtStoricizzazione));
				resultEl.setIdAutoreDocumento(result.get(documentoODS.idAutoreDocumento));
				resultEl.setIdRepository(result.get(documentoODS.idRepository));
				resultEl.setMotivoRisoluzioneDocumento(result.get(documentoODS.motivoRisoluzioneDocumento));
				resultEl.setNumeroLinea(result.get(documentoODS.numeroLinea));
				resultEl.setNumeroTestata(result.get(documentoODS.numeroTestata));
				resultEl.setRankStatoDocumento(result.get(documentoODS.rankStatoDocumento));
				resultEl.setRankStatoDocumentoMese(result.get(documentoODS.rankStatoDocumentoMese));
				resultEl.setStgPk(result.get(documentoODS.stgPk));
				resultEl.setUri(result.get(documentoODS.uri));
				resultEl.setTipo(result.get(documentoODS.tipo));
				resultEl.setTitoloDocumento(result.get(documentoODS.titoloDocumento));
				resultEl.setVersione(result.get(documentoODS.versione));
				resultEl.setSeverity(result.get(documentoODS.severity));
				resultEl.setPriority(result.get(documentoODS.priority));
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
