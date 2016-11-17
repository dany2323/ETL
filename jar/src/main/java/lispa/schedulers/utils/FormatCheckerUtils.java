package lispa.schedulers.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class FormatCheckerUtils {

	
	// Per i project SGR di tipo Sviluppo bisogna effettuare le seguenti operazioni
		// dall'attributo C_NAME va estratta anzitutto la stringa che seguen il carattere "}"
		//Tale stringa deve avere le seguenti caratteristiche:
		// - è obbligatoria
		// - deve iniziare con un trattino
		// - NON devono esistere stringhe duplicate nell'insieme delle stringhe correnti ricavate in questo modo da C_NAME
		
		// Se il corrispondente Codice Identificativo Project Sviluppo SGR_CM - già validato
		// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."
		// allora
		// "nome identificativo project sviluppo SGR_CM deve essere UGUALE al Nome dell'oggetto Oreste corrispondente
		
		public static int checkSISSDescrizioneFormat(Logger logger, Timestamp dataEsecuzione) throws Exception
		{

			ProjectCName cname = null; 
			Vector<String> descrizioni = new Vector<String>();
			
			List<ProjectCName> oresteObjects  = new ArrayList<ProjectCName>();
			List<Tuple> projects 			  = new ArrayList<Tuple>();
			List<Tuple> prodottiOreste 			  = new ArrayList<Tuple>();
			
			int errore =0 ;
			
			QStgProdottiArchitetture stgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			projects = SissCurrentProjectDAO.getAllProjectC_Name(dataEsecuzione);
		
			for(Tuple project : projects)
			{
				cname = new ProjectCName();

				cname.setcName(project.get(0, String.class));
				cname.setPrimaGraffaIndex(Integer.parseInt(project.get(1, String.class)));
				cname.setSecondaGraffaIndex(Integer.parseInt(project.get(2, String.class)));
				cname.setIdentificativo(project.get(3, String.class));
				cname.setDescrizione(project.get(4, String.class));

				if(cname.getSecondaGraffaIndex()==0)
				{
					// Errore: NON esiste parentesi graffa chiusa
					
					errore++;
				
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
							DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT, 
							LogUtils.projectSGRtoString(project), 
							cname.getcName() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED_GRAFFA, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else if(cname.getDescrizione() == null || cname.getDescrizione().equals(""))
				{
					
					// Errore: DESCRIZIONE nulla o vuota
					
					errore++;
				
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
							DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT, 
							LogUtils.projectSGRtoString(project), 
							cname.getcName() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_NULL, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else
				{
					// controllo se DESCRIZIONE è DUPLICATO
					if(cname.getDescrizione()!=null && !cname.getDescrizione().equals("") && cname.getSecondaGraffaIndex()>0)
					{
						//Errore : DESCRIZIONE non inizia con il trattino "-"
						if(!cname.getDescrizione().trim().startsWith("-"))
						{
							errore++;
							
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
									DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT, 
									LogUtils.projectSGRtoString(project), 
									cname.getDescrizione() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
						else
						{
							// Errore: DESCRIZIONE duplicata, già presente nel Vettore delle descrizioni
							if(descrizioni.contains(cname.getIdentificativo()))
							{
								errore++;
								
								ErroriCaricamentoDAO.insert
								(
										DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
										DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT, 
										LogUtils.projectSGRtoString(project),
										cname.getDescrizione() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_DUPLICATO, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
							}
							else
							{
								
								// Se il corrispondente Codice Identificativo Project Sviluppo SGR_CM - già validato
								// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."
								// allora
								// "nome identificativo project sviluppo SGR_CM deve essere UGUALE al Nome dell'oggetto Oreste corrispondente
								
								if(cname.getIdentificativo()!=null && !cname.getIdentificativo().equals(""))
								{
									oresteObjects = SGRProjectUtils.identifyCNameObjects(cname.getIdentificativo());
									
									// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."
									
									if(oresteObjects.size()==1)
									{
										prodottiOreste = ProdottiArchitettureDAO.getProdottoBySiglaProdotto(oresteObjects.get(0).getProdotto(), dataEsecuzione);
										
										for(Tuple row : prodottiOreste)
										{
											
											if( !(row.get(stgProdottiArchitetture.nomeProdotto).trim().toUpperCase())
													.equals(cname.getDescrizione().substring(1).trim().toUpperCase()))
											{
												
												errore++;
												
												ErroriCaricamentoDAO.insert
												(
														DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT, 
														DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT, 
														LogUtils.projectSGRtoString(row), 
														cname.getDescrizione().substring(1).trim().toUpperCase() 
															+ DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_DIVERSO_IN_ORESTE + 
																row.get(stgProdottiArchitetture.nomeProdotto).trim().toUpperCase()+")" , 
														DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
														dataEsecuzione);
											}
										}
									}
								}
								
								// no duplicato, lo aggiungo nel VETTORE delle descrizioni per il
								// controllo successivo
								descrizioni.add(cname.getIdentificativo());
							}
						}
					}
				}
			}
			
			return errore;
		}
		
		
		// Per i project SGR di tipo Sviluppo bisogna effettuare le seguenti operazioni
		// dall'attributo C_NAME va estratta anzitutto la stringa che seguen il carattere "}"
		//Tale stringa deve avere le seguenti caratteristiche:
		// - è obbligatoria
		// - deve iniziare con un trattino
		// - NON devono esistere stringhe duplicate nell'insieme delle stringhe correnti ricavate in questo modo da C_NAME

		// Se il corrispondente Codice Identificativo Project Sviluppo SGR_CM - già validato
		// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."
		// allora
		// "nome identificativo project sviluppo SGR_CM deve essere UGUALE al Nome dell'oggetto Oreste corrispondente

		public static int checkSIREDescrizioneFormat(Logger logger, Timestamp dataEsecuzione) throws Exception
		{

			ProjectCName cname = null; 
			Vector<String> descrizioni = new Vector<String>();

			List<ProjectCName> oresteObjects  = new ArrayList<ProjectCName>();
			List<Tuple> projects 			  = new ArrayList<Tuple>();
			List<Tuple> prodottiOreste 			  = new ArrayList<Tuple>();

			int errore = 0;

			QStgProdottiArchitetture stgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			projects = SireCurrentProjectDAO.getAllProjectC_Name(dataEsecuzione);

			for(Tuple project : projects)
			{
				cname = new ProjectCName();

				cname.setcName(project.get(0, String.class));
				cname.setPrimaGraffaIndex(Integer.parseInt(project.get(1, String.class)));
				cname.setSecondaGraffaIndex(Integer.parseInt(project.get(2, String.class)));
				cname.setIdentificativo(project.get(3, String.class));
				cname.setDescrizione(project.get(4, String.class));

				if(cname.getSecondaGraffaIndex()==0)
				{
					errore++;

					// Errore: NON esiste parentesi graffa chiusa
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT, 
							DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
							LogUtils.projectSGRtoString(project), 
							cname.getcName() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED_GRAFFA, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else if(cname.getDescrizione() == null || cname.getDescrizione().equals(""))
				{
					errore++;

					// Errore: DESCRIZIONE nulla o vuota
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT, 
							DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
							LogUtils.projectSGRtoString(project),  
							cname.getcName() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_NULL, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else
				{
					// controllo se DESCRIZIONE è DUPLICATO
					if(cname.getDescrizione()!=null && !cname.getDescrizione().equals("") && cname.getSecondaGraffaIndex()>0)
					{
						//Errore : DESCRIZIONE non inizia con il trattino "-"
						if(!cname.getDescrizione().trim().startsWith("-"))
						{
							errore++;

							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT, 
									DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
									LogUtils.projectSGRtoString(project),  
									cname.getDescrizione() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_MALFORMED, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
						else
						{
							// Errore: DESCRIZIONE duplicata, già presente nel Vettore delle descrizioni

							if(descrizioni.contains(cname.getIdentificativo()))
							{
								errore++;

								ErroriCaricamentoDAO.insert
								(
										DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT, 
										DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
										LogUtils.projectSGRtoString(project), 
										cname.getDescrizione() + DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_DUPLICATO, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
							}
							else
							{

								// Se il corrispondente Codice Identificativo Project Sviluppo SGR_CM - già validato
								// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."
								// allora
								// "nome identificativo project sviluppo SGR_CM deve essere UGUALE al Nome dell'oggetto Oreste corrispondente

								if(cname.getIdentificativo()!=null && !cname.getIdentificativo().equals(""))
								{
									oresteObjects = SGRProjectUtils.identifyCNameObjects(cname.getIdentificativo());

									// è costituito da un solo oggetto oreste (ossia non contiene nessun ".."

									if(oresteObjects.size()==1)
									{
										prodottiOreste = ProdottiArchitettureDAO.getProdottoBySiglaProdotto(oresteObjects.get(0).getProdotto(), dataEsecuzione);

										for(Tuple row : prodottiOreste)
										{

											if( !(row.get(stgProdottiArchitetture.nomeProdotto).trim().toUpperCase())
													.equals(cname.getDescrizione().substring(1).trim().toUpperCase()))
											{

												//												
												//												
												//												
												//												
												//												

												errore++;

												ErroriCaricamentoDAO.insert
												(
														DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT, 
														DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
														LogUtils.projectSGRtoString(row), 
														cname.getDescrizione().substring(1).trim().toUpperCase() 
														+ DmAlmConstants.SGR_PROJECT_C_NAME_DESCRIZIONE_DIVERSO_IN_ORESTE + 
														row.get(stgProdottiArchitetture.nomeProdotto).trim().toUpperCase()+")" , 
														DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
														dataEsecuzione);
											}
										}
									}
								}

								// no duplicato, lo aggiungo nel VETTORE delle descrizioni per il
								// controllo successivo
								descrizioni.add(cname.getIdentificativo());
							}
						}
					}
				}
			}

			return errore;
		}
	
}
