package lispa.schedulers.queryimplementation.fonte.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryCfWorkitem is a Querydsl query type for SireHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryCfWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryCfWorkitem> {

    private static final long serialVersionUID = -1899539286;

    public static final SireHistoryCfWorkitem cfWorkitem = new SireHistoryCfWorkitem("CF_WORKITEM");

    public final BooleanPath cBooleanValue = createBoolean("C_BOOLEAN_VALUE");

    public final NumberPath<java.math.BigDecimal> cCurrencyValue = createNumber("C_CURRENCY_VALUE", java.math.BigDecimal.class);

    public final DateTimePath<java.sql.Timestamp> cDateValue = createDateTime("C_DATE_VALUE", java.sql.Timestamp.class);

    public final DatePath<java.sql.Date> cDateonlyValue = createDate("C_DATEONLY_VALUE", java.sql.Date.class);

    public final NumberPath<Float> cDurationtimeValue = createNumber("C_DURATIONTIME_VALUE", Float.class);

    public final NumberPath<Float> cFloatValue = createNumber("C_FLOAT_VALUE", Float.class);

    public final NumberPath<Long> cLongValue = createNumber("C_LONG_VALUE", Long.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cStringValue = createString("C_STRING_VALUE");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public SireHistoryCfWorkitem(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryCfWorkitem.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "CF_WORKITEM");
    }

    public SireHistoryCfWorkitem(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "CF_WORKITEM");
    }

    public SireHistoryCfWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryCfWorkitem.class, metadata, DmAlmConstants.POLARION_SCHEMA, "CF_WORKITEM");
    }

}

