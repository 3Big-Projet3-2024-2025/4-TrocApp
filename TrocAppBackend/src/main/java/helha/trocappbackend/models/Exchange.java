package helha.trocappbackend.models;
import jakarta.persistence.*;
import java.sql.Date;

/**
 * Represents an exchange in the TrocApp system, where users can exchange objects.
 * @author Hayriye Dogan
 * @see helha.trocappbackend.models
 */
@Entity
public class Exchange {
    /**
     * Unique identifier for the exchange.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_exchange;
    /**
     * ID of the object offered in the exchange.
     */
    private int offeredObjectId;
    /**
     * ID of the object requested in the exchange.
     */
    private int requestedObjectId;
    /**
     * Date of a proposal for the exchange date.
     */
    private Date proposalDate;
    /**
     * Final date of the exchange date (proposalDate or a new one).
     */
    private Date acceptanceDate;
    /**
     * Current status of the exchange (e.g., "in progress", "declined").
     */
    private String status;
    /**
     * Evaluation score provided by the initiator of the exchange.
     */
    private float firstEvaluation;
    /**
     * Evaluation score provided by the receiver of the exchange.
     */
    private float secondEvaluation;
    /**
     * User who initiated the exchange.
     */
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private helha.trocappbackend.models.User initiator;
    /**
     * User who received the exchange proposal.
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private helha.trocappbackend.models.User receiver;


    /**
     * Constructs a new Exchange with the specified details.
     *
     * @param offeredObjectId ID of the object offered in the exchange.
     * @param requestedObjectId ID of the object requested in the exchange.
     * @param proposalDate Date when the exchange was proposed.
     * @param acceptanceDate Date when the exchange was accepted.
     * @param status Current status of the exchange.
     * @param firstEvaluation Evaluation score provided by the initiator.
     * @param secondEvaluation Evaluation score provided by the receiver.
     * @param initiator User who initiated the exchange.
     * @param receiver User who received the exchange proposal.
     */
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

    /**
     * Default constructor for Exchange.
     */
    public Exchange() {

    }

    // Getters and Setters
    /**
     * Gets the ID of the exchange.
     *
     * @return The exchange ID.
     */
    public int getId_exchange() {
        return id_exchange;
    }

    /**
     * Sets the ID of the exchange.
     *
     * @param id_exchange The exchange ID to set.
     */
    public void setId_exchange(int id_exchange) {
        this.id_exchange = id_exchange;
    }

    /**
     * Gets the ID of the offered object.
     *
     * @return The offered object ID.
     */
    public int getOfferedObjectId() {
        return offeredObjectId;
    }

    /**
     * Sets the ID of the offered object.
     *
     * @param offeredObjectId The offered object ID to set.
     */
    public void setOfferedObjectId(int offeredObjectId) {
        this.offeredObjectId = offeredObjectId;
    }

    /**
     * Gets the ID of the requested object.
     *
     * @return The requested object ID.
     */
    public int getRequestedObjectId() {
        return requestedObjectId;
    }

    /**
     * Sets the ID of the requested object.
     *
     * @param requestedObjectId The requested object ID to set.
     */
    public void setRequestedObjectId(int requestedObjectId) {
        this.requestedObjectId = requestedObjectId;
    }

    /**
     * Gets the proposal date of the exchange.
     *
     * @return The proposal date.
     */
    public Date getProposalDate() {
        return proposalDate;
    }

    /**
     * Sets the proposal date of the exchange.
     *
     * @param proposalDate The proposal date to set.
     */
    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    /**
     * Gets the acceptance date of the exchange.
     *
     * @return The acceptance date.
     */
    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    /**
     * Sets the acceptance date of the exchange.
     *
     * @param acceptanceDate The acceptance date to set.
     */
    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    /**
     * Gets the status of the exchange.
     *
     * @return The exchange status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the exchange.
     *
     * @param status The exchange status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the evaluation score provided by the initiator.
     *
     * @return The first evaluation score.
     */
    public float getFirstEvaluation() {
        return firstEvaluation;
    }

    /**
     * Sets the evaluation score provided by the initiator.
     *
     * @param firstEvaluation The first evaluation score to set.
     */
    public void setFirstEvaluation(float firstEvaluation) {
        this.firstEvaluation = firstEvaluation;
    }

    /**
     * Gets the evaluation score provided by the receiver.
     *
     * @return The second evaluation score.
     */
    public float getSecondEvaluation() {
        return secondEvaluation;
    }

    /**
     * Sets the evaluation score provided by the receiver.
     *
     * @param secondEvaluation The second evaluation score to set.
     */
    public void setSecondEvaluation(float secondEvaluation) {
        this.secondEvaluation = secondEvaluation;
    }

    /**
     * Gets the user who initiated the exchange.
     *
     * @return The initiator user.
     */
    public User getInitiator() {
        return initiator;
    }

    /**
     * Sets the user who initiated the exchange.
     *
     * @param initiator The initiator user to set.
     */
    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    /**
     * Gets the user who received the exchange proposal.
     *
     * @return The receiver user.
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Sets the user who received the exchange proposal.
     *
     * @param receiver The receiver user to set.
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
