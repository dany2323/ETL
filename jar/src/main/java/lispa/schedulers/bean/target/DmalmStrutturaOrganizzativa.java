package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmStrutturaOrganizzativa is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmStrutturaOrganizzativa {

    private String cdArea;

    private String cdEnte;

    private String cdResponsabileArea;

    private String cdUoSuperiore;

    private String cdVisibilita;

    private Integer dmalmStrutturaOrgPk;

    private String dnResponsabileArea;

    private String dsAreaEdma;
   
    private String annullato;

    private String dsUoSuperiore;

    private java.sql.Timestamp dtAttivazione;

    private java.sql.Timestamp dtCaricamento;

    private java.sql.Timestamp dtDisattivazione;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtFineValiditaEdma;

    private java.sql.Timestamp dtInizioValiditaEdma;

    private Integer idEdma;

    private Integer idGradoUfficio;  //
    
    private Integer idSede;

    private Integer idTipologiaUfficio; //

    private String note;
    
    private String email;
    
    private Integer interno;
    
    
    public java.sql.Timestamp getDtFineValiditaEdma() {
		return dtFineValiditaEdma;
	}

	public void setDtFineValiditaEdma(java.sql.Timestamp dtFineValiditaEdma) {
		this.dtFineValiditaEdma = dtFineValiditaEdma;
	}

	public java.sql.Timestamp getDtInizioValiditaEdma() {
		return dtInizioValiditaEdma;
	}

	public void setDtInizioValiditaEdma(java.sql.Timestamp dtInizioValiditaEdma) {
		this.dtInizioValiditaEdma = dtInizioValiditaEdma;
	}

	public Integer getInterno() {
		return interno;
	}

	public void setInterno(Integer interno) {
		this.interno = interno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCdArea() {
        return cdArea;
    }

    public void setCdArea(String cdArea) {
        this.cdArea = cdArea;
    }

    public String getCdEnte() {
        return cdEnte;
    }

    public void setCdEnte(String cdEnte) {
        this.cdEnte = cdEnte;
    }

    public String getCdResponsabileArea() {
        return cdResponsabileArea;
    }

    public void setCdResponsabileArea(String cdResponsabileArea) {
        this.cdResponsabileArea = cdResponsabileArea;
    }

    public String getCdUoSuperiore() {
        return cdUoSuperiore;
    }

    public void setCdUoSuperiore(String cdUoSuperiore) {
        this.cdUoSuperiore = cdUoSuperiore;
    }

    public String getCdVisibilita() {
        return cdVisibilita;
    }

    public void setCdVisibilita(String cdVisibilita) {
        this.cdVisibilita = cdVisibilita;
    }

    public Integer getDmalmStrutturaOrgPk() {
        return dmalmStrutturaOrgPk;
    }

    public void setDmalmStrutturaOrgPk(Integer dmalmStrutturaOrgPk) {
        this.dmalmStrutturaOrgPk = dmalmStrutturaOrgPk;
    }

    public String getDnResponsabileArea() {
        return dnResponsabileArea;
    }

    public void setDnResponsabileArea(String dnResponsabileArea) {
        this.dnResponsabileArea = dnResponsabileArea;
    }

    public String getDsAreaEdma() {
        return dsAreaEdma;
    }

    public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public void setDsAreaEdma(String dsAreaEdma) {
        this.dsAreaEdma = dsAreaEdma;
    }

    public String getDsUoSuperiore() {
        return dsUoSuperiore;
    }

    public void setDsUoSuperiore(String dsUoSuperiore) {
        this.dsUoSuperiore = dsUoSuperiore;
    }

    public java.sql.Timestamp getDtAttivazione() {
        return dtAttivazione;
    }

    public void setDtAttivazione(java.sql.Timestamp dtAttivazione) {
        this.dtAttivazione = dtAttivazione;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
    }

    public java.sql.Timestamp getDtDisattivazione() {
        return dtDisattivazione;
    }

    public void setDtDisattivazione(java.sql.Timestamp dtDisattivazione) {
        this.dtDisattivazione = dtDisattivazione;
    }

    public java.sql.Timestamp getDtFineValidita() {
        return dtFineValidita;
    }

    public void setDtFineValidita(java.sql.Timestamp dtFineValidita) {
        this.dtFineValidita = dtFineValidita;
    }

    public java.sql.Timestamp getDtInizioValidita() {
        return dtInizioValidita;
    }

    public void setDtInizioValidita(java.sql.Timestamp dtInizioValidita) {
        this.dtInizioValidita = dtInizioValidita;
    }

    public Integer getIdEdma() {
        return idEdma;
    }

    public void setIdEdma(Integer idEdma) {
        this.idEdma = idEdma;
    }

    public Integer getIdGradoUfficio() {
        return idGradoUfficio;
    }

    public void setIdGradoUfficio(Integer i) {
        this.idGradoUfficio = i;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Integer getIdTipologiaUfficio() {
        return idTipologiaUfficio;
    }

    public void setIdTipologiaUfficio(Integer idTipologiaUfficio) {
        this.idTipologiaUfficio = idTipologiaUfficio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

