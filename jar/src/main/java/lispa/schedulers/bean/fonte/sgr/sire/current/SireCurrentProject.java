package lispa.schedulers.bean.fonte.sgr.sire.current;

import javax.annotation.Generated;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireCurrentProject {
	
	private Long cPk;
	
	private Long cUri;
	
	private Long cRev;
	
	private Boolean cDeleted;

    private Boolean cActive;

    private String cDescription;

    private java.sql.Date cFinish;

    private String cId;
    
    private Long fkLead;
    
    private Long fkUriLead;
    
    private String cLocation;

    private java.sql.Date cLockworkrecordsdate;

    private String cName;

    private Long fkProjectgroup;

    private Long fkUriProjectgroup;

    private java.sql.Date cStart;

    private String cTrackerprefix;

    
	public Long getcPk() {
		return cPk;
	}

	public void setcPk(Long cPk) {
		this.cPk = cPk;
	}

	public Long getcUri() {
		return cUri;
	}

	public void setcUri(Long cUri) {
		this.cUri = cUri;
	}

	public Long getcRev() {
		return cRev;
	}

	public void setcRev(Long cRev) {
		this.cRev = cRev;
	}

	public Boolean getcDeleted() {
		return cDeleted;
	}

	public void setcDeleted(Boolean cDeleted) {
		this.cDeleted = cDeleted;
	}

	public Boolean getcActive() {
		return cActive;
	}

	public void setcActive(Boolean cActive) {
		this.cActive = cActive;
	}

	public String getcDescription() {
		return cDescription;
	}

	public void setcDescription(String cDescription) {
		this.cDescription = cDescription;
	}

	public java.sql.Date getcFinish() {
		return cFinish;
	}

	public void setcFinish(java.sql.Date cFinish) {
		this.cFinish = cFinish;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public Long getFkLead() {
		return fkLead;
	}

	public void setFkLead(Long fkLead) {
		this.fkLead = fkLead;
	}

	public Long getFkUriLead() {
		return fkUriLead;
	}

	public void setFkUriLead(Long fkUriLead) {
		this.fkUriLead = fkUriLead;
	}

	public String getcLocation() {
		return cLocation;
	}

	public void setcLocation(String cLocation) {
		this.cLocation = cLocation;
	}

	public java.sql.Date getcLockworkrecordsdate() {
		return cLockworkrecordsdate;
	}

	public void setcLockworkrecordsdate(java.sql.Date cLockworkrecordsdate) {
		this.cLockworkrecordsdate = cLockworkrecordsdate;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public Long getFkProjectgroup() {
		return fkProjectgroup;
	}

	public void setFkProjectgroup(Long fkProjectgroup) {
		this.fkProjectgroup = fkProjectgroup;
	}

	public Long getFkUriProjectgroup() {
		return fkUriProjectgroup;
	}

	public void setFkUriProjectgroup(Long fkUriProjectgroup) {
		this.fkUriProjectgroup = fkUriProjectgroup;
	}

	public java.sql.Date getcStart() {
		return cStart;
	}

	public void setcStart(java.sql.Date cStart) {
		this.cStart = cStart;
	}

	public String getcTrackerprefix() {
		return cTrackerprefix;
	}

	public void setcTrackerprefix(String cTrackerprefix) {
		this.cTrackerprefix = cTrackerprefix;
	}

}

