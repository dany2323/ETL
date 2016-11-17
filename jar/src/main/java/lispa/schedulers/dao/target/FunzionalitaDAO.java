package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_FUNZIONALITA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmFunzionalita;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmFunzionalita;
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

public class FunzionalitaDAO {

	private static Logger logger = Logger.getLogger(FunzionalitaDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmFunzionalita dmalmFunzionalita = QDmalmFunzionalita.dmalmFunzionalita;

	public static List<DmalmFunzionalita> getAllFunzionalita(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmFunzionalita bean = null;
		List<DmalmFunzionalita> funzionalita = new ArrayList<DmalmFunzionalita>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_FUNZIONALITA);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {

				bean = new DmalmFunzionalita();
				bean.setDmalmFunzionalitaSeq(rs
						.getInt("DMALM_FUNZIONALITA_SEQ"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setCategoria(rs.getString("CATEGORIA"));
				bean.setDmalmFunzionalitaPk(rs.getInt("ID_EDMA"));
				bean.setDsFunzionalita(rs.getString("DS_FUNZIONALITA"));
				bean.setDtAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setDmalmModuloFk01(rs.getInt("ID_EDMA_PADRE"));
				bean.setIdFunzionalita(rs.getString("ID_FUNZIONALITA"));
				bean.setLinguaggi(rs.getString("LINGUAGGI"));
				bean.setNome(rs.getString("NOME"));
				bean.setSiglaFunzionalita(rs.getString("SIGLA_FUNZIONALITA"));
				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSiglaSottosistema(rs.getString("SIGLA_SOTTOSISTEMA"));
				bean.setTipiElaborazione(rs.getString("TIPI_ELABORAZIONE"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setSiglaFunzionalita("FUNZ_" + rs.getInt("ID_EDMA"));

				funzionalita.add(bean);
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

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return funzionalita;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmFunzionalita funzionalita) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmFunzionalita)
					.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
							.eq(funzionalita.getDmalmFunzionalitaPk()))
					.where(dmalmFunzionalita.dmalmModuloFk01.eq(funzionalita
							.getDmalmModuloFk01()))
					.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
							.lower().eq(
									funzionalita.getIdFunzionalita()
											.toLowerCase())
							: dmalmFunzionalita.idFunzionalita.isNull())
					.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
							.lower().eq(
									funzionalita.getSiglaProdotto()
											.toLowerCase())
							: dmalmFunzionalita.siglaProdotto.isNull())
					.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
							.lower()
							.eq(funzionalita.getSiglaModulo().toLowerCase())
							: dmalmFunzionalita.siglaModulo.isNull())
					.where(dmalmFunzionalita.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmFunzionalita)
									.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
											.eq(funzionalita
													.getDmalmFunzionalitaPk()))
									.where(dmalmFunzionalita.dmalmModuloFk01
											.eq(funzionalita
													.getDmalmModuloFk01()))
									.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
											.lower()
											.eq(funzionalita
													.getIdFunzionalita()
													.toLowerCase())
											: dmalmFunzionalita.idFunzionalita
													.isNull())
									.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
											.lower().eq(
													funzionalita
															.getSiglaProdotto()
															.toLowerCase())
											: dmalmFunzionalita.siglaProdotto
													.isNull())
									.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
											.lower().eq(
													funzionalita
															.getSiglaModulo()
															.toLowerCase())
											: dmalmFunzionalita.siglaModulo
													.isNull())
									.list(dmalmFunzionalita.dtInserimentoRecord
											.max())))
					.set(dmalmFunzionalita.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void resetDataFineValidita9999(DmalmFunzionalita funzionalita)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmFunzionalita)
					.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
							.eq(funzionalita.getDmalmFunzionalitaPk()))
					.where(dmalmFunzionalita.dmalmModuloFk01.eq(funzionalita
							.getDmalmModuloFk01()))
					.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
							.lower().eq(
									funzionalita.getIdFunzionalita()
											.toLowerCase())
							: dmalmFunzionalita.idFunzionalita.isNull())
					.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
							.lower().eq(
									funzionalita.getSiglaProdotto()
											.toLowerCase())
							: dmalmFunzionalita.siglaProdotto.isNull())
					.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
							.lower()
							.eq(funzionalita.getSiglaModulo().toLowerCase())
							: dmalmFunzionalita.siglaModulo.isNull())
					.where(dmalmFunzionalita.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmFunzionalita)
									.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
											.eq(funzionalita
													.getDmalmFunzionalitaPk()))
									.where(dmalmFunzionalita.dmalmModuloFk01
											.eq(funzionalita
													.getDmalmModuloFk01()))
									.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
											.lower()
											.eq(funzionalita
													.getIdFunzionalita()
													.toLowerCase())
											: dmalmFunzionalita.idFunzionalita
													.isNull())
									.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
											.lower().eq(
													funzionalita
															.getSiglaProdotto()
															.toLowerCase())
											: dmalmFunzionalita.siglaProdotto
													.isNull())
									.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
											.lower().eq(
													funzionalita
															.getSiglaModulo()
															.toLowerCase())
											: dmalmFunzionalita.siglaModulo
													.isNull())
									.list(dmalmFunzionalita.dtInserimentoRecord
											.max())))
					.set(dmalmFunzionalita.dtFineValidita,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateFunzionalita(DmalmFunzionalita funzionalita)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmFunzionalita)

					.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
							.eq(funzionalita.getDmalmFunzionalitaPk()))
					.where(dmalmFunzionalita.dmalmModuloFk01.eq(funzionalita
							.getDmalmModuloFk01()))
					.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
							.lower().eq(
									funzionalita.getIdFunzionalita()
											.toLowerCase())
							: dmalmFunzionalita.idFunzionalita.isNull())
					.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
							.lower().eq(
									funzionalita.getSiglaProdotto()
											.toLowerCase())
							: dmalmFunzionalita.siglaProdotto.isNull())
					.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
							.lower()
							.eq(funzionalita.getSiglaModulo().toLowerCase())
							: dmalmFunzionalita.siglaModulo.isNull())
					.where(dmalmFunzionalita.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmFunzionalita)
									.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
											.eq(funzionalita
													.getDmalmFunzionalitaPk()))
									.where(dmalmFunzionalita.dmalmModuloFk01
											.eq(funzionalita
													.getDmalmModuloFk01()))
									.where(funzionalita.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
											.lower()
											.eq(funzionalita
													.getIdFunzionalita()
													.toLowerCase())
											: dmalmFunzionalita.idFunzionalita
													.isNull())
									.where(funzionalita.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
											.lower().eq(
													funzionalita
															.getSiglaProdotto()
															.toLowerCase())
											: dmalmFunzionalita.siglaProdotto
													.isNull())
									.where(funzionalita.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
											.lower().eq(
													funzionalita
															.getSiglaModulo()
															.toLowerCase())
											: dmalmFunzionalita.siglaModulo
													.isNull())
									.list(dmalmFunzionalita.dtInserimentoRecord
											.max())))
					.set(dmalmFunzionalita.dmalmModuloFk01,
							funzionalita.getDmalmModuloFk01())
					.set(dmalmFunzionalita.idFunzionalita,
							funzionalita.getIdFunzionalita())
					.set(dmalmFunzionalita.tipoOggetto,
							funzionalita.getTipoOggetto())
					.set(dmalmFunzionalita.siglaProdotto,
							funzionalita.getSiglaProdotto())
					.set(dmalmFunzionalita.siglaModulo,
							funzionalita.getSiglaModulo())
					.set(dmalmFunzionalita.siglaSottosistema,
							funzionalita.getSiglaSottosistema())
					.set(dmalmFunzionalita.dsFunzionalita,
							funzionalita.getDsFunzionalita())
					.set(dmalmFunzionalita.categoria,
							funzionalita.getCategoria())
					.set(dmalmFunzionalita.linguaggi,
							funzionalita.getLinguaggi())
					.set(dmalmFunzionalita.tipiElaborazione,
							funzionalita.getTipiElaborazione())
					.set(dmalmFunzionalita.dtAnnullamento,
							funzionalita.getDtAnnullamento())

					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertFunzionalita(DmalmFunzionalita bean,
			Timestamp dataesecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmFunzionalita)
					.columns(dmalmFunzionalita.annullato,
							dmalmFunzionalita.categoria,
							dmalmFunzionalita.dmalmFunzionalitaPrimaryKey,
							dmalmFunzionalita.dsFunzionalita,
							dmalmFunzionalita.dtFineValidita,
							dmalmFunzionalita.dtInizioValidita,
							dmalmFunzionalita.dmalmModuloFk01,
							dmalmFunzionalita.idFunzionalita,
							dmalmFunzionalita.linguaggi,
							dmalmFunzionalita.nome,
							dmalmFunzionalita.siglaFunzionalita,
							dmalmFunzionalita.siglaModulo,
							dmalmFunzionalita.siglaProdotto,
							dmalmFunzionalita.siglaSottosistema,
							dmalmFunzionalita.tipiElaborazione,
							dmalmFunzionalita.tipoOggetto,
							dmalmFunzionalita.dtInserimentoRecord,
							dmalmFunzionalita.dtAnnullamento,
							dmalmFunzionalita.dmalmFunzionalitaSeq)
					.values(bean.getAnnullato(), bean.getCategoria(),
							bean.getDmalmFunzionalitaPk(),
							bean.getDsFunzionalita(),
							DateUtils.setDtFineValidita9999(),
							DateUtils.setDtInizioValidita1900(),
							bean.getDmalmModuloFk01(),
							bean.getIdFunzionalita(), bean.getLinguaggi(),
							bean.getNome(), bean.getSiglaFunzionalita(),
							bean.getSiglaModulo(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(),
							bean.getTipiElaborazione(), bean.getTipoOggetto(),
							dataesecuzione, bean.getDtAnnullamento(),
							bean.getDmalmFunzionalitaSeq()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error("insertFunzionalita() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertFunzionalitaUpdate(Timestamp dataEsecuzione,
			DmalmFunzionalita bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmFunzionalita)
					.columns(dmalmFunzionalita.annullato,
							dmalmFunzionalita.categoria,
							dmalmFunzionalita.dmalmFunzionalitaPrimaryKey,
							dmalmFunzionalita.dsFunzionalita,
							dmalmFunzionalita.dtFineValidita,
							dmalmFunzionalita.dtInizioValidita,
							dmalmFunzionalita.dmalmModuloFk01,
							dmalmFunzionalita.idFunzionalita,
							dmalmFunzionalita.linguaggi,
							dmalmFunzionalita.nome,
							dmalmFunzionalita.siglaFunzionalita,
							dmalmFunzionalita.siglaModulo,
							dmalmFunzionalita.siglaProdotto,
							dmalmFunzionalita.siglaSottosistema,
							dmalmFunzionalita.tipiElaborazione,
							dmalmFunzionalita.tipoOggetto,
							dmalmFunzionalita.dtInserimentoRecord,
							dmalmFunzionalita.dtAnnullamento,
							dmalmFunzionalita.dmalmFunzionalitaSeq)
					.values(bean.getAnnullato(), bean.getCategoria(),
							bean.getDmalmFunzionalitaPk(),
							bean.getDsFunzionalita(),
							DateUtils.setDtFineValidita9999(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							bean.getDmalmModuloFk01(),
							bean.getIdFunzionalita(), bean.getLinguaggi(),
							bean.getNome(), bean.getSiglaFunzionalita(),
							bean.getSiglaModulo(), bean.getSiglaProdotto(),
							bean.getSiglaSottosistema(),
							bean.getTipiElaborazione(), bean.getTipoOggetto(),
							dataEsecuzione, bean.getDtAnnullamento(),
							bean.getDmalmFunzionalitaSeq()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage(), e);
			logger.error("insertFunzionalitaUpdate() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getFunzionalita(DmalmFunzionalita bean)
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
					.from(dmalmFunzionalita)
					.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
							.eq(bean.getDmalmFunzionalitaPk()))
					.where(dmalmFunzionalita.dmalmModuloFk01.eq(bean
							.getDmalmModuloFk01()))
					.where(bean.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
							.lower().eq(bean.getIdFunzionalita().toLowerCase())
							: dmalmFunzionalita.idFunzionalita.isNull())
					.where(bean.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
							.lower().eq(bean.getSiglaProdotto().toLowerCase())
							: dmalmFunzionalita.siglaProdotto.isNull())
					.where(bean.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
							.lower().eq(bean.getSiglaModulo().toLowerCase())
							: dmalmFunzionalita.siglaModulo.isNull())
					.where(dmalmFunzionalita.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmFunzionalita)
									.where(dmalmFunzionalita.dmalmFunzionalitaPrimaryKey
											.eq(bean.getDmalmFunzionalitaPk()))
									.where(dmalmFunzionalita.dmalmModuloFk01
											.eq(bean.getDmalmModuloFk01()))
									.where(bean.getIdFunzionalita() != null ? dmalmFunzionalita.idFunzionalita
											.lower().eq(
													bean.getIdFunzionalita()
															.toLowerCase())
											: dmalmFunzionalita.idFunzionalita
													.isNull())
									.where(bean.getSiglaProdotto() != null ? dmalmFunzionalita.siglaProdotto
											.lower().eq(
													bean.getSiglaProdotto()
															.toLowerCase())
											: dmalmFunzionalita.siglaProdotto
													.isNull())
									.where(bean.getSiglaModulo() != null ? dmalmFunzionalita.siglaModulo
											.lower().eq(
													bean.getSiglaModulo()
															.toLowerCase())
											: dmalmFunzionalita.siglaModulo
													.isNull())
									.list(dmalmFunzionalita.dtInserimentoRecord
											.max())))
					.list(dmalmFunzionalita.all());

		}

		catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return funzionalita;
	}

}