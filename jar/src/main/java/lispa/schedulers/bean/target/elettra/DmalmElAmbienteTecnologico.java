package lispa.schedulers.bean.target.elettra;

import java.util.Date;

public class DmalmElAmbienteTecnologico {
	private Integer ambienteTecnologicoPk;
	private String idAmbienteTecnologicoEdma;
	private String idAmbienteTecnologicoEdmaPadre;
	private String idAmbienteTecnologico;
	private String siglaProdotto;
	private String siglaModulo;
	private String nome;
	private String architettura;
	private String infrastruttura;
	private String sistemaOperativo;
	private String tipoLayer;
	private String versioneSistemaOperativo;
	private String descrizioneAmbienteTecnologico;
    private Date dataCaricamento;
    private String annullato;
    private Date dataAnnullamento;
    private Integer prodottoFk;
    private Integer moduloFk;
    private Date dataInizioValidita;
    private Date dataFineValidita;
    
	public Integer getAmbienteTecnologicoPk() {
		return ambienteTecnologicoPk;
	}
	public void setAmbienteTecnologicoPk(Integer ambienteTecnologicoPk) {
		this.ambienteTecnologicoPk = ambienteTecnologicoPk;
	}
	public String getIdAmbienteTecnologicoEdma() {
		return idAmbienteTecnologicoEdma;
	}
	public void setIdAmbienteTecnologicoEdma(String idAmbienteTecnologicoEdma) {
		this.idAmbienteTecnologicoEdma = idAmbienteTecnologicoEdma;
	}
	public String getIdAmbienteTecnologicoEdmaPadre() {
		return idAmbienteTecnologicoEdmaPadre;
	}
	public void setIdAmbienteTecnologicoEdmaPadre(
			String idAmbienteTecnologicoEdmaPadre) {
		this.idAmbienteTecnologicoEdmaPadre = idAmbienteTecnologicoEdmaPadre;
	}
	public String getIdAmbienteTecnologico() {
		return idAmbienteTecnologico;
	}
	public void setIdAmbienteTecnologico(String idAmbienteTecnologico) {
		this.idAmbienteTecnologico = idAmbienteTecnologico;
	}
	public String getSiglaProdotto() {
		return siglaProdotto;
	}
	public void setSiglaProdotto(String siglaProdotto) {
		this.siglaProdotto = siglaProdotto;
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
	public String getArchitettura() {
		return architettura;
	}
	public void setArchitettura(String architettura) {
		this.architettura = architettura;
	}
	public String getInfrastruttura() {
		return infrastruttura;
	}
	public void setInfrastruttura(String infrastruttura) {
		this.infrastruttura = infrastruttura;
	}
	public String getSistemaOperativo() {
		return sistemaOperativo;
	}
	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}
	public String getTipoLayer() {
		return tipoLayer;
	}
	public void setTipoLayer(String tipoLayer) {
		this.tipoLayer = tipoLayer;
	}
	public String getVersioneSistemaOperativo() {
		return versioneSistemaOperativo;
	}
	public void setVersioneSistemaOperativo(String versioneSistemaOperativo) {
		this.versioneSistemaOperativo = versioneSistemaOperativo;
	}
	public String getDescrizioneAmbienteTecnologico() {
		return descrizioneAmbienteTecnologico;
	}
	public void setDescrizioneAmbienteTecnologico(
			String descrizioneAmbienteTecnologico) {
		this.descrizioneAmbienteTecnologico = descrizioneAmbienteTecnologico;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
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
	public Integer getProdottoFk() {
		return prodottoFk;
	}
	public void setProdottoFk(Integer prodottoFk) {
		this.prodottoFk = prodottoFk;
	}
	public Integer getModuloFk() {
		return moduloFk;
	}
	public void setModuloFk(Integer moduloFk) {
		this.moduloFk = moduloFk;
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
