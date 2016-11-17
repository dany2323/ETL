package lispa.schedulers.bean.target;

import javax.annotation.Generated;

/**
 * DmalmTempo is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmalmTempo {

    private String anno;

    private Integer dmalmTempoPk;

    private String dsGiorno;

    private String dsMese;

    private java.sql.Timestamp dtOsservazione;

    private Boolean flFestivo;

    private Byte giorno;

    private String mese;

    private String semestre;

    private String trimestre;

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public Integer getDmalmTempoPk() {
        return dmalmTempoPk;
    }

    public void setDmalmTempoPk(Integer dmalmTempoPk) {
        this.dmalmTempoPk = dmalmTempoPk;
    }

    public String getDsGiorno() {
        return dsGiorno;
    }

    public void setDsGiorno(String dsGiorno) {
        this.dsGiorno = dsGiorno;
    }

    public String getDsMese() {
        return dsMese;
    }

    public void setDsMese(String dsMese) {
        this.dsMese = dsMese;
    }

    public java.sql.Timestamp getDtOsservazione() {
        return dtOsservazione;
    }

    public void setDtOsservazione(java.sql.Timestamp dtOsservazione) {
        this.dtOsservazione = dtOsservazione;
    }

    public Boolean getFlFestivo() {
        return flFestivo;
    }

    public void setFlFestivo(Boolean flFestivo) {
        this.flFestivo = flFestivo;
    }

    public Byte getGiorno() {
        return giorno;
    }

    public void setGiorno(Byte giorno) {
        this.giorno = giorno;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }

}

