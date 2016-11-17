package lispa.schedulers.bean.annullamenti.project;

import java.sql.Timestamp;

public class DmAlmProjectUnmarked {

	private String repository;
	
	private String cTrackerprefix;
	
	private String path;
	
	private Timestamp dataCaricamento;

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getcTrackerprefix() {
		return cTrackerprefix;
	}

	public void setcTrackerprefix(String cTrackerprefix) {
		this.cTrackerprefix = cTrackerprefix;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Timestamp getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Timestamp dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	
}
