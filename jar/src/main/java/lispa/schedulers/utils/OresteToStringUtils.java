package lispa.schedulers.utils;

import lispa.schedulers.queryimplementation.staging.oreste.QStgClassificatori;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;

import com.mysema.query.Tuple;

public class OresteToStringUtils {

	public static String classificatoreOrestetoString(Tuple row) {
		
		QStgClassificatori classificatore = QStgClassificatori.stgClassificatori;
		
		String record = "";
		
		if(row != null) {
			
			record += "[ ClassificatorePK: " + row.get(classificatore.dmalmStgClassificatoriPk) + "§ ";
			
			record += "Codice Classificatore: " + row.get(classificatore.codiceClassificatore) + "§ ";
			
			record += "Codice Tipologia: " + row.get(classificatore.codiceTipologia) + "§ ";
			
			record += "Id: " + row.get(classificatore.id) + "] ";
			
		}
		
		return record;
		
	}

	public static String funzionalitaOrestetoString(Tuple row) {
		
		QStgFunzionalita funzionalita = QStgFunzionalita.stgFunzionalita;
		
		String record = "";
		
		if(row != null) {
			
			record += "[ FunzionalitaPk: " + row.get(funzionalita.dmalmFunzionalitaPk) + "§ ";
	
			record += "IdEdma: " + row.get(funzionalita.idEdmaFunzionalita) + "§ ";
	
			record += "IdEdmaPadre: " + row.get(funzionalita.idEdmaPadre) + "§ ";
	
			record += "IdFunzionalita: " + row.get(funzionalita.idFunzionalita) + "§ ";
	
			record += "Nome: " + row.get(funzionalita.nomeFunzionalita) + "§ ";
	
			record += "Sigla: " + row.get(funzionalita.siglaFunzionalita) + "§ ";
	
			record += "SiglaModulo: " + row.get(funzionalita.siglaModulo) + "§ ";
	
			record += "SiglaProdotto: " + row.get(funzionalita.siglaProdotto) + "§ ";
	
			record += "Clasf_Categoria: " + row.get(funzionalita.clasfCategoria) + "§ ";
	
			record += "Clasf_LinguaggioDiProg: " + row.get(funzionalita.clasfLinguaggioDiProg) + "§ ";
	
			record += "Clasf_TipoElabor: " + row.get(funzionalita.clasfTipoElabor) + "§ ";
	
			record += "Descrizione: " + row.get(funzionalita.descrizione) + "§ ";
	
			record += "DataAnnullamento: " + row.get(funzionalita.dfvFunzionalitaAnnullata) + "§ ";
	
			record += "Annullata: " + row.get(funzionalita.funzionalitaAnnullata) + "§ ";
	
			record += "Tipo: " + row.get(funzionalita.tipoFunzionalita) + " ]";
	
		}
		
		return record;
		
	}

	public static String prodottoOrestetoString(Tuple row){
		
		QStgProdottiArchitetture prodotto = QStgProdottiArchitetture.stgProdottiArchitetture;
		
		String record = "";
		
		if(row!=null)
		{
				record+="[ ProdottoPk: "+row.get(prodotto.dmalmProdottiArchitetturePk)+ "§ ";
				
				record+="IdEdma: "+row.get(prodotto.idEdmaProdotto)+ "§ ";
			
				record+="IdProdotto: "+row.get(prodotto.idProdotto)+ "§ ";
			
				record+="Sigla: "+row.get(prodotto.siglaProdotto)+ "§ ";
			
				record+="Nome: "+row.get(prodotto.nomeProdotto)+ "§ ";
			
				record+="TipoOggetto: "+row.get(prodotto.tipoOggetto)+ "§ ";
			
				record+="Annullato: "+row.get(prodotto.prodottoAnnullato)+ "§ ";
			
				record+="DtAnnullamento: "+row.get(prodotto.dfvProdottoAnnullato)+ "§ ";
			
				record+="Descrizione: "+row.get(prodotto.descrizioneProdotto)+ "§ ";
			
				record+="Categoria: "+row.get(prodotto.clasfCategoria)+ "§ ";
			
				record+="AmbitoManutenzione: "+row.get(prodotto.clasfAmbitoManutenzione)+ "§ ";
			
				record+="Clasf_AreaTematica: "+row.get(prodotto.clasfAreaTematica)+ "§ ";
			
				record+="Clasf_BaseDatiEtl: "+row.get(prodotto.clasfAreaTematica)+ "§ ";
			
				record+="Clasf_BaseDatiLettura: "+row.get(prodotto.clasfBaseDatiLettura)+ "§ ";
			
				record+="Clasf_BaseDatiScrittura: "+row.get(prodotto.clasfBaseDatiScrittura)+ "§ ";
		
				record+="Clasf_FornituraRisorseEsterne: "+row.get(prodotto.clasfFornRisEstGara)+ "§ ";
				
				record+="AreaProdotto: "+row.get(prodotto.edmaAreaProdotto)+ "§ ";
				
				record+="ResponsabileProdotto: "+row.get(prodotto.edmaRespProdotto)+ " ]";
		}
		
		return record;
		
	}

	public static String sottosistemaOrestetoString(Tuple row){
		
		QStgSottosistemi sottosistema = QStgSottosistemi.stgSottosistemi;
		
		String record = "";
		
		if(row!=null)
		{
				record+="[ SottosistemaPk: "+row.get(sottosistema.dmalmSottosistemimPk)+ "§ ";
			
				record+="IdEdma: "+row.get(sottosistema.idEdmaSottosistema)+ "§ ";
				
				record+="IdEdmaPadre: "+row.get(sottosistema.idEdmaPadreSottosistema)+ "§ ";
			
				record+="Id: "+row.get(sottosistema.idSottosistema)+ "§ ";
			
				record+="Sigla: "+row.get(sottosistema.siglaSottosistema)+ "§ ";
				
				record+="SiglaProdotto: "+row.get(sottosistema.siglaProdottoSottosistema)+ "§ ";
			
				record+="Nome: "+row.get(sottosistema.nomeSottosistema)+ "§ ";
			
				record+="Tipo Oggetto: "+row.get(sottosistema.tipoOggetto)+ "§ ";
			
				record+="Annullato: "+row.get(sottosistema.sottosistemaAnnullato)+ "§ ";
			
				record+="DtAnnullamento: "+row.get(sottosistema.dfvSottosistemaAnnullato)+ "§ ";
			
				record+="Descrizione: "+row.get(sottosistema.descrizioneSottosistema)+ "§ ";
			
				record+="Clasf_BaseDatiLettura: "+row.get(sottosistema.clasfBaseDatiLettura)+ "§ ";
			
				record+="Clasf_BaseDatiScrittura: "+row.get(sottosistema.clasfBaseDatiScrittura)+ "§ ";
		
				record+="Clasf_BaseDatiEtl: "+row.get(sottosistema.clasfBaseDatiEtl)+ "§ ";
				
				record+="Clasf_TipoSottosistema: "+row.get(sottosistema.clasfTipoSottosistema)+ "§ ";
				
				record+="Responsabile: "+row.get(sottosistema.edmaRespoSottosistema)+ "] ";
			
		}
		
		return record;
		
	}

	public static String moduloOrestetoString(Tuple row){
		
		QStgModuli modulo = QStgModuli.stgModuli;
		
		String record = "";
		
		if(row!=null)
		{
				record+="[ IdEdma: "+row.get(modulo.idEdmaModulo)+ "§ ";
				
				record+="IdEdmaPadre: "+row.get(modulo.idEdmaPadreModulo)+ "§ ";
			
				record+="Id: "+row.get(modulo.idModulo)+ "§ ";
			
				record+="Sigla: "+row.get(modulo.siglaModulo)+ "§ ";
				
				record+="SiglaProdotto: "+row.get(modulo.siglaProdottoModulo)+ "§ ";
				
				record+="SiglaSottosistema: "+row.get(modulo.siglaSottosistemaModulo)+ "§ ";
			
				record+="Nome: "+row.get(modulo.nomeModulo)+ "§ ";
				
				record+="Descrizione: "+row.get(modulo.descrizioneModulo)+ "§ ";
			
				record+="Tipo Oggetto: "+row.get(modulo.tipoOggetto)+ "§ ";
			
				record+="Annullato: "+row.get(modulo.moduloAnnullato)+ "§ ";
			
				record+="DtAnnullamento: "+row.get(modulo.dfvModuloAnnullato)+ "§ ";
			
				record+="Clasf_Sottosistema: "+row.get(modulo.clasfSottosistemaModulo)+ "§ ";
			
				record+="Clasf_Tecnologia: "+row.get(modulo.clasfTecnologiaModulo)+ "§ ";
				
				record+="Clasf_Tipomodulo: "+row.get(modulo.clasfTipologiaModulo)+ "§ ";
				
				record+="Responsabile: "+row.get(modulo.edmaRespModulo)+ "] ";
			
		}
		
		return record;
		
	}

}
