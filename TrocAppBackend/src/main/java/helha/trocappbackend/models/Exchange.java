package helha.trocappbackend.models;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_exchange;

    private int offeredObjectId;
    private int requestedObjectId;
    private Date proposalDate;
    private Date acceptanceDate;
    private String status;

    private float firstEvaluation;
    private float secondEvaluation;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private helha.trocappbackend.models.User initiator;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private helha.trocappbackend.models.User receiver;

    public Exchange(int offeredObjectId, int requestedObjectId, Date proposalDate, Date acceptanceDate, String status, float firstEvaluation, float secondEvaluation, User initiator, User receiver) {
        this.offeredObjectId = offeredObjectId;
        this.requestedObjectId = requestedObjectId;
        this.proposalDate = proposalDate;
        this.acceptanceDate = acceptanceDate;
        this.status = status;
        this.firstEvaluation = firstEvaluation;
        this.secondEvaluation = secondEvaluation;
        this.initiator = initiator;
        this.receiver = receiver;
    }

    public Exchange() {

    }

    // Getters and Setters
    public int getId_exchange() {
        return id_exchange;
    }

    public void setId_exchange(int id_exchange) {
        this.id_exchange = id_exchange;
    }

    public int getOfferedObjectId() {
        return offeredObjectId;
    }

    public void setOfferedObjectId(int offeredObjectId) {
        this.offeredObjectId = offeredObjectId;
    }

    public int getRequestedObjectId() {
        return requestedObjectId;
    }

    public void setRequestedObjectId(int requestedObjectId) {
        this.requestedObjectId = requestedObjectId;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getFirstEvaluation() {
        return firstEvaluation;
    }

    public void setFirstEvaluation(float firstEvaluation) {
        this.firstEvaluation = firstEvaluation;
    }

    public float getSecondEvaluation() {
        return secondEvaluation;
    }

    public void setSecondEvaluation(float secondEvaluation) {
        this.secondEvaluation = secondEvaluation;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
