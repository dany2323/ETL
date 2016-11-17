package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryAttachment;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QDmalmSireHistoryAttachment is a Querydsl query type for DmalmSireHistoryAttachment
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryAttachment extends com.mysema.query.sql.RelationalPathBase<SireHistoryAttachment> {

    private static final long serialVersionUID = -699899518;

    public static final QSireHistoryAttachment dmalmSireHistoryAttachment = new QSireHistoryAttachment("DMALM_SIRE_HISTORY_ATTACHMENT");

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

    public final NumberPath<Long> sireHistoryAttachmentPk = createNumber("SIRE_HISTORY_ATTACHMENT_PK", Long.class);

    public final com.mysema.query.sql.PrimaryKey<SireHistoryAttachment> sysC0021438 = createPrimaryKey(sireHistoryAttachmentPk);

    public QSireHistoryAttachment(String variable) {
        super(SireHistoryAttachment.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_ATTACHMENT");
    }

    public QSireHistoryAttachment(Path<? extends SireHistoryAttachment> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_ATTACHMENT");
    }

    public QSireHistoryAttachment(PathMetadata<?> metadata) {
        super(SireHistoryAttachment.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_ATTACHMENT");
    }

}

