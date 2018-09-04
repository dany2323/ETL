package lispa.schedulers.dao.target.elettra;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_ELETTRAFUNZIONALITA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import lispa.schedulers.bean.target.DmalmFunzionalita;
import lispa.schedulers.bean.target.elettra.DmalmElFunzionalita;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElFunzionalita;
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

public class ElettraFunzionalitaDAO {

	private static Logger logger = Logger
			.getLogger(ElettraFunzionalitaDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmElFunzionalita dmalmElFunzionalita = QDmalmElFunzionalita.qDmalmElFunzionalita;

	public static List<DmalmElFunzionalita> getAllFunzionalita(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmElFunzionalita bean = null;

		List<DmalmElFunzionalita> funzionalita = new ArrayList<DmalmElFunzionalita>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_ELETTRAFUNZIONALITA);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmElFunzionalita();

				bean.setFunzionalitaPk(rs.getInt("DMALM_STG_FUNZIONALITA_PK"));
				bean.setIdFunzionalitaEdma(rs.getString("ID_FUNZIONALITA_EDMA"));
				bean.setIdEdmaPadre(rs.getString("ID_EDMA_PADRE"));
				bean.setIdFunzionalita(rs.getString("ID_FUNZIONALITA"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setSiglaFunzionalita(rs.getString("SIGLA_FUNZIONALITA"));
				bean.setNome(rs.getString("NOME"));
				bean.setDsFunzionalita(rs.getString("DS_FUNZIONALITA"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDtAnnullamento(rs.getTimestamp("DT_ANNULLAMENTO"));
				bean.setCategoria(rs.getString("CATEGORIA"));
				bean.setLinguaggi(rs.getString("LINGUAGGI"));
				bean.setTipiElaborazione(rs.getString("TIPI_ELABORAZIONE"));
				bean.setDmalmModuloFk01(rs.getInt("MODULO_FK"));

				funzionalita.add(bean);
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

		return funzionalita;
	}
	
	public static List<DmalmElFunzionalita> getFunzionalitaByModuloPk(Integer moduloPk, Timestamp dataEsecuzione) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmElFunzionalita bean = null;

		List<DmalmElFunzionalita> funzionalita = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ELETTRAFUNZIONALITA_BY_MODULO_PK);

			ps = connection.prepareStatement(sql);
			ps.setInt(1, moduloPk);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = getBeanFromTuple(rs);
				funzionalita.add(bean);
			}

			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return funzionalita;
	}

	public static List<Tuple> getFunzionalita(DmalmElFunzionalita bean)
			throws DAOException, PropertiesReaderException

	{
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> funzionalita = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			funzionalita = query
					.from(dmalmElFunzionalita)
					.where(bean.getIdFunzionalita() != null ? dmalmElFunzionalita.idFunzionalita
							.lower().eq(bean.getIdFunzionalita().toLowerCase())
							: dmalmElFunzionalita.idFunzionalita.isNull())
					.where(bean.getSiglaProdotto() != null ? dmalmElFunzionalita.siglaProdotto
							.lower().eq(bean.getSiglaProdotto().toLowerCase())
							: dmalmElFunzionalita.siglaProdotto.isNull())
					.where(bean.getSiglaModulo() != null ? dmalmElFunzionalita.siglaModulo
							.lower().eq(bean.getSiglaModulo().toLowerCase())
							: dmalmElFunzionalita.siglaModulo.isNull())
					.where(bean.getTipoOggetto() != null ? dmalmElFunzionalita.tipoOggetto
							.lower().eq(bean.getTipoOggetto().toLowerCase())
							: dmalmElFunzionalita.tipoOggetto.isNull())
					.where(dmalmElFunzionalita.dtFineValidita.in(new SQLSubQuery()
							.from(dmalmElFunzionalita)
							.where(bean.getIdFunzionalita() != null ? dmalmElFunzionalita.idFunzionalita
									.lower().eq(
											bean.getIdFunzionalita()
													.toLowerCase())
									: dmalmElFunzionalita.idFunzionalita
											.isNull())
							.where(bean.getSiglaProdotto() != null ? dmalmElFunzionalita.siglaProdotto
									.lower().eq(
											bean.getSiglaProdotto()
													.toLowerCase())
									: dmalmElFunzionalita.siglaProdotto
											.isNull())
							.where(bean.getSiglaModulo() != null ? dmalmElFunzionalita.siglaModulo
									.lower()
									.eq(bean.getSiglaModulo().toLowerCase())
									: dmalmElFunzionalita.siglaModulo.isNull())
							.where(bean.getTipoOggetto() != null ? dmalmElFunzionalita.tipoOggetto
									.lower()
									.eq(bean.getTipoOggetto().toLowerCase())
									: dmalmElFunzionalita.tipoOggetto.isNull())
							.list(dmalmElFunzionalita.dtFineValidita.max())))
					.list(dmalmElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return funzionalita;
	}

	public static void insertFunzionalita(DmalmElFunzionalita bean,
			Timestamp dataEsecuzione) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmElFunzionalita)
					.columns(dmalmElFunzionalita.funzionalitaPk,
							dmalmElFunzionalita.idFunzionalitaEdma,
							dmalmElFunzionalita.idEdmaPadre,
							dmalmElFunzionalita.idFunzionalita,
							dmalmElFunzionalita.tipoOggetto,
							dmalmElFunzionalita.siglaProdotto,
							dmalmElFunzionalita.siglaSottosistema,
							dmalmElFunzionalita.siglaModulo,
							dmalmElFunzionalita.siglaFunzionalita,
							dmalmElFunzionalita.nome,
							dmalmElFunzionalita.dsFunzionalita,
							dmalmElFunzionalita.annullato,
							dmalmElFunzionalita.dtAnnullamento,
							dmalmElFunzionalita.categoria,
							dmalmElFunzionalita.linguaggi,
							dmalmElFunzionalita.tipiElaborazione,
							dmalmElFunzionalita.dtCaricamento,
							dmalmElFunzionalita.dtInizioValidita,
							dmalmElFunzionalita.dtFineValidita,
							dmalmElFunzionalita.dmalmModuloFk01)
					.values(bean.getFunzionalitaPk(),
							bean.getIdFunzionalitaEdma(),
							bean.getIdEdmaPadre(), bean.getIdFunzionalita(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getSiglaFunzionalita(), bean.getNome(),
							bean.getDsFunzionalita(), bean.getAnnullato(),
							bean.getDtAnnullamento(), bean.getCategoria(),
							bean.getLinguaggi(), bean.getTipiElaborazione(),
							dataEsecuzione,
							DateUtils.setDtInizioValidita1900(),
							DateUtils.setDtFineValidita9999(),
							bean.getDmalmModuloFk01()).execute();

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
			Integer funzionalitaPk) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmElFunzionalita)
					.where(dmalmElFunzionalita.funzionalitaPk
							.eq(funzionalitaPk))
					.set(dmalmElFunzionalita.dtFineValidita,
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

	public static void insertFunzionalitaUpdate(Timestamp dataEsecuzione,
			DmalmElFunzionalita bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmElFunzionalita)
					.columns(dmalmElFunzionalita.funzionalitaPk,
							dmalmElFunzionalita.idFunzionalitaEdma,
							dmalmElFunzionalita.idEdmaPadre,
							dmalmElFunzionalita.idFunzionalita,
							dmalmElFunzionalita.tipoOggetto,
							dmalmElFunzionalita.siglaProdotto,
							dmalmElFunzionalita.siglaSottosistema,
							dmalmElFunzionalita.siglaModulo,
							dmalmElFunzionalita.siglaFunzionalita,
							dmalmElFunzionalita.nome,
							dmalmElFunzionalita.dsFunzionalita,
							dmalmElFunzionalita.annullato,
							dmalmElFunzionalita.dtAnnullamento,
							dmalmElFunzionalita.categoria,
							dmalmElFunzionalita.linguaggi,
							dmalmElFunzionalita.tipiElaborazione,
							dmalmElFunzionalita.dtCaricamento,
							dmalmElFunzionalita.dtInizioValidita,
							dmalmElFunzionalita.dtFineValidita,
							dmalmElFunzionalita.dmalmModuloFk01)
					.values(StringTemplate.create("STG_FUNZIONALITA_SEQ.nextval"),
							bean.getIdFunzionalitaEdma(),
							bean.getIdEdmaPadre(), bean.getIdFunzionalita(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getSiglaFunzionalita(), bean.getNome(),
							bean.getDsFunzionalita(), bean.getAnnullato(),
							bean.getDtAnnullamento(), bean.getCategoria(),
							bean.getLinguaggi(), bean.getTipiElaborazione(),
							dataEsecuzione,
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.setDtFineValidita9999(),
							bean.getDmalmModuloFk01()).execute();

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

	public static void updateFunzionalita(Integer funzionalitaPk,
			DmalmElFunzionalita funzionalita) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmElFunzionalita)

					.where(dmalmElFunzionalita.funzionalitaPk
							.eq(funzionalitaPk))
					.set(dmalmElFunzionalita.idFunzionalitaEdma,
							funzionalita.getIdFunzionalitaEdma())
					.set(dmalmElFunzionalita.idEdmaPadre,
							funzionalita.getIdEdmaPadre())
					.set(dmalmElFunzionalita.idFunzionalita,
							funzionalita.getIdFunzionalita())
					.set(dmalmElFunzionalita.tipoOggetto,
							funzionalita.getTipoOggetto())
					.set(dmalmElFunzionalita.siglaProdotto,
							funzionalita.getSiglaProdotto())
					.set(dmalmElFunzionalita.siglaSottosistema,
							funzionalita.getSiglaSottosistema())
					.set(dmalmElFunzionalita.siglaModulo,
							funzionalita.getSiglaModulo())
					.set(dmalmElFunzionalita.dsFunzionalita,
							funzionalita.getDsFunzionalita())
					.set(dmalmElFunzionalita.categoria,
							funzionalita.getCategoria())
					.set(dmalmElFunzionalita.linguaggi,
							funzionalita.getLinguaggi())
					.set(dmalmElFunzionalita.tipiElaborazione,
							funzionalita.getTipiElaborazione())
					.set(dmalmElFunzionalita.dmalmModuloFk01,
							funzionalita.getDmalmModuloFk01())

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

	public static DmalmElFunzionalita getBeanFromTuple(ResultSet rs) throws SQLException {
		
		DmalmElFunzionalita bean= new DmalmElFunzionalita();

		bean.setFunzionalitaPk(rs.getInt("DMALM_FUNZIONALITA_PK"));
		bean.setIdFunzionalitaEdma(rs.getString("ID_FUNZIONALITA_EDMA"));
		bean.setIdEdmaPadre(rs.getString("ID_EDMA_PADRE"));
		bean.setIdFunzionalita(rs.getString("ID_FUNZIONALITA"));
		bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
		bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
		bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
		bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
		bean.setSiglaFunzionalita(rs.getString("SIGLA_FUNZIONALITA"));
		bean.setNome(rs.getString("NOME"));
		bean.setDsFunzionalita(rs.getString("DS_FUNZIONALITA"));
		bean.setAnnullato(rs.getString("ANNULLATO"));
		bean.setDtAnnullamento(rs.getTimestamp("DT_ANNULLAMENTO"));
		bean.setCategoria(rs.getString("CATEGORIA"));
		bean.setLinguaggi(rs.getString("LINGUAGGI"));
		bean.setTipiElaborazione(rs.getString("TIPI_ELABORAZIONE"));
		bean.setDmalmModuloFk01(rs.getInt("DMALM_MODULO_FK_01"));
		
		return bean;
		
	}


}
