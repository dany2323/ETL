package lispa.schedulers.bean.staging;

import javax.annotation.Generated;

/**
 * ErroriCaricamentoDmAlm is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class ErroriCaricamentoDmAlm {

    private java.sql.Timestamp dataCaricamento;

    private String entitaSorgente;

    private String entitaTarget;

    private String flagErrore;

    private String motivoErrore;

    private String recordErrore;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public String getEntitaSorgente() {
        return entitaSorgente;
    }

    public void setEntitaSorgente(String entitaSorgente) {
        this.entitaSorgente = entitaSorgente;
    }

    public String getEntitaTarget() {
        return entitaTarget;
    }

    public void setEntitaTarget(String entitaTarget) {
        this.entitaTarget = entitaTarget;
    }

    public String getFlagErrore() {
        return flagErrore;
    }

    public void setFlagErrore(String flagErrore) {
        this.flagErrore = flagErrore;
    }

    public String getMotivoErrore() {
        return motivoErrore;
    }

    public void setMotivoErrore(String motivoErrore) {
        this.motivoErrore = motivoErrore;
    }

    public String getRecordErrore() {
        return recordErrore;
    }

    public void setRecordErrore(String recordErrore) {
        this.recordErrore = recordErrore;
    }

}

