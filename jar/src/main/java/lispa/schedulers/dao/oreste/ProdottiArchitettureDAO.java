package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class ProdottiArchitettureDAO {
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static Logger logger = Logger
			.getLogger(ProdottiArchitettureDAO.class);
	private static QDmalmElProdottiArchitetture dmalmElProdArc = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmAsmProdottiArchitetture dmalmAsmProdArch = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;
	
	public static long fillProdottiArchitetture() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		long n_righe_inserite = 0;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QStgProdottiArchitetture qStgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;
			OresteDmAlmProdottiArchitetture viewProdottiArchitetture = OresteDmAlmProdottiArchitetture.dmAlmProdottiArchitetture;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> prodotti = query.from(viewProdottiArchitetture).list(
					viewProdottiArchitetture.all());

			for (Tuple row : prodotti) {
				n_righe_inserite += new SQLInsertClause(connection, dialect,
						qStgProdottiArchitetture)
						.columns(
								qStgProdottiArchitetture.idEdmaProdotto,
								qStgProdottiArchitetture.idProdotto,
								qStgProdottiArchitetture.tipoOggetto,
								qStgProdottiArchitetture.siglaProdotto,
								qStgProdottiArchitetture.nomeProdotto,
								qStgProdottiArchitetture.descrizioneProdotto,
								qStgProdottiArchitetture.edmaAreaProdotto,
								qStgProdottiArchitetture.edmaRespProdotto,
								qStgProdottiArchitetture.prodottoAnnullato,
								qStgProdottiArchitetture.dfvProdottoAnnullato,
								qStgProdottiArchitetture.clasfAmbitoManutenzione,
								qStgProdottiArchitetture.clasfAreaTematica,
								qStgProdottiArchitetture.clasfBaseDatiEtl,
								qStgProdottiArchitetture.clasfBaseDatiLettura,
								qStgProdottiArchitetture.clasfBaseDatiScrittura,
								qStgProdottiArchitetture.clasfFornRisEstGara,
								qStgProdottiArchitetture.clasfCategoria,
								qStgProdottiArchitetture.dataCaricamento,
								qStgProdottiArchitetture.dmalmProdottiArchitetturePk)
						.values(row
								.get(viewProdottiArchitetture.idEdmaProdotto),
								row.get(viewProdottiArchitetture.idProdotto),
								row.get(viewProdottiArchitetture.tipoOggetto),
								row.get(viewProdottiArchitetture.siglaProdotto) != null ? row
										.get(viewProdottiArchitetture.siglaProdotto)
										.trim()
										: null,
								row.get(viewProdottiArchitetture.nomeProdotto),
								row.get(viewProdottiArchitetture.descrizioneProdotto),
								row.get(viewProdottiArchitetture.edmaAreaProdotto),
								StringUtils.getMaskedValue(row.get(viewProdottiArchitetture.edmaRespProdotto)),
								row.get(viewProdottiArchitetture.prodottoAnnullato),
								row.get(viewProdottiArchitetture.dfvProdottoAnnullato),
								StringUtils.getMaskedValue(row.get(viewProdottiArchitetture.clasfAmbitoManutenzione)),
								row.get(viewProdottiArchitetture.clasfAreaTematica),
								row.get(viewProdottiArchitetture.clasfBaseDatiEtl),
								row.get(viewProdottiArchitetture.clasfBaseDatiLettura),
								row.get(viewProdottiArchitetture.clasfBaseDatiScrittura),
								row.get(viewProdottiArchitetture.clasfFornRisEstGara),
								row.get(viewProdottiArchitetture.clasfCategoria),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("STG_PROD_ARCHITETTURE_SEQ.nextval")

						).execute();
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			n_righe_inserite = 0;
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return n_righe_inserite;

	}

	public static List<Tuple> getAllProdottiArchitetture(
			QStgProdottiArchitetture stgProdotti, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query.from(stgProdotti)
					.where(stgProdotti.dataCaricamento.eq(dataEsecuzione))
					.list(stgProdotti.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getDuplicateSiglaProdotto(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture stgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			prodotti =

			query.from(stgProdottiArchitetture)
					.where(stgProdottiArchitetture.siglaProdotto
							.in(new SQLSubQuery()
									.from(stgProdottiArchitetture)
									.where(stgProdottiArchitetture.dataCaricamento
											.eq(dataEsecuzione))
									.groupBy(
											stgProdottiArchitetture.siglaProdotto)
									.having(stgProdottiArchitetture.siglaProdotto
											.count().gt(1))
									.list(stgProdottiArchitetture.siglaProdotto)))
					.list(stgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getDuplicateNomeProdotto(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture stgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			/*
			 * select * from DMALM_STG_PROD_ARCHITETTURE where
			 * DMALM_STG_PROD_ARCHITETTURE.NOME_PRODOTTO in ( select
			 * a.NOME_PRODOTTO from DMALM_STG_PROD_ARCHITETTURE a group by
			 * a.NOME_PRODOTTO having count(*) > 1 )
			 */

			prodotti =

			query.from(stgProdottiArchitetture)
					.where(stgProdottiArchitetture.nomeProdotto
							.in(new SQLSubQuery()
									.from(stgProdottiArchitetture)
									.where(stgProdottiArchitetture.dataCaricamento
											.eq(dataEsecuzione))
									.groupBy(
											stgProdottiArchitetture.nomeProdotto)
									.having(stgProdottiArchitetture.nomeProdotto
											.count().gt(1))
									.list(stgProdottiArchitetture.nomeProdotto)))
					.list(stgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getSiglaProdottiNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		// select * from DMALM_STG_PROD_ARCHITETTURE where SIGLA_PRODOTTO is
		// null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			prodotti =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgProdottiArchitetture.siglaProdotto.isNull())
					.list(qstgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getNomiProdottoNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		// select * from DMALM_STG_PROD_ARCHITETTURE where NOME_PRODOTTO is null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			prodotti =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgProdottiArchitetture.nomeProdotto.isNull())
					.list(qstgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getProdottiAnnullati(
			QStgProdottiArchitetture qstgProdottiArchitetture,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodottiAnnullati = new ArrayList<Tuple>();

		// select * from DMALM_STG_PROD_ARCHITETTURE where NOME_PRODOTTO is null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			prodottiAnnullati =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgProdottiArchitetture.nomeProdotto
							.like("%ANNULLATO%"))
					.list(qstgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodottiAnnullati;
	}

	public static List<Tuple> getProdottoBySiglaProdotto(String siglaProdotto,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotto = new ArrayList<Tuple>();
		QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

		// select * from DMALM_STG_PROD_ARCHITETTURE where NOME_PRODOTTO is null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			prodotto =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgProdottiArchitetture.siglaProdotto.toUpperCase()
							.trim().equalsIgnoreCase(siglaProdotto))
					.list(qstgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotto;
	}

	public static List<Tuple> getAreeProdottoNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			prodotti =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgProdottiArchitetture.edmaAreaProdotto.isNull())
					.list(qstgProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<String> getAllEdmaAreaProdotto(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<String> aree = null;
		List<String> areeEdma = new ArrayList<String>();

		// select * from DMALM_STG_PROD_ARCHITETTURE where NOME_PRODOTTO is null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			aree =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(dataEsecuzione))
					.list(qstgProdottiArchitetture.edmaAreaProdotto);

			// es. LIA367 - Area Sistemi Direzionali / SISS
			// estraggo la stringa da posizione 0 al trattino più trim()
			for (String area : aree) {

				if (area.indexOf("-") != -1) {
					areeEdma.add(area.substring(0, area.indexOf("-")).trim());
				} else {
					areeEdma.add(area.trim());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return areeEdma;
	}

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			new SQLDeleteClause(connection, dialect, qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento.lt(
							dataEsecuzioneDeleted).or(
							qstgProdottiArchitetture.dataCaricamento
									.gt(DateUtils.addDaysToTimestamp(
											DataEsecuzione.getInstance()
													.getDataEsecuzione(), -1))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			new SQLDeleteClause(connection, dialect, qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento.eq(date))
					.execute();

		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void recoverProdottiArchitetture() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			new SQLDeleteClause(connection, dialect, qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getAllProductArchToLinkWithAsm()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti Aperti non associati ad una Asm  (escluso il tappo prodottoPk = 0)
			prodotti = query
					.from(dmalmElProdArc)
					.leftJoin(dmalmAsmProdArch)
					.on(dmalmElProdArc.prodottoPk
							.eq(dmalmAsmProdArch.dmalmProdottoPk))
					.where(dmalmElProdArc.prodottoPk.ne(new Integer(0)))
					.where(dmalmElProdArc.dataFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.where(dmalmAsmProdArch.dmalmProdottoPk.isNull())
					.list(dmalmElProdArc.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<String> getAsmByProductPk(Integer productPk)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<String> applicazioni = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti Aperti non associati ad una Asm  (escluso il tappo prodottoPk = 0)
			applicazioni = query.distinct()
					.from(dmalmAsmProdArch)
					.join(dmalmAsm)
					.on(dmalmAsm.dmalmAsmPk.eq(dmalmAsmProdArch.dmalmAsmPk))
					.where(dmalmAsmProdArch.dmalmProdottoPk.eq(productPk))
					.list(dmalmAsm.applicazione);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		return applicazioni;
	}

	
	public static void closeProductArchDuplicate() throws DAOException {
		try {
			QueryManager qm = null;

			qm = QueryManager.getInstance();

			String separator = ";";

			// chiude i prodotti legati al tappo per i quali esiste anche una
			// relazione ad ASM
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.CLOSE_PRODOTTI_ARCH, separator);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public static List<Tuple> getProductBySigla(String sigla, Timestamp inizio, Timestamp fine) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();
		List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(sigla);
		if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
			if (sigla.contains(".")) {
				sigla = sigla.substring(0, sigla.indexOf("."));
			}
		}
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(dmalmElProdArc)
					.where(dmalmElProdArc.annullato.eq("NO"))
					.where(dmalmElProdArc.sigla.eq(sigla))
					.where(dmalmElProdArc.dataInizioValidita.between(inizio, fine)
							.or(dmalmElProdArc.dataFineValidita.between(inizio, fine))
							.or(dmalmElProdArc.dataInizioValidita.before(inizio).and(dmalmElProdArc.dataFineValidita.after(fine))))
					.list(dmalmElProdArc.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

}