package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QDmalmSissHistoryAttachment is a Querydsl query type for DmalmSissHistoryAttachment
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryAttachment extends com.mysema.query.sql.RelationalPathBase<SissHistoryAttachment> {

    private static final long serialVersionUID = -754316888;

    public static final QSissHistoryAttachment dmalmSissHistoryAttachment = new QSissHistoryAttachment("DMALM_SISS_HISTORY_ATTACHMENT");

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

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath fkAuthor = createString("FK_AUTHOR");

    public final StringPath fkProject = createString("FK_PROJECT");

    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public final NumberPath<Long> sissHistoryAttachmentPk = createNumber("SISS_HISTORY_ATTACHMENT_PK", Long.class);

    public final com.mysema.query.sql.PrimaryKey<SissHistoryAttachment> sysC0021439 = createPrimaryKey(sissHistoryAttachmentPk);

    public QSissHistoryAttachment(String variable) {
        super(SissHistoryAttachment.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_ATTACHMENT");
    }

    public QSissHistoryAttachment(Path<? extends SissHistoryAttachment> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_ATTACHMENT");
    }

    public QSissHistoryAttachment(PathMetadata<?> metadata) {
        super(SissHistoryAttachment.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_ATTACHMENT");
    }

}

