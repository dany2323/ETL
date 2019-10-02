package lispa.schedulers.utils;

import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.utils.enums.CF_anomalia;
import lispa.schedulers.utils.enums.CF_anomalia_assistenza;
import lispa.schedulers.utils.enums.CF_build;
import lispa.schedulers.utils.enums.CF_classificatore;
import lispa.schedulers.utils.enums.CF_classificatore_demand;
import lispa.schedulers.utils.enums.CF_defect;
import lispa.schedulers.utils.enums.CF_dman;
import lispa.schedulers.utils.enums.CF_documento;
import lispa.schedulers.utils.enums.CF_drqs;
import lispa.schedulers.utils.enums.CF_fase;
import lispa.schedulers.utils.enums.CF_pei;
import lispa.schedulers.utils.enums.CF_progettoese;
import lispa.schedulers.utils.enums.CF_programma;
import lispa.schedulers.utils.enums.CF_release;
import lispa.schedulers.utils.enums.CF_release_it;
import lispa.schedulers.utils.enums.CF_release_ser;
import lispa.schedulers.utils.enums.CF_rfc;
import lispa.schedulers.utils.enums.CF_richiesta_gestione;
import lispa.schedulers.utils.enums.CF_rqd;
import lispa.schedulers.utils.enums.CF_sman;
import lispa.schedulers.utils.enums.CF_sottoprogramma;
import lispa.schedulers.utils.enums.CF_srqs;
import lispa.schedulers.utils.enums.CF_sup;
import lispa.schedulers.utils.enums.CF_role_values;
import lispa.schedulers.utils.enums.CF_task;
import lispa.schedulers.utils.enums.CF_taskit;
import lispa.schedulers.utils.enums.CF_testcase;
import lispa.schedulers.utils.enums.Template_Type;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class EnumUtils {

	public static List<String> workItemTypes() {
		EnumWorkitemType types[] = Workitem_Type.EnumWorkitemType.values();
		List<String> types_list = new ArrayList<String>();
		for(EnumWorkitemType type : types) {
			types_list.add(type.toString());
		}
		return types_list;
		

	}


	/* ritorna la lista di CF di interesse per il Workitem_type */
	public static List<String> getCFEnumerationByType(EnumWorkitemType type) {

		Enum<?>[] CF_enum = null;
		List<String> custom_fields = new ArrayList<String>();

		switch(type.toString()) {

		case "anomalia":
			CF_enum = CF_anomalia.values();
			break;
		case "anomalia_assistenza":
			CF_enum = CF_anomalia_assistenza.values();
			break;
		case "build":
			CF_enum = CF_build.values();
			break;
		case "defect":
			CF_enum = CF_defect.values();
			break;
		case "dman":
			CF_enum = CF_dman.values();
			break;
		case "documento":
			CF_enum = CF_documento.values();
			break;
		case "drqs":
			CF_enum = CF_drqs.values();
			break;
		case "fase":
			CF_enum = CF_fase.values();
			break;
		case "pei":
			CF_enum = CF_pei.values();
			break;
		case "progettoese":
			CF_enum = CF_progettoese.values();
			break;
		case "programma":
			CF_enum = CF_programma.values();
			break;
		case "release":
			CF_enum = CF_release.values();
			break;
		case "release_it":
			CF_enum = CF_release_it.values();
			break;
		case "release_ser":
			CF_enum = CF_release_ser.values();
			break;
		case "richiesta_gestione":	
			CF_enum = CF_richiesta_gestione.values();
			break;
		case "rqd":	
			CF_enum = CF_rqd.values();
			break;
		case "sman":	
			CF_enum = CF_sman.values();
			break;
		case "sottoprogramma":	
			CF_enum = CF_sottoprogramma.values();
			break;
		case "sup":
			CF_enum= CF_sup.values();
			break;
		case "srqs":	
			CF_enum = CF_srqs.values();
			break;
		case "task":	
			CF_enum = CF_task.values();
			break;
		case "taskit":
			CF_enum = CF_taskit.values();
			break;
		case "testcase":	
			CF_enum = CF_testcase.values();
			break;
		case "classificatore_demand":	
			CF_enum = CF_classificatore_demand.values();
			break;
		case "classificatore":	
			CF_enum = CF_classificatore.values();
			break;
		case "rfc":	
			CF_enum = CF_rfc.values();
			break;	
		default:
			CF_enum = null;
		}

		if(CF_enum != null) {
			for(Enum<?> e : CF_enum) {
				custom_fields.add(e.toString());
			}
		}
		
		return custom_fields;
	}
	public static String getTemplateByWorkItem(EnumWorkitemType type) {
		String template = null;

		switch(type.toString()) {

		case "anomalia":
			template=Template_Type.SVILUPPO.toString();
			break;
		case "anomalia_assistenza":
			template=Template_Type.ASSISTENZA.toString();
			break;
		case "build":
			template=Template_Type.IT.toString();
			break;
		case "defect":
			template=Template_Type.SVILUPPO.toString();
			break;
		case "dman":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "documento":
			template=Template_Type.SVILUPPO.toString();
			break;
		case "drqs":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "fase":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "pei":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "progettoese":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "programma":
			template=Template_Type.DEMAND2016.toString();
			break;
		case "release":
			template=Template_Type.SVILUPPO.toString();
			break;
		case "release_it":
			template=Template_Type.IT.toString();
			break;
		case "release_ser":
			template=Template_Type.SERDEP.toString();
			break;
		case "richiesta_gestione":	
			template=Template_Type.ASSISTENZA.toString();
			break;
		case "rqd":	
			template=Template_Type.DEMAND2016.toString();
			break;
		case "sman":	
			template=Template_Type.SVILUPPO.toString();
			break;
		case "sottoprogramma":	
			template=Template_Type.DEMAND2016.toString();
			break;
		case "srqs":	
			template=Template_Type.SVILUPPO.toString();
			break;
		case "task":	
			template=Template_Type.SVILUPPO.toString();
			break;
		case "taskit":
			template=Template_Type.IT.toString();
			break;
		case "testcase":	
			template=Template_Type.SVILUPPO.toString();
			break;
		case "classificatore_demand":	
			template=Template_Type.DEMAND2016.toString();
			break;
		case "classificatore":	
			template="Tutti tranne Demand";
			break;	
		case "sup":	
			template=Template_Type.SVILUPPO.toString();
			break;
		case "rfc":	
			template = Template_Type.DEMAND2016.toString();
			break;

		default:
			template= null;
		}

	

		return template;
	}
	//
	public static String getCustomFieldValue(EnumWorkitemType type,String customFieldId,String idValue) {
		
		Enum<?>[] CF_values = null;
		List<String> custom_fields_values = new ArrayList<String>();
		switch (type)
		{
			case defect:	//DM_ALM-289 Commento 4
							if(customFieldId.equals("role"))
							{
								if(idValue.contains(" "))
									idValue=idValue.split(" ")[0];
								if(idValue.contains("SVI"))
									idValue="SV";
								CF_values=CF_role_values.values();
								for(Enum <?> enumCf: CF_values)
								{
									if(enumCf.toString().equals(idValue))
										return idValue;
								}
								return null;
							}
			case task:  	//DM_ALM-289 Commento 4
							if(customFieldId.equals("role"))
							{
								if(idValue.contains(" "))
									idValue=idValue.split(" ")[0];
								if(idValue.contains("SVI"))
									idValue="SV";
								CF_values=CF_role_values.values();
								for(Enum <?> enumCf: CF_values)
								{
									if(enumCf.toString().equals(idValue))
										return idValue;
								}
								return null;
							}
		}
		return null;
		
	}

}
