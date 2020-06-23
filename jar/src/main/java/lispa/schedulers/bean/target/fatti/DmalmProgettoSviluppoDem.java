package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmProgettoSviluppoDem is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProgettoSviluppoDem {

    private String assignee;

    private String cdProgSvilD;

    private String cfCodice;

    private java.sql.Timestamp cfDataDispEffettiva;

    private java.sql.Timestamp cfDataDispPianificata;

    private java.sql.Timestamp cfDataInizio;

    private java.sql.Timestamp cfDataInizioEff;

    private String cfFornitura;

    private String descrizioneProgSvilD;

    private Integer dmalmProgSvilDPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreProgSvilD;

    private java.sql.Timestamp dtCambioStatoProgSvilD;

    private java.sql.Timestamp dtCaricamentoProgSvilD;

    private java.sql.Timestamp dtCreazioneProgSvilD;

    private java.sql.Timestamp dtModificaProgSvilD;

    private java.sql.Timestamp dtPassaggioEsercizio;

    private java.sql.Timestamp dtRisoluzioneProgSvilD;

    private java.sql.Timestamp dtScadenza;

    private java.sql.Timestamp dtScadenzaProgSvilD;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreProgSvilD;

    private String idRepository;

    private String motivoRisoluzioneProgSvilD;

    private String priorityProgettoSvilDemand;

    private Double rankStatoProgSvilD;

    private Short rankStatoProgSvilDMese;

    private String severityProgettoSvilDemand;

    private Integer tempoTotaleRisoluzione;

    private String titoloProgSvilD;
    
    private String stgPk;

    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
	//DM_ALM-470
  	private String tagAlm;
  	private java.sql.Timestamp tsTagAlm;
  	
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
	
    public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCdProgSvilD() {
        return cdProgSvilD;
    }

    public void setCdProgSvilD(String cdProgSvilD) {
        this.cdProgSvilD = cdProgSvilD;
    }

    public String getCfCodice() {
        return cfCodice;
    }

    public void setCfCodice(String cfCodice) {
        this.cfCodice = cfCodice;
    }

    public java.sql.Timestamp getCfDataDispEffettiva() {
        return cfDataDispEffettiva;
    }

    public void setCfDataDispEffettiva(java.sql.Timestamp cfDataDispEffettiva) {
        this.cfDataDispEffettiva = cfDataDispEffettiva;
    }

    public java.sql.Timestamp getCfDataDispPianificata() {
        return cfDataDispPianificata;
    }

    public void setCfDataDispPianificata(java.sql.Timestamp cfDataDispPianificata) {
        this.cfDataDispPianificata = cfDataDispPianificata;
    }

    public java.sql.Timestamp getCfDataInizio() {
        return cfDataInizio;
    }

    public void setCfDataInizio(java.sql.Timestamp cfDataInizio) {
        this.cfDataInizio = cfDataInizio;
    }

    public java.sql.Timestamp getCfDataInizioEff() {
        return cfDataInizioEff;
    }

    public void setCfDataInizioEff(java.sql.Timestamp cfDataInizioEff) {
        this.cfDataInizioEff = cfDataInizioEff;
    }

    public String getCfFornitura() {
        return cfFornitura;
    }

    public void setCfFornitura(String cfFornitura) {
        this.cfFornitura = cfFornitura;
    }

    public String getDescrizioneProgSvilD() {
        return descrizioneProgSvilD;
    }

    public void setDescrizioneProgSvilD(String descrizioneProgSvilD) {
        this.descrizioneProgSvilD = descrizioneProgSvilD;
    }

    public Integer getDmalmProgSvilDPk() {
        return dmalmProgSvilDPk;
    }

    public void setDmalmProgSvilDPk(Integer dmalmProgSvilDPk) {
        this.dmalmProgSvilDPk = dmalmProgSvilDPk;
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

    public String getDsAutoreProgSvilD() {
        return dsAutoreProgSvilD;
    }

    public void setDsAutoreProgSvilD(String dsAutoreProgSvilD) {
        this.dsAutoreProgSvilD = dsAutoreProgSvilD;
    }

    public java.sql.Timestamp getDtCambioStatoProgSvilD() {
        return dtCambioStatoProgSvilD;
    }

    public void setDtCambioStatoProgSvilD(java.sql.Timestamp dtCambioStatoProgSvilD) {
        this.dtCambioStatoProgSvilD = dtCambioStatoProgSvilD;
    }

    public java.sql.Timestamp getDtCaricamentoProgSvilD() {
        return dtCaricamentoProgSvilD;
    }

    public void setDtCaricamentoProgSvilD(java.sql.Timestamp dtCaricamentoProgSvilD) {
        this.dtCaricamentoProgSvilD = dtCaricamentoProgSvilD;
    }

    public java.sql.Timestamp getDtCreazioneProgSvilD() {
        return dtCreazioneProgSvilD;
    }

    public void setDtCreazioneProgSvilD(java.sql.Timestamp dtCreazioneProgSvilD) {
        this.dtCreazioneProgSvilD = dtCreazioneProgSvilD;
    }

    public java.sql.Timestamp getDtModificaProgSvilD() {
        return dtModificaProgSvilD;
    }

    public void setDtModificaProgSvilD(java.sql.Timestamp dtModificaProgSvilD) {
        this.dtModificaProgSvilD = dtModificaProgSvilD;
    }

    public java.sql.Timestamp getDtPassaggioEsercizio() {
        return dtPassaggioEsercizio;
    }

    public void setDtPassaggioEsercizio(java.sql.Timestamp dtPassaggioEsercizio) {
        this.dtPassaggioEsercizio = dtPassaggioEsercizio;
    }

    public java.sql.Timestamp getDtRisoluzioneProgSvilD() {
        return dtRisoluzioneProgSvilD;
    }

    public void setDtRisoluzioneProgSvilD(java.sql.Timestamp dtRisoluzioneProgSvilD) {
        this.dtRisoluzioneProgSvilD = dtRisoluzioneProgSvilD;
    }

    public java.sql.Timestamp getDtScadenzaProgSvilD() {
        return dtScadenzaProgSvilD;
    }

    public void setDtScadenzaProgSvilD(java.sql.Timestamp dtScadenzaProgSvilD) {
        this.dtScadenzaProgSvilD = dtScadenzaProgSvilD;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreProgSvilD() {
        return idAutoreProgSvilD;
    }

    public void setIdAutoreProgSvilD(String idAutoreProgSvilD) {
        this.idAutoreProgSvilD = idAutoreProgSvilD;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneProgSvilD() {
        return motivoRisoluzioneProgSvilD;
    }

    public void setMotivoRisoluzioneProgSvilD(String motivoRisoluzioneProgSvilD) {
        this.motivoRisoluzioneProgSvilD = motivoRisoluzioneProgSvilD;
    }

    public String getPriorityProgettoSvilDemand() {
        return priorityProgettoSvilDemand;
    }

    public void setPriorityProgettoSvilDemand(String priorityProgettoSvilDemand) {
        this.priorityProgettoSvilDemand = priorityProgettoSvilDemand;
    }

    public Double getRankStatoProgSvilD() {
        return rankStatoProgSvilD;
    }

    public void setRankStatoProgSvilD(Double rankStatoProgSvilD) {
        this.rankStatoProgSvilD = rankStatoProgSvilD;
    }

    public Short getRankStatoProgSvilDMese() {
        return rankStatoProgSvilDMese;
    }

    public void setRankStatoProgSvilDMese(Short rankStatoProgSvilDMese) {
        this.rankStatoProgSvilDMese = rankStatoProgSvilDMese;
    }

    public String getSeverityProgettoSvilDemand() {
        return severityProgettoSvilDemand;
    }

    public void setSeverityProgettoSvilDemand(String severityProgettoSvilDemand) {
        this.severityProgettoSvilDemand = severityProgettoSvilDemand;
    }

    public Integer getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Integer tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getTitoloProgSvilD() {
        return titoloProgSvilD;
    }

    public void setTitoloProgSvilD(String titoloProgSvilD) {
        this.titoloProgSvilD = titoloProgSvilD;
    }

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public java.sql.Timestamp getDtScadenza() {
		return dtScadenza;
	}

	public void setDtScadenza(java.sql.Timestamp dtScadenza) {
		this.dtScadenza = dtScadenza;
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

	public String getTagAlm() {
		return tagAlm;
	}

	public void setTagAlm(String tagAlm) {
		this.tagAlm = tagAlm;
	}

	public java.sql.Timestamp getTsTagAlm() {
		return tsTagAlm;
	}

	public void setTsTagAlm(java.sql.Timestamp tsTagAlm) {
		this.tsTagAlm = tsTagAlm;
	}

}

