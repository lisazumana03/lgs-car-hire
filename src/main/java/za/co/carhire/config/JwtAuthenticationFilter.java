package za.co.carhire.config;
/* JwtAuthenication.java

     User config/JwtAuthentication class

     Author: Bonga Velem

     Student Number: 220052379

     */

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import za.co.carhire.service.authentication.Impl.JwtService;
//This class will be responsible for filtering incoming requests and validating JWT tokens
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>
     * Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */

    private final JwtService jwtService;

    // Reminder about UserDetailsService:
    // UserDetailsService is a core interface in Spring Security that is used to
    // retrieve user-related data.
    // this class locates the username from the token and loads user details from
    // the database
    private final UserDetailsService userDetailsService;

    // HtttpServletRequest
    // Filter to validate JWT token from incoming requests
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        // Use the token for validation
        final String jwtToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwtToken);// Placeholder for actual extraction
                                                         // userEmailFromToken(jwtToken);
        // Validate the token and set authentication in the security context
        // The first step is to extract the token from the Authorization header of the
        // incoming HTTP request.
        // The token is expected to be in the format "Bearer <token
        // the first part of this if statement checks if the Authorization header is
        // present and starts with "Bearer ".
        // If not, it simply continues the filter chain without any further processing.
        // If the token is present, it extracts the actual JWT token by removing the
        // "Bearer " prefix.
        // Then, it uses the JwtService to extract the username (or email) from the
        // token.
        // The extracted username can then be used to validate the token and set the
        // authentication in the security context.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Here, you would typically set the authentication in the security context
            // For simplicity, this example does not include that part
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);// fetches from the database
            // Validate the token
            // The second part of the if statement checks if a username was successfully
            // extracted from the token
            // and if there is no existing authentication in the security context.
            // If both conditions are met, it loads the user details from the database using
            // the UserDetailsService.
            // Finally, it validates the token using the JwtService.
            // If the token is valid, you would typically create an Authentication object
            // and set it in the security context.
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // Set the authentication in the security context
                // You would create an Authentication object and set it here
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
