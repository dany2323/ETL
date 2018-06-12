package lispa.elettra;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestElettra extends TestCase{
	
	public void testLinkPersonaleUnitaOrganizzativa() {
		
		
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			
			ResultSet rs = ElettraPersonaleDAO.getAllPersonaleUnitaOrganizzativa();
			
			if(rs == null){
				System.out.println("Il resultSet è null");
				return;
			}
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			  System.out.println("Size: "+rowcount);
			} else
				System.out.println("Il resultSet è vuoto");
			
			while(rs.next()){
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println("***");
			}
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testGetPersonalePk() {
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			
			//System.out.println(ElettraPersonaleDAO.getPersonalePk());
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
