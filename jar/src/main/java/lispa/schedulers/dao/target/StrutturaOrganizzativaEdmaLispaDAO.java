package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_STRUTTURAORGANIZZATIVA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

import lispa.schedulers.bean.target.DmalmStrutturaOrganizzativa;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmStrutturaOrganizzativa;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

public class StrutturaOrganizzativaEdmaLispaDAO {

	private static Logger logger = Logger
			.getLogger(StrutturaOrganizzativaEdmaLispaDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmStrutturaOrganizzativa struttura = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

	/**
	 * restituisce tutte le unità organizzative inserite alla data di
	 * elaborazione nell'area di staging
	 * 
	 * @param dataEsecuzione
	 * @return
	 * @throws Exception
	 */
	public static List<DmalmStrutturaOrganizzativa> getAllStrutturaOrganizzativa(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmStrutturaOrganizzativa bean = null;
		List<DmalmStrutturaOrganizzativa> strutture = new ArrayList<DmalmStrutturaOrganizzativa>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_STRUTTURAORGANIZZATIVA);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmStrutturaOrganizzativa();

				bean.setDmalmStrutturaOrgPk(rs.getInt("DMALM_STRUTTURA_ORG_PK"));
				bean.setCdArea(rs.getString("CD_AREA"));
				bean.setCdEnte(rs.getString("CD_ENTE"));
				bean.setCdResponsabileArea(rs.getString("CD_RESPONSABILE_AREA"));
				bean.setCdUoSuperiore(rs.getString("CD_UO_SUPERIORE"));
				bean.setCdVisibilita(rs.getString("CD_VISIBILITA"));
				bean.setDnResponsabileArea(rs.getString("DN_RESPONSABILE_AREA"));
				bean.setDsAreaEdma(rs.getString("DS_AREA_EDMA"));
				bean.setDsUoSuperiore(rs.getString("DS_UO_SUPERIORE"));
				bean.setDtCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setDtAttivazione(rs.getString("DT_ATTIVAZIONE") != null ? DateUtils
						.stringToTimestamp(rs.getString("DT_ATTIVAZIONE"),
								"yyyy-MM-dd 00:00:00") : null);
				bean.setDtDisattivazione(rs.getString("DT_DISATTIVAZIONE") != null ? DateUtils
						.stringToTimestamp(rs.getString("DT_DISATTIVAZIONE"),
								"yyyy-MM-dd 00:00:00") : null);
				bean.setDtInizioValiditaEdma(rs.getString("DT_INIZIO_VALIDITA") != null ? DateUtils
						.stringToTimestamp(rs.getString("DT_INIZIO_VALIDITA"),
								"yyyy-MM-dd 00:00:00") : null);
				bean.setDtFineValiditaEdma(rs.getString("DT_FINE_VALIDITA") != null ? DateUtils
						.stringToTimestamp(rs.getString("DT_FINE_VALIDITA"),
								"yyyy-MM-dd 00:00:00") : null);
				bean.setIdEdma(rs.getInt("ID_EDMA"));
				bean.setIdGradoUfficio(rs.getInt("ID_GRADO_UFFICIO"));
				bean.setIdSede(rs.getInt("ID_SEDE"));
				bean.setIdTipologiaUfficio(rs.getInt("ID_TIPOLOGIA_UFFICIO"));
				bean.setNote(rs.getString("NOTE"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setInterno(rs.getInt("INTERNO"));

				strutture.add(bean);
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

		return strutture;
	}

	public static void updateDataFineValidita(Timestamp dataelaborazione,
			DmalmStrutturaOrganizzativa st) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, struttura)
					.where(struttura.cdArea.eq(st.getCdArea()))
					.where(struttura.dtFineValidita.in(new SQLSubQuery()
							.from(struttura)
							.where(struttura.cdArea.eq(st.getCdArea()))
							.list(struttura.dtFineValidita.max())))
					.set(struttura.dtFineValidita,
							DateUtils.addSecondsToTimestamp(dataelaborazione, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void resetDataFineValidita9999(DmalmStrutturaOrganizzativa st)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, struttura)
					.where(struttura.idEdma.eq(st.getIdEdma()))
					.where(struttura.dtFineValidita.in(new SQLSubQuery()
							.from(struttura)
							.where(struttura.idEdma.eq(st.getIdEdma()))
							.list(struttura.dtFineValidita.max())))
					.set(struttura.dtFineValidita,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateStrutturaOrganizzativa(
			DmalmStrutturaOrganizzativa struct) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, struttura)
					.where(struttura.cdArea.eq(struct.getCdArea()))
					.where(struttura.dtFineValidita.in(new SQLSubQuery()
							.from(struttura)
							.where(struttura.cdArea.eq(struct.getCdArea()))
							.list(struttura.dtFineValidita.max())))
					.set(struttura.cdArea, struct.getCdArea())
					.set(struttura.note, struct.getNote())
					.set(struttura.idGradoUfficio, struct.getIdGradoUfficio())
					.set(struttura.idSede, struct.getIdSede())
					.set(struttura.email, struct.getEmail())
					.set(struttura.cdVisibilita, struct.getCdVisibilita())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertStrutturaOrganizzativa(
			DmalmStrutturaOrganizzativa struct) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, struttura)
					.columns(struttura.dmalmStrutturaOrgPk, struttura.cdArea,
							struttura.cdEnte, struttura.cdResponsabileArea,
							struttura.cdUoSuperiore, struttura.cdVisibilita,
							struttura.dnResponsabileArea, struttura.dsAreaEdma,
							struttura.dsUoSuperiore, struttura.dtAttivazione,
							struttura.dtCaricamento,
							struttura.dtDisattivazione,
							struttura.dtFineValidita,
							struttura.dtInizioValidita, struttura.idEdma,
							struttura.idGradoUfficio, struttura.idSede,
							struttura.idTipologiaUfficio, struttura.note,
							struttura.email, struttura.interno,
							struttura.dtFineValiditaEdma,
							struttura.dtInizioValiditaEdma)
					.values(struct.getDmalmStrutturaOrgPk(),
							struct.getCdArea(), struct.getCdEnte(),
							struct.getCdResponsabileArea(),
							struct.getCdUoSuperiore(),
							struct.getCdVisibilita(),
							struct.getDnResponsabileArea(),
							struct.getDsAreaEdma(), struct.getDsUoSuperiore(),
							struct.getDtAttivazione(),
							struct.getDtCaricamento(),
							struct.getDtDisattivazione(),
							struct.getDtFineValiditaEdma(),
							DateUtils.setDtInizioValidita1900(),
							struct.getIdEdma(), struct.getIdGradoUfficio(),
							struct.getIdSede(), struct.getIdTipologiaUfficio(),
							struct.getNote(), struct.getEmail(),
							struct.getInterno(),
							struct.getDtFineValiditaEdma(),
							struct.getDtInizioValiditaEdma()).execute();

			connection.commit();

		} catch (Throwable e) {

			logger.error(e.getMessage(), e);
			logger.error("insertStrutturaOrganizzativa - struct:"
					+ LogUtils.objectToString(struct));
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertStrutturaOrganizzativaUpdate(
			Timestamp dataEsecuzione, DmalmStrutturaOrganizzativa struct)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, struttura)
					.columns(struttura.dmalmStrutturaOrgPk, struttura.cdArea,
							struttura.cdEnte, struttura.cdResponsabileArea,
							struttura.cdUoSuperiore, struttura.cdVisibilita,
							struttura.dnResponsabileArea, struttura.dsAreaEdma,
							struttura.dsUoSuperiore, struttura.dtAttivazione,
							struttura.dtCaricamento,
							struttura.dtDisattivazione,
							struttura.dtFineValiditaEdma,
							struttura.dtInizioValiditaEdma, struttura.idEdma,
							struttura.idGradoUfficio, struttura.idSede,
							struttura.idTipologiaUfficio, struttura.note,
							struttura.email, struttura.interno,
							struttura.dtFineValidita,
							struttura.dtInizioValidita)
					.values(struct.getDmalmStrutturaOrgPk(),
							struct.getCdArea(), struct.getCdEnte(),
							struct.getCdResponsabileArea(),
							struct.getCdUoSuperiore(),
							struct.getCdVisibilita(),
							struct.getDnResponsabileArea(),
							struct.getDsAreaEdma(), struct.getDsUoSuperiore(),
							struct.getDtAttivazione(),
							struct.getDtCaricamento(),
							struct.getDtDisattivazione(),
							struct.getDtFineValiditaEdma(),
							struct.getDtInizioValiditaEdma(),
							struct.getIdEdma(), struct.getIdGradoUfficio(),
							struct.getIdSede(), struct.getIdTipologiaUfficio(),
							struct.getNote(), struct.getEmail(),
							struct.getInterno(),
							DateUtils.setDtFineValidita9999(), dataEsecuzione)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			resetDataFineValidita9999(struct);

			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getStrutturaOrganizzativa(
			DmalmStrutturaOrganizzativa struct) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> strutture = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			if (struct.getIdEdma() != null)
				strutture = query
						.from(struttura)
						.where(struttura.cdArea.eq(struct.getCdArea()))
						.where(struttura.dtFineValidita.in(new SQLSubQuery()
								.from(struttura)
								.where(struttura.cdArea.eq(struct.getCdArea()))
								.list(struttura.dtFineValidita.max())))
						.list(struttura.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture;
	}

	public static int getIdStrutturaOrganizzativaAnomaliaByCodice(
			String codice, int anomaliaPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> strutture = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmStrutturaOrganizzativa struttura = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

			QDmalmAnomaliaProdotto anomaliaProdotto = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;

			if (codice != null)
				strutture = query
						.from(struttura, anomaliaProdotto)
						.where(struttura.cdArea.equalsIgnoreCase(codice.trim()))
						.where(anomaliaProdotto.dmalmAnomaliaProdottoPk
								.eq(anomaliaPk))
						.where(anomaliaProdotto.dtStoricizzazione.between(
								struttura.dtInizioValidita,
								struttura.dtFineValidita))
						.list(struttura.dmalmStrutturaOrgPk);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture.size() > 0 ? strutture.get(0) : 0;
	}

	public static int getIdStrutturaOrganizzativaByCodiceUpdate(String codice,
			Timestamp dt_ultimamodifica) throws DAOException {
		if (codice == null || dt_ultimamodifica == null)
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> strutture = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			strutture = query.from(struttura)
					.where(struttura.cdArea.equalsIgnoreCase(codice.trim()))
					.where(struttura.dtInizioValidita.loe(dt_ultimamodifica))
					.where(struttura.dtFineValidita.goe(dt_ultimamodifica))
					.list(struttura.dmalmStrutturaOrgPk);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture.size() > 0 ? strutture.get(0) : 0;
	}

	public static int getIdStrutturaOrganizzativaProdottoByCodice(String codice)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> strutture = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			strutture = query
					.from(struttura)
					.where(struttura.cdArea.equalsIgnoreCase(codice.trim()))
					.where(struttura.dtFineValidita.in(new SQLSubQuery()
							.from(struttura).where(struttura.cdArea.eq(codice))
							.list(struttura.dtFineValidita.max())))
					.list(struttura.dmalmStrutturaOrgPk);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture.size() > 0 ? strutture.get(0) : 0;
	}

	public static Integer getIdStrutturaOrganizzativaDifettoByCodice(
			String codice, Timestamp dtModificaRecord) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> strutture = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmStrutturaOrganizzativa struttura = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

			if (codice == null)
				return 0;
			strutture = query.from(struttura)
					.where(struttura.cdArea.equalsIgnoreCase(codice.trim()))
					.where(struttura.dtInizioValidita.loe(dtModificaRecord))
					.where(struttura.dtFineValidita.goe(dtModificaRecord))
					.list(struttura.dmalmStrutturaOrgPk);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return strutture.size() > 0 ? strutture.get(0) : 0;
	}

	public static List<DmalmStrutturaOrganizzativa> getStrutturaOrganizzativaByPrimaryKey(
			Integer primaryKey) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();
		List<DmalmStrutturaOrganizzativa> resultListEl = new LinkedList<DmalmStrutturaOrganizzativa>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmStrutturaOrganizzativa struttura = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

			list = query
					.from(struttura)
					.where(struttura.dmalmStrutturaOrgPk.eq(primaryKey))
					.list(struttura.all());
			
			for (Tuple result : list) {
				DmalmStrutturaOrganizzativa resultEl = new DmalmStrutturaOrganizzativa();
				resultEl.setCdArea(result.get(struttura.cdArea));
				resultEl.setCdEnte(result.get(struttura.cdEnte));
				resultEl.setCdResponsabileArea(result.get(struttura.cdResponsabileArea));
				resultEl.setCdUoSuperiore(result.get(struttura.cdUoSuperiore));
				resultEl.setCdVisibilita(result.get(struttura.cdVisibilita));
				resultEl.setDmalmStrutturaOrgPk(result.get(struttura.dmalmStrutturaOrgPk));
				resultEl.setDnResponsabileArea(result.get(struttura.dnResponsabileArea));
				resultEl.setDsAreaEdma(result.get(struttura.dsAreaEdma));
				resultEl.setEmail(result.get(struttura.email));
				resultEl.setDsUoSuperiore(result.get(struttura.dsUoSuperiore));
				resultEl.setDtAttivazione(result.get(struttura.dtAttivazione));
				resultEl.setDtCaricamento(result.get(struttura.dtCaricamento));
				resultEl.setDtDisattivazione(result.get(struttura.dtDisattivazione));
				resultEl.setDtFineValidita(result.get(struttura.dtFineValidita));
				resultEl.setDtInizioValidita(result.get(struttura.dtInizioValidita));
				resultEl.setDtFineValiditaEdma(result.get(struttura.dtFineValiditaEdma));
				resultEl.setDtInizioValiditaEdma(result.get(struttura.dtInizioValiditaEdma));
				resultEl.setIdEdma(result.get(struttura.idEdma));
				resultEl.setInterno(result.get(struttura.interno));
				resultEl.setIdGradoUfficio(result.get(struttura.idGradoUfficio));
				resultEl.setIdSede(result.get(struttura.idSede));
				resultEl.setIdTipologiaUfficio(result.get(struttura.idTipologiaUfficio));
				resultEl.setNote(result.get(struttura.note));
				resultEl.setAnnullato(result.get(struttura.annullato));
				resultListEl.add(resultEl);
			}
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;
	}
}