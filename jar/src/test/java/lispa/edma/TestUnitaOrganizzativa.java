package lispa.edma;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmFillTarget;
import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.StrutturaOrganizzativaEdmaFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.fonte.edma.EdmaDmAlmUnitaOrganizzative;
import lispa.schedulers.queryimplementation.staging.edma.QStgUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;

public class TestUnitaOrganizzativa
extends TestCase 
{
	
	Logger logger = Logger.getLogger(DmAlmFillTarget.class); 
	
	public void testAlreadyExecuted() throws Exception {
		
	}
	
	public void testConnectionLISPA() throws Exception {
		UnitaOrganizzativaDAO.fillUnitaOrganizzativa();
	}
	
	public void testStoricizzazione() throws Exception {
//		StrutturaOrganizzativaEdmaFacade.execute( DataEsecuzione.getInstance().getDataEsecuzione());
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-02-12 00:00:00", "yyyy-MM-dd 00:00:00");
		StrutturaOrganizzativaEdmaFacade.execute(dataEsecuzione);
	}

	public void testFillStaging() throws Exception
	{

		try{
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			EdmaDmAlmUnitaOrganizzative qViewUnitaOrganizzative = EdmaDmAlmUnitaOrganizzative.dmAlmUnitaOrganizzative;
			QStgUnitaOrganizzative  qStgUnitaOrganizzative  = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			
			/*
			 * 
			 * VIEW 
			ID
			CODICE
			DATA_INIZIO_VALIDITA
			DATA_FINE_VALIDITA
			DESCRIZIONE
			DATA_ATTIVAZIONE
			DATA_DISATTIVAZIONE
			NOTE
			INTERNO
			COD_RESPONSABILE
			INDIRIZZO_EMAIL
			ID_TIPOLOGIA_UFFICIO
			ID_GRADO_UFFICIO
			ID_SEDE
			COD_SUPERIORE
			COD_ENTE
			COD_VISIBILITA
			TIPO_PERSONA*/

			new SQLInsertClause(connection, dialect, qStgUnitaOrganizzative)
			.columns(
					qStgUnitaOrganizzative.id, 
					qStgUnitaOrganizzative.codice,
					qStgUnitaOrganizzative.dataInizioValidita,
					qStgUnitaOrganizzative.dataFineValidita,
					qStgUnitaOrganizzative.descrizione,
					qStgUnitaOrganizzative.dataAttivazione,
					qStgUnitaOrganizzative.dataDisattivazione,
					qStgUnitaOrganizzative.note,
					qStgUnitaOrganizzative.interno,
					qStgUnitaOrganizzative.codResponsabile,
					qStgUnitaOrganizzative.indirizzoEmail,
					qStgUnitaOrganizzative.idTipologiaUfficio,
					qStgUnitaOrganizzative.idGradoUfficio,
					qStgUnitaOrganizzative.idSede,
					qStgUnitaOrganizzative.codSuperiore,
					qStgUnitaOrganizzative.codEnte,
					qStgUnitaOrganizzative.codVisibilita,
					qStgUnitaOrganizzative.tipoPersona,
					qStgUnitaOrganizzative.dataCaricamento,
					qStgUnitaOrganizzative.dmalmStgUnitaOrgPk
					
					)
		    .select(new SQLSubQuery().from(qViewUnitaOrganizzative).list(
		    		qViewUnitaOrganizzative.id, 
		    		qViewUnitaOrganizzative.codice,
		    		qViewUnitaOrganizzative.dataInizioValidita,
					qViewUnitaOrganizzative.dataFineValidita,
					qViewUnitaOrganizzative.descrizione,
					qViewUnitaOrganizzative.dataAttivazione,
					qViewUnitaOrganizzative.dataDisattivazione,
					qViewUnitaOrganizzative.note,
					qViewUnitaOrganizzative.interno,
					qViewUnitaOrganizzative.codResponsabile,
					qViewUnitaOrganizzative.indirizzoEmail,
					qViewUnitaOrganizzative.idTipologiaUfficio,
					qViewUnitaOrganizzative.idGradoUfficio,
					qViewUnitaOrganizzative.idSede,
					qViewUnitaOrganizzative.codSuperiore,
					qViewUnitaOrganizzative.codEnte,
					qViewUnitaOrganizzative.codVisibilita,
					null,
					StringTemplate.create("TO_CHAR(sysdate, 'DD/MM/YYYY')"),
					StringTemplate.create("STG_UNITA_ORGANIZZATIVE_SEQ.nextval")
					))
		    .execute();
			
			
		}
		catch(Throwable e)
		{
			
		}

	}
	
//	public void testGetIdStrutturaOrganizzativaByCodice()
//	{
//		try 
//		{
//		
//			
//		
//		} catch (DAOException e) {
//			
//			
//		}
//	}
	
	public void testFillUnitaOrganizzativa() throws SQLException
	{
		try 
		{
		
			UnitaOrganizzativaDAO.fillUnitaOrganizzativa();
		
		} catch (DAOException e) {
			
			
		}
	}

}