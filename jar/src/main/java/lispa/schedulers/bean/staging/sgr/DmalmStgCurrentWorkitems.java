package lispa.schedulers.bean.staging.sgr;

import javax.annotation.Generated;

/**
 * DmalmStgCurrentWorkitems is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmStgCurrentWorkitems {
	
	private String stgPk;

	private String codice;

    private String idRepository;

    private String type;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

}

