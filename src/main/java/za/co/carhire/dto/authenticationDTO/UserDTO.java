package za.co.carhire.dto.authenticationDTO;
/* User.java

     User domain/authentication class

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.domain.authentication.Role;

import java.time.LocalDate;

public record UserDTO(
        Integer userId,
        Long idNumber,
        String firstName,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        String phoneNumber,
        Role role) {
}
