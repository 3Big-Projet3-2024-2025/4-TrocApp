package helha.trocappbackend.controllers;

//import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.GdprRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gdpr")
public class GdprRequestController {

    /*@Autowired
    private GdprRequestService gdprRequestService;
    @Autowired
    private UserRepository userRepository;


    // Crate a GDPR request

    @PostMapping
    public ResponseEntity<GdprRequest> createRequest(@RequestBody GdprRequest gdprRequest) {
        GdprRequest savedRequest = gdprRequestService.createGdprRequest(gdprRequest);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    // Assign a GDPR request to an admin
    @PutMapping("/{gdprRequestId}/assign-admin/{adminUserId}")
    public ResponseEntity<GdprRequest> assignAdmin(@PathVariable int gdprRequestId, @PathVariable int adminUserId) {
        GdprRequest updatedRequest = gdprRequestService.assignAdminToRequest(gdprRequestId, adminUserId);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    // Update a GDPR request with status and response
    @PutMapping("/{gdprRequestId}")
    public ResponseEntity<GdprRequest> updateRequest(@PathVariable int gdprRequestId,
                                                     @RequestParam String status,
                                                     @RequestParam String response) {
        GdprRequest updatedRequest = gdprRequestService.updateGdprRequest(gdprRequestId, status, response);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    // Delete a GDPR request
    @DeleteMapping("/{gdprRequestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable int gdprRequestId) {
        gdprRequestService.deleteGdprRequest(gdprRequestId);
        return new ResponseEntity<>("GDPR request deleted successfully", HttpStatus.NO_CONTENT);
    }

    //Get all GDPR requests
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GdprRequest>> getRequestsByUser(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<GdprRequest> requests = gdprRequestService.getRequestsByUser(user);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //Get all GDPR requests managed by an admin
    @GetMapping("/admin/{adminUserId}")
    public ResponseEntity<List<GdprRequest>> getRequestsByAdmin(@PathVariable int adminUserId) {
        User adminUser = userRepository.findById(adminUserId).orElseThrow(() -> new RuntimeException("Admin user not found"));
        List<GdprRequest> requests = gdprRequestService.getRequestsByAdmin(adminUser);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }*/
}