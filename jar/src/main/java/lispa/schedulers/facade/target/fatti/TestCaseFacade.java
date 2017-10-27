package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.TestCaseOdsDAO;
import lispa.schedulers.dao.target.fatti.TestCaseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class TestCaseFacade {

private static Logger logger = Logger.getLogger(TestCaseFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmTestcase> staging_testcases = new ArrayList<DmalmTestcase>();
		List<Tuple> target_testcases = new ArrayList<Tuple>();
		QDmalmTestcase tstcs = QDmalmTestcase.dmalmTestcase;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmTestcase testcase_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_testcases  = TestCaseDAO.getAllTestcase(dataEsecuzione);
			
			TestCaseOdsDAO.delete();
			
			logger.debug("START -> Popolamento Testcase ODS, "+staging_testcases.size()+ " testcase");
			
			TestCaseOdsDAO.insert(staging_testcases, dataEsecuzione);
			
			List<DmalmTestcase> x = TestCaseOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Testcase ODS, "+staging_testcases.size()+ " testcase");
			
			for(DmalmTestcase testcase : x)
			{   
				
				testcase_tmp = testcase;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_testcases = TestCaseDAO.getTestCase(testcase);

				// se non trovo almento un record, inserisco il project nel target
				if(target_testcases.size()==0)
				{
					righeNuove++;
					testcase.setDtCambioStatoTestcase(testcase.getDtModificaTestcase());
					TestCaseDAO.insertTestCase(testcase);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_testcases)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(tstcs.dmalmStatoWorkitemFk03), testcase.getDmalmStatoWorkitemFk03()))
							{
								testcase.setDtCambioStatoTestcase(testcase.getDtModificaTestcase());
								modificato = true;
							}
							else {
								testcase.setDtCambioStatoTestcase(row.get(tstcs.dtCambioStatoTestcase));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.numeroTestata), testcase.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.numeroLinea), testcase.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.dtScadenzaTestcase), testcase.getDtScadenzaTestcase()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.descrizioneTestcase), testcase.getDescrizioneTestcase()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.dmalmProjectFk02), testcase.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.dmalmUserFk06), testcase.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.uri), testcase.getUri()))
							{
								modificato = true;
							}
							//DM_ALM-320
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.severity), testcase.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.priority), testcase.getPriority()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tstcs.annullato), testcase.getAnnullato()))
							{
								logger.debug("_______________________________________________----_________________________________________");
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								TestCaseDAO.updateRank(testcase, new Double(0));

								// inserisco un nuovo record
								TestCaseDAO.insertTestCaseUpdate(dataEsecuzione, testcase, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								TestCaseDAO.updateTestCase(testcase);
							}
						}
					}
				}
			}
			
			
//			TestCaseDAO.updateUOFK();
//
//			TestCaseDAO.updateATFK();
//			
//			TestCaseDAO.updateTempoFK();
//
//			TestCaseDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(testcase_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(testcase_tmp));
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
							DmAlmConstants.TARGET_TESTCASE, 
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
