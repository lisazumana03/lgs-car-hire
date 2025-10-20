package za.co.carhire.factory.reservation;

/**
 Lisakhanya Zumana (230864821)
 Date: 12 May 2025
 */

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

import java.time.LocalDateTime;

public class BookingFactory {

    public static Booking createBooking(int bookingID, User user, Car car, LocalDateTime bookingDateAndTime,
                                        LocalDateTime startDate, LocalDateTime endDate,
                                        Location pickupLocation, Location dropOffLocation,
                                        BookingStatus bookingStatus){
        if(Helper.isNullOrEmpty(bookingStatus)){
            bookingStatus = BookingStatus.PENDING;
        }
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setCar(car) // Single car object
                .setBookingDateAndTime(bookingDateAndTime)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(bookingStatus) // Use the provided status
                .build();
    }

    public static Booking createSimpleBooking(User user, Car car, LocalDateTime startDate,
                                              LocalDateTime endDate, Location pickupLocation,
                                              Location dropOffLocation) {
        return new Booking.Builder()
                .setUser(user)
                .setCar(car)
                .setBookingDateAndTime(LocalDateTime.now())
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(BookingStatus.PENDING)
                .build();
    }

    public static Booking cancelBooking(Booking booking) {
        return new Booking.Builder()
                .copy(booking)
                .setBookingStatus(BookingStatus.CANCELLED)
                .build();
    }

    public static Booking confirmBooking(Booking booking) {
        return new Booking.Builder()
                .copy(booking)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .build();
    }
}