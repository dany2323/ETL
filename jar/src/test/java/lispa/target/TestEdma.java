package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.PersonaleEdmaFacade;
import lispa.schedulers.facade.target.StrutturaOrganizzativaEdmaFacade;
import lispa.schedulers.utils.DateUtils;

public class TestEdma
extends TestCase 
{
	

	
	public void testFillPersonale() throws Exception 
	{
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-17 00:00:00", "yyyy-MM-dd 00:00:00");
		
		PersonaleEdmaFacade.execute( dataEsecuzione);		
	}
	
	public void testFillUnitaOrganizzativa() throws Exception 
	{
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-01-22 00:00:00", "yyyy-MM-dd 00:00:00");
		
		StrutturaOrganizzativaEdmaFacade.execute( dataEsecuzione);		
	}
	
}