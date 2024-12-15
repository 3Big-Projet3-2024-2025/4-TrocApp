package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberStars;
    private String comment;

    // Relation vers l'utilisateur qui POSTE l'évaluation
    @ManyToOne
    @JoinColumn(name = "poster_id", nullable = false)
    private User poster;

    // Relation vers l'utilisateur qui REÇOIT l'évaluation
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumberStars() { return numberStars; }
    public void setNumberStars(int numberStars) { this.numberStars = numberStars; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public User getPoster() { return poster; }
    public void setPoster(User poster) { this.poster = poster; }

    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
}
