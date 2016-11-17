package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmStatoWorkitem is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmStatoWorkitem {

    private String cdStato;

    private Integer dmalmStatoWorkitemPk;

    private String dsStato;

    private java.sql.Timestamp dtCaricamento;

    private String origineStato;
    
    private String template;

    public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getCdStato() {
        return cdStato;
    }

    public void setCdStato(String cdStato) {
        this.cdStato = cdStato;
    }

    public Integer getDmalmStatoWorkitemPk() {
        return dmalmStatoWorkitemPk;
    }

    public void setDmalmStatoWorkitemPk(Integer dmalmStatoWorkitemPk) {
        this.dmalmStatoWorkitemPk = dmalmStatoWorkitemPk;
    }

    public String getDsStato() {
        return dsStato;
    }

    public void setDsStato(String dsStato) {
        this.dsStato = dsStato;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
    }

    public String getOrigineStato() {
        return origineStato;
    }

    public void setOrigineStato(String origineStato) {
        this.origineStato = origineStato;
    }

}

