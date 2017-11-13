package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.DmalmUserElPersonale;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.UserElPersonaleDAO;
import lispa.schedulers.dao.target.UserSgrCmDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmUser;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class UserElPersonaleFacade {

	private static Logger logger = Logger.getLogger(UserElPersonaleFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception, DAOException {	

		List<Tuple> target_users = new ArrayList<Tuple>();
		QDmalmUser us = QDmalmUser.dmalmUser;

		Integer pkElPersonale = null;
		int righeNuove = 0;

		Date dtCaricamento = new Date();
		Date dtFineCaricamento 	 = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try	{
			// elimino tutti i dati presenti nella tabella associativa
			UserElPersonaleDAO.deleteAll();
			
			target_users  = UserSgrCmDAO.getUserDistinctByIdUserUsername(us);

			for(Tuple row : target_users) {
				
				String username = row.get(us.userName).trim();
				int indexBlank = username.indexOf(" ");
				String cognome = "";
				String nome = "";
				if (indexBlank != -1) {
					cognome = username.substring(0, indexBlank).toUpperCase();
					nome = username.substring(indexBlank+1).toUpperCase();
				} else {
					cognome = username;
					nome = username;
				}
				//String[] splitUsername = username.split(" ");
				//String cognome = splitUsername[0].toUpperCase();
				//String nome = splitUsername[1].toUpperCase();
				
				// Ricerco nella tabella DMALM_EL_PERSONALE la PK passando i valori nome e cognome
				pkElPersonale = ElettraPersonaleDAO.getPersonalePkByNomeCognome(nome, cognome);

				// se trovo almento un record, inserisco i dati nella tabella associativa DMALM_USER_EL_PERSONALE
				if(pkElPersonale != 0) {
					DmalmUserElPersonale bean = new DmalmUserElPersonale();
					bean.setIdUser(row.get(us.idUser));
					bean.setDmalmPersonalePk(pkElPersonale);
					bean.setDtCaricamento(new Timestamp(dtCaricamento.getTime()));
					righeNuove++;
					UserElPersonaleDAO.insertUser(bean);
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(target_users));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(target_users));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(
						dataEsecuzione, 
						DmAlmConstants.TARGET_USER_EL_PERSONALE, 
						stato, 
						new Timestamp(dtCaricamento.getTime()), 
						new Timestamp(dtFineCaricamento.getTime()), 
						righeNuove, 
						0, 
						0, 
						0);
			} catch (DAOException | SQLException e)
			{
				logger.error(e.getMessage(), e);
				
			}
		}
	}

}
