package helha.trocappbackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import helha.trocappbackend.models.Address;

/**
 * Service class responsible for geocoding addresses using the Nominatim API.
 * This service converts physical addresses into geographical coordinates (latitude and longitude).
 */
@Service
public class GeocodingService {

    @Value("${nominatim.api.url}")
    private String nominatimApiUrl; // Nominatim API default URL

    private final RestTemplate restTemplate; // Field for HTTP requests

    /**
     * Constructs a new GeocodingService with the specified RestTemplate
     *
     * @param restTemplate The RestTemplate instance used for making HTTP requests to the Nominatim API
     */
    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Converts a physical address into geographical coordinates using the Nominatim API.
     * The method updates the provided Address object with the retrieved latitude and longitude.
     *
     * @param address The Address object containing the physical address details to be geocoded
     * @return The same Address object updated with latitude and longitude coordinates if successful,
     *         or with NaN values for coordinates if geocoding fails
     */
    public Address geocodeAddress(Address address) {
        address.setLatitude(Double.NaN); // Initialize the coordinates as Not A Number
        address.setLongitude(Double.NaN);

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

                    // Set the coordinates on the Address object
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

    /**
     * Formats an Address object into a string suitable for the Nominatim API query.
     * Combines street, number, city, and zip code into a comma-separated string.
     *
     * @param address The Address object to format
     * @return A formatted string containing the address components separated by commas
     */
    public String formatAddress(Address address) {
        return address.getStreet() + ", " +
                address.getNumber() + ", " +
                address.getCity() + ", " +
                address.getZipCode();
    }
}