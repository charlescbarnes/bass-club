import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Email {
    protected static String username; // GMail username (just the part before "@gmail.com")
    protected static  String password; // GMail password
    protected static final String LOGO = "<center>"
                + "<img src=\"cid:image\" "
                + "alt=\"Big Sandy Creek Bass Club logo\" "
                + "width=\"250\">"
                + "</center>";

    protected static final String LOGO_FILE_PATH = "logo.png";
    protected static final String WEBSITE_HTML_HYPERLINK = "<a href=\"https://www.bigsandycreek.com/\" target=\"_blank\" rel=\"noopener noreferrer\">website</a>";
    protected static final String FACEBOOK_HTML_HYPERLINK = "<a href=\"https://www.facebook.com/bigsandycreek\" target=\"_blank\" rel=\"noopener noreferrer\">Facebook</a>";
    protected static final String INSTAGRAM_HTML_HYPERLINK = "<a href=\"https://www.instagram.com/bigsandycreek/\" target=\"_blank\" rel=\"noopener noreferrer\">Instagram</a>";
    protected static String mailTo;
    protected static String unsubscribeHTML;
    protected ArrayList<String> recipients;
    protected String subject;
    protected String body; // will be nested in <html><body> ... </body></html>
    protected HashMap<String, String> attachments;
    protected String sentMessage = "Email sent!";

    /**
     * Class constructor
     * @param recipients    an ArrayList of recipient email addresses
     * @param subject       the subject of the email (as a string)
     * @param body          the body of the email (as a string)
     */
    public Email(ArrayList<String> recipients, String subject, String body) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
    }

    public void setRecipients(ArrayList<String> recipients) { this.recipients = recipients; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setBody(String body) { this.body = body; }

    /**
     * adds an attachment to the <code>Email</code>
     * @param filePath  the path to the file to be attached
     */
    public void addAttachment(String filePath) {
        attachments = new HashMap<>();
        int fileNameStartIndex = Math.max(filePath.lastIndexOf("/"), filePath.lastIndexOf("\\")) + 1;
        String key = filePath.substring(fileNameStartIndex);
        attachments.put(key, filePath);
    }

    /**
     * club email credentials must be saved in a file called EmailCredentials.txt
     * EmailCredentials must consist of just two lines:
     * line 1: a GMail username (username only, excluding "@gmail.com")
     * line 2: the password for the GMail account
     */
    public static void setEmailCredentials() {
        try(BufferedReader in = new BufferedReader(new FileReader("EmailCredentials.txt"))) {
            username = in.readLine();
            password = in.readLine();
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
    }

    public static void setMailTo() {
        setEmailCredentials();
        mailTo = "<a href=\"mailto:" +
                username +
                "@gmail.com?subject=Unsubscribe&" +
                "body=Let us know what we can do to improve. " +
                "Thanks!\">Click here</a>";
    }

    public static void setUnsubscribeHTML() {
        setEmailCredentials();
        setMailTo();
        unsubscribeHTML = "<p>" + mailTo + " to unsubscribe from these emails.</p>";
    }

    /**
     * sends the <code>Email</code> to <code>recipients</code>
     * @param BCCRecipients determines whether recipients should be placed in the BCC: or To: field
     */
    public void send(boolean BCCRecipients) {
        String messageContent = "<html><body>"
                + LOGO
                + body
                + "</body></html>";

        setEmailCredentials();

        try {
            Properties props = System.getProperties();
            String host = "smtp.gmail.com";
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", username);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            // get the array of addresses
            InternetAddress[] toAddress = new InternetAddress[recipients.size()];
            for (int i = 0; i < recipients.size(); i++) {
                toAddress[i] = new InternetAddress(recipients.get(i));
            }
            // add them as (the correct type of) recipients of the message
            for (InternetAddress address : toAddress) {
                if (BCCRecipients) {
                    message.addRecipient(Message.RecipientType.BCC, address);
                }
                else {
                    message.addRecipient(Message.RecipientType.TO, address);
                }
            }

            message.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageContent, "text/html");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(LOGO_FILE_PATH);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // third part (attachments)
            if (attachments != null) {
                for (Map.Entry<String, String> attachment : attachments.entrySet()) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment.getValue());
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(attachment.getKey());
                    // add it
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            // put everything together
            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.print(sentMessage);
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void setSentMessage(String sentMessage) {
        this.sentMessage = sentMessage;
    }

    public static String getUnsubscribeHTML() {
        setUnsubscribeHTML();
        return unsubscribeHTML;
    }

    public String toString() {
        StringBuilder output = new StringBuilder("To: " + recipients);
        output.append("\n\nSubject: ").append(subject)
                .append("\n\nBody: ").append(body);
        if (attachments != null) {
            String[] attachmentNames = new String[attachments.size()];
            attachments.keySet().toArray(attachmentNames);
            output.append("\n\nAttachments: ");
            for (int i = 0; i < attachments.size(); i++) {
                output.append(attachmentNames[i]);
                if (i != attachments.size()-1) {
                    output.append(", ");
                }
            }
        }
        return output.toString();
    }
}