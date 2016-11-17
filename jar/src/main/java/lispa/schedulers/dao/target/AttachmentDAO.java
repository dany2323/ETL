package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_ATTACHMENT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmAttachment;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmAttachment;
import lispa.schedulers.utils.QueryUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class AttachmentDAO {

	private static Logger logger = Logger.getLogger(AttachmentDAO.class);

	private static QDmalmAttachment attach = QDmalmAttachment.dmalmAttachment;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmAttachment> getAllAttachment(
			Timestamp dataEsecuzione) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmAttachment bean = null;
		List<DmalmAttachment> attachments = new ArrayList<DmalmAttachment>(
				QueryUtils.getCountAttachments());

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_ATTACHMENT);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmAttachment();

				bean.setCdAttachment(rs.getString("ID_ATTACHMENT"));
				bean.setDimensioneFile(rs.getInt("DIMENSIONE_FILE"));
				bean.setStgPk(rs.getString("FK_WORKITEM"));
				bean.setDmalmWorkitemType(rs.getString("TIPO_WORKITEM"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setDtModificaAttachment(rs.getTimestamp("DATA_MODIFICA"));
				bean.setIdProject(rs.getString("ID_PROJECT"));
				bean.setNomeAutore(rs.getString("NOME_AUTORE_ATTACHMENT"));
				bean.setNomeFile(rs.getString("NOME_FILE"));
				bean.setStatoCancellato(rs.getBoolean("STATO_CANCELLATO"));
				bean.setTitolo(rs.getString("TITOLO"));
				bean.setUrl(rs.getString("URL_ATTACHMENT"));

				attachments.add(bean);
			}

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return attachments;

	}

	public static boolean insertAttachment(DmalmAttachment attachment)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			long pk = LinkedWorkitemsDAO.getFactPkByType(
					attachment.getDmalmWorkitemType(), attachment.getStgPk());

			if (pk == 0)
				return false;

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, attach)
					.columns(attach.cdAttachment, attach.dimensioneFile,
							attach.dmalmFkWorkitem01, attach.dmalmWorkitemType,
							attach.dtCaricamento, attach.dtModificaAttachment,
							attach.idProject, attach.nomeAutore,
							attach.nomeFile, attach.statoCancellato,
							attach.titolo, attach.url)
					.values(attachment.getCdAttachment(),
							attachment.getDimensioneFile(), pk,
							attachment.getDmalmWorkitemType(),
							attachment.getDtCaricamento(),
							attachment.getDtModificaAttachment(),
							attachment.getIdProject(),
							attachment.getNomeAutore(),
							attachment.getNomeFile(),
							attachment.getStatoCancellato(),
							attachment.getTitolo(), attachment.getUrl())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return true;

	}

	public static int insertAttachments(Timestamp dataEsecuzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		int res = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_ATTACHMENT);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			res = ps.executeUpdate();

			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return res;
	}

}
