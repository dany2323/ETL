package lispa.schedulers.bean.staging.calipso;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class DmalmStgCalipsoSchedaServizio implements SQLData {

	private static final Integer COUNT_COLUMN = 18;
	private String sql_type;
	private String codiceServizio;
	private String servizioBusiness;
	private String ambito;
	private String cliente;
	private String responsabileStruttura;
	private String responsabileServizio;
	private String responsabileGestione;
	private String referenteGestione;
	private String referenteApplicazione;
	private String softwareSupporto;
	private String ambitoManutenzioneOrdinarSW;
	private String tipologiaIncarico;
	private String schedaIncarico;
	private String statoServizio;
	private String tipologiaInfrastruttura;
	private String classeServizioInfrstrttrl;
	private String ambitoAssiGestRL;
	private String dataUltimoAggiornamento;

	public String getCodiceServizio() {
		return codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

	public String getServizioBusiness() {
		return servizioBusiness;
	}

	public void setServizioBusiness(String servizioBusiness) {
		this.servizioBusiness = servizioBusiness;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getResponsabileStruttura() {
		return responsabileStruttura;
	}

	public void setResponsabileStruttura(String responsabileStruttura) {
		this.responsabileStruttura = responsabileStruttura;
	}

	public String getResponsabileServizio() {
		return responsabileServizio;
	}

	public void setResponsabileServizio(String responsabileServizio) {
		this.responsabileServizio = responsabileServizio;
	}

	public String getResponsabileGestione() {
		return responsabileGestione;
	}

	public void setResponsabileGestione(String responsabileGestione) {
		this.responsabileGestione = responsabileGestione;
	}

	public String getReferenteGestione() {
		return referenteGestione;
	}

	public void setReferenteGestione(String referenteGestione) {
		this.referenteGestione = referenteGestione;
	}

	public String getReferenteApplicazione() {
		return referenteApplicazione;
	}

	public void setReferenteApplicazione(String referenteApplicazione) {
		this.referenteApplicazione = referenteApplicazione;
	}

	public String getSoftwareSupporto() {
		return softwareSupporto;
	}

	public void setSoftwareSupporto(String softwareSupporto) {
		this.softwareSupporto = softwareSupporto;
	}

	public String getAmbitoManutenzioneOrdinarSW() {
		return ambitoManutenzioneOrdinarSW;
	}

	public void setAmbitoManutenzioneOrdinarSW(String ambitoManutenzioneOrdinarSW) {
		this.ambitoManutenzioneOrdinarSW = ambitoManutenzioneOrdinarSW;
	}

	public String getTipologiaIncarico() {
		return tipologiaIncarico;
	}

	public void setTipologiaIncarico(String tipologiaIncarico) {
		this.tipologiaIncarico = tipologiaIncarico;
	}

	public String getSchedaIncarico() {
		return schedaIncarico;
	}

	public void setSchedaIncarico(String schedaIncarico) {
		this.schedaIncarico = schedaIncarico;
	}

	public String getStatoServizio() {
		return statoServizio;
	}

	public void setStatoServizio(String statoServizio) {
		this.statoServizio = statoServizio;
	}

	public String getTipologiaInfrastruttura() {
		return tipologiaInfrastruttura;
	}

	public void setTipologiaInfrastruttura(String tipologiaInfrastruttura) {
		this.tipologiaInfrastruttura = tipologiaInfrastruttura;
	}

	public String getClasseServizioInfrstrttrl() {
		return classeServizioInfrstrttrl;
	}

	public void setClasseServizioInfrstrttrl(String classeServizioInfrstrttrl) {
		this.classeServizioInfrstrttrl = classeServizioInfrstrttrl;
	}

	public String getAmbitoAssiGestRL() {
		return ambitoAssiGestRL;
	}

	public void setAmbitoAssiGestRL(String ambitoAssiGestRL) {
		this.ambitoAssiGestRL = ambitoAssiGestRL;
	}

	public String getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(String dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public Object[] getObject(DmalmStgCalipsoSchedaServizio schedaServizio) throws Exception {
		
		Object[] objRichSupp = new Object[COUNT_COLUMN];
		objRichSupp[0] = schedaServizio.getCodiceServizio();
		objRichSupp[1] = schedaServizio.getServizioBusiness();
		objRichSupp[2] = schedaServizio.getAmbito();
		objRichSupp[3] = schedaServizio.getCliente();
		objRichSupp[4] = schedaServizio.getResponsabileStruttura();
		objRichSupp[5] = schedaServizio.getResponsabileServizio();
		objRichSupp[6] = schedaServizio.getResponsabileGestione();
		objRichSupp[7] = schedaServizio.getReferenteGestione();
		objRichSupp[8] = schedaServizio.getReferenteApplicazione();
		objRichSupp[9] = schedaServizio.getSoftwareSupporto();
		objRichSupp[10] = schedaServizio.getAmbitoManutenzioneOrdinarSW();
		objRichSupp[11] = schedaServizio.getTipologiaIncarico();
		objRichSupp[12] = schedaServizio.getSchedaIncarico();
		objRichSupp[13] = schedaServizio.getStatoServizio();
		objRichSupp[14] = schedaServizio.getTipologiaInfrastruttura();
		objRichSupp[15] = schedaServizio.getClasseServizioInfrstrttrl();
		objRichSupp[16] = schedaServizio.getAmbitoAssiGestRL();
		objRichSupp[17] = schedaServizio.getDataUltimoAggiornamento();
		
		return objRichSupp;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException { 
		return sql_type;
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		
		sql_type = typeName;
		
		codiceServizio = stream.readString();
		servizioBusiness = stream.readString();
		ambito = stream.readString();
		cliente = stream.readString();
		responsabileStruttura = stream.readString();
		responsabileServizio = stream.readString();
		responsabileGestione = stream.readString();
		referenteGestione = stream.readString();
		referenteApplicazione = stream.readString();
		softwareSupporto = stream.readString();
		ambitoManutenzioneOrdinarSW = stream.readString();
		tipologiaIncarico = stream.readString();
		schedaIncarico = stream.readString();
		statoServizio = stream.readString();
		tipologiaInfrastruttura = stream.readString();
		classeServizioInfrstrttrl = stream.readString();
		ambitoAssiGestRL = stream.readString();
		dataUltimoAggiornamento = stream.readString();
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException { 

		stream.writeString(codiceServizio);
		stream.writeString(servizioBusiness);
	    stream.writeString(ambito);
		stream.writeString(cliente);
		stream.writeString(responsabileStruttura);
		stream.writeString(responsabileServizio);
		stream.writeString(responsabileGestione);
		stream.writeString(referenteGestione);
		stream.writeString(referenteApplicazione);
		stream.writeString(softwareSupporto);
		stream.writeString(ambitoManutenzioneOrdinarSW);
		stream.writeString(tipologiaIncarico);
		stream.writeString(schedaIncarico);
		stream.writeString(statoServizio);
		stream.writeString(tipologiaInfrastruttura);
		stream.writeString(classeServizioInfrstrttrl);
		stream.writeString(ambitoAssiGestRL);
		stream.writeString(dataUltimoAggiornamento);
	}
}
