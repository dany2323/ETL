package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmProjectRolesSgr is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProjectRolesSgr {

    private Integer dmalmProjectRolesPk;

    private java.sql.Timestamp dtCaricamento;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;

    private String ruolo;

    private String tipologiaProject;

    public Integer getDmalmProjectRolesPk() {
        return dmalmProjectRolesPk;
    }

    public void setDmalmProjectRolesPk(Integer dmalmProjectRolesPk) {
        this.dmalmProjectRolesPk = dmalmProjectRolesPk;
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

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getTipologiaProject() {
        return tipologiaProject;
    }

    public void setTipologiaProject(String tipologiaProject) {
        this.tipologiaProject = tipologiaProject;
    }

}

