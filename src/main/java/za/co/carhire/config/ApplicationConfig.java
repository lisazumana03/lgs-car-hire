package za.co.carhire.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.carhire.repository.authentication.IUserRepository;

//This class is used to inject beans across the application
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .map(user -> {
                    // Convert user role to Spring Security authority with ROLE_ prefix
                    org.springframework.security.core.authority.SimpleGrantedAuthority authority =
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRole());

                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            java.util.Collections.singletonList(authority)
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This is responsible for the Data Access Object (DAO) authentication
    //Which means it retrieves user details from a database or other persistent storage
    //and uses that information to authenticate users.
    // It uses the UserDetailsService to load user-specific data
    // and the PasswordEncoder to handle password hashing and verification.
    //and it is used by the AuthenticationManager to perform authentication.
    //The AuthenticationManager uses the AuthenticationProvider to authenticate user credentials
    // definition of the authenticationManager is a bean that provides the authentication manager for the application.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
