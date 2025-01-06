package helha.trocappbackend.controllerTest;

import helha.trocappbackend.controllers.UserController;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.services.IUserService;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for the UserController.
 */
@SpringBootTest
public class UserControllerTest {

    /**
     * Injected UserController for testing.
     */
    @InjectMocks
    private UserController userController;

    /**
     * Mocked IUserService for testing.
     */
    @Mock
    private IUserService userService;

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getUsers method with pagination.
     * Should return a page of users.
     */
    @Test
    @DisplayName("Get paginated users: should return a page of users")
    void testGetUsersPage() {
        Page<User> mockPage = new PageImpl<>(List.of(new User(), new User()));
        when(userService.getUsers(any(PageRequest.class))).thenReturn(mockPage);

        Page<User> result = userController.getUsers(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        verify(userService, times(1)).getUsers(any(PageRequest.class));
    }

    /**
     * Tests the getUsers method without pagination.
     * Should return a list of users.
     */
    @Test
    @DisplayName("Get all users: should return a list of users")
    void testGetAllUsers() {
        List<User> mockUsers = List.of(new User(), new User(), new User());
        when(userService.getUsers()).thenReturn(mockUsers);

        List<User> result = userController.getUsers();

        assertEquals(3, result.size());
        verify(userService, times(1)).getUsers();
    }

    /**
     * Tests the getUserById method when the user is found.
     * Should return the user.
     */
    @Test
    @DisplayName("Get user by ID: should return the user if found")
    void testGetUserById_Found() {
        User mockUser = new User();
        mockUser.setId(1);
        when(userService.getUserById(1)).thenReturn(Optional.of(mockUser));

        ResponseEntity<User> result = userController.getUserById(1);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(mockUser, result.getBody());
        verify(userService, times(1)).getUserById(1);
    }

    /**
     * Tests the getUserById method when the user is not found.
     * Should return 404 status.
     */
    @Test
    @DisplayName("Get user by ID: should return 404 if user not found")
    void testGetUserById_NotFound() {
        when(userService.getUserById(1)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.getUserById(1);

        assertEquals(404, result.getStatusCode().value());
        verify(userService, times(1)).getUserById(1);
    }

    /**
     * Tests the addUser method.
     * Should save and return the new user.
     */
    @Test
    @DisplayName("Add user: should save and return the new user")
    void testAddUser() {
        User mockUser = new User();
        when(userService.addUser(mockUser)).thenReturn(mockUser);

        User result = userController.addUser(mockUser);

        assertEquals(mockUser, result);
        verify(userService, times(1)).addUser(mockUser);
    }

    /**
     * Tests the updateUser method.
     * Should update and return the updated user.
     */
    @Test
    @DisplayName("Update user: should update and return the updated user")
    void testUpdateUser() {
        User mockUser = new User();
        when(userService.updateUser(mockUser)).thenReturn(mockUser);

        User result = userController.updateUser(mockUser);

        assertEquals(mockUser, result);
        verify(userService, times(1)).updateUser(mockUser);
    }

    /**
     * Tests the deleteUser method.
     * Should delete the user by ID.
     */
    @Test
    @DisplayName("Delete user: should delete the user by ID")
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1);

        userController.deleteUser(1);

        verify(userService, times(1)).deleteUser(1);
    }

    /**
     * Tests the addRoleToUser method.
     * Should assign a role to the user.
     */
    @Test
    @DisplayName("Add role to user: should assign a role to the user")
    void testAddRoleToUser() {
        User mockUser = new User();
        when(userService.addRoleToUser(1, 2)).thenReturn(mockUser);

        User result = userController.addRoleToUser(1, 2);

        assertEquals(mockUser, result);
        verify(userService, times(1)).addRoleToUser(1, 2);
    }

    /**
     * Tests the getAllRoles method.
     * Should return a list of roles.
     */
    @Test
    @DisplayName("Get all roles: should return a list of roles")
    void testGetAllRoles() {
        List<Role> mockRoles = List.of(new Role(), new Role());
        when(userService.getAllRoles()).thenReturn(mockRoles);

        List<Role> result = userController.getAllRoles();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllRoles();
    }

    /**
     * Tests the assignRoles method.
     * Should assign a list of roles to a user.
     */
    @Test
    @DisplayName("Assign roles: should assign a list of roles to a user")
    void testAssignRoles() {
        User mockUser = new User();
        List<Integer> roleIds = List.of(1, 2, 3);
        when(userService.assignRolesToUser(1, roleIds)).thenReturn(mockUser);

        User result = userController.assignRoles(1, roleIds);

        assertEquals(mockUser, result);
        verify(userService, times(1)).assignRolesToUser(1, roleIds);
    }

    /**
     * Tests the getAllZipCodes method.
     * Should return a list of zip codes.
     */
    @Test
    @DisplayName("Get all zip codes: should return a list of zip codes")
    void testGetAllZipCodes() {
        List<Integer> mockZipCodes = List.of(1000, 2000, 3000);
        when(userService.getAllZipCodes()).thenReturn(mockZipCodes);

        List<Integer> result = userController.getAllZipCodes();

        assertEquals(3, result.size());
        assertTrue(result.contains(2000));
        verify(userService, times(1)).getAllZipCodes();
    }

    /**
     * Tests the searchUsers method.
     * Should return users matching the query.
     */
    @Test
    @DisplayName("Search users: should return users matching the query")
    void testSearchUsers() {
        List<User> mockUsers = List.of(new User(), new User());
        when(userService.searchUsers("test")).thenReturn(mockUsers);

        List<User> result = userController.searchUsers("test");

        assertEquals(2, result.size());
        verify(userService, times(1)).searchUsers("test");
    }

    /**
     * Tests the toggleBlockUser method when the user is found.
     * Should block or unblock the user.
     */
    @Test
    @DisplayName("Toggle block user: should block or unblock the user if found")
    void testToggleBlockUser_Success() {
        User mockUser = new User();
        when(userService.toggleBlockUser(1)).thenReturn(mockUser);

        ResponseEntity<User> result = userController.toggleBlockUser(1);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(mockUser, result.getBody());
        verify(userService, times(1)).toggleBlockUser(1);
    }

    /**
     * Tests the toggleBlockUser method when the user is not found.
     * Should return 404 status.
     */
    @Test
    @DisplayName("Toggle block user: should return 404 if user not found")
    void testToggleBlockUser_NotFound() {
        when(userService.toggleBlockUser(1)).thenThrow(new EntityNotFoundException());

        ResponseEntity<User> result = userController.toggleBlockUser(1);

        assertEquals(404, result.getStatusCode().value());
        verify(userService, times(1)).toggleBlockUser(1);
    }
}