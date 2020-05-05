package lispa.schedulers.queryimplementation.fonte.sgr.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QStructWorkitemHyperlinks is a Querydsl query type for StructWorkitemHyperlinks
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class HistoryHyperlink extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.history.HistoryHyperlink> {

    private static final long serialVersionUID = -42223250;

    public static final HistoryHyperlink structWorkitemHyperlinks = new HistoryHyperlink("STRUCT_WORKITEM_HYPERLINKS");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");
    
    public final StringPath cRole = createString("C_ROLE");

    public final StringPath cUrl = createString("C_URL");

    public HistoryHyperlink(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryHyperlink.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

    public HistoryHyperlink(Path<? extends lispa.schedulers.bean.fonte.sgr.history.HistoryHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

    public HistoryHyperlink(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryHyperlink.class, metadata, DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

}

