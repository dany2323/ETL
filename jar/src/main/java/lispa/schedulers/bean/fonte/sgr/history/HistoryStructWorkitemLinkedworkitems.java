package lispa.schedulers.bean.fonte.sgr.history;

import javax.annotation.Generated;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class HistoryStructWorkitemLinkedworkitems {

    private String cRevision;

    private String cRole;

    private Boolean cSuspect;

    private String fkPWorkitem;

    private String fkUriPWorkitem;

    private String fkUriWorkitem;

    private String fkWorkitem;

    public String getcRevision() {
        return cRevision;
    }

    public void setcRevision(String cRevision) {
        this.cRevision = cRevision;
    }

    public String getcRole() {
        return cRole;
    }

    public void setcRole(String cRole) {
        this.cRole = cRole;
    }

    public Boolean getcSuspect() {
        return cSuspect;
    }

    public void setcSuspect(Boolean cSuspect) {
        this.cSuspect = cSuspect;
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

    public String getFkUriWorkitem() {
        return fkUriWorkitem;
    }

    public void setFkUriWorkitem(String fkUriWorkitem) {
        this.fkUriWorkitem = fkUriWorkitem;
    }

    public String getFkWorkitem() {
        return fkWorkitem;
    }

    public void setFkWorkitem(String fkWorkitem) {
        this.fkWorkitem = fkWorkitem;
    }

}

