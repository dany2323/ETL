package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryCfWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireHistoryCfWorkitem is a Querydsl query type for SireHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryCfWorkitem extends com.mysema.query.sql.RelationalPathBase<SireHistoryCfWorkitem> {

    private static final long serialVersionUID = -299649275;

    public static final QSireHistoryCfWorkitem sireHistoryCfWorkitem = new QSireHistoryCfWorkitem("DMALM_SIRE_HISTORY_CF_WORKITEM");

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
    
    public final StringPath dmalmHistoryCfWorkItemPk = createString("SIRE_HISTORY_CF_WORKITEM_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSireHistoryCfWorkitem(String variable) {
        super(SireHistoryCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_CF_WORKITEM");
    }

    public QSireHistoryCfWorkitem(Path<? extends SireHistoryCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_CF_WORKITEM");
    }

    public QSireHistoryCfWorkitem(PathMetadata<?> metadata) {
        super(SireHistoryCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_CF_WORKITEM");
    }

}

