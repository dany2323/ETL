package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoSviluppoSOds;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.path.StringPath;

public class ProgettoSviluppoSviluppoOdsDAO {

	private static Logger logger = Logger
			.getLogger(ProgettoSviluppoSviluppoOdsDAO.class);

	private static QDmalmProgettoSviluppoSOds progettoSvilSODS = QDmalmProgettoSviluppoSOds.dmalmProgettoSviluppoSOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoSvilSODS)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(
			List<DmalmProgettoSviluppoSvil> staging_progettoSviluppoSvil,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmProgettoSviluppoSvil progetto : staging_progettoSviluppoSvil) {

				new SQLInsertClause(connection, dialect, progettoSvilSODS)
						.columns(progettoSvilSODS.cdProgSvilS,
								progettoSvilSODS.codice,
								progettoSvilSODS.dataChiusuraProgSvilS,
								progettoSvilSODS.dataDisponibilitaEffettiva,
								progettoSvilSODS.dataDisponibilitaPianificata,
								progettoSvilSODS.dataInizio,
								progettoSvilSODS.dataInizioEff,
								progettoSvilSODS.descrizioneProgSvilS,
								progettoSvilSODS.dmalmAreaTematicaFk05,
								progettoSvilSODS.dmalmProgSvilSPk,
								progettoSvilSODS.dmalmProjectFk02,
								progettoSvilSODS.dmalmStatoWorkitemFk03,
								progettoSvilSODS.dmalmStrutturaOrgFk01,
								progettoSvilSODS.dmalmTempoFk04,
								progettoSvilSODS.dsAutoreProgSvilS,
								progettoSvilSODS.dtCambioStatoProgSvilS,
								progettoSvilSODS.dtCaricamentoProgSvilS,
								progettoSvilSODS.dtCreazioneProgSvilS,
								progettoSvilSODS.dtModificaProgSvilS,
								progettoSvilSODS.dtRisoluzioneProgSvilS,
								progettoSvilSODS.dtScadenzaProgSvilS,
								progettoSvilSODS.durataEffettivaProgSvilS,
								progettoSvilSODS.fornitura,
								progettoSvilSODS.idAutoreProgSvilS,
								progettoSvilSODS.idRepository,
								progettoSvilSODS.motivoRisoluzioneProgSvilS,
								progettoSvilSODS.numeroLinea,
								progettoSvilSODS.numeroTestata,
								progettoSvilSODS.stgPk,
								progettoSvilSODS.titoloProgSvilS,
								progettoSvilSODS.dmalmUserFk06,
								progettoSvilSODS.uri,
								progettoSvilSODS.severity,
								progettoSvilSODS.priority)
						.values(progetto.getCdProgSvilS(),
								progetto.getCodice(),
								progetto.getDataChiusuraProgSvilS(),
								progetto.getDataDisponibilitaEffettiva(),
								progetto.getDataDisponibilitaPianificata(),
								progetto.getDataInizio(),
								progetto.getDataInizioEff(),
								progetto.getDescrizioneProgSvilS(),
								progetto.getDmalmAreaTematicaFk05(),
								progetto.getDmalmProgSvilSPk(),
								progetto.getDmalmProjectFk02(),
								progetto.getDmalmStatoWorkitemFk03(),
								progetto.getDmalmStrutturaOrgFk01(),
								progetto.getDmalmTempoFk04(),
								progetto.getDsAutoreProgSvilS(),
								progetto.getDtCambioStatoProgSvilS(),
								progetto.getDtCaricamentoProgSvilS(),
								progetto.getDtCreazioneProgSvilS(),
								progetto.getDtModificaProgSvilS(),
								progetto.getDtRisoluzioneProgSvilS(),
								progetto.getDtScadenzaProgSvilS(),
								progetto.getDurataEffettivaProgSvilS(),
								progetto.getFornitura(),
								progetto.getIdAutoreProgSvilS(),
								progetto.getIdRepository(),
								progetto.getMotivoRisoluzioneProgSvilS(),
								progetto.getNumeroLinea(),
								progetto.getNumeroTestata(),
								progetto.getStgPk(),
								progetto.getTitoloProgSvilS(),
								progetto.getDmalmUserFk06(), progetto.getUri(),
								//DM_ALM-320
							    progetto.getSeverity(),
							    progetto.getPriority())
						.execute();

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

	public static List<DmalmProgettoSviluppoSvil> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmProgettoSviluppoSvil> resultList = new LinkedList<DmalmProgettoSviluppoSvil>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoSvilSODS)
					.orderBy(progettoSvilSODS.cdProgSvilS.asc())
					.orderBy(progettoSvilSODS.dtModificaProgSvilS.asc())
					.list(progettoSvilSODS.all());
			
			for (Tuple t : list) {
				DmalmProgettoSviluppoSvil pSvil = new DmalmProgettoSviluppoSvil();
				
				pSvil.setUri(t.get(progettoSvilSODS.uri));
				pSvil.setCdProgSvilS(t.get(progettoSvilSODS.cdProgSvilS));
				pSvil.setCodice(t.get(progettoSvilSODS.codice));
				pSvil.setDataChiusuraProgSvilS(t.get(progettoSvilSODS.dataChiusuraProgSvilS));
				pSvil.setDataDisponibilitaEffettiva(t.get(progettoSvilSODS.dataDisponibilitaEffettiva));
				pSvil.setDataDisponibilitaPianificata(t.get(progettoSvilSODS.dataDisponibilitaPianificata));
				pSvil.setDataInizio(t.get(progettoSvilSODS.dataInizio));
				pSvil.setDataInizioEff(t.get(progettoSvilSODS.dataInizioEff));
				pSvil.setDescrizioneProgSvilS(t.get(progettoSvilSODS.descrizioneProgSvilS));
				pSvil.setDmalmAreaTematicaFk05(t.get(progettoSvilSODS.dmalmAreaTematicaFk05));
				pSvil.setDmalmProgSvilSPk(t.get(progettoSvilSODS.dmalmProgSvilSPk));
				pSvil.setDmalmProjectFk02(t.get(progettoSvilSODS.dmalmProjectFk02));
				pSvil.setDmalmStatoWorkitemFk03(t.get(progettoSvilSODS.dmalmStatoWorkitemFk03));
				pSvil.setDmalmStrutturaOrgFk01(t.get(progettoSvilSODS.dmalmStrutturaOrgFk01));
				pSvil.setDmalmTempoFk04(t.get(progettoSvilSODS.dmalmTempoFk04));
				pSvil.setDsAutoreProgSvilS(t.get(progettoSvilSODS.dsAutoreProgSvilS));
				pSvil.setDtCambioStatoProgSvilS(t.get(progettoSvilSODS.dtCambioStatoProgSvilS));
				pSvil.setDtCaricamentoProgSvilS(t.get(progettoSvilSODS.dtCaricamentoProgSvilS));
				pSvil.setDtCreazioneProgSvilS(t.get(progettoSvilSODS.dtCreazioneProgSvilS));
				pSvil.setDtModificaProgSvilS(t.get(progettoSvilSODS.dtModificaProgSvilS));
				pSvil.setDtRisoluzioneProgSvilS(t.get(progettoSvilSODS.dtRisoluzioneProgSvilS));
				pSvil.setDtScadenzaProgSvilS(t.get(progettoSvilSODS.dtScadenzaProgSvilS));
				pSvil.setDtStoricizzazione(t.get(progettoSvilSODS.dtStoricizzazione));
				pSvil.setDurataEffettivaProgSvilS(t.get(progettoSvilSODS.durataEffettivaProgSvilS));
				pSvil.setFornitura(t.get(progettoSvilSODS.fornitura));
				pSvil.setIdAutoreProgSvilS(t.get(progettoSvilSODS.idAutoreProgSvilS));
				pSvil.setIdRepository(t.get(progettoSvilSODS.idRepository));
				pSvil.setMotivoRisoluzioneProgSvilS(t.get(progettoSvilSODS.motivoRisoluzioneProgSvilS));
				pSvil.setNumeroLinea(t.get(progettoSvilSODS.numeroLinea));
				pSvil.setNumeroTestata(t.get(progettoSvilSODS.numeroTestata));
				pSvil.setRankStatoProgSvilS(t.get(progettoSvilSODS.rankStatoProgSvilS));
				pSvil.setRankStatoProgSvilSMese(t.get(progettoSvilSODS.rankStatoProgSvilSMese));
				pSvil.setTitoloProgSvilS(t.get(progettoSvilSODS.titoloProgSvilS));
				pSvil.setStgPk(t.get(progettoSvilSODS.stgPk));
				pSvil.setDmalmUserFk06(t.get(progettoSvilSODS.dmalmUserFk06));
				pSvil.setSeverity(t.get(progettoSvilSODS.severity));
				pSvil.setPriority(t.get(progettoSvilSODS.priority));

				resultList.add(pSvil);
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
