package lispa.elettra;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmProjectUnitaOrganizzativaEccezioni;

public class TestElettra extends TestCase{
	
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProjectUnitaOrganizzativaEccezioni projectUnitaOrganizzativaEccezioni = QDmalmProjectUnitaOrganizzativaEccezioni.dmalmProjectUnitaOrganizzativaEccezioni;
	
//	public void testLinkPersonaleUnitaOrganizzativa() {
//		
//		
//		try {
//			Log4JConfiguration.inizialize();
//			ConnectionManager.getInstance().getConnectionOracle();
//			
//			ResultSet rs = ElettraPersonaleDAO.getAllPersonaleUnitaOrganizzativa();
//			
//			if(rs == null){
//				System.out.println("Il resultSet è null");
//				return;
//			}
//			
//			int rowcount = 0;
//			if (rs.last()) {
//			  rowcount = rs.getRow();
//			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
//			  System.out.println("Size: "+rowcount);
//			} else
//				System.out.println("Il resultSet è vuoto");
//			
//			while(rs.next()){
//				System.out.println(rs.getString(1));
//				System.out.println(rs.getString(2));
//				System.out.println("***");
//			}
//			
//			
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (PropertiesReaderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void testAllProjectUOException()
			throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> eccezioniProjectUO = new ArrayList<Tuple>();
		List<DmalmProjectUnitaOrganizzativaEccezioni> resultList = new LinkedList<DmalmProjectUnitaOrganizzativaEccezioni>();
		try {
			Log4JConfiguration.inizialize();
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
			System.out.println("ProjectUnitaOrganizzativaEccezioniDAO - eccezioniProjectUO.size: " + eccezioniProjectUO.size());
		} catch (Exception e) {
			e.getMessage();
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	
//	public void testGetPersonalePk() {
//		try {
//			Log4JConfiguration.inizialize();
//			ConnectionManager.getInstance().getConnectionOracle();
//			
//			System.out.println(ElettraPersonaleDAO.getPersonalePk());
//			
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (PropertiesReaderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
