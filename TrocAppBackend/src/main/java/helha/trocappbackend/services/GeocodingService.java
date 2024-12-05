package helha.trocappbackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import helha.trocappbackend.models.Address;


@Service
public class GeocodingService {

    @Value("${nominatim.api.url}")
    private String nominatimApiUrl; // Nominatim API default URL

    private final RestTemplate restTemplate; // Field for HTTP requests

    // Constructor
    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Function transforming the address object into coordinates (longitude and latitude)
    public Address geocodeAddress(Address address) {
        String formattedAddress = formatAddress(address); // Address in JSON format
        String apiUrl = String.format("%s?q=%s&format=json", nominatimApiUrl, formattedAddress); // Build the API URL with the Nominatim base URL and the data in JSON format
        try {
            // Create and send the HTTP request to the Nominatim API
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            // Check that everything has gone well
            if (response.getStatusCode().is2xxSuccessful()) {
                String jsonResponse = response.getBody();

                // Parsing the JSON response to extract the coordinates with Jackson Library
                JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);

                // Checks if the array exists and is not empty, retrieves the coordinates
                if (jsonNode.isArray() && jsonNode.size() > 0) {
                    JsonNode firstResult = jsonNode.get(0);
                    double latitude = firstResult.path("lat").asDouble();
                    double longitude = firstResult.path("lon").asDouble();

                    // Set the coordinates (longitude and latitude)
                    address.setLatitude(latitude);
                    address.setLongitude(longitude);
                }
            }
        } catch (Exception e) {
            // Display exception details if necessary
            e.printStackTrace();
        }
        // Returns an object of type Address
        return address;
    }

    // Transform Address attributes into a string for JSON format
    public String formatAddress(Address address) {
        return address.getStreet() + ", " +
                address.getNumber() + ", " +
                address.getCity() + ", " +
                address.getZipCode();
    }
}
