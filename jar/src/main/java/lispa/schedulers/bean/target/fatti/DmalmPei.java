package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmPei is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmPei {

	private String stgPk;

    private String cdPei;

    private String codice;

    private String descrizionePei;

    private Integer dmalmPeiPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutorePei;

    private java.sql.Timestamp dtCambioStatoPei;

    private java.sql.Timestamp dtCaricamentoPei;

    private java.sql.Timestamp dtCreazionePei;

    private java.sql.Timestamp dtModificaPei;

    private java.sql.Timestamp dtPrevistaComplReq;

    private java.sql.Timestamp dtPrevistaPassInEs;

    private java.sql.Timestamp dtRisoluzionePei;

    private java.sql.Timestamp dtScadenzaPei;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutorePei;

    private String idRepository;

    private String motivoRisoluzionePei;

    private Double rankStatoPei;

    private Short rankStatoPeiMese;

    private String titoloPei;

    private String uri;
    
    private String changed;
    
    private String annullato;
    
	private java.sql.Timestamp dtAnnullamento;
    
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
	
    public String getCdPei() {
        return cdPei;
    }

    public void setCdPei(String cdPei) {
        this.cdPei = cdPei;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizionePei() {
        return descrizionePei;
    }

    public void setDescrizionePei(String descrizionePei) {
        this.descrizionePei = descrizionePei;
    }

    public Integer getDmalmPeiPk() {
        return dmalmPeiPk;
    }

    public void setDmalmPeiPk(Integer dmalmPeiPk) {
        this.dmalmPeiPk = dmalmPeiPk;
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

    public String getDsAutorePei() {
        return dsAutorePei;
    }

    public void setDsAutorePei(String dsAutorePei) {
        this.dsAutorePei = dsAutorePei;
    }

    public java.sql.Timestamp getDtCambioStatoPei() {
        return dtCambioStatoPei;
    }

    public void setDtCambioStatoPei(java.sql.Timestamp dtCambioStatoPei) {
        this.dtCambioStatoPei = dtCambioStatoPei;
    }

    public java.sql.Timestamp getDtCaricamentoPei() {
        return dtCaricamentoPei;
    }

    public void setDtCaricamentoPei(java.sql.Timestamp dtCaricamentoPei) {
        this.dtCaricamentoPei = dtCaricamentoPei;
    }

    public java.sql.Timestamp getDtCreazionePei() {
        return dtCreazionePei;
    }

    public void setDtCreazionePei(java.sql.Timestamp dtCreazionePei) {
        this.dtCreazionePei = dtCreazionePei;
    }

    public java.sql.Timestamp getDtModificaPei() {
        return dtModificaPei;
    }

    public void setDtModificaPei(java.sql.Timestamp dtModificaPei) {
        this.dtModificaPei = dtModificaPei;
    }

    public java.sql.Timestamp getDtPrevistaComplReq() {
        return dtPrevistaComplReq;
    }

    public void setDtPrevistaComplReq(java.sql.Timestamp dtPrevistaComplReq) {
        this.dtPrevistaComplReq = dtPrevistaComplReq;
    }

    public java.sql.Timestamp getDtPrevistaPassInEs() {
        return dtPrevistaPassInEs;
    }

    public void setDtPrevistaPassInEs(java.sql.Timestamp dtPrevistaPassInEs) {
        this.dtPrevistaPassInEs = dtPrevistaPassInEs;
    }

    public java.sql.Timestamp getDtRisoluzionePei() {
        return dtRisoluzionePei;
    }

    public void setDtRisoluzionePei(java.sql.Timestamp dtRisoluzionePei) {
        this.dtRisoluzionePei = dtRisoluzionePei;
    }

    public java.sql.Timestamp getDtScadenzaPei() {
        return dtScadenzaPei;
    }

    public void setDtScadenzaPei(java.sql.Timestamp dtScadenzaPei) {
        this.dtScadenzaPei = dtScadenzaPei;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutorePei() {
        return idAutorePei;
    }

    public void setIdAutorePei(String idAutorePei) {
        this.idAutorePei = idAutorePei;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzionePei() {
        return motivoRisoluzionePei;
    }

    public void setMotivoRisoluzionePei(String motivoRisoluzionePei) {
        this.motivoRisoluzionePei = motivoRisoluzionePei;
    }

    public Double getRankStatoPei() {
        return rankStatoPei;
    }

    public void setRankStatoPei(Double rankStatoPei) {
        this.rankStatoPei = rankStatoPei;
    }

    public Short getRankStatoPeiMese() {
        return rankStatoPeiMese;
    }

    public void setRankStatoPeiMese(Short rankStatoPeiMese) {
        this.rankStatoPeiMese = rankStatoPeiMese;
    }

    public String getTitoloPei() {
        return titoloPei;
    }

    public void setTitoloPei(String titoloPei) {
        this.titoloPei = titoloPei;
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

