package helha.trocappbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_category;
    private String name;
    //private List<Item> items;

    public Category() {
    }

    public Category(int id, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id_category = id_category;
    }

    public void setName(String name) {
        this.name = name;
    }

}
