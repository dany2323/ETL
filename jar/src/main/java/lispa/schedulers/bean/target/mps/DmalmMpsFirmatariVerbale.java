package lispa.schedulers.bean.target.mps;

import javax.annotation.Generated;

/**
 * DmalmMpsFirmatariVerbale is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")

public class DmalmMpsFirmatariVerbale {

	private Integer idVerbaleValidazione;

	private Integer idUtente;
    
	private String firmatarioVerbale;
    
	private String tipologiaResponsabile;
	
	private Integer ordineFirma;
    
	private String firmato;
    
	private java.sql.Timestamp dataFirma;
	
	private Integer idEnte;

	private String ente;
	
	public Integer getIdVerbaleValidazione() {
		return idVerbaleValidazione;
	}
	public void setIdVerbaleValidazione(Integer idVerbaleValidazione) {
		this.idVerbaleValidazione = idVerbaleValidazione;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	public String getFirmatarioVerbale() {
		return firmatarioVerbale;
	}
	public void setFirmatarioVerbale(String firmatarioVerbale) {
		this.firmatarioVerbale = firmatarioVerbale;
	}
	public String getTipologiaResponsabile() {
		return tipologiaResponsabile;
	}
	public void setTipologiaResponsabile(String tipologiaResponsabile) {
		this.tipologiaResponsabile = tipologiaResponsabile;
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
