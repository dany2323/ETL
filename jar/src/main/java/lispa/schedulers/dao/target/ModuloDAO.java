package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_MODULI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmModulo;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmModulo;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ModuloDAO {

	private static Logger logger = Logger.getLogger(ModuloDAO.class);

	private static QDmalmModulo dmalmModulo = QDmalmModulo.dmalmModulo;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmModulo> getAllModuli(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmModulo bean = null;
		List<DmalmModulo> moduli = new ArrayList<DmalmModulo>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_MODULI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {

				bean = new DmalmModulo();
				bean.setDmalmModuloSeq(rs.getInt("DMALM_MODULO_SEQ"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDtAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setNome(rs.getString("NOME"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setDmalmModuloPk(rs.getInt("ID_EDMA"));
				bean.setDsModulo(rs.getString("DS_MODULO"));
				bean.setDmalmProdottoFk02(rs.getInt("ID_EDMA_PADRE_PRODOTTO"));
				bean.setDmalmSottosistemaFk03(rs
						.getInt("ID_EDMA_PADRE_SOTTOSISTEMA"));
				bean.setIdModulo(rs.getString("ID_MODULO"));

				// FK
				// bean.setDmalmPersonaleFk01(PersonaleEdmaLispaDAO.getPersonaleEdmaLispaByCodice(rs.getString("RESPONSABILE"))+"");
				bean.setDmalmPersonaleFk01(rs.getString("RESPONSABILE"));

				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
				bean.setSottosistema(rs.getString("SOTTOSISTEMA"));
				bean.setTecnologie(rs.getString("TECNOLOGIE"));
				bean.setTipoModulo(rs.getString("TIPO_MODULO"));

				moduli.add(bean);
			}

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmModulo bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmModulo)
					.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
							.getDmalmModuloPk()))
					.where(dmalmModulo.dmalmProdottoFk02.eq(bean
							.getDmalmProdottoFk02()))
					.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
							.getDmalmSottosistemaFk03()))
					.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
					.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
							.eq(bean.getSiglaProdotto())
							: dmalmModulo.siglaProdotto.isNull())
					.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
							.eq(bean.getSiglaSottosistema())
							: dmalmModulo.siglaSottosistema.isNull())
					.where(dmalmModulo.dtInserimentoRecord.in(new SQLSubQuery()
							.from(dmalmModulo)
							.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
									.getDmalmModuloPk()))
							.where(dmalmModulo.dmalmProdottoFk02.eq(bean
									.getDmalmProdottoFk02()))
							.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
									.getDmalmSottosistemaFk03()))
							.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
							.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
									.eq(bean.getSiglaProdotto())
									: dmalmModulo.siglaProdotto.isNull())
							.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
									.eq(bean.getSiglaSottosistema())
									: dmalmModulo.siglaSottosistema.isNull())
							.list(dmalmModulo.dtInserimentoRecord.max())))

					.set(dmalmModulo.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void resetDataFineValidita9999(DmalmModulo bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmModulo)
					.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
							.getDmalmModuloPk()))
					.where(dmalmModulo.dmalmProdottoFk02.eq(bean
							.getDmalmProdottoFk02()))
					.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
							.getDmalmSottosistemaFk03()))
					.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
					.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
							.eq(bean.getSiglaProdotto())
							: dmalmModulo.siglaProdotto.isNull())
					.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
							.eq(bean.getSiglaSottosistema())
							: dmalmModulo.siglaSottosistema.isNull())
					.where(dmalmModulo.dtInserimentoRecord.in(new SQLSubQuery()
							.from(dmalmModulo)
							.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
									.getDmalmModuloPk()))
							.where(dmalmModulo.dmalmProdottoFk02.eq(bean
									.getDmalmProdottoFk02()))
							.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
									.getDmalmSottosistemaFk03()))
							.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
							.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
									.eq(bean.getSiglaProdotto())
									: dmalmModulo.siglaProdotto.isNull())
							.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
									.eq(bean.getSiglaSottosistema())
									: dmalmModulo.siglaSottosistema.isNull())
							.list(dmalmModulo.dtInserimentoRecord.max())))
					.set(dmalmModulo.dtFineValidita,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateModulo(DmalmModulo bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmModulo)
					.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
							.getDmalmModuloPk()))
					.where(dmalmModulo.dmalmProdottoFk02.eq(bean
							.getDmalmProdottoFk02()))
					.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
							.getDmalmSottosistemaFk03()))
					.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
					.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
							.eq(bean.getSiglaProdotto())
							: dmalmModulo.siglaProdotto.isNull())
					.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
							.eq(bean.getSiglaSottosistema())
							: dmalmModulo.siglaSottosistema.isNull())

					.where(dmalmModulo.dtInserimentoRecord.in(new SQLSubQuery()
							.from(dmalmModulo)
							.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
									.getDmalmModuloPk()))
							.where(dmalmModulo.dmalmProdottoFk02.eq(bean
									.getDmalmProdottoFk02()))
							.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
									.getDmalmSottosistemaFk03()))
							.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
							.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
									.eq(bean.getSiglaProdotto())
									: dmalmModulo.siglaProdotto.isNull())
							.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
									.eq(bean.getSiglaSottosistema())
									: dmalmModulo.siglaSottosistema.isNull())
							.list(dmalmModulo.dtInserimentoRecord.max())))

					.set(dmalmModulo.dmalmProdottoFk02,
							bean.getDmalmProdottoFk02())
					.set(dmalmModulo.dmalmSottosistemaFk03,
							bean.getDmalmSottosistemaFk03())
					.set(dmalmModulo.idModulo, bean.getIdModulo())
					.set(dmalmModulo.tipoOggetto, bean.getTipoOggetto())
					.set(dmalmModulo.siglaModulo, bean.getSiglaModulo())
					.set(dmalmModulo.siglaSottosistema,
							bean.getSiglaSottosistema())
					.set(dmalmModulo.dsModulo, bean.getDsModulo())
					.set(dmalmModulo.sottosistema, bean.getSottosistema())
					.set(dmalmModulo.tecnologie, bean.getTecnologie())
					.set(dmalmModulo.tipoModulo, bean.getTipoModulo())
					.set(dmalmModulo.dtAnnullamento, bean.getDtAnnullamento())

					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertModulo(DmalmModulo bean, Timestamp dataesecuzione)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmModulo)
					.columns(dmalmModulo.dmalmModuloPrimaryKey,
							dmalmModulo.dmalmProdottoFk02,
							dmalmModulo.idModulo, dmalmModulo.tipoOggetto,
							dmalmModulo.siglaProdotto,
							dmalmModulo.siglaSottosistema,
							dmalmModulo.siglaModulo, dmalmModulo.nome,
							dmalmModulo.dsModulo, dmalmModulo.annullato,
							dmalmModulo.dtInizioValidita,
							dmalmModulo.dtFineValidita,
							dmalmModulo.dmalmPersonaleFk01,
							dmalmModulo.sottosistema, dmalmModulo.tecnologie,
							dmalmModulo.tipoModulo,
							dmalmModulo.dtInserimentoRecord,
							dmalmModulo.dmalmSottosistemaFk03,
							dmalmModulo.dtAnnullamento,
							dmalmModulo.dmalmModuloSeq)
					.values(bean.getDmalmModuloPk(),
							bean.getDmalmProdottoFk02(), bean.getIdModulo(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getNome(), bean.getDsModulo(),
							bean.getAnnullato(),
							DateUtils.setDtInizioValidita1900(),
							DateUtils.setDtFineValidita9999(),
							bean.getDmalmPersonaleFk01(),
							bean.getSottosistema(), bean.getTecnologie(),
							bean.getTipoModulo(), dataesecuzione,
							bean.getDmalmSottosistemaFk03(),
							bean.getDtAnnullamento(), bean.getDmalmModuloSeq())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error("insertModulo() --> " + LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertModuloUpdate(Timestamp dataEsecuzione,
			DmalmModulo bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmModulo)
					.columns(dmalmModulo.dmalmModuloPrimaryKey,
							dmalmModulo.dmalmProdottoFk02,
							dmalmModulo.idModulo, dmalmModulo.tipoOggetto,
							dmalmModulo.siglaProdotto,
							dmalmModulo.siglaSottosistema,
							dmalmModulo.siglaModulo, dmalmModulo.nome,
							dmalmModulo.dsModulo, dmalmModulo.annullato,
							dmalmModulo.dtInizioValidita,
							dmalmModulo.dtFineValidita,
							dmalmModulo.dmalmPersonaleFk01,
							dmalmModulo.sottosistema, dmalmModulo.tecnologie,
							dmalmModulo.tipoModulo,
							dmalmModulo.dtInserimentoRecord,
							dmalmModulo.dmalmSottosistemaFk03,
							dmalmModulo.dtAnnullamento,
							dmalmModulo.dmalmModuloSeq)
					.values(bean.getDmalmModuloPk(),
							bean.getDmalmProdottoFk02(), bean.getIdModulo(),
							bean.getTipoOggetto(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getSiglaModulo(),
							bean.getNome(), bean.getDsModulo(),
							bean.getAnnullato(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.setDtFineValidita9999(),
							bean.getDmalmPersonaleFk01(),
							bean.getSottosistema(), bean.getTecnologie(),
							bean.getTipoModulo(), dataEsecuzione,
							bean.getDmalmSottosistemaFk03(),
							bean.getDtAnnullamento(), bean.getDmalmModuloSeq())
					.execute();

			connection.commit();

		} catch (Exception e) {

			// resetDataFineValidita9999(bean);

			logger.error(e.getMessage(), e);
			logger.error("insertModuloUpdate() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getModulo(DmalmModulo bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			classificatori = query
					.from(dmalmModulo)
					.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
							.getDmalmModuloPk()))
					.where(dmalmModulo.dmalmProdottoFk02.eq(bean
							.getDmalmProdottoFk02()))
					.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
							.getDmalmSottosistemaFk03()))
					.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
					.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
							.eq(bean.getSiglaProdotto())
							: dmalmModulo.siglaProdotto.isNull())
					.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
							.eq(bean.getSiglaSottosistema())
							: dmalmModulo.siglaSottosistema.isNull())
					.where(dmalmModulo.dtInserimentoRecord.in(new SQLSubQuery()
							.from(dmalmModulo)
							.where(dmalmModulo.dmalmModuloPrimaryKey.eq(bean
									.getDmalmModuloPk()))
							.where(dmalmModulo.dmalmProdottoFk02.eq(bean
									.getDmalmProdottoFk02()))
							.where(dmalmModulo.dmalmSottosistemaFk03.eq(bean
									.getDmalmSottosistemaFk03()))
							.where(dmalmModulo.idModulo.eq(bean.getIdModulo()))
							.where(bean.getSiglaProdotto() != null ? dmalmModulo.siglaProdotto
									.eq(bean.getSiglaProdotto())
									: dmalmModulo.siglaProdotto.isNull())
							.where(bean.getSiglaSottosistema() != null ? dmalmModulo.siglaSottosistema
									.eq(bean.getSiglaSottosistema())
									: dmalmModulo.siglaSottosistema.isNull())
							.list(dmalmModulo.dtInserimentoRecord.max())))
					.list(dmalmModulo.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return classificatori;
	}

}