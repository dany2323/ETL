package lispa.schedulers.utils;

import lispa.schedulers.queryimplementation.staging.elettra.QStgElAmbienteTecnologico;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElClassificatori;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElModuli;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElProdotti;
//import lispa.schedulers.queryimplementation.staging.oreste.QStgAmbienteTecnologico;


import com.mysema.query.Tuple;

public class ElettraToStringUtils {
	
	public static String classificatoriToString(Tuple row) {
		
		QStgElClassificatori qStgElClassificatori = QStgElClassificatori.stgElClassificatori;
		
		String record = "";
		
		if(row != null) {
			
			record += "[ ClassificatorePK: " + row.get(qStgElClassificatori.classificatorePk) + "§ ";
			
			record += "Codice Classificatore: " + row.get(qStgElClassificatori.codiceClassificatore) + "§ ";
			
			record += "Codice Tipologia: " + row.get(qStgElClassificatori.tipoClassificatore) + "§ ";
			
			record += "Id: " + row.get(qStgElClassificatori.idClassificatore) + "] ";
			
		}
		
		return record;
		
	}
	public static String prodottoToString(Tuple row){
		
		QStgElProdotti prodotto = QStgElProdotti.stgElProdotti;
		
		String record = "";
		
		if(row!=null)
		{
				record+="[ ProdottoPk: "+row.get(prodotto.prodottoPk)+ "§ ";
				
				record+="IdEdma: "+row.get(prodotto.idProdottoEdma)+ "§ ";
			
				record+="IdProdotto: "+row.get(prodotto.idProdotto)+ "§ ";
			
				record+="Sigla: "+row.get(prodotto.sigla)+ "§ ";
			
				record+="Nome: "+row.get(prodotto.nome)+ "§ ";
			
				record+="TipoOggetto: "+row.get(prodotto.tipoOggetto)+ "§ ";
			
				record+="Annullato: "+row.get(prodotto.annullato)+ "§ ";
			
				record+="DtAnnullamento: "+row.get(prodotto.dataAnnullamento)+ "§ ";
			
				record+="Descrizione: "+row.get(prodotto.descrizioneProdotto)+ "§ ";
			
				record+="Categoria: "+row.get(prodotto.categoria)+ "§ ";
			
				record+="AmbitoManutenzione: "+row.get(prodotto.ambitoManutenzione)+ "§ ";
			
				record+="Clasf_AreaTematica: "+row.get(prodotto.areaTematica)+ "§ ";
			
				record+="Clasf_BaseDatiEtl: "+row.get(prodotto.BaseDatiEtl)+ "§ ";
			
				record+="Clasf_BaseDatiLettura: "+row.get(prodotto.BaseDatiLettura)+ "§ ";
			
				record+="Clasf_BaseDatiScrittura: "+row.get(prodotto.BaseDatiScrittura)+ "§ ";
		
				record+="Clasf_FornituraRisorseEsterne: "+row.get(prodotto.fornituraRisorseEsterne)+ "§ ";
				
				record+="AreaProdotto: "+row.get(prodotto.areaProdotto)+ "§ ";
				
				record+="ResponsabileProdotto: "+row.get(prodotto.responsabileProdotto)+ " ]";
		}
		
		return record;
		
	}
	
	public static String moduloElettraToString(Tuple row){
		
		QStgElModuli modulo = QStgElModuli.stgElModuli;
		
		String record = "";
		
		if(row!=null)
		{
				record+="[ IdEdma: "+row.get(modulo.idModuloEdma)+ "§ ";
				
				record+="IdEdmaPadre: "+row.get(modulo.idModuloEdmaPadre)+ "§ ";
			
				record+="Id: "+row.get(modulo.idModulo)+ "§ ";
			
				record+="Sigla: "+row.get(modulo.siglaModulo)+ "§ ";
				
				record+="SiglaProdotto: "+row.get(modulo.siglaProdotto)+ "§ ";
				
				record+="SiglaSottosistema: "+row.get(modulo.siglaSottosistema)+ "§ ";
			
				record+="Nome: "+row.get(modulo.nome)+ "§ ";
				
				record+="Descrizione: "+row.get(modulo.descrizioneModulo)+ "§ ";
			
				record+="Tipo Oggetto: "+row.get(modulo.tipoOggetto)+ "§ ";
			
				record+="Annullato: "+row.get(modulo.annullato)+ "§ ";
			
				record+="DtAnnullamento: "+row.get(modulo.dataAnnullamento)+ "§ ";
			
				record+="Clasf_Sottosistema: "+row.get(modulo.sottosistema)+ "§ ";
			
				record+="Clasf_Tecnologia: "+row.get(modulo.tecnologie)+ "§ ";
				
				record+="Clasf_Tipomodulo: "+row.get(modulo.tipoModulo)+ "§ ";
				
				record+="Responsabile: "+row.get(modulo.responsabile)+ "] ";
			
		}
		
		return record;
		
	}
	public static String funzionalitaElettraToString(Tuple row) {
		
		QStgElFunzionalita funzionalita = QStgElFunzionalita.stgElFunzionalita;
		
		String record = "";
		
		if(row != null) {
			
			record += "[ FunzionalitaPk: " + row.get(funzionalita.funzionaliaPk) + "§ ";
	
			record += "IdEdma: " + row.get(funzionalita.idFunzionalitaEdma) + "§ ";
	
			record += "IdEdmaPadre: " + row.get(funzionalita.idEdmaPadre) + "§ ";
	
			record += "IdFunzionalita: " + row.get(funzionalita.idFunzionalia) + "§ ";
	
			record += "Nome: " + row.get(funzionalita.nome) + "§ ";
	
			record += "Sigla: " + row.get(funzionalita.siglaFunzionalita) + "§ ";
	
			record += "SiglaModulo: " + row.get(funzionalita.siglaModulo) + "§ ";
	
			record += "SiglaProdotto: " + row.get(funzionalita.siglaProdotto) + "§ ";
	
			record += "Categoria: " + row.get(funzionalita.categoria) + "§ ";
	
			record += "Linguaggi: " + row.get(funzionalita.linguaggi) + "§ ";
	
			record += "TipiElaborazione: " + row.get(funzionalita.tipiElaborazione) + "§ ";
	
			record += "Descrizione: " + row.get(funzionalita.descrizioneFunzionalita) + "§ ";
	
			record += "DataAnnullamento: " + row.get(funzionalita.dataAnnullamento) + "§ ";
	
			record += "Annullata: " + row.get(funzionalita.annullato) + "§ ";
	
			record += "TipoOggetto: " + row.get(funzionalita.tipoOggetto) + " ]";
	
		}
		
		return record;
		
	}
	public static String ambienteTecnologicoElettraToString(Tuple row) {
		
		QStgElAmbienteTecnologico ambiente = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;
		
		String record = "";
		
		if(row != null) {
			
			record += "[ AmbienteTecnologicoPK: " + row.get(ambiente.ambienteTecnologicoPk) + "§ ";
			
			record += "Id: " + row.get(ambiente.idAmbienteTecnologico) + "§ ";
			
			record += "IdEdma: " + row.get(ambiente.idAmbienteTecnologicoEdma) + "§ ";
			
			record += "IdEdmaPadre: " + row.get(ambiente.idAmbienteTecnologicoEdmaPadre) + "§ ";
			
			record += "Nome: " + row.get(ambiente.nome) + "§ ";
			
			record += "SiglaModulo: " + row.get(ambiente.siglaModulo) + "§ ";
			
			record += "SiglaProdotto: " + row.get(ambiente.siglaProdotto) + "§ ";
			
			record += "Archiitettura:" + row.get(ambiente.architettura) + "§ ";
			
			record += "Infrastruttura: " + row.get(ambiente.infrastruttura) + "§ ";
			
			record += "SistemaOperativo: " + row.get(ambiente.sistemaOperativo) + "§ ";
			
			record += "TipoLayer: " + row.get(ambiente.tipoLayer) + "§ ";
						
			record += "Descrizione: " + row.get(ambiente.descrizioneAmbienteTecnologico) + "§ ";			
			
			record += "VersioneSO: " + row.get(ambiente.versioneSistemaOperativo) + " ]";
			
		}
		
		return record;
		
	}
	
}
