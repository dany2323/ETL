package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmProgettoSviluppoSvil is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProgettoSviluppoSvil {

	private String stgPk;
	
    private String cdProgSvilS;

    private String codice;

    private Integer  dmalmProgettoSferaFk;
    
    private java.sql.Timestamp dataChiusuraProgSvilS;

    private java.sql.Timestamp dataDisponibilitaEffettiva;

    private java.sql.Timestamp dataDisponibilitaPianificata;

    private java.sql.Timestamp dataInizio;

    private java.sql.Timestamp dataInizioEff;

    private String descrizioneProgSvilS;

    private Integer dmalmAreaTematicaFk05;

    private Long dmalmProgSvilSPk;

    private Long dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;

    private Integer dmalmUserFk06;
    
    private String dsAutoreProgSvilS;

    private java.sql.Timestamp dtCambioStatoProgSvilS;

    private java.sql.Timestamp dtCaricamentoProgSvilS;

    private java.sql.Timestamp dtCreazioneProgSvilS;

    private java.sql.Timestamp dtModificaProgSvilS;

    private java.sql.Timestamp dtRisoluzioneProgSvilS;

    private java.sql.Timestamp dtScadenzaProgSvilS;

    private java.sql.Timestamp dtStoricizzazione;

    private Short durataEffettivaProgSvilS;

    private String fornitura;

    private String idAutoreProgSvilS;

    private String idRepository;

    private String motivoRisoluzioneProgSvilS;

    private String numeroLinea;

    private String numeroTestata;

    private Double rankStatoProgSvilS;

    private Short rankStatoProgSvilSMese;

    private String titoloProgSvilS;

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
	
    public String getCdProgSvilS() {
        return cdProgSvilS;
    }

    public void setCdProgSvilS(String cdProgSvilS) {
        this.cdProgSvilS = cdProgSvilS;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataChiusuraProgSvilS() {
        return dataChiusuraProgSvilS;
    }

    public void setDataChiusuraProgSvilS(java.sql.Timestamp dataChiusuraProgSvilS) {
        this.dataChiusuraProgSvilS = dataChiusuraProgSvilS;
    }

    public java.sql.Timestamp getDataDisponibilitaEffettiva() {
        return dataDisponibilitaEffettiva;
    }

    public void setDataDisponibilitaEffettiva(java.sql.Timestamp dataDisponibilitaEffettiva) {
        this.dataDisponibilitaEffettiva = dataDisponibilitaEffettiva;
    }

    public java.sql.Timestamp getDataDisponibilitaPianificata() {
        return dataDisponibilitaPianificata;
    }

    public void setDataDisponibilitaPianificata(java.sql.Timestamp dataDisponibilitaPianificata) {
        this.dataDisponibilitaPianificata = dataDisponibilitaPianificata;
    }

    public java.sql.Timestamp getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(java.sql.Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }

    public java.sql.Timestamp getDataInizioEff() {
        return dataInizioEff;
    }

    public void setDataInizioEff(java.sql.Timestamp dataInizioEff) {
        this.dataInizioEff = dataInizioEff;
    }

    public String getDescrizioneProgSvilS() {
        return descrizioneProgSvilS;
    }

    public void setDescrizioneProgSvilS(String descrizioneProgSvilS) {
        this.descrizioneProgSvilS = descrizioneProgSvilS;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
    }

    public Long getDmalmProgSvilSPk() {
        return dmalmProgSvilSPk;
    }

    public void setDmalmProgSvilSPk(Long dmalmProgSvilSPk) {
        this.dmalmProgSvilSPk = dmalmProgSvilSPk;
    }

    public Long getDmalmProjectFk02() {
        return dmalmProjectFk02;
    }

    public void setDmalmProjectFk02(Long dmalmProjectFk02) {
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

    public String getDsAutoreProgSvilS() {
        return dsAutoreProgSvilS;
    }

    public void setDsAutoreProgSvilS(String dsAutoreProgSvilS) {
        this.dsAutoreProgSvilS = dsAutoreProgSvilS;
    }

    public java.sql.Timestamp getDtCambioStatoProgSvilS() {
        return dtCambioStatoProgSvilS;
    }

    public void setDtCambioStatoProgSvilS(java.sql.Timestamp dtCambioStatoProgSvilS) {
        this.dtCambioStatoProgSvilS = dtCambioStatoProgSvilS;
    }

    public java.sql.Timestamp getDtCaricamentoProgSvilS() {
        return dtCaricamentoProgSvilS;
    }

    public void setDtCaricamentoProgSvilS(java.sql.Timestamp dtCaricamentoProgSvilS) {
        this.dtCaricamentoProgSvilS = dtCaricamentoProgSvilS;
    }

    public java.sql.Timestamp getDtCreazioneProgSvilS() {
        return dtCreazioneProgSvilS;
    }

    public void setDtCreazioneProgSvilS(java.sql.Timestamp dtCreazioneProgSvilS) {
        this.dtCreazioneProgSvilS = dtCreazioneProgSvilS;
    }

    public java.sql.Timestamp getDtModificaProgSvilS() {
        return dtModificaProgSvilS;
    }

    public void setDtModificaProgSvilS(java.sql.Timestamp dtModificaProgSvilS) {
        this.dtModificaProgSvilS = dtModificaProgSvilS;
    }

    public java.sql.Timestamp getDtRisoluzioneProgSvilS() {
        return dtRisoluzioneProgSvilS;
    }

    public void setDtRisoluzioneProgSvilS(java.sql.Timestamp dtRisoluzioneProgSvilS) {
        this.dtRisoluzioneProgSvilS = dtRisoluzioneProgSvilS;
    }

    public java.sql.Timestamp getDtScadenzaProgSvilS() {
        return dtScadenzaProgSvilS;
    }

    public void setDtScadenzaProgSvilS(java.sql.Timestamp dtScadenzaProgSvilS) {
        this.dtScadenzaProgSvilS = dtScadenzaProgSvilS;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public Short getDurataEffettivaProgSvilS() {
        return durataEffettivaProgSvilS;
    }

    public void setDurataEffettivaProgSvilS(Short durataEffettivaProgSvilS) {
        this.durataEffettivaProgSvilS = durataEffettivaProgSvilS;
    }

    public String getFornitura() {
        return fornitura;
    }

    public void setFornitura(String fornitura) {
        this.fornitura = fornitura;
    }

    public String getIdAutoreProgSvilS() {
        return idAutoreProgSvilS;
    }

    public void setIdAutoreProgSvilS(String idAutoreProgSvilS) {
        this.idAutoreProgSvilS = idAutoreProgSvilS;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneProgSvilS() {
        return motivoRisoluzioneProgSvilS;
    }

    public void setMotivoRisoluzioneProgSvilS(String motivoRisoluzioneProgSvilS) {
        this.motivoRisoluzioneProgSvilS = motivoRisoluzioneProgSvilS;
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

    public Double getRankStatoProgSvilS() {
        return rankStatoProgSvilS;
    }

    public void setRankStatoProgSvilS(Double rankStatoProgSvilS) {
        this.rankStatoProgSvilS = rankStatoProgSvilS;
    }

    public Short getRankStatoProgSvilSMese() {
        return rankStatoProgSvilSMese;
    }

    public void setRankStatoProgSvilSMese(Short rankStatoProgSvilSMese) {
        this.rankStatoProgSvilSMese = rankStatoProgSvilSMese;
    }

    public String getTitoloProgSvilS() {
        return titoloProgSvilS;
    }

    public void setTitoloProgSvilS(String titoloProgSvilS) {
        this.titoloProgSvilS = titoloProgSvilS;
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

	/**
	 * @return the dmalmProgettoSferaFk
	 */
	public Integer getDmalmProgettoSferaFk() {
		return dmalmProgettoSferaFk;
	}

	/**
	 * @param dmalmProgettoSferaFk the dmalmProgettoSferaFk to set
	 */
	public void setDmalmProgettoSferaFk(Integer dmalmProgettoSferaFk) {
		this.dmalmProgettoSferaFk = dmalmProgettoSferaFk;
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

