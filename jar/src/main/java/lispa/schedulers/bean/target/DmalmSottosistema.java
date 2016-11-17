package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmSottosistema is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmSottosistema {

    private String annullato;

    private String baseDatiEtl;

    private String baseDatiLettura;

    private String baseDatiScrittura;

    private Integer dmalmSottosistemaPk;

    private String dsSottosistema;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtInserimentoRecord;

    private Integer dmalmProdottoFk01;

    private String idSottosistema;

    private String nome;

    private String siglaProdotto;

    private String siglaSottosistema;

    private String tipo;

    private String tipoOggetto;

    private java.sql.Timestamp dtAnnullamento;
    
    private Integer dmalmSottosistemaSeq;
    
    
    
    public Integer getDmalmSottosistemaSeq() {
		return dmalmSottosistemaSeq;
	}

	public void setDmalmSottosistemaSeq(Integer dmalmSottosistemaSeq) {
		this.dmalmSottosistemaSeq = dmalmSottosistemaSeq;
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

    public Integer getDmalmSottosistemaPk() {
        return dmalmSottosistemaPk;
    }

    public void setDmalmSottosistemaPk(Integer dmalmSottosistemaPk) {
        this.dmalmSottosistemaPk = dmalmSottosistemaPk;
    }

    public String getDsSottosistema() {
        return dsSottosistema;
    }

    public void setDsSottosistema(String dsSottosistema) {
        this.dsSottosistema = dsSottosistema;
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

    public Integer getDmalmProdottoFk01() {
		return dmalmProdottoFk01;
	}

	public void setDmalmProdottoFk01(Integer dmalmProdottoFk01) {
		this.dmalmProdottoFk01 = dmalmProdottoFk01;
	}

	public String getIdSottosistema() {
        return idSottosistema;
    }

    public void setIdSottosistema(String idSottosistema) {
        this.idSottosistema = idSottosistema;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoOggetto() {
        return tipoOggetto;
    }

    public void setTipoOggetto(String tipoOggetto) {
        this.tipoOggetto = tipoOggetto;
    }

}

