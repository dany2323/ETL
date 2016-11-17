package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmGerarchiaUo is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmGerarchiaUo {

    private java.sql.Timestamp dataCaricamento;

    private java.sql.Timestamp dmalmDtFineValidita;

    private java.sql.Timestamp dmalmDtInizioValidita;

    private Short livello;

    private Double pkPredecessore;

    private String struttura;

    private String uoPrecessore;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public java.sql.Timestamp getDmalmDtFineValidita() {
        return dmalmDtFineValidita;
    }

    public void setDmalmDtFineValidita(java.sql.Timestamp dmalmDtFineValidita) {
        this.dmalmDtFineValidita = dmalmDtFineValidita;
    }

    public java.sql.Timestamp getDmalmDtInizioValidita() {
        return dmalmDtInizioValidita;
    }

    public void setDmalmDtInizioValidita(java.sql.Timestamp dmalmDtInizioValidita) {
        this.dmalmDtInizioValidita = dmalmDtInizioValidita;
    }

    public Short getLivello() {
        return livello;
    }

    public void setLivello(Short livello) {
        this.livello = livello;
    }

    public Double getPkPredecessore() {
        return pkPredecessore;
    }

    public void setPkPredecessore(Double pkPredecessore) {
        this.pkPredecessore = pkPredecessore;
    }

    public String getStruttura() {
        return struttura;
    }

    public void setStruttura(String struttura) {
        this.struttura = struttura;
    }

    public String getUoPrecessore() {
        return uoPrecessore;
    }

    public void setUoPrecessore(String uoPrecessore) {
        this.uoPrecessore = uoPrecessore;
    }

}

