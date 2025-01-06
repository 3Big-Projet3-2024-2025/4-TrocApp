package helha.trocappbackend.serviceTest;

import helha.trocappbackend.repositories.AddressRepository;
import helha.trocappbackend.repositories.ItemRepository;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import helha.trocappbackend.repositories.AddressRepository;
import helha.trocappbackend.repositories.ItemRepository;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import helha.trocappbackend.models.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import helha.trocappbackend.models.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Test class for the UserService.
 */
/**
 * Test class for the UserService.
 */
@SpringBootTest
public class UserServiceTest {

    /**
     * Injected UserService for testing.
     */
    @InjectMocks
    private UserService userService;

    /**
     * Mocked UserRepository for testing.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Mocked RoleRepository for testing.
     */
    @Mock
    private RoleRepository roleRepository;

    /**
     * Mocked AddressRepository for testing.
     */
    @Mock
    private AddressRepository addressRepository;

    /**
     * Mocked ItemRepository for testing.
     */
    @Mock
    private ItemRepository itemRepository;

    /**
     * Mocked PasswordEncoder for testing
     */
    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * User variable to simulate a user during tests
     */
    private User testUser;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getUsers method.
     * Should return a list of all users.
     */
    @Test
    @DisplayName("Get users: should return a list of all users")
    void testGetUsers() {
        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests the addUser method.
     * Should successfully add a new user.
     */
    @Test
    @DisplayName("Add user: should successfully add a new user")
    void testAddUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the updateUser method when the user exists.
     * Should update an existing user.
     */
    @Test
    @DisplayName("Update user: should update an existing user")
    void testUpdateUser_UserExists() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);

        when(userRepository.existsById(1)).thenReturn(true);
        when(addressRepository.save(address)).thenReturn(address);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertNotNull(result);
        verify(userRepository, times(1)).existsById(1);
        verify(addressRepository, times(1)).save(address);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the updateUser method when the user does not exist.
     * Should throw an exception if the user does not exist.
     */
    @Test
    @DisplayName("Update user: should throw exception if user does not exist")
    void testUpdateUser_UserNotFound() {
        User user = new User();
        user.setId(1);

        when(userRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(user));
        assertEquals("User with ID 1 not found.", exception.getMessage());

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests the deleteUser method when the user exists.
     * Should delete an existing user.
     */
    @Test
    @DisplayName("Delete user: should delete an existing user")
    void testDeleteUser_UserExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        userService.deleteUser(1);

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    /**
     * Tests the deleteUser method when the user does not exist.
     * Should throw an exception if the user does not exist.
     */
    @Test
    @DisplayName("Delete user: should throw exception if user does not exist")
    void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1));
        assertEquals("User with ID 1 not found.", exception.getMessage());

        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).deleteById(anyInt());
    }

    /**
     * Tests the addRoleToUser method.
     * Should assign a role to a user.
     */
    @Test
    @DisplayName("Add role to user: should assign a role to a user")
    void testAddRoleToUser() {
        User user = new User();
        Role role = new Role();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addRoleToUser(1, 1);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the toggleBlockUser method.
     * Should block a user if they are not blocked.
     */
    @Test
    @DisplayName("Toggle block user: should block a user if they are not blocked")
    void testToggleBlockUser() {
        User user = new User();
        user.setId(1);
        user.setBlocked(false);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.toggleBlockUser(1);

        assertTrue(result.isBlocked());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the getUserItems method.
     * Should return a list of items owned by a user.
     */
    @Test
    @DisplayName("Get user items: should return a list of items owned by a user")
    void testGetUserItems() {
        User user = new User();
        user.setId(1);
        List<Item> items = List.of(new Item(), new Item());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(itemRepository.findByOwner(user)).thenReturn(items);

        List<Item> result = userService.getUserItems(1);

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findById(1);
        verify(itemRepository, times(1)).findByOwner(user);
    }

    /**
     * Tests for the verifyCurrentPassword method.
     * Verifies that the method correctly validates user passwords
     * and handles cases where the user is not found.
     */
    @Test
    @DisplayName("Should successfully verify correct password")
    void verifyCurrentPassword_WithValidCredentials_ShouldReturnTrue() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);

        // Act
        boolean result = userService.verifyCurrentPassword("testUser", "correctPassword");

        // Assert
        assertTrue(result);
        verify(userRepository).findByUsername("testUser");
        verify(passwordEncoder).matches("correctPassword", "encodedPassword");
    }

    @Test
    @DisplayName("Should fail verification with incorrect password")
    void verifyCurrentPassword_WithInvalidPassword_ShouldReturnFalse() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // Act
        boolean result = userService.verifyCurrentPassword("testUser", "wrongPassword");

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("testUser");
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
    }

    @Test
    @DisplayName("Should throw exception when user not found during password verification")
    void verifyCurrentPassword_WithNonexistentUser_ShouldThrowException() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.verifyCurrentPassword("nonexistentUser", "anyPassword"));
        verify(userRepository).findByUsername("nonexistentUser");
        verifyNoInteractions(passwordEncoder);
    }

    /**
     * Tests for the updateUserCredentials method.
     * Verifies that the method correctly updates user credentials
     * and handles various input scenarios appropriately.
     */
    @Test
    @DisplayName("Should successfully update both username and password")
    void updateUserCredentials_WithBothNewUsernameAndPassword_ShouldUpdateBoth() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.updateUserCredentials("testUser", "newPassword", "newUsername");

        // Assert
        assertEquals("newUsername", testUser.getUsername());
        assertEquals("newEncodedPassword", testUser.getPassword());
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should update only username when password is null")
    void updateUserCredentials_WithOnlyNewUsername_ShouldUpdateOnlyUsername() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.updateUserCredentials("testUser", null, "newUsername");

        // Assert
        assertEquals("newUsername", testUser.getUsername());
        assertEquals("encodedPassword", testUser.getPassword());
        verify(userRepository).save(testUser);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Should update only password when username is null")
    void updateUserCredentials_WithOnlyNewPassword_ShouldUpdateOnlyPassword() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.updateUserCredentials("testUser", "newPassword", null);

        // Assert
        assertEquals("testUser", testUser.getUsername());
        assertEquals("newEncodedPassword", testUser.getPassword());
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should throw exception when user not found during credentials update")
    void updateUserCredentials_WithNonexistentUser_ShouldThrowException() {
        // Initialize
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("encodedPassword");

        // Arrange
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.updateUserCredentials("nonexistentUser", "newPassword", "newUsername"));
        verify(userRepository).findByUsername("nonexistentUser");
        verifyNoInteractions(passwordEncoder);
    }
}