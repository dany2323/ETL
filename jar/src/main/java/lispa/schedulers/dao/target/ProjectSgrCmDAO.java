package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PROJECT;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysema.query.Tuple;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.bean.target.DmalmStrutturaOrganizzativa;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.annullamenti.project.QDmAlmProjectUnmarked;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentProject;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProject;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentProject;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.svn.SIREUserRolesXML;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

public class ProjectSgrCmDAO {

	private static Logger logger = Logger.getLogger(ProjectSgrCmDAO.class);

	private static OracleTemplates dialect = new OracleTemplates();

	private static QDmalmProject proj = QDmalmProject.dmalmProject;
	private static QSireCurrentProject currProjSire = QSireCurrentProject.sireCurrentProject;
	private static QSissCurrentProject currProjSiss = QSissCurrentProject.sissCurrentProject;
	private static QSireHistoryProject qSireHistoryProject = QSireHistoryProject.sireHistoryProject;
	private static QSissHistoryProject qSissHistoryProject = QSissHistoryProject.sissHistoryProject;
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmProjectProdotto projectProdotto = QDmalmProjectProdotto.dmalmProjectProdotto;
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public static List<DmalmProject> getAllProject(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProject bean = null;
		List<DmalmProject> project = new LinkedList<DmalmProject>();
		List<DmalmProjectUnitaOrganizzativaEccezioni> eccezioniProjectUO = new LinkedList<DmalmProjectUnitaOrganizzativaEccezioni>();
		

		try {
			// lista delle eccezioni Project/Unita organizzativa
			eccezioniProjectUO = ProjectUnitaOrganizzativaEccezioniDAO
					.getAllProjectUOException();

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_PROJECT);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);
			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);
			rs = ps.executeQuery();

			logger.debug("ProjectSgrCmDAO.getAllProject - Query eseguita!");

			while (rs.next()) {
				bean = new DmalmProject();

				bean.setDmalmProjectPk(rs.getInt("DMALM_PROJECT_PK"));

				String template = rs.getString("TEMPLATE");
				bean.setcTemplate(template);

				// FK AREA TEMATICA SOLO PER TEMPLATE SVILUPPO
				if (DmAlmConstants.SVILUPPO.equals(template)) {
					bean.setDmalmAreaTematicaFk01(AreaTematicaSgrCmDAO
							.getIdAreaTematicabyCodice(rs
									.getString("ID_PROJECT")));
				} else {
					bean.setDmalmAreaTematicaFk01(0);
				}

				//Edma
				// FK Struttura Organizzativa
				String codiceAreaUOEdma = gestioneCodiceAreaUO(eccezioniProjectUO,
						rs.getString("ID_PROJECT"),
						rs.getString("ID_REPOSITORY"),
						rs.getString("NOME_COMPLETO_PROJECT"),
						rs.getString("TEMPLATE"),
						rs.getString("FK_PROJECTGROUP"), dataEsecuzione, false);

				if (codiceAreaUOEdma.equals(DmAlmConstants.NON_PRESENTE)) {
					bean.setDmalmStrutturaOrgFk02(0);
				} else {
					bean.setDmalmStrutturaOrgFk02(StrutturaOrganizzativaEdmaLispaDAO
							.getIdStrutturaOrganizzativaByCodiceUpdate(
									codiceAreaUOEdma, rs.getTimestamp("C_CREATED")));
				}

				//Elettra
				// FK Unità Organizzativa
				String codiceAreaUOElettra = gestioneCodiceAreaUO(eccezioniProjectUO,
						rs.getString("ID_PROJECT"),
						rs.getString("ID_REPOSITORY"),
						rs.getString("NOME_COMPLETO_PROJECT"),
						rs.getString("TEMPLATE"),
						rs.getString("FK_PROJECTGROUP"), dataEsecuzione, true);
				
				if (codiceAreaUOElettra.equals(DmAlmConstants.NON_PRESENTE)) {
					bean.setDmalmUnitaOrganizzativaFk(0);
				} else {
					// UO Elettra
					bean.setDmalmUnitaOrganizzativaFk(ElettraUnitaOrganizzativeDAO
							.getUnitaOrganizzativaByCodiceArea(codiceAreaUOElettra,
									rs.getTimestamp("C_CREATED")));
				}
				
				bean.setDmalmUnitaOrganizzativaFlatFk(null);
				bean.setFlAttivo(rs.getBoolean("FL_ATTIVO"));
				bean.setIdProject(rs.getString("ID_PROJECT"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setPathProject(rs.getString("PATH_PROJECT"));

				bean.setcCreated(rs.getTimestamp("C_CREATED"));
				
				String servMan = "";
				if (rs.getString("ID_REPOSITORY").equals(DmAlmConstants.REPOSITORY_SIRE)) {
					String urlSire = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SIRE_SVN_URL);
					String nameSire = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
					String pswSire = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SIRE_SVN_PSW);

					SVNRepository repository = SVNRepositoryFactory.create(SVNURL
							.parseURIEncoded(urlSire));
					ISVNAuthenticationManager authManagerSire = SVNWCUtil.createDefaultAuthenticationManager(nameSire,
							pswSire);
					repository.setAuthenticationManager(authManagerSire);
					
					servMan = getServiceManager(rs.getString("ID_REPOSITORY"), rs.getString("ID_PROJECT"), 
							SIREUserRolesXML.getProjectSVNPath(rs.getString("PATH_PROJECT")), -1, repository);
				}
				if (rs.getString("ID_REPOSITORY").equals(DmAlmConstants.REPOSITORY_SISS)) {
					String urlSiss = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SISS_SVN_URL);
					String nameSiss = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
					String pswSiss = DmAlmConfigReader.getInstance().getProperty(
							DmAlmConfigReaderProperties.SISS_SVN_PSW);
					
					SVNRepository repository = SVNRepositoryFactory.create(SVNURL
							.parseURIEncoded(urlSiss));
					ISVNAuthenticationManager authManagerSiss = SVNWCUtil.createDefaultAuthenticationManager(nameSiss,
							pswSiss);
					repository.setAuthenticationManager(authManagerSiss);
					
					servMan = getServiceManager(rs.getString("ID_REPOSITORY"), rs.getString("ID_PROJECT"), 
							SIREUserRolesXML.getProjectSVNPath(rs.getString("PATH_PROJECT")), -1, repository);
				}
				 
				bean.setServiceManagers(servMan);

				bean.setcTrackerprefix(rs.getString("C_TRACKERPREFIX"));

				bean.setcIsLocal(rs.getInt("C_IS_LOCAL"));

				bean.setcPk(rs.getString("C_PK"));

				bean.setFkUriLead(rs.getString("FK_URI_LEAD"));

				bean.setcDeleted(rs.getInt("C_DELETED"));

				bean.setcFinish(rs.getTimestamp("C_FINISH"));

				bean.setcUri(rs.getString("C_URI"));

				bean.setcStart(rs.getTimestamp("C_START"));

				bean.setFkUriProjectgroup(rs.getString("FK_URI_PROJECTGROUP"));

				bean.setcActive(rs.getInt("C_ACTIVE"));

				bean.setFkProjectgroup(rs.getString("FK_PROJECTGROUP"));

				bean.setFkLead(rs.getString("FK_LEAD"));

				bean.setcLockworkrecordsdate(rs
						.getTimestamp("C_LOCKWORKRECORDSDATE"));

				bean.setcRev(rs.getLong("N_REVISION"));

				bean.setcDescription(rs.getString("C_DESCRIPTION"));

				bean.setSiglaProject(rs.getString("SIGLA_PROJECT"));

				bean.setNomeCompletoProject(rs
						.getString("NOME_COMPLETO_PROJECT"));

				bean.setDtCaricamento(rs.getTimestamp("DT_CARICAMENTO"));

				project.add(bean);
			}

			logger.debug("ProjectSgrCmDAO. getAllProject - project.size: "
					+ project.size());

			if (rs != null) {
				logger.debug("rs close");
				rs.close();
			}
			if (ps != null) {
				logger.debug("ps close");
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				logger.debug("connection close");
				cm.closeConnection(connection);
			}
		}

		logger.debug("return");
		return project;
	}

	public static String gestioneCodiceAreaUO(
			List<DmalmProjectUnitaOrganizzativaEccezioni> eccezioniProjectUO,
			String idProject, String idRepository, String nomeProject,
			String template, String projectGroup, Timestamp dataEsecuzione, boolean isElettra
			) throws Exception {
		// Se trova l'eccezione riporta il codice area dell'eccezione altrimenti
		// esegue l'algoritmo di calcolo della UO

		String codiceAreaUO = "";
		
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection con = null;
		try {	
			
			con = cm.getConnectionOracle();
			
			for (DmalmProjectUnitaOrganizzativaEccezioni eccezione : eccezioniProjectUO) {
				if (eccezione.getIdRepository().equals(idRepository)
						&& eccezione.getNomeCompletoProject().equals(nomeProject)
						&& ((eccezione.getTemplate() == null && template == null) || (eccezione
								.getTemplate() != null && eccezione.getTemplate()
								.equals(template)))) {
	
					codiceAreaUO = eccezione.getCodiceArea();
					break;
				}
			}
		

		// Se il project non ha una eccezione
		if (codiceAreaUO.equalsIgnoreCase("") && template !=null) {
			switch (template) {
				case DmAlmConstants.SVILUPPO:
					// Template SVILUPPO
					if (nomeProject == null) {
						codiceAreaUO = DmAlmConstants.NON_PRESENTE;
					} else if (nomeProject.startsWith("SW-")) {
						try {
							if (nomeProject.indexOf("{", 1) != -1
									&& nomeProject.indexOf("}", 1) != -1) {
								String siglaProject = nomeProject.substring(
										nomeProject.indexOf("{", 1) + 1,
										nomeProject.indexOf("}", 1));
								String[] multiSiglaProject = null;
								multiSiglaProject = siglaProject
										.split("\\.\\.");
								String codiceProdotto = multiSiglaProject[0];
								
								List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(codiceProdotto);
								
								if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
									if (codiceProdotto.contains(".")) {
										codiceProdotto = codiceProdotto.substring(
												0, codiceProdotto.indexOf("."));
									}
								}
								if(isElettra) {
									// Elettra
									 List<Tuple> productList = ElettraProdottiArchitettureDAO.getProductByAcronym(codiceProdotto);
									 if (productList.size() == 0) {
										 codiceAreaUO = DmAlmConstants.NON_PRESENTE;
									 } else {
										 if (productList.get(0).get(qDmalmElProdottiArchitetture.codiceAreaProdotto) != null) {
											 codiceAreaUO =	 productList.get(0).get(qDmalmElProdottiArchitetture.codiceAreaProdotto);
										} else {
											codiceAreaUO = DmAlmConstants.NON_PRESENTE;
										}
									 }
								} else {
									// Edma
										List<Tuple> productList = ProdottoDAO.getProductByAcronym(codiceProdotto);
										if (productList.size() == 0) {
											codiceAreaUO = DmAlmConstants.NON_PRESENTE;
										} else {
											List<DmalmStrutturaOrganizzativa> structureList = StrutturaOrganizzativaEdmaLispaDAO
													.getStrutturaOrganizzativaByPrimaryKey(productList
															.get(0)
															.get(dmalmProdotto.dmalmUnitaOrganizzativaFk01));
											if (structureList.size() == 0) {
												codiceAreaUO = DmAlmConstants.NON_PRESENTE;
											} else {
												codiceAreaUO = structureList.get(0)
														.getCdArea();
											}
										}
									}
								} else {
									codiceAreaUO = DmAlmConstants.NON_PRESENTE;
								}
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								codiceAreaUO = DmAlmConstants.NON_PRESENTE;
							}
						} else if (nomeProject.contains("RichiesteSupporto")) {
							try {
								codiceAreaUO = "LI"
										+ nomeProject.substring(nomeProject.indexOf(".")+1,
												nomeProject.length());
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								codiceAreaUO = DmAlmConstants.NON_PRESENTE;
							}
						} else {
							codiceAreaUO = DmAlmConstants.NON_PRESENTE;
						}
						break;
	
					case DmAlmConstants.DEMAND:
						// Template DEMAND e DEMAND2016
						if (nomeProject.indexOf(".", 1) != -1) {
							codiceAreaUO = "LI"
									+ nomeProject.substring(0,
											nomeProject.indexOf(".", 1));
						} else {
							codiceAreaUO = DmAlmConstants.NON_PRESENTE;
						}
						break;
						
					case DmAlmConstants.DEMAND2016:
						if(isElettra)
						{							
							codiceAreaUO = "LIF800";
						}
						else
						{
							// Template DEMAND e DEMAND2016
							if (nomeProject.indexOf(".", 1) != -1) {
								codiceAreaUO = "LI"
										+ nomeProject.substring(0,
												nomeProject.indexOf(".", 1));
							} else {
								codiceAreaUO = DmAlmConstants.NON_PRESENTE;
							}
						}
						break;
	
					case DmAlmConstants.ASSISTENZA:
						// Template ASSISTENZA
						if (nomeProject.startsWith("Assistenza.")) {
							codiceAreaUO = "LI"
									+ nomeProject.substring("Assistenza.".length(),
											nomeProject.length());
						} else {
							codiceAreaUO = DmAlmConstants.NON_PRESENTE;
						}
						break;
	
					case DmAlmConstants.IT:
						if(isElettra)
						{
							codiceAreaUO = "LIW8B6";
						}
						else
						{
							codiceAreaUO = "LIA352";
						}
						
						
						break;
	
					case DmAlmConstants.SERDEP:
						// Template SERDEP
						if (nomeProject.indexOf(".", 1) != -1) {
							codiceAreaUO = "LI"
									+ nomeProject.substring(0,
											nomeProject.indexOf(".", 1));
						} else {
							codiceAreaUO = DmAlmConstants.NON_PRESENTE;
						}
						break;
	
					default:
						if(codiceAreaUO.equals(""))
							codiceAreaUO = DmAlmConstants.NON_PRESENTE;
						break;
					}
				}
			
	
			if (codiceAreaUO.equals(DmAlmConstants.NON_PRESENTE)) {
				try {
					String tabellaFonte = "";
					if (idRepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {
						tabellaFonte = DmAlmConstants.FONTE_SGR_SIRE_HISTORY_PROJECT;
					} else {
						tabellaFonte = DmAlmConstants.FONTE_SGR_SISS_HISTORY_PROJECT;
					}
	
					String record = "";
					record = "[ Id : " + idProject + "§ ";
					record += "Name : " + nomeProject + "§ ";
					record += "Template : " + template + "§ ";
					record += "IdRepository : " + idRepository + "§ ";
					record += "ProjectGroup : " + projectGroup + " ] ";
	
					ErroriCaricamentoDAO.insert(tabellaFonte,
							DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, record,
							DmAlmConstants.WRONG_LINK_PROJECT_UNITAORGANIZZATIVA,
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				} catch (Exception e) {
					logger.error("Exception: " + e.getMessage());
				}
			}
		}
		finally
		{
			if(con != null)
				try {
					cm.closeConnection(con);
				} catch (DAOException e) {
					e.printStackTrace();
				}
		}
		return codiceAreaUO;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmProject project) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			//
			new SQLUpdateClause(connection, dialect, proj)
					.where(proj.idProject.eq(project.getIdProject()))
					.where(proj.idRepository.eq(project.getIdRepository()))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(proj.dtFineValidita,
							DateUtils.addSecondsToTimestamp(
									dataFineValidita, -1)).execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateDataFineValiditaAnnullamento(
			Timestamp dataFineValidita, DmalmProject project)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			//
			new SQLUpdateClause(connection, dialect, proj)
					.where(proj.idProject.eq(project.getIdProject()))
					.where(proj.idRepository.eq(project.getIdRepository()))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(proj.dtFineValidita,
							DateUtils.addSecondsToTimestamp(DataEsecuzione
									.getInstance().getDataEsecuzione(), -1))
					.execute();
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateDmalmProject(DmalmProject project)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, proj)
					.where(proj.idProject.eq(project.getIdProject()))
					.where(proj.idRepository.eq(project.getIdRepository()))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(proj.dmalmAreaTematicaFk01,
							project.getDmalmAreaTematicaFk01())
					.set(proj.flAttivo, project.getFlAttivo())
					.set(proj.idRepository, project.getIdRepository())
					.set(proj.nomeCompletoProject,
							project.getNomeCompletoProject())
					.set(proj.pathProject, project.getPathProject())
					.set(proj.serviceManagers, project.getServiceManagers())
					.set(proj.siglaProject, project.getSiglaProject())
					.set(proj.dmalmStrutturaOrgFk02,
							project.getDmalmStrutturaOrgFk02())
					.set(proj.dmalmUnitaOrganizzativaFk,
							project.getDmalmUnitaOrganizzativaFk())
					.set(proj.cTrackerprefix, project.getcTrackerprefix())
					.set(proj.cIsLocal, project.getcIsLocal())
					.set(proj.cPk, project.getcPk())
					.set(proj.fkUriLead, project.getFkUriLead())
					.set(proj.cDeleted, project.getcDeleted())
					.set(proj.cFinish, project.getcFinish())
					.set(proj.cUri, project.getcUri())
					.set(proj.cStart, project.getcStart())
					.set(proj.fkUriProjectgroup, project.getFkUriProjectgroup())
					.set(proj.cActive, project.getcActive())
					.set(proj.fkProjectgroup, project.getFkProjectgroup())
					.set(proj.fkLead, project.getFkLead())
					.set(proj.cLockworkrecordsdate,
							project.getcLockworkrecordsdate())
					.set(proj.cRev, project.getcRev())
					.set(proj.cDescription, project.getcDescription())
					.set(proj.annullato, project.getAnnullato())
					.set(proj.dtAnnullamento, project.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertProject(DmalmProject project) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, proj)
					.columns(proj.dmalmProjectPrimaryKey,
							proj.dmalmAreaTematicaFk01, proj.dtCaricamento,
							proj.dtInizioValidita, proj.dtFineValidita,
							proj.flAttivo, proj.idProject, proj.idRepository,
							proj.nomeCompletoProject, proj.pathProject,
							proj.serviceManagers, proj.siglaProject,
							proj.dmalmStrutturaOrgFk02,
							proj.dmalmUnitaOrganizzativaFk,
							proj.dmalmUnitaOrganizzativaFlatFk,
							proj.cTemplate,
							proj.cCreated, proj.cTrackerprefix, proj.cIsLocal,
							proj.cPk, proj.fkUriLead, proj.cDeleted,
							proj.cFinish, proj.cUri, proj.cStart,
							proj.fkUriProjectgroup, proj.cActive,
							proj.fkProjectgroup, proj.fkLead,
							proj.cLockworkrecordsdate, proj.cRev,
							proj.cDescription, proj.dtAnnullamento)
					.values(project.getDmalmProjectPk(),
							project.getDmalmAreaTematicaFk01(),
							project.getDtCaricamento(),
							project.getcCreated(),
							DateUtils.setDtFineValidita9999(), // 31/12/9999
							project.getFlAttivo(), project.getIdProject(),
							project.getIdRepository(),
							project.getNomeCompletoProject(),
							project.getPathProject(),
							project.getServiceManagers(),
							project.getSiglaProject(),
							project.getDmalmStrutturaOrgFk02(),
							project.getDmalmUnitaOrganizzativaFk(),
							project.getDmalmUnitaOrganizzativaFlatFk(),
							project.getcTemplate(), project.getcCreated(),
							project.getcTrackerprefix(), project.getcIsLocal(),
							project.getcPk(), project.getFkUriLead(),
							project.getcDeleted(), project.getcFinish(),
							project.getcUri(), project.getcStart(),
							project.getFkUriProjectgroup(),
							project.getcActive(), project.getFkProjectgroup(),
							project.getFkLead(),
							project.getcLockworkrecordsdate(),
							project.getcRev(), project.getcDescription(),
							project.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertProjectUpdate(Timestamp dataEsecuzione,
			DmalmProject project, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, proj)
					.columns(proj.dmalmProjectPrimaryKey,
							proj.dmalmAreaTematicaFk01, proj.dtCaricamento,
							proj.dtInizioValidita, proj.dtFineValidita,
							proj.flAttivo, proj.idProject, proj.idRepository,
							proj.nomeCompletoProject, proj.pathProject,
							proj.serviceManagers, proj.siglaProject,
							proj.dmalmStrutturaOrgFk02,
							proj.dmalmUnitaOrganizzativaFk,
							proj.dmalmUnitaOrganizzativaFlatFk,
							proj.cTemplate,
							proj.cCreated, proj.cTrackerprefix, proj.cIsLocal,
							proj.cPk, proj.fkUriLead, proj.cDeleted,
							proj.cFinish, proj.cUri, proj.cStart,
							proj.fkUriProjectgroup, proj.cActive,
							proj.fkProjectgroup, proj.fkLead,
							proj.cLockworkrecordsdate, proj.cRev,
							proj.cDescription, proj.dtAnnullamento,
							proj.annullato)
					.values(pkValue == true ? project.getDmalmProjectPk()
							: StringTemplate
									.create("HISTORY_PROJECT_SEQ.nextval"),
							project.getDmalmAreaTematicaFk01(),
							project.getDtCaricamento(),
							pkValue == true ? project.getcCreated() : project
									.getDtInizioValidita(),
							DateUtils.setDtFineValidita9999(), // 31/12/9999
							project.getFlAttivo(), project.getIdProject(),
							project.getIdRepository(),
							project.getNomeCompletoProject(),
							project.getPathProject(),
							project.getServiceManagers(),
							project.getSiglaProject(),
							project.getDmalmStrutturaOrgFk02(),
							project.getDmalmUnitaOrganizzativaFk(),
							project.getDmalmUnitaOrganizzativaFlatFk(),
							project.getcTemplate(), project.getcCreated(),
							project.getcTrackerprefix(), project.getcIsLocal(),
							project.getcPk(), project.getFkUriLead(),
							project.getcDeleted(), project.getcFinish(),
							project.getcUri(), project.getcStart(),
							project.getFkUriProjectgroup(),
							project.getcActive(), project.getFkProjectgroup(),
							project.getFkLead(),
							project.getcLockworkrecordsdate(),
							project.getcRev(), project.getcDescription(),
							project.getDtAnnullamento(), project.getAnnullato())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getProject(DmalmProject project)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> projects = new ArrayList<Tuple>();

		try {
			logger.debug(project.getIdProject());
			logger.debug(project.getNomeCompletoProject());
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.idProject.equalsIgnoreCase(project
							.getIdProject()))
					.where(proj.idRepository.equalsIgnoreCase(project
							.getIdRepository()))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999())).list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return projects;
	}

	public static int setAnnullato(String chiave, String tipoAnnullamento,
			String idRepo) throws DAOException {

		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		int numero_di_annullati = 0;
		String sql = null;

		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(true);
			/* IMPOSTO IL FLAG ANNULLATO SUL PROJECT E LE SUE STORICIZZAZIONI */
			switch (tipoAnnullamento) {
			case "UNMARKED":
				sql = QueryManager.getInstance().getQuery(
						DmAlmConstants.SET_ANNULLATO_LOGICAMENTE);
				ps = conn.prepareStatement(sql);

				ps.setString(1, tipoAnnullamento);
				ps.setTimestamp(2, dataEsecuzione);
				ps.setString(3, idRepo);
				ps.setString(4, chiave);

				numero_di_annullati = ps.executeUpdate();
				if (numero_di_annullati != 0) {
					logger.info(tipoAnnullamento + " " + numero_di_annullati
							+ " RECORDs " + idRepo + " CON PATH: " + chiave);
				}

				if (ps != null) {
					ps.close();
				}

				break;
			case "ANNULLATO FISICAMENTE":
				sql = QueryManager.getInstance().getQuery(
						DmAlmConstants.SET_ANNULLATO_FISICAMENTE);
				ps = conn.prepareStatement(sql);
				ps.setString(1, tipoAnnullamento);
				ps.setTimestamp(2, dataEsecuzione);
				ps.setString(3, idRepo);
				ps.setString(4, chiave);
				numero_di_annullati = ps.executeUpdate();
				if (numero_di_annullati != 0) {
					logger.info(tipoAnnullamento + " " + numero_di_annullati
							+ " RECORDs " + idRepo + " CON ID: " + chiave);
				}

				if (ps != null) {
					ps.close();
				}

				break;
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

		return numero_di_annullati;
	}

	public static void setRiattivato(String cId, String repo)
			throws DAOException {

		ConnectionManager cm = null;
		Connection conn = null;

		try {

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();

			conn.setAutoCommit(false);

			/* IMPOSTO IL FLAG UNMARKED SUL PROJECT E LE SUE STORICIZZAZIONI */
			new SQLUpdateClause(conn, dialect, proj)
					.where(proj.idProject.eq(cId))
					.where(proj.idRepository.eq(repo)).setNull(proj.annullato)
					.execute();

			conn.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

	}

	public static List<String> getDeletedProjectsPaths(String idRepo)
			throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		List<String> ids = new ArrayList<String>();
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			switch (idRepo) {
			case DmAlmConstants.REPOSITORY_SIRE:
				sql = QueryManager.getInstance().getQuery(
						DmAlmConfigReaderProperties.SQL_DELETED_SIRE_PROJECTS);
				break;
			case DmAlmConstants.REPOSITORY_SISS:
				sql = QueryManager.getInstance().getQuery(
						DmAlmConfigReaderProperties.SQL_DELETED_SISS_PROJECTS);
				break;
			}
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, DateUtils.setDtFineValidita9999());
			ps.setTimestamp(2, DataEsecuzione.getInstance().getDataEsecuzione());
			rs = ps.executeQuery();

			while (rs.next()) {
				ids.add(rs.getString("ID_PROJECT"));
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

		return ids;
	}

	/**
	 * Un Project “unmarked” non sarà più ricercabile e visualizzabile in
	 * Polarion dagli utilizzatori del sistema, i suoi file rimarranno comunque
	 * visibili all’interno del repository Subversion del sistema. Per
	 * individuare i Project “unmarked”, sarà necessario interpretare il
	 * contenuto di due file di testo, uno per il repository SIRE, l’altro per
	 * il repository SISS
	 * 
	 * @param link
	 *            path al file
	 * @param repo
	 * @return
	 * @throws IOException
	 * @throws DAOException
	 */
	public static List<String> getUnmarkedProjectsPathsFromFile(String link,
			String repo) throws IOException, DAOException {

		BufferedReader in = null;

		List<String> projectLocations = new ArrayList<String>();

		ConnectionManager cm = null;
		Connection connectionOracle = null;

		final String SEPARATOR = "\\|";

		try {

			URL url = new URL(link);
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			cm = ConnectionManager.getInstance();
			connectionOracle = cm.getConnectionOracle();

			QDmAlmProjectUnmarked projectsUnmarked = QDmAlmProjectUnmarked.projectUnmarked;

			String inputLine;
			String[] inputParsed;
			while ((inputLine = in.readLine()) != null) {
				inputParsed = inputLine.split(SEPARATOR);

				switch (inputParsed[0].trim()) {

				case DmAlmConstants.UNMARKED:
					String trackerPrefix = inputParsed[1].trim();
					String projectLocation = inputParsed[2].trim();

					/* aggiungo il path del project alla lista */
					projectLocations.add("default:/" + projectLocation);

					/* e inserisco path e trackerPrefix nella tabella */
					new SQLInsertClause(connectionOracle, dialect,
							projectsUnmarked)
							.columns(projectsUnmarked.cTrackerprefix,
									projectsUnmarked.dataCaricamento,
									projectsUnmarked.path,
									projectsUnmarked.repository)
							.values(trackerPrefix,
									DataEsecuzione.getInstance()
											.getDataEsecuzione(),
									projectLocation, repo).execute();

					break;

				default:
					break;

				}

			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (in != null) {
				in.close();
			}
			if (cm != null) {
				cm.closeConnection(connectionOracle);
			}
		}
		return projectLocations;

	}

	public static List<String> getAlreadyUnmarkedProjects(String id_repo)
			throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<String> paths = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_UNMARKED_PROJECTS);
			ps = conn.prepareStatement(sql);

			ps.setTimestamp(1, DateUtils.setDtFineValidita9999());
			ps.setString(2, id_repo);

			rs = ps.executeQuery();

			while (rs.next()) {
				paths.add(rs.getString("PATH_PROJECT"));
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

		return paths;
	}

	public static void setReactivated(List<Tuple> current,
			List<String> reactivated, String id_repo) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		List<String> id_proj_riattivato = null;

		for (String riattivato : reactivated) {
			try {
				cm = ConnectionManager.getInstance();
				conn = cm.getConnectionOracle();
				
				logger.debug("Riattiva project " + riattivato);
				
				switch (id_repo) {
				case DmAlmConstants.REPOSITORY_SIRE:
					id_proj_riattivato = new SQLQuery(conn, dialect)
							.distinct()
							.from(proj)
							.where(proj.pathProject.eq(riattivato
									+ DmAlmConstants.PROJECT_PATH_SUFFIX))
							.list(proj.idProject);
					if (id_proj_riattivato.size() > 0) {
						for (Tuple t : current) {
							if (t.get(currProjSire.cId).equalsIgnoreCase(
									id_proj_riattivato.get(0))) {
								logger.info("[RIATTIVATO-ID] "
										+ id_proj_riattivato.get(0));

								DmalmProject p = getProjectByID(
										id_proj_riattivato.get(0),
										DmAlmConstants.REPOSITORY_SIRE);
								if (p != null) {
									if (!p.getDtCaricamento().equals(
											DataEsecuzione.getInstance()
													.getDataEsecuzione())) {
										// se non è stato storicizzato oggi storicizzo e inserisco il nuovo "attivo"
										logger.info("[RIATTIVATO-STORICIZZATO] "
												+ p.getDmalmProjectPk());
										updateDataFineValidita(DataEsecuzione
												.getInstance().getDataEsecuzione(),
												p);
										p.setAnnullato(null);
										p.setDtAnnullamento(null);
										insertProjectUpdate(DataEsecuzione
												.getInstance().getDataEsecuzione(),
												p, false);
									} else {
										// se storicizzato oggi lo "attivo"
										logger.info("[RIATTIVATO-31/12/9999]");
										String sql = QueryManager
												.getInstance()
												.getQuery(
														DmAlmConfigReaderProperties.SQL_REACTIVATED_PROJECTS);
										ps = conn.prepareStatement(sql);
										ps.setString(1, id_proj_riattivato.get(0));
										ps.setString(2, id_repo);
										ps.setTimestamp(3,
												DateUtils.setDtFineValidita9999());
										ps.executeUpdate();
									}
								}
								
								if (ps != null) {
									ps.close();
								}
							}
						}
					} else {
						logger.info("[NON RIATTIVATO] - Project " + riattivato + " non trovato");
					}
					
					break;
				case DmAlmConstants.REPOSITORY_SISS:
					id_proj_riattivato = new SQLQuery(conn, dialect)
							.distinct()
							.from(proj)
							.where(proj.pathProject.eq(riattivato
									+ DmAlmConstants.PROJECT_PATH_SUFFIX))
							.list(proj.idProject);
					if (id_proj_riattivato.size() > 0) {
						for (Tuple t : current) {
							if (t.get(currProjSiss.cId).equalsIgnoreCase(
									id_proj_riattivato.get(0))) {
								logger.info("[RIATTIVATO_ID] "
										+ id_proj_riattivato.get(0));
								DmalmProject p = getProjectByID(
										id_proj_riattivato.get(0),
										DmAlmConstants.REPOSITORY_SISS);
								if (p != null) {
									if (!p.getDtCaricamento().equals(
											DataEsecuzione.getInstance()
													.getDataEsecuzione())) {
										// se non è stato storicizzato oggi storicizzo e inserisco il nuovo "attivo"
										logger.info("[RIATTIVATO-STORICIZZATO] "
												+ p.getDmalmProjectPk());
										updateDataFineValidita(DataEsecuzione
												.getInstance().getDataEsecuzione(),
												p);
										p.setAnnullato(null);
										p.setDtAnnullamento(null);
										insertProjectUpdate(DataEsecuzione
												.getInstance().getDataEsecuzione(),
												p, false);
									} else {
										// se storicizzato oggi lo "attivo"
										String sql = QueryManager
												.getInstance()
												.getQuery(
														DmAlmConfigReaderProperties.SQL_REACTIVATED_PROJECTS);
										ps = conn.prepareStatement(sql);
										ps.setString(1, id_proj_riattivato.get(0));
										ps.setString(2, id_repo);
										ps.setTimestamp(3,
												DateUtils.setDtFineValidita9999());
										ps.executeUpdate();
									}
								}
								
								if (ps != null) {
									ps.close();
								}
							}
						}
					} else {
						logger.info("[NON RIATTIVATO] - Project " + riattivato + " non trovato");
					}
					
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				ErrorManager.getInstance().exceptionOccurred(true, e);
			} finally {
				if (cm != null) {
					cm.closeConnection(conn);
				}
			}
		}
	}

	public static List<Tuple> getCurrent(String repository) {
		ConnectionManager cm = null;
		Connection conn = null;
		List<Tuple> currentprojects = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			switch (repository) {
			case DmAlmConstants.REPOSITORY_SIRE:
				currentprojects = new SQLQuery(conn, dialect)
						.from(currProjSire)
						.where(currProjSire.dataCaricamento.eq(DataEsecuzione
								.getInstance().getDataEsecuzione()))
						.list(currProjSire.cId, currProjSire.cLocation);

				break;
			case DmAlmConstants.REPOSITORY_SISS:
				currentprojects = new SQLQuery(conn, dialect)
						.from(currProjSiss)
						.where(currProjSiss.dataCaricamento.eq(DataEsecuzione
								.getInstance().getDataEsecuzione()))
						.list(currProjSiss.cId, currProjSiss.cLocation);
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

		return currentprojects;
	}

	public static List<Tuple> getAllProjectToLinkWithProduct()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Project di tipo Sviluppo con Sigla NOT NULL non associati
			// a Prodotto o associati al record Tappo
			relList = query
					.from(proj)
					.leftJoin(projectProdotto)
					.on(projectProdotto.dmalmProjectPk.eq(
							proj.dmalmProjectPrimaryKey).and(
							projectProdotto.dtFineValidita.eq(DateUtils
									.setDtFineValidita9999())))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(proj.cTemplate.eq("SVILUPPO"))
					// .where(proj.siglaProject.isNotNull())
					.where(projectProdotto.dmalmProdottoSeq.isNull().or(
							(projectProdotto.dmalmProdottoSeq.eq(0))))
					.list(proj.dmalmProjectPrimaryKey, proj.siglaProject,
							proj.nomeCompletoProject,
							projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoSeq,
							projectProdotto.dtInizioValidita,
							projectProdotto.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return relList;
	}

	public static List<Tuple> getAllProjectNotInHistory(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relListSire = new ArrayList<Tuple>();
		List<Tuple> relListSiss = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery querySire = new SQLQuery(connection, dialect);

			// tutti i Project non movimentati in History Sire
			relListSire = querySire
					.from(proj)
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(proj.idRepository.eq(DmAlmConstants.REPOSITORY_SIRE))
					.where(proj.idProject.notIn(new SQLSubQuery()
							.from(qSireHistoryProject)
							.where(qSireHistoryProject.dataCaricamento.eq(
									dataEsecuzione).and(
									qSireHistoryProject.cId.isNotNull()))
							.list(qSireHistoryProject.cId))).list(proj.all());

			SQLQuery querySiss = new SQLQuery(connection, dialect);

			// tutti i Project non movimentati in History Siss
			relListSiss = querySiss
					.from(proj)
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(proj.idRepository.eq(DmAlmConstants.REPOSITORY_SISS))
					.where(proj.idProject.notIn(new SQLSubQuery()
							.from(qSissHistoryProject)
							.where(qSissHistoryProject.dataCaricamento.eq(
									dataEsecuzione).and(
									qSissHistoryProject.cId.isNotNull()))
							.list(qSissHistoryProject.cId))).list(proj.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		relListSire.addAll(relListSiss);

		return relListSire;
	}

	public static List<DmalmProject> getProjectToLinkAndSplit(
			String applicazione, Timestamp inizio, Timestamp fine)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> projects = new ArrayList<Tuple>();
		List<DmalmProject> ret = new ArrayList<DmalmProject>();

		
		
		
		try {
			// caso x.y.z (prodotto.modulo.funzionalita)
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.annullato.isNull())
					.where(proj.siglaProject
							.eq(applicazione)
							.or(proj.siglaProject.like(applicazione + "..%"))
							.or(proj.siglaProject.like("%.." + applicazione
									+ "..%"))
							.or(proj.siglaProject.like("%.." + applicazione)))
					.where(proj.dtInizioValidita
							.between(inizio, fine)
							.or(proj.dtFineValidita.between(inizio, fine))
							.or(proj.dtInizioValidita.before(inizio).and(
									proj.dtFineValidita.after(fine))))
					.list(proj.all());

			if (projects.size() > 0) {
				for (Tuple t : projects) {
					DmalmProject d = new DmalmProject();
					d.setSiglaProject(t.get(proj.siglaProject));
					d.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
					d.setDtInizioValidita(t.get(proj.dtInizioValidita));
					d.setDtFineValidita(t.get(proj.dtFineValidita).toString());
					ret.add(d);
				}
			} else {
				// caso x.y (prodotto.modulo)
				String[] temp = applicazione.split("\\.");
				if (temp.length > 1) {
					applicazione = temp[0].concat(".").concat(temp[1]);
				}

				query = new SQLQuery(connection, dialect);

				projects = query
						.from(proj)
						.where(proj.annullato.isNull())
						.where(proj.siglaProject
								.eq(applicazione)
								.or(proj.siglaProject
										.like(applicazione + "..%"))
								.or(proj.siglaProject.like("%.." + applicazione
										+ "..%"))
								.or(proj.siglaProject
										.like("%.." + applicazione)))
						.where(proj.dtInizioValidita
								.between(inizio, fine)
								.or(proj.dtFineValidita.between(inizio, fine))
								.or(proj.dtInizioValidita.before(inizio).and(
										proj.dtFineValidita.after(fine))))
						.list(proj.all());

				if (projects.size() > 0) {
					for (Tuple t : projects) {
						DmalmProject d = new DmalmProject();
						d.setSiglaProject(t.get(proj.siglaProject));
						d.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
						d.setDtInizioValidita(t.get(proj.dtInizioValidita));
						d.setDtFineValidita(t.get(proj.dtFineValidita)
								.toString());
						ret.add(d);
					}
				} else {
					// caso x (prodotto)

					temp = applicazione.split("\\.");
					applicazione = temp[0];

					query = new SQLQuery(connection, dialect);

					projects = query
							.from(proj)
							.where(proj.annullato.isNull())
							.where(proj.siglaProject
									.eq(applicazione)
									.or(proj.siglaProject.like(applicazione
											+ "..%"))
									.or(proj.siglaProject.like("%.."
											+ applicazione + "..%"))
									.or(proj.siglaProject.like("%.."
											+ applicazione))
									.or(proj.siglaProject.like(applicazione
											+ ".%"))
									.or(proj.siglaProject.like("%.."
											+ applicazione + ".%")))
							.where(proj.dtInizioValidita
									.between(inizio, fine)
									.or(proj.dtFineValidita.between(inizio,
											fine))
									.or(proj.dtInizioValidita.before(inizio)
											.and(proj.dtFineValidita
													.after(fine))))
							.list(proj.all());
				
					if (projects.size() > 0) {
						for (Tuple t : projects) {
							DmalmProject d = new DmalmProject();
							d.setSiglaProject(t.get(proj.siglaProject));
							d.setDmalmProjectPk(t
									.get(proj.dmalmProjectPrimaryKey));
							d.setDtInizioValidita(t.get(proj.dtInizioValidita));
							d.setDtFineValidita(t.get(proj.dtFineValidita)
									.toString());
							ret.add(d);
						}
					} else {
						DmalmProject d = new DmalmProject();
						d.setDmalmProjectPk(0);
						d.setDtInizioValidita(inizio);
						d.setDtFineValidita(fine.toString());
						ret.add(d);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return ret;
	}

	public static DmalmProject getProjectByPath(String path, String repo)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		path = path + "/%";
		List<Tuple> projects = new ArrayList<Tuple>();
		DmalmProject ret = new DmalmProject();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.pathProject.like(path))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(proj.idRepository.eq(repo)).list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			Tuple t = projects.get(0);
			ret.setAnnullato(t.get(proj.annullato));
			ret.setcActive(t.get(proj.cActive));
			ret.setcCreated(t.get(proj.cCreated));
			ret.setcDeleted(t.get(proj.cDeleted));
			ret.setcDescription(t.get(proj.cDescription));
			ret.setcFinish(t.get(proj.cFinish));
			ret.setcIsLocal(t.get(proj.cIsLocal));
			ret.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
			ret.setcPk(t.get(proj.cPk));
			ret.setcRev(t.get(proj.cRev));
			ret.setcStart(t.get(proj.cStart));
			ret.setcTemplate(t.get(proj.cTemplate));
			ret.setcTrackerprefix(t.get(proj.cTrackerprefix));
			ret.setcUri(t.get(proj.cUri));
			ret.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
			ret.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
			ret.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
			ret.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
			ret.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
			ret.setDtAnnullamento(t.get(proj.dtAnnullamento));
			ret.setDtCaricamento(t.get(proj.dtCaricamento));
			ret.setDtFineValidita(t.get(proj.dtFineValidita).toString());
			ret.setDtInizioValidita(t.get(proj.dtInizioValidita));
			ret.setFkLead(t.get(proj.fkLead));
			ret.setFkProjectgroup(t.get(proj.fkProjectgroup));
			ret.setFkUriLead(t.get(proj.fkUriLead));
			ret.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
			ret.setFlAttivo(t.get(proj.flAttivo));
			ret.setIdProject(t.get(proj.idProject));
			ret.setIdRepository(t.get(proj.idRepository));
			ret.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
			ret.setPathProject(t.get(proj.pathProject));
			ret.setServiceManagers(t.get(proj.serviceManagers));
			ret.setSiglaProject(t.get(proj.siglaProject));

			return ret;
		} else {
			return null;
		}
	}

	public static DmalmProject getProjectByID(String id, String repo)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		DmalmProject ret = new DmalmProject();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query.from(proj)
					.where(proj.idProject.eq(id))
					.where(proj.idRepository.eq(repo))
					.orderBy(proj.dtFineValidita.desc())
					.list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			Tuple t = projects.get(0);
			ret.setAnnullato(t.get(proj.annullato));
			ret.setcActive(t.get(proj.cActive));
			ret.setcCreated(t.get(proj.cCreated));
			ret.setcDeleted(t.get(proj.cDeleted));
			ret.setcDescription(t.get(proj.cDescription));
			ret.setcFinish(t.get(proj.cFinish));
			ret.setcIsLocal(t.get(proj.cIsLocal));
			ret.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
			ret.setcPk(t.get(proj.cPk));
			ret.setcRev(t.get(proj.cRev));
			ret.setcStart(t.get(proj.cStart));
			ret.setcTemplate(t.get(proj.cTemplate));
			ret.setcTrackerprefix(t.get(proj.cTrackerprefix));
			ret.setcUri(t.get(proj.cUri));
			ret.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
			ret.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
			ret.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
			ret.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
			ret.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
			ret.setDtAnnullamento(t.get(proj.dtAnnullamento));
			ret.setDtCaricamento(t.get(proj.dtCaricamento));
			ret.setDtFineValidita(t.get(proj.dtFineValidita).toString());
			ret.setDtInizioValidita(t.get(proj.dtInizioValidita));
			ret.setFkLead(t.get(proj.fkLead));
			ret.setFkProjectgroup(t.get(proj.fkProjectgroup));
			ret.setFkUriLead(t.get(proj.fkUriLead));
			ret.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
			ret.setFlAttivo(t.get(proj.flAttivo));
			ret.setIdProject(t.get(proj.idProject));
			ret.setIdRepository(t.get(proj.idRepository));
			ret.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
			ret.setPathProject(t.get(proj.pathProject));
			ret.setServiceManagers(t.get(proj.serviceManagers));
			ret.setSiglaProject(t.get(proj.siglaProject));

			return ret;
		} else {
			return null;
		}
	}

	public static List<DmalmProject> getProjectToLinkWi(String annullato,
			Timestamp dt_esecuzione) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		List<DmalmProject> ret = new ArrayList<DmalmProject>();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.annullato.eq(annullato))
					.where(proj.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(proj.dtAnnullamento.eq(dt_esecuzione))
					.list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			for (Tuple t : projects) {
				DmalmProject p = new DmalmProject();
				p.setAnnullato(t.get(proj.annullato));
				p.setcActive(t.get(proj.cActive));
				p.setcCreated(t.get(proj.cCreated));
				p.setcDeleted(t.get(proj.cDeleted));
				p.setcDescription(t.get(proj.cDescription));
				p.setcFinish(t.get(proj.cFinish));
				p.setcIsLocal(t.get(proj.cIsLocal));
				p.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
				p.setcPk(t.get(proj.cPk));
				p.setcRev(t.get(proj.cRev));
				p.setcStart(t.get(proj.cStart));
				p.setcTemplate(t.get(proj.cTemplate));
				p.setcTrackerprefix(t.get(proj.cTrackerprefix));
				p.setcUri(t.get(proj.cUri));
				p.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
				p.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
				p.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
				p.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
				p.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
				p.setDtAnnullamento(t.get(proj.dtAnnullamento));
				p.setDtCaricamento(t.get(proj.dtCaricamento));
				p.setDtFineValidita(t.get(proj.dtFineValidita).toString());
				p.setDtInizioValidita(t.get(proj.dtInizioValidita));
				p.setFkLead(t.get(proj.fkLead));
				p.setFkProjectgroup(t.get(proj.fkProjectgroup));
				p.setFkUriLead(t.get(proj.fkUriLead));
				p.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
				p.setFlAttivo(t.get(proj.flAttivo));
				p.setIdProject(t.get(proj.idProject));
				p.setIdRepository(t.get(proj.idRepository));
				p.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
				p.setPathProject(t.get(proj.pathProject));
				p.setServiceManagers(t.get(proj.serviceManagers));
				p.setSiglaProject(t.get(proj.siglaProject));

				ret.add(p);
			}

			return ret;
		} else {
			return null;
		}
	}

	public static DmalmProject getProjectByHistory(String id,
			Timestamp dt_storicizzazione) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		DmalmProject ret = new DmalmProject();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query.from(proj).where(proj.idProject.eq(id))
					.where(proj.dtFineValidita.eq(dt_storicizzazione))
					.list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			Tuple t = projects.get(0);
			ret.setAnnullato(t.get(proj.annullato));
			ret.setcActive(t.get(proj.cActive));
			ret.setcCreated(t.get(proj.cCreated));
			ret.setcDeleted(t.get(proj.cDeleted));
			ret.setcDescription(t.get(proj.cDescription));
			ret.setcFinish(t.get(proj.cFinish));
			ret.setcIsLocal(t.get(proj.cIsLocal));
			ret.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
			ret.setcPk(t.get(proj.cPk));
			ret.setcRev(t.get(proj.cRev));
			ret.setcStart(t.get(proj.cStart));
			ret.setcTemplate(t.get(proj.cTemplate));
			ret.setcTrackerprefix(t.get(proj.cTrackerprefix));
			ret.setcUri(t.get(proj.cUri));
			ret.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
			ret.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
			ret.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
			ret.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
			ret.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
			ret.setDtAnnullamento(t.get(proj.dtAnnullamento));
			ret.setDtCaricamento(t.get(proj.dtCaricamento));
			ret.setDtFineValidita(t.get(proj.dtFineValidita).toString());
			ret.setDtInizioValidita(t.get(proj.dtInizioValidita));
			ret.setFkLead(t.get(proj.fkLead));
			ret.setFkProjectgroup(t.get(proj.fkProjectgroup));
			ret.setFkUriLead(t.get(proj.fkUriLead));
			ret.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
			ret.setFlAttivo(t.get(proj.flAttivo));
			ret.setIdProject(t.get(proj.idProject));
			ret.setIdRepository(t.get(proj.idRepository));
			ret.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
			ret.setPathProject(t.get(proj.pathProject));
			ret.setServiceManagers(t.get(proj.serviceManagers));
			ret.setSiglaProject(t.get(proj.siglaProject));

			return ret;
		} else {
			return null;
		}
	}

	public static List<DmalmProject> getHistoryProject(String idProject,
			String repo, Timestamp dataChiusura) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		List<DmalmProject> ret = new ArrayList<DmalmProject>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query.from(proj).where(proj.idProject.eq(idProject))
					//.where(proj.dtFineValidita.eq(dataChiusura))
					.where(proj.idRepository.eq(repo)).list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			for (Tuple t : projects) {
				DmalmProject p = new DmalmProject();
				p.setAnnullato(t.get(proj.annullato));
				p.setcActive(t.get(proj.cActive));
				p.setcCreated(t.get(proj.cCreated));
				p.setcDeleted(t.get(proj.cDeleted));
				p.setcDescription(t.get(proj.cDescription));
				p.setcFinish(t.get(proj.cFinish));
				p.setcIsLocal(t.get(proj.cIsLocal));
				p.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
				p.setcPk(t.get(proj.cPk));
				p.setcRev(t.get(proj.cRev));
				p.setcStart(t.get(proj.cStart));
				p.setcTemplate(t.get(proj.cTemplate));
				p.setcTrackerprefix(t.get(proj.cTrackerprefix));
				p.setcUri(t.get(proj.cUri));
				p.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
				p.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
				p.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
				p.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
				p.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
				p.setDtAnnullamento(t.get(proj.dtAnnullamento));
				p.setDtCaricamento(t.get(proj.dtCaricamento));
				p.setDtFineValidita(t.get(proj.dtFineValidita).toString());
				p.setDtInizioValidita(t.get(proj.dtInizioValidita));
				p.setFkLead(t.get(proj.fkLead));
				p.setFkProjectgroup(t.get(proj.fkProjectgroup));
				p.setFkUriLead(t.get(proj.fkUriLead));
				p.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
				p.setFlAttivo(t.get(proj.flAttivo));
				p.setIdProject(t.get(proj.idProject));
				p.setIdRepository(t.get(proj.idRepository));
				p.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
				p.setPathProject(t.get(proj.pathProject));
				p.setServiceManagers(t.get(proj.serviceManagers));
				p.setSiglaProject(t.get(proj.siglaProject));

				ret.add(p);
			}

			return ret;
		} else {
			return null;
		}
	}

	public static List<DmalmProject> getReactivated(List<Tuple> current,
			List<String> reactivated, String id_repo) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		List<String> id_proj_riattivato = null;
		List<DmalmProject> ret = new ArrayList<DmalmProject>();

		for (String riattivato : reactivated) {
			try {
				cm = ConnectionManager.getInstance();
				conn = cm.getConnectionOracle();

				id_proj_riattivato = new SQLQuery(conn, dialect)
						.distinct()
						.from(proj)
						.where(proj.pathProject.eq(riattivato
								+ DmAlmConstants.PROJECT_PATH_SUFFIX))
						.list(proj.idProject);
				if(id_proj_riattivato.size() > 0) {
					for (Tuple t : current) {
						if (id_repo.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SIRE)) {
							if (t.get(currProjSire.cId).equalsIgnoreCase(id_proj_riattivato.get(0))) {
								DmalmProject p = getProjectByID(
										id_proj_riattivato.get(0),
										DmAlmConstants.REPOSITORY_SIRE);

								if (p != null) {
									ret.add(p);
								}
							}
						} else {
							if (t.get(currProjSiss.cId).equalsIgnoreCase(id_proj_riattivato.get(0))) {
								DmalmProject p = getProjectByID(
										id_proj_riattivato.get(0),
										DmAlmConstants.REPOSITORY_SISS);
								if (p != null) {
									ret.add(p);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
				ErrorManager.getInstance().exceptionOccurred(true, e);
			} finally {
				if (cm != null) {
					cm.closeConnection(conn);
				}
			}
		}

		return ret;
	}

	public static List<DmalmProject> getProjectNuovi(Timestamp dataEsecuzione)
			throws DAOException {
		List<DmalmProject> ret = new ArrayList<DmalmProject>();
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query.from(proj)
					.where(proj.dtCaricamento.eq(dataEsecuzione))
					.orderBy(proj.idRepository.asc(), proj.idProject.asc(),proj.dtInizioValidita.asc())
					.list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			for (Tuple t : projects) {
				DmalmProject p = new DmalmProject();
				p.setAnnullato(t.get(proj.annullato));
				p.setcActive(t.get(proj.cActive));
				p.setcCreated(t.get(proj.cCreated));
				p.setcDeleted(t.get(proj.cDeleted));
				p.setcDescription(t.get(proj.cDescription));
				p.setcFinish(t.get(proj.cFinish));
				p.setcIsLocal(t.get(proj.cIsLocal));
				p.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
				p.setcPk(t.get(proj.cPk));
				p.setcRev(t.get(proj.cRev));
				p.setcStart(t.get(proj.cStart));
				p.setcTemplate(t.get(proj.cTemplate));
				p.setcTrackerprefix(t.get(proj.cTrackerprefix));
				p.setcUri(t.get(proj.cUri));
				p.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
				p.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
				p.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
				p.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
				p.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
				p.setDtAnnullamento(t.get(proj.dtAnnullamento));
				p.setDtCaricamento(t.get(proj.dtCaricamento));
				p.setDtFineValidita(t.get(proj.dtFineValidita).toString());
				p.setDtInizioValidita(t.get(proj.dtInizioValidita));
				p.setFkLead(t.get(proj.fkLead));
				p.setFkProjectgroup(t.get(proj.fkProjectgroup));
				p.setFkUriLead(t.get(proj.fkUriLead));
				p.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
				p.setFlAttivo(t.get(proj.flAttivo));
				p.setIdProject(t.get(proj.idProject));
				p.setIdRepository(t.get(proj.idRepository));
				p.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
				p.setPathProject(t.get(proj.pathProject));
				p.setServiceManagers(t.get(proj.serviceManagers));
				p.setSiglaProject(t.get(proj.siglaProject));

				ret.add(p);
			}

			return ret;
		} else {
			return null;
		}
	}
	
	public static String getServiceManager(String myrepo,
			String projectId, String projectLocation, long c_rev, SVNRepository repository) throws Exception {
		
		List<String> serviceManagers = new ArrayList<String>();
		
		Connection connection = null;
		ConnectionManager cm = null;

		String filePath = "";

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			connection.setAutoCommit(false);
			SVNURL root = repository.getRepositoryRoot(true);
			String absolutepath = root + projectLocation;
			projectLocation = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			if(myrepo.equals(DmAlmConstants.REPOSITORY_SIRE)) {
				filePath = projectLocation
					.concat(DmAlmConfigReader
							.getInstance()
							.getProperty(
									DmAlmConfigReaderProperties.SIRE_SVN_USER_ROLES_FILE));
			}
			if (myrepo.equals(DmAlmConstants.REPOSITORY_SISS)) {
				filePath = projectLocation
						.concat(DmAlmConfigReader
								.getInstance()
								.getProperty(
										DmAlmConfigReaderProperties.SISS_SVN_USER_ROLES_FILE));

			}
			SVNNodeKind nodeKind = repository.checkPath(filePath, c_rev);
			SVNProperties fileProperties = null;
			ByteArrayOutputStream baos = null;
			fileProperties = new SVNProperties();

			SVNFileRevision svnfr = new SVNFileRevision(filePath, c_rev, fileProperties, fileProperties);

			baos = new ByteArrayOutputStream();
			if(repository.checkPath(svnfr.getPath(), svnfr.getRevision()) == SVNNodeKind.NONE){
				if(svnfr.getRevision() == -1)
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione HEAD");
				else	
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione " + svnfr.getRevision());
				return "";
			}
			
			repository.getFile(svnfr.getPath(), svnfr.getRevision(),
					fileProperties, baos);
			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String xmlContent = "";
			if (isTextType) {
				xmlContent = baos.toString();
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder()
						.parse(new ByteArrayInputStream(xmlContent
								.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("user");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						NodeList ruoli = eElement.getChildNodes();

						for (int tempruolo = 0; tempruolo < ruoli
								.getLength(); tempruolo++) {

							Node ruolo = ruoli.item(tempruolo);

							if (ruolo.getNodeType() == Node.ELEMENT_NODE) {

								Element el = (Element) ruolo;

								if (el.getAttribute("name").equals("SM")) {
									serviceManagers.add(StringUtils.getMaskedValue(eElement.getAttribute("name")));
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
				
		return StringUtils.ListToString(serviceManagers);
	}

}