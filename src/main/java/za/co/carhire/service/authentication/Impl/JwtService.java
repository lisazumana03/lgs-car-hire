package za.co.carhire.service.authentication.Impl;
/* UserServiceImpl.java

     User service/authentication/impl/JwtService class

     Author: Bonga Velem

     Student Number: 220052379

     */
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
//Note to Bonga:
//3rd step: Create JwtService class to handle JWT operations
// This class will be responsible for generating, validating, and extracting information from JWT tokens.
// It is called by the JwtAuthenticationFilter to validate incoming requests.
//The Valid JWT token is generated upon successful authentication (e.g., during login) and is used to authorize subsequent requests.
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    public String extractUsername(String jwtToken) {
        //This method extracts the username (subject) from the JWT token
        return extractClaim(jwtToken, Claims::getSubject);

    }

    // Generic method to extract any claim from the JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // Method to generate a JWT token for a user without extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    //Reminder for myself:
    //Remember: all related to the JJWT 0.12.x API changes, for next time refer to the official documentation
    // Method to generate a JWT token for a user
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        //This method generates a JWT token for the given username
        return Jwts
                .builder()
                .claims(extraClaims)  // Changed from setClaims
                .subject(userDetails.getUsername())  // Changed from getSubject to subject
                .issuedAt(new Date(System.currentTimeMillis()))  // Changed from setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))  // Changed from setExpiration
                .signWith(getSigningKey())  // Removed SignatureAlgorithm parameter
                .compact();
    }

    // Method to validate the JWT token against user details

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    // Method to check if the JWT token has expired
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    // Method to extract the expiration date from the JWT token
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }


    // Method to extract all claims from the JWT token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser() // Changed from parserBuilder() to parser()
                .verifyWith(getSigningKey()) // Changed from setSigningKey() to verifyWith()
                .build()
                .parseSignedClaims(jwtToken) // Changed from parseClaimsJws() to parseSignedClaims()
                .getPayload(); // Changed from getBody() to getPayload()
    }

    // The secret key is used to sign and verify the JWT tokens
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
