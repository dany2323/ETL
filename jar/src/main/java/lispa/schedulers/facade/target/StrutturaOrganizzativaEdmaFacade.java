package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmStrutturaOrganizzativa;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.StrutturaOrganizzativaEdmaLispaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmStrutturaOrganizzativa;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class StrutturaOrganizzativaEdmaFacade {
	
	private static Logger logger = Logger.getLogger(StrutturaOrganizzativaEdmaFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception, DAOException {	

		List<DmalmStrutturaOrganizzativa> staging_strutturaorganizzativa = new ArrayList<DmalmStrutturaOrganizzativa>();
		List<Tuple> target_strutturaorganizzativa = new ArrayList<Tuple>();
		QDmalmStrutturaOrganizzativa struct = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmStrutturaOrganizzativa struttura_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_strutturaorganizzativa  = StrutturaOrganizzativaEdmaLispaDAO.getAllStrutturaOrganizzativa(dataEsecuzione);

			for(DmalmStrutturaOrganizzativa struttura : staging_strutturaorganizzativa)
			{   
				
				struttura_tmp = struttura;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_strutturaorganizzativa = StrutturaOrganizzativaEdmaLispaDAO.getStrutturaOrganizzativa(struttura);

				// se non trovo almento un record, inserisco la nuova struttura organizzativa nel target
				if(target_strutturaorganizzativa.size()==0)
				{
					righeNuove++;

					StrutturaOrganizzativaEdmaLispaDAO.insertStrutturaOrganizzativa(struttura);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_strutturaorganizzativa)
					{
						// aggiorno la data di fine validita del record corrente

						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(struct.dtInizioValiditaEdma), struttura.getDtInizioValiditaEdma()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.dtFineValiditaEdma), struttura.getDtFineValiditaEdma()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.dsAreaEdma), struttura.getDsAreaEdma()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.dtAttivazione), struttura.getDtAttivazione()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.dtDisattivazione), struttura.getDtDisattivazione()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.cdResponsabileArea), struttura.getCdResponsabileArea()))
							{
								modificato = true;
							}

							if(BeanUtils.areDifferent(row.get(struct.idTipologiaUfficio), struttura.getIdTipologiaUfficio()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.cdUoSuperiore), struttura.getCdUoSuperiore()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(struct.cdEnte), struttura.getCdEnte()))
							{
								modificato = true;
							}


							if(modificato)
							{
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								StrutturaOrganizzativaEdmaLispaDAO.updateDataFineValidita(dataEsecuzione, struttura);

								// inserisco un nuovo record
								StrutturaOrganizzativaEdmaLispaDAO.insertStrutturaOrganizzativaUpdate(dataEsecuzione, struttura);
							}
							else
							{
								// Aggiorno 
								StrutturaOrganizzativaEdmaLispaDAO.updateStrutturaOrganizzativa(struttura);
							}
						}

					}
				}

			}

		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(struttura_tmp));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(struttura_tmp));
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
							DmAlmConstants.TARGET_EDMA_UNITAORGANIZZATIVA, 
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