package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id_category;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Item> items;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
   public Category() {
    }

    public Category(int id_category, String name, User user) {
        this.id_category = id_category;
        this.name = name;
        this.user = user;
    }

    public Category(String name,User user) {
        this.name = name;
        this.user = user;
    }


    public Category(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
