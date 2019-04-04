package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_CLASS_DEMAND;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_CLASS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatore;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class ClassificatoreDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmClassificatore qClassificatore = QDmalmClassificatore.dmalmClassificatore;

	public static List<DmalmClassificatore> getAllClassDem(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psClassDem = null;
		ResultSet rsClassDem = null;

		DmalmClassificatore bean = null;
		List<DmalmClassificatore> classificatoriDemandList = new ArrayList<DmalmClassificatore>(
				QueryUtils
						.getCountByWIType(Workitem_Type.EnumWorkitemType.classificatore_demand));
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sqlClassDem = QueryManager.getInstance().getQuery(SQL_CLASS_DEMAND);

			psClassDem = connection.prepareStatement(sqlClassDem);

			psClassDem.setFetchSize(200);

			psClassDem.setTimestamp(1, dataEsecuzione);
			psClassDem.setTimestamp(2, dataEsecuzione);

			rsClassDem = psClassDem.executeQuery();

			logger.debug("Query Eseguita!");

			while (rsClassDem.next()) {
				// Elabora il risultato
				bean = new DmalmClassificatore();

				bean.setCd_classificatore(rsClassDem.getString("CD_CLASSIFICATORE"));
				bean.setDmalmClassificatorePk(rsClassDem
						.getInt("DMALM_CLASSIFICATORE_PK"));
				bean.setDmalmProjectFk02(rsClassDem.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rsClassDem
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rsClassDem.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreClassificatore(rsClassDem
						.getString("DS_AUTORE_CLASSIFICATORE"));
				bean.setDtCaricamentoClassif(dataEsecuzione);
				bean.setDtCreazioneClassif(rsClassDem
						.getTimestamp("DT_CREAZIONE_CLASSIF"));
				bean.setDtModificaClassif(rsClassDem
						.getTimestamp("DT_MODIFICA_CLASSIF"));
				bean.setDtRisoluzioneClassif(rsClassDem
						.getTimestamp("DT_RISOLUZIONE_CLASSIF"));
				bean.setDtScadenzaProgSvil(rsClassDem
						.getTimestamp("DT_SCADENZA_PROG_SVIL_D"));
				bean.setIdAutoreClassificatore(rsClassDem
						.getString("ID_AUTORE_CLASSIFICATORE"));
				bean.setIdRepository(rsClassDem.getString("ID_REPOSITORY"));
				bean.setStgPk(rsClassDem.getString("STG_PK"));
				bean.setTitoloClassificatore(rsClassDem
						.getString("TITOLO_CLASSIFICATORE"));
				bean.setUriClassficatore(rsClassDem.getString("URI_WI"));
				bean.setCf_ambito(rsClassDem.getString("AMBITO"));
				// bean.setCf_area(rs.getString("AREA"));
				bean.setCf_area(getCustomFieldInString(
						rsClassDem.getString("STG_PK"), rsClassDem.getString("ID_REPOSITORY"),
						"area"));
				// bean.setCf_riferimenti(rs.getString("RIFERIMENTO"));
				bean.setCf_riferimenti(getCustomFieldInString(
						rsClassDem.getString("STG_PK"), rsClassDem.getString("ID_REPOSITORY"),
						"riferimenti"));
				// bean.setCf_scheda_servizio(rs.getString("SCHEDA_SERVIZIO"));
				bean.setCf_scheda_servizio(getCustomFieldInString(
						rsClassDem.getString("STG_PK"), rsClassDem.getString("ID_REPOSITORY"),
						"scheda_servizio"));

				bean.setType(DmAlmConstants.WORKITEM_TYPE_CLASSIFICATORE_DEMAND);
				bean.setTagAlm(rsClassDem.getString("TAG_ALM"));
				bean.setTsTagAlm(rsClassDem.getTimestamp("TS_TAG_ALM"));
				classificatoriDemandList.add(bean);
			}

			if (rsClassDem != null) {
				rsClassDem.close();
			}
			if (psClassDem != null) {
				psClassDem.close();
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

		return classificatoriDemandList;
	}
	
	public static List<DmalmClassificatore> getAllClass(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psClass = null;
		ResultSet rsClass = null;

		DmalmClassificatore bean = null;		
		List<DmalmClassificatore> classificatoriList = new ArrayList<DmalmClassificatore>(
				QueryUtils
						.getCountByWIType(Workitem_Type.EnumWorkitemType.classificatore));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sqlClass = QueryManager.getInstance().getQuery(SQL_CLASS);

			psClass = connection.prepareStatement(sqlClass);

			psClass.setFetchSize(200);

			psClass.setTimestamp(1, dataEsecuzione);
			psClass.setTimestamp(2, dataEsecuzione);

			rsClass = psClass.executeQuery();

			logger.debug("Query Eseguita!");

			while (rsClass.next()) {
				// Elabora il risultato
				bean = new DmalmClassificatore();

				bean.setCd_classificatore(rsClass.getString("CD_CLASSIFICATORE"));
				bean.setDmalmClassificatorePk(rsClass
						.getInt("DMALM_CLASSIFICATORE_PK"));
				bean.setDmalmProjectFk02(rsClass.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rsClass
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rsClass.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreClassificatore(rsClass
						.getString("DS_AUTORE_CLASSIFICATORE"));
				bean.setDtCaricamentoClassif(dataEsecuzione);
				bean.setDtCreazioneClassif(rsClass
						.getTimestamp("DT_CREAZIONE_CLASSIF"));
				bean.setDtModificaClassif(rsClass
						.getTimestamp("DT_MODIFICA_CLASSIF"));
				bean.setDtRisoluzioneClassif(rsClass
						.getTimestamp("DT_RISOLUZIONE_CLASSIF"));
				bean.setDtScadenzaProgSvil(rsClass
						.getTimestamp("DT_SCADENZA_PROG_SVIL_D"));
				bean.setIdAutoreClassificatore(rsClass
						.getString("ID_AUTORE_CLASSIFICATORE"));
				bean.setIdRepository(rsClass.getString("ID_REPOSITORY"));
				bean.setStgPk(rsClass.getString("STG_PK"));
				bean.setTitoloClassificatore(rsClass
						.getString("TITOLO_CLASSIFICATORE"));
				bean.setUriClassficatore(rsClass.getString("URI_WI"));
				bean.setCf_area(getCustomFieldInString(
						rsClass.getString("STG_PK"), rsClass.getString("ID_REPOSITORY"),
						"area"));
				
				bean.setRmResponsabiliProgetto(getCustomFieldInString(
						rsClass.getString("STG_PK"), rsClass.getString("ID_REPOSITORY"),
						"RMrespProgetto"));
				bean.setProgettoInDeroga(rsClass.getBoolean("PROGETTO_IN_DEROGA"));
				bean.setAssigneeProgettoItInDeroga(rsClass.getString("PROGETTO_IN_DEROGA"));
				bean.setLocationSorgenti(getCustomFieldInString(
						rsClass.getString("STG_PK"), rsClass.getString("ID_REPOSITORY"),
						"locSorgenti"));
				bean.setCodiceServizi(getCustomFieldInString(
						rsClass.getString("STG_PK"), rsClass.getString("ID_REPOSITORY"),
						"codiceServizi"));

				bean.setType(DmAlmConstants.WORKITEM_TYPE_CLASSIFICATORE);
				//DM_ALM-320
				bean.setSeverity(rsClass.getString("SEVERITY"));
				bean.setPriority(rsClass.getString("PRIORITY"));
				bean.setTagAlm(rsClass.getString("TAG_ALM"));
				bean.setTsTagAlm(rsClass.getTimestamp("TS_TAG_ALM"));
				
				classificatoriList.add(bean);
			}

			if (rsClass != null) {
				rsClass.close();
			}
			if (psClass != null) {
				psClass.close();
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

		return classificatoriList;
	}

	private static String getCustomFieldInString(String workitemCpk,
			String repository, String customField) throws Exception {

		String customFieldInString = null;
		List<String> cfWorkitem = new ArrayList<String>();

		if (repository.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SIRE)) {
			cfWorkitem = SireHistoryCfWorkitemDAO.getCustomFieldInString(
					workitemCpk, customField);
		} else {
			cfWorkitem = SissHistoryCfWorkitemDAO.getCustomFieldInString(
					workitemCpk, customField);
		}

		if (cfWorkitem != null && cfWorkitem.size() > 0) {
			customFieldInString = "";

			List<String> sortList = cfWorkitem.subList(0, cfWorkitem.size());
			Collections.sort(sortList);

			for (String row : sortList) {
				if (StringUtils.hasText(row)) {
					if (StringUtils.hasText(customFieldInString)) {
						customFieldInString += ";";
					}

					customFieldInString += row;
				}
			}
		}
		return customFieldInString;
	}
		
	private static boolean getBooleanCustomFieldInString(String workitemCpk,
			String repository, String customField) throws Exception {

		List<Integer> cfWorkitem = new ArrayList<Integer>();

		if (repository.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SIRE)) {
			cfWorkitem = SireHistoryCfWorkitemDAO.getBooleanCustomFieldInString(
					workitemCpk, customField);
		} else {
			cfWorkitem = SissHistoryCfWorkitemDAO.getBooleanCustomFieldInString(
					workitemCpk, customField);
		}

		if (cfWorkitem != null && cfWorkitem.size() > 0) {
			if(cfWorkitem.get(0) == 1){
				return true;
			}
		}
		return false;
	}

	public static List<Tuple> getClassificatore(DmalmClassificatore clas)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(qClassificatore)
					.where(qClassificatore.cd_classificatore.equalsIgnoreCase(clas
							.getCd_classificatore()))
					.where(qClassificatore.idRepository.equalsIgnoreCase(clas
							.getIdRepository()))
					.where(qClassificatore.rankStatoClassificatore.eq(new Double(1)))
					.list(qClassificatore.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}

	public static void insert(DmalmClassificatore classificatore) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qClassificatore)
					.columns(qClassificatore.cd_classificatore, qClassificatore.cf_ambito,
							qClassificatore.cf_area, qClassificatore.cf_riferimenti,
							qClassificatore.cf_scheda_servizio,
							qClassificatore.dmalmClassificatorePk,
							qClassificatore.dmalmProjectFk02,
							qClassificatore.dmalmStatoWorkitemFk03,
							qClassificatore.dmalmStrutturaOrgFk01,
							qClassificatore.dmalmTempoFk04, qClassificatore.dmalmUserFk06,
							qClassificatore.dsAutoreClassificatore,
							qClassificatore.dtCambioStatoClassif,
							qClassificatore.dtCaricamentoClassif,
							qClassificatore.dtCreazioneClassif,
							qClassificatore.dtModificaClassif,
							qClassificatore.dtRisoluzioneClassif,
							qClassificatore.dtScadenzaProgSvil,
							qClassificatore.dtStoricizzazione,
							qClassificatore.idAutoreClassificatore,
							qClassificatore.idRepository,
							qClassificatore.rankStatoClassificatore,
							qClassificatore.rankStatoClassifMese, qClassificatore.stgPk,
							qClassificatore.titoloClassificatore,
							qClassificatore.uriClassficatore,
							qClassificatore.rmResponsabiliProgetto,
							qClassificatore.progettoInDeroga,
							qClassificatore.assigneeProgettoItInDeroga,
							qClassificatore.locationSorgenti,
							qClassificatore.type,
							qClassificatore.codiceServizi,
							qClassificatore.severity, qClassificatore.priority,
							qClassificatore.tagAlm, qClassificatore.tsTagAlm)
					.values(classificatore.getCd_classificatore(), classificatore.getCf_ambito(),
							classificatore.getCf_area(), classificatore.getCf_riferimenti(),
							classificatore.getCf_scheda_servizio(),
							classificatore.getDmalmClassificatorePk(),
							classificatore.getDmalmProjectFk02(),
							classificatore.getDmalmStatoWorkitemFk03(),
							classificatore.getDmalmStrutturaOrgFk01(),
							classificatore.getDmalmTempoFk04(), classificatore.getDmalmUserFk06(),
							classificatore.getDsAutoreClassificatore(),
							classificatore.getDtCambioStatoClassif(),
							classificatore.getDtCaricamentoClassif(),
							classificatore.getDtCreazioneClassif(),
							classificatore.getDtModificaClassif(),
							classificatore.getDtRisoluzioneClassif(),
							classificatore.getDtScadenzaProgSvil(),
							classificatore.getDtStoricizzazione(),
							classificatore.getIdAutoreClassificatore(),
							classificatore.getIdRepository(), new Double(1),
							classificatore.getRankStatoClassifMese(), classificatore.getStgPk(),
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
							classificatore.getTagAlm(),
							classificatore.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmClassificatore classificatore, Double double1)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qClassificatore)
					.where(qClassificatore.cd_classificatore.equalsIgnoreCase(classificatore
							.getCd_classificatore()))
					.where(qClassificatore.idRepository.equalsIgnoreCase(classificatore
							.getIdRepository()))
					.set(qClassificatore.rankStatoClassificatore, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertClassUpdate(Timestamp dataEsecuzione,
			DmalmClassificatore classificatore, boolean pkValue) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qClassificatore)
					.columns(qClassificatore.cd_classificatore, qClassificatore.cf_ambito,
							qClassificatore.cf_area, qClassificatore.cf_riferimenti,
							qClassificatore.cf_scheda_servizio,
							qClassificatore.dmalmClassificatorePk,
							qClassificatore.dmalmProjectFk02,
							qClassificatore.dmalmStatoWorkitemFk03,
							qClassificatore.dmalmStrutturaOrgFk01,
							qClassificatore.dmalmTempoFk04, qClassificatore.dmalmUserFk06,
							qClassificatore.dsAutoreClassificatore,
							qClassificatore.dtCambioStatoClassif,
							qClassificatore.dtCaricamentoClassif,
							qClassificatore.dtCreazioneClassif,
							qClassificatore.dtModificaClassif,
							qClassificatore.dtRisoluzioneClassif,
							qClassificatore.dtScadenzaProgSvil,
							qClassificatore.dtStoricizzazione,
							qClassificatore.idAutoreClassificatore,
							qClassificatore.idRepository,
							qClassificatore.rankStatoClassificatore,
							qClassificatore.rankStatoClassifMese, qClassificatore.stgPk,
							qClassificatore.titoloClassificatore,
							qClassificatore.uriClassficatore, qClassificatore.changed,
							qClassificatore.annullato,
							qClassificatore.rmResponsabiliProgetto,
							qClassificatore.progettoInDeroga,
							qClassificatore.assigneeProgettoItInDeroga,
							qClassificatore.locationSorgenti,
							qClassificatore.type,
							qClassificatore.codiceServizi,
							qClassificatore.severity, qClassificatore.priority,
							qClassificatore.tagAlm, qClassificatore.tsTagAlm)
					.values(classificatore.getCd_classificatore(),
							classificatore.getCf_ambito(),
							classificatore.getCf_area(),
							classificatore.getCf_riferimenti(),
							classificatore.getCf_scheda_servizio(),
							pkValue == true ? classificatore.getDmalmClassificatorePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							classificatore.getDmalmProjectFk02(),
							classificatore.getDmalmStatoWorkitemFk03(),
							classificatore.getDmalmStrutturaOrgFk01(),
							classificatore.getDmalmTempoFk04(), classificatore.getDmalmUserFk06(),
							classificatore.getDsAutoreClassificatore(),
							classificatore.getDtCambioStatoClassif(),
							classificatore.getDtCaricamentoClassif(),
							classificatore.getDtCreazioneClassif(),
							classificatore.getDtModificaClassif(),
							classificatore.getDtRisoluzioneClassif(),
							classificatore.getDtScadenzaProgSvil(),
							classificatore.getDtStoricizzazione(),
							classificatore.getIdAutoreClassificatore(),
							classificatore.getIdRepository(), pkValue == true ? new Short("1")  : classificatore.getRankStatoClassificatore(),
							classificatore.getRankStatoClassifMese(), classificatore.getStgPk(),
							classificatore.getTitoloClassificatore(),
							classificatore.getUriClassficatore(), classificatore.getChanged(),
							classificatore.getAnnullato(),
							classificatore.getRmResponsabiliProgetto(),
							classificatore.isProgettoInDeroga(),
							classificatore.getAssigneeProgettoItInDeroga(),
							classificatore.getLocationSorgenti(),
							classificatore.getType(),
							classificatore.getCodiceServizi(),
							classificatore.getSeverity(),
							classificatore.getPriority(),
							classificatore.getTagAlm(),
							classificatore.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateClass(DmalmClassificatore classificatore)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qClassificatore)

					.where(qClassificatore.cd_classificatore.equalsIgnoreCase(classificatore
							.getCd_classificatore()))
					.where(qClassificatore.idRepository.equalsIgnoreCase(classificatore
							.getIdRepository()))
					.where(qClassificatore.rankStatoClassificatore.eq(new Double(1)))

					.set(qClassificatore.cd_classificatore,
							classificatore.getCd_classificatore())
					.set(qClassificatore.cf_ambito, classificatore.getCf_ambito())
					.set(qClassificatore.cf_area, classificatore.getCf_area())
					.set(qClassificatore.cf_riferimenti, classificatore.getCf_riferimenti())
					.set(qClassificatore.cf_scheda_servizio,
							classificatore.getCf_scheda_servizio())
					.set(qClassificatore.dmalmStrutturaOrgFk01,
							classificatore.getDmalmStrutturaOrgFk01())
					.set(qClassificatore.dmalmTempoFk04, classificatore.getDmalmTempoFk04())
					.set(qClassificatore.dmalmProjectFk02, classificatore.getDmalmProjectFk02())
					.set(qClassificatore.dmalmStatoWorkitemFk03,
							classificatore.getDmalmStatoWorkitemFk03())
					.set(qClassificatore.dmalmUserFk06, classificatore.getDmalmUserFk06())
					.set(qClassificatore.dsAutoreClassificatore,
							classificatore.getDsAutoreClassificatore())
					.set(qClassificatore.dtCambioStatoClassif,
							classificatore.getDtCambioStatoClassif())
					.set(qClassificatore.dtCaricamentoClassif,
							classificatore.getDtCaricamentoClassif())
					.set(qClassificatore.dtCreazioneClassif,
							classificatore.getDtCreazioneClassif())
					.set(qClassificatore.dtModificaClassif,
							classificatore.getDtModificaClassif())
					.set(qClassificatore.dtRisoluzioneClassif,
							classificatore.getDtRisoluzioneClassif())
					.set(qClassificatore.dtScadenzaProgSvil,
							classificatore.getDtScadenzaProgSvil())
					.set(qClassificatore.idAutoreClassificatore,
							classificatore.getIdAutoreClassificatore())
					.set(qClassificatore.stgPk, classificatore.getStgPk())
					.set(qClassificatore.uriClassficatore, classificatore.getUriClassficatore())
					.set(qClassificatore.titoloClassificatore,
							classificatore.getTitoloClassificatore())
					.set(qClassificatore.annullato, classificatore.getAnnullato())
					.set(qClassificatore.rmResponsabiliProgetto, classificatore.getRmResponsabiliProgetto())
					.set(qClassificatore.progettoInDeroga, classificatore.isProgettoInDeroga())
					.set(qClassificatore.assigneeProgettoItInDeroga, classificatore.getAssigneeProgettoItInDeroga())
					.set(qClassificatore.locationSorgenti, classificatore.getLocationSorgenti())
					.set(qClassificatore.type, classificatore.getType())
					.set(qClassificatore.codiceServizi,  classificatore.getCodiceServizi())
					.set(qClassificatore.severity, classificatore.getSeverity())
					.set(qClassificatore.priority,  classificatore.getPriority())
					.set(qClassificatore.tagAlm,  classificatore.getTagAlm())
					.set(qClassificatore.tsTagAlm,  classificatore.getTsTagAlm())
					
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmClassificatore getClassificatore(Integer pk)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(qClassificatore)
					.where(qClassificatore.dmalmClassificatorePk.eq(pk))
					.list(qClassificatore.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);
			DmalmClassificatore c = new DmalmClassificatore();

			c.setAnnullato(t.get(qClassificatore.annullato));
			c.setCd_classificatore(t.get(qClassificatore.cd_classificatore));
			c.setCf_ambito(t.get(qClassificatore.cf_ambito));
			c.setCf_area(t.get(qClassificatore.cf_area));
			c.setCf_riferimenti(t.get(qClassificatore.cf_riferimenti));
			c.setCf_scheda_servizio(t.get(qClassificatore.cf_scheda_servizio));
			c.setChanged(t.get(qClassificatore.changed));
			c.setDmalmAreaTematicaFk05(t.get(qClassificatore.dmalmAreaTematicaFk05));
			c.setDmalmClassificatorePk(t.get(qClassificatore.dmalmClassificatorePk));
			c.setDmalmProjectFk02(t.get(qClassificatore.dmalmProjectFk02));
			c.setDmalmStatoWorkitemFk03(t.get(qClassificatore.dmalmStatoWorkitemFk03));
			c.setDmalmStrutturaOrgFk01(t.get(qClassificatore.dmalmStrutturaOrgFk01));
			c.setDmalmTempoFk04(t.get(qClassificatore.dmalmTempoFk04));
			c.setDmalmUserFk06(t.get(qClassificatore.dmalmUserFk06));
			c.setDsAutoreClassificatore(t.get(qClassificatore.dsAutoreClassificatore));
			c.setDtAnnullamento(t.get(qClassificatore.dtAnnullamento));
			c.setDtCambioStatoClassif(t.get(qClassificatore.dtCambioStatoClassif));
			c.setDtCaricamentoClassif(t.get(qClassificatore.dtCaricamentoClassif));
			c.setDtCreazioneClassif(t.get(qClassificatore.dtCreazioneClassif));
			c.setDtModificaClassif(t.get(qClassificatore.dtModificaClassif));
			c.setDtRisoluzioneClassif(t.get(qClassificatore.dtRisoluzioneClassif));
			c.setDtScadenzaProgSvil(t.get(qClassificatore.dtScadenzaProgSvil));
			c.setDtStoricizzazione(t.get(qClassificatore.dtStoricizzazione));
			c.setIdAutoreClassificatore(t.get(qClassificatore.idAutoreClassificatore));
			c.setIdRepository(t.get(qClassificatore.idRepository));
			c.setRankStatoClassificatore(t
					.get(qClassificatore.rankStatoClassificatore));
			c.setRankStatoClassifMese(t.get(qClassificatore.rankStatoClassifMese));
			c.setStgPk(t.get(qClassificatore.stgPk));
			c.setTitoloClassificatore(t.get(qClassificatore.titoloClassificatore));
			c.setUriClassficatore(t.get(qClassificatore.uriClassficatore));
			c.setRmResponsabiliProgetto(t.get(qClassificatore.rmResponsabiliProgetto));
			c.setProgettoInDeroga(t.get(qClassificatore.progettoInDeroga));
			c.setAssigneeProgettoItInDeroga(t.get(qClassificatore.assigneeProgettoItInDeroga));
			c.setLocationSorgenti(t.get(qClassificatore.locationSorgenti));
			c.setType(t.get(qClassificatore.type));
			c.setCodiceServizi(t.get(qClassificatore.codiceServizi));
			//DM_ALM-320
			c.setSeverity(t.get(qClassificatore.severity));
			c.setPriority(t.get(qClassificatore.priority));
			c.setTagAlm(t.get(qClassificatore.tagAlm));
			c.setTsTagAlm(t.get(qClassificatore.tsTagAlm));
			return c;

		} else
			return null;
	}

	public static boolean checkEsistenzaClassificatore(
			DmalmClassificatore c, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(qClassificatore)
					.where(qClassificatore.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(qClassificatore.cd_classificatore.eq(c.getCd_classificatore()))
					.where(qClassificatore.idRepository.eq(c.getIdRepository()))
					.list(qClassificatore.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
