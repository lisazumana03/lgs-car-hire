package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.service.IService;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.util.Set;

public interface IBookingService {

    Set<Booking> getBookings();

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking booking);

    void deleteBooking(Booking booking);
}
