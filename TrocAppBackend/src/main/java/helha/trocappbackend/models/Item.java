package helha.trocappbackend.models;

<<<<<<< HEAD
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
=======
>>>>>>> db6fef4a2f880341729f97cdde25efb8279003b2
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> f0339e0 (Modification de la classe Item)
import jakarta.persistence.*;


@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String photo;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int ownerId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f0339e0 (Modification de la classe Item)

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", category=" + category +
                ", ownerId=" + ownerId +
                ", owner=" + owner +
                '}';
    }
<<<<<<< HEAD
=======
>>>>>>> db6fef4a2f880341729f97cdde25efb8279003b2
=======
>>>>>>> f0339e0 (Modification de la classe Item)
}
