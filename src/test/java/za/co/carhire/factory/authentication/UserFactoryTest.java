package za.co.carhire.factory.authentication;
/* UserFactoryTest.java

     UserFactoryest/authentication/factory class Test

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.junit.jupiter.api.*;
import za.co.carhire.domain.authentication.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private static User user1 = UserFactory.createUser("1109093","Bonga", "bongavelem@outlook.com", "1999-02-18","079 813 5811","Bruno11", "NPS5232");

    private static User userfail = UserFactory.createUser("1109093","Bonga", "bongavelemoutlook.com", "1999-02-18","079 813 5811","Bruno11", "NPS5232");

    private static User userLogin = UserFactory.loginUser("bongavelem@outlook.com", "Bonga34");

    private static User userLoginFail = UserFactory.loginUser("bongavelemoutlook.com", "Bonga34");

    @Test
    void createUser() {
        assertNotNull(user1);
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
}