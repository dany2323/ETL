package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmFase is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmFase {
	
	private String stgPk;

    private Boolean applicabile;

    private String cdFase;

    private String codice;

    private java.sql.Timestamp dataFineBaseline;

    private java.sql.Timestamp dataFineEffettiva;

    private java.sql.Timestamp dataFinePianificata;

    private java.sql.Timestamp dataInizioBaseline;

    private java.sql.Timestamp dataInizioEffettivo;

    private java.sql.Timestamp dataInizioPianificato;

    private java.sql.Timestamp dataPassaggioInEsecuzione;

    private String descrizioneFase;

    private Integer dmalmFasePk;

    private Integer dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreFase;

    private java.sql.Timestamp dtCambioStatoFase;

    private java.sql.Timestamp dtCaricamentoFase;

    private java.sql.Timestamp dtCreazioneFase;

    private java.sql.Timestamp dtModificaFase;

    private java.sql.Timestamp dtRisoluzioneFase;

    private java.sql.Timestamp dtScadenzaFase;

    private java.sql.Timestamp dtStoricizzazione;

    private Short durataEffettivaFase;

    private String idAutoreFase;

    private String idRepository;

    private String motivoRisoluzioneFase;

    private Double rankStatoFase;

    private Short rankStatoFaseMese;

    private String titoloFase;

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
	
    public Boolean getApplicabile() {
        return applicabile;
    }

    public void setApplicabile(Boolean applicabile) {
        this.applicabile = applicabile;
    }

    public String getCdFase() {
        return cdFase;
    }

    public void setCdFase(String cdFase) {
        this.cdFase = cdFase;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataFineBaseline() {
        return dataFineBaseline;
    }

    public void setDataFineBaseline(java.sql.Timestamp dataFineBaseline) {
        this.dataFineBaseline = dataFineBaseline;
    }

    public java.sql.Timestamp getDataFineEffettiva() {
        return dataFineEffettiva;
    }

    public void setDataFineEffettiva(java.sql.Timestamp dataFineEffettiva) {
        this.dataFineEffettiva = dataFineEffettiva;
    }

    public java.sql.Timestamp getDataFinePianificata() {
        return dataFinePianificata;
    }

    public void setDataFinePianificata(java.sql.Timestamp dataFinePianificata) {
        this.dataFinePianificata = dataFinePianificata;
    }

    public java.sql.Timestamp getDataInizioBaseline() {
        return dataInizioBaseline;
    }

    public void setDataInizioBaseline(java.sql.Timestamp dataInizioBaseline) {
        this.dataInizioBaseline = dataInizioBaseline;
    }

    public java.sql.Timestamp getDataInizioEffettivo() {
        return dataInizioEffettivo;
    }

    public void setDataInizioEffettivo(java.sql.Timestamp dataInizioEffettivo) {
        this.dataInizioEffettivo = dataInizioEffettivo;
    }

    public java.sql.Timestamp getDataInizioPianificato() {
        return dataInizioPianificato;
    }

    public void setDataInizioPianificato(java.sql.Timestamp dataInizioPianificato) {
        this.dataInizioPianificato = dataInizioPianificato;
    }

    public java.sql.Timestamp getDataPassaggioInEsecuzione() {
        return dataPassaggioInEsecuzione;
    }

    public void setDataPassaggioInEsecuzione(java.sql.Timestamp dataPassaggioInEsecuzione) {
        this.dataPassaggioInEsecuzione = dataPassaggioInEsecuzione;
    }

    public String getDescrizioneFase() {
        return descrizioneFase;
    }

    public void setDescrizioneFase(String descrizioneFase) {
        this.descrizioneFase = descrizioneFase;
    }

    public Integer getDmalmFasePk() {
        return dmalmFasePk;
    }

    public void setDmalmFasePk(Integer dmalmFasePk) {
        this.dmalmFasePk = dmalmFasePk;
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

    public String getDsAutoreFase() {
        return dsAutoreFase;
    }

    public void setDsAutoreFase(String dsAutoreFase) {
        this.dsAutoreFase = dsAutoreFase;
    }

    public java.sql.Timestamp getDtCambioStatoFase() {
        return dtCambioStatoFase;
    }

    public void setDtCambioStatoFase(java.sql.Timestamp dtCambioStatoFase) {
        this.dtCambioStatoFase = dtCambioStatoFase;
    }

    public java.sql.Timestamp getDtCaricamentoFase() {
        return dtCaricamentoFase;
    }

    public void setDtCaricamentoFase(java.sql.Timestamp dtCaricamentoFase) {
        this.dtCaricamentoFase = dtCaricamentoFase;
    }

    public java.sql.Timestamp getDtCreazioneFase() {
        return dtCreazioneFase;
    }

    public void setDtCreazioneFase(java.sql.Timestamp dtCreazioneFase) {
        this.dtCreazioneFase = dtCreazioneFase;
    }

    public java.sql.Timestamp getDtModificaFase() {
        return dtModificaFase;
    }

    public void setDtModificaFase(java.sql.Timestamp dtModificaFase) {
        this.dtModificaFase = dtModificaFase;
    }

    public java.sql.Timestamp getDtRisoluzioneFase() {
        return dtRisoluzioneFase;
    }

    public void setDtRisoluzioneFase(java.sql.Timestamp dtRisoluzioneFase) {
        this.dtRisoluzioneFase = dtRisoluzioneFase;
    }

    public java.sql.Timestamp getDtScadenzaFase() {
        return dtScadenzaFase;
    }

    public void setDtScadenzaFase(java.sql.Timestamp dtScadenzaFase) {
        this.dtScadenzaFase = dtScadenzaFase;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public Short getDurataEffettivaFase() {
        return durataEffettivaFase;
    }

    public void setDurataEffettivaFase(Short durataEffettivaFase) {
        this.durataEffettivaFase = durataEffettivaFase;
    }

    public String getIdAutoreFase() {
        return idAutoreFase;
    }

    public void setIdAutoreFase(String idAutoreFase) {
        this.idAutoreFase = idAutoreFase;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getMotivoRisoluzioneFase() {
        return motivoRisoluzioneFase;
    }

    public void setMotivoRisoluzioneFase(String motivoRisoluzioneFase) {
        this.motivoRisoluzioneFase = motivoRisoluzioneFase;
    }

    public Double getRankStatoFase() {
        return rankStatoFase;
    }

    public void setRankStatoFase(Double rankStatoFase) {
        this.rankStatoFase = rankStatoFase;
    }

    public Short getRankStatoFaseMese() {
        return rankStatoFaseMese;
    }

    public void setRankStatoFaseMese(Short rankStatoFaseMese) {
        this.rankStatoFaseMese = rankStatoFaseMese;
    }

    public String getTitoloFase() {
        return titoloFase;
    }

    public void setTitoloFase(String titoloFase) {
        this.titoloFase = titoloFase;
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

}

