package lispa.schedulers.facade.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.FilieraTemplateAssFunzionaleDAO;
import lispa.schedulers.dao.target.FilieraTemplateDocumentiDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateAssFunzionale;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateSviluppo;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;


/***
 * Elaborazione post Target: DM_ALM-191 Revisione universo partendo dalla
 * tabella LINKED_WORKITEMS tipologie di workitem da prendere in considerazione
 * per il template Ass Funzionale: 
 * a. Anomalia assistenza (anomalia_assistenza)
 * c. Richieste gestione (richiesta_gestione)
 * x. Documento (documento)
 */
public class CostruzioneFilieraTemplateAssFunzionaleFacade {

	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraTemplateAssFunzionaleFacade.class);

	public static void execute() {
		
		try {

			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraTemplateAssFunzionaleFacade.execute()");

			Integer idFiliera = 0;

			// data inizio filiera: primo Gennaio del 1900
			
			Timestamp dataInizioFiliera = DateUtils.setDtInizioValidita1900();

			logger.info("CostruzioneFilieraTemplateAssFunzionaleFacade - dataInizioFiliera: "
					+ dataInizioFiliera);

			List<DmalmLinkedWorkitems> insertedWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// ad ogni esecuzione la filiera del template Demand è svuotata e
			// ricaricata
			// nuovamente

			FilieraTemplateAssFunzionaleDAO.delete();
			FilieraTemplateDocumentiDAO.delete(DmAlmConstants.ASSISTENZA);

			// creazione filiera template Demand
			List<DmalmLinkedWorkitems> startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateAssFunzionale(dataInizioFiliera);
			logger.debug("CostruzioneFilieraTemplateAssFunzionaleFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			//Gestisci i Work Item orfani
			getWorkItemAssistenzaFuoriFiliera(dataInizioFiliera);
			
			logger.info("STOP CostruzioneFilieraTemplateAssFunzionaleFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void getWorkItemAssistenzaFuoriFiliera(Timestamp dataInizioFiliera) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String anomaliaAssQuery = "select * from DMALM_ANOMALIA_ASSISTENZA d "
								  +"where not exists (select 1 from DMALM_TEMPLATE_ASS_FUNZIONALE t "
								  +"where d.ID_REPOSITORY = t.ID_REPOSITORY "
								  +"and d.URI_ANOMALIA_ASSISTENZA = t.URI_WI)";
		
		String richiestaGestQuery = "select * from DMALM_RICHIESTA_GESTIONE d "
				  +"where not exists (select 1 from DMALM_TEMPLATE_ASS_FUNZIONALE t "
				  +"where d.ID_REPOSITORY = t.ID_REPOSITORY "
				  +"and d.URI_RICHIESTA_GESTIONE = t.URI_WI)";
		
		List<DmalmRichiestaGestione> resultListRichiestaGestione = new LinkedList<DmalmRichiestaGestione>();
		List<DmalmAnomaliaAssistenza> resultListAnomliaAssistenza = new LinkedList<DmalmAnomaliaAssistenza>();
		


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			String queryMaxFieliera = "select max(ID_FILIERA) as MAX_ID from DMALM_TEMPLATE_ASS_FUNZIONALE";
			ps = connection.prepareStatement(queryMaxFieliera,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			rs.first();
			Integer idFiliera = rs.getInt("MAX_ID");
			
			logger.debug("Eseguto la seguente query: "+anomaliaAssQuery);
			ps = connection.prepareStatement(anomaliaAssQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmAnomaliaAssistenza anomaliaAss = new DmalmAnomaliaAssistenza();
				anomaliaAss.setCdAnomaliaAss(rs.getString("CD_ANOMALIA_ASS"));
				anomaliaAss.setDmalmAnomaliaAssPk(rs.getInt("DMALM_ANOMALIA_ASS_PK"));
				anomaliaAss.setIdRepository(rs.getString("ID_REPOSITORY"));
				anomaliaAss.setUri(rs.getString("URI_ANOMALIA_ASSISTENZA"));
				anomaliaAss.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListAnomliaAssistenza.add(anomaliaAss);
			}
			
			logger.debug("Eseguto la seguente query: "+richiestaGestQuery);
			ps = connection.prepareStatement(richiestaGestQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmRichiestaGestione richiestaGestione = new DmalmRichiestaGestione();
				richiestaGestione.setCdRichiestaGest(rs.getString("CD_RICHIESTA_GEST"));
				richiestaGestione.setDmalmRichiestaGestPk(rs.getInt("DMALM_RICHIESTA_GEST_PK"));
				richiestaGestione.setIdRepository(rs.getString("ID_REPOSITORY"));
				richiestaGestione.setUri(rs.getString("URI_RICHIESTA_GESTIONE"));
				richiestaGestione.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListRichiestaGestione.add(richiestaGestione);
			}
			
			// Ora inserisco i Bean delle liste nella tabella DMALM_TEMPLATE_SVILUPPO
			
			QDmalmFilieraTemplateAssFunzionale filiera = QDmalmFilieraTemplateAssFunzionale.dmalmFilieraAssFunzionale;
			SQLTemplates dialect = new HSQLDBTemplates();
			connection.setAutoCommit(false);
			
			for(DmalmAnomaliaAssistenza assistenza: resultListAnomliaAssistenza){
				new SQLInsertClause(connection, dialect, filiera)
				.columns(filiera.idFiliera,
						filiera.livello,
						filiera.sottoLivello,
						filiera.fkWi, 
						filiera.codiceWi,
						filiera.tipoWi,
						filiera.idRepository,
						filiera.uriWi,
						filiera.codiceProject,
						filiera.ruolo,
						filiera.dataCaricamento)
				.values(++idFiliera, 
						1, 
						1,
						assistenza.getDmalmAnomaliaAssPk(),
						assistenza.getCdAnomaliaAss(),
						Workitem_Type.EnumWorkitemType.anomalia_assistenza.toString(),
						assistenza.getIdRepository(),
						assistenza.getUri(),
						getDmalmCodiceProgetto(assistenza.getDmalmProjectFk02()),
						"",
						DataEsecuzione.getInstance().getDataEsecuzione())
				.execute();
			}
			connection.commit();

			for(DmalmRichiestaGestione richiestaGestione: resultListRichiestaGestione){
				new SQLInsertClause(connection, dialect, filiera)
				.columns(filiera.idFiliera,
						filiera.livello,
						filiera.sottoLivello,
						filiera.fkWi, 
						filiera.codiceWi,
						filiera.tipoWi,
						filiera.idRepository,
						filiera.uriWi,
						filiera.codiceProject,
						filiera.ruolo,
						filiera.dataCaricamento)
				.values(++idFiliera, 
						1, 
						1,
						richiestaGestione.getDmalmRichiestaGestPk(),
						richiestaGestione.getCdRichiestaGest(),
						Workitem_Type.EnumWorkitemType.richiesta_gestione.toString(),
						richiestaGestione.getIdRepository(),
						richiestaGestione.getUri(),
						getDmalmCodiceProgetto(richiestaGestione.getDmalmProjectFk02()),
						"",
						DataEsecuzione.getInstance().getDataEsecuzione())
				.execute();
			}
			connection.commit();
		
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
	}

	private static Integer gestisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitemsTemplateAssFunzionale(linkedWorkitem);
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei figli salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciLista(idFiliera, nextWorkitemsList,
						insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// altrimenti se non ho figli inserisco la lista e il wi corrente
				// senza salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList,
						linkedWorkitem);
			}
		}

		return idFiliera;
	}
	
	private static Integer gestisciListaAddWiBuild(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitemsAddBuildTemplate(linkedWorkitem);
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei figli salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciListaAddWiBuild(idFiliera, nextWorkitemsList,
						insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// altrimenti se non ho figli inserisco la lista e il wi corrente
				// senza salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList,
						linkedWorkitem);
			}
		}

		return idFiliera;
	}
	
	private static boolean figlioPresente(
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems linkedWorkitem) throws Exception {
		boolean presente = false;

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			if (linkedWorkitem.getFkWiFiglio().equals(
					insertedWorkitem.getFkWiFiglio())
					|| linkedWorkitem.getFkWiFiglio().equals(
							insertedWorkitem.getFkWiPadre())) {

				presente = true;
				break;
			}
		}

		return presente;
	}

	private static void inserisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems lastWorkitem) throws Exception {

		Integer livello = 0;
		Integer sottoLivello = 1;
		String tipoWiPrecedente = "";

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			livello++;

			if (insertedWorkitem.getTipoWiPadre().equalsIgnoreCase(
					tipoWiPrecedente))
				sottoLivello++;
			else
				sottoLivello = 1;
			
			FilieraTemplateAssFunzionaleDAO.insert(idFiliera, livello, sottoLivello,
					false, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiPadre();
		}

		livello++;

		if (lastWorkitem.getTipoWiPadre().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;
		
		FilieraTemplateAssFunzionaleDAO.insert(idFiliera, livello, sottoLivello,
				true, lastWorkitem);
	}

	private static String getDmalmCodiceProgetto(Integer dmalmProjectFk02) throws DAOException, SQLException {
		String queryMaxFieliera = "select ID_PROJECT from DMALM_PROJECT where DMALM_PROJECT_PK="+dmalmProjectFk02;
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connection = cm.getConnectionOracle();
		PreparedStatement ps = connection.prepareStatement(queryMaxFieliera,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		String codiceProgetto = "";
		
		if(!rs.next()){
			logger.error("Non è stato trovato nessun ProjectId corrispondente all'ID: "+dmalmProjectFk02);
		} else {
			rs.first();
			codiceProgetto = rs.getString("ID_PROJECT");		
		}
		
		rs.close();
		ps.close();
		cm.closeConnection(connection);
		
		return codiceProgetto;
	}

	
}
