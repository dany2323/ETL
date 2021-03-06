package lispa.schedulers.dao.sfera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdotto;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class DmAlmAsmDAO {
	private static Logger logger = Logger.getLogger(DmAlmAsmDAO.class);
	private static QDmalmAsm asm = QDmalmAsm.dmalmAsm;
	private static QDmalmProdotto prodotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmAsmProdotto asmprodotto = QDmalmAsmProdotto.dmalmAsmProdotto;
	private static QDmalmAsmProdottiArchitetture asmProdottiArchitetture = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;
	private static QDmalmElProdottiArchitetture elettraProdotto = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmAsm> getAllAsm(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmAsm bean = null;
		List<DmalmAsm> asm = new LinkedList<DmalmAsm>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ASM);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Asm eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmAsm();

				bean.setNoteAsm(rs.getString("NOTE_ASM"));
				bean.setDmalmStgMisuraPk(rs.getInt("DMALM_STG_MISURA_PK"));
				bean.setpAppAccAuthLastUpdate(rs
						.getString("P_APP_ACC_AUTH_LAST_UPDATE"));
				bean.setpAppCdAltreAsmCommonServ(rs
						.getString("P_APP_CD_ALTRE_ASM_COMMON_SERV"));
				bean.setpAppCdAmbitoManAttuale(rs
						.getString("P_APP_CD_AMBITO_MAN_ATTUALE"));
				bean.setpAppCodAmbitoManFuturo(rs
						.getString("P_APP_CD_AMBITO_MAN_FUTURO"));
				bean.setpAppCodAsmConfinanti(rs
						.getString("P_APP_CD_ASM_CONFINANTI"));
				bean.setpAppCodDirezioneDemand(rs
						.getString("P_APP_CD_DIREZIONE_DEMAND"));
				bean.setpAppCodFlussiIoAsm(rs
						.getString("P_APP_CD_FLUSSI_IO_ASM"));
				bean.setpAppDataFineValiditaAsm(rs
						.getTimestamp("P_APP_DT_FINE_VALIDITA_ASM"));
				bean.setpAppDataInizioValiditaAsm(rs
						.getTimestamp("P_APP_DT_INIZIO_VALIDITA_ASM"));
				bean.setpAppDataUltimoAggiorn(rs
						.getTimestamp("P_APP_DT_ULTIMO_AGGIORN"));
				bean.setpAppDenomSistTerziConfin(rs
						.getString("P_APP_DN_SIST_TERZI_CONFIN"));
				bean.setpAppDenomUtentiFinaliAsm(rs
						.getString("P_APP_DN_UTENTI_FINALI_ASM"));
				bean.setpAppDenominazioneAsm(rs.getString("P_APP_DN_ASM"));
				bean.setpAppFlagDamisurarePatrFp(rs
						.getString("P_APP_FL_DAMISURARE_PATR_FP"));
				bean.setpAppFlagInManutenzione(rs
						.getString("P_APP_FL_IN_MANUTENZIONE"));
				bean.setpAppFlagMisurareSvimevFp(rs
						.getShort("P_APP_FL_MISURARE_SVIMEV_FP"));
				bean.setpAppFlagServizioComune(rs
						.getShort("P_APP_FL_SERVIZIO_COMUNE"));
				bean.setpAppIndicValidazioneAsm(rs
						.getShort("P_APP_INDIC_VALIDAZIONE_ASM"));
				bean.setpAppNomeAuthLastUpdate(rs
						.getString("P_APP_NOME_AUTH_LAST_UPDATE"));
				bean.setAppCls(rs.getString("APP_CLS"));
				bean.setApplicazione(rs.getString("APPLICAZIONE"));
				bean.setDataDismissione(rs.getTimestamp("DT_DISMISSIONE"));
				bean.setDataInizioEsercizio(rs
						.getTimestamp("DT_INIZIO_ESERCIZIO"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setFrequenzaUtilizzo(rs.getString("FREQUENZA_UTILIZZO"));
				bean.setIdAsm(rs.getShort("ID_ASM"));
				bean.setIncludiInDbPatrimonio(rs
						.getShort("INCLUDI_IN_DB_PATRIMONIO"));
				bean.setNumeroUtenti(rs.getShort("NUMERO_UTENTI"));
				bean.setPermissions(rs.getString("PERMISSIONS"));
				bean.setProprietaLegale(rs.getShort("PROPRIETA_LEGALE"));
				bean.setUtilizzata(rs.getShort("UTILIZZATA"));
				bean.setVafPredefinito(rs.getShort("VAF_PREDEFINITO"));
				bean.setDtPrevistaProssimaMpp(rs
						.getTimestamp("DT_PREVISTA_PROSSIMA_MPP_ASM"));
				bean.setFip01InizioEsercizio(rs
						.getTimestamp("FIP01_INIZIO_ESERCIZIO_ASM"));
				bean.setFip02IndiceQualita(rs
						.getInt("FIP02_INDICE_QUALITA_DOC_ASM"));
				if (rs.wasNull()) {
					bean.setFip02IndiceQualita(null);
				}
				bean.setFip03ComplessEserc(rs
						.getString("FIP03_COMP_PT_SVIL_ESERC_ASM"));
				bean.setFip04NrPiattaforma(rs
						.getInt("FIP04_NR_PT_TGT_ESERC_ASM"));
				if (rs.wasNull()) {
					bean.setFip04NrPiattaforma(null);
				}
				bean.setFip07LingProgPrincipale(rs
						.getString("FIP07_LING_PROG_PRIN_REAL_ASM"));
				bean.setFip10GradoAccessibilita(rs
						.getInt("FIP10_GRADO_ACCESSIBILITA_ASM"));
				if (rs.wasNull()) {
					bean.setFip10GradoAccessibilita(null);
				}
				bean.setFip11GradoQualitaCod(rs
						.getString("FIP11_GRADO_QUALITA_COD_ASM"));
				bean.setFip12UtilizzoFramework(rs
						.getString("FIP12_UT_FRAMEWORK_AZ_ASM"));
				bean.setFip13ComplessitaAlg(rs.getInt("FIP13_COMP_ALG_SW_ASM"));
				if (rs.wasNull()) {
					bean.setFip13ComplessitaAlg(null);
				}
				bean.setFip15LivelloCura(rs
						.getString("FIP15_LV_CURA_GRAF_INT_UT_ASM"));

				// Edma
				bean.setStrutturaOrganizzativaFk(0);

				// Elettra
				bean.setUnitaOrganizzativaFk(0);

				asm.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asm;
	}

	public static List<Tuple> getAsm(DmalmAsm app) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> applicazione = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			applicazione = query
					.from(asm)
					.where(asm.idAsm.eq(app.getIdAsm()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999())).list(asm.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return applicazione;
	}

	public static List<Tuple> getAsmByApplicazione(DmalmAsm app)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> applicazione = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			applicazione = query
					.from(asm)
					.where(asm.applicazione.eq(app.getApplicazione()))
					.where(asm.annullato
							.eq(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999())).list(asm.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return applicazione;
	}

	public static void insertAsm(Timestamp dataEsecuzione, DmalmAsm applicazioni)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, asm)
					.columns(asm.dmalmAsmPk, asm.dmalmStgMisuraPk, asm.noteAsm,
							asm.pAppAccAuthLastUpdate,
							asm.pAppCdAltreAsmCommonServ,
							asm.pAppCdAmbitoManAttuale,
							asm.pAppCodAmbitoManFuturo,
							asm.pAppCodAsmConfinanti,
							asm.pAppCodDirezioneDemand, asm.pAppCodFlussiIoAsm,
							asm.pAppDataFineValiditaAsm,
							asm.pAppDataInizioValiditaAsm,
							asm.pAppDataUltimoAggiorn,
							asm.pAppDenomSistTerziConfin,
							asm.pAppDenomUtentiFinaliAsm,
							asm.pAppDenominazioneAsm,
							asm.pAppFlagDamisurarePatrFp,
							asm.pAppFlagInManutenzione,
							asm.pAppFlagMisurareSvimevFp,
							asm.pAppFlagServizioComune,
							asm.pAppIndicValidazioneAsm,
							asm.pAppNomeAuthLastUpdate, asm.appCls,
							asm.applicazione, asm.dataDismissione,
							asm.dataInizioEsercizio, asm.dataCaricamento,
							asm.frequenzaUtilizzo, asm.idAsm,
							asm.includiInDbPatrimonio, asm.numeroUtenti,
							asm.permissions, asm.proprietaLegale,
							asm.utilizzata, asm.vafPredefinito,
							asm.dataInizioValidita, asm.dataFineValidita,
							asm.annullato, asm.strutturaOrganizzativaFk,
							asm.unitaOrganizzativaFk,
							asm.dtPrevistaProssimaMpp,
							asm.fip01InizioEsercizio, asm.fip02IndiceQualita,
							asm.fip03ComplessEserc, asm.fip04NrPiattaforma,
							asm.fip07LingProgPrincipale,
							asm.fip10GradoAccessibilita,
							asm.fip11GradoQualitaCod,
							asm.fip12UtilizzoFramework,
							asm.fip13ComplessitaAlg, asm.fip15LivelloCura)
					.values(StringTemplate.create("DM_ALM_ASM_SEQ.nextval"),
							applicazioni.getDmalmStgMisuraPk(),
							applicazioni.getNoteAsm(),
							applicazioni.getpAppAccAuthLastUpdate(),
							applicazioni.getpAppCdAltreAsmCommonServ(),
							applicazioni.getpAppCdAmbitoManAttuale(),
							applicazioni.getpAppCodAmbitoManFuturo(),
							applicazioni.getpAppCodAsmConfinanti(),
							applicazioni.getpAppCodDirezioneDemand(),
							applicazioni.getpAppCodFlussiIoAsm(),
							applicazioni.getpAppDataFineValiditaAsm(),
							applicazioni.getpAppDataInizioValiditaAsm(),
							applicazioni.getpAppDataUltimoAggiorn(),
							applicazioni.getpAppDenomSistTerziConfin(),
							applicazioni.getpAppDenomUtentiFinaliAsm(),
							applicazioni.getpAppDenominazioneAsm(),
							applicazioni.getpAppFlagDamisurarePatrFp(),
							applicazioni.getpAppFlagInManutenzione(),
							applicazioni.getpAppFlagMisurareSvimevFp(),
							applicazioni.getpAppFlagServizioComune(),
							applicazioni.getpAppIndicValidazioneAsm(),
							applicazioni.getpAppNomeAuthLastUpdate(),
							applicazioni.getAppCls(),
							applicazioni.getApplicazione(),
							applicazioni.getDataDismissione(),
							applicazioni.getDataInizioEsercizio(),
							applicazioni.getDataCaricamento(),
							applicazioni.getFrequenzaUtilizzo(),
							applicazioni.getIdAsm(),
							applicazioni.getIncludiInDbPatrimonio(),
							applicazioni.getNumeroUtenti(),
							applicazioni.getPermissions(),
							applicazioni.getProprietaLegale(),
							applicazioni.getUtilizzata(),
							applicazioni.getVafPredefinito(),
							dataEsecuzione,
							DateUtils.setDtFineValidita9999(),
							applicazioni.getAnnullato(),
							applicazioni.getStrutturaOrganizzativaFk(),
							applicazioni.getUnitaOrganizzativaFk(),
							applicazioni.getDtPrevistaProssimaMpp(),
							applicazioni.getFip01InizioEsercizio(),
							applicazioni.getFip02IndiceQualita(),
							applicazioni.getFip03ComplessEserc(),
							applicazioni.getFip04NrPiattaforma(),
							applicazioni.getFip07LingProgPrincipale(),
							applicazioni.getFip10GradoAccessibilita(),
							applicazioni.getFip11GradoQualitaCod(),
							applicazioni.getFip12UtilizzoFramework(),
							applicazioni.getFip13ComplessitaAlg(),
							applicazioni.getFip15LivelloCura()).execute();

			connection.commit();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDataFineValidita(Timestamp dataEsecuzione,
			DmalmAsm applicazioni) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, asm)
					.where(asm.idAsm.eq(applicazioni.getIdAsm()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(asm.dataFineValidita,
							DateUtils.addDaysToTimestamp(DataEsecuzione
									.getInstance().getDataEsecuzione(), -1))
					.execute();

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

	public static void updateAsm(DmalmAsm applicazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, asm)
					.where(asm.idAsm.eq(applicazione.getIdAsm()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(asm.noteAsm, applicazione.getNoteAsm())
					.set(asm.pAppAccAuthLastUpdate,
							applicazione.getpAppAccAuthLastUpdate())
					.set(asm.pAppCdAltreAsmCommonServ,
							applicazione.getpAppCdAltreAsmCommonServ())
					.set(asm.pAppCdAmbitoManAttuale,
							applicazione.getpAppCdAmbitoManAttuale())
					.set(asm.pAppCodAmbitoManFuturo,
							applicazione.getpAppCodAmbitoManFuturo())
					.set(asm.pAppCodAsmConfinanti,
							applicazione.getpAppCodAsmConfinanti())
					.set(asm.pAppCodDirezioneDemand,
							applicazione.getpAppCodDirezioneDemand())
					.set(asm.pAppCodFlussiIoAsm,
							applicazione.getpAppCodFlussiIoAsm())
					.set(asm.pAppDataFineValiditaAsm,
							applicazione.getpAppDataFineValiditaAsm())
					.set(asm.pAppDataInizioValiditaAsm,
							applicazione.getpAppDataInizioValiditaAsm())
					.set(asm.pAppDataUltimoAggiorn,
							applicazione.getpAppDataUltimoAggiorn())
					.set(asm.pAppDenomSistTerziConfin,
							applicazione.getpAppDenomSistTerziConfin())
					.set(asm.pAppDenomUtentiFinaliAsm,
							applicazione.getpAppDenomUtentiFinaliAsm())
					.set(asm.pAppDenominazioneAsm,
							applicazione.getpAppDenominazioneAsm())
					.set(asm.pAppFlagDamisurarePatrFp,
							applicazione.getpAppFlagDamisurarePatrFp())
					.set(asm.pAppFlagInManutenzione,
							applicazione.getpAppFlagInManutenzione())
					.set(asm.pAppFlagMisurareSvimevFp,
							applicazione.getpAppFlagMisurareSvimevFp())
					.set(asm.pAppFlagServizioComune,
							applicazione.getpAppFlagServizioComune())
					.set(asm.pAppIndicValidazioneAsm,
							applicazione.getpAppIndicValidazioneAsm())
					.set(asm.pAppNomeAuthLastUpdate,
							applicazione.getpAppNomeAuthLastUpdate())
					.set(asm.appCls, applicazione.getAppCls())
					.set(asm.dataDismissione, applicazione.getDataDismissione())
					.set(asm.dataInizioEsercizio,
							applicazione.getDataInizioEsercizio())
					.set(asm.frequenzaUtilizzo,
							applicazione.getFrequenzaUtilizzo())
					.set(asm.idAsm, applicazione.getIdAsm())
					.set(asm.includiInDbPatrimonio,
							applicazione.getIncludiInDbPatrimonio())
					.set(asm.numeroUtenti, applicazione.getNumeroUtenti())
					.set(asm.permissions, applicazione.getPermissions())
					.set(asm.proprietaLegale, applicazione.getProprietaLegale())
					.set(asm.utilizzata, applicazione.getUtilizzata())
					.set(asm.vafPredefinito, applicazione.getVafPredefinito())
					.set(asm.annullato, applicazione.getAnnullato())
					.set(asm.dtPrevistaProssimaMpp,
							applicazione.getDtPrevistaProssimaMpp())
					.set(asm.fip01InizioEsercizio,
							applicazione.getFip01InizioEsercizio())
					.set(asm.fip02IndiceQualita,
							applicazione.getFip02IndiceQualita())
					.set(asm.fip03ComplessEserc,
							applicazione.getFip03ComplessEserc())
					.set(asm.fip04NrPiattaforma,
							applicazione.getFip04NrPiattaforma())
					.set(asm.fip07LingProgPrincipale,
							applicazione.getFip07LingProgPrincipale())
					.set(asm.fip10GradoAccessibilita,
							applicazione.getFip10GradoAccessibilita())
					.set(asm.fip11GradoQualitaCod,
							applicazione.getFip11GradoQualitaCod())
					.set(asm.fip12UtilizzoFramework,
							applicazione.getFip12UtilizzoFramework())
					.set(asm.fip13ComplessitaAlg,
							applicazione.getFip13ComplessitaAlg())
					.set(asm.fip15LivelloCura,
							applicazione.getFip15LivelloCura()).execute();

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

	public static void insertAsmUpdate(Timestamp dataEsecuzione,
			DmalmAsm applicazioni) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLInsertClause(connection, dialect, asm)
					.columns(asm.dmalmAsmPk, asm.dmalmStgMisuraPk, asm.noteAsm,
							asm.pAppAccAuthLastUpdate,
							asm.pAppCdAltreAsmCommonServ,
							asm.pAppCdAmbitoManAttuale,
							asm.pAppCodAmbitoManFuturo,
							asm.pAppCodAsmConfinanti,
							asm.pAppCodDirezioneDemand, asm.pAppCodFlussiIoAsm,
							asm.pAppDataFineValiditaAsm,
							asm.pAppDataInizioValiditaAsm,
							asm.pAppDataUltimoAggiorn,
							asm.pAppDenomSistTerziConfin,
							asm.pAppDenomUtentiFinaliAsm,
							asm.pAppDenominazioneAsm,
							asm.pAppFlagDamisurarePatrFp,
							asm.pAppFlagInManutenzione,
							asm.pAppFlagMisurareSvimevFp,
							asm.pAppFlagServizioComune,
							asm.pAppIndicValidazioneAsm,
							asm.pAppNomeAuthLastUpdate, asm.appCls,
							asm.applicazione, asm.dataDismissione,
							asm.dataInizioEsercizio, asm.dataCaricamento,
							asm.frequenzaUtilizzo, asm.idAsm,
							asm.includiInDbPatrimonio, asm.numeroUtenti,
							asm.permissions, asm.proprietaLegale,
							asm.utilizzata, asm.vafPredefinito,
							asm.dataInizioValidita, asm.dataFineValidita,
							asm.annullato, asm.strutturaOrganizzativaFk,
							asm.unitaOrganizzativaFk,
							asm.dtPrevistaProssimaMpp,
							asm.fip01InizioEsercizio, asm.fip02IndiceQualita,
							asm.fip03ComplessEserc, asm.fip04NrPiattaforma,
							asm.fip07LingProgPrincipale,
							asm.fip10GradoAccessibilita,
							asm.fip11GradoQualitaCod,
							asm.fip12UtilizzoFramework,
							asm.fip13ComplessitaAlg, asm.fip15LivelloCura)
					.values(StringTemplate.create("DM_ALM_ASM_SEQ.nextval"),
							applicazioni.getDmalmStgMisuraPk(),
							applicazioni.getNoteAsm(),
							applicazioni.getpAppAccAuthLastUpdate(),
							applicazioni.getpAppCdAltreAsmCommonServ(),
							applicazioni.getpAppCdAmbitoManAttuale(),
							applicazioni.getpAppCodAmbitoManFuturo(),
							applicazioni.getpAppCodAsmConfinanti(),
							applicazioni.getpAppCodDirezioneDemand(),
							applicazioni.getpAppCodFlussiIoAsm(),
							applicazioni.getpAppDataFineValiditaAsm(),
							applicazioni.getpAppDataInizioValiditaAsm(),
							applicazioni.getpAppDataUltimoAggiorn(),
							applicazioni.getpAppDenomSistTerziConfin(),
							applicazioni.getpAppDenomUtentiFinaliAsm(),
							applicazioni.getpAppDenominazioneAsm(),
							applicazioni.getpAppFlagDamisurarePatrFp(),
							applicazioni.getpAppFlagInManutenzione(),
							applicazioni.getpAppFlagMisurareSvimevFp(),
							applicazioni.getpAppFlagServizioComune(),
							applicazioni.getpAppIndicValidazioneAsm(),
							applicazioni.getpAppNomeAuthLastUpdate(),
							applicazioni.getAppCls(),
							applicazioni.getApplicazione(),
							applicazioni.getDataDismissione(),
							applicazioni.getDataInizioEsercizio(),
							applicazioni.getDataCaricamento(),
							applicazioni.getFrequenzaUtilizzo(),
							applicazioni.getIdAsm(),
							applicazioni.getIncludiInDbPatrimonio(),
							applicazioni.getNumeroUtenti(),
							applicazioni.getPermissions(),
							applicazioni.getProprietaLegale(),
							applicazioni.getUtilizzata(),
							applicazioni.getVafPredefinito(), dataEsecuzione,
							DateUtils.setDtFineValidita9999(),
							applicazioni.getAnnullato(),
							applicazioni.getStrutturaOrganizzativaFk(),
							applicazioni.getUnitaOrganizzativaFk(),
							applicazioni.getDtPrevistaProssimaMpp(),
							applicazioni.getFip01InizioEsercizio(),
							applicazioni.getFip02IndiceQualita(),
							applicazioni.getFip03ComplessEserc(),
							applicazioni.getFip04NrPiattaforma(),
							applicazioni.getFip07LingProgPrincipale(),
							applicazioni.getFip10GradoAccessibilita(),
							applicazioni.getFip11GradoQualitaCod(),
							applicazioni.getFip12UtilizzoFramework(),
							applicazioni.getFip13ComplessitaAlg(),
							applicazioni.getFip15LivelloCura()).execute();

			connection.commit();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void setRemovedAsm(Integer idAsm) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, asm)
					.where(asm.idAsm.eq(idAsm.shortValue()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(asm.annullato, "ANNULLATO FISICAMENTE").execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getAllAsmToLinkWithProduct() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le Asm non associate a Prodotto (si escude il recod DUMMY:
			// dmalmAsmPk = 0)
			asmList = query
					.from(asm)
					.leftJoin(asmprodotto)
					.on(asmprodotto.dmalmAsmPk.eq(asm.dmalmAsmPk).and(
							asmprodotto.dtFineValidita.eq(DateUtils
									.setDtFineValidita9999())))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.dmalmAsmPk.ne(new Integer(0)))
					.where(asmprodotto.dmalmProdottoSeq.isNull().or(
							(asmprodotto.dmalmProdottoSeq.eq(0))))
					.list(asm.applicazione, asm.dmalmAsmPk,
							asmprodotto.dmalmProdottoSeq,
							asmprodotto.dmalmAsmPk,
							asmprodotto.dtInizioValidita,
							asmprodotto.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static List<Tuple> getAllRelationsToClose() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le Asm chiuse con relazione a Prodotto ancora aperta
			asmList = query
					.from(asm)
					.join(asmprodotto)
					.on(asmprodotto.dmalmAsmPk.eq(asm.dmalmAsmPk))
					.where(asm.dataFineValidita.ne(DateUtils
							.setDtFineValidita9999()))
					.where(asmprodotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.list(asmprodotto.dmalmAsmPk, asmprodotto.dmalmProdottoSeq,
							asmprodotto.dtInizioValidita, asm.dataFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static List<Tuple> getAllRelationsToCloseCauseAsmsClosed()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le Asm chiuse con relazione a ProdottiArchitetture ancora
			// aperta
			asmList = query
					.from(asm)
					.join(asmProdottiArchitetture)
					.on(asmProdottiArchitetture.dmalmAsmPk.eq(asm.dmalmAsmPk))
					.where(asm.dataFineValidita.ne(DateUtils
							.setDtFineValidita9999()))
					.where(asmProdottiArchitetture.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.list(asmProdottiArchitetture.dmalmAsmPk,
							asmProdottiArchitetture.dmalmProdottoPk,
							asmProdottiArchitetture.dtInizioValidita,
							asm.dataFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static List<Tuple> getAllAsmToLinkWithProductArchitecture()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le Asm non associate a Prodotto (si escude il recod DUMMY:
			// dmalmAsmPk = 0)
			asmList = query
					.from(asm)
					.leftJoin(asmProdottiArchitetture)
					.on(asmProdottiArchitetture.dmalmAsmPk.eq(asm.dmalmAsmPk)
							.and(asmProdottiArchitetture.dtFineValidita
									.eq(DateUtils.setDtFineValidita9999())))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.dmalmAsmPk.ne(new Integer(0)))
					.where(asmProdottiArchitetture.dmalmProdottoPk.isNull().or(
							(asmProdottiArchitetture.dmalmProdottoPk.eq(0))))
					.list(asm.applicazione, asm.dmalmAsmPk,
							asmProdottiArchitetture.dmalmProdottoPk,
							asmProdottiArchitetture.dmalmAsmPk,
							asmProdottiArchitetture.dtInizioValidita,
							asmProdottiArchitetture.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static List<Tuple> getAsmAnnullataProdotto(Integer prodottoPk)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le Asm non associate a Prodotto (si escude il recod DUMMY:
			// dmalmAsmPk = 0)
			asmList = query
					.from(asm)
					.leftJoin(asmProdottiArchitetture)
					.on(asmProdottiArchitetture.dmalmAsmPk.eq(asm.dmalmAsmPk)
							.and(asmProdottiArchitetture.dtFineValidita
									.eq(DateUtils.setDtFineValidita9999())))
					.where(asmProdottiArchitetture.dmalmProdottoPk.eq(prodottoPk))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.dmalmAsmPk.ne(new Integer(0)))
					.where(asmProdottiArchitetture.dmalmProdottoPk.isNull().or(
							(asmProdottiArchitetture.dmalmProdottoPk.eq(0))))
					.list(asm.applicazione, asm.dmalmAsmPk,
							asmProdottiArchitetture.dmalmProdottoPk,
							asmProdottiArchitetture.dmalmAsmPk,
							asmProdottiArchitetture.dtInizioValidita,
							asmProdottiArchitetture.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}
	public static List<Tuple> getAllAsmStrutturaOrganizzativa()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le asm nuove o quelle per le quali la FK Struttura
			// Organizzativa è variata
			asmList = query
					.from(asm)
					.join(asmprodotto)
					.on(asmprodotto.dmalmAsmPk.eq(asm.dmalmAsmPk))
					.join(prodotto)
					.on(prodotto.dmalmProdottoSeq
							.eq(asmprodotto.dmalmProdottoSeq))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.idAsm.gt(new Short("0")))
					.where(asmprodotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.strutturaOrganizzativaFk
							.ne(prodotto.dmalmUnitaOrganizzativaFk01))
					.distinct()
					.list(asm.idAsm, prodotto.dmalmUnitaOrganizzativaFk01);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static List<Tuple> getAllAsmUnitaOrganizzativa() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutte le asm nuove o quelle per le quali la FK Unita
			// Organizzativa è variata

			asmList = query
					.from(asm)
					.join(asmProdottiArchitetture)
					.on(asmProdottiArchitetture.dmalmAsmPk.eq(asm.dmalmAsmPk))
					.join(elettraProdotto)
					.on(elettraProdotto.prodottoPk
							.eq(asmProdottiArchitetture.dmalmProdottoPk))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.idAsm.gt(new Short("0")))
					.where(asmProdottiArchitetture.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where((asm.unitaOrganizzativaFk.isNull()
							.or(asm.unitaOrganizzativaFk
							.ne(elettraProdotto.unitaOrganizzativaFk))
							))
					.where(asm.applicazione.notLike("#ANNULLATO LOGICAMENTE##%"))
					.distinct()
					.list(asm.idAsm, elettraProdotto.unitaOrganizzativaFk);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmList;
	}

	public static void updateFkStrutturaOrganizzativa(DmalmAsm applicazioni)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, asm)
					.where(asm.idAsm.eq(applicazioni.getIdAsm()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(asm.strutturaOrganizzativaFk,
							applicazioni.getStrutturaOrganizzativaFk())
					.execute();

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

	public static void updateFkUnitaOrganizzativa(DmalmAsm applicazioni)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			//
			new SQLUpdateClause(connection, dialect, asm)
					.where(asm.idAsm.eq(applicazioni.getIdAsm()))
					.where(asm.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(asm.unitaOrganizzativaFk,
							applicazioni.getUnitaOrganizzativaFk()).execute();

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

//	public static List<DmalmAsm> getAsmToLink(String siglaPrj,
//			Timestamp inizio, Timestamp fine) throws DAOException {
//		ConnectionManager cm = null;
//		Connection connection = null;
//
//		List<DmalmAsm> ret = new ArrayList<DmalmAsm>();
//		List<Tuple> t = new ArrayList<Tuple>();
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			SQLQuery query = new SQLQuery(connection, dialect);
//
//			t = query
//					.from(asm)
//					.where(asm.applicazione.eq(siglaPrj))
//					.where(asm.dataInizioValidita.between(inizio, fine).or(
//							asm.dataFineValidita.between(inizio, fine)))
//					.list(asm.all());
//
//			if (t.size() > 0) {
//				for (Tuple tuple : t) {
//						DmalmAsm a = new DmalmAsm();
//						a.setApplicazione(tuple.get(asm.applicazione));
//						a.setDmalmAsmPk(tuple.get(asm.dmalmAsmPk).toString());
//						a.setDataInizioValidita(tuple
//								.get(asm.dataInizioValidita));
//						a.setDataFineValidita(tuple.get(asm.dataFineValidita));
//						ret.add(a);
//				}
//			} else {// non trovato, da legare al tappo
//				DmalmAsm a = new DmalmAsm();
//				a.setApplicazione(siglaPrj);
//				a.setDmalmAsmPk("0");
//				ret.add(a);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new DAOException(e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//		return ret;
//	}

	public static List<DmalmAsm> getAsmToLinkAndSplit(String siglaPrj,
			Timestamp inizio, Timestamp fine) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmAsm> ret = new ArrayList<>();
		List<Tuple> t = new ArrayList<>();

		try {
			// caso x.y.z (prodotto.modulo.funzionalita)
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);
			
			t = query.from(asm)
					.where(asm.annullato.isNull())
					.where(asm.applicazione.eq(siglaPrj)
							.or(asm.applicazione.like(siglaPrj + "..%"))
							.or(asm.applicazione.like("%.." + siglaPrj + "..%"))
							.or(asm.applicazione.like("%.." + siglaPrj))
							.or(asm.applicazione.like(siglaPrj + ".%"))
							.or(asm.applicazione.like("%.." + siglaPrj + ".%")))
					.where(asm.dataInizioValidita.between(inizio, fine)
						.or(asm.dataFineValidita.between(inizio, fine))
						.or(asm.dataInizioValidita.before(inizio).and(asm.dataFineValidita.after(fine))))
					.list(asm.all());

			if (t.size() > 0) {
				for (Tuple tuple : t) {
						DmalmAsm a = new DmalmAsm();
						a.setDmalmAsmPk(tuple.get(asm.dmalmAsmPk).toString());
						a.setDataInizioValidita(tuple
								.get(asm.dataInizioValidita));
						a.setDataFineValidita(tuple.get(asm.dataFineValidita));
						ret.add(a);
				}
			} else {
				// caso x.y (prodotto.modulo)
				String[] temp = siglaPrj.split("\\.");
				if (temp.length > 1) {
					siglaPrj = temp[0].concat(".").concat(temp[1]);
				}
				
				query = new SQLQuery(connection, dialect);

				t = query.from(asm)
						.where(asm.annullato.isNull())
						.where(asm.applicazione.eq(siglaPrj)
								.or(asm.applicazione.like(siglaPrj + "..%"))
								.or(asm.applicazione.like("%.." + siglaPrj + "..%"))
								.or(asm.applicazione.like("%.." + siglaPrj)))
						.where(asm.dataInizioValidita.between(inizio, fine)
							.or(asm.dataFineValidita.between(inizio, fine))
							.or(asm.dataInizioValidita.before(inizio).and(asm.dataFineValidita.after(fine))))
						.list(asm.all());
				
				if (t.size() > 0) {
					for (Tuple tuple : t) {
							DmalmAsm a = new DmalmAsm();
							a.setDmalmAsmPk(tuple.get(asm.dmalmAsmPk).toString());
							a.setDataInizioValidita(tuple
									.get(asm.dataInizioValidita));
							a.setDataFineValidita(tuple.get(asm.dataFineValidita));
							ret.add(a);
					}
				} else {
					// caso x (prodotto)
					List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(siglaPrj);
					if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
						temp = siglaPrj.split("\\.");
						siglaPrj = temp[0];
					}
					
					query = new SQLQuery(connection, dialect);
						
					t = query.from(asm)
							.where(asm.annullato.isNull())
							.where(asm.applicazione.eq(siglaPrj)
									.or(asm.applicazione.like(siglaPrj + "..%"))
									.or(asm.applicazione.like("%.." + siglaPrj + "..%"))
									.or(asm.applicazione.like("%.." + siglaPrj))
									.or(asm.applicazione.like(siglaPrj + ".%"))
									.or(asm.applicazione.like("%.." + siglaPrj + ".%")))
							.where(asm.dataInizioValidita.between(inizio, fine)
								.or(asm.dataFineValidita.between(inizio, fine))
								.or(asm.dataInizioValidita.before(inizio).and(asm.dataFineValidita.after(fine))))
							.list(asm.all());
					
					if (t.size() > 0) {
						for (Tuple tuple : t) {
								DmalmAsm a = new DmalmAsm();
								a.setDmalmAsmPk(tuple.get(asm.dmalmAsmPk).toString());
								a.setDataInizioValidita(tuple
										.get(asm.dataInizioValidita));
								a.setDataFineValidita(tuple.get(asm.dataFineValidita));
								ret.add(a);
						}
					} else {
						// Nessuna asm trovata, da legare al tappo
						DmalmAsm a = new DmalmAsm();
						a.setDmalmAsmPk("0");
						a.setDataInizioValidita(inizio);
						a.setDataFineValidita(fine);
						ret.add(a);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return ret;
	}

	public static void updateErrorColumn(Tuple row, String error) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> asmList = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			
			SQLQuery query = new SQLQuery(connection, dialect);
			
			asmList = query.from(asm)
					.where(asm.idAsm.eq(row.get(asm.idAsm)))
					.where(asm.dataFineValidita.eq(DateUtils.setDtFineValidita9999()))
					.list(asm.all());
			for(Tuple tuple:asmList){
				
				String errors= tuple.get(asm.errori);
				errors+=error+";";
				
				new SQLUpdateClause(connection, dialect, asm)
				.where(asm.idAsm.eq(row.get(asm.idAsm)))
				.where(asm.dataFineValidita.eq(DateUtils
						.setDtFineValidita9999()))
				.set(asm.errori,errors);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}finally{
			
			if(cm!=null)
				cm.closeConnection(connection);
		}
		
		
	}
}
