package lispa.schedulers.dao.target.fatti;
/*
* SonarQube, open source software quality management tool.
* Copyright (C) 2008-2013 SonarSource
* mailto:contact AT sonarsource DOT com
*
* SonarQube is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 3 of the License, or (at your option) any later version.
*
* SonarQube is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program; if not, write to the Free Software Foundation,
* Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class ProgettoEseDAO {

	private static Logger logger = Logger.getLogger(ProgettoEseDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;

	public static List<DmalmProgettoEse> getAllProgettoEse(
			Timestamp dataEsecuzione) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		DmalmProgettoEse bean = null;
		List<DmalmProgettoEse> progetti = new ArrayList<DmalmProgettoEse>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.progettoese));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);
			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGETTO_ESE);
			try(PreparedStatement ps = connection.prepareStatement(sql);){

				ps.setFetchSize(DmAlmConstants.FETCH_SIZE);
	
				ps.setTimestamp(1, dataEsecuzione);
				ps.setTimestamp(2, dataEsecuzione);
	
				try(ResultSet rs = ps.executeQuery();){
	
					logger.debug("Query Eseguita!");
		
					while (rs.next()) {
						// Elabora il risultato
						bean = new DmalmProgettoEse();
		
						bean.setCdProgettoEse(rs.getString("CD_PROGETTO_ESE"));
						bean.setCfCodice(rs.getString("CODICE"));
						bean.setCfDtUltimaSottomissione(rs
								.getTimestamp("DATA_SOTTOMISSIONE"));
						bean.setUri(rs.getString("URI_WI"));
						bean.setStgPk(rs.getString("STG_PROGETTO_ESE_PK"));
						bean.setDescrizioneProgettoEse(rs
								.getString("DESCRIZIONE_PROGETTO_ESE"));
						bean.setDmalmProgettoEsePk(rs.getInt("DMALM_PROGETTO_ESE_PK"));
						bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
						bean.setDmalmStatoWorkitemFk03(rs
								.getInt("DMALM_STATO_WORKITEM_FK_03"));
						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
						bean.setDsAutoreProgettoEse(rs
								.getString("NOME_AUTORE_PROGETTO_ESE"));
						bean.setDtCaricamentoProgettoEse(dataEsecuzione);
						bean.setDtCreazioneProgettoEse(rs
								.getTimestamp("DATA_INSERIMENTO_RECORD"));
						bean.setDtModificaProgettoEse(rs
								.getTimestamp("DATA_MODIFICA_PROGETTO_ESE"));
						bean.setDtRisoluzioneProgettoEse(rs
								.getTimestamp("DATA_RISOLUZIONE_PROGETTO_ESE"));
						bean.setIdAutoreProgettoEse(rs
								.getString("ID_AUTORE_PROGETTO_ESE"));
						bean.setIdRepository(rs.getString("ID_REPOSITORY"));
						bean.setDtScadenzaProgettoEse(rs
								.getTimestamp("DATA_SCADENZA_PROGETTO_ESE"));
						bean.setMotivoRisoluzioneProgEse(rs
								.getString("MOTIVO_RISOLUZIONE_PROGETTOESE"));
						bean.setTitoloProgettoEse(rs.getString("TITOLO_PROGETTO_ESE"));
						//DM_ALM-320
						bean.setSeverity(rs.getString("SEVERITY"));
						bean.setPriority(rs.getString("PRIORITY"));
		
						bean.setTagAlm(rs.getString("TAG_ALM"));
						bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
						progetti.add(bean);
					}
				}
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

		return progetti;
	}

	public static List<Tuple> getProgettoEse(DmalmProgettoEse progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProgettoEse prog = QDmalmProgettoEse.dmalmProgettoEse;

			progetti = query
					.from(prog)
					.where(prog.cdProgettoEse.equalsIgnoreCase(progetto
							.getCdProgettoEse()))
					.where(prog.idRepository.equalsIgnoreCase(progetto
							.getIdRepository()))
					.where(prog.rankStatoProgettoEse.eq(new Double(1)))
					.list(prog.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;

	}

	public static void updateRank(DmalmProgettoEse progetto, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoEse)
					.where(progettoEse.cdProgettoEse.equalsIgnoreCase(progetto
							.getCdProgettoEse()))
					.where(progettoEse.idRepository.equalsIgnoreCase(progetto
							.getIdRepository()))
					.set(progettoEse.rankStatoProgettoEse, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertProgettoEseUpdate(Timestamp dataEsecuzione,
			DmalmProgettoEse progetto, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progettoEse)
					.columns(progettoEse.cdProgettoEse,
							progettoEse.descrizioneProgettoEse,
							progettoEse.dmalmProgettoEsePk,
							progettoEse.dmalmProjectFk02,
							progettoEse.dmalmStatoWorkitemFk03,
							progettoEse.dmalmStrutturaOrgFk01,
							progettoEse.dmalmTempoFk04,
							progettoEse.dsAutoreProgettoEse,
							progettoEse.dtCambioStatoProgettoEse,
							progettoEse.dtCaricamentoProgettoEse,
							progettoEse.dtCreazioneProgettoEse,
							progettoEse.dtModificaProgettoEse,
							progettoEse.dtRisoluzioneProgettoEse,
							progettoEse.dtScadenzaProgettoEse,
							progettoEse.dtStoricizzazione,
							progettoEse.idAutoreProgettoEse,
							progettoEse.idRepository,
							progettoEse.motivoRisoluzioneProgEse,
							progettoEse.rankStatoProgettoEse,
							progettoEse.titoloProgettoEse,
							progettoEse.cfCodice, progettoEse.stgPk,
							progettoEse.cfDtUltimaSottomissione,
							progettoEse.dmalmUserFk06, progettoEse.uri,
							progettoEse.dtAnnullamento, progettoEse.changed,
							progettoEse.annullato,
							progettoEse.severity, progettoEse.priority,
							progettoEse.tsTagAlm,progettoEse.tagAlm)
					.values(progetto.getCdProgettoEse(),
							progetto.getDescrizioneProgettoEse(),
							pkValue == true ? progetto.getDmalmProgettoEsePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							progetto.getDmalmProjectFk02(),
							progetto.getDmalmStatoWorkitemFk03(),
							progetto.getDmalmStrutturaOrgFk01(),
							progetto.getDmalmTempoFk04(),
							progetto.getDsAutoreProgettoEse(),
							progetto.getDtCambioStatoProgettoEse(),
							progetto.getDtCaricamentoProgettoEse(),
							progetto.getDtCreazioneProgettoEse(),
							progetto.getDtModificaProgettoEse(),
							progetto.getDtRisoluzioneProgettoEse(),
							progetto.getDtScadenzaProgettoEse(),
							pkValue == true ? progetto
									.getDtModificaProgettoEse() : progetto
									.getDtStoricizzazione(),
							progetto.getIdAutoreProgettoEse(),
							progetto.getIdRepository(),
							progetto.getMotivoRisoluzioneProgEse(),
							pkValue == true ? new Short("1") : progetto
									.getRankStatoProgettoEse(),
							progetto.getTitoloProgettoEse(),
							progetto.getCfCodice(), progetto.getStgPk(),
							progetto.getCfDtUltimaSottomissione(),
							progetto.getDmalmUserFk06(), progetto.getUri(),
							progetto.getDtAnnullamento(),
							progetto.getChanged(), progetto.getAnnullato(),
							progetto.getSeverity(), progetto.getPriority(),
							progetto.getTsTagAlm(),progetto.getTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertProgettoEse(DmalmProgettoEse progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progettoEse)
					.columns(progettoEse.cdProgettoEse,
							progettoEse.descrizioneProgettoEse,
							progettoEse.dmalmProgettoEsePk,
							progettoEse.dmalmProjectFk02,
							progettoEse.dmalmStatoWorkitemFk03,
							progettoEse.dmalmStrutturaOrgFk01,
							progettoEse.dmalmTempoFk04,
							progettoEse.dsAutoreProgettoEse,
							progettoEse.dtCambioStatoProgettoEse,
							progettoEse.dtCaricamentoProgettoEse,
							progettoEse.dtCreazioneProgettoEse,
							progettoEse.dtModificaProgettoEse,
							progettoEse.dtRisoluzioneProgettoEse,
							progettoEse.dtScadenzaProgettoEse,
							progettoEse.dtStoricizzazione,
							progettoEse.idAutoreProgettoEse,
							progettoEse.idRepository,
							progettoEse.motivoRisoluzioneProgEse,
							progettoEse.rankStatoProgettoEse,
							progettoEse.titoloProgettoEse,
							progettoEse.cfCodice, progettoEse.stgPk,
							progettoEse.cfDtUltimaSottomissione,
							progettoEse.dmalmUserFk06, progettoEse.uri,
							progettoEse.dtAnnullamento,
							progettoEse.severity, progettoEse.priority,
							progettoEse.tagAlm,	progettoEse.tsTagAlm)
					.values(progetto.getCdProgettoEse(),
							progetto.getDescrizioneProgettoEse(),
							progetto.getDmalmProgettoEsePk(),
							progetto.getDmalmProjectFk02(),
							progetto.getDmalmStatoWorkitemFk03(),
							progetto.getDmalmStrutturaOrgFk01(),
							progetto.getDmalmTempoFk04(),
							progetto.getDsAutoreProgettoEse(),
							progetto.getDtCambioStatoProgettoEse(),
							progetto.getDtCaricamentoProgettoEse(),
							progetto.getDtCreazioneProgettoEse(),
							progetto.getDtModificaProgettoEse(),
							progetto.getDtRisoluzioneProgettoEse(),
							progetto.getDtScadenzaProgettoEse(),
							progetto.getDtModificaProgettoEse(),
							progetto.getIdAutoreProgettoEse(),
							progetto.getIdRepository(),
							progetto.getMotivoRisoluzioneProgEse(),
							new Double(1), progetto.getTitoloProgettoEse(),
							progetto.getCfCodice(), progetto.getStgPk(),
							progetto.getCfDtUltimaSottomissione(),
							progetto.getDmalmUserFk06(), progetto.getUri(),
							progetto.getDtAnnullamento(),
							progetto.getSeverity(), progetto.getPriority(),
							progetto.getTagAlm(), progetto.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateProgettoEse(DmalmProgettoEse progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoEse)
					.where(progettoEse.cdProgettoEse.equalsIgnoreCase(progetto
							.getCdProgettoEse()))
					.where(progettoEse.idRepository.equalsIgnoreCase(progetto
							.getIdRepository()))
					.where(progettoEse.rankStatoProgettoEse.eq(new Double(1)))
					.set(progettoEse.cdProgettoEse, progetto.getCdProgettoEse())
					.set(progettoEse.descrizioneProgettoEse,
							progetto.getDescrizioneProgettoEse())
					.set(progettoEse.dmalmProjectFk02,
							progetto.getDmalmProjectFk02())
					.set(progettoEse.dmalmStatoWorkitemFk03,
							progetto.getDmalmStatoWorkitemFk03())
					.set(progettoEse.dmalmStrutturaOrgFk01,
							progetto.getDmalmStrutturaOrgFk01())
					.set(progettoEse.dmalmTempoFk04,
							progetto.getDmalmTempoFk04())
					.set(progettoEse.dsAutoreProgettoEse,
							progetto.getDsAutoreProgettoEse())
					.set(progettoEse.dtCreazioneProgettoEse,
							progetto.getDtCreazioneProgettoEse())
					.set(progettoEse.dtModificaProgettoEse,
							progetto.getDtModificaProgettoEse())
					.set(progettoEse.dtRisoluzioneProgettoEse,
							progetto.getDtRisoluzioneProgettoEse())
					.set(progettoEse.dtScadenzaProgettoEse,
							progetto.getDtScadenzaProgettoEse())
					.set(progettoEse.idAutoreProgettoEse,
							progetto.getIdAutoreProgettoEse())
					.set(progettoEse.idRepository, progetto.getIdRepository())
					.set(progettoEse.motivoRisoluzioneProgEse,
							progetto.getMotivoRisoluzioneProgEse())
					.set(progettoEse.rankStatoProgettoEse, new Double(1))
					.set(progettoEse.titoloProgettoEse,
							progetto.getTitoloProgettoEse())
					.set(progettoEse.cfCodice, progetto.getCfCodice())
					.set(progettoEse.stgPk, progetto.getStgPk())
					.set(progettoEse.uri, progetto.getUri())
					.set(progettoEse.cfDtUltimaSottomissione,
							progetto.getCfDtUltimaSottomissione())
					.set(progettoEse.dtAnnullamento,
							progetto.getDtAnnullamento())
					.set(progettoEse.annullato, progetto.getAnnullato())
					.set(progettoEse.severity, progetto.getSeverity())
					.set(progettoEse.priority, progetto.getPriority())
					.set(progettoEse.tagAlm, progetto.getTagAlm())
					.set(progettoEse.tsTagAlm, progetto.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmProgettoEse getProgettoEse(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProgettoEse prog = QDmalmProgettoEse.dmalmProgettoEse;

			progetti = query.from(prog).where(prog.dmalmProgettoEsePk.eq(pk))
					.list(prog.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (progetti.size() > 0) {
			Tuple t = progetti.get(0);
			DmalmProgettoEse p = new DmalmProgettoEse();

			p.setAnnullato(t.get(progettoEse.annullato));
			p.setCdProgettoEse(t.get(progettoEse.cdProgettoEse));
			p.setCfCodice(t.get(progettoEse.cfCodice));
			p.setCfDtUltimaSottomissione(t
					.get(progettoEse.cfDtUltimaSottomissione));
			p.setChanged(t.get(progettoEse.changed));
			p.setDescrizioneProgettoEse(t
					.get(progettoEse.descrizioneProgettoEse));
			p.setDmalmProgettoEsePk(t.get(progettoEse.dmalmProgettoEsePk));
			p.setDmalmProjectFk02(t.get(progettoEse.dmalmProjectFk02));
			p.setDmalmStatoWorkitemFk03(t
					.get(progettoEse.dmalmStatoWorkitemFk03));
			p.setDmalmStrutturaOrgFk01(t
					.get(progettoEse.dmalmStatoWorkitemFk03));
			p.setDmalmTempoFk04(t.get(progettoEse.dmalmTempoFk04));
			p.setDmalmUserFk06(t.get(progettoEse.dmalmUserFk06));
			p.setDsAutoreProgettoEse(t.get(progettoEse.dsAutoreProgettoEse));
			p.setDtAnnullamento(t.get(progettoEse.dtAnnullamento));
			p.setDtCambioStatoProgettoEse(t
					.get(progettoEse.dtCambioStatoProgettoEse));
			p.setDtCaricamentoProgettoEse(t
					.get(progettoEse.dtCaricamentoProgettoEse));
			p.setDtCreazioneProgettoEse(t
					.get(progettoEse.dtCreazioneProgettoEse));
			p.setDtModificaProgettoEse(t.get(progettoEse.dtModificaProgettoEse));
			p.setDtRisoluzioneProgettoEse(t
					.get(progettoEse.dtRisoluzioneProgettoEse));
			p.setDtScadenzaProgettoEse(t.get(progettoEse.dtScadenzaProgettoEse));
			p.setDtStoricizzazione(t.get(progettoEse.dtStoricizzazione));
			p.setIdAutoreProgettoEse(t.get(progettoEse.idAutoreProgettoEse));
			p.setIdRepository(t.get(progettoEse.idRepository));
			p.setMotivoRisoluzioneProgEse(t
					.get(progettoEse.motivoRisoluzioneProgEse));
			p.setRankStatoProgettoEse(t.get(progettoEse.rankStatoProgettoEse));
			p.setRankStatoProgettoEseMese(t
					.get(progettoEse.rankStatoProgettoEseMese));
			p.setStgPk(t.get(progettoEse.stgPk));
			p.setTitoloProgettoEse(t.get(progettoEse.titoloProgettoEse));
			p.setUri(t.get(progettoEse.uri));
			//DM_ALM-320
			p.setSeverity(t.get(progettoEse.severity));
			p.setPriority(t.get(progettoEse.priority));
			p.setTagAlm(t.get(progettoEse.tagAlm));
			p.setTsTagAlm(t.get(progettoEse.tsTagAlm));
			return p;

		} else
			return null;
	}

	public static boolean checkEsistenzaProgetto(DmalmProgettoEse d,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoEse)
					.where(progettoEse.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(progettoEse.cdProgettoEse.eq(d.getCdProgettoEse()))
					.where(progettoEse.idRepository.eq(d.getIdRepository()))
					.list(progettoEse.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
