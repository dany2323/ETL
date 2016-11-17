package lispa.schedulers.bean.fonte.elettra;

import java.util.Date;

public class ElettraUnitaOrganizzative {
	private String idEdma;
	private String codiceUnitaOrganizzativa;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String descrizioneUnitaOrganizzativa;
    private Date dataAttivazione;
    private Date dataDisattivazione;
    private String note;
    private Short flagInterno;
    private String codiceResponsabile;
    private String indirizzoEmail;
    private Integer tipologiaUfficio;
    private Integer gradoUfficio;
    private Integer idSede;
    private String codiceUOSuperiore;
    private String codiceEnte;
    private String codiceVisibilita;
    
	public String getIdEdma() {
		return idEdma;
	}
	public void setIdEdma(String idEdma) {
		this.idEdma = idEdma;
	}
	public String getCodiceUnitaOrganizzativa() {
		return codiceUnitaOrganizzativa;
	}
	public void setCodiceUnitaOrganizzativa(String codiceUnitaOrganizzativa) {
		this.codiceUnitaOrganizzativa = codiceUnitaOrganizzativa;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getDescrizioneUnitaOrganizzativa() {
		return descrizioneUnitaOrganizzativa;
	}
	public void setDescrizioneUnitaOrganizzativa(
			String descrizioneUnitaOrganizzativa) {
		this.descrizioneUnitaOrganizzativa = descrizioneUnitaOrganizzativa;
	}
	public Date getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	public Date getDataDisattivazione() {
		return dataDisattivazione;
	}
	public void setDataDisattivazione(Date dataDisattivazione) {
		this.dataDisattivazione = dataDisattivazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Short getFlagInterno() {
		return flagInterno;
	}
	public void setFlagInterno(Short flagInterno) {
		this.flagInterno = flagInterno;
	}
	public String getCodiceResponsabile() {
		return codiceResponsabile;
	}
	public void setCodiceResponsabile(String codiceResponsabile) {
		this.codiceResponsabile = codiceResponsabile;
	}
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}
	public Integer getTipologiaUfficio() {
		return tipologiaUfficio;
	}
	public void setTipologiaUfficio(Integer tipologiaUfficio) {
		this.tipologiaUfficio = tipologiaUfficio;
	}
	public Integer getGradoUfficio() {
		return gradoUfficio;
	}
	public void setGradoUfficio(Integer gradoUfficio) {
		this.gradoUfficio = gradoUfficio;
	}
	public Integer getIdSede() {
		return idSede;
	}
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	public String getCodiceUOSuperiore() {
		return codiceUOSuperiore;
	}
	public void setCodiceUOSuperiore(String codiceUOSuperiore) {
		this.codiceUOSuperiore = codiceUOSuperiore;
	}
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public String getCodiceVisibilita() {
		return codiceVisibilita;
	}
	public void setCodiceVisibilita(String codiceVisibilita) {
		this.codiceVisibilita = codiceVisibilita;
	}
}
