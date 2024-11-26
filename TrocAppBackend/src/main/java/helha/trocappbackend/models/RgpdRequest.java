package helha.trocappbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalDateTime;

@Entity
public class RgpdRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String requestType;
    private LocalDateTime requestDate;
    private String status;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getRequestType() {
        return requestType;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
