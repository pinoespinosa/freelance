package spring;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
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

import com.google.common.collect.Lists;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class EmailUtils {

	private static String SMTP_HOST = "smtp.gmail.com";
//	private static String EMAIL = "techsystemtandil@gmail.com";
//	private static String EMAIL_PASSWORD = "36442114";
//	private static String EMAIL_FROM = "info@efevisium.com";

	private static String EMAIL = "plataformaefevisium@gmail.com";
	private static String EMAIL_PASSWORD = "71685471";

	

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
				return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			message.setReplyTo( new InternetAddress[] { new InternetAddress(from) } );

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

	public static void sendEmailAttachFile(String from, List<String> toAddressesList, String title, String messageText,
			String filePath) throws AddressException, IOException {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.ssl.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			message.setReplyTo( new InternetAddress[] { new InternetAddress(from) } );

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

			// construct the pdf body part
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

	public static void sendEmailAttachFileCalendar(String from, List<String> toAddressesList, String title,
			String messageText, String dia, String horaInicio, String horaFin) {

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		SimpleDateFormat formatOutput = new SimpleDateFormat("yyyyMMdd'T'hhmm00");

		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTP_HOST);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "false");
		properties.put("mail.smtp.ssl.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);

			}
		});

		MimeBodyPart pdfBodyPart = new MimeBodyPart();

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			message.setReplyTo( new InternetAddress[] { new InternetAddress(from) } );
			
			InternetAddress[] toAddresses = new InternetAddress[toAddressesList.size()];
			int pos = 0;
			for (String toAdd : toAddressesList) {
				toAddresses[pos] = new InternetAddress(toAdd);
				pos++;
			}
			message.setRecipients(Message.RecipientType.TO, toAddresses);

			try {

				Calendar calendar = new Calendar();
				calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
				calendar.getProperties().add(Version.VERSION_2_0);
				calendar.getProperties().add(CalScale.GREGORIAN);

				TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
				TimeZone timezone = registry.getTimeZone("America/Buenos_Aires");

				// Fecha Inicio
				java.util.Calendar fechaInicio = java.util.Calendar.getInstance();
				fechaInicio.setTime(formatDate.parse(dia + " " + horaInicio));
				DateTime start = new DateTime(formatOutput.format(fechaInicio.getTime()));
				start.setTimeZone(timezone);

				// Fecha Final
				java.util.Calendar fechaFinal = java.util.Calendar.getInstance();
				fechaFinal.setTime(formatDate.parse(dia + " " + horaFin));
				DateTime end = new DateTime(formatOutput.format(fechaFinal.getTime()));
				end.setTimeZone(timezone);

				VEvent reunion = new VEvent(start, end, title);

				UidGenerator uidGenerator = new UidGenerator("1");
				reunion.getProperties().add(uidGenerator.generateUid());

				calendar.getComponents().add(reunion);

				// Print data
				for (Iterator<?> i = calendar.getComponents().iterator(); i.hasNext();) {
					Component component = (Component) i.next();
					System.out.println("Component [" + component.getName() + "]");

					for (Iterator<?> j = component.getProperties().iterator(); j.hasNext();) {
						Property property = (Property) j.next();
						System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
					}
				} // for

				FileOutputStream in = new FileOutputStream("mycalendar.ics");
				CalendarOutputter outputter = new CalendarOutputter();
				outputter.output(calendar, in);

				FileInputStream fout = new FileInputStream("mycalendar.ics");
				// construct the pdf body part
				DataSource dataSource = new ByteArrayDataSource(fout, "application/ics");
				pdfBodyPart = new MimeBodyPart();
				pdfBodyPart.setDataHandler(new DataHandler(dataSource));
				pdfBodyPart.setFileName("mycalendar.ics");
			} catch (Exception e) {
				e.printStackTrace();
			}

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