package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmClassificatori is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmUserElPersonale {

	private String idUser; 
	
	private Integer dmalmPersonalePk;
	
	private java.sql.Timestamp dtCaricamento;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Integer getDmalmPersonalePk() {
		return dmalmPersonalePk;
	}

	public void setDmalmPersonalePk(Integer dmalmPersonalePk) {
		this.dmalmPersonalePk = dmalmPersonalePk;
	}

	public java.sql.Timestamp getDtCaricamento() {
		return dtCaricamento;
	}

	public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
		this.dtCaricamento = dtCaricamento;
	}

    
}
