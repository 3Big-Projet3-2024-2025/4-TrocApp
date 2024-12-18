package helha.trocappbackend.controllers;


import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/users")

import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public Page<User> getUsers(Pageable p) {
        long start = System.currentTimeMillis();
        Page<User> users = userService.getUsers(p);
        long end = System.currentTimeMillis();
        System.out.println("Get Page - Time elapsed: " + (end - start) + "ms");
        return users;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        long start = System.currentTimeMillis();
        List<User> users = userService.getUsers();
        long end = System.currentTimeMillis();
        System.out.println("Get All - Time elapsed: " + (end - start) + "ms");
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)  // Si l'utilisateur existe, retourne un 200 OK avec l'utilisateur
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // Si non, retourne un 404 Not Found
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    @RequestMapping(path = "/update-user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);

    }

    @PostMapping("/{userId}/roles/{roleId}")
    public User addRoleToUser(@PathVariable int userId, @PathVariable int roleId) {
        return userService.addRoleToUser(userId, roleId);
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();  // Récupère tous les rôles depuis la base de données
    }

    @GetMapping
    @RequestMapping(path = "/getAllRoles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    @PutMapping
    public User assignRoles(
            @PathVariable int userId,
            @RequestBody List<Integer> roleIds
    ) {
        return userService.assignRolesToUser(userId, roleIds);
    }

    @GetMapping("/zipcodes")
    public List<Integer> getAllZipCodes() {
        return userService.getAllZipCodes();  // Appelle la méthode du service pour récupérer les codes postaux
    }


    @GetMapping("/cities")
    public List<String> getAllCities() {
        return userService.getAllCities();  // Appelle la méthode du service pour récupérer les codes postaux
    }
    @GetMapping("/streets")
    public List<String> getAllStreets() {
        return userService.getAllStreets();  // Appelle la méthode du service pour récupérer les codes postaux
    }

    @GetMapping("/numbers")
    public List<String> getAllNumbers() {
        return userService.getAllNumbers();  // Appelle la méthode du service pour récupérer les codes postaux
    }
}

