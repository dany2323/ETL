package lispa.schedulers.bean.staging.sgr.xml;

import javax.annotation.Generated;

/**
 * DmAlmStatoWorkitem is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmStatoWorkitem {

    private java.sql.Timestamp dataCaricamento;

    private String descrizione;

    private Double dmAlmStatoWorkitemPk;

    private String iconUrl;

    private String id;

    private String name;

    private Double sortOrder;
    
    private String template;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getDmAlmStatoWorkitemPk() {
        return dmAlmStatoWorkitemPk;
    }

    public void setDmAlmStatoWorkitemPk(Double dmAlmStatoWorkitemPk) {
        this.dmAlmStatoWorkitemPk = dmAlmStatoWorkitemPk;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Double getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

}

