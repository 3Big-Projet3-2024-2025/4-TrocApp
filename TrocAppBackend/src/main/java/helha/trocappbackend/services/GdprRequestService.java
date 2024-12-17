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

        public GdprRequest createGdprRequest(GdprRequest gdprRequest) {
            // L'utilisateur qui a fait la demande
            User user = gdprRequest.getUser();

            // Vérification que l'utilisateur existe
            if (user == null || !userRepository.existsById(user.getId())) {
                throw new IllegalArgumentException("User does not exist.");
            }

            // Par défaut, on laisse le champ adminUser à null et le gestionnaire de la demande sera affecté plus tard.
            return gdprRequestRepository.save(gdprRequest);
        }

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

        public GdprRequest updateGdprRequest(int gdprRequestId, String status, String response) {
            GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                    .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

            // Update the status and response
            gdprRequest.setStatus(status);
            gdprRequest.setResponse(response);
            gdprRequest.setResponsedate(LocalDateTime.now());

            return gdprRequestRepository.save(gdprRequest);
        }

        public void deleteGdprRequest(int gdprRequestId) {
            if (!gdprRequestRepository.existsById(gdprRequestId)) {
                throw new RuntimeException("GDPR Request not found");
            }

            gdprRequestRepository.deleteById(gdprRequestId);
        }

        // Optionnel : get the requests  in function of the status
        public List<GdprRequest> getRequestsByUser(User user) {
            return gdprRequestRepository.findByUser(user);
        }

        public List<GdprRequest> getRequestsByAdmin(User adminUser) {
            return gdprRequestRepository.findByAdminUser(adminUser);
        }
    }


