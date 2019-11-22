package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentCfWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentCfWorkitem is a Querydsl query type for SireCurrentCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
<<<<<<< Updated upstream:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/QSireCurrentCfWorkitem.java
public class QSireCurrentCfWorkitem extends com.mysema.query.sql.RelationalPathBase<SireCurrentCfWorkitem> {
=======
public class SireHistoryCfWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryCfWorkitem> {
>>>>>>> Stashed changes:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/history/SireHistoryCfWorkitem.java

    private static final long serialVersionUID = 688743631;

    public static final QSireCurrentCfWorkitem sireCurrentCfWorkitem = new QSireCurrentCfWorkitem("DMALM_SIRE_CURRENT_CF_WORKITEM");

    //public final BooleanPath cBooleanValue = createBoolean("C_BOOLEAN_VALUE");
    public final NumberPath<Integer> cBooleanValue = createNumber("C_BOOLEAN_VALUE", Integer.class);

    public final NumberPath<java.math.BigDecimal> cCurrencyValue = createNumber("C_CURRENCY_VALUE", java.math.BigDecimal.class);

    public final DateTimePath<java.sql.Timestamp> cDateValue = createDateTime("C_DATE_VALUE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cDateonlyValue = createDateTime("C_DATEONLY_VALUE", java.sql.Timestamp.class);

    public final NumberPath<Long> cDurationtimeValue = createNumber("C_DURATIONTIME_VALUE", Long.class);

    public final NumberPath<Float> cFloatValue = createNumber("C_FLOAT_VALUE", Float.class);

    public final NumberPath<Long> cLongValue = createNumber("C_LONG_VALUE", Long.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cStringValue = createString("C_STRING_VALUE");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");
    
    public final StringPath fkWorkitem = createString("FK_WORKITEM");

<<<<<<< Updated upstream:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/current/QSireCurrentCfWorkitem.java
    public final StringPath sireCurrentCfWorkItemPk = createString("SIRE_CURR_CF_WORKITEM_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSireCurrentCfWorkitem(String variable) {
        super(SireCurrentCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_CF_WORKITEM");
    }

    public QSireCurrentCfWorkitem(Path<? extends SireCurrentCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_CF_WORKITEM");
    }

    public QSireCurrentCfWorkitem(PathMetadata<?> metadata) {
        super(SireCurrentCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_CF_WORKITEM");
=======
    public SireHistoryCfWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_CF_WORKITEM");
    }

    public SireHistoryCfWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_CF_WORKITEM");
    }

    public SireHistoryCfWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_CF_WORKITEM");
>>>>>>> Stashed changes:jar/src/main/java/lispa/schedulers/queryimplementation/staging/sgr/sire/history/SireHistoryCfWorkitem.java
    }

}

