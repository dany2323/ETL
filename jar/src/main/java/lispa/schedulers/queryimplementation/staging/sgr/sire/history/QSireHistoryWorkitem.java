package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;


/**
 * QSireHistoryWorkitem is a Querydsl query type for SireHistoryWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryWorkitem extends com.mysema.query.sql.RelationalPathBase<SireHistoryWorkitem> {

    private static final long serialVersionUID = -128272126;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireHistoryWorkitem sireHistoryWorkitem = new QSireHistoryWorkitem("DMALM_SIRE_HISTORY_WORKITEM");

    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("C_CREATED", java.sql.Timestamp.class);

    //public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cDuedate = createDateTime("C_DUEDATE", java.sql.Timestamp.class);

    public final StringPath cId = createString("C_ID");

    public final NumberPath<Float> cInitialestimate = createNumber("C_INITIALESTIMATE", Float.class);

//    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer>  cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cOutlinenumber = createString("C_OUTLINENUMBER");

    public final StringPath cPk = createString("C_PK");

    public final DateTimePath<java.sql.Timestamp> cPlannedend = createDateTime("C_PLANNEDEND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cPlannedstart = createDateTime("C_PLANNEDSTART", java.sql.Timestamp.class);

    public final StringPath cPreviousstatus = createString("C_PREVIOUSSTATUS");

    public final StringPath cPriority = createString("C_PRIORITY");

    public final NumberPath<Float> cRemainingestimate = createNumber("C_REMAININGESTIMATE", Float.class);

    public final StringPath cResolution = createString("C_RESOLUTION");

    public final DateTimePath<java.sql.Timestamp> cResolvedon = createDateTime("C_RESOLVEDON", java.sql.Timestamp.class);

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cSeverity = createString("C_SEVERITY");

    public final StringPath cStatus = createString("C_STATUS");

    public final NumberPath<Float> cTimespent = createNumber("C_TIMESPENT", Float.class);

    public final StringPath cTitle = createString("C_TITLE");

    public final StringPath cType = createString("C_TYPE");

    public final DateTimePath<java.sql.Timestamp> cUpdated = createDateTime("C_UPDATED", java.sql.Timestamp.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkAuthor = createString("FK_AUTHOR");

    public final StringPath fkModule = createString("FK_MODULE");

    public final StringPath fkProject = createString("FK_PROJECT");

    public final StringPath fkTimepoint = createString("FK_TIMEPOINT");
    
    public final  StringPath cDescription = createString("C_DESCRIPTION");

    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final StringPath fkUriModule = createString("FK_URI_MODULE");

    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final StringPath fkUriTimepoint = createString("FK_URI_TIMEPOINT");
    
    public final StringPath dmalmHistoryWorkitemPk = createString("SIRE_HISTORY_WORKITEM_PK");
    
    public final NumberPath<Float> costoSviluppo = createNumber("COSTO_SVILUPPO", Float.class);
    
    public final StringPath codIntervento = createString("COD_INTERVENTO");
    
    public final StringPath ticketID = createString("TICKET_ID");
    
    public final NumberPath<Float> ca = createNumber("CA", Float.class);
    
    public final DateTimePath<Timestamp> stChiuso = createDateTime("ST_CHIUSO", Timestamp.class);
    
    public final StringPath codice = createString("CODICE");
    
    public final StringPath classeDoc = createString("CLASSE_DOC");
    
    public final StringPath tipoDoc = createString("TIPO_DOC");
    
    public final BooleanPath fr = createBoolean("FR");
    
    public final StringPath fornitura = createString("FORNITURA");
    
    public final StringPath versione = createString("VERSIONE");
    
    public final DatePath<Date> dataInizio = createDate("DATA_INIZIO", Date.class);
    
    public final DatePath<Date> dataDisp = createDate("DATA_DISP", Date.class);
    
    public final DatePath<Date> dataInizioEff = createDate("DATA_INIZIO_EFF", Date.class);
    
    public final StringPath aoid = createString("AOID");
    
    public final DatePath<Date> dataFine = createDate("DATA_FINE", Date.class);

    public final DatePath<Date> dataDispEff = createDate("DATA_DISPONIBILITA_EFFETTIVA", Date.class);
    
    public final DatePath<Date> dataFineEff = createDate("DATA_FINE_EFFETTIVA", Date.class);
    
    public final StringPath version = createString("VERSION");
    
    public QSireHistoryWorkitem(String variable) {
        super(SireHistoryWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORKITEM");
    }

    public QSireHistoryWorkitem(Path<? extends SireHistoryWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORKITEM");
    }

    public QSireHistoryWorkitem(PathMetadata<?> metadata) {
        super(SireHistoryWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORKITEM");
    }

}

