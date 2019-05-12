package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmReleaseServizi is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmReleaseServizi {

    private String cdRelServizi;

    private String descrizioneRelServizi;

    private Integer dmalmProjectFk02;

    private Integer dmalmRelServiziPk;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreRelServizi;

    private java.sql.Timestamp dtCambioStatoRelServizi;

    private java.sql.Timestamp dtCaricamentoRelServizi;

    private java.sql.Timestamp dtCreazioneRelServizi;

    private java.sql.Timestamp dtModificaRelServizi;

    private java.sql.Timestamp dtRisoluzioneRelServizi;

    private java.sql.Timestamp dtScadenzaRelServizi;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreRelServizi;

    private String idRepository;

    private String motivoRisoluzioneRelServizi;

    private String motivoSospensioneReleaseSer;

    private String previstoFermoServizioRel;

    private Double rankStatoRelServizi;

    private Short rankStatoRelServiziMese;

    private String richiestaAnalisiImpattiRel;

    private String stgPk;

    private String titoloRelServizi;

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
	
    public String getCdRelServizi() {
        return cdRelServizi;
    }

    public void setCdRelServizi(String cdRelServizi) {
        this.cdRelServizi = cdRelServizi;
    }

    public String getDescrizioneRelServizi() {
        return descrizioneRelServizi;
    }

    public void setDescrizioneRelServizi(String descrizioneRelServizi) {
        this.descrizioneRelServizi = descrizioneRelServizi;
    }

    public Integer getDmalmProjectFk02() {
        return dmalmProjectFk02;
    }

    public void setDmalmProjectFk02(Integer dmalmProjectFk02) {
        this.dmalmProjectFk02 = dmalmProjectFk02;
    }

    public Integer getDmalmRelServiziPk() {
        return dmalmRelServiziPk;
    }

    public void setDmalmRelServiziPk(Integer dmalmRelServiziPk) {
        this.dmalmRelServiziPk = dmalmRelServiziPk;
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

    public String getDsAutoreRelServizi() {
        return dsAutoreRelServizi;
    }

    public void setDsAutoreRelServizi(String dsAutoreRelServizi) {
        this.dsAutoreRelServizi = dsAutoreRelServizi;
    }

    public java.sql.Timestamp getDtCambioStatoRelServizi() {
        return dtCambioStatoRelServizi;
    }

    public void setDtCambioStatoRelServizi(java.sql.Timestamp dtCambioStatoRelServizi) {
        this.dtCambioStatoRelServizi = dtCambioStatoRelServizi;
    }

    public java.sql.Timestamp getDtCaricamentoRelServizi() {
        return dtCaricamentoRelServizi;
    }

    public void setDtCaricamentoRelServizi(java.sql.Timestamp dtCaricamentoRelServizi) {
        this.dtCaricamentoRelServizi = dtCaricamentoRelServizi;
    }

    public java.sql.Timestamp getDtCreazioneRelServizi() {
        return dtCreazioneRelServizi;
    }

    public void setDtCreazioneRelServizi(java.sql.Timestamp dtCreazioneRelServizi) {
        this.dtCreazioneRelServizi = dtCreazioneRelServizi;
    }

    public java.sql.Timestamp getDtModificaRelServizi() {
        return dtModificaRelServizi;
    }

    public void setDtModificaRelServizi(java.sql.Timestamp dtModificaRelServizi) {
        this.dtModificaRelServizi = dtModificaRelServizi;
    }

    public java.sql.Timestamp getDtRisoluzioneRelServizi() {
        return dtRisoluzioneRelServizi;
    }

    public void setDtRisoluzioneRelServizi(java.sql.Timestamp dtRisoluzioneRelServizi) {
        this.dtRisoluzioneRelServizi = dtRisoluzioneRelServizi;
    }

    public java.sql.Timestamp getDtScadenzaRelServizi() {
        return dtScadenzaRelServizi;
    }

    public void setDtScadenzaRelServizi(java.sql.Timestamp dtScadenzaRelServizi) {
        this.dtScadenzaRelServizi = dtScadenzaRelServizi;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreRelServizi() {
        return idAutoreRelServizi;
    }

    public void setIdAutoreRelServizi(String idAutoreRelServizi) {
        this.idAutoreRelServizi = idAutoreRelServizi;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneRelServizi() {
        return motivoRisoluzioneRelServizi;
    }

    public void setMotivoRisoluzioneRelServizi(String motivoRisoluzioneRelServizi) {
        this.motivoRisoluzioneRelServizi = motivoRisoluzioneRelServizi;
    }

    public String getMotivoSospensioneReleaseSer() {
        return motivoSospensioneReleaseSer;
    }

    public void setMotivoSospensioneReleaseSer(String motivoSospensioneReleaseSer) {
        this.motivoSospensioneReleaseSer = motivoSospensioneReleaseSer;
    }

    public String getPrevistoFermoServizioRel() {
        return previstoFermoServizioRel;
    }

    public void setPrevistoFermoServizioRel(String previstoFermoServizioRel) {
        this.previstoFermoServizioRel = previstoFermoServizioRel;
    }

    public Double getRankStatoRelServizi() {
        return rankStatoRelServizi;
    }

    public void setRankStatoRelServizi(Double rankStatoRelServizi) {
        this.rankStatoRelServizi = rankStatoRelServizi;
    }

    public Short getRankStatoRelServiziMese() {
        return rankStatoRelServiziMese;
    }

    public void setRankStatoRelServiziMese(Short rankStatoRelServiziMese) {
        this.rankStatoRelServiziMese = rankStatoRelServiziMese;
    }

    public String getRichiestaAnalisiImpattiRel() {
        return richiestaAnalisiImpattiRel;
    }

    public void setRichiestaAnalisiImpattiRel(String richiestaAnalisiImpattiRel) {
        this.richiestaAnalisiImpattiRel = richiestaAnalisiImpattiRel;
    }

    public String getStgPk() {
        return stgPk;
    }

    public void setStgPk(String stgPk) {
        this.stgPk = stgPk;
    }

    public String getTitoloRelServizi() {
        return titoloRelServizi;
    }

    public void setTitoloRelServizi(String titoloRelServizi) {
        this.titoloRelServizi = titoloRelServizi;
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

