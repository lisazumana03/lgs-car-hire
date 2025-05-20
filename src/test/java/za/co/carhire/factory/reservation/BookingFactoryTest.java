package za.co.carhire.factory.reservation;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Lisakhanya Zumana (230864821)
Date: 14/05/2025
 */

class BookingFactoryTest {

    @Test
    @Order(1)
    void createBooking() {
        int bookingID = 15;
        User user = new User();

        List<Car> cars = new ArrayList<>();
        cars.add(new Car());

        LocalDateTime bookingDateAndTime = LocalDateTime.now();

        LocalDateTime startDate = LocalDateTime.of(2025, 6, 10, 15, 45);
        LocalDateTime endDate = LocalDateTime.of(2025, 7, 10, 16, 45);

        Location pickupLocation = new Location();
        Location dropOffLocation = new Location();

        String bookingStatus = "Your booking has been created.";

        Booking booking = BookingFactory.createBooking(bookingID, user, cars, bookingDateAndTime, startDate, endDate, pickupLocation, dropOffLocation, bookingStatus);

        System.out.println(booking);
        assertNotNull(booking);
    }

    @Test
    @Order(2)
    void createCancelledBooking() {
        int bookingID = 17;
        User user = new User();

        LocalDateTime bookingDateAndTime = LocalDateTime.now();

        String bookingStatus = "Your booking has been cancelled.";

        Booking booking = BookingFactory.cancelBooking(bookingID, user, bookingDateAndTime, bookingStatus);

        System.out.println(booking);
        assertNotNull(booking);
    }

    @Test
    @Order(3)
    void createConfirmedBooking() {
        int bookingID = 19;
        User user = new User();

        LocalDateTime bookingDateAndTime = LocalDateTime.now();

        String bookingStatus = "Your booking has been confirmed.";

        Booking booking = BookingFactory.cancelBooking(bookingID, user, bookingDateAndTime, bookingStatus);

        System.out.println(booking);
        assertNotNull(booking);
    }

}