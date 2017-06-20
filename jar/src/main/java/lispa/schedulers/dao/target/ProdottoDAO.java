package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PRODOTTI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdotto;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ProdottoDAO {

	private static Logger logger = Logger.getLogger(ProdottoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmAsmProdotto dmalmAsmProdotto = QDmalmAsmProdotto.dmalmAsmProdotto;
	private static QDmalmProjectProdotto dmalmProjectProdotto = QDmalmProjectProdotto.dmalmProjectProdotto;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public static List<DmalmProdotto> getAllProdotti(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProdotto bean = null;
		List<DmalmProdotto> prodotti = new ArrayList<DmalmProdotto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_PRODOTTI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {

				bean = new DmalmProdotto();
				bean.setDmalmProdottoSeq(rs.getInt("DMALM_PRODOTTO_SEQ"));
				bean.setAmbitoManutenzione(rs.getString("AMBITO_MANUTENZIONE"));
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setAreaTematica(rs.getString("AREA_TEMATICA"));
				bean.setBaseDatiEtl(rs.getString("BASE_DATI_ETL"));
				bean.setBaseDatiLettura(rs.getString("BASE_DATI_LETTURA"));
				bean.setBaseDatiScrittura(rs.getString("BASE_DATI_SCRITTURA"));
				bean.setCategoria(rs.getString("CATEGORIA"));
				bean.setDmalmProdottoPk(rs.getInt("ID_EDMA"));
				bean.setDsProdotto(rs.getString("DS_PRODOTTO"));
				bean.setDtAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setFornituraRisorseEsterne(rs
						.getString("FORNITURA_RISORSE_ESTERNE"));
				bean.setIdProdotto(rs.getString("ID_PRODOTTO"));
				bean.setNome(rs.getString("NOME"));
				bean.setSigla(rs.getString("SIGLA"));
				bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));

				// FK
				// bean.setDmalm_unitaorganizzativa_fk01(StrutturaOrganizzativaEdmaLispaDAO.getIdStrutturaOrganizzativaProdottoByCodice(rs.getString("AREA"))+"");
				// bean.setDmalm_personale_fk02(PersonaleEdmaLispaDAO.getPersonaleEdmaLispaByCodice(rs.getString("RESPONSABILE_AREA"))+"");
				bean.setDmalm_unitaorganizzativa_fk01(rs.getInt("AREA"));
				bean.setDmalm_personale_fk02(rs.getInt("RESPONSABILE_AREA"));

				prodotti.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return prodotti;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmProdotto prodotto) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmProdotto)
					.where(dmalmProdotto.dmalmProdottoPrimaryKey.eq(prodotto
							.getDmalmProdottoPk()))
					.where(dmalmProdotto.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmProdotto)
									.where(dmalmProdotto.dmalmProdottoPrimaryKey
											.eq(prodotto.getDmalmProdottoPk()))
									.list(dmalmProdotto.dtInserimentoRecord
											.max())))
					.set(dmalmProdotto.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void resetDataFineValidita9999(DmalmProdotto prodotto)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmProdotto)
					.where(dmalmProdotto.dmalmProdottoPrimaryKey.eq(prodotto
							.getDmalmProdottoPk()))
					.where(dmalmProdotto.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmProdotto)
									.where(dmalmProdotto.dmalmProdottoPrimaryKey
											.eq(prodotto.getDmalmProdottoPk()))
									.list(dmalmProdotto.dtInserimentoRecord
											.max())))
					.set(dmalmProdotto.dtFineValidita,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateProdotto(DmalmProdotto prodotto)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmProdotto)
					.where(dmalmProdotto.dmalmProdottoPrimaryKey.eq(prodotto
							.getDmalmProdottoPk()))
					.where(dmalmProdotto.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmProdotto)
									.where(dmalmProdotto.dmalmProdottoPrimaryKey
											.eq(prodotto.getDmalmProdottoPk()))
									.list(dmalmProdotto.dtInserimentoRecord
											.max())))
					.set(dmalmProdotto.idProdotto, prodotto.getIdProdotto())
					.set(dmalmProdotto.tipoOggetto, prodotto.getTipoOggetto())
					.set(dmalmProdotto.dsProdotto, prodotto.getDsProdotto())
					.set(dmalmProdotto.ambitoManutenzione,
							prodotto.getAmbitoManutenzione())
					.set(dmalmProdotto.areaTematica, prodotto.getAreaTematica())
					.set(dmalmProdotto.baseDatiEtl, prodotto.getBaseDatiEtl())
					.set(dmalmProdotto.baseDatiLettura,
							prodotto.getBaseDatiLettura())
					.set(dmalmProdotto.baseDatiScrittura,
							prodotto.getBaseDatiScrittura())
					.set(dmalmProdotto.categoria, prodotto.getCategoria())
					.set(dmalmProdotto.fornituraRisorseEsterne,
							prodotto.getFornituraRisorseEsterne())
					.set(dmalmProdotto.dtAnnullamento,
							prodotto.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertProdotto(DmalmProdotto bean,
			Timestamp dataesecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmProdotto)
					.columns(dmalmProdotto.ambitoManutenzione,
							dmalmProdotto.annullato,
							dmalmProdotto.dmalmUnitaOrganizzativaFk01,
							dmalmProdotto.areaTematica,
							dmalmProdotto.baseDatiEtl,
							dmalmProdotto.baseDatiLettura,
							dmalmProdotto.baseDatiScrittura,
							dmalmProdotto.categoria,
							dmalmProdotto.dmalmProdottoPrimaryKey,
							dmalmProdotto.dsProdotto,
							dmalmProdotto.fornituraRisorseEsterne,
							dmalmProdotto.idProdotto, dmalmProdotto.nome,
							dmalmProdotto.dmalmPersonaleFk02,
							dmalmProdotto.sigla, dmalmProdotto.tipoOggetto,
							dmalmProdotto.dtInizioValidita,
							dmalmProdotto.dtFineValidita,
							dmalmProdotto.dtInserimentoRecord,
							dmalmProdotto.dtAnnullamento,
							dmalmProdotto.dmalmProdottoSeq)
					.values(bean.getAmbitoManutenzione(), bean.getAnnullato(),
							bean.getDmalm_unitaorganizzativa_fk01(),
							bean.getAreaTematica(), bean.getBaseDatiEtl(),
							bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), bean.getCategoria(),
							bean.getDmalmProdottoPk(), bean.getDsProdotto(),
							bean.getFornituraRisorseEsterne(),
							bean.getIdProdotto(), bean.getNome(),
							bean.getDmalm_personale_fk02(), bean.getSigla(),
							bean.getTipoOggetto(),
							DateUtils.setDtInizioValidita1900(),
							DateUtils.setDtFineValidita9999(), dataesecuzione,
							bean.getDtAnnullamento(),
							bean.getDmalmProdottoSeq()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error("insertProdotto() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertProdottoUpdate(Timestamp dataEsecuzione,
			DmalmProdotto bean) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmProdotto)
					.columns(dmalmProdotto.ambitoManutenzione,
							dmalmProdotto.annullato,
							dmalmProdotto.dmalmUnitaOrganizzativaFk01,
							dmalmProdotto.areaTematica,
							dmalmProdotto.baseDatiEtl,
							dmalmProdotto.baseDatiLettura,
							dmalmProdotto.baseDatiScrittura,
							dmalmProdotto.categoria,
							dmalmProdotto.dmalmProdottoPrimaryKey,
							dmalmProdotto.dsProdotto,
							dmalmProdotto.fornituraRisorseEsterne,
							dmalmProdotto.idProdotto, dmalmProdotto.nome,
							dmalmProdotto.dmalmPersonaleFk02,
							dmalmProdotto.sigla, dmalmProdotto.tipoOggetto,
							dmalmProdotto.dtInizioValidita,
							dmalmProdotto.dtFineValidita,
							dmalmProdotto.dtInserimentoRecord,
							dmalmProdotto.dtAnnullamento,
							dmalmProdotto.dmalmProdottoSeq)
					.values(bean.getAmbitoManutenzione(), bean.getAnnullato(),
							bean.getDmalm_unitaorganizzativa_fk01(),
							bean.getAreaTematica(), bean.getBaseDatiEtl(),
							bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), bean.getCategoria(),
							bean.getDmalmProdottoPk(), bean.getDsProdotto(),
							bean.getFornituraRisorseEsterne(),
							bean.getIdProdotto(), bean.getNome(),
							bean.getDmalm_personale_fk02(), bean.getSigla(),
							bean.getTipoOggetto(),
							DateUtils.formatDataEsecuzione(dataEsecuzione), // inizio
							DateUtils.setDtFineValidita9999(), // fine
							dataEsecuzione, // inserimento
							bean.getDtAnnullamento(), // annullamento
							bean.getDmalmProdottoSeq()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage(), e);
			logger.error("insertProdottoUpdate() --> "
					+ LogUtils.objectToString(bean));

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getProdotto(DmalmProdotto bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(dmalmProdotto)
					.where(dmalmProdotto.dmalmProdottoPrimaryKey.eq(bean
							.getDmalmProdottoPk()))
					.where(dmalmProdotto.dtInserimentoRecord
							.in(new SQLSubQuery()
									.from(dmalmProdotto)
									.where(dmalmProdotto.dmalmProdottoPrimaryKey
											.eq(bean.getDmalmProdottoPk()))
									.list(dmalmProdotto.dtInserimentoRecord
											.max()))).list(dmalmProdotto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getProductByAcronym(String asm)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(dmalmProdotto)
					.where(dmalmProdotto.sigla.eq(asm))
					.where(dmalmProdotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.list(dmalmProdotto.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getAllProductToLinkWithProject()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti Aperti non associati ad un Project (escluso il
			// tappo dmalmProdottoSeq = 0)
			prodotti = query
					.from(dmalmProdotto)
					.leftJoin(dmalmProjectProdotto)
					.on(dmalmProjectProdotto.dmalmProdottoSeq
							.eq(dmalmProdotto.dmalmProdottoSeq))
					.where(dmalmProdotto.dmalmProdottoSeq.ne(new Integer(0)))
					.where(dmalmProdotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(dmalmProjectProdotto.dmalmProdottoSeq.isNull())
					.list(dmalmProdotto.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getAllProductToLinkWithAsm() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti Aperti non associati ad una Asm (escluso il
			// tappo dmalmProdottoSeq = 0)
			prodotti = query
					.from(dmalmProdotto)
					.leftJoin(dmalmAsmProdotto)
					.on(dmalmAsmProdotto.dmalmProdottoSeq
							.eq(dmalmProdotto.dmalmProdottoSeq))
					.where(dmalmProdotto.dmalmProdottoSeq.ne(new Integer(0)))
					.where(dmalmProdotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.where(dmalmAsmProdotto.dmalmProdottoSeq.isNull())
					.list(dmalmProdotto.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static void closeProductDuplicate() throws DAOException {
		try {
			QueryManager qm = null;

			qm = QueryManager.getInstance();

			String separator = ";";

			// chiude i prodotti legati al tappo per i quali esiste anche una
			// relazione ad ASM
			qm.executeMultipleStatementsFromFile(DmAlmConstants.CLOSE_PRODOTTO,
					separator);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	public static List<Tuple> getProductBySigla(String sigla, Timestamp inizio,
			Timestamp fine) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);
			List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(sigla);
			
			if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
				if (sigla.contains(".")) {
					sigla = sigla.substring(0, sigla.indexOf("."));
				}
			}
			prodotti = query
					.from(dmalmProdotto)
					.where(dmalmProdotto.sigla.eq(sigla))
					.where(dmalmProdotto.dtInizioValidita.between(inizio, fine)
						.or(dmalmProdotto.dtFineValidita.between(inizio, fine))
						.or(dmalmProdotto.dtInizioValidita.before(inizio).and(dmalmProdotto.dtFineValidita.after(fine))))
					.list(dmalmProdotto.all());
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