package lispa.schedulers.bean.fonte.sgr.xml;

import javax.annotation.Generated;


/**
 * DmalmSchedeServizio is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmSchedeServizio {
	
	private String id;
	
	private String name;
	
	private Integer sorter;
	
	private String repository;

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

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

}