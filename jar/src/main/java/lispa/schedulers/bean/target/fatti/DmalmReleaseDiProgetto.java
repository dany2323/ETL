package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmReleaseDiProgetto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmReleaseDiProgetto {
	
	private String stgPk;

    private String cdReleasediprog;

    private String codice;

    private java.sql.Timestamp dataDisponibilitaEff;

    private java.sql.Timestamp dataPassaggioInEsercizio;

    private String descrizioneReleasediprog;

    private Integer dmalmProjectFk02;

    private Integer dmalmReleasediprogPk;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmAreaTematicaFk05;
    
    private Integer dmalmUserFk06;
    
    private Integer dmalmTempoFk04;

    private String dsAutoreReleasediprog;

    private java.sql.Timestamp dtCambioStatoReleasediprog;

    private java.sql.Timestamp dtCaricamentoReleasediprog;

    private java.sql.Timestamp dtCreazioneReleasediprog;

    private java.sql.Timestamp dtModificaReleasediprog;

    private java.sql.Timestamp dtRisoluzioneReleasediprog;

    private java.sql.Timestamp dtScadenzaReleasediprog;

    private java.sql.Timestamp dtStoricizzazione;

    private String fornitura;

    private Boolean fr;

    private String idAutoreReleasediprog;

    private String idRepository;

    private String motivoRisoluzioneRelProg;

    private String numeroLinea;

    private String numeroTestata;

    private Double rankStatoReleasediprog;

    private Short rankStatoReleasediprogMese;

    private String titoloReleasediprog;

    private String versione;

    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private java.sql.Timestamp dtInizioQF;
	
	private java.sql.Timestamp dtFineQF;
	
	private Integer numQuickFix;
	
	private String annullato;
    
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
	
    public String getCdReleasediprog() {
        return cdReleasediprog;
    }

    public void setCdReleasediprog(String cdReleasediprog) {
        this.cdReleasediprog = cdReleasediprog;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataDisponibilitaEff() {
        return dataDisponibilitaEff;
    }

    public void setDataDisponibilitaEff(java.sql.Timestamp dataDisponibilitaEff) {
        this.dataDisponibilitaEff = dataDisponibilitaEff;
    }

    public java.sql.Timestamp getDataPassaggioInEsercizio() {
        return dataPassaggioInEsercizio;
    }

    public void setDataPassaggioInEsercizio(java.sql.Timestamp dataPassaggioInEsercizio) {
        this.dataPassaggioInEsercizio = dataPassaggioInEsercizio;
    }

    public String getDescrizioneReleasediprog() {
        return descrizioneReleasediprog;
    }

    public void setDescrizioneReleasediprog(String descrizioneReleasediprog) {
        this.descrizioneReleasediprog = descrizioneReleasediprog;
    }

    public Integer getDmalmProjectFk02() {
        return dmalmProjectFk02;
    }

    public void setDmalmProjectFk02(Integer dmalmProjectFk02) {
        this.dmalmProjectFk02 = dmalmProjectFk02;
    }

    public Integer getDmalmReleasediprogPk() {
        return dmalmReleasediprogPk;
    }

    public void setDmalmReleasediprogPk(Integer dmalmReleasediprogPk) {
        this.dmalmReleasediprogPk = dmalmReleasediprogPk;
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

    public String getDsAutoreReleasediprog() {
        return dsAutoreReleasediprog;
    }

    public void setDsAutoreReleasediprog(String dsAutoreReleasediprog) {
        this.dsAutoreReleasediprog = dsAutoreReleasediprog;
    }

    public java.sql.Timestamp getDtCambioStatoReleasediprog() {
        return dtCambioStatoReleasediprog;
    }

    public void setDtCambioStatoReleasediprog(java.sql.Timestamp dtCambioStatoReleasediprog) {
        this.dtCambioStatoReleasediprog = dtCambioStatoReleasediprog;
    }

    public java.sql.Timestamp getDtCaricamentoReleasediprog() {
        return dtCaricamentoReleasediprog;
    }

    public void setDtCaricamentoReleasediprog(java.sql.Timestamp dtCaricamentoReleasediprog) {
        this.dtCaricamentoReleasediprog = dtCaricamentoReleasediprog;
    }

    public java.sql.Timestamp getDtCreazioneReleasediprog() {
        return dtCreazioneReleasediprog;
    }

    public void setDtCreazioneReleasediprog(java.sql.Timestamp dtCreazioneReleasediprog) {
        this.dtCreazioneReleasediprog = dtCreazioneReleasediprog;
    }

    public java.sql.Timestamp getDtModificaReleasediprog() {
        return dtModificaReleasediprog;
    }

    public void setDtModificaReleasediprog(java.sql.Timestamp dtModificaReleasediprog) {
        this.dtModificaReleasediprog = dtModificaReleasediprog;
    }

    public java.sql.Timestamp getDtRisoluzioneReleasediprog() {
        return dtRisoluzioneReleasediprog;
    }

    public void setDtRisoluzioneReleasediprog(java.sql.Timestamp dtRisoluzioneReleasediprog) {
        this.dtRisoluzioneReleasediprog = dtRisoluzioneReleasediprog;
    }

    public java.sql.Timestamp getDtScadenzaReleasediprog() {
        return dtScadenzaReleasediprog;
    }

    public void setDtScadenzaReleasediprog(java.sql.Timestamp dtScadenzaReleasediprog) {
        this.dtScadenzaReleasediprog = dtScadenzaReleasediprog;
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

    public Boolean getFr() {
        return fr;
    }

    public void setFr(Boolean fr) {
        this.fr = fr;
    }

    public String getIdAutoreReleasediprog() {
        return idAutoreReleasediprog;
    }

    public void setIdAutoreReleasediprog(String idAutoreReleasediprog) {
        this.idAutoreReleasediprog = idAutoreReleasediprog;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneRelProg() {
        return motivoRisoluzioneRelProg;
    }

    public void setMotivoRisoluzioneRelProg(String motivoRisoluzioneRelProg) {
        this.motivoRisoluzioneRelProg = motivoRisoluzioneRelProg;
    }

    public String getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(String numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getNumeroTestata() {
        return numeroTestata;
    }

    public void setNumeroTestata(String numeroTestata) {
        this.numeroTestata = numeroTestata;
    }

    public Double getRankStatoReleasediprog() {
        return rankStatoReleasediprog;
    }

    public void setRankStatoReleasediprog(Double rankStatoReleasediprog) {
        this.rankStatoReleasediprog = rankStatoReleasediprog;
    }

    public Short getRankStatoReleasediprogMese() {
        return rankStatoReleasediprogMese;
    }

    public void setRankStatoReleasediprogMese(Short rankStatoReleasediprogMese) {
        this.rankStatoReleasediprogMese = rankStatoReleasediprogMese;
    }

    public String getTitoloReleasediprog() {
        return titoloReleasediprog;
    }

    public void setTitoloReleasediprog(String titoloReleasediprog) {
        this.titoloReleasediprog = titoloReleasediprog;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}


	public Integer getDmalmAreaTematicaFk05() {
		return dmalmAreaTematicaFk05;
	}

	public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
		this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
	}

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}

	public java.sql.Timestamp getDtInizioQF() {
		return dtInizioQF;
	}

	public void setDtInizioQF(java.sql.Timestamp dtInizioQF) {
		this.dtInizioQF = dtInizioQF;
	}

	public java.sql.Timestamp getDtFineQF() {
		return dtFineQF;
	}

	public void setDtFineQF(java.sql.Timestamp dtFineQF) {
		this.dtFineQF = dtFineQF;
	}

	public Integer getNumQuickFix() {
		return numQuickFix;
	}

	public void setNumQuickFix(Integer numQuickFix) {
		this.numQuickFix = numQuickFix;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}


}

