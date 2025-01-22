package helha.trocappbackend.controllerTest;

import helha.trocappbackend.controllers.GdprRequestController;
import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.GdprRequestRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.GdprRequestService;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link GdprRequestController}.
 * This class contains tests for the endpoints of the GDPR request controller, including creating,
 * retrieving, processing requests, and deactivating users.
 */
@ExtendWith(MockitoExtension.class)
public class GdprRequestControllerTest {

    @InjectMocks
    private GdprRequestController gdprRequestController;

    /**
     * This is the controller that manages the endpoints related to GDPR requests.
     * The `@InjectMocks` annotation tells Mockito to inject the mock dependencies into this controller.
     */
    @Mock
    private GdprRequestService gdprRequestService;

    /**
     * This service handles the logic of managing GDPR requests.
     * The `@Mock` annotation is used to create a mock of this service so that we can simulate its behavior
     * during the tests.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * This repository is responsible for interacting with the database to perform CRUD operations on the User entity.
     * We mock this repository to avoid database interactions during the tests and simulate different scenarios.
     */
    @Mock
    private GdprRequestRepository gdprRequestRepository;

    /**
     * This service handles sending email notifications.
     * It is mocked here so that we can simulate sending emails without actually sending them during tests.
     */
    @Mock
    private EmailService emailService;

    /**
     * This service provides operations related to user management, such as retrieving or updating user information.
     * We mock this service to avoid interactions with actual data and focus on testing the controller's behavior.
     */
    @Mock
    private UserService userService;

    /**
     * Represents a mock user object used in the tests. This user object is typically used to simulate the user data
     * required by various methods in the controller, such as creating GDPR requests.
     */
    private User user;

    /**
     * Represents a mock GDPR request object used in the tests. This request is used to simulate the structure of a
     * GDPR request that is typically created or processed by the controller.
     */
    private GdprRequest gdprRequest;

    /**
     * Sets up the test environment by initializing the user and GDPR request objects.
     */
    @BeforeEach
    void setUp() {
        // Initialize user and gdprRequest objects for the tests
        user = new User("Doha", "Assadi", "doha@gmail.com", "1234", true);  // User: Doha Assadi
        user.setId(1);  // Set ID for the user

        gdprRequest = new GdprRequest();
        gdprRequest.setRequesttype("Delete my data");  // Request type
        gdprRequest.setUser(user);  // Associate the request with the user
        gdprRequest.setConsent(true);  // User consent given
        gdprRequest.setJustification("Request to delete data");  // Justification for the request
    }


    /**
     * Test creating a new GDPR request.
     * Verifies that when a new request is created, the response status is CREATED,
     * and the returned request matches the expected details.
     */
    @Test
    @DisplayName("Test creating a new GDPR request")
    void testCreateRequest() {
        // Arrange
        when(gdprRequestService.createGdprRequest(anyString(), any(), anyBoolean(), anyString())).thenReturn(gdprRequest);

        // Act
        ResponseEntity<GdprRequest> response = gdprRequestController.createRequest(gdprRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response status should be CREATED");
        assertNotNull(response.getBody(), "The response body should contain the created GDPR request");
        assertEquals("Delete my data", response.getBody().getRequesttype(), "The request type should match");
    }

    /**
     * Test getting all GDPR requests for a specific user.
     * Verifies that when requests are fetched for a user, the response status is OK,
     * and the list is not empty.
     */
    @Test
    @DisplayName("Test getting all GDPR requests for a user")
    void testGetRequestsByUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));
        when(gdprRequestService.getRequestsByUser(user)).thenReturn(java.util.List.of(gdprRequest));

        // Act
        ResponseEntity<java.util.List<GdprRequest>> response = gdprRequestController.getRequestsByUser(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertFalse(response.getBody().isEmpty(), "The list of requests should not be empty");
    }

    /**
     * Test processing a GDPR request.
     * Verifies that when a request is processed, the status is updated to 'Processed' and
     * the response message is correctly set.
     */
    @Test
    @DisplayName("Test processing a GDPR request")
    void testProcessRequest() {
        // Arrange
        when(gdprRequestRepository.findById(1)).thenReturn(java.util.Optional.of(gdprRequest));
        when(gdprRequestRepository.save(any(GdprRequest.class))).thenReturn(gdprRequest);

        String responseMessage = "Request processed successfully";
        // Act
        ResponseEntity<GdprRequest> response = gdprRequestController.processRequest(1, responseMessage);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertNotNull(response.getBody(), "The response body should contain the processed GDPR request");
        assertEquals("Processed", response.getBody().getStatus(), "The status should be 'Processed'");
        assertEquals(responseMessage, response.getBody().getResponse(), "The response message should match");
    }

    /**
     * Test deactivating a user based on a GDPR request.
     * Verifies that the user is deactivated successfully and the correct success message is returned.
     */
    @Test
    @DisplayName("Test deactivating a user based on a GDPR request")
    void testDeactivateUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));

        // Act
        ResponseEntity<String> response = gdprRequestController.deactivateUser(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertEquals("User has been deactivated successfully", response.getBody(), "The response message should match");
    }

    /**
     * Test getting all pending GDPR requests.
     * Verifies that when fetching pending requests, the response status is OK,
     * and the list of pending requests is not empty.
     */
    @Test
    @DisplayName("Test getting all pending GDPR requests")
    void testGetPendingRequests() {
        // Arrange
        when(gdprRequestService.getRequestsByStatus("pending", "Pending")).thenReturn(java.util.List.of(gdprRequest));

        // Act
        ResponseEntity<java.util.List<GdprRequest>> response = gdprRequestController.getPendingRequests();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertFalse(response.getBody().isEmpty(), "The list of pending requests should not be empty");
    }

    /**
     * Test getting all processed GDPR requests.
     * Verifies that when fetching processed requests, the response status is OK,
     * and the list of processed requests is not empty.
     */
    @Test
    @DisplayName("Test getting all processed GDPR requests")
    void testGetProcessedRequests() {
        // Arrange
        when(gdprRequestService.getRequestsByStatus("processed", "Processed")).thenReturn(java.util.List.of(gdprRequest));

        // Act
        ResponseEntity<java.util.List<GdprRequest>> response = gdprRequestController.getTreatedRequests();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertFalse(response.getBody().isEmpty(), "The list of processed requests should not be empty");
    }
}
