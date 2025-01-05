package helha.trocappbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTokenMs}")
    private long expirationToken;

    @Value("${jwt.expirationRefreshTokenMs}")
    private long expirationRefreshToken;


    public String generateAccessToken(User user, helha.trocappbackend.models.User userInternal) {
        return generateToken(user,userInternal,expirationToken);
    }

    public String generateRefreshToken(User user, helha.trocappbackend.models.User userInternal){
        return generateToken(user,userInternal,expirationRefreshToken);
    }

    private String generateToken(User user, helha.trocappbackend.models.User userInternal,long expiration) {
        return Jwts.builder()
                .claims(Jwts.claims()
                        .subject(user.getUsername())
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime()+expiration))
                        .add("roles",user.getAuthorities())
                        .add("id",userInternal.getId())
                        .add("unique_name",user.getUsername())
                        .add("email", userInternal.getEmail())
                        .build())
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public Claims parseToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getPayload();
    }
}
