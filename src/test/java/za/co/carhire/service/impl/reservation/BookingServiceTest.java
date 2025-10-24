package za.co.carhire.service.impl.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.factory.reservation.BookingFactory;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.reservation.impl.BookingService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IBookingRepository bookingRepository;

    private Booking booking;

    private Car car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<Car> cars = new ArrayList<>();

        Booking booking = new Booking.Builder()
                .setBookingID(4)
                .setCar(cars)
                .setBookingStatus(BookingStatus.PENDING)
                .build();
    }

    @Test
    void getBookings() {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findAll()).thenReturn(bookings);

        Set<Booking> result = bookingService.getBookings();
        assertEquals(bookings.size(), result.size());
        assertTrue(result.containsAll(bookings));
    }

    @Test
    void create() {
        when(bookingRepository.save(booking)).thenReturn(booking);
        assertEquals(booking, bookingService.create(booking));
    }

    @Test
    void read() {
        when(bookingRepository.findById(booking.getBookingID())).thenReturn(Optional.of(booking));
        assertEquals(booking, bookingService.read(booking.getBookingID()));
    }

    @Test
    void update() {
        when(bookingRepository.save(booking)).thenReturn(booking);
        assertEquals(booking, bookingService.update(booking));
    }

    @Test
    void delete() {
        bookingRepository.delete(booking);
        verify(bookingRepository).delete(booking);
    }
}
