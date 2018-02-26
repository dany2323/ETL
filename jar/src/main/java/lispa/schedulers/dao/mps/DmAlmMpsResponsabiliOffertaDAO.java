package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsResponsabiliOfferta;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsResponsabiliOfferta;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsResponsabiliOffertaDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsResponsabiliOfferta mpsResponsabiliOfferta = QDmalmMpsResponsabiliOfferta.dmalmMpsResponsabiliOfferta;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsResponsabiliOfferta)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<DmalmMpsResponsabiliOfferta> getAllMpsResponsabiliOffertae(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsResponsabiliOfferta bean = null;
		List<DmalmMpsResponsabiliOfferta> mpsResponsabiliOffertae = new ArrayList<DmalmMpsResponsabiliOfferta>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_RESPONSABILI_OFFERTAES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsResponsabiliOfferta();

				bean.setIdContratto(rs.getInt("IDCONTRATTO"));
				bean.setAmNome(rs.getString("AM_NOME"));
				bean.setAmDataFirma(rs.getTimestamp("AM_DATAFIRMA"));
				bean.setAmFirmato(rs.getString("AM_FIRMATO"));
				bean.setSamNome(rs.getString("SAM_NOME"));
				bean.setSamDataFirma(rs.getTimestamp("SAM_DATAFIRMA"));
				bean.setSamFirmato(rs.getString("SAM_FIRMATO"));
				bean.setVdgNome(rs.getString("VDG_NOME"));
				bean.setVdgDataFirma(rs.getTimestamp("VDG_DATAFIRMA"));
				bean.setVdgFirmato(rs.getString("VDG_FIRMATO"));
				bean.setDgNome(rs.getString("DG_NOME"));
				bean.setDgDataFirma(rs.getTimestamp("DG_DATAFIRMA"));
				bean.setDgFirmato(rs.getString("DG_FIRMATO"));
				bean.setCdNome(rs.getString("CD_NOME"));
				bean.setCdDataFirma(rs.getTimestamp("CD_DATAFIRMA"));
				bean.setCdFirmato(rs.getString("CD_FIRMATO"));
				bean.setCopertinaFirmata(rs.getString("COPERTINA_FIRMATA"));
				bean.setDataFirmaCopertina(rs
						.getTimestamp("DATA_FIRMA_COPERTINA"));
				bean.setStatoRazionale(rs.getString("STATO_RAZIONALE"));
				bean.setController(rs.getString("CONTROLLER"));
				bean.setDataVerifica(rs.getTimestamp("DATA_VERIFICA"));
				bean.setProssimoFirmatarioRazionale(rs
						.getString("PROSSIMO_FIRMATARIO_RAZIONALE"));
				bean.setRazionaleDigitale(rs.getString("RAZIONALE_DIGITALE"));
				bean.setNotaRazionale(rs.getString("NOTA_RAZIONALE"));
				bean.setMotivazioneRigetto(rs.getString("MOTIVAZIONE_RIGETTO"));

				mpsResponsabiliOffertae.add(bean);
			}

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsResponsabiliOffertae;
	}

	public static List<Tuple> getMpsResponsabiliOfferta(
			DmalmMpsResponsabiliOfferta mpsResponsabiliOffertae)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsResponsabiliOffertaes = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsResponsabiliOffertaes = query.from(mpsResponsabiliOfferta).list(
					mpsResponsabiliOfferta.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsResponsabiliOffertaes;
	}

	public static void insertMpsResponsabiliOffertae(Timestamp dataEsecuzione,
			DmalmMpsResponsabiliOfferta mpsResponsabiliOffertae)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsResponsabiliOfferta);
			
			insert.columns(mpsResponsabiliOfferta.idContratto,
					mpsResponsabiliOfferta.amNome,
					mpsResponsabiliOfferta.amDataFirma,
					mpsResponsabiliOfferta.amFirmato,
					mpsResponsabiliOfferta.samNome,
					mpsResponsabiliOfferta.samDataFirma,
					mpsResponsabiliOfferta.samFirmato,
					mpsResponsabiliOfferta.vdgNome,
					mpsResponsabiliOfferta.vdgDataFirma,
					mpsResponsabiliOfferta.vdgFirmato,
					mpsResponsabiliOfferta.dgNome,
					mpsResponsabiliOfferta.dgDataFirma,
					mpsResponsabiliOfferta.dgFirmato,
					mpsResponsabiliOfferta.cdNome,
					mpsResponsabiliOfferta.cdDataFirma,
					mpsResponsabiliOfferta.cdFirmato,
					mpsResponsabiliOfferta.copertinaFirmata,
					mpsResponsabiliOfferta.dataFirmaCopertina,
					mpsResponsabiliOfferta.statoRazionale,
					mpsResponsabiliOfferta.controller,
					mpsResponsabiliOfferta.dataVerifica,
					mpsResponsabiliOfferta.prossimoFirmatarioRazionale,
					mpsResponsabiliOfferta.razionaleDigitale,
					mpsResponsabiliOfferta.notaRazionale,
					mpsResponsabiliOfferta.motivazioneRigetto)
			.values(mpsResponsabiliOffertae.getIdContratto(),
					mpsResponsabiliOffertae.getAmNome(),
					mpsResponsabiliOffertae.getAmDataFirma(),
					mpsResponsabiliOffertae.getAmFirmato(),
					mpsResponsabiliOffertae.getSamNome(),
					mpsResponsabiliOffertae.getSamDataFirma(),
					mpsResponsabiliOffertae.getSamFirmato(),
					mpsResponsabiliOffertae.getVdgNome(),
					mpsResponsabiliOffertae.getVdgDataFirma(),
					mpsResponsabiliOffertae.getVdgFirmato(),
					mpsResponsabiliOffertae.getDgNome(),
					mpsResponsabiliOffertae.getDgDataFirma(),
					mpsResponsabiliOffertae.getDgFirmato(),
					mpsResponsabiliOffertae.getCdNome(),
					mpsResponsabiliOffertae.getCdDataFirma(),
					mpsResponsabiliOffertae.getCdFirmato(),
					mpsResponsabiliOffertae.getCopertinaFirmata(),
					mpsResponsabiliOffertae.getDataFirmaCopertina(),
					mpsResponsabiliOffertae.getStatoRazionale(),
					mpsResponsabiliOffertae.getController(),
					mpsResponsabiliOffertae.getDataVerifica(),
					mpsResponsabiliOffertae.getProssimoFirmatarioRazionale(),
					mpsResponsabiliOffertae.getRazionaleDigitale(),
					mpsResponsabiliOffertae.getNotaRazionale(),
					mpsResponsabiliOffertae.getMotivazioneRigetto()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
