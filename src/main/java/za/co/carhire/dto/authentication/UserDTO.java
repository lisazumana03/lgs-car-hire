package za.co.carhire.dto;

import za.co.carhire.domain.authentication.UserRole;
import java.time.LocalDate;

public class UserDTO {
    private Integer userId;
    private Long idNumber;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String licenseNumber;
    private UserRole role;

    public UserDTO() {
    }

    public UserDTO(Integer userId, Long idNumber, String name, String email,
                   LocalDate dateOfBirth, String phoneNumber, String licenseNumber, UserRole role) {
        this.userId = userId;
        this.idNumber = idNumber;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.role = role != null ? role : UserRole.CUSTOMER;
    }

    public static class Builder {
        private Integer userId;
        private Long idNumber;
        private String name;
        private String email;
        private LocalDate dateOfBirth;
        private String phoneNumber;
        private String licenseNumber;
        private UserRole role = UserRole.CUSTOMER;

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setIdNumber(Long idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(userId, idNumber, name, email, dateOfBirth,
                    phoneNumber, licenseNumber, role);
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.role);
    }

    public boolean isCustomer() {
        return UserRole.CUSTOMER.equals(this.role);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", idNumber=" + idNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", role=" + role +
                '}';
    }
}