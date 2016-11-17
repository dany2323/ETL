package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * Total is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class Total {

    private String codice;

    private Integer dmalmPk;

    private java.sql.Timestamp dtStoricizzazione;

    private String idRepository;

    private String stgPk;

    private String type;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Integer getDmalmPk() {
        return dmalmPk;
    }

    public void setDmalmPk(Integer dmalmPk) {
        this.dmalmPk = dmalmPk;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getStgPk() {
        return stgPk;
    }

    public void setStgPk(String stgPk) {
        this.stgPk = stgPk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

