package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.JWT;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.IUserService;
import helha.trocappbackend.services.UserService;
import helha.trocappbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authentication and user account operations.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user with the provided username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return a {@link ResponseEntity} containing the JWT tokens if authentication is successful,
     * or an error message with a BAD_REQUEST status if authentication fails.
     */
    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            User user = (User) auth.getPrincipal();
            helha.trocappbackend.models.User userInternal = userRepository.findByUsername(username);
            JWT jwt = new JWT(jwtUtils.generateAccessToken(user, userInternal),
                    jwtUtils.generateRefreshToken(user, userInternal));
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }

    /**
     * Creates a new user account with the provided details.
     *
     * @param user the details of the user to be created
     * @return a {@link ResponseEntity} containing the created user if successful,
     * or an error message if the username is already taken or an internal error occurs.
     */
    @PostMapping("create_account")
    public ResponseEntity<Object> createAccount(@RequestBody helha.trocappbackend.models.User user) {
        try {
            // Check if a user with the same username already exists
            helha.trocappbackend.models.User userExists = userRepository.findByUsername(user.getUsername());

            if (userExists != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("The username is already taken. Please choose another one.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role roleDefault = roleRepository.findByName("user")
                    .orElseThrow(() -> new RuntimeException("Role with name ‘user’ not found."));

            user.addRole(roleDefault);

            // Save the user
            helha.trocappbackend.models.User savedUser = userService.addUser(user);

            // Return the created user
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the user: " + e.getMessage());
        }
    }
}