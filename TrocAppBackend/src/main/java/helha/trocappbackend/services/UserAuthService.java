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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getFirstName() + " " + user.getLastName(),
                user.getPassword(),
                getGrantedAuthorities(user.getRoles()));
        return userDetails;
    }

    public List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName())); // Supposons que Role a une méthode getName()
        }
        return authorities;
    }
}
