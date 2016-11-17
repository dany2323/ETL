package lispa.schedulers.bean.staging.sgr.sire.history;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

/**
 * SireHistoryWorkitem is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SireHistoryWorkitem {

	private Boolean cAutosuspect;

	private java.sql.Timestamp cCreated;

	private Boolean cDeleted;

	private java.sql.Timestamp cDuedate;

	private String cId;

	private String cDescription;

	private Float cInitialestimate;

	private Boolean cIsLocal;

	private String cLocation;

	private String cOutlinenumber;

	private String cPk;

	private java.sql.Timestamp cPlannedend;

	private java.sql.Timestamp cPlannedstart;

	private String cPreviousstatus;

	private String cPriority;

	private Float cRemainingestimate;

	private String cResolution;

	private java.sql.Timestamp cResolvedon;

	private Long cRev;

	private String cSeverity;

	private String cStatus;

	private Float cTimespent;

	private String cTitle;

	private String cType;

	private java.sql.Timestamp cUpdated;

	private String cUri;

	private String fkAuthor;

	private String fkModule;

	private String fkProject;

	private String fkTimepoint;

	private String fkUriAuthor;

	private String fkUriModule;

	private String fkUriProject;

	private String fkUriTimepoint;

	private Float costoSviluppo;

	private String codIntervento;

	private String ticketID;

	private Float ca;

	private Timestamp stChiuso;

	private String versione;

	private Date dataInizio;

	private Date dataDisp;

	private Date dataInizioEff;

	private String aoid;

	private String codice;

	private String classeDoc;

	private String tipoDoc;

	private Boolean fr;

	private String fornitura;

	private Date dataDispEff;

	private Date dataFine;

	private Date dataFineEff;

	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getDataFineEff() {
		return dataFineEff;
	}

	public void setDataFineEff(Date dataFineEff) {
		this.dataFineEff = dataFineEff;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getClasseDoc() {
		return classeDoc;
	}

	public void setClasseDoc(String classeDoc) {
		this.classeDoc = classeDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public Boolean getFr() {
		return fr;
	}

	public void setFr(Boolean fr) {
		this.fr = fr;
	}

	public String getFornitura() {
		return fornitura;
	}

	public void setFornitura(String fornitura) {
		this.fornitura = fornitura;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataDisp() {
		return dataDisp;
	}

	public void setDataDisp(Date dataDisp) {
		this.dataDisp = dataDisp;
	}

	public Date getDataInizioEff() {
		return dataInizioEff;
	}

	public void setDataInizioEff(Date dataInizioEff) {
		this.dataInizioEff = dataInizioEff;
	}

	public String getAoid() {
		return aoid;
	}

	public void setAoid(String aoid) {
		this.aoid = aoid;
	}

	public Boolean getcAutosuspect() {
		return cAutosuspect;
	}

	public void setcAutosuspect(Boolean cAutosuspect) {
		this.cAutosuspect = cAutosuspect;
	}

	public java.sql.Timestamp getcCreated() {
		return cCreated;
	}

	public void setcCreated(java.sql.Timestamp cCreated) {
		this.cCreated = cCreated;
	}

	public Boolean getcDeleted() {
		return cDeleted;
	}

	public void setcDeleted(Boolean cDeleted) {
		this.cDeleted = cDeleted;
	}

	public java.sql.Timestamp getcDuedate() {
		return cDuedate;
	}

	public void setcDuedate(java.sql.Timestamp cDuedate) {
		this.cDuedate = cDuedate;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public Float getcInitialestimate() {
		return cInitialestimate;
	}

	public void setcInitialestimate(Float cInitialestimate) {
		this.cInitialestimate = cInitialestimate;
	}

	public Boolean getcIsLocal() {
		return cIsLocal;
	}

	public void setcIsLocal(Boolean cIsLocal) {
		this.cIsLocal = cIsLocal;
	}

	public String getcLocation() {
		return cLocation;
	}

	public void setcLocation(String cLocation) {
		this.cLocation = cLocation;
	}

	public String getcOutlinenumber() {
		return cOutlinenumber;
	}

	public void setcOutlinenumber(String cOutlinenumber) {
		this.cOutlinenumber = cOutlinenumber;
	}

	public String getcPk() {
		return cPk;
	}

	public void setcPk(String cPk) {
		this.cPk = cPk;
	}

	public java.sql.Timestamp getcPlannedend() {
		return cPlannedend;
	}

	public void setcPlannedend(java.sql.Timestamp cPlannedend) {
		this.cPlannedend = cPlannedend;
	}

	public java.sql.Timestamp getcPlannedstart() {
		return cPlannedstart;
	}

	public void setcPlannedstart(java.sql.Timestamp cPlannedstart) {
		this.cPlannedstart = cPlannedstart;
	}

	public String getcPreviousstatus() {
		return cPreviousstatus;
	}

	public void setcPreviousstatus(String cPreviousstatus) {
		this.cPreviousstatus = cPreviousstatus;
	}

	public String getcPriority() {
		return cPriority;
	}

	public void setcPriority(String cPriority) {
		this.cPriority = cPriority;
	}

	public Float getcRemainingestimate() {
		return cRemainingestimate;
	}

	public void setcRemainingestimate(Float cRemainingestimate) {
		this.cRemainingestimate = cRemainingestimate;
	}

	public String getcResolution() {
		return cResolution;
	}

	public void setcResolution(String cResolution) {
		this.cResolution = cResolution;
	}

	public java.sql.Timestamp getcResolvedon() {
		return cResolvedon;
	}

	public void setcResolvedon(java.sql.Timestamp cResolvedon) {
		this.cResolvedon = cResolvedon;
	}

	public Long getcRev() {
		return cRev;
	}

	public void setcRev(Long cRev) {
		this.cRev = cRev;
	}

	public String getcSeverity() {
		return cSeverity;
	}

	public void setcSeverity(String cSeverity) {
		this.cSeverity = cSeverity;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public Float getcTimespent() {
		return cTimespent;
	}

	public void setcTimespent(Float cTimespent) {
		this.cTimespent = cTimespent;
	}

	public String getcTitle() {
		return cTitle;
	}

	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public java.sql.Timestamp getcUpdated() {
		return cUpdated;
	}

	public void setcUpdated(java.sql.Timestamp cUpdated) {
		this.cUpdated = cUpdated;
	}

	public String getcUri() {
		return cUri;
	}

	public void setcUri(String cUri) {
		this.cUri = cUri;
	}

	public String getFkAuthor() {
		return fkAuthor;
	}

	public void setFkAuthor(String fkAuthor) {
		this.fkAuthor = fkAuthor;
	}

	public String getFkModule() {
		return fkModule;
	}

	public void setFkModule(String fkModule) {
		this.fkModule = fkModule;
	}

	public String getFkProject() {
		return fkProject;
	}

	public void setFkProject(String fkProject) {
		this.fkProject = fkProject;
	}

	public String getFkTimepoint() {
		return fkTimepoint;
	}

	public void setFkTimepoint(String fkTimepoint) {
		this.fkTimepoint = fkTimepoint;
	}

	public String getFkUriAuthor() {
		return fkUriAuthor;
	}

	public void setFkUriAuthor(String fkUriAuthor) {
		this.fkUriAuthor = fkUriAuthor;
	}

	public String getFkUriModule() {
		return fkUriModule;
	}

	public void setFkUriModule(String fkUriModule) {
		this.fkUriModule = fkUriModule;
	}

	public String getFkUriProject() {
		return fkUriProject;
	}

	public void setFkUriProject(String fkUriProject) {
		this.fkUriProject = fkUriProject;
	}

	public String getFkUriTimepoint() {
		return fkUriTimepoint;
	}

	public void setFkUriTimepoint(String fkUriTimepoint) {
		this.fkUriTimepoint = fkUriTimepoint;
	}

	public Float getCostoSviluppo() {
		return costoSviluppo;
	}

	public void setCostoSviluppo(Float costoSviluppo) {
		this.costoSviluppo = costoSviluppo;
	}

	public String getCodIntervento() {
		return codIntervento;
	}

	public void setCodIntervento(String codIntervento) {
		this.codIntervento = codIntervento;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public Float getCa() {
		return ca;
	}

	public void setCa(Float ca) {
		this.ca = ca;
	}

	public Timestamp getStChiuso() {
		return stChiuso;
	}

	public void setStChiuso(Timestamp stChiuso) {
		this.stChiuso = stChiuso;
	}

	public String getcDescription() {
		return cDescription;
	}

	public void setcDescription(String cDescription) {
		this.cDescription = cDescription;
	}

	public Date getDataDispEff() {
		return dataDispEff;
	}

	public void setDataDispEff(Date dataDispEff) {
		this.dataDispEff = dataDispEff;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

}

