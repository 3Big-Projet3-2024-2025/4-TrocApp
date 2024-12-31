package helha.trocappbackend.models;


import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item;

    private String name;
    private String description;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String photo;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;


    public Item(String name, String description, String photo, Category category, boolean available, User owner) {
        //this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.category = category;
        this.available = available;
        this.owner = owner;
    }



    public Item() {}

    // Getters and setters

    public int getId() {
        return id_item;
    }

    public void setId(int id) {
        this.id_item = id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return available == item.available &&
                Objects.equals(id_item, item.id_item) &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                Objects.equals(photo, item.photo) &&
                Objects.equals(category, item.category) &&
                Objects.equals(owner, item.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_item, name, description, photo, category, available, owner);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id_item +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                '}';
    }
}
