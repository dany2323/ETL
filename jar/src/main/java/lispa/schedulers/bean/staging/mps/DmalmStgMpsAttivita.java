package lispa.schedulers.bean.staging.mps;

import javax.annotation.Generated;

/**
 * DmalmStgMpsAttivita is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmStgMpsAttivita {

    private Integer idAttivitaPadre;

    private Integer idAttivita;

    private Integer idContratto;
    
    private String codAttivita;

    private String titolo;

    private String desAttivita;
    
    private java.sql.Timestamp dataInizio;

    private java.sql.Timestamp dataFine;
    
    private Integer avanzamento;

    private java.sql.Timestamp dataUltimoAvanzamento;
    
    private String tipoAttivita;
    
    private String stato;
    
    private Integer inseritoDa;
    
    private java.sql.Timestamp inseritoIl;

    private Integer modificatoDa;
    
    private java.sql.Timestamp modificatoIl;

    private String recordStatus;

	public Integer getIdAttivitaPadre() {
		return idAttivitaPadre;
	}

	public void setIdAttivitaPadre(Integer idAttivitaPadre) {
		this.idAttivitaPadre = idAttivitaPadre;
	}

	public Integer getIdAttivita() {
		return idAttivita;
	}

	public void setIdAttivita(Integer idAttivita) {
		this.idAttivita = idAttivita;
	}

	public Integer getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(Integer idContratto) {
		this.idContratto = idContratto;
	}

	public String getCodAttivita() {
		return codAttivita;
	}

	public void setCodAttivita(String codAttivita) {
		this.codAttivita = codAttivita;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDesAttivita() {
		return desAttivita;
	}

	public void setDesAttivita(String desAttivita) {
		this.desAttivita = desAttivita;
	}

	public java.sql.Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(java.sql.Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public java.sql.Timestamp getDataFine() {
		return dataFine;
	}

	public void setDataFine(java.sql.Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getAvanzamento() {
		return avanzamento;
	}

	public void setAvanzamento(Integer avanzamento) {
		this.avanzamento = avanzamento;
	}

	public java.sql.Timestamp getDataUltimoAvanzamento() {
		return dataUltimoAvanzamento;
	}

	public void setDataUltimoAvanzamento(java.sql.Timestamp dataUltimoAvanzamento) {
		this.dataUltimoAvanzamento = dataUltimoAvanzamento;
	}

	public String getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Integer getInseritoDa() {
		return inseritoDa;
	}

	public void setInseritoDa(Integer inseritoDa) {
		this.inseritoDa = inseritoDa;
	}

	public java.sql.Timestamp getInseritoIl() {
		return inseritoIl;
	}

	public void setInseritoIl(java.sql.Timestamp inseritoIl) {
		this.inseritoIl = inseritoIl;
	}

	public Integer getModificatoDa() {
		return modificatoDa;
	}

	public void setModificatoDa(Integer modificatoDa) {
		this.modificatoDa = modificatoDa;
	}

	public java.sql.Timestamp getModificatoIl() {
		return modificatoIl;
	}

	public void setModificatoIl(java.sql.Timestamp modificatoIl) {
		this.modificatoIl = modificatoIl;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

    }
