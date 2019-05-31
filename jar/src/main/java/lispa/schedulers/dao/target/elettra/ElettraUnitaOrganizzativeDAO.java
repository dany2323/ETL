package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzative;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzative;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

public class ElettraUnitaOrganizzativeDAO {
	
	private ElettraUnitaOrganizzativeDAO(){}
	private static Logger logger = Logger
			.getLogger(ElettraUnitaOrganizzativeDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElUnitaOrganizzative qDmalmElUnitaOrganizzative = QDmalmElUnitaOrganizzative.qDmalmElUnitaOrganizzative;
	private static QDmalmElUnitaOrganizzativeFlat qDmalmElUnitaOrganizzativeFlat = QDmalmElUnitaOrganizzativeFlat.qDmalmElUnitaOrganizzativeFlat;

	/**
	 * restituisce tutte le unità organizzative inserite alla data di
	 * elaborazione nell'area di staging
	 * 
	 * @param dataEsecuzione
	 * @return
	 * @throws Exception
	 */
	public static List<DmalmElUnitaOrganizzative> getAllUnitaOrganizzative(
			Timestamp dataEsecuzione) throws Exception {
		
		DmalmElUnitaOrganizzative bean = null;
		List<DmalmElUnitaOrganizzative> unita = new ArrayList<>();		
		
			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ELETTRAUNITAORGANIZZATIVE);
			ConnectionManager cm = ConnectionManager.getInstance();
			try( 
					Connection connection = cm.getConnectionOracle();
					PreparedStatement ps = connection.prepareStatement(sql);
					){

				ps.setTimestamp(1, dataEsecuzione);
			try(ResultSet rs = ps.executeQuery();){
				while (rs.next()) {
					bean = new DmalmElUnitaOrganizzative();
	
					bean.setUnitaOrganizzativaPk(rs
							.getInt("DMALM_STG_UNITA_ORG_PK"));
					bean.setIdEdma(rs.getString("ID_EDMA"));
					bean.setCodiceArea(rs.getString("CD_AREA"));
					bean.setDataInizioValiditaEdma(rs
							.getTimestamp("DT_INIZIO_VALIDITA_EDMA"));
					bean.setDataFineValiditaEdma(rs
							.getTimestamp("DT_FINE_VALIDITA_EDMA"));
					bean.setDescrizioneArea(rs.getString("DS_AREA_EDMA"));
					bean.setDataAttivazione(rs.getTimestamp("DT_ATTIVAZIONE"));
					bean.setDataDisattivazione(rs.getTimestamp("DT_DISATTIVAZIONE"));
					bean.setNote(rs.getString("NOTE"));
					bean.setInterno(rs.getShort("INTERNO"));
					bean.setCodiceResponsabile(rs.getString("CD_RESPONSABILE_AREA"));
					bean.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));
					bean.setIdTipologiaUfficio(rs.getInt("ID_TIPOLOGIA_UFFICIO"));
					bean.setIdGradoUfficio(rs.getInt("ID_GRADO_UFFICIO"));
					bean.setIdSede(rs.getInt("ID_SEDE"));
					bean.setCodiceUOSuperiore(rs.getString("CD_UO_SUPERIORE"));
					bean.setDescrizioneUOSuperiore(rs.getString("DS_UO_SUPERIORE"));
					bean.setCodiceEnte(rs.getString("CD_ENTE"));
					bean.setCodiceVisibilita(rs.getString("CD_VISIBILITA"));
					bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
					bean.setAnnullato("NO");
					unita.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} 

		return unita;
	}

	public static List<Tuple> getUnitaOrganizzativa(
			DmalmElUnitaOrganizzative unita) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> strutture = new ArrayList<>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			if (unita.getIdEdma() != null) {
				strutture = query
						.from(qDmalmElUnitaOrganizzative)
						.where(qDmalmElUnitaOrganizzative.codiceArea.eq(unita
								.getCodiceArea()))
						.where(qDmalmElUnitaOrganizzative.dataFineValidita
								.in(new SQLSubQuery()
										.from(qDmalmElUnitaOrganizzative)
										.where(qDmalmElUnitaOrganizzative.codiceArea
												.eq(unita.getCodiceArea()))
										.list(qDmalmElUnitaOrganizzative.dataFineValidita
												.max())))
						.list(qDmalmElUnitaOrganizzative.all());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return strutture;
	}

	public static void insertUnitaOrganizzativa(DmalmElUnitaOrganizzative unita)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElUnitaOrganizzative)
					.columns(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk,
							qDmalmElUnitaOrganizzative.idEdma,
							qDmalmElUnitaOrganizzative.codiceArea,
							qDmalmElUnitaOrganizzative.descrizioneArea,
							qDmalmElUnitaOrganizzative.dataInizioValiditaEdma,
							qDmalmElUnitaOrganizzative.dataFineValiditaEdma,
							qDmalmElUnitaOrganizzative.dataAttivazione,
							qDmalmElUnitaOrganizzative.dataDisattivazione,
							qDmalmElUnitaOrganizzative.note,
							qDmalmElUnitaOrganizzative.interno,
							qDmalmElUnitaOrganizzative.codiceResponsabile,
							qDmalmElUnitaOrganizzative.indirizzoEmail,
							qDmalmElUnitaOrganizzative.idTipologiaUfficio,
							qDmalmElUnitaOrganizzative.idGradoUfficio,
							qDmalmElUnitaOrganizzative.idSede,
							qDmalmElUnitaOrganizzative.codiceUOSuperiore,
							qDmalmElUnitaOrganizzative.descrizioneUOSuperiore,
							qDmalmElUnitaOrganizzative.codiceEnte,
							qDmalmElUnitaOrganizzative.codiceVisibilita,
							qDmalmElUnitaOrganizzative.dataCaricamento,
							qDmalmElUnitaOrganizzative.dataInizioValidita,
							qDmalmElUnitaOrganizzative.dataFineValidita,
							qDmalmElUnitaOrganizzative.annullato,
							qDmalmElUnitaOrganizzative.dataAnnullamento)
					.values(unita.getUnitaOrganizzativaPk(),
							unita.getIdEdma(),
							unita.getCodiceArea(),
							unita.getDescrizioneArea(),
							unita.getDataInizioValiditaEdma(),
							unita.getDataFineValiditaEdma(),
							unita.getDataAttivazione(),
							unita.getDataDisattivazione(),
							unita.getNote(),
							unita.getInterno(),
							unita.getCodiceResponsabile(),
							unita.getIndirizzoEmail(),
							unita.getIdTipologiaUfficio(),
							unita.getIdGradoUfficio(),
							unita.getIdSede(),
							unita.getCodiceUOSuperiore(),
							unita.getDescrizioneUOSuperiore(),
							unita.getCodiceEnte(),
							unita.getCodiceVisibilita(),
							unita.getDataCaricamento(),
							DataEsecuzione.getInstance().getDataEsecuzione(),
							(unita.getDataFineValiditaEdma() != null ? unita
									.getDataFineValiditaEdma() : DateUtils
									.setDtFineValidita9999()), "NO", null)
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("insertUnitaOrganizzativa - unita: "
					+ LogUtils.objectToString(unita));
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDataFineValidita(Timestamp dataelaborazione,
			Integer unitaOrganizzativaPk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk
							.eq(unitaOrganizzativaPk))
					.set(qDmalmElUnitaOrganizzative.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataelaborazione,
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

	public static void insertUnitaOrganizzativaUpdate(Timestamp dataEsecuzione,
			DmalmElUnitaOrganizzative unita) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElUnitaOrganizzative)
					.columns(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk,
							qDmalmElUnitaOrganizzative.idEdma,
							qDmalmElUnitaOrganizzative.codiceArea,
							qDmalmElUnitaOrganizzative.descrizioneArea,
							qDmalmElUnitaOrganizzative.dataInizioValiditaEdma,
							qDmalmElUnitaOrganizzative.dataFineValiditaEdma,
							qDmalmElUnitaOrganizzative.dataAttivazione,
							qDmalmElUnitaOrganizzative.dataDisattivazione,
							qDmalmElUnitaOrganizzative.note,
							qDmalmElUnitaOrganizzative.interno,
							qDmalmElUnitaOrganizzative.codiceResponsabile,
							qDmalmElUnitaOrganizzative.indirizzoEmail,
							qDmalmElUnitaOrganizzative.idTipologiaUfficio,
							qDmalmElUnitaOrganizzative.idGradoUfficio,
							qDmalmElUnitaOrganizzative.idSede,
							qDmalmElUnitaOrganizzative.codiceUOSuperiore,
							qDmalmElUnitaOrganizzative.descrizioneUOSuperiore,
							qDmalmElUnitaOrganizzative.codiceEnte,
							qDmalmElUnitaOrganizzative.codiceVisibilita,
							qDmalmElUnitaOrganizzative.dataCaricamento,
							qDmalmElUnitaOrganizzative.dataInizioValidita,
							qDmalmElUnitaOrganizzative.dataFineValidita,
							qDmalmElUnitaOrganizzative.annullato,
							qDmalmElUnitaOrganizzative.dataAnnullamento)
					.values(unita.getUnitaOrganizzativaPk()==null?StringTemplate
							.create("STG_UNITA_ORGANIZZATIVE_SEQ.nextval"):unita.getUnitaOrganizzativaPk(), unita.getIdEdma(),
							unita.getCodiceArea(), unita.getDescrizioneArea(),
							unita.getDataInizioValiditaEdma(),
							unita.getDataFineValiditaEdma(),
							unita.getDataAttivazione(),
							unita.getDataDisattivazione(), unita.getNote(),
							unita.getInterno(), unita.getCodiceResponsabile(),
							unita.getIndirizzoEmail(),
							unita.getIdTipologiaUfficio(),
							unita.getIdGradoUfficio(), unita.getIdSede(),
							unita.getCodiceUOSuperiore(),
							unita.getDescrizioneUOSuperiore(),
							unita.getCodiceEnte(), unita.getCodiceVisibilita(),
							unita.getDataCaricamento(), dataEsecuzione,
							DateUtils.setDtFineValidita9999(), unita.getAnnullato(), unita.getDataAnnullamento())
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

	public static void updateUnitaOrganizzativa(Integer unitaOrganizzativaPk,
			DmalmElUnitaOrganizzative unita) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk
							.eq(unitaOrganizzativaPk))
					.set(qDmalmElUnitaOrganizzative.codiceArea,
							unita.getCodiceArea())
					.set(qDmalmElUnitaOrganizzative.note, unita.getNote())
					.set(qDmalmElUnitaOrganizzative.idGradoUfficio,
							unita.getIdGradoUfficio())
					.set(qDmalmElUnitaOrganizzative.idSede, unita.getIdSede())
					.set(qDmalmElUnitaOrganizzative.indirizzoEmail,
							unita.getIndirizzoEmail())
					.set(qDmalmElUnitaOrganizzative.codiceVisibilita,
							unita.getCodiceVisibilita())
					.set(qDmalmElUnitaOrganizzative.descrizioneUOSuperiore, unita.getDescrizioneUOSuperiore())
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

	public static Map<Timestamp, Integer> getUnitaOrganizzativaByCodiceArea(String codiceArea,
			Timestamp dataUltimaModifica) throws DAOException {
		HashMap <Timestamp,Integer> result=new HashMap<>();
//		result.put(dataUltimaModifica,0);

		if (codiceArea == null || dataUltimaModifica == null) {
			return result;
		}
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> strutture = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			strutture = query
					.from(qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.codiceArea.eq(codiceArea
							.trim()))
					.where(qDmalmElUnitaOrganizzative.dataInizioValidita
							.loe(dataUltimaModifica))
					.where(qDmalmElUnitaOrganizzative.dataFineValidita
							.goe(dataUltimaModifica))
					.list(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk,qDmalmElUnitaOrganizzative.dataInizioValidita);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		if(!strutture.isEmpty()) {
			result.put(strutture.get(0).get(qDmalmElUnitaOrganizzative.dataInizioValidita),strutture.get(0).get(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk) );
		}
		
		return result;
	}
	
	public static List<DmalmElUnitaOrganizzative> getStartUnitaOrganizzativeFlat() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmElUnitaOrganizzative> resultList = new LinkedList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.codiceArea.eq(DmAlmConstants.ROOT_UO))
					.orderBy(qDmalmElUnitaOrganizzative.dataInizioValidita.asc())
					.list(Projections.bean(DmalmElUnitaOrganizzative.class,
							qDmalmElUnitaOrganizzative.all()));

		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmElUnitaOrganizzative> getNextUnitaOrganizzativeFlat(
			DmalmElUnitaOrganizzative unitaOrganizzativa) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmElUnitaOrganizzative> resultList = new LinkedList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.codiceUOSuperiore.eq(unitaOrganizzativa.getCodiceArea()))
					.where(qDmalmElUnitaOrganizzative.codiceArea.ne(unitaOrganizzativa.getCodiceArea()))
//					.where(qDmalmElUnitaOrganizzative.dataFineValidita.goe(new Timestamp(unitaOrganizzativa.getDataFineValidita().getTime())))
//					.where(qDmalmElUnitaOrganizzative.dataInizioValidita.loe(new Timestamp(unitaOrganizzativa.getDataInizioValidita().getTime())))
					.orderBy(qDmalmElUnitaOrganizzative.codiceArea.asc(), qDmalmElUnitaOrganizzative.dataInizioValidita.asc())
					.list(Projections.bean(DmalmElUnitaOrganizzative.class,
							qDmalmElUnitaOrganizzative.all()));

		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static DmalmElUnitaOrganizzativeFlat getUOFlatByPk(Integer pk) throws DAOException{
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmElUnitaOrganizzativeFlat> resultList = new LinkedList<>();
		if(pk==null) {
			return null;
		}
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(qDmalmElUnitaOrganizzativeFlat)
					.where(qDmalmElUnitaOrganizzativeFlat.idFlatPk.eq(pk))
					.list(Projections.bean(DmalmElUnitaOrganizzativeFlat.class,
							qDmalmElUnitaOrganizzativeFlat.all()));

		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return !resultList.isEmpty()?resultList.get(0):null;
	}

	public static List<DmalmElUnitaOrganizzative> getUnitaOrganizzativaTappo() throws DAOException,
	SQLException { //Aggiunta per DM_ALM-313
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmElUnitaOrganizzative> resultList = new LinkedList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(qDmalmElUnitaOrganizzative)
					.where(qDmalmElUnitaOrganizzative.unitaOrganizzativaPk.eq(0))
					.list(Projections.bean(DmalmElUnitaOrganizzative.class,
							qDmalmElUnitaOrganizzative.all()));

		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

	public static DmalmElUnitaOrganizzative getBeanFromTuple(ResultSet rs) throws SQLException {

		DmalmElUnitaOrganizzative bean=new DmalmElUnitaOrganizzative();
		
		bean.setUnitaOrganizzativaPk(rs
				.getInt("DMALM_UNITA_ORG_PK"));
		bean.setIdEdma(rs.getString("ID_EDMA"));
		bean.setCodiceArea(rs.getString("CD_AREA"));
		bean.setDataInizioValiditaEdma(rs
				.getTimestamp("DT_INIZIO_VALIDITA_EDMA"));
		bean.setDataFineValiditaEdma(rs
				.getTimestamp("DT_FINE_VALIDITA_EDMA"));
		bean.setDescrizioneArea(rs.getString("DS_AREA_EDMA"));
		bean.setDataAttivazione(rs.getTimestamp("DT_ATTIVAZIONE"));
		bean.setDataDisattivazione(rs.getTimestamp("DT_DISATTIVAZIONE"));
		bean.setNote(rs.getString("NOTE"));
		bean.setInterno(rs.getShort("INTERNO"));
		bean.setCodiceResponsabile(rs.getString("CD_RESPONSABILE_AREA"));
		bean.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));
		bean.setIdTipologiaUfficio(rs.getInt("ID_TIPOLOGIA_UFFICIO"));
		bean.setIdGradoUfficio(rs.getInt("ID_GRADO_UFFICIO"));
		bean.setIdSede(rs.getInt("ID_SEDE"));
		bean.setCodiceUOSuperiore(rs.getString("CD_UO_SUPERIORE"));
		bean.setDescrizioneUOSuperiore(rs.getString("DS_UO_SUPERIORE"));
		bean.setCodiceEnte(rs.getString("CD_ENTE"));
		bean.setCodiceVisibilita(rs.getString("CD_VISIBILITA"));
		bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
		bean.setAnnullato(rs.getString("ANNULLATO"));
		bean.setDataFineValidita(rs.getTimestamp("DT_FINE_VALIDITA"));
		
		return bean;
	}

}
