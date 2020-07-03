package Utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    private String gmail_user; // your gmail
    private String password; // your gmail password
    private String message;

    public Email(String gmail_user, String password, String message) {
        this.gmail_user = gmail_user;
        this.password = password;
        this.message = message;
    }

    public String getGmail_user() {
        return gmail_user;
    }

    public void setGmail_user(String gmail_user) {
        this.gmail_user = gmail_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendMessageToEmail(String title, String to_email) {
        String host = "smtp.gmail.com";

        Properties p = System.getProperties();
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.user", gmail_user);
        p.put("mail.smtp.password", password);
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.auth", "true");

        Session s = Session.getDefaultInstance(p);

        try {
            // set up the message object:
            MimeMessage m = new MimeMessage(s);
            m.setFrom(new InternetAddress(gmail_user));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));

            m.setSubject(title);
            m.setText(message + "\n");

            //send email:
            Transport transport = s.getTransport("smtp");
            transport.connect(host, gmail_user, password);
            transport.sendMessage(m, m.getAllRecipients());

            System.out.println("Email sent successfully");
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
