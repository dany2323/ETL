package lispa.schedulers.bean.target.sfera;

import javax.annotation.Generated;

/**
 * DmalmAsmProdotto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmAsmProdotto {
    private Integer dmalmAsmPk;

    private Integer dmalmProdottoSeq;
    
    private java.sql.Timestamp dataFineValidita;
    
    private java.sql.Timestamp dataInizioValidita;
    
	public Integer getDmalmAsmPk() {
		return dmalmAsmPk;
	}

	public void setDmalmAsmPk(Integer dmalmAsmPk) {
		this.dmalmAsmPk = dmalmAsmPk;
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
