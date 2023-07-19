package org.ufrpe.inovagovlab.decisoestce.seguranca.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "995fc63da5a2f0fc33748c930d3f26e8fffed3d456375ecaa1f6ec0ffeeafe2e";

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);

    }

    public boolean isTokenValid(String jwt, UserDetails user) {
        String username = extractUsername(jwt);
        return !isTokenExpired(jwt) && (username.equals(user.getUsername()));

    }

    public String generateToken(UserDetails user){
       return generateToken(user, new HashMap<>());
    }
    public String generateToken(UserDetails user, Map<String, Object> claims){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*24*60))
                .signWith(getKey())
                .compact();
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String jwt) {
       return extractExpiration(jwt).before(new Date());

    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt,Claims::getExpiration);
    }
}
