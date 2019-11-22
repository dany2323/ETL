package lispa.schedulers.bean.staging.sgr.siss.history;

import java.sql.Timestamp;

import javax.annotation.Generated;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmSissHistoryRevision {

	private String cPk;
	
	private String cUri;
	
	private Long cRev;
	
	private String cAuthor;
	
	private Timestamp cCreated;
	
	private String cMessage;
	
	private Long cName;
	
	private String cRepositoryname;
	
	private Boolean cDeleted;
	
	private Boolean cInternalcommit;
	
	private Boolean cIsLocal;
	
	public String getcPk() {
		return cPk;
	}

	public void setcPk(String cPk) {
		this.cPk = cPk;
	}

	public String getcUri() {
		return cUri;
	}

	public void setcUri(String cUri) {
		this.cUri = cUri;
	}

	public Long getcRev() {
		return cRev;
	}

	public void setcRev(Long cRev) {
		this.cRev = cRev;
	}

	public String getcAuthor() {
		return cAuthor;
	}

	public void setcAuthor(String cAuthor) {
		this.cAuthor = cAuthor;
	}

	public Timestamp getcCreated() {
		return cCreated;
	}

	public void setcCreated(Timestamp cCreated) {
		this.cCreated = cCreated;
	}

	public String getcMessage() {
		return cMessage;
	}

	public void setcMessage(String cMessage) {
		this.cMessage = cMessage;
	}

	public Long getcNumber() {
		return cName;
	}

	public void setcNumber(Long cNumber) {
		this.cName = cNumber;
	}

	public String getcRepositoryname() {
		return cRepositoryname;
	}

	public void setcRepositoryname(String cRepositoryname) {
		this.cRepositoryname = cRepositoryname;
	}

	public Boolean getcDeleted() {
		return cDeleted;
	}

	public void setcDeleted(Boolean cDeleted) {
		this.cDeleted = cDeleted;
	}

	public Boolean getcInternalcommit() {
		return cInternalcommit;
	}

	public void setcInternalcommit(Boolean cInternalcommit) {
		this.cInternalcommit = cInternalcommit;
	}

	public Boolean getcIsLocal() {
		return cIsLocal;
	}

	public void setcIsLocal(Boolean cIsLocal) {
		this.cIsLocal = cIsLocal;
	}

	
}
