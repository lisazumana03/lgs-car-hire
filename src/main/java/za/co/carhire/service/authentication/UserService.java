package za.co.carhire.service.authentication;

/* UserService.java

     User Service Interface

     Author: Bonga Velem

     Student Number: 220052379

     */
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.LoginRequestDTO;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> listUsers();

    UserDTO registerUser(SignUpRequestDTO registerDTO);

    Optional<UserDTO> loginUser(LoginRequestDTO loginDTO);

    Optional<UserDTO> getUserProfile(Integer userId);

    UserDTO updateUserProfile(Integer userId, UserDTO updateDTO);

    void deleteUser(Integer userId);

    // Internal method for getting User entity (for relationships)
    User getUserEntity(Integer userId);

    // Get User entity by email (for authorization checks)
    User getUserByEmail(String email);

}
