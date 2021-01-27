package lispa.schedulers.bean.fonte.mps;

import javax.annotation.Generated;

/**
 * DmalmStgMpsVerbali is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmMpsVerbali {

	private String codVerbale;
	
	private java.sql.Timestamp dataVerbale;
	
	private java.sql.Timestamp dataFirma;
	
	private String note;
	
	private String conforme;
	
	private String tipoVerbale;
	
	private String statoVerbale;
	
	private Integer totaleVerbale;
	
	private Integer fatturatoVerbale;
	
	private String prossimoFirmatario;
	
	private String firmaDigitale;
	
	private Integer idVerbaleValidazione;

	public String getCodVerbale() {
		return codVerbale;
	}

	public void setCodVerbale(String codVerbale) {
		this.codVerbale = codVerbale;
	}

	public java.sql.Timestamp getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(java.sql.Timestamp dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

	public java.sql.Timestamp getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(java.sql.Timestamp dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getConforme() {
		return conforme;
	}

	public void setConforme(String conforme) {
		this.conforme = conforme;
	}

	public String getTipoVerbale() {
		return tipoVerbale;
	}

	public void setTipoVerbale(String tipoVerbale) {
		this.tipoVerbale = tipoVerbale;
	}

	public String getStatoVerbale() {
		return statoVerbale;
	}

	public void setStatoVerbale(String statoVerbale) {
		this.statoVerbale = statoVerbale;
	}

	public Integer getTotaleVerbale() {
		return totaleVerbale;
	}

	public void setTotaleVerbale(Integer totaleVerbale) {
		this.totaleVerbale = totaleVerbale;
	}

	public Integer getFatturatoVerbale() {
		return fatturatoVerbale;
	}

	public void setFatturatoVerbale(Integer fatturatoVerbale) {
		this.fatturatoVerbale = fatturatoVerbale;
	}

	public String getProssimoFirmatario() {
		return prossimoFirmatario;
	}

	public void setProssimoFirmatario(String prossimoFirmatario) {
		this.prossimoFirmatario = prossimoFirmatario;
	}

	public String getFirmaDigitale() {
		return firmaDigitale;
	}

	public void setFirmaDigitale(String firmaDigitale) {
		this.firmaDigitale = firmaDigitale;
	}

	public Integer getIdVerbaleValidazione() {
		return idVerbaleValidazione;
	}

	public void setIdVerbaleValidazione(Integer idVerbaleValidazione) {
		this.idVerbaleValidazione = idVerbaleValidazione;
	}

}
