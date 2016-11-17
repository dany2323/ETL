package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmDocumento;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.DocumentoOdsDAO;
import lispa.schedulers.dao.target.fatti.DocumentoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class DocumentoFacade {
	
	private static Logger logger = Logger.getLogger(DocumentoFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		

		List<DmalmDocumento> staging_documenti = new ArrayList<DmalmDocumento>();
		List<Tuple> target_documenti = new ArrayList<Tuple>();
		QDmalmDocumento doc = QDmalmDocumento.dmalmDocumento;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmDocumento documento_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_documenti  = DocumentoDAO.getAllDocumento(dataEsecuzione);
			
			DocumentoOdsDAO.delete();
			
			logger.debug("START -> Popolamento Documento ODS, "+staging_documenti.size()+ " documenti");
			
			DocumentoOdsDAO.insert(staging_documenti, dataEsecuzione);
			
			List<DmalmDocumento> x = DocumentoOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Documento ODS, "+staging_documenti.size()+ " documenti");
			
			for(DmalmDocumento documento : x)
			{   
				
				documento_tmp = documento;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_documenti = DocumentoDAO.getDocumento(documento);

				// se non trovo almento un record, inserisco il project nel target
				if(target_documenti.size()==0)
				{
					righeNuove++;
					documento.setDtCambioStatoDocumento(documento.getDtModificaDocumento());
					DocumentoDAO.insertDocumento(documento);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_documenti)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(doc.dmalmStatoWorkitemFk03), documento.getDmalmStatoWorkitemFk03()))
							{
								documento.setDtCambioStatoDocumento(documento.getDtModificaDocumento());
								modificato = true;
							}
							else {
								documento.setDtCambioStatoDocumento(row.get(doc.dtCambioStatoDocumento));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.numeroTestata), documento.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.dmalmProjectFk02), documento.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.numeroLinea), documento.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.dtScadenzaDocumento), documento.getDtScadenzaDocumento()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.assigneesDocumento), documento.getAssigneesDocumento()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.dmalmUserFk06), documento.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.uri), documento.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(doc.annullato), documento.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								DocumentoDAO.updateRank(documento, new Double(0));

								// inserisco un nuovo record
								DocumentoDAO.insertDocumentoUpdate(dataEsecuzione, documento, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								DocumentoDAO.updateDocumento(documento);
							}
						}
					}
				}
			}
			
//			DocumentoDAO.updateATFK();
//			
//			DocumentoDAO.updateUOFK();
//			
//			DocumentoDAO.updateTempoFK();
//
//			DocumentoDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(documento_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(documento_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_DOCUMENTO, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							righeNuove, 
							righeModificate, 
							0, 
							0
				);	
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);
				
			}
		}

	}

}
