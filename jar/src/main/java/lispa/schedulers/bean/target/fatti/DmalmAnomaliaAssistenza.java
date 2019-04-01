package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmAnomaliaAssistenza is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmAnomaliaAssistenza {

    private String aoid;

    private String ca;

    private String cdAnomaliaAss;

    private String cs;

    private String descrizioneAnomaliaAss;

    private Integer dmalmAnomaliaAssPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreAnomaliaAss;

    private java.sql.Timestamp dtCambioStatoAnomaliaAss;

    private java.sql.Timestamp dtCaricamentoAnomaliaAss;

    private java.sql.Timestamp dtCreazioneAnomaliaAss;

    private java.sql.Timestamp dtModificaAnomaliaAss;

    private java.sql.Timestamp dtRisoluzioneAnomaliaAss;

    private java.sql.Timestamp dtScadenzaAnomaliaAss;

    private java.sql.Timestamp dtStoricizzazione;

    private String frequenza;

    private String idAutoreAnomaliaAss;

    private String idRepository;

    private String motivoRisoluzioneAnomaliaAs;

    private String platform;

    private String prodCod;

    private Double rankStatoAnomaliaAss;

    private Short rankStatoAnomaliaAssMese;

    private String segnalazioni;

    private String so;

    private java.sql.Timestamp stChiuso;

    private String stgPk;

    private Integer tempoTotaleRisoluzione;

    private String ticketid;

    private String titoloAnomaliaAss;
    
    private String severity;
    
    private String priority;

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


    public String getAoid() {
        return aoid;
    }

    public void setAoid(String aoid) {
        this.aoid = aoid;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getCdAnomaliaAss() {
        return cdAnomaliaAss;
    }

    public void setCdAnomaliaAss(String cdAnomaliaAss) {
        this.cdAnomaliaAss = cdAnomaliaAss;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getDescrizioneAnomaliaAss() {
        return descrizioneAnomaliaAss;
    }

    public void setDescrizioneAnomaliaAss(String descrizioneAnomaliaAss) {
        this.descrizioneAnomaliaAss = descrizioneAnomaliaAss;
    }

    public Integer getDmalmAnomaliaAssPk() {
        return dmalmAnomaliaAssPk;
    }

    public void setDmalmAnomaliaAssPk(Integer dmalmAnomaliaAssPk) {
        this.dmalmAnomaliaAssPk = dmalmAnomaliaAssPk;
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

    public String getDsAutoreAnomaliaAss() {
        return dsAutoreAnomaliaAss;
    }

    public void setDsAutoreAnomaliaAss(String dsAutoreAnomaliaAss) {
        this.dsAutoreAnomaliaAss = dsAutoreAnomaliaAss;
    }

    public java.sql.Timestamp getDtCambioStatoAnomaliaAss() {
        return dtCambioStatoAnomaliaAss;
    }

    public void setDtCambioStatoAnomaliaAss(java.sql.Timestamp dtCambioStatoAnomaliaAss) {
        this.dtCambioStatoAnomaliaAss = dtCambioStatoAnomaliaAss;
    }

    public java.sql.Timestamp getDtCaricamentoAnomaliaAss() {
        return dtCaricamentoAnomaliaAss;
    }

    public void setDtCaricamentoAnomaliaAss(java.sql.Timestamp dtCaricamentoAnomaliaAss) {
        this.dtCaricamentoAnomaliaAss = dtCaricamentoAnomaliaAss;
    }

    public java.sql.Timestamp getDtCreazioneAnomaliaAss() {
        return dtCreazioneAnomaliaAss;
    }

    public void setDtCreazioneAnomaliaAss(java.sql.Timestamp dtCreazioneAnomaliaAss) {
        this.dtCreazioneAnomaliaAss = dtCreazioneAnomaliaAss;
    }

    public java.sql.Timestamp getDtModificaAnomaliaAss() {
        return dtModificaAnomaliaAss;
    }

    public void setDtModificaAnomaliaAss(java.sql.Timestamp dtModificaAnomaliaAss) {
        this.dtModificaAnomaliaAss = dtModificaAnomaliaAss;
    }

    public java.sql.Timestamp getDtRisoluzioneAnomaliaAss() {
        return dtRisoluzioneAnomaliaAss;
    }

    public void setDtRisoluzioneAnomaliaAss(java.sql.Timestamp dtRisoluzioneAnomaliaAss) {
        this.dtRisoluzioneAnomaliaAss = dtRisoluzioneAnomaliaAss;
    }

    public java.sql.Timestamp getDtScadenzaAnomaliaAss() {
        return dtScadenzaAnomaliaAss;
    }

    public void setDtScadenzaAnomaliaAss(java.sql.Timestamp dtScadenzaAnomaliaAss) {
        this.dtScadenzaAnomaliaAss = dtScadenzaAnomaliaAss;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getFrequenza() {
        return frequenza;
    }

    public void setFrequenza(String frequenza) {
        this.frequenza = frequenza;
    }

    public String getIdAutoreAnomaliaAss() {
        return idAutoreAnomaliaAss;
    }

    public void setIdAutoreAnomaliaAss(String idAutoreAnomaliaAss) {
        this.idAutoreAnomaliaAss = idAutoreAnomaliaAss;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneAnomaliaAs() {
        return motivoRisoluzioneAnomaliaAs;
    }

    public void setMotivoRisoluzioneAnomaliaAs(String motivoRisoluzioneAnomaliaAs) {
        this.motivoRisoluzioneAnomaliaAs = motivoRisoluzioneAnomaliaAs;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProdCod() {
        return prodCod;
    }

    public void setProdCod(String prodCod) {
        this.prodCod = prodCod;
    }

    public Double getRankStatoAnomaliaAss() {
        return rankStatoAnomaliaAss;
    }

    public void setRankStatoAnomaliaAss(Double rankStatoAnomaliaAss) {
        this.rankStatoAnomaliaAss = rankStatoAnomaliaAss;
    }

    public Short getRankStatoAnomaliaAssMese() {
        return rankStatoAnomaliaAssMese;
    }

    public void setRankStatoAnomaliaAssMese(Short rankStatoAnomaliaAssMese) {
        this.rankStatoAnomaliaAssMese = rankStatoAnomaliaAssMese;
    }

    public String getSegnalazioni() {
        return segnalazioni;
    }

    public void setSegnalazioni(String segnalazioni) {
        this.segnalazioni = segnalazioni;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public java.sql.Timestamp getStChiuso() {
        return stChiuso;
    }

    public void setStChiuso(java.sql.Timestamp stChiuso) {
        this.stChiuso = stChiuso;
    }

    public String getStgPk() {
        return stgPk;
    }

    public void setStgPk(String stgPk) {
        this.stgPk = stgPk;
    }

    public Integer getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Integer tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getTitoloAnomaliaAss() {
        return titoloAnomaliaAss;
    }

    public void setTitoloAnomaliaAss(String titoloAnomaliaAss) {
        this.titoloAnomaliaAss = titoloAnomaliaAss;
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

