package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.bean.target.elettra.DmalmElFunzionalita;
import lispa.schedulers.bean.target.elettra.DmalmElModuli;
import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzative;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmProdottoDAO;
import lispa.schedulers.dao.target.elettra.ElettraFunzionalitaDAO;
import lispa.schedulers.dao.target.elettra.ElettraModuliDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElFunzionalita;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElModuli;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

public class CheckAnnullamentiElettraFacade {
	private static Logger logger = Logger.getLogger(CheckAnnullamentiElettraFacade.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElPersonale personale = QDmalmElPersonale.qDmalmElPersonale;
	private static QDmalmElUnitaOrganizzative unitaOrganizzativa = QDmalmElUnitaOrganizzative.qDmalmElUnitaOrganizzative;
	private static QDmalmElAmbienteTecnologicoClassificatori ambienteTecnologicoClassificatori = QDmalmElAmbienteTecnologicoClassificatori.qDmalmElAmbienteTecnologicoClassificatori;
	private static QDmalmElFunzionalita funzionalita = QDmalmElFunzionalita.qDmalmElFunzionalita;
	private static QDmalmElModuli moduli = QDmalmElModuli.qDmalmElModuli;
	private static QDmalmElProdottiArchitetture prodotti = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

	public static void execute(Timestamp dataEsecuzione) throws DAOException, SQLException {

		checkAnnullamentiElUnitaOrganizzativa(dataEsecuzione);

		checkAnnullamentiElPersonale(dataEsecuzione);


		checkAnnullamentiElProdotto(dataEsecuzione);

		checkAnnullamentiElModulo(dataEsecuzione);

		checkAnnullamentiElFunzionalita(dataEsecuzione);
	}

	private static void checkAnnullamentiElFunzionalita(Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);
			
			
			String sql = QueryManager.getInstance().getQuery(DmAlmConstants.ELETTRA_ANN_FUNZIONALITA);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);
			rs = ps.executeQuery();

			while (rs.next()) {
			
				DmalmElFunzionalita funz=ElettraFunzionalitaDAO.getBeanFromTuple(rs);
				String nome=funz.getNome()!=null?funz.getNome():"";
				
				if(nome.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)){
					nome=nome.substring(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH.length()+9,nome.length());
				}
				
				nome=DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE_STARTSWITH+ format.format(DateUtils.getDataEsecuzione())+"#"+nome;
				
				funz.setAnnullato(DmAlmConstants.ANNULLATO_FISICAMENTE_ELETTRA);
				funz.setNome(nome);
				funz.setFunzionalitaPk(null);
				funz.setDtAnnullamento(DateUtils.addSecondsToTimestamp(dataEsecuzione,-1));
				ElettraFunzionalitaDAO.insertFunzionalitaUpdate(dataEsecuzione, funz);		
				
			}

			conn.commit();

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

	}

	private static void checkAnnullamentiElModulo(Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);
			
			String sql = QueryManager.getInstance().getQuery(DmAlmConstants.ELETTRA_ANN_MODULO);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);
			rs = ps.executeQuery();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			while (rs.next()) {
				
				DmalmElModuli modulo=ElettraModuliDAO.getBeanFromTuple(rs);
				
				checkAnnullaFunzionalitaDiModulo(modulo, dataEsecuzione,dataEsecuzione);
				
				ElettraModuliDAO.updateDataFineValidita(dataEsecuzione, modulo.getModuloPk());
				
				String nome=modulo.getNome()!=null?modulo.getNome():"";
				
				if(nome.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)){
					nome=nome.substring(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH.length()+9,nome.length());
				}
				
				nome=DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE_STARTSWITH+ format.format(DateUtils.getDataEsecuzione())+"#"+nome;
				
				modulo.setAnnullato(DmAlmConstants.ANNULLATO_FISICAMENTE_ELETTRA);
				modulo.setNome(nome);
				modulo.setModuloPk(null);
				modulo.setDataAnnullamento(DateUtils.addSecondsToTimestamp(dataEsecuzione,-1));
				ElettraModuliDAO.insertModuloUpdate(dataEsecuzione, modulo);
			}
			
			conn.commit();

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}

	}

	private static void checkAnnullamentiElProdotto(Timestamp dataEsecuzione) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmElProdottiArchitetture prodottoBean;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);


			String annullaFisicamenteSql = QueryManager.getInstance().getQuery(DmAlmConstants.ELETTRA_ANN_PRODOTTO_FISICAMENTE);

			ps = conn.prepareStatement(annullaFisicamenteSql);
			ps.setTimestamp(1, dataEsecuzione);
			rs = ps.executeQuery();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			
			
			while (rs.next()) {
				
				prodottoBean= ElettraProdottiArchitettureDAO.getBeanFromTuple(rs);
				
				Date dataAnnullamento=DateUtils.addSecondsToTimestamp(dataEsecuzione,-1);
				
				
				List <String> applicazioni=ProdottiArchitettureDAO.getAsmByProductPk(prodottoBean.getProdottoPk());
				if(applicazioni!=null && applicazioni.size()>0){
					for(String app:applicazioni){
						if(app.contains(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE) || app.contains(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE)){
							Date date=DateUtils.getDataAnnullamento(app, logger);
							if(date!=null){
								dataAnnullamento=date;
							}
						}
					}
				}
				
				checkAnnullaModuliDiProdotto(prodottoBean,dataEsecuzione,dataAnnullamento);

				ElettraProdottiArchitettureDAO.updateDataFineValidita(dataEsecuzione, prodottoBean.getProdottoPk());
				
				String nome=prodottoBean.getNome()!=null?prodottoBean.getNome():"";
				
				if(nome.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)){
					nome=nome.substring(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH.length()+9,nome.length());
				}
				
				nome=DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE_STARTSWITH+ format.format(DateUtils.getDataEsecuzione())+"#"+nome;
				
				prodottoBean.setAnnullato(DmAlmConstants.ANNULLATO_FISICAMENTE_ELETTRA);
				prodottoBean.setNome(nome);
				prodottoBean.setProdottoPk(null);
				prodottoBean.setDataAnnullamento(dataAnnullamento);
				ElettraProdottiArchitettureDAO.insertProdottoUpdate(dataEsecuzione,prodottoBean);
				
				
			}
			conn.commit();


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
		
		

	}

	private static void checkAnnullaModuliDiProdotto(DmalmElProdottiArchitetture prodottoBean,Timestamp dataEsecuzione, Date dataAnnullamento) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		try {
		
			List <DmalmElModuli> moduli=ElettraModuliDAO.getModuliByProdottoPk(prodottoBean.getProdottoPk());
			for(DmalmElModuli modulo:moduli)
			{
				checkAnnullaFunzionalitaDiModulo(modulo,dataEsecuzione,dataAnnullamento);

				ElettraModuliDAO.updateDataFineValidita(dataEsecuzione, modulo.getModuloPk());
				
				String nome=modulo.getNome()!=null?prodottoBean.getNome():"";
				
				if(nome.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)){
					nome=nome.substring(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH.length()+9,nome.length());
				}
				
				nome=DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE_STARTSWITH+ format.format(DateUtils.getDataEsecuzione())+"#"+nome;
				modulo.setNome(nome);
				modulo.setAnnullato(DmAlmConstants.ANNULLATO_FISICAMENTE_ELETTRA);
				modulo.setModuloPk(null);
				modulo.setDataAnnullamento(dataAnnullamento);
				ElettraModuliDAO.insertModuloUpdate(dataEsecuzione, modulo);
				
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		
	}

	private static void checkAnnullaFunzionalitaDiModulo(DmalmElModuli modulo, Timestamp dataEsecuzione, Date dataAnnullamento) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		
		try{
			
			List<DmalmElFunzionalita> funzionalitaList=ElettraFunzionalitaDAO.getFunzionalitaByModuloPk(modulo.getModuloPk(),dataEsecuzione);
			
			for(DmalmElFunzionalita funz:funzionalitaList){
				ElettraFunzionalitaDAO.updateDataFineValidita(dataEsecuzione, funz.getFunzionalitaPk());
				
				
				String nome=funz.getNome()!=null?funz.getNome():"";
				
				if(nome.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)){
					nome=nome.substring(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH.length()+9,nome.length());
				}
				
				nome=DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE_STARTSWITH+ format.format(DateUtils.getDataEsecuzione())+"#"+nome;
				
				funz.setAnnullato(DmAlmConstants.ANNULLATO_FISICAMENTE_ELETTRA);
				funz.setNome(nome);
				funz.setFunzionalitaPk(null);
				funz.setDtAnnullamento(new Timestamp(dataAnnullamento.getTime()));
				ElettraFunzionalitaDAO.insertFunzionalitaUpdate(dataEsecuzione, funz);
				
			}
			
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		
		
	}

	private static void checkAnnullamentiElModulo(Connection conn, List<Integer> listIdProdotti,
			String tipoAnnullamento, Timestamp dataOggi) throws DAOException {

		try {
			List<Integer> listIdModuli = new ArrayList<Integer>();
			SimpleDateFormat formatDate = new SimpleDateFormat("YYYYMMDD");
			String dataCaricamento = formatDate.format(dataOggi);

			for (Integer id : listIdProdotti) {
				new SQLUpdateClause(conn, dialect, moduli).where(moduli.prodottoFk.eq(id))
						.set(moduli.nome, "#"+tipoAnnullamento+"##"+dataCaricamento+"#"+id)
						.set(moduli.annullato, tipoAnnullamento).set(moduli.dataAnnullamento, dataOggi).execute();

				logger.debug("UPDATE "+moduli+" SET "+moduli.nome+"= #"+tipoAnnullamento+"##"+dataCaricamento+"#"+id
						+" AND "+moduli.annullato+" = "+tipoAnnullamento+ " AND "+moduli.dataAnnullamento+" = "+dataOggi
						+" WHERE "+moduli.prodottoFk+" = "+id);
				
				SQLQuery query = new SQLQuery(conn, dialect);
				List<Integer> listModuliPk = query.from(moduli).where(moduli.prodottoFk.eq(id)).list(moduli.moduloPk);
				listIdModuli.addAll(listModuliPk);
				
			}
			conn.commit();
			
			if (listIdModuli.size() > 0) {
				checkAnnullamentiElFunzionalita(conn, listIdModuli, tipoAnnullamento, dataOggi);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	private static void checkAnnullamentiElFunzionalita(Connection conn, List<Integer> listIdModuli,
			String tipoAnnullamento, Timestamp dataOggi) throws DAOException {

		try {

			SimpleDateFormat formatDate = new SimpleDateFormat("YYYYMMDD");
			String dataCaricamento = formatDate.format(dataOggi);
			for (Integer id : listIdModuli) {

				new SQLUpdateClause(conn, dialect, funzionalita).where(funzionalita.dmalmModuloFk01.eq(id))
					.set(funzionalita.nome, "#"+tipoAnnullamento+"##"+dataCaricamento+"#"+id)
					.set(funzionalita.annullato, tipoAnnullamento).set(funzionalita.dtAnnullamento, dataOggi)
					.execute();
				
				logger.debug("UPDATE "+funzionalita+" SET "+funzionalita.nome+"= #"+tipoAnnullamento+"##"+dataCaricamento+"#"+id
						+" AND "+funzionalita.annullato+" = "+tipoAnnullamento+ " AND "+funzionalita.dtAnnullamento+" = "+dataOggi
						+" WHERE "+funzionalita.dmalmModuloFk01+" = "+id);
			}
			conn.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	public static void checkAnnullamentiElUnitaOrganizzativa(Timestamp dataEsecuzione)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(DmAlmConstants.ELETTRA_ANN_UNITA_ORGANIZZATIVE);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmElUnitaOrganizzative bean=ElettraUnitaOrganizzativeDAO.getBeanFromTuple(rs);
				
				ElettraUnitaOrganizzativeDAO.updateDataFineValidita(dataEsecuzione, bean.getUnitaOrganizzativaPk());
				Integer maxValue = UtilsDAO.getMaxValueByTable(DmAlmConstants.TARGET_ELETTRA_UNITAORGANIZZATIVE, "DMALM_UNITA_ORG_PK")+1;
				bean.setUnitaOrganizzativaPk(maxValue);
				bean.setAnnullato("SI");
				bean.setDataAnnullamento(DateUtils.addSecondsToTimestamp(dataEsecuzione,-1));
				ElettraUnitaOrganizzativeDAO.insertUnitaOrganizzativaUpdate(dataEsecuzione, bean);
			}


			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

	protected static void checkAnnullamentiElPersonale(Timestamp dataEsecuzione) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			conn.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(DmAlmConstants.ELETTRA_ANN_PERSONALE);

			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);
			rs = ps.executeQuery();

			while (rs.next()) {
				
				DmalmElPersonale bean=ElettraPersonaleDAO.getBeanFromTuple(rs);
				ElettraPersonaleDAO.updateDataFineValidita(dataEsecuzione, bean);
				
				bean.setAnnullato("SI");
				Integer maxValue = UtilsDAO.getMaxValueByTable(DmAlmConstants.TARGET_ELETTRA_PERSONALE, "DMALM_PERSONALE_PK")+1;
				bean.setPersonalePk(maxValue);
				bean.setDataAnnullamento(DateUtils.addSecondsToTimestamp(dataEsecuzione,-1));
				ElettraPersonaleDAO.insertPersonaleUpdate(dataEsecuzione,bean);
			}


			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

	
}
