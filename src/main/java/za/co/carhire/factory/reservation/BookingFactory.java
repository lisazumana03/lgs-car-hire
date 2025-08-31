package za.co.carhire.factory.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 12 May 2025

Imtiyaaz Waggie 219374759
 */

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

import java.time.LocalDateTime;

public class BookingFactory {

    public static Booking createBooking(int bookingID, User user, Car car,
                                        LocalDateTime bookingDateAndTime,
                                        LocalDateTime startDate, LocalDateTime endDate,
                                        Location pickupLocation, Location dropOffLocation,
                                        BookingStatus bookingStatus) {
        if (bookingStatus == null) {
            bookingStatus = BookingStatus.PENDING;
        }

        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setCar(car)
                .setBookingDateAndTime(bookingDateAndTime)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(bookingStatus)
                .build();
    }

    public static Booking createBookingWithInsurance(int bookingID, User user, Car car,
                                                     Insurance insurance,
                                                     LocalDateTime bookingDateAndTime,
                                                     LocalDateTime startDate, LocalDateTime endDate,
                                                     Location pickupLocation, Location dropOffLocation) {
        return new Booking.Builder()
                .setBookingID(bookingID)
                .setUser(user)
                .setCar(car)
                .setInsurance(insurance)
                .setBookingDateAndTime(bookingDateAndTime)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(BookingStatus.PENDING)
                .build();
    }

    public static Booking createMinimalBooking(User user, Car car,
                                               LocalDateTime startDate, LocalDateTime endDate) {
        return new Booking.Builder()
                .setUser(user)
                .setCar(car)
                .setBookingDateAndTime(LocalDateTime.now())
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setBookingStatus(BookingStatus.PENDING)
                .build();
    }

    public static Booking cancelBooking(Booking existingBooking) {
        if (existingBooking == null) {
            return null;
        }

        return new Booking.Builder()
                .copy(existingBooking)
                .setBookingStatus(BookingStatus.CANCELLED)
                .build();
    }

    public static Booking confirmBooking(Booking existingBooking) {
        if (existingBooking == null || existingBooking.getBookingStatus() != BookingStatus.PENDING) {
            return null;
        }

        return new Booking.Builder()
                .copy(existingBooking)
                .setBookingStatus(BookingStatus.CONFIRMED)
                .build();
    }

    public static Booking declineBooking(Booking existingBooking) {
        if (existingBooking == null) {
            return null;
        }

        return new Booking.Builder()
                .copy(existingBooking)
                .setBookingStatus(BookingStatus.DECLINED)
                .build();
    }

    public static Booking createTestBooking() {
        return new Booking.Builder()
                .setBookingID(1)
                .setBookingDateAndTime(LocalDateTime.now())
                .setStartDate(LocalDateTime.now().plusDays(1))
                .setEndDate(LocalDateTime.now().plusDays(5))
                .setBookingStatus(BookingStatus.PENDING)
                .setTotalCost(2500.00)
                .build();
    }
}