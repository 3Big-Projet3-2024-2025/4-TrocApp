package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a GDPR request made by a user.
 * This entity stores information about the request type, status, response, consent, justification,
 * and the user who made the request.
 */
@Entity
public class GdprRequest {

    /**
     * The unique identifier of the GDPR request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_gdprRequest;

    /**
     * The type of the request (e.g., "Delete my data").
     */
    private String requesttype;

    /**
     * The date and time when the request was made.
     * The date is automatically generated when the request is created.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestdate;

    /**
     * The status of the request (e.g., "Pending", "Accepted", "Rejected").
     */
    private String status;

    /**
     * The response given by the administrator regarding the request.
     */
    private String response;

    /**
     * The date and time when the administrator responded to the request.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responsedate;

    /**
     * A flag indicating whether the user gave consent to delete their data.
     */
    private boolean consent;

    /**
     * A justification for the GDPR request (e.g., reason for the request).
     */
    private String justification;

    /**
     * The user who made the GDPR request.
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    /**
     * Default constructor for a GDPR request.
     * Sets the request date to the current time and status to "Pending".
     */
    public GdprRequest() {
        this.requestdate = LocalDateTime.now();
        this.status = "Pending";
    }

    /**
     * Constructs a GDPR request with specified fields.
     *
     * @param id_gdprRequest  The unique identifier of the request.
     * @param requesttype     The type of the request.
     * @param requestdate     The date when the request was made.
     * @param status          The status of the request.
     * @param response        The response given by the administrator.
     * @param responsedate    The date when the administrator responded.
     * @param consent         Whether the user gave consent to delete data.
     * @param justification   The justification for the request.
     * @param user            The user who made the request.
     */
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
        this.justification = justification;
        this.user = user;
    }

    // Getters and Setters

    /**
     * Gets the justification for the GDPR request.
     *
     * @return The justification for the request.
     */
    public String getJustification() {
        return justification;
    }

    /**
     * Sets the justification for the GDPR request.
     *
     * @param justification The justification to set.
     */
    public void setJustification(String justification) {
        this.justification = justification;
    }

    /**
     * Gets the user who made the GDPR request.
     *
     * @return The user associated with the request.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the GDPR request.
     *
     * @param user The user to associate with the request.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the unique identifier of the GDPR request.
     *
     * @return The ID of the request.
     */
    public int getId_gdprRequest() {
        return id_gdprRequest;
    }

    /**
     * Gets the type of the request.
     *
     * @return The request type (e.g., "Delete my data").
     */
    public String getRequesttype() {
        return requesttype;
    }

    /**
     * Gets the date and time when the request was made.
     *
     * @return The date of the request.
     */
    public LocalDateTime getRequestdate() {
        return requestdate;
    }

    /**
     * Gets the status of the request.
     *
     * @return The status of the request (e.g., "Pending", "Accepted", "Rejected").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the response given by the administrator.
     *
     * @return The administrator's response.
     */
    public String getResponse() {
        return response;
    }

    /**
     * Gets the date and time when the administrator responded to the request.
     *
     * @return The date of the response.
     */
    public LocalDateTime getResponsedate() {
        return responsedate;
    }

    /**
     * Checks if the user gave consent to delete their data.
     *
     * @return true if consent is given, false otherwise.
     */
    public boolean isConsent() {
        return consent;
    }

    /**
     * Sets the unique identifier of the GDPR request.
     *
     * @param id_gdprRequest The ID to set.
     */
    public void setId_gdprRequest(int id_gdprRequest) {
        this.id_gdprRequest = id_gdprRequest;
    }

    /**
     * Sets the type of the request.
     *
     * @param requesttype The request type to set.
     */
    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    /**
     * Sets the date and time when the request was made.
     *
     * @param requestdate The request date to set.
     */
    public void setRequestdate(LocalDateTime requestdate) {
        this.requestdate = requestdate;
    }

    /**
     * Sets the status of the request.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the response given by the administrator.
     *
     * @param response The response to set.
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Sets the date and time when the administrator responded to the request.
     *
     * @param responsedate The response date to set.
     */
    public void setResponsedate(LocalDateTime responsedate) {
        this.responsedate = responsedate;
    }

    /**
     * Sets whether the user gave consent to delete their data.
     *
     * @param consent The consent status to set.
     */
    public void setConsent(boolean consent) {
        this.consent = consent;
    }


}
