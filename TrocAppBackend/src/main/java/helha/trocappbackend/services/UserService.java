package helha.trocappbackend.services;


import helha.trocappbackend.models.Address;
import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RoleRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.repositories.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class UserService implements IUserService{
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

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
            if (user.getAddress() != null) {
                // Ici on s'assure que l'adresse est bien persistée, si elle est modifiée
                Address updatedAddress = addressRepository.save(user.getAddress()); // Sauvegarde de l'adresse
                user.setAddress(updatedAddress); // Associe la nouvelle adresse à l'utilisateur
            }

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

    @Override
    public User addRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + userId + " introuvable."));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rôle avec ID " + roleId + " introuvable."));

        user.addRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<Role> getRoles(int id) {
        return roleRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Integer> getAllZipCodes() {
        // Récupère toutes les adresses et extrait les codes postaux sous forme d'entiers
        return addressRepository.findAll().stream()
                .map(Address::getZipCode)   // Récupère le zip code de chaque adresse
                .distinct()                 // Enlève les doublons
                .collect(Collectors.toList());  // Récupère la liste finale
    }

    public List<String> getAllStreets() {
        // Récupère toutes les adresses et extrait les codes postaux sous forme d'entiers
        return addressRepository.findAll().stream()
                .map(Address::getStreet)   // Récupère le zip code de chaque adresse
                .distinct()                 // Enlève les doublons
                .collect(Collectors.toList());  // Récupère la liste finale
    }

    @Override
    public List<String> getAllNumbers() {
        return addressRepository.findAll().stream()
                .map(Address::getNumber)   // Récupère le zip code de chaque adresse
                .distinct()                 // Enlève les doublons
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCities() {
        return addressRepository.findAll().stream()
                .map(Address::getCity)   // Récupère le zip code de chaque adresse
                .distinct()                 // Enlève les doublons
                .collect(Collectors.toList());  // Récupère la liste finale
    }


    @Override
    public User assignRolesToUser(int userId, List<Integer> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Nettoyer les rôles existants
        user.getRoles().clear();

        // Ajouter les nouveaux rôles
        for (int roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Rôle non trouvé"));
            user.addRole(role);
        }

        return userRepository.save(user);
    }

    /*@Override
    public void updateUserCredentials(String username, String newPassword, String newUsername)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        // Mettre à jour le mot de passe
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        // Mettre à jour le nom d'utilisateur
        if (newUsername != null && !newUsername.isEmpty()) {
            user.setUsername(newUsername);
        }

        // Sauvegarder les modifications
        userRepository.save(user);
    }*/
}
