package za.co.carhire.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.dto.AuthenticationRequest;
import za.co.carhire.dto.AuthenticationResponse;
import za.co.carhire.dto.SignUpRequest;
import za.co.carhire.security.JwtUtil;
import za.co.carhire.service.authentication.UserService;

/*

    Imtiyaaz Waggie 219374759

    Date: 25/05/2025

    Updated : UserController created 

*/

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);

            AuthenticationResponse response = new AuthenticationResponse(
                    token,
                    user.getUserId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole(),
                    user.getIdNumber(),
                    user.getDateOfBirth(),
                    user.getPhoneNumber(),
                    user.getLicenseNumber()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        try {
            if (userService.findAll().stream().anyMatch(u -> u.getEmail().equals(request.getEmail()))) {
                return ResponseEntity.status(409).build();
            }

            User user = new User.Builder()
                    .setIdNumber(request.getIdNumber())
                    .setName(request.getName())
                    .setEmail(request.getEmail())
                    .setDateOfBirth(request.getDateOfBirth())
                    .setPhoneNumber(request.getPhoneNumber())
                    .setPassword(request.getPassword())
                    .setLicenseNumber(request.getLicenseNumber())
                    .setRole(UserRole.USER)
                    .build();

            User savedUser = userService.save(user);
            String token = jwtUtil.generateToken(savedUser);

            AuthenticationResponse response = new AuthenticationResponse(
                    token,
                    savedUser.getUserId(),
                    savedUser.getEmail(),
                    savedUser.getName(),
                    savedUser.getRole(),
                    savedUser.getIdNumber(),
                    savedUser.getDateOfBirth(),
                    savedUser.getPhoneNumber(),
                    savedUser.getLicenseNumber()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<AuthenticationResponse> signupAdmin(@RequestBody SignUpRequest request) {
        try {
            if (userService.findAll().stream().anyMatch(u -> u.getEmail().equals(request.getEmail()))) {
                return ResponseEntity.status(409).build(); // Conflict
            }

            User user = new User.Builder()
                    .setIdNumber(request.getIdNumber())
                    .setName(request.getName())
                    .setEmail(request.getEmail())
                    .setDateOfBirth(request.getDateOfBirth())
                    .setPhoneNumber(request.getPhoneNumber())
                    .setPassword(request.getPassword())
                    .setLicenseNumber(request.getLicenseNumber())
                    .setRole(UserRole.ADMIN)
                    .build();

            User savedUser = userService.save(user);
            String token = jwtUtil.generateToken(savedUser);

            AuthenticationResponse response = new AuthenticationResponse(
                    token,
                    savedUser.getUserId(),
                    savedUser.getEmail(),
                    savedUser.getName(),
                    savedUser.getRole(),
                    savedUser.getIdNumber(),
                    savedUser.getDateOfBirth(),
                    savedUser.getPhoneNumber(),
                    savedUser.getLicenseNumber()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
