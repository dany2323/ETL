package lispa.schedulers.bean.target.elettra;

import java.util.Date;

public class DmalmElAmbienteTecnologicoClassificatori {

	private Integer ambienteTecnologicoClassificatoriPk;
	private String idAmbienteTecnologico;
	private String nomeAmbienteTecnologico;
	private String descrizioneAmbienteTecnologico;
	private String idClassificatore;
	private String nomeClassificatore;
	private String descrizioneClassificatore;
	private String annullato;
	private Date dataAnnullamento;
	private Date dataCaricamento;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	
	public Integer getAmbienteTecnologicoClassificatoriPk() {
		return ambienteTecnologicoClassificatoriPk;
	}
	public void setAmbienteTecnologicoClassificatoriPk(
			Integer ambienteTecnologicoClassificatoriPk) {
		this.ambienteTecnologicoClassificatoriPk = ambienteTecnologicoClassificatoriPk;
	}
	public String getIdAmbienteTecnologico() {
		return idAmbienteTecnologico;
	}
	public void setIdAmbienteTecnologico(String idAmbienteTecnologico) {
		this.idAmbienteTecnologico = idAmbienteTecnologico;
	}
	public String getNomeAmbienteTecnologico() {
		return nomeAmbienteTecnologico;
	}
	public void setNomeAmbienteTecnologico(String nomeAmbienteTecnologico) {
		this.nomeAmbienteTecnologico = nomeAmbienteTecnologico;
	}
	public String getDescrizioneAmbienteTecnologico() {
		return descrizioneAmbienteTecnologico;
	}
	public void setDescrizioneAmbienteTecnologico(
			String descrizioneAmbienteTecnologico) {
		this.descrizioneAmbienteTecnologico = descrizioneAmbienteTecnologico;
	}
	public String getIdClassificatore() {
		return idClassificatore;
	}
	public void setIdClassificatore(String idClassificatore) {
		this.idClassificatore = idClassificatore;
	}
	public String getNomeClassificatore() {
		return nomeClassificatore;
	}
	public void setNomeClassificatore(String nomeClassificatore) {
		this.nomeClassificatore = nomeClassificatore;
	}
	public String getDescrizioneClassificatore() {
		return descrizioneClassificatore;
	}
	public void setDescrizioneClassificatore(String descrizioneClassificatore) {
		this.descrizioneClassificatore = descrizioneClassificatore;
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
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
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
