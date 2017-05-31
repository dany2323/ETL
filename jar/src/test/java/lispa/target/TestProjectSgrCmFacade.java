package lispa.target;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.ProjectUnitaOrganizzativaEccezioniDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.ProjectsCSVExceptionsUtils;

public class TestProjectSgrCmFacade extends TestCase{

	public void testCodiceAreaUO(){
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			
			Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2017-05-31 14:12:00","yyyy-MM-dd HH:mm:00");
			ProjectsCSVExceptionsUtils.importCsv(dataEsecuzione);
			
			// lista delle eccezioni Project/Unita organizzativa
			List<DmalmProjectUnitaOrganizzativaEccezioni> eccezioniProjectUO = ProjectUnitaOrganizzativaEccezioniDAO
					.getAllProjectUOException();
			
			List<DmalmProject> staging_projects = ProjectSgrCmDAO.getAllProject(dataEsecuzione);
			for (DmalmProject project : staging_projects) {
				if(ProjectsCSVExceptionsUtils.isAnException(project.getIdProject(),project.getIdRepository())){
					System.out.println(ProjectSgrCmDAO.gestioneCodiceAreaUO(
							eccezioniProjectUO, project.getIdProject(),
							project.getIdRepository(),
							project.getNomeCompletoProject(),
							project.getcTemplate(),
							project.getFkProjectgroup(), dataEsecuzione, true));
				}
				
			}
			
			
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
