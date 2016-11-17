package lispa.schedulers.bean.staging.sgr.siss.current;

import javax.annotation.Generated;

/**
 * SissCurrentProject is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SissCurrentProject {

    private Boolean cActive;

    private Boolean cDeleted;

    private java.sql.Timestamp cFinish;

    private String cId;

    private Boolean cIsLocal;

    private String cLocation;

    private java.sql.Timestamp cLockworkrecordsdate;

    private String cName;

    private String cPk;

    private Long cRev;

    private java.sql.Timestamp cStart;

    private String cTrackerprefix;

    private String cUri;

    private String fkLead;

    private String fkProjectgroup;

    private String fkUriLead;

    private String fkUriProjectgroup;
    
    public Boolean getcActive() {
        return cActive;
    }

    public void setcActive(Boolean cActive) {
        this.cActive = cActive;
    }

    public Boolean getcDeleted() {
        return cDeleted;
    }

    public void setcDeleted(Boolean cDeleted) {
        this.cDeleted = cDeleted;
    }

    public java.sql.Timestamp getcFinish() {
        return cFinish;
    }

    public void setcFinish(java.sql.Timestamp cFinish) {
        this.cFinish = cFinish;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Boolean getcIsLocal() {
        return cIsLocal;
    }

    public void setcIsLocal(Boolean cIsLocal) {
        this.cIsLocal = cIsLocal;
    }

    public String getcLocation() {
        return cLocation;
    }

    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    public java.sql.Timestamp getcLockworkrecordsdate() {
        return cLockworkrecordsdate;
    }

    public void setcLockworkrecordsdate(java.sql.Timestamp cLockworkrecordsdate) {
        this.cLockworkrecordsdate = cLockworkrecordsdate;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPk() {
        return cPk;
    }

    public void setcPk(String cPk) {
        this.cPk = cPk;
    }

    public Long getcRev() {
        return cRev;
    }

    public void setcRev(Long cRev) {
        this.cRev = cRev;
    }

    public java.sql.Timestamp getcStart() {
        return cStart;
    }

    public void setcStart(java.sql.Timestamp cStart) {
        this.cStart = cStart;
    }

    public String getcTrackerprefix() {
        return cTrackerprefix;
    }

    public void setcTrackerprefix(String cTrackerprefix) {
        this.cTrackerprefix = cTrackerprefix;
    }

    public String getcUri() {
        return cUri;
    }

    public void setcUri(String cUri) {
        this.cUri = cUri;
    }

    public String getFkLead() {
        return fkLead;
    }

    public void setFkLead(String fkLead) {
        this.fkLead = fkLead;
    }

    public String getFkProjectgroup() {
        return fkProjectgroup;
    }

    public void setFkProjectgroup(String fkProjectgroup) {
        this.fkProjectgroup = fkProjectgroup;
    }

    public String getFkUriLead() {
        return fkUriLead;
    }

    public void setFkUriLead(String fkUriLead) {
        this.fkUriLead = fkUriLead;
    }

    public String getFkUriProjectgroup() {
        return fkUriProjectgroup;
    }

    public void setFkUriProjectgroup(String fkUriProjectgroup) {
        this.fkUriProjectgroup = fkUriProjectgroup;
    }


}

