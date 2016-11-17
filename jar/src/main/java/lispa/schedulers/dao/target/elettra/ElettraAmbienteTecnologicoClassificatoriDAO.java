package lispa.schedulers.dao.target.elettra;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_ELETTRAAMBIENTETECNOLOGICOCLASSIFICATORI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ElettraAmbienteTecnologicoClassificatoriDAO {

	private static Logger logger = Logger
			.getLogger(ElettraAmbienteTecnologicoClassificatoriDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmElAmbienteTecnologicoClassificatori dmalmElAmbienteTecnologicoClassificatori = QDmalmElAmbienteTecnologicoClassificatori.qDmalmElAmbienteTecnologicoClassificatori;

	public static List<DmalmElAmbienteTecnologicoClassificatori> getAllAmbienteTecnologicoClassificatori(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmElAmbienteTecnologicoClassificatori bean = null;

		List<DmalmElAmbienteTecnologicoClassificatori> ambientetecnologicoclassificatori = new ArrayList<DmalmElAmbienteTecnologicoClassificatori>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_ELETTRAAMBIENTETECNOLOGICOCLASSIFICATORI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmElAmbienteTecnologicoClassificatori();
				bean.setAmbienteTecnologicoClassificatoriPk(rs
						.getInt("DMALM_STG_CLASSIFICATORI_PK"));
				bean.setIdAmbienteTecnologico(rs.getString("ID_AMB_TECN"));
				bean.setNomeAmbienteTecnologico(rs.getString("NOME_AMB_TECN"));
				bean.setDescrizioneAmbienteTecnologico(rs
						.getString("DESCRIZIONE_AMB_TECN"));
				bean.setIdClassificatore(rs.getString("ID_CLASSIFICATORE"));
				bean.setNomeClassificatore(rs.getString("NOME_CLASSIFICATORE"));
				bean.setDescrizioneClassificatore(rs
						.getString("DESCR_CLASSIFICATORE"));
				bean.setAnnullato("NO");

				ambientetecnologicoclassificatori.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return ambientetecnologicoclassificatori;
	}

	public static List<Tuple> getAmbienteTecnologicoClassificatori(
			DmalmElAmbienteTecnologicoClassificatori bean) throws DAOException,
			PropertiesReaderException

	{
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> ambientetecnologicoclassificatori = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			ambientetecnologicoclassificatori = query
					.from(dmalmElAmbienteTecnologicoClassificatori)
					.where(bean.getIdAmbienteTecnologico() != null ? dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico
							.lower().eq(
									bean.getIdAmbienteTecnologico()
											.toLowerCase())
							: dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico
									.isNull())
					.where(bean.getIdClassificatore() != null ? dmalmElAmbienteTecnologicoClassificatori.idClassificatore
							.lower().eq(
									bean.getIdClassificatore().toLowerCase())
							: dmalmElAmbienteTecnologicoClassificatori.idClassificatore
									.isNull())
					.where(dmalmElAmbienteTecnologicoClassificatori.dataFineValidita.in(new SQLSubQuery()
							.from(dmalmElAmbienteTecnologicoClassificatori)
							.where(bean.getIdAmbienteTecnologico() != null ? dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico
									.lower().eq(
											bean.getIdAmbienteTecnologico()
													.toLowerCase())
									: dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico
											.isNull())
							.where(bean.getIdClassificatore() != null ? dmalmElAmbienteTecnologicoClassificatori.idClassificatore
									.lower().eq(
											bean.getIdClassificatore()
													.toLowerCase())
									: dmalmElAmbienteTecnologicoClassificatori.idClassificatore
											.isNull())
							.list(dmalmElAmbienteTecnologicoClassificatori.dataFineValidita
									.max())))
					.list(dmalmElAmbienteTecnologicoClassificatori.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return ambientetecnologicoclassificatori;
	}

	public static void insertAmbienteTecnologicoClassificatori(
			DmalmElAmbienteTecnologicoClassificatori bean,
			Timestamp dataEsecuzione) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					dmalmElAmbienteTecnologicoClassificatori)
					.columns(
							dmalmElAmbienteTecnologicoClassificatori.ambienteTecnologicoClassificatoriPk,
							dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.nomeAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.descrizioneAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.idClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.nomeClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.descrizioneClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.annullato,
							dmalmElAmbienteTecnologicoClassificatori.dataAnnullamento,
							dmalmElAmbienteTecnologicoClassificatori.dataCaricamento,
							dmalmElAmbienteTecnologicoClassificatori.dataInizioValidita,
							dmalmElAmbienteTecnologicoClassificatori.dataFineValidita)
					.values(bean.getAmbienteTecnologicoClassificatoriPk(),
							bean.getIdAmbienteTecnologico(),
							bean.getNomeAmbienteTecnologico(),
							bean.getDescrizioneAmbienteTecnologico(),
							bean.getIdClassificatore(),
							bean.getNomeClassificatore(),
							bean.getDescrizioneClassificatore(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							dataEsecuzione,
							DateUtils.setDtInizioValidita1900(),
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			Integer ambienteTecnologicoClassificatoriPk) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					dmalmElAmbienteTecnologicoClassificatori)
					.where(dmalmElAmbienteTecnologicoClassificatori.ambienteTecnologicoClassificatoriPk
							.eq(ambienteTecnologicoClassificatoriPk))
					.set(dmalmElAmbienteTecnologicoClassificatori.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataFineValidita,
									-1)).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertAmbienteTecnologicoClassificatoriUpdate(
			Timestamp dataEsecuzione,
			DmalmElAmbienteTecnologicoClassificatori bean) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					dmalmElAmbienteTecnologicoClassificatori)
					.columns(
							dmalmElAmbienteTecnologicoClassificatori.ambienteTecnologicoClassificatoriPk,
							dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.nomeAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.descrizioneAmbienteTecnologico,
							dmalmElAmbienteTecnologicoClassificatori.idClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.nomeClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.descrizioneClassificatore,
							dmalmElAmbienteTecnologicoClassificatori.annullato,
							dmalmElAmbienteTecnologicoClassificatori.dataAnnullamento,
							dmalmElAmbienteTecnologicoClassificatori.dataCaricamento,
							dmalmElAmbienteTecnologicoClassificatori.dataInizioValidita,
							dmalmElAmbienteTecnologicoClassificatori.dataFineValidita)
					.values(bean.getAmbienteTecnologicoClassificatoriPk(),
							bean.getIdAmbienteTecnologico(),
							bean.getNomeAmbienteTecnologico(),
							bean.getDescrizioneAmbienteTecnologico(),
							bean.getIdClassificatore(),
							bean.getNomeClassificatore(),
							bean.getDescrizioneClassificatore(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							dataEsecuzione,
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateAmbienteTecnologicoClassificatori(
			Integer ambienteTecnologicoClassificatoriPk,
			DmalmElAmbienteTecnologicoClassificatori ambientetecnologicoclassificatori)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					dmalmElAmbienteTecnologicoClassificatori)

					.where(dmalmElAmbienteTecnologicoClassificatori.ambienteTecnologicoClassificatoriPk
							.eq(ambienteTecnologicoClassificatoriPk))
					.set(dmalmElAmbienteTecnologicoClassificatori.idAmbienteTecnologico,
							ambientetecnologicoclassificatori
									.getIdAmbienteTecnologico())
					.set(dmalmElAmbienteTecnologicoClassificatori.nomeAmbienteTecnologico,
							ambientetecnologicoclassificatori
									.getNomeAmbienteTecnologico())
					.set(dmalmElAmbienteTecnologicoClassificatori.descrizioneAmbienteTecnologico,
							ambientetecnologicoclassificatori
									.getDescrizioneAmbienteTecnologico())
					.set(dmalmElAmbienteTecnologicoClassificatori.idClassificatore,
							ambientetecnologicoclassificatori
									.getIdClassificatore())
					.set(dmalmElAmbienteTecnologicoClassificatori.nomeClassificatore,
							ambientetecnologicoclassificatori
									.getNomeClassificatore())
					.set(dmalmElAmbienteTecnologicoClassificatori.descrizioneClassificatore,
							ambientetecnologicoclassificatori
									.getDescrizioneClassificatore()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
