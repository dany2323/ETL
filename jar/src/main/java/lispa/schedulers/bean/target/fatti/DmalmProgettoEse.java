package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmProgettoEse is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProgettoEse {

    private String stgPk;

    private String cdProgettoEse;

    private String cfCodice;

    private java.sql.Timestamp cfDtUltimaSottomissione;

    private String descrizioneProgettoEse;

    private Integer dmalmProgettoEsePk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreProgettoEse;

    private java.sql.Timestamp dtCambioStatoProgettoEse;

    private java.sql.Timestamp dtCaricamentoProgettoEse;

    private java.sql.Timestamp dtCreazioneProgettoEse;

    private java.sql.Timestamp dtModificaProgettoEse;

    private java.sql.Timestamp dtRisoluzioneProgettoEse;

    private java.sql.Timestamp dtScadenzaProgettoEse;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreProgettoEse;

    private String idRepository;

    private String motivoRisoluzioneProgEse;

    private Double rankStatoProgettoEse;

    private Short rankStatoProgettoEseMese;

    private String titoloProgettoEse;

    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private java.sql.Timestamp ts_tag_alm;
	
	private String tag_alm;
	
	public java.sql.Timestamp getTs_tag_alm() {
		return ts_tag_alm;
	}

	public void setTs_tag_alm(java.sql.Timestamp ts_tag_alm) {
		this.ts_tag_alm = ts_tag_alm;
	}

	public String getTag_alm() {
		return tag_alm;
	}

	public void setTag_alm(String tag_alm) {
		this.tag_alm = tag_alm;
	}

	private String annullato;
    
	//DM_ALM-320
	private String severity;
		
	private String priority;
	
	
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

    public String getCdProgettoEse() {
        return cdProgettoEse;
    }

    public void setCdProgettoEse(String cdProgettoEse) {
        this.cdProgettoEse = cdProgettoEse;
    }

    public String getCfCodice() {
        return cfCodice;
    }

    public void setCfCodice(String cfCodice) {
        this.cfCodice = cfCodice;
    }

    public java.sql.Timestamp getCfDtUltimaSottomissione() {
        return cfDtUltimaSottomissione;
    }

    public void setCfDtUltimaSottomissione(java.sql.Timestamp cfDtUltimaSottomissione) {
        this.cfDtUltimaSottomissione = cfDtUltimaSottomissione;
    }

    public String getDescrizioneProgettoEse() {
        return descrizioneProgettoEse;
    }

    public void setDescrizioneProgettoEse(String descrizioneProgettoEse) {
        this.descrizioneProgettoEse = descrizioneProgettoEse;
    }

    public Integer getDmalmProgettoEsePk() {
        return dmalmProgettoEsePk;
    }

    public void setDmalmProgettoEsePk(Integer dmalmProgettoEsePk) {
        this.dmalmProgettoEsePk = dmalmProgettoEsePk;
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

    public String getDsAutoreProgettoEse() {
        return dsAutoreProgettoEse;
    }

    public void setDsAutoreProgettoEse(String dsAutoreProgettoEse) {
        this.dsAutoreProgettoEse = dsAutoreProgettoEse;
    }

    public java.sql.Timestamp getDtCambioStatoProgettoEse() {
        return dtCambioStatoProgettoEse;
    }

    public void setDtCambioStatoProgettoEse(java.sql.Timestamp dtCambioStatoProgettoEse) {
        this.dtCambioStatoProgettoEse = dtCambioStatoProgettoEse;
    }

    public java.sql.Timestamp getDtCaricamentoProgettoEse() {
        return dtCaricamentoProgettoEse;
    }

    public void setDtCaricamentoProgettoEse(java.sql.Timestamp dtCaricamentoProgettoEse) {
        this.dtCaricamentoProgettoEse = dtCaricamentoProgettoEse;
    }

    public java.sql.Timestamp getDtCreazioneProgettoEse() {
        return dtCreazioneProgettoEse;
    }

    public void setDtCreazioneProgettoEse(java.sql.Timestamp dtCreazioneProgettoEse) {
        this.dtCreazioneProgettoEse = dtCreazioneProgettoEse;
    }

    public java.sql.Timestamp getDtModificaProgettoEse() {
        return dtModificaProgettoEse;
    }

    public void setDtModificaProgettoEse(java.sql.Timestamp dtModificaProgettoEse) {
        this.dtModificaProgettoEse = dtModificaProgettoEse;
    }

    public java.sql.Timestamp getDtRisoluzioneProgettoEse() {
        return dtRisoluzioneProgettoEse;
    }

    public void setDtRisoluzioneProgettoEse(java.sql.Timestamp dtRisoluzioneProgettoEse) {
        this.dtRisoluzioneProgettoEse = dtRisoluzioneProgettoEse;
    }

    public java.sql.Timestamp getDtScadenzaProgettoEse() {
        return dtScadenzaProgettoEse;
    }

    public void setDtScadenzaProgettoEse(java.sql.Timestamp dtScadenzaProgettoEse) {
        this.dtScadenzaProgettoEse = dtScadenzaProgettoEse;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreProgettoEse() {
        return idAutoreProgettoEse;
    }

    public void setIdAutoreProgettoEse(String idAutoreProgettoEse) {
        this.idAutoreProgettoEse = idAutoreProgettoEse;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }


    public String getMotivoRisoluzioneProgEse() {
        return motivoRisoluzioneProgEse;
    }

    public void setMotivoRisoluzioneProgEse(String motivoRisoluzioneProgEse) {
        this.motivoRisoluzioneProgEse = motivoRisoluzioneProgEse;
    }

    public Double getRankStatoProgettoEse() {
        return rankStatoProgettoEse;
    }

    public void setRankStatoProgettoEse(Double rankStatoProgettoEse) {
        this.rankStatoProgettoEse = rankStatoProgettoEse;
    }

    public Short getRankStatoProgettoEseMese() {
        return rankStatoProgettoEseMese;
    }

    public void setRankStatoProgettoEseMese(Short rankStatoProgettoEseMese) {
        this.rankStatoProgettoEseMese = rankStatoProgettoEseMese;
    }

    public String getTitoloProgettoEse() {
        return titoloProgettoEse;
    }

    public void setTitoloProgettoEse(String titoloProgettoEse) {
        this.titoloProgettoEse = titoloProgettoEse;
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

}

