package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;

/**
 * DmalmTask is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmTask {

    private String cdTask;

    private String codice;

    private java.sql.Timestamp dataChiusuraTask;

    private java.sql.Timestamp dataFineEffettiva;

    private java.sql.Timestamp dataFinePianificata;

    private java.sql.Timestamp dataInizioEffettivo;

    private java.sql.Timestamp dataInizioPianificato;

    private String descrizioneTask;

    private Integer dmalmAreaTematicaFk05;

    private Long dmalmProjectFk02;

    private Integer dmalmStatoWorkitemFk03;

    private Integer dmalmStrutturaOrgFk01;

    private Long dmalmTaskPk;

    private Integer dmalmTempoFk04;
    
    private Integer dmalmUserFk06;

    private String dsAutoreTask;

    private java.sql.Timestamp dtCambioStatoTask;

    private java.sql.Timestamp dtCaricamentoTask;

    private java.sql.Timestamp dtCreazioneTask;

    private java.sql.Timestamp dtModificaTask;

    private java.sql.Timestamp dtRisoluzioneTask;

    private java.sql.Timestamp dtScadenzaTask;

    private java.sql.Timestamp dtStoricizzazione;

    private String idAutoreTask;

    private String idRepository;

    private Short initialCost;

    private String motivoRisoluzioneTask;

    private String numeroLinea;

    private String numeroTestata;

    private String priorityTask;

    private Double rankStatoTask;

    private Short rankStatoTaskMese;

    private String severityTask;

    private String taskType;

    private Integer tempoTotaleRisoluzione;

    private String titoloTask;
    
    private String stgPk;
    
    private String uri;
    
    private String changed;
    
	private java.sql.Timestamp dtAnnullamento;
	
	private String annullato;
    
	//DM_ALM-470
	private String tagAlm;
	private java.sql.Timestamp tsTagAlm;
		
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


    public String getCdTask() {
        return cdTask;
    }

    public void setCdTask(String cdTask) {
        this.cdTask = cdTask;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public java.sql.Timestamp getDataChiusuraTask() {
        return dataChiusuraTask;
    }

    public void setDataChiusuraTask(java.sql.Timestamp dataChiusuraTask) {
        this.dataChiusuraTask = dataChiusuraTask;
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

    public String getDescrizioneTask() {
        return descrizioneTask;
    }

    public void setDescrizioneTask(String descrizioneTask) {
        this.descrizioneTask = descrizioneTask;
    }

    public Integer getDmalmAreaTematicaFk05() {
        return dmalmAreaTematicaFk05;
    }

    public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
        this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
    }

    public Long getDmalmProjectFk02() {
        return dmalmProjectFk02;
    }

    public void setDmalmProjectFk02(Long dmalmProjectFk02) {
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

    public Long getDmalmTaskPk() {
        return dmalmTaskPk;
    }

    public void setDmalmTaskPk(Long dmalmTaskPk) {
        this.dmalmTaskPk = dmalmTaskPk;
    }

    public Integer getDmalmTempoFk04() {
        return dmalmTempoFk04;
    }

    public void setDmalmTempoFk04(Integer dmalmTempoFk04) {
        this.dmalmTempoFk04 = dmalmTempoFk04;
    }

    public String getDsAutoreTask() {
        return dsAutoreTask;
    }

    public void setDsAutoreTask(String dsAutoreTask) {
        this.dsAutoreTask = dsAutoreTask;
    }

    public java.sql.Timestamp getDtCambioStatoTask() {
        return dtCambioStatoTask;
    }

    public void setDtCambioStatoTask(java.sql.Timestamp dtCambioStatoTask) {
        this.dtCambioStatoTask = dtCambioStatoTask;
    }

    public java.sql.Timestamp getDtCaricamentoTask() {
        return dtCaricamentoTask;
    }

    public void setDtCaricamentoTask(java.sql.Timestamp dtCaricamentoTask) {
        this.dtCaricamentoTask = dtCaricamentoTask;
    }

    public java.sql.Timestamp getDtCreazioneTask() {
        return dtCreazioneTask;
    }

    public void setDtCreazioneTask(java.sql.Timestamp dtCreazioneTask) {
        this.dtCreazioneTask = dtCreazioneTask;
    }

    public java.sql.Timestamp getDtModificaTask() {
        return dtModificaTask;
    }

    public void setDtModificaTask(java.sql.Timestamp dtModificaTask) {
        this.dtModificaTask = dtModificaTask;
    }

    public java.sql.Timestamp getDtRisoluzioneTask() {
        return dtRisoluzioneTask;
    }

    public void setDtRisoluzioneTask(java.sql.Timestamp dtRisoluzioneTask) {
        this.dtRisoluzioneTask = dtRisoluzioneTask;
    }

    public java.sql.Timestamp getDtScadenzaTask() {
        return dtScadenzaTask;
    }

    public void setDtScadenzaTask(java.sql.Timestamp dtScadenzaTask) {
        this.dtScadenzaTask = dtScadenzaTask;
    }

    public java.sql.Timestamp getDtStoricizzazione() {
        return dtStoricizzazione;
    }

    public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
        this.dtStoricizzazione = dtStoricizzazione;
    }

    public String getIdAutoreTask() {
        return idAutoreTask;
    }

    public void setIdAutoreTask(String idAutoreTask) {
        this.idAutoreTask = idAutoreTask;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public Short getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(Short initialCost) {
        this.initialCost = initialCost;
    }

    public String getMotivoRisoluzioneTask() {
        return motivoRisoluzioneTask;
    }

    public void setMotivoRisoluzioneTask(String motivoRisoluzioneTask) {
        this.motivoRisoluzioneTask = motivoRisoluzioneTask;
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

    public String getPriorityTask() {
        return priorityTask;
    }

    public void setPriorityTask(String priorityTask) {
        this.priorityTask = priorityTask;
    }

    public Double getRankStatoTask() {
        return rankStatoTask;
    }

    public void setRankStatoTask(Double rankStatoTask) {
        this.rankStatoTask = rankStatoTask;
    }

    public Short getRankStatoTaskMese() {
        return rankStatoTaskMese;
    }

    public void setRankStatoTaskMese(Short rankStatoTaskMese) {
        this.rankStatoTaskMese = rankStatoTaskMese;
    }

    public String getSeverityTask() {
        return severityTask;
    }

    public void setSeverityTask(String severityTask) {
        this.severityTask = severityTask;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTempoTotaleRisoluzione() {
        return tempoTotaleRisoluzione;
    }

    public void setTempoTotaleRisoluzione(Integer tempoTotaleRisoluzione) {
        this.tempoTotaleRisoluzione = tempoTotaleRisoluzione;
    }

    public String getTitoloTask() {
        return titoloTask;
    }

    public void setTitoloTask(String titoloTask) {
        this.titoloTask = titoloTask;
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

}

