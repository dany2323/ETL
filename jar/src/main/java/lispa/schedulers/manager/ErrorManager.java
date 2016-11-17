package lispa.schedulers.manager;

import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

public class ErrorManager {

	private static Logger logger = Logger.getLogger(ErrorManager.class);

	private static ErrorManager instance;
	private boolean isError;
	private boolean inDeadlock;
	private boolean cfDeadlock;
	private String customFieldName;
	private List<Exception> exceptions;
	private Exception exception;

	private ErrorManager () {
		this.isError = false;
		this.exceptions = new ArrayList<Exception>();
		this.inDeadlock = false;
		this.cfDeadlock = false;
		this.customFieldName = null;
	}

	public synchronized List<Exception> getExceptions() {
		return exceptions;
	}

	private synchronized void setException(Exception e) {
		if(exception == null) 
			this.exception = e; 
	}
	public synchronized String  getException() {
		return this.exception.getMessage();
	}


	private void addException(Exception typeException) {

		//if(l-eccezione e' bloccante) isError = true;
		this.exceptions.add(typeException);

		Throwable cause = typeException;
		while(cause.getCause() != null)
			cause = cause.getCause();
		String message = cause.getMessage();
		logger.error("[ERROR MANAGER] ricevuta exception con messaggio -> " + message);
		if(message==null)
			return;
		if(message.startsWith("svn")) {
			if(StringUtils.findRegex(message, DmalmRegex.REGEXSVNBLOCCANTE)) {
				logger.fatal("Errore SVN relativo ai permessi, BLOCCANTE ");
				isError = true;
			}
			if(StringUtils.findRegex(message, DmalmRegex.REGEXSVNNONBLOCCANTE_1)) {
			}
			if(StringUtils.findRegex(message, DmalmRegex.REGEXSVNNONBLOCCANTE_2)) {
				logger.fatal("Errore SVN, NON BLOCCANTE");
			}
			else  {
				logger.error("Errore SVN sconosciuto, BLOCCANTE");
				isError = true;
			}
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXORAERROR)) {
			logger.fatal("Errore ORACLE, BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXCONNECTIONTIMEOUT)) {
			logger.fatal("Errore CONNECTION, BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXCONNECTIONREFUSED)) {
			logger.fatal("Errore CONNECTION, BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXINVALIDCINDEX)) {
			logger.fatal("Errore COLUMN NAME DOESN'T EXIST, BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)){
			logger.fatal("Errore DEADLOCK DETECTED, BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXPARAMETRO)){
			logger.fatal("Errore PARAMETRO NELLA PREPARESTMT NON SETTATO , BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXLOCKTABLE)){
			logger.fatal("Errore TIMEOUT NEL TENTATIVO DI LOCKARE UNA TABELLA , BLOCCANTE");
			isError = true;
		}
		else if(StringUtils.findRegex(message, DmalmRegex.REGEXCONNECTIONRESET)){
			logger.fatal("Errore CONNESSIONE RESETTATA , BLOCCANTE");
			isError = true;
		}
		else if(message.startsWith("MPS file non trovato")) {
			logger.error("Errore esecuzione MPS, BLOCCANTE");
			isError = true;
		}
		else {
			logger.error("Altro tipo di errore -- BLOCCANTE?");
			logger.fatal("Errore SCONOSCIUTO ROLLBACK");
			isError = true;
		}
		if(isError)
			setException(typeException);

	}

	public synchronized boolean hasError() {
		return isError;
	}
	
	public synchronized boolean hasDeadLock() {
		return inDeadlock;
	}
	
	public synchronized boolean hascfDeadLock() {
		return cfDeadlock;
	}

	public synchronized String cfNameDeadLock() {
		return this.customFieldName;
	}
	
	public synchronized void resetError() {
		this.isError = false;
		this.exceptions = new ArrayList<Exception>();
		this.exception = null; 
	}
	
	public synchronized void exceptionOccurred(boolean isError, Exception e) {

		logger.error(e.getMessage(), e);
		addException(e);
	}

	public synchronized void exceptionDeadlock(boolean isError, Exception e) {

		logger.error("Deadlock detected");
		inDeadlock = true;
	}
	
	public synchronized void exceptionCFDeadlock(boolean isError, Exception e, String cfName) {

		logger.error("Deadlock detected on CF: " + cfName);
		cfDeadlock = true;
		this.customFieldName = cfName;
	}
	
	public synchronized void resetDeadlock() {
		this.inDeadlock = false;
	}
	
	public synchronized void resetCFDeadlock() {
		this.cfDeadlock = false;
		this.customFieldName = null;
	}

	public synchronized static ErrorManager getInstance() {
		if(instance == null)
			instance = new ErrorManager();
		return instance;
	}
}
