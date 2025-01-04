package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a user in the application.
 */
@JsonIgnoreProperties({ "postedRatings", "receivedRatings", "items", "exchangesAsInitiator", "exchangesAsReceiver"})
@Entity
@Table(name = "app_user")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;
    private Boolean actif;

    /**
     * The address of the user.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties("users")
    private Address address;

    /**
     * The rating of the user.
     */
    private float rating;

    /**
     * Indicates whether the user is blocked.
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean blocked = false;

    /**
     * The list of ratings posted by the user.
     */
    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL)
    private List<Rating> postedRatings;

    /**
     * The list of ratings received by the user.
     */
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Rating> receivedRatings;

    /**
     * The list of items owned by the user for exchange.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

    /**
     * The list of exchanges initiated by the user.
     */
    @OneToMany(mappedBy = "initiator")
    private List<Exchange> exchangesAsInitiator;

    /**
     * The list of exchanges received by the user.
     */
    @OneToMany(mappedBy = "receiver")
    private List<Exchange> exchangesAsReceiver;

    /**
     * The list of categories associated with the user.
     */


    /**
     * The set of roles assigned to the user.
     */
    @JsonIgnoreProperties("roles")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Constructor with parameters.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email address of the user
     * @param password the password of the user
     */
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public User(String firstName, String lastName, String email, String password, boolean actif) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.actif = actif;
    }


    // Autres attributs, getters, et setters

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user ID
     */
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

    /**
     * Sets the unique identifier for the user.
     *
     * @param id the user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the address of the user.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the rating of the user.
     *
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets the rating of the user.
     *
     * @param rating the rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Checks if the user is blocked.
     *
     * @return true if the user is blocked, false otherwise
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets the block status of the user.
     *
     * @param blocked the block status
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Gets the list of ratings posted by the user.
     *
     * @return the list of posted ratings
     */
    public List<Rating> getPostedRatings() {
        return postedRatings;
    }

    /**
     * Sets the list of ratings posted by the user.
     *
     * @param postedRatings the list of posted ratings
     */
    public void setPostedRatings(List<Rating> postedRatings) {
        this.postedRatings = postedRatings;
    }

    /**
     * Gets the list of ratings received by the user.
     *
     * @return the list of received ratings
     */
    public List<Rating> getReceivedRatings() {
        return receivedRatings;
    }

    /**
     * Sets the list of ratings received by the user.
     *
     * @param receivedRatings the list of received ratings
     */
    public void setReceivedRatings(List<Rating> receivedRatings) {
        this.receivedRatings = receivedRatings;
    }

    /*public List<GdprRequest> getGdprRequests() {
        return gdprRequests;
    }*/

    /**
     * Gets the list of items owned by the user for exchange.
     *
     * @return the list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the list of items owned by the user for exchange.
     *
     * @param items the list of items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Gets the list of exchanges initiated by the user.
     *
     * @return the list of exchanges as initiator
     */
    public List<Exchange> getExchangesAsInitiator() {
        return exchangesAsInitiator;
    }

    /**
     * Sets the list of exchanges initiated by the user.
     *
     * @param exchangesAsInitiator the list of exchanges as initiator
     */
    public void setExchangesAsInitiator(List<Exchange> exchangesAsInitiator) {
        this.exchangesAsInitiator = exchangesAsInitiator;
    }

    /**
     * Gets the list of exchanges received by the user.
     *
     * @return the list of exchanges as receiver
     */
    public List<Exchange> getExchangesAsReceiver() {
        return exchangesAsReceiver;
    }

    /**
     * Sets the list of exchanges received by the user.
     *
     * @param exchangesAsReceiver the list of exchanges as receiver
     */
    public void setExchangesAsReceiver(List<Exchange> exchangesAsReceiver) {
        this.exchangesAsReceiver = exchangesAsReceiver;
    }

    /**
     * Gets the list of categories associated with the user.
     *
     * @return the list of categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets the list of categories associated with the user.
     *
     * @param categories the list of categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Gets the set of roles assigned to the user.
     *
     * @return the set of roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    /**
     * Adds a role to the user.
     *
     * @param role the role to add
     */
    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Removes a role from the user.
     *
     * @param role the role to remove
     */
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}