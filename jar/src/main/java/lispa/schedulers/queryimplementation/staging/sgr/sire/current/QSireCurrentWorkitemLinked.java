package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentWorkitemLinked;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentWorkitemLinked is a Querydsl query type for SireCurrentWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
<<<<<<< Updated upstream:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/QSireCurrentWorkitemLinked.java
public class QSireCurrentWorkitemLinked extends com.mysema.query.sql.RelationalPathBase<SireCurrentWorkitemLinked> {
=======
public class SireCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems> {
>>>>>>> Stashed changes:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/SireCurrentStructWorkitemLinkedworkitems.java

    private static final long serialVersionUID = 1317911173;

    public static final QSireCurrentWorkitemLinked sireCurrentWorkitemLinked = new QSireCurrentWorkitemLinked("DMALM_SIRE_CURRENT_WORK_LINKED");

    public final StringPath cRevision = createString("C_REVISION");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath cRole = createString("C_ROLE");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmCurrentWorkitemLinkedPk = createString("SIRE_CURRENT_WORK_LINKED_PK");


<<<<<<< Updated upstream:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/QSireCurrentWorkitemLinked.java
    public QSireCurrentWorkitemLinked(String variable) {
        super(SireCurrentWorkitemLinked.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORK_LINKED");
    }

    public QSireCurrentWorkitemLinked(Path<? extends SireCurrentWorkitemLinked> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORK_LINKED");
    }

    public QSireCurrentWorkitemLinked(PathMetadata<?> metadata) {
        super(SireCurrentWorkitemLinked.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORK_LINKED");
=======
    public SireCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
>>>>>>> Stashed changes:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/SireCurrentStructWorkitemLinkedworkitems.java
    }

}

