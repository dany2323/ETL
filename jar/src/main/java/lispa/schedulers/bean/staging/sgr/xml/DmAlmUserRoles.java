package lispa.schedulers.bean.staging.sgr.xml;

import java.sql.Timestamp;

import javax.annotation.Generated;

/**
 * DmAlmUserRoles is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmUserRoles {

	private java.sql.Timestamp dataCaricamento;

	private Double dmAlmUserRolesPk;

	private String idProject;

	private String ruolo;

	private String siglaProject;

	private String utente;

	private Timestamp dataModifica;

	public java.sql.Timestamp getDataCaricamento() {
		return dataCaricamento;
	}


	public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Timestamp getDt_modifica() {
		return dataModifica;
	}

	public void setDt_modifica(Timestamp dt_modifica) {
		this.dataModifica = dt_modifica;
	}

	public Double getDmAlmUserRolesPk() {
		return dmAlmUserRolesPk;
	}

	public void setDmAlmUserRolesPk(Double dmAlmUserRolesPk) {
		this.dmAlmUserRolesPk = dmAlmUserRolesPk;
	}

	public String getNameProject() {
		return idProject;
	}

	public void setNameProject(String nameProject) {
		this.idProject = nameProject;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getSiglaProject() {
		return siglaProject;
	}

	public void setSiglaProject(String siglaProject) {
		this.siglaProject = siglaProject;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

}

