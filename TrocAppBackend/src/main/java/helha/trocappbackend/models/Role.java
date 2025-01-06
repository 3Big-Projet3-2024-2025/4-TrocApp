package helha.trocappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entity representing a role in the application.
 */
@Entity
public class Role {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the role.
     */
    private String name;

    /**
     * The description of the role.
     */
    private String description;

    /**
     * The set of users assigned to this role.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     * Gets the unique identifier for the role.
     *
     * @return the role ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the role.
     *
     * @param id the role ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     *
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name the role name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the role.
     *
     * @return the role description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the role.
     *
     * @param description the role description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the set of users assigned to this role.
     *
     * @return the set of users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the set of users assigned to this role.
     *
     * @param users the set of users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}