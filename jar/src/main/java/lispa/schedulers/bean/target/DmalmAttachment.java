package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmAttachment is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmAttachment {
	
	private String stgPk;

    private String cdAttachment;

    private Integer dimensioneFile;

    private Integer dmalmFkWorkitem01;

    private String dmalmWorkitemType;

    private java.sql.Timestamp dtCaricamento;

    private java.sql.Timestamp dtModificaAttachment;

    private String idProject;

    private String nomeAutore;

    private String nomeFile;

    private Boolean statoCancellato;

    private String titolo;

    private String url;

    public String getCdAttachment() {
        return cdAttachment;
    }

    public void setCdAttachment(String cdAttachment) {
        this.cdAttachment = cdAttachment;
    }

    public Integer getDimensioneFile() {
        return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {
        this.dimensioneFile = dimensioneFile;
    }

    public Integer getDmalmFkWorkitem01() {
        return dmalmFkWorkitem01;
    }

    public void setDmalmFkWorkitem01(Integer dmalmFkWorkitem01) {
        this.dmalmFkWorkitem01 = dmalmFkWorkitem01;
    }

    public String getDmalmWorkitemType() {
        return dmalmWorkitemType;
    }

    public void setDmalmWorkitemType(String dmalmWorkitemType) {
        this.dmalmWorkitemType = dmalmWorkitemType;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
    }

    public java.sql.Timestamp getDtModificaAttachment() {
        return dtModificaAttachment;
    }

    public void setDtModificaAttachment(java.sql.Timestamp dtModificaAttachment) {
        this.dtModificaAttachment = dtModificaAttachment;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getNomeAutore() {
        return nomeAutore;
    }

    public void setNomeAutore(String nomeAutore) {
        this.nomeAutore = nomeAutore;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public Boolean getStatoCancellato() {
        return statoCancellato;
    }

    public void setStatoCancellato(Boolean statoCancellato) {
        this.statoCancellato = statoCancellato;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

}

