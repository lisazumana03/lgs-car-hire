package za.co.carhire.mapper.authentication;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.dto.authentication.UserDTO;

public class UserMapper {

    /**
     * Convert User entity to UserDTO (excludes password for security)
     * @param user the entity to convert
     * @return the DTO representation
     */
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO.Builder()
                .setUserId(user.getUserId())
                .setIdNumber(user.getIdNumber())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setDateOfBirth(user.getDateOfBirth())
                .setPhoneNumber(user.getPhoneNumber())
                .setLicenseNumber(user.getLicenseNumber())
                .setRole(user.getRole())
                .build();
    }

    /**
     * Convert UserDTO to User entity (password must be set separately)
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static User toEntity(UserDTO dto) {
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
                .setRole(dto.getRole())
                .build();
    }

    /**
     * Update existing User entity from UserDTO (preserves password)
     * @param existingUser the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static User updateEntityFromDTO(User existingUser, UserDTO dto) {
        if (existingUser == null || dto == null) {
            return existingUser;
        }

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setDateOfBirth(dto.getDateOfBirth());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setLicenseNumber(dto.getLicenseNumber());
        existingUser.setRole(dto.getRole());

        return existingUser;
    }
}