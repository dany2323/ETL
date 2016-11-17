package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmClassificatori is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmClassificatori {

    private String codiceClassificatore;

    private Integer dmalmClassificatoriPk;

    private java.sql.Timestamp dtCaricamento;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtInserimentoRecord;

    private Integer idOreste;

    private String tipoClassificatore;

        
    public java.sql.Timestamp getDtInserimentoRecord() {
		return dtInserimentoRecord;
	}

	public void setDtInserimentoRecord(java.sql.Timestamp dtInserimentoRecord) {
		this.dtInserimentoRecord = dtInserimentoRecord;
	}

	public String getCodiceClassificatore() {
        return codiceClassificatore;
    }

    public void setCodiceClassificatore(String codiceClassificatore) {
        this.codiceClassificatore = codiceClassificatore;
    }

    public Integer getDmalmClassificatoriPk() {
        return dmalmClassificatoriPk;
    }

    public void setDmalmClassificatoriPk(Integer dmalmClassificatoriPk) {
        this.dmalmClassificatoriPk = dmalmClassificatoriPk;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
    }

    public java.sql.Timestamp getDtFineValidita() {
        return dtFineValidita;
    }

    public void setDtFineValidita(java.sql.Timestamp dtFineValidita) {
        this.dtFineValidita = dtFineValidita;
    }

    public java.sql.Timestamp getDtInizioValidita() {
        return dtInizioValidita;
    }

    public void setDtInizioValidita(java.sql.Timestamp dtInizioValidita) {
        this.dtInizioValidita = dtInizioValidita;
    }

    public Integer getIdOreste() {
        return idOreste;
    }

    public void setIdOreste(Integer idOreste) {
        this.idOreste = idOreste;
    }

    public String getTipoClassificatore() {
        return tipoClassificatore;
    }

    public void setTipoClassificatore(String tipoClassificatore) {
        this.tipoClassificatore = tipoClassificatore;
    }

}

