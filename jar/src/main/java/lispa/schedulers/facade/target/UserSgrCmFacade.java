package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.DmalmUser;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.UserSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmUser;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class UserSgrCmFacade {

	private static Logger logger = Logger.getLogger(UserSgrCmFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception, DAOException {	

		List<DmalmUser> staging_users = new ArrayList<DmalmUser>();
		List<Tuple> target_users = new ArrayList<Tuple>();
		QDmalmUser us = QDmalmUser.dmalmUser;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmUser user_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_users  = UserSgrCmDAO.getAllUser(dataEsecuzione);

			for(DmalmUser user : staging_users)
			{   
				user_tmp = user;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_users = UserSgrCmDAO.getUser(user);

				// se non trovo almento un record, inserisco il project nel target
				if(target_users.size()==0)
				{

					righeNuove++;
					UserSgrCmDAO.insertUser(user);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_users)
					{
						// aggiorno la data di fine validita del record corrente

						if(row !=null)
						{

							if(BeanUtils.areDifferent(row.get(us.deleted), user.getDeleted()))
							{
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								UserSgrCmDAO.updateDataFineValidita(dataEsecuzione, user);

								// inserisco un nuovo record
								UserSgrCmDAO.insertUserUpdate(dataEsecuzione, user);
							
							}
							else
							{
								// Aggiorno lo stesso

								UserSgrCmDAO.updateUser(user);
							
							}
						}

					}
				}

			}

		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(user_tmp));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(user_tmp));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo
				(
						dataEsecuzione, 
						stato, 
						new Timestamp(dtInizioCaricamento.getTime()), 
						new Timestamp(dtFineCaricamento.getTime()), 
						DmAlmConstants.TARGET_USER, 
						righeNuove, 
						righeModificate
						);

			} catch (DAOException | SQLException e)
			{
				logger.error(e.getMessage(), e);
				
			}
		}
	}

}
