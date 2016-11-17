package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTempo;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class TempoDAO {

	private static Logger logger = Logger.getLogger(TempoDAO.class);

	public static int getTempoPkBy(Timestamp dtOsservazione)
			throws DAOException
	{

		if (dtOsservazione == null)
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> tempoPk = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmTempo tempo = QDmalmTempo.dmalmTempo;

			tempoPk = query.from(tempo)
					.where(tempo.dtOsservazione.eq(dtOsservazione))
					.list(tempo.dmalmTempoPrimaryKey);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return tempoPk.size() > 0 ? tempoPk.get(0).intValue() : 0;
	}

}