package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing users.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/users")
public class UserController {


    /**
     * Service for user-related operations.
     */
    @Autowired
    private IUserService userService;


    /**
     * Repository for role-related operations.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Get a paginated list of users.
     *
     * @param p the pagination information
     * @return a page of users
     */
    @GetMapping
    public Page<User> getUsers(Pageable p) {
        long start = System.currentTimeMillis();
        Page<User> users = userService.getUsers(p);
        long end = System.currentTimeMillis();
        System.out.println("Get Page - Time elapsed: " + (end - start) + "ms");
        return users;
    }

    /**
     * Get a list of all users.
     *
     * @return a list of users
     */
    @GetMapping("/all")
    public List<User> getUsers() {
        long start = System.currentTimeMillis();
        List<User> users = userService.getUsers();
        long end = System.currentTimeMillis();
        System.out.println("Get All - Time elapsed: " + (end - start) + "ms");
        return users;
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Add a new user.
     *
     * @param user the user to add
     * @return the added user
     */
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Update an existing user.
     *
     * @param user the user to update
     * @return the updated user
     */
    @PutMapping
    @RequestMapping(path = "/update-user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * Delete a user by ID.
     *
     * @param id the user ID
     */
    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    /**
     * Add a role to a user.
     *
     * @param userId the user ID
     * @param roleId the role ID
     * @return the updated user
     */
    @PostMapping("/{userId}/roles/{roleId}")
    public User addRoleToUser(@PathVariable int userId, @PathVariable int roleId) {
        return userService.addRoleToUser(userId, roleId);
    }

    /**
     * Get a list of all roles.
     *
     * @return a list of roles
     */
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    /**
     * Get a list of all roles (alternative endpoint).
     *
     * @return a list of roles
     */
    @GetMapping
    @RequestMapping(path = "/getAllRoles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    /**
     * Assign roles to a user.
     *
     * @param userId the user ID
     * @param roleIds the list of role IDs
     * @return the updated user
     */
    @PutMapping("/{userId}/roles")
    public User assignRoles(@PathVariable int userId, @RequestBody List<Integer> roleIds) {
        return userService.assignRolesToUser(userId, roleIds);
    }

    /**
     * Get a list of all zip codes.
     *
     * @return a list of zip codes
     */
    @GetMapping("/zipcodes")
    public List<Integer> getAllZipCodes() {
        return userService.getAllZipCodes();
    }

    /**
     * Get a list of all cities.
     *
     * @return a list of cities
     */
    @GetMapping("/cities")
    public List<String> getAllCities() {
        return userService.getAllCities();
    }

    /**
     * Get a list of all streets.
     *
     * @return a list of streets
     */
    @GetMapping("/streets")
    public List<String> getAllStreets() {
        return userService.getAllStreets();
    }

    /**
     * Get a list of all numbers.
     *
     * @return a list of numbers
     */
    @GetMapping("/numbers")
    public List<String> getAllNumbers() {
        return userService.getAllNumbers();
    }

    /**
     * Search for users by query.
     *
     * @param query the search query
     * @return a list of users matching the query
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String query) {
        return userService.searchUsers(query);
    }

    /**
     * Get items owned by a user.
     *
     * @param userId the user ID
     * @return a list of items, or 404 if user not found
     */
    @GetMapping("/{userId}/items")
    public ResponseEntity<List<Item>> getUserItems(@PathVariable int userId) {
        try {
            List<Item> items = userService.getUserItems(userId);
            return ResponseEntity.ok(items);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Toggle the block status of a user.
     *
     * @param userId the user ID
     * @return the updated user, or 404 if user not found
     */
    @PutMapping("/{userId}/block")
    public ResponseEntity<User> toggleBlockUser(@PathVariable int userId) {
        try {
            User updatedUser = userService.toggleBlockUser(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}