package lispa.schedulers.bean.staging.sgr.siss.current;

import javax.annotation.Generated;

/**
 * SissCurrentWorkitemLinked is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SissCurrentWorkitemLinked {

    private String cRevision;

    private String cRole;

    private Integer cSuspect;

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

    public Integer getcSuspect() {
        return cSuspect;
    }

    public void setcSuspect(Integer cSuspect) {
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

