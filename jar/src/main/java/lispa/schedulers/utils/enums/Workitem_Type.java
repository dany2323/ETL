package lispa.schedulers.utils.enums;

import java.util.EnumMap;

import lispa.schedulers.constant.DmAlmConstants;

public class Workitem_Type {
	
	public enum EnumWorkitemType {
		anomalia,
		documento,
		testcase,
		pei,
		build,
		progettoese,
		fase,
		defect,
		release,
		sottoprogramma,
		programma,
		taskit,
		anomalia_assistenza,	
		release_it,
		sman,
		release_ser,
		drqs,
		dman,
		rqd,
		richiesta_gestione,
		srqs,
		task,
		classificatore_demand, 
		classificatore,
		sup
	}
	
	public static EnumMap<EnumWorkitemType, String> getEnumMapWiTypeStoredProcedure() {
		EnumMap<EnumWorkitemType, String> enumMap = new EnumMap<EnumWorkitemType, String>(EnumWorkitemType.class);
		enumMap.put(EnumWorkitemType.anomalia, DmAlmConstants.STORED_PROCEDURE_STOR_ANOMALIA_PRODOTTO);
		enumMap.put(EnumWorkitemType.defect, DmAlmConstants.STORED_PROCEDURE_STOR_DIFETTO_PRODOTTO);
		enumMap.put(EnumWorkitemType.documento, DmAlmConstants.STORED_PROCEDURE_STOR_DOCUMENTO);
		enumMap.put(EnumWorkitemType.sup, DmAlmConstants.STORED_PROCEDURE_STOR_RICHIESTA_SUPPORTO);
		enumMap.put(EnumWorkitemType.sman, DmAlmConstants.STORED_PROCEDURE_STOR_MANUTENZIONE);
		enumMap.put(EnumWorkitemType.dman, DmAlmConstants.STORED_PROCEDURE_STOR_MANUTENZIONE);
		enumMap.put(EnumWorkitemType.rqd, DmAlmConstants.STORED_PROCEDURE_STOR_PROG_DEMAND);
		enumMap.put(EnumWorkitemType.programma, DmAlmConstants.STORED_PROCEDURE_STOR_PROGRAMMA);
		enumMap.put(EnumWorkitemType.release, DmAlmConstants.STORED_PROCEDURE_REL_PROGETTO);
		enumMap.put(EnumWorkitemType.sottoprogramma, DmAlmConstants.STORED_PROCEDURE_SOTTOPROGRAMMA);
		enumMap.put(EnumWorkitemType.srqs, DmAlmConstants.STORED_PROCEDURE_PROGETTO_SVILUPPO_SVILUPPO);
		enumMap.put(EnumWorkitemType.progettoese, DmAlmConstants.STORED_PROCEDURE_STOR_PROG_ESE);
		enumMap.put(EnumWorkitemType.anomalia_assistenza, DmAlmConstants.STORED_PROCEDURE_ANOMALIA_ASS);
		enumMap.put(EnumWorkitemType.task, DmAlmConstants.STORED_PROCEDURE_TASK);
		enumMap.put(EnumWorkitemType.release_ser, DmAlmConstants.STORED_PROCEDURE_REL_SER);
		enumMap.put(EnumWorkitemType.taskit, DmAlmConstants.STORED_PROCEDURE_TASK_IT);
		enumMap.put(EnumWorkitemType.testcase, DmAlmConstants.STORED_PROCEDURE_TEST_CASE);
		enumMap.put(EnumWorkitemType.richiesta_gestione, DmAlmConstants.STORED_PROCEDURE_RICHIESTA_GESTIONE);
		enumMap.put(EnumWorkitemType.pei, DmAlmConstants.STORED_PROCEDURE_PEI);
		enumMap.put(EnumWorkitemType.build, DmAlmConstants.STORED_PROCEDURE_BUILD);
		enumMap.put(EnumWorkitemType.fase, DmAlmConstants.STORED_PROCEDURE_FASE);
		enumMap.put(EnumWorkitemType.release_it, DmAlmConstants.STORED_PROCEDURE_REL_IT);
		enumMap.put(EnumWorkitemType.drqs, DmAlmConstants.STORED_PROCEDURE_PROG_SV_DEM);
		enumMap.put(EnumWorkitemType.classificatore, DmAlmConstants.STORED_PROCEDURE_CLASS);
		return enumMap;
	}
}
