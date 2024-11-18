package helha.trocappbackend.models;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int offeredObjectId;
    private int requestedObjectId;
    private Date proposalDate;
    private Date acceptanceDate;
    private String status;

    private float firstEvaluation;
    private float secondEvaluation;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;



    // Getters and Setters


}
