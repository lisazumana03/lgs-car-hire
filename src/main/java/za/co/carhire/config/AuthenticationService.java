package za.co.carhire.config;
/* AuthenticationService.java

     User config/JAuthenticationService class

     Author: Bonga Velem

     Student Number: 220052379

     */
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.*;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.Impl.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // These will be injected by @RequiredArgsConstructor (Lombok)
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
                .setPassword(passwordEncoder.encode(request.password())) // Encode the password
                .setRole(request.role())
                .build();

        // User is saved to database
        User savedUser = userRepository.save(user);

        // Generate JWT token
        String jwtToken = jwtService.generateToken(savedUser);

        // This will convert to DTO and return response
        UserDTO userDTO = UserMapper.toDTO(savedUser);
        return new AuthenticationResponse(jwtToken, userDTO);
    }

    public AuthenticationResponse authenticate(LoginRequestDTO request) {
        // Authenticate with Spring Security
        // This will throw BadCredentialsException if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        // If authentication successful, load user from database
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        // Convert to DTO and return response
        UserDTO userDTO = UserMapper.toDTO(user);
        return new AuthenticationResponse(jwtToken, userDTO);
    }
}
