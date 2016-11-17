package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;
import lispa.schedulers.queryimplementation.target.QDmalmStrutturaOrganizzativa;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class CheckAnnullamentiEdmaFacade {

	private static Logger logger = Logger
			.getLogger(CheckAnnullamentiEdmaFacade.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmPersonale personale = QDmalmPersonale.dmalmPersonale;

	private static QDmalmStrutturaOrganizzativa unitaOrganizzativa = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

	public static void execute() throws DAOException {

		Timestamp dataOggi = DataEsecuzione.getInstance().getDataEsecuzione();
		Timestamp dataIeri = DateUtils.getDtPrecedente(dataOggi);

		checkAnnullamentiPersonale(dataOggi, dataIeri);
		checkAnnullamentiUnitaOrgazzitava(dataOggi, dataIeri);
	}

	protected static void checkAnnullamentiPersonale(Timestamp dataOggi,
			Timestamp dataIeri) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);
			logger.debug("CHECK annullamenti personale");
			String sql = "select CODICE from DMALM_STG_PERSONALE where DATA_CARICAMENTO = ? "
					+ "minus "
					+ "select CODICE from DMALM_STG_PERSONALE where DATA_CARICAMENTO = ? ";
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id;
				id = rs.getString("CODICE");
				logger.debug(id + " annullato");
				new SQLUpdateClause(conn, dialect, personale)
						.where(personale.cdPersonale.equalsIgnoreCase(id))
						.set(personale.annullato,
								DmAlmConstants.ANNULLATO_FISICAMENTE).execute();
			}

			conn.commit();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

	protected static void checkAnnullamentiUnitaOrgazzitava(Timestamp dataOggi,
			Timestamp dataIeri) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);
			logger.debug("CHECK annullamenti unita' organizzative");
			String sql = "select CODICE from DMALM_STG_UNITA_ORGANIZZATIVE where DATA_CARICAMENTO = ? "
					+ "minus "
					+ "select CODICE from DMALM_STG_UNITA_ORGANIZZATIVE where DATA_CARICAMENTO = ? ";
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id;
				id = rs.getString("CODICE");
				logger.debug(id + " annullata");
				new SQLUpdateClause(conn, dialect, unitaOrganizzativa)
						.where(unitaOrganizzativa.cdArea.equalsIgnoreCase(id))
						.set(unitaOrganizzativa.annullato,
								DmAlmConstants.ANNULLATO_FISICAMENTE).execute();
			}

			conn.commit();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

}
