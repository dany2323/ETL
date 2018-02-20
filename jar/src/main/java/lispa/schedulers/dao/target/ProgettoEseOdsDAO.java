package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoEseOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
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

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoEse progetto : staging_progettoEse) {
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
								progettoEseOds.severity, progettoEseOds.priority)
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
								progetto.getSeverity(), progetto.getPriority())
						.execute();

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

		List<Tuple> list = null;
		List<DmalmProgettoEse> resultList = new LinkedList<DmalmProgettoEse>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoEseOds)
					.orderBy(progettoEseOds.cdProgettoEse.asc())
					.orderBy(progettoEseOds.dtModificaProgettoEse.asc())
					.list(progettoEseOds.all());
			
			for (Tuple t : list) {
				DmalmProgettoEse pEse = new DmalmProgettoEse();
				
				pEse.setUri(t.get(progettoEseOds.uri));
				pEse.setStgPk(t.get(progettoEseOds.stgPk));
				pEse.setCdProgettoEse(t.get(progettoEseOds.cdProgettoEse));
				pEse.setCfCodice(t.get(progettoEseOds.cfCodice));
				pEse.setCfDtUltimaSottomissione(t.get(progettoEseOds.cfDtUltimaSottomissione));
				pEse.setDescrizioneProgettoEse(t.get(progettoEseOds.descrizioneProgettoEse));
				pEse.setDmalmProgettoEsePk(t.get(progettoEseOds.dmalmProgettoEsePk));
				pEse.setDmalmProjectFk02(t.get(progettoEseOds.dmalmProjectFk02));
				pEse.setDmalmStatoWorkitemFk03(t.get(progettoEseOds.dmalmStatoWorkitemFk03));
				pEse.setDmalmStrutturaOrgFk01(t.get(progettoEseOds.dmalmStrutturaOrgFk01));
				pEse.setDmalmTempoFk04(t.get(progettoEseOds.dmalmTempoFk04));
				pEse.setDsAutoreProgettoEse(t.get(progettoEseOds.dsAutoreProgettoEse));
				pEse.setDtCambioStatoProgettoEse(t.get(progettoEseOds.dtCambioStatoProgettoEse));
				pEse.setDtCaricamentoProgettoEse(t.get(progettoEseOds.dtCaricamentoProgettoEse));
				pEse.setDtCreazioneProgettoEse(t.get(progettoEseOds.dtCreazioneProgettoEse));
				pEse.setDtModificaProgettoEse(t.get(progettoEseOds.dtModificaProgettoEse));
				pEse.setDtRisoluzioneProgettoEse(t.get(progettoEseOds.dtRisoluzioneProgettoEse));
				pEse.setDtScadenzaProgettoEse(t.get(progettoEseOds.dtScadenzaProgettoEse));
				pEse.setDtStoricizzazione(t.get(progettoEseOds.dtStoricizzazione));
				pEse.setIdAutoreProgettoEse(t.get(progettoEseOds.idAutoreProgettoEse));
				pEse.setIdRepository(t.get(progettoEseOds.idRepository));
				pEse.setMotivoRisoluzioneProgEse(t.get(progettoEseOds.motivoRisoluzioneProgEse));
				pEse.setRankStatoProgettoEse(t.get(progettoEseOds.rankStatoProgettoEse));
				pEse.setRankStatoProgettoEseMese(t.get(progettoEseOds.rankStatoProgettoEseMese));
				pEse.setTitoloProgettoEse(t.get(progettoEseOds.titoloProgettoEse));
				pEse.setDmalmUserFk06(t.get(progettoEseOds.dmalmUserFk06));
				pEse.setSeverity(t.get(progettoEseOds.severity));
				pEse.setPriority(t.get(progettoEseOds.priority));
				
				resultList.add(pEse);
			}
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;

	}

}
