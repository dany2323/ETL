package lispa.sgr.util;

import java.sql.SQLException;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

public class TestRegex extends TestCase {
	private static Logger logger = Logger.getLogger(TestRegex.class);

	public static void testSplitString() {
		String stringa = "Invalid coluemN index";
		logger.debug(StringUtils.findRegex(stringa,
				DmalmRegex.REGEXINVALIDCINDEX));

	}

	public static void testGetIndex() {
		String[] c_locationToArray;
		String target;
		String path_marker = ".polarion";
		int target_index = 0;
		String[] array = {
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A592.Funzione.di.Staff.Governo.S.I.e.Centro.di.Competenza/12S89/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A592.Funzione.di.Staff.Governo.S.I.e.Centro.di.Competenza/12S89/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A592.Funzione.di.Staff.Governo.S.I.e.Centro.di.Competenza/12S89/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52E.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52E.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52E.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A592.DM/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMFP/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMFP/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMFP/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMFP/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A541.DM/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMVARIE/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMVARIE/.polarion/polarion-project.xml",
				"default:/Demand/STAFF/A304.Area.Di.Staff.ALM.Demand/ALMVARIE/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09246/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09993T/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09993T/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09988/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09641/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09641/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09988/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A541.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A541.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A541.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A526.Area.Progetti.di.Innovazione.Finanziati.UE.e.Nazionali/09245/.polarion/polarion-project.xml",
				"default:/Demand/DGE/A803.DM/.polarion/polarion-project.xml",
				"default:/Demand/DGE/A803.DM/.polarion/polarion-project.xml",
				"default:/Demand/DGE/A803.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/12S72/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/12S72/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/12S72/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/11983/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/11983/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A527.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A527.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A526.Area.Progetti.di.Innovazione.Finanziati.UE.e.Nazionali/09245/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09130/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09130/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09130/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A592.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A592.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12328/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12328/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A527.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09156/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09246/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12476/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12476/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12476/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09156/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12328/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SV2/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SV2/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SV2/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SV2/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A540.Funzione.Service.Management.Regione.Prenotazione.e.Pagamenti/09003F/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A540.Funzione.Service.Management.Regione.Prenotazione.e.Pagamenti/10099/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09088/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09087/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09820/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09277/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09133/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09993V/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09045/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09058/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12316/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12316/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12316/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12S84/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12S84/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A542.Area.Service.Management.Servizi.di.Prenotazione/12S84/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/10197/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09074/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09992/.polarion/polarion-project.xml",
				"default:/Demand/DGE/A803.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A527.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52E.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A592.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A541.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09073/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/12S70/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/12S70/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/12S70/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/10079/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/10080/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09003V/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A521.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S54/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S54/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A523.Funzione.Service.Management.Regione.Sistemi.Clinici/09003V/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09820/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12411/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12333/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12310/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12311/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A550.Funzione.Servizi.Trasversali.e.di.Supporto.Territorio/10201H/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A550.Funzione.Servizi.Trasversali.e.di.Supporto.Territorio/09184/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S62/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09003U/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12313/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12M58/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09156/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09246/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12334/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A524.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A524.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S62/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S62/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S64/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S64/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S64/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09174/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09127/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A524.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A524.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09133/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A524.Area.Economico.Finanziaria/09277/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S61/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S61/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52B.Area.Service.Management.Prevenzione/12S61/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09051/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12413/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12467/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/09181/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/INT12ANAG/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12415/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09975/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A522.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A522.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A522.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12413/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12413/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A522.Area.Service.Management.Anagrafe.Regionale.e.Sistemi.Amministrativi/12413/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/12S55/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52B.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52A.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A593.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52C.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52C.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12476/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S64/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09003T/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/09003T/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/10129/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/10129/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S97/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52C.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S98/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A52C.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/10111/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A521.Area.Service.Management.flussi.farmaceutica.datawarehouse/10111/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A955.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A955.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A955.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S94/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S94/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S94/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S95/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S95/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S97/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A596.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A596.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A596.DM/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S97/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S97/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S66/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S99/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S98/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S98/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52C.Area.Service.Management.Veterinaria/12S98/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SZ1/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SZ1/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12SZ1/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S99/.polarion/polarion-project.xml",
				"default:/Demand/SISS/A500.Direzione.SISS.Socio.Sanita.e.CRS.SISS/A525.Funzione.Service.Management.Regione.Famiglia.Prevenzione.e.Veterinaria/A52A.Area.Service.Management.Famiglia/12S99/.polarion/polarion-project.xml" };
		for (String s : array) {
			c_locationToArray = StringUtils.splitByRegex(s, "\\/");
			target_index = StringUtils.getCloserIndex(c_locationToArray,
					path_marker, DmalmRegex.REGEXPATTERNDEMAND);
			target = c_locationToArray[target_index];
			if (StringUtils.matchRegex(
					StringUtils.splitByRegex(target, "\\.")[0],
					DmalmRegex.REGEXPATTERNDEMAND))
				target = StringUtils.splitByRegex(target, "\\.")[0];

		}
	}

	public void testfindRegex() throws DAOException, PropertiesReaderException,
			SQLException {

		// String stringa = "CDgO...CDGO_MR-DT";
		String stringa = "CDgO.CDGO_MR-DT";

		System.out
				.println(StringUtils
						.findRegex(stringa,
								"(([a-zA-Z0-9])(.[a-zA-Z0-9])?)(..([a-zA-Z0-9])(.[a-zA-Z0-9])?)*"));
		System.out
				.println(StringUtils.findRegex(stringa, "\\b([a-zA-Z0-9])+$"));

	}

	public void testMatchRegex() throws Exception {

		String stringa = "CDGO.CDGO_M-ETL...CDGO.CDGO_M-REP.CDGO_M-REP2..";

		System.out
				.println(StringUtils
						.matchRegex(
								stringa,
								"^[a-zA-Z0-9-_]+(.[a-zA-Z0-9-_]+)?(.[a-zA-Z0-9-_]+)?(..[a-zA-Z0-9-_]{1}[a-zA-Z0-9-_]*(.[a-zA-Z0-9-_]*)?(.[a-zA-Z0-9-_]*)?)*$"));

	}
}