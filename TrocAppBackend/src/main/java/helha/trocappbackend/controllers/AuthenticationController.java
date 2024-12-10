package helha.trocappbackend.controllers;

import helha.trocappbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            User user = (User)auth.getPrincipal();
            JWT jwt = new JWT(jwtUtils.generateAccessToken(user),jwtUtils.generateRefreshToken(user));
            return ResponseEntity.ok(jwt);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }
}
