package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmDifettoProdottoOds is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmDifettoProdottoOds {

	private Integer dmalmProjectFk02;

    private Integer dmalmStrutturaOrgFk01;
    
    private Integer dmalmTempoFk04;
    
    private Integer idkAreaTematica;
    
    private String descrizioneUnitaOrganizzativa = "";
	
    private String codiceAreaOreste;

    private java.sql.Timestamp dataCambioStato;

    private java.sql.Timestamp dataCaricamento;

    private java.sql.Timestamp dataChiusuraDifetto;

    private java.sql.Timestamp dataCreazioneDifetto;

    private java.sql.Timestamp dataModificaRecord;

    private java.sql.Timestamp dataRisoluzioneDifetto;

    private String descrizioneDifetto;

    private Integer dmalmDifettoProdottoPk;

    private Integer giorniFestivi;

    private String idProject;

    private String identificativoDifetto;

    private String identificativoRepository;

    private String motivoRisoluzione;

    private String nomeAutoreDifetto;

    private String numeroLineaRdi;

    private String numeroTestataRdi;

    private String severityDifetto;

    private Integer dmalmStatoWorkitemFk03;

    private Double tempoTotaleRisoluzione;

    private String useridAutoreDifetto;
    
    private String provenienzaDifetto;
    
    private Double rankStatoDifetto;

    private String stgPk;
    
    private String naturaDifetto;
    
    private String causaDifetto;
    
    private Integer dmalmUserFk06;
    
    private String uri;
    
    private Integer effortCostoSviluppo;
    
    private Short flagUltimaSituazione;

    //DM_ALM-223
  	private java.sql.Timestamp dtDisponibilita;

  	//DM_ALM-320
  	private String priority;
  	
  	public java.sql.Timestamp getDtDisponibilita() {
  		return dtDisponibilita;
  	}

  	public void setDtDisponibilita(java.sql.Timestamp dtDisponibilita) {
  		this.dtDisponibilita = dtDisponibilita;
  	}
  	
    public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
    
    public String getCodiceAreaOreste() {
        return codiceAreaOreste;
    }

    public void setCodiceAreaOreste(String codiceAreaOreste) {
        this.codiceAreaOreste = codiceAreaOreste;
    }

    public java.sql.Timestamp getDataCambioStato() {
        return dataCambioStato;
    }

    public void setDataCambioStato(java.sql.Timestamp dataCambioStato) {
        this.dataCambioStato = dataCambioStato;
    }

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public java.sql.Timestamp getDataChiusuraDifetto() {
        return dataChiusuraDifetto;
    }

    public void setDataChiusuraDifetto(java.sql.Timestamp dataChiusuraDifetto) {
        this.dataChiusuraDifetto = dataChiusuraDifetto;
    }

    public java.sql.Timestamp getDataCreazioneDifetto() {
        return dataCreazioneDifetto;
    }

    public void setDataCreazioneDifetto(java.sql.Timestamp dataCreazioneDifetto) {
        this.dataCreazioneDifetto = dataCreazioneDifetto;
    }

    public java.sql.Timestamp getDataModificaRecord() {
        return dataModificaRecord;
    }

    public void setDataModificaRecord(java.sql.Timestamp dataModificaRecord) {
        this.dataModificaRecord = dataModificaRecord;
    }

    public java.sql.Timestamp getDataRisoluzioneDifetto() {
        return dataRisoluzioneDifetto;
    }

    public void setDataRisoluzioneDifetto(java.sql.Timestamp dataRisoluzioneDifetto) {
        this.dataRisoluzioneDifetto = dataRisoluzioneDifetto;
    }

    public String getDescrizioneDifetto() {
        return descrizioneDifetto;
    }

    public void setDescrizioneDifetto(String descrizioneDifetto) {
        this.descrizioneDifetto = descrizioneDifetto;
    }

    public Integer getDmalmDifettoProdottoPk() {
        return dmalmDifettoProdottoPk;
    }

    public void setDmalmDifettoProdottoPk(Integer dmalmDifettoProdottoPk) {
        this.dmalmDifettoProdottoPk = dmalmDifettoProdottoPk;
    }

    public Integer getGiorniFestivi() {
        return giorniFestivi;
    }

    public void setGiorniFestivi(Integer giorniFestivi) {
        this.giorniFestivi = giorniFestivi;
    }

    public String getIdentificativoDifetto() {
        return identificativoDifetto;
    }

    public void setIdentificativoDifetto(String identificativoDifetto) {
        this.identificativoDifetto = identificativoDifetto;
    }

    public String getIdentificativoRepository() {
        return identificativoRepository;
    }

    public void setIdentificativoRepository(String identificativoRepository) {
        this.identificativoRepository = identificativoRepository;
    }

    public String getMotivoRisoluzione() {
        return motivoRisoluzione;
    }

    public void setMotivoRisoluzione(String motivoRisoluzione) {
        this.motivoRisoluzione = motivoRisoluzione;
    }

    public String getNomeAutoreDifetto() {
        return nomeAutoreDifetto;
    }

    public void setNomeAutoreDifetto(String nomeAutoreDifetto) {
        this.nomeAutoreDifetto = nomeAutoreDifetto;
    }

    public String getNumeroLineaRdi() {
        return numeroLineaRdi;
    }

    public void setNumeroLineaRdi(String numeroLineaRdi) {
        this.numeroLineaRdi = numeroLineaRdi;
    }

    public String getNumeroTestataRdi() {
        return numeroTestataRdi;
    }

    public void setNumeroTestataRdi(String numeroTestataRdi) {
        this.numeroTestataRdi = numeroTestataRdi;
    }

    public String getSeverityDifetto() {
        return severityDifetto;
    }

    public void setSeverityDifetto(String severityDifetto) {
        this.severityDifetto = severityDifetto;
    }

    public Integer getStatoDifetto() {
        return dmalmStatoWorkitemFk03;
    }

    public void setStatoDifetto(Integer statoDifetto) {
        this.dmalmStatoWorkitemFk03 = statoDifetto;
    }

    public Double getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Double tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getUseridAutoreDifetto() {
        return useridAutoreDifetto;
    }

    public void setUseridAutoreDifetto(String useridAutoreDifetto) {
        this.useridAutoreDifetto = useridAutoreDifetto;
    }

	public Integer getDmalmProjectFk02() {
		return dmalmProjectFk02;
	}

	public void setDmalmProjectFk02(Integer dmalmProjectFk02) {
		this.dmalmProjectFk02 = dmalmProjectFk02;
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

	public Integer getIdkAreaTematica() {
		return idkAreaTematica;
	}

	public void setIdkAreaTematica(Integer idkAreaTematica) {
		this.idkAreaTematica = idkAreaTematica;
	}

	public String getProvenienzaDifetto() {
		return provenienzaDifetto;
	}

	public void setProvenienzaDifetto(String provenienzaDifetto) {
		this.provenienzaDifetto = provenienzaDifetto;
	}

	public Double getRankStatoDifetto() {
		return rankStatoDifetto;
	}

	public void setRankStatoDifetto(Double rankStatoDifetto) {
		this.rankStatoDifetto = rankStatoDifetto;
	}

	public String getDescrizioneUnitaOrganizzativa() {
		return descrizioneUnitaOrganizzativa;
	}

	public void setDescrizioneUnitaOrganizzativa(
			String descrizioneUnitaOrganizzativa) {
		this.descrizioneUnitaOrganizzativa = descrizioneUnitaOrganizzativa;
	}

	public String getIdProject() {
		return idProject;
	}

	public void setIdProject(String idProject) {
		this.idProject = idProject;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}

