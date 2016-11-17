package lispa.schedulers.queryimplementation.fonte.sgr.siss.history;

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
public class SissHistoryHyperlink extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryHyperlink> {

    private static final long serialVersionUID = -67527503;

    public static final SissHistoryHyperlink structWorkitemHyperlinks = new SissHistoryHyperlink("STRUCT_WORKITEM_HYPERLINKS");

    public final StringPath cRole = createString("C_ROLE");

    public final StringPath cUrl = createString("C_URL");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public SissHistoryHyperlink(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryHyperlink.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

    public SissHistoryHyperlink(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

    public SissHistoryHyperlink(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryHyperlink.class, metadata, DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_HYPERLINKS");
    }

}

