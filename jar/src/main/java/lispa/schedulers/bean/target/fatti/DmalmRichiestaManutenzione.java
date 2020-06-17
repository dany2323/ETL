package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmRichiestaManutenzione is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmRichiestaManutenzione {
	
	private String stgPk;

    private String cdRichiestaManutenzione;

    private String classeDiFornitura;

    private String codice;

    private java.sql.Timestamp dataChiusuraRichManut;

    private java.sql.Timestamp dataDispEffettiva;

    private java.sql.Timestamp dataDispPianificata;

    private java.sql.Timestamp dataInizioEffettivo;

    private java.sql.Timestamp dataInizioPianificato;

    private String descrizioneRichManutenzione;

    private Long dmalmProjectFk02;

    private Long dmalmRichManutenzionePk;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreRichManutenzione;

    private java.sql.Timestamp dtCambioStatoRichManut;

    private java.sql.Timestamp dtCaricamentoRichManut;

    private java.sql.Timestamp dtCreazioneRichManutenzione;

    private java.sql.Timestamp dtModificaRichManutenzione;

    private java.sql.Timestamp dtRisoluzioneRichManut;

    private java.sql.Timestamp dtScadenzaRichManutenzione;

    private java.sql.Timestamp dtStoricizzazione;

    private Short durataEffettivaRichMan;

    private String idAutoreRichManutenzione;

    private String idRepository;

    private String motivoRisoluzioneRichManut;

    private Short rankStatoRichManutMese;

    private Double rankStatoRichManutenzione;

    private String titoloRichiestaManutenzione;

    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
	//DM_ALM-320
	private String severity;
		
	private String priority;
	
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
	
    public String getCdRichiestaManutenzione() {
        return cdRichiestaManutenzione;
    }

    public void setCdRichiestaManutenzione(String cdRichiestaManutenzione) {
        this.cdRichiestaManutenzione = cdRichiestaManutenzione;
    }

    public String getClasseDiFornitura() {
        return classeDiFornitura;
    }

    public void setClasseDiFornitura(String classeDiFornitura) {
        this.classeDiFornitura = classeDiFornitura;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataChiusuraRichManut() {
        return dataChiusuraRichManut;
    }

    public void setDataChiusuraRichManut(java.sql.Timestamp dataChiusuraRichManut) {
        this.dataChiusuraRichManut = dataChiusuraRichManut;
    }

    public java.sql.Timestamp getDataDispEffettiva() {
        return dataDispEffettiva;
    }

    public void setDataDispEffettiva(java.sql.Timestamp dataDispEffettiva) {
        this.dataDispEffettiva = dataDispEffettiva;
    }

    public java.sql.Timestamp getDataDispPianificata() {
        return dataDispPianificata;
    }

    public void setDataDispPianificata(java.sql.Timestamp dataDispPianificata) {
        this.dataDispPianificata = dataDispPianificata;
    }

    public java.sql.Timestamp getDataInizioEffettivo() {
        return dataInizioEffettivo;
    }

    public void setDataInizioEffettivo(java.sql.Timestamp dataInizioEffettivo) {
        this.dataInizioEffettivo = dataInizioEffettivo;
    }

    public java.sql.Timestamp getDataInizioPianificato() {
        return dataInizioPianificato;
    }

    public void setDataInizioPianificato(java.sql.Timestamp dataInizioPianificato) {
        this.dataInizioPianificato = dataInizioPianificato;
    }

    public String getDescrizioneRichManutenzione() {
        return descrizioneRichManutenzione;
    }

    public void setDescrizioneRichManutenzione(String descrizioneRichManutenzione) {
        this.descrizioneRichManutenzione = descrizioneRichManutenzione;
    }

    public Long getDmalmProjectFk02() {
        return dmalmProjectFk02;
    }

    public void setDmalmProjectFk02(Long dmalmProjectFk02) {
        this.dmalmProjectFk02 = dmalmProjectFk02;
    }

    public Long getDmalmRichManutenzionePk() {
        return dmalmRichManutenzionePk;
    }

    public void setDmalmRichManutenzionePk(Long dmalmRichManutenzionePk) {
        this.dmalmRichManutenzionePk = dmalmRichManutenzionePk;
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

    public String getDsAutoreRichManutenzione() {
        return dsAutoreRichManutenzione;
    }

    public void setDsAutoreRichManutenzione(String dsAutoreRichManutenzione) {
        this.dsAutoreRichManutenzione = dsAutoreRichManutenzione;
    }

    public java.sql.Timestamp getDtCambioStatoRichManut() {
        return dtCambioStatoRichManut;
    }

    public void setDtCambioStatoRichManut(java.sql.Timestamp dtCambioStatoRichManut) {
        this.dtCambioStatoRichManut = dtCambioStatoRichManut;
    }

    public java.sql.Timestamp getDtCaricamentoRichManut() {
        return dtCaricamentoRichManut;
    }

    public void setDtCaricamentoRichManut(java.sql.Timestamp dtCaricamentoRichManut) {
        this.dtCaricamentoRichManut = dtCaricamentoRichManut;
    }

    public java.sql.Timestamp getDtCreazioneRichManutenzione() {
        return dtCreazioneRichManutenzione;
    }

    public void setDtCreazioneRichManutenzione(java.sql.Timestamp dtCreazioneRichManutenzione) {
        this.dtCreazioneRichManutenzione = dtCreazioneRichManutenzione;
    }

    public java.sql.Timestamp getDtModificaRichManutenzione() {
        return dtModificaRichManutenzione;
    }

    public void setDtModificaRichManutenzione(java.sql.Timestamp dtModificaRichManutenzione) {
        this.dtModificaRichManutenzione = dtModificaRichManutenzione;
    }

    public java.sql.Timestamp getDtRisoluzioneRichManut() {
        return dtRisoluzioneRichManut;
    }

    public void setDtRisoluzioneRichManut(java.sql.Timestamp dtRisoluzioneRichManut) {
        this.dtRisoluzioneRichManut = dtRisoluzioneRichManut;
    }

    public java.sql.Timestamp getDtScadenzaRichManutenzione() {
        return dtScadenzaRichManutenzione;
    }

    public void setDtScadenzaRichManutenzione(java.sql.Timestamp dtScadenzaRichManutenzione) {
        this.dtScadenzaRichManutenzione = dtScadenzaRichManutenzione;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public Short getDurataEffettivaRichMan() {
        return durataEffettivaRichMan;
    }

    public void setDurataEffettivaRichMan(Short durataEffettivaRichMan) {
        this.durataEffettivaRichMan = durataEffettivaRichMan;
    }

    public String getIdAutoreRichManutenzione() {
        return idAutoreRichManutenzione;
    }

    public void setIdAutoreRichManutenzione(String idAutoreRichManutenzione) {
        this.idAutoreRichManutenzione = idAutoreRichManutenzione;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneRichManut() {
        return motivoRisoluzioneRichManut;
    }

    public void setMotivoRisoluzioneRichManut(String motivoRisoluzioneRichManut) {
        this.motivoRisoluzioneRichManut = motivoRisoluzioneRichManut;
    }

    public Short getRankStatoRichManutMese() {
        return rankStatoRichManutMese;
    }

    public void setRankStatoRichManutMese(Short rankStatoRichManutMese) {
        this.rankStatoRichManutMese = rankStatoRichManutMese;
    }

    public Double getRankStatoRichManutenzione() {
        return rankStatoRichManutenzione;
    }

    public void setRankStatoRichManutenzione(Double rankStatoRichManutenzione) {
        this.rankStatoRichManutenzione = rankStatoRichManutenzione;
    }

    public String getTitoloRichiestaManutenzione() {
        return titoloRichiestaManutenzione;
    }

    public void setTitoloRichiestaManutenzione(String titoloRichiestaManutenzione) {
        this.titoloRichiestaManutenzione = titoloRichiestaManutenzione;
    }

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
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

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

