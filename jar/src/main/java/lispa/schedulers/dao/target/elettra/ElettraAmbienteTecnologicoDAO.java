package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologico;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologico;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ElettraAmbienteTecnologicoDAO {
	private static Logger logger = Logger
			.getLogger(ElettraAmbienteTecnologicoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElAmbienteTecnologico qDmalmElAmbienteTecnologico = QDmalmElAmbienteTecnologico.qDmalmElAmbienteTecnologico;

	public static List<DmalmElAmbienteTecnologico> getAllAmbienteTecnologico(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmElAmbienteTecnologico bean = null;
		List<DmalmElAmbienteTecnologico> ambienti = new ArrayList<DmalmElAmbienteTecnologico>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ELETTRAAMBIENTETECNOLOGICO);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmElAmbienteTecnologico();

				bean.setAmbienteTecnologicoPk(rs
						.getInt("DMALM_STG_AMB_TECNOLOGICO_PK"));
				bean.setIdAmbienteTecnologicoEdma(rs
						.getString("ID_AMB_TECNOLOGICO_EDMA"));
				bean.setIdAmbienteTecnologicoEdmaPadre(rs
						.getString("ID_AMB_TECNOLOGICO_EDMA_PADRE"));
				bean.setIdAmbienteTecnologico(rs
						.getString("ID_AMB_TECNOLOGICO"));
				bean.setSiglaProdotto(rs.getString("SIGLA_PRODOTTO"));
				bean.setSiglaModulo(rs.getString("SIGLA_MODULO"));
				bean.setNome(rs.getString("NOME"));
				bean.setArchitettura(rs.getString("ARCHITETTURA"));
				bean.setInfrastruttura(rs.getString("INFRASTRUTTURA"));
				bean.setSistemaOperativo(rs.getString("SISTEMA_OPERATIVO"));
				bean.setTipoLayer(rs.getString("TIPO_LAYER"));
				bean.setVersioneSistemaOperativo(rs.getString("VERSIONE_SO"));
				bean.setDescrizioneAmbienteTecnologico(rs
						.getString("DS_AMBIENTE_TECNOLOGICO"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDataAnnullamento(rs.getTimestamp("DT_ANNULLAMENTO"));
				bean.setProdottoFk(rs.getInt("PRODOTTO_FK"));
				bean.setModuloFk(rs.getInt("MODULO_FK"));

				ambienti.add(bean);
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

		return ambienti;
	}

	public static List<Tuple> getAmbienteTecnologico(
			DmalmElAmbienteTecnologico bean) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> ambienti = new ArrayList<Tuple>();
		
		if(bean.getIdAmbienteTecnologico() != null) {

			try {
				cm = ConnectionManager.getInstance();
				connection = cm.getConnectionOracle();
	
				SQLQuery query = new SQLQuery(connection, dialect);
	
				ambienti = query
						.from(qDmalmElAmbienteTecnologico)
						.where(qDmalmElAmbienteTecnologico.idAmbienteTecnologico
								.eq(bean.getIdAmbienteTecnologico()))
						.where(qDmalmElAmbienteTecnologico.dataFineValidita
								.in(new SQLSubQuery()
										.from(qDmalmElAmbienteTecnologico)
										.where(qDmalmElAmbienteTecnologico.idAmbienteTecnologico
												.eq(bean.getIdAmbienteTecnologico()))
										.list(qDmalmElAmbienteTecnologico.dataFineValidita
												.max())))
						.list(qDmalmElAmbienteTecnologico.all());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new DAOException(e);
			} finally {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			}
		}

		return ambienti;
	}

	public static void insertAmbienteTecnologico(DmalmElAmbienteTecnologico bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					qDmalmElAmbienteTecnologico)
					.columns(
							qDmalmElAmbienteTecnologico.ambienteTecnologicoPk,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologicoEdma,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologicoEdmaPadre,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologico,
							qDmalmElAmbienteTecnologico.siglaProdotto,
							qDmalmElAmbienteTecnologico.siglaModulo,
							qDmalmElAmbienteTecnologico.nome,
							qDmalmElAmbienteTecnologico.architettura,
							qDmalmElAmbienteTecnologico.infrastruttura,
							qDmalmElAmbienteTecnologico.sistemaOperativo,
							qDmalmElAmbienteTecnologico.tipoLayer,
							qDmalmElAmbienteTecnologico.versioneSistemaOperativo,
							qDmalmElAmbienteTecnologico.descrizioneAmbienteTecnologico,
							qDmalmElAmbienteTecnologico.dataCaricamento,
							qDmalmElAmbienteTecnologico.annullato,
							qDmalmElAmbienteTecnologico.dataAnnullamento,
							qDmalmElAmbienteTecnologico.prodottoFk,
							qDmalmElAmbienteTecnologico.moduloFk,
							qDmalmElAmbienteTecnologico.dataInizioValidita,
							qDmalmElAmbienteTecnologico.dataFineValidita)
					.values(bean.getAmbienteTecnologicoPk(),
							bean.getIdAmbienteTecnologicoEdma(),
							bean.getIdAmbienteTecnologicoEdmaPadre(),
							bean.getIdAmbienteTecnologico(),
							bean.getSiglaProdotto(), bean.getSiglaModulo(),
							bean.getNome(), bean.getArchitettura(),
							bean.getInfrastruttura(),
							bean.getSistemaOperativo(), bean.getTipoLayer(),
							bean.getVersioneSistemaOperativo(),
							bean.getDescrizioneAmbienteTecnologico(),
							bean.getDataCaricamento(), bean.getAnnullato(),
							bean.getDataAnnullamento(), bean.getProdottoFk(),
							bean.getModuloFk(),
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
			Integer ambienteTecnologicoPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					qDmalmElAmbienteTecnologico)
					.where(qDmalmElAmbienteTecnologico.ambienteTecnologicoPk
							.eq(ambienteTecnologicoPk))
					.set(qDmalmElAmbienteTecnologico.dataFineValidita,
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

	public static void insertAmbienteTecnologicoUpdate(
			Timestamp dataEsecuzione, DmalmElAmbienteTecnologico bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					qDmalmElAmbienteTecnologico)
					.columns(
							qDmalmElAmbienteTecnologico.ambienteTecnologicoPk,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologicoEdma,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologicoEdmaPadre,
							qDmalmElAmbienteTecnologico.idAmbienteTecnologico,
							qDmalmElAmbienteTecnologico.siglaProdotto,
							qDmalmElAmbienteTecnologico.siglaModulo,
							qDmalmElAmbienteTecnologico.nome,
							qDmalmElAmbienteTecnologico.architettura,
							qDmalmElAmbienteTecnologico.infrastruttura,
							qDmalmElAmbienteTecnologico.sistemaOperativo,
							qDmalmElAmbienteTecnologico.tipoLayer,
							qDmalmElAmbienteTecnologico.versioneSistemaOperativo,
							qDmalmElAmbienteTecnologico.descrizioneAmbienteTecnologico,
							qDmalmElAmbienteTecnologico.dataCaricamento,
							qDmalmElAmbienteTecnologico.annullato,
							qDmalmElAmbienteTecnologico.dataAnnullamento,
							qDmalmElAmbienteTecnologico.prodottoFk,
							qDmalmElAmbienteTecnologico.moduloFk,
							qDmalmElAmbienteTecnologico.dataInizioValidita,
							qDmalmElAmbienteTecnologico.dataFineValidita)
					.values(bean.getAmbienteTecnologicoPk(),
							bean.getIdAmbienteTecnologicoEdma(),
							bean.getIdAmbienteTecnologicoEdmaPadre(),
							bean.getIdAmbienteTecnologico(),
							bean.getSiglaProdotto(), bean.getSiglaModulo(),
							bean.getNome(), bean.getArchitettura(),
							bean.getInfrastruttura(),
							bean.getSistemaOperativo(), bean.getTipoLayer(),
							bean.getVersioneSistemaOperativo(),
							bean.getDescrizioneAmbienteTecnologico(),
							bean.getDataCaricamento(), bean.getAnnullato(),
							bean.getDataAnnullamento(), bean.getProdottoFk(),
							bean.getModuloFk(), dataEsecuzione,
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

	public static void updateAmbienteTecnologico(Integer ambienteTecnologicoPk,
			DmalmElAmbienteTecnologico ambiente) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					qDmalmElAmbienteTecnologico)
					.where(qDmalmElAmbienteTecnologico.ambienteTecnologicoPk
							.eq(ambienteTecnologicoPk))
					.set(qDmalmElAmbienteTecnologico.siglaModulo,
							ambiente.getSiglaModulo())
					.set(qDmalmElAmbienteTecnologico.siglaProdotto,
							ambiente.getSiglaProdotto())
					.set(qDmalmElAmbienteTecnologico.architettura,
							ambiente.getArchitettura())
					.set(qDmalmElAmbienteTecnologico.infrastruttura,
							ambiente.getInfrastruttura())
					.set(qDmalmElAmbienteTecnologico.sistemaOperativo,
							ambiente.getSistemaOperativo())
					.set(qDmalmElAmbienteTecnologico.tipoLayer,
							ambiente.getTipoLayer())
					.set(qDmalmElAmbienteTecnologico.versioneSistemaOperativo,
							ambiente.getVersioneSistemaOperativo())
					.set(qDmalmElAmbienteTecnologico.descrizioneAmbienteTecnologico,
							ambiente.getDescrizioneAmbienteTecnologico())
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
}
