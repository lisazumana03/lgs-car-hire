package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingControllerTest {

    private TestRestTemplate restTemplate;

    @InjectMocks
    private BookingController bookingController;

    private static Booking booking;
    @Mock
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        int bookingID = 1;
        User user = new User.Builder()
                .setUserId(4)
                .build();
        List<Car> cars = new ArrayList<>();
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

        booking = BookingFactory.createBooking(bookingID, user, cars, bookingDateAndTime, startDate, endDate, pickUpLocation, dropOffLocation, bookingStatus);
    }

    @Test
    @Order(1)
    void create() {
        String url = "/create";
        System.out.println("Post data: " + booking);
        ResponseEntity<Booking> response = restTemplate.postForEntity(url, booking, Booking.class);
        System.out.println("Response: " + response.getBody());
        assertEquals(booking, response.getBody());
    }

    @Test
    @Order(2)
    void read() {
    }

    @Test
    @Order(3)
    void update() {
        when(bookingService.update(booking)).thenReturn(booking);
        Booking updatedBooking = bookingController.update(booking).getBody();
        assertNotNull(updatedBooking);
        assertEquals(booking, updatedBooking);
        verify(bookingService, times(1)).update(booking);
    }

    @Test
    @Order(4)
    void delete() {
        String url = "delete/"+booking.getBookingID();
        System.out.println("URL: "+url);
        restTemplate.delete(url);
    }
}