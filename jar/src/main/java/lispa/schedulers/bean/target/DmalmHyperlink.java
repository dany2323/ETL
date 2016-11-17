package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmHyperlink is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmHyperlink {
	
	private String stgPk;

    private Integer dmalmFkWorkitem01;

    private String dmalmWorkitemType;

    private java.sql.Timestamp dtCaricamento;

    private String ruolo;

    private String uri;

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

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

}

