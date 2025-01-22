package helha.trocappbackend.serviceTest;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.GdprRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GdprRequestServiceTest {

    @Mock
    private GdprRequestRepository gdprRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GdprRequestService gdprRequestService;

    private User user;
    private GdprRequest gdprRequest;

    /**
     * Set up the test data for the tests.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setActif(true);

        gdprRequest = new GdprRequest();
        gdprRequest.setId_gdprRequest(1);
        gdprRequest.setRequesttype("Delete my data");
        gdprRequest.setUser(user);
        gdprRequest.setConsent(true);
        gdprRequest.setJustification("I no longer want my data stored");
        gdprRequest.setRequestdate(LocalDateTime.now());
        gdprRequest.setStatus("Pending");
    }

    /**
     * Test the creation of a GDPR request.
     *
     * @throws IllegalArgumentException if any validation fails.
     */
    @Test
    @DisplayName("Test creating a GDPR request successfully")
    public void testCreateGdprRequest() {
        // Setup test data with the new user details
        User user = new User("Doha", "Assadi", "doha@gmail.com", "1234", true);
        user.setId(1); // Set ID manually for test

        // Mock repository behavior
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(gdprRequestRepository.save(any(GdprRequest.class))).thenAnswer(invocation -> {
            GdprRequest savedRequest = invocation.getArgument(0);
            savedRequest.setId_gdprRequest(1); // Set ID for the saved request
            return savedRequest;
        });

        // Test data
        String requestType = "Delete my data";
        boolean consent = true;
        String justification = "Request to delete data";

        // Execute the method being tested
        GdprRequest gdprRequest = gdprRequestService.createGdprRequest(requestType, user, consent, justification);

        // Verify the results
        assertNotNull(gdprRequest, "The GDPR request should be created successfully");
        assertEquals(requestType, gdprRequest.getRequesttype(), "Request type should match");
        assertEquals("Pending", gdprRequest.getStatus(), "Status should be 'Pending'");
        assertEquals(user, gdprRequest.getUser(), "User should match");
        assertEquals(justification, gdprRequest.getJustification(), "Justification should match");
        assertTrue(gdprRequest.isConsent(), "Consent should be true");
        assertNotNull(gdprRequest.getRequestdate(), "Request date should be set");

        // Verify that the repository methods were called
        verify(userRepository).existsById(user.getId());
        verify(gdprRequestRepository).save(any(GdprRequest.class));
    }

    /**
     * Test the creation of a GDPR request with invalid data (user does not exist).
     *
     * @throws IllegalArgumentException if user does not exist.
     */
    @Test
    @DisplayName("Test creating a GDPR request with non-existent user")
    void testCreateGdprRequestWithInvalidUser() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            gdprRequestService.createGdprRequest("Delete my data", user, true, "I no longer want my data stored");
        });

        assertEquals("User does not exist.", exception.getMessage(), "The exception message should match.");
    }

    /**
     * Test updating the status and response of an existing GDPR request.
     */
    @Test
    @DisplayName("Test updating a GDPR request status and response")
    void testUpdateGdprRequest() {
        when(gdprRequestRepository.findById(1)).thenReturn(Optional.of(gdprRequest));
        when(gdprRequestRepository.save(gdprRequest)).thenReturn(gdprRequest);

        GdprRequest updatedRequest = gdprRequestService.updateGdprRequest(1, "Processed", "Request has been processed.");

        assertNotNull(updatedRequest, "The updated request should not be null.");
        assertEquals("Processed", updatedRequest.getStatus(), "The status should be 'Processed'.");
        assertEquals("Request has been processed.", updatedRequest.getResponse(), "The response should be updated.");
    }

    /**
     * Test retrieving all GDPR requests by user.
     */
    @Test
    @DisplayName("Test retrieving GDPR requests by user")
    void testGetRequestsByUser() {
        when(gdprRequestRepository.findByUser(user)).thenReturn(List.of(gdprRequest));

        List<GdprRequest> requests = gdprRequestService.getRequestsByUser(user);

        assertNotNull(requests, "The list of requests should not be null.");
        assertEquals(1, requests.size(), "There should be one request.");
    }

    /**
     * Test retrieving all GDPR requests.
     */
    @Test
    @DisplayName("Test retrieving all GDPR requests")
    void testGetAllRequests() {
        when(gdprRequestRepository.findAll()).thenReturn(List.of(gdprRequest));

        List<GdprRequest> requests = gdprRequestService.getAllRequests();

        assertNotNull(requests, "The list of all requests should not be null.");
        assertEquals(1, requests.size(), "There should be one request.");
    }

    /**
     * Test deactivating a user.
     */
    @Test
    @DisplayName("Test deactivating a user account")
    void testDeactivateUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        gdprRequestService.deactivateUser(user.getId());

        assertFalse(user.getActif(), "The user should be deactivated.");
    }

    /**
     * Test retrieving GDPR requests by status.
     */
    @Test
    @DisplayName("Test retrieving GDPR requests by status")
    void testGetRequestsByStatus() {
        when(gdprRequestRepository.findAll()).thenReturn(List.of(gdprRequest));

        List<GdprRequest> requests = gdprRequestService.getRequestsByStatus("Pending");

        assertNotNull(requests, "The list of requests should not be null.");
        assertEquals(1, requests.size(), "There should be one request with status 'Pending'.");
    }
}
