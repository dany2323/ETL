package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmDifettoProdotto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmDifettoProdotto {

	
    private String cdDifetto;

    private Integer dmalmDifettoProdottoPrimaryKey;
    
    private String descrizioneProject;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;
    
    private String descrizioneUnitaOrganizzativa = "";

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreDifetto;

    private String dsDifetto;

    private java.sql.Timestamp dtCambioStatoDifetto;

    private java.sql.Timestamp dtCaricamentoRecordDifetto;

    private java.sql.Timestamp dtChiusuraDifetto;

    private java.sql.Timestamp dtCreazioneDifetto;

    private java.sql.Timestamp dtModificaRecordDifetto;

    private java.sql.Timestamp dtRisoluzioneDifetto;

    private String idAutoreDifetto;

    private Integer idkAreaTematica;

    private String motivoRisoluzioneDifetto;

    private Integer nrGiorniFestivi;

    private String numeroLineaDifetto;

    private String numeroTestataDifetto;

    private String provenienzaDifetto;

    private Double rankStatoDifetto;

    private String severity;
    
    private String idRepository;

    private Double tempoTotRisoluzioneDifetto;
    
    private java.sql.Timestamp dtStoricizzazione;
    
    private String stgPk;
    
    private String naturaDifetto;
    
    private String causaDifetto;

    private String uri;
    
    private String changed;
    
    private Integer effortCostoSviluppo;
    
    private Short flagUltimaSituazione;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
	//DM_ALM-223
	private java.sql.Timestamp dtDisponibilita;
	
    public java.sql.Timestamp getDtDisponibilita() {
		return dtDisponibilita;
	}

	public void setDtDisponibilita(java.sql.Timestamp dtDisponibilita) {
		this.dtDisponibilita = dtDisponibilita;
	}
	
    public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

    public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
    public String getDescrizioneProject ()
	{
		return descrizioneProject;
	}

	public void setDescrizioneProject (String descrizioneProject)
	{
		this.descrizioneProject = descrizioneProject;
	}

	public String getDescrizioneUnitaOrganizzativa ()
	{
		return descrizioneUnitaOrganizzativa;
	}

	public void setDescrizioneUnitaOrganizzativa (String descrizioneUnitaOrganizzativa)
	{
		this.descrizioneUnitaOrganizzativa = descrizioneUnitaOrganizzativa;
	}

	public java.sql.Timestamp getDtStoricizzazione() {
		return dtStoricizzazione;
	}

	public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
		this.dtStoricizzazione = dtStoricizzazione;
	}
	
    public String getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}

	public String getCdDifetto() {
        return cdDifetto;
    }

    public void setCdDifetto(String cdDifetto) {
        this.cdDifetto = cdDifetto;
    }

    public Integer getDmalmDifettoProdottoPk() {
        return dmalmDifettoProdottoPrimaryKey;
    }

    public void setDmalmDifettoProdottoPk(Integer dmalmDifettoProdottoPk) {
        this.dmalmDifettoProdottoPrimaryKey = dmalmDifettoProdottoPk;
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

    public String getDsAutoreDifetto() {
        return dsAutoreDifetto;
    }

    public void setDsAutoreDifetto(String dsAutoreDifetto) {
        this.dsAutoreDifetto = dsAutoreDifetto;
    }

    public String getDsDifetto() {
        return dsDifetto;
    }

    public void setDsDifetto(String dsDifetto) {
        this.dsDifetto = dsDifetto;
    }

    public java.sql.Timestamp getDtCambioStatoDifetto() {
        return dtCambioStatoDifetto;
    }

    public void setDtCambioStatoDifetto(java.sql.Timestamp dtCambioStatoDifetto) {
        this.dtCambioStatoDifetto = dtCambioStatoDifetto;
    }

    public java.sql.Timestamp getDtCaricamentoRecordDifetto() {
        return dtCaricamentoRecordDifetto;
    }

    public void setDtCaricamentoRecordDifetto(java.sql.Timestamp dtCaricamentoRecordDifetto) {
        this.dtCaricamentoRecordDifetto = dtCaricamentoRecordDifetto;
    }

    public java.sql.Timestamp getDtChiusuraDifetto() {
        return dtChiusuraDifetto;
    }

    public void setDtChiusuraDifetto(java.sql.Timestamp dtChiusuraDifetto) {
        this.dtChiusuraDifetto = dtChiusuraDifetto;
    }

    public java.sql.Timestamp getDtCreazioneDifetto() {
        return dtCreazioneDifetto;
    }

    public void setDtCreazioneDifetto(java.sql.Timestamp dtCreazioneDifetto) {
        this.dtCreazioneDifetto = dtCreazioneDifetto;
    }

    public java.sql.Timestamp getDtModificaRecordDifetto() {
        return dtModificaRecordDifetto;
    }

    public void setDtModificaRecordDifetto(java.sql.Timestamp dtModificaRecordDifetto) {
        this.dtModificaRecordDifetto = dtModificaRecordDifetto;
    }

    public java.sql.Timestamp getDtRisoluzioneDifetto() {
        return dtRisoluzioneDifetto;
    }

    public void setDtRisoluzioneDifetto(java.sql.Timestamp dtRisoluzioneDifetto) {
        this.dtRisoluzioneDifetto = dtRisoluzioneDifetto;
    }

    public String getIdAutoreDifetto() {
        return idAutoreDifetto;
    }

    public void setIdAutoreDifetto(String idAutoreDifetto) {
        this.idAutoreDifetto = idAutoreDifetto;
    }

    public Integer getIdkAreaTematica() {
        return idkAreaTematica;
    }

    public void setIdkAreaTematica(Integer idkAreaTematica) {
        this.idkAreaTematica = idkAreaTematica;
    }

    public String getMotivoRisoluzioneDifetto() {
        return motivoRisoluzioneDifetto;
    }

    public void setMotivoRisoluzioneDifetto(String motivoRisoluzioneDifetto) {
        this.motivoRisoluzioneDifetto = motivoRisoluzioneDifetto;
    }

    public Integer getNrGiorniFestivi() {
        return nrGiorniFestivi;
    }

    public void setNrGiorniFestivi(Integer nrGiorniFestivi) {
        this.nrGiorniFestivi = nrGiorniFestivi;
    }

    public String getNumeroLineaDifetto() {
        return numeroLineaDifetto;
    }

    public void setNumeroLineaDifetto(String numeroLineaDifetto) {
        this.numeroLineaDifetto = numeroLineaDifetto;
    }

    public String getNumeroTestataDifetto() {
        return numeroTestataDifetto;
    }

    public void setNumeroTestataDifetto(String numeroTestataDifetto) {
        this.numeroTestataDifetto = numeroTestataDifetto;
    }

    public String getProvenienzaDifetto() {
        return provenienzaDifetto; //DM_ALM-289 Aggiunto commento # 4
    }

    public void setProvenienzaDifetto(String provenienzaDifetto) {
    	//DM_ALM-289 Aggiunto commento # 4
    	if(provenienzaDifetto!=null)
    	{
    		provenienzaDifetto=provenienzaDifetto.trim();
	    	if(provenienzaDifetto.contains("SVI"))
	    		provenienzaDifetto.replaceAll("SVI", "SV");
    	}
        this.provenienzaDifetto = provenienzaDifetto;
    	if(provenienzaDifetto.contains("SVI"))
    		provenienzaDifetto.replaceAll("SVI", "SV");
        this.provenienzaDifetto = provenienzaDifetto.trim();
    }

    public Double getRankStatoDifetto() {
        return rankStatoDifetto;
    }

    public void setRankStatoDifetto(Double rankStatoDifetto) {
        this.rankStatoDifetto = rankStatoDifetto;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Double getTempoTotRisoluzioneDifetto() {
        return tempoTotRisoluzioneDifetto;
    }

    public void setTempoTotRisoluzioneDifetto(Double tempoTotRisoluzioneDifetto) {
        this.tempoTotRisoluzioneDifetto = tempoTotRisoluzioneDifetto;
    }

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

	public String getNaturaDifetto() {
		return naturaDifetto;
	}

	public void setNaturaDifetto(String naturaDifetto) {
		this.naturaDifetto = naturaDifetto;
	}

	public String getCausaDifetto() {
		return causaDifetto;
	}

	public void setCausaDifetto(String causaDifetto) {
		this.causaDifetto = causaDifetto;
	}

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public Integer getEffortCostoSviluppo() {
		return effortCostoSviluppo;
	}

	public void setEffortCostoSviluppo(Integer effortCostoSviluppo) {
		this.effortCostoSviluppo = effortCostoSviluppo;
	}

	public Short getFlagUltimaSituazione() {
		return flagUltimaSituazione;
	}

	public void setFlagUltimaSituazione(Short flagUltimaSituazione) {
		this.flagUltimaSituazione = flagUltimaSituazione;
	}

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

}

