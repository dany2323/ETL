package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgramma;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgrammaOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgrammaOdsDAO {
	private static Logger logger = Logger.getLogger(ProgrammaOdsDAO.class);

	private static QDmalmProgrammaOds programmaODS = QDmalmProgrammaOds.dmalmProgrammaOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, programmaODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmProgramma> staging_programma,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgramma programma : staging_programma) {
				new SQLInsertClause(connection, dialect, programmaODS)
						.columns(programmaODS.assignee,
								programmaODS.cdProgramma,
								programmaODS.descrizioneProgramma,
								programmaODS.dmalmProgrammaPk,
								programmaODS.dmalmProjectFk02,
								programmaODS.dmalmStatoWorkitemFk03,
								programmaODS.dmalmStrutturaOrgFk01,
								programmaODS.dmalmTempoFk04,
								programmaODS.dsAutoreProgramma,
								programmaODS.dtCambioStatoProgramma,
								programmaODS.dtCaricamentoProgramma,
								programmaODS.dtCreazioneProgramma,
								programmaODS.dtModificaProgramma,
								programmaODS.dtRisoluzioneProgramma,
								programmaODS.dtScadenzaProgramma,
								programmaODS.dtStoricizzazione,
								programmaODS.idAutoreProgramma,
								programmaODS.idRepository,
								programmaODS.motivoRisoluzioneProgramma,
								programmaODS.numeroLinea,
								programmaODS.numeroTestata,
								programmaODS.rankStatoProgramma,
								programmaODS.titoloProgramma,
								programmaODS.cfContratto,
								programmaODS.cfReferenteRegionale,
								programmaODS.cfServiceManager,
								programmaODS.cfTipologia, programmaODS.stgPk,
								programmaODS.dmalmUserFk06, programmaODS.uri,
								programmaODS.codice,
								programmaODS.severity, programmaODS.priority)
						.values(programma.getAssignee(),
								programma.getCdProgramma(),
								programma.getDescrizioneProgramma(),
								programma.getDmalmProgrammaPk(),
								programma.getDmalmProjectFk02(),
								programma.getDmalmStatoWorkitemFk03(),
								programma.getDmalmStrutturaOrgFk01(),
								programma.getDmalmTempoFk04(),
								programma.getDsAutoreProgramma(),
								programma.getDtCambioStatoProgramma(),
								programma.getDtCaricamentoProgramma(),
								programma.getDtCreazioneProgramma(),
								programma.getDtModificaProgramma(),
								programma.getDtRisoluzioneProgramma(),
								programma.getDtScadenzaProgramma(),
								programma.getDtModificaProgramma(),
								programma.getIdAutoreProgramma(),
								programma.getIdRepository(),
								programma.getMotivoRisoluzioneProgramma(),
								programma.getNumeroLinea(),
								programma.getNumeroTestata(), new Double(1),
								programma.getTitoloProgramma(),
								programma.getCfContratto(),
								programma.getCfReferenteRegionale(),
								programma.getCfServiceManager(),
								programma.getCfTipologia(),
								programma.getStgPk(),
								programma.getDmalmUserFk06(),
								programma.getUri(), programma.getCodice(),
								programma.getSeverity(), programma.getPriority())
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

	public static List<DmalmProgramma> getAll() throws SQLException,
			DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmProgramma> resultList = new LinkedList<DmalmProgramma>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(programmaODS)
					.orderBy(programmaODS.cdProgramma.asc())
					.orderBy(programmaODS.dtModificaProgramma.asc())
					.list(programmaODS.all());

			for(Tuple t : list) {
				DmalmProgramma prog = new DmalmProgramma();
				
				prog.setUri(t.get(programmaODS.uri));
				prog.setCodice(t.get(programmaODS.codice));
				prog.setCfTipologia(t.get(programmaODS.cfTipologia));
				prog.setCfServiceManager(t.get(programmaODS.cfServiceManager));
				prog.setCfReferenteRegionale(t.get(programmaODS.cfReferenteRegionale));
				prog.setCfContratto(t.get(programmaODS.cfContratto));
				prog.setStgPk(t.get(programmaODS.stgPk));
				prog.setAssignee(t.get(programmaODS.assignee));
				prog.setCdProgramma(t.get(programmaODS.cdProgramma));
				prog.setDescrizioneProgramma(t.get(programmaODS.descrizioneProgramma));
				prog.setDmalmProgrammaPk(t.get(programmaODS.dmalmProgrammaPk));
				prog.setDmalmProjectFk02(t.get(programmaODS.dmalmProjectFk02));
				prog.setDmalmStatoWorkitemFk03(t.get(programmaODS.dmalmStatoWorkitemFk03));
				prog.setDmalmStrutturaOrgFk01(t.get(programmaODS.dmalmStrutturaOrgFk01));
				prog.setDmalmTempoFk04(t.get(programmaODS.dmalmTempoFk04));
				prog.setDsAutoreProgramma(t.get(programmaODS.dsAutoreProgramma));
				prog.setDtCambioStatoProgramma(t.get(programmaODS.dtCambioStatoProgramma));
				prog.setDtCaricamentoProgramma(t.get(programmaODS.dtCaricamentoProgramma));
				prog.setDtCreazioneProgramma(t.get(programmaODS.dtCreazioneProgramma));
				prog.setDtModificaProgramma(t.get(programmaODS.dtModificaProgramma));
				prog.setDtRisoluzioneProgramma(t.get(programmaODS.dtRisoluzioneProgramma));
				prog.setDtScadenzaProgramma(t.get(programmaODS.dtScadenzaProgramma));
				prog.setDtStoricizzazione(t.get(programmaODS.dtStoricizzazione));
				prog.setIdAutoreProgramma(t.get(programmaODS.idAutoreProgramma));
				prog.setIdRepository(t.get(programmaODS.idRepository));
				prog.setMotivoRisoluzioneProgramma(t.get(programmaODS.motivoRisoluzioneProgramma));
				prog.setNumeroLinea(t.get(programmaODS.numeroLinea));
				prog.setNumeroTestata(t.get(programmaODS.numeroTestata));
				prog.setRankStatoProgramma(t.get(programmaODS.rankStatoProgramma));
				prog.setRankStatoProgrammaMese(t.get(programmaODS.rankStatoProgrammaMese));
				prog.setTitoloProgramma(t.get(programmaODS.titoloProgramma));
				prog.setDmalmUserFk06(t.get(programmaODS.dmalmUserFk06));
				prog.setSeverity(t.get(programmaODS.severity));
				prog.setPriority(t.get(programmaODS.priority)); 
				resultList.add(prog);
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
