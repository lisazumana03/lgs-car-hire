package za.co.carhire.dto.authenticationDTO;

import za.co.carhire.domain.authentication.Role;

import java.time.LocalDate;

public record SignUpRequestDTO(
        Integer userId,
        Long idNumber,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String phoneNumber,
        String password,
        Role role
) {
}
