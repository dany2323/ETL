package lispa.schedulers.dao.sfera;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.bean.target.sfera.DmalmMisura;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.fonte.sfera.QDmAlmMisura;
import lispa.schedulers.queryimplementation.staging.sfera.QDmalmStgMisura;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgMisuraDAO {

	private static Logger logger = Logger.getLogger(StgMisuraDAO.class);
	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
	private static QDmalmStgMisura stgMisura = QDmalmStgMisura.dmalmStgMisura;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

	public static void FillStgMisura() throws PropertiesReaderException,
			DAOException, SQLException {
		
		connection = cm.getConnectionOracle();
		QDmAlmMisura stg_Misura = QDmAlmMisura.dmAlmMisura;
		
		try {
			connection.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> misure = query.from(stg_Misura).list(stg_Misura.all());

			int numRighe = 0;

			for (Tuple row : misure) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMisura)
						.columns(stgMisura.a1Num, stgMisura.a1Ufp,
								stgMisura.a2Num, stgMisura.a2Ufp,
								stgMisura.adjmax, stgMisura.adjmin,
								stgMisura.adjufp, stgMisura.ambito,
								stgMisura.noteAsm,
								stgMisura.pAppAccAuthLastUpdate,
								stgMisura.pAppCdAltreAsmCommonServ,
								stgMisura.pAppCodAsmConfinanti,
								stgMisura.pAppCodDirezioneDemand,
								stgMisura.pAppCodFlussiIoAsm,
								stgMisura.pAppCdAmbitoManAttuale,
								stgMisura.pAppCodAmbitoManFuturo,
								stgMisura.pAppDataFineValiditaAsm,
								stgMisura.pAppDataInizioValiditaAsm,
								stgMisura.pAppDataUltimoAggiorn,
								stgMisura.pAppDenomSistTerziConfin,
								stgMisura.pAppDenomUtentiFinaliAsm,
								stgMisura.pAppDenominazioneAsm,
								stgMisura.pAppFlagDamisurarePatrFp,
								stgMisura.pAppFlagMisurareSvimevFp,
								stgMisura.pAppFlagInManutenzione,
								stgMisura.pAppFlagServizioComune,
								stgMisura.pAppIndicValidazioneAsm,
								stgMisura.pAppNomeAuthLastUpdate,
								stgMisura.appCls, stgMisura.applicazione,
								stgMisura.approccio, stgMisura.b1Num,
								stgMisura.b1Ufp, stgMisura.b2Num,
								stgMisura.b2Ufp, stgMisura.b3Num,
								stgMisura.b3Ufp, stgMisura.b4Num,
								stgMisura.b4Ufp, stgMisura.b5Num,
								stgMisura.b5Ufp, stgMisura.bfpNum,
								stgMisura.bfpUfp, stgMisura.c1Num,
								stgMisura.c1Ufp, stgMisura.c2Num,
								stgMisura.c2Ufp, stgMisura.cicloDiVita,
								stgMisura.confine, stgMisura.costo,
								stgMisura.crudNum, stgMisura.crudUfp,
								stgMisura.d1Num, stgMisura.d1Ufp,
								stgMisura.d2Num, stgMisura.d2Ufp,
								stgMisura.dataAvvio,
								stgMisura.dataConsolidamento,
								stgMisura.dataCreazione,
								stgMisura.dataDismissione,
								stgMisura.dataRiferimento,
								stgMisura.dataFineEffettiva,
								stgMisura.dataInizioEsercizio,
								stgMisura.durataEffettiva, stgMisura.eiNum,
								stgMisura.eiUfp, stgMisura.eifNum,
								stgMisura.eifUfp, stgMisura.eoNum,
								stgMisura.eoUfp, stgMisura.eqNum,
								stgMisura.eqUfp, stgMisura.esperienza,
								stgMisura.faseCicloDiVita, stgMisura.fonti,
								stgMisura.fpNonPesatiMax,
								stgMisura.fpNonPesatiMin,
								stgMisura.fpNonPesatiUfp,
								stgMisura.fpPesatiMax,
								stgMisura.fpPesatiMin,
								stgMisura.fpPesatiUfp,
								stgMisura.frequenzaUtilizzo,
								stgMisura.fuNum, stgMisura.fuUfp,
								stgMisura.gdgNum, stgMisura.gdgUfp,
								stgMisura.geiNum, stgMisura.geiUfp,
								stgMisura.geifNum, stgMisura.geifUfp,
								stgMisura.geoNum, stgMisura.geoUfp,
								stgMisura.geqNum, stgMisura.geqUfp,
								stgMisura.gilfNum, stgMisura.gilfUfp,
								stgMisura.gpNum, stgMisura.gpUfp,
								stgMisura.idAsm, stgMisura.idMsr,
								stgMisura.idProgetto, stgMisura.ifpNum,
								stgMisura.ifpUfp, stgMisura.ilfNum,
								stgMisura.ilfUfp,
								stgMisura.impegnoEffettivo,
								stgMisura.includeInBenchmarkingDb,
								stgMisura.includiInDbPatrimonio,
								stgMisura.ldgNum, stgMisura.ldgUfp,
								stgMisura.linkDocumentale,
								stgMisura.noteMsr, stgMisura.metodo,
								stgMisura.mfNum, stgMisura.mfUfp,
								stgMisura.nomeMisura, stgMisura.mldgNum,
								stgMisura.mldgUfp, stgMisura.modello,
								stgMisura.mpNum, stgMisura.mpUfp,
								stgMisura.numeroUtenti,
								stgMisura.permissions, stgMisura.pfNum,
								stgMisura.pfUfp, stgMisura.postVerAddCfp,
								stgMisura.postVerChg, stgMisura.postVerDel,
								stgMisura.postVerFp,
								stgMisura.preVerAddCfp,
								stgMisura.preVerChg, stgMisura.preVerDel,
								stgMisura.preVerFp, stgMisura.notePrj,
								stgMisura.pPrjADisposizione01,
								stgMisura.pPrjADisposizione02,
								stgMisura.pPrjAuditIndexVerify,
								stgMisura.pPrjAuditMonitore,
								stgMisura.pPrjCodRdi,
								stgMisura.pPrjFccFattCorrezTotal,
								stgMisura.pPrjFlAmbTecnPiatEnterpr,
								stgMisura.pPrjFlAmbitoTecnFuturo01,
								stgMisura.pPrjFlAmbitoTecnFuturo02,
								stgMisura.pPrjFlagAmbTecnGis,
								stgMisura.pPrjAmbTecnPortali,
								stgMisura.pPrjFlAmbTecTransBatRep,
								stgMisura.pPrjFlApplicLgFpDwh,
								stgMisura.pPrjFlApplicLgFpFuturo01,
								stgMisura.pPrjFlApplicLgFpFuturo02,
								stgMisura.pPrjFlApplLgFpWeb,
								stgMisura.pPrjFlApplLgFpEdma,
								stgMisura.pPrjFlagApplLgFpGis,
								stgMisura.pPrjFlagApplLgFpMware,
								stgMisura.pPrjFornitoreMpp,
								stgMisura.pPrjFornitoreSviluppoMev,
								stgMisura.pPrjImportoAConsuntivo,
								stgMisura.pPrjImportoRdiAPreventivo,
								stgMisura.pPrjIndexAlmValidProgAsm,
								stgMisura.pPrjMfcAConsuntivo,
								stgMisura.pPrjMfcAPreventivo,
								stgMisura.pPrjMpPercentCicloDiVita,
								stgMisura.pPrjPunPrezzoUnitNominal,
								stgMisura.prjCls, stgMisura.nomeProgetto,
								stgMisura.proprietaLegale,
								stgMisura.responsabile, stgMisura.scopo,
								stgMisura.staffMedio,
								stgMisura.statoMisura,
								stgMisura.tipoProgetto, stgMisura.tpNum,
								stgMisura.tpUfp, stgMisura.ugdgNum,
								stgMisura.ugdgUfp, stgMisura.ugepNum,
								stgMisura.ugepUfp, stgMisura.ugoNum,
								stgMisura.ugoUfp, stgMisura.ugpNum,
								stgMisura.ugpUfp,
								stgMisura.utenteMisuratore,
								stgMisura.utilizzata,
								stgMisura.vafPredefinito,
								stgMisura.verDelta,
								stgMisura.verDeltaPercent,
								stgMisura.verEnd, stgMisura.versioneMsr,
								stgMisura.versionePrj, stgMisura.verStart,
								stgMisura.dataCaricamento,
								stgMisura.dmalmStgMisuraPk,
								stgMisura.dtPrevistaProssimaMpp,
								stgMisura.fip01InizioEsercizio,
								stgMisura.fip02IndiceQualita,
								stgMisura.fip03ComplessEserc,
								stgMisura.fip04NrPiattaforma,
								stgMisura.fip07LingProgPrincipale,
								stgMisura.fip10GradoAccessibilita,
								stgMisura.fip11GradoQualitaCod,
								stgMisura.fip12UtilizzoFramework,
								stgMisura.fip13ComplessitaAlg,
								stgMisura.fip15LivelloCura)
						.values(row.get(stg_Misura.a1Num),
								row.get(stg_Misura.a1Ufp),
								row.get(stg_Misura.a2Num),
								row.get(stg_Misura.a2Ufp),
								row.get(stg_Misura.adjmax),
								row.get(stg_Misura.adjmin),
								row.get(stg_Misura.adjufp),
								row.get(stg_Misura.ambito),
								StringUtils.getMaskedValue(row.get(stg_Misura.noteAsm)),
								StringUtils.getMaskedValue(row.get(stg_Misura.pAppAccAuthLastUpdate)),
								row.get(stg_Misura.pAppCdAltreAsmCommonServ),
								row.get(stg_Misura.pAppCodAsmConfinanti),
								row.get(stg_Misura.pAppCodDirezioneDemand),
								row.get(stg_Misura.pAppCodFlussiIoAsm),
								StringUtils.getMaskedValue(row.get(stg_Misura.pAppCdAmbitoManAttuale)),
								StringUtils.getMaskedValue(row.get(stg_Misura.pAppCodAmbitoManFuturo)),
								row.get(stg_Misura.pAppDataFineValiditaAsm),
								row.get(stg_Misura.pAppDataInizioValiditaAsm),
								row.get(stg_Misura.pAppDataUltimoAggiorn),
								row.get(stg_Misura.pAppDenomSistTerziConfin),
								row.get(stg_Misura.pAppDenomUtentiFinaliAsm),
								row.get(stg_Misura.pAppDenominazioneAsm),
								row.get(stg_Misura.pAppFlagDamisurarePatrFp),
								row.get(stg_Misura.pAppFlagMisurareSvimevFp),
								row.get(stg_Misura.pAppFlagInManutenzione),
								row.get(stg_Misura.pAppFlagServizioComune),
								row.get(stg_Misura.pAppIndicValidazioneAsm),
								StringUtils.getMaskedValue(row.get(stg_Misura.pAppDataUltimoAggiorn)),
								row.get(stg_Misura.appCls), 
								row.get(stg_Misura.applicazione),
								row.get(stg_Misura.approccio), 
								row.get(stg_Misura.b1Num),
								row.get(stg_Misura.b1Ufp), 
								row.get(stg_Misura.b2Num),
								row.get(stg_Misura.b2Ufp), 
								row.get(stg_Misura.b3Num),
								row.get(stg_Misura.b3Ufp), 
								row.get(stg_Misura.b4Num),
								row.get(stg_Misura.b4Ufp), 
								row.get(stg_Misura.b5Num),
								row.get(stg_Misura.b5Ufp), 
								row.get(stg_Misura.bfpNum),
								row.get(stg_Misura.bfpUfp), 
								row.get(stg_Misura.c1Num),
								row.get(stg_Misura.c1Ufp), 
								row.get(stg_Misura.c2Num),
								row.get(stg_Misura.c2Ufp), 
								row.get(stg_Misura.cicloDiVita),
								row.get(stg_Misura.confine), 
								row.get(stg_Misura.costo),
								row.get(stg_Misura.crudNum), 
								row.get(stg_Misura.crudUfp),
								row.get(stg_Misura.d1Num), 
								row.get(stg_Misura.d1Ufp),
								row.get(stg_Misura.d2Num), 
								row.get(stg_Misura.d2Ufp),
								row.get(stg_Misura.dataAvvio),
								row.get(stg_Misura.dataConsolidamento),
								row.get(stg_Misura.dataCreazione),
								row.get(stg_Misura.dataDismissione),
								row.get(stg_Misura.dataRiferimento),
								row.get(stg_Misura.dataFineEffettiva),
								row.get(stg_Misura.dataInizioEsercizio),
								row.get(stg_Misura.durataEffettiva), 
								row.get(stg_Misura.eiNum),
								row.get(stg_Misura.eiUfp), 
								row.get(stg_Misura.eifNum),
								row.get(stg_Misura.eifUfp), 
								row.get(stg_Misura.eoNum),
								row.get(stg_Misura.eoUfp), 
								row.get(stg_Misura.eqNum),
								row.get(stg_Misura.eqUfp), 
								row.get(stg_Misura.esperienza),
								row.get(stg_Misura.faseCicloDiVita), 
								row.get(stg_Misura.fonti),
								row.get(stg_Misura.fpNonPesatiMax),
								row.get(stg_Misura.fpNonPesatiMin),
								row.get(stg_Misura.fpNonPesatiUfp),
								row.get(stg_Misura.fpPesatiMax),
								row.get(stg_Misura.fpPesatiMin),
								row.get(stg_Misura.fpPesatiUfp),
								row.get(stg_Misura.frequenzaUtilizzo),
								row.get(stg_Misura.fuNum), 
								row.get(stg_Misura.fuUfp),
								row.get(stg_Misura.gdgNum), 
								row.get(stg_Misura.gdgUfp),
								row.get(stg_Misura.geiNum), 
								row.get(stg_Misura.geiUfp),
								row.get(stg_Misura.geifNum), 
								row.get(stg_Misura.geifUfp),
								row.get(stg_Misura.geoNum), 
								row.get(stg_Misura.geoUfp),
								row.get(stg_Misura.geqNum), 
								row.get(stg_Misura.geqUfp),
								row.get(stg_Misura.gilfNum), 
								row.get(stg_Misura.gilfUfp),
								row.get(stg_Misura.gpNum), 
								row.get(stg_Misura.gpUfp),
								row.get(stg_Misura.idAsm), 
								row.get(stg_Misura.idMsr),
								row.get(stg_Misura.idProgetto), 
								row.get(stg_Misura.ifpNum),
								row.get(stg_Misura.ifpUfp), 
								row.get(stg_Misura.ilfNum),
								row.get(stg_Misura.ilfUfp),
								row.get(stg_Misura.impegnoEffettivo),
								row.get(stg_Misura.includeInBenchmarkingDb),
								row.get(stg_Misura.includiInDbPatrimonio),
								row.get(stg_Misura.ldgNum), 
								row.get(stg_Misura.ldgUfp),
								row.get(stg_Misura.linkDocumentale),
								StringUtils.getMaskedValue(row.get(stg_Misura.noteMsr)),
								row.get(stg_Misura.metodo),
								row.get(stg_Misura.mfNum), 
								row.get(stg_Misura.mfUfp),
								row.get(stg_Misura.nomeMisura), 
								row.get(stg_Misura.mldgNum),
								row.get(stg_Misura.mldgUfp), 
								row.get(stg_Misura.modello),
								row.get(stg_Misura.mpNum), 
								row.get(stg_Misura.mpUfp),
								row.get(stg_Misura.numeroUtenti),
								StringUtils.getMaskedValue(row.get(stg_Misura.permissions)),
								row.get(stg_Misura.pfNum),
								row.get(stg_Misura.pfUfp), 
								row.get(stg_Misura.postVerAddCfp),
								row.get(stg_Misura.postVerChg), 
								row.get(stg_Misura.postVerDel),
								row.get(stg_Misura.postVerFp),
								row.get(stg_Misura.preVerAddCfp),
								row.get(stg_Misura.preVerChg), 
								row.get(stg_Misura.preVerDel),
								row.get(stg_Misura.preVerFp), 
								StringUtils.getMaskedValue(row.get(stg_Misura.notePrj)),
								row.get(stg_Misura.pPrjADisposizione01),
								row.get(stg_Misura.pPrjADisposizione02),
								row.get(stg_Misura.pPrjAuditIndexVerify),
								StringUtils.getMaskedValue(row.get(stg_Misura.pPrjAuditMonitore)),
								row.get(stg_Misura.pPrjCodRdi),
								row.get(stg_Misura.pPrjFccFattCorrezTotal),
								row.get(stg_Misura.pPrjFlAmbTecnPiatEnterpr),
								row.get(stg_Misura.pPrjFlAmbitoTecnFuturo01),
								row.get(stg_Misura.pPrjFlAmbitoTecnFuturo02),
								row.get(stg_Misura.pPrjFlagAmbTecnGis),
								row.get(stg_Misura.pPrjAmbTecnPortali),
								row.get(stg_Misura.pPrjFlAmbTecTransBatRep),
								row.get(stg_Misura.pPrjFlApplicLgFpDwh),
								row.get(stg_Misura.pPrjFlApplicLgFpFuturo01),
								row.get(stg_Misura.pPrjFlApplicLgFpFuturo02),
								row.get(stg_Misura.pPrjFlApplLgFpWeb),
								row.get(stg_Misura.pPrjFlApplLgFpEdma),
								row.get(stg_Misura.pPrjFlagApplLgFpGis),
								row.get(stg_Misura.pPrjFlagApplLgFpMware),
								StringUtils.getMaskedValue(row.get(stg_Misura.pPrjFornitoreMpp)),
								StringUtils.getMaskedValue(row.get(stg_Misura.pPrjFornitoreSviluppoMev)),
								row.get(stg_Misura.pPrjImportoAConsuntivo),
								row.get(stg_Misura.pPrjImportoRdiAPreventivo),
								row.get(stg_Misura.pPrjIndexAlmValidProgAsm),
								row.get(stg_Misura.pPrjMfcAConsuntivo),
								row.get(stg_Misura.pPrjMfcAPreventivo),
								row.get(stg_Misura.pPrjMpPercentCicloDiVita),
								row.get(stg_Misura.pPrjPunPrezzoUnitNominal),
								row.get(stg_Misura.prjCls), 
								row.get(stg_Misura.nomeProgetto),
								row.get(stg_Misura.proprietaLegale),
								StringUtils.getMaskedValue(row.get(stg_Misura.responsabile)),
								row.get(stg_Misura.scopo),
								row.get(stg_Misura.staffMedio),
								row.get(stg_Misura.statoMisura),
								row.get(stg_Misura.tipoProgetto), 
								row.get(stg_Misura.tpNum),
								row.get(stg_Misura.tpUfp), 
								row.get(stg_Misura.ugdgNum),
								row.get(stg_Misura.ugdgUfp), 
								row.get(stg_Misura.ugepNum),
								row.get(stg_Misura.ugepUfp), 
								row.get(stg_Misura.ugoNum),
								row.get(stg_Misura.ugoUfp), 
								row.get(stg_Misura.ugpNum),
								row.get(stg_Misura.ugpUfp),
								StringUtils.getMaskedValue(row.get(stg_Misura.utenteMisuratore)),
								row.get(stg_Misura.utilizzata),
								row.get(stg_Misura.vafPredefinito),
								row.get(stg_Misura.verDelta),
								row.get(stg_Misura.verDeltaPercent),
								row.get(stg_Misura.verEnd), 
								row.get(stg_Misura.versioneMsr),
								row.get(stg_Misura.versionePrj), 
								row.get(stg_Misura.verStart),
								dataEsecuzione,
								StringTemplate.create("DM_ALM_STG_MISURA_SEQ.nextval"),
								row.get(stg_Misura.dtPrevistaProssimaMpp),
								row.get(stg_Misura.fip01InizioEsercizio),
								row.get(stg_Misura.fip02IndiceQualita),
								row.get(stg_Misura.fip03ComplessEserc),
								row.get(stg_Misura.fip04NrPiattaforma),
								row.get(stg_Misura.fip07LingProgPrincipale),
								row.get(stg_Misura.fip10GradoAccessibilita),
								row.get(stg_Misura.fip11GradoQualitaCod),
								row.get(stg_Misura.fip12UtilizzoFramework),
								row.get(stg_Misura.fip13ComplessitaAlg),
								row.get(stg_Misura.fip15LivelloCura))
						.execute();
			}

			connection.commit();

			logger.info("StgMisuraDAO.FillStgMisura - righe inserite: " + numRighe);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void delete(Timestamp dataEsecuzioneDelete)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMisura qstgmisura = QDmalmStgMisura.dmalmStgMisura;

			new SQLDeleteClause(connection, dialect, qstgmisura).where(
					qstgmisura.dataCaricamento.lt(dataEsecuzioneDelete).or(
							qstgmisura.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMisura() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStgMisura stgmisura = QDmalmStgMisura.dmalmStgMisura;
			new SQLDeleteClause(connection, dialect, stgmisura).where(
					stgmisura.dataCaricamento.eq(DataEsecuzione.getInstance()
							.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	/**
	 * Estrae la lista degli ASM eliminati fisicamente dal sorgente: ASM
	 * presenti alla data dell'esecuzione precedente, ma non più presenti alla
	 * data di esecuzione attuale
	 * 
	 * @return List<String>
	 * @throws IOException
	 * @throws DAOException
	 */

	public static List<DmalmMisura> getRemoveFromFile(String type,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmalmMisura> removeList = new ArrayList<DmalmMisura>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = getQueryRemoveFromFile(type);

			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				DmalmMisura bean = new DmalmMisura();

				bean.setIdAsm(rs.getShort("ID_ASM"));

				if (type.equalsIgnoreCase("PRJ")) {
					bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
				} else if (type.equalsIgnoreCase("MIS")) {
					bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
					bean.setIdMsr(rs.getInt("ID_MSR"));
					bean.setDmalmMisuraPk(rs.getInt("MISURA_PK"));
				}

				removeList.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return removeList;
	}

	private static String getQueryRemoveFromFile(String type)
			throws PropertiesReaderException, Exception {
		String sql = null;

		if (type.equalsIgnoreCase("ASM")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ASM_ELIMINATE);
		} else if (type.equalsIgnoreCase("PRJ")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGETTI_ELIMINATI);
		} else if (type.equalsIgnoreCase("MIS")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_MISURE_ELIMINATE);
		}

		return sql;
	}

	public static List<Tuple> getControlloDatiSfera(Timestamp dataEsecuzione)
			throws DAOException {
		List<Tuple> stgMisuraDati = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			stgMisuraDati = query.from(stgMisura)
					.where(stgMisura.dataCaricamento.eq(dataEsecuzione))
					.orderBy(stgMisura.idAsm.asc(), stgMisura.idProgetto.asc())
					.list(stgMisura.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return stgMisuraDati;
	}

	public static List<Tuple> checkPat002(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misurePat002 = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePat002 = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.startsWith("PAT-002-"))
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePat002;
	}

	public static List<Tuple> checkMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misurePatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.list(stgMisura.nomeMisura, stgMisura.nomeProgetto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePatr;
	}

	public static List<String> checkCountMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<String> misurePatr = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePatr;
	}

	public static Boolean checkBucoNumerazMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		boolean bucoNumerazione = true;

		List<Tuple> maxPatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			String resMaxPatr = null;

			maxPatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.orderBy(stgMisura.nomeMisura.substring(4, 7).desc())
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

			if (maxPatr.size() > 0) {
				resMaxPatr = maxPatr.get(0).get(stgMisura.nomeMisura)
						.substring(4, 7);

				if (Integer.parseInt(resMaxPatr) != maxPatr.size()) {
					bucoNumerazione = false;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return bucoNumerazione;
	}

	public static List<String> checkCountMisurePatrBas(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		List<String> misurePatr = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("BAS-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		return misurePatr;
	}

	public static Boolean checkBucoNumerazMisurePatrBas(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		boolean bucoNumerazione = true;

		List<Tuple> maxPatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			String resMaxPatr = null;

			maxPatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("BAS-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.orderBy(stgMisura.nomeMisura.substring(4, 7).desc())
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

			if (maxPatr.size() > 0) {
				resMaxPatr = maxPatr.get(0).get(stgMisura.nomeMisura)
						.substring(4, 7);
				if (Integer.parseInt(resMaxPatr) != maxPatr.size()) {
					bucoNumerazione = false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return bucoNumerazione;
	}

	public static List<String> checkCountMisureSt(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<String> misureSt = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misureSt = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 2).eq("ST"))
					.where(stgMisura.nomeMisura.substring(2, 3).between("1",
							"3"))
					.where(stgMisura.nomeMisura.substring(3, 4).eq("-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misureSt;
	}

	public static List<Tuple> checkBucoNumerazMisureSt(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misureSt = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misureSt = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 2).eq("ST"))
					.where(stgMisura.nomeMisura.substring(2, 3).between("1",
							"3"))
					.where(stgMisura.nomeMisura.substring(3, 4).eq("-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7),
							stgMisura.nomeMisura.substring(0, 7).count());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misureSt;
	}

	public static Boolean checkAsmValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean asmValida = false;
		String appCodAsm = null;
		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.distinct()
					.where(stgMisura.applicazione.startsWith(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.list(stgMisura.applicazione);

			for (String ar : asmRes) {
				appCodAsm = ar.trim();

				if (appCodAsm.contains("#")) {
					appCodAsm = appCodAsm.substring(0,
							appCodAsm.indexOf("#") - 1);
				}

				if (asm.equals(appCodAsm)) {
					asmValida = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmValida;
	}

	public static Boolean checkAltreAsmValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean asmValida = false;
		String appCodAsm = null;
		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.where(stgMisura.applicazione.startsWith(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.pAppFlagServizioComune.eq("SI"))
					.list(stgMisura.applicazione);

			for (String a : asmRes) {
				appCodAsm = a.trim();

				if (appCodAsm.contains("#")) {
					appCodAsm = appCodAsm.substring(0,
							appCodAsm.indexOf("#") - 1);
				}

				if (asm.equals(appCodAsm)) {
					asmValida = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmValida;
	}

	public static Boolean checkDenomValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean denomValida = true;

		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.where(stgMisura.applicazione.eq(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.list(stgMisura.applicazione);

			if (asmRes.size() == 0) {
				denomValida = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return denomValida;
	}
}
