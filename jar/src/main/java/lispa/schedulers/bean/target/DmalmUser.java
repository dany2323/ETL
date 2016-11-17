package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmUser is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmUser {

    private java.sql.Timestamp dataCaricamento;

    private Boolean deleted;

    private Boolean disabledNotification;

    private Integer dmalmUserPk;

    private java.sql.Timestamp dtFineValidita;

    private java.sql.Timestamp dtInizioValidita;

    private String idUser;

    private String initialsUser;

    private String userAvatarFilename;

    private String userEmail;

    private String userName;
    
    private String idRepository;

    public java.sql.Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDisabledNotification() {
        return disabledNotification;
    }

    public void setDisabledNotification(Boolean disabledNotification) {
        this.disabledNotification = disabledNotification;
    }

    public Integer getDmalmUserPk() {
        return dmalmUserPk;
    }

    public void setDmalmUserPk(Integer dmalmUserPk) {
        this.dmalmUserPk = dmalmUserPk;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getInitialsUser() {
        return initialsUser;
    }

    public void setInitialsUser(String initialsUser) {
        this.initialsUser = initialsUser;
    }

    public String getUserAvatarFilename() {
        return userAvatarFilename;
    }

    public void setUserAvatarFilename(String userAvatarFilename) {
        this.userAvatarFilename = userAvatarFilename;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}

}

