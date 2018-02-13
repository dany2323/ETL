package lispa.schedulers.dao.sfera;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PROGETTI_SFERA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.sfera.DmalmProgettoSfera;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmAsmProjectEl;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;
import lispa.schedulers.utils.DateUtils;

public class DmAlmProgettoSferaDAO {

	private static Logger logger = Logger
			.getLogger(DmAlmProgettoSferaDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoSfera prog = QDmalmProgettoSfera.dmalmProgettoSfera;
	private static QDmalmProgettoSviluppoSvil progSv = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
	private static QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;
	private static QDmalmAsm asm = QDmalmAsm.dmalmAsm;
	private static QDmalmStatoWorkitem statoWorkitem = QDmalmStatoWorkitem.dmalmStatoWorkitem;
	private static QDmalmAsmProjectEl asmProjectProdotto = QDmalmAsmProjectEl.dmalmAsmProject;

	public static List<DmalmProgettoSfera> getAllProgetti(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProgettoSfera bean = null;
		List<DmalmProgettoSfera> project = new LinkedList<DmalmProgettoSfera>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance()
					.getQuery(SQL_PROGETTI_SFERA);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Progetti Sfera eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmProgettoSfera();

				bean.setDmalmStgMisuraPk(rs.getInt("DMALM_STG_MISURA_PK"));
				bean.setCosto(rs.getInt("COSTO"));
				bean.setDataFineEffettiva(rs.getTimestamp("DT_FINE_EFFETTIVA"));
				bean.setDataAvvio(rs.getTimestamp("DT_AVVIO"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setDurataEffettiva(rs.getInt("DURATA_EFFETTIVA"));
				bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
				bean.setImpegnoEffettivo(rs.getInt("IMPEGNO_EFFETTIVO"));

				if (rs.getString("INCLUDE_IN_BENCHMARKING_DB") == null) {
					bean.setIncludeInBenchmarkingDb(null);
				} else {
					if (rs.getString("INCLUDE_IN_BENCHMARKING_DB")
							.equalsIgnoreCase("Vero")) {
						bean.setIncludeInBenchmarkingDb(new Short((short) 1));
					} else {
						bean.setIncludeInBenchmarkingDb(new Short((short) 0));
					}
				}

				// bean.setIncludeInBenchmarkingDb(rs
				// .getShort("INCLUDE_IN_BENCHMARKING_DB"));
				bean.setNotePrj(rs.getString("NOTE_PRJ"));
				bean.setpPrjADisposizione01(rs
						.getString("P_PRJ_A_DISPOSIZIONE_01"));
				bean.setpPrjADisposizione02(rs
						.getString("P_PRJ_A_DISPOSIZIONE_02"));

				if (rs.getString("P_PRJ_AMB_TECN_PORTALI") == null) {
					bean.setpPrjAmbTecnPortali(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_AMB_TECN_PORTALI")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjAmbTecnPortali(new Short((short) 1));
					} else {
						bean.setpPrjAmbTecnPortali(new Short((short) 0));
					}
				}
				// bean.setpPrjAmbTecnPortali(rs
				// .getShort("P_PRJ_AMB_TECN_PORTALI"));
				bean.setpPrjAuditIndexVerify(rs
						.getString("P_PRJ_AUDIT_INDEX_VERIFY"));
				bean.setpPrjAuditMonitore(rs.getString("P_PRJ_AUDIT_MONITORE"));
				bean.setpPrjCodRdi(rs.getInt("P_PRJ_CD_RDI"));
//				bean.setpPrjDtConsegnaMppForn(rs
//						.getTimestamp("P_PRJ_DT_CONSEGNA_MPP_FORN"));
				bean.setpPrjFccFattCorrezTotal(rs
						.getDouble("P_PRJ_FCC_FATT_CORREZ_TOTAL"));

				if (rs.getString("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP") == null) {
					bean.setpPrjFlAmbTecTransBatRep(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlAmbTecTransBatRep(new Short((short) 1));
					} else {
						bean.setpPrjFlAmbTecTransBatRep(new Short((short) 0));
					}
				}
				// bean.setpPrjFlAmbTecTransBatRep(rs
				// .getShort("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP"));

				if (rs.getString("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR") == null) {
					bean.setpPrjFlAmbTecnPiatEnterpr(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlAmbTecnPiatEnterpr(new Short((short) 1));
					} else {
						bean.setpPrjFlAmbTecnPiatEnterpr(new Short((short) 0));
					}
				}
				// bean.setpPrjFlAmbTecnPiatEnterpr(rs
				// .getShort("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR"));

				if (rs.getString("P_PRJ_FL_AMBITO_TECN_FUTURO01") == null) {
					bean.setpPrjFlAmbitoTecnFuturo01(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_AMBITO_TECN_FUTURO01")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlAmbitoTecnFuturo01(new Short((short) 1));
					} else {
						bean.setpPrjFlAmbitoTecnFuturo01(new Short((short) 0));
					}
				}
				// bean.setpPrjFlAmbitoTecnFuturo01(rs
				// .getShort("P_PRJ_FL_AMBITO_TECN_FUTURO01"));

				if (rs.getString("P_PRJ_FL_AMBITO_TECN_FUTURO02") == null) {
					bean.setpPrjFlAmbitoTecnFuturo02(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_AMBITO_TECN_FUTURO02")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlAmbitoTecnFuturo02(new Short((short) 1));
					} else {
						bean.setpPrjFlAmbitoTecnFuturo02(new Short((short) 0));
					}
				}
				// bean.setpPrjFlAmbitoTecnFuturo02(rs
				// .getShort("P_PRJ_FL_AMBITO_TECN_FUTURO02"));

				if (rs.getString("P_PRJ_FL_APPL_LG_FP_EDMA") == null) {
					bean.setpPrjFlApplLgFpEdma(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPL_LG_FP_EDMA")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlApplLgFpEdma(new Short((short) 1));
					} else {
						bean.setpPrjFlApplLgFpEdma(new Short((short) 0));
					}
				}
				// bean.setpPrjFlApplLgFpEdma(rs
				// .getShort("P_PRJ_FL_APPL_LG_FP_EDMA"));

				if (rs.getString("P_PRJ_FL_APPL_LG_FP_WEB") == null) {
					bean.setpPrjFlApplLgFpWeb(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPL_LG_FP_WEB")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlApplLgFpWeb(new Short((short) 1));
					} else {
						bean.setpPrjFlApplLgFpWeb(new Short((short) 0));
					}
				}
				// bean.setpPrjFlApplLgFpWeb(rs
				// .getShort("P_PRJ_FL_APPL_LG_FP_WEB"));

				if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_DWH") == null) {
					bean.setpPrjFlApplicLgFpDwh(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_DWH")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlApplicLgFpDwh(new Short((short) 1));
					} else {
						bean.setpPrjFlApplicLgFpDwh(new Short((short) 0));
					}
				}
				// bean.setpPrjFlApplicLgFpDwh(rs
				// .getShort("P_PRJ_FL_APPLIC_LG_FP_DWH"));

				if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_FUTURO01") == null) {
					bean.setpPrjFlApplicLgFpFuturo01(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_FUTURO01")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlApplicLgFpFuturo01(new Short((short) 1));
					} else {
						bean.setpPrjFlApplicLgFpFuturo01(new Short((short) 0));
					}
				}
				// bean.setpPrjFlApplicLgFpFuturo01(rs
				// .getShort("P_PRJ_FL_APPLIC_LG_FP_FUTURO01"));

				if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_FUTURO02") == null) {
					bean.setpPrjFlApplicLgFpFuturo02(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPLIC_LG_FP_FUTURO02")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlApplicLgFpFuturo02(new Short((short) 1));
					} else {
						bean.setpPrjFlApplicLgFpFuturo02(new Short((short) 0));
					}
				}
				// bean.setpPrjFlApplicLgFpFuturo02(rs
				// .getShort("P_PRJ_FL_APPLIC_LG_FP_FUTURO02"));

				if (rs.getString("P_PRJ_FL_AMB_TECN_GIS") == null) {
					bean.setpPrjFlagAmbTecnGis(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_AMB_TECN_GIS").equalsIgnoreCase(
							"SI")) {
						bean.setpPrjFlagAmbTecnGis(new Short((short) 1));
					} else {
						bean.setpPrjFlagAmbTecnGis(new Short((short) 0));
					}
				}
				// bean.setpPrjFlagAmbTecnGis(rs.getShort("P_PRJ_FL_AMB_TECN_GIS"));

				if (rs.getString("P_PRJ_FL_APPL_LG_FP_GIS") == null) {
					bean.setpPrjFlagApplLgFpGis(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPL_LG_FP_GIS")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlagApplLgFpGis(new Short((short) 1));
					} else {
						bean.setpPrjFlagApplLgFpGis(new Short((short) 0));
					}
				}
				// bean.setpPrjFlagApplLgFpGis(rs
				// .getShort("P_PRJ_FL_APPL_LG_FP_GIS"));

				if (rs.getString("P_PRJ_FL_APPL_LG_FP_MWARE") == null) {
					bean.setpPrjFlagApplLgFpMware(new Short((short) 0));
				} else {
					if (rs.getString("P_PRJ_FL_APPL_LG_FP_MWARE")
							.equalsIgnoreCase("SI")) {
						bean.setpPrjFlagApplLgFpMware(new Short((short) 1));
					} else {
						bean.setpPrjFlagApplLgFpMware(new Short((short) 0));
					}
				}
				// bean.setpPrjFlagApplLgFpMware(rs
				// .getShort("P_PRJ_FL_APPL_LG_FP_MWARE"));

				bean.setpPrjFornitoreMpp(rs.getString("P_PRJ_FORNITORE_MPP"));
				bean.setpPrjFornitoreSviluppoMev(rs
						.getString("P_PRJ_FORNITORE_SVILUPPO_MEV"));
				bean.setpPrjImportoAConsuntivo(rs
						.getDouble("P_PRJ_IMPORTO_A_CONSUNTIVO"));
				bean.setpPrjImportoRdiAPreventivo(rs
						.getDouble("P_PRJ_IMPORTO_RDI_A_PREVENTIVO"));
				bean.setpPrjIndexAlmValidProgAsm(rs
						.getShort("P_PRJ_INDEX_ALM_VALID_PROG_ASM"));
				bean.setpPrjMfcAConsuntivo(rs
						.getDouble("P_PRJ_MFC_A_CONSUNTIVO"));
				bean.setpPrjMfcAPreventivo(rs
						.getDouble("P_PRJ_MFC_A_PREVENTIVO"));
				bean.setpPrjMpPercentCicloDiVita(rs
						.getDouble("P_PRJ_MP_PERCENT_CICLO_DI_VITA"));
				bean.setpPrjPunPrezzoUnitNominal(rs
						.getDouble("P_PRJ_PUN_PREZZO_UNIT_NOMINAL"));
				bean.setPrjCls(rs.getString("PRJ_CLS"));
				bean.setNomeProgetto(rs.getString("NOME_PROGETTO"));
				bean.setStaffMedio(rs.getInt("STAFF_MEDIO"));
				bean.setTipoProgetto(rs.getString("TIPO_PROGETTO"));
				bean.setVersionePrj(rs.getString("VERSIONE_PRJ"));
				bean.setCicloDiVita(rs.getString("CICLO_DI_VITA"));
				// Uso il campo Annullato in quanto il campo applicazione non
				// esiste
				bean.setAnnullato(rs.getString("APPLICAZIONE"));
				bean.setIdAsm(rs.getShort("ID_ASM"));
				bean.setDmalmAsmFk(getAsmByID(rs.getShort("ID_ASM"),
						dataEsecuzione));
				
				project.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return project;
	}

	public static Integer getAsmByID(short id, Timestamp dt_modifica)
			throws DAOException {

		QDmalmAsm asm = QDmalmAsm.dmalmAsm;
		ConnectionManager cm = null;
		Connection connection = null;
		int asmFk = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			asmFk = query.from(QDmalmAsm.dmalmAsm).where(asm.idAsm.eq(id))
					.where(asm.dataFineValidita.goe(dt_modifica))
					.where(asm.dataInizioValidita.loe(dt_modifica))
					.list(asm.dmalmAsmPk).get(0);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return asmFk;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmProgettoSfera project) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.idProgetto.eq(project.getIdProgetto()))
					.where(prog.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(prog.dataFineValidita,
							DateUtils.addDaysToTimestamp(DataEsecuzione
									.getInstance().getDataEsecuzione(), -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDmalmProgetto(DmalmProgettoSfera project)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.idAsm.eq(project.getIdAsm()))
					.where(prog.idProgetto.eq(project.getIdProgetto()))
					.where(prog.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(prog.dataFineEffettiva, project.getDataFineEffettiva())
					.set(prog.dataAvvio, project.getDataAvvio())
					.set(prog.idProgetto, project.getIdProgetto())
					.set(prog.impegnoEffettivo, project.getImpegnoEffettivo())
					.set(prog.includeInBenchmarkingDb,
							project.getIncludeInBenchmarkingDb())
					.set(prog.notePrj, project.getNotePrj())
					.set(prog.pPrjADisposizione01,
							project.getpPrjADisposizione01())
					.set(prog.pPrjADisposizione02,
							project.getpPrjADisposizione02())
					.set(prog.pPrjAmbTecnPortali,
							project.getpPrjAmbTecnPortali())
					.set(prog.pPrjAuditIndexVerify,
							project.getpPrjAuditIndexVerify())
					.set(prog.pPrjAuditMonitore, project.getpPrjAuditMonitore())
					.set(prog.pPrjCodRdi, project.getpPrjCodRdi())
//					.set(prog.pPrjDtConsegnaMppForn,
//							project.getpPrjDtConsegnaMppForn())
					.set(prog.pPrjFccFattCorrezTotal,
							project.getpPrjFccFattCorrezTotal())
					.set(prog.pPrjFlAmbTecTransBatRep,
							project.getpPrjFlAmbTecTransBatRep())
					.set(prog.pPrjFlAmbTecnPiatEnterpr,
							project.getpPrjFlAmbTecnPiatEnterpr())
					.set(prog.pPrjFlAmbitoTecnFuturo01,
							project.getpPrjFlAmbitoTecnFuturo01())
					.set(prog.pPrjFlAmbitoTecnFuturo02,
							project.getpPrjFlAmbitoTecnFuturo02())
					.set(prog.pPrjFlApplLgFpEdma,
							project.getpPrjFlApplLgFpEdma())
					.set(prog.pPrjFlApplLgFpWeb, project.getpPrjFlApplLgFpWeb())
					.set(prog.pPrjFlApplicLgFpDwh,
							project.getpPrjFlApplicLgFpDwh())
					.set(prog.pPrjFlApplicLgFpFuturo01,
							project.getpPrjFlApplicLgFpFuturo01())
					.set(prog.pPrjFlApplicLgFpFuturo02,
							project.getpPrjFlApplicLgFpFuturo02())
					.set(prog.pPrjFlagAmbTecnGis,
							project.getpPrjFlagAmbTecnGis())
					.set(prog.pPrjFlagApplLgFpGis,
							project.getpPrjFlagApplLgFpGis())
					.set(prog.pPrjFlagApplLgFpMware,
							project.getpPrjFlagApplLgFpMware())
					.set(prog.pPrjImportoAConsuntivo,
							project.getpPrjImportoAConsuntivo())
					.set(prog.pPrjImportoRdiAPreventivo,
							project.getpPrjImportoRdiAPreventivo())
					.set(prog.pPrjIndexAlmValidProgAsm,
							project.getpPrjIndexAlmValidProgAsm())
					.set(prog.pPrjMfcAConsuntivo,
							project.getpPrjMfcAConsuntivo())
					.set(prog.pPrjMfcAPreventivo,
							project.getpPrjMfcAPreventivo())
					.set(prog.pPrjMpPercentCicloDiVita,
							project.getpPrjMpPercentCicloDiVita())
					.set(prog.pPrjPunPrezzoUnitNominal,
							project.getpPrjPunPrezzoUnitNominal())
					.set(prog.prjCls, project.getPrjCls())
					.set(prog.versionePrj, project.getVersionePrj())
					.set(prog.dataModifica, project.getDataModifica())
					.set(prog.annullato, project.getAnnullato()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertProgettoSfera(DmalmProgettoSfera project,
			Timestamp dataEsecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			if (project.getIdProgetto() != 0) {

				new SQLInsertClause(connection, dialect, prog)
						.columns(prog.dataFineEffettiva, prog.dataAvvio,
								prog.idProgetto, prog.impegnoEffettivo,
								prog.includeInBenchmarkingDb, prog.notePrj,
								prog.pPrjADisposizione01,
								prog.pPrjADisposizione02,
								prog.pPrjAmbTecnPortali,
								prog.pPrjAuditIndexVerify,
								prog.pPrjAuditMonitore, prog.pPrjCodRdi,
//								prog.pPrjDtConsegnaMppForn,
								prog.pPrjFccFattCorrezTotal,
								prog.pPrjFlAmbTecTransBatRep,
								prog.pPrjFlAmbTecnPiatEnterpr,
								prog.pPrjFlAmbitoTecnFuturo01,
								prog.pPrjFlAmbitoTecnFuturo02,
								prog.pPrjFlApplLgFpEdma,
								prog.pPrjFlApplLgFpWeb,
								prog.pPrjFlApplicLgFpDwh,
								prog.pPrjFlApplicLgFpFuturo01,
								prog.pPrjFlApplicLgFpFuturo02,
								prog.pPrjFlagAmbTecnGis,
								prog.pPrjFlagApplLgFpGis,
								prog.pPrjFlagApplLgFpMware,
								prog.pPrjImportoAConsuntivo,
								prog.pPrjImportoRdiAPreventivo,
								prog.pPrjIndexAlmValidProgAsm,
								prog.pPrjMfcAConsuntivo,
								prog.pPrjMfcAPreventivo,
								prog.pPrjMpPercentCicloDiVita,
								prog.pPrjPunPrezzoUnitNominal, prog.prjCls,
								prog.versionePrj, prog.dataModifica,
								prog.dmalmProgettoSferaPk,
								prog.dmalmStgMisuraPk,
								prog.pPrjFornitoreSviluppoMev,
								prog.nomeProgetto, prog.tipoProgetto,
								prog.costo, prog.durataEffettiva,
								prog.staffMedio, prog.dataCaricamento,
								prog.dataFineValidita, prog.dataInizioValidita,
								prog.pPrjFornitoreMpp, prog.cicloDiVita,
								prog.idAsm, prog.dmalmAsmFk, prog.annullato)
						.values(project.getDataFineEffettiva(),
								project.getDataAvvio(),
								project.getIdProgetto(),
								project.getImpegnoEffettivo(),
								project.getIncludeInBenchmarkingDb(),
								project.getNotePrj(),
								project.getpPrjADisposizione01(),
								project.getpPrjADisposizione02(),
								project.getpPrjAmbTecnPortali(),
								project.getpPrjAuditIndexVerify(),
								project.getpPrjAuditMonitore(),
								project.getpPrjCodRdi(),
//								project.getpPrjDtConsegnaMppForn(),
								project.getpPrjFccFattCorrezTotal(),
								project.getpPrjFlAmbTecTransBatRep(),
								project.getpPrjFlAmbTecnPiatEnterpr(),
								project.getpPrjFlAmbitoTecnFuturo01(),
								project.getpPrjFlAmbitoTecnFuturo02(),
								project.getpPrjFlApplLgFpEdma(),
								project.getpPrjFlApplLgFpWeb(),
								project.getpPrjFlApplicLgFpDwh(),
								project.getpPrjFlApplicLgFpFuturo01(),
								project.getpPrjFlApplicLgFpFuturo02(),
								project.getpPrjFlagAmbTecnGis(),
								project.getpPrjFlagApplLgFpGis(),
								project.getpPrjFlagApplLgFpMware(),
								project.getpPrjImportoAConsuntivo(),
								project.getpPrjImportoRdiAPreventivo(),
								project.getpPrjIndexAlmValidProgAsm(),
								project.getpPrjMfcAConsuntivo(),
								project.getpPrjMfcAPreventivo(),
								project.getpPrjMpPercentCicloDiVita(),
								project.getpPrjPunPrezzoUnitNominal(),
								project.getPrjCls(),
								project.getVersionePrj(),
								project.getDataModifica(),
								StringTemplate
										.create("DM_ALM_PROGETTO_SFERA_SEQ.nextval"),
								project.getDmalmStgMisuraPk(),
								project.getpPrjFornitoreSviluppoMev(),
								project.getNomeProgetto(),
								project.getTipoProgetto(), project.getCosto(),
								project.getDurataEffettiva(),
								project.getStaffMedio(),
								project.getDataCaricamento(),
								DateUtils.setDtFineValidita9999(),
								project.getDataCaricamento(),
								project.getpPrjFornitoreMpp(),
								project.getCicloDiVita(), project.getIdAsm(),
								project.getDmalmAsmFk(), project.getAnnullato())
						.execute();

				connection.commit();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getProgetto(DmalmProgettoSfera progetto)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> projects = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(prog)
					.where(prog.idAsm.eq(progetto.getIdAsm()))
					.where(prog.idProgetto.eq(progetto.getIdProgetto()))
					.where(prog.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999())).list(prog.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return projects;
	}

	public static List<Tuple> checkLinkProgeSferaWi(Timestamp dataEsecuzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(prog)
					.where(prog.dmalmProgettoSferaPk.gt(new Integer(0)))
					.where(prog.tipoProgetto.equalsIgnoreCase(
							DmAlmConstants.SVI).or(
							prog.tipoProgetto
									.equalsIgnoreCase(DmAlmConstants.MEV)))
					.where(prog.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(prog.nomeProgetto.notLike("%NOWI%"))
					.orderBy(prog.nomeProgetto.asc(),
							prog.dmalmProgettoSferaPk.asc()).list(prog.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return projects;
	}

	
	
	// 16-10-2015 LEGAME DIRETTO WI PROGETTO SFERA NON PIU RICHIESTO
	// public static void linkProgeSferaWi(Timestamp dataEsecuzione)
	// throws DAOException {
	//
	// ConnectionManager cm = null;
	// Connection connection = null;
	// String nomeprog = null;
	// String[] multiWI = null;
	// String nomeWI = null;
	// Integer preDmalmProgSvilSPk = 0;
	// Integer preDmalmManutenzionePk = 0;
	// List<Tuple> manWiTupla = new ArrayList<Tuple>();
	// List<Tuple> sviWiTupla = new ArrayList<Tuple>();
	//
	// long updated_rows = 0;
	//
	// String WI_modified = null;
	//
	// List<Tuple> projects = new ArrayList<Tuple>();
	// try {
	//
	// cm = ConnectionManager.getInstance();
	// connection = cm.getConnectionOracle();
	//
	// SQLQuery query = new SQLQuery(connection, dialect);
	//
	// projects = query
	// .from(prog)
	// .where(prog.dmalmProgettoSferaPk.gt(new Integer(0)))
	// .where(prog.tipoProgetto.equalsIgnoreCase(
	// DmAlmConstants.SVI).or(
	// prog.tipoProgetto
	// .equalsIgnoreCase(DmAlmConstants.MEV)))
	// .where(prog.dataFineValidita.eq(DateUtils
	// .setDtFineValidita9999()))
	// .orderBy(prog.nomeProgetto.asc(),
	// prog.dmalmProgettoSferaPk.asc()).list(prog.all());
	// for (Tuple progetto : projects) {
	// WI_modified = progetto.get(prog.nomeProgetto).trim();
	// if (progetto.get(prog.nomeProgetto).contains("#")) {
	// WI_modified = WI_modified.substring(0,
	// progetto.get(prog.nomeProgetto).indexOf("#"));
	// }
	//
	// logger.debug("=================================================================================================================");
	// logger.debug("linkProgeSferaWi - WI_modified: " + WI_modified);
	//
	// preDmalmProgSvilSPk = 0;
	// preDmalmManutenzionePk = 0;
	//
	// try {
	// multiWI = WI_modified.split("\\*\\*");
	// nomeWI = multiWI[0].split("-")[0];
	// multiWI[0] = multiWI[0].split("-")[1];
	// } catch (Exception e) {
	// logger.error("exception: " + e);
	//
	// // gestione errore split per nomenclatura del nome errata
	// multiWI = new String[0];
	//
	// logger.info("Errore gestito: " + WI_modified
	// + " nome errato impossibile cercare link Sfera/Wi");
	// ErroriCaricamentoDAO.insert(
	// DmAlmConstants.FONTE_MISURA,
	// DmAlmConstants.TARGET_PROGETTO_SFERA,
	// "PROGETTO SFERA PK: "
	// + progetto.get(prog.dmalmProgettoSferaPk)
	// .toString()
	// + " PROGETTO SFERA NAME: " + WI_modified,
	// DmAlmConstants.WRONG_LINK_PROGSF_WI,
	// DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, DateUtils
	// .getDataEsecuzione());
	// }
	//
	// for (String wi : multiWI) {
	//
	// logger.debug("               ");
	// logger.debug("linkProgeSferaWi - WI         : " + nomeWI
	// + "-" + wi);
	//
	// updated_rows = 0;
	//
	// wi.trim();
	//
	// nomeprog = nomeWI + "-" + wi;
	//
	// logger.debug("linkProgeSferaWi - nomeprog   : " + nomeprog);
	// logger.debug("               ");
	//
	// // cerco la pk del workintem di sviluppo
	// sviWiTupla = DmAlmProgettoSferaDAO.getPkWorkitemSviluppo(
	// progetto.get(prog.dmalmAsmFk), nomeprog);
	//
	// logger.debug("linkProgeSferaWi - sviWiTupla.size: "
	// + sviWiTupla.size());
	//
	// for (Tuple sviWiRow : sviWiTupla) {
	// if (sviWiRow != null) {
	//
	// logger.debug("        ");
	// logger.debug("linkProgeSferaWi - preDmalmProgSvilSPk         (svi)        : "
	// + preDmalmProgSvilSPk);
	// logger.debug("linkProgeSferaWi - progSv.dmalmProgSvilSPk     (svi)        : "
	// + sviWiRow.get(progSv.dmalmProgSvilSPk));
	//
	// if (preDmalmProgSvilSPk.intValue() != sviWiRow.get(
	// progSv.dmalmProgSvilSPk).intValue()) {
	// preDmalmProgSvilSPk = sviWiRow
	// .get(progSv.dmalmProgSvilSPk);
	//
	// logger.debug("linkProgeSferaWi - WIprogSv ProgettoSferaFk    (svi)        : "
	// + sviWiRow
	// .get(progSv.dmalmProgettoSferaFk));
	// logger.debug("linkProgeSferaWi - prog.dmalmProgettoSferaPk   (svi)        : "
	// + progetto
	// .get(prog.dmalmProgettoSferaPk));
	//
	// if (sviWiRow.get(progSv.dmalmProgettoSferaFk) == null
	// || sviWiRow.get(
	// progSv.dmalmProgettoSferaFk)
	// .equals(new Integer(0))) {
	//
	// logger.debug("linkProgeSferaWi - dmalmProgSvilSPk            (svi) UPDATE : "
	// + sviWiRow
	// .get(progSv.dmalmProgSvilSPk));
	//
	// updated_rows += new SQLUpdateClause(
	// connection, dialect, progSv)
	// .where(progSv.dmalmProgSvilSPk.eq(sviWiRow
	// .get(progSv.dmalmProgSvilSPk)))
	// .set(progSv.dmalmProgettoSferaFk,
	// progetto.get(prog.dmalmProgettoSferaPk))
	// .execute();
	// } else {
	// if (!sviWiRow
	// .get(progSv.dmalmProgettoSferaFk)
	// .equals(progetto
	// .get(prog.dmalmProgettoSferaPk))) {
	//
	// logger.debug("linkProgeSferaWi - dmalmProgSvilSPk            (svi) INS/STO: "
	// + sviWiRow
	// .get(progSv.dmalmProgSvilSPk));
	//
	// /*
	// * // STORICIZZO updated_rows += new
	// * SQLUpdateClause( connection, dialect,
	// * progSv)
	// * .where(progSv.dmalmProgSvilSPk
	// * .eq(sviWiRow
	// * .get(progSv.dmalmProgSvilSPk)))
	// * .set(progSv.rankStatoProgSvilS, new
	// * Double(0)).execute();
	// *
	// * DmalmProgettoSviluppoSvil
	// * progettoSviluppo = new
	// * DmalmProgettoSviluppoSvil();
	// *
	// * progettoSviluppo.setCdProgSvilS(sviWiRow
	// * .get(progSv.cdProgSvilS));
	// * progettoSviluppo.setCodice(sviWiRow
	// * .get(progSv.codice));
	// * progettoSviluppo
	// * .setDataChiusuraProgSvilS(sviWiRow
	// * .get(progSv.dataChiusuraProgSvilS));
	// * progettoSviluppo
	// * .setDataDisponibilitaEffettiva
	// * (sviWiRow
	// * .get(progSv.dataDisponibilitaEffettiva
	// * )); progettoSviluppo
	// * .setDataDisponibilitaPianificata
	// * (sviWiRow
	// * .get(progSv.dataDisponibilitaPianificata
	// * ));
	// * progettoSviluppo.setDataInizio(sviWiRow
	// * .get(progSv.dataInizio));
	// * progettoSviluppo
	// * .setDataInizioEff(sviWiRow
	// * .get(progSv.dataInizioEff));
	// * progettoSviluppo
	// * .setDescrizioneProgSvilS(sviWiRow
	// * .get(progSv.descrizioneProgSvilS));
	// * progettoSviluppo
	// * .setDmalmAreaTematicaFk05(sviWiRow
	// * .get(progSv.dmalmAreaTematicaFk05));
	// *
	// * progettoSviluppo
	// * .setDmalmProjectFk02(sviWiRow
	// * .get(progSv.dmalmProjectFk02));
	// * progettoSviluppo
	// * .setDmalmStatoWorkitemFk03(sviWiRow
	// * .get(progSv.dmalmStatoWorkitemFk03));
	// * progettoSviluppo
	// * .setDmalmStrutturaOrgFk01(sviWiRow
	// * .get(progSv.dmalmStrutturaOrgFk01));
	// * progettoSviluppo
	// * .setDmalmTempoFk04(sviWiRow
	// * .get(progSv.dmalmTempoFk04));
	// * progettoSviluppo
	// * .setDsAutoreProgSvilS(sviWiRow
	// * .get(progSv.dsAutoreProgSvilS));
	// * progettoSviluppo
	// * .setDtCambioStatoProgSvilS(sviWiRow
	// * .get(progSv.dtCambioStatoProgSvilS));
	// * progettoSviluppo
	// * .setDtCaricamentoProgSvilS(sviWiRow
	// * .get(progSv.dtCaricamentoProgSvilS));
	// * progettoSviluppo
	// * .setDtCreazioneProgSvilS(sviWiRow
	// * .get(progSv.dtCreazioneProgSvilS));
	// * progettoSviluppo
	// * .setDtModificaProgSvilS(sviWiRow
	// * .get(progSv.dtModificaProgSvilS));
	// * progettoSviluppo
	// * .setDtRisoluzioneProgSvilS(sviWiRow
	// * .get(progSv.dtRisoluzioneProgSvilS));
	// * progettoSviluppo
	// * .setDtScadenzaProgSvilS(sviWiRow
	// * .get(progSv.dtScadenzaProgSvilS));
	// * progettoSviluppo
	// * .setDurataEffettivaProgSvilS(sviWiRow
	// * .
	// * get(progSv.durataEffettivaProgSvilS))
	// * ;
	// * progettoSviluppo.setFornitura(sviWiRow
	// * .get(progSv.fornitura));
	// * progettoSviluppo
	// * .setIdAutoreProgSvilS(sviWiRow
	// * .get(progSv.idAutoreProgSvilS));
	// * progettoSviluppo
	// * .setIdRepository(sviWiRow
	// * .get(progSv.idRepository));
	// * progettoSviluppo
	// * .setMotivoRisoluzioneProgSvilS
	// * (sviWiRow
	// * .get(progSv.motivoRisoluzioneProgSvilS
	// * ));
	// * progettoSviluppo.setNumeroLinea(sviWiRow
	// * .get(progSv.numeroLinea));
	// * progettoSviluppo
	// * .setNumeroTestata(sviWiRow
	// * .get(progSv.numeroTestata));
	// * progettoSviluppo
	// * .setTitoloProgSvilS(sviWiRow
	// * .get(progSv.titoloProgSvilS));
	// * progettoSviluppo.setStgPk(sviWiRow
	// * .get(progSv.stgPk));
	// * progettoSviluppo.
	// * setDmalmUserFk06(sviWiRow
	// * .get(progSv.dmalmUserFk06));
	// * progettoSviluppo.setUri(sviWiRow
	// * .get(progSv.uri)); progettoSviluppo
	// * .setDmalmProgettoSferaFk(progetto
	// * .get(prog.dmalmProgettoSferaPk));
	// * progettoSviluppo.setDmalmProgSvilSPk(
	// * DmAlmProgettoSferaDAO
	// * .getWorkitemPk());
	// *
	// * // inserisco un nuovo record
	// * ProgettoSviluppoSviluppoDAO
	// * .insertProgettoSviluppoSvilUpdate(
	// * dataEsecuzione, progettoSviluppo);
	// */
	// }
	// }
	// }
	// }
	// }
	//
	// // cerco la riga del workintem di manutenzione
	//
	// manWiTupla = DmAlmProgettoSferaDAO
	// .getPkWorkitemManutenzione(
	// progetto.get(prog.dmalmAsmFk), nomeprog);
	//
	// logger.debug("linkProgeSferaWi - manWiTupla.size: "
	// + manWiTupla.size());
	//
	// for (Tuple manWiRow : manWiTupla) {
	// if (manWiRow != null) {
	//
	// logger.debug("        ");
	// logger.debug("linkProgeSferaWi - preDmalmManutenzionePk      (man)        : "
	// + preDmalmManutenzionePk);
	// logger.debug("linkProgeSferaWi - man.dmalmManutenzionePk     (man)        : "
	// + manWiRow.get(man.dmalmManutenzionePk));
	//
	// if (preDmalmManutenzionePk.intValue() != manWiRow
	// .get(man.dmalmManutenzionePk).intValue()) {
	//
	// preDmalmManutenzionePk = manWiRow
	// .get(man.dmalmManutenzionePk);
	//
	// logger.debug("linkProgeSferaWi - WIman ProgettoSferaFk       (man)        : "
	// + manWiRow
	// .get(man.dmalmProgettoSferaFk));
	// logger.debug("linkProgeSferaWi - prog.dmalmProgettoSferaPk   (man)        : "
	// + progetto
	// .get(prog.dmalmProgettoSferaPk));
	//
	// if (manWiRow.get(man.dmalmProgettoSferaFk) == null
	// || manWiRow.get(
	// man.dmalmProgettoSferaFk)
	// .equals(new Integer(0))) {
	//
	// logger.debug("linkProgeSferaWi - dmalmManutenzionePk         (man) UPDATE : "
	// + manWiRow
	// .get(man.dmalmManutenzionePk));
	//
	// updated_rows += new SQLUpdateClause(
	// connection, dialect, man)
	// .where(man.dmalmManutenzionePk.eq(manWiRow
	// .get(man.dmalmManutenzionePk)))
	// .set(man.dmalmProgettoSferaFk,
	// progetto.get(prog.dmalmProgettoSferaPk))
	// .execute();
	// } else {
	// if (!manWiRow
	// .get(man.dmalmProgettoSferaFk)
	// .equals(progetto
	// .get(prog.dmalmProgettoSferaPk))) {
	//
	// logger.debug("linkProgeSferaWi - dmalmManutenzionePk         (man) INS/STO: "
	// + manWiRow
	// .get(man.dmalmManutenzionePk));
	// /*
	// * // STORICIZZO updated_rows += new
	// * SQLUpdateClause( connection, dialect,
	// * man) .where(man.dmalmManutenzionePk
	// * .eq
	// * (manWiRow.get(man.dmalmManutenzionePk
	// * ))) .set(man.rankStatoManutenzione,
	// * new Double(0)).execute();
	// *
	// * DmalmManutenzione manutenzione = new
	// * DmalmManutenzione();
	// *
	// * manutenzione
	// * .setAssigneesManutenzione(manWiRow
	// * .get(man.assigneesManutenzione));
	// * manutenzione
	// * .setCdManutenzione(manWiRow
	// * .get(man.cdManutenzione));
	// * manutenzione.setCodice(manWiRow
	// * .get(man.codice));
	// * manutenzione.setDataDispEff(manWiRow
	// * .get(man.dataDispEff));
	// * manutenzione.setDataDisponibilita
	// * (manWiRow
	// * .get(man.dataDisponibilita));
	// * manutenzione.setDataInizio(manWiRow
	// * .get(man.dataInizio));
	// * manutenzione.setDataInizioEff
	// * (manWiRow .get(man.dataInizioEff));
	// * manutenzione
	// * .setDataRilascioInEs(manWiRow
	// * .get(man.dataRilascioInEs));
	// * manutenzione
	// * .setDescrizioneManutenzione(manWiRow
	// * .get(man.descrizioneManutenzione));
	// * manutenzione
	// * .setDmalmAreaTematicaFk05(manWiRow
	// * .get(man.dmalmAreaTematicaFk05));
	// * manutenzione
	// * .setDmalmProjectFk02(manWiRow
	// * .get(man.dmalmProjectFk02));
	// * manutenzione
	// * .setDmalmStatoWorkitemFk03(manWiRow
	// * .get(man.dmalmStatoWorkitemFk03));
	// * manutenzione
	// * .setDmalmStrutturaOrgFk01(manWiRow
	// * .get(man.dmalmStrutturaOrgFk01));
	// * manutenzione
	// * .setDmalmTempoFk04(manWiRow
	// * .get(man.dmalmTempoFk04));
	// * manutenzione
	// * .setDsAutoreManutenzione(manWiRow
	// * .get(man.dsAutoreManutenzione));
	// * manutenzione
	// * .setDtCambioStatoManutenzione
	// * (manWiRow
	// * .get(man.dtCambioStatoManutenzione));
	// * manutenzione
	// * .setDtCaricamentoManutenzione
	// * (manWiRow
	// * .get(man.dtCaricamentoManutenzione));
	// * manutenzione
	// * .setDtCreazioneManutenzione(manWiRow
	// * .get(man.dtCreazioneManutenzione));
	// * manutenzione
	// * .setDtModificaManutenzione(manWiRow
	// * .get(man.dtModificaManutenzione));
	// * manutenzione
	// * .setDtRisoluzioneManutenzione
	// * (manWiRow
	// * .get(man.dtRisoluzioneManutenzione));
	// * manutenzione
	// * .setDtScadenzaManutenzione(manWiRow
	// * .get(man.dtScadenzaManutenzione));
	// * manutenzione
	// * .setDtModificaManutenzione(manWiRow
	// * .get(man.dtModificaManutenzione));
	// * manutenzione.setFornitura(manWiRow
	// * .get(man.fornitura)); manutenzione
	// * .setIdAutoreManutenzione(manWiRow
	// * .get(man.idAutoreManutenzione));
	// * manutenzione.setIdRepository(manWiRow
	// * .get(man.idRepository)); manutenzione
	// * .
	// * setMotivoRisoluzioneManutenzion(manWiRow
	// * .
	// * get(man.motivoRisoluzioneManutenzion)
	// * );
	// * manutenzione.setNumeroLinea(manWiRow
	// * .get(man.numeroLinea));
	// * manutenzione.setNumeroTestata
	// * (manWiRow .get(man.numeroTestata));
	// * manutenzione
	// * .setPriorityManutenzione(manWiRow
	// * .get(man.priorityManutenzione));
	// * manutenzione
	// * .setSeverityManutenzione(manWiRow
	// * .get(man.severityManutenzione));
	// * manutenzione
	// * .setTempoTotaleRisoluzione(manWiRow
	// * .get(man.tempoTotaleRisoluzione));
	// * manutenzione
	// * .setTitoloManutenzione(manWiRow
	// * .get(man.titoloManutenzione));
	// * manutenzione.setStgPk(manWiRow
	// * .get(man.stgPk));
	// * manutenzione.setDmalmUserFk06
	// * (manWiRow .get(man.dmalmUserFk06));
	// * manutenzione
	// * .setUri(manWiRow.get(man.uri));
	// * manutenzione
	// * .setDmalmProgettoSferaFk(progetto
	// * .get(prog.dmalmProgettoSferaPk));
	// * manutenzione.setDmalmManutenzionePk(
	// * DmAlmProgettoSferaDAO
	// * .getWorkitemPk());
	// *
	// * // inserisco un nuovo record
	// * ManutenzioneDAO
	// * .insertManutenzioneUpdate(
	// * dataEsecuzione, manutenzione);
	// */
	// }
	// }
	// }
	// }
	// }
	//
	// logger.debug("   ");
	// logger.debug("linkProgeSferaWi - " + nomeWI + "-" + wi
	// + " - updated_rows: " + updated_rows);
	//
	// if (updated_rows == 0) {
	// ErroriCaricamentoDAO.insert(
	// DmAlmConstants.FONTE_MISURA,
	// DmAlmConstants.TARGET_PROGETTO_SFERA,
	// "PROGETTO SFERA PK: "
	// + progetto.get(
	// prog.dmalmProgettoSferaPk)
	// .toString()
	// + " PROGETTO SFERA NAME: " + nomeWI
	// + "-" + wi,
	// DmAlmConstants.WRONG_LINK_PROGSF_WI,
	// DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
	// DateUtils.getDataEsecuzione());
	// }
	// }
	// }
	//
	// connection.commit();
	// } catch (Exception e) {
	// logger.error(e.getMessage());
	// ErrorManager.getInstance().exceptionOccurred(true, e);
	//
	// } finally {
	// if (cm != null)
	// cm.closeConnection(connection);
	// }
	//
	// return;
	// }

	public static List<Tuple> getProgettoByName(DmalmProgettoSfera prj,
			String applicazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetto = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetto = query
					.from(prog)
					.join(asm)
					.on(asm.dmalmAsmPk.eq(prog.dmalmAsmFk))
					.where(prog.nomeProgetto.eq(prj.getNomeProgetto()))
					.where(prog.annullato
							.eq(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE))
					.where(prog.dataFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(asm.applicazione.eq(applicazione)).list(prog.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return progetto;
	}

	public static List<Tuple> getPkWorkitemSviluppo(Integer asmPk,
			String nomeProgetto) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> listaWi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listaWi = query
					.from(asmProjectProdotto)
					.join(progSv).on(progSv.dmalmProjectFk02.eq(asmProjectProdotto.dmalmProjectPk))
					.join(statoWorkitem).on(statoWorkitem.dmalmStatoWorkitemPrimaryKey.eq(progSv.dmalmStatoWorkitemFk03))
					.where(asmProjectProdotto.dmalmAsmPk.eq(asmPk))
					.where(progSv.rankStatoProgSvilS.eq(new Double(1)))
					.where(progSv.cdProgSvilS.eq(nomeProgetto))
					.list(progSv.cdProgSvilS, progSv.dtRisoluzioneProgSvilS,
							statoWorkitem.cdStato);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return listaWi;
	}

	public static List<Tuple> getPkWorkitemManutenzione(Integer asmPk,
			String nomeProgetto) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> listaWi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);
			
			listaWi = query
					.from(asmProjectProdotto)
					.join(man).on(man.dmalmProjectFk02.eq(asmProjectProdotto.dmalmProjectPk))
					.join(statoWorkitem).on(statoWorkitem.dmalmStatoWorkitemPrimaryKey.eq(man.dmalmStatoWorkitemFk03))
					.where(asmProjectProdotto.dmalmAsmPk.eq(asmPk))
					.where(man.rankStatoManutenzione.eq(new Double(1)))
					.where(man.cdManutenzione.eq(nomeProgetto))
					.list(man.cdManutenzione, man.dtRisoluzioneManutenzione,
							statoWorkitem.cdStato);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return listaWi;
	}

	public static Integer getWorkitemPk() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer resWorkitemPk = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.WORKITEM_PK_NEXTVAL);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();
			rs.next();
			resWorkitemPk = rs.getInt("WORKITEM_PK");
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return resWorkitemPk;
	}
}
