package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.reservation.Payment;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.factory.reservation.BookingFactory;
import za.co.carhire.service.reservation.impl.BookingService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static Booking booking;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/booking";
    }

    @BeforeEach
    void setUp() {
        int bookingID = 1;
        User user = new User.Builder()
                .setUserId(4)
                .build();

        Car car = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .build();

        LocalDateTime bookingDateAndTime = LocalDateTime.of(2025, 5, 25, 10, 0);
        LocalDateTime startDate = LocalDateTime.of(2025, 5, 25, 10, 30);
        LocalDateTime endDate = LocalDateTime.of(2025, 5, 25, 11, 0);
        Payment payment = new Payment.Builder()
                .setPaymentID(3)
                .build();

        Location pickUpLocation = new Location.Builder()
                .setLocationName("Beaufort West")
                .setProvinceOrState("Western Cape")
                .build();

        Location dropOffLocation = new Location.Builder()
                .setLocationName("Laingsburg")
                .setProvinceOrState("Western Cape")
                .build();

        BookingStatus bookingStatus = BookingStatus.PENDING;

        booking = BookingFactory.createBooking(bookingID, user, car, bookingDateAndTime, startDate, endDate, pickUpLocation, dropOffLocation, bookingStatus);
    }

    @Test
    @Order(1)
    void create() {
        String url = getBaseUrl() + "/create";
        System.out.println("Post data: " + booking);

        ResponseEntity<Booking> response = restTemplate.postForEntity(url, booking, Booking.class);

        System.out.println("Response: " + response.getBody());

        assertNotNull(response.getBody());
        assertEquals(booking.getBookingID(), response.getBody().getBookingID());
    }

    @Test
    @Order(2)
    void read() {
        String url = getBaseUrl() + "/read/" + booking.getBookingID();
        ResponseEntity<Booking> response = restTemplate.getForEntity(url, Booking.class);

        assertNotNull(response.getBody());
        assertEquals(booking.getBookingID(), response.getBody().getBookingID());
    }

    @Test
    @Order(3)
    void update() {
        String url = getBaseUrl() + "/update";
        ResponseEntity<Booking> response = restTemplate.postForEntity(url, booking, Booking.class);

        assertNotNull(response.getBody());
        assertEquals(booking.getBookingID(), response.getBody().getBookingID());
    }

    @Test
    @Order(4)
    void delete() {
        String url = "delete/"+booking.getBookingID();
        System.out.println("URL: "+url);
        restTemplate.delete(url);
    }
}