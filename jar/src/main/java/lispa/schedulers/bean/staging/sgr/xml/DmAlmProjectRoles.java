package lispa.schedulers.bean.staging.sgr.xml;

import javax.annotation.Generated;

/**
 * DmAlmProjectRoles is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmProjectRoles {

    private java.sql.Timestamp dataCaricamento;

    private String descrizione;

    private Double dmAlmProjectRolesPk;

    private String ruolo;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getDmAlmProjectRolesPk() {
        return dmAlmProjectRolesPk;
    }

    public void setDmAlmProjectRolesPk(Double dmAlmProjectRolesPk) {
        this.dmAlmProjectRolesPk = dmAlmProjectRolesPk;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

}

