package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoSviluppoSOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.path.StringPath;

public class ProgettoSviluppoSviluppoOdsDAO {

	private static Logger logger = Logger
			.getLogger(ProgettoSviluppoSviluppoOdsDAO.class);

	private static QDmalmProgettoSviluppoSOds progettoSvilSODS = QDmalmProgettoSviluppoSOds.dmalmProgettoSviluppoSOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoSvilSODS)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(
			List<DmalmProgettoSviluppoSvil> staging_progettoSviluppoSvil,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmProgettoSviluppoSvil progetto : staging_progettoSviluppoSvil) {

				new SQLInsertClause(connection, dialect, progettoSvilSODS)
						.columns(progettoSvilSODS.cdProgSvilS,
								progettoSvilSODS.codice,
								progettoSvilSODS.dataChiusuraProgSvilS,
								progettoSvilSODS.dataDisponibilitaEffettiva,
								progettoSvilSODS.dataDisponibilitaPianificata,
								progettoSvilSODS.dataInizio,
								progettoSvilSODS.dataInizioEff,
								progettoSvilSODS.descrizioneProgSvilS,
								progettoSvilSODS.dmalmAreaTematicaFk05,
								progettoSvilSODS.dmalmProgSvilSPk,
								progettoSvilSODS.dmalmProjectFk02,
								progettoSvilSODS.dmalmStatoWorkitemFk03,
								progettoSvilSODS.dmalmStrutturaOrgFk01,
								progettoSvilSODS.dmalmTempoFk04,
								progettoSvilSODS.dsAutoreProgSvilS,
								progettoSvilSODS.dtCambioStatoProgSvilS,
								progettoSvilSODS.dtCaricamentoProgSvilS,
								progettoSvilSODS.dtCreazioneProgSvilS,
								progettoSvilSODS.dtModificaProgSvilS,
								progettoSvilSODS.dtRisoluzioneProgSvilS,
								progettoSvilSODS.dtScadenzaProgSvilS,
								progettoSvilSODS.durataEffettivaProgSvilS,
								progettoSvilSODS.fornitura,
								progettoSvilSODS.idAutoreProgSvilS,
								progettoSvilSODS.idRepository,
								progettoSvilSODS.motivoRisoluzioneProgSvilS,
								progettoSvilSODS.numeroLinea,
								progettoSvilSODS.numeroTestata,
								progettoSvilSODS.stgPk,
								progettoSvilSODS.titoloProgSvilS,
								progettoSvilSODS.dmalmUserFk06,
								progettoSvilSODS.uri,
								progettoSvilSODS.severity,
								progettoSvilSODS.priority,
								progettoSvilSODS.tagAlm, progettoSvilSODS.tsTagAlm)
						.values(progetto.getCdProgSvilS(),
								progetto.getCodice(),
								progetto.getDataChiusuraProgSvilS(),
								progetto.getDataDisponibilitaEffettiva(),
								progetto.getDataDisponibilitaPianificata(),
								progetto.getDataInizio(),
								progetto.getDataInizioEff(),
								progetto.getDescrizioneProgSvilS(),
								progetto.getDmalmAreaTematicaFk05(),
								progetto.getDmalmProgSvilSPk(),
								progetto.getDmalmProjectFk02(),
								progetto.getDmalmStatoWorkitemFk03(),
								progetto.getDmalmStrutturaOrgFk01(),
								progetto.getDmalmTempoFk04(),
								progetto.getDsAutoreProgSvilS(),
								progetto.getDtCambioStatoProgSvilS(),
								progetto.getDtCaricamentoProgSvilS(),
								progetto.getDtCreazioneProgSvilS(),
								progetto.getDtModificaProgSvilS(),
								progetto.getDtRisoluzioneProgSvilS(),
								progetto.getDtScadenzaProgSvilS(),
								progetto.getDurataEffettivaProgSvilS(),
								progetto.getFornitura(),
								progetto.getIdAutoreProgSvilS(),
								progetto.getIdRepository(),
								progetto.getMotivoRisoluzioneProgSvilS(),
								progetto.getNumeroLinea(),
								progetto.getNumeroTestata(),
								progetto.getStgPk(),
								progetto.getTitoloProgSvilS(),
								progetto.getDmalmUserFk06(), progetto.getUri(),
								//DM_ALM-320
							    progetto.getSeverity(),
							    progetto.getPriority(),
							    progetto.getTagAlm(), progetto.getTsTagAlm())
						.execute();

			}

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmProgettoSviluppoSvil> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmProgettoSviluppoSvil> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoSvilSODS)
					.orderBy(progettoSvilSODS.cdProgSvilS.asc())
					.orderBy(progettoSvilSODS.dtModificaProgSvilS.asc())
					.list(Projections.bean(DmalmProgettoSviluppoSvil.class,
							progettoSvilSODS.all()));
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}

}
