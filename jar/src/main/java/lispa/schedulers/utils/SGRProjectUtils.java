package lispa.schedulers.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;

public class SGRProjectUtils {

	private static Logger logger = Logger.getLogger(SGRProjectUtils.class); 

	public static List<ProjectCName> identifyCNameObjects(String c_name)
	{

		//		esempio A.B..X.C.D..F

		//		A..B.C..Z.X.Y 
		//		Si legge che Codice Identificativo Project Sviluppo SGR_CM Ã¨ composto (unione) 
		//		da 3 distinti oggetti Oreste: 
		//			
		//		- un Prodotto /Arch Appl (A) 
		//		- un Modulo (C) di un Prodotto / Arch Appl (B) 
		//		- una FunzionalitÃ  (Y) di un Modulo (X) di un Prodotto / Arch Appl (Z)

		List<String> object = null;
		List<ProjectCName> projectObject = new ArrayList<ProjectCName>();

		ProjectCName cname = null;

		c_name = c_name.replace("..", "*");

		try
		{
			StringTokenizer st = new StringTokenizer(c_name, "*");
			StringTokenizer sub  = null;

			while (st.hasMoreElements()) 
			{
				sub= new StringTokenizer(st.nextElement().toString(), ".");

				object = new ArrayList<String>();
				while (sub.hasMoreElements())
				{
					object.add(sub.nextElement().toString());
				}

				if(object.size()>0)
				{
					cname = new ProjectCName();

					if(object.size()==1)
					{
						// esiste solo il prodotto
						cname.setProdotto(object.get(0));
						cname.setModulo(null);
						cname.setFunzionalita(null);
					}
					if(object.size()==2)
					{
						// esiste il prodotto e il modulo
						cname.setProdotto(object.get(0));
						cname.setModulo(object.get(1));
						cname.setFunzionalita(null);
					}
					if(object.size()==3)
					{
						// esiste il prodotto, il modulo e la funzionalita
						cname.setProdotto(object.get(0));
						cname.setModulo(object.get(1));
						cname.setFunzionalita(object.get(2));
					}

					projectObject.add(cname);
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}

		return projectObject;
	}
	
	public static int checkProdottoInOreste(String repository, ProjectCName el, QStgProdottiArchitetture  qstgProdottiArchitetture, Tuple row, String identificativo, Logger logger, Timestamp dataEsecuzione) throws Exception
	{
		boolean isSIRE = false;
		
		int errori = 0 ;
		
		if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SIRE))
		{
			isSIRE = true;
		}
		else if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SISS))
		{
			isSIRE = false;
		}
		else
		{
			throw new Exception("Impossibile identificare un repository! ");
		}
		
		List<Tuple> prodotti;

		//		A..B.C..Z.X.Y
		//		I Codici devono essere verificati come SIGLE di item correnti 
		//		e non scaduti di entitÃ  â€œProdotto / Arch Appl Oresteâ€�: esistenti, NON 
		//		ANNULLATI LOGICAMENTE 

			
			prodotti = ProdottiArchitettureDAO.getProdottoBySiglaProdotto(el.getProdotto(), dataEsecuzione);

			// controllo il prodotto in Oreste, se non esiste, Errore!
			if(prodotti!=null && prodotti.size()==0)
			{
				errori++;
				
				ErroriCaricamentoDAO.insert
				(
						isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
						isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						identificativo + DmAlmConstants.SGR_PROJECT_PRODOTTO_NOT_FOUND_ORESTE, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione
						);
			}
			else
			{
				for(Tuple prodotto : prodotti)
				{
					// se annullato, Errore!
					if(prodotto.get(qstgProdottiArchitetture.nomeProdotto).toLowerCase().contains("ANNULLATO".toLowerCase()))
					{
						
						errori++;
						
						ErroriCaricamentoDAO.insert
						(
								isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
								isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
								LogUtils.projectSGRtoString(row),
								el.getProdotto() + DmAlmConstants.SGR_PROJECT_PRODOTTO_ANNULLATO_ORESTE, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione
								);
					}
				}
			}
				
		return errori;
	}
	
//	A..B.C..Z.X.Y
	//	I Codici â€œCâ€� e â€œXâ€� devono essere verificati come SIGLE di item correnti e 
	//	non scaduti di entitÃ  â€œModulo Prodotto / Arch Appl Oresteâ€�: esistenti, NON 
	//	ANNULLATI LOGICAMENTE, e figli rispettivamente del Prodotto / Arch 
	//	Appl B e del Prodotto / Arch Appl Z

	public static int checkModuloInOreste( String repository, ProjectCName el, QStgModuli  qstgModuli, Tuple row, String identificativo, Logger logger, Timestamp dataEsecuzione) throws Exception
	{
		List<Tuple> moduli = new ArrayList<Tuple>();
		
		boolean isSIRE = false;
		
		int errore = 0;
		
		if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SIRE))
		{
			isSIRE = true;
		}
		else if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SISS))
		{
			isSIRE = false;
		}
		else
		{
			throw new Exception("Impossibile identificare un repository! ");
		}

			moduli = ModuliDAO.getModuliByProdotto(qstgModuli, el.getProdotto(), dataEsecuzione);

			// controllo il prodotto in Oreste, se non esiste, Errore!
			if(moduli!=null && moduli.size()==0)
			{
				errore++;
				
				ErroriCaricamentoDAO.insert
				(
						isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
						isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row), 
						identificativo + DmAlmConstants.SGR_PROJECT_MODULO_PRODOTTO_NOTFOUND_ORESTE, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione
						);
			}
			else
			{
				for(Tuple modulo : moduli)
				{
					// se annullato, Errore!
					if(modulo.get(qstgModuli.nomeModulo).toLowerCase().contains("ANNULLATO".toLowerCase())){

						errore++;
						
						ErroriCaricamentoDAO.insert
						(
								isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
								isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
								LogUtils.projectSGRtoString(row),
								el.getModulo() + DmAlmConstants.SGR_PROJECT_MODULO_ANNULLATO_ORESTE, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione
								);
					}
				}
			}
		
		return errore;
	}

	//	 A..B.C..Z.X.Y
	//	 Il Codice â€œYâ€� deve essere verificato come SIGLA di item corrente e non 
	//	 scaduto di entitÃ  â€œFunzionalitÃ  di Prodotto / ARCH Appl Oresteâ€�: esistente, 
	//	 NON ANNULLATO LOGICAMENTE, e figlio del Prodotto / Arch Appl Z, 
	//	 Modulo X

	public static int checkFunzionalitaInOreste( String repository, ProjectCName el, QStgFunzionalita  qstgFunzionalita, Tuple row, String identificativo, Logger logger, Timestamp dataEsecuzione) throws Exception
	{
		List<Tuple> funzionalita = new ArrayList<Tuple>();
		
		boolean isSIRE = false;
		
		int errore = 0;
		
		if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SIRE))
		{
			isSIRE = true;
		}
		else if(repository!= null && repository.equals(DmAlmConstants.REPOSITORY_SISS))
		{
			isSIRE = false;
		}
		else
		{
			throw new Exception("Impossibile identificare un repository! ");
		}

			funzionalita = FunzionalitaDAO.getFunzionalitaByProdottoModulo(el.getProdotto(), el.getModulo(), dataEsecuzione);

			// controllo la funzionalita, associata al modulo e al prodotto in Oreste, se non esiste: Errore!
			if(funzionalita!=null && funzionalita.size()==0)
			{
				errore ++;
				
				ErroriCaricamentoDAO.insert
				(
						isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
						isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row), 
						identificativo + DmAlmConstants.SGR_PROJECT_FUNZIONALITA_MODULO_PRODOTTO_NOTFOUND_ORESTE, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione
						);
			}
			else
			{
				for(Tuple funz : funzionalita)
				{
					// se annullato, Errore!
					if(funz.get(qstgFunzionalita.nomeFunzionalita).toLowerCase().contains("ANNULLATO".toLowerCase())){
						
						errore++;
						
						ErroriCaricamentoDAO.insert
						(
								isSIRE ? DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
								isSIRE ? DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT : DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
								LogUtils.projectSGRtoString(row), 
								el.getFunzionalita()+ DmAlmConstants.SGR_PROJECT_FUNZIONALITA_ANNULLATO_ORESTE, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione
								);
					}
				}
			}
		
		return errore;
	}
	
	
}
