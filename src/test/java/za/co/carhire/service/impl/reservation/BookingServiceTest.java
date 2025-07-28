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
import za.co.carhire.factory.reservation.BookingFactory;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.reservation.impl.BookingService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IBookingRepository bookingRepository;

    private Booking booking01;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        booking01 = BookingFactory.createBooking(booking01.getBookingID(), booking01.getUser(), booking01.getCar(), booking01.getBookingDateAndTime(), booking01.getStartDate(), booking01.getEndDate(), booking01.getPickupLocation(), booking01.getDropOffLocation(), booking01);
    }

    @Test
    void getBookings() {
    }

    @Test
    void create() {
        when(bookingRepository.save(booking01)).thenReturn(booking01);
        assertEquals(booking01, bookingService.create(booking01));
    }

    @Test
    void read() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {

    }
}