package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryHyperlink;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmSireHistoryHyperlink is a Querydsl query type for DmalmSireHistoryHyperlink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryHyperlink extends com.mysema.query.sql.RelationalPathBase<SireHistoryHyperlink> {

    private static final long serialVersionUID = 205012439;

    public static final QSireHistoryHyperlink dmalmSireHistoryHyperlink = new QSireHistoryHyperlink("DMALM_SIRE_HISTORY_HYPERLINK");

    public final StringPath cRole = createString("C_ROLE");

    public final StringPath cUri = createString("C_URI");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final NumberPath<Long> sireHistoryHyperlinkPk = createNumber("SIRE_HISTORY_HYPERLINK_PK", Long.class);

    public final com.mysema.query.sql.PrimaryKey<SireHistoryHyperlink> sysC0021440 = createPrimaryKey(sireHistoryHyperlinkPk);

    public QSireHistoryHyperlink(String variable) {
        super(SireHistoryHyperlink.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_HYPERLINK");
    }

    public QSireHistoryHyperlink(Path<? extends SireHistoryHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_HYPERLINK");
    }

    public QSireHistoryHyperlink(PathMetadata<?> metadata) {
        super(SireHistoryHyperlink.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_HYPERLINK");
    }

}

