package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_AMBIENTETECNOLOGICO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmAmbienteTecnologico;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmAmbienteTecnologico;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class AmbienteTecnologicoDAO {
	private static Logger logger = Logger
			.getLogger(AmbienteTecnologicoDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmAmbienteTecnologico dmalmAmbiente = QDmalmAmbienteTecnologico.dmalmAmbienteTecnologico;

	private static List<Tuple> ambienti = new ArrayList<Tuple>();

	public static List<DmalmAmbienteTecnologico> getAllambienteTecnologico(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmAmbienteTecnologico bean = null;
		List<DmalmAmbienteTecnologico> ambienti = new ArrayList<DmalmAmbienteTecnologico>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_AMBIENTETECNOLOGICO);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmAmbienteTecnologico();

				bean.setDmalmAmbienteTecnologicoSeq(rs
						.getInt("DMALM_AMBIENTE_TECNOLOGICO_SEQ"));
				bean.setArchitettura(rs.getString("ARCHITETTURA"));
				bean.setDmalmAmbienteTecnologicoPk(rs.getInt("ID_EDMA"));
				bean.setDsAmbienteTecnologico(rs
						.getString("DS_AMBIENTE_TECNOLOGICO"));
				bean.setDtFineValidita(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setIdAmbienteTecnologico(rs
						.getString("ID_AMBIENTE_TECNOLOGICO"));
				bean.setDmalmProdottoFk01(rs.getInt("ID_EDMA_PADRE_PRODOTTO"));
				bean.setDmalmModuloFk02(rs.getInt("ID_EDMA_PADRE_MODULO"));
				bean.setInfrastruttura(rs.getString("INFRASTRUTTURA"));
				bean.setNome(rs.getString("NOME"));
				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSistemaOperativo(rs.getString("SISTEMA_OPERATIVO"));
				bean.setTipoLayer(rs.getString("TIPO_LAYER"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
				bean.setVersioneSo(rs.getString("VERSIONE_SO"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDtAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));

				ambienti.add(bean);
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

		return ambienti;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmAmbienteTecnologico ambiente) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmAmbiente)
					.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey
							.eq(ambiente.getDmalmAmbienteTecnologicoPk()))
					.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
							.lower().eq(
									ambiente.getIdAmbienteTecnologico()
											.toLowerCase())
							: dmalmAmbiente.idAmbienteTecnologico.isNull())
					.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
							.lower()
							.eq(ambiente.getSiglaModulo().toLowerCase())
							: dmalmAmbiente.siglaModulo.isNull())
					.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
							.lower().eq(
									ambiente.getSiglaProdotto().toLowerCase())
							: dmalmAmbiente.siglaProdotto.isNull())
					.where(dmalmAmbiente.dmalmProdottoFk01.eq(ambiente
							.getDmalmProdottoFk01()))
					.where(dmalmAmbiente.dmalmModuloFk02.eq(ambiente
							.getDmalmModuloFk02()))
					.where(dmalmAmbiente.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmAmbiente)
									.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey.eq(ambiente
											.getDmalmAmbienteTecnologicoPk()))
									.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
											.lower()
											.eq(ambiente
													.getIdAmbienteTecnologico()
													.toLowerCase())
											: dmalmAmbiente.idAmbienteTecnologico
													.isNull())
									.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
											.lower().eq(
													ambiente.getSiglaModulo()
															.toLowerCase())
											: dmalmAmbiente.siglaModulo
													.isNull())
									.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
											.lower().eq(
													ambiente.getSiglaProdotto()
															.toLowerCase())
											: dmalmAmbiente.siglaProdotto
													.isNull())
									.where(dmalmAmbiente.dmalmProdottoFk01
											.eq(ambiente.getDmalmProdottoFk01()))
									.where(dmalmAmbiente.dmalmModuloFk02
											.eq(ambiente.getDmalmModuloFk02()))
									.list(dmalmAmbiente.dtInserimentoRecord
											.max())))
					.set(dmalmAmbiente.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void resetDataFineValidita9999(
			DmalmAmbienteTecnologico ambiente) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmAmbiente)
					.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey
							.eq(ambiente.getDmalmAmbienteTecnologicoPk()))
					.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
							.lower().eq(
									ambiente.getIdAmbienteTecnologico()
											.toLowerCase())
							: dmalmAmbiente.idAmbienteTecnologico.isNull())
					.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
							.lower()
							.eq(ambiente.getSiglaModulo().toLowerCase())
							: dmalmAmbiente.siglaModulo.isNull())
					.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
							.lower().eq(
									ambiente.getSiglaProdotto().toLowerCase())
							: dmalmAmbiente.siglaProdotto.isNull())
					.where(dmalmAmbiente.dmalmProdottoFk01.eq(ambiente
							.getDmalmProdottoFk01()))
					.where(dmalmAmbiente.dmalmModuloFk02.eq(ambiente
							.getDmalmModuloFk02()))
					.where(dmalmAmbiente.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmAmbiente)
									.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey.eq(ambiente
											.getDmalmAmbienteTecnologicoPk()))
									.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
											.lower()
											.eq(ambiente
													.getIdAmbienteTecnologico()
													.toLowerCase())
											: dmalmAmbiente.idAmbienteTecnologico
													.isNull())
									.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
											.lower().eq(
													ambiente.getSiglaModulo()
															.toLowerCase())
											: dmalmAmbiente.siglaModulo
													.isNull())
									.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
											.lower().eq(
													ambiente.getSiglaProdotto()
															.toLowerCase())
											: dmalmAmbiente.siglaProdotto
													.isNull())
									.where(dmalmAmbiente.dmalmProdottoFk01
											.eq(ambiente.getDmalmProdottoFk01()))
									.where(dmalmAmbiente.dmalmModuloFk02
											.eq(ambiente.getDmalmModuloFk02()))
									.list(dmalmAmbiente.dtInserimentoRecord
											.max())))
					.set(dmalmAmbiente.dtFineValidita,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateAmbienteTecnologico(
			DmalmAmbienteTecnologico ambiente) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmAmbiente)

					.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey
							.eq(ambiente.getDmalmAmbienteTecnologicoPk()))
					.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
							.lower().eq(
									ambiente.getIdAmbienteTecnologico()
											.toLowerCase())
							: dmalmAmbiente.idAmbienteTecnologico.isNull())
					.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
							.lower()
							.eq(ambiente.getSiglaModulo().toLowerCase())
							: dmalmAmbiente.siglaModulo.isNull())
					.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
							.lower().eq(
									ambiente.getSiglaProdotto().toLowerCase())
							: dmalmAmbiente.siglaProdotto.isNull())
					.where(dmalmAmbiente.dmalmProdottoFk01.eq(ambiente
							.getDmalmProdottoFk01()))
					.where(dmalmAmbiente.dmalmModuloFk02.eq(ambiente
							.getDmalmModuloFk02()))
					.where(dmalmAmbiente.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmAmbiente)
									.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey.eq(ambiente
											.getDmalmAmbienteTecnologicoPk()))
									.where(ambiente.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
											.lower()
											.eq(ambiente
													.getIdAmbienteTecnologico()
													.toLowerCase())
											: dmalmAmbiente.idAmbienteTecnologico
													.isNull())
									.where(ambiente.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
											.lower().eq(
													ambiente.getSiglaModulo()
															.toLowerCase())
											: dmalmAmbiente.siglaModulo
													.isNull())
									.where(ambiente.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
											.lower().eq(
													ambiente.getSiglaProdotto()
															.toLowerCase())
											: dmalmAmbiente.siglaProdotto
													.isNull())
									.where(dmalmAmbiente.dmalmProdottoFk01
											.eq(ambiente.getDmalmProdottoFk01()))
									.where(dmalmAmbiente.dmalmModuloFk02
											.eq(ambiente.getDmalmModuloFk02()))
									.list(dmalmAmbiente.dtInserimentoRecord
											.max())))
					.set(dmalmAmbiente.dmalmProdottoFk01,
							ambiente.getDmalmProdottoFk01())
					.set(dmalmAmbiente.dmalmModuloFk02,
							ambiente.getDmalmModuloFk02())
					.set(dmalmAmbiente.idAmbienteTecnologico,
							ambiente.getIdAmbienteTecnologico())
					.set(dmalmAmbiente.tipoOggetto, ambiente.getTipoOggetto())
					.set(dmalmAmbiente.siglaModulo, ambiente.getSiglaModulo())
					.set(dmalmAmbiente.siglaProdotto,
							ambiente.getSiglaProdotto())
					.set(dmalmAmbiente.dsAmbienteTecnologico,
							ambiente.getDsAmbienteTecnologico())
					.set(dmalmAmbiente.versioneSo, ambiente.getVersioneSo())
					.set(dmalmAmbiente.architettura, ambiente.getArchitettura())
					.set(dmalmAmbiente.infrastruttura,
							ambiente.getInfrastruttura())
					.set(dmalmAmbiente.sistemaOperativo,
							ambiente.getSistemaOperativo())
					.set(dmalmAmbiente.tipoLayer, ambiente.getTipoLayer())

					.execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertAmbienteTecnologico(DmalmAmbienteTecnologico bean,
			Timestamp dataesecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmAmbiente)
					.columns(dmalmAmbiente.architettura,
							dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey,
							dmalmAmbiente.dsAmbienteTecnologico,
							dmalmAmbiente.dtInizioValidita,
							dmalmAmbiente.dtFineValidita,
							dmalmAmbiente.idAmbienteTecnologico,
							dmalmAmbiente.dmalmProdottoFk01,
							dmalmAmbiente.dmalmModuloFk02,
							dmalmAmbiente.infrastruttura, dmalmAmbiente.nome,
							dmalmAmbiente.siglaModulo,
							dmalmAmbiente.siglaProdotto,
							dmalmAmbiente.sistemaOperativo,
							dmalmAmbiente.tipoLayer, dmalmAmbiente.tipoOggetto,
							dmalmAmbiente.versioneSo,
							dmalmAmbiente.dtInserimentoRecord,
							dmalmAmbiente.dtAnnullamento,
							dmalmAmbiente.annullato,
							dmalmAmbiente.dmalmAmbienteTecnologicoSeq

					)
					.values(bean.getArchitettura(),
							bean.getDmalmAmbienteTecnologicoPk(),
							bean.getDsAmbienteTecnologico(),
							DateUtils.setDtInizioValidita1900(),
							bean.getDtFineValidita(),
							bean.getIdAmbienteTecnologico(),
							bean.getDmalmProdottoFk01(),
							bean.getDmalmModuloFk02(),
							bean.getInfrastruttura(), bean.getNome(),
							bean.getSiglaModulo(), bean.getSiglaProdotto(),
							bean.getSistemaOperativo(), bean.getTipoLayer(),
							bean.getTipoOggetto(), bean.getVersioneSo(),
							dataesecuzione, bean.getDtAnnullamento(),
							bean.getAnnullato(),
							bean.getDmalmAmbienteTecnologicoSeq()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertAmbienteTecnologicoUpdate(
			Timestamp dataEsecuzione, DmalmAmbienteTecnologico bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmAmbiente)
					.columns(dmalmAmbiente.architettura,
							dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey,
							dmalmAmbiente.dsAmbienteTecnologico,
							dmalmAmbiente.dtInizioValidita,
							dmalmAmbiente.dtFineValidita,
							dmalmAmbiente.idAmbienteTecnologico,
							dmalmAmbiente.dmalmProdottoFk01,
							dmalmAmbiente.dmalmModuloFk02,
							dmalmAmbiente.infrastruttura, dmalmAmbiente.nome,
							dmalmAmbiente.siglaModulo,
							dmalmAmbiente.siglaProdotto,
							dmalmAmbiente.sistemaOperativo,
							dmalmAmbiente.tipoLayer, dmalmAmbiente.tipoOggetto,
							dmalmAmbiente.versioneSo,
							dmalmAmbiente.dtInserimentoRecord,
							dmalmAmbiente.dtAnnullamento,
							dmalmAmbiente.annullato,
							dmalmAmbiente.dmalmAmbienteTecnologicoSeq)
					.values(bean.getArchitettura(),
							bean.getDmalmAmbienteTecnologicoPk(),
							bean.getDsAmbienteTecnologico(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.setDtFineValidita9999(),
							bean.getIdAmbienteTecnologico(),
							bean.getDmalmProdottoFk01(),
							bean.getDmalmModuloFk02(),
							bean.getInfrastruttura(), bean.getNome(),
							bean.getSiglaModulo(), bean.getSiglaProdotto(),
							bean.getSistemaOperativo(), bean.getTipoLayer(),
							bean.getTipoOggetto(), bean.getVersioneSo(),
							dataEsecuzione, bean.getDtAnnullamento(),
							bean.getAnnullato(),
							bean.getDmalmAmbienteTecnologicoSeq()

					).execute();

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

	public static List<Tuple> getAmbienteTecnologico(
			DmalmAmbienteTecnologico bean) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			ambienti = query
					.from(dmalmAmbiente)
					.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey
							.eq(bean.getDmalmAmbienteTecnologicoPk()))
					.where(bean.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
							.lower().eq(
									bean.getIdAmbienteTecnologico()
											.toLowerCase())
							: dmalmAmbiente.idAmbienteTecnologico.isNull())
					.where(bean.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
							.lower().eq(bean.getSiglaModulo().toLowerCase())
							: dmalmAmbiente.siglaModulo.isNull())
					.where(bean.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
							.lower().eq(bean.getSiglaProdotto().toLowerCase())
							: dmalmAmbiente.siglaProdotto.isNull())
					.where(dmalmAmbiente.dmalmProdottoFk01.eq(bean
							.getDmalmProdottoFk01()))
					.where(dmalmAmbiente.dmalmModuloFk02.eq(bean
							.getDmalmModuloFk02()))
					.where(dmalmAmbiente.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmAmbiente)
									.where(dmalmAmbiente.dmalmAmbienteTecnologicoPrimaryKey.eq(bean
											.getDmalmAmbienteTecnologicoPk()))
									.where(bean.getIdAmbienteTecnologico() != null ? dmalmAmbiente.idAmbienteTecnologico
											.lower()
											.eq(bean.getIdAmbienteTecnologico()
													.toLowerCase())
											: dmalmAmbiente.idAmbienteTecnologico
													.isNull())
									.where(bean.getSiglaModulo() != null ? dmalmAmbiente.siglaModulo
											.lower().eq(
													bean.getSiglaModulo()
															.toLowerCase())
											: dmalmAmbiente.siglaModulo
													.isNull())
									.where(bean.getSiglaProdotto() != null ? dmalmAmbiente.siglaProdotto
											.lower().eq(
													bean.getSiglaProdotto()
															.toLowerCase())
											: dmalmAmbiente.siglaProdotto
													.isNull())
									.where(dmalmAmbiente.dmalmProdottoFk01
											.eq(bean.getDmalmProdottoFk01()))
									.where(dmalmAmbiente.dmalmModuloFk02
											.eq(bean.getDmalmModuloFk02()))
									.list(dmalmAmbiente.dtInserimentoRecord
											.max()))).list(dmalmAmbiente.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return ambienti;
	}

}