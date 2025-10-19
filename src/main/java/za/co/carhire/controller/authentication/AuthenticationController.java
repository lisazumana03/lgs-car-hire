package za.co.carhire.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.config.AuthenticationService;
import za.co.carhire.dto.authenticationDTO.AuthenticationResponse;
import za.co.carhire.dto.authenticationDTO.LoginRequestDTO;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody SignUpRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequestDTO authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
