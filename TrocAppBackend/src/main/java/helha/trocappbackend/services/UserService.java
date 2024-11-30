package helha.trocappbackend.services;


import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserService implements IUserService{
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> getUsers(Pageable page) {
        // Utilise le repository pour récupérer une liste paginée d'utilisateurs
        return userRepository.findAll(page);
    }

    @Override
    public List<User> getUsers() {
        // Retourne tous les utilisateurs depuis la base de données
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        // Enregistre un nouvel utilisateur dans la base de données
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // Vérifie si l'utilisateur existe, puis le met à jour
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user); // Save fait un update si l'entité existe déjà
        }
        throw new RuntimeException("Utilisateur avec ID " + user.getId() + " introuvable.");
    }

    @Override
    public void deleteUser(int id) {
        // Supprime un utilisateur par ID s'il existe
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Utilisateur avec ID " + id + " introuvable.");
        }
    }


    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);  // Retourne un Optional<User>
    }

    /*
    @Override
    public User addRoleToUser(int userId, String role) {
        // Ajoute un rôle à l'utilisateur
        User user = getUserById(userId); // Récupère l'utilisateur
        //user.getRoles().add(role);      // Ajoute le rôle (assurez-vous que `roles` est une collection dans `User`)
        return userRepository.save(user); // Sauvegarde l'utilisateur mis à jour
    }*/
}
