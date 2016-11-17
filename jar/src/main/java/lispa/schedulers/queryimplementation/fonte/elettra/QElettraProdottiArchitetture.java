package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<ElettraProdottiArchitetture> {
	private static final long serialVersionUID = -423818340846641827L;

	public static final QElettraProdottiArchitetture elettraProdottiArchitetture = new QElettraProdottiArchitetture(
			"DM_ALM_EL_PRODOTTI_ARCHITETT");
	
	public final StringPath idEdmaProdArchApplOreste = createString("Id Edma Prod ArchAppl Oreste");
	public final StringPath idProdottoArchApplOreste = createString("Id_Prodotto ArchAppl Oreste");
	public final StringPath tipoProdottoOreste = createString("Tipo Prodotto Oreste");
	public final StringPath siglaProdArchApplOreste = createString("Sigla Prod ArchAppl Oreste");
	public final StringPath nomeProdArchApplOreste = createString("Nome Prod ArchAppl Oreste");
	public final StringPath descrProdArchApplOreste = createString("Descr Prod ArchAppl Oreste");
	public final StringPath areaProdArchApplOreste = createString("Area Prod ArchAppl Oreste");
	public final StringPath respProdArchApplOreste = createString("Resp Prod ArchAppl Oreste");
	public final StringPath prodArchApplOresteAnnullato = createString("Prod ArchAppl Oreste Annullato");
	public final StringPath dfvAnnullamento = createString("dfv annullamento");
	public final StringPath classifAmbitoDiManutenzione = createString("Classif Ambito di Manutenzione");
	public final StringPath classifAreaTematica = createString("Classif Area Tematica");
	public final StringPath classifBaseDatiETL = createString("Classif Base Dati ETL");
	public final StringPath classifBaseDatiLettura = createString("Classif Base Dati Lettura");
	public final StringPath classifBaseDatiScrittura = createString("Classif Base Dati Scrittura");
	public final StringPath classifCategoriaProdotto = createString("Classif Categoria Prodotto");
	public final StringPath classifFornituraDaGara = createString("Classif Fornitura da gara");
	public final StringPath codiceAreaProdArchAppl = createString("Codice Area Prod ArchAppl");
	
	public QElettraProdottiArchitetture(String variable) {
		super(ElettraProdottiArchitetture.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PRODOTTI_ARCHITETT");
	}

	public QElettraProdottiArchitetture(Path<? extends ElettraProdottiArchitetture> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PRODOTTI_ARCHITETT");
	}

	public QElettraProdottiArchitetture(PathMetadata<?> metadata) {
		super(ElettraProdottiArchitetture.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_PRODOTTI_ARCHITETT");
	}
}
