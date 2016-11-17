package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmModulo is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmModulo {

    private String annullato;

    private Integer dmalmModuloPk;

    private String dsModulo;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;
    
    private java.sql.Timestamp dtInserimentoRecord;

    private Integer dmalmProdottoFk02;
    
    private Integer dmalmSottosistemaFk03; 

    private String idModulo;

    private String nome;

    private String dmalmPersonaleFk01;

    private String siglaModulo;

    private String siglaProdotto;

    private String siglaSottosistema;

    private String sottosistema;

    private String tecnologie;

    private String tipoModulo;

    private String tipoOggetto;
    
    private java.sql.Timestamp dtAnnullamento;
    
    private Integer dmalmModuloSeq;

    public Integer getDmalmModuloSeq() {
		return dmalmModuloSeq;
	}

	public void setDmalmModuloSeq(Integer dmalmModuloSeq) {
		this.dmalmModuloSeq = dmalmModuloSeq;
	}

	public java.sql.Timestamp getDtAnnullamento ()
	{
		return dtAnnullamento;
	}

	public void setDtAnnullamento (java.sql.Timestamp dtAnnullamento)
	{
		this.dtAnnullamento = dtAnnullamento;
	}

	public Integer getDmalmSottosistemaFk03 ()
	{
		return dmalmSottosistemaFk03;
	}

	public void setDmalmSottosistemaFk03 (Integer dmalmSottosistemaFk03)
	{
		this.dmalmSottosistemaFk03 = dmalmSottosistemaFk03;
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

    public Integer getDmalmModuloPk() {
        return dmalmModuloPk;
    }

    public void setDmalmModuloPk(Integer dmalmModuloPk) {
        this.dmalmModuloPk = dmalmModuloPk;
    }

    public String getDsModulo() {
        return dsModulo;
    }

    public void setDsModulo(String dsModulo) {
        this.dsModulo = dsModulo;
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


    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getSottosistema() {
        return sottosistema;
    }

    public void setSottosistema(String sottosistema) {
        this.sottosistema = sottosistema;
    }

    public String getTecnologie() {
        return tecnologie;
    }

    public void setTecnologie(String tecnologie) {
        this.tecnologie = tecnologie;
    }

    public String getTipoModulo() {
        return tipoModulo;
    }

    public void setTipoModulo(String tipoModulo) {
        this.tipoModulo = tipoModulo;
    }

    public String getTipoOggetto() {
        return tipoOggetto;
    }

    public void setTipoOggetto(String tipoOggetto) {
        this.tipoOggetto = tipoOggetto;
    }

	public Integer getDmalmProdottoFk02() {
		return dmalmProdottoFk02;
	}

	public void setDmalmProdottoFk02(Integer dmalmProdottoFk02) {
		this.dmalmProdottoFk02 = dmalmProdottoFk02;
	}

	public String getDmalmPersonaleFk01() {
		return dmalmPersonaleFk01;
	}

	public void setDmalmPersonaleFk01(String dmalmPersonaleFk01) {
		this.dmalmPersonaleFk01 = dmalmPersonaleFk01;
	}
    
    

}

