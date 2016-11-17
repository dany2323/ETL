package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.bean.target.DmalmUserRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.UserRolesSgrDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmUserRoles;
import lispa.schedulers.utils.BeanUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class UserRolesSgrFacade {

	private static Logger logger = Logger.getLogger(UserRolesSgrFacade.class);
	private static QDmAlmUserRoles stgUserRoles = QDmAlmUserRoles.dmAlmUserRoles;

	/**
	 * gli userRoles vengono presi splittati prima per projectID e poi su queste
	 * collection di liste viene applicato un ulteriore filtro per splittare gli
	 * userRoles con stesso projectID per data_modifica, questo avviene solo al
	 * primo riempimento degli userRoles
	 * 
	 * @param dataEsecuzione
	 */
	public static void execute(Timestamp dataEsecuzione) {
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			for (Tuple row : UserRolesSgrDAO.getDistinctProjectID()) {
//				Collection<List<DmalmUserRolesSgr>> userRolesGroupedByProjID = UserRolesSgrDAO
//						.getAllUserRolesGroupedByProjectIDandRevision(
//								dataEsecuzione,
//								row.get(stgUserRoles.idProject),
//								row.get(stgUserRoles.repository));
//
//				Iterator<List<DmalmUserRolesSgr>> it = userRolesGroupedByProjID
//						.iterator();
//
//				while (it.hasNext()) {
//					List<DmalmUserRolesSgr> listaStaging = it.next();
//					String projId = listaStaging.get(0).getOrigine();
//					String repo = listaStaging.get(0).getRepository();
//					Timestamp c_created = listaStaging.get(0).getDtModifica();
//					int fkProject = UserRolesSgrDAO.getFkProject(projId, repo,	c_created);
//					
//					List<DmalmUserRolesSgr> listaTarget = UserRolesSgrDAO.getUserRolesByProjectID(projId, repo);
//					
//					if (listaTarget.size() == 0) {
//						UserRolesSgrDAO
//								.insertUserRoles(listaStaging, fkProject);
//						righeNuove += listaStaging.size();
//					} else {
//
//						boolean modificato = false;
//
//						if (BeanUtils.areDifferent(fkProject, listaTarget
//								.get(0).getDmalmProjectFk01())) {
//							modificato = true;
//						}
//
//						if (!modificato
//								&& BeanUtils.areDifferentLists(listaStaging,
//										listaTarget)) {
//							modificato = true;
//						}
//
//						if (modificato) {
//							UserRolesSgrDAO.updateDataFineValidita(projId,
//									repo, c_created);
//							
//							UserRolesSgrDAO.insertUserRolesUpdate(listaStaging,
//									fkProject, c_created);
//							
//							righeModificate += listaStaging.size();
//						}
//					}
//				}

				Collection<List<DmalmUserRolesSgr>> userRolesGroupedByProjID = UserRolesSgrDAO
				.getAllUserRolesGroupedByProjectIDandRevision(
						dataEsecuzione,
						row.get(stgUserRoles.idProject),
						row.get(stgUserRoles.repository));
				
				Iterator<List<DmalmUserRolesSgr>> it = userRolesGroupedByProjID.iterator();

				while (it.hasNext()) {
					List<DmalmUserRolesSgr> listaStaging = it.next();
					
					if (listaStaging.size() > 0) {
						List<DmalmUserRolesSgr> listaTarget = UserRolesSgrDAO.getUserRolesByProjectID(row.get(stgUserRoles.idProject),
								row.get(stgUserRoles.repository));
						
						Timestamp c_created = listaStaging.get(0).getDtModifica();
						int fkProject = UserRolesSgrDAO.getFkProject(row.get(stgUserRoles.idProject),
								row.get(stgUserRoles.repository), c_created);
						
						if (listaTarget.size() == 0) {
							//inserisce tutti
							UserRolesSgrDAO.insertUserRoles(listaStaging, fkProject);
							righeNuove += listaStaging.size();
						} else {
							if (BeanUtils.areDifferent(fkProject, listaTarget
									.get(0).getDmalmProjectFk01())) {
								//storicizza e inserisce i nuovi
								UserRolesSgrDAO.updateDataFineValidita(row.get(stgUserRoles.idProject),
										row.get(stgUserRoles.repository), c_created);
								
								UserRolesSgrDAO.insertUserRolesUpdate(listaStaging,
										fkProject, c_created);
								
								righeModificate += listaStaging.size();
							} else {
								//inserisce i nuovi e chiude i vecchi non più presenti
								List<DmalmUserRolesSgr> listaNuovi = new ArrayList<DmalmUserRolesSgr>();
								
								for(DmalmUserRolesSgr userRole : listaStaging)
								{  
									if(!presenteSuLista(userRole, listaTarget)) {
										listaNuovi.add(userRole);
									}
								}
								
								UserRolesSgrDAO.insertUserRolesUpdate(listaNuovi,
										fkProject, c_created);
								righeNuove += listaStaging.size();
								
								for(DmalmUserRolesSgr userRole : listaTarget)
								{  
									if(!presenteSuLista(userRole, listaStaging)) {
										UserRolesSgrDAO.updateDataFineValiditaUserRole(userRole, c_created);
										righeModificate += 1;
									}
								}
							}
						}
					}					
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_USERROLES, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	private static boolean presenteSuLista(DmalmUserRolesSgr utenteRuolo, List<DmalmUserRolesSgr> lista) {
		boolean result = false;
		
		for(DmalmUserRolesSgr userRole : lista)
		{  
			if(userRole.getUserid().equalsIgnoreCase(utenteRuolo.getUserid()) && userRole.getRuolo().equalsIgnoreCase(utenteRuolo.getRuolo())) {
				result = true;
				break;
			}
		}
		
		return result;
	}
}