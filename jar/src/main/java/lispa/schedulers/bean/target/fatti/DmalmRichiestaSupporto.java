package lispa.schedulers.bean.target.fatti;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class DmalmRichiestaSupporto implements SQLData {

	private static final Integer COUNT_COLUMN = 23;
	private String sql_type;
	private String idRepository;
	private String uriRichiestaSupporto;
	private Integer dmalmRichiestaSupportoPk;
	private String stgPk;
	private Integer dmalmProjectFk02;
	private Integer dmalmUserFk06;
	private String cdRichiestaSupporto;
	private java.sql.Timestamp dataRisoluzioneRichSupporto;
	private Integer nrGiorniFestivi;
	private Integer tempoTotRichSupporto;
	private Integer dmalmStatoWorkitemFk03;
	private java.sql.Timestamp dataCreazRichSupporto;
	private java.sql.Timestamp dataModificaRecord;
	private java.sql.Timestamp dataChiusRichSupporto;
	private String useridRichSupporto;
	private String nomeRichSupporto;
	private String motivoRisoluzione;
	private String severityRichSupporto;
	private String descrizioneRichSupporto;
	private String numeroTestataRdi;
	private Integer rankStatoRichSupporto;
	private java.sql.Timestamp dataDisponibilita;
	private String priorityRichSupporto;
	
	public DmalmRichiestaSupporto() {
		
	}
	
	public DmalmRichiestaSupporto(String sql_type, DmalmRichiestaSupporto richiesta) {
		
		this.sql_type = sql_type;
		this.idRepository = richiesta.getIdRepository();
		this.uriRichiestaSupporto = richiesta.getUriRichiestaSupporto();
		this.dmalmRichiestaSupportoPk = richiesta.getDmalmRichiestaSupportoPk();
		this.stgPk = richiesta.getStgPk();
		this.dmalmProjectFk02 = richiesta.getDmalmProjectFk02();
		this.dmalmUserFk06 = richiesta.getDmalmUserFk06();
		this.cdRichiestaSupporto = richiesta.getCdRichiestaSupporto();
		this.dataRisoluzioneRichSupporto = richiesta.getDataRisoluzioneRichSupporto();
		this.nrGiorniFestivi = richiesta.getNrGiorniFestivi();
		this.tempoTotRichSupporto = richiesta.getTempoTotRichSupporto();
		this.dmalmStatoWorkitemFk03 = richiesta.getDmalmStatoWorkitemFk03();
		this.dataCreazRichSupporto = richiesta.getDataCreazRichSupporto();
		this.dataModificaRecord = richiesta.getDataModificaRecord();
		this.dataChiusRichSupporto = richiesta.getDataChiusRichSupporto();
		this.useridRichSupporto = richiesta.getUseridRichSupporto();
		this.nomeRichSupporto = richiesta.getNomeRichSupporto();
		this.motivoRisoluzione = richiesta.getMotivoRisoluzione();
		this.severityRichSupporto = richiesta.getSeverityRichSupporto();
		this.descrizioneRichSupporto = richiesta.getDescrizioneRichSupporto();
		this.numeroTestataRdi = richiesta.getNumeroTestataRdi();
		this.rankStatoRichSupporto = richiesta.getRankStatoRichSupporto();
		this.dataDisponibilita = richiesta.getDataDisponibilita();
		this.priorityRichSupporto = richiesta.getPriorityRichSupporto();
	}
	
	public DmalmRichiestaSupporto(String sql_type, String idRepository, String uriRichiestaSupporto,
			Integer dmalmRichiestaSupportoPk, String stgPk, Integer dmalmProjectFk02,
			Integer dmalmUserFk06, String cdRichiestaSupporto, java.sql.Timestamp dataRisoluzioneRichSupporto,
			Integer nrGiorniFestivi, Integer tempoTotRichSupporto, Integer dmalmStatoWorkitemFk03,
			java.sql.Timestamp dataCreazRichSupporto, java.sql.Timestamp dataModificaRecord,
			java.sql.Timestamp dataChiusRichSupporto, String useridRichSupporto,	String nomeRichSupporto,
			String motivoRisoluzione, String severityRichSupporto, String descrizioneRichSupporto,
			String numeroTestataRdi, Integer rankStatoRichSupporto, java.sql.Timestamp dataDisponibilita,
			String priorityRichSupporto) {
		
		this.sql_type = sql_type;
		this.idRepository = idRepository;
		this.uriRichiestaSupporto = uriRichiestaSupporto;
		this.dmalmRichiestaSupportoPk = dmalmRichiestaSupportoPk;
		this.stgPk = stgPk;
		this.dmalmProjectFk02 = dmalmProjectFk02;
		this.dmalmUserFk06 = dmalmUserFk06;
		this.cdRichiestaSupporto = cdRichiestaSupporto;
		this.dataRisoluzioneRichSupporto = dataRisoluzioneRichSupporto;
		this.nrGiorniFestivi = nrGiorniFestivi;
		this.tempoTotRichSupporto = tempoTotRichSupporto;
		this.dmalmStatoWorkitemFk03 = dmalmStatoWorkitemFk03;
		this.dataCreazRichSupporto = dataCreazRichSupporto;
		this.dataModificaRecord = dataModificaRecord;
		this.dataChiusRichSupporto = dataChiusRichSupporto;
		this.useridRichSupporto = useridRichSupporto;
		this.nomeRichSupporto = nomeRichSupporto;
		this.motivoRisoluzione = motivoRisoluzione;
		this.severityRichSupporto = severityRichSupporto;
		this.descrizioneRichSupporto = descrizioneRichSupporto;
		this.numeroTestataRdi = numeroTestataRdi;
		this.rankStatoRichSupporto = rankStatoRichSupporto;
		this.dataDisponibilita = dataDisponibilita;
		this.priorityRichSupporto = priorityRichSupporto;
	}
	
	public String getIdRepository() {
		return idRepository;
	}
	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}
	public String getUriRichiestaSupporto() {
		return uriRichiestaSupporto;
	}
	public void setUriRichiestaSupporto(String uriRichiestaSupporto) {
		this.uriRichiestaSupporto = uriRichiestaSupporto;
	}
	public Integer getDmalmRichiestaSupportoPk() {
		return dmalmRichiestaSupportoPk;
	}
	public void setDmalmRichiestaSupportoPk(Integer dmalmRichiestaSupportoPk) {
		this.dmalmRichiestaSupportoPk = dmalmRichiestaSupportoPk;
	}
	public String getStgPk() {
		return stgPk;
	}
	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}
	public Integer getDmalmProjectFk02() {
		return dmalmProjectFk02;
	}
	public void setDmalmProjectFk02(Integer dmalmProjectFk02) {
		this.dmalmProjectFk02 = dmalmProjectFk02;
	}
	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}
	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}
	public String getCdRichiestaSupporto() {
		return cdRichiestaSupporto;
	}
	public void setCdRichiestaSupporto(String cdRichiestaSupporto) {
		this.cdRichiestaSupporto = cdRichiestaSupporto;
	}
	public java.sql.Timestamp getDataRisoluzioneRichSupporto() {
		return dataRisoluzioneRichSupporto;
	}
	public void setDataRisoluzioneRichSupporto(java.sql.Timestamp dataRisoluzioneRichSupporto) {
		this.dataRisoluzioneRichSupporto = dataRisoluzioneRichSupporto;
	}
	public Integer getNrGiorniFestivi() {
		return nrGiorniFestivi;
	}
	public void setNrGiorniFestivi(Integer nrGiorniFestivi) {
		this.nrGiorniFestivi = nrGiorniFestivi;
	}
	public Integer getTempoTotRichSupporto() {
		return tempoTotRichSupporto;
	}
	public void setTempoTotRichSupporto(Integer tempoTotRichSupporto) {
		this.tempoTotRichSupporto = tempoTotRichSupporto;
	}
	public Integer getDmalmStatoWorkitemFk03() {
		return dmalmStatoWorkitemFk03;
	}
	public void setDmalmStatoWorkitemFk03(Integer dmalmStatoWorkitemFk03) {
		this.dmalmStatoWorkitemFk03 = dmalmStatoWorkitemFk03;
	}
	public java.sql.Timestamp getDataCreazRichSupporto() {
		return dataCreazRichSupporto;
	}
	public void setDataCreazRichSupporto(java.sql.Timestamp dataCreazRichSupporto) {
		this.dataCreazRichSupporto = dataCreazRichSupporto;
	}
	public java.sql.Timestamp getDataModificaRecord() {
		return dataModificaRecord;
	}
	public void setDataModificaRecord(java.sql.Timestamp dataModificaRecord) {
		this.dataModificaRecord = dataModificaRecord;
	}
	public java.sql.Timestamp getDataChiusRichSupporto() {
		return dataChiusRichSupporto;
	}
	public void setDataChiusRichSupporto(java.sql.Timestamp dataChiusRichSupporto) {
		this.dataChiusRichSupporto = dataChiusRichSupporto;
	}
	public String getUseridRichSupporto() {
		return useridRichSupporto;
	}
	public void setUseridRichSupporto(String useridRichSupporto) {
		this.useridRichSupporto = useridRichSupporto;
	}
	public String getNomeRichSupporto() {
		return nomeRichSupporto;
	}
	public void setNomeRichSupporto(String nomeRichSupporto) {
		this.nomeRichSupporto = nomeRichSupporto;
	}
	public String getMotivoRisoluzione() {
		return motivoRisoluzione;
	}
	public void setMotivoRisoluzione(String motivoRisoluzione) {
		this.motivoRisoluzione = motivoRisoluzione;
	}
	public String getSeverityRichSupporto() {
		return severityRichSupporto;
	}
	public void setSeverityRichSupporto(String severityRichSupporto) {
		this.severityRichSupporto = severityRichSupporto;
	}
	public String getDescrizioneRichSupporto() {
		return descrizioneRichSupporto;
	}
	public void setDescrizioneRichSupporto(String descrizioneRichSupporto) {
		this.descrizioneRichSupporto = descrizioneRichSupporto;
	}
	public String getNumeroTestataRdi() {
		return numeroTestataRdi;
	}
	public void setNumeroTestataRdi(String numeroTestataRdi) {
		this.numeroTestataRdi = numeroTestataRdi;
	}
	public Integer getRankStatoRichSupporto() {
		return rankStatoRichSupporto;
	}
	public void setRankStatoRichSupporto(Integer rankStatoRichSupporto) {
		this.rankStatoRichSupporto = rankStatoRichSupporto;
	}
	public java.sql.Timestamp getDataDisponibilita() {
		return dataDisponibilita;
	}
	public void setDataDisponibilita(java.sql.Timestamp dataDisponibilita) {
		this.dataDisponibilita = dataDisponibilita;
	}
	public String getPriorityRichSupporto() {
		return priorityRichSupporto;
	}
	public void setPriorityRichSupporto(String priorityRichSupporto) {
		this.priorityRichSupporto = priorityRichSupporto;
	}
	
	public Object[] getObject(DmalmRichiestaSupporto richiesta) {
		
		Object[] objRichSupp = new Object[COUNT_COLUMN];
		objRichSupp[0] = ;
		objRichSupp[1] = ;
		objRichSupp[2] = ; 
		objRichSupp[3] = ; 
		objRichSupp[4] = ; 
		objRichSupp[5] = ; 
		objRichSupp[6] = ; 
		objRichSupp[7] = ;
		objRichSupp[8] = ;
		objRichSupp[9] = ;
		objRichSupp[10] = ;
		objRichSupp[11] = ;
		objRichSupp[12] = ;
		objRichSupp[13] = ;
		objRichSupp[14] = ;
		objRichSupp[15] = ;
		objRichSupp[16] = ;
		objRichSupp[17] = ;
		objRichSupp[18] = ;
		objRichSupp[19] = ;
		objRichSupp[20] = ;
		objRichSupp[21] = ;
		objRichSupp[22] = ;
		objRichSupp[23] = ;
		
		return objRichSupp;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException { 
		return sql_type;
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		
		sql_type = typeName;
		
		idRepository = stream.readString();
		uriRichiestaSupporto = stream.readString();
		dmalmRichiestaSupportoPk = stream.readInt();
		stgPk = stream.readString();
		dmalmProjectFk02 = stream.readInt();
		dmalmUserFk06 = stream.readInt();
		cdRichiestaSupporto = stream.readString();
		dataRisoluzioneRichSupporto = stream.readTimestamp();
		nrGiorniFestivi = stream.readInt();
		tempoTotRichSupporto = stream.readInt();
		dmalmStatoWorkitemFk03 = stream.readInt();
		dataCreazRichSupporto = stream.readTimestamp();
		dataModificaRecord = stream.readTimestamp();
		dataChiusRichSupporto = stream.readTimestamp();
		useridRichSupporto = stream.readString();
		nomeRichSupporto = stream.readString();
		motivoRisoluzione = stream.readString();
		severityRichSupporto = stream.readString();
		descrizioneRichSupporto = stream.readString();
		numeroTestataRdi = stream.readString();
		rankStatoRichSupporto = stream.readInt();
		dataDisponibilita = stream.readTimestamp();
		priorityRichSupporto = stream.readString();
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException { 

		stream.writeString(idRepository);
	    stream.writeString(uriRichiestaSupporto);
	    stream.writeInt(dmalmRichiestaSupportoPk);
		stream.writeString(stgPk);
		stream.writeInt(dmalmProjectFk02);
		stream.writeInt(dmalmUserFk06);
		stream.writeString(cdRichiestaSupporto);
		stream.writeTimestamp(dataRisoluzioneRichSupporto);
		stream.writeInt(nrGiorniFestivi);
		stream.writeInt(tempoTotRichSupporto);
		stream.writeInt(dmalmStatoWorkitemFk03);
		stream.writeTimestamp(dataCreazRichSupporto);
		stream.writeTimestamp(dataModificaRecord);
		stream.writeTimestamp(dataChiusRichSupporto);
		stream.writeString(useridRichSupporto);
		stream.writeString(nomeRichSupporto);
		stream.writeString(motivoRisoluzione);
		stream.writeString(severityRichSupporto);
		stream.writeString(descrizioneRichSupporto);
		stream.writeString(numeroTestataRdi);
		stream.writeInt(rankStatoRichSupporto);
		stream.writeTimestamp(dataDisponibilita);
		stream.writeString(priorityRichSupporto);
	}
}
