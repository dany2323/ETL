package lispa.schedulers.bean.staging.sgr.sire.current;

import javax.annotation.Generated;

/**
 * SireCurrentProjectgroup is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireCurrentProjectgroup {

    private Boolean cDeleted;

    private Boolean cIsLocal;

    private String cLocation;

    private String cName;

    private String cPk;

    private Long cRev;

    private String cUri;

    private String fkParent;

    private String fkUriParent;

    public Boolean getcDeleted() {
        return cDeleted;
    }

    public void setcDeleted(Boolean cDeleted) {
        this.cDeleted = cDeleted;
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

    public String getcUri() {
        return cUri;
    }

    public void setcUri(String cUri) {
        this.cUri = cUri;
    }

    public String getFkParent() {
        return fkParent;
    }

    public void setFkParent(String fkParent) {
        this.fkParent = fkParent;
    }

    public String getFkUriParent() {
        return fkUriParent;
    }

    public void setFkUriParent(String fkUriParent) {
        this.fkUriParent = fkUriParent;
    }

}

