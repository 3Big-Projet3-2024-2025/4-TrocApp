package helha.trocappbackend.services;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GdprRequestService {

    @Autowired
    private GdprRequestRepository gdprRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a GDPR request
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

    // Assign an admin user to handle the GDPR request
    public GdprRequest assignAdminToRequest(int gdprRequestId, int adminUserId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        User adminUser = userRepository.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        if (!adminUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("User is not an administrator");
        }

        gdprRequest.setAdminUser(adminUser);
        return gdprRequestRepository.save(gdprRequest);
    }

    // Update the GDPR request with status and response
    public GdprRequest updateGdprRequest(int gdprRequestId, String status, String response) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        // Update the status and response
        gdprRequest.setStatus(status);
        gdprRequest.setResponse(response);
        gdprRequest.setResponsedate(LocalDateTime.now());

        return gdprRequestRepository.save(gdprRequest);
    }

    // Delete the GDPR request and perform data deletion (sensitive data)
    public void deleteGdprRequest(int gdprRequestId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        User user = gdprRequest.getUser();

        // Logic for deleting the user's personal data in the database
        deleteUserData(user);

        gdprRequestRepository.deleteById(gdprRequestId);
    }

    // Helper method for deleting the user's data from the system
    private void deleteUserData(User user) {
        // Delete user-related data from various tables as necessary
        // For example, if there are specific repositories for user data:
        // userDataRepository.deleteByUser(user);

        // Assuming that we delete the user entirely (if that's the requirement)
        userRepository.delete(user); // Delete the user itself (if required)
    }

    // Get requests by user
    public List<GdprRequest> getRequestsByUser(User user) {
        return gdprRequestRepository.findByUser(user);
    }

    // Get requests by admin user
    public List<GdprRequest> getRequestsByAdmin(User adminUser) {
        return gdprRequestRepository.findByAdminUser(adminUser);
    }
    // Service method to get all GDPR requests
    public List<GdprRequest> getAllGdprRequests() {
        return gdprRequestRepository.findAll();  // Utilisation de findAll() pour obtenir toutes les demandes
    }

}
