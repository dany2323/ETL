package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.LinkedWorkitemsFacade;
import lispa.schedulers.manager.DataEsecuzione;

public class TestLinkedWorkitems extends TestCase {

	public void testFillLinkedWorkitems() throws Exception {

		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.getDataEsecuzione();

		LinkedWorkitemsFacade.execute(dataEsecuzione);
	}
}
