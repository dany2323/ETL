package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_DOCUMENTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmDocumento;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class DocumentoDAO {

	private static Logger logger = Logger.getLogger(DocumentoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmDocumento doc = QDmalmDocumento.dmalmDocumento;

	public static List<DmalmDocumento> getAllDocumento(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmDocumento bean = null;
		List<DmalmDocumento> documenti = new ArrayList<DmalmDocumento>(
				QueryUtils.getCountByWIType(Workitem_Type.documento));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_DOCUMENTO);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmDocumento();

				bean.setCdDocumento(rs.getString("CD_DOCUMENTO"));
				bean.setClassificazione(rs
						.getString("CLASSIFICAZIONE_DOCUMENTO"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDescrizioneDocumento(rs
						.getString("DESCRIZIONE_DOCUMENTO"));

				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));

				bean.setDmalmDocumentoPk(rs.getInt("DMALM_DOCUMENTO_PK"));
				bean.setDtStoricizzazione((rs
						.getTimestamp("DATA_MODIFICA_DOCUMENTO")));
				bean.setRankStatoDocumento(new Double(1));
				bean.setDsAutoreDocumento(rs.getString("NOME_AUTORE_DOCUMENTO"));
				bean.setDtCaricamentoDocumento(dataEsecuzione);
				bean.setDtCreazioneDocumento(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaDocumento(rs
						.getTimestamp("DATA_MODIFICA_DOCUMENTO"));
				bean.setDtRisoluzioneDocumento(rs
						.getTimestamp("DATA_RISOLUZIONE_DOCUMENTO"));
				bean.setDtScadenzaDocumento(rs
						.getTimestamp("DATA_SCADENZA_DOCUMENTO"));
				bean.setIdAutoreDocumento(rs.getString("ID_AUTORE_DOCUMENTO"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneDocumento(rs
						.getString("MOTIVO_RISOL_DOCUMENTO"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("COD_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("COD_INTERVENTO")));
				bean.setTipo(rs.getString("TIPO_DOCUMENTO"));
				bean.setTitoloDocumento(rs.getString("TITOLO_DOCUMENTO"));
				bean.setVersione(rs.getString("VERSIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_DOCUMENTO_PK"));

				documenti.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return documenti;
	}

	public static List<Tuple> getDocumento(DmalmDocumento documento)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> documenti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			documenti = query
					.from(doc)
					.where(doc.cdDocumento.equalsIgnoreCase(documento
							.getCdDocumento()))
					.where(doc.idRepository.equalsIgnoreCase(documento
							.getIdRepository()))
					.where(doc.rankStatoDocumento.eq(new Double(1)))
					.list(doc.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return documenti;
	}

	public static void insertDocumento(DmalmDocumento documento)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					doc);
			insert.populate(documento).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmDocumento documento, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, doc)
					.where(doc.cdDocumento.equalsIgnoreCase(documento
							.getCdDocumento()))
					.where(doc.idRepository.equalsIgnoreCase(documento
							.getIdRepository()))
					.set(doc.rankStatoDocumento, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertDocumentoUpdate(Timestamp dataEsecuzione,
			DmalmDocumento documento, boolean pkValue) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, doc)
					.columns(doc.annullato, doc.assigneesDocumento,
							doc.cdDocumento, doc.changed, doc.classificazione,
							doc.codice, doc.descrizioneDocumento,
							doc.dmalmAreaTematicaFk05, doc.dmalmDocumentoPk,
							doc.dmalmProjectFk02, doc.dmalmStatoWorkitemFk03,
							doc.dmalmStrutturaOrgFk01, doc.dmalmTempoFk04,
							doc.dmalmUserFk06, doc.dsAutoreDocumento,
							doc.dtAnnullamento, doc.dtCambioStatoDocumento,
							doc.dtCaricamentoDocumento,
							doc.dtCreazioneDocumento, doc.dtModificaDocumento,
							doc.dtRisoluzioneDocumento,
							doc.dtScadenzaDocumento, doc.dtStoricizzazione,
							doc.idAutoreDocumento, doc.idRepository,
							doc.motivoRisoluzioneDocumento, doc.numeroLinea,
							doc.numeroTestata, doc.rankStatoDocumento,
							doc.rankStatoDocumentoMese, doc.stgPk, doc.tipo,
							doc.titoloDocumento, doc.uri)
					.values(documento.getAnnullato(),
							documento.getAssigneesDocumento(),
							documento.getCdDocumento(),
							documento.getChanged(),
							documento.getClassificazione(),
							documento.getCodice(),
							documento.getDescrizioneDocumento(),
							documento.getDmalmAreaTematicaFk05(),
							pkValue == true ? documento.getDmalmDocumentoPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							documento.getDmalmProjectFk02(),
							documento.getDmalmStatoWorkitemFk03(),
							documento.getDmalmStrutturaOrgFk01(),
							documento.getDmalmTempoFk04(),
							documento.getDmalmUserFk06(),
							documento.getDsAutoreDocumento(),
							documento.getDtAnnullamento(),
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
							pkValue == true ? new Short("1")  : documento.getRankStatoDocumento(),
							documento.getRankStatoDocumentoMese(),
							documento.getStgPk(), documento.getTipo(),
							documento.getTitoloDocumento(), documento.getUri())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateDocumento(DmalmDocumento documento)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, doc)

					.where(doc.cdDocumento.equalsIgnoreCase(documento
							.getCdDocumento()))
					.where(doc.idRepository.equalsIgnoreCase(documento
							.getIdRepository()))
					.where(doc.rankStatoDocumento.eq(new Double(1)))

					.set(doc.assigneesDocumento,
							documento.getAssigneesDocumento())
					.set(doc.cdDocumento, documento.getCdDocumento())
					.set(doc.classificazione, documento.getClassificazione())
					.set(doc.codice, documento.getCodice())
					.set(doc.descrizioneDocumento,
							documento.getDescrizioneDocumento())
					.set(doc.dmalmAreaTematicaFk05,
							documento.getDmalmAreaTematicaFk05())
					.set(doc.dmalmProjectFk02, documento.getDmalmProjectFk02())
					.set(doc.dmalmStatoWorkitemFk03,
							documento.getDmalmStatoWorkitemFk03())
					.set(doc.dmalmStrutturaOrgFk01,
							documento.getDmalmStrutturaOrgFk01())
					.set(doc.dmalmTempoFk04, documento.getDmalmTempoFk04())
					.set(doc.dsAutoreDocumento,
							documento.getDsAutoreDocumento())
					.set(doc.dtCreazioneDocumento,
							documento.getDtCreazioneDocumento())
					.set(doc.dtModificaDocumento,
							documento.getDtModificaDocumento())
					.set(doc.dtRisoluzioneDocumento,
							documento.getDtRisoluzioneDocumento())
					.set(doc.dtScadenzaDocumento,
							documento.getDtScadenzaDocumento())
					.set(doc.idAutoreDocumento,
							documento.getIdAutoreDocumento())
					.set(doc.idRepository, documento.getIdRepository())
					.set(doc.motivoRisoluzioneDocumento,
							documento.getMotivoRisoluzioneDocumento())
					.set(doc.numeroLinea, documento.getNumeroLinea())
					.set(doc.numeroTestata, documento.getNumeroTestata())
					.set(doc.tipo, documento.getTipo())
					.set(doc.titoloDocumento, documento.getTitoloDocumento())
					.set(doc.versione, documento.getVersione())
					.set(doc.stgPk, documento.getStgPk())
					.set(doc.uri, documento.getUri())
					.set(doc.dtAnnullamento, documento.getDtAnnullamento())
					.set(doc.annullato, documento.getAnnullato())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmDocumento getDocumento(Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> documenti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			documenti = query.from(doc).where(doc.dmalmDocumentoPk.eq(pk))
					.list(doc.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (documenti.size() > 0) {
			Tuple t = documenti.get(0);

			DmalmDocumento d = new DmalmDocumento();

			d.setAnnullato(t.get(doc.annullato));
			d.setAssigneesDocumento(t.get(doc.assigneesDocumento));
			d.setCdDocumento(t.get(doc.cdDocumento));
			d.setChanged(t.get(doc.changed));
			d.setClassificazione(t.get(doc.classificazione));
			d.setCodice(t.get(doc.codice));
			d.setDescrizioneDocumento(t.get(doc.descrizioneDocumento));
			d.setDmalmAreaTematicaFk05(t.get(doc.dmalmAreaTematicaFk05));
			d.setDmalmDocumentoPk(t.get(doc.dmalmDocumentoPk));
			d.setDmalmProjectFk02(t.get(doc.dmalmProjectFk02));
			d.setDmalmStatoWorkitemFk03(t.get(doc.dmalmStatoWorkitemFk03));
			d.setDmalmStrutturaOrgFk01(t.get(doc.dmalmStrutturaOrgFk01));
			d.setDmalmTempoFk04(t.get(doc.dmalmTempoFk04));
			d.setDmalmUserFk06(t.get(doc.dmalmUserFk06));
			d.setDsAutoreDocumento(t.get(doc.dsAutoreDocumento));
			d.setDtAnnullamento(t.get(doc.dtAnnullamento));
			d.setDtCambioStatoDocumento(t.get(doc.dtCambioStatoDocumento));
			d.setDtCaricamentoDocumento(t.get(doc.dtCaricamentoDocumento));
			d.setDtCreazioneDocumento(t.get(doc.dtCreazioneDocumento));
			d.setDtModificaDocumento(t.get(doc.dtModificaDocumento));
			d.setDtRisoluzioneDocumento(t.get(doc.dtRisoluzioneDocumento));
			d.setDtScadenzaDocumento(t.get(doc.dtScadenzaDocumento));
			d.setDtStoricizzazione(t.get(doc.dtStoricizzazione));
			d.setIdAutoreDocumento(t.get(doc.idAutoreDocumento));
			d.setIdRepository(t.get(doc.idRepository));
			d.setMotivoRisoluzioneDocumento(t
					.get(doc.motivoRisoluzioneDocumento));
			d.setNumeroLinea(t.get(doc.numeroLinea));
			d.setNumeroTestata(t.get(doc.numeroTestata));
			d.setRankStatoDocumento(t.get(doc.rankStatoDocumento));
			d.setRankStatoDocumentoMese(t.get(doc.rankStatoDocumentoMese));
			d.setStgPk(t.get(doc.stgPk));
			d.setTipo(t.get(doc.tipo));
			d.setTitoloDocumento(t.get(doc.titoloDocumento));
			d.setUri(t.get(doc.uri));
			d.setVersione(t.get(doc.versione));

			return d;
		} else
			return null;
	}

	public static boolean checkEsistenzaDocumento(DmalmDocumento d,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> documenti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			documenti = query
					.from(doc)
					.where(doc.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(doc.cdDocumento.eq(d.getCdDocumento()))
					.where(doc.idRepository.eq(d.getIdRepository()))
					.list(doc.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (documenti.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
