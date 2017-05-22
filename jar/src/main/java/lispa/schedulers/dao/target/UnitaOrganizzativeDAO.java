package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class UnitaOrganizzativeDAO {

	private static Logger logger = Logger.getLogger(UnitaOrganizzativeDAO.class);

	public static Integer FindByCdArea(Timestamp dataEsecuzione, String cdArea)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer pkVal = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			ps = connection.prepareStatement("SELECT DMALM_UNITA_ORG_PK FROM DMALM_EL_UNITA_ORGANIZZATIVE WHERE CD_AREA = ?");
			ps.setString(1, cdArea);
			
			logger.debug("ps executing ");
			rs = ps.executeQuery();
			if(rs.next())
			{
				pkVal = rs.getInt(0);
			}
			else
				pkVal = null;

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
		return pkVal;
	}
}