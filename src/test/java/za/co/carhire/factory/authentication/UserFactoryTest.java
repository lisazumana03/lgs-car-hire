package za.co.carhire.factory.authentication;
/* UserFactoryTest.java

     UserFactoryTest/authentication/factory class Test

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.junit.jupiter.api.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private static User user1 = UserFactory.createUser("1109093","Bonga", "bongavelem@outlook.com", "1999-02-18","079 813 5811","Bruno11", "NPS5232");

    private static User userfail = UserFactory.createUser("1109093","Bonga", "bongavelemoutlook.com", "1999-02-18","079 813 5811","Bruno11", "NPS5232");

    private static User userLogin = UserFactory.loginUser("bongavelem@outlook.com", "Bonga34");

    private static User userLoginFail = UserFactory.loginUser("bongavelemoutlook.com", "Bonga34");

    private static User admin = UserFactory.createAdmin("9999999999", "Admin User", "admin@test.com", "1985-01-01", "+27123456789", "Admin@123", "ADMIN001");

    private static User userWithRole = UserFactory.createUserWithRole("8888888888", "Role User", "roleuser@test.com", "1990-06-15", "+27987654321", "User@123", "USER001", UserRole.USER);

    @Test
    void createUser() {
        assertNotNull(user1);
        assertEquals(UserRole.USER, user1.getRole());
        System.out.println("User Register test: " + user1.toString());
    }

    @Test
    void FailedCreateUser(){
        assertNotNull(userfail);
        System.out.println("Failed Register test: " + userfail.toString());
    }

    @Test
    void loginUser() {
        assertNotNull(userLogin);
        System.out.println("User Login test" + userLogin.toString());
    }

    @Test
    void LoginUserFail(){
        assertNotNull(userLoginFail);
        System.out.println("User Login test Fail: " + userLoginFail.toString());
    }

    @Test
    void createAdminUser() {
        assertNotNull(admin);
        assertEquals(UserRole.ADMIN, admin.getRole());
        assertEquals("Admin User", admin.getName());
        assertEquals("admin@test.com", admin.getEmail());
        System.out.println("Admin Create test: " + admin.toString());
    }

    @Test
    void createUserWithRole() {
        assertNotNull(userWithRole);
        assertEquals(UserRole.USER, userWithRole.getRole());
        assertEquals("Role User", userWithRole.getName());
        assertEquals("roleuser@test.com", userWithRole.getEmail());
        System.out.println("User with Role test: " + userWithRole.toString());
    }

    @Test
    void testDefaultRole() {
        User user = UserFactory.createUser("7777777777", "Default Role", "default@test.com", "1992-12-12", "+27111222333", "Pass@123", "DEF001");
        assertNotNull(user);
        assertEquals(UserRole.USER, user.getRole());
        System.out.println("Default role test: " + user.toString());
    }
}