package lispa.schedulers.dao.sfera;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsmProdottiArchitetture;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class DmAlmAsmProdottiArchitettureDAO {
	private static Logger logger = Logger
			.getLogger(DmAlmAsmProdottiArchitettureDAO.class);
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmAsmProdottiArchitetture qDmalmAsmProdottiArchitetture = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;

	public static void insertRelAsmProdottiArchitetture(
			Timestamp dataEsecuzione, DmalmAsmProdottiArchitetture relazione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect,
					qDmalmAsmProdottiArchitetture)
					.columns(qDmalmAsmProdottiArchitetture.dmalmAsmPk,
							qDmalmAsmProdottiArchitetture.dmalmProdottoPk,
							qDmalmAsmProdottiArchitetture.dtInizioValidita,
							qDmalmAsmProdottiArchitetture.dtFineValidita)
					.values(relazione.getDmalmAsmPk(),
							relazione.getDmalmProdottoPk(), dataEsecuzione,
							DateUtils.getDtFineValidita9999()).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("insertRelqDmalmAsmProdottiArchitetture() - rel: "
					+ LogUtils.objectToString(relazione));

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void closeRelAsmProdottiArchitetture(
			DmalmAsmProdottiArchitetture relazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLUpdateClause(connection, dialect,
					qDmalmAsmProdottiArchitetture)
					.where(qDmalmAsmProdottiArchitetture.dmalmAsmPk
							.eq(relazione.getDmalmAsmPk()))
					.where(qDmalmAsmProdottiArchitetture.dmalmProdottoPk
							.eq(relazione.getDmalmProdottoPk()))
					.where(qDmalmAsmProdottiArchitetture.dtInizioValidita
							.eq(relazione.getDataInizioValidita()))
					.set(qDmalmAsmProdottiArchitetture.dtFineValidita,
							relazione.getDataFineValidita()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getAllRelationsToCloseCauseProductsClosed()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti chiusi con relazione ad Asm ancora aperta
			asmList = query
					.from(qDmalmElProdottiArchitetture)
					.join(qDmalmAsmProdottiArchitetture)
					.on(qDmalmAsmProdottiArchitetture.dmalmProdottoPk
							.eq(qDmalmElProdottiArchitetture.prodottoPk))
					.where(qDmalmElProdottiArchitetture.dataFineValidita
							.ne(DateUtils.getDtFineValidita9999()))
					.where(qDmalmAsmProdottiArchitetture.dtFineValidita
							.eq(DateUtils.getDtFineValidita9999()))
					.list(qDmalmAsmProdottiArchitetture.dmalmAsmPk,
							qDmalmAsmProdottiArchitetture.dmalmProdottoPk,
							qDmalmAsmProdottiArchitetture.dtInizioValidita,
							qDmalmElProdottiArchitetture.dataFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return asmList;
	}
}
