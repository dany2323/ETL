package lispa.schedulers.bean.target;

public class DmalmProjectProdottiArchitetture {
	private Long dmalmProjectPk;
	private Integer dmalmProdottoPk;
	private java.sql.Timestamp dataFineValidita;
	
	public Long getDmalmProjectPk() {
		return dmalmProjectPk;
	}
	public void setDmalmProjectPk(Long dmalmProjectPk) {
		this.dmalmProjectPk = dmalmProjectPk;
	}
	public Integer getDmalmProdottoPk() {
		return dmalmProdottoPk;
	}
	public void setDmalmProdottoPk(Integer dmalmProdottoPk) {
		this.dmalmProdottoPk = dmalmProdottoPk;
	}
	public java.sql.Timestamp getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(java.sql.Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public java.sql.Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(java.sql.Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	private java.sql.Timestamp dataInizioValidita;
}
