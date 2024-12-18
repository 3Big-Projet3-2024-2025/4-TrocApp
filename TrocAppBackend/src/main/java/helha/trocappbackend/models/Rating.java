package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(1) // Minimum 1 étoile
    @Max(5) // Maximum 5 étoiles
    private int numberStars;

    private String comment;

    // Relation vers l'utilisateur qui POSTE l'évaluation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poster_id", nullable = false)
    private User poster;

    // Relation vers l'utilisateur qui REÇOIT l'évaluation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumberStars() { return numberStars; }
    public void setNumberStars(int numberStars) { this.numberStars = numberStars; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public User getPoster() { return poster; }
    public void setPoster(User poster) { this.poster = poster; }

    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
}
