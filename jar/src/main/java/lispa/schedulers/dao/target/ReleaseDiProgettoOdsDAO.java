package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmReleaseDiProgettoOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ReleaseDiProgettoOdsDAO {

	private static Logger logger = Logger
			.getLogger(ReleaseDiProgettoOdsDAO.class);

	private static QDmalmReleaseDiProgettoOds releaseODS = QDmalmReleaseDiProgettoOds.dmalmReleaseDiProgettoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, releaseODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmReleaseDiProgetto> staging_releases,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmReleaseDiProgetto release : staging_releases) {

				if(listPk.contains(release.getDmalmReleasediprogPk()))
					logger.info("Trovata DmalmReleasediprogPk DUPLICATA!!!"+release.getDmalmReleasediprogPk());
				else{
					
				listPk.add(release.getDmalmReleasediprogPk());
				new SQLInsertClause(connection, dialect, releaseODS)
						.columns(releaseODS.cdReleasediprog, releaseODS.codice,
								releaseODS.dataDisponibilitaEff,
								releaseODS.dataPassaggioInEsercizio,
								releaseODS.descrizioneReleasediprog,
								releaseODS.dmalmProjectFk02,
								releaseODS.dmalmReleasediprogPk,
								releaseODS.dmalmStatoWorkitemFk03,
								releaseODS.dmalmStrutturaOrgFk01,
								releaseODS.dmalmTempoFk04,
								releaseODS.dsAutoreReleasediprog,
								releaseODS.dtCambioStatoReleasediprog,
								releaseODS.dtCaricamentoReleasediprog,
								releaseODS.dtCreazioneReleasediprog,
								releaseODS.dtModificaReleasediprog,
								releaseODS.dtRisoluzioneReleasediprog,
								releaseODS.dtScadenzaReleasediprog,
								releaseODS.dtStoricizzazione,
								releaseODS.fornitura, releaseODS.fr,
								releaseODS.idAutoreReleasediprog,
								releaseODS.idRepository,
								releaseODS.motivoRisoluzioneRelProg,
								releaseODS.numeroLinea,
								releaseODS.numeroTestata,
								releaseODS.rankStatoReleasediprog,
								releaseODS.titoloReleasediprog,
								releaseODS.versione, releaseODS.stgPk,
								releaseODS.dmalmAreaTematicaFk05,
								releaseODS.dmalmUserFk06, releaseODS.uri,
								releaseODS.dtInizioQF, releaseODS.dtFineQF,
								releaseODS.numQuickFix,
								releaseODS.severity, releaseODS.priority, releaseODS.typeRelease)
						.values(release.getCdReleasediprog(),
								release.getCodice(),
								release.getDataDisponibilitaEff(),
								release.getDataPassaggioInEsercizio(),
								release.getDescrizioneReleasediprog(),
								release.getDmalmProjectFk02(),
								release.getDmalmReleasediprogPk(),
								release.getDmalmStatoWorkitemFk03(),
								release.getDmalmStrutturaOrgFk01(),
								release.getDmalmTempoFk04(),
								release.getDsAutoreReleasediprog(),
								release.getDtCambioStatoReleasediprog(),
								release.getDtCaricamentoReleasediprog(),
								release.getDtCreazioneReleasediprog(),
								release.getDtModificaReleasediprog(),
								release.getDtRisoluzioneReleasediprog(),
								release.getDtScadenzaReleasediprog(),
								release.getDtModificaReleasediprog(),
								release.getFornitura(), release.getFr(),
								release.getIdAutoreReleasediprog(),
								release.getIdRepository(),
								release.getMotivoRisoluzioneRelProg(),
								release.getNumeroLinea(),
								release.getNumeroTestata(), new Double(1),
								release.getTitoloReleasediprog(),
								release.getVersione(), release.getStgPk(),
								release.getDmalmAreaTematicaFk05(),
								release.getDmalmUserFk06(), release.getUri(),
								release.getDtInizioQF(), release.getDtFineQF(),
								release.getNumQuickFix(),
								//DM_ALM-320
								release.getSeverity(), release.getPriority(),
								release.getTypeRelease()).execute();
				}
			}

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmReleaseDiProgetto> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmReleaseDiProgetto> resultList = new LinkedList<DmalmReleaseDiProgetto>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseODS)
					.orderBy(releaseODS.cdReleasediprog.asc())
					.orderBy(releaseODS.dtModificaReleasediprog.asc())
					.list(releaseODS.all());

			for (Tuple t : list) {
				DmalmReleaseDiProgetto relProg = new DmalmReleaseDiProgetto();
				
				relProg.setUri(t.get(releaseODS.uri));
				relProg.setCdReleasediprog(t.get(releaseODS.cdReleasediprog));
				relProg.setCodice(t.get(releaseODS.codice));
				relProg.setDataDisponibilitaEff(t.get(releaseODS.dataDisponibilitaEff));
				relProg.setDataPassaggioInEsercizio(t.get(releaseODS.dataPassaggioInEsercizio));
				relProg.setDescrizioneReleasediprog(t.get(releaseODS.descrizioneReleasediprog));
				relProg.setDmalmProjectFk02(t.get(releaseODS.dmalmProjectFk02));
				relProg.setDmalmReleasediprogPk(t.get(releaseODS.dmalmReleasediprogPk));
				relProg.setDmalmStatoWorkitemFk03(t.get(releaseODS.dmalmStatoWorkitemFk03));
				relProg.setDmalmStrutturaOrgFk01(t.get(releaseODS.dmalmStrutturaOrgFk01));
				relProg.setDmalmTempoFk04(t.get(releaseODS.dmalmTempoFk04));
				relProg.setDsAutoreReleasediprog(t.get(releaseODS.dsAutoreReleasediprog));
				relProg.setDtCambioStatoReleasediprog(t.get(releaseODS.dtCambioStatoReleasediprog));
				relProg.setDtCaricamentoReleasediprog(t.get(releaseODS.dtCaricamentoReleasediprog));
				relProg.setDtCreazioneReleasediprog(t.get(releaseODS.dtCreazioneReleasediprog));
				relProg.setDtModificaReleasediprog(t.get(releaseODS.dtModificaReleasediprog));
				relProg.setDtRisoluzioneReleasediprog(t.get(releaseODS.dtRisoluzioneReleasediprog));
				relProg.setDtScadenzaReleasediprog(t.get(releaseODS.dtScadenzaReleasediprog));
				relProg.setDtStoricizzazione(t.get(releaseODS.dtStoricizzazione));
				relProg.setFornitura(t.get(releaseODS.fornitura));
				relProg.setFr(t.get(releaseODS.fr));
				relProg.setIdAutoreReleasediprog(t.get(releaseODS.idAutoreReleasediprog));
				relProg.setIdRepository(t.get(releaseODS.idRepository));
				relProg.setMotivoRisoluzioneRelProg(t.get(releaseODS.motivoRisoluzioneRelProg));
				relProg.setNumeroLinea(t.get(releaseODS.numeroLinea));
				relProg.setNumeroTestata(t.get(releaseODS.numeroTestata));
				relProg.setRankStatoReleasediprog(t.get(releaseODS.rankStatoReleasediprog));
				relProg.setRankStatoReleasediprogMese(t.get(releaseODS.rankStatoReleasediprogMese));
				relProg.setTitoloReleasediprog(t.get(releaseODS.titoloReleasediprog));
				relProg.setVersione(t.get(releaseODS.versione));
				relProg.setStgPk(t.get(releaseODS.stgPk));
				relProg.setDmalmAreaTematicaFk05(t.get(releaseODS.dmalmAreaTematicaFk05));
				relProg.setDmalmUserFk06(t.get(releaseODS.dmalmUserFk06));
				relProg.setDtInizioQF(t.get(releaseODS.dtInizioQF));
				relProg.setDtFineQF(t.get(releaseODS.dtFineQF));
				relProg.setNumQuickFix(t.get(releaseODS.numQuickFix));
				relProg.setSeverity(t.get(releaseODS.severity));
				relProg.setPriority(t.get(releaseODS.priority));
				relProg.setTypeRelease(t.get(releaseODS.typeRelease));

				resultList.add(relProg);
			}
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;

	}

}
