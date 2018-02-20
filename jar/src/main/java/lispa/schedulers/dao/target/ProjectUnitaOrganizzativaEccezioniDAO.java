package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.QDmalmProjectUnitaOrganizzativaEccezioni;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.Projections;

public class ProjectUnitaOrganizzativaEccezioniDAO {
	private static Logger logger = Logger
			.getLogger(ProjectUnitaOrganizzativaEccezioniDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProjectUnitaOrganizzativaEccezioni projectUnitaOrganizzativaEccezioni = QDmalmProjectUnitaOrganizzativaEccezioni.dmalmProjectUnitaOrganizzativaEccezioni;

	public static List<DmalmProjectUnitaOrganizzativaEccezioni> getAllProjectUOException()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> eccezioniProjectUO = new ArrayList<Tuple>();
		List<DmalmProjectUnitaOrganizzativaEccezioni> resultList = new LinkedList<DmalmProjectUnitaOrganizzativaEccezioni>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			eccezioniProjectUO = query.from(projectUnitaOrganizzativaEccezioni)
					.list(projectUnitaOrganizzativaEccezioni.all());
			
			for (Tuple t : eccezioniProjectUO) {
				DmalmProjectUnitaOrganizzativaEccezioni uoEcc = new DmalmProjectUnitaOrganizzativaEccezioni();
				
				uoEcc.setIdRepository(t.get(projectUnitaOrganizzativaEccezioni.idRepository));
				uoEcc.setNomeCompletoProject(t.get(projectUnitaOrganizzativaEccezioni.nomeCompletoProject));
				uoEcc.setTemplate(t.get(projectUnitaOrganizzativaEccezioni.template));
				uoEcc.setCodiceArea(t.get(projectUnitaOrganizzativaEccezioni.codiceArea)); 
				resultList.add(uoEcc);
			}
			logger.debug("ProjectUnitaOrganizzativaEccezioniDAO - eccezioniProjectUO.size: " + eccezioniProjectUO.size());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
}
