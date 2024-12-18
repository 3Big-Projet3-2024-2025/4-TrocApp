package helha.trocappbackend.services;

import org.springframework.stereotype.Service;

@Service
public class GeocodingService {

    private final String GEOCODING_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

    /*private final RestTemplate restTemplate;

    //private final RestTemplate restTemplate;

    /*public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Location getCoordinatesFromAddress(String address) {
        // Construct the URL for the geocoding service
        String url = GEOCODING_URL + address;

        // Make the API request to get coordinates based on address
        Location[] locations = restTemplate.getForObject(url, Location[].class);

        if (locations != null && locations.length > 0) {
            // Return the first location found
            return new Location(locations[0].getLatitude(), locations[0].getLongitude());
        }

        return null;  // Return null if no location was found
    }

    // Service method to convert address to location and set it on the Object
    public Object createObjectWithLocation(Object object) {
        String address = object.getAddress();
        Location location = getCoordinatesFromAddress(address);
        if (location != null) {
            object.setLocation(location);  // Set the location in the Object entity
        }
        return object;  // Return the object with the geocoded location
    }*/
}
