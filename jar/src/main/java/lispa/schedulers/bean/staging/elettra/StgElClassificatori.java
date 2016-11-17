package lispa.schedulers.bean.staging.elettra;

import java.util.Date;

public class StgElClassificatori {
	private Integer classificatorePk;
	private String idClassificatore;
	private String codiceClassificatore;
    private String tipoClassificatore;
    private Date dataCaricamento;
    
	public Integer getClassificatorePk() {
		return classificatorePk;
	}
	public void setClassificatorePk(Integer classificatorePk) {
		this.classificatorePk = classificatorePk;
	}
	public String getIdClassificatore() {
		return idClassificatore;
	}
	public void setIdClassificatore(String idClassificatore) {
		this.idClassificatore = idClassificatore;
	}
	public String getCodiceClassificatore() {
		return codiceClassificatore;
	}
	public void setCodiceClassificatore(String codiceClassificatore) {
		this.codiceClassificatore = codiceClassificatore;
	}
	public String getTipoClassificatore() {
		return tipoClassificatore;
	}
	public void setTipoClassificatore(String tipoClassificatore) {
		this.tipoClassificatore = tipoClassificatore;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
}
