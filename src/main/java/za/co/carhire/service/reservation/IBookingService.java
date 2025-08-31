package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.dto.reservation.BookingDTO;
import za.co.carhire.service.IService;

/*
    Lisakhanya Zumana (230864821)
    Date: 24/05/2025 / Updated 2025
    Co-Author: Imtiyaaz Waggie 219374759
 */

import java.util.List;
import java.util.Set;

public interface IBookingService extends IService<Booking, Integer> {

    Booking read(int bookingID);
    Set<Booking> getBookings();
    void delete(int bookingID);

    Booking cancelBooking(int bookingID);
    Booking confirmBooking(int bookingID);

    Booking createFullBooking(BookingDTO bookingDto);

    List<Booking> getBookingsByUser(int userId);
    List<Booking> getBookingsByCar(int carId);
    List<Booking> getBookingsByStatus(String status);
    List<Booking> getBookingsByLocation(int locationId);
    List<Booking> getActiveBookings();
    List<Booking> getUpcomingBookings();
    List<Booking> getPastBookings();

    boolean checkCarAvailability(int carId, String startDate, String endDate);
    List<Booking> getConflictingBookings(int carId, String startDate, String endDate);

    double calculateTotalRevenue(String startDate, String endDate);
    int getTotalBookingsCount(String startDate, String endDate);
    List<Booking> getBookingsByDateRange(String startDate, String endDate);
}