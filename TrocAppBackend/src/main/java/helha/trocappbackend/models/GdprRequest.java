package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GdprRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_gdprRequest;

    private String requesttype; // Type of request (e.g., "Delete my data")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestdate; // Date of the request (automatically generated)

    private String status; // Status of the request (Pending, Accepted, Rejected)

    private String response; // Response from the administrator

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responsedate; // Date of the response

    private boolean consent; // Consent to delete data

    private String justification; // New field added for the justification

    @ManyToOne

    @JoinColumn(name = "id_user") // User who made the request
    private User user;

    public GdprRequest() {
        this.requestdate = LocalDateTime.now();
        this.status = "Pending";
    }

    // Constructor without the adminUser
    public GdprRequest(int id_gdprRequest, String requesttype, LocalDateTime requestdate,
                       String status, String response, LocalDateTime responsedate,
                       boolean consent, String justification, User user) {
        this.id_gdprRequest = id_gdprRequest;
        this.requesttype = requesttype;
        this.requestdate = requestdate != null ? requestdate : LocalDateTime.now();
        this.status = status != null ? status : "Pending";
        this.response = response;
        this.responsedate = responsedate;
        this.consent = consent;
        this.justification = justification; // Added here
        this.user = user;
    }

    // Getters and Setters
    // Other getters and setters remain the same
    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId_gdprRequest() {
        return id_gdprRequest;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public LocalDateTime getRequestdate() {
        return requestdate;
    }

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public LocalDateTime getResponsedate() {
        return responsedate;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setId_gdprRequest(int id_gdprRequest) {
        this.id_gdprRequest = id_gdprRequest;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public void setRequestdate(LocalDateTime requestdate) {
        this.requestdate = requestdate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setResponsedate(LocalDateTime responsedate) {
        this.responsedate = responsedate;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }
}
