package lispa.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.AnomaliaProdottoOdsDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDAO;
import lispa.schedulers.facade.target.fatti.AnomaliaProdottoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import com.mysema.query.Tuple;

public class TestAnomalia extends TestCase {

	public void testFKProject() throws Exception {

	}

	public void testStoricizzazione() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-10-30 20:40:00", "yyyy-MM-dd HH:mm:00");

		AnomaliaProdottoFacade.execute(dataEsecuzione);

		//
		// DifettoProdottoFacade.execute(dataEsecuzione);
	}

	// public void testUpdateRank() throws Exception {
	// AnomaliaProdottoDAO.updateRankInMonth();
	// DifettoDAO.updateRankInMonth();
	// }
	//
	public void testFillAnomaliaProdotto() throws Exception {
		List<DmalmAnomaliaProdotto> staging_anomaliaprodotto = new ArrayList<DmalmAnomaliaProdotto>();
		List<Tuple> target_anomaliaprodotto = new ArrayList<Tuple>();
		QDmalmAnomaliaProdotto anom = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = null;
		Date dtFineCaricamento = null;

		DmalmAnomaliaProdotto anomalia_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			dtInizioCaricamento = new Date();

			System.out.println("START -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			List<DmalmAnomaliaProdotto> x = AnomaliaProdottoOdsDAO.getAll();

			System.out.println("STOP -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			for (DmalmAnomaliaProdotto anomalia : x) {

				anomalia_tmp = anomalia;
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999

				target_anomaliaprodotto = AnomaliaProdottoDAO
						.getAnomaliaProdotto(anomalia);

				// se non trovo almento un record, inserisco il project nel
				// target
				if (target_anomaliaprodotto.size() == 0) {
					anomalia.setDtCambioStatoAnomalia(anomalia
							.getDtModificaRecordAnomalia());
					righeNuove++;
					AnomaliaProdottoDAO.insertAnomaliaProdotto(anomalia);
				} else {
					boolean modificato = false;

					for (Tuple row : target_anomaliaprodotto) {

						if (row != null) {
							if (BeanUtils.areDifferent(
									row.get(anom.tempoTotRisoluzioneAnomalia),
									anomalia.getTempoTotRisoluzioneAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(anom.dmalmStatoWorkitemFk03),
													anomalia.getDmalmStatoWorkitemFk03())) {
								anomalia.setDtCambioStatoAnomalia(anomalia
										.getDtModificaRecordAnomalia());
								modificato = true;
							} else {
								anomalia.setDtCambioStatoAnomalia(row
										.get(anom.dtCambioStatoAnomalia));
							}
							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(anom.numeroTestataAnomalia),
													anomalia.getNumeroTestataAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.numeroLineaAnomalia),
											anomalia.getNumeroLineaAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.dmalmProjectFk02),
											anomalia.getDmalmProjectFk02())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.effortAnalisi),
											anomalia.getEffortAnalisi())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.dmalmUserFk06),
											anomalia.getDmalmUserFk06())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.uri),
											anomalia.getUri())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								// AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione,
								// anomalia);
								AnomaliaProdottoDAO
										.updateRankFlagUltimaSituazione(
												anomalia, new Double(0),
												new Short("0"));

								// inserisco un nuovo record
//								AnomaliaProdottoDAO
//										.insertAnomaliaProdottoUpdate(
//												dataEsecuzione, anomalia);

							} else {
								// Aggiorno lo stesso
								AnomaliaProdottoDAO
										.updateDmalmAnomaliaProdotto(anomalia);
							}
						}

					}
				}
			}

			System.out.println("righeNuove: " + righeNuove);
			System.out.println("righeModificate: " + righeModificate);
			System.out.println("dtInizioCaricamento: " + dtInizioCaricamento);
			System.out.println("dtFineCaricamento: " + dtFineCaricamento);
			System.out.println("stato: " + stato);
		} catch (Exception e) {
			System.out.println(LogUtils.objectToString(anomalia_tmp));
			System.out.println(e.getMessage());
		}
	}

	public void testGetAnomalia() throws Exception {
		DmalmAnomaliaProdotto bean = new DmalmAnomaliaProdotto();

		AnomaliaProdottoDAO.getAnomaliaProdotto(bean);
	}

	public void testGetQueryFromFile() throws Exception {

		try {

		} catch (Throwable e) {

		}
	}

	public void testStrutturaOrganizzativa() throws Exception {
		try {
			PreparedStatement pstm = null;

			ConnectionManager cm = ConnectionManager.getInstance();
			Connection c = cm.getConnectionOracle();
			String query = QueryManager.getInstance().getQuery("anomalia.sql");

			pstm = c.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

			}

		} catch (Throwable e) {

		}
	}
}