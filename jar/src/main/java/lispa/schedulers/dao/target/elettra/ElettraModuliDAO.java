package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElModuli;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElModuli;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class ElettraModuliDAO {
	private static Logger logger = Logger.getLogger(ElettraModuliDAO.class);

	private static QDmalmElModuli qDmalmElModuli = QDmalmElModuli.qDmalmElModuli;
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmElModuli> getAllModuli(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmElModuli bean = null;
		List<DmalmElModuli> moduli = new ArrayList<DmalmElModuli>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ELETTRAMODULI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmElModuli();

				bean.setModuloPk(rs.getInt("DMALM_STG_MODULI_PK"));
				bean.setIdModuloEdma(rs.getString("ID_MODULO_EDMA"));
				bean.setIdModuloEdmaPadre(rs.getString("ID_MODULO_EDMA_PADRE"));
				bean.setIdModulo(rs.getString("ID_MODULO"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setNome(rs.getString("NOME"));
				bean.setDescrizioneModulo(rs.getString("DS_MODULO"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDataAnnullamento(DateUtils.stringToTimestamp(
						rs.getString("DT_ANNULLAMENTO") + " 00:00:00",
						"yyyyMMdd 00:00:00"));
				bean.setResponsabile(rs.getString("RESPONSABILE"));
				bean.setSottosistema(rs.getString("SOTTOSISTEMA"));
				bean.setTecnologie(rs.getString("TECNOLOGIE"));
				bean.setTipoModulo(rs.getString("TIPO_MODULO"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setPersonaleFk(rs.getInt("PERSONALE_FK"));
				bean.setProdottoFk(rs.getInt("PRODOTTO_FK"));

				moduli.add(bean);
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

		return moduli;
	}

	public static List<Tuple> getModulo(DmalmElModuli bean) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;
		List<Tuple> moduli = new ArrayList<Tuple>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(qDmalmElModuli)
					.where(qDmalmElModuli.idModulo.eq(bean.getIdModulo()))
					.where(qDmalmElModuli.dataFineValidita.in(new SQLSubQuery()
							.from(qDmalmElModuli)
							.where(qDmalmElModuli.idModulo.eq(bean
									.getIdModulo()))
							.list(qDmalmElModuli.dataFineValidita.max())))
					.list(qDmalmElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}
	public static List<DmalmElModuli> getModuliByProdottoPk(int prodottoPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		List<DmalmElModuli> moduli = new ArrayList<>();
		DmalmElModuli bean;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_MODULI_BY_PRODOTTO_PK);

			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, prodottoPk);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean=getBeanFromTuple(rs);
				moduli.add(bean);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}


	public static void insertModulo(DmalmElModuli bean) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElModuli)
					.columns(qDmalmElModuli.moduloPk,
							qDmalmElModuli.idModuloEdma,
							qDmalmElModuli.idModuloEdmaPadre,
							qDmalmElModuli.idModulo,
							qDmalmElModuli.tipoOggetto,
							qDmalmElModuli.siglaProdotto,
							qDmalmElModuli.siglaSottosistema,
							qDmalmElModuli.siglaModulo, qDmalmElModuli.nome,
							qDmalmElModuli.descrizioneModulo,
							qDmalmElModuli.annullato,
							qDmalmElModuli.dataAnnullamento,
							qDmalmElModuli.responsabile,
							qDmalmElModuli.sottosistema,
							qDmalmElModuli.tecnologie,
							qDmalmElModuli.tipoModulo,
							qDmalmElModuli.dataCaricamento,
							qDmalmElModuli.personaleFk,
							qDmalmElModuli.prodottoFk,
							qDmalmElModuli.dataInizioValidita,
							qDmalmElModuli.dataFineValidita)
					.values(bean.getModuloPk(), bean.getIdModuloEdma(),
							bean.getIdModuloEdmaPadre(), bean.getIdModulo(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getNome(), bean.getDescrizioneModulo(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							bean.getResponsabile(), bean.getSottosistema(),
							bean.getTecnologie(), bean.getTipoModulo(),
							bean.getDataCaricamento(), bean.getPersonaleFk(),
							bean.getProdottoFk(),
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
			Integer moduloPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElModuli)
					.where(qDmalmElModuli.moduloPk.eq(moduloPk))
					.set(qDmalmElModuli.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataFineValidita,
									-1)).execute();

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

	public static void insertModuloUpdate(Timestamp dataEsecuzione,
			DmalmElModuli bean) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElModuli)
					.columns(qDmalmElModuli.moduloPk,
							qDmalmElModuli.idModuloEdma,
							qDmalmElModuli.idModuloEdmaPadre,
							qDmalmElModuli.idModulo,
							qDmalmElModuli.tipoOggetto,
							qDmalmElModuli.siglaProdotto,
							qDmalmElModuli.siglaSottosistema,
							qDmalmElModuli.siglaModulo, qDmalmElModuli.nome,
							qDmalmElModuli.descrizioneModulo,
							qDmalmElModuli.annullato,
							qDmalmElModuli.dataAnnullamento,
							qDmalmElModuli.responsabile,
							qDmalmElModuli.sottosistema,
							qDmalmElModuli.tecnologie,
							qDmalmElModuli.tipoModulo,
							qDmalmElModuli.dataCaricamento,
							qDmalmElModuli.personaleFk,
							qDmalmElModuli.prodottoFk,
							qDmalmElModuli.dataInizioValidita,
							qDmalmElModuli.dataFineValidita)
					.values(bean.getModuloPk()==null?StringTemplate
							.create("STG_MODULI_SEQ.nextval"):bean.getModuloPk(), bean.getIdModuloEdma(),
							bean.getIdModuloEdmaPadre(), bean.getIdModulo(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getNome(), bean.getDescrizioneModulo(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							bean.getResponsabile(), bean.getSottosistema(),
							bean.getTecnologie(), bean.getTipoModulo(),
							bean.getDataCaricamento(), bean.getPersonaleFk(),
							bean.getProdottoFk(), dataEsecuzione,
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

	public static void updateModulo(Integer moduloPk, DmalmElModuli bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElModuli)
					.where(qDmalmElModuli.moduloPk.eq(moduloPk))
					.set(qDmalmElModuli.idModulo, bean.getIdModulo())
					.set(qDmalmElModuli.tipoOggetto, bean.getTipoOggetto())
					.set(qDmalmElModuli.siglaSottosistema,
							bean.getSiglaSottosistema())
					.set(qDmalmElModuli.siglaModulo, bean.getSiglaModulo())
					.set(qDmalmElModuli.descrizioneModulo,
							bean.getDescrizioneModulo())
					.set(qDmalmElModuli.responsabile, bean.getResponsabile())
					.set(qDmalmElModuli.sottosistema, bean.getSottosistema())
					.set(qDmalmElModuli.tecnologie, bean.getTecnologie())
					.set(qDmalmElModuli.tipoModulo, bean.getTipoModulo())
					.set(qDmalmElModuli.prodottoFk, bean.getProdottoFk())
					.set(qDmalmElModuli.personaleFk, bean.getPersonaleFk())
					.execute();

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

	public static DmalmElModuli getBeanFromTuple(ResultSet rs) throws SQLException {
		DmalmElModuli bean=new DmalmElModuli();
		bean.setModuloPk(rs.getInt("DMALM_MODULO_PK"));
		bean.setIdModuloEdma(rs.getString("ID_MODULO_EDMA"));
		bean.setIdModuloEdmaPadre(rs.getString("ID_MODULO_EDMA_PADRE"));
		bean.setIdModulo(rs.getString("ID_MODULO"));
		bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
		bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
		bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
		bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
		bean.setNome(rs.getString("NOME"));
		bean.setDescrizioneModulo(rs.getString("DS_MODULO"));
		bean.setAnnullato(rs.getString("ANNULLATO"));
		bean.setDataAnnullamento(rs.getTimestamp("DT_ANNULLAMENTO"));
		bean.setResponsabile(rs.getString("RESPONSABILE"));
		bean.setSottosistema(rs.getString("SOTTOSISTEMA"));
		bean.setTecnologie(rs.getString("TECNOLOGIE"));
		bean.setTipoModulo(rs.getString("TIPO_MODULO"));
		bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
		bean.setPersonaleFk(rs.getInt("DMALM_PERSONALE_FK_01"));
		bean.setProdottoFk(rs.getInt("DMALM_PRODOTTO_FK_02"));

		return bean;
	}
}
