package helha.trocappbackend.models;

import jakarta.persistence.*;


@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String photo;
    private String category;
    private int ownerId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // Getters and setters
}
