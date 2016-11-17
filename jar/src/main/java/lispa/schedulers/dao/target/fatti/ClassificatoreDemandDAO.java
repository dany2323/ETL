package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_CLASS_DEMAND;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmClassificatoreDemand;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatoreDemand;
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

public class ClassificatoreDemandDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmClassificatoreDemand classDem = QDmalmClassificatoreDemand.dmalmClassificatoreDemand;

	public static List<DmalmClassificatoreDemand> getAllClassDem(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmClassificatoreDemand bean = null;
		List<DmalmClassificatoreDemand> classificatori = new ArrayList<DmalmClassificatoreDemand>(
				QueryUtils
						.getCountByWIType(Workitem_Type.classificatore_demand));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_CLASS_DEMAND);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmClassificatoreDemand();

				bean.setCd_classificatore(rs.getString("CD_CLASSIFICATORE"));
				bean.setDmalmClassificatorePk(rs
						.getInt("DMALM_CLASSIFICATORE_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreClassificatore(rs
						.getString("DS_AUTORE_CLASSIFICATORE"));
				bean.setDtCaricamentoClassif(dataEsecuzione);
				bean.setDtCreazioneClassif(rs
						.getTimestamp("DT_CREAZIONE_CLASSIF"));
				bean.setDtModificaClassif(rs
						.getTimestamp("DT_MODIFICA_CLASSIF"));
				bean.setDtRisoluzioneClassif(rs
						.getTimestamp("DT_RISOLUZIONE_CLASSIF"));
				bean.setDtScadenzaProgSvil(rs
						.getTimestamp("DT_SCADENZA_PROG_SVIL_D"));
				bean.setIdAutoreClassificatore(rs
						.getString("ID_AUTORE_CLASSIFICATORE"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setTitoloClassificatore(rs
						.getString("TITOLO_CLASSIFICATORE"));
				bean.setUriClassficatore(rs.getString("URI_WI"));
				bean.setCf_ambito(rs.getString("AMBITO"));
				// bean.setCf_area(rs.getString("AREA"));
				bean.setCf_area(getCustomFieldInString(
						rs.getString("STG_PK"), rs.getString("ID_REPOSITORY"),
						"area"));
				// bean.setCf_riferimenti(rs.getString("RIFERIMENTO"));
				bean.setCf_riferimenti(getCustomFieldInString(
						rs.getString("STG_PK"), rs.getString("ID_REPOSITORY"),
						"riferimenti"));
				// bean.setCf_scheda_servizio(rs.getString("SCHEDA_SERVIZIO"));
				bean.setCf_scheda_servizio(getCustomFieldInString(
						rs.getString("STG_PK"), rs.getString("ID_REPOSITORY"),
						"scheda_servizio"));

				classificatori.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
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

		return classificatori;
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

			List<String> sortList = cfWorkitem.subList(1, cfWorkitem.size());
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

	public static List<Tuple> getClassDem(DmalmClassificatoreDemand cDem)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(classDem)
					.where(classDem.cd_classificatore.equalsIgnoreCase(cDem
							.getCd_classificatore()))
					.where(classDem.idRepository.equalsIgnoreCase(cDem
							.getIdRepository()))
					.where(classDem.rankStatoClassificatore.eq(new Double(1)))
					.list(classDem.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}

	public static void insert(DmalmClassificatoreDemand cDem) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, classDem)
					.columns(classDem.cd_classificatore, classDem.cf_ambito,
							classDem.cf_area, classDem.cf_riferimenti,
							classDem.cf_scheda_servizio,
							classDem.dmalmClassificatorePk,
							classDem.dmalmProjectFk02,
							classDem.dmalmStatoWorkitemFk03,
							classDem.dmalmStrutturaOrgFk01,
							classDem.dmalmTempoFk04, classDem.dmalmUserFk06,
							classDem.dsAutoreClassificatore,
							classDem.dtCambioStatoClassif,
							classDem.dtCaricamentoClassif,
							classDem.dtCreazioneClassif,
							classDem.dtModificaClassif,
							classDem.dtRisoluzioneClassif,
							classDem.dtScadenzaProgSvil,
							classDem.dtStoricizzazione,
							classDem.idAutoreClassificatore,
							classDem.idRepository,
							classDem.rankStatoClassificatore,
							classDem.rankStatoClassifMese, classDem.stgPk,
							classDem.titoloClassificatore,
							classDem.uriClassficatore)
					.values(cDem.getCd_classificatore(), cDem.getCf_ambito(),
							cDem.getCf_area(), cDem.getCf_riferimenti(),
							cDem.getCf_scheda_servizio(),
							cDem.getDmalmClassificatorePk(),
							cDem.getDmalmProjectFk02(),
							cDem.getDmalmStatoWorkitemFk03(),
							cDem.getDmalmStrutturaOrgFk01(),
							cDem.getDmalmTempoFk04(), cDem.getDmalmUserFk06(),
							cDem.getDsAutoreClassificatore(),
							cDem.getDtCambioStatoClassif(),
							cDem.getDtCaricamentoClassif(),
							cDem.getDtCreazioneClassif(),
							cDem.getDtModificaClassif(),
							cDem.getDtRisoluzioneClassif(),
							cDem.getDtScadenzaProgSvil(),
							cDem.getDtStoricizzazione(),
							cDem.getIdAutoreClassificatore(),
							cDem.getIdRepository(), new Double(1),
							cDem.getRankStatoClassifMese(), cDem.getStgPk(),
							cDem.getTitoloClassificatore(),
							cDem.getUriClassficatore()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmClassificatoreDemand cDem, Double double1)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, classDem)
					.where(classDem.cd_classificatore.equalsIgnoreCase(cDem
							.getCd_classificatore()))
					.where(classDem.idRepository.equalsIgnoreCase(cDem
							.getIdRepository()))
					.set(classDem.rankStatoClassificatore, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertClassDemUpdate(Timestamp dataEsecuzione,
			DmalmClassificatoreDemand cDem, boolean pkValue) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, classDem)
					.columns(classDem.cd_classificatore, classDem.cf_ambito,
							classDem.cf_area, classDem.cf_riferimenti,
							classDem.cf_scheda_servizio,
							classDem.dmalmClassificatorePk,
							classDem.dmalmProjectFk02,
							classDem.dmalmStatoWorkitemFk03,
							classDem.dmalmStrutturaOrgFk01,
							classDem.dmalmTempoFk04, classDem.dmalmUserFk06,
							classDem.dsAutoreClassificatore,
							classDem.dtCambioStatoClassif,
							classDem.dtCaricamentoClassif,
							classDem.dtCreazioneClassif,
							classDem.dtModificaClassif,
							classDem.dtRisoluzioneClassif,
							classDem.dtScadenzaProgSvil,
							classDem.dtStoricizzazione,
							classDem.idAutoreClassificatore,
							classDem.idRepository,
							classDem.rankStatoClassificatore,
							classDem.rankStatoClassifMese, classDem.stgPk,
							classDem.titoloClassificatore,
							classDem.uriClassficatore, classDem.changed,
							classDem.annullato)
					.values(cDem.getCd_classificatore(),
							cDem.getCf_ambito(),
							cDem.getCf_area(),
							cDem.getCf_riferimenti(),
							cDem.getCf_scheda_servizio(),
							pkValue == true ? cDem.getDmalmClassificatorePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							cDem.getDmalmProjectFk02(),
							cDem.getDmalmStatoWorkitemFk03(),
							cDem.getDmalmStrutturaOrgFk01(),
							cDem.getDmalmTempoFk04(), cDem.getDmalmUserFk06(),
							cDem.getDsAutoreClassificatore(),
							cDem.getDtCambioStatoClassif(),
							cDem.getDtCaricamentoClassif(),
							cDem.getDtCreazioneClassif(),
							cDem.getDtModificaClassif(),
							cDem.getDtRisoluzioneClassif(),
							cDem.getDtScadenzaProgSvil(),
							cDem.getDtStoricizzazione(),
							cDem.getIdAutoreClassificatore(),
							cDem.getIdRepository(), pkValue == true ? new Short("1")  : cDem.getRankStatoClassificatore(),
							cDem.getRankStatoClassifMese(), cDem.getStgPk(),
							cDem.getTitoloClassificatore(),
							cDem.getUriClassficatore(), cDem.getChanged(),
							cDem.getAnnullato()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateClassdem(DmalmClassificatoreDemand cDem)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, classDem)

					.where(classDem.cd_classificatore.equalsIgnoreCase(cDem
							.getCd_classificatore()))
					.where(classDem.idRepository.equalsIgnoreCase(cDem
							.getIdRepository()))
					.where(classDem.rankStatoClassificatore.eq(new Double(1)))

					.set(classDem.cd_classificatore,
							cDem.getCd_classificatore())
					.set(classDem.cf_ambito, cDem.getCf_ambito())
					.set(classDem.cf_area, cDem.getCf_area())
					.set(classDem.cf_riferimenti, cDem.getCf_riferimenti())
					.set(classDem.cf_scheda_servizio,
							cDem.getCf_scheda_servizio())
					.set(classDem.dmalmStrutturaOrgFk01,
							cDem.getDmalmStrutturaOrgFk01())
					.set(classDem.dmalmTempoFk04, cDem.getDmalmTempoFk04())
					.set(classDem.dmalmProjectFk02, cDem.getDmalmProjectFk02())
					.set(classDem.dmalmStatoWorkitemFk03,
							cDem.getDmalmStatoWorkitemFk03())
					.set(classDem.dmalmUserFk06, cDem.getDmalmUserFk06())
					.set(classDem.dsAutoreClassificatore,
							cDem.getDsAutoreClassificatore())
					.set(classDem.dtCambioStatoClassif,
							cDem.getDtCambioStatoClassif())
					.set(classDem.dtCaricamentoClassif,
							cDem.getDtCaricamentoClassif())
					.set(classDem.dtCreazioneClassif,
							cDem.getDtCreazioneClassif())
					.set(classDem.dtModificaClassif,
							cDem.getDtModificaClassif())
					.set(classDem.dtRisoluzioneClassif,
							cDem.getDtRisoluzioneClassif())
					.set(classDem.dtScadenzaProgSvil,
							cDem.getDtScadenzaProgSvil())
					.set(classDem.idAutoreClassificatore,
							cDem.getIdAutoreClassificatore())
					.set(classDem.stgPk, cDem.getStgPk())
					.set(classDem.uriClassficatore, cDem.getUriClassficatore())
					.set(classDem.titoloClassificatore,
							cDem.getTitoloClassificatore())
					.set(classDem.annullato, cDem.getAnnullato())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmClassificatoreDemand getClassDem(Integer pk)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(classDem)
					.where(classDem.dmalmClassificatorePk.eq(pk))
					.list(classDem.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);
			DmalmClassificatoreDemand c = new DmalmClassificatoreDemand();

			c.setAnnullato(t.get(classDem.annullato));
			c.setCd_classificatore(t.get(classDem.cd_classificatore));
			c.setCf_ambito(t.get(classDem.cf_ambito));
			c.setCf_area(t.get(classDem.cf_area));
			c.setCf_riferimenti(t.get(classDem.cf_riferimenti));
			c.setCf_scheda_servizio(t.get(classDem.cf_scheda_servizio));
			c.setChanged(t.get(classDem.changed));
			c.setDmalmAreaTematicaFk05(t.get(classDem.dmalmAreaTematicaFk05));
			c.setDmalmClassificatorePk(t.get(classDem.dmalmClassificatorePk));
			c.setDmalmProjectFk02(t.get(classDem.dmalmProjectFk02));
			c.setDmalmStatoWorkitemFk03(t.get(classDem.dmalmStatoWorkitemFk03));
			c.setDmalmStrutturaOrgFk01(t.get(classDem.dmalmStrutturaOrgFk01));
			c.setDmalmTempoFk04(t.get(classDem.dmalmTempoFk04));
			c.setDmalmUserFk06(t.get(classDem.dmalmUserFk06));
			c.setDsAutoreClassificatore(t.get(classDem.dsAutoreClassificatore));
			c.setDtAnnullamento(t.get(classDem.dtAnnullamento));
			c.setDtCambioStatoClassif(t.get(classDem.dtCambioStatoClassif));
			c.setDtCaricamentoClassif(t.get(classDem.dtCaricamentoClassif));
			c.setDtCreazioneClassif(t.get(classDem.dtCreazioneClassif));
			c.setDtModificaClassif(t.get(classDem.dtModificaClassif));
			c.setDtRisoluzioneClassif(t.get(classDem.dtRisoluzioneClassif));
			c.setDtScadenzaProgSvil(t.get(classDem.dtScadenzaProgSvil));
			c.setDtStoricizzazione(t.get(classDem.dtStoricizzazione));
			c.setIdAutoreClassificatore(t.get(classDem.idAutoreClassificatore));
			c.setIdRepository(t.get(classDem.idRepository));
			c.setRankStatoClassificatore(t
					.get(classDem.rankStatoClassificatore));
			c.setRankStatoClassifMese(t.get(classDem.rankStatoClassifMese));
			c.setStgPk(t.get(classDem.stgPk));
			c.setTitoloClassificatore(t.get(classDem.titoloClassificatore));
			c.setUriClassficatore(t.get(classDem.uriClassficatore));

			return c;

		} else
			return null;
	}

	public static boolean checkEsistenzaClassificatore(
			DmalmClassificatoreDemand c, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(classDem)
					.where(classDem.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(classDem.cd_classificatore.eq(c.getCd_classificatore()))
					.where(classDem.idRepository.eq(c.getIdRepository()))
					.list(classDem.all());

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
