package lispa.target;




import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.UserSgrCmFacade;
import lispa.schedulers.utils.DateUtils;

public class TestUser extends TestCase {
	
 
	public static void testInsertUser() throws DAOException, Exception {
		UserSgrCmFacade.execute(DateUtils.stringToTimestamp("2014-10-11 21:00:00.0", "yyyy-MM-dd HH:mm:00"));
	}
}
