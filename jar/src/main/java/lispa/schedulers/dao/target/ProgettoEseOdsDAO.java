package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoEseOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgettoEseOdsDAO {
	private static Logger logger = Logger.getLogger(ProgettoEseOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoEseOds progettoEseOds = QDmalmProgettoEseOds.dmalmProgettoEseOds;

	public static void delete() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoEseOds).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmProgettoEse> staging_progettoEse,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoEse progetto : staging_progettoEse) {
				if(listPk.contains(progetto.getDmalmProgettoEsePk()))
					logger.info("Trovata DmalmProgettoEsePk DUPLICATA!!!"+progetto.getDmalmProgettoEsePk());
				else{
					listPk.add(progetto.getDmalmProgettoEsePk());
					new SQLInsertClause(connection, dialect, progettoEseOds)
							.columns(progettoEseOds.cdProgettoEse,
									progettoEseOds.descrizioneProgettoEse,
									progettoEseOds.dmalmProgettoEsePk,
									progettoEseOds.dmalmProjectFk02,
									progettoEseOds.dmalmStatoWorkitemFk03,
									progettoEseOds.dmalmStrutturaOrgFk01,
									progettoEseOds.dmalmTempoFk04,
									progettoEseOds.dsAutoreProgettoEse,
									progettoEseOds.dtCambioStatoProgettoEse,
									progettoEseOds.dtCaricamentoProgettoEse,
									progettoEseOds.dtCreazioneProgettoEse,
									progettoEseOds.dtModificaProgettoEse,
									progettoEseOds.dtRisoluzioneProgettoEse,
									progettoEseOds.dtScadenzaProgettoEse,
									progettoEseOds.dtStoricizzazione,
									progettoEseOds.idAutoreProgettoEse,
									progettoEseOds.idRepository,
									progettoEseOds.motivoRisoluzioneProgEse,
									progettoEseOds.rankStatoProgettoEse,
									progettoEseOds.titoloProgettoEse,
									progettoEseOds.cfCodice, progettoEseOds.stgPk,
									progettoEseOds.cfDtUltimaSottomissione,
									progettoEseOds.dmalmUserFk06,
									progettoEseOds.uri,
									progettoEseOds.severity, progettoEseOds.priority,
									progettoEseOds.tagAlm, progettoEseOds.tsTagAlm)
							.values(progetto.getCdProgettoEse(),
									progetto.getDescrizioneProgettoEse(),
									progetto.getDmalmProgettoEsePk(),
									progetto.getDmalmProjectFk02(),
									progetto.getDmalmStatoWorkitemFk03(),
									progetto.getDmalmStrutturaOrgFk01(),
									progetto.getDmalmTempoFk04(),
									progetto.getDsAutoreProgettoEse(),
									progetto.getDtCambioStatoProgettoEse(),
									progetto.getDtCaricamentoProgettoEse(),
									progetto.getDtCreazioneProgettoEse(),
									progetto.getDtModificaProgettoEse(),
									progetto.getDtRisoluzioneProgettoEse(),
									progetto.getDtScadenzaProgettoEse(),
									progetto.getDtModificaProgettoEse(),
									progetto.getIdAutoreProgettoEse(),
									progetto.getIdRepository(),
									progetto.getMotivoRisoluzioneProgEse(),
									new Double(1), progetto.getTitoloProgettoEse(),
									progetto.getCfCodice(), progetto.getStgPk(),
									progetto.getCfDtUltimaSottomissione(),
									progetto.getDmalmUserFk06(), progetto.getUri(),
									progetto.getSeverity(), progetto.getPriority(),
									progetto.getTagAlm(), progetto.getTsTagAlm())
							.execute();
				}
				connection.commit();

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmProgettoEse> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmProgettoEse> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoEseOds)
					.orderBy(progettoEseOds.cdProgettoEse.asc())
					.orderBy(progettoEseOds.dtModificaProgettoEse.asc())
					.list(Projections.bean(DmalmProgettoEse.class,
							progettoEseOds.all()));

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
