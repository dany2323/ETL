package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmClassificatoreOds;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ClassificatoreOdsDAO {

	ClassificatoreOdsDAO(){}
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

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmClassificatore classificatore : stg) {

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
								ods.severity, ods.priority)
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
								classificatore.getPriority()).execute();

			}

			connection.commit();

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

		List<Tuple> list = null;
		List<DmalmClassificatore> resultListEl = new LinkedList<DmalmClassificatore>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(ods)
					.orderBy(ods.cd_classificatore.asc())
					.orderBy(ods.dtModificaClassif.asc())
					.list(ods.all());
			
			for (Tuple result : list) {
				DmalmClassificatore resultEl = new DmalmClassificatore();
				resultEl.setCd_classificatore(result.get(ods.cd_classificatore));
				resultEl.setCf_ambito(result.get(ods.cf_ambito));
				resultEl.setCf_area(result.get(ods.cf_area));
				resultEl.setCf_riferimenti(result.get(ods.cf_riferimenti));
				resultEl.setCf_scheda_servizio(result.get(ods.cf_scheda_servizio));
				resultEl.setDmalmClassificatorePk(result.get(ods.dmalmClassificatorePk));
				resultEl.setDmalmProjectFk02(result.get(ods.dmalmProjectFk02));
				resultEl.setDmalmStatoWorkitemFk03(result.get(ods.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(ods.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(ods.dmalmTempoFk04));
				resultEl.setDmalmUserFk06(result.get(ods.dmalmUserFk06));
				resultEl.setDsAutoreClassificatore(result.get(ods.dsAutoreClassificatore));
				resultEl.setDtCambioStatoClassif(result.get(ods.dtCambioStatoClassif));
				resultEl.setDtCaricamentoClassif(result.get(ods.dtCaricamentoClassif));
				resultEl.setDtCreazioneClassif(result.get(ods.dtCreazioneClassif));
				resultEl.setDtModificaClassif(result.get(ods.dtModificaClassif));
				resultEl.setDtRisoluzioneClassif(result.get(ods.dtRisoluzioneClassif));
				resultEl.setDtScadenzaProgSvil(result.get(ods.dtScadenzaProgSvil));
				resultEl.setDtStoricizzazione(result.get(ods.dtStoricizzazione));
				resultEl.setIdAutoreClassificatore(result.get(ods.idAutoreClassificatore));
				resultEl.setIdRepository(result.get(ods.idRepository));
				resultEl.setRankStatoClassifMese(result.get(ods.rankStatoClassifMese));
				resultEl.setRankStatoClassificatore(result.get(ods.rankStatoClassificatore));
				resultEl.setStgPk(result.get(ods.stgPk));
				resultEl.setTitoloClassificatore(result.get(ods.titoloClassificatore));
				resultEl.setUriClassficatore(result.get(ods.uriClassficatore));
				resultEl.setRmResponsabiliProgetto(result.get(ods.rmResponsabiliProgetto));
				resultEl.setProgettoInDeroga(result.get(ods.progettoInDeroga));
				resultEl.setAssigneeProgettoItInDeroga(result.get(ods.assigneeProgettoItInDeroga));
				resultEl.setLocationSorgenti(result.get(ods.locationSorgenti));
				resultEl.setType(result.get(ods.type));
				resultEl.setCodiceServizi(result.get(ods.codiceServizi));
				resultEl.setSeverity(result.get(ods.severity));
				resultEl.setPriority(result.get(ods.priority));
				resultListEl.add(resultEl);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;

	}

}
