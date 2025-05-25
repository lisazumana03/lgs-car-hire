package za.co.carhire.service.impl.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.repository.reservation.IBookingRepository;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private IBookingRepository IBookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBookings() {
    }

    @Test
    void create() {

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