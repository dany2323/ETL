package lispa.schedulers.bean.target;

import java.sql.Timestamp;

import javax.annotation.Generated;

/**
 * DmalmUserRolesSgr is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmUserRolesSgr {

    private Integer dmalmUserRolesPk;

    private java.sql.Timestamp dtCaricamento;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;

    private String origine;

    private String ruolo;

    private String userid;
    
    private String repository;
    
    private Integer dmalmProjectFk01;
    
    private Timestamp dtModifica;

    /**
	 * @return the dtModifica
	 */
	public Timestamp getDtModifica() {
		return dtModifica;
	}

	/**
	 * @param dtModifica the dtModifica to set
	 */
	public void setDtModifica(Timestamp dtModifica) {
		this.dtModifica = dtModifica;
	}

	public Integer getDmalmUserRolesPk() {
        return dmalmUserRolesPk;
    }

    public void setDmalmUserRolesPk(Integer dmalmUserRoles) {
        this.dmalmUserRolesPk = dmalmUserRoles;
    }

    public java.sql.Timestamp getDtCaricamento() {
        return dtCaricamento;
    }

    public void setDtCaricamento(java.sql.Timestamp dtCaricamento) {
        this.dtCaricamento = dtCaricamento;
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

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	@Override
	public boolean equals(Object x) {
		
		if(x == null) return false;
		if(!(x instanceof DmalmUserRolesSgr)) return false;
		if(this.hashCode() != x.hashCode()) return false;
		DmalmUserRolesSgr beanx = (DmalmUserRolesSgr)x;
		
		return (
				this.getUserid().equals(beanx.getUserid()) 
				&&
				this.getRuolo().equals(beanx.getRuolo()) 
				&&
				this.getRepository().equals(beanx.getRepository())
				&&
				this.getOrigine().equals(beanx.getOrigine())
				); 
	}
	
	@Override
	public int hashCode(){
	   
		return getRepository().hashCode() + getOrigine().hashCode() + getUserid().hashCode() + getRuolo().hashCode();
		
	}

	public Integer getDmalmProjectFk01() {
		return dmalmProjectFk01;
	}

	public void setDmalmProjectFk01(Integer dmalmProjectFk01) {
		this.dmalmProjectFk01 = dmalmProjectFk01;
	}

}

