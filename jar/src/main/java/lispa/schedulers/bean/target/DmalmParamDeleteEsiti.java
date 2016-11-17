package lispa.schedulers.bean.target;

import java.sql.Date;

public class DmalmParamDeleteEsiti {

	private int id;

	private Date dtesecuzioneOp;

	private String codOperazione;

	private String entita;

	private Date dtDa;

	private Date dtA;

	private Date delAllDa;

	private Date delAllA;

	private String entitaTarget;

	private String entitaSorgente;

	private Date dtCaricamento;

	private java.sql.Timestamp dtEsecEffettiva;

	public Date getDtesecuzioneOp() {
		return dtesecuzioneOp;
	}

	public void setDtesecuzioneOp(Date dtesecuzioneOp) {
		this.dtesecuzioneOp = dtesecuzioneOp;
	}

	public String getCodOperazione() {
		return codOperazione;
	}

	public void setCodOperazione(String codOperazione) {
		this.codOperazione = codOperazione;
	}

	public String getEntita() {
		return entita;
	}

	public void setEntita(String entita) {
		this.entita = entita;
	}

	public Date getDtDa() {
		return dtDa;
	}

	public void setDtDa(Date dtDa) {
		this.dtDa = dtDa;
	}

	public Date getDtA() {
		return dtA;
	}

	public void setDtA(Date dtA) {
		this.dtA = dtA;
	}

	public Date getDelAllDa() {
		return delAllDa;
	}

	public void setDelAllDa(Date delAllDa) {
		this.delAllDa = delAllDa;
	}

	public Date getDelAllA() {
		return delAllA;
	}

	public void setDelAllA(Date delAllA) {
		this.delAllA = delAllA;
	}

	public String getEntitaTarget() {
		return entitaTarget;
	}

	public void setEntitaTarget(String entitaTarget) {
		this.entitaTarget = entitaTarget;
	}

	public String getEntitaSorgente() {
		return entitaSorgente;
	}

	public void setEntitaSorgente(String entitaSorgente) {
		this.entitaSorgente = entitaSorgente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDtCaricamento() {
		return dtCaricamento;
	}

	public void setDtCaricamento(Date dtCaricamento) {
		this.dtCaricamento = dtCaricamento;
	}

	public java.sql.Timestamp getDtEsecEffettiva() {
		return dtEsecEffettiva;
	}

	public void setDtEsecEffettiva(java.sql.Timestamp dtEsecEffettiva) {
		this.dtEsecEffettiva = dtEsecEffettiva;
	}

}
