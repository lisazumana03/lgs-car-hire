package za.co.carhire.dto;

import java.time.LocalDate;

public class UserDTO {
    private Integer userId;
    private Long idNumber;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String licenseNumber;

    public UserDTO() {
    }

    public UserDTO(Integer userId, Long idNumber, String name, String email,
            LocalDate dateOfBirth, String phoneNumber, String licenseNumber) {
        this.userId = userId;
        this.idNumber = idNumber;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
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
                '}';
    }
}