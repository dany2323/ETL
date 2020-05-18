package lispa.schedulers.bean.fonte.sgr.sire.current;

import javax.annotation.Generated;
import java.sql.Timestamp;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireCurrentRevision {

	private String cPk;

	private String cUri;

	private Long cRev;

	private String cAuthor;

	private Timestamp cCreated;

	private String cMessage;

	private String cName;

	private String cRepositoryname;

	private Boolean cDeleted;

	private Boolean cInternalcommit;

	private Boolean cIsLocal;

	private int sissHistoryRevisionPk;

	private java.sql.Timestamp dataCaricamento;

	public java.sql.Timestamp getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

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

	public String getcNumber() {
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

	public int getSissHistoryRevisionPk() {
		return sissHistoryRevisionPk;
	}

	public void setSissHistoryRevisionPk(int sissHistoryRevisionPk) {
		this.sissHistoryRevisionPk = sissHistoryRevisionPk;
	}

}