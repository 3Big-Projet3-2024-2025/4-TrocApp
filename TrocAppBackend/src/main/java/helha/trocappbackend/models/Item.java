package helha.trocappbackend.models;


import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents an item that can be exchanged in the TrocApp system.
 * Each item has a name, description, photo, category, owner, and availability status.
 * @author Hayriye Dogan
 * @see helha.trocappbackend.models
 */
@Entity
public class Item {
    /**
     * Unique identifier for the item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item;
    /**
     * Name of the item.
     */
    private String name;
    /**
     * Description of the item.
     */
    private String description;
    /**
     * Photo of the item, stored as a large object (LOB).
     * Fetch type is set to LAZY to optimize performance.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String photo;
    /**
     * The category to which this item belongs.
     * The association is managed using a foreign key column `category_id`.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    /**
     * Indicates whether the item is available for exchange.
     */
    private boolean available;
    /**
     * The user who owns the item.
     * The association is managed using a foreign key column `user_id`.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Constructs an Item with the specified properties.
     *
     * @param name        the name of the item
     * @param description the description of the item
     * @param photo       the photo of the item
     * @param category    the category of the item
     * @param available   the availability status of the item
     * @param owner       the owner of the item
     */
    public Item(String name, String description, String photo, Category category, boolean available, User owner) {
        //this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.category = category;
        this.available = available;
        this.owner = owner;
    }


    /**
     * Default no-argument constructor.
     */
    public Item() {}



    // Getters and setters
    /**
     * Returns the unique identifier of the item.
     *
     * @return the item ID
     */
    public int getId() {
        return id_item;
    }

    /**
     * Sets the unique identifier of the item.
     *
     * @param id the new item ID
     */
    public void setId(int id) {
        this.id_item = id;
    }

    /**
     * Returns the name of the item.
     *
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the new item name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the item.
     *
     * @return the item description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description the new item description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the photo of the item.
     *
     * @return the item photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the photo of the item.
     *
     * @param photo the new item photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Returns the category of the item.
     *
     * @return the item category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the item.
     *
     * @param category the new item category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the owner of the item.
     *
     * @return the item owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the item.
     *
     * @param owner the new item owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Returns whether the item is available for exchange.
     *
     * @return {@code true} if the item is available, {@code false} otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the item.
     *
     * @param available the new availability status
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Compares this Item to the specified object for equality.
     * Two items are considered equal if their IDs, names, descriptions, photos,
     * categories, owners, and availability statuses are all equal.
     *
     * @param o the object to compare with this item
     * @return {@code true} if the specified object is equal to this item; {@code false} otherwise
     */
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

    /**
     * Computes the hash code for this Item object.
     * The hash code is based on the item's ID, name, description, photo, category, availability status, and owner.
     *
     * @return the hash code for this item
     */
    @Override
    public int hashCode() {
        return Objects.hash(id_item, name, description, photo, category, available, owner);
    }

    /**
     * Returns a string representation of the Item object.
     * The string includes the item's ID, name, description, photo, category, and owner.
     *
     * @return a string representation of the item
     */
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
