package helha.trocappbackend.services;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
//import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GdprRequestService {

    private final GdprRequestRepository gdprRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public GdprRequestService(GdprRequestRepository gdprRequestRepository,
                              UserRepository userRepository) {
        this.gdprRequestRepository = gdprRequestRepository;
        this.userRepository = userRepository;
    }

    // Créer une demande GDPR
    public GdprRequest createGdprRequest(String requestType, User user, boolean consent, String justification) {
        if (user == null || !userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User does not exist.");
        }

        if (!consent) {
            throw new IllegalArgumentException("Consent is required for GDPR request.");
        }
        if (justification == null || justification.trim().isEmpty()) {
            throw new IllegalArgumentException("Justification is required for GDPR request.");
        }

        GdprRequest gdprRequest = new GdprRequest();
        gdprRequest.setRequesttype(requestType);
        gdprRequest.setUser(user);
        gdprRequest.setConsent(consent);
        gdprRequest.setJustification(justification);
        gdprRequest.setRequestdate(LocalDateTime.now());
        gdprRequest.setStatus("Pending");

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

    // Récupérer les demandes par utilisateur
    public List<GdprRequest> getRequestsByUser(User user) {
        return gdprRequestRepository.findByUser(user);
    }

    // Service pour obtenir toutes les demandes GDPR
    public List<GdprRequest> getAllRequests() {
        return gdprRequestRepository.findAll();
    }

    public void deactivateUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActif(false);
        userRepository.save(user);
    }

    public List<GdprRequest> getRequestsByStatus(String... statuses) {
        return gdprRequestRepository.findAll().stream()
                .filter(request -> Arrays.asList(statuses).contains(request.getStatus()))
                .collect(Collectors.toList());
    }




}
