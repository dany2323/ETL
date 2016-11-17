package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryProjectgroup;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireHistoryProjectgroup is a Querydsl query type for SireHistoryProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryProjectgroup extends com.mysema.query.sql.RelationalPathBase<SireHistoryProjectgroup> {

    private static final long serialVersionUID = 2026847076;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireHistoryProjectgroup sireHistoryProjectgroup = new QSireHistoryProjectgroup("DMALM_SIRE_HISTORY_PROJGROUP");

//    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

//    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");
    
    public final NumberPath<Integer> sireHistoryProjGroupPk = createNumber("SIRE_HISTORY_PROJGROUP_PK", Integer.class);

    public QSireHistoryProjectgroup(String variable) {
        super(SireHistoryProjectgroup.class, forVariable(variable),DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJGROUP");
    }

    public QSireHistoryProjectgroup(Path<? extends SireHistoryProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJGROUP");
    }

    public QSireHistoryProjectgroup(PathMetadata<?> metadata) {
        super(SireHistoryProjectgroup.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJGROUP");
    }

}

