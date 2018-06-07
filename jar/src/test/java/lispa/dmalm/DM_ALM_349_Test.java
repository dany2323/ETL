package lispa.dmalm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.Test;

import com.mysema.query.Tuple;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryCfWorkitem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;

public class DM_ALM_349_Test  extends TestCase {
	
	private static QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;
	private static QDmalmReleaseIt releaseIt = QDmalmReleaseIt.dmalmReleaseIt;

	@Test
	public void testCaricamentoNuoviCampiInStaging() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		
		try {
			Log4JConfiguration.inizialize();
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			OracleTemplates dialect = new OracleTemplates();
			
			SQLQuery query = new SQLQuery(connection, dialect);
	
			List<Tuple> cfs1 = query
					.from(stgCFWorkItems)
					.where(stgCFWorkItems.cName.equalsIgnoreCase("motivo_sospensione"))
					.list(stgCFWorkItems.all());			
			List<Tuple> cfs2 = query
					.from(stgCFWorkItems)
					.where(stgCFWorkItems.cName.equalsIgnoreCase("counter_qf"))
					.list(stgCFWorkItems.all());			
			List<Tuple> cfs3 = query
					.from(stgCFWorkItems)
					.where(stgCFWorkItems.cName.equalsIgnoreCase("giorni_qf"))
					.list(stgCFWorkItems.all());
			
			assertEquals(cfs1.size()>0, true);	
			assertEquals(cfs2.size()>0, true);	
			assertEquals(cfs3.size()>0, true);	
		
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	@Test
	//Testa che i campi della release it siano stati caricati
	public void testCaricamentoNuoviCampiInTarget() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		
		try {
			Log4JConfiguration.inizialize();
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			OracleTemplates dialect = new OracleTemplates();
			
			SQLQuery query = new SQLQuery(connection, dialect);
	
			List<Tuple> cfs1 = query
					.from(releaseIt)
					.where(releaseIt.motivoSospensione.isNotNull())
					.list(releaseIt.all());
			
			List<Tuple> cfs2 = query
					.from(releaseIt)
					.where(releaseIt.counterQf.isNotNull())
					.list(releaseIt.all());
			
			List<Tuple> cfs3 = query
					.from(releaseIt)
					.where(releaseIt.giorniQf.isNotNull())
					.list(releaseIt.all());
			
			assertEquals(cfs1.size()>0, true);	
			assertEquals(cfs2.size()>0, true);	
			assertEquals(cfs3.size()>0, true);	

		
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}		
	}
	
	@Test
	public void testStoricizzazioneCampoMotivoSospensione() throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Log4JConfiguration.inizialize();
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();	
			
			// Calcolo ultimo valore del campo, quello dopo la storicizzazione
			String sqlUltimoValoreCampo = 
					  "select MOTIVO_SOSPENSIONE "
					+ "from DMALM_RELEASE_IT "
					+ "where DT_STORICIZZAZIONE = ("
						+ "select max(t.DT_STORICIZZAZIONE) "
						+ "from DMALM_RELEASE_IT t)";
			ps = connection.prepareStatement(sqlUltimoValoreCampo);
			rs = ps.executeQuery();
			
			rs.next();
			String ultimoValoreCampo = rs.getString("MOTIVO_SOSPENSIONE");

			// Calcolo penultimo valore del campo, quello prima della storicizzazione
			String sqlPenultimoValoreCampo = 
					"SELECT t1.MOTIVO_SOSPENSIONE " + 
					"FROM " + 
					"  (SELECT t2.MOTIVO_SOSPENSIONE, rownum rnum " + 
					"  FROM " + 
					"    (SELECT t3.MOTIVO_SOSPENSIONE " + 
					"    FROM DMALM_RELEASE_IT t3 " + 
					"    WHERE t3.CD_RELEASE_IT='IT-ANAG-1575' " + 
					"    ORDER BY t3.DT_STORICIZZAZIONE DESC) t2 " + 
					"  WHERE rownum<=2) t1 " + 
					"WHERE t1.rnum=2";
					
				
			ps = connection.prepareStatement(sqlPenultimoValoreCampo);
			rs = ps.executeQuery();
			
			rs.next();
			String penultimoValoreCampo = rs.getString("MOTIVO_SOSPENSIONE");
			
			assertEquals(
					((penultimoValoreCampo!=null && ultimoValoreCampo==null) || (penultimoValoreCampo==null && ultimoValoreCampo!=null)) 
					||
					(penultimoValoreCampo!=null && ultimoValoreCampo!=null && !penultimoValoreCampo.equals(ultimoValoreCampo)), 
					true);	

		} catch (Exception e) {
			System.out.println(e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}		
	}
	

}
