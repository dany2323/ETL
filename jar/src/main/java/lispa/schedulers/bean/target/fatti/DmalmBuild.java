package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmBuild is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmBuild {

    private String cdBuild;

    private String codice;

    private String descrizioneBuild;

    private Integer dmalmBuildPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreBuild;

    private java.sql.Timestamp dtCambioStatoBuild;

    private java.sql.Timestamp dtCaricamentoBuild;

    private java.sql.Timestamp dtCreazioneBuild;

    private java.sql.Timestamp dtModificaBuild;

    private java.sql.Timestamp dtRisoluzioneBuild;

    private java.sql.Timestamp dtScadenzaBuild;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreBuild;

    private String idRepository;

    private String motivoRisoluzioneBuild;

    private Double rankStatoBuild;

    private Short rankStatoBuildMese;

    private String stgPk;

    private String titoloBuild;

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
	
    public String getCdBuild() {
        return cdBuild;
    }

    public void setCdBuild(String cdBuild) {
        this.cdBuild = cdBuild;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizioneBuild() {
        return descrizioneBuild;
    }

    public void setDescrizioneBuild(String descrizioneBuild) {
        this.descrizioneBuild = descrizioneBuild;
    }

    public Integer getDmalmBuildPk() {
        return dmalmBuildPk;
    }

    public void setDmalmBuildPk(Integer dmalmBuildPk) {
        this.dmalmBuildPk = dmalmBuildPk;
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

    public String getDsAutoreBuild() {
        return dsAutoreBuild;
    }

    public void setDsAutoreBuild(String dsAutoreBuild) {
        this.dsAutoreBuild = dsAutoreBuild;
    }

    public java.sql.Timestamp getDtCambioStatoBuild() {
        return dtCambioStatoBuild;
    }

    public void setDtCambioStatoBuild(java.sql.Timestamp dtCambioStatoBuild) {
        this.dtCambioStatoBuild = dtCambioStatoBuild;
    }

    public java.sql.Timestamp getDtCaricamentoBuild() {
        return dtCaricamentoBuild;
    }

    public void setDtCaricamentoBuild(java.sql.Timestamp dtCaricamentoBuild) {
        this.dtCaricamentoBuild = dtCaricamentoBuild;
    }

    public java.sql.Timestamp getDtCreazioneBuild() {
        return dtCreazioneBuild;
    }

    public void setDtCreazioneBuild(java.sql.Timestamp dtCreazioneBuild) {
        this.dtCreazioneBuild = dtCreazioneBuild;
    }

    public java.sql.Timestamp getDtModificaBuild() {
        return dtModificaBuild;
    }

    public void setDtModificaBuild(java.sql.Timestamp dtModificaBuild) {
        this.dtModificaBuild = dtModificaBuild;
    }

    public java.sql.Timestamp getDtRisoluzioneBuild() {
        return dtRisoluzioneBuild;
    }

    public void setDtRisoluzioneBuild(java.sql.Timestamp dtRisoluzioneBuild) {
        this.dtRisoluzioneBuild = dtRisoluzioneBuild;
    }

    public java.sql.Timestamp getDtScadenzaBuild() {
        return dtScadenzaBuild;
    }

    public void setDtScadenzaBuild(java.sql.Timestamp dtScadenzaBuild) {
        this.dtScadenzaBuild = dtScadenzaBuild;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreBuild() {
        return idAutoreBuild;
    }

    public void setIdAutoreBuild(String idAutoreBuild) {
        this.idAutoreBuild = idAutoreBuild;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }



    public String getMotivoRisoluzioneBuild() {
        return motivoRisoluzioneBuild;
    }

    public void setMotivoRisoluzioneBuild(String motivoRisoluzioneBuild) {
        this.motivoRisoluzioneBuild = motivoRisoluzioneBuild;
    }

    public Double getRankStatoBuild() {
        return rankStatoBuild;
    }

    public void setRankStatoBuild(Double rankStatoBuild) {
        this.rankStatoBuild = rankStatoBuild;
    }

    public Short getRankStatoBuildMese() {
        return rankStatoBuildMese;
    }

    public void setRankStatoBuildMese(Short rankStatoBuildMese) {
        this.rankStatoBuildMese = rankStatoBuildMese;
    }

    public String getStgPk() {
        return stgPk;
    }

    public void setStgPk(String stgPk) {
        this.stgPk = stgPk;
    }

    public String getTitoloBuild() {
        return titoloBuild;
    }

    public void setTitoloBuild(String titoloBuild) {
        this.titoloBuild = titoloBuild;
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

