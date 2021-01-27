package lispa.schedulers.bean.fonte.sgr.xml;

import java.sql.Timestamp;
import javax.annotation.Generated;

/**
 * DmAlmUserRoles is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmUserRolesSgr {

	private String origine;

	private String userId;
	
	private String ruolo;

	private String repository;

	private Timestamp dtModifica;

	public Timestamp getDtModifica() {
		return dtModifica;
	}

	public void setDtModifica(Timestamp dtModifica) {
		this.dtModifica = dtModifica;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}