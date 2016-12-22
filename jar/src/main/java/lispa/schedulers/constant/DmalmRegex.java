package lispa.schedulers.constant;

public final class DmalmRegex {
	//REGEX TEMPLATES
		public static final String REGEXPATTERNSVILUPPO = "SW-\\{[^{].*[^}]\\}.*";
		public static final String REGEXPATTERNDEMAND = "^A\\d+\\w?";
		public static final String REGEXPATTERNSERDEP = "^A\\d{2}\\d?\\w?";
		public static final String REGEXPATTERNASSISTENZA = "Assistenza.A(FA)?\\d{2}\\d?\\w?";
		
	//REGEX ERRORI
		public static final String REGEXORAERROR = "\\bORA-\\d{5}\\b";
		public static final String REGEXCONNECTIONTIMEOUT = "\\bconnect timed out\\b";
		public static final String REGEXCONNECTIONREFUSED = "\\bConnection refused\\b";
		public static final String REGEXINVALIDCINDEX = "\\b(?i)Invalid column index\\b";
		public static final String REGEXDEADLOCK = "\\b(?i)Deadlock detected\\b";
		public static final String REGEXPARAMETRO = "\\b(?i)Parametro IN o OUT mancante nell'indice\\b";
		public static final String REGEXLOCKTABLE = "\\b(?i)Timeout trying to lock table\\b";
		public static final String REGEXCONNECTIONRESET = "\\b(?i)Connection reset\\b";
		
	//REGEX GENERALI	
		public static final String REGEXSVNDATE = "\\.\\d{6}Z";
		public static final String REGEXGENERALDATE="((19|20)\\d\\d)(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])"; //yyyyMMdd
	//SEPARATORE SFERA
		public static final char COMMA =',';
	//MISURE
		public static final String MPP ="\\bPATR\\s*(_?|-?)\\s*";
		public static final String REGEXNOMEMISURA = "\\bPAT-[0-9][0-9][1-9]-(B|C)(#(.*)+)?$";
		public static final String REGEXNOMEMISURAST = "\\bST[1-3]-[0-9][0-9][0-9](#(.*)+)?$";
		public static final String REGEXNOMEPROGETTOBAS = "\\b[a-zA-Z][0-9][0-9](#(.*)+)?$";
		public static final String REGEXNOMEMISURABAS = "\\bBAS-[0-9][0-9][1-9](#(.*)+)?$";
		public static final String REGEXCODRDI = "\\b(19|20)[0-9][0-9][0-9][0-9][0-9][0-9]$";
		
		public static final String REGEXAPPROCCIO = "\\b(?i)standard\\b";
		public static final String REGEXUTENTEMISURATORE = "\\b(?i)admin\\b";
		
		public static final String REGEXTYPEB = "\\bPAT-00[12]-B(?i)(#RESPINTA)?";
		public static final String REGEXTYPEC = "\\bPAT-00[12]-C(?i)(#RESPINTA)?";
		public static final String STATUS = "\\b(?i)COMPLETATA";
//		public static final String REGEXPATTERNORESTE = "(([a-zA-Z0-9])(.[a-zA-Z0-9])?)(..([a-zA-Z0-9])(.[a-zA-Z0-9])?)*";
//		public static final String REGEXPATTERNSFERAASM = "(?i)(#ANNULLATO LOGICAMENTE##[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]#)?(([a-zA-Z0-9])(.[a-zA-Z0-9])?)(..([a-zA-Z0-9])(.[a-zA-Z0-9])?)*";
		public static final String SEMICOLONWITHSPACE = "([a-zA-Z0-9 ]+;)*";
		public static final String SEMICOLONWITHOUTSPACE = "([a-zA-Z0-9]+;)*";
		public static final String NESSUNO = "NESSUN[OA][;]?";
		public static final String REGEXSVNBLOCCANTE = "\\b(?i)svn:\\s*E170001";
		public static final String REGEXSVNNONBLOCCANTE_1= "\\b(?i)svn:\\s*E160013";
		public static final String REGEXSVNNONBLOCCANTE_2= "\\b(?i)svn:\\s*E160017";
		public static final String REGEXLOWERCASE = ".*[a-z]+.*";
		public static final String REGEXFLUSSI_IO = "\\b\\d\\d?_\\w\\w?[_-]";
		public static final String REGEX_NECA = ".*;\\s*NECA\\s*;.*";
		public static final String REGEX_NECA_THIN = "\\s*NECA\\s*;";
		public static final String REGEX_FOL = ".*;\\s*FOL\\s*;.*";
		public static final String REGEX_FOL_THIN = "\\s*FOL\\s*;";
		public static final String REGEX_SIG = ".*;\\s*SIG\\s*;.*";
		public static final String REGEX_SIG_THIN = "\\s*SIG\\s*;";
		public static final String REGEX_SINTEL = ".*;\\s*SINTEL\\s*;.*";
		public static final String REGEX_SINTEL_THIN = "\\s*SINTEL\\s*;";
		public static final String FLAG_VALUES = "\\b(SI|NO)\\b";

		


}
