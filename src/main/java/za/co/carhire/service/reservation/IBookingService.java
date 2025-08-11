package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.service.IService;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.util.List;
import java.util.Set;

public interface IBookingService extends IService<Booking, Integer> {
    Set<Booking> getBookings();
    void delete(int bookingID);
    //Cancel booking logic
    Booking cancelBooking(int bookingID);
}
