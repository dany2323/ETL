package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmTestcase is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmTestcase {
	
    private String cdTestcase;

    private String codice;

    private java.sql.Timestamp dataEsecuzioneTestcase;

    private String descrizioneTestcase;

    private Integer dmalmAreaTematicaFk05;

    private Long dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private Long dmalmTestcasePk;

    private String dsAutoreTestcase;

    private java.sql.Timestamp dtCambioStatoTestcase;

    private java.sql.Timestamp dtCaricamentoTestcase;

    private java.sql.Timestamp dtCreazioneTestcase;

    private java.sql.Timestamp dtModificaTestcase;

    private java.sql.Timestamp dtRisoluzioneTestcase;

    private java.sql.Timestamp dtScadenzaTestcase;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreTestcase;

    private String idRepository;

    private String motivoRisoluzioneTestcase;

    private String numeroLinea;

    private String numeroTestata;

    private Double rankStatoTestcase;

    private Short rankStatoTestcaseMese;
    
    private String stgPk;

    private String titoloTestcase;
    
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

    public String getCdTestcase() {
        return cdTestcase;
    }

    public void setCdTestcase(String cdTestcase) {
        this.cdTestcase = cdTestcase;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataEsecuzioneTestcase() {
        return dataEsecuzioneTestcase;
    }

    public void setDataEsecuzioneTestcase(java.sql.Timestamp dataEsecuzioneTestcase) {
        this.dataEsecuzioneTestcase = dataEsecuzioneTestcase;
    }

    public String getDescrizioneTestcase() {
        return descrizioneTestcase;
    }

    public void setDescrizioneTestcase(String descrizioneTestcase) {
        this.descrizioneTestcase = descrizioneTestcase;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
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

    public Long getDmalmTestcasePk() {
        return dmalmTestcasePk;
    }

    public void setDmalmTestcasePk(Long dmalmTestcasePk) {
        this.dmalmTestcasePk = dmalmTestcasePk;
    }

    public String getDsAutoreTestcase() {
        return dsAutoreTestcase;
    }

    public void setDsAutoreTestcase(String dsAutoreTestcase) {
        this.dsAutoreTestcase = dsAutoreTestcase;
    }

    public java.sql.Timestamp getDtCambioStatoTestcase() {
        return dtCambioStatoTestcase;
    }

    public void setDtCambioStatoTestcase(java.sql.Timestamp dtCambioStatoTestcase) {
        this.dtCambioStatoTestcase = dtCambioStatoTestcase;
    }

    public java.sql.Timestamp getDtCaricamentoTestcase() {
        return dtCaricamentoTestcase;
    }

    public void setDtCaricamentoTestcase(java.sql.Timestamp dtCaricamentoTestcase) {
        this.dtCaricamentoTestcase = dtCaricamentoTestcase;
    }

    public java.sql.Timestamp getDtCreazioneTestcase() {
        return dtCreazioneTestcase;
    }

    public void setDtCreazioneTestcase(java.sql.Timestamp dtCreazioneTestcase) {
        this.dtCreazioneTestcase = dtCreazioneTestcase;
    }

    public java.sql.Timestamp getDtModificaTestcase() {
        return dtModificaTestcase;
    }

    public void setDtModificaTestcase(java.sql.Timestamp dtModificaTestcase) {
        this.dtModificaTestcase = dtModificaTestcase;
    }

    public java.sql.Timestamp getDtRisoluzioneTestcase() {
        return dtRisoluzioneTestcase;
    }

    public void setDtRisoluzioneTestcase(java.sql.Timestamp dtRisoluzioneTestcase) {
        this.dtRisoluzioneTestcase = dtRisoluzioneTestcase;
    }

    public java.sql.Timestamp getDtScadenzaTestcase() {
        return dtScadenzaTestcase;
    }

    public void setDtScadenzaTestcase(java.sql.Timestamp dtScadenzaTestcase) {
        this.dtScadenzaTestcase = dtScadenzaTestcase;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreTestcase() {
        return idAutoreTestcase;
    }

    public void setIdAutoreTestcase(String idAutoreTestcase) {
        this.idAutoreTestcase = idAutoreTestcase;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneTestcase() {
        return motivoRisoluzioneTestcase;
    }

    public void setMotivoRisoluzioneTestcase(String motivoRisoluzioneTestcase) {
        this.motivoRisoluzioneTestcase = motivoRisoluzioneTestcase;
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

    public Double getRankStatoTestcase() {
        return rankStatoTestcase;
    }

    public void setRankStatoTestcase(Double rankStatoTestcase) {
        this.rankStatoTestcase = rankStatoTestcase;
    }

    public Short getRankStatoTestcaseMese() {
        return rankStatoTestcaseMese;
    }

    public void setRankStatoTestcaseMese(Short rankStatoTestcaseMese) {
        this.rankStatoTestcaseMese = rankStatoTestcaseMese;
    }

    public String getTitoloTestcase() {
        return titoloTestcase;
    }

    public void setTitoloTestcase(String titoloTestcase) {
        this.titoloTestcase = titoloTestcase;
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

