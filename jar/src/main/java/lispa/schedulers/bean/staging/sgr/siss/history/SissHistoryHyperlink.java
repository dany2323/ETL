package lispa.schedulers.bean.staging.sgr.siss.history;

import javax.annotation.Generated;

/**
 * DmalmSissHistoryHyperlink is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SissHistoryHyperlink {

    private String cRole;

    private String cUri;

    private java.sql.Timestamp dataCaricamento;

    private String fkPWorkitem;

    private String fkUriPWorkitem;

    private Long sissHistoryHyperlinkPk;

    public String getcRole() {
        return cRole;
    }

    public void setcRole(String cRole) {
        this.cRole = cRole;
    }

    public String getcUri() {
        return cUri;
    }

    public void setcUri(String cUri) {
        this.cUri = cUri;
    }

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public String getFkPWorkitem() {
        return fkPWorkitem;
    }

    public void setFkPWorkitem(String fkPWorkitem) {
        this.fkPWorkitem = fkPWorkitem;
    }

    public String getFkUriPWorkitem() {
        return fkUriPWorkitem;
    }

    public void setFkUriPWorkitem(String fkUriPWorkitem) {
        this.fkUriPWorkitem = fkUriPWorkitem;
    }

    public Long getSissHistoryHyperlinkPk() {
        return sissHistoryHyperlinkPk;
    }

    public void setSissHistoryHyperlinkPk(Long sissHistoryHyperlinkPk) {
        this.sissHistoryHyperlinkPk = sissHistoryHyperlinkPk;
    }

}

