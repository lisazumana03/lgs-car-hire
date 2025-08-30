package za.co.carhire.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.factory.authentication.UserFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private User testCustomer;
    private User testAdmin;

    @BeforeEach
    void setUp() {
        testCustomer = UserFactory.createTestCustomer();
        testAdmin = UserFactory.createTestAdmin();
    }

    @Test
    @DisplayName("Should register new customer")
    void testRegisterCustomer() throws Exception {
        when(userService.register(any())).thenReturn(testCustomer);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }

    @Test
    @DisplayName("Should login with valid credentials")
    void testLoginSuccess() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "john@example.com");
        credentials.put("password", "password123");

        when(userService.authenticate(anyString(), anyString())).thenReturn(testCustomer);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"))
                .andExpect(jsonPath("$.user.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("Should fail login with invalid credentials")
    void testLoginFail() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "john@example.com");
        credentials.put("password", "wrongpassword");

        when(userService.authenticate(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid email or password"));
    }

    @Test
    @DisplayName("Should get user profile")
    void testGetProfile() throws Exception {
        when(userService.read(1)).thenReturn(testCustomer);

        mockMvc.perform(get("/api/users/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("Should return 404 for non-existent user")
    void testGetProfileNotFound() throws Exception {
        when(userService.read(999)).thenReturn(null);

        mockMvc.perform(get("/api/users/profile/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    @Test
    @DisplayName("Should change password with correct old password")
    void testChangePassword() throws Exception {
        Map<String, String> passwords = new HashMap<>();
        passwords.put("oldPassword", "password123");
        passwords.put("newPassword", "newPassword456");

        when(userService.changePassword(eq(1), anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/users/change-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwords)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed successfully"));
    }

    @Test
    @DisplayName("Should fail password change with wrong old password")
    void testChangePasswordFail() throws Exception {
        Map<String, String> passwords = new HashMap<>();
        passwords.put("oldPassword", "wrongPassword");
        passwords.put("newPassword", "newPassword456");

        when(userService.changePassword(eq(1), anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/users/change-password/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwords)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid old password"));
    }

    @Test
    @DisplayName("Admin should get all users")
    void testGetAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(testCustomer, testAdmin));

        mockMvc.perform(get("/api/users/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Admin"));
    }

    @Test
    @DisplayName("Admin should get all customers")
    void testGetAllCustomers() throws Exception {
        when(userService.findAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        mockMvc.perform(get("/api/users/admin/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].role").value("CUSTOMER"));
    }

    @Test
    @DisplayName("Admin should get all admins")
    void testGetAllAdmins() throws Exception {
        when(userService.findAllAdmins()).thenReturn(Arrays.asList(testAdmin));

        mockMvc.perform(get("/api/users/admin/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].role").value("ADMIN"));
    }

    @Test
    @DisplayName("Admin should promote user to admin")
    void testPromoteToAdmin() throws Exception {
        when(userService.promoteToAdmin(1)).thenReturn(true);
        when(userService.read(1)).thenReturn(new User.Builder()
                .copy(testCustomer)
                .setRole(UserRole.ADMIN)
                .build());

        mockMvc.perform(put("/api/users/admin/promote/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User promoted to admin"))
                .andExpect(jsonPath("$.user.role").value("ADMIN"));
    }

    @Test
    @DisplayName("Admin should demote admin to customer")
    void testDemoteToCustomer() throws Exception {
        when(userService.demoteToCustomer(2)).thenReturn(true);
        when(userService.read(2)).thenReturn(new User.Builder()
                .copy(testAdmin)
                .setRole(UserRole.CUSTOMER)
                .build());

        mockMvc.perform(put("/api/users/admin/demote/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User demoted to customer"))
                .andExpect(jsonPath("$.user.role").value("CUSTOMER"));
    }

    @Test
    @DisplayName("Admin should delete user")
    void testDeleteUser() throws Exception {
        when(userService.read(1)).thenReturn(testCustomer);

        mockMvc.perform(delete("/api/users/admin/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    @DisplayName("Admin should find user by email")
    void testFindByEmail() throws Exception {
        when(userService.findByEmail("john.doe@example.com")).thenReturn(testCustomer);

        mockMvc.perform(get("/api/users/admin/by-email/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("Should check if user can rent car")
    void testCheckCanRent() throws Exception {
        when(userService.canRentCar(1)).thenReturn(true);

        mockMvc.perform(get("/api/users/admin/can-rent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.canRent").value(true));
    }
}