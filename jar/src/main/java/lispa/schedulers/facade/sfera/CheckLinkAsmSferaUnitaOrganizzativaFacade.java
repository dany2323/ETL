package lispa.schedulers.facade.sfera;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.sfera.target.AsmFacade;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.utils.MisuraUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkAsmSferaUnitaOrganizzativaFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaUnitaOrganizzativaFacade.class);
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmElProdottiArchitetture dmalmProdotto = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;
			List<Tuple> asmList = new ArrayList<Tuple>();

			logger.debug("START CheckLinkAsmSferaUnitaOrganizzativaFacade");

			// tutte le asm nuove o quelle per le quali la FK Unità
			// Organizzativa è variata 
			asmList = DmAlmAsmDAO.getAllAsmUnitaOrganizzativa();

			logger.debug("asmList.size: " + asmList.size());

			for (Tuple asmRow : asmList) {
				if (asmRow != null) {
					logger.debug("IdAsm: " + asmRow.get(dmalmAsm.idAsm)
							+ " - dmalmUnitaOrganizzativaFk01: "
							+ asmRow.get(dmalmProdotto.unitaOrganizzativaFk));

					DmalmAsm asm = new DmalmAsm();
					asm.setIdAsm(asmRow.get(dmalmAsm.idAsm));

					List<Tuple> asmTarget = DmAlmAsmDAO.getAsm(asm);

					for (Tuple rowTarget : asmTarget) {
						if (rowTarget != null) {
							asm = MisuraUtils.tuplaToAsm(rowTarget);
							asm.setDataCaricamento(dataEsecuzione);
							asm.setUnitaOrganizzativaFk(asmRow
									.get(dmalmProdotto.unitaOrganizzativaFk));
							Timestamp dt = rowTarget
									.get(dmalmAsm.dataCaricamento);
							if (dt.equals(dataEsecuzione)) {
								// update del valore della FK in quanto la riga
								// è nuova o storicizzata in questa esecuzione
								logger.debug("Eseguita update semplice della Fk di unita organizzativa");
								DmAlmAsmDAO.updateFkUnitaOrganizzativa(asm);
							} else {
								logger.debug("Eseguita storicizzazione");
								// Storicizzo la vecchia FK ed inserisco il
								// nuovo valore
								DmAlmAsmDAO.updateDataFineValidita(
										dataEsecuzione, asm);

								// inserisco un nuovo record
								DmAlmAsmDAO
										.insertAsmUpdate(dataEsecuzione, asm);
							}
						}
					}
				}
			}

			logger.debug("STOP CheckLinkAsmSferaUnitaOrganizzativaFacade");
			
			AsmFacade.recalculateUoFkFlat();
			
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
