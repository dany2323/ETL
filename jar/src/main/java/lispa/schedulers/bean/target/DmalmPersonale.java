package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmPersonale is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmPersonale {

    private String cdEnte;

    private String cdPersonale;

    private String cdResponsabile;

    private String cdSuperiore;

    private String cdVisibilita;
    
    private String annullato;

    private String codiceFiscale;

    private String cognome;

    private Integer dmalmPersonalePk;

    private java.sql.Timestamp dtAttivazione;

    private java.sql.Timestamp dtDisattivazione;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtFineValiditaEdma;

    private java.sql.Timestamp dtInizioValiditaEdma;

    private Integer idEdma;

    private Integer idGrado;

    private Integer idSede;

    private String identificatore;

    private String indirizzoEmail;

    private Integer interno;

    private String matricola;

    private String nome;

    private String note;
    
    private java.sql.Timestamp dtCaricamento;
    
    

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

	public java.sql.Timestamp getDtCaricamento() {
		return dtCaricamento;
	}

	public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
		this.dtCaricamento = dtCaricamento;
	}

	public String getCdEnte() {
        return cdEnte;
    }

    public void setCdEnte(String cdEnte) {
        this.cdEnte = cdEnte;
    }

    public String getCdPersonale() {
        return cdPersonale;
    }

    public void setCdPersonale(String cdPersonale) {
        this.cdPersonale = cdPersonale;
    }

    public String getCdResponsabile() {
        return cdResponsabile;
    }

    public void setCdResponsabile(String cdResponsabile) {
        this.cdResponsabile = cdResponsabile;
    }

    public String getCdSuperiore() {
        return cdSuperiore;
    }

    public void setCdSuperiore(String cdSuperiore) {
        this.cdSuperiore = cdSuperiore;
    }

    public String getCdVisibilita() {
        return cdVisibilita;
    }

    public void setCdVisibilita(String cdVisibilita) {
        this.cdVisibilita = cdVisibilita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Integer getDmalmPersonalePk() {
        return dmalmPersonalePk;
    }

    public void setDmalmPersonalePk(Integer dmalmPersonalePk) {
        this.dmalmPersonalePk = dmalmPersonalePk;
    }

    public java.sql.Timestamp getDtAttivazione() {
        return dtAttivazione;
    }

    public void setDtAttivazione(java.sql.Timestamp dtAttivazione) {
        this.dtAttivazione = dtAttivazione;
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

    public Integer getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public String getIdentificatore() {
        return identificatore;
    }

    public void setIdentificatore(String identificatore) {
        this.identificatore = identificatore;
    }

    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public void setIndirizzoEmail(String indirizzoEmail) {
        this.indirizzoEmail = indirizzoEmail;
    }

    public Integer getInterno() {
        return interno;
    }

    public void setInterno(Integer interno) {
        this.interno = interno;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

