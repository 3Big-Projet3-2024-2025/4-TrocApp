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

class GeocodingServiceTest {

    // Field for HTTP requests
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GeocodingService geocodingService;

    private Address address;

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

    @Order(1)
    @Test
    void geocodeAddress_shouldReturnCoordinates_whenApiReturnsValidResponse() {
        //A mock response from the Nominatim API
        String mockApiResponse = "[{\"lat\": \"50.8467\", \"lon\": \"4.3499\"}]";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        //Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        //Verify that the coordinates are correctly set
        assertEquals(50.8467, result.getLatitude());
        assertEquals(4.3499, result.getLongitude());
    }

    @Order(2)
    @Test
    void geocodeAddress_shouldHandleEmptyApiResponse() {
        //An empty array response from the Nominatim API
        String mockApiResponse = "[]";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        //Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        //The coordinates should remain Not A Number
        assertTrue(Double.isNaN(result.getLatitude()));
        assertTrue(Double.isNaN(result.getLongitude()));
    }

    @Order(3)
    @Test
    void geocodeAddress_shouldHandleApiError() {
        //A API error
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("API Error"));

        //Call the geocodeAddress function
        Address result = geocodingService.geocodeAddress(address);

        //The coordinates should remain Not A Number
        assertTrue(Double.isNaN(result.getLatitude()));
        assertTrue(Double.isNaN(result.getLongitude()));
    }

    @Order(4)
    @Test
    void formatAddress_shouldReturnCorrectlyFormattedString() {
        // Call the formatAddress function
        String formattedAddress = geocodingService.formatAddress(address);

        // The formatted address should match the expected format
        assertEquals("Rue de la Loi, 16, Bruxelles, 1000", formattedAddress);
    }
}
