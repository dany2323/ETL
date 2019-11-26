package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;


/**
 * SissHistoryCfWorkitem is a Querydsl query type for SissHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryCfWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryCfWorkitem> {

    private static final long serialVersionUID = -917026934;

    public static final SissHistoryCfWorkitem cfWorkitem = new SissHistoryCfWorkitem("DM_ALM_H_SISS_CF_WORKITEM");

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

    public SissHistoryCfWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_CF_WORKITEM");
    }

    public SissHistoryCfWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_CF_WORKITEM");
    }

    public SissHistoryCfWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_CF_WORKITEM");
    }

}

