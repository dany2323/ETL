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
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElModuli;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElProdotti;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElFunzionalita;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElModuli;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
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
	private static QDmalmElFunzionalita funzionalita = QDmalmElFunzionalita.qDmalmElFunzionalita;
	private static QDmalmElModuli moduli = QDmalmElModuli.qDmalmElModuli;
	private static QDmalmElProdottiArchitetture prodotti = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

	public static void execute() throws DAOException {
		Timestamp dataOggi = DataEsecuzione.getInstance().getDataEsecuzione();
		Timestamp dataIeri = DateUtils.getDtPrecedente(dataOggi);

		checkAnnullamentiElUnitaOrganizzativa(dataOggi, dataIeri);

		checkAnnullamentiElPersonale(dataOggi, dataIeri);

		checkAnnullamentiElAmbienteTecnologicoClassificatori(dataOggi, dataIeri);
		
		checkAnnullamentiElProdotto(dataOggi, dataIeri);
		
		checkAnnullamentiElModulo(dataOggi, dataIeri);
		
		checkAnnullamentiElFunzionalita(dataOggi, dataIeri);
	}

	private static void checkAnnullamentiElFunzionalita(Timestamp dataOggi, Timestamp dataIeri) throws DAOException {
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
				id = rs.getString("ID_FUNZIONALITA");

				new SQLUpdateClause(conn, dialect, funzionalita)
						.where(funzionalita.idFunzionalita.equalsIgnoreCase(id))
						.set(funzionalita.annullato, DmAlmConstants.FISICAMENTE)
						.set(funzionalita.dtAnnullamento, dataOggi).execute();
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

	private static void checkAnnullamentiElModulo(Timestamp dataOggi, Timestamp dataIeri) throws DAOException{
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_MODULO);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id;
				id = rs.getString("ID_MODULO");

				new SQLUpdateClause(conn, dialect, moduli)
						.where(moduli.idModulo.equalsIgnoreCase(id))
						.set(moduli.annullato, DmAlmConstants.FISICAMENTE)
						.set(moduli.dataAnnullamento,dataOggi).execute();
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

	private static void checkAnnullamentiElProdotto(Timestamp dataOggi, Timestamp dataIeri) throws DAOException{
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String annullaFisicamenteSql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_PRODOTTO);
			String annullaLogicamenteSql = QueryManager.getInstance().getQuery(
					DmAlmConstants.ELETTRA_ANN_PRODOTTO_FISICAMENTE);

			ps = conn.prepareStatement(annullaFisicamenteSql);
			ps.setTimestamp(1, dataIeri);
			ps.setTimestamp(2, dataOggi);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id;
				id = rs.getString("ID_PRODOTTO");

				new SQLUpdateClause(conn, dialect, prodotti)
						.where(prodotti.idProdotto.equalsIgnoreCase(id))
						.set(prodotti.annullato, DmAlmConstants.FISICAMENTE)
						.set(prodotti.dataAnnullamento, dataOggi).execute();
			}

			conn.commit();
			
			ps= conn.prepareStatement(annullaLogicamenteSql);
			ps.setString(1, DmAlmConstants.DISMESSO);
			ps.setString(2, DmAlmConstants.FISICAMENTE);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				String id;
				id = rs.getString("DMALM_PRODOTTO_PK");

				new SQLUpdateClause(conn, dialect, prodotti)
						.where(prodotti.idProdotto.equalsIgnoreCase(id))
						.set(prodotti.annullato, DmAlmConstants.ANNULLATO_LOGICAMENTE_ELETTRA)
						.set(prodotti.dataAnnullamento, dataOggi).execute();
			}
			
			
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

	public static void checkAnnullamentiElUnitaOrganizzativa(
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
								DmAlmConstants.ANNULLATO_LOGICAMENTE_ELETTRA)
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
						.set(personale.annullato, DmAlmConstants.ANNULLATO_LOGICAMENTE_ELETTRA)
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
