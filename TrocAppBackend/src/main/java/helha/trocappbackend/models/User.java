package helha.trocappbackend.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user") // Changez le nom ici
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private float rating;


    // Liste des objets détenus par l'utilisateur pour l'échange
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(mappedBy = "initiator")
    private List<Exchange> exchangesAsInitiator;

    @OneToMany(mappedBy = "receiver")
    private List<Exchange> exchangesAsReceiver;

    @ManyToMany
    @JoinTable(
            name = "user_role", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "user_id"), // Colonne pour la clé étrangère vers User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Colonne pour la clé étrangère vers Role
    )
    private Set<Role> roles; // Un utilisateur peut avoir plusieurs rôles

    // Autres attributs, getters, et setters
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

    /*public List<Exchange> getExchangesAsInitiator() {
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
    }*/
}