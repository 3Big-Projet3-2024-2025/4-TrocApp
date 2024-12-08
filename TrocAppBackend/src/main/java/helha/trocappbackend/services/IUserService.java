package helha.trocappbackend.services;

import helha.trocappbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    // Récupérer une liste paginée d'utilisateurs
    public Page<User> getUsers(Pageable page);

    // Récupérer une liste complète d'utilisateurs
    public List<User> getUsers();

    // Ajouter un nouvel utilisateur
    public User addUser(User user);

    // Mettre à jour un utilisateur existant
    public User updateUser(User user);

    // Supprimer un utilisateur par ID
    public void deleteUser(int id);

    // Récupérer un utilisateur par son ID
    Optional<User> getUserById(int id);

    // Ajouter un rôle à un utilisateur
    public User addRoleToUser(int userId, int roleId);
}
