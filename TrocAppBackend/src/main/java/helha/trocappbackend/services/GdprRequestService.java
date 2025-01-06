package helha.trocappbackend.services;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling GDPR request-related operations.
 * This includes creating, updating, retrieving, and processing GDPR requests.
 */
@Service
public class GdprRequestService {

    private final GdprRequestRepository gdprRequestRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a GdprRequestService instance.
     *
     * @param gdprRequestRepository The repository for managing GDPR requests.
     * @param userRepository The repository for managing users.
     */
    @Autowired
    public GdprRequestService(GdprRequestRepository gdprRequestRepository,
                              UserRepository userRepository) {
        this.gdprRequestRepository = gdprRequestRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new GDPR request for a specific user.
     *
     * @param requestType The type of GDPR request (e.g., "Delete my data").
     * @param user The user making the request.
     * @param consent Whether the user consents to the request.
     * @param justification The justification provided by the user for the request.
     * @return The created GDPR request.
     * @throws IllegalArgumentException If the user doesn't exist, consent is not provided, or justification is missing.
     */
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

    /**
     * Updates the status and response for an existing GDPR request.
     *
     * @param gdprRequestId The ID of the GDPR request to be updated.
     * @param status The new status of the request (e.g., "Processed").
     * @param response The response from the administrator to the user's request.
     * @return The updated GDPR request.
     * @throws RuntimeException If the request with the provided ID is not found.
     */
    public GdprRequest updateGdprRequest(int gdprRequestId, String status, String response) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new RuntimeException("GDPR Request not found"));

        // Update status and response
        gdprRequest.setStatus(status);
        gdprRequest.setResponse(response);
        gdprRequest.setResponsedate(LocalDateTime.now());

        return gdprRequestRepository.save(gdprRequest);
    }

    /**
     * Retrieves a list of GDPR requests made by a specific user.
     *
     * @param user The user for whom the GDPR requests are being retrieved.
     * @return A list of GDPR requests associated with the user.
     */
    public List<GdprRequest> getRequestsByUser(User user) {
        return gdprRequestRepository.findByUser(user);
    }

    /**
     * Retrieves a list of all GDPR requests in the system.
     *
     * @return A list of all GDPR requests.
     */
    public List<GdprRequest> getAllRequests() {
        return gdprRequestRepository.findAll();
    }

    /**
     * Deactivates a user account based on their ID.
     *
     * @param userId The ID of the user to be deactivated.
     * @throws RuntimeException If the user with the provided ID is not found.
     */
    public void deactivateUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActif(false);
        userRepository.save(user);
    }

    /**
     * Retrieves all GDPR requests that match the provided statuses.
     *
     * @param statuses The statuses of the requests to filter by (e.g., "Pending", "Processed").
     * @return A list of GDPR requests with matching statuses.
     */
    public List<GdprRequest> getRequestsByStatus(String... statuses) {
        return gdprRequestRepository.findAll().stream()
                .filter(request -> Arrays.asList(statuses).contains(request.getStatus()))
                .collect(Collectors.toList());
    }
}
