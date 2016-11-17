package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmFunzionalita is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmFunzionalita {

    private String annullato;

    private String categoria;

    private Integer dmalmFunzionalitaPk;

    private String dsFunzionalita;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtInserimentoRecord;

    private Integer dmalmModuloFk01;

    private String idFunzionalita;

    private String linguaggi;

    private String nome;

    private String siglaFunzionalita;

    private String siglaModulo;

    private String siglaProdotto;

    private String siglaSottosistema;

    private String tipiElaborazione;

    private String tipoOggetto;

    private java.sql.Timestamp dtAnnullamento;
    
    private Integer dmalmFunzionalitaSeq; 
    
    public Integer getDmalmFunzionalitaSeq() {
		return dmalmFunzionalitaSeq;
	}

	public void setDmalmFunzionalitaSeq(Integer dmalmFunzionalitaSeq) {
		this.dmalmFunzionalitaSeq = dmalmFunzionalitaSeq;
	}

	public java.sql.Timestamp getDtAnnullamento ()
	{
		return dtAnnullamento;
	}

	public void setDtAnnullamento (java.sql.Timestamp dtAnnullamento)
	{
		this.dtAnnullamento = dtAnnullamento;
	}

	public java.sql.Timestamp getDtInserimentoRecord() {
		return dtInserimentoRecord;
	}

	public void setDtInserimentoRecord(java.sql.Timestamp dtInserimentoRecord) {
		this.dtInserimentoRecord = dtInserimentoRecord;
	}

	public String getAnnullato() {
        return annullato;
    }

    public void setAnnullato(String annullato) {
        this.annullato = annullato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getDmalmFunzionalitaPk() {
        return dmalmFunzionalitaPk;
    }

    public void setDmalmFunzionalitaPk(Integer dmalmFunzionalitaPk) {
        this.dmalmFunzionalitaPk = dmalmFunzionalitaPk;
    }

    public String getDsFunzionalita() {
        return dsFunzionalita;
    }

    public void setDsFunzionalita(String dsFunzionalita) {
        this.dsFunzionalita = dsFunzionalita;
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

    public Integer getDmalmModuloFk01() {
		return dmalmModuloFk01;
	}

	public void setDmalmModuloFk01(Integer dmalmModuloFk01) {
		this.dmalmModuloFk01 = dmalmModuloFk01;
	}

	public String getIdFunzionalita() {
        return idFunzionalita;
    }

    public void setIdFunzionalita(String idFunzionalita) {
        this.idFunzionalita = idFunzionalita;
    }

    public String getLinguaggi() {
        return linguaggi;
    }

    public void setLinguaggi(String linguaggi) {
        this.linguaggi = linguaggi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSiglaFunzionalita() {
        return siglaFunzionalita;
    }

    public void setSiglaFunzionalita(String siglaFunzionalita) {
        this.siglaFunzionalita = siglaFunzionalita;
    }

    public String getSiglaModulo() {
        return siglaModulo;
    }

    public void setSiglaModulo(String siglaModulo) {
        this.siglaModulo = siglaModulo;
    }

    public String getSiglaProdotto() {
        return siglaProdotto;
    }

    public void setSiglaProdotto(String siglaProdotto) {
        this.siglaProdotto = siglaProdotto;
    }

    public String getSiglaSottosistema() {
        return siglaSottosistema;
    }

    public void setSiglaSottosistema(String siglaSottosistema) {
        this.siglaSottosistema = siglaSottosistema;
    }

    public String getTipiElaborazione() {
        return tipiElaborazione;
    }

    public void setTipiElaborazione(String tipiElaborazione) {
        this.tipiElaborazione = tipiElaborazione;
    }

    public String getTipoOggetto() {
        return tipoOggetto;
    }

    public void setTipoOggetto(String tipoOggetto) {
        this.tipoOggetto = tipoOggetto;
    }

}

