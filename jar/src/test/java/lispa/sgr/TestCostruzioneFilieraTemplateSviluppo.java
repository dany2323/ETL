package lispa.sgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateSviluppo;

public class TestCostruzioneFilieraTemplateSviluppo extends TestCase {
	
	private Connection connection;
	private ConnectionManager cm;
	
	
	public void testLoadDefects(){
		
		try {

		
			Log4JConfiguration.inizialize();
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();
			QDmalmFilieraTemplateSviluppo filiera = QDmalmFilieraTemplateSviluppo.dmalmFilieraTemplateSviluppo;
	
			
			String defectQuery = "select * from DMALM_DIFETTO_PRODOTTO d "
					+ "where not exists "
					+ "(select 1 from DMALM_TEMPLATE_SVILUPPO t "
					+ "where d.ID_REPOSITORY = t.ID_REPOSITORY "
					+ "and d.URI_DIFETTO_PRODOTTO = t.URI_WI)";
			List<DmalmDifettoProdotto> resultListDefect = new LinkedList<DmalmDifettoProdotto>();
		
			String queryMaxFieliera = "select max(ID_FILIERA) as MAX_ID from DMALM_TEMPLATE_SVILUPPO";
			PreparedStatement ps = connection.prepareStatement(queryMaxFieliera,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			rs.first();
			Integer idFiliera = rs.getInt("MAX_ID");
			
			System.out.println("Eseguo la seguente query: "+defectQuery);
			ps = connection.prepareStatement(defectQuery);
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				DmalmDifettoProdotto defect = new DmalmDifettoProdotto();
				defect.setCdDifetto(rs.getString("CD_DIFETTO"));
				defect.setDmalmDifettoProdottoPk(rs.getInt("DMALM_DIFETTO_PRODOTTO_PK"));
				defect.setIdRepository(rs.getString("ID_REPOSITORY"));
				defect.setUri(rs.getString("URI_DIFETTO_PRODOTTO"));
				defect.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListDefect.add(defect);
			}
			
			for(DmalmDifettoProdotto defect: resultListDefect){			
				System.out.println(defect.getDmalmDifettoProdottoPk());
				if(String.valueOf(defect.getDmalmDifettoProdottoPk()).length()>16){
					System.err.println("ERRORE: "+defect.getDmalmDifettoProdottoPk()+" LUNGHEZZA: "+String.valueOf(defect.getDmalmDifettoProdottoPk()).length());
				}
								
				System.out.println(defect.getCdDifetto());
				if(defect.getCdDifetto().length()>100){
					System.err.println("ERRORE: "+defect.getCdDifetto()+" LUNGHEZZA: "+defect.getCdDifetto().length());
				}
				
				System.out.println(defect.getIdRepository());
				if(defect.getIdRepository().length()>20){
					System.err.println("ERRORE: "+defect.getIdRepository()+" LUNGHEZZA: "+defect.getIdRepository().length());
				}
				
				System.out.println(defect.getUri());
				if(defect.getUri().length()>4000){
					System.err.println("ERRORE: "+defect.getUri()+" LUNGHEZZA: "+defect.getUri().length());
				}
				
				String codDef = getDmalmCodiceProgetto(defect.getDmalmProjectFk02());
				System.out.println(codDef);
				if(codDef.length()>100){
					System.err.println("ERRORE: "+codDef+" LUNGHEZZA: "+codDef.length());
				}
				
				/*new SQLInsertClause(connection, dialect, filiera)
				.columns(filiera.idFiliera,
						filiera.livello,
						filiera.sottoLivello,
						filiera.fkWi, 
						filiera.codiceWi,
						filiera.tipoWi,
						filiera.idRepository,
						filiera.uriWi,
						filiera.codiceProject,
						filiera.ruolo,
						filiera.dataCaricamento)
				.values(++idFiliera, 
						1, 
						1,
						defect.getDmalmDifettoProdottoPk(),
						defect.getCdDifetto(),
						"defect",
						defect.getIdRepository(),
						defect.getUri(),
						getDmalmCodiceProgetto(defect.getDmalmProjectFk02()),
						"",
						DataEsecuzione.getInstance().getDataEsecuzione())
				.execute();*/
			}
		
		
			connection.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String getDmalmCodiceProgetto(Integer dmalmProjectFk02) throws DAOException, SQLException {
		String queryMaxFieliera = "select ID_PROJECT from DMALM_PROJECT where DMALM_PROJECT_PK="+dmalmProjectFk02;
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connection = cm.getConnectionOracle();
		PreparedStatement ps = connection.prepareStatement(queryMaxFieliera,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = ps.executeQuery();
		String codiceProgetto = "";
		
		if(!rs.next()){
			System.out.println("Non è stato trovato nessun ProjectId corrispondente all'ID: "+dmalmProjectFk02);
		} else {
			rs.first();
			codiceProgetto = rs.getString("ID_PROJECT");		
		}
		
		rs.close();
		ps.close();
		cm.closeConnection(connection);
		
		return codiceProgetto;
	}

}
