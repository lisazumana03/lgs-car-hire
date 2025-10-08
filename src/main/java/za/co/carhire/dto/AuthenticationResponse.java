package za.co.carhire.dto;

import za.co.carhire.domain.authentication.UserRole;

/*
    Imtiyaaz Waggie 219374759
    Date: 8/10/2025
*/
public class AuthenticationResponse {
    private String token;
    private Integer userId;
    private String email;
    private String name;
    private UserRole role;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, Integer userId, String email, String name, UserRole role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
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
}
