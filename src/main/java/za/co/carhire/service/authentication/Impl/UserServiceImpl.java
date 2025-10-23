package za.co.carhire.service.authentication.Impl;

/* UserServiceImpl.java

     User service/impl/UserServiceImpl class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authenticationDTO.LoginRequestDTO;
import za.co.carhire.dto.authenticationDTO.SignUpRequestDTO;
import za.co.carhire.dto.authenticationDTO.UpdateUserDTO;
import za.co.carhire.dto.authenticationDTO.UserDTO;
import za.co.carhire.mapper.UserMapper;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Deprecated // Use AuthenticationService.register() instead for JWT authentication
    public UserDTO registerUser(SignUpRequestDTO registerDTO) {
        // Build User via Builder
        User user = new User.Builder()
                .setIdNumber(registerDTO.idNumber())
                .setFirstName(registerDTO.firstName())
                .setLastName(registerDTO.lastName())
                .setEmail(registerDTO.email())
                .setDateOfBirth(registerDTO.dateOfBirth())
                .setPhoneNumber(registerDTO.phoneNumber())
                .setPassword(passwordEncoder.encode(registerDTO.password())) // Hash password with BCrypt
                .setRole(registerDTO.role())
                .build();

        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    @Override
    @Deprecated // Use AuthenticationService.authenticate() instead for JWT authentication
    public Optional<UserDTO> loginUser(LoginRequestDTO loginDTO) {
        // This method is deprecated - use AuthenticationService for JWT-based
        // authentication
        User user = userRepository.findByEmail(loginDTO.email())
                .orElse(null);

        if (user != null && passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            return Optional.of(UserMapper.toDTO(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> getUserProfile(Integer userId) {
        return userRepository.findById(userId).map(UserMapper::toDTO);
    }

    @Override
    public UserDTO updateUserProfile(Integer userId, UpdateUserDTO updateDTO) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Create a new User with updated fields using UpdateUserDTO
        User updated = new User.Builder()
                .setUserId(existing.getUserId()) // keep same ID
                .setIdNumber(updateDTO.idNumber())
                .setFirstName(updateDTO.firstName())
                .setLastName(updateDTO.lastName())
                .setEmail(updateDTO.email())
                .setDateOfBirth(updateDTO.dateOfBirth())
                .setPhoneNumber(updateDTO.phoneNumber())
                .setPassword(existing.getPassword()) // keep existing password
                .setRole(updateDTO.role())
                .build();

        User saved = userRepository.save(updated);
        return UserMapper.toDTO(saved);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserEntity(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}