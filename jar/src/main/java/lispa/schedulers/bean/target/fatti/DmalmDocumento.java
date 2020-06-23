package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmDocumento is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmDocumento {

	private String stgPk;
	
    private String assigneesDocumento;

    private String cdDocumento;

    private String classificazione;

    private String codice;

    private String descrizioneDocumento;

    private Integer dmalmAreaTematicaFk05;

    private Integer dmalmDocumentoPk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreDocumento;

    private java.sql.Timestamp dtCambioStatoDocumento;

    private java.sql.Timestamp dtCaricamentoDocumento;

    private java.sql.Timestamp dtCreazioneDocumento;

    private java.sql.Timestamp dtModificaDocumento;

    private java.sql.Timestamp dtRisoluzioneDocumento;

    private java.sql.Timestamp dtScadenzaDocumento;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreDocumento;

    private String idRepository;

    private String motivoRisoluzioneDocumento;

    private String numeroLinea;

    private String numeroTestata;

    private Double rankStatoDocumento;

    private Short rankStatoDocumentoMese;

    private String tipo;

    private String titoloDocumento;

    private String versione;

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
	
    public String getAssigneesDocumento() {
        return assigneesDocumento;
    }

    public void setAssigneesDocumento(String assigneesDocumento) {
        this.assigneesDocumento = assigneesDocumento;
    }

    public String getCdDocumento() {
        return cdDocumento;
    }

    public void setCdDocumento(String cdDocumento) {
        this.cdDocumento = cdDocumento;
    }

    public String getClassificazione() {
        return classificazione;
    }

    public void setClassificazione(String classificazione) {
        this.classificazione = classificazione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizioneDocumento() {
        return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {
        this.descrizioneDocumento = descrizioneDocumento;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
    }

    public Integer getDmalmDocumentoPk() {
        return dmalmDocumentoPk;
    }

    public void setDmalmDocumentoPk(Integer dmalmDocumentoPk) {
        this.dmalmDocumentoPk = dmalmDocumentoPk;
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

    public String getDsAutoreDocumento() {
        return dsAutoreDocumento;
    }

    public void setDsAutoreDocumento(String dsAutoreDocumento) {
        this.dsAutoreDocumento = dsAutoreDocumento;
    }

    public java.sql.Timestamp getDtCambioStatoDocumento() {
        return dtCambioStatoDocumento;
    }

    public void setDtCambioStatoDocumento(java.sql.Timestamp dtCambioStatoDocumento) {
        this.dtCambioStatoDocumento = dtCambioStatoDocumento;
    }

    public java.sql.Timestamp getDtCaricamentoDocumento() {
        return dtCaricamentoDocumento;
    }

    public void setDtCaricamentoDocumento(java.sql.Timestamp dtCaricamentoDocumento) {
        this.dtCaricamentoDocumento = dtCaricamentoDocumento;
    }

    public java.sql.Timestamp getDtCreazioneDocumento() {
        return dtCreazioneDocumento;
    }

    public void setDtCreazioneDocumento(java.sql.Timestamp dtCreazioneDocumento) {
        this.dtCreazioneDocumento = dtCreazioneDocumento;
    }

    public java.sql.Timestamp getDtModificaDocumento() {
        return dtModificaDocumento;
    }

    public void setDtModificaDocumento(java.sql.Timestamp dtModificaDocumento) {
        this.dtModificaDocumento = dtModificaDocumento;
    }

    public java.sql.Timestamp getDtRisoluzioneDocumento() {
        return dtRisoluzioneDocumento;
    }

    public void setDtRisoluzioneDocumento(java.sql.Timestamp dtRisoluzioneDocumento) {
        this.dtRisoluzioneDocumento = dtRisoluzioneDocumento;
    }

    public java.sql.Timestamp getDtScadenzaDocumento() {
        return dtScadenzaDocumento;
    }

    public void setDtScadenzaDocumento(java.sql.Timestamp dtScadenzaDocumento) {
        this.dtScadenzaDocumento = dtScadenzaDocumento;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreDocumento() {
        return idAutoreDocumento;
    }

    public void setIdAutoreDocumento(String idAutoreDocumento) {
        this.idAutoreDocumento = idAutoreDocumento;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneDocumento() {
        return motivoRisoluzioneDocumento;
    }

    public void setMotivoRisoluzioneDocumento(String motivoRisoluzioneDocumento) {
        this.motivoRisoluzioneDocumento = motivoRisoluzioneDocumento;
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

    public Double getRankStatoDocumento() {
        return rankStatoDocumento;
    }

    public void setRankStatoDocumento(Double rankStatoDocumento) {
        this.rankStatoDocumento = rankStatoDocumento;
    }

    public Short getRankStatoDocumentoMese() {
        return rankStatoDocumentoMese;
    }

    public void setRankStatoDocumentoMese(Short rankStatoDocumentoMese) {
        this.rankStatoDocumentoMese = rankStatoDocumentoMese;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitoloDocumento() {
        return titoloDocumento;
    }

    public void setTitoloDocumento(String titoloDocumento) {
        this.titoloDocumento = titoloDocumento;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
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

}

