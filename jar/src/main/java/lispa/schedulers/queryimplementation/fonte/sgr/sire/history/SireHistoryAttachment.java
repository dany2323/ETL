package lispa.schedulers.queryimplementation.fonte.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QAttachment is a Querydsl query type for Attachment
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryAttachment extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment> {

    private static final long serialVersionUID = -1678920117;

    public static final SireHistoryAttachment attachment = new SireHistoryAttachment("ATTACHMENT");

    public final SimplePath<java.sql.Blob> cContent = createSimple("C_CONTENT", java.sql.Blob.class);

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final StringPath cFilename = createString("C_FILENAME");

    public final StringPath cId = createString("C_ID");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final NumberPath<Long> cLength = createNumber("C_LENGTH", Long.class);

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cTitle = createString("C_TITLE");

    public final DateTimePath<java.sql.Timestamp> cUpdated = createDateTime("C_UPDATED", java.sql.Timestamp.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath cUrl = createString("C_URL");

    public final StringPath fkAuthor = createString("FK_AUTHOR");

    public final StringPath fkProject = createString("FK_PROJECT");

    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public final com.mysema.query.sql.PrimaryKey<lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment> constraintA = createPrimaryKey(cPk);

    public SireHistoryAttachment(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "ATTACHMENT");
    }

    public SireHistoryAttachment(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "ATTACHMENT");
    }

    public SireHistoryAttachment(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment.class, metadata, DmAlmConstants.POLARION_SCHEMA, "ATTACHMENT");
    }

}

