package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmProgettoDemand is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProgettoDemand {

    private String aoid;

    private String cdProgettoDemand;

    private java.sql.Timestamp cfDtDisponibilita;

    private java.sql.Timestamp cfDtDisponibilitaEff;

    private java.sql.Timestamp cfDtEnunciazione;

    private java.sql.Timestamp cfDtValidazione;

    private String cfOwnerDemand;

    private String cfReferenteEsercizio;

    private String cfReferenteSviluppo;

    private String codice;

    private String descrizioneProgettoDemand;

    private Integer dmalmProgettoDemandPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreProgettoDemand;

    private java.sql.Timestamp dtCambioStatoProgettoDem;

    private java.sql.Timestamp dtCaricamentoProgettoDemand;

    private java.sql.Timestamp dtChiusuraProgettoDemand;

    private java.sql.Timestamp dtCreazioneProgettoDemand;

    private java.sql.Timestamp dtModificaProgettoDemand;

    private java.sql.Timestamp dtRisoluzioneProgettoDemand;

    private java.sql.Timestamp dtScadenzaProgettoDemand;

    private java.sql.Timestamp dtStoricizzazione;

    private String fornitura;

    private String idAutoreProgettoDemand;

    private String idRepository;

    private String motivoRisoluzioneProgDem;

    private Short rankStatoProgettoDemMese;

    private Double rankStatoProgettoDemand;

    private Integer tempoTotaleRisoluzione;

    private String titoloProgettoDemand;
    
    private String stgPk;

    private String uri;
    
    private String codObiettivoAziendale;
    
    private String codObiettivoUtente;
    
    private String changed;
    
    private String cfClassificazione;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
    public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}
    

    public String getCodObiettivoAziendale() {
		return codObiettivoAziendale;
	}

	public void setCodObiettivoAziendale(String codObiettivoAziendale) {
		this.codObiettivoAziendale = codObiettivoAziendale;
	}

	public String getCodObiettivoUtente() {
		return codObiettivoUtente;
	}

	public void setCodObiettivoUtente(String codObiettivoUtente) {
		this.codObiettivoUtente = codObiettivoUtente;
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

	public String getAoid() {
        return aoid;
    }

    public void setAoid(String aoid) {
        this.aoid = aoid;
    }

    public String getCdProgettoDemand() {
        return cdProgettoDemand;
    }

    public void setCdProgettoDemand(String cdProgettoDemand) {
        this.cdProgettoDemand = cdProgettoDemand;
    }

    public java.sql.Timestamp getCfDtDisponibilita() {
        return cfDtDisponibilita;
    }

    public void setCfDtDisponibilita(java.sql.Timestamp cfDtDisponibilita) {
        this.cfDtDisponibilita = cfDtDisponibilita;
    }

    public java.sql.Timestamp getCfDtDisponibilitaEff() {
        return cfDtDisponibilitaEff;
    }

    public void setCfDtDisponibilitaEff(java.sql.Timestamp cfDtDisponibilitaEff) {
        this.cfDtDisponibilitaEff = cfDtDisponibilitaEff;
    }

    public java.sql.Timestamp getCfDtEnunciazione() {
        return cfDtEnunciazione;
    }

    public void setCfDtEnunciazione(java.sql.Timestamp cfDtEnunciazione) {
        this.cfDtEnunciazione = cfDtEnunciazione;
    }

    public java.sql.Timestamp getCfDtValidazione() {
        return cfDtValidazione;
    }

    public void setCfDtValidazione(java.sql.Timestamp cfDtValidazione) {
        this.cfDtValidazione = cfDtValidazione;
    }

    public String getCfOwnerDemand() {
        return cfOwnerDemand;
    }

    public void setCfOwnerDemand(String cfOwnerDemand) {
        this.cfOwnerDemand = cfOwnerDemand;
    }

    public String getCfReferenteEsercizio() {
        return cfReferenteEsercizio;
    }

    public void setCfReferenteEsercizio(String cfReferenteEsercizio) {
        this.cfReferenteEsercizio = cfReferenteEsercizio;
    }

    public String getCfReferenteSviluppo() {
        return cfReferenteSviluppo;
    }

    public void setCfReferenteSviluppo(String cfReferenteSviluppo) {
        this.cfReferenteSviluppo = cfReferenteSviluppo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizioneProgettoDemand() {
        return descrizioneProgettoDemand;
    }

    public void setDescrizioneProgettoDemand(String descrizioneProgettoDemand) {
        this.descrizioneProgettoDemand = descrizioneProgettoDemand;
    }

    public Integer getDmalmProgettoDemandPk() {
        return dmalmProgettoDemandPk;
    }

    public void setDmalmProgettoDemandPk(Integer dmalmProgettoDemandPk) {
        this.dmalmProgettoDemandPk = dmalmProgettoDemandPk;
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

    public String getDsAutoreProgettoDemand() {
        return dsAutoreProgettoDemand;
    }

    public void setDsAutoreProgettoDemand(String dsAutoreProgettoDemand) {
        this.dsAutoreProgettoDemand = dsAutoreProgettoDemand;
    }

    public java.sql.Timestamp getDtCambioStatoProgettoDem() {
        return dtCambioStatoProgettoDem;
    }

    public void setDtCambioStatoProgettoDem(java.sql.Timestamp dtCambioStatoProgettoDem) {
        this.dtCambioStatoProgettoDem = dtCambioStatoProgettoDem;
    }

    public java.sql.Timestamp getDtCaricamentoProgettoDemand() {
        return dtCaricamentoProgettoDemand;
    }

    public void setDtCaricamentoProgettoDemand(java.sql.Timestamp dtCaricamentoProgettoDemand) {
        this.dtCaricamentoProgettoDemand = dtCaricamentoProgettoDemand;
    }

    public java.sql.Timestamp getDtChiusuraProgettoDemand() {
        return dtChiusuraProgettoDemand;
    }

    public void setDtChiusuraProgettoDemand(java.sql.Timestamp dtChiusuraProgettoDemand) {
        this.dtChiusuraProgettoDemand = dtChiusuraProgettoDemand;
    }

    public java.sql.Timestamp getDtCreazioneProgettoDemand() {
        return dtCreazioneProgettoDemand;
    }

    public void setDtCreazioneProgettoDemand(java.sql.Timestamp dtCreazioneProgettoDemand) {
        this.dtCreazioneProgettoDemand = dtCreazioneProgettoDemand;
    }

    public java.sql.Timestamp getDtModificaProgettoDemand() {
        return dtModificaProgettoDemand;
    }

    public void setDtModificaProgettoDemand(java.sql.Timestamp dtModificaProgettoDemand) {
        this.dtModificaProgettoDemand = dtModificaProgettoDemand;
    }

    public java.sql.Timestamp getDtRisoluzioneProgettoDemand() {
        return dtRisoluzioneProgettoDemand;
    }

    public void setDtRisoluzioneProgettoDemand(java.sql.Timestamp dtRisoluzioneProgettoDemand) {
        this.dtRisoluzioneProgettoDemand = dtRisoluzioneProgettoDemand;
    }

    public java.sql.Timestamp getDtScadenzaProgettoDemand() {
        return dtScadenzaProgettoDemand;
    }

    public void setDtScadenzaProgettoDemand(java.sql.Timestamp dtScadenzaProgettoDemand) {
        this.dtScadenzaProgettoDemand = dtScadenzaProgettoDemand;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getFornitura() {
        return fornitura;
    }

    public void setFornitura(String fornitura) {
        this.fornitura = fornitura;
    }

    public String getIdAutoreProgettoDemand() {
        return idAutoreProgettoDemand;
    }

    public void setIdAutoreProgettoDemand(String idAutoreProgettoDemand) {
        this.idAutoreProgettoDemand = idAutoreProgettoDemand;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneProgDem() {
        return motivoRisoluzioneProgDem;
    }

    public void setMotivoRisoluzioneProgDem(String motivoRisoluzioneProgDem) {
        this.motivoRisoluzioneProgDem = motivoRisoluzioneProgDem;
    }

    public Short getRankStatoProgettoDemMese() {
        return rankStatoProgettoDemMese;
    }

    public void setRankStatoProgettoDemMese(Short rankStatoProgettoDemMese) {
        this.rankStatoProgettoDemMese = rankStatoProgettoDemMese;
    }

    public Double getRankStatoProgettoDemand() {
        return rankStatoProgettoDemand;
    }

    public void setRankStatoProgettoDemand(Double rankStatoProgettoDemand) {
        this.rankStatoProgettoDemand = rankStatoProgettoDemand;
    }

    public Integer getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Integer tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getTitoloProgettoDemand() {
        return titoloProgettoDemand;
    }

    public void setTitoloProgettoDemand(String titoloProgettoDemand) {
        this.titoloProgettoDemand = titoloProgettoDemand;
    }

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public String getCfClassificazione() {
		return cfClassificazione;
	}

	public void setCfClassificazione(String cfClassificazione) {
		this.cfClassificazione = cfClassificazione;
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

