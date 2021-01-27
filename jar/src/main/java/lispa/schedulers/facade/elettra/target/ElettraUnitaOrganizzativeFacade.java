package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzative;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeFlatDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzative;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraUnitaOrganizzativeFacade {
	private ElettraUnitaOrganizzativeFacade(){}
	private static Logger logger = Logger
			.getLogger(ElettraUnitaOrganizzativeFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception{
		if (ErrorManager.getInstance().hasError())
			return;
		
		logger.info("START ElettraUnitaOrganizzativeFacade.execute");
		
		fillElettraUnitaOrganizzative(dataEsecuzione);
		
		if (ErrorManager.getInstance().hasError())
			return;
		
		fillElettraUnitaOrganizzativeFlat(dataEsecuzione);
		
		logger.info("STOP ElettraUnitaOrganizzativeFacade.execute");
	}
	
	private static void fillElettraUnitaOrganizzative(Timestamp dataEsecuzione) {
		List<DmalmElUnitaOrganizzative> stagingUnitaorganizzative = new ArrayList<>();
		List<Tuple> targetUnitaorganizzative = new ArrayList<>();
		QDmalmElUnitaOrganizzative qDmalmElUnitaOrganizzative = QDmalmElUnitaOrganizzative.qDmalmElUnitaOrganizzative;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElUnitaOrganizzative unitaorganizzativaTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START fillElettraUnitaOrganizzative");

			stagingUnitaorganizzative = ElettraUnitaOrganizzativeDAO
					.getAllUnitaOrganizzative(dataEsecuzione);

			for (DmalmElUnitaOrganizzative unitaorganizzativa : stagingUnitaorganizzative) {
				unitaorganizzativaTmp = unitaorganizzativa;

				// Ricerco nel db target un record con codiceArea =
				// unita.getCodiceArea e data fine validita max
				targetUnitaorganizzative = ElettraUnitaOrganizzativeDAO
						.getUnitaOrganizzativa(unitaorganizzativa);

				// se non trovo almento un record, inserisco la nuova unita
				// organizzativa nel target
				if (targetUnitaorganizzative.size() == 0) {
					righeNuove++;

					ElettraUnitaOrganizzativeDAO
							.insertUnitaOrganizzativa(unitaorganizzativa);
				} else {
					boolean modificato = false;

					for (Tuple row : targetUnitaorganizzative) {
						if (row != null) {
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.dataInizioValiditaEdma),
											unitaorganizzativa
													.getDataInizioValiditaEdma())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.dataFineValiditaEdma),
											unitaorganizzativa
													.getDataFineValiditaEdma())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.descrizioneArea),
											unitaorganizzativa
													.getDescrizioneArea())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.dataAttivazione),
											unitaorganizzativa
													.getDataAttivazione())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.dataDisattivazione),
											unitaorganizzativa
													.getDataDisattivazione())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.codiceResponsabile),
											unitaorganizzativa
													.getCodiceResponsabile())) {
								modificato = true;
							}

							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.idTipologiaUfficio),
											unitaorganizzativa
													.getIdTipologiaUfficio())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.codiceUOSuperiore),
											unitaorganizzativa
													.getCodiceUOSuperiore())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElUnitaOrganizzative.codiceEnte),
											unitaorganizzativa.getCodiceEnte())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraUnitaOrganizzativeDAO
										.updateDataFineValidita(
												dataEsecuzione,
												row.get(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk));

								// inserisco un nuovo record
								ElettraUnitaOrganizzativeDAO
										.insertUnitaOrganizzativaUpdate(
												dataEsecuzione,
												unitaorganizzativa);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraUnitaOrganizzativeDAO
										.updateUnitaOrganizzativa(
												row.get(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk),
												unitaorganizzativa);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(unitaorganizzativaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(unitaorganizzativaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_ELETTRA_UNITAORGANIZZATIVE,
						stato, new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP fillElettraUnitaOrganizzative");
		}
	}
	
	public static void fillElettraUnitaOrganizzativeFlat(Timestamp dataEsecuzione) {
		try {
			logger.info("START fillElettraUnitaOrganizzativeFlat");
			
			Integer idUnitaOrganizzativaFlat = 0;
			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList = new LinkedList<>();
			
			// ad ogni esecuzione la tabella Flat è svuotata e ricaricata nuovamente
			ElettraUnitaOrganizzativeFlatDAO.delete();
			
			List<DmalmElUnitaOrganizzative> startUnitaOrganizzativeList = ElettraUnitaOrganizzativeDAO.getStartUnitaOrganizzativeFlat();
			if (startUnitaOrganizzativeList.size() > 0) {
				insertedUnitaOrganizzativeList.add(startUnitaOrganizzativeList.get(0));
				idUnitaOrganizzativaFlat = gestisciLista(idUnitaOrganizzativaFlat, startUnitaOrganizzativeList,
						insertedUnitaOrganizzativeList, dataEsecuzione);
			}
			
			logger.info("STOP fillElettraUnitaOrganizzativeFlat");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static Integer gestisciLista(Integer idUnitaOrganizzativaFlat,
			List<DmalmElUnitaOrganizzative> unitaOrganizzativeList,
			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList, Timestamp dataEsecuzione) throws Exception {

//		String unitaOrganizzativeFlat = "";
//		for (DmalmElUnitaOrganizzative insertedUnitaOrganizzativa : insertedUnitaOrganizzativeList) {
//			unitaOrganizzativeFlat+=insertedUnitaOrganizzativa.getCodiceArea() + ";";
//		}
//		
//		System.out.println("Flat: " + unitaOrganizzativeFlat);
		
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
	
//	private static boolean figlioPresente(
//			List<DmalmElUnitaOrganizzative> insertedUnitaOrganizzativeList,
//			DmalmElUnitaOrganizzative unitaOrganizzativa) throws Exception {
//		boolean presente = false;
//
//		for (DmalmElUnitaOrganizzative insertedUnitaOrganizzativa : insertedUnitaOrganizzativeList) {
//			if (unitaOrganizzativa.getUnitaOrganizzativaPk() ==	insertedUnitaOrganizzativa.getUnitaOrganizzativaPk()) {
//
//				presente = true;
//				break;
//			}
//		}
//
//		return presente;
//	}
	
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
//		
//		switch (contatore) {
//        case 1:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk01(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea01(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea01(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 2:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk02(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea02(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea02(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 3:
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk03(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea03(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea03(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 4:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk04(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea04(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea04(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 5:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk05(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea05(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea05(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 6:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk06(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea06(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea06(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 7:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk07(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea07(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea07(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 8:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk08(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea08(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea08(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 9:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk09(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea09(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea09(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 10:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk10(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea10(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea10(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 11:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk11(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea11(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea11(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 12:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk12(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea12(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea12(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 13:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk13(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea13(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea13(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 14:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk14(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea14(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea14(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        case 15:  
//        	unitaOrganizzativaFlat.setUnitaOrganizzativaFk15(lastUnitaOrganizzativa.getUnitaOrganizzativaPk());
//        	unitaOrganizzativaFlat.setCodiceArea15(lastUnitaOrganizzativa.getCodiceArea());
//        	unitaOrganizzativaFlat.setDescrizioneArea15(lastUnitaOrganizzativa.getDescrizioneArea());
//            break;
//        default:  break;
//		}
//
//		if(unitaOrganizzativaFlat.getDataInizioValidita()==null || lastUnitaOrganizzativa.getDataInizioValidita().after(unitaOrganizzativaFlat.getDataInizioValidita())) {
//    		unitaOrganizzativaFlat.setDataInizioValidita(lastUnitaOrganizzativa.getDataInizioValidita());
//    	}
//    	if(unitaOrganizzativaFlat.getDataFineValidita()==null || lastUnitaOrganizzativa.getDataFineValidita().after(unitaOrganizzativaFlat.getDataFineValidita())) {
//    		unitaOrganizzativaFlat.setDataFineValidita(lastUnitaOrganizzativa.getDataFineValidita());
//    	}

    	//se data fine < data inizio è una relazione inconsistente, non va inserita
    	if(!unitaOrganizzativaFlat.getDataFineValidita().before(unitaOrganizzativaFlat.getDataInizioValidita())) {
    		// insert della riga flat
    		ElettraUnitaOrganizzativeFlatDAO.insert(unitaOrganizzativaFlat);
    	}

	}

}
