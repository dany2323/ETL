package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import lispa.schedulers.bean.target.elettra.DmAlmSourceElProdEccez;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.utils.DateUtils;

public class DmAlmSourceElProdEccezDAO {
	
	private static Logger logger = Logger
			.getLogger(DmAlmSourceElProdEccezDAO.class);

	private static QDmAlmSourceElProdEccez dmAlmSourceElProd = QDmAlmSourceElProdEccez.dmAlmSourceElProd;
	
	public static List<Tuple> getData() throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			relList = query.from(dmAlmSourceElProd).list(dmAlmSourceElProd.siglaOggettoElettra,dmAlmSourceElProd.tipoElProdEccezione);
					
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		
		return relList;
	}
	
	public static List<Tuple> getRow(String id) throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			
			relList.addAll(query.from(dmAlmSourceElProd).where(dmAlmSourceElProd.siglaOggettoElettra.eq(id)).list(dmAlmSourceElProd.siglaOggettoElettra,dmAlmSourceElProd.tipoElProdEccezione));
					
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		
		return relList;
	}
		
}
