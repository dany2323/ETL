package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmPeiOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class PeiOdsDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmPeiOds peiODS = QDmalmPeiOds.dmalmPeiOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, peiODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmPei> staging_peis,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmPei pei : staging_peis) {

				new SQLInsertClause(connection, dialect, peiODS)

						.columns(peiODS.cdPei, peiODS.codice,
								peiODS.descrizionePei, peiODS.dmalmPeiPk,
								peiODS.dmalmProjectFk02,
								peiODS.dmalmStatoWorkitemFk03,
								peiODS.dmalmStrutturaOrgFk01,
								peiODS.dmalmTempoFk04, peiODS.dsAutorePei,
								peiODS.dtCambioStatoPei,
								peiODS.dtCaricamentoPei, peiODS.dtCreazionePei,
								peiODS.dtModificaPei,
								peiODS.dtPrevistaComplReq,
								peiODS.dtPrevistaPassInEs,
								peiODS.dtRisoluzionePei, peiODS.dtScadenzaPei,
								peiODS.dtStoricizzazione, peiODS.idAutorePei,
								peiODS.idRepository,
								peiODS.motivoRisoluzionePei,
								peiODS.rankStatoPei, peiODS.titoloPei,
								peiODS.stgPk, peiODS.dmalmUserFk06, peiODS.uri)
						.values(pei.getCdPei(), pei.getCodice(),
								pei.getDescrizionePei(), pei.getDmalmPeiPk(),
								pei.getDmalmProjectFk02(),
								pei.getDmalmStatoWorkitemFk03(),
								pei.getDmalmStrutturaOrgFk01(),
								pei.getDmalmTempoFk04(), pei.getDsAutorePei(),
								pei.getDtCambioStatoPei(),
								pei.getDtCaricamentoPei(),
								pei.getDtCreazionePei(),
								pei.getDtModificaPei(),
								pei.getDtPrevistaComplReq(),
								pei.getDtPrevistaPassInEs(),
								pei.getDtRisoluzionePei(),
								pei.getDtScadenzaPei(), pei.getDtModificaPei(),
								pei.getIdAutorePei(), pei.getIdRepository(),
								pei.getMotivoRisoluzionePei(), new Double(1),
								pei.getTitoloPei(), pei.getStgPk(),
								pei.getDmalmUserFk06(), pei.getUri()).execute();

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

	public static List<DmalmPei> getAll() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmPei> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(peiODS).orderBy(peiODS.cdPei.asc())
					.orderBy(peiODS.dtModificaPei.asc())
					.list(Projections.bean(DmalmPei.class, peiODS.all()));

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}

}
