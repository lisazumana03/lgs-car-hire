package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.service.IService;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.util.List;
import java.util.Set;

public interface IBookingService {

    Set<Booking> getBookings();

    Booking create(Booking booking);
    Booking read(int bookingID);
    Booking update(Booking booking);
    void delete(int bookingID);
}
