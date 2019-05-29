package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmManutenzione is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmManutenzione {
	
	private String stgPk;

	private Integer dmalmProgettoSferaFk;
	
    private String assigneesManutenzione;

    private String cdManutenzione;

    private String codice;

    private java.sql.Timestamp dataDispEff;

    private java.sql.Timestamp dataDisponibilita;

    private java.sql.Timestamp dataInizio;

    private java.sql.Timestamp dataInizioEff;

    private java.sql.Timestamp dataRilascioInEs;

    private String descrizioneManutenzione;

    private Integer dmalmAreaTematicaFk05;

    private Integer dmalmManutenzionePk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreManutenzione;

    private java.sql.Timestamp dtCambioStatoManutenzione;

    private java.sql.Timestamp dtCaricamentoManutenzione;

    private java.sql.Timestamp dtCreazioneManutenzione;

    private java.sql.Timestamp dtModificaManutenzione;

    private java.sql.Timestamp dtRisoluzioneManutenzione;

    private java.sql.Timestamp dtScadenzaManutenzione;

    private java.sql.Timestamp dtStoricizzazione;

    private String fornitura;

    private String idAutoreManutenzione;

    private String idRepository;

    private String motivoRisoluzioneManutenzion;

    private String numeroLinea;

    private String numeroTestata;

    private String priorityManutenzione;

    private Double rankStatoManutenzione;

    private Short rankStatoManutenzioneMese;

    private String severityManutenzione;

    private Integer tempoTotaleRisoluzione;

    private String titoloManutenzione;

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
	
    public String getAssigneesManutenzione() {
        return assigneesManutenzione;
    }

    public void setAssigneesManutenzione(String assigneesManutenzione) {
        this.assigneesManutenzione = assigneesManutenzione;
    }

    public String getCdManutenzione() {
        return cdManutenzione;
    }

    public void setCdManutenzione(String cdManutenzione) {
        this.cdManutenzione = cdManutenzione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataDispEff() {
        return dataDispEff;
    }

    public void setDataDispEff(java.sql.Timestamp dataDispEff) {
        this.dataDispEff = dataDispEff;
    }

    public java.sql.Timestamp getDataDisponibilita() {
        return dataDisponibilita;
    }

    public void setDataDisponibilita(java.sql.Timestamp dataDisponibilita) {
        this.dataDisponibilita = dataDisponibilita;
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

    public java.sql.Timestamp getDataRilascioInEs() {
        return dataRilascioInEs;
    }

    public void setDataRilascioInEs(java.sql.Timestamp dataRilascioInEs) {
        this.dataRilascioInEs = dataRilascioInEs;
    }

    public String getDescrizioneManutenzione() {
        return descrizioneManutenzione;
    }

    public void setDescrizioneManutenzione(String descrizioneManutenzione) {
        this.descrizioneManutenzione = descrizioneManutenzione;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
    }

    public Integer getDmalmManutenzionePk() {
        return dmalmManutenzionePk;
    }

    public void setDmalmManutenzionePk(Integer dmalmManutenzionePk) {
        this.dmalmManutenzionePk = dmalmManutenzionePk;
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

    public String getDsAutoreManutenzione() {
        return dsAutoreManutenzione;
    }

    public void setDsAutoreManutenzione(String dsAutoreManutenzione) {
        this.dsAutoreManutenzione = dsAutoreManutenzione;
    }

    public java.sql.Timestamp getDtCambioStatoManutenzione() {
        return dtCambioStatoManutenzione;
    }

    public void setDtCambioStatoManutenzione(java.sql.Timestamp dtCambioStatoManutenzione) {
        this.dtCambioStatoManutenzione = dtCambioStatoManutenzione;
    }

    public java.sql.Timestamp getDtCaricamentoManutenzione() {
        return dtCaricamentoManutenzione;
    }

    public void setDtCaricamentoManutenzione(java.sql.Timestamp dtCaricamentoManutenzione) {
        this.dtCaricamentoManutenzione = dtCaricamentoManutenzione;
    }

    public java.sql.Timestamp getDtCreazioneManutenzione() {
        return dtCreazioneManutenzione;
    }

    public void setDtCreazioneManutenzione(java.sql.Timestamp dtCreazioneManutenzione) {
        this.dtCreazioneManutenzione = dtCreazioneManutenzione;
    }

    public java.sql.Timestamp getDtModificaManutenzione() {
        return dtModificaManutenzione;
    }

    public void setDtModificaManutenzione(java.sql.Timestamp dtModificaManutenzione) {
        this.dtModificaManutenzione = dtModificaManutenzione;
    }

    public java.sql.Timestamp getDtRisoluzioneManutenzione() {
        return dtRisoluzioneManutenzione;
    }

    public void setDtRisoluzioneManutenzione(java.sql.Timestamp dtRisoluzioneManutenzione) {
        this.dtRisoluzioneManutenzione = dtRisoluzioneManutenzione;
    }

    public java.sql.Timestamp getDtScadenzaManutenzione() {
        return dtScadenzaManutenzione;
    }

    public void setDtScadenzaManutenzione(java.sql.Timestamp dtScadenzaManutenzione) {
        this.dtScadenzaManutenzione = dtScadenzaManutenzione;
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

    public String getIdAutoreManutenzione() {
        return idAutoreManutenzione;
    }

    public void setIdAutoreManutenzione(String idAutoreManutenzione) {
        this.idAutoreManutenzione = idAutoreManutenzione;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneManutenzion() {
        return motivoRisoluzioneManutenzion;
    }

    public void setMotivoRisoluzioneManutenzion(String motivoRisoluzioneManutenzion) {
        this.motivoRisoluzioneManutenzion = motivoRisoluzioneManutenzion;
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

    public String getPriorityManutenzione() {
        return priorityManutenzione;
    }

    public void setPriorityManutenzione(String priorityManutenzione) {
        this.priorityManutenzione = priorityManutenzione;
    }

    public Double getRankStatoManutenzione() {
        return rankStatoManutenzione;
    }

    public void setRankStatoManutenzione(Double rankStatoManutenzione) {
        this.rankStatoManutenzione = rankStatoManutenzione;
    }

    public Short getRankStatoManutenzioneMese() {
        return rankStatoManutenzioneMese;
    }

    public void setRankStatoManutenzioneMese(Short rankStatoManutenzioneMese) {
        this.rankStatoManutenzioneMese = rankStatoManutenzioneMese;
    }

    public String getSeverityManutenzione() {
        return severityManutenzione;
    }

    public void setSeverityManutenzione(String severityManutenzione) {
        this.severityManutenzione = severityManutenzione;
    }

    public Integer getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Integer tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getTitoloManutenzione() {
        return titoloManutenzione;
    }

    public void setTitoloManutenzione(String titoloManutenzione) {
        this.titoloManutenzione = titoloManutenzione;
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

