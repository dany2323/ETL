package lispa.schedulers.bean.fonte.sgr.siss.current;

import java.sql.Timestamp;

import javax.annotation.Generated;

/**
 * SireCurrentWorkitemLinked is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SissCurrentRevision {

    private Long cPk;

    private Long cUri;
    
    private Long cRev;
    
    private Boolean cDeleted;
    
    private String cAuthor;
    
    private Timestamp cCreated;
    
    private Boolean cInternalcommit;
    
    private String cMessage;
    
    private String cName;
    
    private String cRepositoryname;

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

	public Boolean getcInternalcommit() {
		return cInternalcommit;
	}

	public void setcInternalcommit(Boolean cInternalcommit) {
		this.cInternalcommit = cInternalcommit;
	}

	public String getcMessage() {
		return cMessage;
	}

	public void setcMessage(String cMessage) {
		this.cMessage = cMessage;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcRepositoryname() {
		return cRepositoryname;
	}

	public void setcRepositoryname(String cRepositoryname) {
		this.cRepositoryname = cRepositoryname;
	}
    
}

