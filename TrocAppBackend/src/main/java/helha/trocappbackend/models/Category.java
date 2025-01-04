package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

/**
 * Represents a category in the system.
 * Each category can contain multiple items.
 */
@Entity
public class Category {

    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_category;

    /**
     * Name of the category.
     */
    private String name;

    /**
     * List of items associated with this category.
     * This relationship is ignored in the JSON responses (via the @JsonIgnore annotation).
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Item> items;

    /**
     * Default constructor.
     */
    public Category() {
    }

    /**
     * Constructor with category ID and name.
     *
     * @param id_category Unique identifier for the category.
     * @param name Name of the category.
     */

    public Category(int id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    /**
     * Constructor with only the category name.
     *
     * @param name Name of the category.
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier for the category.
     *
     * @return The unique identifier for the category.
     */
    public int getId_category() {
        return id_category;
    }

    /**
     * Gets the name of the category.
     *
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of items associated with this category.
     *
     * @return List of items in the category.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the unique identifier for the category.
     *
     * @param id_category The unique identifier for the category.
     */
    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    /**
     * Sets the name of the category.
     *
     * @param name The name of the category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of items associated with this category.
     *
     * @param items List of items to associate with the category.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
