package za.co.carhire.config;
/* AuthenticationService.java

     User config/JAuthenticationService class

     Author: Bonga Velem

     Student Number: 220052379

     */
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.*;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.Impl.JwtService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(SignUpRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }

        User user = new User.Builder()
                .setIdNumber(request.idNumber())
                .setFirstName(request.firstName())
                .setLastName(request.lastName())
                .setEmail(request.email())
                .setDateOfBirth(request.dateOfBirth())
                .setPhoneNumber(request.phoneNumber())
                .setPassword(passwordEncoder.encode(request.password()))
                .setRole(request.role())
                .build();

        User savedUser = userRepository.save(user);

        // Create Spring Security UserDetails with authorities
        UserDetails userDetails = createUserDetails(savedUser);

        // Generate JWT token with UserDetails (which includes authorities)
        String jwtToken = jwtService.generateToken(userDetails);

        UserDTO userDTO = UserMapper.toDTO(savedUser);
        return new AuthenticationResponse(jwtToken, userDTO);
    }

    public AuthenticationResponse authenticate(LoginRequestDTO request) {
        // Authenticate with Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        // If authentication successful, load user from database
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create Spring Security UserDetails with authorities
        UserDetails userDetails = createUserDetails(user);

        // Generate JWT token with UserDetails (which includes authorities)
        String jwtToken = jwtService.generateToken(userDetails);

        UserDTO userDTO = UserMapper.toDTO(user);
        return new AuthenticationResponse(jwtToken, userDTO);
    }

    // Helper method to convert User to Spring Security UserDetails with authorities
    private UserDetails createUserDetails(User user) {
        // Convert user role to Spring Security authority with ROLE_ prefix
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}