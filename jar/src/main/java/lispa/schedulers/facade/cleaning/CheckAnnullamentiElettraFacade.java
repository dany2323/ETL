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
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class CheckAnnullamentiElettraFacade {
	private static Logger logger = Logger
			.getLogger(CheckAnnullamentiElettraFacade.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElPersonale personale = QDmalmElPersonale.qDmalmElPersonale;
	private static QDmalmElUnitaOrganizzative unitaOrganizzativa = QDmalmElUnitaOrganizzative.qDmalmElUnitaOrganizzative;
	private static QDmalmElAmbienteTecnologicoClassificatori ambienteTecnologicoClassificatori = QDmalmElAmbienteTecnologicoClassificatori.qDmalmElAmbienteTecnologicoClassificatori;

	public static void execute() throws DAOException {
		Timestamp dataOggi = DataEsecuzione.getInstance().getDataEsecuzione();
		Timestamp dataIeri = DateUtils.getDtPrecedente(dataOggi);

		checkAnnullamentiElUnitaOrganizzativa(dataOggi, dataIeri);

		checkAnnullamentiElPersonale(dataOggi, dataIeri);

		checkAnnullamentiElAmbienteTecnologicoClassificatori(dataOggi, dataIeri);
	}

	protected static void checkAnnullamentiElUnitaOrganizzativa(
			Timestamp dataOggi, Timestamp dataIeri) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_UNITA_ORGANIZZATIVE);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);

			rs = ps.executeQuery();

			while (rs.next()) {
				String id;
				id = rs.getString("CD_AREA");

				new SQLUpdateClause(conn, dialect, unitaOrganizzativa)
						.where(unitaOrganizzativa.codiceArea
								.equalsIgnoreCase(id))
						.set(unitaOrganizzativa.annullato,
								DmAlmConstants.FISICAMENTE)
						.set(unitaOrganizzativa.dataAnnullamento, dataOggi)
						.execute();
			}

			conn.commit();

			if (rs != null) {
				rs.close();
			}
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

	protected static void checkAnnullamentiElPersonale(Timestamp dataOggi,
			Timestamp dataIeri) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_PERSONALE);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id;
				id = rs.getString("CD_PERSONALE");

				new SQLUpdateClause(conn, dialect, personale)
						.where(personale.codicePersonale.equalsIgnoreCase(id))
						.set(personale.annullato, DmAlmConstants.FISICAMENTE)
						.set(personale.dataAnnullamento, dataOggi).execute();
			}

			conn.commit();

			if (rs != null) {
				rs.close();
			}
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

	protected static void checkAnnullamentiElAmbienteTecnologicoClassificatori(
			Timestamp dataOggi, Timestamp dataIeri) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_AMBTEC_CLASSIF);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();

			while (rs.next()) {
				String idAmbTecn;
				String idClassificatore;
				idAmbTecn = rs.getString("ID_AMB_TECN");
				idClassificatore = rs.getString("ID_CLASSIFICATORE");

				new SQLUpdateClause(conn, dialect,
						ambienteTecnologicoClassificatori)
						.where(ambienteTecnologicoClassificatori.idAmbienteTecnologico
								.equalsIgnoreCase(idAmbTecn))
						.where(ambienteTecnologicoClassificatori.idClassificatore
								.equalsIgnoreCase(idClassificatore))
						.set(ambienteTecnologicoClassificatori.annullato,
								DmAlmConstants.FISICAMENTE)
						.set(ambienteTecnologicoClassificatori.dataAnnullamento,
								dataOggi).execute();
			}

			conn.commit();

			if (rs != null) {
				rs.close();
			}
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
