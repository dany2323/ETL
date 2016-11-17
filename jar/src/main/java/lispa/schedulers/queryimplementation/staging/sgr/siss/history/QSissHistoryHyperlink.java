package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryHyperlink;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmSissHistoryHyperlink is a Querydsl query type for DmalmSissHistoryHyperlink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryHyperlink extends com.mysema.query.sql.RelationalPathBase<SissHistoryHyperlink> {

    private static final long serialVersionUID = 1311635697;

    public static final QSissHistoryHyperlink dmalmSissHistoryHyperlink = new QSissHistoryHyperlink("DMALM_SISS_HISTORY_HYPERLINK");

    public final StringPath cRole = createString("C_ROLE");

    public final StringPath cUri = createString("C_URI");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final NumberPath<Long> sissHistoryHyperlinkPk = createNumber("SISS_HISTORY_HYPERLINK_PK", Long.class);

    public final com.mysema.query.sql.PrimaryKey<SissHistoryHyperlink> sysC0021441 = createPrimaryKey(sissHistoryHyperlinkPk);

    public QSissHistoryHyperlink(String variable) {
        super(SissHistoryHyperlink.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_HYPERLINK");
    }

    public QSissHistoryHyperlink(Path<? extends SissHistoryHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_HYPERLINK");
    }

    public QSissHistoryHyperlink(PathMetadata<?> metadata) {
        super(SissHistoryHyperlink.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_HYPERLINK");
    }

}

