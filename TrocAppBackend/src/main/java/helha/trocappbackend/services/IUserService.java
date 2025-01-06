package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface for user service operations.
 */
public interface IUserService {

    /**
     * Retrieves a paginated list of users.
     *
     * @param page the pagination information
     * @return a paginated list of users
     */
    Page<User> getUsers(Pageable page);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users
     */
    List<User> getUsers();

    /**
     * Adds a new user.
     *
     * @param user the user to add
     * @return the added user
     */
    User addUser(User user);

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return the updated user
     */
    User updateUser(User user);

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteUser(int id);

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> getUserById(int id);

    /**
     * Adds a role to a user.
     *
     * @param userId the ID of the user
     * @param roleId the ID of the role
     * @return the updated user
     */
    User addRoleToUser(int userId, int roleId);

    /**
     * Retrieves all roles of a user.
     *
     * @param id the ID of the user
     * @return a list of roles of the user
     */
    List<Role> getRoles(int id);

    /**
     * Assigns roles to a user.
     *
     * @param userId the ID of the user
     * @param roleIds the IDs of the roles
     * @return the updated user
     */
    User assignRolesToUser(int userId, List<Integer> roleIds);

    /**
     * Retrieves all roles.
     *
     * @return a list of all roles
     */
    List<Role> getAllRoles();

    /**
     * Retrieves all zip codes.
     *
     * @return a list of all zip codes
     */
    List<Integer> getAllZipCodes();

    /**
     * Retrieves all streets.
     *
     * @return a list of all streets
     */
    List<String> getAllStreets();

    /**
     * Retrieves all numbers.
     *
     * @return a list of all numbers
     */
    List<String> getAllNumbers();

    /**
     * Retrieves all cities.
     *
     * @return a list of all cities
     */
    List<String> getAllCities();

    //void updateUserCredentials(String username, String newPassword, String newUsername);

    /**
     * Searches for users by a query.
     *
     * @param query the search query
     * @return a list of users matching the query
     */
    List<User> searchUsers(String query);

    /**
     * Retrieves the items of a user.
     *
     * @param userId the ID of the user
     * @return a list of items owned by the user
     */
    List<Item> getUserItems(int userId);

    /**
     * Toggles the blocked status of a user.
     *
     * @param userId the ID of the user
     * @return the updated user
     */
    User toggleBlockUser(int userId);
}