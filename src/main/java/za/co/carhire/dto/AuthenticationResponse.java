package za.co.carhire.dto;

import za.co.carhire.domain.authentication.UserRole;
import java.time.LocalDate;

/*
    Imtiyaaz Waggie 219374759
    Date: 8/10/2025
    Updated: 10/10/2025 - Added full user data to response
*/
public class AuthenticationResponse {
    private String token;
    private Integer userId;
    private String email;
    private String name;
    private UserRole role;
    private Long idNumber;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String licenseNumber;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, Integer userId, String email, String name, UserRole role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public AuthenticationResponse(String token, Integer userId, String email, String name, UserRole role,
                                   Long idNumber, LocalDate dateOfBirth, String phoneNumber, String licenseNumber) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
