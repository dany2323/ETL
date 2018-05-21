package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConstants;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment;
import lispa.schedulers.utils.StringUtils;

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

public class SireHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment fonteAttachment = SireHistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment stgAttachment = QSireHistoryAttachment.dmalmSireHistoryAttachment;

	public static void fillSireHistoryAttachment(long minRevision, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> attachments = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
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
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".t_user where t_user.c_pk = fk_author) as fk_rev_author"),
							fonteAttachment.fkUriProject,
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".project where project.c_pk = fk_project) as fk_rev_project"),
							fonteAttachment.fkUriWorkitem,
							StringTemplate.create("(select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_rev_workitem")
							);
			
//			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgAttachment);
			int n_righe_inserite = 0;
			
			for (Tuple row : attachments) {

				Object[] val = row.toArray();
				
				String cUri = val[5] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[5].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[5].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String cPk = cUri+"%"+ (val[6] != null ? val[6].toString() : "");
				String fkUriAuthor= val[10] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[10].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[10].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkUriProject = val[12] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[12].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[12].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkUriWorkitem = val[14] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[14].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[14].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkAuthor = fkUriAuthor+"%"+(val[11] != null ? val[6].toString() : "");
				String fkProject = fkUriProject+"%"+(val[13] != null ? val[6].toString() : "");
				String fkWorkitem = fkUriWorkitem+"%"+(val[15] != null ? val[6].toString() : "");
				
				//Applico il cast a timespent solo se esistono dei valori data 
				StringExpression dateValue = null;
				if(val[8] != null) {
					dateValue = StringTemplate.create("to_timestamp('"+val[8]+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
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
								stgAttachment.sireHistoryAttachmentPk
						)
								
						.values(
								val[0],
								val[1],
								val[2],
								val[3],
								val[4],
								cPk,
								val[6],
								val[7],
								dateValue,
								cUri,
								val[10],
								DataEsecuzione.getInstance().getDataEsecuzione(),
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
	
	public static void recoverSireHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryAttachment stgHistoryAttachment = QSireHistoryAttachment.dmalmSireHistoryAttachment;
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