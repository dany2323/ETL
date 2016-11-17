package lispa.schedulers.bean.target;

import java.sql.Timestamp;

import javax.annotation.Generated;

/**
 * DmalmProject is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmProject {

    private Integer dmalmAreaTematicaFk01;
    
    private Integer dmalmStrutturaOrgFk02;
    
    private Integer dmalmUnitaOrganizzativaFk;
    
    private Integer dmalmUnitaOrganizzativaFlatFk;

    private Integer dmalmProjectPk;

    private java.sql.Timestamp dtCaricamento;

    private String dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;

    private Boolean flAttivo;
    
    private String annullato;

    private String idProject;

    private String idRepository;

    private String nomeCompletoProject;

    private String pathProject;

    private String serviceManagers;

    private String siglaProject;
    
    private Timestamp cCreated;
    
    private String cTemplate;
    
    private String cTrackerprefix;
    
    private Integer cIsLocal;
    
    private String cPk;
    
    private String fkUriLead;
    
    private Integer cDeleted;

    private java.sql.Timestamp cFinish;
    
    private String cUri;
    
    private java.sql.Timestamp cStart;
    
    private String fkUriProjectgroup;
    
    private Integer cActive;
    
    private String fkProjectgroup;

    private String fkLead;
    
    private java.sql.Timestamp cLockworkrecordsdate;
    
    private Long cRev;
    
    private String cDescription;
    
    private java.sql.Timestamp dtAnnullamento;

    public Integer getDmalmAreaTematicaFk01() {
        return dmalmAreaTematicaFk01;
    }

    public void setDmalmAreaTematicaFk01(Integer dmalmAreaTematicaFk01) {
        this.dmalmAreaTematicaFk01 = dmalmAreaTematicaFk01;
    }

    public Integer getDmalmProjectPk() {
        return dmalmProjectPk;
    }

    public void setDmalmProjectPk(Integer dmalmProjectPk) {
        this.dmalmProjectPk = dmalmProjectPk;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
    }

    public String getDtFineValidita() {
        return dtFineValidita;
    }

    public void setDtFineValidita(String dtFineValidita) {
        this.dtFineValidita = dtFineValidita;
    }

    public java.sql.Timestamp getDtInizioValidita() {
        return dtInizioValidita;
    }

    public void setDtInizioValidita(java.sql.Timestamp dtInizioValidita) {
        this.dtInizioValidita = dtInizioValidita;
    }

    public Boolean getFlAttivo() {
        return flAttivo;
    }

    public void setFlAttivo(Boolean flAttivo) {
        this.flAttivo = flAttivo;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(String idRepository) {
        this.idRepository = idRepository;
    }

    public String getNomeCompletoProject() {
        return nomeCompletoProject;
    }

    public void setNomeCompletoProject(String nomeCompletoProject) {
        this.nomeCompletoProject = nomeCompletoProject;
    }

    public String getPathProject() {
        return pathProject;
    }

    public void setPathProject(String pathProject) {
        this.pathProject = pathProject;
    }

    public String getServiceManagers() {
        return serviceManagers;
    }

    public void setServiceManagers(String serviceManagers) {
        this.serviceManagers = serviceManagers;
    }

    public String getSiglaProject() {
        return siglaProject;
    }

    public void setSiglaProject(String siglaProject) {
        this.siglaProject = siglaProject;
    }

	public Timestamp getcCreated() {
		return cCreated;
	}

	public void setcCreated(Timestamp cCreated) {
		this.cCreated = cCreated;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setUnmarked(String annullato) {
		this.annullato = annullato;
	}

	public String getcTemplate() {
		return cTemplate;
	}

	public void setcTemplate(String cTemplate) {
		this.cTemplate = cTemplate;
	}

	public Integer getDmalmStrutturaOrgFk02() {
		return dmalmStrutturaOrgFk02;
	}

	public void setDmalmStrutturaOrgFk02(Integer dmalmStrutturaOrgFk01) {
		this.dmalmStrutturaOrgFk02 = dmalmStrutturaOrgFk01;
	}

	public Integer getDmalmUnitaOrganizzativaFk() {
		return dmalmUnitaOrganizzativaFk;
	}

	public void setDmalmUnitaOrganizzativaFk(Integer dmalmUnitaOrganizzativaFk) {
		this.dmalmUnitaOrganizzativaFk = dmalmUnitaOrganizzativaFk;
	}

	public Integer getDmalmUnitaOrganizzativaFlatFk() {
		return dmalmUnitaOrganizzativaFlatFk;
	}

	public void setDmalmUnitaOrganizzativaFlatFk(
			Integer dmalmUnitaOrganizzativaFlatFk) {
		this.dmalmUnitaOrganizzativaFlatFk = dmalmUnitaOrganizzativaFlatFk;
	}

	public String getcTrackerprefix() {
		return cTrackerprefix;
	}

	public void setcTrackerprefix(String cTrackerprefix) {
		this.cTrackerprefix = cTrackerprefix;
	}

	public String getcPk() {
		return cPk;
	}

	public void setcPk(String cPk) {
		this.cPk = cPk;
	}

	public String getFkUriLead() {
		return fkUriLead;
	}

	public void setFkUriLead(String fkUriLead) {
		this.fkUriLead = fkUriLead;
	}

	public java.sql.Timestamp getcFinish() {
		return cFinish;
	}

	public void setcFinish(java.sql.Timestamp cFinish) {
		this.cFinish = cFinish;
	}

	public String getcUri() {
		return cUri;
	}

	public void setcUri(String cUri) {
		this.cUri = cUri;
	}

	public java.sql.Timestamp getcStart() {
		return cStart;
	}

	public void setcStart(java.sql.Timestamp cStart) {
		this.cStart = cStart;
	}

	public String getFkUriProjectgroup() {
		return fkUriProjectgroup;
	}

	public void setFkUriProjectgroup(String fkUriProjectgroup) {
		this.fkUriProjectgroup = fkUriProjectgroup;
	}

	public String getFkProjectgroup() {
		return fkProjectgroup;
	}

	public void setFkProjectgroup(String fkProjectgroup) {
		this.fkProjectgroup = fkProjectgroup;
	}

	public String getFkLead() {
		return fkLead;
	}

	public void setFkLead(String fkLead) {
		this.fkLead = fkLead;
	}

	public java.sql.Timestamp getcLockworkrecordsdate() {
		return cLockworkrecordsdate;
	}

	public void setcLockworkrecordsdate(java.sql.Timestamp cLockworkrecordsdate) {
		this.cLockworkrecordsdate = cLockworkrecordsdate;
	}

	public Long getcRev() {
		return cRev;
	}

	public void setcRev(Long cRev) {
		this.cRev = cRev;
	}

	public String getcDescription() {
		return cDescription;
	}

	public void setcDescription(String cDescription) {
		this.cDescription = cDescription;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public Integer getcIsLocal() {
		return cIsLocal;
	}

	public void setcIsLocal(Integer cIsLocal) {
		this.cIsLocal = cIsLocal;
	}

	public Integer getcDeleted() {
		return cDeleted;
	}

	public void setcDeleted(Integer cDeleted) {
		this.cDeleted = cDeleted;
	}

	public Integer getcActive() {
		return cActive;
	}

	public void setcActive(Integer cActive) {
		this.cActive = cActive;
	}

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}
}

