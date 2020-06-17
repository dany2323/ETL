package lispa.schedulers.bean.target.fatti;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import lispa.schedulers.dao.UtilsDAO;

public class DmalmRfc implements SQLData {

	private static final Integer COUNT_COLUMN = 38;
	private String sql_type;
	private String idRepository;
	private Integer dmalmRfcPk;
	private String stgPk;
	private Long dmalmProjectFk02;
	private Integer dmalmUserFk06;
	private String cdRfc;
	private Integer dmalmStatoWorkitemFk03;
	private java.sql.Timestamp dataModificaRecord;
	private Integer rankStatoRfc;
	private java.sql.Timestamp dataCambioStatoRfc;
	private String annullato;
	private java.sql.Timestamp dataAnnullamento;
	private java.sql.Timestamp dataStoricizzazione;
	private java.sql.Timestamp dataCaricamento;
    private Float timespent;
  	private String tagAlm;
  	private java.sql.Timestamp tsTagAlm;
  	private Integer dmalmTempoFk04;
  	private Integer rankStatoRfcMese;
  	private String uriRfc;
  	private String description;
  	private String tipologiaIntervento;
  	private String tipologiaDatiTrattati;
  	private String richiestaIt;
  	private String infrastrutturaEsistente;
  	private String cambiamentoRichiesto;
  	private String descrizioneUtenza;
  	private String requisitiDiUtilizzo;
  	private String modalitaGestione;
  	private String criptazioneDati;
  	private String politicaArchiviazioneDati;
  	private String separazioneRuoliAccessoDati;
  	private String classeDiServizio;
  	private String accessoAlServizio;
  	private String assistenzaUtenti;
  	private String analisiDeiRischi;
  	private String descrizioneDelServizio;
  	private String codSchedaServizioPolarion;
  	private String titoloDellaRichiesta;
	
	public String getIdRepository() {
		return idRepository;
	}
	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}
	public String getStgPk() {
		return stgPk;
	}
	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}
	public Long getDmalmProjectFk02() {
		return dmalmProjectFk02;
	}
	public void setDmalmProjectFk02(Long dmalmProjectFk02) {
		this.dmalmProjectFk02 = dmalmProjectFk02;
	}
	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}
	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}
	public Integer getDmalmStatoWorkitemFk03() {
		return dmalmStatoWorkitemFk03;
	}
	public void setDmalmStatoWorkitemFk03(Integer dmalmStatoWorkitemFk03) {
		this.dmalmStatoWorkitemFk03 = dmalmStatoWorkitemFk03;
	}
	public java.sql.Timestamp getDataModificaRecord() {
		return dataModificaRecord;
	}
	public void setDataModificaRecord(java.sql.Timestamp dataModificaRecord) {
		this.dataModificaRecord = dataModificaRecord;
	}
	public String getAnnullato() {
		return annullato;
	}
	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}
	public java.sql.Timestamp getDataAnnullamento() {
		return dataAnnullamento;
	}
	public void setDataAnnullamento(java.sql.Timestamp dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}
	public java.sql.Timestamp getDataStoricizzazione() {
		return dataStoricizzazione;
	}
	public void setDataStoricizzazione(java.sql.Timestamp dataStoricizzazione) {
		this.dataStoricizzazione = dataStoricizzazione;
	}
	public java.sql.Timestamp getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(java.sql.Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Object[] getObject(DmalmRfc rfc, boolean flag) throws Exception {

		Integer seqId = UtilsDAO.getSequenceNextval("HISTORY_WORKITEM_SEQ.nextval");
		
		Object[] objRfc = new Object[COUNT_COLUMN];
		objRfc[0] = rfc.getIdRepository();
		objRfc[1] = (flag == true ? rfc.getDmalmRfcPk() : seqId);
		objRfc[2] = rfc.getStgPk();
		objRfc[3] = rfc.getDmalmProjectFk02();
		objRfc[4] = rfc.getDmalmUserFk06();
		objRfc[5] = rfc.getCdRfc();
		objRfc[6] = rfc.getDmalmStatoWorkitemFk03();
		objRfc[7] = rfc.getDataModificaRecord();
		objRfc[8] = (flag == true ? new Short("1") : 
			rfc.getRankStatoRfc());
		objRfc[9] = rfc.getDataCambioStatoRfc();
		objRfc[10] = rfc.getAnnullato();
		objRfc[11] = rfc.getDataAnnullamento();
		objRfc[12] = (flag== true ? rfc.getDataModificaRecord() : 
			rfc.getDataStoricizzazione());
		objRfc[13] = rfc.getTimespent();
		objRfc[14] = rfc.getTagAlm();
		objRfc[15] = rfc.getTsTagAlm();
		objRfc[16] = rfc.getDmalmTempoFk04();
		objRfc[17] = rfc.getRankStatoRfcMese();
		objRfc[18] = rfc.getUriRfc();
		objRfc[19] = rfc.getDescription();
		objRfc[20] = rfc.getTipologiaIntervento();
		objRfc[21] = rfc.getTipologiaDatiTrattati();
		objRfc[22] = rfc.getRichiestaIt();
		objRfc[23] = rfc.getInfrastrutturaEsistente();
		objRfc[24] = rfc.getCambiamentoRichiesto();
		objRfc[25] = rfc.getDescrizioneUtenza();
		objRfc[26] = rfc.getRequisitiDiUtilizzo();
		objRfc[27] = rfc.getModalitaGestione();
		objRfc[28] = rfc.getCriptazioneDati();
		objRfc[29] = rfc.getPoliticaArchiviazioneDati();
		objRfc[30] = rfc.getSeparazioneRuoliAccessoDati();
		objRfc[31] = rfc.getClasseDiServizio();
		objRfc[32] = rfc.getAccessoAlServizio();
		objRfc[33] = rfc.getAssistenzaUtenti();
		objRfc[34] = rfc.getAnalisiDeiRischi();
		objRfc[35] = rfc.getDescrizioneDelServizio();
		objRfc[36] = rfc.getCodSchedaServizioPolarion();
		objRfc[37] = rfc.getTitoloDellaRichiesta();
		
		return objRfc;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException { 
		return sql_type;
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		
		sql_type = typeName;
		
		idRepository = stream.readString();
		dmalmRfcPk = stream.readInt();
		stgPk = stream.readString();
		dmalmProjectFk02 = stream.readLong();
		dmalmUserFk06 = stream.readInt();
		cdRfc = stream.readString();
		dmalmStatoWorkitemFk03 = stream.readInt();
		dataModificaRecord = stream.readTimestamp();
		rankStatoRfc = stream.readInt();
		dataCambioStatoRfc = stream.readTimestamp();
		annullato = stream.readString();
		dataAnnullamento = stream.readTimestamp();
		dataStoricizzazione = stream.readTimestamp();
		timespent = stream.readFloat();
		tagAlm = stream.readString();
		tsTagAlm = stream.readTimestamp();
		dmalmTempoFk04 = stream.readInt();
		rankStatoRfcMese = stream.readInt();
		uriRfc = stream.readString();
		description = stream.readString();
		tipologiaIntervento = stream.readString();
		tipologiaDatiTrattati = stream.readString();
		richiestaIt = stream.readString();
		infrastrutturaEsistente = stream.readString();
		cambiamentoRichiesto = stream.readString();
		descrizioneUtenza = stream.readString();
		requisitiDiUtilizzo = stream.readString();
		modalitaGestione = stream.readString();
		criptazioneDati = stream.readString();
		politicaArchiviazioneDati = stream.readString();
		separazioneRuoliAccessoDati = stream.readString();
		classeDiServizio = stream.readString();
		accessoAlServizio = stream.readString();
		assistenzaUtenti = stream.readString();
		analisiDeiRischi = stream.readString();
		descrizioneDelServizio = stream.readString();
		codSchedaServizioPolarion = stream.readString();
		titoloDellaRichiesta = stream.readString();
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException { 
		stream.writeString(idRepository);
		stream.writeInt(dmalmRfcPk);
	    stream.writeString(stgPk);
		stream.writeLong(dmalmProjectFk02);
		stream.writeInt(dmalmUserFk06);
		stream.writeString(cdRfc);
		stream.writeInt(dmalmStatoWorkitemFk03);
		stream.writeTimestamp(dataModificaRecord);
		stream.writeInt(rankStatoRfc);
		stream.writeTimestamp(dataCambioStatoRfc);
		stream.writeString(annullato);
		stream.writeTimestamp(dataAnnullamento);
		stream.writeTimestamp(dataStoricizzazione);
		stream.writeFloat(timespent);
		stream.writeString(tagAlm);
		stream.writeTimestamp(tsTagAlm);
		stream.writeInt(dmalmTempoFk04);
		stream.writeInt(rankStatoRfcMese);
		stream.writeString(uriRfc);
		stream.writeString(description);
		stream.writeString(tipologiaIntervento);
		stream.writeString(tipologiaDatiTrattati);
		stream.writeString(richiestaIt);
		stream.writeString(infrastrutturaEsistente);
		stream.writeString(cambiamentoRichiesto);
		stream.writeString(descrizioneUtenza);
		stream.writeString(requisitiDiUtilizzo);
		stream.writeString(modalitaGestione);
		stream.writeString(criptazioneDati);
		stream.writeString(politicaArchiviazioneDati);
		stream.writeString(separazioneRuoliAccessoDati);
		stream.writeString(classeDiServizio);
		stream.writeString(accessoAlServizio);
		stream.writeString(assistenzaUtenti);
		stream.writeString(analisiDeiRischi);
		stream.writeString(descrizioneDelServizio);
		stream.writeString(codSchedaServizioPolarion);
		stream.writeString(titoloDellaRichiesta);
	}
	public Float getTimespent() {
		return timespent;
	}
	public void setTimespent(Float timespent) {
		this.timespent = timespent;
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
	public Integer getDmalmRfcPk() {
		return dmalmRfcPk;
	}
	public void setDmalmRfcPk(Integer dmalmRfcPk) {
		this.dmalmRfcPk = dmalmRfcPk;
	}
	public String getCdRfc() {
		return cdRfc;
	}
	public void setCdRfc(String cdRfc) {
		this.cdRfc = cdRfc;
	}
	public Integer getRankStatoRfc() {
		return rankStatoRfc;
	}
	public void setRankStatoRfc(Integer rankStatoRfc) {
		this.rankStatoRfc = rankStatoRfc;
	}
	public java.sql.Timestamp getDataCambioStatoRfc() {
		return dataCambioStatoRfc;
	}
	public void setDataCambioStatoRfc(java.sql.Timestamp dataCambioStatoRfc) {
		this.dataCambioStatoRfc = dataCambioStatoRfc;
	}
	public Integer getDmalmTempoFk04() {
		return dmalmTempoFk04;
	}
	public void setDmalmTempoFk04(Integer dmalmTempoFk04) {
		this.dmalmTempoFk04 = dmalmTempoFk04;
	}
	public Integer getRankStatoRfcMese() {
		return rankStatoRfcMese;
	}
	public void setRankStatoRfcMese(Integer rankStatoRfcMese) {
		this.rankStatoRfcMese = rankStatoRfcMese;
	}
	public String getUriRfc() {
		return uriRfc;
	}
	public void setUriRfc(String uriRfc) {
		this.uriRfc = uriRfc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}
	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}
	public String getTipologiaDatiTrattati() {
		return tipologiaDatiTrattati;
	}
	public void setTipologiaDatiTrattati(String tipologiaDatiTrattati) {
		this.tipologiaDatiTrattati = tipologiaDatiTrattati;
	}
	public String getRichiestaIt() {
		return richiestaIt;
	}
	public void setRichiestaIt(String richiestaIt) {
		this.richiestaIt = richiestaIt;
	}
	public String getInfrastrutturaEsistente() {
		return infrastrutturaEsistente;
	}
	public void setInfrastrutturaEsistente(String infrastrutturaEsistente) {
		this.infrastrutturaEsistente = infrastrutturaEsistente;
	}
	public String getCambiamentoRichiesto() {
		return cambiamentoRichiesto;
	}
	public void setCambiamentoRichiesto(String cambiamentoRichiesto) {
		this.cambiamentoRichiesto = cambiamentoRichiesto;
	}
	public String getDescrizioneUtenza() {
		return descrizioneUtenza;
	}
	public void setDescrizioneUtenza(String descrizioneUtenza) {
		this.descrizioneUtenza = descrizioneUtenza;
	}
	public String getRequisitiDiUtilizzo() {
		return requisitiDiUtilizzo;
	}
	public void setRequisitiDiUtilizzo(String requisitiDiUtilizzo) {
		this.requisitiDiUtilizzo = requisitiDiUtilizzo;
	}
	public String getModalitaGestione() {
		return modalitaGestione;
	}
	public void setModalitaGestione(String modalitaGestione) {
		this.modalitaGestione = modalitaGestione;
	}
	public String getCriptazioneDati() {
		return criptazioneDati;
	}
	public void setCriptazioneDati(String criptazioneDati) {
		this.criptazioneDati = criptazioneDati;
	}
	public String getPoliticaArchiviazioneDati() {
		return politicaArchiviazioneDati;
	}
	public void setPoliticaArchiviazioneDati(String politicaArchiviazioneDati) {
		this.politicaArchiviazioneDati = politicaArchiviazioneDati;
	}
	public String getSeparazioneRuoliAccessoDati() {
		return separazioneRuoliAccessoDati;
	}
	public void setSeparazioneRuoliAccessoDati(String separazioneRuoliAccessoDati) {
		this.separazioneRuoliAccessoDati = separazioneRuoliAccessoDati;
	}
	public String getClasseDiServizio() {
		return classeDiServizio;
	}
	public void setClasseDiServizio(String classeDiServizio) {
		this.classeDiServizio = classeDiServizio;
	}
	public String getAccessoAlServizio() {
		return accessoAlServizio;
	}
	public void setAccessoAlServizio(String accessoAlServizio) {
		this.accessoAlServizio = accessoAlServizio;
	}
	public String getAssistenzaUtenti() {
		return assistenzaUtenti;
	}
	public void setAssistenzaUtenti(String assistenzaUtenti) {
		this.assistenzaUtenti = assistenzaUtenti;
	}
	public String getAnalisiDeiRischi() {
		return analisiDeiRischi;
	}
	public void setAnalisiDeiRischi(String analisiDeiRischi) {
		this.analisiDeiRischi = analisiDeiRischi;
	}
	public String getDescrizioneDelServizio() {
		return descrizioneDelServizio;
	}
	public void setDescrizioneDelServizio(String descrizioneDelServizio) {
		this.descrizioneDelServizio = descrizioneDelServizio;
	}
	public String getCodSchedaServizioPolarion() {
		return codSchedaServizioPolarion;
	}
	public void setCodSchedaServizioPolarion(String codSchedaServizioPolarion) {
		this.codSchedaServizioPolarion = codSchedaServizioPolarion;
	}
	public String getTitoloDellaRichiesta() {
		return titoloDellaRichiesta;
	}
	public void setTitoloDellaRichiesta(String titoloDellaRichiesta) {
		this.titoloDellaRichiesta = titoloDellaRichiesta;
	}
}
