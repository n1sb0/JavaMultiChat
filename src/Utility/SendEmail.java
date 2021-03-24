package Utility;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	public static int SendEmailTo(String EmailTo) {
		//authentication info
		final String username = "nsb.app0@gmail.com";
		final String password = "Bogdan12313";
		String toEmail = EmailTo;
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		MimeMessage msg = new MimeMessage(session);
		
		int code = (int) (Math.random() * 10000);
		try {
			msg.setFrom(new InternetAddress(username));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject("Recupera la password");		
			msg.setText("Ciao, il tuo codice: "+ code);
			
			Transport.send(msg);
			
			System.out.println("Sent message");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return code;
	}

}