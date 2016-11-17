package lispa.schedulers.bean.fonte.sgr.siss.history;

import javax.annotation.Generated;

/**
 * StructWorkitemHyperlinks is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SissHistoryHyperlink {

    private String cRole;

    private String cUrl;

    private String fkPWorkitem;

    private String fkUriPWorkitem;

    public String getcRole() {
        return cRole;
    }

    public void setcRole(String cRole) {
        this.cRole = cRole;
    }

    public String getcUrl() {
        return cUrl;
    }

    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
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

}

