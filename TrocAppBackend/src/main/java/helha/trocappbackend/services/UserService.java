package helha.trocappbackend.services;

import helha.trocappbackend.models.Address;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.repositories.ItemRepository;
import helha.trocappbackend.repositories.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Primary
public class UserService implements IUserService {

    /**
     * List of users.
     */
    private List<User> users = new ArrayList<>();


    /**
     * Repository for user-related operations.
     */
    @Autowired
    private UserRepository userRepository;



    /**
     * Repository for role-related operations.
     */
    @Autowired
    private RoleRepository roleRepository;


    /**
     * Repository for address-related operations.
     */
    @Autowired
    private AddressRepository addressRepository;



    /**
     * Repository for item-related operations.
     */
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieves a paginated list of users.
     *
     * @param page the pagination information
     * @return a paginated list of users
     */
    @Override
    public Page<User> getUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Adds a new user.
     *
     * @param user the user to add
     * @return the added user
     */
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return the updated user
     */
    @Override
    public User updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            if (user.getAddress() != null) {
                Address updatedAddress = addressRepository.save(user.getAddress());
                user.setAddress(updatedAddress);
            }
            return userRepository.save(user);
        }
        throw new RuntimeException("User with ID " + user.getId() + " not found.");
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return an Optional containing the user if found, or empty if not found
     */
    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Adds a role to a user.
     *
     * @param userId the ID of the user
     * @param roleId the ID of the role
     * @return the updated user
     */
    @Override
    public User addRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found."));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found."));
        user.addRole(role);
        return userRepository.save(user);
    }

    /**
     * Retrieves all roles.
     *
     * @return a list of all roles
     */
    @Override
    public List<Role> getRoles(int id) {
        return roleRepository.findAll();
    }

    /**
     * Retrieves all roles.
     *
     * @return a list of all roles
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Retrieves all zip codes.
     *
     * @return a list of all zip codes
     */
    public List<Integer> getAllZipCodes() {
        return addressRepository.findAll().stream()
                .map(Address::getZipCode)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all streets.
     *
     * @return a list of all streets
     */
    public List<String> getAllStreets() {
        return addressRepository.findAll().stream()
                .map(Address::getStreet)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all numbers.
     *
     * @return a list of all numbers
     */
    @Override
    public List<String> getAllNumbers() {
        return addressRepository.findAll().stream()
                .map(Address::getNumber)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all cities.
     *
     * @return a list of all cities
     */
    @Override
    public List<String> getAllCities() {
        return addressRepository.findAll().stream()
                .map(Address::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Assigns roles to a user.
     *
     * @param userId the ID of the user
     * @param roleIds the IDs of the roles
     * @return the updated user
     */
    @Override
    public User assignRolesToUser(int userId, List<Integer> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getRoles().clear();
        for (int roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            user.addRole(role);
        }
        return userRepository.save(user);
    }

    /**
     * Searches for users by a query.
     *
     * @param query the search query
     * @return a list of users matching the query
     */
    @Override
    public List<User> searchUsers(String query) {
        String lowercaseQuery = query.toLowerCase();
        return userRepository.findAll().stream()
                .filter(user ->
                        user.getFirstName().toLowerCase().contains(lowercaseQuery) ||
                                user.getLastName().toLowerCase().contains(lowercaseQuery))
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the items of a user.
     *
     * @param userId the ID of the user
     * @return a list of items owned by the user
     */
    @Transactional(readOnly = true)
    @Override
    public List<Item> getUserItems(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return itemRepository.findByOwner(user);
    }

    /**
     * Toggles the blocked status of a user.
     *
     * @param userId the ID of the user
     * @return the updated user
     */
    @Override
    public User toggleBlockUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setBlocked(!user.isBlocked());
        return userRepository.save(user);
    }

    /**
     * Verifies if the current password provided matches the password stored in the database for the given username.
     *
     * @param username        the username of the user whose password needs to be verified.
     * @param currentPassword the current password provided by the user.
     * @return {@code true} if the provided password matches the stored password, {@code false} otherwise.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public boolean verifyCurrentPassword(String username, String currentPassword)
    {
        // Find the registered user by his username and check if the user exists
        User foundUser = userRepository.findByUsername(username);

        if (foundUser == null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        // Check password passed with database password
        return passwordEncoder.matches(currentPassword, foundUser.getPassword());
    }

    /**
     * Updates the credentials (password and/or username) of a user identified by their current username.
     *
     * @param username    the current username of the user whose credentials need to be updated.
     * @param newPassword the new password to set. If {@code null} or empty, the password is not updated.
     * @param newUsername the new username to set. If {@code null} or empty, the username is not updated.
     * @throws UsernameNotFoundException if no user is found with the given username.
     * */
    @Override
    public void updateUserCredentials(String username, String newPassword, String newUsername)
    {
        // Find the registered user by his username and check if the user exists
        User foundUser = userRepository.findByUsername(username);

        if (foundUser == null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        // Update password after checking new password is not empty
        if (newPassword != null && !newPassword.isEmpty())
        {
            foundUser.setPassword(passwordEncoder.encode(newPassword));
        }

        // Update the username after checking that the new one is not empty
        if (newUsername != null && !newUsername.isEmpty()) {
            foundUser.setUsername(newUsername);
        }

        // Sauvegarder les modifications
        userRepository.save(foundUser);
    }

}
