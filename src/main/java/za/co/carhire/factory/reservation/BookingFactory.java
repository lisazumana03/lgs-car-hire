package za.co.carhire.factory.reservation;

/*
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
import java.util.List;

public class BookingFactory {
    public static Booking createBooking(int bookingID, User user, List<Car>cars, LocalDateTime bookingDateAndTime, LocalDateTime startDate, LocalDateTime endDate, Location pickupLocation, Location dropOffLocation, BookingStatus bookingStatus){
        if(Helper.isNullOrEmpty(bookingStatus)){
            bookingStatus = BookingStatus.PENDING;
        }
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setCar(cars)
                .setBookingDateAndTime(bookingDateAndTime)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(BookingStatus.PENDING)
                .build();
    }

    public static Booking cancelBooking(int bookingID, User user, LocalDateTime bookingDateAndTime,BookingStatus bookingStatus){
        if(Helper.isNullOrEmpty(bookingStatus)){
            return null;
        }
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setBookingDateAndTime(bookingDateAndTime)
                .setBookingStatus(BookingStatus.CANCELLED)
                .build();
    }
    public static Booking confirmBooking(int bookingID, User user, LocalDateTime bookingDateAndTime,BookingStatus bookingStatus){
        if(Helper.isNullOrEmpty(bookingStatus)){
            return null;
        }
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setBookingDateAndTime(bookingDateAndTime)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .build();
    }

}
