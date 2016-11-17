package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmProgramma is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProgramma {

    private String assignee;

    private String cdProgramma;

    private String descrizioneProgramma;

    private Integer dmalmProgrammaPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreProgramma;

    private java.sql.Timestamp dtCambioStatoProgramma;

    private java.sql.Timestamp dtCaricamentoProgramma;

    private java.sql.Timestamp dtCreazioneProgramma;

    private java.sql.Timestamp dtModificaProgramma;

    private java.sql.Timestamp dtRisoluzioneProgramma;

    private java.sql.Timestamp dtScadenzaProgramma;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreProgramma;

    private String idRepository;

    private String motivoRisoluzioneProgramma;

    private String numeroLinea;

    private String numeroTestata;

    private Double rankStatoProgramma;

    private Short rankStatoProgrammaMese;

    private String titoloProgramma;
    
    private String codice;
    
    private String stgPk;
    
    private String cfTipologia;
    
    private String cfServiceManager;
    
    private String cfReferenteRegionale;
    
    private String cfContratto;

    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
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
	
    /**
	 * @return the codice
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @param codice the codice to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCfTipologia() {
		return cfTipologia;
	}

	public void setCfTipologia(String cfTipologia) {
		this.cfTipologia = cfTipologia;
	}

	public String getCfServiceManager() {
		return cfServiceManager;
	}

	public void setCfServiceManager(String cfServiceManager) {
		this.cfServiceManager = cfServiceManager;
	}

	public String getCfReferenteRegionale() {
		return cfReferenteRegionale;
	}

	public void setCfReferenteRegionale(String cfReferenteRegionale) {
		this.cfReferenteRegionale = cfReferenteRegionale;
	}

	public String getCfContratto() {
		return cfContratto;
	}

	public void setCfContratto(String cfContratto) {
		this.cfContratto = cfContratto;
	}

	
	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

	public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCdProgramma() {
        return cdProgramma;
    }

    public void setCdProgramma(String cdProgramma) {
        this.cdProgramma = cdProgramma;
    }

    public String getDescrizioneProgramma() {
        return descrizioneProgramma;
    }

    public void setDescrizioneProgramma(String descrizioneProgramma) {
        this.descrizioneProgramma = descrizioneProgramma;
    }
 

    public Integer getDmalmProgrammaPk() {
        return dmalmProgrammaPk;
    }

    public void setDmalmProgrammaPk(Integer dmalmProgrammaPk) {
        this.dmalmProgrammaPk = dmalmProgrammaPk;
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

    public String getDsAutoreProgramma() {
        return dsAutoreProgramma;
    }

    public void setDsAutoreProgramma(String dsAutoreProgramma) {
        this.dsAutoreProgramma = dsAutoreProgramma;
    }

    public java.sql.Timestamp getDtCambioStatoProgramma() {
        return dtCambioStatoProgramma;
    }

    public void setDtCambioStatoProgramma(java.sql.Timestamp dtCambioStatoProgramma) {
        this.dtCambioStatoProgramma = dtCambioStatoProgramma;
    }

    public java.sql.Timestamp getDtCaricamentoProgramma() {
        return dtCaricamentoProgramma;
    }

    public void setDtCaricamentoProgramma(java.sql.Timestamp dtCaricamentoProgramma) {
        this.dtCaricamentoProgramma = dtCaricamentoProgramma;
    }

    public java.sql.Timestamp getDtCreazioneProgramma() {
        return dtCreazioneProgramma;
    }

    public void setDtCreazioneProgramma(java.sql.Timestamp dtCreazioneProgramma) {
        this.dtCreazioneProgramma = dtCreazioneProgramma;
    }

    public java.sql.Timestamp getDtModificaProgramma() {
        return dtModificaProgramma;
    }

    public void setDtModificaProgramma(java.sql.Timestamp dtModificaProgramma) {
        this.dtModificaProgramma = dtModificaProgramma;
    }

    public java.sql.Timestamp getDtRisoluzioneProgramma() {
        return dtRisoluzioneProgramma;
    }

    public void setDtRisoluzioneProgramma(java.sql.Timestamp dtRisoluzioneProgramma) {
        this.dtRisoluzioneProgramma = dtRisoluzioneProgramma;
    }

    public java.sql.Timestamp getDtScadenzaProgramma() {
        return dtScadenzaProgramma;
    }

    public void setDtScadenzaProgramma(java.sql.Timestamp dtScadenzaProgramma) {
        this.dtScadenzaProgramma = dtScadenzaProgramma;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreProgramma() {
        return idAutoreProgramma;
    }

    public void setIdAutoreProgramma(String idAutoreProgramma) {
        this.idAutoreProgramma = idAutoreProgramma;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneProgramma() {
        return motivoRisoluzioneProgramma;
    }

    public void setMotivoRisoluzioneProgramma(String motivoRisoluzioneProgramma) {
        this.motivoRisoluzioneProgramma = motivoRisoluzioneProgramma;
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

    public Double getRankStatoProgramma() {
        return rankStatoProgramma;
    }

    public void setRankStatoProgramma(Double rankStatoProgramma) {
        this.rankStatoProgramma = rankStatoProgramma;
    }

    public Short getRankStatoProgrammaMese() {
        return rankStatoProgrammaMese;
    }

    public void setRankStatoProgrammaMese(Short rankStatoProgrammaMese) {
        this.rankStatoProgrammaMese = rankStatoProgrammaMese;
    }

    public String getTitoloProgramma() {
        return titoloProgramma;
    }

    public void setTitoloProgramma(String titoloProgramma) {
        this.titoloProgramma = titoloProgramma;
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

}

