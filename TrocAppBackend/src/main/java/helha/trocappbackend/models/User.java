package helha.trocappbackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Set;


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


    // Liste des objets détenus par l'utilisateur pour l'échange
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(mappedBy = "initiator")
    private List<Exchange> exchangesAsInitiator;

    @OneToMany(mappedBy = "receiver")
    private List<Exchange> exchangesAsReceiver;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "user_id"), // Colonne pour la clé étrangère vers User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Colonne pour la clé étrangère vers Role
    )
    private Set<Role> roles; // Un utilisateur peut avoir plusieurs rôles

    // Autres attributs, getters, et setters

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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