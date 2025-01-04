package helha.trocappbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

   // @Autowired
    private JavaMailSender mailSender;

    public EmailService() {
        super();
    }

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
