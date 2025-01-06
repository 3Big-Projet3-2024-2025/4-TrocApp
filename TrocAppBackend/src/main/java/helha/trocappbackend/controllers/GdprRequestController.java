package helha.trocappbackend.controllers;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.GdprRequestService;
import helha.trocappbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This controller handles the API endpoints for managing GDPR requests.
 * It allows creating, retrieving, processing, and deactivating users based on GDPR requests.
 * It also includes email notifications when a request is processed.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/gdpr")
public class GdprRequestController {

    @Autowired
    private GdprRequestService gdprRequestService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GdprRequestRepository gdprRequestRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    /**
     * Creates a new GDPR request.
     *
     * @param request the GDPR request to be created
     * @return a ResponseEntity containing the created GDPR request and HTTP status
     */
    @PostMapping
    public ResponseEntity<GdprRequest> createRequest(@RequestBody GdprRequest request) {
        GdprRequest savedRequest = gdprRequestService.createGdprRequest(
                request.getRequesttype(),
                request.getUser(),
                request.isConsent(),
                request.getJustification()
        );
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    /**
     * Retrieves all GDPR requests associated with a specific user.
     *
     * @param userId the ID of the user whose requests are to be retrieved
     * @return a ResponseEntity containing a list of GDPR requests for the user and HTTP status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GdprRequest>> getRequestsByUser(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<GdprRequest> requests = gdprRequestService.getRequestsByUser(user);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Retrieves all GDPR requests.
     *
     * @return a ResponseEntity containing a list of all GDPR requests and HTTP status
     */
    @GetMapping
    public ResponseEntity<List<GdprRequest>> getAllRequests() {
        List<GdprRequest> requests = gdprRequestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    /**
     * Retrieves a specific GDPR request by its ID.
     *
     * @param gdprRequestId the ID of the GDPR request to be retrieved
     * @return a ResponseEntity containing the GDPR request and HTTP status
     */
    @GetMapping("/{gdprRequestId}")
    public ResponseEntity<GdprRequest> getRequestById(@PathVariable int gdprRequestId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new NoSuchElementException("GdprRequest not found with id: " + gdprRequestId));
        return new ResponseEntity<>(gdprRequest, HttpStatus.OK);
    }

    /**
     * Deactivates a user based on a GDPR request.
     *
     * @param userId the ID of the user to be deactivated
     * @return a ResponseEntity with a success message and HTTP status
     */
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Use the GdprRequestService to deactivate the user
        gdprRequestService.deactivateUser(user.getId());

        return new ResponseEntity<>("User has been deactivated successfully", HttpStatus.OK);
    }

    /**
     * Retrieves all pending GDPR requests.
     *
     * @return a ResponseEntity containing a list of pending GDPR requests and HTTP status
     */
    @GetMapping("/pending")
    public ResponseEntity<List<GdprRequest>> getPendingRequests() {
        List<GdprRequest> pendingRequests = gdprRequestService.getRequestsByStatus("pending", "Pending");
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    /**
     * Retrieves all processed (accepted or rejected) GDPR requests.
     *
     * @return a ResponseEntity containing a list of processed GDPR requests and HTTP status
     */
    @GetMapping("/processed")
    public ResponseEntity<List<GdprRequest>> getTreatedRequests() {
        List<GdprRequest> treatedRequests = gdprRequestService.getRequestsByStatus("processed", "Processed");
        return new ResponseEntity<>(treatedRequests, HttpStatus.OK);
    }

    /**
     * Processes a GDPR request, setting the response, status, and response date.
     * It also sends an email notification to the user.
     *
     * @param id the ID of the GDPR request to be processed
     * @param response the response to be set for the GDPR request
     * @return a ResponseEntity containing the processed GDPR request and HTTP status
     */
    @PutMapping("/{id}/processed")
    public ResponseEntity<GdprRequest> processRequest(@PathVariable int id, @RequestBody String response) {
        // Handle the case where the GdprRequest isn't found
        GdprRequest gdprRequest = gdprRequestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("GdprRequest not found with id: " + id));

        // Set the response, update the status, and response date
        gdprRequest.setResponse(response);
        gdprRequest.setResponsedate(LocalDateTime.now());
        gdprRequest.setStatus("Processed");

        // Save the request in the repository
        gdprRequestRepository.save(gdprRequest);

        // Send an email notification to the user
        String userEmail = gdprRequest.getUser().getEmail();
        String subject = "Your request has been processed";
        String text = "Hello,\n\n" +
                "Your request has been processed. Here is the response:\n" +
                response + "\n\n" +
                "Best regards,\nTrocApp Team";

        emailService.sendEmail(userEmail, subject, text);

        return ResponseEntity.ok(gdprRequest); // Return the processed request
    }
}
