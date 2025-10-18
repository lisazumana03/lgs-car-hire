package za.co.carhire.mapper;

import org.springframework.stereotype.Component;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;

@Component
public class UserMapper {

    // Convert User entity to UserDTO
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getIdNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getRole());
    }

    // Convert SignUpRequestDTO to User entity (builder pattern)
    public static User toSignUpRequest(SignUpRequestDTO dto) {
        return new User.Builder()
                .setIdNumber(dto.idNumber())
                .setFirstName(dto.firstName())
                .setLastName(dto.lastName())
                .setEmail(dto.email())
                .setDateOfBirth(dto.dateOfBirth())
                .setPhoneNumber(dto.phoneNumber())
                .setPassword(dto.password())
                .setRole(dto.role())
                .build();
    }

}
