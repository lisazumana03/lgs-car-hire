package za.co.carhire.factory.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 12 May 2025
 */

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class BookingFactory {
    public static Booking createBooking(int bookingID, User user, List<Car>cars, LocalDateTime bookingDateAndTime, Date startDate, Date endDate, Location pickUpLocation, Location dropOffLocation,String bookingStatus){
        if(Helper.isNullOrEmpty(bookingStatus)){
            return null;
        }
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setCar(cars)
                .setBookingDateAndTime(bookingDateAndTime)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickUpLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus("Your booking has been created.")
                .build();
    }

    public static Booking cancelBooking(Booking booking){
        if (booking == null){
            return null;
        }
        return new Booking.Builder()
                .build();
    }
    public static Booking confirmBooking(Booking booking){
        if (booking == null){
            return null;
        }
        return booking;
    }

}
