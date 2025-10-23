package za.co.carhire.service.authentication;

/* UserService.java

     User service/UserService interface

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.dto.authenticationDTO.LoginRequestDTO;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;
import za.co.carhire.dto.authenticationDTO.UpdateUserDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.domain.authentication.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> listUsers();

    @Deprecated // Use AuthenticationService.register() instead for JWT authentication
    UserDTO registerUser(SignUpRequestDTO registerDTO);

    @Deprecated // Use AuthenticationService.authenticate() instead for JWT authentication
    Optional<UserDTO> loginUser(LoginRequestDTO loginDTO);

    Optional<UserDTO> getUserProfile(Integer userId);

    // Updated method signature to use UpdateUserDTO
    UserDTO updateUserProfile(Integer userId, UpdateUserDTO updateDTO);

    void deleteUser(Integer userId);

    User getUserEntity(Integer userId);

    User getUserByEmail(String email);
}