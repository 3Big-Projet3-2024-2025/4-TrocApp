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
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Item> items;

    @ManyToOne
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

    public void setId(int id) {
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
