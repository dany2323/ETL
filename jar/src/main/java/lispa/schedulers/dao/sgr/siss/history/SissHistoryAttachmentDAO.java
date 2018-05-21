package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConstants;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment;
import lispa.schedulers.utils.StringUtils;

public class SissHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment fonteAttachment = SissHistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;

	public static void fillSissHistoryAttachment(long minRevision, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> attachments = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
		long n_righe_inserite = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			attachments = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			attachments = query.from(fonteAttachment)
					.where(fonteAttachment.cRev.gt(minRevision))
					.where(fonteAttachment.cRev.loe(maxRevision))
					.list(
							fonteAttachment.cDeleted,
							fonteAttachment.cFilename,
							fonteAttachment.cId,
							StringTemplate.create("0 as c_is_local"),
							fonteAttachment.cLength,
							fonteAttachment.cUri,
							fonteAttachment.cRev,
							fonteAttachment.cTitle,
							fonteAttachment.cUpdated,
							fonteAttachment.cUrl,
							fonteAttachment.fkUriAuthor,
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_author) as fk_rev_author"),
							fonteAttachment.fkUriProject,
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSissHistory() + ".project where project.c_pk = fk_project) as fk_rev_project"),
							fonteAttachment.fkUriWorkitem,
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSissHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_rev_workitem")
							);

			logger.debug("fillSissHistoryAttachment - attachments.size: "+ (attachments != null ? attachments.size() : 0));
			
			Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgAttachment);
			
			for (Tuple row : attachments) {

				Object[] vals = row.toArray();
				String cUri = vals[5] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[5].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[5].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String cPk = cUri+"%"+ (vals[6] != null ? vals[6].toString() : "");
				String fkUriAuthor= vals[10] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[10].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[10].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkUriProject = vals[12] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[12].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[12].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkUriWorkitem = vals[14] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[14].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[14].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkAuthor = fkUriAuthor+"%"+(vals[11] != null ? vals[6].toString() : "");
				String fkProject = fkUriProject+"%"+(vals[13] != null ? vals[6].toString() : "");
				String fkWorkitem = fkUriWorkitem+"%"+(vals[15] != null ? vals[6].toString() : "");
				
				//Applico il cast a timespent solo se esistono dei valori data 
				StringExpression dateValue = null;
				if(vals[8] != null) {
					dateValue = StringTemplate.create("to_timestamp('"+vals[8]+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
				}

				new SQLInsertClause(connOracle, dialect, stgAttachment)
					.columns(
								stgAttachment.cDeleted,
								stgAttachment.cFilename,
								stgAttachment.cId,
								stgAttachment.cIsLocal,
								stgAttachment.cLength,
								stgAttachment.cPk,
								stgAttachment.cRev,
								stgAttachment.cTitle,
								stgAttachment.cUpdated,
								stgAttachment.cUri,
								stgAttachment.cUrl,
								stgAttachment.dataCaricamento,
								stgAttachment.fkAuthor,
								stgAttachment.fkProject,
								stgAttachment.fkUriAuthor,
								stgAttachment.fkUriProject,
								stgAttachment.fkUriWorkitem,
								stgAttachment.fkWorkitem,
								stgAttachment.sissHistoryAttachmentPk
						)
						.values(
								vals[0],
								vals[1],
								vals[2],
								vals[3],
								vals[4],
								cPk,
								vals[6],
								vals[7],
								dateValue,
								cUri,
								vals[10],
								dataEsecuzione,
								StringUtils.getMaskedValue(fkAuthor),
								fkProject,
								StringUtils.getMaskedValue(fkUriAuthor),
								fkUriProject,
								fkUriWorkitem,
								fkWorkitem,
								StringTemplate.create("HISTORY_ATTACHMENT_SEQ.nextval")
										
						).execute();
				
				n_righe_inserite++;
				
//				if (!insert.isEmpty()) {
//					if (n_righe_inserite % lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
//						insert.execute();
//						connOracle.commit();
//						insert = new SQLInsertClause(connOracle, dialect, stgAttachment);
//					}
//				}

			}
			
			connOracle.commit();
//			if (!insert.isEmpty()) {
//				insert.execute();
//				connOracle.commit();
//			}
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(pgConnection);
			if(cm != null) cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgAttachment).list(stgAttachment.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}
	
	public static void recoverSissHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryAttachment stgHistoryAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHistoryAttachment).where(stgHistoryAttachment.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	
	private static SQLQuery queryConnOracle(Connection connOracle, PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}
