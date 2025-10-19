package za.co.carhire.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(auth -> auth
                                                // Public endpoints - no authentication required
                                                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                                                // Admin-only endpoints for Cars (POST, PUT, DELETE)
                                                .requestMatchers(HttpMethod.POST, "/api/car/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/api/car/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/api/car/**").hasRole("ADMIN")

                                                // Admin-only endpoints for Insurance
                                                .requestMatchers(HttpMethod.POST, "/api/insurance/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/api/insurance/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/api/insurance/**")
                                                .hasRole("ADMIN")

                                                // Admin-only endpoints for Maintenance
                                                .requestMatchers(HttpMethod.POST, "/api/maintenance/**")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/api/maintenance/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/api/maintenance/**")
                                                .hasRole("ADMIN")

                                                // Admin-only endpoint for sending Notifications
                                                .requestMatchers(HttpMethod.POST, "/api/notifications/**")
                                                .hasRole("ADMIN")

                                                // Admin-only for admin panel
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                                // Customer operations - all authenticated users can access
                                                .requestMatchers("/api/booking/**").authenticated()
                                                .requestMatchers("/api/payment/**").authenticated()
                                                .requestMatchers("/api/invoice/**").authenticated()
                                                .requestMatchers("/api/location/**").authenticated()

                                                // All other requests require authentication (includes all GET requests)
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:5173",
                                "http://127.0.0.1:5173"));
                configuration.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList(
                                "Authorization",
                                "Content-Type",
                                "X-Requested-With",
                                "Accept",
                                "Origin",
                                "Access-Control-Request-Method",
                                "Access-Control-Request-Headers"));
                configuration.setExposedHeaders(List.of("Authorization"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
