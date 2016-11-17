package lispa.schedulers.bean.target.elettra;

import java.util.Date;

public class DmalmElClassificatori {
	private Integer classificatorePk;
	private String idClassificatore;
	private String tipoClassificatore;
	private String codiceClassificatore;
    private Date dataCaricamento;
    private Date dataInizioValidita;
	private Date dataFineValidita;
	
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
	public String getTipoClassificatore() {
		return tipoClassificatore;
	}
	public void setTipoClassificatore(String tipoClassificatore) {
		this.tipoClassificatore = tipoClassificatore;
	}
	public String getCodiceClassificatore() {
		return codiceClassificatore;
	}
	public void setCodiceClassificatore(String codiceClassificatore) {
		this.codiceClassificatore = codiceClassificatore;
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
