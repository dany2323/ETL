package lispa.schedulers.bean.fonte.sgr.xml;

import javax.annotation.Generated;

/**
 * DmalmWorkitemLinkRoles is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class DmAlmWorkitemLinkRoles {

    private String descrizione;

    private String idRuolo;

    private String nomeRuolo;

    private String nomeRuoloInverso;

    private Boolean parent;

    private String repository;

    private String templates;

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(String idRuolo) {
        this.idRuolo = idRuolo;
    }

    public String getNomeRuolo() {
        return nomeRuolo;
    }

    public void setNomeRuolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    public String getNomeRuoloInverso() {
        return nomeRuoloInverso;
    }

    public void setNomeRuoloInverso(String nomeRuoloInverso) {
        this.nomeRuoloInverso = nomeRuoloInverso;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }
}