package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmPeiOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
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
								peiODS.stgPk, peiODS.dmalmUserFk06, peiODS.uri,
								peiODS.severity, peiODS.priority)
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
								pei.getDmalmUserFk06(), pei.getUri(),
								pei.getSeverity(), pei.getPriority()).execute();

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

		List<Tuple> list = null;
		List<DmalmPei> resultList = new LinkedList<DmalmPei>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(peiODS).orderBy(peiODS.cdPei.asc())
					.orderBy(peiODS.dtModificaPei.asc())
					.list(peiODS.all());

			for (Tuple t : list) {
				DmalmPei pei = new DmalmPei();
				pei.setUri(t.get(peiODS.uri));
				pei.setCdPei(t.get(peiODS.cdPei));
				pei.setCodice(t.get(peiODS.codice));
				pei.setDescrizionePei(t.get(peiODS.descrizionePei));
				pei.setDmalmPeiPk(t.get(peiODS.dmalmPeiPk));
				pei.setDmalmProjectFk02(t.get(peiODS.dmalmProjectFk02));
				pei.setDmalmStatoWorkitemFk03(t.get(peiODS.dmalmStatoWorkitemFk03));
				pei.setDmalmStrutturaOrgFk01(t.get(peiODS.dmalmStrutturaOrgFk01));
				pei.setDmalmTempoFk04(t.get(peiODS.dmalmTempoFk04));
				pei.setDsAutorePei(t.get(peiODS.dsAutorePei));
				pei.setDtCambioStatoPei(t.get(peiODS.dtCambioStatoPei));
				pei.setDtCaricamentoPei(t.get(peiODS.dtCaricamentoPei));
				pei.setDtCreazionePei(t.get(peiODS.dtCreazionePei));
				pei.setDtModificaPei(t.get(peiODS.dtModificaPei)); 
				pei.setDtPrevistaComplReq(t.get(peiODS.dtPrevistaComplReq));
				pei.setDtPrevistaPassInEs(t.get(peiODS.dtPrevistaPassInEs));
				pei.setDtRisoluzionePei(t.get(peiODS.dtRisoluzionePei)); 
				pei.setDtScadenzaPei(t.get(peiODS.dtScadenzaPei));
				pei.setDtStoricizzazione(t.get(peiODS.dtStoricizzazione));
				pei.setIdAutorePei(t.get(peiODS.idAutorePei));
				pei.setIdRepository(t.get(peiODS.idRepository)); 
				pei.setMotivoRisoluzionePei(t.get(peiODS.motivoRisoluzionePei));
				pei.setRankStatoPei(t.get(peiODS.rankStatoPei)); 
				pei.setRankStatoPeiMese(t.get(peiODS.rankStatoPeiMese));
				pei.setTitoloPei(t.get(peiODS.titoloPei));
				pei.setStgPk(t.get(peiODS.stgPk));
				pei.setDmalmUserFk06(t.get(peiODS.dmalmUserFk06));
				pei.setSeverity(t.get(peiODS.severity));
				pei.setPriority(t.get(peiODS.priority)); 
				
				resultList.add(pei);
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

		return resultList;

	}

}
