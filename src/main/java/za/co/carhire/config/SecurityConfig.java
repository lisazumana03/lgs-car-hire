package za.co.carhire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    // Security configurations would go here
    @Bean
    public String securityBean() {
        return "Security Configured";
    }
}
