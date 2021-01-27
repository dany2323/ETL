package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_SOTTOSISTEMI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmSottosistema;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmSottosistema;
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

public class SottosistemaDAO {

	private static Logger logger = Logger.getLogger(SottosistemaDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmSottosistema dmalmSottosistema = QDmalmSottosistema.dmalmSottosistema;

	public static List<DmalmSottosistema> getAllSottosistemi(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmSottosistema bean = null;
		List<DmalmSottosistema> sottosistemi = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_SOTTOSISTEMI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {

				bean = new DmalmSottosistema();

				bean.setDmalmSottosistemaPk(rs.getInt("ID_EDMA"));
				bean.setDmalmProdottoFk01(rs.getInt("ID_EDMA_PADRE"));
				bean.setIdSottosistema(rs.getString("ID_SOTTOSISTEMA"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setNome(rs.getString("NOME"));
				bean.setDsSottosistema(rs.getString("DS_SOTTOSISTEMA"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDtAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setTipo(rs.getString("TIPO"));
				bean.setBaseDatiEtl(rs.getString("BASE_DATI_ETL"));
				bean.setBaseDatiLettura(rs.getString("BASE_DATI_LETTURA"));
				bean.setBaseDatiScrittura(rs.getString("BASE_DATI_SCRITTURA"));
				bean.setSiglaSottosistema("SOTT_" + rs.getInt("ID_EDMA"));

				sottosistemi.add(bean);
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

		return sottosistemi;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmSottosistema sottosistema) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmSottosistema)
					.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
							.eq(sottosistema.getDmalmSottosistemaPk()))
					.where(dmalmSottosistema.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmSottosistema)
									.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
											.eq(sottosistema
													.getDmalmSottosistemaPk()))
									.list(dmalmSottosistema.dtInserimentoRecord
											.max())))
					.set(dmalmSottosistema.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	// public static void resetDataFineValidita9999(DmalmSottosistema
	// sottosistema) throws DAOException
	//
	// {
	//
	// ConnectionManager cm = null;
	// Connection connection = null;
	//
	// try
	// {
	//
	// cm = ConnectionManager.getInstance();
	// connection = cm.getConnectionOracle();
	//
	// connection.setAutoCommit(false);
	//
	// new SQLUpdateClause(connection, dialect, dmalmSottosistema)
	// .where(dmalmSottosistema.dmalmSottosistemaPrimaryKey.eq(sottosistema.getDmalmSottosistemaPk()))
	// .where(dmalmSottosistema.dmalmProdottoFk01.eq(sottosistema.getDmalmProdottoFk01()))
	// .where(dmalmSottosistema.idSottosistema.eq(sottosistema.getIdSottosistema()))
	// .where(sottosistema.getSiglaProdotto() != null ?
	// dmalmSottosistema.siglaProdotto.eq(sottosistema.getSiglaProdotto()) :
	// dmalmSottosistema.siglaProdotto.isNull())
	// .where(dmalmSottosistema.dtInserimentoRecord.in
	// (
	// new SQLSubQuery().from(dmalmSottosistema)
	// .where(dmalmSottosistema.dmalmSottosistemaPrimaryKey.eq(sottosistema.getDmalmSottosistemaPk()))
	// .where(dmalmSottosistema.dmalmProdottoFk01.eq(sottosistema.getDmalmProdottoFk01()))
	// .where(dmalmSottosistema.idSottosistema.eq(sottosistema.getIdSottosistema()))
	// .where(sottosistema.getSiglaProdotto() != null ?
	// dmalmSottosistema.siglaProdotto.eq(sottosistema.getSiglaProdotto()) :
	// dmalmSottosistema.siglaProdotto.isNull())
	// .list(dmalmSottosistema.dtInserimentoRecord.max())
	// ))
	// .set(dmalmSottosistema.dtFineValidita, DateUtils.getDtFineValidita9999())
	// .execute();
	//
	// connection.commit();
	//
	//
	// }
	// catch(Exception e)
	// {
	// ErrorManager.getInstance().exceptionOccurred(true, e);
	//
	// }
	// finally
	// {
	// if(cm != null) cm.closeConnection(connection);
	// }
	// }
	//

	public static void updateSottosistema(DmalmSottosistema sottosistema)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmSottosistema)

					.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
							.eq(sottosistema.getDmalmSottosistemaPk()))
					.where(dmalmSottosistema.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmSottosistema)
									.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
											.eq(sottosistema
													.getDmalmSottosistemaPk()))
									.list(dmalmSottosistema.dtInserimentoRecord
											.max())))
					.set(dmalmSottosistema.dmalmProdottoFk01,
							sottosistema.getDmalmProdottoFk01())
					.set(dmalmSottosistema.idSottosistema,
							sottosistema.getIdSottosistema())
					.set(dmalmSottosistema.tipoOggetto,
							sottosistema.getTipoOggetto())
					.set(dmalmSottosistema.siglaProdotto,
							sottosistema.getSiglaProdotto())
					.set(dmalmSottosistema.dsSottosistema,
							sottosistema.getDsSottosistema())
					.set(dmalmSottosistema.tipo, sottosistema.getTipo())
					.set(dmalmSottosistema.baseDatiEtl,
							sottosistema.getBaseDatiEtl())
					.set(dmalmSottosistema.baseDatiLettura,
							sottosistema.getBaseDatiLettura())
					.set(dmalmSottosistema.baseDatiScrittura,
							sottosistema.getBaseDatiScrittura())
					.set(dmalmSottosistema.dtAnnullamento,
							sottosistema.getDtAnnullamento())

					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertSottosistema(DmalmSottosistema bean,
			Timestamp dataesecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmSottosistema)
					.columns(dmalmSottosistema.dmalmSottosistemaPrimaryKey,
							dmalmSottosistema.dmalmProdottoFk01,
							dmalmSottosistema.idSottosistema,
							dmalmSottosistema.tipoOggetto,
							dmalmSottosistema.siglaProdotto,
							dmalmSottosistema.siglaSottosistema,
							dmalmSottosistema.nome,
							dmalmSottosistema.dsSottosistema,
							dmalmSottosistema.annullato,
							dmalmSottosistema.dtInizioValidita,
							dmalmSottosistema.dtFineValidita,
							dmalmSottosistema.tipo,
							dmalmSottosistema.baseDatiEtl,
							dmalmSottosistema.baseDatiLettura,
							dmalmSottosistema.baseDatiScrittura,
							dmalmSottosistema.dtInserimentoRecord,
							dmalmSottosistema.dtAnnullamento)
					.values(bean.getDmalmSottosistemaPk(),
							bean.getDmalmProdottoFk01(),
							bean.getIdSottosistema(), bean.getTipoOggetto(),
							bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getNome(),
							bean.getDsSottosistema(), bean.getAnnullato(),
							DateUtils.getDtInizioValidita1900(),
							DateUtils.getDtFineValidita9999(), bean.getTipo(),
							bean.getBaseDatiEtl(), bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), dataesecuzione,
							bean.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error("insertSottosistema() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertSottosistemaUpdate(Timestamp dataEsecuzione,
			DmalmSottosistema bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmSottosistema)
					.columns(dmalmSottosistema.dmalmSottosistemaPrimaryKey,
							dmalmSottosistema.dmalmProdottoFk01,
							dmalmSottosistema.idSottosistema,
							dmalmSottosistema.tipoOggetto,
							dmalmSottosistema.siglaProdotto,
							dmalmSottosistema.siglaSottosistema,
							dmalmSottosistema.nome,
							dmalmSottosistema.dsSottosistema,
							dmalmSottosistema.annullato,
							dmalmSottosistema.dtInizioValidita,
							dmalmSottosistema.dtFineValidita,
							dmalmSottosistema.tipo,
							dmalmSottosistema.baseDatiEtl,
							dmalmSottosistema.baseDatiLettura,
							dmalmSottosistema.baseDatiScrittura,
							dmalmSottosistema.dtInserimentoRecord,
							dmalmSottosistema.dtAnnullamento)
					.values(bean.getDmalmSottosistemaPk(),
							bean.getDmalmProdottoFk01(),
							bean.getIdSottosistema(), bean.getTipoOggetto(),
							bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(), bean.getNome(),
							bean.getDsSottosistema(), bean.getAnnullato(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.getDtFineValidita9999(), bean.getTipo(),
							bean.getBaseDatiEtl(), bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), dataEsecuzione,
							bean.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage(), e);
			logger.error("insertSottosistemaUpdate() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getSottosistema(DmalmSottosistema bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> sottosistemi = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			sottosistemi =

			query.from(dmalmSottosistema)
					.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
							.eq(bean.getDmalmSottosistemaPk()))
					.where(dmalmSottosistema.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmSottosistema)
									.where(dmalmSottosistema.dmalmSottosistemaPrimaryKey
											.eq(bean.getDmalmSottosistemaPk()))
									.list(dmalmSottosistema.dtInserimentoRecord
											.max())))
					.list(dmalmSottosistema.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return sottosistemi;
	}
}