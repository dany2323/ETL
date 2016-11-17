package lispa.schedulers.bean.staging.oreste;

import javax.annotation.Generated;

/**
 * StgClassificatori is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class StgClassificatori {

    private String codiceClassificatore;

    private String codiceTipologia;

    private java.sql.Timestamp dataCaricamento;

    private Double id;

    public String getCodiceClassificatore() {
        return codiceClassificatore;
    }

    public void setCodiceClassificatore(String codiceClassificatore) {
        this.codiceClassificatore = codiceClassificatore;
    }

    public String getCodiceTipologia() {
        return codiceTipologia;
    }

    public void setCodiceTipologia(String codiceTipologia) {
        this.codiceTipologia = codiceTipologia;
    }

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

}

