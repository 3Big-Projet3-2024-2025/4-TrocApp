package helha.trocappbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails using JavaMailSender.
 * This class provides a method to send simple text-based emails.
 *
 * <p>Usage:</p>
 * <pre>{@code
 * @Autowired
 * private EmailService emailService;
 *
 * emailService.sendEmail("recipient@example.com", "Subject", "Email body text");
 * }</pre>
 *
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.services
 */
@Service
public class EmailService {

    /**
     * The JavaMailSender instance used to send emails.
     * This bean is auto-wired from the Spring context.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Default constructor for the EmailService class.
     */
    public EmailService() {
        super();
    }

    /**
     * Sends a simple email with the specified recipient, subject, and body text.
     *
     * @param to      The email address of the recipient.
     * @param subject The subject of the email.
     * @param text    The body text of the email.
     *
     * @throws RuntimeException If an error occurs during email sending.
     */
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            String smtpUser = "la226220@student.helha.be";
            message.setFrom(smtpUser);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            System.out.println("Email succesfully sent");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Sending an email failed " + e.getMessage());
        }
    }

}
