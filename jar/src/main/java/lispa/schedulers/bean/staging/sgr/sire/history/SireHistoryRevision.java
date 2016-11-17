package lispa.schedulers.bean.staging.sgr.sire.history;

import java.sql.Timestamp;

import javax.annotation.Generated;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireHistoryRevision {

	private String cPk;
	
	private String cUri;
	
	private Long cRev;
	
	private java.sql.Timestamp dataCaricamento;
	
	public java.sql.Timestamp getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	private String cAuthor;
	
	private Timestamp cCreated;
	
	private String cMessage;
	
	private String cName;
	
	private String cRepositoryname;
	
	private Boolean cDeleted;
	
	private Boolean cInternalcommit;
	
	private Boolean cIsLocal;
	
	private int sireHistoryRevisionPk;
	
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

	public String getcName() {
		return cName;
	}

	public void setcNumber(String cName) {
		this.cName = cName;
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

	public int getSireHistoryRevisionPk() {
		return sireHistoryRevisionPk;
	}

	public void setSireHistoryRevisionPk(int sireHistoryRevisionPk) {
		this.sireHistoryRevisionPk = sireHistoryRevisionPk;
	}
	
}
