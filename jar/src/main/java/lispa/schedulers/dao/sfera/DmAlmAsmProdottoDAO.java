package lispa.schedulers.dao.sfera;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsmProdotto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdotto;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class DmAlmAsmProdottoDAO {
	private static Logger logger = Logger.getLogger(DmAlmAsmProdottoDAO.class);
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmAsmProdotto asmProdotto = QDmalmAsmProdotto.dmalmAsmProdotto;

	public static void insertRelAsmProdotto(Timestamp dataEsecuzione,
			DmalmAsmProdotto relazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, asmProdotto)
					.columns(asmProdotto.dmalmAsmPk,
							asmProdotto.dmalmProdottoSeq,
							asmProdotto.dtInizioValidita,
							asmProdotto.dtFineValidita)
					.values(relazione.getDmalmAsmPk(),
							relazione.getDmalmProdottoSeq(), dataEsecuzione,
							DateUtils.getDtFineValidita9999()).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("insertRelAsmProdotto() - rel: "
					+ LogUtils.objectToString(relazione));

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void closeRelAsmProdotto(DmalmAsmProdotto relazione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLUpdateClause(connection, dialect, asmProdotto)
					.where(asmProdotto.dmalmAsmPk.eq(relazione.getDmalmAsmPk()))
					.where(asmProdotto.dmalmProdottoSeq.eq(relazione
							.getDmalmProdottoSeq()))
					.where(asmProdotto.dtInizioValidita.eq(relazione
							.getDataInizioValidita()))
					.set(asmProdotto.dtFineValidita,
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
					.from(dmalmProdotto)
					.join(asmProdotto)
					.on(asmProdotto.dmalmProdottoSeq
							.eq(dmalmProdotto.dmalmProdottoSeq))
					.where(dmalmProdotto.dtFineValidita.ne(DateUtils
							.getDtFineValidita9999()))
					.where(asmProdotto.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.list(asmProdotto.dmalmAsmPk, asmProdotto.dmalmProdottoSeq,
							asmProdotto.dtInizioValidita,
							dmalmProdotto.dtFineValidita);
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
