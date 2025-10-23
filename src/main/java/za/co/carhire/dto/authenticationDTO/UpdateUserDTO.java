package za.co.carhire.dto.authenticationDTO;

import za.co.carhire.domain.authentication.Role;

import java.time.LocalDate;

public record UpdateUserDTO(
        Long idNumber,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String phoneNumber,
        Role role
) {
    public UpdateUserDTO {
        // Validation can be added here if needed
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
}