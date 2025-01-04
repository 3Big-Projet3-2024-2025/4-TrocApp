package helha.trocappbackend.controllers;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.GdprRequestService;
import helha.trocappbackend.services.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    // Create a GDPR request
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

    // Get all GDPR requests by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GdprRequest>> getRequestsByUser(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<GdprRequest> requests = gdprRequestService.getRequestsByUser(user);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // Get all GDPR requests
    @GetMapping
    public ResponseEntity<List<GdprRequest>> getAllRequests() {
        List<GdprRequest> requests = gdprRequestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // Get a GDPR request by ID
    @GetMapping("/{gdprRequestId}")
    public ResponseEntity<GdprRequest> getRequestById(@PathVariable int gdprRequestId) {
        GdprRequest gdprRequest = gdprRequestRepository.findById(gdprRequestId)
                .orElseThrow(() -> new NoSuchElementException("GdprRequest not found with id: " + gdprRequestId));
        return new ResponseEntity<>(gdprRequest, HttpStatus.OK);
    }

    // Deactivate user based on a GDPR request
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Use the GdprRequestService to deactivate the user
        gdprRequestService.deactivateUser(user.getId());

        return new ResponseEntity<>("User has been deactivated successfully", HttpStatus.OK);
    }

    // Get all untreated GDPR requests (Pending)
    @GetMapping("/pending")
    public ResponseEntity<List<GdprRequest>> getPendingRequests() {
        List<GdprRequest> pendingRequests = gdprRequestService.getRequestsByStatus("pending", "Pending");
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    // Get all treated GDPR requests (Accepted, Rejected)
    @GetMapping("/processed")
    public ResponseEntity<List<GdprRequest>> getTreatedRequests() {
        List<GdprRequest> treatedRequests = gdprRequestService.getRequestsByStatus("processed", "Processed");
        return new ResponseEntity<>(treatedRequests, HttpStatus.OK);
    }

    // Process a GDPR request
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
        String subject = "Votre demande GDPR a été traitée";
        String text = "Bonjour,\n\n" +
                "Votre demande GDPR a été traitée. Voici la réponse :\n" +
                response + "\n\n" +
                "Cordialement,\nL'équipe TrocApp";

        emailService.sendEmail(userEmail, subject, text);

        return ResponseEntity.ok(gdprRequest); // Return the processed request
    }


}