package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmClassificatoreOds;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ClassificatoreOdsDAO {

	ClassificatoreOdsDAO(){}
	
	private static Logger logger = Logger.getLogger(ClassificatoreOdsDAO.class);
	
	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static QDmalmClassificatoreOds ods = QDmalmClassificatoreOds.dmalmClassificatore;

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

	public static void insert(List<DmalmClassificatore> stg) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmClassificatore classificatore : stg) {
				if(listPk.contains(classificatore.getDmalmClassificatorePk()))
					logger.info("Trovata DmalmClassificatorePk DUPLICATA!!!"+classificatore.getDmalmClassificatorePk());
				else{
					listPk.add(classificatore.getDmalmClassificatorePk());
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
									ods.titoloClassificatore, ods.uriClassficatore,
									ods.rmResponsabiliProgetto,
									ods.progettoInDeroga,
									ods.assigneeProgettoItInDeroga,
									ods.locationSorgenti,
									ods.type,
									ods.codiceServizi,
									ods.severity, ods.priority,
									ods.tagAlm, ods.tsTagAlm)
							.values(classificatore.getCd_classificatore(),
									classificatore.getCf_ambito(), classificatore.getCf_area(),
									classificatore.getCf_riferimenti(),
									classificatore.getCf_scheda_servizio(),
									classificatore.getDmalmClassificatorePk(),
									classificatore.getDmalmProjectFk02(),
									classificatore.getDmalmStatoWorkitemFk03(),
									classificatore.getDmalmStrutturaOrgFk01(),
									classificatore.getDmalmTempoFk04(),
									classificatore.getDmalmUserFk06(),
									classificatore.getDsAutoreClassificatore(),
									classificatore.getDtCambioStatoClassif(),
									classificatore.getDtCaricamentoClassif(),
									classificatore.getDtCreazioneClassif(),
									classificatore.getDtModificaClassif(),
									classificatore.getDtRisoluzioneClassif(),
									classificatore.getDtScadenzaProgSvil(),
									classificatore.getDtModificaClassif(),
									classificatore.getIdAutoreClassificatore(),
									classificatore.getIdRepository(),
									Double.valueOf(1),
									classificatore.getRankStatoClassifMese(),
									classificatore.getStgPk(),
									classificatore.getTitoloClassificatore(),
									classificatore.getUriClassficatore(),
									classificatore.getRmResponsabiliProgetto(),
									classificatore.isProgettoInDeroga(),
									classificatore.getAssigneeProgettoItInDeroga(),
									classificatore.getLocationSorgenti(),
									classificatore.getType(),
									classificatore.getCodiceServizi(),
									classificatore.getSeverity(),
									classificatore.getPriority(),
									classificatore.getTagAlm(), classificatore.getTsTagAlm()).execute();
	
				}
	
				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmClassificatore> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmClassificatore> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(ods)
					.orderBy(ods.cd_classificatore.asc())
					.orderBy(ods.dtModificaClassif.asc())
					.list(Projections.bean(DmalmClassificatore.class,	ods.all()));

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
