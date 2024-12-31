package helha.trocappbackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"password", "postedRatings", "receivedRatings", "items", "exchangesAsInitiator", "exchangesAsReceiver"})
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    //@JsonManagedReference
    @JsonIgnoreProperties("users")
    private Address address;

    private float rating;

    // Liste des évaluations postées par l'utilisateur
    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL)
    private List<Rating> postedRatings;

    // Liste des évaluations reçues par l'utilisateur
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Rating> receivedRatings;


    // Liste des objets détenus par l'utilisateur pour l'échange
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(mappedBy = "initiator")
    private List<Exchange> exchangesAsInitiator;

    @OneToMany(mappedBy = "receiver")
    private List<Exchange> exchangesAsReceiver;
    /*@OneToMany(mappedBy = "user")
    private List <GdprRequest> gdprRequests ;*/
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;
    //@JsonManagedReference
    @JsonIgnoreProperties("roles")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "user_id"), // Colonne pour la clé étrangère vers User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Colonne pour la clé étrangère vers Role
    )

    private Set<Role> roles; // Un utilisateur peut avoir plusieurs rôles
    //Doha
    // Default constructor
    public User() {}

    // Constructor with parameters
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Autres attributs, getters, et setters

    public Set<Role> getRoles() {
        return roles;
    }





    /*public void addRole(Role role) {
        this.roles.add(role);
    }*/

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Rating> getPostedRatings() { return postedRatings; }
    public void setPostedRatings(List<Rating> postedRatings) { this.postedRatings = postedRatings; }

    public List<Rating> getReceivedRatings() { return receivedRatings; }
    public void setReceivedRatings(List<Rating> receivedRatings) { this.receivedRatings = receivedRatings; }


    /*public List<GdprRequest> getGdprRequests() {
        return gdprRequests;
    }*/

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /*public void setGdprRequests(List<GdprRequest> gdprRequests) {
        this.gdprRequests = gdprRequests;
    }*/


    public List<Exchange> getExchangesAsInitiator() {
        return exchangesAsInitiator;
    }

    public void setExchangesAsInitiator(List<Exchange> exchangesAsInitiator) {
        this.exchangesAsInitiator = exchangesAsInitiator;
    }

    public List<Exchange> getExchangesAsReceiver() {
        return exchangesAsReceiver;
    }

    public void setExchangesAsReceiver(List<Exchange> exchangesAsReceiver) {
        this.exchangesAsReceiver = exchangesAsReceiver;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}