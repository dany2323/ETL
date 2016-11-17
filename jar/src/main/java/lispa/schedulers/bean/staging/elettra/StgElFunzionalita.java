package lispa.schedulers.bean.staging.elettra;

import java.util.Date;

public class StgElFunzionalita {
	private Integer funzionaliaPk;
	private String idFunzionalitaEdma;
	private String idEdmaPadre;
	private String idFunzionalia;
	private String tipoOggetto;
	private String siglaProdotto;
	private String siglaSottosistema;
	private String siglaModulo;
	private String siglaFunzionalita;
	private String nome;
	private String descrizioneFunzionalita;
	private String annullato;
	private String dataAnnullamento;
	private String categoria;
	private String linguaggi;
	private String tipiElaborazione;
    private Date dataCaricamento;
    
	public Integer getFunzionaliaPk() {
		return funzionaliaPk;
	}
	public void setFunzionaliaPk(Integer funzionaliaPk) {
		this.funzionaliaPk = funzionaliaPk;
	}
	public String getIdFunzionalitaEdma() {
		return idFunzionalitaEdma;
	}
	public void setIdFunzionalitaEdma(String idFunzionalitaEdma) {
		this.idFunzionalitaEdma = idFunzionalitaEdma;
	}
	public String getIdEdmaPadre() {
		return idEdmaPadre;
	}
	public void setIdEdmaPadre(String idEdmaPadre) {
		this.idEdmaPadre = idEdmaPadre;
	}
	public String getIdFunzionalia() {
		return idFunzionalia;
	}
	public void setIdFunzionalia(String idFunzionalia) {
		this.idFunzionalia = idFunzionalia;
	}
	public String getTipoOggetto() {
		return tipoOggetto;
	}
	public void setTipoOggetto(String tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
	public String getSiglaProdotto() {
		return siglaProdotto;
	}
	public void setSiglaProdotto(String siglaProdotto) {
		this.siglaProdotto = siglaProdotto;
	}
	public String getSiglaSottosistema() {
		return siglaSottosistema;
	}
	public void setSiglaSottosistema(String siglaSottosistema) {
		this.siglaSottosistema = siglaSottosistema;
	}
	public String getSiglaModulo() {
		return siglaModulo;
	}
	public void setSiglaModulo(String siglaModulo) {
		this.siglaModulo = siglaModulo;
	}
	public String getSiglaFunzionalita() {
		return siglaFunzionalita;
	}
	public void setSiglaFunzionalita(String siglaFunzionalita) {
		this.siglaFunzionalita = siglaFunzionalita;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizioneFunzionalita() {
		return descrizioneFunzionalita;
	}
	public void setDescrizioneFunzionalita(String descrizioneFunzionalita) {
		this.descrizioneFunzionalita = descrizioneFunzionalita;
	}
	public String getAnnullato() {
		return annullato;
	}
	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}
	public String getDataAnnullamento() {
		return dataAnnullamento;
	}
	public void setDataAnnullamento(String dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getLinguaggi() {
		return linguaggi;
	}
	public void setLinguaggi(String linguaggi) {
		this.linguaggi = linguaggi;
	}
	public String getTipiElaborazione() {
		return tipiElaborazione;
	}
	public void setTipiElaborazione(String tipiElaborazione) {
		this.tipiElaborazione = tipiElaborazione;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
}
