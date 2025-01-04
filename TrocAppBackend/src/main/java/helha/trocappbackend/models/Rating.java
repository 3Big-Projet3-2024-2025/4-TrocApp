package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Entity representing a rating in the application.
 */
@Entity
public class Rating {

    /**
     * The unique identifier for the rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The number of stars given in the rating.
     * Minimum value is 1 and maximum value is 5.
     */
    @Min(1)
    @Max(5)
    private int numberStars;

    /**
     * The comment associated with the rating.
     */
    private String comment;

    /**
     * The user who posted the rating.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poster_id", nullable = false)
    private User poster;

    /**
     * The user who received the rating.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    /**
     * The date and time when the rating was created.
     */
    @JoinColumn(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Default constructor.
     */
    public Rating() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Gets the unique identifier for the rating.
     *
     * @return the rating ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the rating.
     *
     * @param id the rating ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the number of stars given in the rating.
     *
     * @return the number of stars
     */
    public int getNumberStars() {
        return numberStars;
    }

    /**
     * Sets the number of stars given in the rating.
     *
     * @param numberStars the number of stars
     */
    public void setNumberStars(int numberStars) {
        this.numberStars = numberStars;
    }

    /**
     * Gets the comment associated with the rating.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment associated with the rating.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the user who posted the rating.
     *
     * @return the poster
     */
    public User getPoster() {
        return poster;
    }

    /**
     * Sets the user who posted the rating.
     *
     * @param poster the poster
     */
    public void setPoster(User poster) {
        this.poster = poster;
    }

    /**
     * Gets the user who received the rating.
     *
     * @return the receiver
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Sets the user who received the rating.
     *
     * @param receiver the receiver
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}