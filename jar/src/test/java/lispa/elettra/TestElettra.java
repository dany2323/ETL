package lispa.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.Projections;
import junit.framework.TestCase;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzative;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeFlatDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;

public class TestElettra extends TestCase{
	
	private static Logger logger = Logger.getLogger(TestElettra.class);
	
	
	public static void testElettraUnitaOrganizzativeFlat() {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-01-23 21:00:00.0", "yyyy-MM-dd HH:mm:00"));
		Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
		
		try {
			Log4JConfiguration.inizialize();
			
			logger.info("START fillElettraUnitaOrganizzativeFlat");
			
			Integer idUnitaOrganizzativaFlat = 0;
			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList = new LinkedList<>();
			
			// ad ogni esecuzione la tabella Flat è svuotata e ricaricata nuovamente
			ElettraUnitaOrganizzativeFlatDAO.delete();
			
			List<DmalmElUnitaOrganizzative> startUnitaOrganizzativeList = getStartUnitaOrganizzativeFlat();
			if (startUnitaOrganizzativeList.size() > 0) {
				insertedUnitaOrganizzativeList.add(startUnitaOrganizzativeList.get(0));
				idUnitaOrganizzativaFlat = gestisciLista(idUnitaOrganizzativaFlat, startUnitaOrganizzativeList,
						insertedUnitaOrganizzativeList, dataEsecuzione);
			}
			
			logger.info("STOP fillElettraUnitaOrganizzativeFlat");
			
			updateFlatProject();
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static Integer gestisciLista(Integer idUnitaOrganizzativaFlat,
			List<DmalmElUnitaOrganizzative> unitaOrganizzativeList,
			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList, Timestamp dataEsecuzione) throws Exception {

		idUnitaOrganizzativaFlat++;
		inserisciLista(idUnitaOrganizzativaFlat, insertedUnitaOrganizzativeList,
				 dataEsecuzione);
		
		
		List<DmalmElUnitaOrganizzative> nextUnitaOrganizzativeList = new LinkedList<>();

		nextUnitaOrganizzativeList = ElettraUnitaOrganizzativeDAO.getNextUnitaOrganizzativeFlat(insertedUnitaOrganizzativeList.get(insertedUnitaOrganizzativeList.size()-1));

			
			for (DmalmElUnitaOrganizzative unitaOrganizzativa : nextUnitaOrganizzativeList) {
				
				insertedUnitaOrganizzativeList.add(unitaOrganizzativa);
				
				idUnitaOrganizzativaFlat = gestisciLista(idUnitaOrganizzativaFlat, nextUnitaOrganizzativeList,
						insertedUnitaOrganizzativeList, dataEsecuzione);
				idUnitaOrganizzativaFlat++;
				

				insertedUnitaOrganizzativeList.remove(unitaOrganizzativa);
			}
	
		return idUnitaOrganizzativaFlat;
	}
	
	private static void inserisciLista(Integer idUnitaOrganizzativaFlat,
			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList,
			Timestamp dataEsecuzione) throws Exception {
		
		if(insertedUnitaOrganizzativeList.size() > 15) {
			String unitaOrganizzativeFlat = "";
			for (DmalmElUnitaOrganizzative insertedUnitaOrganizzativa : insertedUnitaOrganizzativeList) {
				unitaOrganizzativeFlat+=insertedUnitaOrganizzativa.getCodiceArea() + ";";
			}			
			Exception e= new Exception("Superato il limite di 15 Unita Organizzative Flat: " + unitaOrganizzativeFlat);
			
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new Exception (e);
		}
		
		int contatore = 0;
		DmalmElUnitaOrganizzativeFlat unitaOrganizzativaFlat = new DmalmElUnitaOrganizzativeFlat();
		unitaOrganizzativaFlat.setIdFlatPk(idUnitaOrganizzativaFlat);
		unitaOrganizzativaFlat.setDataCaricamento(dataEsecuzione);
		
		for (DmalmElUnitaOrganizzative insertedUnitaOrganizzativa : insertedUnitaOrganizzativeList) {
			contatore++;
			
			switch (contatore) {
            case 1:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk01(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea01(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea01(insertedUnitaOrganizzativa.getDescrizioneArea());
            	unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
        		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
                break;
            case 2:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk02(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea02(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea02(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 3:
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk03(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea03(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea03(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 4:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk04(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea04(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea04(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 5:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk05(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea05(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea05(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 6:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk06(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea06(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea06(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 7:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk07(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea07(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea07(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 8:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk08(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea08(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea08(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 9:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk09(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea09(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea09(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 10:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk10(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea10(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea10(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 11:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk11(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea11(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea11(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
            	}
                break;
            case 12:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk12(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea12(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea12(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 13:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk13(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea13(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea13(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 14:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk14(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea14(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea14(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            case 15:  
            	unitaOrganizzativaFlat.setUnitaOrganizzativaFk15(insertedUnitaOrganizzativa.getUnitaOrganizzativaPk());
            	unitaOrganizzativaFlat.setCodiceArea15(insertedUnitaOrganizzativa.getCodiceArea());
            	unitaOrganizzativaFlat.setDescrizioneArea15(insertedUnitaOrganizzativa.getDescrizioneArea());
//            	if(insertedUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
            		unitaOrganizzativaFlat.setDataInizioValidita(insertedUnitaOrganizzativa.getDataInizioValidita());
//            	}
//            	if(insertedUnitaOrganizzativa.getDataFineValidita().before(unitaOrganizzativaFlat.getDataFineValidita())) {
            		unitaOrganizzativaFlat.setDataFineValidita(insertedUnitaOrganizzativa.getDataFineValidita());
//            	}
                break;
            default:  break;
			}    
		}
		

		contatore++;

    	//se data fine < data inizio è una relazione inconsistente, non va inserita
    	if(!unitaOrganizzativaFlat.getDataFineValidita().before(unitaOrganizzativaFlat.getDataInizioValidita())) {
    		// insert della riga flat
    		ElettraUnitaOrganizzativeFlatDAO.insert(unitaOrganizzativaFlat);
    	}

	}
	
	public static List<DmalmElUnitaOrganizzative> getStartUnitaOrganizzativeFlat() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		
		List<DmalmElUnitaOrganizzative> resultList = new LinkedList<>();
		SQLTemplates dialect = new HSQLDBTemplates();
		QDmalmElUnitaOrganizzative qDmalmElUnitaOrganizzative = QDmalmElUnitaOrganizzative.qDmalmElUnitaOrganizzative;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.codiceArea.eq("ARIA S.P.A."))
					.orderBy(qDmalmElUnitaOrganizzative.dataInizioValidita.asc())
					.list(Projections.bean(DmalmElUnitaOrganizzative.class,
							qDmalmElUnitaOrganizzative.all()));

		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	private static void updateFlatProject() {
		try {
			QueryManager qm = QueryManager.getInstance();

			logger.info("INIZIO Update Project UnitaOrganizzativaFlatFk");
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.M_UPDATE_PROJECT_UOFLATFK,
					DmAlmConstants.M_SEPARATOR);
			logger.info("FINE Update Project UnitaOrganizzativaFlatFk");
		} catch (Exception e) {
			// non viene emesso un errore bloccante in quanto la Fk è
			// recuperabile dopo l'esecuzione
			logger.error(e.getMessage(), e);
		}
	}
}
