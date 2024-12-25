package helha.trocappbackend.controllers;
/*
import helha.trocappbackend.models.JWT;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.UserService;
import helha.trocappbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            User user = (User)auth.getPrincipal();
            helha.trocappbackend.models.User userInternal = userRepository.findByUsername(username);
            JWT jwt = new JWT(jwtUtils.generateAccessToken(user,userInternal),jwtUtils.generateRefreshToken(user,userInternal));
            return ResponseEntity.ok(jwt);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }
}
*/