package lispa.schedulers.bean.fonte.sgr.sire.current;


import javax.annotation.Generated;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireCurrentStructWorkitemLinkedworkitems {

    private String cRevision;

    private String cRole;

    private Integer cSuspect;

    private Long fkPWorkitem;

    private Long fkUriPWorkitem;

    private Long fkUriWorkitem;

    private Long fkWorkitem;

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

    public Long getFkPWorkitem() {
        return fkPWorkitem;
    }

    public void setFkPWorkitem(Long fkPWorkitem) {
        this.fkPWorkitem = fkPWorkitem;
    }

    public Long getFkUriPWorkitem() {
        return fkUriPWorkitem;
    }

    public void setFkUriPWorkitem(Long fkUriPWorkitem) {
        this.fkUriPWorkitem = fkUriPWorkitem;
    }

    public Long getFkUriWorkitem() {
        return fkUriWorkitem;
    }

    public void setFkUriWorkitem(Long fkUriWorkitem) {
        this.fkUriWorkitem = fkUriWorkitem;
    }

    public Long getFkWorkitem() {
        return fkWorkitem;
    }

    public void setFkWorkitem(Long fkWorkitem) {
        this.fkWorkitem = fkWorkitem;
    }
}
