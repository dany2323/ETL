package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmSchedeServizio is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmSchedeServizioTarget {

private Integer dmalm_schedeServizio_Pk;
	
	private String id;
	
	private String name;
	
	private Integer sorter;
	
	private java.sql.Timestamp dtCaricamento;
	
	private java.sql.Timestamp dtInizioValidita;
	
	private java.sql.Timestamp dtFineValidita;
	
	private String repository;

	public Integer getDmalm_schedeServizio_Pk() {
		return dmalm_schedeServizio_Pk;
	}

	public void setDmalm_schedeServizio_Pk(Integer dmalm_schedeServizio_Pk) {
		this.dmalm_schedeServizio_Pk = dmalm_schedeServizio_Pk;
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

	public Integer getSorter() {
		return sorter;
	}

	public void setSorter(Integer sorter) {
		this.sorter = sorter;
	}

	public java.sql.Timestamp getDtCaricamento() {
		return dtCaricamento;
	}

	public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
		this.dtCaricamento = dtCaricamento;
	}

	public java.sql.Timestamp getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(java.sql.Timestamp dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public java.sql.Timestamp getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(java.sql.Timestamp dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}
