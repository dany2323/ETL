package lispa.schedulers.bean.target;

public class DmalmFilieraTemplateDocumenti {
	
	private String template;
	private Integer idFiliera;
	private Integer livello;
	private Integer fkWi;
	private String tipoWi;
	private Integer fkDocumento;
	
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Integer getIdFiliera() {
		return idFiliera;
	}
	public void setIdFiliera(Integer idFiliera) {
		this.idFiliera = idFiliera;
	}
	public Integer getLivello() {
		return livello;
	}
	public void setLivello(Integer livello) {
		this.livello = livello;
	}
	public Integer getFkWi() {
		return fkWi;
	}
	public void setFkWi(Integer fkWi) {
		this.fkWi = fkWi;
	}
	public String getTipoWi() {
		return tipoWi;
	}
	public void setTipoWi(String tipoWi) {
		this.tipoWi = tipoWi;
	}
	public Integer getFkDocumento() {
		return fkDocumento;
	}
	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}	

}
