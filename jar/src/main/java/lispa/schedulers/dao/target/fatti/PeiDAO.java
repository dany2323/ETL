package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PEI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class PeiDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmPei p = QDmalmPei.dmalmPei;

	public static List<DmalmPei> getAllPei(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmPei bean = null;
		List<DmalmPei> peis = new ArrayList<DmalmPei>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.pei));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_PEI);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmPei();

				bean.setCdPei(rs.getString("CD_PEI"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDescrizionePei(rs.getString("DESCRIPTION"));
				bean.setDmalmPeiPk(rs.getInt("DMALM_PEI_PK"));

				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));

				bean.setDsAutorePei(rs.getString("NOME_AUTORE_PEI"));
				bean.setDtCaricamentoPei(dataEsecuzione);
				bean.setDtCreazionePei(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaPei(rs.getTimestamp("DATA_MODIFICA_PEI"));
				bean.setDtPrevistaComplReq(rs
						.getTimestamp("DATA_CF_FORNITURA_REQS_PEI"));
				bean.setDtPrevistaPassInEs(rs
						.getTimestamp("DATA_CF_FORNITURA_PROD_PEI"));
				bean.setDtRisoluzionePei(rs
						.getTimestamp("DATA_RISOLUZIONE_PEI"));
				bean.setDtScadenzaPei(rs.getTimestamp("DATA_SCADENZA_PEI"));
				bean.setIdAutorePei(rs.getString("ID_AUTORE_PEI"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzionePei(rs
						.getString("MOTIVO_RISOLUZIONE_PEI"));
				bean.setTitoloPei(rs.getString("TITOLO_PEI"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PEI_PK"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));

				peis.add(bean);
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

		return peis;
	}

	public static List<Tuple> getPei(DmalmPei pei) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> peis = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			peis = query
					.from(p)
					.where(p.cdPei.equalsIgnoreCase(pei.getCdPei()))
					.where(p.idRepository.equalsIgnoreCase(pei
							.getIdRepository()))
					.where(p.rankStatoPei.eq(new Double(1))).list(p.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return peis;

	}

	public static void insertPei(DmalmPei pei) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, p)
					.columns(p.cdPei, p.codice, p.descrizionePei, p.dmalmPeiPk,
							p.dmalmProjectFk02, p.dmalmStatoWorkitemFk03,
							p.dmalmStrutturaOrgFk01, p.dmalmTempoFk04,
							p.dsAutorePei, p.dtCambioStatoPei,
							p.dtCaricamentoPei, p.dtCreazionePei,
							p.dtModificaPei, p.dtPrevistaComplReq,
							p.dtPrevistaPassInEs, p.dtRisoluzionePei,
							p.dtScadenzaPei, p.dtStoricizzazione,
							p.idAutorePei, p.idRepository,
							p.motivoRisoluzionePei, p.rankStatoPei,
							p.titoloPei, p.stgPk, p.dmalmUserFk06, p.uri,
							p.dtAnnullamento,
							p.severity, p.priority)
					.values(pei.getCdPei(), pei.getCodice(),
							pei.getDescrizionePei(), pei.getDmalmPeiPk(),
							pei.getDmalmProjectFk02(),
							pei.getDmalmStatoWorkitemFk03(),
							pei.getDmalmStrutturaOrgFk01(),
							pei.getDmalmTempoFk04(), pei.getDsAutorePei(),
							pei.getDtCambioStatoPei(),
							pei.getDtCaricamentoPei(), pei.getDtCreazionePei(),
							pei.getDtModificaPei(),
							pei.getDtPrevistaComplReq(),
							pei.getDtPrevistaPassInEs(),
							pei.getDtRisoluzionePei(), pei.getDtScadenzaPei(),
							pei.getDtModificaPei(), pei.getIdAutorePei(),
							pei.getIdRepository(),
							pei.getMotivoRisoluzionePei(), new Double(1),
							pei.getTitoloPei(), pei.getStgPk(),
							pei.getDmalmUserFk06(), pei.getUri(),
							pei.getDtAnnullamento(),
							pei.getSeverity(), pei.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmPei pei, Double double1)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, p)
					.where(p.cdPei.equalsIgnoreCase(pei.getCdPei()))
					.where(p.idRepository.equalsIgnoreCase(pei
							.getIdRepository())).set(p.rankStatoPei, double1)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertPeiUpdate(Timestamp dataEsecuzione, DmalmPei pei,
			boolean pkValue) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, p)
					.columns(p.cdPei, p.codice, p.descrizionePei, p.dmalmPeiPk,
							p.dmalmProjectFk02, p.dmalmStatoWorkitemFk03,
							p.dmalmStrutturaOrgFk01, p.dmalmTempoFk04,
							p.dsAutorePei, p.dtCambioStatoPei,
							p.dtCaricamentoPei, p.dtCreazionePei,
							p.dtModificaPei, p.dtPrevistaComplReq,
							p.dtPrevistaPassInEs, p.dtRisoluzionePei,
							p.dtScadenzaPei, p.dtStoricizzazione,
							p.idAutorePei, p.idRepository,
							p.motivoRisoluzionePei, p.rankStatoPei,
							p.titoloPei, p.stgPk, p.dmalmUserFk06, p.uri,
							p.dtAnnullamento, p.changed, p.annullato,
							p.severity, p.priority)
					.values(pei.getCdPei(),
							pei.getCodice(),
							pei.getDescrizionePei(),
							pkValue == true ? pei.getDmalmPeiPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							pei.getDmalmProjectFk02(),
							pei.getDmalmStatoWorkitemFk03(),
							pei.getDmalmStrutturaOrgFk01(),
							pei.getDmalmTempoFk04(),
							pei.getDsAutorePei(),
							pei.getDtCambioStatoPei(),
							pei.getDtCaricamentoPei(),
							pei.getDtCreazionePei(),
							pei.getDtModificaPei(),
							pei.getDtPrevistaComplReq(),
							pei.getDtPrevistaPassInEs(),
							pei.getDtRisoluzionePei(),
							pei.getDtScadenzaPei(),
							pkValue == true ? pei.getDtModificaPei() : pei
									.getDtStoricizzazione(),
							pei.getIdAutorePei(), pei.getIdRepository(),
							pei.getMotivoRisoluzionePei(), 
							pkValue == true ? new Short("1")  : pei.getRankStatoPei(),
							pei.getTitoloPei(), pei.getStgPk(),
							pei.getDmalmUserFk06(), pei.getUri(),
							pei.getDtAnnullamento(), pei.getChanged(),
							pei.getAnnullato(),
							pei.getSeverity(), pei.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updatePei(DmalmPei pei) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, p)

					.where(p.cdPei.equalsIgnoreCase(pei.getCdPei()))
					.where(p.idRepository.equalsIgnoreCase(pei
							.getIdRepository()))
					.where(p.rankStatoPei.eq(new Double(1)))

					.set(p.cdPei, pei.getCdPei())
					.set(p.codice, pei.getCodice())
					.set(p.descrizionePei, pei.getDescrizionePei())
					.set(p.dmalmProjectFk02, pei.getDmalmProjectFk02())
					.set(p.dmalmStatoWorkitemFk03,
							pei.getDmalmStatoWorkitemFk03())
					.set(p.dmalmStrutturaOrgFk01,
							pei.getDmalmStrutturaOrgFk01())
					.set(p.dmalmTempoFk04, pei.getDmalmTempoFk04())
					.set(p.dsAutorePei, pei.getDsAutorePei())
					.set(p.dtCreazionePei, pei.getDtCreazionePei())
					.set(p.dtModificaPei, pei.getDtModificaPei())
					.set(p.dtPrevistaComplReq, pei.getDtPrevistaComplReq())
					.set(p.dtPrevistaPassInEs, pei.getDtPrevistaPassInEs())
					.set(p.dtRisoluzionePei, pei.getDtRisoluzionePei())
					.set(p.dtScadenzaPei, pei.getDtScadenzaPei())
					.set(p.idAutorePei, pei.getIdAutorePei())
					.set(p.idRepository, pei.getIdRepository())
					.set(p.motivoRisoluzionePei, pei.getMotivoRisoluzionePei())
					.set(p.titoloPei, pei.getTitoloPei())
					.set(p.stgPk, pei.getStgPk()).set(p.uri, pei.getUri())
					.set(p.dtAnnullamento, pei.getDtAnnullamento())
					.set(p.annullato, pei.getAnnullato())
					.set(p.severity, pei.getSeverity())
					.set(p.priority, pei.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmPei getPei(Integer pk) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> peis = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			peis = query.from(p).where(p.dmalmPeiPk.eq(pk)).list(p.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (peis.size() > 0) {

			Tuple t = peis.get(0);
			DmalmPei d = new DmalmPei();

			d.setAnnullato(t.get(p.annullato));
			d.setCdPei(t.get(p.cdPei));
			d.setChanged(t.get(p.changed));
			d.setCodice(t.get(p.codice));
			d.setDescrizionePei(t.get(p.descrizionePei));
			d.setDmalmPeiPk(t.get(p.dmalmPeiPk));
			d.setDmalmProjectFk02(t.get(p.dmalmProjectFk02));
			d.setDmalmStatoWorkitemFk03(t.get(p.dmalmStatoWorkitemFk03));
			d.setDmalmStrutturaOrgFk01(t.get(p.dmalmStrutturaOrgFk01));
			d.setDmalmTempoFk04(t.get(p.dmalmTempoFk04));
			d.setDmalmUserFk06(t.get(p.dmalmUserFk06));
			d.setDsAutorePei(t.get(p.dsAutorePei));
			d.setDtAnnullamento(t.get(p.dtAnnullamento));
			d.setDtCambioStatoPei(t.get(p.dtCambioStatoPei));
			d.setDtCaricamentoPei(t.get(p.dtCaricamentoPei));
			d.setDtCreazionePei(t.get(p.dtCreazionePei));
			d.setDtModificaPei(t.get(p.dtModificaPei));
			d.setDtPrevistaComplReq(t.get(p.dtPrevistaComplReq));
			d.setDtPrevistaPassInEs(t.get(p.dtPrevistaPassInEs));
			d.setDtRisoluzionePei(t.get(p.dtRisoluzionePei));
			d.setDtScadenzaPei(t.get(p.dtScadenzaPei));
			d.setDtStoricizzazione(t.get(p.dtStoricizzazione));
			d.setIdAutorePei(t.get(p.idAutorePei));
			d.setIdRepository(t.get(p.idRepository));
			d.setMotivoRisoluzionePei(t.get(p.motivoRisoluzionePei));
			d.setRankStatoPei(t.get(p.rankStatoPei));
			d.setRankStatoPeiMese(t.get(p.rankStatoPeiMese));
			d.setStgPk(t.get(p.stgPk));
			d.setTitoloPei(t.get(p.titoloPei));
			d.setUri(t.get(p.uri));
			//DM_ALM-320
			d.setSeverity(t.get(p.severity));
			d.setPriority(t.get(p.priority));

			return d;

		} else
			return null;
	}

	public static boolean checkEsistenzaPei(DmalmPei f, DmalmProject p2) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(p)
					.where(p.dmalmProjectFk02.eq(p2
							.getDmalmProjectPk()))
					.where(p.cdPei.eq(f.getCdPei()))
					.where(p.idRepository.eq(f.getIdRepository()))
					.list(p.all());

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
