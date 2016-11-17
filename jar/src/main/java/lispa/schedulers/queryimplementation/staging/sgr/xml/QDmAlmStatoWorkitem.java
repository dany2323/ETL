package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.xml.DmAlmStatoWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmAlmStatoWorkitem is a Querydsl query type for DmAlmStatoWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmStatoWorkitem extends com.mysema.query.sql.RelationalPathBase<DmAlmStatoWorkitem> {

    private static final long serialVersionUID = 1545915439;

    public static final QDmAlmStatoWorkitem dmAlmStatoWorkitem = new QDmAlmStatoWorkitem("DMALM_STATO_WORKITEM_SVN");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final NumberPath<Double> dmAlmStatoWorkitemPk = createNumber("STATO_WORKITEM_PK", Double.class);

    public final StringPath iconUrl = createString("ICON_URL");
    
    public final StringPath repository = createString("REPOSITORY");
    
    public final StringPath workitemType = createString("WORKITEM_TYPE");

    public final StringPath id = createString("ID");

    public final StringPath name = createString("NAME");
    
    public final StringPath template = createString("TEMPLATE");

    public final NumberPath<Double> sortOrder = createNumber("SORT_ORDER", Double.class);

    public final com.mysema.query.sql.PrimaryKey<DmAlmStatoWorkitem> dmAlmStatoWorkitem_Pk = createPrimaryKey(dmAlmStatoWorkitemPk);

    public QDmAlmStatoWorkitem(String variable) {
        super(DmAlmStatoWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STATO_WORKITEM_SVN");
    }

    public QDmAlmStatoWorkitem(Path<? extends DmAlmStatoWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STATO_WORKITEM_SVN");
    }

    public QDmAlmStatoWorkitem(PathMetadata<?> metadata) {
        super(DmAlmStatoWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STATO_WORKITEM_SVN");
    }

}

