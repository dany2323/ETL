package lispa.schedulers.utils;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;

public class PersonaleUtils {
	
	public static DmalmPersonale tuplaToPersonale(Tuple row) {
		QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;			

		DmalmPersonale personale = new DmalmPersonale();
		
		personale.setIdEdma(row.get(qPersonale.idEdma));
		personale.setCdPersonale(row.get(qPersonale.cdPersonale));
		personale.setDtInizioValiditaEdma(row.get(qPersonale.dtInizioValiditaEdma));
		personale.setDtFineValiditaEdma(row.get(qPersonale.dtFineValiditaEdma));
		personale.setDtAttivazione(row.get(qPersonale.dtAttivazione));
		personale.setDtDisattivazione(row.get(qPersonale.dtDisattivazione));
		personale.setNote(row.get(qPersonale.note));
		personale.setInterno(row.get(qPersonale.interno));
		personale.setCdResponsabile(row.get(qPersonale.cdResponsabile));
		personale.setIndirizzoEmail(row.get(qPersonale.indirizzoEmail));
		personale.setNome(row.get(qPersonale.nome));
		personale.setCognome(row.get(qPersonale.cognome));
		personale.setMatricola(row.get(qPersonale.matricola));
		personale.setCodiceFiscale(row.get(qPersonale.codiceFiscale));
		personale.setIdentificatore(row.get(qPersonale.identificatore));
		personale.setIdGrado(row.get(qPersonale.idGrado));
		personale.setIdSede(row.get(qPersonale.idSede));
		personale.setCdSuperiore(row.get(qPersonale.cdSuperiore));
		personale.setCdEnte(row.get(qPersonale.cdEnte));
		personale.setCdVisibilita(row.get(qPersonale.cdVisibilita));
		personale.setDtCaricamento(row.get(qPersonale.dtCaricamento));
		personale.setDtInizioValidita(row.get(qPersonale.dtInizioValidita));
		personale.setDtFineValidita(row.get(qPersonale.dtFineValidita));
		personale.setAnnullato(row.get(qPersonale.annullato));
		
		return personale;
	}


}
