package lispa.schedulers.bean.target.fatti;

import javax.annotation.Generated;
import lispa.schedulers.dao.UtilsDAO;

/**
 * DmalmTestcase is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmChecklist {
	
	private static final Integer COUNT_COLUMN = 25;
    private String cdChecklist;
    private String dsChecklist;
    private Integer dmalmAreaTematicaFk05;
    private Integer dmalmProjectFk02;
    private Integer dmalmStatoWorkitemFk03;
    private Integer dmalmStrutturaOrgFk01;
    private Integer dmalmTempoFk04;
    private Integer dmalmUserFk06;
    private Integer dmalmChecklistPk;
    private java.sql.Timestamp dtCambioStatoChecklist;
    private java.sql.Timestamp dtCaricamentoChecklist;
    private java.sql.Timestamp dtCreazioneChecklist;
    private java.sql.Timestamp dtModificaChecklist;
    private java.sql.Timestamp dtRisoluzioneChecklist;
    private java.sql.Timestamp dtChiusuraChecklist;
    private java.sql.Timestamp dtStoricizzazione;
    private String idAutoreChecklist;
    private String dsAutoreChecklist;
    private String idRepository;
    private Integer rankStatoChecklist;
    private Integer rankStatoChecklistMese;
    private String stgPk;
    private String uri;
    private String changed;
    private String annullato;
	private java.sql.Timestamp dtAnnullamento;

	public String getCdChecklist() {
		return cdChecklist;
	}

	public void setCdChecklist(String cdChecklist) {
		this.cdChecklist = cdChecklist;
	}

	public String getDsChecklist() {
		return dsChecklist;
	}

	public void setDsChecklist(String dsChecklist) {
		this.dsChecklist = dsChecklist;
	}

	public Integer getDmalmAreaTematicaFk05() {
		return dmalmAreaTematicaFk05;
	}

	public void setDmalmAreaTematicaFk05(Integer dmalmAreaTematicaFk05) {
		this.dmalmAreaTematicaFk05 = dmalmAreaTematicaFk05;
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

	public Integer getDmalmUserFk06() {
		return dmalmUserFk06;
	}

	public void setDmalmUserFk06(Integer dmalmUserFk06) {
		this.dmalmUserFk06 = dmalmUserFk06;
	}

	public Integer getDmalmChecklistPk() {
		return dmalmChecklistPk;
	}

	public void setDmalmChecklistPk(Integer dmalmChecklistPk) {
		this.dmalmChecklistPk = dmalmChecklistPk;
	}

	public java.sql.Timestamp getDtCambioStatoChecklist() {
		return dtCambioStatoChecklist;
	}

	public void setDtCambioStatoChecklist(java.sql.Timestamp dtCambioStatoChecklist) {
		this.dtCambioStatoChecklist = dtCambioStatoChecklist;
	}

	public java.sql.Timestamp getDtCaricamentoChecklist() {
		return dtCaricamentoChecklist;
	}

	public void setDtCaricamentoChecklist(java.sql.Timestamp dtCaricamentoChecklist) {
		this.dtCaricamentoChecklist = dtCaricamentoChecklist;
	}

	public java.sql.Timestamp getDtCreazioneChecklist() {
		return dtCreazioneChecklist;
	}

	public void setDtCreazioneChecklist(java.sql.Timestamp dtCreazioneChecklist) {
		this.dtCreazioneChecklist = dtCreazioneChecklist;
	}

	public java.sql.Timestamp getDtModificaChecklist() {
		return dtModificaChecklist;
	}

	public void setDtModificaChecklist(java.sql.Timestamp dtModificaChecklist) {
		this.dtModificaChecklist = dtModificaChecklist;
	}

	public java.sql.Timestamp getDtRisoluzioneChecklist() {
		return dtRisoluzioneChecklist;
	}

	public void setDtRisoluzioneChecklist(java.sql.Timestamp dtRisoluzioneChecklist) {
		this.dtRisoluzioneChecklist = dtRisoluzioneChecklist;
	}

	public java.sql.Timestamp getDtChiusuraChecklist() {
		return dtChiusuraChecklist;
	}

	public void setDtChiusuraChecklist(java.sql.Timestamp dtChiusuraChecklist) {
		this.dtChiusuraChecklist = dtChiusuraChecklist;
	}

	public java.sql.Timestamp getDtStoricizzazione() {
		return dtStoricizzazione;
	}

	public void setDtStoricizzazione(java.sql.Timestamp dtStoricizzazione) {
		this.dtStoricizzazione = dtStoricizzazione;
	}

	public String getIdAutoreChecklist() {
		return idAutoreChecklist;
	}

	public void setIdAutoreChecklist(String idAutoreChecklist) {
		this.idAutoreChecklist = idAutoreChecklist;
	}

	public String getDsAutoreChecklist() {
		return dsAutoreChecklist;
	}

	public void setDsAutoreChecklist(String dsAutoreChecklist) {
		this.dsAutoreChecklist = dsAutoreChecklist;
	}

	public String getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(String idRepository) {
		this.idRepository = idRepository;
	}

	public Integer getRankStatoChecklist() {
		return rankStatoChecklist;
	}

	public void setRankStatoChecklist(Integer rankStatoChecklist) {
		this.rankStatoChecklist = rankStatoChecklist;
	}

	public Integer getRankStatoChecklistMese() {
		return rankStatoChecklistMese;
	}

	public void setRankStatoChecklistMese(Integer rankStatoChecklistMese) {
		this.rankStatoChecklistMese = rankStatoChecklistMese;
	}

	public String getStgPk() {
		return stgPk;
	}

	public void setStgPk(String stgPk) {
		this.stgPk = stgPk;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public String getAnnullato() {
		return annullato;
	}

	public void setAnnullato(String annullato) {
		this.annullato = annullato;
	}

	public java.sql.Timestamp getDtAnnullamento() {
		return dtAnnullamento;
	}

	public void setDtAnnullamento(java.sql.Timestamp dtAnnullamento) {
		this.dtAnnullamento = dtAnnullamento;
	}
	
	public Object[] getObject(DmalmChecklist checklist, boolean flag) throws Exception {
		
		Integer seqId = UtilsDAO.getSequenceNextval("HISTORY_WORKITEM_SEQ.nextval");
		
		Object[] objChecklist = new Object[COUNT_COLUMN];
		objChecklist[0] = (flag == true ? checklist.getDmalmChecklistPk() : seqId);
		objChecklist[1] = checklist.getDmalmStrutturaOrgFk01();
		objChecklist[2] = checklist.getDmalmProjectFk02();
		objChecklist[3] = checklist.getDmalmStatoWorkitemFk03();
		objChecklist[4] = checklist.getDmalmTempoFk04();
		objChecklist[5] = checklist.getDmalmAreaTematicaFk05();
		objChecklist[6] = checklist.getCdChecklist();
		objChecklist[7] = checklist.getDsChecklist();
		objChecklist[8] = checklist.getIdRepository();
		objChecklist[9] = checklist.getDtCreazioneChecklist();
		objChecklist[10] = checklist.getDtRisoluzioneChecklist();
		objChecklist[11] = checklist.getDtChiusuraChecklist();
		objChecklist[12] = checklist.getDtModificaChecklist();
		objChecklist[13] = checklist.getDtCambioStatoChecklist();
		objChecklist[14] = checklist.getIdAutoreChecklist();
		objChecklist[15] = checklist.getDsAutoreChecklist();
		objChecklist[16] = checklist.getDmalmUserFk06();
		objChecklist[17] = (flag== true ? checklist.getDtModificaChecklist() : checklist.getDtStoricizzazione());
		objChecklist[18] = (flag == true ? new Short("1") : checklist.getRankStatoChecklist());
		objChecklist[19] = checklist.getRankStatoChecklistMese();
		objChecklist[20] = checklist.getStgPk();
		objChecklist[21] = checklist.getUri();
		objChecklist[22] = checklist.getChanged();
		objChecklist[23] = checklist.getAnnullato();
		objChecklist[24] = checklist.getDtAnnullamento();
		
		return objChecklist;
	}
}

