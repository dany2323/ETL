package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateDemand;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class FilieraTemplateDemandDAO {
	private static Logger logger = Logger
			.getLogger(FilieraTemplateDemandDAO.class);

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmFilieraTemplateDemand filieraTemplateDemand = QDmalmFilieraTemplateDemand.dmalmFilieraTemplateDemand;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, filieraTemplateDemand)
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insert(Integer idFiliera, Integer livello,
			Integer sottoLivello, boolean lastWorkitem,
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Integer> fkDocumento = new ArrayList<Integer>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraTemplateDemand filieraTemplateDemand = QDmalmFilieraTemplateDemand.dmalmFilieraTemplateDemand;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);
			fkDocumento = LinkedWorkitemsDAO.getFkDocumento(linkedWorkitem.getFkWiPadre());

			// uriWi padre e figlio sono invertiti nel linkedworkitems, quindi
			// qui sotto sono scritti al contrario

			new SQLInsertClause(connection, dialect, filieraTemplateDemand)
					.columns(filieraTemplateDemand.idFiliera,
							filieraTemplateDemand.livello,
							filieraTemplateDemand.sottoLivello,
							filieraTemplateDemand.fkWi, 
							filieraTemplateDemand.codiceWi,
							filieraTemplateDemand.tipoWi,
							filieraTemplateDemand.idRepository,
							filieraTemplateDemand.uriWi,
							filieraTemplateDemand.codiceProject,
							filieraTemplateDemand.ruolo,
							filieraTemplateDemand.dataCaricamento)
					.values(idFiliera, 
							livello, 
							sottoLivello,
							linkedWorkitem.getFkWiPadre(),
							linkedWorkitem.getCodiceWiPadre(),
							linkedWorkitem.getTipoWiPadre(),
							linkedWorkitem.getIdRepositoryPadre(),
							linkedWorkitem.getUriWiFiglio(),
							linkedWorkitem.getCodiceProjectPadre(),
							linkedWorkitem.getRuolo(),
							DataEsecuzione.getInstance().getDataEsecuzione())
					.execute();
			
			if (fkDocumento != null) {
				FilieraTemplateDocumentiDAO.insert(
						DmAlmConstants.DEMAND, idFiliera, livello,
						linkedWorkitem.getFkWiPadre(), linkedWorkitem.getTipoWiPadre(), fkDocumento);
			}

			if (lastWorkitem) {
				livello++;
				fkDocumento = LinkedWorkitemsDAO.getFkDocumento(linkedWorkitem.getFkWiFiglio());
				
				if(linkedWorkitem.getTipoWiFiglio().equals(linkedWorkitem.getTipoWiPadre())) {
					sottoLivello++;
				}

				new SQLInsertClause(connection, dialect, filieraTemplateDemand)
						.columns(filieraTemplateDemand.idFiliera,
								filieraTemplateDemand.livello,
								filieraTemplateDemand.sottoLivello,
								filieraTemplateDemand.fkWi,
								filieraTemplateDemand.codiceWi,
								filieraTemplateDemand.tipoWi,
								filieraTemplateDemand.idRepository,
								filieraTemplateDemand.uriWi,
								filieraTemplateDemand.codiceProject,
								filieraTemplateDemand.ruolo,
								filieraTemplateDemand.dataCaricamento)
						.values(idFiliera,
								livello,
								sottoLivello,
								linkedWorkitem.getFkWiFiglio(),
								linkedWorkitem.getCodiceWiFiglio(),
								linkedWorkitem.getTipoWiFiglio(),
								linkedWorkitem.getIdRepositoryFiglio(),
								linkedWorkitem.getUriWiPadre(),
								linkedWorkitem.getCodiceProjectFiglio(),
								linkedWorkitem.getRuolo(),
								DataEsecuzione.getInstance().getDataEsecuzione())
						.execute();
				
				if (fkDocumento != null) {
					FilieraTemplateDocumentiDAO.insert(
							DmAlmConstants.DEMAND, idFiliera, livello,
							linkedWorkitem.getFkWiPadre(), linkedWorkitem.getTipoWiFiglio(), fkDocumento);
				}
			}

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}
