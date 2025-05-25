package za.co.carhire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Notification;
import za.co.carhire.factory.authentication.UserFactory;
import za.co.carhire.factory.reservation.NotificationFactory;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}