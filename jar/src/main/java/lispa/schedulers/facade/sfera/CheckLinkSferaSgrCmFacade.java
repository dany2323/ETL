package lispa.schedulers.facade.sfera;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.sfera.DmAlmMisuraDAO;
import lispa.schedulers.dao.sfera.DmAlmProgettoSferaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;
import lispa.schedulers.utils.MisuraUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkSferaSgrCmFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkSferaSgrCmFacade.class);

	private static QDmalmProgettoSfera prog = QDmalmProgettoSfera.dmalmProgettoSfera;
	private static QDmalmStatoWorkitem statoWorkitem = QDmalmStatoWorkitem.dmalmStatoWorkitem;
	private static QDmalmProgettoSviluppoSvil progSv = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
	private static QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;

	public static void execute(Timestamp dataEsecuzione) {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START CheckLinkSferaSgrCmFacade");

			String nomeprog = null;
			String[] multiWI = null;
			String nomeWI = null;
			List<Tuple> manWiTupla = new ArrayList<Tuple>();
			List<Tuple> sviWiTupla = new ArrayList<Tuple>();

			String WI_modified = null;
			String statoMan = null;
			String statoSvi = null;

			List<Tuple> statiMisura = new ArrayList<Tuple>();
			List<Tuple> datiWIsvi = new ArrayList<Tuple>();
			List<Tuple> datiWIman = new ArrayList<Tuple>();

			List<Tuple> projects = DmAlmProgettoSferaDAO
					.checkLinkProgeSferaWi(dataEsecuzione);

			for (Tuple progetto : projects) {
				if(!progetto.get(prog.nomeProgetto).startsWith("UFFICIOSO-"))
				{
					statoMan = "";
					statoSvi = "";
					WI_modified = progetto.get(prog.nomeProgetto).trim();
					if (progetto.get(prog.nomeProgetto).contains("#")) {
						WI_modified = WI_modified.substring(0,
								progetto.get(prog.nomeProgetto).indexOf("#"));
					}
	
					try {
						multiWI = WI_modified.split("\\*\\*");
						nomeWI= multiWI[0].substring(0, multiWI[0].lastIndexOf("-"));
						for(int i=1;i<multiWI.length;i++)
						{
							multiWI[i]=nomeWI+"-"+multiWI[i];
						}
						
					} catch (Exception e) {
						// gestione errore split per nomenclatura del nome errata
						multiWI = new String[0];
	
						ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.ProgettoSferaToString(progetto),
								"Progetto nome errato impossibile cercare link Sfera/Wi: "
										+ WI_modified,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								MisuraUtils.getPkTarget(DmAlmConstants.PK_TARGET_PROGETTO_SFERA, DmAlmConstants.TARGET_PROGETTO_SFERA, DmAlmConstants.ID_TARGET_PROGETTO_SFERA, Integer.parseInt(String.valueOf(progetto.get(prog.idProgetto)))),
								dataEsecuzione);
					}
	
					for (String wi : multiWI) {
						wi=wi.trim();
						//nomeprog = nomeWI + "-" + wi;
						nomeprog=wi;
						// cerco la pk del workintem di sviluppo
						sviWiTupla = DmAlmProgettoSferaDAO.getPkWorkitemSviluppo(
								progetto.get(prog.dmalmAsmFk), nomeprog);
	
						// cerco la pk del workintem di manutenzione
						manWiTupla = DmAlmProgettoSferaDAO
								.getPkWorkitemManutenzione(
										progetto.get(prog.dmalmAsmFk), nomeprog);
	
						if (sviWiTupla.size() == 0 && manWiTupla.size() == 0) {
							
							ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.ProgettoSferaToString(progetto),
									DmAlmConstants.MANCATA_CORRISPONDENZA_SFERA_WI
									//		+ nomeprog,
											+ MisuraUtils.ProgettoSferaToString(progetto),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									MisuraUtils.getPkTarget(DmAlmConstants.PK_TARGET_PROGETTO_SFERA, DmAlmConstants.TARGET_PROGETTO_SFERA, DmAlmConstants.ID_TARGET_PROGETTO_SFERA, Integer.parseInt(String.valueOf(progetto.get(prog.idProgetto)))),
									dataEsecuzione);
						} else {
							if (sviWiTupla.size() != 0) {
								for (Tuple sviWI : sviWiTupla) {
									if (sviWI.get(statoWorkitem.cdStato).equals(
											"in_esercizio")
											|| sviWI.get(statoWorkitem.cdStato)
													.equals("chiuso")) {
										statoSvi = statoSvi + "S";
										datiWIsvi.add(sviWI);
									} else {
										statoSvi = statoSvi + "N";
									}
								}
							}
	
							if (manWiTupla.size() != 0) {
								for (Tuple manWI : manWiTupla) {
									if (manWI.get(statoWorkitem.cdStato).equals(
											"in_esercizio")
											|| manWI.get(statoWorkitem.cdStato)
													.equals("chiuso")) {
										statoMan = statoMan + "S";
										datiWIman.add(manWI);
									} else {
										statoMan = statoMan + "N";
									}
								}
							}
						}
					}
	
					// Per tutti i WI corrispondenti ad un PROGETTO SFERA
					// che hanno lo Stato in “chiuso” oppure “in_esercizio”
					// lo Stato di tutte le MISURE SFERA di tutti i PROGETTI
					// SFERA corrispondenti deve essere “consolidata” oppure
					// “sospesa”.
					// Se non trovo nemmeno una "N" negli stati dei wi significa
					// che i wi collegati al progetto sfera sono tutti chiusi
					// quindi il controllo va fatto e devo leggere le misure
	
					if ((!statoSvi.equalsIgnoreCase("") && statoSvi.indexOf("N") == -1)
							|| (!statoMan.equalsIgnoreCase("") && statoMan
									.indexOf("N") == -1)) {
						// estrarre tutte le misure da dmalm_misura
						statiMisura = DmAlmMisuraDAO
								.checkLinkMisureSferaWi(progetto
										.get(prog.idProgetto));
	
						if (statiMisura.size() != 0) {
							if (datiWIsvi.size() != 0) {
								for (Tuple errSvi : datiWIsvi) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_MISURA,
													DmAlmConstants.TARGET_MISURA,
													"Progetto Sfera :"+MisuraUtils
															.ProgettoSferaToString(progetto)+ " stato WI:"+ errSvi.get(progSv.cdProgSvilS)+" resolved ("+progSv.dtRisoluzioneProgSvilS+")",
													DmAlmConstants.NO_CORR_STATO_WI_MISURA,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													MisuraUtils.getPkTarget(DmAlmConstants.PK_TARGET_MISURA, DmAlmConstants.TARGET_MISURA, DmAlmConstants.ID_TARGET_MISURA, Integer.parseInt(String.valueOf(progetto.get(prog.idProgetto)))),
													dataEsecuzione);
								}
							}
	
							if (datiWIman.size() != 0) {
								for (Tuple errMan : datiWIman) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_MISURA,
													DmAlmConstants.TARGET_MISURA,
													MisuraUtils
															.ProgettoSferaToString(progetto),
													"Mancata corrispondenza tra lo STATO dei WI "
															+ errMan.get(man.cdManutenzione)
															+ " resolved ("
															+ errMan.get(man.dtRisoluzioneManutenzione)
															+ ")  e lo stato della MISURA SFERA",
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													MisuraUtils.getPkTarget(DmAlmConstants.PK_TARGET_MISURA, DmAlmConstants.TARGET_MISURA, DmAlmConstants.ID_TARGET_MISURA, Integer.parseInt(String.valueOf(progetto.get(prog.idProgetto)))),
													dataEsecuzione);
								}
							}
						}
					}
				}
			}
			logger.debug("STOP CheckLinkSferaSgrCmFacade");
		} catch (DAOException e) {
			//ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			//ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
