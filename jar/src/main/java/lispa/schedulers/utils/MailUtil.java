package lispa.schedulers.utils;


import java.sql.Timestamp;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;

import org.apache.log4j.Logger;

public class MailUtil {
	
	private static Logger logger = Logger.getLogger(MailUtil.class); 
    
	public static void sendMail(String subject, String body,Timestamp dataEsecuzione) throws PropertiesReaderException {
		String from = DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.MAIL_FROM);
		// Per inserire più indirizzi destinatari, separarli da ";"
		String to = DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.MAIL_TO);
		String server = DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.MAIL_SERVER); 
		
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", server);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		session.setDebug(true);
		// Splitting toAddress
		String[] parts = to.split(";");
		InternetAddress[] toArray = new InternetAddress[parts.length];

		try{

			// creating array toAddress
			for (int i=0; i<parts.length; i++)  {
				toArray[i] = new InternetAddress(parts[i]);  
			}


			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO,
					toArray);

			// Set Subject: header field
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart attachment = new MimeBodyPart();
			MimeBodyPart plaintext = new MimeBodyPart();

	        
	        String file = DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.MAIL_LOG_PATH);
	        String fileName = "ETL_DM_ALM_"+dataEsecuzione+".log";
	      
	        DataSource source = new FileDataSource(file);
	        attachment.setDataHandler(new DataHandler(source));
	        attachment.setFileName(fileName);
	        plaintext.setText(body);
	        multipart.addBodyPart(attachment);
	        multipart.addBodyPart(plaintext);
	        

	        message.setContent(multipart);

			// Send the actual HTML message, as big as you like
			

			// Send message
			Transport.send(message);
			
			logger.debug("Sent message successfully....");
			
		}catch (Exception mex) {
			logger.error(mex.toString(), mex);
		}	
	}
}


