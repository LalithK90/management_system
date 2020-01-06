package lk.imms.management_system.util.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    // to access application properties entered details
    private final Environment environment;

    @Autowired
    public EmailService(JavaMailSender javaMailSender,  Environment environment) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
    }

    public boolean sendPatientRegistrationEmail(String receiverEmail, String subject, String message) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String messageText = message +
                "\n\n\n\n\nThis is a one way communication email service hence please do not reply if you need to further details regarding anything take call to hot line which mentioned above " +
                "\n\n\n\n\nWe will not responsible for reports not collected within 30 days.   ";
        try {
            mailMessage.setTo(receiverEmail);
            mailMessage.setFrom("Excellent_Health_Solution - (not reply)");
            mailMessage.setSubject(subject);
            mailMessage.setText(messageText);

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void sendPatientReport(String receiverEmail, String subject, String fileName) {
//todo send pdf as email after encryption
        /*
     File Encryption

In order to apply permission using iText library, we need to have already created pdf document. In our example, we will use our iTextHelloWorld.pdf file generated previously.

Once we load the file using PdfReader, we need to create a PdfStamper which is used to apply additional content to file like metadata, encryption etc:

PdfReader pdfReader = new PdfReader("HelloWorld.pdf");
PdfStamper pdfStamper
  = new PdfStamper(pdfReader, new FileOutputStream("encryptedPdf.pdf"));

pdfStamper.setEncryption(
  "userpass".getBytes(),
  ".getBytes(),
  0,
  PdfWriter.ENCRYPTION_AES_256
);

pdfStamper.close();

In our example, we encrypted the file with two passwords. The user password (“userpass”) w@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}here a user has only read-only right with no possibility to print it, and owner password (“ownerpass”) that is used as master key allowing a person to have full access to pdf.

If we want to allow the user to print pdf, instead of 0 (third parameter of setEncryption) we can pass:

PdfWriter.ALLOW_PRINTING

Of course, we can mix different permissions like:


PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY

Keep in mind that using iText to set access permissions, we are also creating a temporary pdf which should be deleted and if not it could be fully accessible to anybody.


     */



        //final String username = "excellenthealthsolution@gmail.com";
        final String username = environment.getProperty("spring.mail.username");
        //final String password = "dinesh2018";
        final String password = environment.getProperty("spring.mail.password");

        // Assuming you are sending email through gmail
        String host = environment.getProperty("spring.mail.host");
        //String host = "smtp.gmail.com";
        String port = environment.getProperty("spring.mail.port");
        //String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Please find the attachment");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // set file path to include email
            String filename = fileName;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}