package helha.trocappbackend.serviceTest;

import helha.trocappbackend.models.Address;
import helha.trocappbackend.services.GeocodingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GeocodingService.
 * Tests the functionality of geocoding addresses using mocked Nominatim API responses.
 */
class GeocodingServiceTest {

    @Mock
    private RestTemplate restTemplate; // Field for HTTP requests

    @InjectMocks
    private GeocodingService geocodingService;

    private Address address;

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and creates a sample address for testing.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize an example address object for testing
        address = new Address();
        address.setStreet("Rue de la Loi");
        address.setNumber("16");
        address.setCity("Bruxelles");
        address.setZipCode(1000);
    }

    /**
     * Tests successful geocoding when the API returns valid coordinates.
     * Verifies that the address object is updated with correct latitude and longitude.
     */
    @Order(1)
    @Test
    void testGeocodeAddress_ValidCoordinates() {
        // A mock response from the Nominatim API
        String mockApiResponse = "[{\"lat\": \"50.8467\", \"lon\": \"4.3499\"}]";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        // Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        // Verify that the coordinates are correctly set
        assertEquals(50.8467, result.getLatitude());
        assertEquals(4.3499, result.getLongitude());
    }

    /**
     * Tests handling of empty API responses.
     * Verifies that coordinates remain as NaN when no results are returned.
     */
    @Order(2)
    @Test
    void testGeocodeAddress_EmptyApiResponse() {
        // An empty array response from the Nominatim API
        String mockApiResponse = "[]";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        // Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        // The coordinates should remain Not A Number
        assertTrue(Double.isNaN(result.getLatitude()));
        assertTrue(Double.isNaN(result.getLongitude()));
    }

    /**
     * Tests handling of API errors.
     * Verifies that coordinates remain as NaN when API call fails.
     */
    @Order(3)
    @Test
    void testGeocodeAddress_ApiError() {
        // A API error
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("API Error"));

        // Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        // The coordinates should remain Not A Number
        assertTrue(Double.isNaN(result.getLatitude()));
        assertTrue(Double.isNaN(result.getLongitude()));
    }

    /**
     * Tests geocoding with invalid JSON response.
     * Verifies error handling for invalid JSON format.
     */
    @Order(4)
    @Test
    void testGeocodeAddress_InvalidJson() {
        // Prepare invalid JSON response
        String mockResponse = "invalid json";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponseEntity);

        // Execute geocoding
        Address result = geocodingService.geocodeAddress(address);

        // Verify NaN coordinates
        assertTrue(Double.isNaN(result.getLatitude()));
        assertTrue(Double.isNaN(result.getLongitude()));
    }

    /**
     * Tests address formatting functionality.
     * Verifies that the address is correctly formatted as a string for the API request.
     */
    @Order(5)
    @Test
    void testFormatAddress_FormattedStringCorrectly() {
        // Call the formatAddress function
        String formattedAddress = geocodingService.formatAddress(address);

        // The formatted address should match the expected format
        assertEquals("Rue de la Loi, 16, Bruxelles, 1000", formattedAddress);
    }
}