
package lispa.sgr.util;



import java.sql.Timestamp;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import junit.framework.TestCase;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.MailUtil;

public class TestMail extends TestCase {
	public static void testSendMail() {

		Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione(); 
		      
		final String username = "chiaravalloti.salvatore";
		final String password = "";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		session.setDebug(true);
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("chiaravalloti.salvatore@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("salvatore.chiaravalloti@capgemini.com"));
			message.setSubject("MOZZARELLE");
			message.setContent("Dear Mail Crawler,"
					+ "\n\n No spam to my email, please!", "text/html" );
			
			  MimeBodyPart messageBodyPart = new MimeBodyPart();
			  MimeBodyPart text = new MimeBodyPart();
		       
		        Multipart multipart = new MimeMultipart();
		        
		       
		        String file = "D:\\Users\\schiarav\\Desktop\\Datamart delle metriche del software\\04 mozzarelle.jpg";
		        String fileName = "ETL_DM_ALM_"+dataEsecuzione+".jpg";
		      
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        text.setText("TEST MOZZARELLE");
		        multipart.addBodyPart(messageBodyPart);
		        multipart.addBodyPart(text);

		        message.setContent(multipart);
		        
 
			Transport.send(message);
 
			
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void testnewSendMail() throws PropertiesReaderException {
		MailUtil.sendMail("TEST", "TEST", DataEsecuzione.getInstance().getDataEsecuzione());
	}



//		String  d_email = "chiaravalloti.salvatore@gmail.com";
//		final String d_uname = "chiaravalloti.salvatore";
//		final String d_password = "282884aaS1Sas@";
//		String d_host = "smtp.gmail.com", d_port  = "465", 
//			m_to = "chiaravalloti.salvatore@gmail.com", m_subject = "Indoors Readable File: ", m_text = "This message is from Indoor Positioning App. Required file(s) are attached.";
//		    Properties props = new Properties();
//		    props.put("mail.smtp.user", d_email);
//		    props.put("mail.smtp.host", d_host);
//		    props.put("mail.smtp.port", d_port);
//		    props.put("mail.smtp.starttls.enable","true");
//		    props.put("mail.smtp.debug", "true");
//		    props.put("mail.smtp.auth", "true");
//		    props.put("mail.smtp.socketFactory.port", d_port);
//		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		    props.put("mail.smtp.socketFactory.fallback", "false");
//
//		    
//		    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(d_uname, d_password);
//				}
//			  });
//		    session.setDebug(true);
//
//		    MimeMessage msg = new MimeMessage(session);
//		    try {
//		        msg.setSubject(m_subject);
//		        msg.setFrom(new InternetAddress(d_email));
//		        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
//
//		Transport transport = session.getTransport("smtps");
//		            transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
//		            transport.sendMessage(msg, msg.getAllRecipients());
//		            transport.close();
//
//		        } catch (AddressException e) {
//		            
//		        
//		        } catch (MessagingException e) {
//		            
	
		        
//	}
}
