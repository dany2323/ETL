package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmProdotto is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProdotto {

    private String ambitoManutenzione;

    private String annullato;

    private Integer dmalm_unitaorganizzativa_fk01;

    private String areaTematica;

    private String baseDatiEtl;

    private String baseDatiLettura;

    private String baseDatiScrittura;

    private String categoria;

    private Integer dmalmProdottoPk;

    private String dsProdotto;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtInserimentoRecord;

    private String fornituraRisorseEsterne;

    private Integer idEdma;

    private String idProdotto;

    private String nome;

    private Integer dmalm_personale_fk02;

    private String sigla;

    private String tipoOggetto;
    
    private java.sql.Timestamp dtAnnullamento;
    
    private Integer dmalmProdottoSeq;
    
    
    
    public Integer getDmalmProdottoSeq() {
		return dmalmProdottoSeq;
	}

	public void setDmalmProdottoSeq(Integer dmalmProdottoSeq) {
		this.dmalmProdottoSeq = dmalmProdottoSeq;
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

	public Integer getDmalm_unitaorganizzativa_fk01() {
		return dmalm_unitaorganizzativa_fk01;
	}

	public void setDmalm_unitaorganizzativa_fk01(
			Integer dmalm_unitaorganizzativa_fk01) {
		this.dmalm_unitaorganizzativa_fk01 = dmalm_unitaorganizzativa_fk01;
	}

	public Integer getDmalm_personale_fk02() {
		return dmalm_personale_fk02;
	}

	public void setDmalm_personale_fk02(Integer dmalm_personale_fk02) {
		this.dmalm_personale_fk02 = dmalm_personale_fk02;
	}

	public String getAmbitoManutenzione() {
        return ambitoManutenzione;
    }

    public void setAmbitoManutenzione(String ambitoManutenzione) {
        this.ambitoManutenzione = ambitoManutenzione;
    }

    public String getAnnullato() {
        return annullato;
    }

    public void setAnnullato(String annullato) {
        this.annullato = annullato;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }

    public String getBaseDatiEtl() {
        return baseDatiEtl;
    }

    public void setBaseDatiEtl(String baseDatiEtl) {
        this.baseDatiEtl = baseDatiEtl;
    }

    public String getBaseDatiLettura() {
        return baseDatiLettura;
    }

    public void setBaseDatiLettura(String baseDatiLettura) {
        this.baseDatiLettura = baseDatiLettura;
    }

    public String getBaseDatiScrittura() {
        return baseDatiScrittura;
    }

    public void setBaseDatiScrittura(String baseDatiScrittura) {
        this.baseDatiScrittura = baseDatiScrittura;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getDmalmProdottoPk() {
        return dmalmProdottoPk;
    }

    public void setDmalmProdottoPk(Integer dmalmProdottoPk) {
        this.dmalmProdottoPk = dmalmProdottoPk;
    }

    public String getDsProdotto() {
        return dsProdotto;
    }

    public void setDsProdotto(String dsProdotto) {
        this.dsProdotto = dsProdotto;
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

    public String getFornituraRisorseEsterne() {
        return fornituraRisorseEsterne;
    }

    public void setFornituraRisorseEsterne(String fornituraRisorseEsterne) {
        this.fornituraRisorseEsterne = fornituraRisorseEsterne;
    }

    public Integer getIdEdma() {
        return idEdma;
    }

    public void setIdEdma(Integer idEdma) {
        this.idEdma = idEdma;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipoOggetto() {
        return tipoOggetto;
    }

    public void setTipoOggetto(String tipoOggetto) {
        this.tipoOggetto = tipoOggetto;
    }

}

