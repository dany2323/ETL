package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmClassificatoreDemand;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmClassificatoreDemOds;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ClassificatoreDemOdsDAO {


	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmClassificatoreDemOds ods = QDmalmClassificatoreDemOds.dmalmClassificatoreDemand;

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, ods).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmClassificatoreDemand> stg,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmClassificatoreDemand cDem : stg) {

				new SQLInsertClause(connection, dialect, ods)

						.columns(ods.cd_classificatore, ods.cf_ambito,
								ods.cf_area, ods.cf_riferimenti,
								ods.cf_scheda_servizio,
								ods.dmalmClassificatorePk,
								ods.dmalmProjectFk02,
								ods.dmalmStatoWorkitemFk03,
								ods.dmalmStrutturaOrgFk01, ods.dmalmTempoFk04,
								ods.dmalmUserFk06, ods.dsAutoreClassificatore,
								ods.dtCambioStatoClassif,
								ods.dtCaricamentoClassif,
								ods.dtCreazioneClassif, ods.dtModificaClassif,
								ods.dtRisoluzioneClassif,
								ods.dtScadenzaProgSvil, ods.dtStoricizzazione,
								ods.idAutoreClassificatore, ods.idRepository,
								ods.rankStatoClassificatore,
								ods.rankStatoClassifMese, ods.stgPk,
								ods.titoloClassificatore, ods.uriClassficatore)
						.values(cDem.getCd_classificatore(),
								cDem.getCf_ambito(), cDem.getCf_area(),
								cDem.getCf_riferimenti(),
								cDem.getCf_scheda_servizio(),
								cDem.getDmalmClassificatorePk(),
								cDem.getDmalmProjectFk02(),
								cDem.getDmalmStatoWorkitemFk03(),
								cDem.getDmalmStrutturaOrgFk01(),
								cDem.getDmalmTempoFk04(),
								cDem.getDmalmUserFk06(),
								cDem.getDsAutoreClassificatore(),
								cDem.getDtCambioStatoClassif(),
								cDem.getDtCaricamentoClassif(),
								cDem.getDtCreazioneClassif(),
								cDem.getDtModificaClassif(),
								cDem.getDtRisoluzioneClassif(),
								cDem.getDtScadenzaProgSvil(),
								cDem.getDtModificaClassif(),
								cDem.getIdAutoreClassificatore(),
								cDem.getIdRepository(),
								new Double(1),
								cDem.getRankStatoClassifMese(),
								cDem.getStgPk(),
								cDem.getTitoloClassificatore(),
								cDem.getUriClassficatore()).execute();

			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmClassificatoreDemand> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmClassificatoreDemand> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(ods)
					.orderBy(ods.cd_classificatore.asc())
					.orderBy(ods.dtModificaClassif.asc())
					.list(Projections.bean(DmalmClassificatoreDemand.class,	ods.all()));

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
