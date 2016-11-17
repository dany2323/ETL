package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmLinkedWorkitems is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmLinkedWorkitems {
	private String codiceWiFiglio;

	private String codiceWiPadre;

	private Integer fkWiFiglio;

	private Integer fkWiPadre;

	private String ruolo;

	private String tipoWiFiglio;

	private String tipoWiPadre;

	private String pkPadre;

	private String pkFiglio;

	private java.sql.Timestamp dtCaricamento;

	private Integer linkedWorkitemsPk;
	
	private String titoloWiPadre;

	private String titoloWiFiglio;

	private String uriWiPadre;

	private String uriWiFiglio;

	private String idRepositoryFiglio;

	private String idRepositoryPadre;

	private String codiceProjectFiglio;

	private String codiceProjectPadre;

	public String getUriWiPadre() {
		return uriWiPadre;
	}

	public void setUriWiPadre(String uriWiPadre) {
		this.uriWiPadre = uriWiPadre;
	}

	public String getUriWiFiglio() {
		return uriWiFiglio;
	}

	public void setUriWiFiglio(String uriWiFiglio) {
		this.uriWiFiglio = uriWiFiglio;
	}

	public String getTitoloWiPadre() {
		return titoloWiPadre;
	}

	public void setTitoloWiPadre(String titoloWiPadre) {
		this.titoloWiPadre = titoloWiPadre;
	}

	public String getTitoloWiFiglio() {
		return titoloWiFiglio;
	}

	public void setTitoloWiFiglio(String titoloWiFiglio) {
		this.titoloWiFiglio = titoloWiFiglio;
	}

	public String getCodiceWiFiglio() {
		return codiceWiFiglio;
	}

	public void setCodiceWiFiglio(String codiceWiFiglio) {
		this.codiceWiFiglio = codiceWiFiglio;
	}

	public String getCodiceWiPadre() {
		return codiceWiPadre;
	}

	public void setCodiceWiPadre(String codiceWiPadre) {
		this.codiceWiPadre = codiceWiPadre;
	}

	public Integer getFkWiFiglio() {
		return fkWiFiglio;
	}

	public void setFkWiFiglio(Integer fkWiFiglio) {
		this.fkWiFiglio = fkWiFiglio;
	}

	public Integer getFkWiPadre() {
		return fkWiPadre;
	}

	public void setFkWiPadre(Integer fkWiPadre) {
		this.fkWiPadre = fkWiPadre;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTipoWiFiglio() {
		return tipoWiFiglio;
	}

	public void setTipoWiFiglio(String tipoWiFiglio) {
		this.tipoWiFiglio = tipoWiFiglio;
	}

	public String getTipoWiPadre() {
		return tipoWiPadre;
	}

	public void setTipoWiPadre(String tipoWiPadre) {
		this.tipoWiPadre = tipoWiPadre;
	}

	public java.sql.Timestamp getDtCaricamento() {
		return dtCaricamento;
	}

	public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
		this.dtCaricamento = dtCaricamento;
	}

	public Integer getLinkedWorkitemsPk() {
		return linkedWorkitemsPk;
	}

	public void setLinkedWorkitemsPk(Integer linkedWorkitemsPk) {
		this.linkedWorkitemsPk = linkedWorkitemsPk;
	}

	public String getPkFiglio() {
		return pkFiglio;
	}

	public void setPkFiglio(String pkFiglio) {
		this.pkFiglio = pkFiglio;
	}

	public String getPkPadre() {
		return pkPadre;
	}

	public void setPkPadre(String pkPadre) {
		this.pkPadre = pkPadre;
	}

	public String getIdRepositoryFiglio() {
		return idRepositoryFiglio;
	}

	public void setIdRepositoryFiglio(String idRepositoryFiglio) {
		this.idRepositoryFiglio = idRepositoryFiglio;
	}

	public String getIdRepositoryPadre() {
		return idRepositoryPadre;
	}

	public void setIdRepositoryPadre(String idRepositoryPadre) {
		this.idRepositoryPadre = idRepositoryPadre;
	}

	public String getCodiceProjectFiglio() {
		return codiceProjectFiglio;
	}

	public void setCodiceProjectFiglio(String codiceProjectFiglio) {
		this.codiceProjectFiglio = codiceProjectFiglio;
	}

	public String getCodiceProjectPadre() {
		return codiceProjectPadre;
	}

	public void setCodiceProjectPadre(String codiceProjectPadre) {
		this.codiceProjectPadre = codiceProjectPadre;
	}
}
