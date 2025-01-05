package helha.trocappbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/test-email")
    public String testEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("la226220@student.helha.be"); // Ton adresse email
            message.setTo("test@domain.com");  // Adresse email de test
            message.setSubject("Test Email");
            message.setText("This is a test email from Spring Boot.");

            mailSender.send(message);

            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
