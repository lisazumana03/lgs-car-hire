package za.co.carhire.dto;

import za.co.carhire.domain.authentication.Role;

import java.time.LocalDate;

/* SignUpRequest.java

     SignUpRequest DTO class for handling user registration requests

     Author: Bonga Velem

     Student Number: 220052379

     */

public class SignUpRequest {
    private Long idNumber;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String password;
    private String licenseNumber;
    private Role role;

    public SignUpRequest() {
    }

    public SignUpRequest(Long idNumber, String name, String email, LocalDate dateOfBirth,
                        String phoneNumber, String password, String licenseNumber, Role role) {
        this.idNumber = idNumber;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.licenseNumber = licenseNumber;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "idNumber=" + idNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='[PROTECTED]'" +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
