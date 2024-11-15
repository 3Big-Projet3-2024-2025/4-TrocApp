package helha.trocappbackend.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private float rating;

    /*@ManyToMany
    @JoinTable(
            name = "USER_EXCHANGE",  // Nom de la table de jonction
            joinColumns = @JoinColumn(name = "USER_ID"),  // Colonne de clé étrangère pour User
            inverseJoinColumns = @JoinColumn(name = "EXCHANGE_ID")  // Colonne de clé étrangère pour Exchange
    )
    private Set<Exchange> exchanges;  // Liste des échanges auxquels l'utilisateur participe


    // Liste des échanges où l'utilisateur est l'initiateur (propose un échange)
    @OneToMany(mappedBy = "initiator", cascade = CascadeType.ALL)
    private List<Exchange> exchangesAsInitiator;

    // Liste des échanges où l'utilisateur est le récepteur (accepte un échange)
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Exchange> exchangesAsReceiver;

    // Liste des objets détenus par l'utilisateur pour l'échange
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;*/

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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