package lispa.elettra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmAsmProject;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.dao.target.DmAlmProjectProdottoDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.CheckDmalmSourceElProdFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.utils.DateUtils;

public class TestEccezioniProdotti extends TestCase{
	
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public void testEccezioniProdotti() throws Exception {
		
		
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			//Test Validazione Tabella
			CheckDmalmSourceElProdFacade.execute(dataEsecuzione);
			
			//Test Compare fra progetti

			dataEsecuzione=DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2017-06-16 04:30:00.0","yyyy-MM-dd HH:mm:00"));

			List<DmalmElProdottiArchitetture> projects = ElettraProdottiArchitettureDAO.getAllProdotti(dataEsecuzione);

			
			for(DmalmElProdottiArchitetture project:projects)
			{
				String sigla=project.getIdProdottoEdma();
				List<Tuple> prodotti = new ArrayList<Tuple>();
				List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(sigla);
				if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
					if (sigla.contains(".")) {
						sigla = sigla.substring(0, sigla.indexOf("."));
					}
				}
			}
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
