package lispa.schedulers.bean.staging;

import javax.annotation.Generated;

/**
 * DmalmEsitiCaricamenti is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmEsitiCaricamenti {

    private java.sql.Timestamp dataCaricamento;

    private java.sql.Timestamp dtFineCaricamento;

    private java.sql.Timestamp dtInizioCaricamento;

    private String entitaTarget;

    private Integer righeCaricate;

    private Integer righeErrate;

    private Integer righeModificate;

    private Integer righeScartate;

    private String statoEsecuzione;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public java.sql.Timestamp getDtFineCaricamento() {
        return dtFineCaricamento;
    }

    public void setDtFineCaricamento(java.sql.Timestamp dtFineCaricamento) {
        this.dtFineCaricamento = dtFineCaricamento;
    }

    public java.sql.Timestamp getDtInizioCaricamento() {
        return dtInizioCaricamento;
    }

    public void setDtInizioCaricamento(java.sql.Timestamp dtInizioCaricamento) {
        this.dtInizioCaricamento = dtInizioCaricamento;
    }

    public String getEntitaTarget() {
        return entitaTarget;
    }

    public void setEntitaTarget(String entitaTarget) {
        this.entitaTarget = entitaTarget;
    }

    public Integer getRigheCaricate() {
        return righeCaricate;
    }

    public void setRigheCaricate(Integer righeCaricate) {
        this.righeCaricate = righeCaricate;
    }

    public Integer getRigheErrate() {
        return righeErrate;
    }

    public void setRigheErrate(Integer righeErrate) {
        this.righeErrate = righeErrate;
    }

    public Integer getRigheModificate() {
        return righeModificate;
    }

    public void setRigheModificate(Integer righeModificate) {
        this.righeModificate = righeModificate;
    }

    public Integer getRigheScartate() {
        return righeScartate;
    }

    public void setRigheScartate(Integer righeScartate) {
        this.righeScartate = righeScartate;
    }

    public String getStatoEsecuzione() {
        return statoEsecuzione;
    }

    public void setStatoEsecuzione(String statoEsecuzione) {
        this.statoEsecuzione = statoEsecuzione;
    }

}

