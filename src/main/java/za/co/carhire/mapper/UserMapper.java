package za.co.carhire.mapper;

import org.springframework.stereotype.Component;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

  public UserDTO toDTO(User user) {
    if (user == null) {
      return null;
    }

    return new UserDTO(
        user.getUserId(),
        user.getIdNumber(),
        user.getName(),
        user.getEmail(),
        user.getDateOfBirth(),
        user.getPhoneNumber(),
        user.getLicenseNumber(),
        user.getRole());
  }

  public User toDomain(UserDTO dto) {
    if (dto == null) {
      return null;
    }

    return new User.Builder()
        .setUserId(dto.getUserId())
        .setIdNumber(dto.getIdNumber())
        .setName(dto.getName())
        .setEmail(dto.getEmail())
        .setDateOfBirth(dto.getDateOfBirth())
        .setPhoneNumber(dto.getPhoneNumber())
        .setLicenseNumber(dto.getLicenseNumber())
        .setRole(dto.getRole() != null ? dto.getRole() : za.co.carhire.domain.authentication.Role.CUSTOMER)
        .build();
  }

  public List<UserDTO> toDTOList(List<User> users) {
    if (users == null) {
      return null;
    }

    return users.stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public List<User> toDomainList(List<UserDTO> dtos) {
    if (dtos == null) {
      return null;
    }

    return dtos.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }
}
