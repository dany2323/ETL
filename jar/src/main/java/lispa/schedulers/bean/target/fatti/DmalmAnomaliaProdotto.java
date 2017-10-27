package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmAnomaliaProdotto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmAnomaliaProdotto {

    private String annullato;

    private String cdAnomalia;

    private String descrizioneAnomalia;

    private Integer dmalmAnomaliaProdottoPk;

    private Integer dmalmAreaTematicaFk05;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAnomalia;

    private String dsAutoreAnomalia;

    private java.sql.Timestamp dtAperturaTicket;

    private java.sql.Timestamp dtCambioStatoAnomalia;

    private java.sql.Timestamp dtCaricamentoRecordAnomalia;

    private java.sql.Timestamp dtChiusuraAnomalia;

    private java.sql.Timestamp dtChiusuraTicket;

    private java.sql.Timestamp dtCreazioneAnomalia;

    private java.sql.Timestamp dtModificaRecordAnomalia;

    private java.sql.Timestamp dtRisoluzioneAnomalia;

    private java.sql.Timestamp dtStoricizzazione;

    private Integer effortAnalisi;

    private Integer effortCostoSviluppo;

    private String idAnomaliaAssistenza;

    private String idAutoreAnomalia;

    private String idRepository;

    private String motivoRisoluzioneAnomalia;

    private Integer nrGiorniFestivi;

    private String numeroLineaAnomalia;

    private String numeroTestataAnomalia;

    private Double rankStatoAnomalia;

    private Short rankStatoAnomaliaMese;

    private String severity;

    private String stgPk;

    private Integer tempoTotRisoluzioneAnomalia;

    private String ticketSiebelAnomaliaAss;

    private String uri;
    
    private String changed;
    
    private Short flagUltimaSituazione;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String contestazione;
	
	private String noteContestazione;
	
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
	
    public String getAnnullato() {
        return annullato;
    }

    public void setAnnullato(String annullato) {
        this.annullato = annullato;
    }

    public String getCdAnomalia() {
        return cdAnomalia;
    }

    public void setCdAnomalia(String cdAnomalia) {
        this.cdAnomalia = cdAnomalia;
    }

    public String getDescrizioneAnomalia() {
        return descrizioneAnomalia;
    }

    public void setDescrizioneAnomalia(String descrizioneAnomalia) {
        this.descrizioneAnomalia = descrizioneAnomalia;
    }

    public Integer getDmalmAnomaliaProdottoPk() {
        return dmalmAnomaliaProdottoPk;
    }

    public void setDmalmAnomaliaProdottoPk(Integer dmalmAnomaliaProdottoPk) {
        this.dmalmAnomaliaProdottoPk = dmalmAnomaliaProdottoPk;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
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

    public String getDsAnomalia() {
        return dsAnomalia;
    }

    public void setDsAnomalia(String dsAnomalia) {
        this.dsAnomalia = dsAnomalia;
    }

    public String getDsAutoreAnomalia() {
        return dsAutoreAnomalia;
    }

    public void setDsAutoreAnomalia(String dsAutoreAnomalia) {
        this.dsAutoreAnomalia = dsAutoreAnomalia;
    }

    public java.sql.Timestamp getDtAperturaTicket() {
        return dtAperturaTicket;
    }

    public void setDtAperturaTicket(java.sql.Timestamp dtAperturaTicket) {
        this.dtAperturaTicket = dtAperturaTicket;
    }

    public java.sql.Timestamp getDtCambioStatoAnomalia() {
        return dtCambioStatoAnomalia;
    }

    public void setDtCambioStatoAnomalia(java.sql.Timestamp dtCambioStatoAnomalia) {
        this.dtCambioStatoAnomalia = dtCambioStatoAnomalia;
    }

    public java.sql.Timestamp getDtCaricamentoRecordAnomalia() {
        return dtCaricamentoRecordAnomalia;
    }

    public void setDtCaricamentoRecordAnomalia(java.sql.Timestamp dtCaricamentoRecordAnomalia) {
        this.dtCaricamentoRecordAnomalia = dtCaricamentoRecordAnomalia;
    }

    public java.sql.Timestamp getDtChiusuraAnomalia() {
        return dtChiusuraAnomalia;
    }

    public void setDtChiusuraAnomalia(java.sql.Timestamp dtChiusuraAnomalia) {
        this.dtChiusuraAnomalia = dtChiusuraAnomalia;
    }

    public java.sql.Timestamp getDtChiusuraTicket() {
        return dtChiusuraTicket;
    }

    public void setDtChiusuraTicket(java.sql.Timestamp dtChiusuraTicket) {
        this.dtChiusuraTicket = dtChiusuraTicket;
    }

    public java.sql.Timestamp getDtCreazioneAnomalia() {
        return dtCreazioneAnomalia;
    }

    public void setDtCreazioneAnomalia(java.sql.Timestamp dtCreazioneAnomalia) {
        this.dtCreazioneAnomalia = dtCreazioneAnomalia;
    }

    public java.sql.Timestamp getDtModificaRecordAnomalia() {
        return dtModificaRecordAnomalia;
    }

    public void setDtModificaRecordAnomalia(java.sql.Timestamp dtModificaRecordAnomalia) {
        this.dtModificaRecordAnomalia = dtModificaRecordAnomalia;
    }

    public java.sql.Timestamp getDtRisoluzioneAnomalia() {
        return dtRisoluzioneAnomalia;
    }

    public void setDtRisoluzioneAnomalia(java.sql.Timestamp dtRisoluzioneAnomalia) {
        this.dtRisoluzioneAnomalia = dtRisoluzioneAnomalia;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public Integer getEffortAnalisi() {
        return effortAnalisi;
    }

    public void setEffortAnalisi(Integer effortAnalisi) {
        this.effortAnalisi = effortAnalisi;
    }

    public Integer getEffortCostoSviluppo() {
        return effortCostoSviluppo;
    }

    public void setEffortCostoSviluppo(Integer effortCostoSviluppo) {
        this.effortCostoSviluppo = effortCostoSviluppo;
    }

    public String getIdAnomaliaAssistenza() {
        return idAnomaliaAssistenza;
    }

    public void setIdAnomaliaAssistenza(String idAnomaliaAssistenza) {
        this.idAnomaliaAssistenza = idAnomaliaAssistenza;
    }

    public String getIdAutoreAnomalia() {
        return idAutoreAnomalia;
    }

    public void setIdAutoreAnomalia(String idAutoreAnomalia) {
        this.idAutoreAnomalia = idAutoreAnomalia;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneAnomalia() {
        return motivoRisoluzioneAnomalia;
    }

    public void setMotivoRisoluzioneAnomalia(String motivoRisoluzioneAnomalia) {
        this.motivoRisoluzioneAnomalia = motivoRisoluzioneAnomalia;
    }

    public Integer getNrGiorniFestivi() {
        return nrGiorniFestivi;
    }

    public void setNrGiorniFestivi(Integer nrGiorniFestivi) {
        this.nrGiorniFestivi = nrGiorniFestivi;
    }

    public String getNumeroLineaAnomalia() {
        return numeroLineaAnomalia;
    }

    public void setNumeroLineaAnomalia(String numeroLineaAnomalia) {
        this.numeroLineaAnomalia = numeroLineaAnomalia;
    }

    public String getNumeroTestataAnomalia() {
        return numeroTestataAnomalia;
    }

    public void setNumeroTestataAnomalia(String numeroTestataAnomalia) {
        this.numeroTestataAnomalia = numeroTestataAnomalia;
    }

    public Double getRankStatoAnomalia() {
        return rankStatoAnomalia;
    }

    public void setRankStatoAnomalia(Double rankStatoAnomalia) {
        this.rankStatoAnomalia = rankStatoAnomalia;
    }

    public Short getRankStatoAnomaliaMese() {
        return rankStatoAnomaliaMese;
    }

    public void setRankStatoAnomaliaMese(Short rankStatoAnomaliaMese) {
        this.rankStatoAnomaliaMese = rankStatoAnomaliaMese;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStgPk() {
        return stgPk;
    }

    public void setStgPk(String stgPk) {
        this.stgPk = stgPk;
    }

    public Integer getTempoTotRisoluzioneAnomalia() {
        return tempoTotRisoluzioneAnomalia;
    }

    public void setTempoTotRisoluzioneAnomalia(Integer tempoTotRisoluzioneAnomalia) {
        this.tempoTotRisoluzioneAnomalia = tempoTotRisoluzioneAnomalia;
    }

    public String getTicketSiebelAnomaliaAss() {
        return ticketSiebelAnomaliaAss;
    }

    public void setTicketSiebelAnomaliaAss(String ticketSiebelAnomaliaAss) {
        this.ticketSiebelAnomaliaAss = ticketSiebelAnomaliaAss;
    }

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
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

	public String getContestazione() {
		return contestazione;
	}

	public void setContestazione(String contestazione) {
		this.contestazione = contestazione;
	}

	public String getNoteContestazione() {
		return noteContestazione;
	}

	public void setNoteContestazione(String noteContestazione) {
		this.noteContestazione = noteContestazione;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}

