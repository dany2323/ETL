package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmAmbienteTecnologico is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmAmbienteTecnologico {

    private String architettura;

    private Integer dmalmAmbienteTecnologicoPk;

    private String dsAmbienteTecnologico;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;

    private java.sql.Timestamp dtInserimentoRecord;
    
    private String idAmbienteTecnologico;

    private Integer dmalmProdottoFk01;
    
    private Integer dmalmModuloFk02;

    private String infrastruttura;

    private String nome;

    private String siglaModulo;

    private String siglaProdotto;

    private String sistemaOperativo;

    private String tipoLayer;

    private String tipoOggetto;

    private String versioneSo;
    
    private String annullato;
    
    private java.sql.Timestamp dtAnnullamento;
    
    private Integer dmalmAmbienteTecnologicoSeq;
    
    
    public Integer getDmalmAmbienteTecnologicoSeq() {
		return dmalmAmbienteTecnologicoSeq;
	}

	public void setDmalmAmbienteTecnologicoSeq(Integer dmalmAmbienteTecnologicoSeq) {
		this.dmalmAmbienteTecnologicoSeq = dmalmAmbienteTecnologicoSeq;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public java.sql.Timestamp getDtInserimentoRecord() {
		return dtInserimentoRecord;
	}

	public void setDtInserimentoRecord(java.sql.Timestamp dtInserimentoRecord) {
		this.dtInserimentoRecord = dtInserimentoRecord;
	}

	public Integer getDmalmModuloFk02() {
		return dmalmModuloFk02;
	}

	public void setDmalmModuloFk02(Integer dmalmModuloFk02) {
		this.dmalmModuloFk02 = dmalmModuloFk02;
	}

	public String getArchitettura() {
        return architettura;
    }

    public void setArchitettura(String architettura) {
        this.architettura = architettura;
    }

    public Integer getDmalmAmbienteTecnologicoPk() {
        return dmalmAmbienteTecnologicoPk;
    }

    public void setDmalmAmbienteTecnologicoPk(Integer dmalmAmbienteTecnologicoPk) {
        this.dmalmAmbienteTecnologicoPk = dmalmAmbienteTecnologicoPk;
    }

    public String getDsAmbienteTecnologico() {
        return dsAmbienteTecnologico;
    }

    public void setDsAmbienteTecnologico(String dsAmbienteTecnologico) {
        this.dsAmbienteTecnologico = dsAmbienteTecnologico;
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

    public String getIdAmbienteTecnologico() {
        return idAmbienteTecnologico;
    }

    public void setIdAmbienteTecnologico(String idAmbienteTecnologico) {
        this.idAmbienteTecnologico = idAmbienteTecnologico;
    }

    public Integer getDmalmProdottoFk01() {
		return dmalmProdottoFk01;
	}

	public void setDmalmProdottoFk01(Integer dmalmProdottoFk01) {
		this.dmalmProdottoFk01 = dmalmProdottoFk01;
	}

	public String getInfrastruttura() {
        return infrastruttura;
    }

    public void setInfrastruttura(String infrastruttura) {
        this.infrastruttura = infrastruttura;
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

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getTipoLayer() {
        return tipoLayer;
    }

    public void setTipoLayer(String tipoLayer) {
        this.tipoLayer = tipoLayer;
    }

    public String getTipoOggetto() {
        return tipoOggetto;
    }

    public void setTipoOggetto(String tipoOggetto) {
        this.tipoOggetto = tipoOggetto;
    }

    public String getVersioneSo() {
        return versioneSo;
    }

    public void setVersioneSo(String versioneSo) {
        this.versioneSo = versioneSo;
    }

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}

}

