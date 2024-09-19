package uz.pdp.onlineshop.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.onlineshop.entity.User;

@Service
public class JwtService {
    @Value("${jjwt.token.key.secret}")
    private String JWT_TOKEN_SECRET;
    @Value("${jjwt.token.time.expiration}")
    private Long JWT_TOKEN_EXPIRATION;

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(JWT_TOKEN_SECRET.getBytes());
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user ) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("enabled", user.isEnabled())
                .claim("roles", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (userDetails.getUsername().equals(email) && !isTokenExpired(token));
    }


}
