package lispa.schedulers.bean.staging.elettra;

import java.util.Date;

public class StgElProdotti {
	private Integer prodottoPk;
	private String idProdottoEdma;
	private String idProdotto;
	private String tipoOggetto;
	private String sigla;
	private String nome;
	private String descrizioneProdotto;
	private String areaProdotto;
	private String responsabileProdotto;
	private String annullato;
	private String dataAnnullamento;
	private String ambitoManutenzione;
	private String areaTematica;
	private String BaseDatiEtl;
	private String BaseDatiLettura;
	private String BaseDatiScrittura;
	private String categoria;
	private String fornituraRisorseEsterne;
	private String codiceAreaProdotto;
    private Date dataCaricamento;
    
	public Integer getProdottoPk() {
		return prodottoPk;
	}
	public void setProdottoPk(Integer prodottoPk) {
		this.prodottoPk = prodottoPk;
	}
	public String getIdProdottoEdma() {
		return idProdottoEdma;
	}
	public void setIdProdottoEdma(String idProdottoEdma) {
		this.idProdottoEdma = idProdottoEdma;
	}
	public String getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(String idProdotto) {
		this.idProdotto = idProdotto;
	}
	public String getTipoOggetto() {
		return tipoOggetto;
	}
	public void setTipoOggetto(String tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizioneProdotto() {
		return descrizioneProdotto;
	}
	public void setDescrizioneProdotto(String descrizioneProdotto) {
		this.descrizioneProdotto = descrizioneProdotto;
	}
	public String getAreaProdotto() {
		return areaProdotto;
	}
	public void setAreaProdotto(String areaProdotto) {
		this.areaProdotto = areaProdotto;
	}
	public String getResponsabileProdotto() {
		return responsabileProdotto;
	}
	public void setResponsabileProdotto(String responsabileProdotto) {
		this.responsabileProdotto = responsabileProdotto;
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
	public String getAmbitoManutenzione() {
		return ambitoManutenzione;
	}
	public void setAmbitoManutenzione(String ambitoManutenzione) {
		this.ambitoManutenzione = ambitoManutenzione;
	}
	public String getAreaTematica() {
		return areaTematica;
	}
	public void setAreaTematica(String areaTematica) {
		this.areaTematica = areaTematica;
	}
	public String getBaseDatiEtl() {
		return BaseDatiEtl;
	}
	public void setBaseDatiEtl(String baseDatiEtl) {
		BaseDatiEtl = baseDatiEtl;
	}
	public String getBaseDatiLettura() {
		return BaseDatiLettura;
	}
	public void setBaseDatiLettura(String baseDatiLettura) {
		BaseDatiLettura = baseDatiLettura;
	}
	public String getBaseDatiScrittura() {
		return BaseDatiScrittura;
	}
	public void setBaseDatiScrittura(String baseDatiScrittura) {
		BaseDatiScrittura = baseDatiScrittura;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFornituraRisorseEsterne() {
		return fornituraRisorseEsterne;
	}
	public void setFornituraRisorseEsterne(String fornituraRisorseEsterne) {
		this.fornituraRisorseEsterne = fornituraRisorseEsterne;
	}
	public String getCodiceAreaProdotto() {
		return codiceAreaProdotto;
	}
	public void setCodiceAreaProdotto(String codiceAreaProdotto) {
		this.codiceAreaProdotto = codiceAreaProdotto;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
}
