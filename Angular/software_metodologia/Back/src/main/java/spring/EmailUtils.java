package spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailUtils {

	private static String SMTP_HOST = "smtp.gmail.com";

	public static void sendEmail(String from, List<String> toAddressesList, String title, String messageText)
			throws UnsupportedEncodingException, AddressException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.ssl.enable", "true");


		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("techsystemtandil@gmail.com", "36442114");
			}
		});
		Message msg = new MimeMessage(session);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			InternetAddress[] toAddresses = new InternetAddress[toAddressesList.size()];
			int pos = 0;
			for (String toAdd : toAddressesList) {
				toAddresses[pos] = new InternetAddress(toAdd);
				pos++;
			}
			message.setRecipients(Message.RecipientType.TO, toAddresses);

			// Set Subject: header field
			message.setSubject(title);

			// Now set the actual message
			message.setText(messageText);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
	public static void sendEmailAttachFile(String from, List<String> toAddressesList, String title, String messageText, String filePath)
			throws AddressException, IOException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.ssl.enable", "true");


		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("techsystemtandil@gmail.com", "36442114");
			}
		});
		Message msg = new MimeMessage(session);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress("techsystemtandil@gmail.com"));

			InternetAddress[] toAddresses = new InternetAddress[toAddressesList.size()];
			int pos = 0;
			for (String toAdd : toAddressesList) {
				toAddresses[pos] = new InternetAddress(toAdd);
				pos++;
			}
			message.setRecipients(Message.RecipientType.TO, toAddresses);

	        System.out.println("Adjuntando archivo del directorio: " + filePath);
	        Path path = Paths.get(filePath);        
	        byte[] bytes = Files.readAllBytes(path);
             
            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("Acta.pdf");
			
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(messageText);
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
            message.setContent(mimeMultipart);
			
			
			// Set Subject: header field
			message.setSubject(title);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	
	
	
}