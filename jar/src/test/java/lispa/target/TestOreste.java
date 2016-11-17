package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.facade.target.AmbienteTecnologicoFacade;
import lispa.schedulers.facade.target.ClassificatoriFacade;
import lispa.schedulers.facade.target.FunzionalitaFacade;
import lispa.schedulers.facade.target.ModuloFacade;
import lispa.schedulers.facade.target.ProdottoFacade;
import lispa.schedulers.facade.target.RelClassificatoriOresteFacade;
import lispa.schedulers.facade.target.SottosistemaFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.utils.DateUtils;

public class TestOreste
extends TestCase 
{ 
	
	
	public void testDeleteOreste() throws Exception {
		
		
		
	}
	
	public void testFillTargetProdotto() throws Exception 
	{
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2015-03-14 00:00:00", "yyyy-MM-dd HH:mm:00"));

		Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
		try
		{
			ProdottiArchitettureDAO.fillProdottiArchitetture();
		}
		catch(Exception e)
		{
			
		}
		ProdottoFacade.execute( dataEsecuzione);		
	
	}
	
	public void testFillTargetModulo() throws Exception 
	{
	
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-24 00:00:00", "yyyy-MM-dd 00:00:00");

		ModuloFacade.execute( dataEsecuzione);	
	
	}
	
	public void testFillTargetSottosistema() throws Exception 
	{
	
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-24 00:00:00", "yyyy-MM-dd 00:00:00");
		
		SottosistemaFacade.execute( dataEsecuzione);		
	
	}
	
	public void testFillTargetFunzionalita() throws Exception 
	{
	
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-24 00:00:00", "yyyy-MM-dd 00:00:00");
		
		FunzionalitaFacade.execute( dataEsecuzione);		
	
	}
	
	public void testFillTargetAmbienteTecnologico() throws Exception 
	{
	
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-28 00:00:00", "yyyy-MM-dd 00:00:00");
		
		AmbienteTecnologicoFacade.execute( dataEsecuzione);		
	
	}
	
	public void testFillTargetClassificatori() throws Exception 
	{
	
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-24 00:00:00", "yyyy-MM-dd 00:00:00");
		
		ClassificatoriFacade.execute( dataEsecuzione);
	
	}
	
	public void testFillTargetOreste() throws Exception 
	{
		
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-24 00:00:00", "yyyy-MM-dd 00:00:00");
		
		
		
		ProdottoFacade.execute( dataEsecuzione);		
		
		ModuloFacade.execute( dataEsecuzione);	
		
		SottosistemaFacade.execute( dataEsecuzione);	
		
		FunzionalitaFacade.execute( dataEsecuzione);		
		
		AmbienteTecnologicoFacade.execute( dataEsecuzione);
		
		ClassificatoriFacade.execute(dataEsecuzione);
		
		
		
	}
	public void testFillRelOreste() throws Exception 
	{
		System.out.println(DataEsecuzione.getInstance().getDataEsecuzione().toString());
		System.out.println(QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_REL_AMBIENTITECNOLOGICI_CLASSIFICATORI).replace("?", "TIMESTAMP '"+DataEsecuzione.getInstance().getDataEsecuzione().toString()+"'"));
		//AmbienteTecnologicoFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
		RelClassificatoriOresteFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
	}

	
}