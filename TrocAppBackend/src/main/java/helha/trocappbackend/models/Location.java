package helha.trocappbackend.models;

public class Location {
    private double latitude;
    private double longitude;

    // Constructor
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLon() {
        return longitude;
    }

    public void setLon(double longitude) {
        this.longitude = longitude;
    }
}