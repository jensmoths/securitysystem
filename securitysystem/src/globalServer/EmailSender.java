package globalServer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private Properties properties;

    public EmailSender(){

        properties = new Properties();

    }

    public void sendMail(String recipient, String subject, String text) throws MessagingException {

        System.out.println("börjar skicka");

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "securehomesmau@gmail.com";
        String password = "emailutskick456";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = messageCreator(session,myAccountEmail,recipient, subject, text);
        Transport.send(message);
        System.out.println("skickat");
    }


    public Message messageCreator(Session session, String myAccountEmail, String recipient, String subject, String text) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(text);
        return message;

    }


    public static void main(String[] args) throws MessagingException {
        new EmailSender().sendMail("darwesh.ammar@hotmail.com" , "Larm har gått", "Hej kära kund!\n\n" + " Brandlarmet har upptäckt rök i hallen");
    }

}
