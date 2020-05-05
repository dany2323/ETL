package lispa.schedulers.bean.fonte.sgr.history;

import javax.annotation.Generated;

/**
 * Attachment is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class HistoryAttachment {

    private java.sql.Blob cContent;

    private Boolean cDeleted;

    private String cFilename;

    private String cId;

    private Boolean cIsLocal;

    private Long cLength;

    private String cPk;

    private Long cRev;

    private String cTitle;

    private java.sql.Timestamp cUpdated;

    private String cUri;

    private String cUrl;

    private String fkAuthor;

    private String fkProject;

    private String fkUriAuthor;

    private String fkUriProject;

    private String fkUriWorkitem;

    private String fkWorkitem;

    public java.sql.Blob getcContent() {
        return cContent;
    }

    public void setcContent(java.sql.Blob cContent) {
        this.cContent = cContent;
    }

    public Boolean getcDeleted() {
        return cDeleted;
    }

    public void setcDeleted(Boolean cDeleted) {
        this.cDeleted = cDeleted;
    }

    public String getcFilename() {
        return cFilename;
    }

    public void setcFilename(String cFilename) {
        this.cFilename = cFilename;
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

    public Long getcLength() {
        return cLength;
    }

    public void setcLength(Long cLength) {
        this.cLength = cLength;
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

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public java.sql.Timestamp getcUpdated() {
        return cUpdated;
    }

    public void setcUpdated(java.sql.Timestamp cUpdated) {
        this.cUpdated = cUpdated;
    }

    public String getcUri() {
        return cUri;
    }

    public void setcUri(String cUri) {
        this.cUri = cUri;
    }

    public String getcUrl() {
        return cUrl;
    }

    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
    }

    public String getFkAuthor() {
        return fkAuthor;
    }

    public void setFkAuthor(String fkAuthor) {
        this.fkAuthor = fkAuthor;
    }

    public String getFkProject() {
        return fkProject;
    }

    public void setFkProject(String fkProject) {
        this.fkProject = fkProject;
    }

    public String getFkUriAuthor() {
        return fkUriAuthor;
    }

    public void setFkUriAuthor(String fkUriAuthor) {
        this.fkUriAuthor = fkUriAuthor;
    }

    public String getFkUriProject() {
        return fkUriProject;
    }

    public void setFkUriProject(String fkUriProject) {
        this.fkUriProject = fkUriProject;
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

