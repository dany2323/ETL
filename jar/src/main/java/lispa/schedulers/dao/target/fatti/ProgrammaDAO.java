package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmProgramma;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgramma;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class ProgrammaDAO {
	private static Logger logger = Logger.getLogger(ProgrammaDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgramma prog = QDmalmProgramma.dmalmProgramma;

	public static List<DmalmProgramma> getAllProgramma(Timestamp dataEsecuzione)
			throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProgramma bean = null;
		List<DmalmProgramma> programmi = new ArrayList<DmalmProgramma>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.programma));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGRAMMA);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmProgramma();

				bean.setCdProgramma(rs.getString("CD_PROGRAMMA"));
				bean.setDescrizioneProgramma(rs
						.getString("DESCRIZIONE_PROGRAMMA"));
				bean.setDmalmProgrammaPk(rs.getLong("DMALM_PROGRAMMA_PK"));
				bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreProgramma(rs.getString("NOME_AUTORE_PROGRAMMA"));
				bean.setDtCaricamentoProgramma(dataEsecuzione);
				bean.setDtCreazioneProgramma(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaProgramma(rs
						.getTimestamp("DATA_MODIFICA_PROGRAMMA"));
				bean.setDtRisoluzioneProgramma(rs
						.getTimestamp("DATA_RISOLUZIONE_PROGRAMMA"));
				bean.setDtScadenzaProgramma(rs
						.getTimestamp("DATA_SCADENZA_PROGRAMMA"));
				bean.setIdAutoreProgramma(rs.getString("ID_AUTORE_PROGRAMMA"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setMotivoRisoluzioneProgramma(rs
						.getString("MOTIVO_RISOLUZIONE_PROGRAMMA"));
				bean.setNumeroLinea(rs.getString("CF_COD_INTERVENTO_PROGRAMMA"));
				bean.setNumeroTestata(rs
						.getString("CF_COD_INTERVENTO_PROGRAMMA"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PROGRAMMA_PK"));
				bean.setTitoloProgramma(rs.getString("TITOLO_PROGRAMMA"));
				bean.setCfContratto(rs.getString("CONTRATTO_PROGRAMMA"));
				bean.setCfServiceManager(rs
						.getString("SERVICE_MANAGER_PROGRAMMA"));
				bean.setCfReferenteRegionale(rs
						.getString("REFERENTE_REGIONALE_PROGRAMMA"));
				bean.setCfTipologia(rs.getString("TIPOLOGIA_PROGRAMMA"));
				
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));

				programmi.add(bean);
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

		return programmi;
	}

	public static List<Tuple> getProgramma(DmalmProgramma programma)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> programmi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			programmi = query
					.from(prog)
					.where(prog.cdProgramma.equalsIgnoreCase(programma
							.getCdProgramma()))
					.where(prog.idRepository.equalsIgnoreCase(programma
							.getIdRepository()))
					.where(prog.rankStatoProgramma.eq(new Double(1)))
					.list(prog.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return programmi;

	}

	public static void insertProgramma(DmalmProgramma program)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, prog)
					.columns(prog.assignee, prog.cdProgramma,
							prog.descrizioneProgramma, prog.dmalmProgrammaPk,
							prog.dmalmProjectFk02, prog.dmalmStatoWorkitemFk03,
							prog.dmalmStrutturaOrgFk01, prog.dmalmTempoFk04,
							prog.dsAutoreProgramma,
							prog.dtCambioStatoProgramma,
							prog.dtCaricamentoProgramma,
							prog.dtCreazioneProgramma,
							prog.dtModificaProgramma,
							prog.dtRisoluzioneProgramma,
							prog.dtScadenzaProgramma, prog.dtStoricizzazione,
							prog.idAutoreProgramma, prog.idRepository,
							prog.motivoRisoluzioneProgramma, prog.numeroLinea,
							prog.numeroTestata, prog.rankStatoProgramma,
							prog.titoloProgramma, prog.cfContratto,
							prog.cfReferenteRegionale, prog.cfServiceManager,
							prog.cfTipologia, prog.stgPk, prog.dmalmUserFk06,
							prog.codice, prog.uri, prog.dtAnnullamento,
							prog.severity, prog.priority,
							prog.tsTagAlm,prog.tagAlm)
					.values(program.getAssignee(), program.getCdProgramma(),
							program.getDescrizioneProgramma(),
							program.getDmalmProgrammaPk(),
							program.getDmalmProjectFk02(),
							program.getDmalmStatoWorkitemFk03(),
							program.getDmalmStrutturaOrgFk01(),
							program.getDmalmTempoFk04(),
							program.getDsAutoreProgramma(),
							program.getDtCambioStatoProgramma(),
							program.getDtCaricamentoProgramma(),
							program.getDtCreazioneProgramma(),
							program.getDtModificaProgramma(),
							program.getDtRisoluzioneProgramma(),
							program.getDtScadenzaProgramma(),
							program.getDtModificaProgramma(),
							program.getIdAutoreProgramma(),
							program.getIdRepository(),
							program.getMotivoRisoluzioneProgramma(),
							program.getNumeroLinea(),
							program.getNumeroTestata(), new Double(1),
							program.getTitoloProgramma(),
							program.getCfContratto(),
							program.getCfReferenteRegionale(),
							program.getCfServiceManager(),
							program.getCfTipologia(), program.getStgPk(),
							program.getDmalmUserFk06(), program.getCodice(),
							program.getUri(), program.getDtAnnullamento(),
							//DM_ALM-320
							program.getSeverity(), program.getPriority(),
							program.getTsTagAlm(),program.getTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmProgramma program, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.cdProgramma.equalsIgnoreCase(program
							.getCdProgramma()))
					.where(prog.idRepository.equalsIgnoreCase(program
							.getIdRepository()))
					.set(prog.rankStatoProgramma, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertProgrammaUpdate(Timestamp dataEsecuzione,
			DmalmProgramma program, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, prog)
					.columns(prog.assignee, prog.cdProgramma,
							prog.descrizioneProgramma, prog.dmalmProgrammaPk,
							prog.dmalmProjectFk02, prog.dmalmStatoWorkitemFk03,
							prog.dmalmStrutturaOrgFk01, prog.dmalmTempoFk04,
							prog.dsAutoreProgramma,
							prog.dtCambioStatoProgramma,
							prog.dtCaricamentoProgramma,
							prog.dtCreazioneProgramma,
							prog.dtModificaProgramma,
							prog.dtRisoluzioneProgramma,
							prog.dtScadenzaProgramma, prog.dtStoricizzazione,
							prog.idAutoreProgramma, prog.idRepository,
							prog.motivoRisoluzioneProgramma, prog.numeroLinea,
							prog.numeroTestata, prog.rankStatoProgramma,
							prog.titoloProgramma, prog.cfContratto,
							prog.cfReferenteRegionale, prog.cfServiceManager,
							prog.cfTipologia, prog.stgPk, prog.dmalmUserFk06,
							prog.codice, prog.uri, prog.dtAnnullamento,
							prog.changed, prog.annullato,
							prog.severity, prog.priority,
							prog.tsTagAlm,prog.tagAlm)
					.values(program.getAssignee(),
							program.getCdProgramma(),
							program.getDescrizioneProgramma(),
							pkValue == true ? program.getDmalmProgrammaPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							program.getDmalmProjectFk02(),
							program.getDmalmStatoWorkitemFk03(),
							program.getDmalmStrutturaOrgFk01(),
							program.getDmalmTempoFk04(),
							program.getDsAutoreProgramma(),
							program.getDtCambioStatoProgramma(),
							program.getDtCaricamentoProgramma(),
							program.getDtCreazioneProgramma(),
							program.getDtModificaProgramma(),
							program.getDtRisoluzioneProgramma(),
							program.getDtScadenzaProgramma(),
							pkValue == true ? program.getDtModificaProgramma()
									: program.getDtStoricizzazione(),
							program.getIdAutoreProgramma(),
							program.getIdRepository(),
							program.getMotivoRisoluzioneProgramma(),
							program.getNumeroLinea(),
							program.getNumeroTestata(), 
							pkValue == true ? new Short("1")  : program.getRankStatoProgramma(),
							program.getTitoloProgramma(),
							program.getCfContratto(),
							program.getCfReferenteRegionale(),
							program.getCfServiceManager(),
							program.getCfTipologia(), program.getStgPk(),
							program.getDmalmUserFk06(), program.getCodice(),
							program.getUri(), program.getDtAnnullamento(),
							program.getChanged(), program.getAnnullato(),
							//DM_ALM-320
							program.getSeverity(), program.getPriority(),
							program.getTsTagAlm(),program.getTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateProgramma(DmalmProgramma program)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.cdProgramma.equalsIgnoreCase(program
							.getCdProgramma()))
					.where(prog.idRepository.equalsIgnoreCase(program
							.getIdRepository()))
					.where(prog.rankStatoProgramma.eq(new Double(1)))
					.set(prog.cdProgramma, program.getCdProgramma())
					.set(prog.descrizioneProgramma,
							program.getDescrizioneProgramma())
					.set(prog.dmalmProjectFk02, program.getDmalmProjectFk02())
					.set(prog.dmalmStatoWorkitemFk03,
							program.getDmalmStatoWorkitemFk03())
					.set(prog.dmalmStrutturaOrgFk01,
							program.getDmalmStrutturaOrgFk01())
					.set(prog.dmalmTempoFk04, program.getDmalmTempoFk04())
					.set(prog.dsAutoreProgramma, program.getDsAutoreProgramma())
					.set(prog.dtCreazioneProgramma,
							program.getDtCreazioneProgramma())
					.set(prog.dtModificaProgramma,
							program.getDtModificaProgramma())
					.set(prog.dtRisoluzioneProgramma,
							program.getDtRisoluzioneProgramma())
					.set(prog.dtScadenzaProgramma,
							program.getDtScadenzaProgramma())
					.set(prog.idAutoreProgramma, program.getIdAutoreProgramma())
					.set(prog.idRepository, program.getIdRepository())
					.set(prog.motivoRisoluzioneProgramma,
							program.getMotivoRisoluzioneProgramma())
					.set(prog.numeroLinea, program.getNumeroLinea())
					.set(prog.numeroTestata, program.getNumeroTestata())
					.set(prog.titoloProgramma, program.getTitoloProgramma())
					.set(prog.cfContratto, program.getCfContratto())
					.set(prog.cfReferenteRegionale,
							program.getCfReferenteRegionale())
					.set(prog.cfServiceManager, program.getCfServiceManager())
					.set(prog.cfTipologia, program.getCfTipologia())
					.set(prog.stgPk, program.getStgPk())
					.set(prog.uri, program.getUri())
					.set(prog.codice, program.getCodice())
					.set(prog.dtAnnullamento, program.getDtAnnullamento())
					.set(prog.annullato, program.getAnnullato())
					.set(prog.severity, program.getSeverity())
					//DM_ALM-320
					.set(prog.priority, program.getPriority())
					.set(prog.tagAlm, program.getTagAlm())
					.set(prog.tsTagAlm, program.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmProgramma getProgramma(Long pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> programmi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			programmi = query.from(prog).where(prog.dmalmProgrammaPk.eq(pk))
					.list(prog.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (programmi.size() > 0) {

			Tuple t = programmi.get(0);
			DmalmProgramma p = new DmalmProgramma();

			p.setAnnullato(t.get(prog.annullato));
			p.setAssignee(t.get(prog.assignee));
			p.setCdProgramma(t.get(prog.cdProgramma));
			p.setCfContratto(t.get(prog.cfContratto));
			p.setCfReferenteRegionale(t.get(prog.cfReferenteRegionale));
			p.setCfServiceManager(t.get(prog.cfServiceManager));
			p.setCfTipologia(t.get(prog.cfTipologia));
			p.setChanged(t.get(prog.changed));
			p.setCodice(t.get(prog.codice));
			p.setDescrizioneProgramma(t.get(prog.descrizioneProgramma));
			p.setDmalmProgrammaPk(t.get(prog.dmalmProgrammaPk));
			p.setDmalmProjectFk02(t.get(prog.dmalmProjectFk02));
			p.setDmalmStatoWorkitemFk03(t.get(prog.dmalmStatoWorkitemFk03));
			p.setDmalmStrutturaOrgFk01(t.get(prog.dmalmStrutturaOrgFk01));
			p.setDmalmTempoFk04(t.get(prog.dmalmTempoFk04));
			p.setDmalmUserFk06(t.get(prog.dmalmUserFk06));
			p.setDsAutoreProgramma(t.get(prog.dsAutoreProgramma));
			p.setDtAnnullamento(t.get(prog.dtAnnullamento));
			p.setDtCambioStatoProgramma(t.get(prog.dtCambioStatoProgramma));
			p.setDtCaricamentoProgramma(t.get(prog.dtCaricamentoProgramma));
			p.setDtCreazioneProgramma(t.get(prog.dtCreazioneProgramma));
			p.setDtModificaProgramma(t.get(prog.dtModificaProgramma));
			p.setDtRisoluzioneProgramma(t.get(prog.dtRisoluzioneProgramma));
			p.setDtScadenzaProgramma(t.get(prog.dtScadenzaProgramma));
			p.setDtStoricizzazione(t.get(prog.dtStoricizzazione));
			p.setIdAutoreProgramma(t.get(prog.idAutoreProgramma));
			p.setIdRepository(t.get(prog.idRepository));
			p.setMotivoRisoluzioneProgramma(t
					.get(prog.motivoRisoluzioneProgramma));
			p.setNumeroLinea(t.get(prog.numeroLinea));
			p.setNumeroTestata(t.get(prog.numeroTestata));
			p.setRankStatoProgramma(t.get(prog.rankStatoProgramma));
			p.setRankStatoProgrammaMese(t.get(prog.rankStatoProgrammaMese));
			p.setStgPk(t.get(prog.stgPk));
			p.setTitoloProgramma(t.get(prog.titoloProgramma));
			p.setUri(t.get(prog.uri));
			//DM_ALM-320
			p.setSeverity(t.get(prog.severity));
			p.setPriority(t.get(prog.priority));
			p.setTagAlm(t.get(prog.tagAlm));
			p.setTsTagAlm(t.get(prog.tsTagAlm));
			
			return p;

		} else
			return null;
	}

	public static boolean checkEsistenzaProgramma(DmalmProgramma d,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(prog)
					.where(prog.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(prog.cdProgramma.eq(d.getCdProgramma()))
					.where(prog.idRepository.eq(d.getIdRepository()))
					.list(prog.all());

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
