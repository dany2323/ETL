package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmClassificatoreDemand is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")

public class DmalmClassificatore {

	private String annullato;
	
	private String cd_classificatore;
	
	private String cf_ambito;
	
	private String cf_area;
	
	private String cf_riferimenti;
	
	private String cf_scheda_servizio;
	
	private String changed;
	
	private Integer dmalmAreaTematicaFk05;
	
	private Integer dmalmClassificatorePk;
	
	private Integer dmalmProjectFk02;
	
	private Integer dmalmStatoWorkitemFk03;
	
	private Integer dmalmStrutturaOrgFk01;
	
	private Integer dmalmTempoFk04;
	
	private Integer dmalmUserFk06;
	
	private String dsAutoreClassificatore;
	
	private java.sql.Timestamp dtAnnullamento;
	
	private java.sql.Timestamp dtCambioStatoClassif;
	
	private java.sql.Timestamp dtCaricamentoClassif;
	
	private java.sql.Timestamp dtCreazioneClassif;
	
	private java.sql.Timestamp dtModificaClassif;
	
	private java.sql.Timestamp dtRisoluzioneClassif;
	
	private java.sql.Timestamp dtScadenzaProgSvil;
	
	private java.sql.Timestamp dtStoricizzazione;
	
	private String idAutoreClassificatore;
	
	private String idRepository;
	
	private Short rankStatoClassifMese;
	
	private Double rankStatoClassificatore;
	
	private String stgPk;
	
	private String titoloClassificatore;
	
	private String uriClassficatore;
	
	private String rmResponsabileProgetto;
	
	private boolean progettoInDeroga;
	
	private String assigneeProgettoItInDeroga;
	
	private String locationSorgenti;
	
	private String type;

	public String getRmResponsabileProgetto() {
		return rmResponsabileProgetto;
	}

	public void setRmResponsabileProgetto(String rmResponsabileProgetto) {
		this.rmResponsabileProgetto = rmResponsabileProgetto;
	}

	public boolean isProgettoInDeroga() {
		return progettoInDeroga;
	}

	public void setProgettoInDeroga(boolean progettoInDeroga) {
		this.progettoInDeroga = progettoInDeroga;
	}

	public String getAssigneeProgettoItInDeroga() {
		return assigneeProgettoItInDeroga;
	}

	public void setAssigneeProgettoItInDeroga(String assigneeProgettoItInDeroga) {
		this.assigneeProgettoItInDeroga = assigneeProgettoItInDeroga;
	}

	public String getLocationSorgenti() {
		return locationSorgenti;
	}

	public void setLocationSorgenti(String locationSorgenti) {
		this.locationSorgenti = locationSorgenti;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public String getCd_classificatore() {
		return cd_classificatore;
	}

	public void setCd_classificatore(String cd_classificatore) {
		this.cd_classificatore = cd_classificatore;
	}

	public String getCf_ambito() {
		return cf_ambito;
	}

	public void setCf_ambito(String cf_ambito) {
		this.cf_ambito = cf_ambito;
	}

	public String getCf_area() {
		return cf_area;
	}

	public void setCf_area(String cf_area) {
		this.cf_area = cf_area;
	}

	public String getCf_riferimenti() {
		return cf_riferimenti;
	}

	public void setCf_riferimenti(String cf_riferimenti) {
		this.cf_riferimenti = cf_riferimenti;
	}

	public String getCf_scheda_servizio() {
		return cf_scheda_servizio;
	}

	public void setCf_scheda_servizio(String cf_scheda_servizio) {
		this.cf_scheda_servizio = cf_scheda_servizio;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public Integer getDmalmAreaTematicaFk05() {
		return dmalmAreaTematicaFk05;
	}

	public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
		this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
	}

	public Integer getDmalmClassificatorePk() {
		return dmalmClassificatorePk;
	}

	public void setDmalmClassificatorePk(Integer dmalmClassificatorePk) {
		this.dmalmClassificatorePk = dmalmClassificatorePk;
	}

	public String getDsAutoreClassificatore() {
		return dsAutoreClassificatore;
	}

	public void setDsAutoreClassificatore(String dsAutoreClassificatore) {
		this.dsAutoreClassificatore = dsAutoreClassificatore;
	}

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}

	public java.sql.Timestamp getDtCambioStatoClassif() {
		return dtCambioStatoClassif;
	}

	public void setDtCambioStatoClassif(java.sql.Timestamp dtCambioStatoClassif) {
		this.dtCambioStatoClassif = dtCambioStatoClassif;
	}

	public java.sql.Timestamp getDtCaricamentoClassif() {
		return dtCaricamentoClassif;
	}

	public void setDtCaricamentoClassif(java.sql.Timestamp dtCaricamentoClassif) {
		this.dtCaricamentoClassif = dtCaricamentoClassif;
	}

	public java.sql.Timestamp getDtCreazioneClassif() {
		return dtCreazioneClassif;
	}

	public void setDtCreazioneClassif(java.sql.Timestamp dtCreazioneClassif) {
		this.dtCreazioneClassif = dtCreazioneClassif;
	}

	public java.sql.Timestamp getDtModificaClassif() {
		return dtModificaClassif;
	}

	public void setDtModificaClassif(java.sql.Timestamp dtModificaClassif) {
		this.dtModificaClassif = dtModificaClassif;
	}

	public java.sql.Timestamp getDtRisoluzioneClassif() {
		return dtRisoluzioneClassif;
	}

	public void setDtRisoluzioneClassif(java.sql.Timestamp dtRisoluzioneClassif) {
		this.dtRisoluzioneClassif = dtRisoluzioneClassif;
	}

	public java.sql.Timestamp getDtScadenzaProgSvil() {
		return dtScadenzaProgSvil;
	}

	public void setDtScadenzaProgSvil(java.sql.Timestamp dtScadenzaProgSvil) {
		this.dtScadenzaProgSvil = dtScadenzaProgSvil;
	}

	public java.sql.Timestamp getDtStoricizzazione() {
		return dtStoricizzazione;
	}

	public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
		this.dtStoricizzazione = dtStoricizzazione;
	}

	public String getIdAutoreClassificatore() {
		return idAutoreClassificatore;
	}

	public void setIdAutoreClassificatore(String idAutoreClassificatore) {
		this.idAutoreClassificatore = idAutoreClassificatore;
	}

	public String getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}

	public String getTitoloClassificatore() {
		return titoloClassificatore;
	}

	public void setTitoloClassificatore(String titoloClassificatore) {
		this.titoloClassificatore = titoloClassificatore;
	}

	public Integer getDmalmProjectFk02() {
		return dmalmProjectFk02;
	}

	public void setDmalmProjectFk02(Integer dmalmProjectFk02) {
		this.dmalmProjectFk02 = dmalmProjectFk02;
	}

	public Integer getDmalmStatoWorkitemFk03() {
		return dmalmStatoWorkitemFk03;
	}

	public void setDmalmStatoWorkitemFk03(Integer dmalmStatoWorkitemFk03) {
		this.dmalmStatoWorkitemFk03 = dmalmStatoWorkitemFk03;
	}

	public Integer getDmalmStrutturaOrgFk01() {
		return dmalmStrutturaOrgFk01;
	}

	public void setDmalmStrutturaOrgFk01(Integer dmalmStrutturaOrgFk01) {
		this.dmalmStrutturaOrgFk01 = dmalmStrutturaOrgFk01;
	}

	public Integer getDmalmTempoFk04() {
		return dmalmTempoFk04;
	}

	public void setDmalmTempoFk04(Integer dmalmTempoFk04) {
		this.dmalmTempoFk04 = dmalmTempoFk04;
	}

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public Short getRankStatoClassifMese() {
		return rankStatoClassifMese;
	}

	public void setRankStatoClassifMese(Short rankStatoClassifMese) {
		this.rankStatoClassifMese = rankStatoClassifMese;
	}

	public Double getRankStatoClassificatore() {
		return rankStatoClassificatore;
	}

	public void setRankStatoClassificatore(Double rankStatoClassificatore) {
		this.rankStatoClassificatore = rankStatoClassificatore;
	}

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

	public String getUriClassficatore() {
		return uriClassficatore;
	}

	public void setUriClassficatore(String uriClassficatore) {
		this.uriClassficatore = uriClassficatore;
	}
	
	
}
