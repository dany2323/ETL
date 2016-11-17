package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.mps.DmalmMpsAttivita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

/**
 * QDmalmMpsAttivita is a Querydsl query type for DmalmMpsAttivita
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")

public class QDmalmMpsAttivita extends com.mysema.query.sql.RelationalPathBase<DmalmMpsAttivita> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -779633059269064092L;

	public static final QDmalmMpsAttivita dmalmMpsAttivita = new QDmalmMpsAttivita("MPS_BO_ATTIVITA");

    public final NumberPath<Integer> idAttivitaPadre = createNumber("IDATTIVITAPADRE", Integer.class);
    
    public final NumberPath<Integer> idAttivita = createNumber("IDATTIVITA", Integer.class);
    
    public final NumberPath<Integer> idContratto = createNumber("IDCONTRATTO", Integer.class);
    
    public final StringPath codAttivita = createString("CODATTIVITA");
    
    public final StringPath titolo = createString("TITOLO");
    
    public final StringPath desAttivita = createString("DESATTIVITA");
    
    public final DateTimePath<java.sql.Timestamp> dataInizio = createDateTime("DATA_INIZIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFine = createDateTime("DATA_FINE", java.sql.Timestamp.class);
    
    public final NumberPath<Integer> avanzamento = createNumber("AVANZAMENTO", Integer.class);
    
    public final DateTimePath<java.sql.Timestamp> dataUltimoAvanzamento = createDateTime("DATA_ULTIMO_AVANZAMENTO", java.sql.Timestamp.class);

    public final StringPath tipoAttivita = createString("TIPO_ATTIVITA");
    
    public final StringPath stato = createString("STATO");

    public final NumberPath<Integer> inseritoDa = createNumber("INSERITO_DA", Integer.class);
   
    public final DateTimePath<java.sql.Timestamp> inseritoIl = createDateTime("INSERITO_IL", java.sql.Timestamp.class);
    
    public final NumberPath<Integer> modificatoDa = createNumber("MODIFICATO_DA", Integer.class);
    
    public final DateTimePath<java.sql.Timestamp> modificatoIl = createDateTime("MODIFICATO_IL", java.sql.Timestamp.class);

    public final StringPath recordStatus = createString("RECORDSTATUS");

    public QDmalmMpsAttivita(String variable) {
        super(DmalmMpsAttivita.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_ATTIVITA");
    }

    public QDmalmMpsAttivita(Path<? extends DmalmMpsAttivita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_ATTIVITA");
    }

    public QDmalmMpsAttivita(PathMetadata<?> metadata) {
        super(DmalmMpsAttivita.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_ATTIVITA");
    }
	
}
