package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElUnitaOrganizzativeFlat extends com.mysema.query.sql.RelationalPathBase<DmalmElUnitaOrganizzativeFlat> {
	private static final long serialVersionUID = 9219078626033239599L;

	public static final QDmalmElUnitaOrganizzativeFlat qDmalmElUnitaOrganizzativeFlat = new QDmalmElUnitaOrganizzativeFlat("DMALM_EL_UNITA_ORGANIZZAT_FLAT");
	
	public final NumberPath<Integer> idFlatPk = createNumber("ID_FLAT", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk01 = createNumber("UNITA_ORG_FK_01", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk02 = createNumber("UNITA_ORG_FK_02", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk03 = createNumber("UNITA_ORG_FK_03", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk04 = createNumber("UNITA_ORG_FK_04", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk05 = createNumber("UNITA_ORG_FK_05", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk06 = createNumber("UNITA_ORG_FK_06", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk07 = createNumber("UNITA_ORG_FK_07", Integer.class);
	public final NumberPath<Integer> unitaOrganizzativaFk08 = createNumber("UNITA_ORG_FK_08", Integer.class);
	public final StringPath codiceArea01 = createString("CD_AREA_01");
	public final StringPath codiceArea02 = createString("CD_AREA_02");
	public final StringPath codiceArea03 = createString("CD_AREA_03");
	public final StringPath codiceArea04 = createString("CD_AREA_04");
	public final StringPath codiceArea05 = createString("CD_AREA_05");
	public final StringPath codiceArea06 = createString("CD_AREA_06");
	public final StringPath codiceArea07 = createString("CD_AREA_07");
	public final StringPath codiceArea08 = createString("CD_AREA_08");
	public final StringPath descrizioneArea01 = createString("DS_AREA_EDMA_01");
	public final StringPath descrizioneArea02 = createString("DS_AREA_EDMA_02");
	public final StringPath descrizioneArea03 = createString("DS_AREA_EDMA_03");
	public final StringPath descrizioneArea04 = createString("DS_AREA_EDMA_04");
	public final StringPath descrizioneArea05 = createString("DS_AREA_EDMA_05");
	public final StringPath descrizioneArea06 = createString("DS_AREA_EDMA_06");
	public final StringPath descrizioneArea07 = createString("DS_AREA_EDMA_07");
	public final StringPath descrizioneArea08 = createString("DS_AREA_EDMA_08");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
	
	public final com.mysema.query.sql.PrimaryKey<DmalmElUnitaOrganizzativeFlat> dmalmUnitaOrganizzativaFlatPk = createPrimaryKey(idFlatPk);

    public QDmalmElUnitaOrganizzativeFlat(String variable) {
        super(DmalmElUnitaOrganizzativeFlat.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZAT_FLAT");
    }

    public QDmalmElUnitaOrganizzativeFlat(Path<? extends DmalmElUnitaOrganizzativeFlat> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZAT_FLAT");
    }

    public QDmalmElUnitaOrganizzativeFlat(PathMetadata<?> metadata) {
        super(DmalmElUnitaOrganizzativeFlat.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZAT_FLAT");
    }

}
