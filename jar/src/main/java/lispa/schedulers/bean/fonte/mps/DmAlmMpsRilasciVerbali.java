package lispa.schedulers.bean.fonte.mps;

import javax.annotation.Generated;

/**
 * DmalmStgMpsRilasciVerbali is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmMpsRilasciVerbali {

	private String codVerbale;

	private Integer idVerbaleValidazione;

	private Integer idRilascio;

	private String importo;

	public String getCodVerbale() {
		return codVerbale;
	}

	public void setCodVerbale(String codVerbale) {
		this.codVerbale = codVerbale;
	}

	public Integer getIdVerbaleValidazione() {
		return idVerbaleValidazione;
	}

	public void setIdVerbaleValidazione(Integer idVerbaleValidazione) {
		this.idVerbaleValidazione = idVerbaleValidazione;
	}

	public Integer getIdRilascio() {
		return idRilascio;
	}

	public void setIdRilascio(Integer idRilascio) {
		this.idRilascio = idRilascio;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

}
