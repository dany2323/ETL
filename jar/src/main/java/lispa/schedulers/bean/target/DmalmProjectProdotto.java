package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmProjectProdotto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProjectProdotto {
	private Integer dmalmProjectPk;

	private Integer dmalmProdottoSeq;

	private java.sql.Timestamp dataFineValidita;

	private java.sql.Timestamp dataInizioValidita;

	public Integer getDmalmProjectPk() {
		return dmalmProjectPk;
	}

	public void setDmalmProjectPk(Integer dmalmProjectPk) {
		this.dmalmProjectPk = dmalmProjectPk;
	}

	public Integer getDmalmProdottoSeq() {
		return dmalmProdottoSeq;
	}

	public void setDmalmProdottoSeq(Integer dmalmProdottoSeq) {
		this.dmalmProdottoSeq = dmalmProdottoSeq;
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
}
