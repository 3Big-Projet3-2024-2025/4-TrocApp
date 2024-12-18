package helha.trocappbackend.models;
import helha.trocappbackend.models.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
public class GdprRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_gdprRequest;

    private String requesttype; // Type of request (ex : "Delete my data")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestdate; // Date of the request (automatically generated)
    private String status; // Status of the request (Pending, Accepted, Rejected)
    private String response; // response of the administrator
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responsedate; // Date of the response

    private boolean consent; // Consent to delete data

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user") //User who made the request
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_admin_user") // Administrator who handled the request
    @JsonIgnore
    private User adminUser;

    public GdprRequest() {
        // Initialisation of the request date
        this.requestdate = LocalDateTime.now();
        this.status = "Pending"; // Statut initial
    }

    public GdprRequest(int id_gdprRequest, String requesttype, LocalDateTime requestdate,
                       String status, String response, LocalDateTime responsedate,
                       boolean consent, User user, User adminUser) {
        this.id_gdprRequest = id_gdprRequest;
        this.requesttype = requesttype;
        this.requestdate = requestdate != null ? requestdate : LocalDateTime.now(); // If the request date is null, we set it to the current date
        this.status = status != null ? status : "Pending";
        this.response = response;
        this.responsedate = responsedate;
        this.consent = consent;
        this.user = user;
        this.adminUser = adminUser;
    }

    // Getters and Setters

    public int getId_gdprRequest() {
        return id_gdprRequest;
    }

    public void setId_gdprRequest(int id_gdprRequest) {
        this.id_gdprRequest = id_gdprRequest;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public LocalDateTime getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(LocalDateTime requestdate) {
        this.requestdate = requestdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getResponsedate() {
        return responsedate;
    }

    public void setResponsedate(LocalDateTime responsedate) {
        this.responsedate = responsedate;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }
}