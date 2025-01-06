package helha.trocappbackend.services;

import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service responsible for loading user-specific data for authentication purposes.
 * Implements the {@link UserDetailsService} interface to integrate with Spring Security.
 * The service fetches user details from the database and provides the necessary authorities (roles).
 */
@Transactional(readOnly = true)
@Service
public class UserAuthService implements UserDetailsService {

    /**
     * Repository for accessing user data from the database.
     */
    @Autowired
    UserRepository userRepo;

    /**
     * Loads a user by their username.
     * This method is used by Spring Security to authenticate and authorize a user.
     *
     * @param username the username of the user to load.
     * @return a {@link UserDetails} object containing user information and authorities.
     * @throws UsernameNotFoundException if no user with the specified username is found.
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user.getRoles()));
        return userDetails;
    }

    /**
     * Converts a set of roles into a list of granted authorities.
     * Each role is prefixed with "ROLE_" to comply with Spring Security conventions.
     *
     * @param roles the set of roles associated with a user.
     * @return a list of {@link GrantedAuthority} objects representing the user's roles.
     */
    public List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())); // Assumes Role has a getName() method.
        }
        return authorities;
    }
}
