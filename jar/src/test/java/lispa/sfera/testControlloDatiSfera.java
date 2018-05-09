package lispa.sfera;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmCleaning;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.cleaning.CheckSferaMisureFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.sfera.CheckLinkSferaSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmAsmProjectEl;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.utils.DateUtils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.h2.util.StringUtils;
import org.junit.Test;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class testControlloDatiSfera extends TestCase {

	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmProject project = QDmalmProject.dmalmProject;
	private static QDmalmAsmProjectEl asmProject = QDmalmAsmProjectEl.dmalmAsmProject;
	private static QDmalmElProdottiArchitetture prodotto = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static SQLTemplates dialect = new HSQLDBTemplates();
	
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaProjectElFacade.class);
	
	public void testControlloDatiSfera() throws Exception {
		
		
		
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			
			Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
					"2018-04-11 20:40:00", "yyyy-MM-dd HH:mm:00");
			
			ConnectionManager cm = null;
			Connection connection = null;
			
			

			
			CheckSferaMisureFacade.checkControlloDatiSfera(logger, dataEsecuzione);
	}
}
