package org.aeis.usermanagement.service.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.aeis.usermanagement.entity.Instructor;
import org.aeis.usermanagement.entity.Role;
import org.aeis.usermanagement.entity.Student;
import org.aeis.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract specific claims
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public List<Map<String, Object>> extractCourses(String token) {
        return extractClaim(token, claims -> claims.get("courses", List.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Create token with user details and custom claims
    public String generateTokenWithUserInfo(User user) {
        Map<String, Object> claims = new HashMap<>();


        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());


        if (user instanceof Instructor) {
            handleIfUserIsInstructor((Instructor) user, claims);
        } else if (user instanceof Student) {
            handleIfUserIfStudent((Student) user, claims);
        }

        return buildToken(claims, user, jwtExpiration);
    }

    private  void handleIfUserIfStudent(Student user, Map<String, Object> claims) {
        Student student = user;
        claims.put("courses", student.getRegisteredCourses().stream()
                .map(course -> Map.of(
                        "id", course.getId(),
                        "name", course.getName()
                ))
                .collect(Collectors.toList()));
        claims.put("role", Role.STUDENT.name());
    }

    private  void handleIfUserIsInstructor(Instructor user, Map<String, Object> claims) {
        Instructor instructor = user;
        claims.put("courses", instructor.getAssignedCourses().stream()
                .map(course -> Map.of(
                        "id", course.getId(),
                        "name", course.getName()
                ))
                .collect(Collectors.toList()));
        claims.put("role", Role.INSTRUCTOR.name());
    }

    // Original method maintained for backward compatibility
    public String generateToken(User userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtExpiration);
    }




    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))  && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}