package lispa.schedulers.bean.target.elettra;

import java.util.Date;

public class DmalmElModuli {
	private Integer moduloPk;
	private String idModuloEdma;
	private String idModuloEdmaPadre;
	private String idModulo;
	private String tipoOggetto;
	private String siglaProdotto;
	private String siglaSottosistema;
	private String siglaModulo;
	private String nome;
	private String descrizioneModulo;
	private String annullato;
	private Date dataAnnullamento;
	private String responsabile;
	private String sottosistema;
	private String tecnologie;
	private String tipoModulo;
    private Date dataCaricamento;
    private Integer personaleFk;
    private Integer prodottoFk;
    private Date dataInizioValidita;
    private Date dataFineValidita;
    
	public Integer getModuloPk() {
		return moduloPk;
	}
	public void setModuloPk(Integer moduloPk) {
		this.moduloPk = moduloPk;
	}
	public String getIdModuloEdma() {
		return idModuloEdma;
	}
	public void setIdModuloEdma(String idModuloEdma) {
		this.idModuloEdma = idModuloEdma;
	}
	public String getIdModuloEdmaPadre() {
		return idModuloEdmaPadre;
	}
	public void setIdModuloEdmaPadre(String idModuloEdmaPadre) {
		this.idModuloEdmaPadre = idModuloEdmaPadre;
	}
	public String getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizioneModulo() {
		return descrizioneModulo;
	}
	public void setDescrizioneModulo(String descrizioneModulo) {
		this.descrizioneModulo = descrizioneModulo;
	}
	public String getAnnullato() {
		return annullato;
	}
	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}
	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}
	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
	public String getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
	public String getSottosistema() {
		return sottosistema;
	}
	public void setSottosistema(String sottosistema) {
		this.sottosistema = sottosistema;
	}
	public String getTecnologie() {
		return tecnologie;
	}
	public void setTecnologie(String tecnologie) {
		this.tecnologie = tecnologie;
	}
	public String getTipoModulo() {
		return tipoModulo;
	}
	public void setTipoModulo(String tipoModulo) {
		this.tipoModulo = tipoModulo;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	public Integer getPersonaleFk() {
		return personaleFk;
	}
	public void setPersonaleFk(Integer personaleFk) {
		this.personaleFk = personaleFk;
	}
	public Integer getProdottoFk() {
		return prodottoFk;
	}
	public void setProdottoFk(Integer prodottoFk) {
		this.prodottoFk = prodottoFk;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
