package helha.trocappbackend.services;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
//import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GdprRequestService {

    /*@Autowired
    private GdprRequestRepository gdprRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    //private JavaMailSender mailSender;  // Utilisation de JavaMailSender pour l'envoi de mails

    // Créer une demande GDPR
    public GdprRequest createGdprRequest(GdprRequest gdprRequest) {
        // L'utilisateur qui a fait la demande
        User user = gdprRequest.getUser();

        // Vérification que l'utilisateur existe
        if (user == null || !userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User does not exist.");
        }

        // Vérification du consentement
        if (!gdprRequest.isConsent()) {
            throw new IllegalArgumentException("Consent is required for GDPR request.");
        }

        // On définit la date de la demande à la date actuelle
        gdprRequest.setRequestdate(LocalDateTime.now());

        // Par défaut, on laisse le champ adminUser à null et le gestionnaire de la demande sera affecté plus tard.
        return gdprRequestRepository.save(gdprRequest);
    }

    // Assigner un admin à la demande GDPR
    public GdprRequest assignAdminToRequest(int gdprRequestId, int adminUserId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        User adminUser = userRepository.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        // Vérification que l'utilisateur est un administrateur
        if (!adminUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("User is not an administrator");
        }

        gdprRequest.setAdminUser(adminUser);
        return gdprRequestRepository.save(gdprRequest);
    }

    // Mettre à jour une demande GDPR avec un statut et une réponse
    public GdprRequest updateGdprRequest(int gdprRequestId, String status, String response) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        // Mise à jour du statut et de la réponse
        gdprRequest.setStatus(status);
        gdprRequest.setResponse(response);
        gdprRequest.setResponsedate(LocalDateTime.now());

        return gdprRequestRepository.save(gdprRequest);
    }

    // Supprimer la demande GDPR et anonymiser les données de l'utilisateur
    public void deleteGdprRequest(int gdprRequestId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        User user = gdprRequest.getUser();

        // Logique pour anonymiser les données de l'utilisateur
        anonymizeUserData(user);

        // Supprimer la demande
        gdprRequestRepository.deleteById(gdprRequestId);
    }

    // Méthode pour anonymiser les données personnelles de l'utilisateur
    private void anonymizeUserData(User user) {
        // Remplacer toutes les données sensibles par des astérisques
        user.setEmail("*****");
        user.setFirstName("*****");
        user.setLastName("*****");
        // Ajouter d'autres champs sensibles ici si nécessaire
        userRepository.save(user);  // Sauvegarder les modifications dans la base de données
    }

    // Envoyer un email à l'utilisateur pour répondre à sa demande
    /*public void sendEmailToUser(int gdprRequestId, String emailContent) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        User user = gdprRequest.getUser();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Réponse à votre demande GDPR");
            helper.setText(emailContent, true);  // Texte de l'email (HTML ou texte brut)

            mailSender.send(message);  // Envoi du mail
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }*/

    // Récupérer les demandes par utilisateur
    /*public List<GdprRequest> getRequestsByUser(User user) {
        return gdprRequestRepository.findByUser(user);
    }

    // Récupérer les demandes par administrateur
    public List<GdprRequest> getRequestsByAdmin(User adminUser) {
        return gdprRequestRepository.findByAdminUser(adminUser);
    }

    // Service pour obtenir toutes les demandes GDPR
    public List<GdprRequest> getAllGdprRequests() {
        return gdprRequestRepository.findAll();  // Utilisation de findAll() pour obtenir toutes les demandes
    }*/

}
