package za.co.carhire.dto.authenticationDTO;

import za.co.carhire.domain.authentication.Role;

public record LoginRequestDTO(
        String email,
        String password,
        Role role
) {
}
