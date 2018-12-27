package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class ElettraProdottiArchitettureDAO {
	private static Logger logger = Logger
			.getLogger(ElettraProdottiArchitettureDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmProjectProdottiArchitetture dmalmProjectProdotto = QDmalmProjectProdottiArchitetture.qDmalmProjectProdottiArchitetture;

	private ElettraProdottiArchitettureDAO() {}
	public static List<DmalmElProdottiArchitetture> getAllProdotti(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;

		DmalmElProdottiArchitetture bean = null;
		List<DmalmElProdottiArchitetture> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_ELETTRAPRODOTTIARCHITETTURE);
			try(PreparedStatement ps = connection.prepareStatement(sql);){
				ps.setTimestamp(1, dataEsecuzione);

				rs = ps.executeQuery();
	
				while (rs.next()) {
					bean = new DmalmElProdottiArchitetture();
	
					bean.setProdottoPk(rs.getInt("DMALM_STG_PROD_ARCH_PK"));
					bean.setIdProdottoEdma(rs.getString("ID_PRODOTTO_EDMA"));
					bean.setIdProdotto(rs.getString("ID_PRODOTTO"));
					bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
					bean.setSigla(rs.getString("SIGLA"));
					bean.setNome(rs.getString("NOME"));
					bean.setDescrizioneProdotto(rs.getString("DS_PRODOTTO"));
					bean.setAreaProdotto(rs.getString("AREA_PRODOTTO"));
					bean.setResponsabileProdotto(rs
							.getString("RESPONSABILE_PRODOTTO"));
					bean.setAnnullato(rs.getString("ANNULLATO"));
					bean.setDataAnnullamento(DateUtils.stringToDate(
							rs.getString("DT_ANNULLAMENTO"), "yyyyMMdd"));
					bean.setAmbitoManutenzione(rs.getString("AMBITO_MANUTENZIONE"));
					bean.setAreaTematica(rs.getString("AREA_TEMATICA"));
					bean.setBaseDatiEtl(rs.getString("BASE_DATI_ETL"));
					bean.setBaseDatiLettura(rs.getString("BASE_DATI_LETTURA"));
					bean.setBaseDatiScrittura(rs.getString("BASE_DATI_SCRITTURA"));
					bean.setCategoria(rs.getString("CATEGORIA"));
					bean.setFornituraRisorseEsterne(rs
							.getString("FORNITURA_RISORSE_ESTERNE"));
					bean.setCodiceAreaProdotto(rs.getString("CD_AREA_PRODOTTO"));
					bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
					bean.setUnitaOrganizzativaFk(rs.getInt("UNITAORGANIZZATIVA_FK"));
					bean.setPersonaleFk(rs.getInt("PERSONALE_FK"));
					//modificato per DM_ALM-224
					bean.setAmbitoTecnologico(rs.getString("AMBITO_TECNOLOGICO"));
					bean.setAmbitoManutenzioneDenom(rs.getString("AMBITO_MANUTENZIONE_DENOM"));
					bean.setAmbitoManutenzioneCodice(rs.getString("AMBITO_MANUTENZIONE_CODICE"));
					bean.setStato(rs.getString("STATO_PRODOTTO"));
					
					
					prodotti.add(bean);
				}
			}
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new DAOException(e);
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (cm != null) {
					cm.closeConnection(connection);
				}
			}
			

		return prodotti;
	}
	
	public static DmalmElProdottiArchitetture getBeanFromTuple(ResultSet rs) throws SQLException
	{
		DmalmElProdottiArchitetture bean = new DmalmElProdottiArchitetture();

		bean.setProdottoPk(rs.getInt("DMALM_PRODOTTO_PK"));
		bean.setIdProdottoEdma(rs.getString("ID_PRODOTTO_EDMA"));
		bean.setIdProdotto(rs.getString("ID_PRODOTTO"));
		bean.setTipoOggetto(rs.getString("TIPO_OGGETTO"));
		bean.setSigla(rs.getString("SIGLA"));
		bean.setNome(rs.getString("NOME"));
		bean.setDescrizioneProdotto(rs.getString("DS_PRODOTTO"));
		bean.setAreaProdotto(rs.getString("AREA_PRODOTTO"));
		bean.setResponsabileProdotto(rs
				.getString("RESPONSABILE_PRODOTTO"));
		bean.setAnnullato(rs.getString("ANNULLATO"));
		bean.setDataAnnullamento(DateUtils.stringToDate(
				rs.getString("DT_ANNULLAMENTO"), "yyyyMMdd"));
		bean.setAmbitoManutenzione(rs.getString("AMBITO_MANUTENZIONE"));
		bean.setAreaTematica(rs.getString("AREA_TEMATICA"));
		bean.setBaseDatiEtl(rs.getString("BASE_DATI_ETL"));
		bean.setBaseDatiLettura(rs.getString("BASE_DATI_LETTURA"));
		bean.setBaseDatiScrittura(rs.getString("BASE_DATI_SCRITTURA"));
		bean.setCategoria(rs.getString("CATEGORIA"));
		bean.setFornituraRisorseEsterne(rs
				.getString("FORNITURA_RISORSE_ESTERNE"));
		bean.setCodiceAreaProdotto(rs.getString("CD_AREA_PRODOTTO"));
		bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
		bean.setUnitaOrganizzativaFk(rs.getInt("DMALM_UNITAORGANIZZATIVA_FK_01"));
		bean.setPersonaleFk(rs.getInt("DMALM_PERSONALE_FK_02"));
		//modificato per DM_ALM-224
		bean.setAmbitoTecnologico(rs.getString("AMBITO_TECNOLOGICO"));
		bean.setAmbitoManutenzioneDenom(rs.getString("AMBITO_MANUTENZIONE_DENOM"));
		bean.setAmbitoManutenzioneCodice(rs.getString("AMBITO_MANUTENZIONE_CODICE"));
		bean.setStato(rs.getString("STATO_PRODOTTO"));
		
		return bean;
	}
	
	public static DmalmElProdottiArchitetture getBeanFromTuple(Tuple rs) 
	{
		
		DmalmElProdottiArchitetture bean = new DmalmElProdottiArchitetture();

		
		bean.setProdottoPk(rs.get(qDmalmElProdottiArchitetture.prodottoPk));
		bean.setIdProdottoEdma(rs.get(qDmalmElProdottiArchitetture.idProdottoEdma));
		bean.setIdProdotto(rs.get(qDmalmElProdottiArchitetture.idProdotto));	
		bean.setTipoOggetto(rs.get(qDmalmElProdottiArchitetture.tipoOggetto));
		bean.setSigla(rs.get(qDmalmElProdottiArchitetture.sigla));
		bean.setNome(rs.get(qDmalmElProdottiArchitetture.nome));
		bean.setDescrizioneProdotto(rs.get(qDmalmElProdottiArchitetture.descrizioneProdotto));
		bean.setAreaProdotto(rs.get(qDmalmElProdottiArchitetture.areaProdotto));
		bean.setResponsabileProdotto(rs.get(qDmalmElProdottiArchitetture.responsabileProdotto));
		
		bean.setAnnullato(rs.get(qDmalmElProdottiArchitetture.annullato));
		
		bean.setDataAnnullamento(rs.get(qDmalmElProdottiArchitetture.dataAnnullamento));
		bean.setAmbitoManutenzione(rs.get(qDmalmElProdottiArchitetture.ambitoManutenzione));
		
		bean.setAreaTematica(rs.get(qDmalmElProdottiArchitetture.areaTematica));
		bean.setBaseDatiEtl(rs.get(qDmalmElProdottiArchitetture.baseDatiEtl));

		bean.setBaseDatiLettura(rs.get(qDmalmElProdottiArchitetture.baseDatiLettura));
		
		bean.setBaseDatiScrittura(rs.get(qDmalmElProdottiArchitetture.baseDatiScrittura));

		bean.setCategoria(rs.get(qDmalmElProdottiArchitetture.categoria));
		
		bean.setFornituraRisorseEsterne(rs.get(qDmalmElProdottiArchitetture.fornituraRisorseEsterne));
		
		bean.setCodiceAreaProdotto(rs.get(qDmalmElProdottiArchitetture.codiceAreaProdotto));
		
		bean.setDataCaricamento(rs.get(qDmalmElProdottiArchitetture.dataCaricamento));
		
		bean.setDataInizioValidita(rs.get(qDmalmElProdottiArchitetture.dataInizioValidita));
		bean.setDataFineValidita(rs.get(qDmalmElProdottiArchitetture.dataFineValidita));

		bean.setUnitaOrganizzativaFk(rs.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk));
		
		bean.setUnitaOrgFlatFk(rs.get(qDmalmElProdottiArchitetture.unitaOrgFlatFk));
		
		bean.setPersonaleFk(rs.get(qDmalmElProdottiArchitetture.personaleFk));
		
		bean.setAmbitoTecnologico(rs.get(qDmalmElProdottiArchitetture.ambitoTecnologico));
		
		bean.setAmbitoManutenzioneDenom(rs.get(qDmalmElProdottiArchitetture.ambitoManutenzioneDenom));

		bean.setAmbitoManutenzioneCodice(rs.get(qDmalmElProdottiArchitetture.ambitoManutenzioneCodice));
		bean.setStato(rs.get(qDmalmElProdottiArchitetture.stato));
		
		return bean;
	}

	public static List<Tuple> getProdotto(DmalmElProdottiArchitetture bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.sigla.eq(bean
							.getSigla()))
					.where(qDmalmElProdottiArchitetture.dataFineValidita.in(new SQLSubQuery()
							.from(qDmalmElProdottiArchitetture)
							.where(qDmalmElProdottiArchitetture.sigla.eq(bean
									.getSigla()))
							.list(qDmalmElProdottiArchitetture.dataFineValidita
									.max())))
					.list(qDmalmElProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return prodotti;
	}
	
	public static List <Tuple> getProdottiWithoutUO (Timestamp dataEsecuzione){
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.dataCaricamento.eq(dataEsecuzione))
					.where(qDmalmElProdottiArchitetture.unitaOrganizzativaFk.eq(0))
					.list(qDmalmElProdottiArchitetture.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				try {
					cm.closeConnection(connection);
				} catch (DAOException e) {
					logger.error(e);
				}
			}
			
		}
		return prodotti;

	}
	public static List<Integer> getAllTargetProdottoAnnullati()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.annullato.eq("SI"))
					.list(qDmalmElProdottiArchitetture.prodottoPk);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return prodotti;
	}

	public static void insertProdotto(DmalmElProdottiArchitetture bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					qDmalmElProdottiArchitetture)
					.columns(
							qDmalmElProdottiArchitetture.prodottoPk,
							qDmalmElProdottiArchitetture.idProdottoEdma,
							qDmalmElProdottiArchitetture.idProdotto,
							qDmalmElProdottiArchitetture.tipoOggetto,
							qDmalmElProdottiArchitetture.sigla,
							qDmalmElProdottiArchitetture.nome,
							qDmalmElProdottiArchitetture.descrizioneProdotto,
							qDmalmElProdottiArchitetture.areaProdotto,
							qDmalmElProdottiArchitetture.responsabileProdotto,
							qDmalmElProdottiArchitetture.annullato,
							qDmalmElProdottiArchitetture.dataAnnullamento,
							qDmalmElProdottiArchitetture.ambitoManutenzione,
							qDmalmElProdottiArchitetture.areaTematica,
							qDmalmElProdottiArchitetture.baseDatiEtl,
							qDmalmElProdottiArchitetture.baseDatiLettura,
							qDmalmElProdottiArchitetture.baseDatiScrittura,
							qDmalmElProdottiArchitetture.categoria,
							qDmalmElProdottiArchitetture.fornituraRisorseEsterne,
							qDmalmElProdottiArchitetture.codiceAreaProdotto,
							qDmalmElProdottiArchitetture.dataCaricamento,
							qDmalmElProdottiArchitetture.unitaOrganizzativaFk,
							qDmalmElProdottiArchitetture.personaleFk,
							qDmalmElProdottiArchitetture.dataInizioValidita,
							qDmalmElProdottiArchitetture.dataFineValidita,
							qDmalmElProdottiArchitetture.ambitoTecnologico,
							qDmalmElProdottiArchitetture.ambitoManutenzioneDenom,
							qDmalmElProdottiArchitetture.ambitoManutenzioneCodice,
							qDmalmElProdottiArchitetture.stato)
					.values(bean.getProdottoPk(), bean.getIdProdottoEdma(),
							bean.getIdProdotto(), bean.getTipoOggetto(),
							bean.getSigla(), bean.getNome(),
							bean.getDescrizioneProdotto(),
							bean.getAreaProdotto(),
							bean.getResponsabileProdotto(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							bean.getAmbitoManutenzione(),
							bean.getAreaTematica(), bean.getBaseDatiEtl(),
							bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), bean.getCategoria(),
							bean.getFornituraRisorseEsterne(),
							bean.getCodiceAreaProdotto(),
							bean.getDataCaricamento(),
							bean.getUnitaOrganizzativaFk(),
							bean.getPersonaleFk(),
							DataEsecuzione.getInstance().getDataEsecuzione(),
							DateUtils.setDtFineValidita9999(),
							bean.getAmbitoTecnologico(),
							bean.getAmbitoManutenzioneDenom(),
							bean.getAmbitoManutenzioneCodice(),
							bean.getStato()).execute();

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

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			Integer prodottoPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.prodottoPk
							.eq(prodottoPk))
					.set(qDmalmElProdottiArchitetture.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataFineValidita,
									-1)).execute();

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
	public static void updateDataAnnullamento(Timestamp dataAnnullamento,
			Integer prodottoPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.prodottoPk
							.eq(prodottoPk))
					.set(qDmalmElProdottiArchitetture.dataAnnullamento,dataAnnullamento).execute();

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

	public static void insertProdottoUpdate(Timestamp dataEsecuzione,
			DmalmElProdottiArchitetture bean) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect,
					qDmalmElProdottiArchitetture)
					.columns(
							qDmalmElProdottiArchitetture.prodottoPk,
							qDmalmElProdottiArchitetture.idProdottoEdma,
							qDmalmElProdottiArchitetture.idProdotto,
							qDmalmElProdottiArchitetture.tipoOggetto,
							qDmalmElProdottiArchitetture.sigla,
							qDmalmElProdottiArchitetture.nome,
							qDmalmElProdottiArchitetture.descrizioneProdotto,
							qDmalmElProdottiArchitetture.areaProdotto,
							qDmalmElProdottiArchitetture.responsabileProdotto,
							qDmalmElProdottiArchitetture.annullato,
							qDmalmElProdottiArchitetture.dataAnnullamento,
							qDmalmElProdottiArchitetture.ambitoManutenzione,
							qDmalmElProdottiArchitetture.areaTematica,
							qDmalmElProdottiArchitetture.baseDatiEtl,
							qDmalmElProdottiArchitetture.baseDatiLettura,
							qDmalmElProdottiArchitetture.baseDatiScrittura,
							qDmalmElProdottiArchitetture.categoria,
							qDmalmElProdottiArchitetture.fornituraRisorseEsterne,
							qDmalmElProdottiArchitetture.codiceAreaProdotto,
							qDmalmElProdottiArchitetture.dataCaricamento,
							qDmalmElProdottiArchitetture.unitaOrganizzativaFk,
							qDmalmElProdottiArchitetture.personaleFk,
							qDmalmElProdottiArchitetture.dataInizioValidita,
							qDmalmElProdottiArchitetture.dataFineValidita,
							//modificato per DM_ALM-224
							qDmalmElProdottiArchitetture.ambitoTecnologico,
							qDmalmElProdottiArchitetture.ambitoManutenzioneDenom,
							qDmalmElProdottiArchitetture.ambitoManutenzioneCodice,
							qDmalmElProdottiArchitetture.stato)
					.values(StringTemplate
							.create("STG_PROD_ARCHITETTURE_SEQ.nextval"), bean.getIdProdottoEdma(),
							bean.getIdProdotto(), bean.getTipoOggetto(),
							bean.getSigla(), bean.getNome(),
							bean.getDescrizioneProdotto(),
							bean.getAreaProdotto(),
							bean.getResponsabileProdotto(),
							bean.getAnnullato(), bean.getDataAnnullamento(),
							bean.getAmbitoManutenzione(),
							bean.getAreaTematica(), bean.getBaseDatiEtl(),
							bean.getBaseDatiLettura(),
							bean.getBaseDatiScrittura(), bean.getCategoria(),
							bean.getFornituraRisorseEsterne(),
							bean.getCodiceAreaProdotto(),
							bean.getDataCaricamento(),
							bean.getUnitaOrganizzativaFk(),
							bean.getPersonaleFk(), dataEsecuzione,
							DateUtils.setDtFineValidita9999(),
							//modificato pr DM_ALM-224
							bean.getAmbitoTecnologico(),
							bean.getAmbitoManutenzioneDenom(),
							bean.getAmbitoManutenzioneCodice(),
							bean.getStato()).execute();

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

	public static void updateProdotto(Integer prodottoPk,
			DmalmElProdottiArchitetture prodotto) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,
					qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.prodottoPk
							.eq(prodottoPk))
					.set(qDmalmElProdottiArchitetture.idProdotto,
							prodotto.getIdProdotto())
					.set(qDmalmElProdottiArchitetture.tipoOggetto,
							prodotto.getTipoOggetto())
					.set(qDmalmElProdottiArchitetture.descrizioneProdotto,
							prodotto.getDescrizioneProdotto())
					.set(qDmalmElProdottiArchitetture.ambitoManutenzione,
							prodotto.getAmbitoManutenzione())
					.set(qDmalmElProdottiArchitetture.areaTematica,
							prodotto.getAreaTematica())
					.set(qDmalmElProdottiArchitetture.baseDatiEtl,
							prodotto.getBaseDatiEtl())
					.set(qDmalmElProdottiArchitetture.baseDatiLettura,
							prodotto.getBaseDatiLettura())
					.set(qDmalmElProdottiArchitetture.baseDatiScrittura,
							prodotto.getBaseDatiScrittura())
					.set(qDmalmElProdottiArchitetture.categoria,
							prodotto.getCategoria())
					.set(qDmalmElProdottiArchitetture.fornituraRisorseEsterne,
							prodotto.getFornituraRisorseEsterne())
					.set(qDmalmElProdottiArchitetture.dataAnnullamento,
							DateUtils.dateToTimestamp(prodotto.getDataAnnullamento()))
					.set(qDmalmElProdottiArchitetture.ambitoTecnologico, prodotto.getAmbitoTecnologico())
					.set(qDmalmElProdottiArchitetture.ambitoManutenzioneDenom, prodotto.getAmbitoManutenzioneDenom())
					.set(qDmalmElProdottiArchitetture.ambitoManutenzioneCodice, prodotto.getAmbitoManutenzioneCodice())
					.set(qDmalmElProdottiArchitetture.stato, prodotto.getStato())
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

	public static List<Tuple> getProductByAcronym(String asm)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.sigla.eq(asm))
					.where(qDmalmElProdottiArchitetture.dataFineValidita
							.eq(DateUtils.setDtFineValidita9999()))
					.list(qDmalmElProdottiArchitetture.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return prodotti;
	}

	public static List<Tuple> getAllProductToLinkWithProject()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti Aperti non associati ad un Project (escluso il
			// tappo prodottoPk = 0)
			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.leftJoin(dmalmProjectProdotto)
					.on(dmalmProjectProdotto.dmalmProdottoPk
							.eq(qDmalmElProdottiArchitetture.prodottoPk))
					.where(qDmalmElProdottiArchitetture.prodottoPk
							.ne(Integer.valueOf(0)))
					.where(qDmalmElProdottiArchitetture.dataFineValidita
							.eq(DateUtils.setDtFineValidita9999()))
					.where(dmalmProjectProdotto.dmalmProdottoPk.isNull())
					.list(qDmalmElProdottiArchitetture.all());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return prodotti;
	}
	public static List<DmalmElProdottiArchitetture>  getAllTargetProdottoNotAnnullati() throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> prodotti = new ArrayList<>();
		List<DmalmElProdottiArchitetture> prodottiBean= new ArrayList<>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query
					.from(qDmalmElProdottiArchitetture)
					.where(qDmalmElProdottiArchitetture.nome.notLike("%ANNULLATO LOGICAMENTE%" ))
					.where(qDmalmElProdottiArchitetture.nome.notLike("%ANNULLATO FISICAMENTE%"))
					.where(qDmalmElProdottiArchitetture.dataFineValidita.eq(DateUtils.setDtFineValidita9999()))
					.list(qDmalmElProdottiArchitetture.all());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		for(Tuple row:prodotti) {
			prodottiBean.add(getBeanFromTuple(row));
		}
		
		return prodottiBean;
	}

}
