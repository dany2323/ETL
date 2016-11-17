package lispa.schedulers.bean.target.mps;

import javax.annotation.Generated;

/**
 * DmalmMpsResponsabiliContratto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")

public class DmalmMpsResponsabiliContratto {
	
	private Integer idContratto;
	private Integer idUtente;
	private String responsabile;
	private String desTipologiaResponsabile;
	private String firmatario;
	private Integer ordineFirma;
	private String firmato;
	private java.sql.Timestamp dataFirma;
	private Integer idEnte;
	private String ente;

	public Integer getIdContratto() {
		return idContratto;
	}
	public void setIdContratto(Integer idContratto) {
		this.idContratto = idContratto;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	public String getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
	public String getDesTipologiaResponsabile() {
		return desTipologiaResponsabile;
	}
	public void setDesTipologiaResponsabile(String desTipologiaResponsabile) {
		this.desTipologiaResponsabile = desTipologiaResponsabile;
	}
	public String getFirmatario() {
		return firmatario;
	}
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	public Integer getOrdineFirma() {
		return ordineFirma;
	}
	public void setOrdineFirma(Integer ordineFirma) {
		this.ordineFirma = ordineFirma;
	}
	public String getFirmato() {
		return firmato;
	}
	public void setFirmato(String firmato) {
		this.firmato = firmato;
	}
	public java.sql.Timestamp getDataFirma() {
		return dataFirma;
	}
	public void setDataFirma(java.sql.Timestamp dataFirma) {
		this.dataFirma = dataFirma;
	}
	public Integer getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}

}
