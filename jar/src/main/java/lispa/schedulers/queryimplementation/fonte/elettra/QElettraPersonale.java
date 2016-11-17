package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraPersonale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QElettraPersonale extends
		com.mysema.query.sql.RelationalPathBase<ElettraPersonale> {
	private static final long serialVersionUID = 5162764710944136734L;

	public static final QElettraPersonale elettraPersonale = new QElettraPersonale(
			"DM_ALM_EL_PERSONALE");

	public final StringPath idEdma = createString("Id Edma Pers EDMA_LISPA");
	public final StringPath codicePersonale = createString("Codice Pers EDMA_LISPA");
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime(
			"DT IN Validita Pers EDMA_LISPA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime(
			"DT FN Validita Pers EDMA_LISPA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataAttivazione = createDateTime(
			"DT Attivaz Pers EDMA_LISPA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataDisattivazione = createDateTime(
			"DT Disattivaz Pers EDMA_LISPA", java.sql.Timestamp.class);
	public final StringPath note = createString("Note Pers EDMA_LISPA");
	public final NumberPath<Short> flagInterno = createNumber(
			"Flag Interno Pers EDMA_LISPA", Short.class);
	public final StringPath codiceResponsabile = createString("Cod Resp Pers EDMA_LISPA");
	public final StringPath indirizzoEmail = createString("E-Mail Pers EDMA_LISPA");
	public final StringPath nome = createString("Nome Pers EDMA_LISPA");
	public final StringPath cognome = createString("Cognome Pers EDMA_LISPA");
	public final StringPath matricola = createString("Matricola Pers EDMA_LISPA");
	public final StringPath codiceFiscale = createString("Cod Fisc Pers EDMA_LISPA");
	public final StringPath identificatore = createString("Identific Pers EDMA_LISPA");
	public final NumberPath<Integer> idGrado = createNumber(
			"Grado Pers EDMA_LISPA", Integer.class);
	public final NumberPath<Integer> idSede = createNumber(
			"Id Sede Pers EDMA_LISPA", Integer.class);
	public final StringPath codiceUOSuperiore = createString("Cod UO Sup Pers EDMA_LISPA");
	public final StringPath codiceEnte = createString("Codice Ente Pers EDMA_LISPA");
	public final StringPath codiceVisibilita = createString("Cod Visibilita Pers EDMA_LISPA");

	public QElettraPersonale(String variable) {
		super(ElettraPersonale.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PERSONALE");
	}

	public QElettraPersonale(Path<? extends ElettraPersonale> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PERSONALE");
	}

	public QElettraPersonale(PathMetadata<?> metadata) {
		super(ElettraPersonale.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PERSONALE");
	}
}
