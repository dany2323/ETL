package lispa.schedulers.bean.staging.sgr.xml;

import javax.annotation.Generated;

/**
 * DmalmWorkitemLinkRoles is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmTemplateProject {

    private String pathLocation;

    private String templateId;

    private Integer rev;
    
	public String getPathLocation() {
		return pathLocation;
	}

	public void setPathLocation(String pathLocation) {
		this.pathLocation = pathLocation;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getRev() {
		return rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

}